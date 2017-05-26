package com.pltfm.app.service;

import java.sql.SQLException;
import java.util.List;

import com.pltfm.app.vobject.NwesCsReply;

/***
 * 
 * 服务回复信息
 */
public interface NwesCsReplyService {
  /***
   * 
   * 删除回复信息
   */
  int delete(List<Integer> replyIds) throws SQLException;

  /***
   * 
   * 添加回复信息
   */
  Integer insert(NwesCsReply nwesCsReply) throws SQLException;

  /***
   * 
   * 跟据服务外键查询回复信息
   */
  public NwesCsReply getCustomerSurveyId(Integer customerSurveyId) throws SQLException;

  /***
   * 
   * 跟据id查询回复信息
   */
  NwesCsReply selectByPrimaryKey(Integer replyId) throws SQLException;

  /***
   * 
   * 跟据服务外键查询回复信息总条数
   */
  public Integer getReplyCounts(Integer customerSurveyId) throws SQLException;
}
