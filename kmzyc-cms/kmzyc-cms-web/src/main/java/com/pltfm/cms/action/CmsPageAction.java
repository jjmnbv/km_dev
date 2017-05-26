package com.pltfm.cms.action;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.kmzyc.cms.remote.service.RemoteSupplierParseService;
import com.kmzyc.commons.page.Page;
import com.kmzyc.supplier.model.ShopMain;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.opensymphony.xwork2.ActionContext;
import com.pltfm.app.util.ConfigureUtils;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.util.Token;
import com.pltfm.cms.parse.PagePublish;
import com.pltfm.cms.parse.PathConstants;
import com.pltfm.cms.parse.TimingPublish;
import com.pltfm.cms.service.CmsLogService;
import com.pltfm.cms.service.CmsPageService;
import com.pltfm.cms.service.CmsPageVisualizationService;
import com.pltfm.cms.service.CmsPageWindowService;
import com.pltfm.cms.service.CmsSiteDataService;
import com.pltfm.cms.service.CmsStylesService;
import com.pltfm.cms.service.CmsTemplateServce;
import com.pltfm.cms.service.CmsThemeService;
import com.pltfm.cms.service.CmsThemeTemplateService;
import com.pltfm.cms.service.CmsWindowService;
import com.pltfm.cms.util.SqlJoinUtil;
import com.pltfm.cms.vobject.CmsLog;
import com.pltfm.cms.vobject.CmsPage;
import com.pltfm.cms.vobject.CmsPageDetail;
import com.pltfm.cms.vobject.CmsPageVisualization;
import com.pltfm.cms.vobject.CmsPageWindow;
import com.pltfm.cms.vobject.CmsSiteData;
import com.pltfm.cms.vobject.CmsStyles;
import com.pltfm.cms.vobject.CmsTemplate;
import com.pltfm.cms.vobject.CmsTheme;
import com.pltfm.cms.vobject.CmsThemeTemplate;
import com.pltfm.cms.vobject.CmsWindow;
import com.pltfm.cms.vobject.KeyWords;
import com.pltfm.sys.bean.SysUserBean;
import com.pltfm.sys.model.SysUser;
import com.pltfm.sys.util.DatetimeUtil;

@Component("cmsPageAction")
@Scope(value = "prototype")
public class CmsPageAction extends BaseAction {

    public static final String PAGE_LIST = "pageList";
    @Resource(name = "cmsPageWindowService")
    private CmsPageWindowService cmsPageWindowService;//cmsPageWindowService接口
    @Resource(name = "cmsPageService")
    private CmsPageService cmsPageService;//cmsPageService接口
    @Resource(name = "cmsTemplateServce")
    private CmsTemplateServce cmsTemplateServce;//cmsTemplateService接口
    @Resource(name = "cmsWindowService")
    private CmsWindowService cmsWindowService;//cmsWindowService接口
    @Resource(name = "cmsLogService")
    private CmsLogService cmsLogService;//cmsLogService接口
    private CmsLog cmsLog = new CmsLog();//日志实体
    private CmsPage cmsPage;//页面组件
    private Page page;//分页组件
    private CmsTemplate cmsTemplate;//模板组件
    private Integer templateId;//模板主键
    private Integer pageId;//页面主键
    private String checkeds;//多选框
    private CmsWindow cmsWindow;//窗口组件
    private CmsPageDetail cmsPageDetail;//页面详情组件
    private Integer windowId;//窗口主键
    private Integer backType;//返回页面标志
    private Integer adminType;//角色区分
    private Integer pageNo;//页码
    private Integer dataType;//数据类型
    @Resource(name = "timingPublish")
    private TimingPublish timingPublish;
    @Resource(name = "pagePublish")
    private PagePublish pagePublish;
    @Resource(name = "remoteSupplierParseService")
    private RemoteSupplierParseService remoteSupplierParseService;
    @Resource(name = "cmsInformationAtion")
    private CmsInformationAtion cmsInformationAtion;

    private Integer supplierId;

    private Integer shopId;

    private Integer shopType;


    /**
     * 主题业务逻辑接口
     */
    @Resource(name = "cmsThemeService")
    private CmsThemeService cmsThemeService;
    /**
     * 主题模版业务逻辑接口
     */
    @Resource(name = "cmsThemeTemplateService")
    private CmsThemeTemplateService cmsThemeTemplateService;

    /**
     * 主题实体
     */
    private CmsTheme cmsTheme;


    //页面更新定时开关
    private String timingPublishFlag;
    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 站点id
     */
    private Integer siteId;

    /**
     * 站点数据业务逻辑接口
     */
    @Resource(name = "cmsSiteDataService")
    private CmsSiteDataService cmsSiteDataService;

    /**
     * 风格业务逻辑接口
     */
    @Resource(name = "cmsStylesService")
    private CmsStylesService cmsStylesService;

    /**
     * 风格图片
     */
    private String stylesImage;

    /**
     * 风格Id
     */
    private Integer stylesId;

    /**
     * 风格实体
     */
    private CmsStyles cmsStyles;

