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

import com.kmzyc.zkconfig.ConfigurationUtil;
import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ActionContext;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.util.Token;
import com.pltfm.cms.service.CmsInformactionTypeService;
import com.pltfm.cms.service.CmsWindowDataService;
import com.pltfm.cms.vobject.CmsInformationType;
import com.pltfm.cms.vobject.CmsWindowData;
import com.pltfm.cms.vobject.KeyWords;
import com.pltfm.sys.bean.SysUserBean;
import com.pltfm.sys.model.SysUser;

@Component("cmsInformationTypeAction")
@Scope("prototype")
public class CmsInformationTypeAction extends BaseAction {
	private static Logger logger = LoggerFactory.getLogger(CmsInformationTypeAction.class);
    private static final long serialVersionUID = -7781882539619743331L;
    @Resource(name = "cmsInformactionTypeService")
    private CmsInformactionTypeService cmsInformactionTypeService;
    private Page page;
    private CmsInformationType infortype;
    private Integer pageNo;
    private Integer inforid;
    private Integer levelId[];
    private Integer windowId;
    private String checkeds;
    private Integer dataType;
    ActionContext actionContext = ActionContext.getContext();
    Map session = actionContext.getSession();
    /**
     * 页面Id
     */
    private Integer pageId;
    /**
     * 角色区分
     */
    private Integer adminType;

    /**
     * 窗口数据业务逻辑层接口
     */
    @Resource(name = "cmsWindowDataService")
    private CmsWindowDataService cmsWindowDataService;


    public String List() {
        try {
            keyWords = null;
            if (infortype != null) {
                infortype.setSiteId((Integer) session.get("siteId"));
            } else {
                infortype = new CmsInformationType();
                infortype.setSiteId((Integer) session.get("siteId"));
            }
            page = cmsInformactionTypeService.queryForPage(infortype, page);
            //获取对应的管理员
            sysUserMap = new HashMap();
            SysUserBean bean = SysUserBean.instance();
            SysUser vo = new SysUser();
            List<SysUser> list = bean.getSysUserList(vo);
            for (SysUser user : list) {
                sysUserMap.put(user.getUserId() + "", user.getUserName());
            }
            return "Success";
        } catch (Exception e) {
            logger.error("CmsInformationTypeAction.List资讯Type List异常：" + e.getMessage(), e);
            return "Error";
        }
    }

    /**
     * 保留关键字查询
     */
    public String queryInformationType() {
        try {
            infortype = new CmsInformationType();
            if (session.get("siteId") != null) {
                infortype.setSiteId((Integer) session.get("siteId"));
            }
            if (page == null && keyWords != null) {
                page = new Page();
                page.setPageNo(keyWords.getPageNo());
                page.setPageSize(keyWords.getPageSize());
            }
            if (keyWords != null) {
                infortype.setName(keyWords.getName_keyword().trim());
                if (keyWords.getStatus_keyword() == null)
                    infortype.setStatus(null);
                else
                    infortype.setStatus(keyWords.getStatus_keyword().shortValue());
            }
            page = cmsInformactionTypeService.queryForPage(infortype, page);
            if (keyWords == null) {
                keyWords = new KeyWords();
            }
            keyWords.setPageNo(this.page.getPageNo());
            keyWords.setPageSize(page.getPageSize());
            //获取对应的管理员
            sysUserMap = new HashMap();
            SysUserBean bean = SysUserBean.instance();
            SysUser vo = new SysUser();
            List<SysUser> list = bean.getSysUserList(vo);
            for (SysUser user : list) {
                sysUserMap.put(user.getUserId() + "", user.getUserName());
            }
            return "Success";
        } catch (Exception e) {
            logger.error("CmsInformationTypeAction.queryInformationType资讯Type 保留关键字查询异常：" + e.getMessage(), e);
            return "Error";
        }
    }

    /**
     * 跳转数据列表页面
     */
    public String openInformationTypeList() {
        return "openInformationTypeList";
    }

