package com.kmzyc.promotion.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.promotion.app.dao.CouponGrantSetDAO;
import com.kmzyc.promotion.app.vobject.Coupon;
import com.kmzyc.promotion.app.vobject.CouponGrant;
import com.kmzyc.promotion.app.vobject.CouponGrantInfo;
import com.kmzyc.promotion.app.vobject.CouponGrantSetOBJ;
import com.kmzyc.promotion.app.vobject.CouponGrantSeting;
import com.kmzyc.promotion.app.vobject.CouponGrantVO;
import com.kmzyc.promotion.app.vobject.Product;
import com.kmzyc.promotion.app.vobject.UserLeve;
import com.kmzyc.promotion.app.vobject.UserLoginId;
import com.pltfm.app.dataobject.UserInfoDO;

@SuppressWarnings("unchecked")
@Repository("couponGrantSetdao")
public class CouponGrantSetDAOImpl implements CouponGrantSetDAO {

  public CouponGrantSetDAOImpl() {}



  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  public CouponGrantSetDAOImpl(SqlMapClient sqlMapClient) {
    super();
    this.sqlMapClient = sqlMapClient;
  }

  @Override
  public void saveCouponGrantSet(CouponGrantSetOBJ cgso) throws SQLException {
    sqlMapClient.insert("COUPON_GRANT_SETING.saveCouponGrantset", cgso);
  }

  @Override
  public List<CouponGrantSeting> selectCouponGrantVOList(CouponGrantSeting cgs) throws SQLException {
    List<CouponGrantSeting> list =
        sqlMapClient.queryForList("COUPON_GRANT_SETING.selectPageCouponGrantSet", cgs);
    return list;
  }

  @Override
  public Integer selectCount(CouponGrantSeting cgs) throws SQLException {
    return (Integer) sqlMapClient.queryForObject("COUPON_GRANT_SETING.selectCount", cgs);
  }

  @Override
  public Integer updateCouponGrant(CouponGrantSeting cgs) throws SQLException {
    return (Integer) sqlMapClient.update("COUPON_GRANT_SETING.updateCouponGrantset", cgs);
  }

  public Boolean batchUpdateCouponGrant(List<CouponGrantSeting> cgsList) throws SQLException {
    if (cgsList != null && cgsList.size() > 0) {
      sqlMapClient.startBatch();
      for (int i = 0; i < cgsList.size(); i++) {
        sqlMapClient.update("COUPON_GRANT_SETING.updateCouponGrantset", cgsList.get(i));
      }
      sqlMapClient.executeBatch();
    }

    return true;
  }

  @Override
  public List<CouponGrantSeting> selectByCouponGrant(CouponGrantSeting cgs) throws SQLException {
    return (List<CouponGrantSeting>) sqlMapClient.queryForList(
        "COUPON_GRANT_SETING.selectCouponGrantset", cgs);
  }

  @Override
  public List<CouponGrantSeting> queryGrantSetForRegist() throws SQLException {
    return (List<CouponGrantSeting>) sqlMapClient
        .queryForList("COUPON_GRANT_SETING.queryGrantSetForRegist");
  }

  @Override
  public Integer deleteCouponGrant(CouponGrantSeting cgs) throws SQLException {
    return sqlMapClient.delete("COUPON_GRANT_SETING.deleteCouponGrantset", cgs);
  }

  @Override
  public Long saveGrantCoupon(CouponGrantSeting cgs) throws SQLException {
    return (Long) sqlMapClient.insert("COUPON_GRANT_SETING.saveCouponGrantset", cgs);
  }

  @Override
  public Long inserCouponGrantOBJ(CouponGrant cg) throws SQLException {
    return (Long) sqlMapClient.insert("COUPON_GRANT_SETING.ibatorgenerated_insertSelective", cg);

  }

  @Override
  public List<UserLoginId> selectLoginIdByLeveId(Integer leveid) throws SQLException {
    return sqlMapClient.queryForList("COUPON_GRANT_SETING.selectLoginId", leveid);
  }

  @Override
  public List<UserLoginId> selectUserByLoginId(Integer loginId) throws SQLException {
    return sqlMapClient.queryForList("COUPON_GRANT_SETING.selectUserByLoginId", loginId);
  }

  @Override
  public List<UserLeve> selectUserLeveByLeveId(Integer leveId) throws SQLException {
    return sqlMapClient.queryForList("COUPON_GRANT_SETING.selectUserLevelByLevelId", leveId);
  }

  /**
   * 分页
   */
  @Override
  public List<CouponGrantVO> selectCouponGrant(CouponGrantVO cgv) throws SQLException {
    return sqlMapClient.queryForList("COUPON_GRANT.selectCouponGrantOBJ", cgv);
  }

  @Override
  public Integer selectCouponGrantCount(CouponGrantVO cgv) throws SQLException {
    return (Integer) sqlMapClient.queryForObject("COUPON_GRANT.selectCouponGrantCount", cgv);
  }

  @Override
  public List<CouponGrantVO> selectCouponGrantALL(CouponGrantVO cgv) throws SQLException {
    return sqlMapClient.queryForList("COUPON_GRANT.selectCouponGrantByCGVO", cgv);
  }

  @Override
  public Long saveVCode(CouponGrantInfo cgi) throws SQLException {
    return (Long) sqlMapClient.insert("COUPON_GRANT_SETING.grantInfo_insertSelective", cgi);
  }

  @Override
  public Boolean bathSaveCouponGrantInfo(List<CouponGrantInfo> cgiList) throws SQLException {
    if (cgiList != null && cgiList.size() > 0) {
      sqlMapClient.startBatch();
      for (int i = 0; i < cgiList.size(); i++) {
        sqlMapClient.insert("COUPON_GRANT_SETING.grantInfo_insertSelective", cgiList.get(i));
      }
      sqlMapClient.executeBatch();
    }

    return true;
  }

  @Override
  public Integer selectCountCoupon(Coupon coupon) throws SQLException {
    return (Integer) sqlMapClient.queryForObject("COUPON.selectCountByStatus", coupon);
  }

  @Override
  public List<Coupon> selectCouponList(Coupon coupon) throws SQLException {
    return sqlMapClient.queryForList("COUPON.ibatorgenerated_selectByStatus", coupon);
  }

  @Override
  public List<CouponGrantSeting> qureyBystarAndEndTime(CouponGrantSeting cgs) throws SQLException {
    return sqlMapClient.queryForList("COUPON_GRANT_SETING.selectBystarAndEndTime", cgs);
  }

  @Override
  public Integer updateGrantSeting(CouponGrantSeting cgs) throws SQLException {
    return sqlMapClient.update("COUPON_GRANT_SETING.updateGrantSeting", cgs);
  }

  @Override
  public Product selectProductBySKU(String skuCode) throws SQLException {
    return (Product) sqlMapClient.queryForObject("COUPON_GRANT_SETING.selectProductBySKU", skuCode);
  }

  @Override
  public List<Product> selectProductAndSuppliersBySKU(List<String> skuCodes) throws SQLException {
    return (List<Product>) sqlMapClient.queryForList(
        "COUPON_GRANT_SETING.selectProductAndSuppliersBySKU", skuCodes);
  }

  @Override
  public List<UserInfoDO> selectUserById(UserInfoDO userCondition) throws SQLException {
    return (List<UserInfoDO>) sqlMapClient.queryForList("COUPON_GRANT_SETING.selectUserbyId",
        userCondition);
  }

}
