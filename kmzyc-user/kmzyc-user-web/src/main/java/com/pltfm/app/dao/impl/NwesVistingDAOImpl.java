package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.NwesVistingDAO;
import com.pltfm.app.vobject.NwesVisting;
import com.pltfm.sys.model.SysModelUtil;

/***
 * 
 * 拜访记录DAOIMLP
 */
@Component(value = "nwesVistingDAO")
public class NwesVistingDAOImpl implements NwesVistingDAO {
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  /***
   * 
   * 删除调查记录
   */
  public int delete(NwesVisting nwesVisting) throws SQLException {

    return sqlMapClient.delete("NWES_VISTING.ibatorgenerated_delete", nwesVisting);
  }


  /***
   * 
   * 添加调查记录
   */
  public Integer insert(NwesVisting nwesVisting) throws SQLException {
    Object keyObject = sqlMapClient.insert("NWES_VISTING.ibatorgenerated_insert", nwesVisting);
    return (Integer) keyObject;
  }

  /***
   * 
   * 跟据id查询调查记录
   */
  public NwesVisting selectByPrimaryKey(Integer vistingId) throws SQLException {

    return (NwesVisting) sqlMapClient.queryForObject("NWES_VISTING.ibatorgenerated_getVistingId",
        vistingId);
  }

  /***
   * 
   * 修改调查记录
   */
  public Integer update(NwesVisting nwesVisting) throws SQLException {
    Object keyObject = sqlMapClient.update("NWES_VISTING.ibatorgenerated_update", nwesVisting);
    return (Integer) keyObject;
  }

  /**
   * 按条件查询调查信息总数量
   * 
   * @param vo
   * @return 返回值
   */
  public Integer selectCountByVo(NwesVisting vo) throws SQLException {
    List list = sqlMapClient.queryForList("NWES_VISTING.selectCountByVo", vo);

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

  public List selectPageByVo(NwesVisting vo) throws SQLException {
    List pageList = sqlMapClient.queryForList("NWES_VISTING.searchPageByVo", vo);
    return pageList;
  }

  public SqlMapClient getSqlMapClient() {
    return sqlMapClient;
  }

  public void setSqlMapClient(SqlMapClient sqlMapClient) {
    this.sqlMapClient = sqlMapClient;
  }

}
