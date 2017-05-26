package com.pltfm.cms.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.kmzyc.commons.page.Page;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.opensymphony.xwork2.ActionContext;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.util.ListUtils;
import com.pltfm.app.util.Token;
import com.pltfm.cms.parse.DefaultHtmlBuilder;
import com.pltfm.cms.parse.PathConstants;
import com.pltfm.cms.service.CmsInformactionTypeService;
import com.pltfm.cms.service.CmsInformationService;
import com.pltfm.cms.service.CmsPageService;
import com.pltfm.cms.service.CmsTemplateServce;
import com.pltfm.cms.util.FileOperateUtils;
import com.pltfm.cms.vobject.CmsInformation;
import com.pltfm.cms.vobject.CmsInformationType;
import com.pltfm.cms.vobject.CmsPage;
import com.pltfm.cms.vobject.CmsTemplate;
import com.pltfm.cms.vobject.KeyWords;
import com.pltfm.cms.vobject.UploadFile;
import com.pltfm.sys.bean.SysUserBean;
import com.pltfm.sys.model.SysUser;

@Component("cmsInformationAtion")
@Scope(value = "prototype")
public class CmsInformationAtion extends BaseAction {
	private static Logger logger = LoggerFactory.getLogger(CmsPromotionAction.class);
    private CmsInformation infor;


    UploadFile fileImage;//主题图片
    // myFile属性用来封装上传的文件
    private File resumefile;
    // myFileContentType属性用来封装上传文件的类型  
    private String resumefileContentType;
    // myFileFileName属性用来封装上传文件的文件名  
    private String resumefileFileName;

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


    private Page page;
    @Resource(name = "cmsInformationService")
    private CmsInformationService cmsInformationService;
    @Resource(name = "cmsInformactionTypeService")
    private CmsInformactionTypeService cmsInformactionTypeService;
    @Resource(name = "cmsTemplateServce")
    private CmsTemplateServce cmsTemplateService;
    @Resource(name = "cmsPageService")
    private CmsPageService cmsPageService;
    private Integer inforid;
    private Integer[] levelId;
    private Integer adminType;//角色区分
    private Integer pageNo;
    /*@Resource(name="templateConfig")
	private Map<String, String> templateConfig;*/

    @Resource(name = "htmlBuilder")
    DefaultHtmlBuilder defaultHtmlBuilder;

    private String publishPath;

    List inforTypeList;

    ActionContext actionContext = ActionContext.getContext();
    Map session = actionContext.getSession();
/*	public Map<String, String> getTemplateConfig() {
		return templateConfig;
	}

	public void setTemplateConfig(Map<String, String> templateConfig) {
		this.templateConfig = templateConfig;
	}*/



    public String gotoAdd() {

        CmsInformationType inforType = new CmsInformationType();
        if (session.get("siteId") != null) {
            inforType.setSiteId((Integer) session.get("siteId"));
        }
        try {
            inforTypeList = cmsInformationService.inforTypeList(inforType);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("CmsPromotionAction.gotoAdd文章跳转添加页异常：" + e.getMessage(), e);
        }
        return "goto";
    }

    /**
     * 文章添加
     */
    @Token
    public String Add() {
        try {
            if (resumefile != null) {
                fileImage = new UploadFile(resumefile,resumefileFileName,resumefileContentType);
            }
            SysUser sysuser = (SysUser) session.get("sysUser");
            if (session.get("siteId") != null) {
                infor.setSiteId((Integer) session.get("siteId"));
            }
            if (null == infor.getCreateDate()) {
                infor.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
            }

            infor.setCreated(sysuser.getUserId());
            infor.setModifyDate(DateTimeUtils.getCalendarInstance().getTime());
            //	infor.setPublishDate(DateTimeUtils.getCalendarInstance().getTime());
            infor.setModified(sysuser.getUserId());
            Date d = new Date();

            if (StringUtils.isEmpty(infor.getContent())) {
                infor.setContent("/infor-" + d.getTime() + ".shtml");
            }

            cmsInformationService.add(fileImage, infor);
            this.addActionMessage(ConfigurationUtil.getString("add.success"));
            infor = new CmsInformation();
        } catch (Exception e) {
            logger.error("CmsPromotionAction.Add文章添加页异常：" + e.getMessage(), e);
            this.addActionMessage(ConfigurationUtil.getString("add.fail"));
            this.keyWords = null;
            infor = new CmsInformation();
            return List();
        }
        this.keyWords = null;
        return List();
    }