    private String content;

    /**
     * 页面可视化实体集合
     */
    private List<CmsPageVisualization> cpvList;

    /**
     * 页面可视化窗口数据绑定业务逻辑接口
     */
    @Resource(name = "cmsPageVisualizationService")
    private CmsPageVisualizationService cmsPageVisualizationService;

    /**
     * 输出路径
     */
    private String outputPath;

    //日志
    private static Logger logger = LoggerFactory.getLogger(CmsPageAction.class);

    ActionContext actionContext = ActionContext.getContext();
    Map session = actionContext.getSession();
    public String test() {
        return null;

    }


    public String publish() {
        timingPublish.publish();
        this.addActionMessage(ConfigurationUtil.getString("publish.page.success"));
        return this.queryForPage();
    }

    /**
     * 可视化
     */
    public String visualization() {
        try {
            cmsStyles = cmsStylesService.queryByCmsStylesId(stylesId);
        } catch (Exception e) {
            logger.error("CmsPageAction.visualization异常：" + e.getMessage(), e);
        }
        return "visualization";
    }

    public String visualizationData() {
        //构造HttpClient的实例
        HttpClient httpClient = new HttpClient();


        //设置编码参数

        httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
        //忽略Cookies
        httpClient.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);

        //创建GET方法的实例
        Integer siteId = (Integer) session.get("siteId");

        GetMethod getMethod = new GetMethod(PathConstants.staticPath(siteId) + outputPath);


