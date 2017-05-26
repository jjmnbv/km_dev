package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.NwesCustomServiceDAO;
import com.pltfm.app.vobject.NwesCustomService;
import com.pltfm.sys.model.SysModelUtil;

/***
 * 服务信息
 */
@Component(value = "nwesCustomServiceDAO")
public class NwesCustomServiceDAOImpl implements NwesCustomServiceDAO {
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  /***
   * 
   * 删除服务信息
   */
  public int delete(NwesCustomService nwesCustomService) throws SQLException {
    return sqlMapClient.delete("NWES_CUSTOM_SERVICE.ibatorgenerated_delete", nwesCustomService);
  }


  /***
   * 
   * 添加服务信息
   */
  public Integer insert(NwesCustomService nwesCustomService) throws SQLException {
    Object keyObject =
        sqlMapClient.insert("NWES_CUSTOM_SERVICE.ibatorgenerated_insert", nwesCustomService);
    return (Integer) keyObject;
  }

  /***
   * 
   * 跟据id查询服务信息
   */
  public NwesCustomService selectByPrimaryKey(Integer customServiceId) throws SQLException {
    return (NwesCustomService) sqlMapClient
        .queryForObject("NWES_CUSTOM_SERVICE.ibatorgenerated_selectByPrimaryKey", customServiceId);
  }

  /***
   * 
   * 修改服务信息
   */
  public Integer update(NwesCustomService nwesCustomService) throws SQLException {
    Object keyObject =
        sqlMapClient.update("NWES_CUSTOM_SERVICE.ibatorgenerated_update", nwesCustomService);
    return (Integer) keyObject;
  }

  /**
   * 按条件查询服务信息总数量
   * 
   * @param vo
   * @return 返回值
   */
  public Integer selectCountByVo(NwesCustomService vo) throws SQLException {
    List list = sqlMapClient.queryForList("NWES_CUSTOM_SERVICE.selectCountByVo", vo);

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

  public List selectPageByVo(NwesCustomService vo) throws SQLException {
    List pageList = sqlMapClient.queryForList("NWES_CUSTOM_SERVICE.searchPageByVo", vo);
    return pageList;
  }

  public SqlMapClient getSqlMapClient() {
    return sqlMapClient;
  }

  public void setSqlMapClient(SqlMapClient sqlMapClient) {
    this.sqlMapClient = sqlMapClient;
  }
}
