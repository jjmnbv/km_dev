package com.pltfm.sys.action;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.sys.bean.ReportColumnSettingBean;
import com.pltfm.sys.bean.ReportMainBean;
import com.pltfm.sys.bean.ReportParamBean;
import com.pltfm.sys.model.ReportColumnSetting;
import com.pltfm.sys.model.ReportMain;
import com.pltfm.sys.model.ReportParam;
import com.pltfm.sys.model.SysUser;

/**
 * 类 ReportMainAction 报表Action类
 * 
 * @author
 * @version 2.1
 * @since JDK1.5
 */
@SuppressWarnings("unchecked")
public class ReportMainAction extends ActionSupport implements ModelDriven {
  private static final long serialVersionUID = 1L;
  Logger log = Logger.getLogger(this.getClass());
  ReportMain reportMain = new ReportMain(); // model
  String rtnMessage = "";

  List reportList = new ArrayList();
  List colsList = new ArrayList();
  List paramsList = new ArrayList();
  List resultList = new ArrayList(); // 报表查询列表
  String viewSql; // 预览的sql语句
  String colId;
  String paramId;
  String[] delId; // 删除操作ID
  String userName; // 用户名字
  ResultSet resultSet; // 查询结果
  String selectSql; // 增加报表时的select查询语句
  ReportColumnSetting col = new ReportColumnSetting();
  ReportParam param = new ReportParam();

  /**
   * 报表列表
   * 
   * @param
   * @return String
   * @exception Exception
   */
  public String listReportMain() throws Exception {
    log.warn("================ in ReportMainAction listReportMain()");
    try {
      ReportMainBean bean = new ReportMainBean();
      reportList = bean.getReportMainList(reportMain);
    } catch (Exception e) {
      log.error(e);
      log.warn(e.getMessage());
      return ERROR;
    }
    return SUCCESS;
  }

  /**
   * 报表修改页面数据
   * 
   * @param Integer
   * @return
   * @exception Exception
   */
  void getReportUpdatePageData(Integer reportId) throws Exception {
    // log.warn("================ in ReportMainAction getReportUpdatePageData()");
    try {
      ReportMainBean bean = new ReportMainBean();
      reportMain = bean.getReportMainDetail(reportId);

      // 得到要查询的所有列名
      ReportColumnSettingBean colBean = new ReportColumnSettingBean();
      colsList = colBean.getColsByReportId(reportId);

      // 得到查询语句中的所有参数
      ReportParamBean paramBean = new ReportParamBean();
      paramsList = paramBean.getParamsByReportId(reportId);
    } catch (Exception e) {
      log.error(e);
      log.warn(e.getMessage());
    }
  }

  /**
   * 组装Sql语句的数据
   * 
   * @param Integer
   * @return
   * @exception Exception
   */
  void getViewSqlData(Integer reportId) throws Exception {
    // log.warn("================ in ReportMainAction getViewSqlData()");
    try {
      ReportMainBean bean = new ReportMainBean();
      reportMain = bean.getReportMainDetail(reportId);
      // 得到要查询的所有列名
      ReportColumnSettingBean colBean = new ReportColumnSettingBean();
      colsList = colBean.getDisplayColsByReportId(reportId);
      // 得到查询语句中的所有参数
      ReportParamBean paramBean = new ReportParamBean();
      paramsList = paramBean.getDisplayParamsByReportId(reportId);
    } catch (Exception e) {
      log.error(e);
      log.warn(e.getMessage());
    }
  }