    /**
     * 根据ID查文章信息
     * Adv_Byid.action?Advid=
     */
    public String Byid() {
        try {
            boolean flag = true;
            infor = cmsInformationService.byid(flag, inforid);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("CmsPromotionAction.Byid根据ID查文章信息异常：" + e.getMessage(), e);
            return "Error";
        }
        return "ByidSuccess";
    }

    /**
     * 修改文章
     */
    @Token
    public String Update() {
        try {
            if (resumefile != null) {
                fileImage = new UploadFile(resumefile,resumefileFileName,resumefileContentType);
            }
            SysUser sysuser = (SysUser) session.get("sysUser");
            infor.setModifyDate(DateTimeUtils.getCalendarInstance().getTime());
            infor.setModified(sysuser.getUserId());
            infor.setStatus(1);
            cmsInformationService.update(fileImage, infor);
        } catch (Exception e) {
            logger.error("CmsPromotionAction.Update修改文章异常：" + e.getMessage(), e);
            infor = new CmsInformation();
            this.addActionMessage(ConfigurationUtil.getString("update.fail"));
            this.keyWords = null;
            return List();

        }
        this.addActionMessage(ConfigurationUtil.getString("update.success"));
        infor = new CmsInformation();
        this.keyWords = null;
        return List();
    }

    /**
     * 删除文章
     */
    public String Delete() {
        try {
            cmsInformationService.delete(inforid);
            this.addActionMessage(ConfigurationUtil.getString("delete.success"));
        } catch (Exception e) {
            this.addActionMessage(ConfigurationUtil.getString("delete.fail"));
            logger.error("CmsPromotionAction.Delete删除文章异常：" + e.getMessage(), e);
            
            infor = new CmsInformation();
            this.keyWords = null;
            return List();
        }

        infor = new CmsInformation();
        this.keyWords = null;
        return List();
    }


    /**
     * 删除文章
     */
    @Token
    public String All_Delete() {
        try {
            for (int i = 0; i < levelId.length; i++) {
                cmsInformationService.delete(levelId[i]);
            }
            this.addActionMessage(ConfigurationUtil.getString("delete.success"));
        } catch (Exception e) {
            this.addActionMessage(ConfigurationUtil.getString("delete.fail"));
            logger.error("CmsPromotionAction.All_Delete多条删除文章异常：" + e.getMessage(), e);
            return "Error";
        }
        this.keyWords = null;
        return List();
    }

    /**
     * 多条发布
     */
    @SuppressWarnings("unchecked")
    public String publishAll() {
        try {
            boolean flag = false;
            for (int i = 0; i < levelId.length; i++) {
                infor = cmsInformationService.byid(flag, levelId[i]);
                publicNews(infor);
            }
            infor = new CmsInformation();
            this.addActionMessage("发布成功");
            this.keyWords = null;
            return List();
        } catch (Exception e) {
            infor = new CmsInformation();
            this.addActionMessage("发布失败");
            logger.error("CmsPromotionAction.publishAll多条发布文章异常：" + e.getMessage(), e);
            this.keyWords = null;
            return List();
        }
    }

