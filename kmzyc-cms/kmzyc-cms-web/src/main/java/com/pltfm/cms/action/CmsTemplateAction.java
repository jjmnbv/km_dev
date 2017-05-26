package com.pltfm.cms.action;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
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
import com.pltfm.cms.parse.PathConstants;
import com.pltfm.cms.service.CmsAdvService;
import com.pltfm.cms.service.CmsLogService;
import com.pltfm.cms.service.CmsSupplierAdcolumnService;
import com.pltfm.cms.service.CmsTemplateRelationService;
import com.pltfm.cms.service.CmsTemplateServce;
import com.pltfm.cms.util.FileOperateUtils;
import com.pltfm.cms.util.SqlJoinUtil;
import com.pltfm.cms.vobject.CmsAdcolumn;
import com.pltfm.cms.vobject.CmsAdv;
import com.pltfm.cms.vobject.CmsLog;
import com.pltfm.cms.vobject.CmsPageDetail;
import com.pltfm.cms.vobject.CmsSupplierAdcolumn;
import com.pltfm.cms.vobject.CmsTemplate;
import com.pltfm.cms.vobject.CmsTemplateRelation;
import com.pltfm.cms.vobject.CmsTemplateRelationExample;
import com.pltfm.cms.vobject.KeyWords;
import com.pltfm.sys.bean.SysUserBean;
import com.pltfm.sys.model.SysUser;

@Component("cmsTemplateAction")
@Scope("prototype")
public class CmsTemplateAction extends BaseAction {
    ActionContext actionContext = ActionContext.getContext();
    Map session = actionContext.getSession();
    @Resource(name = "cmsTemplateServce")
    private CmsTemplateServce cmsTemplateServce;

    @Resource(name = "cmsAdvService")
    CmsAdvService cmsAdvService;
    @Resource(name = "cmsLogService")
    private CmsLogService cmsLogService;//cmsLogService接口
    @Resource(name = "cmsSupplierAdcolumnService")
    private CmsSupplierAdcolumnService cmsSupplierAdcolumnService;
    private CmsLog cmsLog = new CmsLog();//日志实体
    private CmsTemplate cmsTemplate;
    private Page page;
    private Integer templateId;
    private String name;//模板名称
    private String checkeds;
    private File template;//获取上传文件
    private String templateFileName;//获取文件名
    private String templateContentType;//获取文件类型
    private String pid;
    private String path;
    private Integer pageNo;//页码
    private CmsPageDetail cmsPageDetail;//详细页
    private String place;
    private String type;
    @Resource(name = "cmsTemplateRelationService")
    private CmsTemplateRelationService cmsTemplateRelationService;

    List<CmsTemplate> templateList;

    private CmsTemplateRelation cmsTemplateRelation;
    //日志
    private static Logger logger = LoggerFactory.getLogger(CmsTemplateAction.class);

    /**
     * 最后确认数据Id集合
     */
    private List<Integer> dataIds;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    //模板列表
    public String queryTemplate() {
        try {
            Integer siteId = (Integer) session.get("siteId");

            if (cmsTemplate == null) {
                cmsTemplate = new CmsTemplate();
            }
            if (page == null && keyWords != null) {
                page = new Page();

                if (keyWords.getPageNo() != null && keyWords.getPageSize() != null) {
                    page.setPageNo(keyWords.getPageNo());
                    page.setPageSize(keyWords.getPageSize());
                }
            }
            if (keyWords != null) {

                if (keyWords.getId_keyword() != null) {
                    cmsTemplate.setTemplateId(keyWords.getId_keyword());
                }
                if (keyWords.getName_keyword() != null) {
                    cmsTemplate.setName(keyWords.getName_keyword().trim());
                }
                if (keyWords.getTheme_keyword() != null) {
                    cmsTemplate.setTheme(keyWords.getTheme_keyword());
                }
                if (keyWords.getStatus_keyword() != null)
                    cmsTemplate.setStatus(keyWords.getStatus_keyword());
                if (keyWords.getType_keyword() != null)
                    cmsTemplate.setType(keyWords.getType_keyword());
            }
            if (null != siteId) {
                cmsTemplate.setSiteId(siteId);
            }

            this.page = cmsTemplateServce.queryForPage(cmsTemplate, page);
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
            return "templateList";
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("CmsWindowDataAction.queryTemplate报错：" + e);
            return "error";
        }
    }

