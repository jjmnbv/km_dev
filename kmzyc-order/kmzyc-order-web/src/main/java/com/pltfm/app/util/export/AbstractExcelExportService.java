package com.pltfm.app.util.export;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.sys.util.ErrorCode;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * 导出基类
 * 
 * @author 何楷
 * @since JDK1.6
 * @history 2015-11-9 何楷 新建
 */

public abstract class AbstractExcelExportService {
  /**
   * 日志
   */
  private static final Logger logger = Logger.getLogger(AbstractExcelExportService.class);

  /** 设置标题 **/
  protected WritableCellFormat titleHeadFormat;
  /** 数据列头列头样式 */
  protected WritableCellFormat columnHeadFormat;
  /** 设置文本样式(居中文本样式) **/
  protected WritableCellFormat txtCenterFormat;
  /** 文本样式(居左文本样式) */
  protected WritableCellFormat txtLeftFormat;
  /** 文本样式(居右文本样式) */
  protected WritableCellFormat txtRightFormat;
  /** 文本样式(提示信息，如汇总等) */
  protected WritableCellFormat txtLeftNoBorderFormat;
  /** 文本样式(提示信息，如汇总等) */
  protected WritableCellFormat txtRightNoBorderFormat;
  /** 文本样式(提示信息，如汇总等) */
  protected WritableCellFormat txtCenterNoBorderFormat;
  /** 十进制含小树的数字类型格式样式 **/
  protected WritableCellFormat numberDecimalDataFormat;
  /** 十进制整数 数字类型格式样式 **/
  protected WritableCellFormat numberIntegerDataFormat;
  /** 日期类型格式样式 **/
  protected WritableCellFormat txtInfoDateFormat;


  /**
   * 构造器
   * 
   * @throws ServiceException
   */
  public AbstractExcelExportService() throws Exception {
    // 初始化样式
    WritableFont fontHead =
        new WritableFont(WritableFont.createFont("宋体"), 12, WritableFont.NO_BOLD);
    columnHeadFormat = new WritableCellFormat(fontHead);// 标题样式
    columnHeadFormat.setAlignment(jxl.format.Alignment.CENTRE);
    columnHeadFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
    columnHeadFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
    columnHeadFormat.setBackground(Colour.BLUE2);

    WritableFont fontTitle = new WritableFont(WritableFont.createFont("宋体"), 18, WritableFont.BOLD);
    titleHeadFormat = new WritableCellFormat(fontTitle);// 标题样式
    titleHeadFormat.setAlignment(jxl.format.Alignment.CENTRE);
    titleHeadFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);

