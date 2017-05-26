package com.kmzyc.b2b.third.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.b2b.third.model.wxCard.WxConsumeCodeRecord;
import com.kmzyc.b2b.third.model.wxCard.WxKmrsShopInfo;
import com.kmzyc.b2b.third.model.wxCard.WxReservation;

/**
 * 微信卡券业务处理类
 * 
 * @author Administrator
 * 
 */
public interface WxCardManageService {

  /**
   * 新增核销记录
   * 
   * @param para
   * @throws SQLException
   */
  public void saveRecord(WxConsumeCodeRecord para) throws SQLException;

  /**
   * 查询店铺验证码
   * 
   * @param verifyCode
   * @return
   * @throws SQLException
   */
  public boolean queryShopVerifyCode(String verifyCode) throws SQLException;

  /**
   * 导入code 此方法已用完,请勿擅自启动(仅用于春节微信卡券摇一摇导入code)
   * 
   * @param cardId
   * @param couponId
   * @param num
   *@param tableName
   * @return
   * @throws SQLException
   */
  public Map<String, Object> importCode(String cardId, String couponId, int num, String tableName)
      throws SQLException;

  /**
   * 保存客户预约信息
   * 
   * @param para
   * @return
   */
  public void saveReservation(WxReservation para) throws SQLException;

  /**
   * 根据code和card查预约记录
   * 
   * @param cardId
   * @param code
   * @return
   * @throws SQLException
   */
  public WxReservation queryReservationByCardAndCode(String cardId, String code)
      throws SQLException;

  /**
   * 查询所有的康美人生门店信息
   * 
   * @return
   * @throws SQLException
   */
  public List<WxKmrsShopInfo> queryAllKmrsShopInfo() throws SQLException;

  /**
   * 查询该card是否已经被核销
   * 
   * @param cardId
   * @param code
   * @return
   * @throws SQLException
   */
  public Integer queryConsumeByCardAndCode(String cardId, String code) throws SQLException;
}
