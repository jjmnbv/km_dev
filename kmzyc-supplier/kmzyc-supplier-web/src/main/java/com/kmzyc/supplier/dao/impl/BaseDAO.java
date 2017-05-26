package com.kmzyc.supplier.dao.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.km.framework.page.Pagination;

@Repository
public class BaseDAO<T extends Object>{

	static Logger logger = LoggerFactory.getLogger(BaseDAO.class);
	
	@Resource
	protected SqlMapClient sqlMapClient;

	/**
	 * 分页查询,
	 * @param sqlMapClient:根据选择传递工具:如sqlMapClientByCustomer或sqlMapClient
	 * @param sqlId 
	 * @param countSqlId 总数的sqlmap语句Id
	 * @param pagination
	 * @return
	 * @throws SQLException
	 */
	public Pagination findPaginationByPage(SqlMapClient sqlMapClient, String sqlId,String countSqlId, Pagination pagination) throws SQLException {
		try {
			pagination.setRecordList(sqlMapClient.queryForList(sqlId,pagination));
		} catch (SQLException e) {
			logger.error("分页查询：查询分页列表出现异常！", e);
			throw new SQLException("分页查询：查询分页列表出现异常!", e);
		}
		try {
			pagination.setTotalRecords((Integer)sqlMapClient.queryForObject(countSqlId, pagination));
		} catch (SQLException e) {
			logger.error("分页查询：查询总记录数出现异常！", e);
			throw new SQLException("分页查询：查询总记录数出现异常!", e);
		}
		return pagination;
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
			logger.error(e.getMessage(),e);
			return 0;
		}
		return 0;
	}
	
	/**
	 * 批量插入（一次）
	 * 
	 * @param statementName
	 * @param parameterList
	 * @return
	 */
	public int batchInsertData(final String statementName,
			final java.util.List<T> parameterList) {
		try {
			if (!CollectionUtils.isEmpty(parameterList)) {
				sqlMapClient.startBatch();
				for (int i = 0; i < parameterList.size(); i++) {
					sqlMapClient.insert(statementName, parameterList.get(i));
				}
				sqlMapClient.executeBatch();
				return 1;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return 0;
		}
		return 0;
	}
	
	/**
	 * 批量删除（一次）
	 * 
	 * @param statementName
	 * @param parameterList
	 * @return
	 */
	public int batchDeleteByPrimaryKeyData(final String statementName,
			final java.util.List parameterList) {
		try {
			if (!CollectionUtils.isEmpty(parameterList)) {
				sqlMapClient.startBatch();
				for (int i = 0; i < parameterList.size(); i++) {
					sqlMapClient.delete(statementName, parameterList.get(i));
				}
				sqlMapClient.executeBatch();
				return 1;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return 0;
		}
		return 0;
	}

}
