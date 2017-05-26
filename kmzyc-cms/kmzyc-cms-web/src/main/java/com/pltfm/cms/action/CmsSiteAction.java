package com.pltfm.cms.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.zkconfig.ConfigurationUtil;
import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ActionContext;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.util.ListUtils;
import com.pltfm.app.util.Token;
import com.pltfm.cms.parse.PathConstants;
import com.pltfm.cms.service.CmsSiteService;
import com.pltfm.cms.service.CmsUserSiteService;
import com.pltfm.cms.vobject.CmsSite;
import com.pltfm.cms.vobject.CmsUser;
import com.pltfm.cms.vobject.CmsUserSite;
import com.pltfm.sys.bean.SysUserBean;
import com.pltfm.sys.model.SysUser;

@Component("cmsSiteAction")
@Scope("prototype")
public class CmsSiteAction extends BaseAction {
	private static Logger logger = LoggerFactory.getLogger(CmsSiteAction.class);
    @Resource(name = "cmsSiteService")
    private CmsSiteService cmsSiteService;
    @Resource(name = "cmsUserSiteService")
    private CmsUserSiteService cmsUserSiteService;
    private CmsSite cmsSite = new CmsSite();//站点实体
    private CmsUser cmsUser = new CmsUser();//用户实体
    private CmsUserSite cmsUserSite = new CmsUserSite();//站点用户集合实体
    private List<Integer> siteIds;//站点id集合
    private Integer siteId;//站点id
    private Page page;//分页组
    private String Ids;//站点id集合
    private Integer userId;//userId
    private List<CmsUserSite> cmsSiteList;
    private List<Integer> delId;//用户id集合
    private Integer types;//选择转向
    /*@Resource(name="templateConfig")
    private Map<String, String> templateConfig;*/
    private String checkeds;//多选框
    private String userSiteIds;// 站点用户id


    //分页列表
    public String queryForPage() {
        try {
            page = cmsSiteService.searchPageByVo(page, cmsSite);
            sysUserMap = new HashMap();
            SysUserBean bean = SysUserBean.instance();
            SysUser vo = new SysUser();
            List<SysUser> list = bean.getSysUserList(vo);
            for (SysUser user : list) {
                sysUserMap.put(user.getUserId() + "", user.getUserName());
            }

        } catch (Exception e) {
            logger.error("CmsSiteAction.queryForPage异常：" + e.getMessage(), e);
        }
        return "siteList";
    }

    //站点受权分页列表
    public String querySiteUserPage() {
        try {
            //page =cmsSiteService.searchPageByVo(page, cmsSite);
            page = cmsSiteService.searchUserByVo(page, cmsUser);
            cmsSiteList = cmsUserSiteService.cmsUserSite(0);
        } catch (Exception e) {
            logger.error("CmsSiteAction.querySiteUserPage异常：" + e.getMessage(), e);
        }
        return "siteUserList";
    }

    // 跟据站点ID查询详情
    /*public String gotoDetail()
	{
		try {
			//获取对应的管理员
			sysUserMap=new HashMap();
			SysUserBean bean = SysUserBean.instance();
		    SysUser vo=new SysUser();
		    List<SysUser> list=bean.getSysUserList(vo);
		    for(SysUser user:list)
		    {
		    	sysUserMap.put(user.getUserId()+"", user.getUserName());
		    }
			HttpServletRequest request = ServletActionContext.getRequest();
			if(request.getParameter("siteId")!=null){
				this.siteId=Integer.parseInt(request.getParameter("siteId"));
			}
			
			this.cmsSite=this.cmsSiteService.selectByPrimaryKey(this.siteId);
			cmsUserSite.setSiteId(siteId);
			page =cmsUserSiteService.searchPageByVo(page,cmsUserSite);
			checkeds=null;
		} catch (Exception e) {
			logger.error("异常：" + e.getMessage(), e);
		}
		return "gotoDetail";
	}*/

    // 跟据用户ID查询详情
    public String gotoDetail() {
        try {
            //获取对应的管理员
            sysUserMap = new HashMap();
            SysUserBean bean = SysUserBean.instance();
            SysUser vo = new SysUser();
            List<SysUser> list = bean.getSysUserList(vo);
            for (SysUser user : list) {
                sysUserMap.put(user.getUserId() + "", user.getUserName());
            }
            HttpServletRequest request = ServletActionContext.getRequest();
            if (request.getParameter("userId") != null) {
                this.userId = Integer.parseInt(request.getParameter("userId"));
            }

            this.cmsUser = this.cmsSiteService.selectUser(userId);
            cmsUserSite.setUserId(userId);
            page = cmsUserSiteService.searchPageByVo(page, cmsUserSite);
            checkeds = null;
        } catch (Exception e) {
            logger.error("CmsSiteAction.gotoDetail异常：" + e.getMessage(), e);
        }
        return "gotoDetail";
    }
    // 多删除站点下的用户

