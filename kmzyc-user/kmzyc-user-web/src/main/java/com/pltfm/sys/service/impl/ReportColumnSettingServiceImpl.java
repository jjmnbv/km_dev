package com.pltfm.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pltfm.sys.dao.ReportColumnSettingDAO;
import com.pltfm.sys.model.ReportColumnSetting;
import com.pltfm.sys.model.ReportColumnSettingExample;
import com.pltfm.sys.service.ReportColumnSettingService;
import com.pltfm.sys.util.DatetimeUtil;

@Service(value = "reportColumnSettingServiceImpl")
public class ReportColumnSettingServiceImpl implements ReportColumnSettingService {

  @Resource(name = "reportColumnSettingDAO")
  public ReportColumnSettingDAO reportColumnSettingDAO;

  /**
   * 由报表主表ID得到得到要显示的列名
   * 
   * @param Integer
   * @return List
   * @exception Exception
   */
  @SuppressWarnings("rawtypes")
  public List getColsByReportId(Integer reportId) throws Exception {
    ReportColumnSettingExample exam = new ReportColumnSettingExample();
    exam.createCriteria().andReportIdEqualTo(reportId).andIsEnableEqualTo("1");
    exam.setOrderByClause("col_order asc");
    return (List) reportColumnSettingDAO.selectByExample(exam);
  }



  /**
   * 由报表主表ID得到需要显示的列名
   * 
   * @param Integer
   * @return List
   * @exception Exception
   */
  @SuppressWarnings("rawtypes")
  public List getDisplayColsByReportId(Integer reportId) throws Exception {
    ReportColumnSettingExample exam = new ReportColumnSettingExample();
    exam.createCriteria().andReportIdEqualTo(reportId).andIsEnableEqualTo("1")
        .andIsShowEqualTo("1");
    exam.setOrderByClause("col_order asc");
    return reportColumnSettingDAO.selectByExample(exam);
  }



  /**
   * 新增
   * 
   * @param colVo
   * @return Integer
   * @exception Exception
   */
  public Integer addCol(ReportColumnSetting colVo) throws Exception {
    colVo.setIsEnable("1");
    colVo.setCreateDate(DatetimeUtil.getCalendarInstance().getTime());
    reportColumnSettingDAO.insert(colVo);
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
    colVo = reportColumnSettingDAO.selectByPrimaryKey(id);
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
    colVo.setUpdateDate(DatetimeUtil.getCalendarInstance().getTime());
    reportColumnSettingDAO.updateByPrimaryKeySelective(colVo);
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
    ReportColumnSetting colVo = reportColumnSettingDAO.selectByPrimaryKey(colId);
    colVo.setIsEnable("0");
    reportColumnSettingDAO.updateByPrimaryKeySelective(colVo);
  }

}
