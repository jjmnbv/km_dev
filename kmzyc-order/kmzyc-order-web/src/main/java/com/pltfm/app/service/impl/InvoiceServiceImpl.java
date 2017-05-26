package com.pltfm.app.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.dao.InvoiceDAO;
import com.pltfm.app.dao.InvoiceItemDAO;
import com.pltfm.app.entities.Invoice;
import com.pltfm.app.entities.InvoiceItem;
import com.pltfm.app.entities.OrderItem;
import com.pltfm.app.entities.OrderItemExample;
import com.pltfm.app.entities.OrderMain;
import com.pltfm.app.service.InvoiceService;
import com.pltfm.app.service.OrderItemQryService;
import com.pltfm.app.util.OrderDictionaryEnum;
import com.pltfm.sys.util.ErrorCode;

@Service("invoiceService")
@SuppressWarnings("unchecked")
public class InvoiceServiceImpl implements InvoiceService {
  private static Logger logger = Logger.getLogger(InvoiceServiceImpl.class);

  @Resource
  InvoiceDAO invoiceDAO;
  @Resource
  private InvoiceItemDAO invoiceItemDAO;
  @Resource
  private OrderItemQryService orderItemQryService;

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
  public Long save(OrderMain om) throws ServiceException {
    try {
      // 生成发票
      Date now = new Date();
      Long invoiceId = invoiceDAO.insertSelective(order2invoice(om, now));
      // 生成发票明细
      OrderItemExample oi = new OrderItemExample();
      OrderItemExample.Criteria criteria = oi.createCriteria();
      criteria.andOrderCodeEqualTo(om.getOrderCode());
      List orderItemList = orderItemQryService.listOrderItems(om.getOrderCode());
      // List orderItemList = orderItemDAO.selectByExample(oi);
      for (Object oit : orderItemList) {
        invoiceItemDAO.insertSelective(orderItem2invoiceItem((OrderItem) oit, invoiceId));
      }
      return invoiceId;
    } catch (SQLException e) {
      logger.info(e.getMessage());
      throw new ServiceException(ErrorCode.INNER_SINGLE_ERROR, "根据订单号生成订单发票及发票明细：" + e.getMessage());
    }
  }

  private Invoice order2invoice(OrderMain om, Date now) {// 订单到发票未完整
    Invoice iv = new Invoice();
    iv.setPayeeName(om.getCustomerName());
    iv.setCreateType((short) OrderDictionaryEnum.OrderPurchaserType.Register.getKey());// 自开
    iv.setPayerName(om.getConsigneeName());
    iv.setInvoiceSum(om.getCommoditySum());
    iv.setCreateDate(now);
    return iv;
  }

  private InvoiceItem orderItem2invoiceItem(OrderItem oit, Long invoiceId) {// 订单项到发票项
    InvoiceItem ivt = new InvoiceItem();
    // ivt.setDeductionFlag(deductionFlag);//抵扣？
    ivt.setDiscountAmount((oit.getCommodityUnitPrice().multiply(
        BigDecimal.valueOf(oit.getCommodityNumber())).subtract(oit.getCommoditySum())));// 折扣额
    ivt.setInvoiceId(invoiceId);
    ivt.setInvoiceItemCode(oit.getCommoditySku());// 商品号？
    ivt.setInvoiceItemName(oit.getCommodityName());
    ivt.setInvoiceItemNumber(oit.getCommodityNumber());
    ivt.setLineSum(oit.getCommoditySum());
    // ivt.setNote(note);
    ivt.setUnitPrice(oit.getCommodityUnitPrice());
    return ivt;
  }

}
