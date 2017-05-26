package com.kmzyc.b2b.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.b2b.model.RefData;
import com.kmzyc.b2b.vo.SellerInfo;
import com.kmzyc.b2b.vo.WxKmrsShopInfo;
import com.kmzyc.b2b.vo.WxReservation;
import com.km.framework.persistence.Dao;

@SuppressWarnings("unchecked")
public interface WxReservationDao extends Dao {

  public List<SellerInfo> selectWxReservationList(List<Object> merchantIds) throws SQLException;

  /**
   * 查询店铺信息
   */
  public List<WxKmrsShopInfo> findAllShopInfo() throws SQLException;

  public List<WxReservation> findAllWxCoupon(Map map) throws SQLException;

  public List<RefData> findRefList(Map map) throws SQLException;

}
