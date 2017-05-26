package com.pltfm.sys.bean;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.pltfm.sys.dao.ReportMainDAOImpl;
import com.pltfm.sys.model.ReportMain;
import com.pltfm.sys.model.ReportMainExample;
import com.pltfm.sys.util.DBConnection;
import com.pltfm.sys.util.DatetimeUtil;
import com.pltfm.sys.util.StaticRealPath;

/**
 * 类 ReportMainBean 报表主类
 *
 * @author
 * @version 2.1
 * @since JDK1.5
 */
@SuppressWarnings("unchecked")
public class ReportMainBean extends BaseBean {

    Logger log = Logger.getLogger(this.getClass());

    public ReportMainBean() {
        // 总的sqlmap配置文件
        super();
    }

    /**
     * 根据VO查询报表列表
     *
     * @return List
     */

    public List getReportMainList(ReportMain vo) throws Exception {
        log.warn("----------------------getReportMainList");
        List dataList = new ArrayList();
        try {
            ReportMainDAOImpl dao = new ReportMainDAOImpl(sqlMap);
            ReportMainExample exam = new ReportMainExample();
            // 组合查询条件
            String reportName = vo.getReportName();
            if (reportName != null && !reportName.equals("")) {
                reportName = "%" + reportName + "%";
            } else {
                reportName = "%%";
            }
            exam.createCriteria().andReportNameLike(reportName).andIsEnableEqualTo("1");
            exam.setOrderByClause("report_no asc");
            dataList = dao.selectByExample(exam);
        } catch (SQLException e) {
            log.error(e);
            log.warn(e.getMessage());
            throw e;
        }
        return dataList;
    }

    /**
     * 新增
     *
     * @return Integer
     */

    public Integer addReportMain(ReportMain vo) throws Exception {
        try {
            ReportMainDAOImpl dao = new ReportMainDAOImpl(sqlMap);
            vo.setCreateDate(DatetimeUtil.getCalendarInstance().getTime());
            dao.insert(vo);
        } catch (SQLException e) {
            log.error(e);
            log.warn(e.getMessage());
            throw e;
        }
        return vo.getReportId();
    }

    /**
     * 详细信息
     *
     * @return ReportMain
     */

    public ReportMain getReportMainDetail(Integer id) throws Exception {
        ReportMain reportMain = null;
        try {
            ReportMainDAOImpl dao = new ReportMainDAOImpl(sqlMap);
            reportMain = dao.selectByPrimaryKey(id);
        } catch (SQLException e) {
            log.error(e);
            log.warn(e.getMessage());
            throw e;
        }
        return reportMain;
    }

    /**
     * 修改
     *
     * @return ReportMain
     */
    public ReportMain updateReportMain(ReportMain reportMain) throws Exception {
        try {
            ReportMainDAOImpl dao = new ReportMainDAOImpl(sqlMap);
            reportMain.setUpdateDate(DatetimeUtil.getCalendarInstance().getTime());
            dao.updateByPrimaryKeySelective(reportMain);
        } catch (SQLException e) {
            log.error(e);
            log.warn(e.getMessage());
            throw e;
        }
        return reportMain;
    }

    /**
     * 删除
     *
     * @param String []
     */
    public void deleteReportMain(String[] reportId) throws Exception {
        try {
            ReportMainDAOImpl dao = new ReportMainDAOImpl(sqlMap);
            ReportMain reportMain = new ReportMain();
            log.warn("-----------bean reportId.length=" + reportId.length);
            // 开始循环删除
            if (reportId.length > 0) {
                for (int i = 0; i < reportId.length; i++) {
                    log.warn("--------reportId[" + i + "]=" + reportId[i]);
                    reportMain = dao.selectByPrimaryKey(Integer.valueOf(reportId[i]));
                    reportMain.setIsEnable("0");
                    dao.updateByPrimaryKeySelective(reportMain);
                }
            }
        } catch (SQLException e) {
            log.error(e);
            log.warn(e.getMessage());
            throw e;
        }
    }