  /**
   * 新增报表
   * 
   * @param
   * @return String
   * @exception Exception
   */
  public String doSave() throws Exception {
    // log.warn("================ in ReportMainAction doSave() ");
    try {
      // 得到当前的操作用户
      Map session = ActionContext.getContext().getSession();
      SysUser su = (SysUser) session.get("sysUser");
      reportMain.setCreateUser(su.getUserId());

      // 新增报表记录
      ReportMainBean bean = new ReportMainBean();
      Integer reportId = bean.addReportMain(reportMain);
      this.setRtnMessage("addOk");

      // 根据sql查询语句自动生成显示列记录和条件配置记录
      if (selectSql != null && !"".equals(selectSql)) {
        String[] col = selectSql.split(",");
        ReportColumnSettingBean colsBean = new ReportColumnSettingBean();
        if (col != null && col.length != 0) {
          ReportColumnSetting colVo = new ReportColumnSetting();
          for (int i = 0; i < col.length; i++) {
            colVo.setReportId(reportId);
            colVo.setFieldName(col[i]);
            colVo.setColType("3");
            colVo.setColOrder(i + 1);
            colVo.setIsShow("1");
            colVo.setIsSum("1");
            colsBean.addCol(colVo);
          }
        }
      }
      // 报表修改页面的数据
      getReportUpdatePageData(reportId);
    } catch (Exception e) {
      log.error(e);
      log.warn(e.getMessage());
      return ERROR;
    }

    return SUCCESS;
  }

  /**
   * 新增报表显示列信息
   * 
   * @param
   * @return String
   * @exception Exception
   */
  public String doSaveCol() throws Exception {
    // log.warn("================ in ReportMainAction doSaveCol() ");
    // 得到当前的操作用户
    Map session = ActionContext.getContext().getSession();
    SysUser su = (SysUser) session.get("sysUser");
    col.setCreateUser(su.getUserId());
    try {
      // 新增报表显示列信息
      ReportColumnSettingBean colBean = new ReportColumnSettingBean();
      col.setReportId(reportMain.getReportId());
      colBean.addCol(col);
      setRtnMessage("addColOk");

      // 得到报表修改页面数据
      getReportUpdatePageData(reportMain.getReportId());
    } catch (Exception e) {
      log.error(e);
      log.warn(e.getMessage());
      return ERROR;
    }

    return SUCCESS;
  }

  /**
   * 新增报表参数信息
   * 
   * @param
   * @return String
   * @exception Exception
   */
  public String doSaveParam() throws Exception {
    // log.warn("================ in ReportMainAction doSaveParam() ");
    try {
      // 得到当前的操作用户
      Map session = ActionContext.getContext().getSession();
      SysUser su = (SysUser) session.get("sysUser");
      param.setCreateUser(su.getUserId());

      // 新增报表参数信息
      ReportParamBean paramBean = new ReportParamBean();
      param.setReportId(reportMain.getReportId());
      // Integer paramId = paramBean.addParam(param);
      paramBean.addParam(param);
      setRtnMessage("addParamOk");

      // 得到报表修改页面数据
      getReportUpdatePageData(reportMain.getReportId());
    } catch (Exception e) {
      log.error(e);
      log.warn(e.getMessage());
      return ERROR;
    }

    return SUCCESS;
  }

  /**
   * 报表的修改页面信息
   * 
   * @param
   * @return String
   * @exception Exception
   */
  public String getReportMainDetail() throws Exception {
    log.warn("================ in ReportMainAction getReportMainDetail()");
    try {
      getReportUpdatePageData(reportMain.getReportId());
    } catch (Exception e) {
      log.error(e);
      log.warn(e.getMessage());
      return ERROR;
    }

    return SUCCESS;
  }

  /**
   * 报表显示列的详细信息
   * 
   * @param
   * @return String
   * @exception Exception
   */
  public String getColDetail() throws Exception {
    log.warn("================ in ReportMainAction getColDeatail()");
    try {
      ReportColumnSettingBean colBean = new ReportColumnSettingBean();
      col = colBean.getColDetail(Integer.valueOf(colId));
    } catch (Exception e) {
      log.error(e);
      log.warn(e.getMessage());
      return ERROR;
    }

    return SUCCESS;
  }

  /**
   * 报表参数的详细信息
   * 
   * @param
   * @return String
   * @exception Exception
   */
  public String getParamDetail() throws Exception {
    log.warn("================ in ReportMainAction getParamDeatail()");
    try {
      ReportParamBean paramBean = new ReportParamBean();
      param = paramBean.getParamDetail(Integer.valueOf(paramId));
    } catch (Exception e) {
      log.error(e);
      log.warn(e.getMessage());
      return ERROR;
    }

    return SUCCESS;
  }