    /**
     * 文章LIST
     */
    public String List() {
        try {
            infor = new CmsInformation();
            if (session.get("siteId") != null) {
                infor.setSiteId((Integer) session.get("siteId"));
            }
            if (page == null && keyWords != null && keyWords.getPageNo() != null) {
                page = new Page();
                page.setPageNo(keyWords.getPageNo());
                page.setPageSize(keyWords.getPageSize());
            }
            if (keyWords != null) {
                infor.setName(keyWords.getName_keyword().trim());
                infor.setContent(keyWords.getOutPath_keyword().trim());
                if (keyWords.getStatus_keyword() != null)
                    infor.setStatus(keyWords.getStatus_keyword());
                if (keyWords.getType_keyword() != null)
                    infor.setTypeId(keyWords.getType_keyword());
            }
            page = cmsInformationService.queryForPage(infor, page);

            publishPath = PathConstants.staticPath(infor.getSiteId());
            if (keyWords == null) {
                keyWords = new KeyWords();
            }
            keyWords.setPageNo(page.getPageNo());
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
            logger.error("CmsPromotionAction.List文章LIST异常：" + e.getMessage(), e);
            return "Error";
        }
    }

    /**
     * 静态专题页
     */
    public String queryHoliday() {
        try {
            infor = new CmsInformation();
            if (session.get("siteId") != null) {
                infor.setSiteId((Integer) session.get("siteId"));
            }
            if (page == null && keyWords != null) {
                page = new Page();
                page.setPageNo(keyWords.getPageNo());
                page.setPageSize(keyWords.getPageSize());
            }
            if (keyWords != null) {
                infor.setName(keyWords.getName_keyword().trim());
                infor.setContent(keyWords.getOutPath_keyword().trim());
                if (keyWords.getStatus_keyword() != null)
                    infor.setStatus(keyWords.getStatus_keyword());
                if (keyWords.getType_keyword() != null)
                    infor.setTypeId(keyWords.getType_keyword());
            }
            page = cmsInformationService.queryForPage(infor, page);

            if (keyWords == null) {
                keyWords = new KeyWords();
            }
            keyWords.setPageNo(page.getPageNo());
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
            logger.error("CmsPromotionAction.queryHoliday文章静态专题页异常：" + e.getMessage(), e);
            return "Error";
        }
    }

    /**
     * 添加文章中的文章类型LIST
     */
    public String Add_ajax() {
        try {
            CmsInformationType inforType = new CmsInformationType();
            if (session.get("siteId") != null) {
                inforType.setSiteId((Integer) session.get("siteId"));
            }
            List list = cmsInformationService.inforTypeList(inforType);
            JSONObject json = new JSONObject();
            json.put("inforlist", list);
            HttpServletResponse response = ServletActionContext.getResponse();

            response.setContentType("text/html;charset=utf-8");
            response.getWriter().print(json);

        } catch (Exception e) {
            logger.error("CmsPromotionAction.Add_ajax添加文章中的文章类型LIST异常：" + e.getMessage(), e);
        }
        return null;
    }

    /**
     * 发布文章
     */
    @SuppressWarnings("unchecked")
    public String publish() {
        try {
            boolean flag = false;
            infor = cmsInformationService.byid(flag, inforid);

            publicNews(infor);

            infor = new CmsInformation();
            this.addActionMessage("发布成功");
            this.keyWords = null;
            return List();
        } catch (Exception e) {
            infor = new CmsInformation();
            this.addActionMessage("发布失败");
            logger.error("CmsPromotionAction.publish发布文章异常：" + e.getMessage(), e);
            this.keyWords = null;
            return List();
        }
    }

