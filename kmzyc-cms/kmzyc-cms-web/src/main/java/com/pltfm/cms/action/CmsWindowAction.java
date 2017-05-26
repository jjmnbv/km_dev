package com.pltfm.cms.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ActionContext;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.util.Token;
import com.pltfm.cms.service.CmsLogService;
import com.pltfm.cms.service.CmsPageService;
import com.pltfm.cms.service.CmsPageWindowService;
import com.pltfm.cms.service.CmsSiteDataService;
import com.pltfm.cms.service.CmsTemplateServce;
import com.pltfm.cms.service.CmsWindowDataService;
import com.pltfm.cms.service.CmsWindowService;
import com.pltfm.cms.util.SqlJoinUtil;
import com.pltfm.cms.vobject.CmsLog;
import com.pltfm.cms.vobject.CmsPage;
import com.pltfm.cms.vobject.CmsPageDetail;
import com.pltfm.cms.vobject.CmsPageWindow;
import com.pltfm.cms.vobject.CmsSiteData;
import com.pltfm.cms.vobject.CmsTemplate;
import com.pltfm.cms.vobject.CmsWindow;
import com.pltfm.cms.vobject.CmsWindowData;
import com.pltfm.cms.vobject.KeyWords;
import com.pltfm.sys.bean.SysUserBean;
import com.pltfm.sys.model.SysUser;

/**
 * 窗口Action类
 *
 * @author cjm
 * @since 2013-9-10
 */
@Scope(value = "prototype")
@Component(value = "cmsWindowAction")
public class CmsWindowAction extends BaseAction {
    /**
     * 窗口业务逻辑层接口
     */
    @Resource(name = "cmsWindowService")
    private CmsWindowService cmsWindowService;//cmsWindowService接口
    @Resource(name = "cmsTemplateServce")
    private CmsTemplateServce cmsTemplateServce;//cmsTemplateService接口
    @Resource(name = "cmsPageWindowService")
    private CmsPageWindowService cmsPageWindowService;//cmsPageWindowService接口
    @Resource(name = "cmsWindowDataService")
    private CmsWindowDataService cmsWindowDataService;//cmsWindowDataService接口
    @Resource(name = "cmsLogService")
    private CmsLogService cmsLogService;//cmsLogService接口
    @Resource(name = "cmsSiteDataService")
    private CmsSiteDataService cmsSiteDataService;

    private CmsLog cmsLog = new CmsLog();//日志实体

    private Integer userId;

    private Integer siteId;

    private CmsTemplate cmsTemplate;//模板组件
    private CmsPageDetail cmsPageDetail;//详细页
    private Integer pageId;//页面Id
    private CmsPage cmsPage;//页面组件
    @Resource(name = "cmsPageService")
    private CmsPageService cmsPageService;//cmsPageService接口
    private Integer pageNo;//页码
    private Integer adminType;//角色区分

    /**
     * 分页对象
     */
    private Page page;

    /**
     * 数据类型
     */
    private Integer dataType;
    /**
     * 选中的checked
     */
    private String checkeds;

    /**
     * 当前要绑定的窗口Id
     */
    private String windowId;

    /**
     * 窗口实体
     */
    private CmsWindow cmsWindow;

    /**
     * 活动数据Id集合
     */
    private String[] promotionIds;

