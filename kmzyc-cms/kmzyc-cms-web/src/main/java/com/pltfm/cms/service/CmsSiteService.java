package com.pltfm.cms.service;

import com.kmzyc.commons.page.Page;
import com.pltfm.cms.vobject.CmsSite;
import com.pltfm.cms.vobject.CmsUser;

import java.util.List;

/***
 * 站点CmsSiteService
 */
public interface CmsSiteService {

    /***
     * 多条件查询站点记录
     *
     * @param 站点记录实体
     * @return
     * @throws Exception
     *             异常
     */
    public Page searchPageByVo(Page pageParam, CmsSite cmsSite)
            throws Exception;

    /***
     *
     * 查询多站点对象
     * */
    public List querySiteLst(CmsSite site) throws Exception;

    /***
     * 多条件查询用户记录
     *
     */
    public Page searchUserByVo(Page pageParam, CmsUser vo) throws Exception;

    /***
     *
     * 跟据用户id查询站点记录
     * */
    public CmsUser selectUser(Integer userId) throws Exception;

    /***
     *
     * 删除站点
     * */
    public Integer delete(List<Integer> siteIds) throws Exception;

    /***
     *
     * 删除站点与用户，站点与数据
     * */
    public Integer delUserTite(String userSiteId) throws Exception;

    /***
     *
     * 是否已存在了该站点
     * */
    public List selectIsName(CmsSite cmsSite) throws Exception;

    /***
     *
     * 添加站点记录
     * */
    public Integer insert(CmsSite cmsSite) throws Exception;

    /***
     *
     * 修改站点记录
     * */
    public Integer update(CmsSite cmsSite) throws Exception;

    /***
     *
     * 跟据id查询站点记录
     * */
    public CmsSite selectByPrimaryKey(Integer siteId) throws Exception;

    /***
     *
     * 查询站点对象
     * */
    public CmsSite querySite(CmsSite site) throws Exception;
}
