package com.pltfm.app.dao;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapExecutor;

@SuppressWarnings( {"unchecked", "hiding"})
@Repository
public class BaseDAO<T extends Object> extends SqlMapClientDaoSupport {
  private Logger log = Logger.getLogger(BaseDAO.class);

  @Autowired
  public void setsqlMapClient(SqlMapClient sqlMapClient) {
    super.setSqlMapClient(sqlMapClient);
  }

  @Resource
  protected SqlMapClient sqlMapClient;

  /**
   * @return
   */
  public <T> List<T> queryForList(String statementName, Object parameterObject) {
    return (List<T>) this.getSqlMapClientTemplate().queryForList(statementName, parameterObject);

  }

  /**
   * 批量更新（一次，自身有事务，外部事务控制无效）
   * 
   * @param statementName
   * @param parameterList
   * @return
   */
  public int batchUpdateData(String statementName, java.util.List<Object> parameterList) {
    try {
      if (!CollectionUtils.isEmpty(parameterList)) {
        sqlMapClient.openSession();
        sqlMapClient.startBatch();
        for (int i = 0; i < parameterList.size(); i++) {
          sqlMapClient.update(statementName, parameterList.get(i));
        }
        sqlMapClient.executeBatch();
        return 1;
      }
    } catch (Exception e) {
      log.error("批量更新数据异常！", e);
      return 0;
    }
    return 0;
  }

  /**
   * 批量保存（一次，，自身有事务，外部事务控制无效）
   * 
   * @param dataList
   */
  public void batchInsertData(final String statement, final List<T> dataList) {
    if (!CollectionUtils.isEmpty(dataList)) {
      getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
        @Override
        public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
          executor.startBatch();
          for (int i = 0; i < dataList.size(); i++) {
            executor.insert(statement, dataList.get(i));
          }
          executor.executeBatch();
          return null;
        }
      });
    }
  }

  /**
   * 批量删除（一次，自身有事务，外部事务控制无效）
   * 
   * @param dataList
   */
  public void batchDeleteData(final String statement, final List<T> dataList) {
    if (!CollectionUtils.isEmpty(dataList)) {
      getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
        @Override
        public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
          executor.startBatch();
          for (int i = 0; i < dataList.size(); i++) {
            executor.delete(statement, dataList.get(i));
          }
          executor.executeBatch();
          return null;
        }
      });
    }
  }

  /**
   * 根据主键批量保存（一次,自身无事务,集合不为泛型）
   * 
   * @param dataList
   */
  public int batchInsertByDataNt(final List dataList, final String statement) {
    try {
      if (!CollectionUtils.isEmpty(dataList)) {
        sqlMapClient.startBatch();
        for (int i = 0; i < dataList.size(); i++) {
          sqlMapClient.insert(statement, dataList.get(i));
        }
        sqlMapClient.executeBatch();
        return 1;
      }
    } catch (Exception e) {
      log.error("批量保存异常！", e);
      return 0;
    }
    return 0;
  }

  /**
   * 根据主键批量更新（一次,自身无事务,集合不为泛型）
   * 
   * @param dataList
   */
  public int batchUpdateByDataNt(final List dataList, final String statement) {
    try {
      if (!CollectionUtils.isEmpty(dataList)) {
        sqlMapClient.startBatch();
        for (int i = 0; i < dataList.size(); i++) {
          sqlMapClient.update(statement, dataList.get(i));
        }
        sqlMapClient.executeBatch();
        return 1;
      }
    } catch (Exception e) {
      log.error("根据主键批量更新异常！", e);
      return 0;
    }
    return 0;
  }

  /**
   * 根据主键批量删除（一次,自身无事务,集合不为泛型）
   * 
   * @param dataList
   */
  public int batchDeleteByDataNt(final List dataList, final String statement) {
    try {
      if (!CollectionUtils.isEmpty(dataList)) {
        sqlMapClient.startBatch();
        for (int i = 0; i < dataList.size(); i++) {
          sqlMapClient.delete(statement, dataList.get(i));
        }
        sqlMapClient.executeBatch();
        return 1;
      }
    } catch (Exception e) {
      log.error("根据主键批量删除异常！", e);
      return 0;
    }
    return 0;
  }

}
