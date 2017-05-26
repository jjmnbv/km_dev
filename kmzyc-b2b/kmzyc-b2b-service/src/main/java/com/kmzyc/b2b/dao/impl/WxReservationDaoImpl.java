package com.kmzyc.b2b.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.kmzyc.b2b.dao.WxReservationDao;
import com.kmzyc.b2b.model.RefData;
import com.kmzyc.b2b.vo.SellerInfo;
import com.kmzyc.b2b.vo.WxKmrsShopInfo;
import com.kmzyc.b2b.vo.WxReservation;
import com.km.framework.persistence.impl.DaoImpl;

@Repository
@SuppressWarnings("unchecked")
public class WxReservationDaoImpl extends DaoImpl implements WxReservationDao {
  @javax.annotation.Resource(name = "sqlMapClient")
  private com.ibatis.sqlmap.client.SqlMapClient sqlMapClient;

  public List<SellerInfo> selectWxReservationList(List<Object> merchantIds) throws SQLException {
    return (List<SellerInfo>) sqlMapClient.queryForList(
        "AccountInfo.SQL_QUERY_SELLERINFO_BY_MERCHANTID", merchantIds);
  }

  /**
   * 查询店铺信息
   * 
   * @throws SQLException
   */
  public List<WxKmrsShopInfo> findAllShopInfo() throws SQLException {
    return (List<WxKmrsShopInfo>) sqlMapClient.queryForList("WX_RESERVATION.selectStore");
  }

  public List<WxReservation> findAllWxCoupon(Map map) throws SQLException {
    return (List<WxReservation>) sqlMapClient.queryForList("WX_RESERVATION.selectWxCouponExp", map);
  }

  /**
   * 移动话费充值查询
   * 
   * @param map
   * @return
   * @throws SQLException
   */

  public List<RefData> findRefList(Map map) throws SQLException {
    return (List<RefData>) sqlMapClient.queryForList("RefData.selectRefDataByCondition", map);
  }

}
