package com.pltfm.app.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;
import com.pltfm.app.entities.SellerSettlement;
import com.pltfm.app.entities.SellerSettlementCriteria;
import com.pltfm.app.entities.SellerSettlementExample;
import com.pltfm.app.vobject.Message;

@SuppressWarnings("unchecked")
public interface SellerSettlementService {

  /**
   * 计算结算单数量
   * 
   * @param example
   * @return
   * @throws SQLException
   */
  int countByExample(SellerSettlementExample example) throws ServiceException;

  /**
   * 删除结算单
   * 
   * @param example
   * @return
   * @throws SQLException
   */
  int deleteByExample(SellerSettlementExample example) throws ServiceException;

  /**
   * 插入、新增结算单
   * 
   * @param record
   * @return
   * @throws ServiceException
   */
  Long insert(SellerSettlement record) throws ServiceException;

  /**
   * 查询结算单
   * 
   * @param example
   * @return
   * @throws ServiceException
   */
  List selectByExample(SellerSettlementExample example) throws ServiceException;

  /**
   * 修改结算单
   * 
   * @param record
   * @param example
   * @return
   * @throws ServiceException
   */
  int updateByExample(SellerSettlement record, SellerSettlementExample example)
      throws ServiceException;

  /**
   * 生成结算单: 查询商家sellerId的订单、商家、付款、运费、退款相关信息 生成结算单、妥投商品明细、妥投订单运费明细、退款明细、操作流水
   * 
   * @param sellerId 商家Id
   * @param period 帐期
   * @param operateType 操作类型
   * @param operatorType 操作人类型
   * @param isSysAuto 是否系统自动生成
   * @return
   */
  public Message generateSettlement(String sellerId, String period, short operateType,
      short operatorType, boolean isSysAuto) throws Exception;

  /**
   * 分页查询结算单
   * 
   * @param page
   * @param criteria
   * @return
   * @throws ServiceException
   */
  public Page querySettlementList(Page page, SellerSettlementCriteria criteria)
      throws ServiceException;

  /**
   * 结算单详情
   * 
   * @param criteria
   * @param isPintOperates 是否查询日志
   * @return
   * @throws ServiceException
   */
  public SellerSettlement getSettlementByNo(SellerSettlementCriteria criteria,
      boolean isPintOperates) throws ServiceException;

  /**
   * 统计 结算单
   * 
   * @param criteria
   * @return
   * @throws ServiceException
   */
  public int selectSettlementSize(SellerSettlementCriteria criteria) throws ServiceException;

  /**
   * 结算单列表
   * 
   * @param criteria
   * @return
   * @throws ServiceException
   */
  public List<SellerSettlement> selectSettlementList(SellerSettlementCriteria criteria)
      throws ServiceException;

  /**
   * 修改结算单 运营确认
   * 
   * @param record
   * @param example
   * @return
   * @throws ServiceException
   */
  int updateOperate(SellerSettlement record) throws ServiceException;

  /**
   * 获取需要结算的商户
   * 
   * @param map
   * @return
   * @throws ServiceException
   */
  public List selectSellersNeedSettle(Map map) throws ServiceException;

  /**
   * 系统自动生成未生成相应结算期数据的商家的数据
   * 
   * @param list 需要生成结算的商户ID集合
   * @return
   * @throws ServiceException
   */
  public Message sysAutoGenerateSettlements(Map map) throws ServiceException;

  /**
   * 查询商家名称
   * 
   * @param supplierName
   * @return
   * @throws ServiceException
   */
  public List<SellerSettlement> selectSupplierListByName(String supplierName)
      throws ServiceException;

  /**
   * 查询所有商家名称
   * 
   * @return
   * @throws ServiceException
   */
  public List<SellerSettlement> selectAllSupplier() throws ServiceException;

  /**
   * 判断商户period期结算单是否已结出
   * 
   * @param period
   * @return
   * @throws SQLException
   */
  public boolean thePeriodAlreadyExists(String sellerId, String period) throws ServiceException;

  /**
   * 商户 年度内存在的结算单
   * 
   * @param sellerId
   * @param period
   * @return
   * @throws ServiceException
   */
  public List<String> selectSupplierByLikePeriod(String sellerId, String period)
      throws ServiceException;

  /**
   * 修改结算单 财务确认
   * 
   * @param record
   * @param example
   * @return
   * @throws ServiceException
   */
  int updateFinancialConfirmation(SellerSettlement record) throws ServiceException;

  /**
   * 修改结算单 商家确认
   * 
   * @param record
   * @param example
   * @return
   * @throws ServiceException
   */
  int updateSellerConfirmation(SellerSettlement record) throws ServiceException;

  /**
   * 财务结算是差异调整结算
   * 
   * @param sellerId
   * @param sellerSettlement
   * @return
   * @throws ServiceException
   */
  void diffAdjUpdate(SellerSettlement sellerSettlement, Map map) throws ServiceException;

  /**
   * 通过账期标识获取该账期的前后时间
   * 
   * @param period
   * @return
   */
  Date[] getStartEndDates(String period);

  /**
   * 通过起始日期获取期间对应账期
   * 
   * @param start
   * @param end
   * @return
   */
  List<String> getPeriodsByStartDateEndDate(Date start, Date end);

  /**
   * 财务更改差异单，并结算差异金额至商家
   * 
   * @param sellerSettlement
   * @throws ServiceException
   */
  void updateDiffForSettled(SellerSettlement sellerSettlement) throws ServiceException;

  /**
   * 结算完成发送短信接口
   * 
   * @param loginId
   * @param period
   * @param mobile
   */
  void sendMsg(String loginId, String period, String mobile) throws ServiceException;

  /**
   * 通过loginid获取手机号码
   * 
   * @param record
   * @return
   * @throws ServiceException
   */
  public String selectMobieByLoginId(SellerSettlement record) throws ServiceException;

  /**
   * 通过商家主键获取商家登录ID
   * 
   * @param sellerId
   * @return
   * @throws ServiceException
   */
  public Long queryLoginIdByCommercialId(String sellerId) throws ServiceException;
  
  /**
   * 查询结算单导出数据
   * 
   * @param criteria
   * @return
   * @throws ServiceException
   */
  public List<Map<String,Object>> selectSettlementListForExport(SellerSettlementCriteria criteria)
      throws ServiceException;
}
