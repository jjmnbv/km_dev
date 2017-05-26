package com.kmzyc.product.remote.service;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.vobject.AftersaleReturnOrder;
import com.pltfm.app.vobject.LogisticAndDistributionInfoVO;

public interface AftersaleReturnOrderRemoteService {

    /**
     * 处理退货换货
     *
     * @param record
     * @param userId
     * @param userName
     * @param changeReturnOrder
     * @return
     */
    int updateObject(AftersaleReturnOrder record, Integer userId, String userName, boolean changeReturnOrder)
            throws ServiceException;

    /**
     * 配送
     *
     * @param logisticAndDistributionInfoVO
     * @throws ServiceException
     */
    int setLogisticAndDistributionInfo(LogisticAndDistributionInfoVO logisticAndDistributionInfoVO)
            throws ServiceException;
}
