package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.BnesMessageCenterDAO;
import com.pltfm.app.vobject.BnesMessageCenter;
import com.pltfm.sys.model.SysModelUtil;

/**
 * 
 * 
 * 消息DAO
 * 
 */
@Component(value = "bnesMessageCenterDAO")
public class BnesMessageCenterDAOImpl implements BnesMessageCenterDAO {
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  public SqlMapClient getSqlMapClient() {
    return sqlMapClient;
  }

  public void setSqlMapClient(SqlMapClient sqlMapClient) {
    this.sqlMapClient = sqlMapClient;
  }

  /**
   * 
   * 
   * 添加消息
   * 
   */
  public Integer insert(BnesMessageCenter record) throws SQLException {
    Object newKey =
        sqlMapClient.insert("BNES_MESSAGE_CENTER.ibatorgenerated_insertSelective", record);
    return (Integer) newKey;
  }

  /**
   * 
   * 
   * 删除消息
   * 
   */
  public int delete(BnesMessageCenter record) throws SQLException {
    return sqlMapClient.delete("BNES_MESSAGE_CENTER.ibatorgenerated_delete", record);
  }

  /**
   * 
   * 
   * 修改消息
   * 
   */
  public int update(BnesMessageCenter record) throws SQLException {
    return sqlMapClient.update("BNES_MESSAGE_CENTER.ibatorgenerated_update", record);
  }

  /**
   * 按条件查询基本信息总数量
   * 
   * @param vo
   * @return 返回值
   */
  public int selectCountByVo(BnesMessageCenter bnesMessageCenter) throws SQLException {
    List list = sqlMapClient.queryForList("BNES_MESSAGE_CENTER.selectCountByVo", bnesMessageCenter);
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

  public List selectPageByVo(BnesMessageCenter bnesMessageCenter) throws SQLException {
    List pageList =
        sqlMapClient.queryForList("BNES_MESSAGE_CENTER.searchPageByVo", bnesMessageCenter);
    return pageList;
  }

  /**
   * 跟据信息id查询信息信息
   * 
   * @param Rank 信息息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public BnesMessageCenter getMessageId(Integer messageId) throws SQLException {
    BnesMessageCenter bnesMessageCenter = (BnesMessageCenter) sqlMapClient
        .queryForObject("BNES_MESSAGE_CENTER.ibatorgenerated_getMessageId", messageId);
    return bnesMessageCenter;
  }
}
