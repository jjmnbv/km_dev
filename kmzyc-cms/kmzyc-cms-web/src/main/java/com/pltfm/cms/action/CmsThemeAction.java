package com.pltfm.cms.action;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.zkconfig.ConfigurationUtil;
import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ActionContext;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.util.ListUtils;
import com.pltfm.cms.parse.PathConstants;
import com.pltfm.cms.service.CmsInformationService;
import com.pltfm.cms.service.CmsTemplateServce;
import com.pltfm.cms.service.CmsThemeService;
import com.pltfm.cms.service.CmsThemeTemplateService;
import com.pltfm.cms.util.FileOperateUtils;
import com.pltfm.cms.util.SqlJoinUtil;
import com.pltfm.cms.vobject.CmsInformation;
import com.pltfm.cms.vobject.CmsTemplate;
import com.pltfm.cms.vobject.CmsTheme;
import com.pltfm.cms.vobject.CmsThemeTemplate;
import com.pltfm.cms.vobject.UploadFile;
import com.pltfm.sys.bean.SysUserBean;
import com.pltfm.sys.model.SysUser;

@Component(value = "cmsThemeAction")
@Scope(value = "prototype")
public class CmsThemeAction extends BaseAction {
    @Resource(name = "cmsThemeService")
    CmsThemeService cmsThemeService;

    @Resource(name = "cmsTemplateServce")
    CmsTemplateServce cmsTemplateService;
    @Resource(name = "cmsThemeTemplateService")
    CmsThemeTemplateService cmsThemeTemplateService;
    @Resource(name = "cmsInformationService")
    private CmsInformationService cmsInformationService;

    CmsTheme cmsTheme;
    CmsTemplate cmsTemplate;

    CmsThemeTemplate cmsThemeTemplate;
    CmsInformation infor;

    UploadFile fileImage;//主题图片
    // myFile属性用来封装上传的文件
    private File resumefile;

    // myFileContentType属性用来封装上传文件的类型  
    private String resumefileContentType;

    // myFileFileName属性用来封装上传文件的文件名  
    private String resumefileFileName;

    /**
     * 选中的checked
     */
    private String checkeds;

    /**
     * 最后确认数据Id集合
     */
    private List<Integer> dataIds;

    List<CmsTemplate> templateList;
    Page page;

    ActionContext actionContext = ActionContext.getContext();
    Map session = actionContext.getSession();

    //日志
    private static Logger logger = LoggerFactory.getLogger(CmsThemeAction.class);

