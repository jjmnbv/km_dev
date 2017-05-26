package com.pltfm.sys.bean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.pltfm.sys.dao.ReportColumnSettingDAOImpl;
import com.pltfm.sys.model.ReportColumnSetting;
import com.pltfm.sys.model.ReportColumnSettingExample;
import com.pltfm.sys.util.DatetimeUtil;

/**
 * 类 ReportColumnSettingBean 报表显示列
 * 
 * @author
 * @version 2.1
 * @since JDK1.5
 */
@SuppressWarnings("unchecked")
public class ReportColumnSettingBean extends BaseBean {

  Logger log = Logger.getLogger(this.getClass());

  public ReportColumnSettingBean() {
    // 总的sqlmap配置文件
    super();
  }

  /**
   * 由报表主表ID得到得到要显示的列名
   * 
   * @param Integer
   * @return List
   * @exception Exception
   */
  public List getColsByReportId(Integer reportId) throws Exception {
    List colsList = new ArrayList();
    try {
      ReportColumnSettingDAOImpl dao = new ReportColumnSettingDAOImpl(sqlMap);
      ReportColumnSettingExample exam = new ReportColumnSettingExample();
      exam.createCriteria().andReportIdEqualTo(reportId).andIsEnableEqualTo("1");
      exam.setOrderByClause("col_order asc");
      colsList = dao.selectByExample(exam);
    } catch (SQLException e) {
      log.error(e);
      log.warn(e.getMessage());
      throw e;
    }
    return colsList;
  }

  /**
   * 由报表主表ID得到需要显示的列名
   * 
   * @param Integer
   * @return List
   * @exception Exception
   */
  public List getDisplayColsByReportId(Integer reportId) throws Exception {
    List colsList = new ArrayList();
    try {
      ReportColumnSettingDAOImpl dao = new ReportColumnSettingDAOImpl(sqlMap);
      ReportColumnSettingExample exam = new ReportColumnSettingExample();
      exam.createCriteria().andReportIdEqualTo(reportId).andIsEnableEqualTo("1").andIsShowEqualTo(
          "1");
      exam.setOrderByClause("col_order asc");
      colsList = dao.selectByExample(exam);
    } catch (SQLException e) {
      log.error(e);
      log.warn(e.getMessage());
      throw e;
    }
    return colsList;
  }

  /**
   * 新增
   * 
   * @param ReportColumnSetting
   * @return Integer
   * @exception Exception
   */
  public Integer addCol(ReportColumnSetting colVo) throws Exception {
    try {
      ReportColumnSettingDAOImpl colDao = new ReportColumnSettingDAOImpl(sqlMap);
      colVo.setIsEnable("1");
      colVo.setCreateDate(DatetimeUtil.getCalendarInstance().getTime());
      colDao.insert(colVo);
    } catch (SQLException e) {
      log.error(e);
      log.warn(e.getMessage());
      throw e;
    }
    return colVo.getColId();
  }

  /**
   * 详细信息
   * 
   * @param Integer
   * @return ReportColumnSetting
   * @exception Exception
   */
  public ReportColumnSetting getColDetail(Integer id) throws Exception {
    ReportColumnSetting colVo = null;
    try {
      ReportColumnSettingDAOImpl dao = new ReportColumnSettingDAOImpl(sqlMap);
      colVo = dao.selectByPrimaryKey(id);
      sqlMap.commitTransaction();
    } catch (SQLException e) {
      log.error(e);
      log.warn(e.getMessage());
      throw e;
    }
    return colVo;
  }

  /**
   * 修改
   * 
   * @param ReportColumnSetting
   * @return ReportColumnSetting
   * @exception Exception
   */
  public ReportColumnSetting updateCol(ReportColumnSetting colVo) throws Exception {
    try {
      ReportColumnSettingDAOImpl dao = new ReportColumnSettingDAOImpl(sqlMap);
      colVo.setUpdateDate(DatetimeUtil.getCalendarInstance().getTime());
      dao.updateByPrimaryKeySelective(colVo);
    } catch (SQLException e) {
      log.error(e);
      log.warn(e.getMessage());
      throw e;
    }
    return colVo;
  }

  /**
   * 删除
   * 
   * @param Integer
   * @return
   * @exception Exception
   */
  public void deleteCol(Integer colId) throws Exception {
    try {
      ReportColumnSettingDAOImpl dao = new ReportColumnSettingDAOImpl(sqlMap);
      ReportColumnSetting colVo = new ReportColumnSetting();
      colVo = dao.selectByPrimaryKey(colId);
      colVo.setIsEnable("0");
      dao.updateByPrimaryKeySelective(colVo);
    } catch (SQLException e) {
      log.error(e);
      log.warn(e.getMessage());
      throw e;
    }
  }

}
