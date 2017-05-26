package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.NewsCustomerSurveyDAO;
import com.pltfm.app.vobject.NewsCustomerSurvey;
import com.pltfm.sys.model.SysModelUtil;

/***
 * 
 * 调查记录DAOIMLP
 */
@Component(value = "newsCustomerSurveyDAO")
public class NewsCustomerSurveyDAOImpl implements NewsCustomerSurveyDAO {
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  /***
   * 
   * 删除调查记录
   */
  public int deleteByExample(NewsCustomerSurvey example) throws SQLException {

    return sqlMapClient.delete("NEWS_CUSTOMER_SURVEY.ibatorgenerated_delete", example);
  }


  /***
   * 
   * 添加调查记录
   */
  public Integer insert(NewsCustomerSurvey record) throws SQLException {
    Object keyObject =
        sqlMapClient.insert("NEWS_CUSTOMER_SURVEY.ibatorgenerated_insertSelective", record);
    return (Integer) keyObject;
  }

  /***
   * 
   * 跟据id查询调查记录
   */
  public NewsCustomerSurvey selectByPrimaryKey(Integer customerSurveyId) throws SQLException {
    return (NewsCustomerSurvey) sqlMapClient
        .queryForObject("NEWS_CUSTOMER_SURVEY.getCustomerTypeId", customerSurveyId);
  }

  /***
   * 
   * 修改调查记录
   */
  public Integer updateByPrimaryKeySelective(NewsCustomerSurvey record) throws SQLException {
    Object keyObject = sqlMapClient.update("NEWS_CUSTOMER_SURVEY.ibatorgenerated_update", record);
    return (Integer) keyObject;
  }

  /**
   * 按条件查询基本信息总数量
   * 
   * @param vo
   * @return 返回值
   */
  public Integer selectCountByVo(NewsCustomerSurvey vo) throws SQLException {
    List list = sqlMapClient.queryForList("NEWS_CUSTOMER_SURVEY.selectCountByVo", vo);

    SysModelUtil countResult = (SysModelUtil) list.get(0);
    // 总条数
    int recs = countResult.getTheCount().intValue();
    return recs;
  }

  /**
   * 根据vo条件查询分类信息page
   * 
   * @param page 分页类
   * @param vo 信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */

  public List selectPageByVo(NewsCustomerSurvey vo) throws SQLException {
    List pageList = sqlMapClient.queryForList("NEWS_CUSTOMER_SURVEY.searchPageByVo", vo);
    return pageList;
  }

  public SqlMapClient getSqlMapClient() {
    return sqlMapClient;
  }

  public void setSqlMapClient(SqlMapClient sqlMapClient) {
    this.sqlMapClient = sqlMapClient;
  }

}
