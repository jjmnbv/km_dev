package com.pltfm.app.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.util.CreateExcelUtil;
import com.pltfm.app.util.SysConstants;

import oracle.sql.TIMESTAMP;

@SuppressWarnings("unchecked")
public abstract class BaseService {

    private Logger log = Logger.getLogger(BaseService.class);
    protected static final int DEE_DIV_SCALE = 2;
    private static final int memorySize = 100;// SXSSFWorkbook在内存中保持的最大行数
    private static final int pageSize = 5000;// 分批查询单次数据量

    /**
     * 渠道
     * 
     * @param channel
     * @return
     */
    public String getInterfaceSysCode(String channel) {
        String interfaceSysCode = "";
        if ("B2B".equalsIgnoreCase(channel)) {
            interfaceSysCode = SysConstants.B2B_SYSCODE;
        }

        return interfaceSysCode;
    }

    /**
     * 导出文件
     * 
     * @param sheetName
     * @param lable
     * @param data
     * @return
     * @throws ServiceException
     */
    public String exportFile(int uid, String sheetName, List<String> lable, List<String> key,
            List<Map<String, Object>> data) throws ServiceException {
        if (null != lable && !lable.isEmpty()) {
            HSSFWorkbook wb = new HSSFWorkbook();// excel
            // 列名样式 水平居中 垂直居中 12px出题黑楷体文字
            HSSFCellStyle cellStyle = wb.createCellStyle();
            cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            Font font = wb.createFont();
            font.setFontHeightInPoints((short) 12);
            font.setFontName("楷体");
            font.setBoldweight(Font.BOLDWEIGHT_BOLD);
            font.setColor(HSSFColor.BLACK.index);
            cellStyle.setFont(font);
            // 数据样式 水平居中 垂直居中 默认字体大小
            HSSFCellStyle cellStyleCon = wb.createCellStyle();
            cellStyleCon.setAlignment(CellStyle.ALIGN_CENTER);
            cellStyleCon.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

            // sheet
            HSSFSheet sheet = wb.createSheet(sheetName);
            int len = lable.size();
            for (int i = 0; i < len; i++) {
                sheet.setColumnWidth(i, 4500);
            }

            // 标题
            CreateExcelUtil excel = new CreateExcelUtil(wb, sheet);
            int rowIndex = 0;// 行下标
            HSSFRow row = sheet.createRow(rowIndex++);
            for (int i = 0; i < len; i++) {
                excel.cteateCell(wb, row, i, cellStyle, lable.get(i));
            }
            // 内容
            if (null != data && !data.isEmpty()) {
                for (Map map : data) {
                    row = sheet.createRow(rowIndex++);
                    for (int j = 0; j < len; j++) {
                        Cell cell = row.createCell(j);
                        cell.setCellStyle(cellStyleCon);
                        getStringValue(cell, map.get(key.get(j)));
                    }
                }
            }
            List<String> path = getExcelName(uid + "_export", ".xls");
            FileOutputStream fileOut;
            try {
                fileOut = new FileOutputStream(path.get(0));
                wb.write(fileOut);
                fileOut.close();
                log.info("生成excel文件完成！");
                return path.get(1);
            } catch (Exception e) {
                throw new ServiceException(0, "生成" + sheetName + "数据异常" + e.getMessage());
            }
        }
        return null;
    }

    /**
     * 导出文件excel2007格式，分页查询，支持单个sheet1000,000行数据
     */
    public String exportFile2007(Map<String, String> map, int uid, String sheetName,
            List<String> lable, List<String> key) throws ServiceException {
        int rows = getRows(map);// 总行数
        if (null != lable && !lable.isEmpty()) {
            Workbook wb = new SXSSFWorkbook(memorySize);// excel
            // 列名样式 水平居中 垂直居中 12px出题黑楷体文字
            CellStyle cellStyle = wb.createCellStyle();
            cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            Font font = wb.createFont();
            font.setFontHeightInPoints((short) 12);
            font.setFontName("楷体");
            font.setBoldweight(Font.BOLDWEIGHT_BOLD);
            font.setColor(HSSFColor.BLACK.index);
            cellStyle.setFont(font);
            // 数据样式 水平居中 垂直居中 默认字体大小
            CellStyle cellStyleCon = wb.createCellStyle();
            cellStyleCon.setAlignment(CellStyle.ALIGN_CENTER);
            cellStyleCon.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

            // sheet
            Sheet sheet = wb.createSheet(sheetName);
            int len = lable.size();
            for (int i = 0; i < len; i++) {
                sheet.setColumnWidth(i, 4500);
            }

            // 标题
            CreateExcelUtil excel = new CreateExcelUtil(wb, sheet);
            int rowIndex = 0;// 行下标
            Row row = sheet.createRow(rowIndex++);
            for (int i = 0; i < len; i++) {
                excel.cteateCell(wb, row, i, cellStyle, lable.get(i));
            }
            // 内容
            List<Map<String, Object>> data;
            int loop = (rows + pageSize - 1) / pageSize;
            for (int i = 1; i <= loop; i++) {
                int start = pageSize * (i - 1) + 1;
                int end = start + pageSize - 1;
                int nowIndex = rowIndex + start - 1;
                if (end > rows) {
                    end = rows;
                }
                map.put("start", start + "");
                map.put("end", end + "");
                data = getData(map);
                if (null != data && !data.isEmpty()) {
                    for (Map dmap : data) {
                        row = sheet.createRow(nowIndex++);
                        for (int j = 0; j < len; j++) {
                            Cell cell = row.createCell(j);
                            cell.setCellStyle(cellStyleCon);
                            getStringValue(cell, dmap.get(key.get(j)));
                            // excel.cteateCell(wb, row, j, cellStyleCon,
                            // getStringValue(map.get(key.get(j))));
                        }
                    }
                }
            }
            List<String> path = getExcelName(uid + "_export", ".xlsx");
            FileOutputStream fileOut;
            try {
                fileOut = new FileOutputStream(path.get(0));
                wb.write(fileOut);
                fileOut.close();
                log.info("生成excel文件完成！");
                return path.get(1);
            } catch (Exception e) {
                throw new ServiceException(0, "生成" + sheetName + "数据异常" + e.getMessage());
            }
        }
        return null;
    }

