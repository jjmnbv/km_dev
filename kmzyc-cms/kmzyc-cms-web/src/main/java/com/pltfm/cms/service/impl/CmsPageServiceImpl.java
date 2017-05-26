package com.pltfm.cms.service.impl;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ActionContext;
import com.pltfm.app.util.Constants;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.cms.dao.CmsPageDAO;
import com.pltfm.cms.dao.CmsPageWindowDAO;
import com.pltfm.cms.dao.CmsTemplateDAO;
import com.pltfm.cms.dao.CmsThemeTemplateDAO;
import com.pltfm.cms.dao.CmsWindowDAO;
import com.pltfm.cms.dao.CmsWindowDataDAO;
import com.pltfm.cms.parse.PathConstants;
import com.pltfm.cms.service.CmsPageService;
import com.pltfm.cms.service.CmsPageWindowService;
import com.pltfm.cms.service.CmsTemplateServce;
import com.pltfm.cms.service.CmsThemeTemplateService;
import com.pltfm.cms.service.CmsWindowService;
import com.pltfm.cms.util.FileOperateUtils;
import com.pltfm.cms.util.SqlJoinUtil;
import com.pltfm.cms.vobject.CmsPage;
import com.pltfm.cms.vobject.CmsPageWindow;
import com.pltfm.cms.vobject.CmsPageWindowQry;
import com.pltfm.cms.vobject.CmsTemplate;
import com.pltfm.cms.vobject.CmsTheme;
import com.pltfm.cms.vobject.CmsThemeTemplate;
import com.pltfm.cms.vobject.CmsWindow;
import com.pltfm.cms.vobject.CmsWindowData;
import com.pltfm.sys.model.SysUser;

@Component("cmsPageService")
public class CmsPageServiceImpl implements CmsPageService {

    @Resource(name = "cmsPageWindowService")
    private CmsPageWindowService cmsPageWindowService;
    @Resource(name = "cmsPageDAO")
    private CmsPageDAO cmsPageDAO;

    /**
     * 窗口DAO层接口
     */
    @Resource(name = "cmsWindowDAO")
    private CmsWindowDAO cmsWindowDAO;

    @Resource(name = "cmsPageWindowDAO")
    private CmsPageWindowDAO cmsPageWindowDAO;
    // 窗口业务逻辑层接口
    @Resource(name = "cmsWindowService")
    private CmsWindowService cmsWindowService;
    // 模板业务逻辑层接口
    @Resource(name = "cmsTemplateServce")
    private CmsTemplateServce cmsTemplateServce;

    // 模板业务逻辑层接口
    @Resource(name = "cmsTemplateDAO")
    private CmsTemplateDAO cmsTemplateDAO;

    // 主题模板业务逻辑层接口
    @Resource(name = "cmsThemeTemplateService")
    private CmsThemeTemplateService cmsThemeTemplateService;

    @Resource(name = "cmsWindowDataDAO")
    private CmsWindowDataDAO cmsWindowDataDAO;

    @Resource(name = "cmsThemeTemplateDAO")
    private CmsThemeTemplateDAO cmsThemeTemplateDAO;

    public CmsPageDAO getCmsPageDAO() {
        return cmsPageDAO;
    }

    public void setCmsPageDAO(CmsPageDAO cmsPageDAO) {
        this.cmsPageDAO = cmsPageDAO;
    }

    public CmsPageWindowService getCmsPageWindowService() {
        return cmsPageWindowService;
    }

    public void setCmsPageWindowService(
            CmsPageWindowService cmsPageWindowService) {
        this.cmsPageWindowService = cmsPageWindowService;
    }

    @Override
    public Page getAllPage(CmsPage cmsPage, Page page) throws Exception {
        // TODO Auto-generated method stub
        if (page == null)
            page = new Page();
        if (cmsPage == null) {
            cmsPage = new CmsPage();
        }
        int totalNum = cmsPageDAO.countByCmsPage(cmsPage);
        if (totalNum != 0) {
            page.setRecordCount(totalNum);
            // 设置查询开始结束索引
            cmsPage.setStartIndex(page.getStartIndex());
            cmsPage.setEndIndex(page.getStartIndex() + page.getPageSize());
            page.setDataList(cmsPageDAO.getAllPage(cmsPage));
        } else {
            page.setRecordCount(0);
            page.setDataList(null);
        }
        return page;
    }