    //发布资讯列表
    public boolean publicNewsList(CmsPage cmsPage) throws Exception {
        //资讯的总条数
        try {
            if (resumefile != null) {
                fileImage = new UploadFile(resumefile,resumefileFileName,resumefileContentType);
            }
            if (null == infor) {
                infor = new CmsInformation();
            }
            SysUser sysuser = (SysUser) session.get("sysUser");
            if (session.get("siteId") != null) {
                infor.setSiteId((Integer) session.get("siteId"));
            }
            if (page == null) {
                page = new Page();
                page.setPageSize(20);
            }
            //资讯列别
            CmsInformationType cmsInformationType = new CmsInformationType();
            cmsInformationType.setTypeCode(cmsPage.getPublicType() + "");
            cmsInformationType = cmsInformactionTypeService.queryInfoTypeByTypeCode(cmsInformationType);
            infor.setTypeId(cmsInformationType.getId());
            infor.setStatus(0);
            page = cmsInformationService.queryForPage(infor, page);
            //map数据集合
            Map map = new HashMap();
            map = defaultHtmlBuilder.addPath(map, infor.getSiteId());
            //总共页数
            Integer totalPage = page.getPageCount();

            String path = "";
            //文件路径

            //清除记录
            String informationPath = "/" + cmsPage.getOutputPath();
            cmsInformationService.deleteInfoByType(informationPath);
            //生成后的资讯类型
            cmsInformationType.setTypeCode("ZX001");
            cmsInformationType = cmsInformactionTypeService.queryInfoTypeByTypeCode(cmsInformationType);
            //根据页数循环生成静态页数
            for (int i = 1; i <= totalPage; i++) {
                path = PathConstants.informPublish(infor.getSiteId());
                path = path + "/" + cmsPage.getOutputPath() + "_" + i + ".shtml";
                CmsInformation cmsInfor = new CmsInformation();
                //路径
                cmsInfor.setContent("/" + cmsPage.getOutputPath() + "_" + i + ".shtml");

                cmsInfor.setTypeId(cmsInformationType.getId());

                cmsInfor.setName(cmsPage.getName());
                //自定义
                cmsInfor.setTemplateType(3);

                cmsInfor.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
                cmsInfor.setCreated(sysuser.getUserId());
                cmsInfor.setModifyDate(DateTimeUtils.getCalendarInstance().getTime());
                //		cmsInfor.setPublishDate(DateTimeUtils.getCalendarInstance().getTime());
                cmsInfor.setModified(sysuser.getUserId());
                cmsInfor.setInformContent(cmsPage.getContent());
                cmsInfor.setSiteId(infor.getSiteId());
                cmsInfor.setStatus(0);

                page.setPageNo(i);
                page = cmsInformationService.queryForPage(infor, page);
                //资讯列表对象

                map.put("information", page.getDataList());
                //分页对象
                map.put("pagination", page);
                //生成源码
                String pagePath = PathConstants.pageTemPath(cmsPage.getSiteId()) + "/" + cmsPage.getPageId() + ".html";
                String text = defaultHtmlBuilder.getHtml(pagePath, map);

                File file = new File(path);
                OutputStreamWriter writer = new OutputStreamWriter(
                        new FileOutputStream(file), "UTF-8");
                BufferedWriter bWriter = new BufferedWriter(writer);
                bWriter.write(text);
                bWriter.close();

                //添加
                cmsInformationService.add(fileImage, cmsInfor);
                cmsPage.setStatus(1);
                cmsPageService.updateCmsPage(cmsPage);

            }


        } catch (Exception e) {
            logger.error("CmsPromotionAction.publicNewsList发布资讯列表异常：" + e.getMessage(), e);
            return false;
        }

        //路径标题定义
        //
        return true;
    }


