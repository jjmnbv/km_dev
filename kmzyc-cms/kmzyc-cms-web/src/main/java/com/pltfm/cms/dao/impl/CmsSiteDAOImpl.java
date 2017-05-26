package com.pltfm.cms.dao.impl;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.cms.dao.CmsSiteDAO;
import com.pltfm.cms.vobject.CmsSite;
import com.pltfm.cms.vobject.CmsUser;

import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

@Component(value = "cmsSiteDAO")
public class CmsSiteDAOImpl implements CmsSiteDAO {
    @Resource(name = "sqlMapClient")
    private SqlMapClient sqlMapClient;

    /***
     * 多条件查询站点条数
     */
    public int count(CmsSite cmsSite) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject("CMS_SITE.selectCountByVo", cmsSite);
        return count.intValue();
    }

    /***
     * 多条件查询站点记录
     * @param  站点记录实体
     * @return
     * @throws Exception 异常
     */
    public List select(CmsSite cmsSite) throws SQLException {
        List list = sqlMapClient.queryForList("CMS_SITE.searchPageByVo", cmsSite);
        return list;
    }


    /***
     *
     * 分页查询用户总数
     * */
    public int userCount(CmsUser user) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject("CMS_SITE.selectUserCountByVo", user);
        return count.intValue();
    }

    /***
     *
     * 分页查询用户
     * */
    public List userSelect(CmsUser user) throws SQLException {
        List list = sqlMapClient.queryForList("CMS_SITE.searchUserPageByVo", user);
        return list;
    }

    /***
     *
     * 删除站点
     * */
    public Integer delete(Integer siteId) throws SQLException {
        CmsSite key = new CmsSite();
        key.setSiteId(siteId);
        int rows = sqlMapClient.delete("CMS_SITE.ibatorgenerated_delete", key);
        return rows;
    }

    /***
     *
     * 添加站点记录
     * */
    public Integer insert(CmsSite cmsSite) throws SQLException {
        Object keyObject = sqlMapClient.insert("CMS_SITE.ibatorgenerated_insert", cmsSite);
        return (Integer) keyObject;
    }


    /***
     *
     * 修改站点记录
     * */
    public Integer update(CmsSite cmsSite) throws SQLException {
        Object keyObject = sqlMapClient.update("CMS_SITE.ibatorgenerated_update", cmsSite);
        return (Integer) keyObject;
    }


    /***
     *
     * 是否已存在了该站点
     * */
    public List selectIsName(CmsSite cmsSite) throws SQLException {
        List list = sqlMapClient.queryForList("CMS_SITE.ibatorgenerated_selectIsName", cmsSite);
        return list;
    }

    /***
     *
     * 跟据id查询站点记录
     * */
    public CmsSite selectByPrimaryKey(Integer siteId) throws SQLException {
        CmsSite key = new CmsSite();
        key.setSiteId(siteId);
        CmsSite record = (CmsSite) sqlMapClient.queryForObject("CMS_SITE.ibatorgenerated_select", key);
        return record;
    }


    /***
     *
     * 跟据用户id查询站点记录
     * */
    public CmsUser selectUser(Integer userId) throws SQLException {
        CmsUser key = new CmsUser();
        key.setUserId(userId);
        CmsUser record = (CmsUser) sqlMapClient.queryForObject("CMS_SITE.selectUser", key);
        return record;
    }

    /***
     *
     * 跟据用户id查询角色
     * */
    public List selectUserRolesList(Integer userId) throws SQLException {
        CmsUser key = new CmsUser();
        key.setUserId(userId);
        List list = sqlMapClient.queryForList("CMS_SITE.selectUsersRoleList", key);
        return list;
    }

    /***
     *
     * 查询站点对象
     * */
    public CmsSite querySite(CmsSite site) throws SQLException {
        CmsSite record = (CmsSite) sqlMapClient.queryForObject("CMS_SITE.ibatorgenerated_querySite", site);
        return record;
    }

    /***
     *
     * 查询多站点对象
     * */
    public List querySiteLst(CmsSite site) throws SQLException {
        List list = sqlMapClient.queryForList("CMS_SITE.ibatorgenerated_querySiteAll", site);
        return list;
    }

    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }

    public void setSqlMapClient(SqlMapClient sqlMapClient) {
        this.sqlMapClient = sqlMapClient;
    }
}