    //列表
    public String queryThemeList() {
        Integer siteId = (Integer) session.get("siteId");
        if (cmsTheme == null) {
            cmsTheme = new CmsTheme();
            cmsTheme.setSiteId(siteId);
        } else {
            cmsTheme.setSiteId(siteId);
        }
        if (null != siteId) {
            cmsTheme.setSiteId(siteId);
        }
        try {
            page = cmsThemeService.queryThemeListForPage(cmsTheme, page);
            sysUserMap = new HashMap();
            SysUserBean bean = SysUserBean.instance();
            SysUser vo = new SysUser();
            List<SysUser> list = bean.getSysUserList(vo);
            for (SysUser user : list) {
                sysUserMap.put(user.getUserId() + "", user.getUserName());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("CmsThemeAction.queryThemeList报错：" + e);
        }

        return "queryList";
    }

    //前往添加
    public String gotoAdd() {
        return "gotoAdd";
    }

    //前往修改
    public String gotoUpdate() {

        //ThemeId
        try {
            List list = cmsThemeService.queryThemeList(cmsTheme);
            cmsTheme = (CmsTheme) list.get(0);
            String imageOut = PathConstants.cmsPicPath();
            if (cmsTheme.getPicture() != null) {
                cmsTheme.setPicture(imageOut + "/" + cmsTheme.getPicture());
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            logger.error("CmsThemeAction.gotoUpdate报错：" + e);
        }
        return "gotoEidt";
    }

    //添加和修改
    public String edit() {
        //修改
        if (cmsTheme.getThemeId() != null) {
            try {


                if (resumefile != null) {
                    fileImage = new UploadFile(resumefile, resumefileFileName, resumefileContentType);
                }
                SysUser sysuser = (SysUser) session.get("sysUser");

                cmsTheme.setModifyDate(DateTimeUtils.getCalendarInstance().getTime());
                cmsTheme.setModified(sysuser.getUserId());

                cmsThemeService.update(fileImage, cmsTheme);
                this.addActionMessage(ConfigurationUtil.getString("update.success"));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                logger.error("CmsThemeAction.edit报错：" + e);
                this.addActionMessage(ConfigurationUtil.getString("update.fail"));
            }
            //添加
        } else {
            try {
                if (resumefile != null) {
                    fileImage = new UploadFile(resumefile, resumefileFileName, resumefileContentType);
                }
                SysUser sysuser = (SysUser) session.get("sysUser");
                Integer siteId = (Integer) session.get("siteId");
                cmsTheme.setCreated(sysuser.getUserId());

                cmsTheme.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
                cmsTheme.setSiteId(siteId);
                cmsTheme.setStatus(0);
                cmsTheme.setModifyDate(DateTimeUtils.getCalendarInstance().getTime());
                cmsTheme.setModified(sysuser.getUserId());
                cmsThemeService.add(fileImage, cmsTheme);
                this.addActionMessage(ConfigurationUtil.getString("add.success"));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                logger.error("CmsThemeAction.edit报错：" + e);
                this.addActionMessage(ConfigurationUtil.getString("add.fail"));
            }
        }


        return queryThemeList();
    }

    //删除主题
    public String delThemeData() {
        try {
            if (cmsTheme != null) {
                cmsThemeService.del(cmsTheme);
            }
            this.addActionMessage(ConfigurationUtil.getString("delete.success"));
        } catch (SQLException e) {
            this.addActionMessage(ConfigurationUtil.getString("delete.fail"));
            // TODO Auto-generated catch block
            logger.error("CmsThemeAction.delThemeData报错：" + e);
        }
        cmsTheme = new CmsTheme();
        return queryThemeList();

    }

    //批量删除主题
    public String delThemeDatas() {
        try {

            if (dataIds != null && dataIds.size() > 0) {

                cmsThemeService.delDatas(dataIds);
            }
            this.addActionMessage(ConfigurationUtil.getString("delete.success"));
        } catch (SQLException e) {
            this.addActionMessage(ConfigurationUtil.getString("delete.fail"));
            // TODO Auto-generated catch block
            logger.error("CmsThemeAction.delThemeDatas报错：" + e);
        }
        cmsTheme = new CmsTheme();
        return queryThemeList();

    }

    //前往绑定页面  (有分页)
    public String gotoBandList() {
        try {
            // 查询已绑定的数据
            if (cmsThemeTemplate != null) {
                //  cmsTheme.setThemeId(cmsThemeTemplate.getThemeId());
                page = cmsThemeTemplateService.queryThemeTempList(cmsThemeTemplate, page);
            }

            //cmsTheme=(CmsTheme)list.get(0);
        } catch (Exception e) {

            // TODO Auto-generated catch block
            logger.error("CmsThemeAction.gotoBandList报错：" + e);

        }
        return "gotoBandList";


    }

    //删除绑定数据
    public String delBandData() {
        try {
            if (cmsThemeTemplate != null) {
                cmsThemeTemplateService.deleteByPrimaryKey(cmsThemeTemplate.getThemeTemplateId());
            }
            this.addActionMessage(ConfigurationUtil.getString("delete.success"));
        } catch (SQLException e) {
            this.addActionMessage(ConfigurationUtil.getString("delete.fail"));
            // TODO Auto-generated catch block
            logger.error("CmsThemeAction.delBandData报错：" + e);
        }
        return gotoBandList();
    }

    //删除批量绑定数据
    public String delBandDatas() {
        try {
            if (dataIds != null && dataIds.size() > 0) {

                cmsThemeTemplateService.delBandDatas(dataIds);
            }

            this.addActionMessage(ConfigurationUtil.getString("delete.success"));
        } catch (SQLException e) {
            this.addActionMessage(ConfigurationUtil.getString("delete.fail"));
            // TODO Auto-generated catch block
            logger.error("CmsThemeAction.delBandDatas报错：" + e);
        }
        return gotoBandList();
    }

    //前往绑定数据页面
    public String gotoBandPage() {
        //查询页面列表(除去已绑定)
        try {
            if (cmsTemplate == null) {
                cmsTemplate = new CmsTemplate();
            }
            //	page=cmsTemplateServce.queryForPage(cmsTemplate, page);

            page = cmsTemplateService.queryTemplateFilterBandTheme(cmsTemplate, page);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("CmsThemeAction.gotoBandPage报错：" + e);
        }
        return "gotoPageList";
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
                templateList = cmsTemplateService.querySeletedTemplateList(SqlJoinUtil.getSqlIn(tempList, 1000, "template_id"));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("CmsThemeAction.gotoConfirmBand报错：" + e);
        }
        return "gotoConfirmBandList";
    }

    //添加绑定数据
    public String addBandData() {
        //模板列表

        try {
            List<CmsTemplate> tempList = cmsTemplateService.querySeletedTemplateList(SqlJoinUtil.getSqlIn(dataIds, 1000, "template_id"));
            //添加
            if (tempList != null) {
                SysUser sysuser = (SysUser) session.get("sysUser");
                Integer siteId = (Integer) session.get("siteId");
                cmsThemeTemplate.setCreated(sysuser.getUserId());
                cmsThemeTemplate.setStatus(0);
                cmsThemeTemplate.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
                cmsThemeTemplate.setSiteId(siteId);
                cmsThemeTemplate.setModifyDate(DateTimeUtils.getCalendarInstance().getTime());
                cmsThemeTemplate.setModified(sysuser.getUserId());
                cmsThemeTemplateService.insert(tempList, cmsThemeTemplate);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("CmsThemeAction.addBandData报错：" + e);
        }
        cmsThemeTemplate.setType(null);
        return gotoBandList();
    }

    //前往绑窗口(废弃)
    public String gotoBandWindow() {
        //查询窗口列表(除去已绑定)
        return "gotoWindowList";

    }

    //静态专题页列表
    public String queryStaticHolidayList() {

        Integer siteId = (Integer) session.get("siteId");
        if (infor == null) {
            infor = new CmsInformation();
        }
        if (null != siteId) {
            infor.setSiteId(siteId);
        }
        try {
            page = cmsInformationService.queryStaticHolidayList(infor, page);
            sysUserMap = new HashMap();
            SysUserBean bean = SysUserBean.instance();
            SysUser vo = new SysUser();
            List<SysUser> list = bean.getSysUserList(vo);
            for (SysUser user : list) {
                sysUserMap.put(user.getUserId() + "", user.getUserName());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("CmsThemeAction.queryStaticHolidayList报错：" + e);
        }
        return "staticHolidayList";
    }

    //静态专题页添加和修改
    public String gotoStaticHolidayAdd() {
        infor = new CmsInformation();
        return "gotoStaticHolidayEdit";
    }

    public String gotoStaticHolidayEdit() {
        try {
            infor = cmsInformationService.queryStaticHolidayPage(infor.getInforId());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("CmsThemeAction.gotoStaticHolidayEdit报错：" + e);
        }
        return "gotoStaticHolidayEdit";
    }

    public String staticHolidayEdit() {
        //修改

        SysUser sysuser = (SysUser) session.get("sysUser");
        if (infor.getInforId() != null) {
            try {
                infor.setModifyDate(DateTimeUtils.getCalendarInstance().getTime());
                infor.setModified(sysuser.getUserId());
                //未发布
                infor.setStatus(1);
                cmsInformationService.updateStaticHoliday(infor);
                this.addActionMessage(ConfigurationUtil.getString("update.success"));
            } catch (Exception e) {
                logger.error("CmsThemeAction.staticHolidayEdit报错：" + e);
                this.addActionMessage(ConfigurationUtil.getString("update.fail"));
            }
            //添加
        } else {
            try {
                if (session.get("siteId") != null) {
                    infor.setSiteId((Integer) session.get("siteId"));
                }
                infor.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
                infor.setCreated(sysuser.getUserId());
                infor.setModifyDate(DateTimeUtils.getCalendarInstance().getTime());
                infor.setModified(sysuser.getUserId());
                //未发布
                infor.setStatus(1);
                //	 Date d=new Date();
                //	 infor.setContent("/infor-"+d.getTime()+".shtml");
                cmsInformationService.addStaticHoliday(infor);
                this.addActionMessage(ConfigurationUtil.getString("add.success"));

            } catch (Exception e) {
                logger.error("CmsThemeAction.staticHolidayEdit报错：" + e);
                this.addActionMessage(ConfigurationUtil.getString("add.fail"));
            }


        }

        return queryStaticHolidayList();

    }

    //静态专题页删除
    public String staticHolidayDel() {
        try {
            cmsInformationService.delete(infor.getInforId());
            this.addActionMessage(ConfigurationUtil.getString("delete.success"));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("CmsThemeAction.staticHolidayDel报错：" + e);
            this.addActionMessage(ConfigurationUtil.getString("delete.fail"));
        }
        return queryStaticHolidayList();
    }

    public String staticHolidayDelDatas() {

        try {
            if (dataIds != null && dataIds.size() > 0) {

                cmsInformationService.staticHolidayDelDatas(dataIds);
            }

            this.addActionMessage(ConfigurationUtil.getString("delete.success"));
        } catch (Exception e) {
            this.addActionMessage(ConfigurationUtil.getString("delete.fail"));
            // TODO Auto-generated catch block
            logger.error("CmsThemeAction.staticHolidayDelDatas报错：" + e);
        }

        return queryStaticHolidayList();
    }
    //多选发布


    public String publishStaticAll() {

        if (dataIds != null && dataIds.size() > 0) {
            for (int i = 0; i < dataIds.size(); i++) {
                //	if(infor==null){
                infor = new CmsInformation();
                infor.setInforId(dataIds.get(i));
                publishStaticHoliday();

                //	}

            }
        }
        infor = null;
        return queryStaticHolidayList();
    }


    //静态专题页发布
    public String publishStaticHoliday() {
        try {
            if (infor != null) {
                if (resumefile != null) {
                    fileImage = new UploadFile(resumefile, resumefileFileName, resumefileContentType);
                }

                infor = cmsInformationService.queryStaticHolidayPage(infor.getInforId());
                CmsTemplate cmsTempate = new CmsTemplate();
                cmsTempate.setSiteId(infor.getSiteId());
                cmsTempate.setType(infor.getTemplateType());
                List<CmsTemplate> templateList;
                templateList = cmsTemplateService.selectBySiteIdType(cmsTempate);

                if (ListUtils.isNotEmpty(templateList)) {
                    CmsTemplate cmsTemplate = templateList.get(0);
                    String pageContent = "";
                    String templatePath = PathConstants.templatePath(infor.getSiteId());
                    //获取模板信息
                    String templateContent = FileOperateUtils.reader(templatePath + "/" + cmsTemplate.getTemplateId() + ".html");

                    //获取资讯内容信息
                    String informContent = FileOperateUtils.reader(PathConstants.staticHolidayTemplatePath(infor.getSiteId()) + "/" + infor.getInforId() + ".html");

                    if (infor.getInforname() != null) {
                        pageContent += "<!--#set var=\"title\" value='" + infor.getInforname() + "'-->";
                        pageContent += "<!--#set var=\"titleURL\" value='" + PathConstants.holidayPublish(infor.getSiteId()) + infor.getContent() + "'-->";

                    }
                    if (infor.getName() != null) {
                        pageContent += "<!--#set var=\"name\" value='" + infor.getName() + "'-->";
                    }
                    if (infor.getKey() != null) {
                        pageContent += "<!--#set var=\"Keywords\" value='" + infor.getKey() + "'-->";
                    }
                    if (infor.getDescription() != null) {
                        pageContent += "<!--#set var=\"Description\" value='" + infor.getDescription() + "'-->";
                    }
                    if (infor.getContentCss() != null) {
                        templateContent = templateContent.replace("@css", infor.getContentCss());
                    } else {
                        templateContent = templateContent.replace("@css", "");
                    }
                    if (infor.getContentJs() != null) {
                        templateContent = templateContent.replace("@js", infor.getContentJs());
                    } else {
                        templateContent = templateContent.replace("@js", "");
                    }

                    templateContent = templateContent.replace("@content", informContent);
                    pageContent = pageContent + templateContent;


                    String path = "";
                    if (infor.getContent() != null) {
                        String outPath = "";
                        if (infor.getContent() != null) {
                            outPath = infor.getContent().substring(0, infor.getContent().lastIndexOf("/"));
                        }
                        if (!outPath.equals("")) {
                            path = PathConstants.holidayPublish(infor.getSiteId()) + outPath;
                        } else {
                            path = PathConstants.holidayPublish(infor.getSiteId());
                        }
                    }
                    // 判断pages文件夹是否存在，若不存在，则新建一个
                    FileOperateUtils.checkAndCreateDirs(path);
                    String fileOutPath = path + infor.getContent().substring(infor.getContent().lastIndexOf("/"));
                    FileOperateUtils.writer(fileOutPath, pageContent);
                    //发布
                    infor.setStatus(0);
                    cmsInformationService.update(fileImage, infor);
                }
            }
            this.addActionMessage("发布成功");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("CmsThemeAction.publishStaticHoliday报错：" + e);
            this.addActionMessage("发布失败");
        }

        return queryStaticHolidayList();
    }

    public CmsThemeService getCmsThemeService() {
        return cmsThemeService;
    }

    public void setCmsThemeService(CmsThemeService cmsThemeService) {
        this.cmsThemeService = cmsThemeService;
    }

    public CmsTheme getCmsTheme() {
        return cmsTheme;
    }

    public void setCmsTheme(CmsTheme cmsTheme) {
        this.cmsTheme = cmsTheme;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public UploadFile getFileImage() {
        return fileImage;
    }

    public void setFileImage(UploadFile fileImage) {
        this.fileImage = fileImage;
    }

    public File getResumefile() {
        return resumefile;
    }

    public void setResumefile(File resumefile) {
        this.resumefile = resumefile;
    }

    public String getResumefileContentType() {
        return resumefileContentType;
    }

    public void setResumefileContentType(String resumefileContentType) {
        this.resumefileContentType = resumefileContentType;
    }

    public String getResumefileFileName() {
        return resumefileFileName;
    }

    public void setResumefileFileName(String resumefileFileName) {
        this.resumefileFileName = resumefileFileName;
    }

    public CmsTemplateServce getCmsTemplateService() {
        return cmsTemplateService;
    }

    public void setCmsTemplateService(CmsTemplateServce cmsTemplateService) {
        this.cmsTemplateService = cmsTemplateService;
    }

    public CmsTemplate getCmsTemplate() {
        return cmsTemplate;
    }

    public void setCmsTemplate(CmsTemplate cmsTemplate) {
        this.cmsTemplate = cmsTemplate;
    }

    public String getCheckeds() {
        return checkeds;
    }

    public void setCheckeds(String checkeds) {
        this.checkeds = checkeds;
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

    public CmsThemeTemplateService getCmsThemeTemplateService() {
        return cmsThemeTemplateService;
    }

    public void setCmsThemeTemplateService(
            CmsThemeTemplateService cmsThemeTemplateService) {
        this.cmsThemeTemplateService = cmsThemeTemplateService;
    }

    public CmsThemeTemplate getCmsThemeTemplate() {
        return cmsThemeTemplate;
    }

    public void setCmsThemeTemplate(CmsThemeTemplate cmsThemeTemplate) {
        this.cmsThemeTemplate = cmsThemeTemplate;
    }

    public CmsInformationService getCmsInformationService() {
        return cmsInformationService;
    }

    public void setCmsInformationService(CmsInformationService cmsInformationService) {
        this.cmsInformationService = cmsInformationService;
    }

    public CmsInformation getInfor() {
        return infor;
    }

    public void setInfor(CmsInformation infor) {
        this.infor = infor;
    }


}
