package com.pltfm.app.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.commons.exception.ActionException;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.opensymphony.xwork2.ActionContext;
import com.pltfm.app.entities.OrderAssessDetail;
import com.pltfm.app.entities.OrderAssessInfo;
import com.pltfm.app.service.OrderAssessService;
import com.pltfm.sys.model.SysUser;

@SuppressWarnings("unchecked")
@Controller("importExcelOrderAssessAction")
@Scope("prototype")
public class ImportExcelOrderAssessAction extends BaseAction {
  private static final long serialVersionUID = -6242987825376233501L;
  Logger log = Logger.getLogger(ImportExcelOrderAssessAction.class);
  private File doc;
  private String docFileName;
  /*
   * excel订单导入评分
   */

  @Resource
  private OrderAssessService orderAssessService;

  private final String assessTypeOne = "宝贝描述相符";
  private final String assessTypeTwo = "卖家发货速度";
  private final String assessTypeThree = "物流配送速度";
  private final String assessTypeFour = "售前售后服务";

  private final String assess_Type_One = "Assess_Type_one";
  private final String assess_Type_Two = "Assess_Type_two";
  private final String assess_Type_Three = "Assess_Type_three";
  private final String assess_Type_Four = "Assess_Type_four";

  /**
   * 转向订单查询页面
   * 
   * @return
   * @throws ActionException
   */
  public String index() throws ActionException {
    return "index";
  }

  /**
   * 文件上传
   * 
   * @return
   * @throws ActionException
   */
  public String upload() throws ActionException {
    // 指定文件上传路径
    String realPath =
        ConfigurationUtil.getString("path")
            + File.separator + "temp" + File.separator;
    HttpServletRequest request = ServletActionContext.getRequest();
    HttpServletResponse response = ServletActionContext.getResponse();
    // 指定上传的文件
    File path = new File(realPath);
    if (!path.exists()) {
      path.mkdirs();
    }
    File excelFile = new File(realPath, docFileName);
    final String info = "success!";
    String url = "/app/orderAssess/indexOrderAssess.action";
    try {
      // 文件上传
      FileUtils.copyFile(doc, excelFile);
      // 读取excel文件内容，并存储到list
      List<Object> oaList = readExcel(realPath, docFileName);
      // 执行批量插入数据操作
      orderAssessService.bathInsertOrderAssess(oaList);
      // 文件上传完成，删除上传的 文件
      excelFile.delete();
      // 操作成功提示
      printOut(request, response, info, url);
    } catch (Exception e) {
      log.error("excel导入异常！", e);
      // printOut(request, response, errorInfo, url);
    }
    return null;
  }

