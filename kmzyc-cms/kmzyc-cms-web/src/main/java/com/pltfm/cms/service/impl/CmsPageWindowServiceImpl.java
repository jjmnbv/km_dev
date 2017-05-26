package com.pltfm.cms.service.impl;

import com.pltfm.cms.dao.CmsPageDAO;
import com.pltfm.cms.dao.CmsPageWindowDAO;
import com.pltfm.cms.service.CmsPageWindowService;
import com.pltfm.cms.vobject.CmsPage;
import com.pltfm.cms.vobject.CmsPageWindow;
import com.pltfm.cms.vobject.CmsPageWindowQry;
import com.pltfm.sys.util.DatetimeUtil;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

@Component(value = "cmsPageWindowService")
public class CmsPageWindowServiceImpl implements CmsPageWindowService {
    @Resource(name = "cmsPageWindowDAO")
    private CmsPageWindowDAO cmsPageWindowDAO;
    @Resource(name = "cmsPageDAO")
    private CmsPageDAO cmsPageDAO;

    //查询窗口列表
    public List queryWindowList(CmsPageWindowQry qry) throws SQLException {
        return cmsPageWindowDAO.queryWindowList(qry);
    }

    @Override
    @Transactional
    public void add(CmsPageWindow cmsPageWindow) throws Exception {
        // TODO Auto-generated method stub
        this.cmsPageWindowDAO.insert(cmsPageWindow);
    }

    @Override
    @Transactional
    public void addAll(String ids, CmsPageWindow cmsPageWindow) throws Exception {

        String[] id = ids.split(",");
        List list = null;
        for (int i = 0; i < id.length; i++) {
            int winId = Integer.parseInt(id[i]);
            cmsPageWindow.setWindowId(winId);
            list = this.cmsPageWindowDAO.getPageWindowByPwId(cmsPageWindow);
            if (list.size() > 0)
                continue;
            this.add(cmsPageWindow);
        }

        CmsPage cmsPage = cmsPageDAO.selectByPrimaryKey(cmsPageWindow.getPageId());
        //
        //绑定或删除窗口都属于页面的修改
        cmsPage.setStatus(2);
        cmsPage.setModifyDate(DatetimeUtil.getCalendarInstance().getTime());
        cmsPageDAO.updateByPrimaryKey(cmsPage);

    }

    public CmsPageWindowDAO getCmsPageWindowDAO() {
        return cmsPageWindowDAO;
    }

    public void setCmsPageWindowDAO(CmsPageWindowDAO cmsPageWindowDAO) {
        this.cmsPageWindowDAO = cmsPageWindowDAO;
    }

    @Override
    public int delRecord(CmsPageWindow cmsPageWindow) throws Exception {
        // TODO Auto-generated method stub
        int res = this.cmsPageWindowDAO.delPageWindowByPwId(cmsPageWindow);
        return res;
    }

    @Override
    public List queryByWindowId(CmsPageWindow cmsPageWindow) throws Exception {
        // TODO Auto-generated method stub
        return this.cmsPageWindowDAO.getPageWindowByPwId(cmsPageWindow);
    }

    @Override
    public int delAllRecord(String ids, CmsPageWindow cmsPageWindow) throws Exception {
        // TODO Auto-generated method stub
        int res = 1;
        String[] id = ids.split(",");
        for (int i = 0; i < id.length; i++) {
            cmsPageWindow.setWindowId(Integer.valueOf(id[i]));
            res = delRecord(cmsPageWindow);
            if (res < 1)
                return res;
        }
        return res;
    }

    public CmsPageDAO getCmsPageDAO() {
        return cmsPageDAO;
    }

    public void setCmsPageDAO(CmsPageDAO cmsPageDAO) {
        this.cmsPageDAO = cmsPageDAO;
    }

//查询窗口绑定窗口的数据

    public List selectWindInWind(String str) throws SQLException {
        return cmsPageWindowDAO.selectWindInWind(str);
    }

}
