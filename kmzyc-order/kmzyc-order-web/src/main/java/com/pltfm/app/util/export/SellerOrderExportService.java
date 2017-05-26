package com.pltfm.app.util.export;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.entities.OrderMain;
import com.pltfm.app.util.OrderDictionaryEnum;
import com.pltfm.sys.util.ErrorCode;

import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * 供应商导出所有订单功能
 * 
 * @author 何楷
 * @since JDK1.6
 * @history 2015-11-9 何楷 新建
 */
public class SellerOrderExportService extends AbstractExcelExportService {

  private static final Logger logger = Logger.getLogger(SellerOrderExportService.class);

  private  final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

  public SellerOrderExportService() throws Exception {
    super();
  }

  @Override
  protected String getSheetTitle(Map<String, Object> params) throws ServiceException {
    return null;
  }

  @Override
  protected String[] getColumnTitles(Map<String, Object> params) throws ServiceException {
    return new String[] {"订单号", "商品名称", "商品规格", "SKU编码", "单价（元）", "数量（件）", "买家账号", "收货人姓名",
        "收货人电话", "收货地址", "下单时间", "  完成时间", "订单状态", "物流公司", "物流单号", "佣金点", "佣金（元）", "单品优惠活动",
        "订单优惠活动", "运费（元）", "订单金额（元）", "推广服务费", "应结货款（元）","商家货号","买家备注","卖家备注"};
  }

  @SuppressWarnings("unchecked")
  @Override
  public void writeData(WritableSheet sheet, List lstExportData, Map<String, Object> extraParams)
      throws ServiceException {
    // 从第2行开始插入数据,rowIndex从0开始
    int rowIndex = 1;
    OrderMain tempOrder = null;
    try {
      if (lstExportData != null && lstExportData.size() > 0) {
        for (int i = 0; i < lstExportData.size(); i++) {


          tempOrder = (OrderMain) lstExportData.get(i);

          if (tempOrder.getOrderCode().equals("151019183313417827")) {
            System.out.println("stop");
          }

          this.doSetRowData(sheet, tempOrder, rowIndex);

          if (tempOrder.getOrderItems() != null && tempOrder.getOrderItems().size() > 0) {
            rowIndex = rowIndex + tempOrder.getOrderItems().size();
          } else {
            rowIndex++;
          }
        }

        // 添加汇总行
        doSetSumData(sheet);
      }
    } catch (Exception e) {
      e.printStackTrace();
      logger.error(e.getMessage());
      throw new ServiceException(ErrorCode.INNER_ORDER_QUERY_ERROR, "导出供应商订单写数据错误".concat(e
          .getMessage()));
    }
  }

  private void doSetSumData(WritableSheet sheet) throws RowsExceededException, WriteException {
    int totalRows = sheet.getRows();
    int totalRowIndex = totalRows + 3;
    sheet.addCell(new Label(0, totalRowIndex, "合计", txtCenterNoBorderFormat));
    sheet.addCell(new Label(1, totalRowIndex, "/", txtRightNoBorderFormat));
    sheet.addCell(new Label(2, totalRowIndex, "/", txtRightNoBorderFormat));
    sheet.addCell(new Label(3, totalRowIndex, "/", txtRightNoBorderFormat));
    sheet.addCell(new Label(4, totalRowIndex, "/", txtRightNoBorderFormat));
    sheet.addCell(new Formula(5, totalRowIndex, "SUM(F2:F" + totalRows + ")",
        numberIntegerDataFormat));
    sheet.addCell(new Label(6, totalRowIndex, "/", txtRightNoBorderFormat));
    sheet.addCell(new Label(7, totalRowIndex, "/", txtRightNoBorderFormat));
    sheet.addCell(new Label(8, totalRowIndex, "/", txtRightNoBorderFormat));
    sheet.addCell(new Label(9, totalRowIndex, "/", txtRightNoBorderFormat));
    sheet.addCell(new Label(10, totalRowIndex, "/", txtRightNoBorderFormat));
    sheet.addCell(new Label(11, totalRowIndex, "/", txtRightNoBorderFormat));
    sheet.addCell(new Label(12, totalRowIndex, "/", txtRightNoBorderFormat));
    sheet.addCell(new Label(13, totalRowIndex, "/", txtRightNoBorderFormat));
    sheet.addCell(new Label(14, totalRowIndex, "/", txtRightNoBorderFormat));
    sheet.addCell(new Label(15, totalRowIndex, "/", txtRightNoBorderFormat));
    sheet.addCell(new Formula(16, totalRowIndex, "SUM(Q2:Q" + totalRows + ")",
        numberDecimalDataFormat));
    sheet.addCell(new Label(17, totalRowIndex, "/", txtRightNoBorderFormat));
    sheet.addCell(new Label(18, totalRowIndex, "/", txtRightNoBorderFormat));
    sheet.addCell(new Label(19, totalRowIndex, "/", txtRightNoBorderFormat));
    sheet.addCell(new Formula(20, totalRowIndex, "SUM(U2:U" + totalRows + ")",
        numberDecimalDataFormat));
    sheet.addCell(new Formula(21, totalRowIndex, "SUM(V2:V" + totalRows + ")",
        numberDecimalDataFormat));
    sheet.addCell(new Label(22, totalRowIndex, "/", txtRightNoBorderFormat));
    sheet.addCell(new Label(23, totalRowIndex, "/", txtRightNoBorderFormat));
    sheet.addCell(new Label(24, totalRowIndex, "/", txtRightNoBorderFormat));
  }

