package com.kmzyc.framework.persistence.impl;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.framework.mybatis.session.SqlSession;
import com.kmzyc.framework.page.Pagination;
import com.kmzyc.framework.persistence.Dao;

public abstract class DaoImpl implements Dao {
	static Logger logger = LoggerFactory.getLogger(DaoImpl.class);
	@Autowired
    protected SqlSession sqlSession;
	
	public Object save(String sqlId, Object entity) throws SQLException {
		try {
			return sqlSession.insert(sqlId, entity);
		} catch (Exception e) {
			logger.error("保存实体出现异常！",e);
			throw new SQLException("保存实体出现异常!",e);
		}
	}

	public Integer update(String sqlId, Object entity) throws SQLException {
		try {
			return sqlSession.update(sqlId, entity);
		} catch (Exception e) {
			logger.error("更新实体出现异常！",e);
			throw new SQLException("更新实体出现异常!",e);
		}
	}

	public Integer deleteById(String sqlId, Object id) throws SQLException {
		try {
			return sqlSession.delete(sqlId, id);
		} catch (Exception e) {
			logger.error("根据ID删除记录出现异常！",e);
			throw new SQLException("根据ID删除记录出现异常!",e);
		}
	}
	
	public Integer deleteByIds(String sqlId, Object[] ids) throws SQLException {
		try {
			return sqlSession.delete(sqlId, ids);
		} catch (Exception e) {
			logger.error("根据ids[]批量删除记录出现异常！",e);
			throw new SQLException("根据ids[]批量删除记录出现异常!",e);
		}
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public Object findById(String sqlId, Object id) throws SQLException {
		try {
			return sqlSession.selectOne(sqlId, id);
		} catch (Exception e) {
			logger.error("根据ID查询实体出现异常！",e);
			throw new SQLException("根据ID查询实体出现异常!",e);
		}
	}

	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public List findByProperty(String sqlId, Object propertyName)  throws SQLException{
		try {
			return sqlSession.selectList(sqlId, propertyName);
		} catch (DataAccessException e) {
			logger.error("根据属性查询实体表出现异常！",e);
			throw new SQLException("根据属性查询实体表出现异常!",e);
		}
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public List findList(String sqlId) throws SQLException {
		try {
			return sqlSession.selectList(sqlId);
		} catch (Exception e) {
			logger.error("查询记录列表出现异常！",e);
			throw new SQLException("查询记录列表出现异常!",e);
		}
	}

	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public int findByCount(String sqlId, Pagination pagination) throws SQLException {
		try {
			return (Integer) sqlSession.selectOne(sqlId, pagination);
		} catch (Exception e) {
			logger.error("查询总记录数出现异常！",e);
			throw new SQLException("查询总记录数出现异常!",e);
		}
	}

	/**
	 * 分页查询实现方法，先查询分页列表，再查询总记录数
	 */
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public Pagination findByPage(String sqlId, String countSqlId, Pagination pagination) throws SQLException {
		pagination.setRecordList((sqlSession.selectList(sqlId,pagination)));
		try {
			pagination.setTotalRecords(findByCount(countSqlId, pagination));
		} catch (SQLException e) {
			logger.error("分页查询：查询总记录数出现异常！",e);
			throw new SQLException("分页查询：查询总记录数出现异常!",e);
		}
		return pagination;
	}

}
