package com.kmzyc.promotion.app.dao;

import java.sql.SQLException;
import java.util.List;

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

public interface CouponGrantSetDAO {
  /**
   * 保存优惠券发放设置
   * 
   * @param cgso
   * @return
   * @throws SQLException
   */
  public void saveCouponGrantSet(CouponGrantSetOBJ cgso) throws SQLException;

  /**
   * 查询优惠券发放设置集合(分页)
   * 
   * @return
   * @throws SQLException
   */
  public List<CouponGrantSeting> selectCouponGrantVOList(CouponGrantSeting cgs) throws SQLException;

  /**
   * 查询优惠券发放设置总数
   * 
   * @param cgs
   * @return
   * @throws SQLException
   */
  public Integer selectCount(CouponGrantSeting cgs) throws SQLException;


  /**
   * 20141105 add 查询所有符合注册发放的优惠券发放设置 此处只查询符合注册类型的,所以可不用传参,以后若是要扩展,可增加参数
   * 
   * @return
   * @throws SQLException
   */
  public List<CouponGrantSeting> queryGrantSetForRegist() throws SQLException;


  /**
   * 更新优惠券发放设置表（注册发放状态）
   * 
   * @param cgs
   * @return
   * @throws SQLException
   */
  public Integer updateCouponGrant(CouponGrantSeting cgs) throws SQLException;

  /**
   * 批量更新优惠券发放设置表
   * 
   * @param cgsList
   * @return
   * @throws SQLException
   */
  public Boolean batchUpdateCouponGrant(List<CouponGrantSeting> cgsList) throws SQLException;

  /**
   * 查询集合
   * 
   * @param cgs
   * @return
   * @throws SQLException
   */
  public List<CouponGrantSeting> selectByCouponGrant(CouponGrantSeting cgs) throws SQLException;

  /**
   * 根据开始和结束时间查询集合
   * 
   * @param cgs
   * @return
   * @throws SQLException
   */
  public List<CouponGrantSeting> qureyBystarAndEndTime(CouponGrantSeting cgs) throws SQLException;

  /**
   * 删除（couponID，couponIssuingId）
   * 
   * @param cgs
   * @return
   * @throws SQLException
   */
  public Integer deleteCouponGrant(CouponGrantSeting cgs) throws SQLException;

  /**
   * 保存（返回主键）
   * 
   * @param cgs
   * @return
   * @throws SQLException
   */
  public Long saveGrantCoupon(CouponGrantSeting cgs) throws SQLException;

  /**
   * 生成优惠券实体
   * 
   * @param cg
   * @throws SQLException
   */
  public Long inserCouponGrantOBJ(CouponGrant cg) throws SQLException;

  /**
   * 通过客户等级Id查询出客户id集合
   * 
   * @param leveid
   * @return
   * @throws SQLException
   */
  public List<UserLoginId> selectLoginIdByLeveId(Integer leveid) throws SQLException;

  /**
   * 通过id查询客户
   * 
   * @param loginId
   * @return
   * @throws SQLException
   */
  public List<UserLoginId> selectUserByLoginId(Integer loginId) throws SQLException;

  /**
   * 通过leveId查询客户等级
   * 
   * @param leveId
   * @return
   * @throws SQLException
   */
  public List<UserLeve> selectUserLeveByLeveId(Integer leveId) throws SQLException;

  /**
   * 分页查询出
   * 
   * @param cgv
   * @return
   * @throws SQLException
   */
  public List<CouponGrantVO> selectCouponGrant(CouponGrantVO cgv) throws SQLException;

  /**
   * 查询出总数
   * 
   * @param cgv
   * @return
   * @throws SQLException
   */
  public Integer selectCouponGrantCount(CouponGrantVO cgv) throws SQLException;

  /**
   * 查询全部集合
   * 
   * @param cgv
   * @return
   * @throws SQLException
   */
  public List<CouponGrantVO> selectCouponGrantALL(CouponGrantVO cgv) throws SQLException;

  /**
   * 保存激活码相关信息
   * 
   * @param cgi
   * @return
   * @throws SQLException
   */
  public Long saveVCode(CouponGrantInfo cgi) throws SQLException;

  /**
   * 批量保存
   * 
   * @param cgiList
   * @return
   * @throws SQLException
   */
  public Boolean bathSaveCouponGrantInfo(List<CouponGrantInfo> cgiList) throws SQLException;

  /**
   * 查询可用优惠券总数
   * 
   * @param coupon
   * @return
   * @throws SQLException
   */
  public Integer selectCountCoupon(Coupon coupon) throws SQLException;

  /**
   * 分页查询可用优惠券
   * 
   * @param coupon
   * @return
   * @throws SQLException
   */
  public List<Coupon> selectCouponList(Coupon coupon) throws SQLException;

  /**
   * 更新发放设置
   * 
   * @param cgs
   * @return
   * @throws SQLException
   */
  public Integer updateGrantSeting(CouponGrantSeting cgs) throws SQLException;

  /**
   * 查询
   * 
   * @param sukCode
   * @return
   * @throws SQLException
   */
  public Product selectProductBySKU(String sukCode) throws SQLException;

  /**
   * 批量查询商品信息与供应商信息
   * 
   * @param sukCodes
   * @return
   * @throws SQLException
   */
  public List<Product> selectProductAndSuppliersBySKU(List<String> skuCodes) throws SQLException;

  public List<UserInfoDO> selectUserById(UserInfoDO userCondition) throws SQLException;

}
