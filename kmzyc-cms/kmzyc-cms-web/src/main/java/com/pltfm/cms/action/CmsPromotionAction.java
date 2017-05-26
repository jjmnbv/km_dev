package com.pltfm.cms.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ActionContext;
import com.pltfm.app.service.ViewPromotionService;
import com.pltfm.app.util.AjaxUtil;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.util.Token;
import com.pltfm.app.vobject.PromotionType;
import com.pltfm.app.vobject.ViewPromotion;
import com.pltfm.cms.parse.HtmlBuilder;
import com.pltfm.cms.parse.PathConstants;
import com.pltfm.cms.parse.PromotionPublish;
import com.pltfm.cms.service.CmsAdcolumnDataService;
import com.pltfm.cms.service.CmsInformationService;
import com.pltfm.cms.service.CmsPageService;
import com.pltfm.cms.service.CmsPromotionService;
import com.pltfm.cms.service.CmsSiteDataService;
import com.pltfm.cms.service.CmsSiteService;
import com.pltfm.cms.util.FileOperateUtils;
import com.pltfm.cms.util.ReturnResult;
import com.pltfm.cms.util.SqlJoinUtil;
import com.pltfm.cms.vobject.CmsAdcolumn;
import com.pltfm.cms.vobject.CmsInformation;
import com.pltfm.cms.vobject.CmsPage;
import com.pltfm.cms.vobject.CmsPromotionTask;
import com.pltfm.cms.vobject.CmsSiteData;
import com.pltfm.cms.vobject.KeyWords;
import com.pltfm.sys.bean.SysUserBean;
import com.pltfm.sys.model.SysUser;
import com.pltfm.sys.util.DatetimeUtil;
import com.whalin.MemCached.MemCachedClient;

@Scope(value = "prototype")
@Component("cmsPromotionAction")
public class CmsPromotionAction extends UploadFileAction {
	private static Logger logger = LoggerFactory.getLogger(CmsPromotionAction.class);
    @Resource(name = "cmsAdcolumnDataService")
    private CmsAdcolumnDataService cmsAdcolumnDataService;//cmsAdcolumnDataService广告接口
    @Resource(name = "cmsPageService")
    private CmsPageService cmsPageService;//cmsPageService页面接口
    @Resource(name = "cmsPromotionService")
    private CmsPromotionService cmsPromotionService;//cmsPromotionService活动接口
    @Resource(name = "cmsInformationService")
    private CmsInformationService cmsInformationService;//cmsInformationService资讯接口
    @Resource(name = "promotionPublish")
    private PromotionPublish promotionPublish;
    private CmsPromotionTask cmspt;
    private Integer adminType;//角色区分
    private Integer outPutType;//1页面2广告3活动4资讯
    private String outPut;
    private Integer userId;
    private Integer siteId;
    private Integer dataType;
    private String checkeds;//多选框
    private Integer pageNo;
    private Integer operateType;//运营端类型
    @Resource(name = "memcachedClient")
    private MemCachedClient memcachedClient;

    public Integer getPromotionTypeId() {
        return promotionTypeId;
    }

    public void setPromotionTypeId(Integer promotionTypeId) {
        this.promotionTypeId = promotionTypeId;
    }

    private Integer promotionTypeId;//促销类型
    private Page page;
    private List<PromotionType> listPromotionType;
    private String ajax;
    private Integer promotionId;
    private Integer[] levelId;
    @Resource(name = "htmlBuilder")
    private HtmlBuilder htmlBuilder;
//    private uploadFile file, file2, file3, file4;
    // myFile属性用来封装上传的文件
//    private File resumefile, resumefile2, resumefile3, resumefile4;

    // myFileContentType属性用来封装上传文件的类型  
//    private String resumefileContentType, resumefile2ContentType, resumefile3ContentType, resumefile4ContentType;

    // myFileFileName属性用来封装上传文件的文件名  
//    private String resumefileFileName, resumefile2FileName, resumefile3FileName, resumefile4FileName;

