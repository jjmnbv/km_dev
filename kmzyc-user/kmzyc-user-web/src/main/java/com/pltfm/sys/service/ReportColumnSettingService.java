package com.pltfm.sys.service;

import java.util.List;

import com.pltfm.sys.model.ReportColumnSetting;

public interface ReportColumnSettingService {


  /**
   * 由报表主表ID得到得到要显示的列名
   * 
   * @param Integer
   * @return List
   * @exception Exception
   */
  @SuppressWarnings("rawtypes")
  public List getColsByReportId(Integer reportId) throws Exception;



  /**
   * 由报表主表ID得到需要显示的列名
   * 
   * @param Integer
   * @return List
   * @exception Exception
   */
  @SuppressWarnings("rawtypes")
  public List getDisplayColsByReportId(Integer reportId) throws Exception;


  /**
   * 新增
   * 
   * @param ReportColumnSetting
   * @return Integer
   * @exception Exception
   */
  public Integer addCol(ReportColumnSetting colVo) throws Exception;



  /**
   * 详细信息
   * 
   * @param Integer
   * @return ReportColumnSetting
   * @exception Exception
   */
  public ReportColumnSetting getColDetail(Integer id) throws Exception;



  /**
   * 修改
   * 
   * @param ReportColumnSetting
   * @return ReportColumnSetting
   * @exception Exception
   */
  public ReportColumnSetting updateCol(ReportColumnSetting colVo) throws Exception;


  /**
   * 删除
   * 
   * @param Integer
   * @return
   * @exception Exception
   */
  public void deleteCol(Integer colId) throws Exception;

}