    // 需要导出数据总条数，需要子类重写
    protected int getRows(Map map) throws ServiceException {
        return 0;
    };

    // 分页查询方法，需要子类重写
    protected List<Map<String, Object>> getData(Map map) throws ServiceException {
        return null;
    }

    public List<String> getExcelName(String channel, String type) {
        List<String> liststr = new ArrayList<String>();
        StringBuffer sb = new StringBuffer();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String excelPath = ConfigurationUtil.getString("path");
        String visitPath = ConfigurationUtil.getString("visitPath");
        // String excelPath = "E:/";
        // String visitPath = "E:/";
        sb.append("/report/").append(sdf.format(cal.getTime())).append('_').append(channel)
                .append(type);
        File file = new File(excelPath + sb.toString());
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        liststr.add(file.getPath());
        liststr.add(visitPath + sb.toString());
        return liststr;
    }

    public void getStringValue(Cell cell, Object obj) {
        if (null != obj) {
            if (obj instanceof Date) {
                Date t = (Date) obj;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    cell.setCellValue(sdf.format(t));
                } catch (Exception e) {
                }
            } else if (obj instanceof TIMESTAMP) {
                TIMESTAMP t = (TIMESTAMP) obj;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                try {
                    cell.setCellValue(sdf.format(t.timestampValue()));
                } catch (SQLException e) {
                }
            } else if (obj instanceof Long || obj instanceof Double || obj instanceof Float
                    || obj instanceof BigDecimal) {
                cell.setCellValue(new BigDecimal(obj.toString()).doubleValue());
            } else if (obj instanceof Integer) {
                cell.setCellValue(Integer.parseInt(obj.toString()));
            } else {
                cell.setCellValue(obj.toString());
            }
        }
    }

    /**
     * 导出文件
     * 
     * @param sheetName
     * @param lable
     * @param data
     * @return
     * @throws ServiceException
     */
    public String exportFileForFileName(String fileName, String sheetName, List<String> lable,
            List<String> key, List<Map<String, Object>> data) throws ServiceException {
        if (null != lable && !lable.isEmpty()) {
            HSSFWorkbook wb = new HSSFWorkbook();// excel
            // 列名样式 水平居中 垂直居中 12px出题黑楷体文字
            HSSFCellStyle cellStyle = wb.createCellStyle();
            cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            Font font = wb.createFont();
            font.setFontHeightInPoints((short) 12);
            font.setFontName("楷体");
            font.setBoldweight(Font.BOLDWEIGHT_BOLD);
            font.setColor(HSSFColor.BLACK.index);
            cellStyle.setFont(font);
            // 数据样式 水平居中 垂直居中 默认字体大小
            HSSFCellStyle cellStyleCon = wb.createCellStyle();
            cellStyleCon.setAlignment(CellStyle.ALIGN_CENTER);
            cellStyleCon.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

            // sheet
            HSSFSheet sheet = wb.createSheet(sheetName);
            int len = lable.size();
            for (int i = 0; i < len; i++) {
                sheet.setColumnWidth(i, 4500);
            }

            // 标题
            CreateExcelUtil excel = new CreateExcelUtil(wb, sheet);
            int rowIndex = 0;// 行下标
            HSSFRow row = sheet.createRow(rowIndex++);
            for (int i = 0; i < len; i++) {
                excel.cteateCell(wb, row, i, cellStyle, lable.get(i));
            }
            // 内容
            if (null != data && !data.isEmpty()) {
                for (Map map : data) {
                    row = sheet.createRow(rowIndex++);
                    for (int j = 0; j < len; j++) {
                        Cell cell = row.createCell(j);
                        cell.setCellStyle(cellStyleCon);
                        getStringValue(cell, map.get(key.get(j)));
                    }
                }
            }
            List<String> path = getExcelPath(fileName, ".xls");
            FileOutputStream fileOut;
            try {
                fileOut = new FileOutputStream(path.get(0));
                wb.write(fileOut);
                fileOut.close();
                log.info("生成excel文件完成！");
                return path.get(1);
            } catch (Exception e) {
                throw new ServiceException(0, "生成" + sheetName + "数据异常" + e.getMessage());
            }
        }
        return null;
    }

    public List<String> getExcelPath(String fileName, String type) {
        List<String> liststr = new ArrayList<String>();
        StringBuffer sb = new StringBuffer();

        String excelPath = ConfigurationUtil.getString("path");
        String visitPath = ConfigurationUtil.getString("visitPath");
        sb.append("/report/").append(fileName).append(type);
        File file = new File(excelPath + sb.toString());
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        liststr.add(file.getPath());
        liststr.add(visitPath + sb.toString());
        return liststr;
    }

}
