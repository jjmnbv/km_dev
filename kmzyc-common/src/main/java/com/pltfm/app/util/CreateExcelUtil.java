package com.pltfm.app.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

public class CreateExcelUtil {

  private Workbook wb = null;

  private Sheet sheet = null;

  public Workbook getWb() {
    return wb;
  }

  public void setWb(Workbook wb) {
    this.wb = wb;
  }

  public Sheet getSheet() {
    return sheet;
  }

  public void setSheet(Sheet sheet) {
    this.sheet = sheet;
  }

  /**
   * @param wb
   * @param sheet
   */
  public CreateExcelUtil(Workbook wb, Sheet sheet) {
    super();
    this.wb = wb;
    this.sheet = sheet;
  }

  /**
   * 创建通用EXCEL头部
   * 
   * @param headString 头部显示的字符
   * @param colSum 该报表的列数
   */
  public void createNormalHead(String headString, int colSum) {

    Row row = sheet.createRow(0);

    // 设置第一行
    Cell cell = row.createCell(0);
    row.setHeight((short) 400);

    // 定义单元格为字符串类型
    cell.setCellType(HSSFCell.ENCODING_UTF_16);
    cell.setCellValue(new HSSFRichTextString(headString));

    // 指定合并区域
    sheet.addMergedRegion(new CellRangeAddress(0, (short) 0, 0, (short) colSum));

    CellStyle cellStyle = wb.createCellStyle();

    cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
    cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
    cellStyle.setWrapText(true);// 指定单元格自动换行

    // 设置单元格字体
    Font font = wb.createFont();
    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    font.setFontName("宋体");
    font.setFontHeight((short) 300);
    cellStyle.setFont(font);

    cell.setCellStyle(cellStyle);
  }

  /**
   * 创建内容单元格
   * 
   * @param wb HSSFWorkbook
   * @param row HSSFRow
   * @param col short型的列索引
   * @param cellstyle 对齐方式
   * @param val 列值
   */
  public void cteateCell(Workbook wb, Row row, int col, CellStyle cellstyle, String val) {

    Cell cell = row.createCell(col);
    cell.setCellType(HSSFCell.ENCODING_UTF_16);
    cell.setCellValue(new HSSFRichTextString(val));

    cell.setCellStyle(cellstyle);
  }

}