  /**
   * 新增报表
   * 
   * @param
   * @return String
   * @exception Exception
   */
  public String doUpdate() throws Exception {
    log.warn("================ in ReportMainAction doUpdate() ");
    try {
      // 得到当前操作用户
      Map session = ActionContext.getContext().getSession();
      SysUser su = (SysUser) session.get("sysUser");
      reportMain.setUpdateUser(su.getUserId());

      // 修改报表主信息
      ReportMainBean bean = new ReportMainBean();
      reportMain = bean.updateReportMain(reportMain);
      setRtnMessage("updateOk");
      reportMain = new ReportMain();
    } catch (Exception e) {
      log.error(e);
      log.warn(e.getMessage());
      return ERROR;
    }

    return listReportMain();
  }

  /**
   * 更新显示列信息
   * 
   * @param
   * @return String
   * @exception Exception
   */
  public String doUpdateCol() throws Exception {
    log.warn("================ in ReportMainAction doUpdateCol() ");

    try {
      // 组装col
      ReportColumnSettingBean colBean = new ReportColumnSettingBean();
      if (!"1".equals(col.getIsShow())) { // 是否显示
        col.setIsShow("");
      }
      if (!"1".equals(col.getIsSum())) { // 是否合计
        col.setIsSum("");
      }
      col = colBean.updateCol(col);
      setRtnMessage("updateColOk");
    } catch (Exception e) {
      log.error(e);
      log.warn(e.getMessage());
      return ERROR;
    }

    return SUCCESS;
  }

  /**
   * 更新参数列信息
   * 
   * @param
   * @return String
   * @exception Exception
   */
  public String doUpdateParam() throws Exception {
    log.warn("================ in ReportMainAction doUpdateParam() ");
    try {
      // 组装param
      ReportParamBean paramBean = new ReportParamBean();
      if (!"1".equals(param.getIsShow())) { // 是否显示
        param.setIsShow("");
      }
      if (!"1".equals(param.getIsMust())) { // 是否必须
        param.setIsMust("");
      }
      param = paramBean.updateParam(param);
      setRtnMessage("updateParamOk");
    } catch (Exception e) {
      log.error(e);
      log.warn(e.getMessage());
      return ERROR;
    }

    return SUCCESS;
  }

  /**
   * 删除报表信息
   * 
   * @param
   * @return String
   * @exception Exception
   */
  public String doDelete() throws Exception {
    log.warn("================ in ReportMainAction doDelete() ");
    try {
      ReportMainBean bean = new ReportMainBean();
      bean.deleteReportMain(delId);
    } catch (Exception e) {
      log.error(e);
      log.warn(e.getMessage());
      return ERROR;
    }

    return SUCCESS;
  }

  /**
   * 删除显示列信息
   * 
   * @param
   * @return String
   * @exception Exception
   */
  public String doDeleteCol() throws Exception {
    log.warn("================ in ReportMainAction doDeleteCol() ");
    try {
      ReportColumnSettingBean colBean = new ReportColumnSettingBean();
      colBean.deleteCol(Integer.valueOf(colId));

      // 得到修改页面数据
      getReportUpdatePageData(reportMain.getReportId());
    } catch (Exception e) {
      log.error(e);
      log.warn(e.getMessage());
      return ERROR;
    }

    return SUCCESS;
  }

  /**
   * 删除参数列信息
   * 
   * @param
   * @return String
   * @exception Exception
   */
  public String doDeleteParam() throws Exception {
    log.warn("================ in ReportMainAction doDeleteParam() ");
    try {
      ReportParamBean paramBean = new ReportParamBean();
      paramBean.deleteParam(Integer.valueOf(paramId));

      // 得到修改页面数据
      getReportUpdatePageData(reportMain.getReportId());
    } catch (Exception e) {
      log.error(e);
      log.warn(e.getMessage());
      return ERROR;
    }

    return SUCCESS;
  }