  /**
   * 读取excel文件内容
   * 
   * @param fileName
   * @return
   */
  public List<Object> readExcel(String realPath, String fileName) {
    // 定义excel工作簿
    Workbook workbook;
    // 定义存储评分明细的集合
    List<OrderAssessDetail> assessList = new ArrayList<OrderAssessDetail>();
    // 定义存储单个订单的总评分的集合
    List<OrderAssessInfo> infoList = new ArrayList<OrderAssessInfo>();
    List<Object> assList = new ArrayList<Object>();
    // 获取登陆用户名
    Map session = ActionContext.getContext().getSession();
    SysUser sysuser = (SysUser) session.get("sysUser");
    // 根据.xlsx后缀创建excel工作簿
    try {
      if (fileName.indexOf(".xlsx") > -1) {
        workbook = new XSSFWorkbook(new FileInputStream(realPath + fileName));
      } else {
        workbook = new HSSFWorkbook(new FileInputStream(realPath + fileName));
      }
      // 获取工作簿的各个工作表总数
      int sheetNum = workbook.getNumberOfSheets();
      String orderCode = null;
      // 初始化评分明细
      OrderAssessDetail orderAssessDetail = new OrderAssessDetail();
      // 初始化总评分
      OrderAssessInfo orderAssessInfo = new OrderAssessInfo();
      // 定义评分明细变量
      int scoreTmp = 0;
      for (int i = 0; i < sheetNum; i++) {
        // 获取工作表
        Sheet sheet = workbook.getSheetAt(i);
        if (sheet != null) {
          for (int j = 1; j < sheet.getLastRowNum() + 1; j++) {
            // 获取行
            Row row = sheet.getRow(j);
            if (row != null) {
              String value = "";
              if (orderAssessInfo != null) {
                Cell cell = row.getCell(0);
                String value2 = null;
                if (cell != null) {
                  value2 = formatCell(cell, value2);
                }
                if (!StringUtils.isBlank(value2)) {
                  if (!value2.equals(orderCode)) {
                    if (j != 1) {
                      orderAssessInfo.setGuestNum(sysuser.getUserName());
                      orderAssessInfo.setAssessMark(Long.valueOf(scoreTmp));
                      orderAssessInfo.setCreateDate(new Date());
                      infoList.add(orderAssessInfo);
                      orderAssessInfo = new OrderAssessInfo();
                      scoreTmp = 0;
                    }
                  }
                }

                if (j == sheet.getLastRowNum() - 3) {
                  orderAssessInfo.setGuestNum(sysuser.getUserName());
                  orderAssessInfo.setAssessMark(Long.valueOf(scoreTmp));
                  orderAssessInfo.setCreateDate(new Date());
                  infoList.add(orderAssessInfo);
                }
              }
              for (int k = 0; k < row.getLastCellNum(); k++) {
                Cell cell = row.getCell(k);
                // 格式化单元格数据
                value = formatCell(cell, value);
                switch (k) {
                  case 0:
                    // 设置订单号
                    if (StringUtils.isBlank(value)) {
                      orderAssessDetail.setOrderCode(orderCode);
                    } else {
                      orderCode = value;
                      orderAssessDetail.setOrderCode(value);
                      orderAssessInfo.setOrderCode(orderCode);
                      orderAssessInfo.setCreateDate(new Date());
                    }
                    break;
                  case 6:
                    // 设置评分内容
                    if (!StringUtils.isBlank(value)) {
                      orderAssessInfo.setAssessContext(value);
                    }
                    break;
                  case 7:
                    // 设置评分
                    if (value.trim().equals(assessTypeOne)) {
                      orderAssessDetail.setAssessType(assess_Type_One);
                    } else if (value.trim().equals(assessTypeTwo)) {
                      orderAssessDetail.setAssessType(assess_Type_Two);
                    } else if (value.trim().equals(assessTypeThree)) {
                      orderAssessDetail.setAssessType(assess_Type_Three);
                    } else if (value.trim().equals(assessTypeFour)) {
                      orderAssessDetail.setAssessType(assess_Type_Four);
                    }
                    orderAssessDetail.setAssessName(value);
                    break;
                  case 8:
                    orderAssessDetail.setAssessScore(Integer.valueOf(value));
                    scoreTmp += Integer.valueOf(value);
                    break;
                }
              }
              orderAssessDetail.setCreateDate(new Date());
              assessList.add(orderAssessDetail);
              orderAssessDetail = new OrderAssessDetail();
            }
          }
        }
      }
      assList.add(infoList);
      assList.add(assessList);
    } catch (FileNotFoundException e) {
      log.error("Excel文件未找到!", e);
    } catch (IOException e) {
      log.error("文件传输异常", e);
    } catch (Exception e) {
      log.error("异常", e);
    }

    return assList;
  }

  public void printOut(HttpServletRequest request, HttpServletResponse response, String str,
      String url) throws Exception {
    PrintWriter out = response.getWriter();
    String basePath =
        request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + request.getContextPath();
    try {
      StringBuffer sb = new StringBuffer();
      sb.append("<script type='text/javascript'>").append("alert('").append(str).append("');")
          .append("window.location.href='").append(basePath).append(url).append("';");
      sb.append("</script>");
      out.print(sb.toString());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      out.close();
    }
  }

  public String formatCell(Cell cell, String value) {
    switch (cell.getCellType()) {
      case HSSFCell.CELL_TYPE_NUMERIC: // 数值型
        if (HSSFDateUtil.isCellDateFormatted(cell)) {
          // 如果是Date类型则 ，获取该Cell的Date值
          value = HSSFDateUtil.getJavaDate(cell.getNumericCellValue()).toString();
        } else {// 纯数字，这里要判断是否为小数的情况，因为整数在写入时会被加上小数点
          String t = cell.getNumericCellValue() + "";
          BigDecimal n = new BigDecimal(cell.getNumericCellValue());
          // 判断是否有小数点
          if (t.indexOf(".") < 0) {
            value = n.intValue() + "";
          } else {
            // 数字格式化对象
            NumberFormat nf = NumberFormat.getInstance();
            // 小数点最大两位
            nf.setMaximumFractionDigits(2);
            // 执行格式化
            // value = nf.format(n.doubleValue());
            value = String.valueOf(n);
          }
        }
        break;
      case HSSFCell.CELL_TYPE_STRING: // 字符串型
        value = cell.getRichStringCellValue().toString();
        break;
      case HSSFCell.CELL_TYPE_FORMULA:// 公式型
        // 读公式计算值
        value = String.valueOf(cell.getNumericCellValue());
        break;
      case HSSFCell.CELL_TYPE_BOOLEAN:// 布尔
        value = " " + cell.getBooleanCellValue();
        break;
      /* 此行表示该单元格值为空 */
      case HSSFCell.CELL_TYPE_BLANK: // 空值
        value = " ";
        break;
      case HSSFCell.CELL_TYPE_ERROR: // 故障
        value = " ";
        break;
      default:
        value = cell.getRichStringCellValue().toString();
    }
    return value;
  }

  public File getDoc() {
    return doc;
  }

  public void setDoc(File doc) {
    this.doc = doc;
  }

  public String getDocFileName() {
    return docFileName;
  }

  public void setDocFileName(String docFileName) {
    this.docFileName = docFileName;
  }

}
