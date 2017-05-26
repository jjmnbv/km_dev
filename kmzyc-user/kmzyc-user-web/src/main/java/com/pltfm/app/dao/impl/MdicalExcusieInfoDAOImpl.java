package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.MdicalExcusieInfoDAO;
import com.pltfm.app.vobject.MdicalExcusieInfo;
import com.pltfm.sys.model.SysModelUtil;

/**
 * 医务专属信息处理
 * 
 * @author gwl
 * @since 2013-07-08
 */
@Component(value = "mdicalExcusieInfoDAO")
public class MdicalExcusieInfoDAOImpl implements MdicalExcusieInfoDAO {
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  /**
   * 添加医务专属信息
   * 
   * @param userLevel 医务专属信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public Integer insertMdicalExcusieInfo(MdicalExcusieInfo MdicalExcusieInfo) throws SQLException {
    Object newKey =
        sqlMapClient.insert("APP_MdicalExcusieInfo.ibatorgenerated_insert", MdicalExcusieInfo);
    return (Integer) newKey;
  }

  /**
   * 删除医务专属信息
   * 
   * @param MdicalExcusieInfo 医务专属信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public Integer deleteMdicalExcusieInfo(MdicalExcusieInfo mdicalExcusieInfo) throws SQLException {
    Object newKey =
        sqlMapClient.delete("APP_MdicalExcusieInfo.ibatorgenerated_delete", mdicalExcusieInfo);
    return (Integer) newKey;
  }

  /**
   * 按条件查询医务信息总数量
   * 
   * @param vo
   * @return 返回值
   */
  public int selectCountByVo(MdicalExcusieInfo mdicalExcusieInfo) throws SQLException {
    List list =
        sqlMapClient.queryForList("APP_MdicalExcusieInfo.selectCountByVo", mdicalExcusieInfo);

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

  public List selectPageByVo(MdicalExcusieInfo mdicalExcusieInfo) throws SQLException {
    List pageList =
        sqlMapClient.queryForList("APP_MdicalExcusieInfo.searchPageByVo", mdicalExcusieInfo);
    return pageList;
  }

  /**
   * 修改医务专属信息
   * 
   * @param MdicalExcusieInfo 医务专属信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public Integer udpateMdicalExcusieInfo(MdicalExcusieInfo mdicalExcusieInfo) throws SQLException {
    Object newKey =
        sqlMapClient.update("APP_MdicalExcusieInfo.ibatorgenerated_update", mdicalExcusieInfo);
    return (Integer) newKey;
  }

  /**
   * 跟据个人id查询医务专属信息
   * 
   * @param MdicalExcusieInfo 医务专属信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public MdicalExcusieInfo getPersonalId_FK(Integer value) throws SQLException {
    return (MdicalExcusieInfo) sqlMapClient
        .queryForObject("APP_MdicalExcusieInfo.ibatorgenerated_getpersonalid", value);
  }

  /**
   * 跟据id查询医务专属信息
   * 
   * @param MdicalExcusieInfo 医务专属信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public MdicalExcusieInfo getPersonal_id(Integer value) throws SQLException {
    return (MdicalExcusieInfo) sqlMapClient.queryForObject(
        "APP_MdicalExcusieInfo.ibatorgenerated_getMedicalMattersExclusiveId", value);
  }

  public SqlMapClient getSqlMapClient() {
    return sqlMapClient;
  }

  public void setSqlMapClient(SqlMapClient sqlMapClient) {
    this.sqlMapClient = sqlMapClient;
  }
}
