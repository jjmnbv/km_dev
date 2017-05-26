package com.kmzyc.b2b.dao;

import java.sql.SQLException;
import java.util.List;

import com.km.framework.persistence.Dao;
import com.kmzyc.b2b.model.WxScanProduct;

/**
 * wx扫产品码事件推送记录表 ,主要是查询以及插入,无更新和删除操作
 * 
 * @author KM
 * 
 */
public interface WxScanProductDao extends Dao {

  /**
   * 插入记录
   * 
   * @param record
   */
  Object insert(WxScanProduct record) throws SQLException;


  /**
   * 根据openID获取扫码记录
   * 
   * @param mapCondition
   * @return
   * @throws SQLException
   */
  public List<Long> queryWXScanProductSku(String openId) throws SQLException;
}
