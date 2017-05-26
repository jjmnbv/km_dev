package com.pltfm.app.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.dao.DistributionDetailInfoDAO;
import com.pltfm.app.dao.DistributionInfoDAO;
import com.pltfm.app.service.DistributionInfoService;
import com.pltfm.app.vobject.DistributionDetailInfo;
import com.pltfm.app.vobject.DistributionInfo;
import com.pltfm.app.vobject.DistributionInfoExample;
import com.pltfm.app.vobject.DistributionInfoExample.Criteria;
import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.sys.util.DatetimeUtil;
import com.kmzyc.commons.page.Page;

/**
 * 配送单Service实现类
 *
 * @author luoyi
 * @since 2013/08/20
 */
@Service("distributionInfoService")
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class DistributionInfoServiceImpl implements DistributionInfoService {

    @Resource
    private DistributionInfoDAO distributionInfoDao;

    @Resource
    private DistributionDetailInfoDAO distributionDetailInfoDao;

    @Override
    public List<DistributionInfo> findDistributionInfoList(Page page, DistributionInfo distributionInfo, Date endDate)
            throws ServiceException {
        int pageIndex = page.getPageNo();
        if (pageIndex == 0)
            pageIndex = 1;
        int pageSize = page.getPageSize();
        int skip = (pageIndex - 1) * pageSize;
        // 每页记录数
        int max = pageSize;

        DistributionInfoExample example = new DistributionInfoExample();
        Criteria criteria = example.createCriteria();
        // 是否送达不为空
        if (StringUtils.isNotBlank(distributionInfo.getIsDeliveries())) {
            criteria.andIsDeliveriesEqualTo(distributionInfo.getIsDeliveries());
        }
        // 仓库不为空
        if (null != distributionInfo.getWarehouseId()) {
            criteria.andWarehouseIdEqualTo(distributionInfo.getWarehouseId());
        }
        // 查询日期不为空
        if (null != distributionInfo.getLogisticsDate()) {
            if (null == endDate) {
                criteria.andLogisticsDateGreaterThanOrEqualTo(distributionInfo.getLogisticsDate());
            } else {
                // 结束日期要加上1天
                criteria.andLogisticsDateGreaterThanOrEqualTo(distributionInfo.getLogisticsDate());
                criteria.andLogisticsDateLessThan(DatetimeUtil.getDateByDay(endDate, 1));
            }
        } else if (null != endDate) {// 开始日期为空,结束日期不为空
            criteria.andLogisticsDateLessThanOrEqualTo(endDate);
        }
        // 配送单号不为空
        if (StringUtils.isNotBlank(distributionInfo.getDistributionNo())) {
            criteria.andDistributionNoLike(distributionInfo.getDistributionNo());
        }
        // 订单号不为空
        if (StringUtils.isNotBlank(distributionInfo.getOrderNo())) {
            criteria.andOrderNoLike(distributionInfo.getOrderNo());
        }
        // 收货人
        if (StringUtils.isNotBlank(distributionInfo.getUserName())) {
            criteria.andUserNameLike(distributionInfo.getUserName());
        }
        // 设置按出库单ID排序
        example.setOrderByClause(" DISTRIBUTION_ID DESC");

        try {
            int count = distributionInfoDao.countByExample(example);
            List<DistributionInfo> distributionInfoList = distributionInfoDao.selectByExample(example, skip, max);
            page.setDataList(distributionInfoList);// 设置数据
            page.setRecordCount(count);// 设置总记录数
            return distributionInfoList;
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public DistributionInfo findDistributionById(Long distributionId) throws ServiceException {
        try {
            return distributionInfoDao.selectByPrimaryKey(distributionId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public DistributionInfo updateDistributionByDetail(DistributionInfo distributionInfo, List<DistributionDetailInfo> detailList)
            throws ServiceException {
        BigDecimal totalSum = new BigDecimal(0);
        int totalQuantity = 0;// 总数量
        for (DistributionDetailInfo detailInfo : detailList) {
            totalSum = totalSum.add(detailInfo.getSum());// 总金额
            totalQuantity += detailInfo.getQuantity();// 合计数量
        }

        distributionInfo.setTotalSum(totalSum);// 设置总金额
        distributionInfo.setTotalQuantity(totalQuantity);// 设置总金额
        return distributionInfo;
    }

    @Override
    public DistributionInfo findDistributionByNo(String DistributionNo) throws ServiceException {
        DistributionInfoExample example = new DistributionInfoExample();
        Criteria criteria = example.createCriteria();
        criteria.andDistributionNoEqualTo(DistributionNo);

        try {
            List<DistributionInfo> distributionList = distributionInfoDao.selectByExample(example);// 此集合肯定只有一个记录，如果有两条，说明单号重复，数据有问题
            return distributionList == null ? null : distributionList.get(0);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public ResultMessage deleteDistributionInfoList(List<DistributionInfo> distributionList) throws ServiceException {
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setIsSuccess(false);
        resultMessage.setMessage("批量删除配送单失败!");

        try {
            int count = distributionDetailInfoDao.batchDeleteDistributionInfo(
                    "DISTRIBUTION_DETAIL_INFO.batchDeleteDistributionDetail", distributionList);// 配送明细单删除
            if (count > 0) {
                count = distributionInfoDao.batchDeleteForDistribution(
                        "DISTRIBUTION_INFO.batchDeleteDistributionInfo", distributionList);// 配送单批量删除
                if (count <= 0) {
                    throw new ServiceException("配送单数据有误,批量删除配送单失败!");
                }
            } else {
                throw new ServiceException("配送单数据有误,批量删除配送单失败!");
            }
        } catch (Exception e) {
            throw new ServiceException("配送单数据有误,批量删除配送单失败!");
        }

        resultMessage.setIsSuccess(true);
        resultMessage.setMessage("批量删除配送单成功!");
        return resultMessage;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public ResultMessage updateDistributionInfoList(List<DistributionInfo> distributionList) throws ServiceException {
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setIsSuccess(true);
        resultMessage.setMessage("批量审核配送单成功!");
        try {

            int count = distributionInfoDao.batchUpdateForDistributionInfo(
                    "DISTRIBUTION_INFO.batchUpdateDistributionInfo", distributionList);// 配送明细单删除
            if (count <= 0) {
                resultMessage.setIsSuccess(false);
                resultMessage.setMessage("批量审核配送单失败!");
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }

        return resultMessage;
    }

    @Override
    public DistributionInfo findDistributionByOrderNo(String orderNo) throws ServiceException {
        DistributionInfoExample example = new DistributionInfoExample();
        Criteria criteria = example.createCriteria();
        criteria.andOrderNoEqualTo(orderNo);

        try {
            List<DistributionInfo> distributionList = distributionInfoDao.selectByExample(example);
            return distributionList == null ? null : distributionList.get(0);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void updateDistributionInfo(DistributionInfo distributionInfo) throws ServiceException {
        if (distributionInfo == null) {
            return;
        }

        // 执行配送主单修改
        try {
            distributionInfoDao.updateByPrimaryKeySelective(distributionInfo);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

}
