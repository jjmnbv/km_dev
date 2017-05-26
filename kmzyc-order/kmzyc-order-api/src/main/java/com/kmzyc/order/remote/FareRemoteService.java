package com.kmzyc.order.remote;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.entities.FareType;
import com.pltfm.app.vobject.FareTypeVo;

/**
 * 运费接口
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings("unchecked")
public interface FareRemoteService extends Serializable {

  /**
   * 获得运费配置
   */
  public FareType getFareType(Long id) throws ServiceException;

  /**
   * 获得所有运费配置
   */
  public List getAllFare() throws ServiceException;

  /**
   * 计算运费
   * 
   * @param typeId 运费配置序号
   * @param sum 订单总金额
   * @param weight 订单总重量
   * @return
   * @throws ServiceException
   */
  public BigDecimal getFare(FareTypeVo fareTypeVo) throws ServiceException;

  /**
   * 修改订单运费接口
   */
  public Boolean changeLogisticsFee(String orderCode, String account, BigDecimal newFare)
      throws ServiceException;

}
