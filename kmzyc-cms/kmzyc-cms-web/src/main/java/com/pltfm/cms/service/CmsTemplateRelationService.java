package com.pltfm.cms.service;

import com.pltfm.cms.vobject.CmsTemplate;
import com.pltfm.cms.vobject.CmsTemplateRelationExample;
import com.pltfm.sys.model.SysUser;

import java.sql.SQLException;
import java.util.List;

public interface CmsTemplateRelationService {
    public void insert(CmsTemplate pTemplate, List<CmsTemplate> sTemplateList, SysUser sysUser) throws Exception;

    public int deleteByExample(CmsTemplateRelationExample example) throws SQLException;

}
