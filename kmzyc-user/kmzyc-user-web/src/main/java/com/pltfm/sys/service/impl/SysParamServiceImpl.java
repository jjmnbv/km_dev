package com.pltfm.sys.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.sys.dao.SysParamDAO;
import com.pltfm.sys.model.SysParamExample;
import com.pltfm.sys.service.SysParamService;

@Component(value = "sysParamService")
public class SysParamServiceImpl implements SysParamService {

  Logger log = LoggerFactory.getLogger(this.getClass());

  @Resource(name = "sqlMapClient")
  /**
   * 数据库操作对象
   */
  private SqlMapClient sqlMapClient;
  @Resource(name = "sysParamDAO")
  private SysParamDAO sysParamDAO;


  /**
   * 根据平台编号和组名称查询该组中的所有参数 List (SysParam)
   * 
   * @param String,String
   * @return List
   * @exception Exception
   */
  @Override
  public List getSysParamList(String sysCd, String paramGp) throws Exception {
    List paramList = null;
    // log.warn("===================Go into SysParamBean.getSysParamList(sysCd,paramGp)");
    try {
      SysParamExample exam = new SysParamExample(); // 组装where查询条件的对象
      exam.createCriteria().andSysCdEqualTo(sysCd).andParamGpEqualTo(paramGp);
      exam.setOrderByClause("param_value asc");
      paramList = sysParamDAO.selectByExample(exam); // 获取SysParam对象集合
    } catch (SQLException e) {
      e.printStackTrace();
      log.warn(e.getMessage());
      throw e;
    }
    return paramList;
  }

}
