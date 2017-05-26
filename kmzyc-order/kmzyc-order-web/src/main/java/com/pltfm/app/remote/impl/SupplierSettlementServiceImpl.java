package com.pltfm.app.remote.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.order.remote.SupplierSettlementService;
import com.pltfm.app.action.OrderClearingAction;
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
import com.pltfm.app.entities.SettmentOperateStatement;
import com.pltfm.app.service.DiffAdjService;
import com.pltfm.app.service.HurlFareService;
import com.pltfm.app.service.HurlProductService;
import com.pltfm.app.service.OrderQryService;
import com.pltfm.app.service.SellerSettlementService;
import com.pltfm.app.service.SettlementRefundService;
import com.pltfm.app.service.SettmentOperateStatementService;
import com.pltfm.app.util.OrderDictionaryEnum.SettlementOperatorType;
import com.pltfm.sys.util.ErrorCode;

/**
 * 供应商结算单，对外提供查询接口
 * 
 * @author Administrator
 * 
 */
@Service("supplierSettlementRemoteService")
@Scope("singleton")
@SuppressWarnings("unchecked")
public class SupplierSettlementServiceImpl implements SupplierSettlementService {

  private Logger log = Logger.getLogger(OrderClearingAction.class);

  @Resource
  private SellerSettlementService sellerSettlementService;
  @Resource
  private DiffAdjService diffAdjService;
  @Resource
  private HurlProductService hurlProductService;
  @Resource
  private HurlFareService hurlFareService;
  @Resource
  private SettlementRefundService settlementRefundService;

  @Resource
  private SettmentOperateStatementService settmentOperateStatementService;

  @Resource
  private OrderQryService orderQryService;

