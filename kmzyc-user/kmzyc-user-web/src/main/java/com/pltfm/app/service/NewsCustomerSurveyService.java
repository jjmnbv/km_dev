package com.pltfm.app.service;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.vobject.NewsCustomerSurvey;

/***
 * 
 * 调查记录Service接口
 */
public interface NewsCustomerSurveyService {
  /***
   * 
   * 删除调查记录
   */
  int delete(List<Integer> customerSurveyIds) throws SQLException;


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
   * 分页查询调查记录信息
   * 
   * @param 调查记录信息实体
   * @return
   * @throws Exception 异常
   */
  public Page searchPageByVo(Page pageParam, NewsCustomerSurvey vo) throws Exception;
}
