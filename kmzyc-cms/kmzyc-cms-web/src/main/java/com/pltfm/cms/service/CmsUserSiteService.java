package com.pltfm.cms.service;

import com.kmzyc.commons.page.Page;
import com.pltfm.cms.vobject.CmsUserSite;
import com.pltfm.cms.vobject.CmsUserSiteShow;

import java.util.List;

/***
 *
 * CmsUserSiteService
 * */
public interface CmsUserSiteService {
    /**
     * 分页查询用户与站点信息
     *
     * @param cmsUserSite 用户与站点信息实体
     * @param page        分页实体
     * @throws Exception 异常
     * @return 返回值
     */
    Page queryForPage(CmsUserSite cmsUserSite, Page page) throws Exception;

    /**
     * 跟据站点查询用户
     */
    public List<CmsUserSite> selUse(Integer siteId) throws Exception;

    /***
     *
     * 删除站点用户记录
     * */
    public Integer delete(List<Integer> siteIds) throws Exception;

    /***
     *
     * 跟据用户Id查询用户所有的站点
     * */
    public List cmsUserSite(Integer id) throws Exception;

    /***
     *
     * 添加站点用户记录
     * */
    public Integer insert(CmsUserSite cmsUserSite) throws Exception;

    /***
     *
     * 跟据站点id查询是否关系到用户站点表
     * */

    public Integer checkUserSite(Integer siteId) throws Exception;

    /***
     *
     * 保存用户选择站点记录
     * */
    public Integer insert(List<Integer> siteIds, int userid) throws Exception;

    /***
     *
     * 保存站点选择用户记录
     * userid 用户id集合
     * siteId 站点id
     * id 前当操作员id
     * */
    public Integer addUuseStie(List<Integer> userid, int siteId, int id) throws Exception;

    /***
     *
     * 查询用户下站点
     * */

    public String selectStieName(Integer userid) throws Exception;

    /***
     *
     * 修改站点用户记录
     * */
    public Integer update(CmsUserSite cmsUserSite) throws Exception;

    /***
     *
     * 跟据id查询站点用户记录
     * */
    public CmsUserSite selectByPrimaryKey(Integer siteId) throws Exception;

    /***
     * 多条件查询站点记录
     *
     * @param 站点记录实体
     * @return
     * @throws Exception
     *             异常
     */
    public Page searchPageByVo(Page pageParam, CmsUserSite cmsUserSite)
            throws Exception;

    /**
     * 查询用户所拥有的站点信息
     *
     * @param userSite 用户站点对应实体
     * @return 所有用户所拥有的站点信息
     * @throws Exception 异常信息
     */
    public List<CmsUserSiteShow> queryUserSiteList(CmsUserSite userSite) throws Exception;
}