    @Override
    @Transactional
    public boolean addPage(CmsPage cmsPage) throws Exception {
        // TODO Auto-generated method stub
        // 如果为空，则默认为未发布
        if (cmsPage.getStatus() == null)
            cmsPage.setStatus(0);
        this.cmsPageDAO.insert(cmsPage);
        String path = PathConstants.pageTemPath(cmsPage.getSiteId());
        // 判断pages文件夹是否存在，若不存在，则新建一个
        FileOperateUtils.checkAndCreateDirs(path);
        // 写入文件
        File pageFile = null;
        // if(cmsPage.getPublicType()==4){
        // pageFile=new File(path+"/"+"detail.html");
        // }else
        if (cmsPage.getPublicType() == 13) {
            // 获取当前管理员账号
            Map session = ActionContext.getContext().getSession();
            SysUser sysUser = (SysUser) session.get("sysUser");
            Integer siteId = (Integer) session.get("siteId");
            CmsTemplate cmsTemplate = new CmsTemplate();
            cmsTemplate.setSiteId(siteId);
            cmsTemplate.setType(16);
            List<CmsTemplate> cttList = cmsTemplateServce
                    .selectBySiteIdType(cmsTemplate);
            for (CmsTemplate ctt : cttList) {
                CmsTemplate cmsTempl = cmsTemplateServce.getTemplateById(ctt
                        .getTemplateId());
                CmsWindow cmsWindow = new CmsWindow();
                cmsWindow.setContent(cmsTempl.getContent());
                cmsWindow.setName(cmsTempl.getName()
                        + cmsPage.getPageId().toString());
                cmsWindow.setTheme(cmsTempl.getTheme()
                        + cmsPage.getPageId().toString());
                cmsWindow.setParamsType(1);
                cmsWindow.setRemark(cmsTemplate.getRemark());
                // 站点ID
                cmsWindow.setSiteId(siteId);
                // 创建时间以及创建人
                cmsWindow.setCreated(sysUser.getUserId());
                cmsWindow.setCreateDate(DateTimeUtils.getCalendarInstance()
                        .getTime());
                cmsWindow.setStatus(0);
                cmsWindowService.addCmsWindow(cmsWindow);

                CmsPageWindow cmsPageWindow = new CmsPageWindow();
                cmsPageWindow.setPageId(cmsPage.getPageId());
                cmsPageWindow.setWindowId(cmsWindow.getWindowId());
                cmsPageWindowService.add(cmsPageWindow);
                cmsPage.setContent(cmsPage.getContent().replaceAll("#KM#",
                        cmsPage.getPageId().toString()));

            }
        }
        pageFile = new File(path + "/" + cmsPage.getPageId() + ".html");
        FileUtils.writeStringToFile(pageFile, cmsPage.getContent(), "utf-8");
        return true;
    }

    @Override
    public List selectBpageId(CmsPage cmsPage) throws Exception {
        return cmsPageDAO.selectBpageId(cmsPage);
    }

    @Override
    @Transactional
    public boolean addTheme(CmsPage cmsPage, CmsTheme cmsTheme)
            throws Exception {
        // TODO Auto-generated method stub
        // 如果为空，则默认为未发布
        if (cmsPage.getStatus() == null)
            cmsPage.setStatus(0);
        this.cmsPageDAO.insert(cmsPage);
        String path = PathConstants.pageTemPath(cmsPage.getSiteId());
        // 判断pages文件夹是否存在，若不存在，则新建一个
        FileOperateUtils.checkAndCreateDirs(path);
        CmsThemeTemplate cmsThemeTemplate = new CmsThemeTemplate();
        cmsThemeTemplate.setThemeId(cmsTheme.getThemeId());
        // 窗口模板
        cmsThemeTemplate.setType(13);
        List<CmsThemeTemplate> cttList = cmsThemeTemplateService
                .queryByThemeTemp(cmsThemeTemplate);
        for (CmsThemeTemplate ctt : cttList) {

            CmsTemplate cmsTemplate = cmsTemplateServce.getTemplateById(ctt
                    .getTemplateId());

            createWindowAndRation(null, cmsTemplate, cmsPage);

        }
        // 写入文件
        File pageFile = null;
        cmsPage.setContent(cmsPage.getContent().replaceAll("#KM#",
                cmsPage.getPageId().toString()));
        pageFile = new File(path + "/" + cmsPage.getPageId() + ".html");
        FileUtils.writeStringToFile(pageFile, cmsPage.getContent(), "utf-8");
        return true;
    }

