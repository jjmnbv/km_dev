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

import com.kmzyc.commons.page.Page;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.opensymphony.xwork2.ActionContext;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.util.Token;
import com.pltfm.cms.service.CmsAdcolumnDataService;
import com.pltfm.cms.vobject.CmsAdcolumn;
import com.pltfm.cms.vobject.KeyWords;
import com.pltfm.sys.bean.SysUserBean;
import com.pltfm.sys.model.SysUser;


@Scope(value = "prototype")
@Component("cmsAdcolumnAction")

public class CmsAdcolumnAction extends BaseAction {
	private static Logger logger = LoggerFactory.getLogger(CmsAdcolumnAction.class);
    private List<CmsAdcolumn> AdvList;
    ActionContext actionContext = ActionContext.getContext();
    Map session = actionContext.getSession();
    /**
     * 广告位业务逻辑层接口
     */
    @Resource(name = "cmsAdcolumnDataService")
    private CmsAdcolumnDataService cmsAdcolumnDataService;
    private CmsAdcolumn cmsAdcolumn;
    private Integer Advid;
    private Integer adminType;//角色区分
    private String checkeds;
    private Integer[] levelId;
    private Integer pageNo, siteId;

    /**
     * 广告位类型
     */
    private Integer colType;
    /**
     * 分页对象
     */
    private Page page;


    public String gotoAdd() {
        return "gotoAdd";
    }

    /**
     * 添加广告位信息
     */
    @Token
    public String Add() {
        try {
            SysUser sysUser = (SysUser) session.get("sysUser");
            siteId = (Integer) session.get("siteId");
            cmsAdcolumn.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
            cmsAdcolumn.setCreated(sysUser.getUserId());
            cmsAdcolumn.setModifyDate(DateTimeUtils.getCalendarInstance().getTime());
            cmsAdcolumn.setModified(sysUser.getUserId());
            cmsAdcolumn.setSiteId(siteId);
            cmsAdcolumnDataService.addCmsAdcolumn(cmsAdcolumn);
        } catch (Exception e) {
            this.addActionMessage(ConfigurationUtil.getString("add.fail"));
            logger.error("CmsAdcolumnAction.Add添加广告位信息异常：" + e.getMessage(), e);
            return queryAdcolumn();
        }
        this.addActionMessage(ConfigurationUtil.getString("add.success"));
        //cmsAdcolumn=new CmsAdcolumn();
        this.keyWords = new KeyWords();
        keyWords.setName_keyword(cmsAdcolumn.getName());
        return queryAdcolumn();
    }

