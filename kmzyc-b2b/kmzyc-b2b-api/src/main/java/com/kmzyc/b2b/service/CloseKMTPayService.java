package com.kmzyc.b2b.service;

import com.kmzyc.commons.exception.ServiceException;

/**
 *
 * @author KM
 * @date 2016年7月5日 上午11:19:48
 * @version
 * @see
 */
public interface CloseKMTPayService {
    /**
     * 
     *
     * @author KM
     * @param tradeNo
     * @param outTradeNo
     * @return
     * @throws ServiceException
     */
    public boolean closeKMTPay(String tradeNo, String outTradeNo);
}