    /**
     * 弹出层保留关键字查询
     */
    public String queryPopInformationType() {
        try {
            infortype = new CmsInformationType();
            if (session.get("siteId") != null) {
                infortype.setSiteId((Integer) session.get("siteId"));
            }
            if (page == null && keyWords != null) {
                page = new Page();
                page.setPageNo(keyWords.getPageNo());
                page.setPageSize(keyWords.getPageSize());
            }
            if (keyWords != null) {
                infortype.setName(keyWords.getName_keyword().trim());
                if (keyWords.getStatus_keyword() == null)
                    infortype.setStatus(null);
                else
                    infortype.setStatus(keyWords.getStatus_keyword().shortValue());
            }
            page = cmsInformactionTypeService.queryForPage(infortype, page);
            Integer siteId = (Integer) session.get("siteId");
            List<CmsWindowData> dataList = this.cmsWindowDataService.queryByWindowIdDataType(this.windowId, 8, siteId);
            List listData = page.getDataList();
            if (dataList.size() != 0) {
                for (int i = 0; i < listData.size(); i++) {
                    CmsInformationType cmsInformationType = (CmsInformationType) listData.get(i);
                    for (int j = 0; j < dataList.size(); j++) {
                        CmsWindowData cmsWinData = dataList.get(j);
                        if (cmsInformationType.getId().equals(cmsWinData.getDataId())) {
                            cmsInformationType.setFlag(1);
                            break;
                        }
                    }
                }
            }

            if (keyWords == null) {
                keyWords = new KeyWords();
            }
            keyWords.setPageNo(this.page.getPageNo());
            keyWords.setPageSize(page.getPageSize());
            //获取对应的管理员
            sysUserMap = new HashMap();
            SysUserBean bean = SysUserBean.instance();
            SysUser vo = new SysUser();
            List<SysUser> list = bean.getSysUserList(vo);
            for (SysUser user : list) {
                sysUserMap.put(user.getUserId() + "", user.getUserName());
            }
            return "queryPopInformationType";
        } catch (Exception e) {
            logger.error("CmsInformationTypeAction.queryPopInformationType资讯Type 弹出层保留关键字查询异常：" + e.getMessage(), e);
            return "Error";
        }
    }

    public String gotoAdd() {
        return "gotoAdd";
    }

    /*  添加资讯类别 */
    @Token
    public String Add() {
        try {
            SysUser sysuser = (SysUser) session.get("sysUser");
            //按照站点来添加站点数据
            infortype.setSiteId((Integer) session.get("siteId"));
            infortype.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
            infortype.setCreated(sysuser.getUserId());
            infortype.setModifyDate(DateTimeUtils.getCalendarInstance().getTime());
            infortype.setModified(sysuser.getUserId());
            cmsInformactionTypeService.add(infortype);
            infortype = new CmsInformationType();
            this.addActionMessage(ConfigurationUtil.getString("add.success"));
        } catch (Exception e) {
            this.addActionMessage(ConfigurationUtil.getString("add.fail"));
            logger.error("CmsInformationTypeAction.Add添加资讯类别异常：" + e.getMessage(), e);
            this.keyWords = null;
            return queryInformationType();
        }
        this.keyWords = null;
        return queryInformationType();
    }

    public String Byid() {
        try {

            infortype = cmsInformactionTypeService.byid(inforid);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("CmsInformationTypeAction.Byid资讯Type Byid异常：" + e.getMessage(), e);
            return "Error";
        }
        return "ByidSuccess";
    }

    @Token
    public String Update() {
        try {
            SysUser sysuser = (SysUser) session.get("sysUser");
            infortype.setModifyDate(DateTimeUtils.getCalendarInstance().getTime());
            infortype.setModified(sysuser.getUserId());
            cmsInformactionTypeService.update(infortype);
            infortype = new CmsInformationType();
            this.addActionMessage(ConfigurationUtil.getString("update.success"));
        } catch (Exception e) {
            this.addActionMessage(ConfigurationUtil.getString("update.fail"));
            logger.error("CmsInformationTypeAction.Update资讯Type 修改异常：" + e.getMessage(), e);
            this.keyWords = null;
            return queryInformationType();
        }
        this.keyWords = null;
        return queryInformationType();
    }