    /**
     * 查询数据类型
     */
    private Integer queryType;
    //日志
    private static Logger logger = LoggerFactory.getLogger(CmsWindowAction.class);
    ActionContext actionContext = ActionContext.getContext();
    Map session = actionContext.getSession();
    /**
     * 弹出窗口信息列表
     */
    public String queryWindow() {
        try {
            Integer siteId = (Integer) session.get("siteId");
            if (cmsWindow == null) {
                cmsWindow = new CmsWindow();
            }
            if (null != siteId) {
                cmsWindow.setSiteId(siteId);
            }
            //空格处理
            if (null != cmsWindow.getName())
                cmsWindow.setName(cmsWindow.getName().trim());
            if (null != cmsWindow.getTheme())
                cmsWindow.setTheme(cmsWindow.getTheme().trim());
            page = cmsWindowService.queryForPage(cmsWindow, page);
            List<CmsWindowData> dataList = this.cmsWindowDataService.queryByWindowIdDataType(Integer.valueOf(this.windowId), 4, siteId);
            List list = page.getDataList();
            if (dataList.size() != 0) {
                for (int i = 0; i < list.size(); i++) {
                    CmsWindow cmsWindow = (CmsWindow) list.get(i);
                    for (int j = 0; j < dataList.size(); j++) {
                        CmsWindowData cmsWinData = dataList.get(j);
                        if (cmsWindow.getWindowId().equals(cmsWinData.getDataId())) {
                            cmsWindow.setFlag(1);
                            break;
                        }
                    }
                }
            }
            return "windowSccuess";
        } catch (Exception e) {
            logger.error("CmsWindowAction.queryWindow异常：" + e.getMessage(), e);
            return "windowError";
        }
    }

    /**
     * 弹出窗口信息列表
     */
    public String popQueryWindow() {
        try {
            if (cmsWindow == null) {
                cmsWindow = new CmsWindow();
            }
            if (null != siteId) {
                cmsWindow.setSiteId(siteId);
            }
            page = cmsWindowService.queryForPage(cmsWindow, page);
            List<CmsSiteData> dataList = null;

            dataList = cmsSiteDataService.queryByUserIdAndSiteIdAndDataType(userId, siteId, dataType);
            List list = page.getDataList();
            if (dataList.size() != 0) {
                for (int i = 0; i < list.size(); i++) {
                    CmsWindow cmsWindow = (CmsWindow) list.get(i);
                    for (int j = 0; j < dataList.size(); j++) {
                        CmsSiteData cmsSiteData = dataList.get(j);
                        if (cmsWindow.getWindowId().equals(cmsSiteData.getDataId())) {
                            cmsWindow.setFlag(1);
                            break;
                        }
                    }
                }
            }
            return "popQueryWindow";
        } catch (Exception e) {
            logger.error("CmsWindowAction.popQueryWindow异常：" + e.getMessage(), e);
            return "windowError";
        }
    }

    /**
     * 跳转数据列表页面
     */
    public String openWindowList() {
        return "openWindowList";
    }


    /**
     * 跳转数据列表页面
     */
    public String popOpenWindowList() {
        return "popOpenWindowList";
    }

    //返回窗口列表
    public String queryWindowPage() {
        SysUser sysuser = (SysUser) session.get("sysUser");
        String ids = null;
        try {
            if (adminType != null && adminType == 0) {
                List lsits = cmsSiteDataService.listToString(sysuser.getUserId(), 4);
                if (lsits != null && lsits.size() != 0) {
                    ids = SqlJoinUtil.getSqlIn(lsits, 1000, "WINDOW_ID");
                }
            }


            if (cmsWindow == null) {
                cmsWindow = new CmsWindow();
            }
            Integer siteId = (Integer) session.get("siteId");
            if (null != siteId) {
                cmsWindow.setSiteId(siteId);
            }
            cmsWindow.setIds(ids);
            if (page == null && keyWords != null) {
                page = new Page();
                if (keyWords.getPageNo() != null && keyWords.getPageSize() != null) {
                    page.setPageNo(keyWords.getPageNo());
                    page.setPageSize(keyWords.getPageSize());
                }
            }
            if (keyWords != null) {
                if (keyWords.getId_keyword() != null) {
                    cmsWindow.setWindowId(keyWords.getId_keyword());
                }
                if (keyWords.getName_keyword() != null) {
                    cmsWindow.setName(keyWords.getName_keyword().trim());
                }
                if (keyWords.getTheme_keyword() != null) {
                    cmsWindow.setTheme(keyWords.getTheme_keyword().trim());
                }

                if (keyWords.getStatus_keyword() != null)
                    cmsWindow.setStatus(keyWords.getStatus_keyword());
                if (keyWords.getType_keyword() != null)
                    cmsWindow.setParamsType(keyWords.getType_keyword());
            }
            page = cmsWindowService.queryForPage(cmsWindow, page);
            if (keyWords == null) {
                keyWords = new KeyWords();
            }
            keyWords.setPageNo(page.getPageNo());
            keyWords.setPageSize(page.getPageSize());

            sysUserMap = new HashMap();
            SysUserBean bean = SysUserBean.instance();
            SysUser vo = new SysUser();
            List<SysUser> list = bean.getSysUserList(vo);
            for (SysUser user : list) {
                sysUserMap.put(user.getUserId() + "", user.getUserName());
            }

            return "windowList";
        } catch (Exception e) {
            logger.error("CmsWindowAction.queryWindowPage异常：" + e.getMessage(), e);
            return "windowError";
        }
    }