    // 添加供应商paot头部
    @Override
    @Transactional
    public boolean addShopTheme(CmsPage cmsPage, CmsTheme cmsTheme)
            throws Exception {
        // TODO Auto-generated method stub
        // 如果为空，则默认为未发布
        if (cmsPage.getStatus() == null)
            cmsPage.setStatus(0);
        this.cmsPageDAO.insert(cmsPage);
        String path = PathConstants.pageTemPath(cmsPage.getSiteId());
        // 判断pages文件夹是否存在，若不存在，则新建一个
        FileOperateUtils.checkAndCreateDirs(path);
        CmsThemeTemplate cmsThemeTemplate = new CmsThemeTemplate();
        cmsThemeTemplate.setThemeId(cmsTheme.getThemeId());
        // 窗口模板
        cmsThemeTemplate.setType(13);
        List<CmsThemeTemplate> cttList = cmsThemeTemplateService
                .queryByThemeTemp(cmsThemeTemplate);
        for (CmsThemeTemplate ctt : cttList) {

            CmsTemplate cmsTemplate = cmsTemplateServce.getTemplateById(ctt
                    .getTemplateId());

            createShopWindowAndRation(null, cmsTemplate, cmsPage);

        }
        // 写入文件
        File pageFile = null;
        cmsPage.setContent(cmsPage.getContent().replaceAll("#KM#",
                cmsPage.getPageId().toString()));
        pageFile = new File(path + "/" + cmsPage.getPageId() + ".html");
        FileUtils.writeStringToFile(pageFile, cmsPage.getContent(), "utf-8");
        return true;
    }

