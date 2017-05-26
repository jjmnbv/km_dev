package com.kmzyc.framework.persistence;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.framework.page.Pagination;


public interface Dao {

    /**
     * 保存实体
     *
     * @param sqlId  　保存实体sql语句ID
     * @param entity 　要保存的实体
     * @return
     * @throws SQLException
     */
    public Object save(String sqlId, Object entity) throws SQLException;

    /**
     * 更新实体
     *
     * @param sqlId  　更新实体sql语句ID
     * @param entity
     * @return
     * @throws SQLException
     */
    public Integer update(String sqlId, Object entity) throws SQLException;

    /**
     * 根据ID删除记录
     *
     * @param sqlId 　sql语句ID
     * @param id
     * @return
     * @throws SQLException
     */
    public Integer deleteById(String sqlId, Object id) throws SQLException;

    /**
     * 根据ids[]批量删除记录
     *
     * @param sqlId 　sql批量删除语句ID
     * @param id
     * @return
     * @throws SQLException
     */
    public Integer deleteByIds(String sqlId, Object[] ids) throws SQLException;

    /**
     * 根据ID查询实体
     *
     * @param sqlId 　根据ID查询实体sql语句ID
     * @param id
     * @throws SQLException
     * @return　实体类
     */
    public Object findById(String sqlId, Object id) throws SQLException;

    /**
     * 根据属性名称查询实体
     *
     * @param sqlId 　sql语句ID
     * @param id
     * @throws SQLException
     * @throws Exception
     * @return　数据列表
     */
    public List findByProperty(String sqlId, Object propertyName) throws SQLException;

    /**
     * 查询记录列表
     *
     * @param sqlId 　查询记录数sql语句ID
     * @throws SQLException
     * @return　数据列表
     */
    public List findList(String sqlId) throws SQLException;

    /**
     * 查询总记录数
     *
     * @param sqlId      　分页总记录数sql语句ID
     * @param pagination
     * @throws SQLException
     * @return　int总记录数
     */
    public int findByCount(String sqlId, Pagination pagination) throws SQLException;

    /**
     * 分页查询数据
     *
     * @param sqlId      　分页列表sql语句ID
     * @param countSqlId 　分页总记录数sql语句ID
     * @param pagination 　分页组件类
     * @throws SQLException
     * @return　Pagination　分页组件类
     */
    public Pagination findByPage(String sqlId, String countSqlId, Pagination pagination) throws SQLException;


}