    //变量模板列表
    public String queryVarTemplate() {
        try {
            Integer siteId = (Integer) session.get("siteId");

            if (cmsTemplate == null) {
                cmsTemplate = new CmsTemplate();
            }
            if (null != siteId) {
                cmsTemplate.setSiteId(siteId);
            }

            this.page = cmsTemplateServce.queryVarTemplateList(cmsTemplate, page);
            if (keyWords == null) {
                keyWords = new KeyWords();
            }
            sysUserMap = new HashMap();
            SysUserBean bean = SysUserBean.instance();
            SysUser vo = new SysUser();
            List<SysUser> list = bean.getSysUserList(vo);
            for (SysUser user : list) {
                sysUserMap.put(user.getUserId() + "", user.getUserName());
            }
            return "varTemplateList";
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("CmsWindowDataAction.queryVarTemplate报错：" + e);
            return "error";
        }
    }

    //goto已绑定数据列表页
    public String gotoBandList() {
        try {
            if (cmsTemplate == null) {
                cmsTemplate = new CmsTemplate();
            }
            page = cmsTemplateServce.queryBandWindowList(cmsTemplate, page);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("CmsWindowDataAction.queryVarTemplate报错：" + e);
        }
        return "gotoTemplateBandList";
    }

    //goto绑定数据页(除去已绑定)
    public String gotoWindowList() {

        try {
            if (cmsTemplate == null) {
                cmsTemplate = new CmsTemplate();
            }
            page = cmsTemplateServce.queryTemplateFilterBandWindow(cmsTemplate, page);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("CmsWindowDataAction.gotoWindowList报错：" + e);
        }
        return "gotoWindowList";
    }


