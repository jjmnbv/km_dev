package com.pltfm.cms.dao;

import com.pltfm.cms.vobject.CmsSite;
import com.pltfm.cms.vobject.CmsUser;

import java.sql.SQLException;
import java.util.List;

public interface CmsSiteDAO {
    /***
     *
     * 分页查询站点总数
     * */
    public int count(CmsSite cmsSite) throws SQLException;

    /***
     *
     * 查询多站点对象
     * */
    public List querySiteLst(CmsSite site) throws SQLException;

    /***
     *
     * 分页查询站点
     * */
    public List select(CmsSite cmsSite) throws SQLException;

    /***
     *
     * 跟据用户id查询站点记录
     * */

    public CmsUser selectUser(Integer userId) throws SQLException;

    /***
     *
     * 跟据用户id查询角色
     * */
    public List selectUserRolesList(Integer userId) throws SQLException;

    /***
     *
     * 分页查询用户总数
     * */
    public int userCount(CmsUser user) throws SQLException;

    /***
     *
     * 分页查询用户
     * */
    public List userSelect(CmsUser user) throws SQLException;

    public Integer delete(Integer cmsSite) throws SQLException;

    /***
     *
     * 是否已存在了该站点
     * */
    public List selectIsName(CmsSite cmsSite) throws SQLException;

    public Integer insert(CmsSite cmsSite) throws SQLException;

    /***
     *
     * 跟据id查询站点记录
     * */
    public CmsSite selectByPrimaryKey(Integer siteId) throws SQLException;

    /***
     *
     * 修改站点记录
     * */
    public Integer update(CmsSite cmsSite) throws SQLException;

    /***
     *
     * 查询站点对象
     * */
    public CmsSite querySite(CmsSite site) throws SQLException;
}