    //关键字搜索
    public String queryWindowByKey() {
        try {
            cmsWindow = new CmsWindow();
            if (page == null) {
                page = new Page();
                if (keyWords != null) {
                    page.setPageNo(keyWords.getPageNo());
                    page.setPageSize(keyWords.getPageSize());
                }
            }
            if (keyWords != null) {
                cmsWindow.setWindowId(keyWords.getId_keyword());
                cmsWindow.setName(keyWords.getName_keyword().trim());
                cmsWindow.setTheme(keyWords.getTheme_keyword());
                if (keyWords.getStatus_keyword() == -1)
                    cmsWindow.setStatus(null);
                else
                    cmsWindow.setStatus(keyWords.getStatus_keyword());
                if (keyWords.getType_keyword() == -1)
                    cmsWindow.setParamsType(null);
                else
                    cmsWindow.setParamsType(keyWords.getType_keyword());
            }
            page = cmsWindowService.queryForPage(cmsWindow, page);
            if (keyWords != null) {
                keyWords.setPageNo(this.page.getPageNo());
                keyWords.setPageSize(page.getPageSize());
            }
            return "windowList";
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("CmsWindowAction.queryWindowByKey异常：" + e.getMessage(), e);
            return "error";
        }
    }

    /**
     * 跳转到窗口添加页
     */
    public String gotoAddPage() {
        return "gotoAddWindow";
    }

    /**
     * 跳转模板选择页
     */
    public String gotoSelPage() {
        try {
            Integer siteId = (Integer) session.get("siteId");
            this.page = new Page();
            if (cmsTemplate == null)
                cmsTemplate = new CmsTemplate();
            cmsTemplate.setStatus(0);
            cmsTemplate.setSiteId(siteId);
            this.page = this.cmsTemplateServce.queryForPage(cmsTemplate, page);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("CmsWindowAction.gotoSelPage异常：" + e.getMessage(), e);
        }
        return "gotoSelTemplate";
    }

    public String templateSel() {
        try {
            Integer siteId = (Integer) session.get("siteId");
            if (cmsTemplate.getType() == -1) {
                cmsTemplate.setType(null);
            }
            cmsTemplate.setStatus(0);
            cmsTemplate.setSiteId(siteId);
            this.page = this.cmsTemplateServce.queryForPage(cmsTemplate, page);
        } catch (Exception e) {
            logger.error("CmsWindowAction.templateSel异常：" + e.getMessage(), e);
        }
        return "gotoSelTemplate";
    }

    //模板选择
    public void selTemplate() {
        try {
            this.cmsTemplate = this.cmsTemplateServce.getTemplateById(Integer.valueOf(windowId));
            JSONObject json = new JSONObject();
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setContentType("text/html;charset=utf-8");
            json.put("content", cmsTemplate.getContent());
            json.put("templateId", cmsTemplate.getTemplateId());
            response.getWriter().print(json);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            logger.error("CmsWindowAction.selTemplate异常：" + e.getMessage(), e);
        }
    }

