package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pltfm.app.dao.DistributionDetailInfoDAO;
import com.pltfm.app.dao.DistributionInfoDAO;
import com.pltfm.app.service.DistributionDetailInfoService;
import com.pltfm.app.service.DistributionInfoService;
import com.pltfm.app.vobject.DistributionDetailInfo;
import com.pltfm.app.vobject.DistributionDetailInfoExample;
import com.pltfm.app.vobject.DistributionDetailInfoExample.Criteria;
import com.pltfm.app.vobject.DistributionInfo;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;

/**
 * 配送明细单Service实现类
 *
 * @author luoyi
 * @since 2013/08/20
 */
@Service("distributionDetailInfoService")
public class DistributionDetailInfoServiceImpl implements DistributionDetailInfoService {

    @Resource
    private DistributionDetailInfoDAO distributionDetailInfoDao;

    @Resource
    private DistributionInfoDAO distributionInfoDao;

    @Resource
    private DistributionInfoService distributionInfoService;

    public List<DistributionDetailInfo> findDistributionDetailInfoList(Page page, DistributionDetailInfo detailInfo)
            throws ServiceException {
        int pageIndex = page.getPageNo();
        if (pageIndex == 0)
            pageIndex = 1;
        int pageSize = page.getPageSize();
        int skip = (pageIndex - 1) * pageSize;
        // 每页记录数
        int max = pageSize;

        DistributionDetailInfoExample example = new DistributionDetailInfoExample();
        Criteria criteria = example.createCriteria();
        // 主单不为空
        if (null != detailInfo.getDistributionId()) {
            criteria.andDistributionIdEqualTo(detailInfo.getDistributionId());
        }
        // detailId不为空
        if (null != detailInfo.getDetailId()) {
            criteria.andDetailIdEqualTo(detailInfo.getDetailId());
        }
        // 设置按出库单ID排序
        example.setOrderByClause(" Detail_ID ASC");

        try {
            int count = distributionDetailInfoDao.countByExample(example);
            List<DistributionDetailInfo> detailList = distributionDetailInfoDao.selectByExample(example, skip, max);
            page.setDataList(detailList);// 设置数据
            page.setRecordCount(count);// 设置总记录数
            return detailList;
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void saveDistributionDetailList(List<DistributionDetailInfo> detailList, DistributionInfo distributionInfo)
            throws ServiceException {
        try {
            // 先进行主单(配送单)保存
            distributionInfoDao.insertSelective(distributionInfo);

            String distributionNo = distributionInfo.getDistributionNo();
            DistributionInfo temp = distributionInfoService.findDistributionByNo(distributionNo);
            // 根据主单生成的id号，给明细单
            Long distributionId = temp.getDistributionId();
            for (DistributionDetailInfo distributionDetailInfo : detailList) {
                distributionDetailInfo.setDistributionId(distributionId);// 设置外键：配送单ID
                distributionDetailInfo.setBillDetailId(1l);// 设置业务类型
            }

            // 进行从单(配送明细单)保存
            int count = saveDistributionDetails(detailList);
            if (count < 1) {
                throw new ServiceException("保存配送单明细失败!");
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }

    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void editDistributionDetailList(List<DistributionDetailInfo> detailList, DistributionInfo distributionInfo)
            throws ServiceException {
        try {
            // 先进行主单(配送单)保存
            distributionInfoDao.updateByPrimaryKeySelective(distributionInfo);

            // 根据stockInId 查询出所有在数据库中的StockInDetail集合
            DistributionDetailInfoExample example = new DistributionDetailInfoExample();
            Criteria criteria = example.createCriteria();
            criteria.andDistributionIdEqualTo(distributionInfo.getDistributionId());
            List<DistributionDetailInfo> details2 = distributionDetailInfoDao.selectByExample(example);

            //被删除的细目单元素
            List<DistributionDetailInfo> delDetailList = new LinkedList<DistributionDetailInfo>();

            // 修改的数据为原有的detailId数据(直接批量保存)
            List<DistributionDetailInfo> oldDetailList = new LinkedList<DistributionDetailInfo>();

            //新添加的数据(批量添加)
            List<DistributionDetailInfo> newDetailList = new LinkedList<DistributionDetailInfo>();

            //对集合循环分类
            for (int i = 0; i < detailList.size(); i++) {
                //先将DistributionId设置给传进来的参数
                detailList.get(i).setDistributionId(distributionInfo.getDistributionId());
                if (null != detailList.get(i).getDetailId()) {
                    oldDetailList.add(detailList.get(i));//添加到已存在数据中
                } else {
                    newDetailList.add(detailList.get(i));//添加到新数据中
                }
            }

            //旧数据还没有完全删除,需要进行对比
            if (null != oldDetailList && oldDetailList.size() > 0 && oldDetailList.size() != details2.size()) {
                //将需要删除的添加到集合
                for (int i = 0; i < details2.size(); i++) {//外层条件:单据原有的细目单
                    int index = 0;
                    for (int j = 0; j < oldDetailList.size(); j++) {//内层条件:还剩下的细目单
                        index++;
                        if (details2.get(i).getDetailId().equals(oldDetailList.get(j).getDetailId())) {//如果找到相同的ID
                            break;//跳出此次循环
                        }
                        // 到了最后一第记录，还没有找到匹配的，将原先的数据添加到要批量删除的数据中
                        else if (index == oldDetailList.size()) {
                            delDetailList.add(details2.get(i));
                        }
                    }
                }
            }

            int count = 0;//记录数
            //进行细目单批量删除、修改、添加
            if (CollectionUtils.isNotEmpty(delDetailList)) {
                count = distributionDetailInfoDao.batchDeleteDistributionDetail(delDetailList);//批量删除
                if (count == 0) {
                    throw new ServiceException("配送单数据出错!");
                }
            }
            if (CollectionUtils.isNotEmpty(oldDetailList)) {
                count = distributionDetailInfoDao.batchUpdateDistributionDetail(oldDetailList);//批量修改
                if (count == 0) {
                    throw new ServiceException("配送单数据出错!");
                }
            }
            if (CollectionUtils.isNotEmpty(newDetailList)) {
                count = distributionDetailInfoDao.batchSaveForDistributionDetail(newDetailList);//批量添加
                if (count == 0) {
                    throw new ServiceException("配送单数据出错!");
                }
            }
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public int saveDistributionDetails(List<DistributionDetailInfo> dInfoList) throws ServiceException {
        try {
            return distributionDetailInfoDao.batchSaveForDistributionDetail(dInfoList);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public List<DistributionDetailInfo> findDistributionDetailInfoList(Long distributionId) throws ServiceException {
        DistributionDetailInfoExample example = new DistributionDetailInfoExample();
        Criteria criteria = example.createCriteria();
        criteria.andDistributionIdEqualTo(distributionId);// 查询条件
        try {
            return distributionDetailInfoDao.selectByExample(example);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }
}
