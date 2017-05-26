package com.pltfm.cms.service.impl;

import com.kmzyc.commons.page.Page;
import com.pltfm.cms.dao.CmsThemeTemplateDAO;
import com.pltfm.cms.service.CmsThemeTemplateService;
import com.pltfm.cms.vobject.CmsTemplate;
import com.pltfm.cms.vobject.CmsThemeTemplate;

import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

@Component(value = "cmsThemeTemplateService")
public class CmsThemeTemplateServiceImpl implements CmsThemeTemplateService {

    @Resource(name = "cmsThemeTemplateDAO")
    CmsThemeTemplateDAO cmsThemeTemplateDao;

    public CmsThemeTemplateDAO getCmsThemeTemplateDao() {
        return cmsThemeTemplateDao;
    }

    public void setCmsThemeTemplateDao(CmsThemeTemplateDAO cmsThemeTemplateDao) {
        this.cmsThemeTemplateDao = cmsThemeTemplateDao;
    }

    @Override
    public Integer queryThemeTempCount(CmsThemeTemplate cmsThemeTemplate)
            throws SQLException {
        // TODO Auto-generated method stub
        return cmsThemeTemplateDao.queryThemeTempCount(cmsThemeTemplate);
    }

    @Override
    public Page queryThemeTempList(CmsThemeTemplate cmsThemeTemplate, Page page)
            throws SQLException {
        //	return cmsThemeTemplateDao.queryThemeTempList(cmsThemeTemplate);


        if (page == null) {
            page = new Page();
        }

        int totalNum = cmsThemeTemplateDao.queryThemeTempCount(cmsThemeTemplate);
        if (totalNum != 0) {
            page.setRecordCount(totalNum);
            // 设置查询开始结束索引
            cmsThemeTemplate.setStartIndex(page.getStartIndex());
            cmsThemeTemplate.setEndIndex(page.getStartIndex() + page.getPageSize());
            page.setDataList(cmsThemeTemplateDao.queryThemeTempList(cmsThemeTemplate));
        } else {
            page.setRecordCount(0);
            page.setDataList(null);
        }
        return page;

    }


    /**
     * 根据主题Id和模板类型查询模板Id
     *
     * @param cmsThemeTemplate 主题模板
     * @return 主题模板的集合
     */
    @Override
    public List<CmsThemeTemplate> queryByThemeTemp(CmsThemeTemplate cmsThemeTemplate)
            throws Exception {
        return cmsThemeTemplateDao.queryByThemeTemp(cmsThemeTemplate);
    }

    @Override
    public void insert(List<CmsTemplate> tempList, CmsThemeTemplate record) throws SQLException {
        for (int i = 0; i < tempList.size(); i++) {
            CmsTemplate temp = tempList.get(i);
            record.setTemplateId(temp.getTemplateId());
            record.setType(temp.getType());
            cmsThemeTemplateDao.insert(record);
        }
    }

    @Override
    public int deleteByPrimaryKey(Integer themeTemplateId) throws SQLException {

        return cmsThemeTemplateDao.deleteByPrimaryKey(themeTemplateId);
    }

    public void delBandDatas(List<Integer> ids) throws SQLException {

        for (int i = 0; i < ids.size(); i++) {
            cmsThemeTemplateDao.deleteByPrimaryKey(ids.get(i));
        }
    }


}
