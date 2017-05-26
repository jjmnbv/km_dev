package com.pltfm.cms.service.impl;

import com.kmzyc.commons.page.Page;
import com.pltfm.cms.dao.CmsWindowDAO;
import com.pltfm.cms.dao.CmsWindowDataDAO;
import com.pltfm.cms.parse.PathConstants;
import com.pltfm.cms.service.CmsPageWindowService;
import com.pltfm.cms.service.CmsWindowService;
import com.pltfm.cms.util.FileOperateUtils;
import com.pltfm.cms.vobject.CmsPageWindow;
import com.pltfm.cms.vobject.CmsWindow;
import com.pltfm.cms.vobject.CmsWindowData;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

/**
 * 窗口业务逻辑层接口实现类
 *
 * @author cjm
 * @since 2013-9-10
 */
@Component(value = "cmsWindowService")
public class CmsWindowServiceImpl implements CmsWindowService {
    /**
     * 窗口DAO层接口
     */
    @Resource(name = "cmsWindowDAO")
    private CmsWindowDAO cmsWindowDAO;
    @Resource(name = "cmsPageWindowService")
    private CmsPageWindowService cmsPageWindowService;
    @Resource(name = "cmsWindowDataDAO")
    private CmsWindowDataDAO cmsWindowDataDAO;

    public CmsWindowDataDAO getCmsWindowDataDAO() {
        return cmsWindowDataDAO;
    }

    public void setCmsWindowDataDAO(CmsWindowDataDAO cmsWindowDataDAO) {
        this.cmsWindowDataDAO = cmsWindowDataDAO;
    }

    /**
     * 分页查询窗口信息
     *
     * @param cmsWindowData 窗口信息实体
     * @param page          分页实体
     * @throws Exception 异常
     * @return 返回值
     */
    @Override
    public Page queryForPage(CmsWindow cmsWindow, Page page) throws Exception {
        if (page == null) {
            page = new Page();
        }
        if (cmsWindow == null) {
            cmsWindow = new CmsWindow();
        }
        // 根据条件获取窗口数据信息总条数
        int totalNum = cmsWindowDAO.countByCmsWindowData(cmsWindow);
        if (totalNum != 0) {
            page.setRecordCount(totalNum);
            // 设置查询开始结束索引
            cmsWindow.setStartIndex(page.getStartIndex());
            cmsWindow.setEndIndex(page.getStartIndex() + page.getPageSize());
            page.setDataList(cmsWindowDAO.queryForPage(cmsWindow));
        } else {
            page.setRecordCount(0);
            page.setDataList(null);
        }
        return page;
    }

    /**
     * 根据窗口信息主键查询单条活动信息
     *
     * @param viewPromotionId 窗口信息主键
     * @throws SQLException sql异常
     * @return 返回值
     */
    @Override
    public CmsWindow selectByPrimaryKey(Integer cmsWindowId) throws Exception {
        return cmsWindowDAO.selectByPrimaryKey(cmsWindowId);
    }

    public CmsWindowDAO getCmsWindowDAO() {
        return cmsWindowDAO;
    }

    public void setCmsWindowDAO(CmsWindowDAO cmsWindowDAO) {
        this.cmsWindowDAO = cmsWindowDAO;
    }


    public CmsPageWindowService getCmsPageWindowService() {
        return cmsPageWindowService;
    }

    public void setCmsPageWindowService(CmsPageWindowService cmsPageWindowService) {
        this.cmsPageWindowService = cmsPageWindowService;
    }

    @Override
    @Transactional
    public int addCmsWindow(CmsWindow cmsWindow) throws Exception {
        // TODO Auto-generated method stub
        //插入cmsWindow数据
        int res = this.cmsWindowDAO.insert(cmsWindow);

        //取到路径并生成相应的html文件
        String path = PathConstants.windowTemPath(cmsWindow.getSiteId());
        FileOperateUtils.checkAndCreateDirs(path);
        File wFile = new File(path, cmsWindow.getWindowId() + ".html");


        FileUtils.writeStringToFile(wFile, cmsWindow.getContent(), "utf-8");
        return res;
    }

    @Override
    @Transactional
    public CmsWindow getWindowById(Integer id) throws Exception {
        // TODO Auto-generated method stub
        //查询cmsWindow
        CmsWindow cmsWindow = this.cmsWindowDAO.selectByPrimaryKey(id);
        //从相应的html文件中获取到内容并复制给cmsWindow的content
        String path = PathConstants.windowTemPath(cmsWindow.getSiteId());
        File file = new File(path, cmsWindow.getWindowId() + ".html");
        if (file.exists()) {
            cmsWindow.setContent(FileUtils.readFileToString(file, "utf-8"));
        }
        return cmsWindow;
    }