  /**
   * 按行添加数据
   * 
   * @param sheet
   * @param orderMainVO
   * @param j
   * @throws RowsExceededException
   * @throws WriteException
   */
  private void doSetRowData(WritableSheet sheet, OrderMain orderMainVO, int rowIndex)
      throws RowsExceededException, WriteException {
    // 起始列
    int colIndex = 0;
    // 合并的行数
    int mergeRows =
        orderMainVO.getOrderItems() != null && orderMainVO.getOrderItems().size() > 1 ? orderMainVO
            .getOrderItems().size() - 1 : 0;
    String tempString = "";
    BigDecimal tempBigDecimal = BigDecimal.ZERO;
    Float temp = 0F;

    // 订单号
    sheet.mergeCells(colIndex, rowIndex, colIndex, rowIndex + mergeRows);
    tempString = StringUtils.isBlank(orderMainVO.getOrderCode()) ? "" : orderMainVO.getOrderCode();
    sheet.addCell(new Label(colIndex, rowIndex, tempString, txtCenterFormat));

    // 商品名称
    ++colIndex;
    if (orderMainVO.getOrderItems() != null && orderMainVO.getOrderItems().size() > 0) {
      for (int i = 0; i < orderMainVO.getOrderItems().size(); i++) {
        tempString =
            StringUtils.isBlank(orderMainVO.getOrderItems().get(i).getCommodityTitle())
                ? ""
                : orderMainVO.getOrderItems().get(i).getCommodityTitle();
        sheet.addCell(new Label(colIndex, rowIndex + i, tempString, txtLeftFormat));
      }
    } else {
      // 如果orderItems不存在的处理
      sheet.addCell(new Label(colIndex, rowIndex, "", txtRightFormat));
    }

    // 商品规格
    ++colIndex;
    if (orderMainVO.getOrderItems() != null && orderMainVO.getOrderItems().size() > 0) {
      for (int i = 0; i < orderMainVO.getOrderItems().size(); i++) {
        tempString =
            StringUtils.isBlank(orderMainVO.getOrderItems().get(i).getCommoditySkuDescription())
                ? ""
                : orderMainVO.getOrderItems().get(i).getCommoditySkuDescription();
        sheet.addCell(new Label(colIndex, rowIndex + i, tempString, txtCenterFormat));
      }
    } else {
      // 如果orderItems不存在的处理
      sheet.addCell(new Label(colIndex, rowIndex, "", txtRightFormat));
    }


    // SKU编码
    ++colIndex;
    if (orderMainVO.getOrderItems() != null && orderMainVO.getOrderItems().size() > 0) {
      for (int i = 0; i < orderMainVO.getOrderItems().size(); i++) {
        tempString =
            StringUtils.isBlank(orderMainVO.getOrderItems().get(i).getCommoditySku())
                ? ""
                : orderMainVO.getOrderItems().get(i).getCommoditySku();
        sheet.addCell(new Label(colIndex, rowIndex + i, tempString, txtCenterFormat));
      }
    } else {
      // 如果orderItems不存在的处理
      sheet.addCell(new Label(colIndex, rowIndex, "", txtRightFormat));
    }

    // 单价（元）
    ++colIndex;
    if (orderMainVO.getOrderItems() != null && orderMainVO.getOrderItems().size() > 0) {
      for (int i = 0; i < orderMainVO.getOrderItems().size(); i++) {
        tempBigDecimal = orderMainVO.getOrderItems().get(i).getCommodityUnitPrice();
        if (null == tempBigDecimal || tempBigDecimal.compareTo(BigDecimal.ZERO) == 0) {
          sheet.addCell(new Label(colIndex, rowIndex + i, "", txtRightFormat));
        } else {
          sheet.addCell(new jxl.write.Number(colIndex, rowIndex + i, tempBigDecimal.doubleValue(),
              numberDecimalDataFormat));
        }
      }
    } else {
      // 如果orderItems不存在的处理
      sheet.addCell(new Label(colIndex, rowIndex, "", txtRightFormat));
    }

    // 数量（件）
    ++colIndex;
    if (orderMainVO.getOrderItems() != null && orderMainVO.getOrderItems().size() > 0) {
      for (int i = 0; i < orderMainVO.getOrderItems().size(); i++) {
        tempBigDecimal =
            BigDecimal.valueOf(orderMainVO.getOrderItems().get(i).getCommodityNumber());
        if (null == tempBigDecimal || tempBigDecimal.compareTo(BigDecimal.ZERO) == 0) {
          sheet.addCell(new Label(colIndex, rowIndex + i, "", txtRightFormat));
        } else {
          sheet.addCell(new jxl.write.Number(colIndex, rowIndex + i, tempBigDecimal.doubleValue(),
              numberIntegerDataFormat));
        }
      }
    } else {
      // 如果orderItems不存在的处理
      sheet.addCell(new Label(colIndex, rowIndex, "", txtRightFormat));
    }

    // 买家账号
    sheet.mergeCells(++colIndex, rowIndex, colIndex, rowIndex + mergeRows);
    tempString =
        StringUtils.isBlank(orderMainVO.getCustomerAccount()) ? "" : orderMainVO
            .getCustomerAccount();
    sheet.addCell(new Label(colIndex, rowIndex, tempString, txtCenterFormat));

    // 收货人姓名
    sheet.mergeCells(++colIndex, rowIndex, colIndex, rowIndex + mergeRows);
    tempString =
        StringUtils.isBlank(orderMainVO.getConsigneeName()) ? "" : orderMainVO.getConsigneeName();
    sheet.addCell(new Label(colIndex, rowIndex, tempString, txtCenterFormat));

    // 收货人电话
    sheet.mergeCells(++colIndex, rowIndex, colIndex, rowIndex + mergeRows);
    tempString =
        StringUtils.isBlank(orderMainVO.getConsigneeMobile()) ? "" : orderMainVO
            .getConsigneeMobile();
    sheet.addCell(new Label(colIndex, rowIndex, tempString, txtCenterFormat));

    // 收货地址
    sheet.mergeCells(++colIndex, rowIndex, colIndex, rowIndex + mergeRows);
    tempString =
        StringUtils.isBlank(orderMainVO.getConsigneeAddr()) ? "" : orderMainVO.getConsigneeAddr();
    sheet.addCell(new Label(colIndex, rowIndex, tempString, txtCenterFormat));

    // 下单时间
    sheet.mergeCells(++colIndex, rowIndex, colIndex, rowIndex + mergeRows);
    tempString =
        orderMainVO.getCreateDate() == null ? "" : dateFormat.format(orderMainVO.getCreateDate());
    sheet.addCell(new Label(colIndex, rowIndex, tempString, txtInfoDateFormat));

    // 完成时间
    sheet.mergeCells(++colIndex, rowIndex, colIndex, rowIndex + mergeRows);
    tempString =
        orderMainVO.getFinishDate() == null ? "" : dateFormat.format(orderMainVO.getFinishDate());
    sheet.addCell(new Label(colIndex, rowIndex, tempString, txtInfoDateFormat));

    // 订单状态
    sheet.mergeCells(++colIndex, rowIndex, colIndex, rowIndex + mergeRows);
    tempString =
        OrderDictionaryEnum.Order_Status.getValueByKey(orderMainVO.getOrderStatus().intValue());
    tempString = StringUtils.isBlank(tempString) ? "" : tempString;
    sheet.addCell(new Label(colIndex, rowIndex, tempString, txtCenterFormat));


    // 物流公司
    sheet.mergeCells(++colIndex, rowIndex, colIndex, rowIndex + mergeRows);
    tempString =
        StringUtils.isBlank(orderMainVO.getLogisticsName()) ? "" : orderMainVO.getLogisticsName();
    sheet.addCell(new Label(colIndex, rowIndex, tempString, txtCenterFormat));

    // 物流单号
    sheet.mergeCells(++colIndex, rowIndex, colIndex, rowIndex + mergeRows);
    tempString =
        StringUtils.isBlank(orderMainVO.getLogisticsOrderNo()) ? "" : orderMainVO
            .getLogisticsOrderNo();
    sheet.addCell(new Label(colIndex, rowIndex, tempString, txtCenterFormat));

    // 佣金点
    ++colIndex;
    if (orderMainVO.getOrderItems() != null && orderMainVO.getOrderItems().size() > 0) {
      for (int i = 0; i < orderMainVO.getOrderItems().size(); i++) {
        tempBigDecimal = orderMainVO.getOrderItems().get(i).getCommissionRate();
        if (null == tempBigDecimal || tempBigDecimal.compareTo(BigDecimal.ZERO) == 0) {
          sheet.addCell(new Label(colIndex, rowIndex + i, "", txtRightFormat));
        } else {
          sheet.addCell(new jxl.write.Number(colIndex, rowIndex + i, tempBigDecimal.doubleValue(),
              numberDecimalDataFormat));
        }
      }
    } else {
      // 如果orderItems不存在的处理
      sheet.addCell(new Label(colIndex, rowIndex, "", txtRightFormat));
    }


    // 佣金（元）
    ++colIndex;
    if (orderMainVO.getOrderItems() != null && orderMainVO.getOrderItems().size() > 0) {
      for (int i = 0; i < orderMainVO.getOrderItems().size(); i++) {
        tempBigDecimal = orderMainVO.getOrderItems().get(i).getCommission();
        if (null == tempBigDecimal || tempBigDecimal.compareTo(BigDecimal.ZERO) == 0) {
          sheet.addCell(new Label(colIndex, rowIndex + i, "", txtRightFormat));
        } else {
          sheet.addCell(new jxl.write.Number(colIndex, rowIndex + i, tempBigDecimal.doubleValue(),
              numberDecimalDataFormat));
        }
      }
    } else {
      // 如果orderItems不存在的处理
      sheet.addCell(new Label(colIndex, rowIndex, "", txtRightFormat));
    }

    // 单品优惠活动
    ++colIndex;
    if (orderMainVO.getOrderItems() != null && orderMainVO.getOrderItems().size() > 0) {
      for (int i = 0; i < orderMainVO.getOrderItems().size(); i++) {
        tempString =
            StringUtils.isBlank(orderMainVO.getOrderItems().get(i).getPromotionType())
                ? ""
                : orderMainVO.getOrderItems().get(i).getPromotionType();
        sheet.addCell(new Label(colIndex, rowIndex + i, tempString, txtCenterFormat));
      }
    } else {
      // 如果orderItems不存在的处理
      sheet.addCell(new Label(colIndex, rowIndex, "", txtRightFormat));
    }

    // 订单优惠活动
    sheet.mergeCells(++colIndex, rowIndex, colIndex, rowIndex + mergeRows);
    tempString =
        StringUtils.isBlank(orderMainVO.getPromotionName()) ? "" : orderMainVO.getPromotionName();
    sheet.addCell(new Label(colIndex, rowIndex, tempString, txtCenterFormat));

    // 运费（元）
    sheet.mergeCells(++colIndex, rowIndex, colIndex, rowIndex + mergeRows);
    tempBigDecimal = orderMainVO.getFare();
    if (null == tempBigDecimal || tempBigDecimal.compareTo(BigDecimal.ZERO) == 0) {
      sheet.addCell(new Label(colIndex, rowIndex, "", txtRightFormat));
    } else {
      sheet.addCell(new jxl.write.Number(colIndex, rowIndex, tempBigDecimal.doubleValue(),
          numberDecimalDataFormat));
    }

    // 订单金额（元）
    sheet.mergeCells(++colIndex, rowIndex, colIndex, rowIndex + mergeRows);
    tempBigDecimal = orderMainVO.getAmountPayable();
    if (null == tempBigDecimal || tempBigDecimal.compareTo(BigDecimal.ZERO) == 0) {
      sheet.addCell(new Label(colIndex, rowIndex, "", txtRightFormat));
    } else {
      sheet.addCell(new jxl.write.Number(colIndex, rowIndex, tempBigDecimal.doubleValue(),
          numberDecimalDataFormat));
    }
    
    //推广服务费 （元）
    sheet.mergeCells(++colIndex, rowIndex, colIndex, rowIndex + mergeRows);
    temp = orderMainVO.getOrderPv();
    if (null == temp || temp == 0) {
      sheet.addCell(new Label(colIndex, rowIndex, "", txtRightFormat));
    } else {
      sheet.addCell(new jxl.write.Number(colIndex, rowIndex, temp.doubleValue(),
          numberDecimalDataFormat));
    }

    // 应结货款（元） orderCommodityPayment
    sheet.mergeCells(++colIndex, rowIndex, colIndex, rowIndex + mergeRows);
    tempBigDecimal = BigDecimal.ZERO;
    if (orderMainVO.getOrderItems() != null && orderMainVO.getOrderItems().size() > 0) {
      for (int i = 0; i < orderMainVO.getOrderItems().size(); i++) {
        tempBigDecimal =
            tempBigDecimal.add(orderMainVO.getOrderItems().get(i).getCommodityPayment());
      }
    }
    if (null == tempBigDecimal || tempBigDecimal.compareTo(BigDecimal.ZERO) == 0) {
      sheet.addCell(new Label(colIndex, rowIndex, "", txtRightFormat));
    } else {
      sheet.addCell(new jxl.write.Number(colIndex, rowIndex, tempBigDecimal.doubleValue(),
          numberDecimalDataFormat));
    }
    
    // 商家货号
    ++colIndex;
    if (orderMainVO.getOrderItems() != null && orderMainVO.getOrderItems().size() > 0) {
      for (int i = 0; i < orderMainVO.getOrderItems().size(); i++) {
        tempString =
            StringUtils.isBlank(orderMainVO.getOrderItems().get(i).getSellerSkuCode())
                ? ""
                : orderMainVO.getOrderItems().get(i).getSellerSkuCode();
        sheet.addCell(new Label(colIndex, rowIndex + i, tempString, txtCenterFormat));
      }
    } else {
      // 如果orderItems不存在的处理
      sheet.addCell(new Label(colIndex, rowIndex, "", txtRightFormat));
    }
    
    // 买家备注
    sheet.mergeCells(++colIndex, rowIndex, colIndex, rowIndex + mergeRows);
    tempString =
        StringUtils.isBlank(orderMainVO.getOrderDescription()) ? "" : orderMainVO.getOrderDescription();
    sheet.addCell(new Label(colIndex, rowIndex, tempString, txtCenterFormat));
    
    // 卖家备注
    sheet.mergeCells(++colIndex, rowIndex, colIndex, rowIndex + mergeRows);
    tempString =
        StringUtils.isBlank(orderMainVO.getOrderOperationRemark()) ? "" : orderMainVO.getOrderOperationRemark();
    sheet.addCell(new Label(colIndex, rowIndex, tempString, txtCenterFormat));
    
    // 加一个空列防止退换货文本超出
    sheet.addCell(new Label(++colIndex, rowIndex, "", txtLeftFormat));
    
  }
}
