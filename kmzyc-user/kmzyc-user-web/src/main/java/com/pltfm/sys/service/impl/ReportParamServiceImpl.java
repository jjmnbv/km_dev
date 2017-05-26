package com.pltfm.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pltfm.sys.dao.ReportParamDAO;
import com.pltfm.sys.model.ReportParam;
import com.pltfm.sys.model.ReportParamExample;
import com.pltfm.sys.service.ReportParamService;
import com.pltfm.sys.util.DatetimeUtil;

/**
 * 类 ReportParamBean 报表参数列
 *
 * @author
 * @version 2.1
 * @since JDK1.5
 */
@Service
public class ReportParamServiceImpl implements ReportParamService {
  @Resource(name = "reportParamDAO")
  public ReportParamDAO reportParamDAO;



  /**
   * 由报表主表ID得到查询语句中的所有参数
   * 
   * @param Integer
   * @return List
   * @exception Exception
   */
  @SuppressWarnings("rawtypes")
  public List getParamsByReportId(Integer reportId) throws Exception {
    ReportParamExample exam = new ReportParamExample();
    exam.createCriteria().andReportIdEqualTo(reportId).andIsEnableEqualTo("1");
    exam.setOrderByClause("param_order asc");
    return reportParamDAO.selectByExample(exam);
  }



  /**
   * 由报表主表ID得到所要显示所有参数
   * 
   * @param Integer
   * @return List
   * @exception Exception
   */
  @SuppressWarnings("rawtypes")
  public List getDisplayParamsByReportId(Integer reportId) throws Exception {
    ReportParamExample exam = new ReportParamExample();
    exam.createCriteria().andReportIdEqualTo(reportId).andIsShowEqualTo("1")
        .andIsEnableEqualTo("1");
    exam.setOrderByClause("param_order asc");
    return reportParamDAO.selectByExample(exam);
  }



  /**
   * 新增
   * 
   * @param ReportParam
   * @return Integer
   * @exception Exception
   */
  public Integer addParam(ReportParam paramVo) throws Exception {
    paramVo.setIsEnable("1");
    paramVo.setCreateDate(DatetimeUtil.getCalendarInstance().getTime());
    reportParamDAO.insert(paramVo);
    return paramVo.getParamId();
  }



  /**
   * 详细信息
   * 
   * @param Integer
   * @return ReportParam
   * @exception Exception
   */
  public ReportParam getParamDetail(Integer id) throws Exception {
    ReportParam paramVo = null;
    paramVo = reportParamDAO.selectByPrimaryKey(id);
    return paramVo;
  }



  /**
   * 修改
   * 
   * @param ReportParam
   * @return ReportParam
   * @exception Exception
   */
  public ReportParam updateParam(ReportParam paramVo) throws Exception {
    paramVo.setUpdateDate(DatetimeUtil.getCalendarInstance().getTime());
    reportParamDAO.updateByPrimaryKeySelective(paramVo);
    return paramVo;
  }



  /**
   * 删除
   * 
   * @param Integer
   * @return
   * @exception Exception
   */
  public void deleteParam(Integer paramId) throws Exception {
    ReportParam paramVo = reportParamDAO.selectByPrimaryKey(paramId);
    paramVo.setIsEnable("0");
    reportParamDAO.updateByPrimaryKeySelective(paramVo);
  }
}