    @Override
    @Transactional
    public int updateWindow(CmsWindow cmsWindow) throws Exception {
        // TODO Auto-generated method stub
        //返回要修改的CmsWindow对象,并修改
        CmsWindow window = this.cmsWindowDAO.selectByPrimaryKey(cmsWindow.getWindowId());
        cmsWindow.setCreated(window.getCreated());
        cmsWindow.setCreateDate(window.getCreateDate());
        int res = this.cmsWindowDAO.updateByPrimaryKey(cmsWindow);
        String path = PathConstants.windowTemPath(window.getSiteId());
        FileOperateUtils.checkAndCreateDirs(path);
        File file = new File(path, window.getWindowId() + ".html");
        FileUtils.writeStringToFile(file, cmsWindow.getContent(), "utf-8");
        return res;
    }

    @Override
    @Transactional
    public int delAllWindow(String ids) throws Exception {
        // TODO Auto-generated method stub
        int res = 1;
        String[] id = ids.split(",");
        for (int i = 0; i < id.length; i++) {
            res = delWindowById(Integer.valueOf(id[i]));
            if (res == 0) {
                return res;
            }
        }
        return res;
    }

    @Override
    @Transactional
    public int delWindowById(Integer cmsWindowId) throws Exception {
        // TODO Auto-generated method stub
        CmsWindow cmsWindow = this.cmsWindowDAO.selectByPrimaryKey(cmsWindowId);
        //删除该窗口
        int res = this.cmsWindowDAO.deleteByPrimaryKey(cmsWindowId);
        if (res == 0)
            return res;

        //删除页面绑定该窗口的数据
        CmsPageWindow cmsPageWindow = new CmsPageWindow();
        cmsPageWindow.setWindowId(cmsWindowId);
        List list = this.cmsPageWindowService.queryByWindowId(cmsPageWindow);
        for (int i = 0; i < list.size(); i++) {
            this.cmsPageWindowService.delRecord(cmsPageWindow);
        }

        //删除该窗口绑定的数据
        CmsWindowData cmsWindowData = new CmsWindowData();
        cmsWindowData.setWindowId(cmsWindowId);
        this.cmsWindowDataDAO.deleteByCmsWindowData(cmsWindowData);

        //输出该窗口相关的文件
        String path = PathConstants.windowTemPath(cmsWindow.getSiteId());
        File file = new File(path, cmsWindowId + ".html");
        if (file.exists())
            file.delete();
        return res;
    }

    @Override
    public Page getWindow_NotIn(CmsWindow cmsWindow, Page page, Integer pageId) throws Exception {
        // TODO Auto-generated method stub
        if (page == null) {
            page = new Page();
        }
        if (cmsWindow == null) {
            cmsWindow = new CmsWindow();
        }
        cmsWindow.setPageId(pageId);
        // 根据条件获取窗口数据信息总条数
        int totalNum = cmsWindowDAO.countByPageId_NotIn(cmsWindow);
        if (totalNum != 0) {
            page.setRecordCount(totalNum);
            // 设置查询开始结束索引
            cmsWindow.setStartIndex(page.getStartIndex());
            cmsWindow.setEndIndex(page.getStartIndex() + page.getPageSize());
            page.setDataList(cmsWindowDAO.getWindowByPageId_NotIn(cmsWindow));
        } else {
            page.setRecordCount(0);
            page.setDataList(null);
        }
        return page;
    }

    @Override
    public Page getWindow_In(CmsWindow cmsWindow, Page page, Integer pageId)
            throws Exception {
        // TODO Auto-generated method stub
        if (page == null) {
            page = new Page();
        }
        if (cmsWindow == null) {
            cmsWindow = new CmsWindow();
        }
        cmsWindow.setPageId(pageId);
        // 根据条件获取窗口数据信息总条数
        int totalNum = cmsWindowDAO.countByPageId_In(pageId);
        if (totalNum != 0) {
            page.setRecordCount(totalNum);
            // 设置查询开始结束索引
            cmsWindow.setStartIndex(page.getStartIndex());
            cmsWindow.setEndIndex(page.getStartIndex() + page.getPageSize());
            page.setDataList(cmsWindowDAO.getWindowByPageId_In(cmsWindow));
        } else {
            page.setRecordCount(0);
            page.setDataList(null);
        }
        return page;
    }

    @Override
    public List getByName(CmsWindow cmsWindow) throws Exception {
        // TODO Auto-generated method stub
        return this.cmsWindowDAO.getWindow(cmsWindow);
    }

    @Override
    public String checkName(CmsWindow cmsWindow) throws Exception {
        // TODO Auto-generated method stub
        if (cmsWindow.getWindowId() != null) {
            CmsWindow window = this.getWindowById(cmsWindow.getWindowId());
            if (!window.getName().equals(cmsWindow.getName())) {
                List list = this.getByName(cmsWindow);
                if (list.size() > 0)
                    return "fail";
            }
        } else {
            List list = this.getByName(cmsWindow);
            if (list.size() > 0)
                return "fail";
        }
        return "success";
    }


}