    public String delCmsUserSites() {
        try {
            this.cmsSiteService.delUserTite(checkeds);
            this.addActionMessage(ConfigurationUtil.getString("delete.success"));
        } catch (Exception e) {
            this.addActionMessage(ConfigurationUtil.getString("delete.fail"));
            logger.error("CmsSiteAction.delCmsUserSites异常：" + e.getMessage(), e);
        }
        return this.gotoDetail();
    }

    // 单删除站点下的用户
    public String delUserSiteIds() {
        try {
            this.cmsSiteService.delUserTite(userSiteIds);
            this.addActionMessage(ConfigurationUtil.getString("delete.success"));
        } catch (Exception e) {
            this.addActionMessage(ConfigurationUtil.getString("delete.fail"));
            logger.error("CmsSiteAction.delUserSiteIds异常：" + e.getMessage(), e);
        }
        return this.gotoDetail();
    }

    //选择站点列表
    public String popUPquery() {
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            if (request.getParameter("userId") != null) {
                userId = Integer.parseInt(request.getParameter("userId"));
                cmsSite.setUserId(userId);
            }
            page = cmsSiteService.searchPageByVo(page, cmsSite);
        } catch (Exception e) {
            logger.error("CmsSiteAction.popUPquery异常：" + e.getMessage(), e);
        }
        return "popUPquery";
    }
    //保存选择的用户

    public void addUser() {
        try {
            //获取当前管理员账号
            Map session = ActionContext.getContext().getSession();
            SysUser sysUser = (SysUser) session.get("sysUser");
            cmsUserSiteService.addUuseStie(delId, siteId, sysUser.getUserId());
        } catch (Exception e) {
            logger.error("CmsSiteAction.addUser异常：" + e.getMessage(), e);
        }
		/*if(types!=null){
			return this.gotoDetail();
		}
		return this.queryForPage();*/
    }


    //保存选择的站点
    public String addSite() {
        try {
            //获取当前管理员账号
            //Map session = ActionContext.getContext().getSession();
            //SysUser sysUser=(SysUser) session.get("sysUser");
            cmsUserSiteService.insert(siteIds, userId);
            PathConstants.updateSiteInfo();
            this.addActionMessage(ConfigurationUtil.getString("add.success"));
        } catch (Exception e) {
            this.addActionError(ConfigurationUtil.getString("add.fail"));
            logger.error("CmsSiteAction.addSite异常：" + e.getMessage(), e);
        }
        return this.querySiteUserPage();
    }


    //goto新增站点
    public String gotoAddCmsSite() {
        cmsSite = new CmsSite();
        return "gotoAddCmsSite";
    }

    //增加站点
    @Token
    public String addCmsSite() {
        try {
            //获取当前管理员账号
            Map session = ActionContext.getContext().getSession();
            SysUser sysUser = (SysUser) session.get("sysUser");
            this.cmsSite.setCreated(sysUser.getUserId());
            this.cmsSite.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
            this.cmsSiteService.insert(cmsSite);
            cmsSite = new CmsSite();
            //更新初始化站点信息
            PathConstants.updateSiteInfo();
            this.addActionMessage(ConfigurationUtil.getString("add.success"));
        } catch (Exception e) {
            this.addActionError(ConfigurationUtil.getString("add.fail"));
            logger.error("CmsSiteAction.addCmsSite异常：" + e.getMessage(), e);
        }
        return this.queryForPage();
    }

    //goto站点
    public String gotoEditCmsSite() {
        try {
            this.cmsSite = this.cmsSiteService.selectByPrimaryKey(this.siteId);
        } catch (Exception e) {
            logger.error("CmsSiteAction.gotoEditCmsSite异常：" + e.getMessage(), e);
        }
        return "gotoEditCmsSite";
    }

    //修改站点
    @Token
    public String editCmsSite() {
        try {
            //获取当前管理员账号
            Map session = ActionContext.getContext().getSession();
            SysUser sysUser = (SysUser) session.get("sysUser");
            this.cmsSite.setModified(sysUser.getUserId());
            this.cmsSite.setModifyDate(DateTimeUtils.getCalendarInstance().getTime());
            this.cmsSiteService.update(cmsSite);
            cmsSite = new CmsSite();
            //更新初始化站点信息
            PathConstants.updateSiteInfo();
            this.addActionMessage(ConfigurationUtil.getString("update.success"));
        } catch (Exception e) {
            this.addActionMessage(ConfigurationUtil.getString("update.fail"));
            logger.error("CmsSiteAction.editCmsSite异常：" + e.getMessage(), e);
        }
        return this.queryForPage();
    }
    //删除站点

    public String delCmsSite() {
        try {
            this.cmsSiteService.delete(siteIds);
            //更新初始化站点信息
            PathConstants.updateSiteInfo();
            this.addActionMessage(ConfigurationUtil.getString("delete.success"));
        } catch (Exception e) {
            this.addActionMessage(ConfigurationUtil.getString("delete.fail"));
            logger.error("CmsSiteAction.delCmsSite异常：" + e.getMessage(), e);
        }
        return this.queryForPage();
    }

    //检测名字是否唯一
    public void check() {
        try {
            List list = this.cmsSiteService.selectIsName(cmsSite);//传入站点名称，查询是否有记录
            int value = 0;
            if (ListUtils.isNotEmpty(list)) {//list是否有值
                if (siteId != null) {
                    CmsSite cms = (CmsSite) list.get(0);
                    if (cms.getSiteId().equals(siteId)) {//查询出的站点id与更新时的传入的id比较
                        value = 0;
                    } else {
                        value = 1;
                    }
                } else {
                    value = 1;
                }

                super.writeJson(value);
            }
        } catch (Exception e) {
            logger.error("CmsSiteAction.check异常：" + e.getMessage(), e);
        }
    }

    //检测是否关系了
    public void checkUserSite() {
        try {
            int value = this.cmsUserSiteService.checkUserSite(siteId);//传入站点id，查询是否有记录
            super.writeJson(value);
        } catch (Exception e) {
            logger.error("CmsSiteAction.checkUserSite异常：" + e.getMessage(), e);
        }
    }


    public CmsSiteService getCmsSiteService() {
        return cmsSiteService;
    }

    public void setCmsSiteService(CmsSiteService cmsSiteService) {
        this.cmsSiteService = cmsSiteService;
    }

    public CmsSite getCmsSite() {
        return cmsSite;
    }

    public void setCmsSite(CmsSite cmsSite) {
        this.cmsSite = cmsSite;
    }

    public List<Integer> getSiteIds() {
        return siteIds;
    }

    public void setSiteIds(List<Integer> siteIds) {
        this.siteIds = siteIds;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public String getIds() {
        return Ids;
    }

    public Integer getUserId() {
        return userId;
    }


    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setIds(String ids) {
        Ids = ids;
    }

    public CmsUserSiteService getCmsUserSiteService() {
        return cmsUserSiteService;
    }


    public void setCmsUserSiteService(CmsUserSiteService cmsUserSiteService) {
        this.cmsUserSiteService = cmsUserSiteService;
    }

    public CmsUserSite getCmsUserSite() {
        return cmsUserSite;
    }


    public void setCmsUserSite(CmsUserSite cmsUserSite) {
        this.cmsUserSite = cmsUserSite;
    }

    public String getUserSiteIds() {
        return userSiteIds;
    }

    public void setUserSiteIds(String userSiteIds) {
        this.userSiteIds = userSiteIds;
    }

    public List<Integer> getDelId() {
        return delId;
    }

    public void setDelId(List<Integer> delId) {
        this.delId = delId;
    }

    public Integer getTypes() {
        return types;
    }

    public void setTypes(Integer types) {
        this.types = types;
    }

    public String getCheckeds() {
        return checkeds;
    }

    public void setCheckeds(String checkeds) {
        this.checkeds = checkeds;
    }

    public CmsUser getCmsUser() {
        return cmsUser;
    }

    public void setCmsUser(CmsUser cmsUser) {
        this.cmsUser = cmsUser;
    }

    public List<CmsUserSite> getCmsSiteList() {
        return cmsSiteList;
    }

    public void setCmsSiteList(List<CmsUserSite> cmsSiteList) {
        this.cmsSiteList = cmsSiteList;
    }
	/*public Map<String, String> getTemplateConfig() {
		return templateConfig;
	}

	public void setTemplateConfig(Map<String, String> templateConfig) {
		this.templateConfig = templateConfig;
	}*/
}
