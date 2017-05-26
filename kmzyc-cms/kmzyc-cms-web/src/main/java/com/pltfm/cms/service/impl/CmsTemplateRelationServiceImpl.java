package com.pltfm.cms.service.impl;

import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.cms.dao.CmsTemplateRelationDAO;
import com.pltfm.cms.service.CmsTemplateRelationService;
import com.pltfm.cms.vobject.CmsTemplate;
import com.pltfm.cms.vobject.CmsTemplateRelation;
import com.pltfm.cms.vobject.CmsTemplateRelationExample;
import com.pltfm.sys.model.SysUser;

import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

@Component("cmsTemplateRelationService")
public class CmsTemplateRelationServiceImpl implements CmsTemplateRelationService {
    @Resource(name = "cmsTemplateRelationDAO")

    private CmsTemplateRelationDAO cmsTemplateRelationDAO;

    public CmsTemplateRelationDAO getCmsTemplateRelationDAO() {
        return cmsTemplateRelationDAO;
    }

    public void setCmsTemplateRelationDAO(
            CmsTemplateRelationDAO cmsTemplateRelationDAO) {
        this.cmsTemplateRelationDAO = cmsTemplateRelationDAO;
    }

    public void insert(CmsTemplate pTemplate, List<CmsTemplate> sTemplateList, SysUser sysUser) throws Exception {
        for (int i = 0; i < sTemplateList.size(); i++) {
            CmsTemplate sTemplate = sTemplateList.get(i);
            CmsTemplateRelation record = new CmsTemplateRelation();
            record.setpTemplateId(pTemplate.getTemplateId());
            record.setSiteId(pTemplate.getSiteId());
            record.setTemplateType(pTemplate.getType());
            record.setTemplateId(sTemplate.getTemplateId());
            record.setCreated(sysUser.getUserId());
            record.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
            record.setModified(sysUser.getUserId());
            record.setModifyDate(DateTimeUtils.getCalendarInstance().getTime());

            //
            cmsTemplateRelationDAO.insert(record);
        }
    }

    public int deleteByExample(CmsTemplateRelationExample example) throws SQLException {
        return cmsTemplateRelationDAO.deleteByExample(example);
    }

}
