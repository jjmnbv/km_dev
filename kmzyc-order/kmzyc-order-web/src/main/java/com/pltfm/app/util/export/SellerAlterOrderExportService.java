package com.pltfm.app.util.export;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.entities.OrderAlter;
import com.pltfm.app.util.OrderAlterDictionaryEnum;
import com.pltfm.sys.util.ErrorCode;

import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * 导出供应商退换货订单
 * 
 * @author 何楷
 * @since JDK1.6
 * @history 2015-11-9 何楷 新建
 */
public class SellerAlterOrderExportService extends AbstractExcelExportService {

  private static final Logger logger = Logger.getLogger(SellerAlterOrderExportService.class);

  private  final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");


  private final WritableCellFormat txtLeftAutoWarpFormat;


  public SellerAlterOrderExportService() throws Exception {
    super();

    WritableFont fontData =
        new WritableFont(WritableFont.createFont("宋体"), 10, WritableFont.NO_BOLD);
    txtLeftAutoWarpFormat = new WritableCellFormat(fontData);// 居左文本样式
    txtLeftAutoWarpFormat.setAlignment(jxl.format.Alignment.LEFT);
    txtLeftAutoWarpFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
    txtLeftAutoWarpFormat.setWrap(true);// 通过调整宽度和高度自动换行
  }

  @Override
  protected String[] getColumnTitles(Map<String, Object> params) throws ServiceException {
    return new String[] {"退换货编号", "订单编号", "买家账号", " 商品标题", "SKU编码", "商品单价（元）", "数量 ", "退款金额（元）",
        "服务类型", "申请人", "订单生成时间 ", "退换货申请时间", "退换货审核时间", "退换货完成时间", "状态", "退换货原因"};
  }

  @Override
  protected String getSheetTitle(Map<String, Object> params) throws ServiceException {
    return null;
  }

