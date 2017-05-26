package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.BloodIntegralInfoDAO;
import com.pltfm.app.vobject.BloodIntegralInfo;
import com.pltfm.sys.model.SysModelUtil;

/****
 * 积分明细DAO实现
 */
@Component(value = "bloodIntegralInfoDAO")
public class BloodIntegralInfoDAOImpl implements BloodIntegralInfoDAO {
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  /***
   * 删除经验明细
   **/
  public int delete(BloodIntegralInfo bloodIntegralInfo) throws SQLException {
    return sqlMapClient.delete("BLOOD_INTEGRAL_INFO.ibatorgenerated_delete", bloodIntegralInfo);
  }

  /***
   * 添加经验明细
   **/
  public int insert(BloodIntegralInfo bloodIntegralInfo) throws SQLException {
    Object keyObject =
        sqlMapClient.insert("BLOOD_INTEGRAL_INFO.ibatorgenerated_insert", bloodIntegralInfo);
    return (Integer) keyObject;
  }

  /**
   * 按条件查询经验明细总数量
   * 
   * @param vo
   * @return 返回值
   */
  public Integer selectCountByVo(BloodIntegralInfo vo) throws SQLException {
    List list = sqlMapClient.queryForList("BLOOD_INTEGRAL_INFO.selectCountByVo", vo);

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

  public List selectPageByVo(BloodIntegralInfo vo) throws SQLException {
    List pageList = sqlMapClient.queryForList("BLOOD_INTEGRAL_INFO.searchPageByVo", vo);
    return pageList;
  }

  public SqlMapClient getSqlMapClient() {
    return sqlMapClient;
  }

  public void setSqlMapClient(SqlMapClient sqlMapClient) {
    this.sqlMapClient = sqlMapClient;
  }


}
