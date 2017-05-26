package com.pltfm.app.service;

import java.sql.SQLException;
import java.util.List;

import com.pltfm.app.vobject.DistributionDetailInfo;
import com.pltfm.app.vobject.DistributionInfo;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;

/**
 * 接口：配送明细单Service
 *
 * @author luoyi
 * @createDate 2013/08/15
 */
public interface DistributionDetailInfoService {

    /**
     * 分页查询配送明细单信息
     *
     * @param page       分页
     * @param detailInfo 配送明细单
     * @return throws SQLException
     */
    List<DistributionDetailInfo> findDistributionDetailInfoList(Page page, DistributionDetailInfo detailInfo)
            throws ServiceException;

    /**
     * 查询配送明细单信息
     *
     * @param distributionId 配送明ID
     * @return throws SQLException
     */
    List<DistributionDetailInfo> findDistributionDetailInfoList(Long distributionId) throws ServiceException;

    /**
     * 保存配送细目单集合
     *
     * @param detailList
     * @throws SQLException
     */
    void saveDistributionDetailList(List<DistributionDetailInfo> detailList, DistributionInfo distributionInfo)
            throws ServiceException;

    /**
     * 修改配送细目单集合
     *
     * @param detailList
     * @throws Exception
     */
    void editDistributionDetailList(List<DistributionDetailInfo> detailList, DistributionInfo distributionInfo)
            throws ServiceException;

}
