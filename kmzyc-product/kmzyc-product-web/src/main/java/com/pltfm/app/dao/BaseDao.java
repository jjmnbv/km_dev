package com.pltfm.app.dao;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.km.framework.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BaseDao<T extends Object> extends SqlMapClientDaoSupport {
	@Autowired
	public void setsqlMapClient4(SqlMapClient sqlMapClient) {
		super.setSqlMapClient(sqlMapClient);
	}

	@Resource
	protected SqlMapClient sqlMapClient;

	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> queryForList(String statementName, Object parameterObject) {
		return (List<T>) this.getSqlMapClientTemplate().queryForList(
				statementName, parameterObject);

	}

	/**
	 * 批量更新（一次）
	 * 
	 * @param statementName
	 * @param parameterList
	 * @return
	 */
	public int batchUpdateData(final String statementName,
			final java.util.List<T> parameterList) {
		try {
			if (!CollectionUtils.isEmpty(parameterList)) {
				sqlMapClient.startBatch();
				for (int i = 0; i < parameterList.size(); i++) {
					sqlMapClient.update(statementName, parameterList.get(i));
				}
				sqlMapClient.executeBatch();
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 0;
	}
	
	/**
	 * 批量更新（集合不为泛型）
	 * 
	 * @param statementName
	 * @param parameterList
	 * @return
	 */
	public int batchUpdateDataNotGen(final String statementName,
			final java.util.List parameterList) {
		try {
			if (!CollectionUtils.isEmpty(parameterList)) {
				sqlMapClient.startBatch();
				for (int i = 0; i < parameterList.size(); i++) {
					sqlMapClient.update(statementName, parameterList.get(i));
				}
				sqlMapClient.executeBatch();
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 0;
	}
	
	/**
	 * 根据主键批量删除（一次,自身无事务）
	 * 
	 * @param dataList
	 */
	public int batchDeleteByDataPrimaryKeyNt(final List<T> dataList, final String statement) {
		try {
			if (!CollectionUtils.isEmpty(dataList)) {
				sqlMapClient.startBatch();
				for (int i = 0; i < dataList.size(); i++) {
					sqlMapClient.delete(statement, dataList.get(i));
				}
				sqlMapClient.executeBatch();
				return 1;
			}
		}catch (Exception e) {
			e.printStackTrace();
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
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 0;
	}
	
	/**
	 * 根据主键批量删除（一次）
	 * 
	 * @param dataList
	 */
	public int batchDeleteByDataPrimaryKey(final List<T> dataList, final String statement) {
		if (CollectionUtils.isEmpty(dataList)) return 0;
		getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
			@Override
			public Object doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {

				executor.startBatch();
				for (int i = 0; i < dataList.size(); i++) {
					executor.delete(statement, dataList.get(i));
				}
				return executor.executeBatch();
			
			}
		});
		return 1;
	}
	
	/**
	 * 批量保存（一次,自身无事务）
	 * 
	 * @param dataList
	 */
	public int batchInsertDataNt(final List dataList, final String statement) {
		try {
			if (!CollectionUtils.isEmpty(dataList)) {
				sqlMapClient.startBatch();
				for (int i = 0; i < dataList.size(); i++) {
					sqlMapClient.insert(statement, dataList.get(i));
				}
				sqlMapClient.executeBatch();
				return 1;
			}
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 0;
	}
	
	/**
	 * 批量保存（一次）
	 * 
	 * @param dataList
	 */
	public void batchInsertData(final List<T> dataList, final String statement) {
		if (!CollectionUtils.isEmpty(dataList)) {
			getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
				@Override
				public Object doInSqlMapClient(SqlMapExecutor executor)
						throws SQLException {
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
	 * 批量更新（一次,无事务）
	 * 
	 * @param dataList
	 */
	public int batchUpdateNt(final List<T> dataList, final String statement) {
		try {
			if (!CollectionUtils.isEmpty(dataList)) {
				sqlMapClient.startBatch();
				for (int i = 0; i < dataList.size(); i++) {
					sqlMapClient.update(statement, dataList.get(i));
				}
				sqlMapClient.executeBatch();
				return 1;
			}
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 0;
	}
	public List<T> myPatchUpdate(final List<T> dataList, final String statement) {
		try {
			List<T> returnDataList = new ArrayList<T>();
			if (!CollectionUtils.isEmpty(dataList)) {
				sqlMapClient.startBatch();
				for (int i = 0; i < dataList.size(); i++) {
					int c = sqlMapClient.update(statement, dataList.get(i));
					if(c==1){
						returnDataList.add(dataList.get(i));
					}
				}
				sqlMapClient.executeBatch();
				return returnDataList;
			}
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	
	/**
	 * 批量更新（每200一次）
	 * 
	 * @param dataList
	 */
	public void batchUpdate(final List<T> dataList, final String statement) {

		if (!CollectionUtils.isEmpty(dataList)) {
			getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
				@Override
				public Object doInSqlMapClient(SqlMapExecutor executor)
						throws SQLException {
					executor.startBatch();
					for (int i = 0; i < dataList.size(); i++) {
						executor.update(statement, dataList.get(i));
						// 批次提交
						if (i % 200 == 0) {
							executor.executeBatch();
						}
					}
					executor.executeBatch();
					return null;
				}

			});
		}
	}

	/**
	 * 批量删除（每200一次）
	 * 
	 * @param dataList
	 */
	public void batchDelete(final List<T> dataList, final String statement) {

		if (!CollectionUtils.isEmpty(dataList)) {
			getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
				@Override
				public Object doInSqlMapClient(SqlMapExecutor executor)
						throws SQLException {

					executor.startBatch();
					for (int i = 0; i < dataList.size(); i++) {
						executor.delete(statement, dataList.get(i));
						// 批次提交
						if (i % 200 == 0) {
							executor.executeBatch();
						}
					}
					executor.executeBatch();
					return null;
				
				}
			});
		}
	}
	
	/**
	 * 根据主键批量删除（一次）
	 * 
	 * @param dataList
	 */
	public void batchDeleteByPrimaryKey(final List<T> dataList, final String statement) {

		if (!CollectionUtils.isEmpty(dataList)) {
			getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
				@Override
				public Object doInSqlMapClient(SqlMapExecutor executor)
						throws SQLException {

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
	 * 批量保存（每200一次,无事务）
	 * 
	 * @param dataList
	 */
	public int batchinsertNt(final List<T> dataList, final String statement) {
		try {
			if (!CollectionUtils.isEmpty(dataList)) {
				sqlMapClient.startBatch();
				for (int i = 0; i < dataList.size(); i++) {
					sqlMapClient.insert(statement, dataList.get(i));
					// 批次提交
					if (i % 200 == 0) {
						sqlMapClient.executeBatch();
					}
				}
				sqlMapClient.executeBatch();
			}
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 批量保存（每200一次）
	 * 
	 * @param dataList
	 */
	public void batchinsert(final List<T> dataList, final String statement) {
		if (!CollectionUtils.isEmpty(dataList)) {
			getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
				@Override
				public Object doInSqlMapClient(SqlMapExecutor executor)
						throws SQLException {
					executor.startBatch();
					for (int i = 0; i < dataList.size(); i++) {
						executor.insert(statement, dataList.get(i));
						// 批次提交
						if (i % 200 == 0) {
							executor.executeBatch();
						}
					}
					executor.executeBatch();
					return null;
				}
			});
		}
	}
	
	@SuppressWarnings("unchecked")
	public Pagination queryPagination(String sql,String countSql,Pagination page)throws Exception{
		List<T> recordList = null;
//		page.setTotalRecords(0);
//		page.setTotalpage(0);
		recordList = sqlMapClient.queryForList(sql, page);
		Integer count = (Integer) sqlMapClient.queryForObject(countSql,page);
		page.setRecordList(recordList);
		page.setTotalRecords(count);
		int yu = count%page.getNumperpage();
		int totalpage = count/page.getNumperpage();
		page.setTotalpage(yu==0?totalpage:totalpage+1);
		return page;
		
	}
}
