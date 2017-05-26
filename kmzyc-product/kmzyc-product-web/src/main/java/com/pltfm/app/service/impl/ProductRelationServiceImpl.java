package com.pltfm.app.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.cms.remote.service.RemoteDulitaocanService;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;
import com.pltfm.app.dao.ProductRelationDAO;
import com.pltfm.app.dao.ProductRelationDetailDAO;
import com.pltfm.app.dao.ProductmainTiedDAO;
import com.pltfm.app.enums.ProductRelationDelStatus;
import com.pltfm.app.enums.ProductRelationStatus;
import com.pltfm.app.service.CmsProductUpShelfService;
import com.pltfm.app.service.ProductRelationDetailService;
import com.pltfm.app.service.ProductRelationService;
import com.pltfm.app.service.ProductService;
import com.pltfm.app.util.PurchaseUtils;
import com.pltfm.app.vobject.ProductRelation;
import com.pltfm.app.vobject.ProductRelationAndDetail;
import com.pltfm.app.vobject.ProductRelationDetail;
import com.pltfm.app.vobject.ProductRelationDetailExample;
import com.pltfm.app.vobject.ProductRelationExample;
import com.pltfm.app.vobject.ProductRelationExample.Criteria;

@Component("productRelationService")
public class ProductRelationServiceImpl implements ProductRelationService {

    Logger logger = LoggerFactory.getLogger(ProductRelationServiceImpl.class);

    @Resource
    private ProductRelationDAO productRelationDAO;

    @Resource
    private ProductRelationDetailDAO productRelationDetailDao;

    @Resource
    private ProductRelationDetailService productRelationDetailService;

    @Resource
    private ProductService productService;

    @Resource
    private CmsProductUpShelfService cmsProductUpShelfService;

    @Resource
    private ProductmainTiedDAO productMainTiedDao;