    /**
     * 根据ID删除广告位信息
     */
    public String Delete() {
        try {
            int s = cmsAdcolumnDataService.delete(Advid);
            if (s == 0) {
                this.addActionMessage(ConfigurationUtil.getString("delete.fail"));
            } else {
                this.addActionMessage(ConfigurationUtil.getString("delete.success"));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            this.addActionMessage(ConfigurationUtil.getString("delete.fail"));
            logger.error("CmsAdcolumnAction.Delete根据ID删除广告位信息异常：" + e.getMessage(), e);
            return "Error";
        }
        this.keyWords = null;
        return queryAdcolumn();
    }

    /**
     * 根据ID数组删除广告位信息
     */
    @Token
    public String All_Delete() {
        try {
            int s = 0;
            for (int i = 0; i < levelId.length; i++) {
                s = cmsAdcolumnDataService.delete(levelId[i]) + s;
            }
            if (s == levelId.length) {
                this.addActionMessage(ConfigurationUtil.getString("delete.success"));
            }
            if (s == 0) {
                this.addActionMessage("此广告位下有广告，如需删除请先清除此广告位下广告。");
            }
            if (s > 0 && s < levelId.length) {
                this.addActionMessage("部分删除失败");
            }


        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("CmsAdcolumnAction.All_Delete根据ID数组删除广告位信息异常：" + e.getMessage(), e);
            this.keyWords = null;
            return queryAdcolumn();
        }
        this.keyWords = null;
        return queryAdcolumn();
    }

    /**
     * 根据ID查广告位信息
     */
    public String Byid() {
        try {

            cmsAdcolumn = cmsAdcolumnDataService.byid(Advid);
            colType = cmsAdcolumn.getOutput().indexOf("prodBrand_");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("CmsAdcolumnAction.Byid根据ID查广告位信息异常：" + e.getMessage(), e);
            return "Error";
        }
        return "ByidSuccess";
    }

    /**
     * 根据ID查广告位是否有广告信息
     */
    public void ByAdId() {
        try {
            int coumt = cmsAdcolumnDataService.byAdId(Advid);
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setContentType("text/html;charset=utf-8");
            String re = "0";
            if (coumt > 0) {
                re = "1";
            }
            response.getWriter().print(re);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("CmsAdcolumnAction.ByAdId根据ID查广告位是否有广告信息异常：" + e.getMessage(), e);


        }

    }


    /**
     * 修改广告位信息
     */
    @Token
    public String Update() {
        try {
            SysUser sysuser = (SysUser) session.get("sysUser");
            cmsAdcolumn.setModifyDate(DateTimeUtils.getCalendarInstance().getTime());
            cmsAdcolumn.setModified(sysuser.getUserId());
            cmsAdcolumnDataService.update(cmsAdcolumn);
            //  cmsAdcolumn=new CmsAdcolumn();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("CmsAdcolumnAction.Update修改广告位信息异常：" + e.getMessage(), e);
            this.addActionMessage(ConfigurationUtil.getString("update.fail"));
            this.keyWords = null;
            return queryAdcolumn();
        }
        this.addActionMessage(ConfigurationUtil.getString("update.success"));
        //	this.keyWords=null;
        //保持状态
        this.keyWords = new KeyWords();
        keyWords.setName_keyword(cmsAdcolumn.getName());
        return queryAdcolumn();
    }

    /**
     * 分页查询广告位数据信息
     */
    public String List() {
        try {
            keyWords = null;
            siteId = (Integer) session.get("siteId");
            cmsAdcolumn.setSiteId(siteId);
            page = cmsAdcolumnDataService.queryForPage(cmsAdcolumn, page);
            return "Success";
        } catch (Exception e) {
            logger.error("CmsAdcolumnAction.List分页查询广告位信息异常：" + e.getMessage(), e);
            return "Error";
        }
    }

    /**
     * 带关键字查询
     */
    public String queryAdcolumn() {
        try {
            siteId = (Integer) session.get("siteId");
            if (cmsAdcolumn == null) {
                cmsAdcolumn = new CmsAdcolumn();
                cmsAdcolumn.setSiteId(siteId);
            }

            if (page == null && keyWords != null) {
                page = new Page();

                if (keyWords.getPageNo() != null && keyWords.getPageSize() != null) {
                    page.setPageNo(keyWords.getPageNo());
                    page.setPageSize(keyWords.getPageSize());
                }
            }
            if (keyWords != null) {

                if (keyWords.getName_keyword() != null) {
                    cmsAdcolumn.setName(keyWords.getName_keyword().trim());
                }
                if (keyWords.getOutPath_keyword() != null) {
                    cmsAdcolumn.setOutput(keyWords.getOutPath_keyword());
                }

                if (keyWords.getStatus_keyword() == null)
                    cmsAdcolumn.setStatus(null);
                else
                    cmsAdcolumn.setStatus(keyWords.getStatus_keyword().shortValue());
            }
            page = cmsAdcolumnDataService.queryForPage(cmsAdcolumn, page);
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
            return "Success";
        } catch (Exception e) {
            logger.error("CmsAdcolumnAction.queryAdcolumn广告位带关键字查询信息异常：" + e.getMessage(), e);
            return "Error";
        }
    }


    /**
     * 查广告位名是否存在
     */
    public void name_ajax() {
        try {
            siteId = (Integer) session.get("siteId");
            cmsAdcolumn.setSiteId(siteId);
            int count = cmsAdcolumnDataService.countByAdcolumnname(cmsAdcolumn);
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().print(count);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("CmsAdcolumnAction.name_ajax查广告位名是否存在查询信息异常：" + e.getMessage(), e);
        }
    }


    public CmsAdcolumn getCmsAdcolumn() {
        return cmsAdcolumn;
    }

    public void setCmsAdcolumn(CmsAdcolumn cmsAdcolumn) {
        this.cmsAdcolumn = cmsAdcolumn;
    }

    public CmsAdcolumnDataService getCmsAdcolumnDataService() {
        return cmsAdcolumnDataService;
    }

    public void setCmsAdcolumnDataService(
            CmsAdcolumnDataService cmsAdcolumnDataService) {
        this.cmsAdcolumnDataService = cmsAdcolumnDataService;
    }

    public List<CmsAdcolumn> getAdvList() {
        return AdvList;
    }

    public void setAdvList(List<CmsAdcolumn> advList) {
        AdvList = advList;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Integer getAdvid() {
        return Advid;
    }

    public void setAdvid(Integer advid) {
        Advid = advid;
    }

    public Integer[] getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer[] levelId) {
        this.levelId = levelId;
    }

    public String getCheckeds() {
        return checkeds;
    }

    public void setCheckeds(String checkeds) {
        this.checkeds = checkeds;
    }

    public Integer getAdminType() {
        return adminType;
    }

    public void setAdminType(Integer adminType) {
        this.adminType = adminType;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Integer getColType() {
        return colType;
    }

    public void setColType(Integer colType) {
        this.colType = colType;
    }


}