  @SuppressWarnings("unchecked")
  @Override
  protected void writeData(WritableSheet sheet, List lstExportData, Map<String, Object> params)
      throws ServiceException {
    // 从第2行开始插入数据,rowIndex从0开始
    int rowIndex = 1;
    OrderAlter tempOrderAlter = null;
    try {
      if (lstExportData != null && lstExportData.size() > 0) {
        for (int i = 0; i < lstExportData.size(); i++) {
          tempOrderAlter = (OrderAlter) lstExportData.get(i);
          this.doSetRowData(sheet, tempOrderAlter, rowIndex);
          rowIndex++;
        }

        // 添加汇总行
        doSetSumData(sheet);
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
      throw new ServiceException(ErrorCode.INNER_ORDER_QUERY_ERROR, "导出供应商订单写数据错误".concat(e
          .getMessage()));
    }
  }

  // 设置合计汇总行的数据及公式
  private void doSetSumData(WritableSheet sheet) throws RowsExceededException, WriteException {
    int totalRows = sheet.getRows();
    int totalRowIndex = totalRows + 3;
    sheet.addCell(new Label(0, totalRowIndex, "合计", txtCenterNoBorderFormat));
    sheet.addCell(new Label(1, totalRowIndex, "/", txtRightNoBorderFormat));
    sheet.addCell(new Label(2, totalRowIndex, "/", txtRightNoBorderFormat));
    sheet.addCell(new Label(3, totalRowIndex, "/", txtRightNoBorderFormat));
    sheet.addCell(new Label(4, totalRowIndex, "/", txtRightNoBorderFormat));
    sheet.addCell(new Label(5, totalRowIndex, "/", txtRightNoBorderFormat));
    sheet.addCell(new Formula(6, totalRowIndex, "SUM(G2:G" + totalRows + ")",
        numberIntegerDataFormat));
    sheet.addCell(new Formula(7, totalRowIndex, "SUM(H2:H" + totalRows + ")",
        numberDecimalDataFormat));
    sheet.addCell(new Label(8, totalRowIndex, "/", txtRightNoBorderFormat));
    sheet.addCell(new Label(9, totalRowIndex, "/", txtRightNoBorderFormat));
    sheet.addCell(new Label(10, totalRowIndex, "/", txtRightNoBorderFormat));
    sheet.addCell(new Label(11, totalRowIndex, "/", txtRightNoBorderFormat));
    sheet.addCell(new Label(12, totalRowIndex, "/", txtRightNoBorderFormat));
    sheet.addCell(new Label(13, totalRowIndex, "/", txtRightNoBorderFormat));
    sheet.addCell(new Label(14, totalRowIndex, "/", txtRightNoBorderFormat));
    sheet.addCell(new Label(15, totalRowIndex, "/", txtRightNoBorderFormat));

  }

  /**
   * 按行添加数据
   * 
   * @param sheet
   * @param orderAlterVO
   * @param j
   * @throws RowsExceededException
   * @throws WriteException
   */
  private void doSetRowData(WritableSheet sheet, OrderAlter orderAlterVO, int rowIndex)
      throws RowsExceededException, WriteException {
    // 起始列
    int colIndex = 0;
    String tempString = "";
    BigDecimal tempBigDecimal = BigDecimal.ZERO;

    // 退换货编号
    tempString =
        StringUtils.isBlank(orderAlterVO.getOrderAlterCode()) ? "" : orderAlterVO
            .getOrderAlterCode();
    sheet.addCell(new Label(colIndex, rowIndex, tempString, txtCenterFormat));

    // 订单编号
    tempString =
        StringUtils.isBlank(orderAlterVO.getOrderCode()) ? "" : orderAlterVO.getOrderCode();
    sheet.addCell(new Label(++colIndex, rowIndex, tempString, txtCenterFormat));

    // 买家账号
    tempString =
        StringUtils.isBlank(orderAlterVO.getCustomerAccount()) ? "" : orderAlterVO
            .getCustomerAccount();
    sheet.addCell(new Label(++colIndex, rowIndex, tempString, txtCenterFormat));

    // 商品标题
    tempString =
        StringUtils.isBlank(orderAlterVO.getCommodityTitle()) ? "" : orderAlterVO
            .getCommodityTitle();
    sheet.addCell(new Label(++colIndex, rowIndex, tempString, txtLeftFormat));


    // SKU编码
    tempString =
        StringUtils.isBlank(orderAlterVO.getCommoditySKU()) ? "" : orderAlterVO.getCommoditySKU();
    sheet.addCell(new Label(++colIndex, rowIndex, tempString, txtCenterFormat));

    // 商品单价
    tempBigDecimal = orderAlterVO.getCommodityUnitPrice();
    if (null == tempBigDecimal || tempBigDecimal.compareTo(BigDecimal.ZERO) == 0) {
      sheet.addCell(new Label(++colIndex, rowIndex, "", txtRightFormat));
    } else {
      sheet.addCell(new jxl.write.Number(++colIndex, rowIndex, tempBigDecimal.doubleValue(),
          numberDecimalDataFormat));
    }

    // 数量
    tempBigDecimal = BigDecimal.valueOf(orderAlterVO.getAlterNum());
    if (null == tempBigDecimal || tempBigDecimal.compareTo(BigDecimal.ZERO) == 0) {
      sheet.addCell(new Label(++colIndex, rowIndex, "", txtRightFormat));
    } else {
      sheet.addCell(new jxl.write.Number(++colIndex, rowIndex, tempBigDecimal.doubleValue(),
          numberIntegerDataFormat));
    }

    // 退款金额
    tempBigDecimal = orderAlterVO.getRuturnMoney();
    if (null == tempBigDecimal || tempBigDecimal.compareTo(BigDecimal.ZERO) == 0) {
      sheet.addCell(new Label(++colIndex, rowIndex, "", txtRightFormat));
    } else {
      sheet.addCell(new jxl.write.Number(++colIndex, rowIndex, tempBigDecimal.doubleValue(),
          numberDecimalDataFormat));
    }

    // 服务类型
    tempString = OrderAlterDictionaryEnum.AlterTypes.getValueByKey(orderAlterVO.getAlterType());
    tempString = StringUtils.isBlank(tempString) ? "" : tempString;
    sheet.addCell(new Label(++colIndex, rowIndex, tempString, txtCenterFormat));

    // 申请人
    tempString = StringUtils.isBlank(orderAlterVO.getProposer()) ? "" : orderAlterVO.getProposer();
    sheet.addCell(new Label(++colIndex, rowIndex, tempString, txtCenterFormat));

    // 订单生成时间
    tempString =
        orderAlterVO.getOrderCreateDate() == null ? "" : dateFormat.format(orderAlterVO
            .getOrderCreateDate());
    sheet.addCell(new Label(++colIndex, rowIndex, tempString, txtInfoDateFormat));

    // 退换货申请时间
    tempString =
        orderAlterVO.getCreateDate() == null ? "" : dateFormat.format(orderAlterVO.getCreateDate());
    sheet.addCell(new Label(++colIndex, rowIndex, tempString, txtInfoDateFormat));

    // 退换货审核时间
    tempString =
        orderAlterVO.getCheckDate() == null ? "" : dateFormat.format(orderAlterVO.getCheckDate());
    sheet.addCell(new Label(++colIndex, rowIndex, tempString, txtInfoDateFormat));

    // 退换货完成时间
    tempString =
        orderAlterVO.getFinishDate() == null ? "" : dateFormat.format(orderAlterVO.getFinishDate());
    sheet.addCell(new Label(++colIndex, rowIndex, tempString, txtInfoDateFormat));

    // 状态
    // tempString =
    // OrderAlterDictionaryEnum.Propose_Status.getValueByKey(orderAlterVO.getProposeStatus());
    tempString =
        StringUtils.isBlank(orderAlterVO.getProposeStatusName()) ? "" : orderAlterVO
            .getProposeStatusName();
    tempString = StringUtils.isBlank(tempString) ? "" : tempString;
    sheet.addCell(new Label(++colIndex, rowIndex, tempString, txtCenterFormat));

    // 退换货原因
    tempString =
        StringUtils.isBlank(orderAlterVO.getAlterComment()) ? "" : orderAlterVO.getAlterComment();
    sheet.addCell(new Label(++colIndex, rowIndex, tempString, txtLeftFormat));

    // 加一个空列防止退换货文本超出
    sheet.addCell(new Label(++colIndex, rowIndex, "", txtLeftFormat));

  }
}