        try {
            //访问指定URL并取得返回状态码
            int statusCode = httpClient.executeMethod(getMethod);
            if (statusCode == 200) {//返回成功状态码200
                //读取页面HTML源码
                StringBuffer sb = new StringBuffer();
                InputStream in = getMethod.getResponseBodyAsStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                if (br != null) {
                    br.close();
                    content = sb.toString();
                }

            }
        } catch (Exception ex) {
            logger.error("CmsPageAction.visualizationData异常：" + ex.getMessage(), ex);
        } finally {
            //释放连接
            getMethod.releaseConnection();
        }
        return "visualizationData";
    }

    //页面发布
    public String pagePublic() {
        boolean res;
        try {
            if (cmsPage.getPageId() != null) {
                cmsPage = cmsPageService.getCmsPageById(cmsPage.getPageId());
            }
            String outPath = cmsPage.getOutputPath();
            if (outPath.indexOf(".") > 0) {
                res = this.pagePublish.parse(cmsPage);
                //资讯类型
            } else {
                res = cmsInformationAtion.publicNewsList(cmsPage);
            }


            if (res) {
                this.addActionMessage(ConfigurationUtil.getString("publish.page.success"));
            } else {
                this.addActionMessage(ConfigurationUtil.getString("publish.page.fail"));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("CmsPageAction.pagePublic异常：" + e.getMessage(), e);
            this.addActionMessage(ConfigurationUtil.getString("publish.page.fail"));
        }
        return this.queryForPage();
    }

    //预览供应商
    public String pageViwePublic() {
        try {
            ShopMain shopMain = new ShopMain();
            if (supplierId != null) {
                shopMain.setSupplierId(Long.valueOf(supplierId));
            } else {
                shopMain.setSupplierId(921l);
            }


            shopMain.setShopSite("B2B");
            if (shopId != null) {
                shopMain.setShopId(Long.valueOf(shopId));
            } else {
                shopMain.setShopId(322l);
            }


            String res = this.remoteSupplierParseService.remoteViweParse(shopMain, 2);
            if (res.equals("")) {
                this.addActionMessage(ConfigurationUtil.getString("publish.page.success"));
            } else {
                this.addActionMessage(ConfigurationUtil.getString("publish.page.fail"));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("CmsPageAction.pageViwePublic异常：" + e.getMessage(), e);

            this.addActionMessage(ConfigureUtils
                    .getMessageConfig("publish.page.fail"));
        }
        return this.queryForPage();
    }


    //测试供应选择店铺
    public String pagerAddTheme() {
        boolean res;
        try {

            ShopMain shopMain = new ShopMain();
            if (supplierId != null) {
                shopMain.setSupplierId(Long.valueOf(supplierId));
            } else {
                shopMain.setSupplierId(921l);
            }


            shopMain.setShopSite("B2B");
            if (shopId != null) {
                shopMain.setShopId(Long.valueOf(shopId));
            } else {
                shopMain.setShopId(322l);
            }

            if (shopType != null) {
                this.remoteSupplierParseService.remoteAddTheme(shopMain, shopType);
            } else {
                this.remoteSupplierParseService.remoteAddTheme(shopMain, 2);
            }

            res = true;
            if (res) {
                this.addActionMessage(ConfigurationUtil.getString("publish.page.success"));
            } else {
                this.addActionMessage(ConfigurationUtil.getString("publish.page.fail"));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("CmsPageAction.pagerAddTheme异常：" + e.getMessage(), e);
            this.addActionMessage(ConfigurationUtil.getString("publish.page.fail"));
        }
        return this.queryForPage();
    }

    //供应商测试页面发布
    public String pageSupply() {
        boolean res;
        try {

            ShopMain shopMain = new ShopMain();
            if (supplierId != null) {
                shopMain.setSupplierId(Long.valueOf(supplierId));
            } else {
                shopMain.setSupplierId(921l);
            }


            shopMain.setShopSite("B2B");
            if (shopId != null) {
                shopMain.setShopId(Long.valueOf(shopId));
            } else {
                shopMain.setShopId(322l);
            }


            this.remoteSupplierParseService.remoteParse(shopMain, 2);
            res = true;
            if (res) {
                this.addActionMessage(ConfigurationUtil.getString("publish.page.success"));
            } else {
                this.addActionMessage(ConfigurationUtil.getString("publish.page.fail"));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("CmsPageAction.pageSupply异常：" + e.getMessage(), e);
            this.addActionMessage(ConfigurationUtil.getString("publish.page.fail"));
        }
        return this.queryForPage();
    }


    //多选发布
    public String parseAll() {
        boolean res;
        try {
            res = this.pagePublish.parseAll(this.checkeds);
            this.checkeds = null;
            if (res) {
                this.addActionMessage(ConfigurationUtil.getString("publish.page.success"));
            } else {
                this.addActionMessage(ConfigurationUtil.getString("publish.page.fail"));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("CmsPageAction.parseAll异常：" + e.getMessage(), e);
            this.addActionMessage(ConfigurationUtil.getString("publish.page.fail"));
        }
        return this.queryForPage();
    }

    /**
     * 跳转数据列表页面
     */
    public String openPageList() {
        return "openPageList";
    }

    /**
     * 页面弹出层
     */
    public String queryByPage() {
        try {
            cmsPage = new CmsPage();
            if (page == null && keyWords != null) {
                page = new Page();
                page.setPageNo(keyWords.getPageNo());
                page.setPageSize(keyWords.getPageSize());
            }
            if (keyWords != null) {
                cmsPage.setPageId(keyWords.getId_keyword());
                cmsPage.setName(keyWords.getName_keyword().trim());
                cmsPage.setOutputPath(keyWords.getOutPath_keyword().trim());

                if (keyWords.getStatus_keyword() != null)
                    cmsPage.setStatus(keyWords.getStatus_keyword());
            }
            cmsPage.setSiteId(siteId);
            page = cmsPageService.getAllPage(cmsPage, page);

            List<CmsSiteData> dataList = null;

            dataList = cmsSiteDataService.queryByUserIdAndSiteIdAndDataType(userId, siteId, dataType);

            if (page.getDataList().size() != 0) {
                for (int i = 0; i < page.getDataList().size(); i++) {
                    CmsPage cmsPage = (CmsPage) page.getDataList().get(i);
                    for (int j = 0; j < dataList.size(); j++) {
                        CmsSiteData cmsSiteData = dataList.get(j);
                        if (cmsPage.getPageId().equals(cmsSiteData.getDataId())) {
                            cmsPage.setFlag(1);//1表示已选择
                            break;
                        }
                    }
                }
            }

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
        } catch (Exception e) {
            logger.error("CmsPageAction.queryByPage异常：" + e.getMessage(), e);
        }

        return "queryByPage";
    }

    //修改页面更新定时器
    public String updateTimingFlag() {
        //0开启1关闭
        //	String timingPublishFlag =ConfigurationUtil.getString("timing_publish_flag");
    /*	if(timingPublishFlag.equals("0")){
			timingPublishFlag="1";
		}else{
			timingPublishFlag="0";
		}*/
        //PropertyFileConfigurer.witeProperties("config/pathConfig.properties","timing_publish_flag",timingPublishFlag);
        //this.addActionMessage(ConfigureUtils.getMessageConfig("add.success"));
        return this.queryForPage();
    }

    //分页列表
    public String queryForPage() {
        try {
            if (cmsPage == null) {
                cmsPage = new CmsPage();

            }
            String ids = null;
            Integer siteId = (Integer) session.get("siteId");
            SysUser sysuser = (SysUser) session.get("sysUser");
            if (adminType != null && adminType == 0) {
                List lists = cmsSiteDataService.listToString(sysuser.getUserId(), 1);
                if (lists != null && lists.size() != 0) {
                    ids = SqlJoinUtil.getSqlIn(lists, 1000, "PAGE_ID");
                }
                cmsPage.setIds(ids);
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
                    cmsPage.setPageId(keyWords.getId_keyword());
                }
                if (keyWords.getName_keyword() != null) {
                    cmsPage.setName(keyWords.getName_keyword().trim());
                }
                if (keyWords.getOutPath_keyword() != null) {
                    cmsPage.setOutputPath(keyWords.getOutPath_keyword().trim());
                }
                if (keyWords.getStatus_keyword() != null)
                    cmsPage.setStatus(keyWords.getStatus_keyword());
            }
            if (null != siteId) {
                cmsPage.setSiteId(siteId);
            }

            //返回专题页列表
//			if(cmsPage.getPublicType()==null || 9 != cmsPage.getPublicType()){
//				cmsPage.setPublicType(null);
//			}

            this.page = this.cmsPageService.getAllPage(cmsPage, page);
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
        } catch (Exception e) {
            logger.error("CmsPageAction.queryForPage异常：" + e.getMessage(), e);
        }
        //	timingPublishFlag =PropertyFileConfigurer.readValue("config/pathConfig.properties","timing_publish_flag");
        return PAGE_LIST;
    }


    //goto新增页
    public String gotoAddPage() {
        return "gotoAddPage";
    }

    //goto新增专题页
    public String gotoAddTheme() {
        return "gotoAddTheme";
    }


    //增加页面
    @Token
    public String addPage() {
        try {
            //获取当前管理员账号
            Map session = ActionContext.getContext().getSession();
            SysUser sysUser = (SysUser) session.get("sysUser");
            Integer siteId = (Integer) session.get("siteId");
            //站点ID
            cmsPage.setSiteId(siteId);
            //创建时间以及创建人
            this.cmsPage.setCreated(sysUser.getUserId());
            this.cmsPage.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());


            if (this.cmsPageService.addPage(cmsPage)) {
                List lsits = cmsSiteDataService.listToString(sysUser.getUserId(), 2);
                if (lsits != null && lsits.size() != 0) {
                    try {
                        cmsSiteDataService.addCSD(cmsPage.getPageId(), 1, sysUser.getUserId(), siteId, sysUser.getUserId());
                    } catch (Exception e) {
                        logger.error("CmsPageAction.addPage异常：" + e.getMessage(), e);
                    }
                }

            } else {
                this.addActionMessage(ConfigurationUtil.getString("add.fail"));

            }
            //日志记录
            cmsLog.setModuleName("页面");
            cmsLog.setModuleContent("新建页面> 页面名称:" + cmsPage.getName() + "  " +
                    "页面主题:" + cmsPage.getTitle() + " 页面输入路径:" + cmsPage.getOutputPath() + " 备注:" + cmsPage.getRemark());
            cmsLog.setConsoleOperator(sysUser.getUserId());
            cmsLog.setType(1);
            log.info(cmsLogService.insert(cmsLog));

            //	cmsPage=new CmsPage();
            //	cmsPage.setPageId(siteId)
            this.addActionMessage(ConfigurationUtil.getString("add.success"));
        } catch (Exception e) {
            this.addActionError(ConfigurationUtil.getString("add.fail"));
            logger.error("CmsPageAction.addPage异常：" + e.getMessage(), e);
        }
        //	this.keyWords=null;
        //保持状态
        this.keyWords = new KeyWords();
        keyWords.setId_keyword(cmsPage.getPageId());
        return this.queryForPage();
    }

    /**
     * 添加专题页
     */
    public String addTheme() {
        try {
            //获取当前管理员账号
            Map session = ActionContext.getContext().getSession();
            SysUser sysUser = (SysUser) session.get("sysUser");
            Integer siteId = (Integer) session.get("siteId");
            //站点ID
            cmsPage.setSiteId(siteId);
            //创建时间以及创建人
            this.cmsPage.setCreated(sysUser.getUserId());
            this.cmsPage.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());

            //添加专题页
            if (!this.cmsPageService.addTheme(cmsPage, cmsTheme)) {
                this.addActionMessage(ConfigurationUtil.getString("add.fail"));
            }

            //日志记录
            cmsLog.setModuleName("页面");
            cmsLog.setModuleContent("新建页面> 页面名称:" + cmsPage.getName() + "  " +
                    "页面主题:" + cmsPage.getTitle() + " 页面输入路径:" + cmsPage.getOutputPath() + " 备注:" + cmsPage.getRemark());
            cmsLog.setConsoleOperator(sysUser.getUserId());
            cmsLog.setType(1);
            log.info(cmsLogService.insert(cmsLog));

            //	cmsPage=new CmsPage();
            //	cmsPage.setPageId(siteId)
            this.addActionMessage(ConfigurationUtil.getString("add.success"));
        } catch (Exception e) {
            this.addActionError(ConfigurationUtil.getString("add.fail"));
            logger.error("CmsPageAction.addTheme异常：" + e.getMessage(), e);
        }
        //	this.keyWords=null;
        //保持状态
        this.keyWords = new KeyWords();
        keyWords.setId_keyword(cmsPage.getPageId());
        return this.queryForPage();
    }

    //goto模板选择页
    public String gotoSelPage() {
        try {
            Integer siteId = (Integer) session.get("siteId");
            this.page = new Page();
            if (this.cmsTemplate == null)
                cmsTemplate = new CmsTemplate();
            cmsTemplate.setStatus(0);
            cmsTemplate.setSiteId(siteId);
            this.page = this.cmsTemplateServce.queryForPage(cmsTemplate, page);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("CmsPageAction.gotoSelPage异常：" + e.getMessage(), e);
        }
        return "gotoSelPage";
    }

    //goto主题选择页
    public String gotoTheme() {
        Integer siteId = (Integer) session.get("siteId");
        if (cmsTheme == null) {
            cmsTheme = new CmsTheme();
        }
        if (null != siteId) {
            cmsTheme.setSiteId(siteId);
        }
        try {
            page = cmsThemeService.queryThemeListForPage(cmsTheme, page);
        } catch (SQLException e) {
            logger.error("CmsPageAction.gotoTheme异常：" + e.getMessage(), e);
        }
        return "gotoTheme";
    }


    //选择模板
    public void selTemplate() {
        try {
            this.cmsTemplate = this.cmsTemplateServce.getTemplateById(this.templateId);

            JSONObject json = new JSONObject();
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setContentType("text/html;charset=utf-8");
            json.put("content", cmsTemplate.getContent());
            json.put("templateId", cmsTemplate.getTemplateId());
            json.put("templateName", cmsTemplate.getName());
            response.getWriter().print(json.toJSONString());
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("CmsPageAction.selTemplate异常：" + e.getMessage(), e);
        }
    }

    //选择模板
    public void selStyles() {
        try {
            CmsStyles styles = null;
            styles = cmsStylesService.queryByCmsStylesId(stylesId);

            this.cmsTemplate = this.cmsTemplateServce.getTemplateById(styles.getTemplateId());

            JSONObject json = new JSONObject();
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setContentType("text/html;charset=utf-8");
            json.put("content", cmsTemplate.getContent());
            json.put("templateId", cmsTemplate.getTemplateId());
            json.put("stylesId", styles.getStylesId());
            json.put("remark", styles.getRemark());
            response.getWriter().print(json.toJSONString());
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("CmsPageAction.selStyles异常：" + e.getMessage(), e);
        }
    }


    /**
     * 选择风格
     */
    public String gotoSelStyles() {
        Integer siteId = (Integer) session.get("siteId");
        if (null == cmsStyles) {
            cmsStyles = new CmsStyles();
        }
        cmsStyles.setSiteId(siteId);
        try {
            page = cmsStylesService.queryForPage(cmsStyles, page);
            //获取对应的管理员
            sysUserMap = new HashMap();
            SysUserBean bean = SysUserBean.instance();
            SysUser vo = new SysUser();
            List<SysUser> list = bean.getSysUserList(vo);
            for (SysUser user : list) {
                sysUserMap.put(user.getUserId() + "", user.getUserName());
            }
        } catch (Exception e) {
            logger.error("CmsPageAction.gotoSelStyles异常：" + e.getMessage(), e);
        }

        return "gotoSelStyles";
    }

    //选择主题页面模板
    public void selThemeTemplate() {
        try {
            CmsThemeTemplate cmsThemeTemplate = new CmsThemeTemplate();
            cmsThemeTemplate.setThemeId(cmsTheme.getThemeId());
            //页面模板
            cmsThemeTemplate.setType(12);
            List<CmsThemeTemplate> cttList = cmsThemeTemplateService.queryByThemeTemp(cmsThemeTemplate);


            this.cmsTemplate = this.cmsTemplateServce.getTemplateById(cttList.get(0).getTemplateId());
            JSONObject json = new JSONObject();
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setContentType("text/html;charset=utf-8");
            json.put("content", cmsTemplate.getContent());
            json.put("templateId", cmsTemplate.getTemplateId());
            json.put("themeId", cmsTheme.getThemeId());
            response.getWriter().print(json.toJSONString());
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("CmsPageAction.selThemeTemplate异常：" + e.getMessage(), e);
        }
    }

    //goto编辑页
    public String gotoEditPage() {
        try {
            this.cmsPage = this.cmsPageService.getCmsPageById(this.pageId);
            if (cmsPage.getStylesId() != null) {
                CmsStyles styles = cmsStylesService.queryByCmsStylesId(cmsPage.getStylesId());
                stylesImage = styles.getRemark();
            }
        } catch (Exception e) {
            logger.error("CmsPageAction.gotoEditPage异常：" + e.getMessage(), e);
        }
        return "gotoEditPage";
    }


    //goto编辑页
    public String gotoThemeEditPage() {
        try {
            this.cmsPage = this.cmsPageService.getCmsPageById(this.pageId);
        } catch (Exception e) {
            logger.error("CmsPageAction.gotoThemeEditPage异常：" + e.getMessage(), e);
        }
        return "gotoThemeEditPage";
    }

    //页面编辑
    @Token
    public String editPage() {
        try {
            //获取当前管理员账号
            Map session = ActionContext.getContext().getSession();
            SysUser sysUser = (SysUser) session.get("sysUser");
            this.cmsPage.setModified(sysUser.getUserId());
            this.cmsPage.setModifyDate(DateTimeUtils.getCalendarInstance().getTime());
            //已发布的情况
            //	if(cmsPage.getStatus()==1){
            //改成已修改
            cmsPage.setStatus(2);
            //	}
            this.cmsPageService.updateCmsPage(cmsPage);

            //日志记录
            cmsLog.setModuleName("页面");
            cmsLog.setModuleContent("修改页面> 页面名称:" + cmsPage.getName() + "  " +
                    "页面主题:" + cmsPage.getTitle() + " 页面输入路径:" + cmsPage.getOutputPath() + " 备注:" + cmsPage.getRemark());
            cmsLog.setConsoleOperator(sysUser.getUserId());
            cmsLog.setType(2);
            log.info(cmsLogService.insert(cmsLog));

            this.addActionMessage(ConfigurationUtil.getString("update.success"));
        } catch (Exception e) {
            this.addActionMessage(ConfigurationUtil.getString("update.fail"));
            logger.error("CmsPageAction.editPage异常：" + e.getMessage(), e);
        }
        //保持状态
        this.keyWords = new KeyWords();
        keyWords.setId_keyword(cmsPage.getPageId());
        //	cmsPage=new CmsPage();

        return this.queryForPage();
    }

    //删除页面
    public String delPage() {
        try {
            //日志记录
            SysUser sysUser = (SysUser) session.get("sysUser");
            CmsPage cmsPages = cmsPageService.getCmsPageById(pageId);
            cmsLog.setModuleName("页面");
            cmsLog.setModuleContent("删除" + cmsPages.getName() + "页");
            cmsLog.setConsoleOperator(sysUser.getUserId());
            cmsLog.setType(3);
            log.info(cmsLogService.insert(cmsLog));

            this.cmsPageService.delCmsPageById(pageId);
            this.addActionMessage(ConfigurationUtil.getString("delete.success"));
        } catch (Exception e) {
            this.addActionMessage(ConfigurationUtil.getString("delete.fail"));
            logger.error("CmsPageAction.delPage异常：" + e.getMessage(), e);
        }
        this.keyWords = null;

        return this.queryForPage();
    }

    //删除所选的页面
    public String delAllPage() {
        try {
            SysUser sysUser = (SysUser) session.get("sysUser");
            //日志记录
            cmsLog.setModuleName("页面");
            cmsLog.setIds(this.checkeds);
            cmsLog.setConsoleOperator(sysUser.getUserId());
            log.info(cmsLogService.insert(cmsLog));

            this.cmsPageService.delAllPage(this.checkeds);
            this.checkeds = null;
            this.addActionMessage(ConfigurationUtil.getString("delete.success"));
        } catch (Exception e) {
            this.addActionMessage(ConfigurationUtil.getString("delete.fail"));
            logger.error("CmsPageAction.delAllPage异常：" + e.getMessage(), e);

        }
        this.keyWords = null;

        return this.queryForPage();
    }

    //页面关键字搜索
    public String queryPage() {
        try {
            //	cmsPage=new CmsPage();
            //保留关键字
            if (page == null && keyWords != null) {
                page = new Page();
                if (keyWords.getPageNo() != null && keyWords.getPageSize() != null) {
                    page.setPageNo(keyWords.getPageNo());
                    page.setPageSize(keyWords.getPageSize());
                }
            }
            if (keyWords != null) {
                if (keyWords.getId_keyword() != null) {
                    cmsPage.setPageId(keyWords.getId_keyword());
                }
                if (keyWords.getName_keyword() != null) {
                    cmsPage.setName(keyWords.getName_keyword().trim());
                }
                if (keyWords.getOutPath_keyword() != null) {
                    cmsPage.setOutputPath(keyWords.getOutPath_keyword().trim());
                }
                if (keyWords.getStatus_keyword() != null) {
                    if (keyWords.getStatus_keyword() == -1)
                        cmsPage.setStatus(null);
                    else
                        cmsPage.setStatus(keyWords.getStatus_keyword());
                }

            }

            //分页查询
            this.page = this.cmsPageService.getAllPage(cmsPage, page);
            if (keyWords != null) {
                keyWords.setPageNo(this.page.getPageNo());
                keyWords.setPageSize(page.getPageSize());
            }
            //获取对应的管理员
            sysUserMap = new HashMap();
            SysUserBean bean = SysUserBean.instance();
            SysUser vo = new SysUser();
            List<SysUser> list = bean.getSysUserList(vo);
            for (SysUser user : list) {
                sysUserMap.put(user.getUserId() + "", user.getUserName());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("CmsPageAction.queryPage异常：" + e.getMessage(), e);
        }
        return "pageList";
    }

    //模板列表
    public String tempSel() {
        try {
            Integer siteId = (Integer) session.get("siteId");
            if (cmsTemplate == null) {
                cmsTemplate = new CmsTemplate();
            }
            if (this.page == null) {
                page = new Page();
            }
            if (cmsTemplate.getType() == -1)
                cmsTemplate.setType(null);
            cmsTemplate.setStatus(0);
            cmsTemplate.setSiteId(siteId);
            this.page = this.cmsTemplateServce.queryForPage(cmsTemplate, page);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("CmsPageAction.tempSel异常：" + e.getMessage(), e);
        }
        return "gotoSelPage";
    }

    //跳转窗口选择页
    public String gotoBindWindow() {
        try {
            Integer siteId = (Integer) session.get("siteId");
            if (cmsWindow == null)
                cmsWindow = new CmsWindow();
            if (page == null)
                page = new Page();
            cmsWindow.setSiteId(siteId);
            this.page = this.cmsWindowService.getWindow_NotIn(cmsWindow, page, pageId);
            //this.page=this.cmsWindowService.queryForPage(cmsWindow, page);
        } catch (Exception e) {
            logger.error("CmsPageAction.gotoBindWindow异常：" + e.getMessage(), e);
        }
        return "gotoBindWindow";
    }

    //页面详情
    public String pageDetail() {
        try {
            if (cmsPageDetail == null)
                cmsPageDetail = new CmsPageDetail();
            this.cmsPage = this.cmsPageService.getCmsPageById(pageId);
            this.cmsPageDetail.setCmsPage(cmsPage);
            this.page = this.cmsWindowService.getWindow_In(cmsWindow, page, pageId);
            this.cmsPageDetail.setPage(page);

//			if(cmsPage.getTemplateId()!=null){
//				if(cmsTheme==null){
//					cmsTheme = new CmsTheme();
//				}
//				this.cmsTheme=(CmsTheme) this.cmsThemeService.queryThemeList(cmsTheme);
//			}

            SysUserBean bean = SysUserBean.instance();
            //获取创建人
            if (cmsPage.getCreated() != null) {
                SysUser created = bean.getSysUserDetail(cmsPage.getCreated());
                this.cmsPageDetail.setUser_Cre(created.getUserName());
            }
            //获取修改人
            if (cmsPage.getModified() != null) {
                SysUser modified = bean.getSysUserDetail(cmsPage.getModified());
                this.cmsPageDetail.setUser_Mod(modified.getUserName());
            }
        } catch (Exception e) {
            logger.error("CmsPageAction.pageDetail异常：" + e.getMessage(), e);
        }
        return "pageDetail";
    }

    //删除某条页面绑定窗口的记录
    public String delPwDate() {
        try {
            CmsPageWindow cmsPageWindow = new CmsPageWindow();
            cmsPageWindow.setPageId(pageId);
            cmsPageWindow.setWindowId(windowId);

            //日志记录
            SysUser sysUser = (SysUser) session.get("sysUser");
            cmsLog.setModuleName("页面");
            cmsLog.setIds(windowId.toString());
            cmsLog.setConsoleOperator(sysUser.getUserId());
            log.info(cmsLogService.del(cmsLog, pageId));

            int res = this.cmsPageWindowService.delRecord(cmsPageWindow);
            if (res > 0) {
                CmsPage cmsPage = this.cmsPageService.getCmsPageById(pageId);
                cmsPage.setModifyDate(DatetimeUtil.getCalendarInstance().getTime());
                cmsPage.setStatus(2);
                this.cmsPageService.updateCmsPage(cmsPage);
            }
            this.addActionMessage(ConfigurationUtil.getString("delete.success"));
        } catch (Exception e) {
            this.addActionMessage(ConfigurationUtil.getString("delete.fail"));
            logger.error("CmsPageAction.delPwDate异常：" + e.getMessage(), e);
        }
        this.keyWords = null;
        return this.pageDetail();
    }

    //删除选中的窗口记录
    public String delAll() {
        try {
            CmsPageWindow cmsPageWindow = new CmsPageWindow();
            cmsPageWindow.setPageId(pageId);

            //日志记录
            SysUser sysUser = (SysUser) session.get("sysUser");
            cmsLog.setModuleName("页面");
            cmsLog.setIds(this.checkeds);
            cmsLog.setConsoleOperator(sysUser.getUserId());
            log.info(cmsLogService.del(cmsLog, pageId));

            int res = this.cmsPageWindowService.delAllRecord(this.checkeds, cmsPageWindow);
            if (res > 0) {
                CmsPage cmsPage = this.cmsPageService.getCmsPageById(pageId);
                cmsPage.setModifyDate(DatetimeUtil.getCalendarInstance().getTime());
                cmsPage.setStatus(2);
                this.cmsPageService.updateCmsPage(cmsPage);
            }
            this.addActionMessage(ConfigurationUtil.getString("delete.success"));
        } catch (Exception e) {
            this.addActionMessage(ConfigurationUtil.getString("delete.fail"));
            logger.error("CmsPageAction.delAll异常：" + e.getMessage(), e);
        }
        this.keyWords = null;
        return this.pageDetail();
    }

    //检测名字是否唯一
    public void check() {
        try {
            HttpServletResponse response = ServletActionContext.getResponse();
            Integer siteId = (Integer) session.get("siteId");
            if (siteId != null && cmsPage != null)
                cmsPage.setSiteId(siteId);
            String result = this.cmsPageService.checkName(cmsPage);
            response.getWriter().write(result);
        } catch (Exception e) {
            logger.error("CmsPageAction.check异常：" + e.getMessage(), e);
        }
    }

    public CmsPageService getCmsPageService() {
        return cmsPageService;
    }

    public void setCmsPageService(CmsPageService cmsPageService) {
        this.cmsPageService = cmsPageService;
    }

    public CmsPage getCmsPage() {
        return cmsPage;
    }

    public void setCmsPage(CmsPage cmsPage) {
        this.cmsPage = cmsPage;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
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

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public String getCheckeds() {
        return checkeds;
    }

    public void setCheckeds(String checkeds) {
        this.checkeds = checkeds;
    }

    public CmsWindowService getCmsWindowService() {
        return cmsWindowService;
    }

    public void setCmsWindowService(CmsWindowService cmsWindowService) {
        this.cmsWindowService = cmsWindowService;
    }

    public CmsWindow getCmsWindow() {
        return cmsWindow;
    }

    public void setCmsWindow(CmsWindow cmsWindow) {
        this.cmsWindow = cmsWindow;
    }

    public CmsPageDetail getCmsPageDetail() {
        return cmsPageDetail;
    }

    public void setCmsPageDetail(CmsPageDetail cmsPageDetail) {
        this.cmsPageDetail = cmsPageDetail;
    }

    public CmsPageWindowService getCmsPageWindowService() {
        return cmsPageWindowService;
    }

    public void setCmsPageWindowService(CmsPageWindowService cmsPageWindowService) {
        this.cmsPageWindowService = cmsPageWindowService;
    }

    public Integer getWindowId() {
        return windowId;
    }

    public void setWindowId(Integer windowId) {
        this.windowId = windowId;
    }

    public Integer getBackType() {
        return backType;
    }

    public void setBackType(Integer backType) {
        this.backType = backType;
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


    public void setTimingPublish(TimingPublish timingPublish) {
        this.timingPublish = timingPublish;
    }


    public void setCmsSiteDataService(CmsSiteDataService cmsSiteDataService) {
        this.cmsSiteDataService = cmsSiteDataService;
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

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public String getTimingPublishFlag() {
        return timingPublishFlag;
    }

    public void setTimingPublishFlag(String timingPublishFlag) {
        this.timingPublishFlag = timingPublishFlag;
    }


    public PagePublish getPagePublish() {
        return pagePublish;
    }


    public void setPagePublish(PagePublish pagePublish) {
        this.pagePublish = pagePublish;
    }

    public RemoteSupplierParseService getRemoteSupplierParseService() {
        return remoteSupplierParseService;
    }


    public void setRemoteSupplierParseService(
            RemoteSupplierParseService remoteSupplierParseService) {
        this.remoteSupplierParseService = remoteSupplierParseService;
    }


    public CmsTheme getCmsTheme() {
        return cmsTheme;
    }


    public void setCmsTheme(CmsTheme cmsTheme) {
        this.cmsTheme = cmsTheme;
    }


    public CmsThemeService getCmsThemeService() {
        return cmsThemeService;
    }


    public void setCmsThemeService(CmsThemeService cmsThemeService) {
        this.cmsThemeService = cmsThemeService;
    }


    public CmsThemeTemplateService getCmsThemeTemplateService() {
        return cmsThemeTemplateService;
    }


    public void setCmsThemeTemplateService(
            CmsThemeTemplateService cmsThemeTemplateService) {
        this.cmsThemeTemplateService = cmsThemeTemplateService;
    }


    public CmsInformationAtion getCmsInformationAtion() {
        return cmsInformationAtion;
    }


    public void setCmsInformationAtion(CmsInformationAtion cmsInformationAtion) {
        this.cmsInformationAtion = cmsInformationAtion;
    }


    public CmsStylesService getCmsStylesService() {
        return cmsStylesService;
    }


    public void setCmsStylesService(CmsStylesService cmsStylesService) {
        this.cmsStylesService = cmsStylesService;
    }


    public Integer getStylesId() {
        return stylesId;
    }


    public void setStylesId(Integer stylesId) {
        this.stylesId = stylesId;
    }


    public List<CmsPageVisualization> getCpvList() {
        return cpvList;
    }


    public void setCpvList(List<CmsPageVisualization> cpvList) {
        this.cpvList = cpvList;
    }


    public CmsPageVisualizationService getCmsPageVisualizationService() {
        return cmsPageVisualizationService;
    }


    public void setCmsPageVisualizationService(
            CmsPageVisualizationService cmsPageVisualizationService) {
        this.cmsPageVisualizationService = cmsPageVisualizationService;
    }

    public Integer getShopId() {
        return shopId;
    }


    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public CmsStyles getCmsStyles() {
        return cmsStyles;
    }


    public void setCmsStyles(CmsStyles cmsStyles) {
        this.cmsStyles = cmsStyles;
    }


    public String getStylesImage() {
        return stylesImage;
    }

    public Integer getSupplierId() {
        return supplierId;
    }


    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public void setStylesImage(String stylesImage) {
        this.stylesImage = stylesImage;
    }


    public String getOutputPath() {
        return outputPath;
    }

    public Integer getShopType() {
        return shopType;
    }


    public void setShopType(Integer shopType) {
        this.shopType = shopType;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }


    public String getContent() {
        return content;
    }


    public void setContent(String content) {
        this.content = content;
    }


}
