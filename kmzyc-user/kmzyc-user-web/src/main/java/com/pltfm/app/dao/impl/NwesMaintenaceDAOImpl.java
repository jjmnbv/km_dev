package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.NwesMaintenaceDAO;
import com.pltfm.app.vobject.NwesMaintenace;
import com.pltfm.sys.model.SysModelUtil;

/***
 * 
 * 维护记录DAOIMLP
 */
@Component(value = "nwesMaintenaceDAO")
public class NwesMaintenaceDAOImpl implements NwesMaintenaceDAO {
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  /***
   * 
   * 删除维护记录
   */
  public int delete(NwesMaintenace nwesMaintenace) throws SQLException {

    return sqlMapClient.delete("NWES_MAINTENACE.ibatorgenerated_delete", nwesMaintenace);
  }


  /***
   * 
   * 添加维护记录
   */
  public Integer insert(NwesMaintenace nwesMaintenace) throws SQLException {
    Object keyObject =
        sqlMapClient.insert("NWES_MAINTENACE.ibatorgenerated_insert", nwesMaintenace);
    return (Integer) keyObject;
  }

  /***
   * 
   * 跟据id查询维护记录
   */
  public NwesMaintenace selectByPrimaryKey(Integer maintenaceId) throws SQLException {
    return (NwesMaintenace) sqlMapClient
        .queryForObject("NWES_MAINTENACE.ibatorgenerated_getMaintenaceId", maintenaceId);
  }

  /***
   * 
   * 修改维护记录
   */
  public Integer update(NwesMaintenace nwesMaintenace) throws SQLException {
    Object keyObject =
        sqlMapClient.update("NWES_MAINTENACE.ibatorgenerated_update", nwesMaintenace);
    return (Integer) keyObject;
  }

  /**
   * 按条件查询维护信息总数量
   * 
   * @param vo
   * @return 返回值
   */
  public Integer selectCountByVo(NwesMaintenace vo) throws SQLException {
    List list = sqlMapClient.queryForList("NWES_MAINTENACE.selectCountByVo", vo);

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

  public List selectPageByVo(NwesMaintenace vo) throws SQLException {
    List pageList = sqlMapClient.queryForList("NWES_MAINTENACE.searchPageByVo", vo);
    return pageList;
  }

  public SqlMapClient getSqlMapClient() {
    return sqlMapClient;
  }

  public void setSqlMapClient(SqlMapClient sqlMapClient) {
    this.sqlMapClient = sqlMapClient;
  }

}
