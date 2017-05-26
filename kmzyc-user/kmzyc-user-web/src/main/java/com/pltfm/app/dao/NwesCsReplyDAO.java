package com.pltfm.app.dao;

import com.pltfm.app.vobject.NwesCsReply;

import java.sql.SQLException;
import java.util.List;

/***
 * 服务回复信息
 */
public interface NwesCsReplyDAO {
  /***
   * 
   * 删除服务回复
   */
  int delete(NwesCsReply nwesCsReply) throws SQLException;

  /***
   * 
   * 服务外键删除回复信息
   */
  public int deleteCustomerSurveyId(Integer customerSurveyId) throws SQLException;

  /***
   * 
   * 添加服务回复
   */
  Integer insert(NwesCsReply nwesCsReply) throws SQLException;

  /***
   * 
   * 跟据id查询服务回复
   */
  NwesCsReply selectByPrimaryKey(Integer replyId) throws SQLException;

  /***
   * 
   * 跟据服务外键查询回复信息
   */
  public NwesCsReply getCustomerSurveyId(Integer customerSurveyId) throws SQLException;

  /***
   * 
   * 跟据服务外键查询回复信息总条数
   */
  public Integer getReplyCounts(Integer customerSurveyId) throws SQLException;

  /***
   * 
   * 修改服务回复
   */
  Integer update(NwesCsReply nwesCsReply) throws SQLException;

  /**
   * 按条件查询服务回复总数量
   * 
   * @param vo
   * @return 返回值
   */
  Integer selectCountByVo(NwesCsReply vo) throws SQLException;

  /**
   * 根据vo条件查询分类信息page
   * 
   * @param page 分页类
   * @param vo 信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */

  public List selectPageByVo(NwesCsReply vo) throws SQLException;
}