    /**
     * 根据ID删除资讯类信息
     */
    public String Delete() {
        try {
            if (cmsInformactionTypeService.delete(inforid) == 0) {
                this.addActionMessage(ConfigurationUtil.getString("delete.fail"));
            } else {
                this.addActionMessage(ConfigurationUtil.getString("delete.success"));
            }
        } catch (Exception e) {
            this.addActionMessage(ConfigurationUtil.getString("delete.fail"));
            logger.error("CmsInformationTypeAction.Delete资讯Type 根据ID删除资讯类信息异常：" + e.getMessage(), e);
            this.keyWords = null;
            return queryInformationType();
        }
        this.keyWords = null;
        return queryInformationType();
    }

    /**
     * 根据ID删除资讯类信息前判断此类型下有没有文章
     */
    public void ByInforId() {
        try {
            int count = cmsInformactionTypeService.byInforId(inforid);
            String re = "0";
            if (count > 0) {
                re = "1";
            }
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().print(re);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("CmsInformationTypeAction.ByInforId资讯Type 根据ID删除资讯类信息前判断此类型下有没有文章异常：" + e.getMessage(), e);

        }
    }

    /**
     * 根据ID数组删除广告位信息
     */
    @Token
    public String All_Delete() {
        try {
            int s = 0;
            for (int i = 0; i < levelId.length; i++) {
                s = cmsInformactionTypeService.delete(levelId[i]) + s;
            }
            if (s == levelId.length) {
                this.addActionMessage(ConfigurationUtil.getString("delete.success"));
            }
            if (s == 0) {
                this.addActionMessage("此文章类型下有文章，如需删除请先清除此类型下文章。");
            }
            if (s > 0 && s < levelId.length) {
                this.addActionMessage("部分删除失败");
            }

        } catch (Exception e) {
            this.addActionMessage(ConfigurationUtil.getString("delete.fail"));
            logger.error("CmsInformationTypeAction.All_Delete资讯Type 根据ID数组删除广告位信息异常：" + e.getMessage(), e);
            this.keyWords = null;
            return queryInformationType();
        }
        this.keyWords = null;
        return queryInformationType();
    }

    /**
     * 查广告位名是否存在
     */
    public void name_ajax() {
        try {
            Integer siteId = (Integer) session.get("siteId");
            infortype.setSiteId(siteId);
            int count = cmsInformactionTypeService.countByName(infortype);
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().print(count);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("CmsInformationTypeAction.name_ajax资讯Type 查询名称是否存在常：" + e.getMessage(), e);

        }
    }


    public CmsInformactionTypeService getCmsInformactionTypeService() {
        return cmsInformactionTypeService;
    }

    public void setCmsInformactionTypeService(
            CmsInformactionTypeService cmsInformactionTypeService) {
        this.cmsInformactionTypeService = cmsInformactionTypeService;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public CmsInformationType getInfortype() {
        return infortype;
    }

    public void setInfortype(CmsInformationType infortype) {
        this.infortype = infortype;
    }

    public Integer getInforid() {
        return inforid;
    }

    public void setInforid(Integer inforid) {
        this.inforid = inforid;
    }

    public Integer[] getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer[] levelId) {
        this.levelId = levelId;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getWindowId() {
        return windowId;
    }

    public void setWindowId(Integer windowId) {
        this.windowId = windowId;
    }

    public String getCheckeds() {
        return checkeds;
    }

    public void setCheckeds(String checkeds) {
        this.checkeds = checkeds;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
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

    public Integer getAdminType() {
        return adminType;
    }

    public void setAdminType(Integer adminType) {
        this.adminType = adminType;
    }


}