    WritableFont fontData =
        new WritableFont(WritableFont.createFont("宋体"), 10, WritableFont.NO_BOLD);
    txtCenterFormat = new WritableCellFormat(fontData);// 居中文本样式
    txtCenterFormat.setAlignment(jxl.format.Alignment.CENTRE);
    // txtCenterFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
    txtCenterFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);

    txtLeftFormat = new WritableCellFormat(fontData);// 居左文本样式
    txtLeftFormat.setAlignment(jxl.format.Alignment.LEFT);
    // txtLeftFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
    txtLeftFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);

    txtRightFormat = new WritableCellFormat(fontData);// 居右文本样式
    txtRightFormat.setAlignment(jxl.format.Alignment.RIGHT);
    // txtRightFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
    txtRightFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);

    WritableFont txtNoBorderfont =
        new WritableFont(WritableFont.createFont("宋体"), 12, WritableFont.NO_BOLD);
    txtLeftNoBorderFormat = new WritableCellFormat(txtNoBorderfont);// 居右文本样式
    txtLeftNoBorderFormat.setAlignment(jxl.format.Alignment.LEFT);
    txtLeftNoBorderFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);

    txtRightNoBorderFormat = new WritableCellFormat(txtNoBorderfont);// 居右文本样式
    txtRightNoBorderFormat.setAlignment(jxl.format.Alignment.RIGHT);
    txtRightNoBorderFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);

    txtCenterNoBorderFormat = new WritableCellFormat(txtNoBorderfont);// 居中文本样式
    txtCenterNoBorderFormat.setAlignment(jxl.format.Alignment.CENTRE);
    txtCenterNoBorderFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);

    NumberFormat decimalFontNum = new NumberFormat("#,##0.00");
    numberDecimalDataFormat = new WritableCellFormat(decimalFontNum);// 数字样式
    numberDecimalDataFormat.setAlignment(jxl.format.Alignment.RIGHT);// 水平居右
    numberDecimalDataFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);// 垂直对齐
    // numberDecimalDataFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);

    NumberFormat integerFontNum = new NumberFormat("#,##");
    numberIntegerDataFormat = new WritableCellFormat(integerFontNum);// 数字样式
    numberIntegerDataFormat.setAlignment(jxl.format.Alignment.RIGHT);// 水平居右
    numberIntegerDataFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);// 垂直对齐
    // numberIntegerDataFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);

    WritableFont dateFont =
        new WritableFont(WritableFont.createFont("宋体"), 10, WritableFont.NO_BOLD);
    txtInfoDateFormat = new WritableCellFormat(dateFont);// 标题样式
    txtInfoDateFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
    txtInfoDateFormat.setAlignment(jxl.format.Alignment.LEFT);
  }

  /**
   * 创建excel
   * 
   * @param saveFilePath excel保存的路径
   * @return
   * @throws Exception
   */
  private WritableWorkbook createExcel(String saveFilePath) throws Exception {
    WritableWorkbook wb = null;
    // 默认为新建方式,如果是非新建方式则需要提供templateFilePath参数，暂不处理
    wb = Workbook.createWorkbook(new File(saveFilePath));
    wb.setColourRGB(Colour.BLUE2, 0xC5, 0xD9, 0xF1);
    return wb;
  }


  /**
   * 设置sheet页中的标题 ,该方法是一个默认的实现,默认第一行居中创建excel标题
   * 
   * @param sheet
   * @param params
   * @throws Exception
   */
  protected void setSheetTitle(WritableSheet sheet, Map<String, Object> params) throws Exception {
    String title = this.getSheetTitle(params);
    int lastColIndex = -1;
    // 获取总列数，来进行单元格合并
    String[] columnTitles = getColumnTitles(params);
    if (columnTitles != null && columnTitles.length > 0) {
      lastColIndex = columnTitles.length - 1;
    }
    if (StringUtils.isNotEmpty(title) && lastColIndex >= 0) {
      // 如果多列则合并
      if (lastColIndex > 0) {
        sheet.mergeCells(0, 0, lastColIndex, 0);
      }
      sheet.addCell(new Label(0, 0, title, titleHeadFormat));
    }
  }

  /**
   * 设置数据列列名，该方法是一个默认的实现，默认第二行，第一列开始初始化列标题
   * 
   * @param sheet
   * @param params
   * @throws Exception
   */
  protected void setColumnDataTitle(WritableSheet sheet, Map<String, Object> params)
      throws Exception {
    int rowIndex = 1;
    // 查看是否需要设置title 如果不需要 则从第1行开始
    if (StringUtils.isEmpty(this.getSheetTitle(params))) {
      rowIndex = 0;
    }

    String[] arrTitleName = this.getColumnTitles(params);
    if (arrTitleName != null && arrTitleName.length > 0) {
      for (int colIndex = 0; colIndex < arrTitleName.length; colIndex++) {
        sheet.setColumnView(colIndex, 22);
        sheet.addCell(new Label(colIndex, rowIndex, arrTitleName[colIndex], columnHeadFormat));
      }
    }
  }

  /**
   * 后期处理，用来处理冻结表头，设置密码等其他操作
   * 
   * @param wb
   * @param sheet
   */
  protected void postProcess(WritableWorkbook wb, WritableSheet sheet) throws Exception {
  // SheetSettings sheetSettings = sheet.getSettings();
  // sheetSettings.setVerticalFreeze(2); //冻结列表头
  // sheetSettings.setHorizontalFreeze(1);//冻结行表头
  // sheetSettings.setShowGridLines(false);//显示网格线
  }

  /**
   * 导出excel
   * 
   * @param saveFilePath excel文件保存路径
   * @param sheetName excel中sheet名字
   * @param lstExportData 要写入excel的列表数据
   * @param extraParams 其他参数
   * @throws ServiceException
   */
  @SuppressWarnings("unchecked")
  public void exportExcel(String saveFilePath, String sheetName, List lstExportData,
      Map<String, Object> extraParams) throws ServiceException {
    WritableWorkbook wb = null;
    try {
      // 初始化创建excel
      wb = createExcel(saveFilePath);
      // 创建excelsheet
      WritableSheet sheet = wb.createSheet(sheetName, 0);
      // 初始化excelSheet 标题
      setSheetTitle(sheet, extraParams);
      // 创建题头
      setColumnDataTitle(sheet, extraParams);
      // 添加数据
      writeData(sheet, lstExportData, extraParams);
      // 其他处理
      postProcess(wb, sheet);
      // 保存Excel
      wb.write();
    } catch (Exception e) {
      logger.error(e.getMessage());
      throw new ServiceException(ErrorCode.INNER_ORDER_QUERY_ERROR, "导出Excel文件失败！ ".concat(e
          .getMessage()));
    } finally {
      if (wb != null) {
        try {
          wb.close();
        } catch (Exception e) {
          logger.error(e.getMessage());
          throw new ServiceException(ErrorCode.INNER_ORDER_QUERY_ERROR, "导出Excel文件失败！ ".concat(e
              .getMessage()));
        }
      }
    }

  }

  /**
   * 向sheet中写入数据
   * 
   * @param lstExportData
   * @param startIndex
   */
  @SuppressWarnings("unchecked")
  protected abstract void writeData(WritableSheet sheet, List lstExportData,
      Map<String, Object> params) throws Exception;

  /**
   * 获取sheet的标题信息
   * 
   * @param params
   * @return
   * @throws ServiceException
   */
  protected abstract String getSheetTitle(Map<String, Object> params) throws Exception;


  /**
   * 获取数据列的题头信息
   * 
   * @param params
   * @return
   * @throws ServiceException
   */
  protected abstract String[] getColumnTitles(Map<String, Object> params) throws Exception;



}