    // 供应商窗口与窗口关系建立
    public void createShopWindowAndRation(CmsWindow cmsPWindow,
                                          CmsTemplate cmsTemplate, CmsPage cmsPage) throws Exception {

        CmsWindow cmsWindow = new CmsWindow();

        // StringBuffer content=new StringBuffer(cmsWindow.getContent());
        cmsWindow.setContent(cmsTemplate.getContent());

        cmsWindow.setName(cmsTemplate.getName()
                + cmsPage.getPageId().toString());
        cmsWindow.setTheme(cmsTemplate.getTheme()
                + cmsPage.getPageId().toString());
        cmsWindow.setParamsType(1);
        cmsWindow.setTemplateId(cmsTemplate.getTemplateId());
        cmsWindow.setRemark(cmsTemplate.getRemark());
        // 获取当前管理员账号
        // Map session = ActionContext.getContext().getSession();

        Integer siteId = Constants.SET_B2B_ID;
        // 站点ID
        cmsWindow.setSiteId(siteId);
        // 创建时间以及创建人
        cmsWindow.setCreated(Constants.USER_B2B_ID);
        cmsWindow.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
        cmsWindow.setStatus(0);

        if (cmsWindow.getContent().indexOf("#KM#") > 0) {
            cmsWindow.setContent(cmsWindow.getContent().replaceAll("#KM#",
                    cmsPage.getPageId().toString()));
        }
        cmsWindowService.addCmsWindow(cmsWindow);

        // 如果父窗口存在，建立窗口与窗口关系
        if (cmsPWindow != null) {

            CmsWindowData windowData = new CmsWindowData();
            windowData.setSiteId(cmsPWindow.getSiteId());
            // 窗口
            windowData.setDataType(4);
            windowData.setWindowId(cmsPWindow.getWindowId());
            windowData.setDataId(cmsWindow.getWindowId());
            // 创建时间以及创建人
            windowData.setCreated(Constants.USER_B2B_ID);
            windowData.setCreateDate(DateTimeUtils.getCalendarInstance()
                    .getTime());
            windowData.setStatus(0);
            cmsWindowDataDAO.insert(windowData);
            // 不存在，建立页面与窗口关系
        } else {
            CmsPageWindow cmsPageWindow = new CmsPageWindow();
            cmsPageWindow.setPageId(cmsPage.getPageId());
            cmsPageWindow.setWindowId(cmsWindow.getWindowId());
            cmsPageWindowService.add(cmsPageWindow);

        }

        // 子窗口列表
        cmsTemplate.setEndIndex(100);
        cmsTemplate.setStartIndex(0);
        List list = cmsTemplateDAO.queryBandWindowList(cmsTemplate);
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                CmsTemplate temp2 = (CmsTemplate) list.get(i);
                temp2 = cmsTemplateServce
                        .getTemplateById(temp2.getTemplateId());
                createShopWindowAndRation(cmsWindow, temp2, cmsPage);
            }
        }

    }

    @Override
    @Transactional
    // 添加供应商paot头部
    public Integer addOthTop(CmsPage cmsPage, Integer pid) throws Exception {

        // 如果为空，则默认为未发布
        if (cmsPage.getStatus() == null)
            cmsPage.setStatus(0);
        this.cmsPageDAO.insert(cmsPage);

        String path = PathConstants.pageTemPath(cmsPage.getSiteId());
        CmsPageWindow cmsPageWindow = new CmsPageWindow();

        cmsPageWindow.setPageId(pid);
        List<CmsPageWindow> cmsList = cmsPageWindowService
                .queryByWindowId(cmsPageWindow);

        for (int i = 0; i < cmsList.size(); i++) {
            CmsPageWindow window = cmsList.get(i);
            cmsPageWindow.setWindowId(window.getWindowId());
            cmsPageWindow.setPageId(cmsPage.getPageId());
            addOthCmsPageWindow(cmsPageWindow);
        }

        File pageFile = null;
        cmsPage.setContent(cmsPage.getContent().replaceAll("#KM#",
                pid.toString()));
        pageFile = new File(path + "/" + cmsPage.getPageId() + ".html");
        FileUtils.writeStringToFile(pageFile, cmsPage.getContent(), "utf-8");

        return cmsPage.getPageId();
    }

    // 修改供应商PAGE模板
    @Override
    public boolean upOthTop(CmsPage cmsPage, Integer pid) throws Exception {
        if (cmsPage.getStatus() == null)
            cmsPage.setStatus(0);
        this.cmsPageDAO.updateByPrimaryKeySelective(cmsPage);

        String path = PathConstants.pageTemPath(cmsPage.getSiteId());

        File pageFile = null;
        cmsPage.setContent(cmsPage.getContent().replaceAll("#KM#",
                pid.toString()));
        pageFile = new File(path + "/" + cmsPage.getPageId() + ".html");
        FileUtils.writeStringToFile(pageFile, cmsPage.getContent(), "utf-8");
        return true;
    }

    // 添加供应商paot头部窗口
    public void addOthCmsPageWindow(CmsPageWindow cmsPageWindow)
            throws Exception {
        cmsPageWindowService.add(cmsPageWindow);
    }

    //添加窗口，窗口和页面的对应关系
    public boolean addWindPage(CmsPage cmsPage, CmsTemplate cmsTemplate) throws SQLException {
        CmsWindow cmsWindow = new CmsWindow();
        cmsWindow.setName(cmsTemplate.getName() + cmsPage.getPageId().toString());
        cmsWindow.setTheme(cmsTemplate.getTheme() + cmsPage.getPageId().toString());
        cmsWindow.setSiteId(cmsTemplate.getSiteId());
        cmsWindow.setContent(cmsTemplate.getContent());


        // 创建时间以及创建人
        cmsWindow.setCreated(Constants.USER_B2B_ID);
        cmsWindow.setCreateDate(DateTimeUtils.getCalendarInstance()
                .getTime());
        cmsWindow.setStatus(0);
//		cmsWindowDataDAO.iinsert(cmsWindow);
        cmsWindowDAO.insert(cmsWindow);
        CmsPageWindow cmsPageWindow = new CmsPageWindow();
        cmsPageWindow.setWindowId(cmsWindow.getWindowId());

        cmsPageWindow.setPageId(cmsPage.getPageId());
        cmsPageWindowDAO.insert(cmsPageWindow);
        return true;
    }

    // 修改供应商PAGE模板
    @Override
    public boolean upPageTheme(CmsPage cmsPage, CmsTheme cmsTheme)
            throws Exception {
        // TODO Auto-generated method stub
        // 如果为空，则默认为未发布
        if (cmsPage.getStatus() == null)
            cmsPage.setStatus(0);
        this.cmsPageDAO.updateByPrimaryKeySelective(cmsPage);
        String path = PathConstants.pageTemPath(cmsPage.getSiteId());

        List<CmsWindow> cmsWindowList = new ArrayList();

        List idList = cmsPageWindowDAO.selectByPageId(cmsPage);

        // 当前页面窗口列表
        CmsPageWindowQry cmsPageWindowQry = new CmsPageWindowQry();
        cmsPageWindowQry.setPageId(cmsPage.getPageId());
        List windowLists = cmsPageWindowDAO.queryWindowList(cmsPageWindowQry);

        List window_templateIds_curr = new ArrayList();
        for (int i = 0; i < windowLists.size(); i++) {

            Integer templateId = ((CmsWindow) windowLists.get(i)).getTemplateId();
            if (null != templateId) {
                window_templateIds_curr.add(templateId);
            }
        }


        //页面模板绑定的窗口模板列表
        if (cmsTheme.getType().equals(5)) {
            CmsThemeTemplate cmsThemeTemplate = new CmsThemeTemplate();
            cmsThemeTemplate.setThemeId(cmsTheme.getThemeId());
            //模板窗口列表
            List listThemeTemplate = cmsThemeTemplateDAO.queryByThemeTemp(cmsThemeTemplate);
            List theme_templateIds_curr = new ArrayList();

            for (int i = 0; i < listThemeTemplate.size(); i++) {

                Integer templateId = ((CmsThemeTemplate) listThemeTemplate.get(i)).getTemplateId();
                if (null != templateId) {
                    theme_templateIds_curr.add(templateId);
                }
            }
            //有新创建窗口(去重复)
            if (listThemeTemplate.size() > windowLists.size() && window_templateIds_curr.size() > 0) {
                List<Integer> tempList = new ArrayList<Integer>();
                for (int i = 0; i < theme_templateIds_curr.size(); i++) {
                    Integer tmpId = (Integer) theme_templateIds_curr.get(i);
                    //不存在
                    if (!window_templateIds_curr.contains(tmpId)) {
                        tempList.add(tmpId);
                    }
                }

                for (int i = 0; i < listThemeTemplate.size(); i++) {
                    CmsThemeTemplate cms_Theme_Template = ((CmsThemeTemplate) listThemeTemplate.get(i));
                    if (tempList.contains(cms_Theme_Template.getTemplateId())) {
                        CmsTemplate cmsTemplate = cmsTemplateDAO.selectByPrimaryKey(cms_Theme_Template.getTemplateId());

                        addWindPage(cmsPage, cmsTemplate);
                    }
                }


            }

        }


        for (int i = 0; i < idList.size(); i++) {
            Object s = idList.get(i);
            cmsWindowList.add(cmsWindowService.selectByPrimaryKey(s.hashCode()));
        }

        CmsWindow cmsWindow;
        CmsThemeTemplate cmsThemeTemplate = new CmsThemeTemplate();
        cmsThemeTemplate.setThemeId(cmsTheme.getThemeId());
        // 窗口模板
        cmsThemeTemplate.setType(13);
        List<CmsThemeTemplate> cttList = cmsThemeTemplateService.queryByThemeTemp(cmsThemeTemplate);
        for (CmsThemeTemplate ctt : cttList) {
            CmsTemplate cmsTemplate = cmsTemplateServce.getTemplateById(ctt.getTemplateId());

//            cmsWindow.setTheme(cmsTemplate.getTheme() + cmsPage.getPageId().toString());
            for (int j = 0; j < cmsWindowList.size(); j++) {
                cmsWindow = cmsWindowList.get(j);

                if (cmsWindow.getName().equals(
                        cmsTemplate.getName() + cmsPage.getPageId().toString())) {
                    cmsWindow.setWindowId(cmsWindow.getWindowId());
                    cmsWindow.setContent(cmsTemplate.getContent());
                    cmsWindow.setTemplateId(cmsTemplate.getTemplateId());
                    //添加模板对应 对应
                    cmsWindow.setTemplateId(cmsTemplate.getTemplateId());
                    if (cmsWindow.getContent().indexOf("#KM#") > 0) {
                        cmsWindow.setContent(cmsWindow.getContent().replaceAll(
                                "#KM#", cmsPage.getPageId().toString()));
                    }

                    cmsWindowService.updateWindow(cmsWindow);
                }
            }
        }

        cmsPage.setContent(cmsPage.getContent().replaceAll("#KM#",
                cmsPage.getPageId().toString()));
        File pageFile = new File(path + "/" + cmsPage.getPageId() + ".html");
        FileUtils.writeStringToFile(pageFile, cmsPage.getContent(), "utf-8");
        return true;
    }

    // 窗口与窗口关系建立
    public void createWindowAndRation(CmsWindow cmsPWindow,
                                      CmsTemplate cmsTemplate, CmsPage cmsPage) throws Exception {

        CmsWindow cmsWindow = new CmsWindow();

        // StringBuffer content=new StringBuffer(cmsWindow.getContent());
        cmsWindow.setContent(cmsTemplate.getContent());

        cmsWindow.setName(cmsTemplate.getName()
                + cmsPage.getPageId().toString());
        cmsWindow.setTheme(cmsTemplate.getTheme()
                + cmsPage.getPageId().toString());
        cmsWindow.setParamsType(1);
        cmsWindow.setRemark(cmsTemplate.getRemark());
        // 获取当前管理员账号
        Map session = ActionContext.getContext().getSession();
        SysUser sysUser = (SysUser) session.get("sysUser");
        Integer siteId = (Integer) session.get("siteId");
        // 站点ID
        cmsWindow.setSiteId(siteId);
        // 创建时间以及创建人
        cmsWindow.setCreated(sysUser.getUserId());
        cmsWindow.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
        cmsWindow.setStatus(0);

        if (cmsWindow.getContent().indexOf("#KM#") > 0) {
            cmsWindow.setContent(cmsWindow.getContent().replaceAll("#KM#",
                    cmsPage.getPageId().toString()));
        }
        cmsWindowService.addCmsWindow(cmsWindow);

        // 如果父窗口存在，建立窗口与窗口关系
        if (cmsPWindow != null) {

            CmsWindowData windowData = new CmsWindowData();
            windowData.setSiteId(cmsPWindow.getSiteId());
            // 窗口
            windowData.setDataType(4);
            windowData.setWindowId(cmsPWindow.getWindowId());
            windowData.setDataId(cmsWindow.getWindowId());
            // 创建时间以及创建人
            windowData.setCreated(sysUser.getUserId());
            windowData.setCreateDate(DateTimeUtils.getCalendarInstance()
                    .getTime());
            windowData.setStatus(0);
            cmsWindowDataDAO.insert(windowData);
            // 不存在，建立页面与窗口关系
        } else {
            CmsPageWindow cmsPageWindow = new CmsPageWindow();
            cmsPageWindow.setPageId(cmsPage.getPageId());
            cmsPageWindow.setWindowId(cmsWindow.getWindowId());
            cmsPageWindowService.add(cmsPageWindow);

        }

        // 子窗口列表
        cmsTemplate.setEndIndex(100);
        cmsTemplate.setStartIndex(0);
        List list = cmsTemplateDAO.queryBandWindowList(cmsTemplate);
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                CmsTemplate temp2 = (CmsTemplate) list.get(i);
                temp2 = cmsTemplateServce
                        .getTemplateById(temp2.getTemplateId());
                createWindowAndRation(cmsWindow, temp2, cmsPage);
            }
        }

    }

    @Override
    public CmsPage getCmsPageById(Integer cmsPageId) throws Exception {
        CmsPage cmsPage = this.cmsPageDAO.selectByPrimaryKey(cmsPageId);
        String path = null;
        try {

            if (cmsPage != null) {
                path = PathConstants.pageTemPath(cmsPage.getSiteId());
                File file = null;
                // if(cmsPage.getPublicType()==4)
                // file=new File(path,"detail.html");
                // else
                file = new File(path, cmsPage.getPageId() + ".html");
                if (file.exists())
                    cmsPage.setContent(FileUtils
                            .readFileToString(file, "utf-8"));
            }
            return cmsPage;
        } catch (Exception e) {
            return cmsPage;
        }

    }

    @Override
    public int delCmsPage(CmsPage cmsPage) throws Exception {
        // TODO Auto-generated method stub
        int res = this.cmsPageDAO.deleteByPrimaryKey(cmsPage.getPageId());
        return res;
    }

    @Override
    @Transactional
    public int updateCmsPage(CmsPage cmsPage) throws Exception {
        // TODO Auto-generated method stub
        CmsPage page = this.cmsPageDAO.selectByPrimaryKey(cmsPage.getPageId());
        cmsPage.setCreated(page.getCreated());
        cmsPage.setCreateDate(page.getCreateDate());
        // 数据库修改该对象
        int res = this.cmsPageDAO.updateByPrimaryKey(cmsPage);
        if (cmsPage.getContent() != null) {
            String path = PathConstants.pageTemPath(page.getSiteId());
            // 修改相应的文件
            FileOperateUtils.checkAndCreateDirs(path);
            File file = null;
            // if(page.getPublicType()==4)
            // file=new File(path,"detail.html");
            // else
            file = new File(path, page.getPageId() + ".html");
            FileUtils.writeStringToFile(file, cmsPage.getContent(), "utf-8");
        }
        return res;
    }

    @Override
    @Transactional
    public int delAllPage(String ids) throws Exception {
        // TODO Auto-generated method stub
        int temp = 1;
        String id[] = ids.split(",");
        for (int i = 0; i < id.length; i++) {
            if (delCmsPageById(Integer.valueOf(id[i])) == 0) {
                temp = 0;
            }
        }
        return temp;
    }

    @Override
    @Transactional
    public int delCmsPageById(Integer pageId) throws Exception {
        // TODO Auto-generated method stub
        CmsPage page = this.cmsPageDAO.selectByPrimaryKey(pageId);
        int res = this.cmsPageDAO.delPageById(pageId);
        CmsPageWindow cmsPageWindow = new CmsPageWindow();
        cmsPageWindow.setPageId(pageId);
        // 删除绑定的窗口
        List list = this.cmsPageWindowService.queryByWindowId(cmsPageWindow);
        for (int i = 0; i < list.size(); i++) {
            this.cmsPageWindowService.delRecord(cmsPageWindow);
        }
        String path = PathConstants.pageTemPath(page.getSiteId());
        // 找到相应的文件并删除
        File file = null;
        // if(page.getPublicType()==4)
        // file=new File(path,"detail.html");
        // else
        FileOperateUtils.delFile(path+"/"+".html");
        return res;
    }

    @Override
    public List getByName(CmsPage cmsPage) throws Exception {
        // TODO Auto-generated method stub
        return cmsPageDAO.getPage(cmsPage);
    }

    /**
     * 根据发布类型查询页面
     *
     * @param publishType 发布类型
     */
    @Override
    public List<CmsPage> selectByPrimaryPublishType(CmsPage page)
            throws Exception {
        return cmsPageDAO.selectByPrimaryPublishType(page);
    }

    /**
     * 供应商根据发布类型查询页面
     *
     * @param publishType 发布类型
     */
    @Override
    public List<CmsPage> selectByPrimaryPublishType1(CmsPage page)
            throws Exception {
        return cmsPageDAO.selectByPrimaryPublishType1(page);
    }

    /**
     * 套餐发布类型查询页面
     */
    @Override
    public List<CmsPage> selectByPrimaryPublishType2(CmsPage page)
            throws Exception {
        return cmsPageDAO.selectByPrimaryPublishType2(page);
    }

    // 备份线上数据
    @Override
    @Transactional
    public void insertPageOnline(CmsPage page) throws Exception {
        // 修改发布状态
        CmsPage cmsPage = cmsPageDAO.selectByPrimaryKey(page.getPageId());
        // 已发布
        cmsPage.setStatus(1);
        cmsPageDAO.updateByPrimaryKey(cmsPage);
        // cmsPageDAO.
        // 所有相关表的备份
        cmsPageDAO.deletePageOnline(page);
        cmsPageDAO.insertPageOnline(page);
        // 1>page文件备份
        String path = PathConstants.pageTemPath(cmsPage.getSiteId());
        // 判断pages文件夹是否存在，若不存在，则新建一个
        String destPath=PathConstants.pageOnlineTemPath(cmsPage
                .getSiteId());

        FileOperateUtils.checkAndCreateDirs(destPath);
        // 写入文件
        File pageFile = null;
        // if(page.getPublicType()==4)
        // pageFile=new File(path+"/"+"detail.html");
        // else
        pageFile = new File(path + "/" + page.getPageId() + ".html");
        FileOperateUtils.delFile(destPath + "/" + page.getPageId() + ".html");
        File destFile = new File(destPath);
        FileUtils.copyFileToDirectory(pageFile, destFile);
        // 2>window文件备份
        // 1.页面下所绑定的窗口
        List<Integer> list = cmsPageWindowDAO.selectByPageId(page);

        String sqlIn = SqlJoinUtil.getSqlIn(list, 1000, "window_id");
        if (!sqlIn.equals("()")) {
            // 2.窗口数据中绑定的窗口dataType=4
            List<Integer> list_wind = cmsPageWindowDAO
                    .selectWindInWind(SqlJoinUtil.getSqlIn(list, 1000,
                            "window_id"));
            // 3.递归窗口中的窗口
            while (null != list_wind && list_wind.size() > 0) {
                // 累计
                list.addAll(list_wind);
                list_wind = cmsPageWindowDAO.selectWindInWind(SqlJoinUtil
                        .getSqlIn(list_wind, 1000, "window_id"));
            }
        }
        path = PathConstants.windowTemPath(cmsPage.getSiteId());
        FileOperateUtils.checkAndCreateDirs(path);

        if (list.size() > 0) {
            for (Integer windowId : list) {
                // 写入文件
                pageFile = new File(path + "/" + windowId + ".html");
                File f2 = new File(destFile + "/" + windowId + ".html");
                if (f2.exists()) {
                    f2.delete();
                }
                FileUtils.copyFileToDirectory(pageFile, destFile);
            }
        }
    }

    @Override
    public String checkName(CmsPage cmsPage) throws Exception {
        // TODO Auto-generated method stub
        if (cmsPage.getPageId() != null) {
            CmsPage page = this.getCmsPageById(cmsPage.getPageId());
            if (!page.getName().equals(cmsPage.getName())) {
                List list = this.getByName(cmsPage);
                if (list.size() > 0)
                    return "fail";
            }
        } else {
            List list = this.getByName(cmsPage);
            if (list.size() > 0)
                return "fail";
        }
        return "success";
    }

    @Override
    public Integer byPage(CmsPage cmsPage) throws Exception {
        // TODO Auto-generated method stub
        return this.cmsPageDAO.countOutPut(cmsPage);
    }

    public CmsPageWindowDAO getCmsPageWindowDAO() {
        return cmsPageWindowDAO;
    }

    public void setCmsPageWindowDAO(CmsPageWindowDAO cmsPageWindowDAO) {
        this.cmsPageWindowDAO = cmsPageWindowDAO;
    }

    @Override
    public List findAllPage() throws SQLException {
        return cmsPageDAO.findAllPage();
    }

    public CmsWindowService getCmsWindowService() {
        return cmsWindowService;
    }

    public void setCmsWindowService(CmsWindowService cmsWindowService) {
        this.cmsWindowService = cmsWindowService;
    }

    public CmsTemplateServce getCmsTemplateServce() {
        return cmsTemplateServce;
    }

    public void setCmsTemplateServce(CmsTemplateServce cmsTemplateServce) {
        this.cmsTemplateServce = cmsTemplateServce;
    }

    public CmsThemeTemplateService getCmsThemeTemplateService() {
        return cmsThemeTemplateService;
    }

    public void setCmsThemeTemplateService(
            CmsThemeTemplateService cmsThemeTemplateService) {
        this.cmsThemeTemplateService = cmsThemeTemplateService;
    }

    public CmsTemplateDAO getCmsTemplateDAO() {
        return cmsTemplateDAO;
    }

    public void setCmsTemplateDAO(CmsTemplateDAO cmsTemplateDAO) {
        this.cmsTemplateDAO = cmsTemplateDAO;
    }

    public CmsWindowDataDAO getCmsWindowDataDAO() {
        return cmsWindowDataDAO;
    }

    public void setCmsWindowDataDAO(CmsWindowDataDAO cmsWindowDataDAO) {
        this.cmsWindowDataDAO = cmsWindowDataDAO;
    }

    public CmsWindowDAO getCmsWindowDAO() {
        return cmsWindowDAO;
    }

    public void setCmsWindowDAO(CmsWindowDAO cmsWindowDAO) {
        this.cmsWindowDAO = cmsWindowDAO;
    }

}