  /**
   * 预览sql语句
   * 
   * @param
   * @return String
   * @exception Exception
   */
  public String viewSql() throws Exception {
    log.warn("================ in ReportMainAction viewSql() ");

    try {
      // 得到修改页面数据
      getViewSqlData(reportMain.getReportId());

      // 组装需显示的sql语句
      StringBuffer colStr = new StringBuffer("");
      StringBuffer paramStr = new StringBuffer("");

      // 构造显示列语句
      for (int i = 0; i < colsList.size(); i++) {
        col = (ReportColumnSetting) colsList.get(i);
        if (i != colsList.size() - 1) {
          colStr.append(col.getFieldName()).append(",");
        } else {
          colStr.append(col.getFieldName());
        }
      }
      // 构造条件语句
      for (int j = 0; j < paramsList.size(); j++) {
        param = (ReportParam) paramsList.get(j);
        paramStr.append(param.getSubSentence()).append(" ");
      }

      viewSql =
          "select " + colStr + " from " + reportMain.getReportSqlFrom() + " where "
              + reportMain.getReportSqlWhere() + " " + paramStr;

      this.setRtnMessage("viewSql");
    } catch (Exception e) {
      log.error(e);
      log.warn(e.getMessage());
      return ERROR;
    }

    return SUCCESS;
  }

  /**
   * 进入报表查询页面
   * 
   * @param
   * @return String
   * @exception Exception
   */
  public String gotoReportQuery() throws Exception {
    log.warn("================ in ReportMainAction gotoReportQuery() ");

    try {
      Integer reportId = reportMain.getReportId();

      // 报表的详细信息
      ReportMainBean bean = new ReportMainBean();
      reportMain = bean.getReportMainDetail(reportId);

      // 报表的所有参数
      ReportParamBean paramBean = new ReportParamBean();
      paramsList = paramBean.getDisplayParamsByReportId(reportId);
      // paramsList = paramBean.getParamsByReportId(reportId);
    } catch (Exception e) {
      log.error(e);
      log.warn(e.getMessage());
      return ERROR;
    }

    return SUCCESS;
  }

  /**
   * 报表查询
   * 
   * @param
   * @return String
   * @exception Exception
   */
  public String reportQuery() throws Exception {
    log.warn("================ in ReportMainAction reportQuery() ");

    try {
      // 得到当前的操作用户,为打印中的制表人作准备
      Map session = ActionContext.getContext().getSession();
      SysUser su = (SysUser) session.get("sysUser");
      userName = su.getUserName();

      // 得到显示sql语句 数据

      getViewSqlData(reportMain.getReportId());

      // 组装需显示的sql语句
      StringBuffer colStr = new StringBuffer("");
      StringBuffer paramStr = new StringBuffer("");

      // 构造显示列语句
      for (int i = 0; i < colsList.size(); i++) {
        col = (ReportColumnSetting) colsList.get(i);
        if (i != colsList.size() - 1) {
          colStr.append(col.getFieldName()).append(",");
        } else {
          colStr.append(col.getFieldName());
        }
      }

      // 构造条件语句
      for (int j = 0; j < paramsList.size(); j++) {
        param = (ReportParam) paramsList.get(j);
        // 得到request对象
        ActionContext ctx = ActionContext.getContext();
        HttpServletRequest request =
            (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);

        String tempId = request.getParameter(String.valueOf(param.getParamId()));
        if (tempId != null && !"null".equals(tempId) && !"".equals(tempId)) {
          paramStr.append(param.getSubSentence().replaceAll("\\?", tempId)).append(" ");
        }
      }
      // 组装查询sql
      viewSql =
          "select " + colStr + " from " + reportMain.getReportSqlFrom() + " where "
              + reportMain.getReportSqlWhere() + " " + paramStr;

      // 执行sql语句并组装成list
      ReportMainBean bean = new ReportMainBean();
      resultList = bean.executeSql(viewSql);

    } catch (Exception e) {
      log.error(e);
      log.warn(e.getMessage());
      return ERROR;
    }
    return SUCCESS;
  }

