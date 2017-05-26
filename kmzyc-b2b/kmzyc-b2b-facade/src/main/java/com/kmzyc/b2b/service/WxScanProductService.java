package com.kmzyc.b2b.service;

import java.util.List;

import com.kmzyc.b2b.model.WxScanProduct;
import com.kmzyc.commons.exception.ServiceException;

/**
 * 微信组方扫码记录service 20150921 mlq add
 * 
 * @author KM
 * 
 */
public interface WxScanProductService {


  /**
   * 保存扫码记录 ,其中openid和被扫码skuId和条形码内容一定不能为空,如果数据获取为空,则略过此记录 20150921 mlq add
   * 
   * @param para
   * @return
   * @throws ServiceException
   */
  public boolean saveWxScanProductRecord(WxScanProduct para) throws ServiceException;


  /**
   * 根据openID获取扫码记录
   * 
   * @param mapCondition
   * @return
   * @throws ServiceException
   */
  public List<Long> queryWXScanProductSku(String openId) throws ServiceException;
}
