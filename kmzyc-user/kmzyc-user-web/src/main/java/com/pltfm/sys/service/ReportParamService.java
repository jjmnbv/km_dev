package com.pltfm.sys.service;

import java.util.List;

import com.pltfm.sys.model.ReportParam;

/**
 * 类 ReportParamBean 报表参数列
 *
 * @author
 * @version 2.1
 * @since JDK1.5
 */
public interface ReportParamService {



  /**
   * 由报表主表ID得到查询语句中的所有参数
   * 
   * @param Integer
   * @return List
   * @exception Exception
   */
  @SuppressWarnings("rawtypes")
  public List getParamsByReportId(Integer reportId) throws Exception;



  /**
   * 由报表主表ID得到所要显示所有参数
   * 
   * @param Integer
   * @return List
   * @exception Exception
   */
  @SuppressWarnings("rawtypes")
  public List getDisplayParamsByReportId(Integer reportId) throws Exception;



  /**
   * 新增
   * 
   * @param ReportParam
   * @return Integer
   * @exception Exception
   */
  public Integer addParam(ReportParam paramVo) throws Exception;



  /**
   * 详细信息
   * 
   * @param Integer
   * @return ReportParam
   * @exception Exception
   */
  public ReportParam getParamDetail(Integer id) throws Exception;



  /**
   * 修改
   * 
   * @param ReportParam
   * @return ReportParam
   * @exception Exception
   */
  public ReportParam updateParam(ReportParam paramVo) throws Exception;



  /**
   * 删除
   * 
   * @param Integer
   * @return
   * @exception Exception
   */
  public void deleteParam(Integer paramId) throws Exception;
}
