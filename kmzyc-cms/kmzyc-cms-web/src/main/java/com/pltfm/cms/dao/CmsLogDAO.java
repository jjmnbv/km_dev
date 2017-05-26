package com.pltfm.cms.dao;

import com.pltfm.cms.vobject.CmsLog;

import java.sql.SQLException;
import java.util.List;

public interface CmsLogDAO {
    public int count(CmsLog example) throws SQLException;

    public List select(CmsLog example) throws SQLException;

    public Integer delete(Integer cmsLogId) throws SQLException;


    public Integer insert(CmsLog record) throws SQLException;

    public CmsLog selectByPrimaryKey(Integer cmsLogId) throws SQLException;

}