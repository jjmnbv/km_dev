package com.pltfm.app.dao;

import com.pltfm.app.vobject.NewsCustomerSurvey;

import java.sql.SQLException;
import java.util.List;

/***
 * 
 * 调查记录DAO接口
 */
public interface NewsCustomerSurveyDAO {
  /***
   * 
   * 删除调查记录
   */
  int deleteByExample(NewsCustomerSurvey example) throws SQLException;


  /***
   * 
   * 添加调查记录
   */
  Integer insert(NewsCustomerSurvey record) throws SQLException;

  /***
   * 
   * 跟据id查询调查记录
   */
  NewsCustomerSurvey selectByPrimaryKey(Integer customerSurveyId) throws SQLException;

  /***
   * 
   * 修改调查记录
   */
  Integer updateByPrimaryKeySelective(NewsCustomerSurvey record) throws SQLException;

  /**
   * 按条件查询调查信息总数量
   * 
   * @param vo
   * @return 返回值
   */
  Integer selectCountByVo(NewsCustomerSurvey vo) throws SQLException;

  /**
   * 根据vo条件查询分类信息page
   * 
   * @param page 分页类
   * @param vo 信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */

  public List selectPageByVo(NewsCustomerSurvey vo) throws SQLException;
}
