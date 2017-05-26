package com.pltfm.cms.dao.impl;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.cms.dao.CmsLogDAO;
import com.pltfm.cms.vobject.CmsLog;

import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

@Component(value = "cmsLogDAO")
public class CmsLogDAOImpl implements CmsLogDAO {

    @Resource(name = "sqlMapClient")
    private SqlMapClient sqlMapClient;

    /***
     * 多条件查询日志条数
     */
    public int count(CmsLog example) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject("CMS_LOG.selectCountByVo", example);
        return count.intValue();
    }

    /***
     * 多条件查询日志记录
     * @param  日志记录实体
     * @return
     * @throws Exception 异常
     */
    public List select(CmsLog example) throws SQLException {
        List list = sqlMapClient.queryForList("CMS_LOG.searchPageByVo", example);
        return list;
    }

    /***
     *
     * 删除日志
     * */
    public Integer delete(Integer cmsLogId) throws SQLException {
        CmsLog key = new CmsLog();
        key.setCmsLogId(cmsLogId);
        int rows = sqlMapClient.delete("CMS_LOG.ibatorgenerated_delete", key);
        return rows;
    }

    /***
     *
     * 添加日志记录
     * */
    public Integer insert(CmsLog record) throws SQLException {
        Object keyObject = sqlMapClient.insert("CMS_LOG.ibatorgenerated_insert", record);
        return (Integer) keyObject;
    }

    /***
     *
     * 跟据id查询日志记录
     * */
    public CmsLog selectByPrimaryKey(Integer cmsLogId) throws SQLException {
        CmsLog key = new CmsLog();
        key.setCmsLogId(cmsLogId);
        CmsLog record = (CmsLog) sqlMapClient.queryForObject("CMS_LOG.ibatorgenerated_select", key);
        return record;
    }
}