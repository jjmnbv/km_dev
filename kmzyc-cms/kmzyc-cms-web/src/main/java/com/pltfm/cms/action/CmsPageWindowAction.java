package com.pltfm.cms.action;

import com.opensymphony.xwork2.ActionContext;
import com.pltfm.cms.service.CmsLogService;
import com.pltfm.cms.service.CmsPageService;
import com.pltfm.cms.service.CmsPageWindowService;
import com.pltfm.cms.vobject.CmsLog;
import com.pltfm.cms.vobject.CmsPageWindow;
import com.pltfm.sys.model.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;


@Component("cmsPageWindowAction")
@Scope(value = "prototype")
public class CmsPageWindowAction extends BaseAction {

    @Resource(name = "cmsPageWindowService")
    private CmsPageWindowService cmsPageWindowService;//cmsPageWindowService接口
    @Resource(name = "cmsPageService")
    private CmsPageService cmsPageService;//cmsPageService接口
    @Resource(name = "cmsPageAction")
    private CmsPageAction cmsPageAction;
    @Resource(name = "cmsLogService")
    private CmsLogService cmsLogService;//cmsLogService接口
    private CmsLog cmsLog = new CmsLog();//日志实体
    private String checkeds;//多选框
    private CmsPageWindow cmsPageWindow;//页面窗口对应组件
    private Integer pageId;//页面主键
    private Integer backType;//返回页面标志
    private Integer adminType;
    private static Logger logger = LoggerFactory.getLogger(CmsPageWindowAction.class);

    //插入页面窗口数据

    public void add() {
        try {
            //获取当前管理员账号
            Map session = ActionContext.getContext().getSession();
            SysUser sysUser = (SysUser) session.get("sysUser");
            cmsPageWindow = new CmsPageWindow();

            //操作日志
            cmsLog.setModuleName("页面");
            cmsLog.setIds(checkeds);
            cmsLog.setConsoleOperator(sysUser.getUserId());
            log.info(cmsLogService.add(cmsLog, pageId));
            cmsPageWindow.setPageId(pageId);

            this.cmsPageWindowService.addAll(checkeds, cmsPageWindow);
            HttpServletResponse response = ServletActionContext.getResponse();
            if (backType == null) {
                response.getWriter().print("1");//列表页
            } else {
                response.getWriter().print("2");//详细页
            }
        } catch (Exception e) {
            logger.error("CmsPageWindowAction.add报错：" + e);
        }
    }

    public CmsPageWindowService getCmsPageWindowService() {
        return cmsPageWindowService;
    }

    public void setCmsPageWindowService(CmsPageWindowService cmsPageWindowService) {
        this.cmsPageWindowService = cmsPageWindowService;
    }

    public String getCheckeds() {
        return checkeds;
    }

    public void setCheckeds(String checkeds) {
        this.checkeds = checkeds;
    }

    public CmsPageWindow getCmsPageWindow() {
        return cmsPageWindow;
    }

    public void setCmsPageWindow(CmsPageWindow cmsPageWindow) {
        this.cmsPageWindow = cmsPageWindow;
    }

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public Integer getBackType() {
        return backType;
    }

    public void setBackType(Integer backType) {
        this.backType = backType;
    }

    public CmsPageAction getCmsPageAction() {
        return cmsPageAction;
    }

    public void setCmsPageAction(CmsPageAction cmsPageAction) {
        this.cmsPageAction = cmsPageAction;
    }

    public CmsPageService getCmsPageService() {
        return cmsPageService;
    }

    public void setCmsPageService(CmsPageService cmsPageService) {
        this.cmsPageService = cmsPageService;
    }

    public Integer getAdminType() {
        return adminType;
    }

    public void setAdminType(Integer adminType) {
        this.adminType = adminType;
    }


}