    //前往绑定确认页面
    public String gotoConfirmBand() {
        //根据选择的ID得到集合
        List<Integer> tempList = new ArrayList();
        if (checkeds != null) {
            String[] checkStrs = checkeds.split(",");
            for (int i = 0; i < checkStrs.length; i++) {
                tempList.add(Integer.parseInt(checkStrs[i]));
            }
        }
        try {
            if (tempList != null) {
                templateList = cmsTemplateServce.querySeletedTemplateList(SqlJoinUtil.getSqlIn(tempList, 1000, "template_id"));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("CmsWindowDataAction.gotoConfirmBand报错：" + e);
        }
        return "gotoConfirmBandList";
    }

    //添加绑定数据
    public String addBandData() {
        //模板列表


        try {
            List<CmsTemplate> tempList = cmsTemplateServce.querySeletedTemplateList(SqlJoinUtil.getSqlIn(dataIds, 1000, "template_id"));
            cmsTemplate = cmsTemplateServce.getTemplateById(cmsTemplate.getTemplateId());
            //添加
            if (tempList != null) {
                //获取当前管理员账号
                Map session = ActionContext.getContext().getSession();
                SysUser sysUser = (SysUser) session.get("sysUser");

                cmsTemplateRelationService.insert(cmsTemplate, tempList, sysUser);
            }
        } catch (Exception e) {

            logger.error("CmsWindowDataAction.addBandData报错：" + e);
        }

        return gotoBandList();
    }

    //删除绑定数据
    public String delBandData() {
        try {
            CmsTemplateRelationExample example = new CmsTemplateRelationExample();
            example.createCriteria().andRelationIdEqualTo(cmsTemplateRelation.getRelationId());
            cmsTemplateRelationService.deleteByExample(example);

            this.addActionMessage(ConfigurationUtil.getString("delete.success"));
        } catch (SQLException e) {
            this.addActionMessage(ConfigurationUtil.getString("delete.fail"));
            // TODO Auto-generated catch block
            logger.error("CmsWindowDataAction.delBandData报错：" + e);
        }

        return gotoBandList();
    }

    //删除批量绑定数据
    public String delBandDatas() {
        try {
            if (dataIds != null && dataIds.size() > 0) {

                CmsTemplateRelationExample example = new CmsTemplateRelationExample();

                for (int i = 0; i < dataIds.size(); i++) {

                    example.createCriteria().andRelationIdEqualTo(dataIds.get(i));
                    cmsTemplateRelationService.deleteByExample(example);
                }


            }

            this.addActionMessage(ConfigurationUtil.getString("delete.success"));
        } catch (SQLException e) {
            this.addActionMessage(ConfigurationUtil.getString("delete.fail"));
            // TODO Auto-generated catch block
            logger.error("CmsWindowDataAction.delBandDatas报错：" + e);
        }
        return gotoBandList();
    }

    //goto修改
    public String getTemplateById() {
        try {
            this.cmsTemplate = this.cmsTemplateServce.getTemplateById(this.templateId);
            //变量模板
            if (cmsTemplate.getType() == 13 || cmsTemplate.getType() == 12) {
                return "gotoEditVarTemplate";
            } else {
                return "editTemplate";
            }


        } catch (Exception e) {
            logger.error("CmsWindowDataAction.getTemplateById报错：" + e);
            return "error";
        }
    }


    //goto添加
    public String gotoTemplateAdd() {

        if (null != type && type.equals("var")) {
            return "gotoAddVarTemplate";
        } else {
            return "gotoTemplateAdd";
        }
    }

    //关键字搜索
    public String queryTemplateByKey() {
        Integer siteId = (Integer) session.get("siteId");
        try {
            cmsTemplate = new CmsTemplate();
            if (page == null) {
                page = new Page();
                if (keyWords != null) {
                    page.setPageNo(keyWords.getPageNo());
                    page.setPageSize(keyWords.getPageSize());
                }
            }
            if (keyWords != null) {
                if (keyWords.getId_keyword() != null) {
                    cmsTemplate.setTemplateId(keyWords.getId_keyword());
                }
                if (keyWords.getName_keyword() != null) {
                    cmsTemplate.setName(keyWords.getName_keyword().trim());
                }
                if (keyWords.getTheme_keyword() != null) {
                    cmsTemplate.setTheme(keyWords.getTheme_keyword());
                }
                if (keyWords.getStatus_keyword() == -1)
                    cmsTemplate.setStatus(null);
                else
                    cmsTemplate.setStatus(keyWords.getStatus_keyword());
                if (keyWords.getType_keyword() == -1)
                    cmsTemplate.setType(null);
                else
                    cmsTemplate.setType(keyWords.getType_keyword());
            }
            if (null != siteId) {
                cmsTemplate.setSiteId(siteId);
            }
            this.page = cmsTemplateServce.queryForPage(cmsTemplate, page);
            if (keyWords != null) {
                keyWords.setPageNo(this.page.getPageNo());
                keyWords.setPageSize(page.getPageSize());
            }
            return "templateList";
        } catch (Exception e) {
            logger.error("CmsWindowDataAction.queryTemplateByKey报错：" + e);
            return "error";

        }
    }

    //添加模板
    @SuppressWarnings("deprecation")
    @Token
    public String addTemplate() {
        try {
            if (this.cmsTemplate == null) {
                this.addActionMessage(ConfigurationUtil.getString("add.fail"));
                return "error";
            } else {
                Integer siteId = (Integer) session.get("siteId");
                if (null != siteId) {
                    cmsTemplate.setSiteId(siteId);
                }

                //获取当前管理员账号
                Map session = ActionContext.getContext().getSession();
                SysUser sysUser = (SysUser) session.get("sysUser");
                //设置额外属性
                this.cmsTemplate.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
                this.cmsTemplate.setCreated(sysUser.getUserId());
                this.cmsTemplateServce.addTemplate(cmsTemplate);

                //日志记录
                cmsLog.setModuleName("模板");
                cmsLog.setModuleContent("新建模板> 模板名称:" + cmsTemplate.getName() + "  " +
                        "模板主题:" + cmsTemplate.getTheme() + " 备注:" + cmsTemplate.getRemark());
                cmsLog.setConsoleOperator(sysUser.getUserId());
                cmsLog.setType(1);
                log.info(cmsLogService.insert(cmsLog));

                //	cmsTemplate=new CmsTemplate();
                this.addActionMessage(ConfigurationUtil.getString("add.success"));
                //		this.keyWords=null;

                //保持状态
                this.keyWords = new KeyWords();
                keyWords.setId_keyword(cmsTemplate.getTemplateId());

                if (cmsTemplate.getType() == 13 || cmsTemplate.getType() == 12) {
                    return this.queryVarTemplate();
                } else {
                    return this.queryTemplate();
                }


            }
        } catch (Exception e) {
            this.addActionMessage(ConfigurationUtil.getString("add.fail"));
            logger.error("CmsWindowDataAction.addTemplate报错：" + e);
            return "error";
        }
    }

    //模板详情
    public String getTemplateDetail() {
        try {
            if (this.cmsPageDetail == null)
                this.cmsPageDetail = new CmsPageDetail();
            cmsTemplate = this.cmsTemplateServce.getTemplateById(this.templateId);
            this.cmsPageDetail.setCmsTemplate(cmsTemplate);
            SysUserBean bean = SysUserBean.instance();
            //获取创建人
            if (cmsTemplate.getCreated() != null) {
                SysUser created = bean.getSysUserDetail(cmsTemplate.getCreated());
                if (created != null) {
                    this.cmsPageDetail.setUser_Cre(created.getUserName());
                }
            }
            //获取修改人
            if (cmsTemplate.getModified() != null) {
                SysUser modified = bean.getSysUserDetail(cmsTemplate.getModified());
                if (modified != null) {
                    this.cmsPageDetail.setUser_Mod(modified.getUserName());
                }
            }
            return "templateDetail";
        } catch (Exception e) {
            logger.error("CmsWindowDataAction.getTemplateDetail报错：" + e);
            return "error";
        }
    }

    //修改模板
    @Token
    public String editTemplate() {
        try {
            if (this.cmsTemplate == null) {
                this.addActionMessage(ConfigurationUtil.getString("update.fail"));
                return "error";
            } else {
                //获取当前管理员账号
                Map session = ActionContext.getContext().getSession();
                SysUser sysUser = (SysUser) session.get("sysUser");
                cmsTemplate.setModified(sysUser.getUserId());
                cmsTemplate.setModifyDate(DateTimeUtils.getCalendarInstance().getTime());
                //调用cmsTemplateServce接口的修改方法
                this.cmsTemplateServce.updateTemplate(cmsTemplate);

                //日志记录
                cmsLog.setModuleName("模板");
                cmsLog.setModuleContent("修改模板> 模板名称:" + cmsTemplate.getName() + "  " +
                        "模板主题:" + cmsTemplate.getTheme() + " 备注:" + cmsTemplate.getRemark());
                cmsLog.setConsoleOperator(sysUser.getUserId());
                cmsLog.setType(2);
                log.info(cmsLogService.insert(cmsLog));

                //	cmsTemplate=new CmsTemplate();
                this.addActionMessage(ConfigurationUtil.getString("update.success"));
                //保持状态
                this.keyWords = new KeyWords();
                keyWords.setId_keyword(cmsTemplate.getTemplateId());

                if (cmsTemplate.getType() == 13 || cmsTemplate.getType() == 12) {
                    return this.queryVarTemplate();
                } else {
                    return this.queryTemplate();
                }

            }
        } catch (Exception e) {
            this.addActionMessage(ConfigurationUtil.getString("update.fail"));
            logger.error("CmsWindowDataAction.editTemplate报错：" + e);
            return "error";
        }
    }

    //删除模板
    public String deleteTemplate() {
        try {

            SysUser sysUser = (SysUser) session.get("sysUser");

            //日志记录
            cmsTemplate = this.cmsTemplateServce.getTemplateById(this.templateId);
            cmsLog.setModuleName("模板");
            cmsLog.setModuleContent("删除" + cmsTemplate.getName() + "模板");
            cmsLog.setConsoleOperator(sysUser.getUserId());
            cmsLog.setType(3);
            log.info(cmsLogService.insert(cmsLog));

            //调用cmsTemplateServce接口的删除方法
            this.cmsTemplateServce.deleteTemplateById(templateId);
            this.addActionMessage(ConfigurationUtil.getString("delete.success"));
        } catch (Exception e) {
            this.addActionMessage(ConfigurationUtil.getString("delete.fail"));
            logger.error("CmsWindowDataAction.deleteTemplate报错：" + e);
        }
        this.keyWords = null;
        return this.queryTemplate();
    }

    //删除所有模板
    public String deleteAll() {
        try {
            SysUser sysUser = (SysUser) session.get("sysUser");
            //日志记录
            cmsLog.setModuleName("模板");
            cmsLog.setIds(checkeds);
            cmsLog.setConsoleOperator(sysUser.getUserId());
            log.info(cmsLogService.insert(cmsLog));
            //调用cmsTemplateServce接口删除所有模板方法
            this.cmsTemplateServce.deleteAll(checkeds);
            this.checkeds = null;
            this.addActionMessage(ConfigurationUtil.getString("delete.success"));
        } catch (Exception e) {
            this.addActionMessage(ConfigurationUtil.getString("delete.fail"));
            logger.error("CmsWindowDataAction.deleteAll报错：" + e);
        }
        this.keyWords = null;
        return this.queryTemplate();
    }

    /**
     * 名字校验
     */
    public void check() {
        try {
            HttpServletResponse response = ServletActionContext.getResponse();
            Integer siteId = (Integer) session.get("siteId");
            if (siteId != null && cmsTemplate != null)
                cmsTemplate.setSiteId(siteId);
            String result = this.cmsTemplateServce.check(cmsTemplate);
            response.getWriter().write(result);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("CmsWindowDataAction.check报错：" + e);
        }
    }

    //更新广告模板
    public String updateAdvTemplate() {

        try {
            CmsTemplate tempate = cmsTemplateServce.getTemplateById(cmsTemplate.getTemplateId());
            String tempName = tempate.getName();
            String[] str = tempName.split("_");

            //查询需要更新的广告模板
            CmsAdcolumn cmsAdcolumn = new CmsAdcolumn();
            cmsAdcolumn.setOutput("%/_" + str[1] + ".shtml");
            List advList = cmsAdvService.queryUpdateAdvTempList(cmsAdcolumn);


            //把供应商模板copy至广告供应商下

            //1.写入/adv/advTemp文件
            List<Integer> adcIds = new ArrayList();
            for (int i = 0; i < advList.size(); i++) {
                CmsAdv adv = (CmsAdv) advList.get(i);
                path = PathConstants.advTempPath(adv.getSiteId());
                FileOperateUtils.checkAndCreateDirs(path);
                File file = new File(path);
                File tempFile = new File(file, adv.getAdvId() + ".html");
                FileUtils.writeStringToFile(tempFile, cmsTemplate.getContent(), "utf-8");
                adcIds.add(adv.getAdcolumnId());
            }
            //2.模版/adv/supplier下读取文件
            List supplierAdcolumnList = cmsSupplierAdcolumnService.querySupplierIdByAdcolumn(SqlJoinUtil.getSqlIn(adcIds, 1000, "adcolumn_id"));
            for (int i = 0; i < supplierAdcolumnList.size(); i++) {
                CmsSupplierAdcolumn cmsSupplierAdcolumn = (CmsSupplierAdcolumn) supplierAdcolumnList.get(i);

                path = PathConstants.advSupplierPath(tempate.getSiteId());
                File contentfile = new File(path + "/" + cmsSupplierAdcolumn.getSupplierAdcolumnId() + ".html");
                FileUtils.writeStringToFile(contentfile, cmsTemplate.getContent(), "utf-8");

            }

            this.addActionMessage(ConfigurationUtil.getString("dataUpdate.success"));


        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("CmsWindowDataAction.updateAdvTemplate报错：" + e);
            this.addActionMessage(ConfigurationUtil.getString("dataUpdate.fail"));
        }
        return this.queryTemplate();

    }

    public CmsTemplateServce getCmsTemplateServce() {
        return cmsTemplateServce;
    }

    public void setCmsTemplateServce(CmsTemplateServce cmsTemplateServce) {
        this.cmsTemplateServce = cmsTemplateServce;
    }

    public File getTemplate() {
        return template;
    }

    public void setTemplate(File template) {
        this.template = template;
    }

    public String getTemplateFileName() {
        return templateFileName;
    }

    public void setTemplateFileName(String templateFileName) {
        this.templateFileName = templateFileName;
    }

    public String getTemplateContentType() {
        return templateContentType;
    }

    public void setTemplateContentType(String templateContentType) {
        this.templateContentType = templateContentType;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public String getCheckeds() {
        return checkeds;
    }

    public void setCheckeds(String checkeds) {
        this.checkeds = checkeds;
    }

    public CmsTemplate getCmsTemplate() {
        return cmsTemplate;
    }

    public void setCmsTemplate(CmsTemplate cmsTemplate) {
        this.cmsTemplate = cmsTemplate;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public CmsPageDetail getCmsPageDetail() {
        return cmsPageDetail;
    }

    public void setCmsPageDetail(CmsPageDetail cmsPageDetail) {
        this.cmsPageDetail = cmsPageDetail;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public CmsAdvService getCmsAdvService() {
        return cmsAdvService;
    }

    public void setCmsAdvService(CmsAdvService cmsAdvService) {
        this.cmsAdvService = cmsAdvService;
    }

    public CmsSupplierAdcolumnService getCmsSupplierAdcolumnService() {
        return cmsSupplierAdcolumnService;
    }

    public void setCmsSupplierAdcolumnService(
            CmsSupplierAdcolumnService cmsSupplierAdcolumnService) {
        this.cmsSupplierAdcolumnService = cmsSupplierAdcolumnService;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CmsTemplateRelationService getCmsTemplateRelationService() {
        return cmsTemplateRelationService;
    }

    public void setCmsTemplateRelationService(
            CmsTemplateRelationService cmsTemplateRelationService) {
        this.cmsTemplateRelationService = cmsTemplateRelationService;
    }

    public List<CmsTemplate> getTemplateList() {
        return templateList;
    }

    public void setTemplateList(List<CmsTemplate> templateList) {
        this.templateList = templateList;
    }

    public List<Integer> getDataIds() {
        return dataIds;
    }

    public void setDataIds(List<Integer> dataIds) {
        this.dataIds = dataIds;
    }

    public CmsTemplateRelation getCmsTemplateRelation() {
        return cmsTemplateRelation;
    }

    public void setCmsTemplateRelation(CmsTemplateRelation cmsTemplateRelation) {
        this.cmsTemplateRelation = cmsTemplateRelation;
    }

}
