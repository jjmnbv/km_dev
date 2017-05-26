package com.pltfm.app.service;

import java.util.Date;
import java.util.List;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.vobject.DistributionDetailInfo;
import com.pltfm.app.vobject.DistributionInfo;
import com.kmzyc.commons.page.Page;

/**
 * 接口：配送单Service
 *
 * @author luoyi
 * @createDate 2013/08/20
 */
public interface DistributionInfoService {
    /**
     * 分页查询配送单信息
     *
     * @param page              分页
     * @param distributionInfo  配送单
     * @param endDate           结束日期
     * @return throws ServiceException
     */
    List<DistributionInfo> findDistributionInfoList(Page page, DistributionInfo distributionInfo, Date endDate)
            throws ServiceException;

    /**
     * 根据配送单ID，查找配送单信息
     *
     * @param distributionId
     * @return
     */
    DistributionInfo findDistributionById(Long distributionId) throws ServiceException;

    /**
     * 根据配送明细单，汇总配送单信息
     *
     * @param distributionInfo
     * @param detailList
     * @return
     */
    DistributionInfo updateDistributionByDetail(DistributionInfo distributionInfo, List<DistributionDetailInfo> detailList)
            throws ServiceException;

    /**
     * 根据配送单号，查找配送单信息
     *
     * @param DistributionNo
     * @return
     */
    DistributionInfo findDistributionByNo(String DistributionNo) throws ServiceException;

    /**
     * 根据订单号，查找配送单信息
     *
     * @param orderNo
     * @return
     */
    DistributionInfo findDistributionByOrderNo(String orderNo) throws ServiceException;


    /**
     * 批量删除配送单
     *
     * @param distributionList
     * @return
     * @throws ServiceException
     */
    ResultMessage deleteDistributionInfoList(List<DistributionInfo> distributionList) throws ServiceException;

    /**
     * 批量审核配送单
     *
     * @param distributionList
     * @return
     * @throws ServiceException
     */
    ResultMessage updateDistributionInfoList(List<DistributionInfo> distributionList) throws ServiceException;

    /**
     * 修改配送主单信息
     *
     * @param distributionInfo
     * @throws ServiceException
     */
    void updateDistributionInfo(DistributionInfo distributionInfo) throws ServiceException;
}
