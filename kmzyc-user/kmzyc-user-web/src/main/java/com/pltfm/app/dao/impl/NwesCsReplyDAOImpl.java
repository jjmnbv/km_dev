package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.NwesCsReplyDAO;
import com.pltfm.app.vobject.NwesCsReply;
import com.pltfm.sys.model.SysModelUtil;

/***
 * 服务回复信息
 */
@Component(value = "nwesCsReplyDAO")
public class NwesCsReplyDAOImpl implements NwesCsReplyDAO {
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  /***
   * 
   * 删除回复信息
   */
  public int delete(NwesCsReply nwesCsReply) throws SQLException {
    return sqlMapClient.delete("NWES_CS_REPLY.ibatorgenerated_delete", nwesCsReply);
  }

  /***
   * 
   * 服务外键删除回复信息
   */
  public int deleteCustomerSurveyId(Integer customerSurveyId) throws SQLException {
    return sqlMapClient.delete("NWES_CS_REPLY.ibatorgenerated_deleteCustomerSurveyId",
        customerSurveyId);
  }

  /***
   * 
   * 添加回复信息
   */
  public Integer insert(NwesCsReply nwesCsReply) throws SQLException {
    Object keyObject = sqlMapClient.insert("NWES_CS_REPLY.ibatorgenerated_insert", nwesCsReply);
    return (Integer) keyObject;
  }

  /***
   * 
   * 跟据id查询回复信息
   */
  public NwesCsReply selectByPrimaryKey(Integer replyId) throws SQLException {
    return (NwesCsReply) sqlMapClient
        .queryForObject("NWES_CS_REPLY.ibatorgenerated_selectByPrimaryKey", replyId);
  }

  /***
   * 
   * 跟据服务外键查询回复信息总条数
   */
  public Integer getReplyCounts(Integer customerSurveyId) throws SQLException {
    List list =
        sqlMapClient.queryForList("NWES_CS_REPLY.ibatorgenerated_getCount", customerSurveyId);

    SysModelUtil countResult = (SysModelUtil) list.get(0);
    // 总条数
    int recs = countResult.getTheCount().intValue();
    return recs;
  }

  /***
   * 
   * 跟据服务外键查询回复信息
   */
  public NwesCsReply getCustomerSurveyId(Integer customerSurveyId) throws SQLException {
    return (NwesCsReply) sqlMapClient
        .queryForObject("NWES_CS_REPLY.ibatorgenerated_getCustomerSurveyId", customerSurveyId);
  }

  /***
   * 
   * 修改回复信息
   */
  public Integer update(NwesCsReply nwesCsReply) throws SQLException {
    Object keyObject = sqlMapClient.update("NWES_CS_REPLY.ibatorgenerated_update", nwesCsReply);
    return (Integer) keyObject;
  }

  /**
   * 按条件查询回复信息总数量
   * 
   * @param vo
   * @return 返回值
   */
  public Integer selectCountByVo(NwesCsReply vo) throws SQLException {
    List list = sqlMapClient.queryForList("NWES_CS_REPLY.selectCountByVo", vo);

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

  public List selectPageByVo(NwesCsReply vo) throws SQLException {
    List pageList = sqlMapClient.queryForList("NWES_CS_REPLY.searchPageByVo", vo);
    return pageList;
  }

  public SqlMapClient getSqlMapClient() {
    return sqlMapClient;
  }

  public void setSqlMapClient(SqlMapClient sqlMapClient) {
    this.sqlMapClient = sqlMapClient;
  }
}