    /**
     * 活动信息业务逻辑层接口
     */
    @Resource(name = "viewPromotionService")
    private ViewPromotionService viewPromotionService;
    @Resource(name = "cmsSiteDataService")
    CmsSiteDataService cmsSiteDataService;

    /**
     * 站点接口
     */
    @Resource(name = "cmsSiteService")
    private CmsSiteService cmsSiteService;


    /**
     * 活动信息实体
     */
    private ViewPromotion viewPromotion;
    /**
     * 活动类目
     */
    private Map categoryMap;

    private String callback;

    private String jsoncallback;
    ActionContext actionContext = ActionContext.getContext();
    Map session = actionContext.getSession();
    /*
     * 渠道
	 */
//	private String channel;

    /**
     * 查询条件渠道
     */
//    private String channelQuery;

    /**
     * 跳转数据列表页面
     */
    public String openPomotionList() {
        return "openPomotionList";
    }

    /**
     * 弹出层活动列表
     */
    public String queryByPromotion() {
        try {
            cmspt = new CmsPromotionTask();
            if (page == null && keyWords != null) {
                page = new Page();
                page.setPageNo(keyWords.getPageNo());
                page.setPageSize(keyWords.getPageSize());
            }
            if (keyWords != null) {
                cmspt.setName(keyWords.getName_keyword().trim());
                cmspt.setOutput(keyWords.getOutPath_keyword().trim());
                cmspt.setBeginTime(keyWords.getBeginTime_keyword());
                cmspt.setEndTime(keyWords.getEndTime_keyword());
                if (keyWords.getStatus_keyword() != null)
                    cmspt.setStatus(keyWords.getStatus_keyword());
            }
            cmspt.setSiteId(siteId);
            page = cmsPromotionService.queryForPage(cmspt, page);

            List<CmsSiteData> dataList;

            dataList = cmsSiteDataService.queryByUserIdAndSiteIdAndDataType(userId, siteId, dataType);

            if (page.getDataList().size() != 0) {
                for (int i = 0; i < page.getDataList().size(); i++) {
                    CmsPromotionTask cmsPromotionTask = (CmsPromotionTask) page.getDataList().get(i);
                    for (CmsSiteData cmsSiteData : dataList) {
                        if (cmsPromotionTask.getId().equals(cmsSiteData.getDataId())) {
                            cmsPromotionTask.setFlag(1);//1表示已选择
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
            this.categoryMap = viewPromotionService.getPromotionCategory();
        } catch (Exception e) {
            logger.error("CmsPromotionAction.queryByPromotion弹出层活动列表异常：" + e.getMessage(), e);
        }
        return "queryByPromotion";
    }

    /**
     * 活动信息列表
     */
    public String queryForPage() {

        try {
            String ids = null;
            Integer siteId = (Integer) session.get("siteId");
            SysUser sysuser = (SysUser) session.get("sysUser");
            if (adminType != null && adminType == 0) {
                List lists = cmsSiteDataService.listToString(sysuser.getUserId(), 3);
                if (CollectionUtils.isNotEmpty(lists)) {
                    ids = SqlJoinUtil.getSqlIn(lists, 1000, "ID");
                }
            }
            cmspt = new CmsPromotionTask();
            cmspt.setIds(ids);
            if (page == null && keyWords != null) {
                page = new Page();
                if (keyWords.getPageNo() != null && keyWords.getPageSize() != null) {
                    page.setPageNo(keyWords.getPageNo());
                    page.setPageSize(keyWords.getPageSize());
                }
            }
            if (keyWords != null) {


                if (keyWords.getName_keyword() != null) {
                    cmspt.setName(keyWords.getName_keyword().trim());
                }
                if (keyWords.getOutPath_keyword() != null) {
                    cmspt.setOutput(keyWords.getOutPath_keyword());
                }
                if (keyWords.getBeginTime_keyword() != null) {
                    cmspt.setBeginTime(keyWords.getBeginTime_keyword());
                }
                if (keyWords.getEndTime_keyword() != null) {
                    cmspt.setEndTime(keyWords.getEndTime_keyword());
                }

                if (keyWords.getStatus_keyword() != null)
                    cmspt.setStatus(keyWords.getStatus_keyword());
            }
            cmspt.setSiteId(siteId);
            page = cmsPromotionService.queryForPage(cmspt, page);
            if (keyWords == null) {
                keyWords = new KeyWords();
            }
            keyWords.setPageNo(page.getPageNo());
            keyWords.setPageSize(page.getPageSize());
            this.categoryMap = viewPromotionService.getPromotionCategory();
            sysUserMap = new HashMap();
            SysUserBean bean = SysUserBean.instance();
            SysUser vo = new SysUser();
            List<SysUser> list = bean.getSysUserList(vo);
            for (SysUser user : list) {
                sysUserMap.put(user.getUserId() + "", user.getUserName());
            }
        } catch (Exception e) {
            logger.error("CmsPromotionAction.queryForPage活动信息列表异常：" + e.getMessage(), e);
        }
        return "promotionList";
    }

    public String addgoto() {

        return "goto";
    }

    /**
     * 活动信息添加
     */
    @Token
    public String promotionAdd() {
        buildFile();
        try {
            ///	cmsPromotionAttrService.addpromotion(file,file2,file3,file4,pro);
            //获取当前站点ID
            Integer siteId = (Integer) session.get("siteId");
            //获取当前管理员账号
            SysUser sysUser = (SysUser) session.get("sysUser");

            cmspt.setSiteId(siteId);
            int s = cmsPromotionService.promotionAdd(file, file2, file3, file4, cmspt);
            if (s == 1) {
                List lsits = cmsSiteDataService.listToString(sysUser.getUserId(), 2);
                if (lsits != null && lsits.size() != 0) {
                    try {
                        cmsSiteDataService.addCSD(cmspt.getId(), 3, sysUser.getUserId(), siteId, sysUser.getUserId());
                    } catch (Exception e) {
                        logger.error("CmsPromotionAction.promotionAdd活动站点添加异常：" + e.getMessage(), e);
                    }
                }
            } else {
                this.addActionMessage("添加失败");
            }
            //查询该站点下的所有活动
            CmsPromotionTask promotion = new CmsPromotionTask();
            promotion.setSiteId(siteId);
            List<CmsPromotionTask> list = cmsPromotionService.getAllPromotionTask(promotion);
            if (list != null) {
                for (CmsPromotionTask promotionTask : list) {
                    this.memcachedClient.set("cms_url_promotion_id_" + promotionTask.getId() + "", promotionTask.getOutput());
                }
            }
            //	String res=this.promotionPublish.promotionPublish(cmspt.getId(),siteId);
        /*	if(res.equals("notFound"))
            {
				this.addActionMessage("生成页面失败，产品系统中的活动已取消发布或已删除");
			}else if(res.equals("success"))
			{
				this.addActionMessage("添加成功");
			}
			else
			{
				this.addActionMessage("生成页面失败，无法获取活动产品数据");
			}
			*/
            cmspt = new CmsPromotionTask();

        } catch (Exception e) {
            logger.error("CmsPromotionAction.promotionAdd活动添加异常：" + e.getMessage(), e);
            this.addActionMessage("添加失败");
        }
        this.addActionMessage("添加成功");
        this.keyWords = new KeyWords();
        keyWords.setName_keyword(cmspt.getName());
        return queryForPage();
    }

    /**
     * 产品提供的活动列表
     */
    public String promotion() {
        try {
//            Integer siteId = (Integer) session.get("siteId");
//            CmsSite cmsSite = cmsSiteService.selectByPrimaryKey(siteId);
            listPromotionType = new ArrayList<PromotionType>();
            if (viewPromotion == null)
                viewPromotion = new ViewPromotion();
            //去掉全场活动
            viewPromotion.setProductFilterType(1);

            //当channel为空时从站点中去渠道数据
//			if(channel== null || channel.isEmpty()){
//				channel = cmsSite.getChannel();
//			}


            //设置渠道
//			viewPromotion.setChannel(StringUtil.packChannel(channel));

            if (viewPromotion.getExpiredFlag() != null) {
                viewPromotion.setNowTime(DatetimeUtil.getCurTime());
            }
            page = viewPromotionService.queryForPage(viewPromotion, page);
            this.categoryMap = viewPromotionService.getPromotionCategory();
//            Iterator itr = categoryMap.keySet().iterator();
            Iterator<Map.Entry<Integer,String>> itr=categoryMap.entrySet().iterator();
            PromotionType promotionType;
            while (itr.hasNext()) {
                Map.Entry<Integer,String> entry=itr.next();
                Integer key =entry.getKey();
                String value = entry.getValue();
                promotionType = new PromotionType();
                promotionType.setPromotionTypeId(key);
                promotionType.setPromotionTypeName(value);
                listPromotionType.add(promotionType);
            }
            return "viewPromotionSccuess";
        } catch (Exception e) {
            logger.error("CmsPromotionAction.promotion产品提供的活动列表异常：" + e.getMessage(), e);
            return "viewPromotionError";
        }
    }

    /**
     * 从产品提供的活动列表获取内容给活动赋值
     */
    public String ajaxpromotion() {
        JSONObject json = new JSONObject();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=utf-8");
        try {

            Integer siteId = (Integer) session.get("siteId");
            CmsPromotionTask cmsp = new CmsPromotionTask();
            cmsp.setId(promotionId);
            cmsp.setSiteId(siteId);
            if (this.operateType != null) {
                cmsp.setOperateType(operateType);
            }
            cmspt = cmsPromotionService.cmspById(cmsp);
            if (cmspt == null) {
                viewPromotion = cmsPromotionService.byId(promotionId);
                String begin = DateTimeUtils.getDateTime(viewPromotion.getStartTime());
                String end = DateTimeUtils.getDateTime(viewPromotion.getEndTime());

                json.put("obj", viewPromotion);
                json.put("begin", begin);
                json.put("end", end);
            } else {
                json = null;
            }
            response.getWriter().print(json);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            logger.error("CmsPromotionAction.ajaxpromotion从产品提供的活动列表获取内容给活动赋值异常：" + e.getMessage(), e);
        }
        viewPromotion = new ViewPromotion();
        return null;
    }

    /**
     * 删除
     */
    public String promotionDelete() {
        try {
            Integer siteId = (Integer) session.get("siteId");
            cmsPromotionService.promotionDelete(promotionId, siteId);
            cmspt = new CmsPromotionTask();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("CmsPromotionAction.promotionDelete活动删除异常：" + e.getMessage(), e);
            this.addActionMessage("删除成功");
        }
        this.addActionMessage("删除成功");
        this.keyWords = null;
        return queryForPage();
    }

    /**
     * 多选删除
     */
    @Token
    public String All_promotionDelete() {
        try {
            Integer siteId = (Integer) session.get("siteId");
            for (int i = 0; i < levelId.length; i++) {
                cmsPromotionService.promotionDelete(levelId[i], siteId);
            }
            cmspt = new CmsPromotionTask();
            this.addActionMessage(ConfigurationUtil.getString("delete.success"));
        } catch (Exception e) {
            this.addActionMessage(ConfigurationUtil.getString("delete.fail"));
            logger.error("CmsPromotionAction.All_promotionDelete活动多选删除异常：" + e.getMessage(), e);
        }
        this.keyWords = null;
        return queryForPage();
    }

    /**
     * 根据ID查
     */
    public String promotionById() {
        try {
            Integer siteId = (Integer) session.get("siteId");
            cmspt = cmsPromotionService.cmspbyId(promotionId, siteId);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("CmsPromotionAction.promotionById活动根据ID查异常：" + e.getMessage(), e);
        }
        return "ByidSuccess";
    }

    /**
     * 修改
     */
    @Token
    public String promotionUpdate() {
        buildFile();
        try {
            //	cmspt=cmsPromotionService.queryPromo(cmspt.getId());
            siteId = (Integer) session.get("siteId");
            cmspt.setStatus(2);
            cmspt.setSiteId(siteId);
            //获取当前管理员账号
            SysUser sysUser = (SysUser) session.get("sysUser");
            cmspt.setModified(sysUser.getUserId());
            cmspt.setModifyDate(DateTimeUtils.getCalendarInstance().getTime());
            cmsPromotionService.promotionUpdate(file, file2, file3, file4, cmspt);
            this.memcachedClient.set("cms_url_promotion_id_" + cmspt.getId() + "", cmspt.getOutput());
            //	cmspt=new CmsPromotionTask();
            this.addActionMessage("修改成功");
        } catch (Exception e) {
            logger.error("CmsPromotionAction.promotionUpdate活动修改异常：" + e.getMessage(), e);
        }
        this.addActionMessage("修改成功");
        //保持状态
        this.keyWords = new KeyWords();
        keyWords.setName_keyword(cmspt.getName());
        return queryForPage();
    }

    //活动发布
    public String promotionPublic() {
        Integer siteId = (Integer) session.get("siteId");
        String result = this.promotionPublish.promotionPublish(this.promotionId, siteId);
        if (result.equals("notFound")) {
            this.addActionMessage(ConfigurationUtil.getString("publish.promotion.fail"));
        } else if (result.equals("success")) {
            this.addActionMessage(ConfigurationUtil.getString("publish.promotion.success"));
        } else {
            this.addActionMessage(ConfigurationUtil.getString("publish.promotion.error"));
        }
        ViewPromotion promotion = null;
        try {
            //Integer siteId = (Integer)session.get("siteId");
            CmsPromotionTask cmsPromotionTask = this.cmsPromotionService.cmspbyId(promotionId, siteId);
            promotion = cmsPromotionService.byId(this.promotionId);
            //获取模板路径
            promotion.setPath(PathConstants.promotion(cmsPromotionTask.getSiteId()) + "/" + promotion.getPromotionId() + ".html");
            //获取内容
            String html = htmlBuilder.promotionParse(promotion, cmsPromotionTask);
            if (html == null || html.equals(""))//发布失败
            {
                this.addActionMessage(ConfigurationUtil.getString("publish.page.fail"));
            } else {
                //组装发布路径
                String outPath = null;
                if (cmsPromotionTask.getOutput().startsWith("/")) {
                    outPath = PathConstants.publishPath(cmsPromotionTask.getSiteId());
                } else {
                    outPath = PathConstants.publishPath(cmsPromotionTask.getSiteId()) + "/";
                }
                outPath = outPath + cmsPromotionTask.getOutput().substring(0, cmsPromotionTask.getOutput().lastIndexOf("/"));
                //判断输入文件夹是否存在
                FileOperateUtils.checkAndCreateDirs(outPath);
                //完整的输出路径
                outPath = outPath + cmsPromotionTask.getOutput().substring(cmsPromotionTask.getOutput().lastIndexOf("/"));
                //写入文件
                FileOperateUtils.writer(outPath, html);
                CmsPromotionTask promotionTask = this.cmsPromotionService.queryPromo(this.promotionId, siteId);
                promotionTask.setStatus(1);
                this.cmsPromotionService.promotionUpdate(promotionTask);
                this.addActionMessage(ConfigurationUtil.getString("publish.page.success"));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("CmsPromotionAction.promotionPublic活动发布异常：" + e.getMessage(), e);
            this.addActionMessage(ConfigurationUtil.getString("publish.page.fail"));
        }
        return this.queryForPage();
    }

    //活动预览
    public void promotionPreview() {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");
        try {
            Integer siteId = (Integer) session.get("siteId");
            CmsPromotionTask cmsPromotionTask = this.cmsPromotionService.cmspAjaxbyId(this.promotionId, siteId);
            ViewPromotion promotion = cmsPromotionService.byId(this.promotionId);
            promotion.setPath(PathConstants.promotion(cmsPromotionTask.getSiteId()) + "/" + promotion.getPromotionId() + ".html");
            String html = htmlBuilder.promotionParse(promotion, cmsPromotionTask);
            if (html == null)
                return;
            // 写入文件
            String outPath = null, viewPath = null;
            //预览文件名
            if (!cmsPromotionTask.getOutput().contains("/")) {
                outPath = PathConstants.viewPagePublishPath(cmsPromotionTask.getSiteId()) + "/" + cmsPromotionTask.getOutput();
                viewPath = PathConstants.pageViewPath(cmsPromotionTask.getSiteId()) + "/" + cmsPromotionTask.getOutput();
            } else {
                outPath = PathConstants.viewPagePublishPath(cmsPromotionTask.getSiteId());
                if (!cmsPromotionTask.getOutput().startsWith("/"))
                    outPath = outPath + "/";
                outPath = outPath + cmsPromotionTask.getOutput().substring(0, cmsPromotionTask.getOutput().lastIndexOf("/"));
                //判断输入文件夹是否存在
                FileOperateUtils.checkAndCreateDirs(outPath);
                //完整的文件输出路径以及预览路径
                String pageName = cmsPromotionTask.getOutput().substring(cmsPromotionTask.getOutput().lastIndexOf("/")).replace("/", "preview_");
                outPath = outPath + "/" + pageName;

                String s = cmsPromotionTask.getOutput().substring(0, cmsPromotionTask.getOutput().lastIndexOf("/"));
                if (s.startsWith("/"))
                    viewPath = PathConstants.pageViewPath(cmsPromotionTask.getSiteId()) + s;
                else
                    viewPath = PathConstants.pageViewPath(cmsPromotionTask.getSiteId()) + "/" + s;
                viewPath += pageName;
            }

            //写入文件
            FileOperateUtils.writer(outPath, html);
            //预览
            response.sendRedirect(viewPath);
        } catch (Exception e) {
            logger.error("CmsPromotionAction.promotionPreview活动预览异常：" + e.getMessage(), e);
        }
    }

    public void outPutValidate() {
        int count = 0;
        Integer siteId = (Integer) session.get("siteId");
        try {
            String path = "";
            if (outPutType == 1) {
                CmsPage page = new CmsPage();
                page.setOutputPath(outPut);
                if (promotionId != null) {
                    page.setPageId(promotionId);
                }
                if (siteId != null)
                    page.setSiteId(siteId);
                count = this.cmsPageService.byPage(page);
            }
            if (outPutType == 2) {//广告位
                CmsAdcolumn cmsAdcolumn = new CmsAdcolumn();
                cmsAdcolumn.setOutput(outPut);
                if (promotionId != null) {
                    cmsAdcolumn.setAdcolumnId(promotionId);
                }
                if (siteId != null)
                    cmsAdcolumn.setSiteId(siteId);
                count = cmsAdcolumnDataService.byAdvcolumn(cmsAdcolumn);
            }
            if (outPutType == 3) { //活动
                CmsPromotionTask cmspt = new CmsPromotionTask();
                cmspt.setOutput(outPut);
                if (promotionId != null) {
                    cmspt.setId(promotionId);
                }
                if (siteId != null)
                    cmspt.setSiteId(siteId);
                count = cmsPromotionService.byTask(cmspt);
            }
            if (outPutType == 4) {//资讯
                CmsInformation cmsInformation = new CmsInformation();
                cmsInformation.setContent(outPut);
                if (promotionId != null) {
                    cmsInformation.setInforId(promotionId);
                }
                if (siteId != null)
                    cmsInformation.setSiteId(siteId);
                count = cmsInformationService.byinfor(cmsInformation);
            }

            HttpServletResponse response = ServletActionContext.getResponse();
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().print(count);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("CmsPromotionAction.outPutValidate活动outPutValidate异常：" + e.getMessage(), e);
        }
    }

    //根据促销类型加载活动（参数1：促销类型;参数2：页数）
    public void queryPromotionByType() {

        try {
            CmsPromotionTask cmspt = new CmsPromotionTask();
            Page page = new Page();
            //每页为3条，方便测试，去掉
            //	page.setPageSize(3);
            cmspt.setPromotionTypeId(promotionTypeId);
            page.setPageNo(pageNo);
            String json = "";
            page = cmsPromotionService.queryForPage(cmspt, page);
            //json.put("page", page);
            AjaxUtil.writeJSONToResponseByObject(page, true, jsoncallback);
        } catch (Exception e) {
            logger.error("CmsPromotionAction.queryPromotionByType根据促销类型加载活动异常：" + e.getMessage(), e);
        }

    }

    //加载过期的活动
    public void queryExpirePromotion() {
        //	HttpServletResponse response = ServletActionContext.getResponse();
        try {

            String json = "";
            List list = cmsPromotionService.queryExpirePromotion();
            ReturnResult returnResult = new ReturnResult("200", "成功", list);
            //json.put("page", page);
            AjaxUtil.writeJSONToResponseByObject(returnResult, true, jsoncallback);
        } catch (Exception e) {
            logger.error("CmsPromotionAction.queryExpirePromotion加载过期的活动异常：" + e.getMessage(), e);
        }

    }


    public CmsPromotionTask getCmspt() {
        return cmspt;
    }

    public void setCmspt(CmsPromotionTask cmspt) {
        this.cmspt = cmspt;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public List<PromotionType> getListPromotionType() {
        return listPromotionType;
    }

    public void setListPromotionType(List<PromotionType> listPromotionType) {
        this.listPromotionType = listPromotionType;
    }

    public ViewPromotion getViewPromotion() {
        return viewPromotion;
    }

    public void setViewPromotion(ViewPromotion viewPromotion) {
        this.viewPromotion = viewPromotion;
    }

    public String getAjax() {
        return ajax;
    }


    public void setAjax(String ajax) {
        this.ajax = ajax;
    }


    public Integer getPromotionId() {
        return promotionId;
    }


    public void setPromotionId(Integer promotionId) {
        this.promotionId = promotionId;
    }

    public HtmlBuilder getHtmlBuilder() {
        return htmlBuilder;
    }

    public void setHtmlBuilder(HtmlBuilder htmlBuilder) {
        this.htmlBuilder = htmlBuilder;
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

    public Integer getOutPutType() {
        return outPutType;
    }

    public void setOutPutType(Integer outPutType) {
        this.outPutType = outPutType;
    }

    public String getOutPut() {
        return outPut;
    }

    public void setOutPut(String outPut) {
        this.outPut = outPut;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public PromotionPublish getPromotionPublish() {
        return promotionPublish;
    }

    public void setPromotionPublish(PromotionPublish promotionPublish) {
        this.promotionPublish = promotionPublish;
    }

    public Map getCategoryMap() {
        return categoryMap;
    }

    public void setCategoryMap(Map categoryMap) {
        this.categoryMap = categoryMap;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public String getJsoncallback() {
        return jsoncallback;
    }

    public void setJsoncallback(String jsoncallback) {
        this.jsoncallback = jsoncallback;
    }

    public CmsSiteDataService getCmsSiteDataService() {
        return cmsSiteDataService;
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

    public String getCheckeds() {
        return checkeds;
    }

    public void setCheckeds(String checkeds) {
        this.checkeds = checkeds;
    }

    public CmsSiteService getCmsSiteService() {
        return cmsSiteService;
    }

    public void setCmsSiteService(CmsSiteService cmsSiteService) {
        this.cmsSiteService = cmsSiteService;
    }

    public Integer getOperateType() {
        return operateType;
    }

    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }

//	public String getChannel() {
//		return channel;
//	}

//	public void setChannel(String channel) {
//		this.channel = channel;
//	}

//    public String getChannelQuery() {
//        return channelQuery;
//    }
//
//    public void setChannelQuery(String channelQuery) {
//        this.channelQuery = channelQuery;
//    }


}
