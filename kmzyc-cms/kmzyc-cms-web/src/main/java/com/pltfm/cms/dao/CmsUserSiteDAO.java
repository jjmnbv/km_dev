package com.pltfm.cms.dao;


import com.pltfm.cms.vobject.CmsUserSite;
import com.pltfm.cms.vobject.CmsUserSiteShow;

import java.sql.SQLException;
import java.util.List;

/***
 *
 * 站点与用户的CmsUserSiteDAO 
 * */
public interface CmsUserSiteDAO {

    /***
     *
     * 删除站点与用户
     * */
    Integer delete(Integer userSiteId) throws SQLException;

    /***
     *
     * 查询用户下站点
     * */

    public List selectStieName(Integer userid) throws SQLException;

    /***
     *
     * 跟据用户Id查询用户所有的站点
     * */
    public List cmsUserSite(Integer id) throws SQLException;

    /***
     *
     * 添加站点与用户
     * */
    Integer insert(CmsUserSite record) throws SQLException;

    /***
     *
     * 跟据站点id查询是否关系到用户站点表
     * */

    public Integer checkUserSite(Integer id) throws SQLException;

    /**
     * 跟据站点查询用户信息
     */
    public List<CmsUserSite> selUse(Integer siteId) throws SQLException;

    /***
     *
     * 跟据id查询站点与用户
     * */
    CmsUserSite selectById(Integer userSiteId) throws SQLException;

    /***
     *
     * 修改查询站点与用户
     * */
    Integer update(CmsUserSite record) throws SQLException;

    /***
     *
     * 通过多条件查询统数据总条数
     * */
    public Integer count(CmsUserSite cmsUserSite) throws SQLException;

    /***
     *
     * 分页查询
     * */
    public List select(CmsUserSite cmsUserSite) throws SQLException;

    List selectByUserId(int userid) throws SQLException;

    /**
     * 用户站点信息总数量
     *
     * @param cmsUserSite 用户站点信息实体
     * @throws SQLException 异常
     * @return 返回值
     */
    Integer countByCmsUserSite(CmsUserSite cmsUserSite) throws SQLException;

    /**
     * 分页查询用户站点信息
     *
     * @param cmsUserSite 用户站点信息实体
     * @throws SQLException 异常
     * @return 返回值
     */
    List queryForPage(CmsUserSite cmsUserSite) throws SQLException;

    /**
     * 查询用户所拥有的站点信息
     *
     * @param userSite 用户站点对应实体
     * @return 所有用户所拥有的站点信息
     * @throws Exception 异常信息
     */
    public List<CmsUserSiteShow> queryUserSiteList(CmsUserSite userSite) throws SQLException;

    /**
     * 查询用户所拥有的站点的总数
     *
     * @param userSite 用户站点对应实体
     * @return 所有用户所拥有的站点信息
     * @throws Exception 异常信息
     */
    public Integer queryUserSiteCount(CmsUserSite userSite) throws SQLException;
}