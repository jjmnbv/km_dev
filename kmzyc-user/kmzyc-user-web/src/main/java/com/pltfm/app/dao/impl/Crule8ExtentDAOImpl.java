package com.pltfm.app.dao.impl;


import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.Crule8ExtentDAO;
import com.pltfm.app.dataobject.Crule8ExtentDO;

/**
 * 数据访问对象实现类
 * 
 * @since 2014-10-24
 */
@Component(value = "crule8ExtentDAO")
public class Crule8ExtentDAOImpl implements Crule8ExtentDAO {
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;


  /**
   * 插入数据
   * 
   * @param crule8ExtentDO
   * @return 插入数据的主键
   */
  public BigDecimal insertCrule8ExtentDO(Crule8ExtentDO crule8ExtentDO) {
    BigDecimal rowNum = null;
    try {
      rowNum = (BigDecimal) sqlMapClient.insert("Crule8Extent.insert", crule8ExtentDO);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return rowNum;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Crule8ExtentDO> findSuplerListByExample(Crule8ExtentDO crule8ExtentDO) {
    // TODO Auto-generated method stub


    List<Crule8ExtentDO> list = null;
    try {
      list = sqlMapClient.queryForList("Crule8Extent.findSuplerListByDO", crule8ExtentDO);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return list;
  }


  /* *//**
        * 统计记录数
        * 
        * @param crule8ExtentDO
        * @return 查出的记录数
        */

  /*
   * public Integer countCrule8ExtentDOByExample(Crule8ExtentDO crule8ExtentDO) { Integer count =
   * (Integer) getSqlMapClientTemplate().queryForObject("Crule8Extent.countByDOExample",
   * crule8ExtentDO); return count; }
   * 
   *//**
     * 获取对象列表
     * 
     * @param crule8ExtentDO
     * @return 对象列表
     *//*
       * @SuppressWarnings("unchecked") public List<Crule8ExtentDO> findListByExample(Crule8ExtentDO
       * crule8ExtentDO) { List<Crule8ExtentDO> list =
       * getSqlMapClientTemplate().queryForList("Crule8Extent.findListByDO", crule8ExtentDO); return
       * list; }
       */

  public SqlMapClient getSqlMapClient() {
    return sqlMapClient;
  }

  public void setSqlMapClient(SqlMapClient sqlMapClient) {
    this.sqlMapClient = sqlMapClient;
  }

  @Override
  public int deleteCrule8ExtentDO(BigDecimal crule8Id) {
    // TODO Auto-generated method stub


    int rowNums = 0;
    try {
      rowNums = sqlMapClient.delete("Crule8Extent.deleteByPrimaryKey", crule8Id);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return rowNums;
  }



}
