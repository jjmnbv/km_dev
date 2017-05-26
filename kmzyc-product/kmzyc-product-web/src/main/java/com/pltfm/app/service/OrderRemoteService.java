package com.pltfm.app.service;

import java.util.List;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.vobject.LogisticAndDistributionInfoVO;
import com.pltfm.app.vobject.StockOut;

public interface OrderRemoteService {

    /**
     * 调用订单系统的接口
     *
     * @param logisticAndDistributionInfoVO     物流单号
     * @param stockOutType                      订单号
     * @return
     * @throws Exception
     */
    void getLogisticNumber(LogisticAndDistributionInfoVO logisticAndDistributionInfoVO, Short stockOutType)
            throws ServiceException;

    /**
     * 订单转退货接口
     *
     * @param stockOutList
     */
    void changeAlterStatusForProduct(List<StockOut> stockOutList) throws ServiceException;

}