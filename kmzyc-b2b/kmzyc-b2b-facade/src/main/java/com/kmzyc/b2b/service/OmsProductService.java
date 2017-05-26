package com.kmzyc.b2b.service;

import java.util.List;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.vobject.ProductSku;

public interface OmsProductService {

  /**
   * 接收管易推送的物流单号并推送至订单系统
   * 
   * @param id
   * @param code
   * @param name
   * @throws Exception
   */
  public String toOrderLogisticNumber(Map<String, String> params) throws ServiceException;

  /**
   * 20151023 epr系统对接更新库存
   * 
   * @param skuList
   * @return
   * @throws ServiceException
   */
  public String updateStockForErp(Map<String, String> params) throws ServiceException;

  /**
   * 根据erp查询是否有对应的sku信息 20151023 mlq add 管易系统对接
   * 
   * @param erpProCode 对接系统产品编号
   * @param sysCode 系统编号,如:捷科
   * @return
   * @throws Exception
   */
  public List<ProductSku> queryProductSkuByErpProCode(String erpProCode, String sysCode)
      throws ServiceException;


  /**
   * 20151028 oms系统对接 查询单个商品,返回为list是因为如果是传的是捷科对应产品的关系是一对多
   * 
   * @param params
   * @return
   * @throws ServiceException
   */
  public String querySingleProduct(Map<String, String> params) throws ServiceException;


  /**
   * 20151030 oms系统对接 查询列表商品
   * 
   * @param params
   * @return
   * @throws ServiceException
   */
  public String queryProductListForOms(Map<String, String> params) throws ServiceException;


}
