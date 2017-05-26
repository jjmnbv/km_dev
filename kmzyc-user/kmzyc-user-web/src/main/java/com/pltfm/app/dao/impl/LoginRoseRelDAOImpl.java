package com.pltfm.app.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.LoginRoseRelDAO;
import com.pltfm.app.dataobject.LoginRoseRelDO;

/**
 * 数据访问对象实现类
 * 
 * @since 2014-05-22
 */
@Component(value = "loginRoseRelDAO")
public class LoginRoseRelDAOImpl implements LoginRoseRelDAO {

  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  /**
   * 插入数据
   * 
   * @param loginRoseRelDO
   * @return 插入数据的主键
   */
  public BigDecimal insertLoginRoseRelDO(LoginRoseRelDO loginRoseRelDO) {
    BigDecimal LR_ID = null;
    try {
      LR_ID = (BigDecimal) sqlMapClient.insert("LoginRoseRel.insert", loginRoseRelDO);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return (BigDecimal) LR_ID;
  }

  public SqlMapClient getSqlMapClient() {
    return sqlMapClient;
  }

  public void setSqlMapClient(SqlMapClient sqlMapClient) {
    this.sqlMapClient = sqlMapClient;
  }

  /**
   * 统计记录数
   * 
   * @param loginRoseRelDO
   * @return 查出的记录数
   */
  /*
   * public Integer countLoginRoseRelDOByExample(LoginRoseRelDO loginRoseRelDO) { Integer count =
   * (Integer) getSqlMapClientTemplate().queryForObject("LoginRoseRel.countByDOExample",
   * loginRoseRelDO); return count; }
   * 
   *//**
     * 统计记录数
     * 
     * @param loginRoseRelQuery
     * @return 查出的记录数
     */

  /*
   * public Integer countLoginRoseRelQueryByExample(LoginRoseRelQuery loginRoseRelQuery) { Integer
   * count = (Integer) getSqlMapClientTemplate().queryForObject("LoginRoseRel.countByQueryExample",
   * loginRoseRelQuery); return count; }
   * 
   *//**
     * 更新记录
     * 
     * @param loginRoseRelDO
     * @return 受影响的行数
     */
  public Integer updateLoginRoseRelDO(LoginRoseRelDO loginRoseRelDO) {
    int result = 0;
    try {
      result = sqlMapClient.update("LoginRoseRel.updateLoginRel", loginRoseRelDO);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return result;
  }

  /**
   * 获取对象列表
   * 
   * @param loginRoseRelDO
   * @return 对象列表
   */
  /*
   * @SuppressWarnings("unchecked") public List<LoginRoseRelDO> findListByExample(LoginRoseRelDO
   * loginRoseRelDO) { List<LoginRoseRelDO> list =
   * getSqlMapClientTemplate().queryForList("LoginRoseRel.findListByDO", loginRoseRelDO); return
   * list; }
   * 
   *//**
     * 获取对象列表
     * 
     * @param loginRoseRelQuery
     * @return 对象列表
     */
  /*
   * @SuppressWarnings("unchecked") public List<LoginRoseRelQuery>
   * findListByExample(LoginRoseRelQuery loginRoseRelQuery) { List<LoginRoseRelQuery> list =
   * getSqlMapClientTemplate().queryForList("LoginRoseRel.findListByQuery", loginRoseRelQuery);
   * return list; }
   * 
   *//**
     * 根据主键获取loginRoseRelDO
     * 
     * @param lrId
     * @return loginRoseRelDO
     */
  /*
   * public LoginRoseRelDO findLoginRoseRelDOByPrimaryKey(BigDecimal lrId) { LoginRoseRelDO
   * loginRoseRelDO = (LoginRoseRelDO)
   * getSqlMapClientTemplate().queryForObject("LoginRoseRel.findByPrimaryKey", lrId); return
   * loginRoseRelDO; }
   * 
   *//**
     * 删除记录
     * 
     * @param lrId
     * @return 受影响的行数
     *//*
       * public Integer deleteLoginRoseRelDOByPrimaryKey(BigDecimal lrId) { Integer rows = (Integer)
       * getSqlMapClientTemplate().delete("LoginRoseRel.deleteByPrimaryKey", lrId); return rows; }
       */
}
