package com.kmzyc.order.remote;

import java.io.Serializable;

import com.kmzyc.commons.exception.ServiceException;

/**
 * 订单风控查询接口
 * 
 * @author 
 * @date 
 */
@SuppressWarnings("unchecked")
public interface OrderRiskRemoteService extends Serializable{
    
    
    /**
     *  查询是否在风控黑名单
     * 
     * @param long
     * @return boolean
     * @throws ServiceException
     */
    public boolean queryOrderRisk(long loginId) throws ServiceException;

}
