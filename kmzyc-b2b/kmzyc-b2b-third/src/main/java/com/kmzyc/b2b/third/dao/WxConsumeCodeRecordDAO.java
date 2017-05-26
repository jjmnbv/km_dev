package com.kmzyc.b2b.third.dao;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.b2b.third.model.wxCard.QueryPara;
import com.kmzyc.b2b.third.model.wxCard.WxConsumeCodeRecord;
import com.kmzyc.b2b.third.model.wxCard.WxKmrsShopInfo;
import com.kmzyc.b2b.third.model.wxCard.WxReservation;

/**
 * 微信卡券核销dao请求处理
 * 
 * @author Administrator
 * 
 */
public interface WxConsumeCodeRecordDAO {
  /**
   * 插入核销记录
   * 
   * @param record
   * @throws SQLException
   */
  void insert(WxConsumeCodeRecord record) throws SQLException;

  void insertSelective(WxConsumeCodeRecord record) throws SQLException;

  /**
   * 查询激活码供卡券导入
   * 
   * @param couponId
   * @param min
   * @param max
   * @return
   * @throws SQLException
   */
  List<QueryPara> queryActiveCodeForImport(String couponId, int min, int max, String tableName)
      throws SQLException;

  /**
   * 查询店铺验证码
   * 
   * @param verifyCode
   * @return
   * @throws SQLException
   */
  Integer queryShopVerifyCode(String verifyCode) throws SQLException;

  /**
   * 按照cardId和code查询该code是否已经被核销
   * 
   * @param cardId
   * @param code
   * @return
   * @throws SQLException
   */
  Integer queryConsumeByCardIdAndCode(String cardId, String code) throws SQLException;

  /*************** 预约记录开始 只限礼品券 begin *********************/
  /**
   * 新增预约记录
   */
  public void insertReservation(WxReservation para) throws SQLException;

  /**
   * 按code和卡券ID查询预约记录
   * 
   * @param para
   * @return
   * @throws SQLException
   */
  public WxReservation queryReservationByCodeAndId(WxReservation para) throws SQLException;

  /**
   * 查询礼品券指定可预约的所有门店信息
   * 
   * @return
   * @throws SQLException
   */
  public List<WxKmrsShopInfo> queryAllKmrsShopInfo() throws SQLException;

  /**************** 预约记录结束end ********************/

}