  @Override
  public int selectSettlementSize(SellerSettlementCriteria criteria) throws ServiceException {
    int count = 0;
    try {
      count = sellerSettlementService.selectSettlementSize(criteria);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTER_SELLER_SETTLEMENT_SIZE_ERROR, "查询结算单数量项发生错误："
          + e.getMessage());
    }
    return count;
  }

  @Override
  public List<SellerSettlement> selectSettlementList(SellerSettlementCriteria criteria)
      throws ServiceException {
    List<SellerSettlement> list = null;
    try {
      list = sellerSettlementService.selectSettlementList(criteria);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTER_SELLER_SETTLEMENT_ERROR, "查询结算单列表项发生错误："
          + e.getMessage());
    }
    return list;
  }

  @Override
  public SellerSettlement getSellerSettlementDetailByNo(String settlementNo)
      throws ServiceException {

    SellerSettlement info = null;
    SellerSettlementCriteria criteria = new SellerSettlementCriteria();
    criteria.setSettlementNo(settlementNo);
    try {
      info = sellerSettlementService.getSettlementByNo(criteria, true);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTER_SELLER_SETTLEMENT_DETAIL_ERROR, "查询结算单详情项发生错误："
          + e.getMessage());
    }
    return info;
  }

  @Override
  public SellerSettlement getSellerSettlementSimpleDetailByNo(String settlementNo)
      throws ServiceException {

    return getSellerSettlementDetailByNo(settlementNo);
  }

  @Override
  public List<HurlProduct> selectHurlProductList(HurlProductCriteria criteria)
      throws ServiceException {
    List<HurlProduct> list = null;
    try {
      list = hurlProductService.selectByExample(HurlProductCriteria.converToExample(criteria));
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTER_HURL_PRODUCT_ERROR, "查询妥投商品明细项发生错误："
          + e.getMessage());
    }
    return list;
  }

  @Override
  public int selectHurlProductSize(HurlProductCriteria criteria) throws ServiceException {
    int count = 0;
    try {
      count = hurlProductService.countByExample(HurlProductCriteria.converToExample(criteria));
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTER_HURL_PRODUCT_ERROR, "查询妥投商品明细项发生错误："
          + e.getMessage());
    }
    return count;
  }

  @Override
  public List<HurlFare> selectHurlFareList(HurlFareCriteria criteria) throws ServiceException {

    List<HurlFare> list = null;
    try {
      list = hurlFareService.selectByExample(HurlFareCriteria.converToExample(criteria));
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTER_HURL_FARE_ERROR, "查询妥投订单运费表项发生错误："
          + e.getMessage());
    }
    return list;
  }

  @Override
  public int selectHurlFareSize(HurlFareCriteria criteria) throws ServiceException {
    int count = 0;
    try {
      count = hurlFareService.countByExample(HurlFareCriteria.converToExample(criteria));
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTER_HURL_FARE_ERROR, "查询妥投订单运费表项发生错误："
          + e.getMessage());
    }
    return count;
  }

  @Override
  public List<SettlementRefund> selectSettlementRefundList(SettlementRefundCriteria criteria)
      throws ServiceException {
    List<SettlementRefund> list = null;
    try {
      list =
          settlementRefundService.selectByExample(SettlementRefundCriteria
              .converToExample(criteria));
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTER_SETTLEMENT_REFUND_ERROR, "查询退款明细项发生错误："
          + e.getMessage());
    }
    return list;
  }

  @Override
  public int selectSettlementRefundSize(SettlementRefundCriteria criteria) throws ServiceException {
    int count = 0;
    try {
      count =
          settlementRefundService
              .countByExample(SettlementRefundCriteria.converToExample(criteria));
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTER_SETTLEMENT_REFUND_ERROR, "查询退款明细项发生错误："
          + e.getMessage());
    }
    return count;
  }

  @Override
  public List<DiffAdj> selectDiffAdjList(DiffAdjCriteria criteria) throws ServiceException {
    List<DiffAdj> list = null;
    try {

      list = diffAdjService.selectDiffAdjList(criteria);

    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTER_DIFF_ADJ_ERROR, "查询差异调整明细项发生错误：" + e.getMessage());
    }
    return list;
  }

  @Override
  public int selectDiffAdjSize(DiffAdjCriteria criteria) throws ServiceException {
    int count = 0;
    try {

      count = diffAdjService.selectDiffAdjSize(criteria);

    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTER_DIFF_ADJ_ERROR, "查询差异调整明细项发生错误：" + e.getMessage());
    }
    return count;
  }

  @Override
  public int updateSettlementSellerConfirmation(String settlementNo, String sellerId,
      String sellerConfirmation, String userName) throws ServiceException {

    log
        .info("====/updateSettlementSellerConfirmation (商户确认状态)。。。start...{ settlementNo, sellerId,  sellerConfirmation, loginId}={"
            + settlementNo + "," + sellerId + "," + sellerConfirmation + "," + userName + "}");
    int count = 0;
    try {

      SellerSettlement record = new SellerSettlement();

      record.setSettlementNo(settlementNo);
      record.setSellerId(sellerId);
      record.setSellerConfirmation(sellerConfirmation);
      record.setSerllerConfirmTime(new Date());

      count = sellerSettlementService.updateSellerConfirmation(record);

      SettmentOperateStatement operater = new SettmentOperateStatement();
      operater.setOperateTime(new Date());

      operater.setOperator(userName);
      operater.setSettmentNo(settlementNo);
      // 前端 用户
      short sss1 = Short.valueOf(SettlementOperatorType.ftUser.getKey() + "");
      operater.setOperatorType(sss1);
      // 结算状态，1:未确认,2:商家已确认,3:运营已确认,4:财务审核通过,5:财务审核拒绝,6:已结出
      operater.setOperateType((short) 2);
      settmentOperateStatementService.insert(operater);

    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTER_DIFF_ADJ_ERROR, "修改 商家确认意见发生错误：" + e.getMessage());
    }
    return count;
  }

  @Override
  public String hurlExport(String sno, String filePath, HurlProductCriteria hurlProductCriteria)
      throws SQLException, ServiceException {
    return hurlProductService.hurlExport(sno, filePath, hurlProductCriteria);
  }

  @Override
  public String diffAdjExport(String sno, String filePath, DiffAdjCriteria diffAdjCriteria)
      throws ServiceException {
    return diffAdjService.diffAdjExport(sno, filePath, diffAdjCriteria);
  }

  @Override
  public String hurlFareExport(String sno, String filePath, HurlFareCriteria hurlFareCriteria)
      throws SQLException, ServiceException {
    return hurlFareService.hurlFareExport(sno, filePath, hurlFareCriteria);
  }

  @Override
  public String refundExport(String sno, String filePath,
      SettlementRefundCriteria settlementRefundCriteria) throws SQLException, ServiceException {
    return settlementRefundService.refundExport(sno, filePath, settlementRefundCriteria);
  }

  @Override
  public String getFilePath(String settlementNo, int type) {
    return diffAdjService.getFilePath(settlementNo, type);
  }

  @Override
  public Map selectHurlSum(HurlProductCriteria hurlProductCriteria) throws ServiceException {
    return hurlProductService.selectHurlSum(hurlProductCriteria);
  }

  @Override
  public Map refundSum(SettlementRefundCriteria settlementRefundCriteria) throws ServiceException {
    return settlementRefundService.refundSum(settlementRefundCriteria);
  }

  @Override
  public Map hurlFareSumResult(HurlFareCriteria hurlFareCriteria) throws ServiceException {
    return hurlFareService.hurlFareSumResult(hurlFareCriteria);
  }

  @Override
  public Map selectSumDiff(DiffAdjCriteria diffAdjCriteria) throws ServiceException {
    return diffAdjService.selectSumDiff(diffAdjCriteria);
  }

  @Override
  public String exportSettleOrder(String roleName, Integer uid) throws ServiceException {
    return orderQryService.exportSettleOrder(roleName, uid);
  }



}