    /**
     * 添加窗口
     */
    @Token
    public String addWindow() {
        SysUser sysUser = (SysUser) session.get("sysUser");
        try {
            Integer siteId = (Integer) session.get("siteId");

            if (null != siteId) {
                cmsWindow.setSiteId(siteId);
            }
            if (this.cmsWindow == null) {
                this.addActionMessage(ConfigurationUtil.getString("add.fail"));
                return "error";
            }

            //获取当前管理员账号
//			Map session = ActionContext.getContext().getSession();

            cmsWindow.setCreated(sysUser.getUserId());
            cmsWindow.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
            this.cmsWindowService.addCmsWindow(cmsWindow);

            try {
                List lsits = cmsSiteDataService.listToString(sysUser.getUserId(), 2);
                if (lsits != null && lsits.size() != 0) {
                    cmsSiteDataService.addCSD(cmsWindow.getWindowId(), 4, sysUser.getUserId(), siteId, sysUser.getUserId());
                }
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

            //日志记录
            cmsLog.setModuleName("窗口");
            cmsLog.setModuleContent("新建窗口> 窗口名称:" + cmsWindow.getName() + "  窗口主题:" + cmsWindow.getTheme() + " 备注:" + cmsWindow.getRemark());
            cmsLog.setConsoleOperator(sysUser.getUserId());
            cmsLog.setType(1);
            log.info(cmsLogService.insert(cmsLog));

            //	this.cmsWindow=new CmsWindow();
            this.addActionMessage(ConfigurationUtil.getString("add.success"));
        } catch (Exception e) {
            this.addActionMessage(ConfigurationUtil.getString("add.fail"));
            logger.error("CmsWindowAction.addWindow异常：" + e.getMessage(), e);
        }
        //保持状态
        this.keyWords = new KeyWords();
        keyWords.setId_keyword(cmsWindow.getWindowId());
        return this.queryWindowPage();
    }

    //goto窗口编辑页
    public String gotoEditWin() {
        try {
            this.cmsWindow = this.cmsWindowService.getWindowById(Integer.valueOf(windowId));
        } catch (Exception e) {
            logger.error("CmsWindowAction.gotoEditWin异常：" + e.getMessage(), e);
        }
        return "gotoEditWindow";
    }

    //编辑窗口
    @Token
    public String editWindow() {
        try {
            if (this.cmsWindow == null)
                return "error";
            //获取当前管理员账号
            Map session = ActionContext.getContext().getSession();
            SysUser sysUser = (SysUser) session.get("sysUser");
            cmsWindow.setModified((sysUser.getUserId()));
            cmsWindow.setModifyDate((DateTimeUtils.getCalendarInstance().getTime()));
            this.cmsWindowService.updateWindow(cmsWindow);

            //日志记录
            cmsLog.setModuleName("窗口");
            cmsLog.setModuleContent("修改窗口> 窗口名称:" + cmsWindow.getName() + "  窗口主题:" + cmsWindow.getTheme() + " 备注:" + cmsWindow.getRemark());
            cmsLog.setConsoleOperator(sysUser.getUserId());
            cmsLog.setType(2);
            log.info(cmsLogService.insert(cmsLog));

            //	this.cmsWindow=new CmsWindow();
            this.addActionMessage(ConfigurationUtil.getString("update.success"));
        } catch (Exception e) {
            this.addActionMessage(ConfigurationUtil.getString("update.fail"));
            logger.error("CmsWindowAction.editWindow异常：" + e.getMessage(), e);
        }
        if (pageId != null) {
            /*CmsPageAction cmsPageAction=new CmsPageAction();
			cmsPageAction.setPageId(pageId);
			cmsPageAction.pageDetail();*/

            try {
                if (cmsPageDetail == null)
                    cmsPageDetail = new CmsPageDetail();
                this.cmsPage = this.cmsPageService.getCmsPageById(pageId);
                this.cmsPageDetail.setCmsPage(cmsPage);
                this.page = this.cmsWindowService.getWindow_In(cmsWindow, page, pageId);
                this.cmsPageDetail.setPage(page);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                logger.error("CmsWindowAction.editWindow异常：" + e.getMessage(), e);
            }
            return "pageDetail";

        }
        //保持状态
        this.keyWords = new KeyWords();
        keyWords.setId_keyword(cmsWindow.getWindowId());
        return this.queryWindowPage();
    }

    //删除某个窗口
    public String delWindow() {
        try {
            CmsPageWindow cmsPageWindow = new CmsPageWindow();
            cmsPageWindow.setWindowId(Integer.valueOf(this.windowId));
            List list = cmsPageWindowService.queryByWindowId(cmsPageWindow);
            if (list.size() > 0) {
                this.addActionError(ConfigurationUtil.getString("delete.error"));
            } else {


                //日志记录
                SysUser sysUser = (SysUser) session.get("sysUser");
                CmsWindow cmsWindow = this.cmsWindowService.getWindowById(Integer.valueOf(this.windowId));
                cmsLog.setModuleName("窗口");
                cmsLog.setModuleContent("删除" + cmsWindow.getName() + "窗口");
                cmsLog.setConsoleOperator(sysUser.getUserId());
                cmsLog.setType(3);
                log.info(cmsLogService.insert(cmsLog));

                this.cmsWindowService.delWindowById(Integer.valueOf(this.windowId));
                this.addActionMessage(ConfigurationUtil.getString("delete.success"));
            }
        } catch (Exception e) {
            this.addActionMessage(ConfigurationUtil.getString("delete.fail"));
            logger.error("CmsWindowAction.delWindow异常：" + e.getMessage(), e);
        }
        this.keyWords = null;
        return queryWindowPage();
    }

    //删除所选的窗口
    public String delAllWindow() {
        try {
            //日志记录
            SysUser sysUser = (SysUser) session.get("sysUser");
            cmsLog.setModuleName("窗口");
            cmsLog.setIds(checkeds);
            cmsLog.setConsoleOperator(sysUser.getUserId());
            log.info(cmsLogService.insert(cmsLog));

            this.cmsWindowService.delAllWindow(checkeds);
            this.checkeds = null;
            this.addActionMessage(ConfigurationUtil.getString("delete.success"));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("CmsWindowAction.delAllWindow异常：" + e.getMessage(), e);
            this.addActionMessage(ConfigurationUtil.getString("delete.fail"));
        }
        this.keyWords = null;
        return queryWindowPage();
    }

    //窗口详情
    public String windowDetail() {
        try {
            Integer siteId = (Integer) session.get("siteId");
            CmsWindowData cmsWindowData = new CmsWindowData();
            cmsWindowData.setWindowId(Integer.valueOf(windowId));
            cmsWindowData.setDataType(queryType);
            cmsWindowData.setSiteId(siteId);
            CmsWindow cmsWindow = this.cmsWindowService.getWindowById(Integer.valueOf(this.windowId));
            this.page = cmsWindowDataService.queryBywindowId(cmsWindowData, page);
            if (this.cmsPageDetail == null)
                this.cmsPageDetail = new CmsPageDetail();
            this.cmsPageDetail.setCmsWindow(cmsWindow);
            this.cmsPageDetail.setPage(page);
            SysUserBean bean = SysUserBean.instance();
            //获取创建人
            if (cmsWindow.getCreated() != null) {
                SysUser created = bean.getSysUserDetail(cmsWindow.getCreated());
                this.cmsPageDetail.setUser_Cre(created.getUserName());
            }
            //获取修改人
            if (cmsWindow.getModified() != null) {
                SysUser modified = bean.getSysUserDetail(cmsWindow.getModified());
                this.cmsPageDetail.setUser_Mod(modified.getUserName());
            }
        } catch (NumberFormatException e) {
            logger.error("CmsWindowAction.windowDetail异常：" + e.getMessage(), e);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("CmsWindowAction.windowDetail异常：" + e.getMessage(), e);
        }
        return "windowDetail";
    }

    //检测名字是否唯一
    public void check() {
        try {
            HttpServletResponse response = ServletActionContext.getResponse();
            Integer siteId = (Integer) session.get("siteId");
            if (siteId != null && cmsWindow != null)
                cmsWindow.setSiteId(siteId);
            String result = this.cmsWindowService.checkName(cmsWindow);
            response.getWriter().write(result);
        } catch (Exception e) {
            logger.error("CmsWindowAction.check异常：" + e.getMessage(), e);
        }
    }


    public CmsWindowService getCmsWindowService() {
        return cmsWindowService;
    }

    public void setCmsWindowService(CmsWindowService cmsWindowService) {
        this.cmsWindowService = cmsWindowService;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public String getCheckeds() {
        return checkeds;
    }

    public void setCheckeds(String checkeds) {
        this.checkeds = checkeds;
    }

    public CmsWindow getCmsWindow() {
        return cmsWindow;
    }

    public void setCmsWindow(CmsWindow cmsWindow) {
        this.cmsWindow = cmsWindow;
    }


    public String[] getPromotionIds() {
        return promotionIds;
    }

    public void setPromotionIds(String[] promotionIds) {
        this.promotionIds = promotionIds;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public CmsTemplateServce getCmsTemplateServce() {
        return cmsTemplateServce;
    }

    public void setCmsTemplateServce(CmsTemplateServce cmsTemplateServce) {
        this.cmsTemplateServce = cmsTemplateServce;
    }

    public CmsTemplate getCmsTemplate() {
        return cmsTemplate;
    }

    public void setCmsTemplate(CmsTemplate cmsTemplate) {
        this.cmsTemplate = cmsTemplate;
    }

    public String getWindowId() {
        return windowId;
    }

    public void setWindowId(String windowId) {
        this.windowId = windowId;
    }

    public CmsPageWindowService getCmsPageWindowService() {
        return cmsPageWindowService;
    }

    public void setCmsPageWindowService(CmsPageWindowService cmsPageWindowService) {
        this.cmsPageWindowService = cmsPageWindowService;
    }

    public CmsPageDetail getCmsPageDetail() {
        return cmsPageDetail;
    }

    public void setCmsPageDetail(CmsPageDetail cmsPageDetail) {
        this.cmsPageDetail = cmsPageDetail;
    }

    public CmsWindowDataService getCmsWindowDataService() {
        return cmsWindowDataService;
    }

    public void setCmsWindowDataService(CmsWindowDataService cmsWindowDataService) {
        this.cmsWindowDataService = cmsWindowDataService;
    }

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public CmsPage getCmsPage() {
        return cmsPage;
    }

    public void setCmsPage(CmsPage cmsPage) {
        this.cmsPage = cmsPage;
    }

    public CmsPageService getCmsPageService() {
        return cmsPageService;
    }

    public void setCmsPageService(CmsPageService cmsPageService) {
        this.cmsPageService = cmsPageService;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getQueryType() {
        return queryType;
    }

    public void setQueryType(Integer queryType) {
        this.queryType = queryType;
    }

    public Integer getAdminType() {
        return adminType;
    }

    public void setAdminType(Integer adminType) {
        this.adminType = adminType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public CmsSiteDataService getCmsSiteDataService() {
        return cmsSiteDataService;
    }

    public void setCmsSiteDataService(CmsSiteDataService cmsSiteDataService) {
        this.cmsSiteDataService = cmsSiteDataService;
    }

}
