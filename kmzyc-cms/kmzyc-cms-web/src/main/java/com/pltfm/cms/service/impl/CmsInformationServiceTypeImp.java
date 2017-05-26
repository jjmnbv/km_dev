package com.pltfm.cms.service.impl;

import com.kmzyc.commons.page.Page;
import com.pltfm.cms.dao.CmsInformationTypeDAO;
import com.pltfm.cms.service.CmsInformactionTypeService;
import com.pltfm.cms.vobject.CmsInformationType;

import org.springframework.stereotype.Component;

import java.sql.SQLException;

import javax.annotation.Resource;

@Component("cmsInformactionTypeService")
public class CmsInformationServiceTypeImp implements CmsInformactionTypeService {

    @Resource(name = "cmsInformationTypeDAO")
    private CmsInformationTypeDAO cmsInformationTypeDAO;

    @Override
    public Page queryForPage(CmsInformationType information, Page page)
            throws Exception {

        if (page == null) {
            page = new Page();
        }
        if (information == null) {
            information = new CmsInformationType();
        }
        // 根据条件获取广告信息总条数countByExample
        int totalNum = cmsInformationTypeDAO.countByExample(information);

        if (totalNum != 0) {
            page.setRecordCount(totalNum);
            // 设置查询开始结束索引
            information.setStartIndex(page.getStartIndex());
            information.setEndIndex(page.getStartIndex() + page.getPageSize());
            page.setDataList(cmsInformationTypeDAO.queryForPage(information));
        } else {
            page.setRecordCount(0);
            page.setDataList(null);
        }
        return page;

    }

    @Override
    public void add(CmsInformationType information) throws Exception {
        cmsInformationTypeDAO.insert(information);
    }

    @Override
    public CmsInformationType byid(Integer id) throws Exception {

        return cmsInformationTypeDAO.byid(id);
    }

    @Override
    public Integer update(CmsInformationType information) throws Exception {
        // TODO Auto-generated method stub
        return cmsInformationTypeDAO.updateByPrimaryKeySelective(information);
    }

    @Override
    public Integer delete(Integer id) throws Exception {
        // TODO Auto-generated method stub
        int count = cmsInformationTypeDAO.byInforId(id);
        if (count == 0) {
            return cmsInformationTypeDAO.deleteByPrimaryKey(id);
        } else {
            return 0;
        }
    }

    public CmsInformationTypeDAO getCmsInformationTypeDAO() {
        return cmsInformationTypeDAO;
    }

    public void setCmsInformationTypeDAO(CmsInformationTypeDAO cmsInformationTypeDAO) {
        this.cmsInformationTypeDAO = cmsInformationTypeDAO;
    }

    @Override
    public Integer byInforId(Integer id) throws Exception {
        // TODO Auto-generated method stub
        return cmsInformationTypeDAO.byInforId(id);
    }

    @Override
    public Integer countByName(CmsInformationType information) throws Exception {
        // TODO Auto-generated method stub
        return cmsInformationTypeDAO.countByName(information);
    }

    @Override
    public CmsInformationType queryInfoTypeByTypeCode(
            CmsInformationType cmsInformationType) throws SQLException {
        // TODO Auto-generated method stub
        return cmsInformationTypeDAO.queryInfoTypeByTypeCode(cmsInformationType);
    }


}
