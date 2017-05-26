package com.pltfm.app.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.dao.ProductStockDAO;
import com.pltfm.app.dao.StockOutDAO;
import com.pltfm.app.dao.StockOutDetailDAO;
import com.pltfm.app.service.StockOutDetailService;
import com.pltfm.app.service.StockOutService;
import com.pltfm.app.vobject.ProductStock;
import com.pltfm.app.vobject.StockOut;
import com.pltfm.app.vobject.StockOutDetail;
import com.pltfm.app.vobject.StockOutDetailExample;
import com.pltfm.app.vobject.StockOutDetailExample.Criteria;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;

/**
 * 接口：出库明细单Service
 * 
 * @author luoyi
 * @createDate 2013/08/16
 */
@Service(value = "stockOutDetailService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class StockOutDetailServiceImpl implements StockOutDetailService {

    private Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private StockOutDetailDAO stockOutDetailDao;
    
	@Resource
	private StockOutDAO stockOutDao;
    
	@Resource
	private StockOutService stockOutService;
	
	@Resource ProductStockDAO productStockDao;
    
	@Override
	public List<StockOutDetail> findStockOutDetailList(Page page, StockOutDetail stockOutDetail, Long stockOutId)
            throws ServiceException {
		int pageIndex = page.getPageNo();
		if (pageIndex == 0)
			pageIndex = 1;
		int pageSize = page.getPageSize();
		int skip = (pageIndex - 1) * pageSize;
		// 每页记录数
		int max = pageSize;

		StockOutDetailExample example = new StockOutDetailExample();
		Criteria criteria = example.createCriteria();
		// 状态不为空
		if (null != stockOutId) {
			criteria.andStockOutIdEqualTo(stockOutId);
		}
		// 设置按出库单ID排序
		example.setOrderByClause(" detail_Id ASC");

        try {
            // 总记录数
            int count = stockOutDetailDao.countByExample(example);
            List<StockOutDetail> stockOutDetailList = stockOutDetailDao.selectByExample(example, skip, max);
            page.setDataList(stockOutDetailList);// 设置数据
            page.setRecordCount(count);// 设置总记录数
            return stockOutDetailList;
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }
	
	@Override
	public List<StockOutDetail> findStockOutDetailList(Long stockOutId) throws ServiceException {
		StockOutDetailExample example = new StockOutDetailExample();
		Criteria criteria = example.createCriteria();
		// 状态不为空
		if (null != stockOutId) {
			criteria.andStockOutIdEqualTo(stockOutId);
		}
		// 设置按出库单ID排序
		example.setOrderByClause(" detail_Id ASC");

        try {
            return stockOutDetailDao.selectByExample(example);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Transactional(readOnly=true,propagation=Propagation.REQUIRED,rollbackFor = ServiceException.class)
	public StockOut getStockOutByDetail(List<StockOutDetail> stockOutDetails, StockOut stockOut)
            throws ServiceException {
		// 出库总数量
		int totalQuantity = 0;
		// 出库总金额
		double totalMoney = 0;
		for (StockOutDetail st : stockOutDetails) {
			totalQuantity += st.getQuantity();// 计算得到总数量
			totalMoney += st.getQuantity() * st.getPrice().doubleValue(); // 成本*数量得到总金额
			double money = st.getQuantity() * st.getPrice().doubleValue();// 单个产品总金额
			st.setSum(new BigDecimal(money));// 不含税金额
			st.setPrice(st.getPrice());// 税率
		}

		stockOut.setTotalQuantity(totalQuantity);// 总数量
		stockOut.setTotalSum(new BigDecimal(totalMoney));// 出库总金额
		return stockOut;
	}

	@Override
	@Transactional(readOnly=true,propagation=Propagation.REQUIRED,rollbackFor = ServiceException.class)
	public ResultMessage saveStockOutDetail(StockOut stockOut, List<StockOutDetail> stockOutDetails)
            throws ServiceException {
		ResultMessage resultMessage = new ResultMessage();
		resultMessage.setIsSuccess(false);
		resultMessage.setMessage("保存出库单失败!");
		
		try {
			// 主单处理
			getStockOutByDetail(stockOutDetails, stockOut);
			// 首先，进行主单的保存(出库单)
			stockOutDao.insertSelective(stockOut);
			// 主要是为了获得保存后的采购单ID
			StockOut stockOut2 = null;
			stockOut2 = stockOutService.findStockOutByNo(stockOut.getStockOutNo());
			// 进行从单的保存(出库明细单)
			List<Map<String,Long>> mapList=new ArrayList<Map<String,Long>>();

			for (StockOutDetail stockOutDetail : stockOutDetails) {
				stockOutDetail.setStockOutId(stockOut2.getStockOutId());// 设置出库单ID
				Map<String,Long> map = new HashMap<String, Long>();
				map.put("warehouseId", stockOut2.getWarehouseId());
				map.put("skuAttributeId", stockOutDetail.getProductSkuId());
				mapList.add(map);
			}

			//根据仓库ID和产品ID,查找到库存ID,
			List<ProductStock> productStockList= productStockDao.selectProductStockList(mapList);
			int sizeI = stockOutDetails.size();
			int sizeI2 = productStockList.size();
			if(sizeI != sizeI2) {
                throw new ServiceException("数据异常，保存出库单失败!");
            }

			//给出库细目单设置库存ID
			for (int i = 0; i < sizeI; i++) {//外层细目单
				for (int j = 0; j < sizeI2; j++) {//得到的库存ID集合
					if(stockOutDetails.get(i).getProductSkuId()==productStockList.get(j).getSkuAttributeId().intValue()){
						stockOutDetails.get(i).setStockId(productStockList.get(j).getStockId());//设置库存ID
						break;
					}
				}
			}
			
			//批量保存
			int count = stockOutDetailDao.batchInsertStockOut(stockOutDetails);
			if(count<1){
				throw new ServiceException("保存出库单明细失败!");
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}

		resultMessage.setIsSuccess(true);
		resultMessage.setMessage("保存出库单成功!");
		return resultMessage;
	}

	@Transactional(readOnly=true,propagation=Propagation.REQUIRED,rollbackFor = ServiceException.class)
	public ResultMessage updateStockOutDetail(StockOut stockOut, List<StockOutDetail> stockOutDetails)
            throws ServiceException {
		ResultMessage resultMessage = new ResultMessage();
		resultMessage.setIsSuccess(true);
		resultMessage.setMessage("修改出库单成功!");

		try {
			// 主单处理
			getStockOutByDetail(stockOutDetails, stockOut);
			stockOutDao.updateByPrimaryKeySelective(stockOut);
			// 根据stockOutId查找原有的细目单
			StockOutDetailExample example = new StockOutDetailExample();
			Criteria criteria = example.createCriteria();
			criteria.andStockOutIdEqualTo(stockOut.getStockOutId());
			List<StockOutDetail> stockOutDetails2 = stockOutDetailDao.selectByExample(example);
			//被删除的细目单元素
			List<StockOutDetail> delDetailList = new LinkedList<StockOutDetail>();
			// 修改的数据为原有的detailId数据(直接批量保存)
			List<StockOutDetail> oldDetailList = new LinkedList<StockOutDetail>();
			//新添加的数据(批量添加)
			List<StockOutDetail> newDetailList = new LinkedList<StockOutDetail>();
			Map<Long,StockOutDetail> oldStockOutDetailMap = new HashMap<Long,StockOutDetail>();
			Map<Long,StockOutDetail> newStockOutDetailMap = new HashMap<Long,StockOutDetail>();
			for(StockOutDetail sod : stockOutDetails2){
				oldStockOutDetailMap.put(sod.getDetailId(), sod);
			}
			for(StockOutDetail sod : stockOutDetails){
				newStockOutDetailMap.put(sod.getDetailId(), sod);
			}

			for (int i = 0; i < stockOutDetails.size(); i++) {
				StockOutDetail sod = stockOutDetails.get(i);
				if(sod.getDetailId()==null){//新增的明细
					sod.setStockOutId(stockOut.getStockOutId());
					newDetailList.add(sod);
				}else if(oldStockOutDetailMap.containsKey(sod.getDetailId())){//原有的明细修改
					oldDetailList.add(sod);
				}
			}
			for (int i = 0; i < stockOutDetails2.size(); i++) {
				StockOutDetail sod = stockOutDetails2.get(i);
				if(!newStockOutDetailMap.containsKey(sod.getDetailId())){//与原有明细不匹配的，说明需要删除的
					delDetailList.add(sod);
				}
			}
			
			int count = 0;//记录数
			//进行细目单批量删除、修改、添加
			if(null!=delDetailList && delDetailList.size()>0){
				count = stockOutDetailDao.batchDeleteStockOutDetail(delDetailList);//批量删除
				if(count<1){
					throw new ServiceException("配送单数据出错!");
				}
			}
			
			if(null!=oldDetailList && oldDetailList.size()> 0){
				count = stockOutDetailDao.batchUpdateStockOutDetail(oldDetailList);//批量修改
				if(count<1){
					throw new ServiceException("配送单数据出错!");
				}
			}
			
			if(null!=newDetailList && newDetailList.size()> 0){
				count = stockOutDetailDao.batchInsertStockOut(newDetailList);//批量添加
				if(count<1){
					throw new ServiceException("配送单数据出错!");
				}
			}
		} catch (Exception e) {
            logger.error("批量修改采购单失败.", e);
			resultMessage.setMessage("批量修改采购单失败!");
			resultMessage.setIsSuccess(false);
			throw new ServiceException(e);
		}
		return resultMessage;
	}

}