package com.pltfm.app.remote.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.order.remote.OrderRiskRemoteService;
import com.pltfm.app.service.OrderRiskRosterService;
import com.pltfm.sys.util.ErrorCode;

@Service("orderRiskRemoteService")
@SuppressWarnings("unchecked")
public class OrderRiskRemoteServiceImpl implements  OrderRiskRemoteService {
  /**
    * 
    */
    private static final long serialVersionUID = 7768885819082068399L;

    @Resource
    private OrderRiskRosterService orderRiskRosterService;

    
    /**
     * 根据登录id查询风控黑名单
     * 
     * @param   loginId
     * @return  boolean
     * @throws ServiceException
     */
    @Override
    public boolean queryOrderRisk(long loginId) throws ServiceException {
        
        boolean result =false;
        try{
             result = orderRiskRosterService.queryBlackList(loginId);
        }catch(Exception e){
            throw new ServiceException(ErrorCode.INNER_ORDER_QUERY_ERROR, "根据登录id查询风控黑名单接口异常：" + e.getMessage());
        }
        return result;
    }



}