    /**
     * 执行SQL语句
     *
     * @return List
     */
    public List executeSql(String sql) throws Exception {
        log.warn("-----------ReportMainBean executeSql();");
        // 定义变量
        List list = new ArrayList();
        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            // 建立连接并执行sql语句
            log.warn("----------begin");
            conn = DBConnection.getConnection();
            statement = conn.createStatement();
            rs = statement.executeQuery(sql);

            log.warn("----------sql=" + sql);
            // 查询结果数
            int columnCount = rs.getMetaData().getColumnCount();

            log.warn("-----------columnCount=" + columnCount);

            // 把ResultSet结果集内容放入到list列表中
            while (rs.next()) {
                Object[] tempObj = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    tempObj[i - 1] = rs.getObject(i);
                    log.warn("---------tempObj[" + (i - 1) + "]=" + rs.getObject(i));
                }
                list.add(tempObj);
            }

        } catch (Exception e) {
            log.error(e);
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    log.error(e);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    log.error(e);
                }
            }
        }
        return list;
    }

    /**
     * 数据放入Excel表格中
     */
    public void listToExcel(List excelList, String reportName) throws Exception {
        log.warn("================ in ReportMainBean listToExcel() ");
        try {
            /***** 初始化工作 ***********/
            WritableWorkbook wwb = null; // 写入的Excel工作薄
            FileInputStream fis = null; // 一个文件流
            ActionContext ctx = ActionContext.getContext();
            HttpServletResponse response =
                    (HttpServletResponse) ctx.get(ServletActionContext.HTTP_RESPONSE);
            ServletOutputStream sos = response.getOutputStream(); // 一个输出流
            String fileName = reportName + ".xls"; // 生成的excel文件名
            Label tempCell = null; // 临时用的excel单元格
            String filePath = StaticRealPath.getWebAppPath().split("/")[0] + "\\excel\\"; // excel路径

            /******** 创建可写入的Excel工作薄和工作表 ********/
            log.warn("---------------url=" + filePath + fileName);
            File file = new File(filePath);
            // File file = new File(filePath+fileName);
            if (!file.exists()) {
                if (!file.mkdir()) {
                    log.warn("-------创建目录失败！");
                    throw new Exception("目录不存在，创建失败！");
                }
            }
            file = new File(filePath + fileName);
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    log.warn("----------文件创建失败");
                    throw new Exception("文件不存在，创建失败！");
                }
            }
            wwb = Workbook.createWorkbook(file);
            WritableSheet ws = wwb.createSheet("Sheet1", 0);
            /******* 各种数据的格式 *******/
            // 大标题的格式
            WritableFont wfTitle = new WritableFont(WritableFont.createFont("宋体"), 20, WritableFont.BOLD); // 设置字体
            WritableCellFormat wcfTitle = new WritableCellFormat(wfTitle);
            wcfTitle.setBorder(Border.ALL, BorderLineStyle.THIN); // 设置边框
            wcfTitle.setWrap(true); // 设置自适应
            wcfTitle.setAlignment(jxl.format.Alignment.CENTRE); // 设置左右居中
            wcfTitle.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); // 设置上下居中
            // 小标题的格式
            WritableFont wfTitle2 =
                    new WritableFont(WritableFont.createFont("宋体"), 10, WritableFont.BOLD);
            WritableCellFormat wcfTitle2 = new WritableCellFormat(wfTitle2);
            wcfTitle2.setWrap(true);
            wcfTitle2.setBorder(Border.ALL, BorderLineStyle.THIN);
            wcfTitle2.setAlignment(jxl.format.Alignment.CENTRE);
            wcfTitle2.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
            wcfTitle2.setBackground(Colour.GRAY_25); // 设置背景色
            // 数据项的格式
            WritableFont wfTitle3 = new WritableFont(WritableFont.createFont("宋体"), 10);
            WritableCellFormat wcfTitle3 = new WritableCellFormat(wfTitle3);
            wcfTitle3.setBorder(Border.ALL, BorderLineStyle.THIN);
            wcfTitle3.setWrap(true);
            wcfTitle3.setAlignment(jxl.format.Alignment.CENTRE);
            wcfTitle3.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
            if (excelList.size() > 0) {
                // 加入主标题
                int cols = ((Object[]) excelList.get(0)).length;
                ws.mergeCells(0, 0, cols, 0); // 合并单元格
                tempCell = new Label(0, 0, reportName, wcfTitle); // 新建一个有数据的单元格
                ws.addCell(tempCell);

                for (int i = 0; i < excelList.size(); i++) { // 第i行
                    Object[] cell = (Object[]) excelList.get(i); // 第i行数据
                    /***** 加入序列号并设置单元格的高度和宽度 ****/
                    tempCell = new Label(0, i + 1, String.valueOf(i + 1), wcfTitle3);
                    ws.addCell(tempCell); // 增加序号单元格
                    ws.setRowView(i + 1, 800);
                    ws.setColumnView(0, 12);
                    /************ 向excel表中加入其它的数据 **********/
                    for (int j = 0; j < cell.length; j++) { // 第j列
                        String cellIJ = "";
                        if (cell[j] != null) {
                            cellIJ = cell[j].toString();
                        }
                        /******* 加入数据并设置单元格的高宽度 *********/
                        tempCell = new Label(j + 1, i + 1, cellIJ, wcfTitle3);
                        ws.addCell(tempCell);
                        ws.setRowView(i + 1, 800);
                        ws.setColumnView(j + 1, 12);
                    }
                }
            }

            // 用于另存为的操作
            wwb.write();
            wwb.close();
            /**** 设置response ******/
            response.reset();
            response.setHeader("pragma", "no-cache");
            response.setHeader("Content-Disposition", "attachment; filename="
                    + new String(fileName.getBytes("GBK"), "ISO8859_1"));
            response.setContentType("application/*");
            /*** 把文件中的数据读到sos输出流中 *****/
            if (file != null) {
                fis = new FileInputStream(file);
                byte[] b = new byte[1024];
                while (fis.read(b) != -1) {
                    sos.write(b);
                }
                sos.flush();
                sos.close();
                fis.close();
            }
        } catch (Exception e) {
            log.error(e);
            throw e;
        }

    }

}