    //发布文章核心
    public void publicNews(CmsInformation infor) throws Exception {
        if (infor != null && infor.getTemplateType() != null) {
            fileImage = null;
            //自定义类型资讯发布
            if (infor.getTemplateType() == 3) {
                String pageTitle = "<!DOCTYPE html><html><head>" +
                        "<meta charset='utf-8' />" +
                        "<title>" + infor.getName() + "</title>" +
                        "<meta name='Keywords' content='" + infor.getKey() + "'/>" +
                        "<meta name='Description' content='" + infor.getDescription() + "'/>"
                        + "<link rel='shortcut icon' href='http://jscss.kmb2b.com/res/images/kmzl.ico' type='image/x-icon'/>"
                        + "</head><body>";
                String content = FileOperateUtils.reader(PathConstants.informPath(infor.getSiteId()) + "/" + infor.getInforId() + ".html");
                String pageContent = pageTitle + content + "</body></html>";
                String path = "";
                if (infor.getContent() != null) {
                    String outPath = "";
                    if (infor.getContent().contains("/")) {
                        outPath = infor.getContent().substring(0, infor.getContent().lastIndexOf("/"));
                    }
                    if (!outPath.equals("")) {
                        path = PathConstants.informPublish(infor.getSiteId()) + outPath;
                    } else {
                        path = PathConstants.informPublish(infor.getSiteId());
                    }
                }
                // 判断pages文件夹是否存在，若不存在，则新建一个
                FileOperateUtils.checkAndCreateDirs(path);

                String fileOutPath = "";
                if (infor.getContent().contains("/")) {
                    fileOutPath = path + infor.getContent().substring(infor.getContent().lastIndexOf("/"));
                } else {
                    fileOutPath = path + "/" + infor.getInforId() + ".shtml";
                    infor.setContent("/" + infor.getInforId() + ".shtml");
                }
                FileOperateUtils.writer(fileOutPath, pageContent);
                infor.setStatus(0);
                infor.setInformContent(infor.getContent_content());
                infor.setPublishDate(DateTimeUtils.getCalendarInstance().getTime());
                cmsInformationService.update(fileImage, infor);
                //帮助中信息、公告咨询发布
            } else {
                CmsTemplate cmsTempate = new CmsTemplate();
                cmsTempate.setSiteId(infor.getSiteId());
                cmsTempate.setType(infor.getTemplateType());
                List<CmsTemplate> templateList = cmsTemplateService.selectBySiteIdType(cmsTempate);
                if (ListUtils.isNotEmpty(templateList)) {
                    CmsTemplate cmsTemplate = templateList.get(0);
                    String templatePath = PathConstants.templatePath(infor.getSiteId());
                    //获取模板信息
                    String templateContent = FileOperateUtils.reader(templatePath + "/" + cmsTemplate.getTemplateId() + ".html");
                    //获取资讯内容信息
                    String informContent = FileOperateUtils.reader(PathConstants.informPath(infor.getSiteId()) + "/" + infor.getInforId() + ".html");
                    String pageContent = "";
                    if (infor.getInforname() != null) {
                        pageContent += "<!--#set var=\"title\" value='" + infor.getInforname() + "'-->";
                        pageContent += "<!--#set var=\"titleURL\" value='" + PathConstants.informPublish(infor.getSiteId()) + infor.getContent() + "'-->";

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
                    pageContent += "<!--#set var=\"date\" value='" + DateTimeUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss") + "'-->";
                    templateContent = templateContent.replace("@content", informContent);
                    pageContent = pageContent + templateContent;
                    String path = "";
                    if (infor.getContent() != null) {
                        String outPath = "";
                        if (infor.getContent().contains("/")) {
                            outPath = infor.getContent().substring(0, infor.getContent().lastIndexOf("/"));
                        }
                        Integer type = infor.getTemplateType();
                        if (!outPath.equals("")) {
                            //静态页面的
                            if (type == 7 || type == 8) {
                                path = PathConstants.holidayPublish(infor.getSiteId()) + outPath;
                            } else {
                                path = PathConstants.informPublish(infor.getSiteId()) + outPath;
                            }


                        } else {
                            //静态页面的
                            if (type == 7 || type == 8) {
                                path = PathConstants.holidayPublish(infor.getSiteId());
                            } else {
                                path = PathConstants.informPublish(infor.getSiteId());
                            }


                        }
                    }
                    // 判断pages文件夹是否存在，若不存在，则新建一个
                    FileOperateUtils.checkAndCreateDirs(path);
                    String fileOutPath = "";
                    if (infor.getContent().contains("/")) {
                        fileOutPath = path + infor.getContent().substring(infor.getContent().lastIndexOf("/"));
                    } else {
                        fileOutPath = path + "/" + infor.getInforId() + ".shtml";
                        infor.setContent("/" + infor.getInforId() + ".shtml");
                    }
                    FileOperateUtils.writer(fileOutPath, pageContent);
                    infor.setStatus(0);
                    infor.setPublishDate(DateTimeUtils.getCalendarInstance().getTime());
                    cmsInformationService.update(fileImage, infor);
                }
            }
        }
    	/*
    	else{
		    	String pageTitle="<!DOCTYPE html><html><head>"+
				"<meta charset='utf-8' />"+
				"<title>康美之恋-"+infor.getName()+"</title>"+
				"<meta name='Keywords' content='"+infor.getKey()+"'/>"+
				"<meta name='Description' content='"+infor.getDescription()+"'/></head><body>";
			    String content = FileOperateUtils.reader(PathConstants.informPath(infor.getSiteId())+"/"+infor.getInforId()+".html");
				String pageContent = pageTitle+content+"</body></html>";
				String path ="";
				if(infor.getContent()!=null){
					String outPath="";
					if(infor.getContent().contains("/")){
						 outPath= infor.getContent().substring(0, infor.getContent().lastIndexOf("/"));
					}
				    if(!outPath.equals("")){
				    	path = PathConstants.informPublish(infor.getSiteId()) +outPath;
				    }else{
				    	path = PathConstants.informPublish(infor.getSiteId()); 
				    }
				}
			    // 判断pages文件夹是否存在，若不存在，则新建一个
				File file = new File(path);
				if (!file.exists()) {
					file.mkdir();
				}
				String fileOutPath="";
				if(infor.getContent().contains("/")){
					fileOutPath=path+infor.getContent().substring(infor.getContent().lastIndexOf("/"));
				}else{
					fileOutPath=path+"/"+infor.getInforId()+".shtml";
					infor.setContent("/"+infor.getInforId()+".shtml");
				}
				FileOperateUtils.writer(fileOutPath, pageContent);
				infor.setStatus(0);  
				infor.setInformContent(infor.getContent_content());
				cmsInformationService.update(infor);
		 }*/
    }


    /**
     * 查广告位名是否存在
     */
    public void name_ajax() {
        try {
            Integer siteId = (Integer) session.get("siteId");
            if (siteId != null && infor != null)
                infor.setSiteId(siteId);
            int count = cmsInformationService.countByInforname(infor);
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().print(count);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("CmsPromotionAction.name_ajax查名称是否存在异常：" + e.getMessage(), e);

        }
    }


    public CmsInformation getInfor() {
        return infor;
    }

    public void setInfor(CmsInformation infor) {
        this.infor = infor;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public CmsInformationService getCmsInformationService() {
        return cmsInformationService;
    }

    public void setCmsInformationService(CmsInformationService cmsInformationService) {
        this.cmsInformationService = cmsInformationService;
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

    public List getInforTypeList() {
        return inforTypeList;
    }

    public void setInforTypeList(List inforTypeList) {
        this.inforTypeList = inforTypeList;
    }

    public CmsInformactionTypeService getCmsInformactionTypeService() {
        return cmsInformactionTypeService;
    }

    public void setCmsInformactionTypeService(
            CmsInformactionTypeService cmsInformactionTypeService) {
        this.cmsInformactionTypeService = cmsInformactionTypeService;
    }

    public CmsTemplateServce getCmsTemplateService() {
        return cmsTemplateService;
    }

    public void setCmsTemplateService(CmsTemplateServce cmsTemplateService) {
        this.cmsTemplateService = cmsTemplateService;
    }

    public CmsPageService getCmsPageService() {
        return cmsPageService;
    }

    public void setCmsPageService(CmsPageService cmsPageService) {
        this.cmsPageService = cmsPageService;
    }

    public String getPublishPath() {
        return publishPath;
    }

    public void setPublishPath(String publishPath) {
        this.publishPath = publishPath;
    }

}
