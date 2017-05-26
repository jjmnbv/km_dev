package com.kmzyc.order.remote;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.entities.DiffAdj;
import com.pltfm.app.entities.DiffAdjCriteria;
import com.pltfm.app.entities.HurlFare;
import com.pltfm.app.entities.HurlFareCriteria;
import com.pltfm.app.entities.HurlProduct;
import com.pltfm.app.entities.HurlProductCriteria;
import com.pltfm.app.entities.SellerSettlement;
import com.pltfm.app.entities.SellerSettlementCriteria;
import com.pltfm.app.entities.SettlementRefund;
import com.pltfm.app.entities.SettlementRefundCriteria;

/**
 * 供应商结算单，对外提供查询接口
 * 
 * @author lvzj
 * 
 */
@SuppressWarnings("unused")
public interface SupplierSettlementService {

  /**
   * 查询结算单 数量
   * 
   * @param criteria
   * @return
   * @throws ServiceException
   */
  public int selectSettlementSize(SellerSettlementCriteria criteria) throws ServiceException;

  /**
   * 查询结算单 列表
   * 
   * @param criteria
   * @return
   * @throws ServiceException
   */
  public List<SellerSettlement> selectSettlementList(SellerSettlementCriteria criteria)
      throws ServiceException;

  /**
   * 商家结算单详情 （详细的，全部查出）
   * 
   * @return
   * @throws ServiceException
   */
  public SellerSettlement getSellerSettlementDetailByNo(String settlementNo)
      throws ServiceException;

  /**
   * 明细展示接口 （简单）
   * 
   * @return
   * @throws ServiceException
   */
  public SellerSettlement getSellerSettlementSimpleDetailByNo(String settlementNo)
      throws ServiceException;

  /**
   * 妥投商品结算明细 列表
   * 
   * @param criteria
   * @return
   * @throws ServiceException
   */
  public List<HurlProduct> selectHurlProductList(HurlProductCriteria criteria)
      throws ServiceException;

  /**
   * 统计 妥投商品结算明细 列表
   * 
   * @param criteria
   * @return
   * @throws ServiceException
   */
  public int selectHurlProductSize(HurlProductCriteria criteria) throws ServiceException;

  /**
   * 妥投订单运费明细: hurl_fare 列表
   * 
   * @param criteria
   * @return
   * @throws ServiceException
   */
  public List<HurlFare> selectHurlFareList(HurlFareCriteria criteria) throws ServiceException;

  /**
   * 统计 妥投订单运费明细: hurl_fare
   * 
   * @param criteria
   * @return
   * @throws ServiceException
   */
  public int selectHurlFareSize(HurlFareCriteria criteria) throws ServiceException;

  /**
   * 退款明细：Settlement_refund 列表
   * 
   * @param criteria
   * @return
   * @throws ServiceException
   */
  public List<SettlementRefund> selectSettlementRefundList(SettlementRefundCriteria criteria)
      throws ServiceException;

  /**
   * 统计 退款明细：Settlement_refund
   * 
   * @param criteria
   * @return
   * @throws ServiceException
   */
  public int selectSettlementRefundSize(SettlementRefundCriteria criteria) throws ServiceException;

  /**
   * 差异调整明细Diff_adj 列表
   * 
   * @param criteria
   * @return
   * @throws ServiceException
   */
  public List<DiffAdj> selectDiffAdjList(DiffAdjCriteria criteria) throws ServiceException;

  /**
   * 统计 差异调整明细Diff_adj
   * 
   * @param criteria
   * @return
   * @throws ServiceException
   */
  public int selectDiffAdjSize(DiffAdjCriteria criteria) throws ServiceException;

  /**
   * 修改 商家确认意见
   * 
   * @param settlementNo 结算单号
   * @param sellerId 对应商家会员ID
   * @param sellerConfirmation 商家确认意见
   * @return
   */
  int updateSettlementSellerConfirmation(String settlementNo, String sellerId,
      String sellerConfirmation, String loginId) throws ServiceException;

  /**
   * 妥投商品导出
   * 
   * @param sno
   * @param filePath
   * @return
   * @throws SQLException
   * @throws ServiceException
   */
  public String hurlExport(String sno, String filePath, HurlProductCriteria hurlProductCriteria)
      throws SQLException, ServiceException;

  /**
   * 差异调整明细导出
   * 
   * @param sno
   * @param filePath
   * @param diffAdjCriteria
   * @return
   * @throws ServiceException
   */
  public String diffAdjExport(String sno, String filePath, DiffAdjCriteria diffAdjCriteria)
      throws ServiceException;

  /**
   * 妥投运费数据导出
   * 
   * @param sno
   * @param filePath
   * @param hurlFareCriteria
   * @return
   * @throws SQLException
   * @throws ServiceException
   */
  public String hurlFareExport(String sno, String filePath, HurlFareCriteria hurlFareCriteria)
      throws SQLException, ServiceException;

  /**
   * 退款数据导出
   * 
   * @param sno
   * @param filePath
   * @param settlementRefundCriteria
   * @return
   * @throws SQLException
   * @throws ServiceException
   */
  public String refundExport(String sno, String filePath,
      SettlementRefundCriteria settlementRefundCriteria) throws SQLException, ServiceException;

  /**
   * 查询文件路径
   * 
   * @param settlementNo
   * @param type
   * @return 返回
   */
  String getFilePath(String settlementNo, int type);

  /**
   * 获取妥投汇总金额数
   * 
   * @param map
   * @return
   * @throws ServiceException
   */
  public Map selectHurlSum(HurlProductCriteria hurlProductCriteria) throws ServiceException;

  /**
   * 获取退款综合映射
   * 
   * @param settlementRefundCriteria
   * @return
   * @throws ServiceException
   */
  public Map refundSum(SettlementRefundCriteria settlementRefundCriteria) throws ServiceException;

  /**
   * 运费汇总数据映射
   * 
   * @param hurlFareCriteria
   * @return
   * @throws ServiceException
   */
  Map hurlFareSumResult(HurlFareCriteria hurlFareCriteria) throws ServiceException;

  /**
   * 差异调整金额汇总
   * 
   * @param diffAdjCriteria
   * @return
   * @throws ServiceException
   */
  public Map selectSumDiff(DiffAdjCriteria diffAdjCriteria) throws ServiceException;

  /**
   * 提供给远程调用 -- 导出结算单详情
   * 
   * @param roleName
   * @param uid
   * @return
   * @throws ServiceException
   */
  public String exportSettleOrder(String roleName, Integer uid) throws ServiceException;
}