    @Resource
    private RemoteDulitaocanService remoteDulitaocanService;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public Long saveProductRelation(ProductRelation productRelation) throws ServiceException {
        try {
            return productRelationDAO.insertProductRelation(productRelation);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void getProductRelationByMainSkuId(ProductRelation productRelation, Page page) throws ServiceException {
        ProductRelationExample example = new ProductRelationExample();
        Criteria criteria = example.createCriteria();
        criteria.andMainSkuIdEqualTo(productRelation.getMainSkuId());
        criteria.andDelStatusEqualTo(ProductRelationDelStatus.NOTDEL.getStatus().shortValue());
        Short typeMin = 1;
        Short typeMax = 3;
        criteria.andRelationTypeBetween(typeMin, typeMax);
        example.setOrderByClause("relation_type  asc ,relation_id desc");
        
        try {
            List<ProductRelation> relations = productRelationDAO.selectByExample(example, page);
            int count = productRelationDAO.countByExample(example);
            page.setDataList(relations);
            page.setRecordCount(count);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void getProductPackageByMainSkuId(ProductRelation productRelation, Page page) throws ServiceException {
        ProductRelationExample example = new ProductRelationExample();
        Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(productRelation.getRelationName())) {//套餐名称
            criteria.andRelationNameLike("%" + productRelation.getRelationName().trim() + "%");
        }
        if (StringUtils.isNotBlank(productRelation.getProductSkuCode())) {//按sku查询
            example.setProductSkuCode(productRelation.getProductSkuCode());
        }
        if (StringUtils.isNotBlank(productRelation.getSupplierName())) {//商户名称
            criteria.andSupplierNameLike("%" + productRelation.getSupplierName().trim() + "%");
        }
        if (productRelation.getStatus() != null) {//套餐状态
            criteria.andStatusEqualTo(productRelation.getStatus());
        }
        Short type = 0;
        criteria.andRelationTypeEqualTo(type);
        example.setOrderByClause("relation_id desc");
        
        try {
            List<ProductRelation> relations = productRelationDAO.selectByExampleTaoCan(example, page);
            int count = productRelationDAO.countByExampleTaoCan(example);
            page.setDataList(relations);
            page.setRecordCount(count);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ProductRelation queryProductRelation(Long relationId) throws ServiceException {
        ProductRelationExample example = new ProductRelationExample();
        Criteria criteria = example.createCriteria();
        criteria.andRelationIdEqualTo(relationId);

        try {
            List<ProductRelation> list = productRelationDAO.selectByExample(example);
            return list.get(0);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public List<ProductRelationAndDetail> selectProductRelationAndDetailBySkuId(Long skuId)
            throws ServiceException {
        try {
            return productRelationDAO.selectProductRelationAndDetailBySkuId(skuId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ProductRelationAndDetail> selectProductRelationAndDetailsByRelaitonSkuId(Long skuId)
            throws ServiceException {
        try {
            return productRelationDAO.selectProductRelationAndDetailsByRelaitonSkuId(skuId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public int batchDelProductRelationStatus(List<Long> relations) throws ServiceException {
        try {
            return productRelationDAO.batchUpateProductRelationStatus(relations);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 根据套餐id删除套餐（组方）信息
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public int delProductRelation(List<Long> relationIds) throws ServiceException {
        try {
            productRelationDetailDao.batchDelProductRelationDetailByRelationId(relationIds);//删除子表的数据
            productRelationDAO.batchDelProductRelation(relationIds);
            return 0;
        } catch (Exception e) {
            logger.error("根据套餐id删除套餐（组方）信息出现异常", e);
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public String upOrDownShelf(List<Long> ids, String type) throws ServiceException {
        String result = "";
        ProductRelation productRelation = null;
        ProductRelation productRelationCms = null;
        List<ProductRelation> productRelationList = new ArrayList<ProductRelation>();
        Map cmsRet = null;
        try {
            for (int j = 0; j < ids.size(); j++) {
                productRelationCms = new ProductRelation();
                productRelationCms.setRelationId(ids.get(j));
                productRelationList.add(productRelationCms);
            }
            if ("up".equals(type.trim())) {
                cmsRet = remoteDulitaocanService.remoteParse(productRelationList);//调用cms发布上架接口
                if (cmsRet == null) {
                    return "调用上架接口出现异常！";
                }
            }
            productRelationList.clear();
            for (int i = 0; i < ids.size(); i++) {
                //	productRelation = productRelationDAO.selectByPrimaryKey(ids.get(i));
                productRelation = new ProductRelation();
                productRelation.setRelationId(ids.get(i));
                if ("up".equals(type.trim())) {//执行上架操作
                    productRelation.setStatus(Short
                            .valueOf(ProductRelationStatus.UP.getStatus().toString()));
                } else {//执行下架操作
                    productRelation.setStatus(Short
                            .valueOf(ProductRelationStatus.DOWN.getStatus().toString()));
                }
                productRelationList.add(productRelation);
            }

            productRelationDAO.batchUpdateRelationList(productRelationList);

            if ("up".equals(type.trim()))
                result = "上架成功！";
            else
                result = "下架成功！";
        } catch (Exception e) {
            logger.error("套餐上下架出现异常", e);
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public int updateProductRelationStatus(Long relationId, String status) throws ServiceException {
        Map<String, String> condition = new HashMap<String, String>();
        condition.put("relationId", relationId.toString());
        condition.put("status", status);

        try {
            return productRelationDAO.updateProductRelationStatus(condition);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public int updateProductRelation(ProductRelation productRelation) throws ServiceException {
        ProductRelationDetailExample example = new ProductRelationDetailExample();
        com.pltfm.app.vobject.ProductRelationDetailExample.Criteria criteria = example.createCriteria();
        criteria.andRelationIdEqualTo(productRelation.getRelationId());
        try {
            return productRelationDAO.updateProductRelation(productRelation);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ProductRelation selectProductRelationById(Long productRelationId) throws ServiceException {
        try {
            return productRelationDAO.selectByPrimaryKey(productRelationId);
        } catch (Exception e) {
            logger.error("根据套餐id查询套餐信息出现异常", e);
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public int updateProductRelationSer(ProductRelation productRelation) throws ServiceException {
        try {
            return productRelationDAO.updateByPrimaryKeySelective(productRelation);
        } catch (Exception e) {
            logger.error("根据套餐id修改套餐信息出现异常", e);
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public int addStatusUpdateProductRelation(ProductRelationDetail newRelationDetail) throws ServiceException {
        try {
            productRelationDetailDao.updateByPrimaryKeySelective(newRelationDetail);
            return 0;
        } catch (Exception e) {
            logger.error("根据套餐id修改套餐信息出现异常", e);
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public int addStatusInsertProductRelation(List<Long> skuIds, List<BigDecimal> prices, List<Short> proCount,
                                              ProductRelation productRelation) throws ServiceException {
        int count = 0;
        ProductRelationDetail newRelationDetail = new ProductRelationDetail();
        try {
            productRelationDAO.updateByPrimaryKeySelective(productRelation);
            for (int i = 0; i < skuIds.size(); i++) {
                newRelationDetail.setProductCount(proCount.get(i));
                //newRelationDetail.setRelationDetailId(skuIds.get(i));
                newRelationDetail.setRelationSkuId(skuIds.get(i));
                newRelationDetail.setRelationSkuPrice(prices.get(i));
                newRelationDetail.setRelationId(productRelation.getRelationId());
                productRelationDetailDao.insertSelective(newRelationDetail);
            }
            count = 1;
        } catch (Exception e) {
            logger.error("根据套餐id修改套餐信息出现异常", e);
            throw new ServiceException(e);
        }
        return count;
    }
    
    @Override
    public List<Integer> selectProductIdByRelationId(List<Long> relationId) throws ServiceException {
        try {
            return productRelationDAO.selectProductIdByRelationId(relationId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void getAllZFProductRelation(ProductRelation productRelation, Page page) throws ServiceException {
        ProductRelationExample example = new ProductRelationExample();
        Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(productRelation.getRelationName())) {//组方关联名称
            criteria.andRelationNameLike(new StringBuffer("%").append(productRelation.getRelationName().trim()).append("%").toString());
        }
        if (StringUtils.isNotBlank(productRelation.getProductNo())) {//组方主产品编号
            criteria.andProductNoLike(new StringBuffer("%").append(productRelation.getProductNo().trim()).append("%").toString());
        }
        if (StringUtils.isNotBlank(productRelation.getProductSkuCode())) {//组方主产品sku
            criteria.andProductSkuCodeLike(new StringBuffer("%").append(productRelation.getProductSkuCode().trim()).append("%").toString());
        }
        //组方状态
        if (productRelation.getStatus() != null) {
            criteria.andZFStatusEqualTo(productRelation.getStatus());
        }
        Short type = 4;//组方类型
        criteria.andRelationTypeEqualTo(type);
        example.setOrderByClause("CREATE_DATE DESC");

        try {
            List<ProductRelation> relations = productRelationDAO.selectByExampleZF(example, page);
            int count = productRelationDAO.countByExampleZF(example);
            page.setDataList(relations);
            page.setRecordCount(count);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void saveProductRelationAndDetail(ProductRelation productRelation, List<ProductRelationDetail> list)
            throws ServiceException {
        try {
            Long productRelationId = productRelationDAO.insertProductRelation(productRelation);
            if (list != null) {
                for (ProductRelationDetail detail : list) {
                    // 将主单的主键id放入到子单中
                    detail.setRelationId(productRelationId);
                }
                productRelationDetailService.saveProductRelationDetail(list);
            }
        } catch (SQLException e) {
            logger.error("定制管理-产品关联-产品关联或组方列表-保存新添加信息异常", e);
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void savePRDetailAndUpdatePR(ProductRelation productRelation, List<ProductRelationDetail> list)
            throws ServiceException {
        try {
            productRelationDetailService.saveProductRelationDetail(list);
            updateProductRelation(productRelation);
        } catch (Exception e) {
            logger.error("定制管理-产品关联-产品关联列表-编辑后保存添加信息异常", e);
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void saveTCPRDetailAndUpdatePR(ProductRelation productRelation, List<ProductRelationDetail> list, 
                                          List<BigDecimal> prices, List<Long> skuIds, List<Short> proCount)
            throws ServiceException {
        try {
            ProductRelationDetail newRelation = null;
            Long productRelationId = saveProductRelation(productRelation);
            for (int i = 0; i < prices.size(); i++) {
                if (prices.get(i) != null) {
                    newRelation = new ProductRelationDetail();
                    newRelation.setRelationSkuPrice(prices.get(i));// 商品单价
                    newRelation.setRelationSkuId(skuIds.get(i));// 商品skuid
                    newRelation.setProductCount(proCount.get(i));// 商品数量
                    newRelation.setRelationDetailType(Short.parseShort(BigDecimal.ZERO.toString()));
                    newRelation.setRelationId(productRelationId);
                    list.add(newRelation);
                }
            }
            productRelationDetailService.saveProductRelationDetail(list);
        } catch (Exception e) {
            logger.error("定制管理-套餐管理-新增套餐异常", e);
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void updateProductRelationSerAndDetail(ProductRelation productRelation, List<Long> relationDetailIdList, 
                                                  List<Short> proCount, List<Long> skuIds, List<BigDecimal> prices, 
                                                  List<Long> skuIdsAdd, List<BigDecimal> pricesAdd, 
                                                  List<Short> proCountAdd) throws ServiceException {
        try {
            //修改套餐信息（不含套餐产品信息）
            updateProductRelationSer(productRelation);
            ProductRelationDetail newRelationDetail = new ProductRelationDetail();
            for (int i = 0; i < relationDetailIdList.size(); i++) {
                newRelationDetail.setProductCount(proCount.get(i));
                newRelationDetail.setRelationDetailId(relationDetailIdList.get(i));
                newRelationDetail.setRelationSkuId(skuIds.get(i));
                newRelationDetail.setRelationSkuPrice(prices.get(i));
                addStatusUpdateProductRelation(newRelationDetail);
            }
            if (skuIdsAdd != null) {
                addStatusInsertProductRelation(skuIdsAdd, pricesAdd, proCountAdd, productRelation);
            }
        } catch (Exception e) {
            logger.error("定制管理-套餐管理-修改保存套餐信息异常", e);
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void updateZFProductRelationAndDetail(String oldRIdCorrelationSIds, ProductRelation productRelation,
                                                 List<ProductRelationDetail> list) throws ServiceException {
        try {
            if (oldRIdCorrelationSIds != null && !oldRIdCorrelationSIds.equals("")) {
                Long[] sIds = PurchaseUtils.strArrToIntArr(oldRIdCorrelationSIds);// 套餐id
                List<Long> delPackageList = new ArrayList<Long>();
                for (Long id : sIds) {
                    delPackageList.add(id);
                }
                if (delPackageList.size() > 0) {
                    // 删除组合子产品信息
                    productRelationDetailService.batchDelProductRelationDetailByRelationDetailId(delPackageList);
                }
            }
            updateProductRelation(productRelation);
            // 获取到
            if (list != null) {
                for (ProductRelationDetail detail : list) {
                    // 将主单的主键id放入到子单中
                    detail.setRelationId(productRelation.getRelationId());
                }
                productRelationDetailService.saveProductRelationDetail(list);
            }
        } catch (Exception e) {
            logger.error("定制管理-组方列表-修改保存组方信息异常", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ProductRelation> getRecommendListBySkuId(Long skuId) throws ServiceException {
        try {
            return productRelationDAO.getRecommendListBySkuId(skuId);
        } catch (SQLException e) {
            logger.error("根据skuId[" + skuId + "]获取人气组合失败", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ProductRelation> getPackageListBySkuId(Long skuId) throws ServiceException {
        try {
            return productRelationDAO.getPackageListBySkuId(skuId);
        } catch (SQLException e) {
            logger.error("根据skuId[" + skuId + "]获取优惠套餐组合失败", e);
            throw new ServiceException(e);
        }
    }

}