  /**
   * 生成excel报表
   * 
   * @param
   * @return String
   * @exception Exception
   */
  public void generateExcel() throws Exception {

    log.warn("================ in ReportMainAction generateExcel() ");
    try {
      // 得到excel表格中的主体信息
      ReportMainBean bean = new ReportMainBean();
      resultList = bean.executeSql(viewSql);

      log.warn("--------------viewSql=" + viewSql);

      // 得到主表，列表，参数表的显示Sql数据
      getViewSqlData(reportMain.getReportId());

      log.warn("--------------reportId=" + reportMain.getReportId());

      List excelList = new ArrayList();
      Object[] tempRow = new Object[colsList.size()];

      // 组装excel表的标题
      if (colsList.size() > 0) {
        for (int i = 0; i < colsList.size(); i++) {
          ReportColumnSetting tempCol = (ReportColumnSetting) colsList.get(i);
          tempRow[i] = tempCol.getColNameCn();
        }
      }
      excelList.add(tempRow);
      log.warn("--------------111122222");

      Double[] count = new Double[colsList.size()]; // 用于保存合计的数据

      // 组装excel表的数据
      if (resultList != null && resultList.size() > 0) {
        for (int j = 0; j < resultList.size(); j++) { // 循环表格数据
          Object[] tempRowData = new Object[colsList.size()];
          Object[] obj = (Object[]) resultList.get(j);
          for (int k = 0; k < obj.length; k++) { // 各列数据
            Object val = obj[k];
            if (colsList.size() > 0) { // 判断列的类型
              ReportColumnSetting colVo = (ReportColumnSetting) colsList.get(k);
              if ("4".equals(colVo.getColType())) { // 如果是时间格式
                if (val != null) { // 如果为空
                  tempRowData[k] = new SimpleDateFormat("yyyy-MM-dd").format((Date) (val));
                } else {
                  tempRowData[k] = "";
                }
                continue;
              } else { // 如果是其它类型
                if (!"3".equals(colVo.getColType()) && "1".equals(colVo.getIsSum())) {
                  // 如果是数字并且需合计的字段
                  if (j == 0) {
                    if (val != null && !"".equals(val)) {
                      count[k] = Double.valueOf(val.toString());
                    } else {
                      count[k] = Double.valueOf("0.0");
                    }
                  } else {
                    if (val != null && !"".equals(val)) {
                      count[k] = Double.valueOf(val.toString());
                    }
                  }
                }
                if (val != null) {
                  tempRowData[k] = val.toString();
                } else {
                  tempRowData[k] = "";
                }
                continue;
              }
            }
          }
          excelList.add(tempRowData);
        }
      }

      log.warn("--------------11333");

      log.warn("--------------reportName=" + reportMain.getReportName());
      bean.listToExcel(excelList, reportMain.getReportName());

      log.warn("--------------11444");

    } catch (Exception e) {
      log.error(e);
      log.warn(e.getMessage());
    }
  }

  public String getRtnMessage() {
    return rtnMessage;
  }

  public void setRtnMessage(String rtnMessage) {
    this.rtnMessage = rtnMessage;
  }

  public ResultSet getResultSet() {
    return resultSet;
  }

  public void setResultSet(ResultSet resultSet) {
    this.resultSet = resultSet;
  }

  public List getColsList() {
    return colsList;
  }

  public void setColsList(List colsList) {
    this.colsList = colsList;
  }

  public List getParamsList() {
    return paramsList;
  }

  public void setParamsList(List paramsList) {
    this.paramsList = paramsList;
  }

  public List getReportList() {
    return reportList;
  }

  public void setReportList(List reportList) {
    this.reportList = reportList;
  }

  public String[] getDelId() {
    return delId;
  }

  public void setDelId(String[] delId) {
    this.delId = delId;
  }

  public ReportColumnSetting getCol() {
    return col;
  }

  public void setCol(ReportColumnSetting col) {
    this.col = col;
  }

  public ReportParam getParam() {
    return param;
  }

  public void setParam(ReportParam param) {
    this.param = param;
  }

  public String getColId() {
    return colId;
  }

  public void setColId(String colId) {
    this.colId = colId;
  }

  public String getParamId() {
    return paramId;
  }

  public void setParamId(String paramId) {
    this.paramId = paramId;
  }

  public String getViewSql() {
    return viewSql;
  }

  public void setViewSql(String viewSql) {
    this.viewSql = viewSql;
  }

  public List getResultList() {
    return resultList;
  }

  public void setResultList(List resultList) {
    this.resultList = resultList;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getSelectSql() {
    return selectSql;
  }

  public void setSelectSql(String selectSql) {
    this.selectSql = selectSql;
  }

  public Object getModel() {
    return reportMain;
  }

}
