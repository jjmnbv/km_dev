package com.pltfm.cms.parse;

//import com.kmzyc.zkconfig.ConfigurationUtil;

import com.kmzyc.zkconfig.ConfigurationUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.pltfm.app.util.ListUtils;
import com.pltfm.cms.service.CmsAdcolumnDataService;
import com.pltfm.cms.service.CmsAdvService;
import com.pltfm.cms.service.CmsSiteService;
import com.pltfm.cms.util.FileOperateUtils;
import com.pltfm.cms.util.HtmlCompressor;
import com.pltfm.cms.vobject.CmsAdcolumn;
import com.pltfm.cms.vobject.CmsAdv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

//广告发布
@Component(value = "advPublish")
public class AdvPublish extends ActionSupport {
    private static final long serialVersionUID = 4227442622537948239L;
    @Resource(name = "htmlBuilder")
    HtmlBuilder htmlBuilder;
    // 广告对象
    @Resource(name = "cmsAdvService")
    CmsAdvService cmsAdvService;
    // 广告位对象
    @Resource(name = "cmsAdcolumnDataService")
    CmsAdcolumnDataService cmsAdcolumnDataService;

    List cmsAdcolumnList;

    CmsAdcolumn adcolumn;
    CmsAdv cmsAdv;
    /*@Resource(name="templateConfig")
    private HashMap pathConfig;*/

    @Resource(name = "promotionPublish")
    private PromotionPublish promotionPublish;


    @Resource(name = "cmsSiteService")
    private CmsSiteService cmsSiteService;
    private static Logger logger = LoggerFactory.getLogger(AdvPublish.class);

    // 调度器
    @SuppressWarnings("unchecked")
    public void work() {
        try {

            List<CmsAdv> list = cmsAdvService.checkAllAdv();
            if (ListUtils.isNotEmpty(list)) {
                for (CmsAdv cmsAdv : list) {
                    //	cmsAdvAction.advPublish(cmsAdv.getAdvId());
                    cmsAdv = cmsAdvService.byid(cmsAdv.getAdvId());
                    Integer siteId = cmsAdv.getSiteId();
                    Map<String, Object> result = new HashMap<String, Object>();
                    result.put(DataType.advert.name(), cmsAdv);
                    result.put(DataType.cssAndJsPath.name(),
                            PathConstants.getSitePath("cssAndJsPath", "mail", cmsAdv.getSiteId()));
                    adcolumn = cmsAdcolumnDataService.byid(cmsAdv.getAdcolumnId());

                    // 选择对应模版

                    String path = PathConstants.advTempPath(cmsAdv.getSiteId()) + "/" +
                            cmsAdv.getAdvId() + ".html";
                    String content = htmlBuilder.getHtml(path, result);

                    if (!content.equals("")) {
                        //更新广告状态(已发布)
                        cmsAdv = cmsAdvService.findAdv(cmsAdv.getAdvId());
                        cmsAdv.setStatus(1);
                        cmsAdvService.update(null, null, null, null, null, null, cmsAdv);

                        // 广告位输出路径

                        String outPath =
                                PathConstants.publishPath(siteId) + "/" + adcolumn.getOutput();

                        // 判断pages文件夹是否存在，若不存在，则新建一个


                        String outPathFile = "";
                        if (adcolumn.getOutput() != null) {
                            String outPutPath = adcolumn.getOutput()
                                    .substring(0, adcolumn.getOutput().lastIndexOf("/"));
                            if (!outPutPath.equals("")) {
                                outPathFile = PathConstants.publishPath(siteId) + "/" + outPutPath;
                            } else {
                                outPathFile = PathConstants.publishPath(siteId);
                            }
                        }
                        // 判断pages文件夹是否存在，若不存在，则新建一个
                        FileOperateUtils.checkAndCreateDirs(outPathFile);
                        //写入广告位路径
                        if (outPath.endsWith("js")) {
                            content = java.net.URLEncoder.encode(content);
                            content = "document.write(decodeURIComponent(\"" + content +
                                    "\").replace(/\\+/g,' '));";
                            FileOperateUtils.writer(outPath,
                                    "true".equals(ConfigurationUtil.getString("isCompress")) ?
                                            HtmlCompressor.compress(content) : content);
                            //shtml格式
                        } else {
                            FileOperateUtils.writer(outPath,
                                    "true".equals(ConfigurationUtil.getString("isCompress")) ?
                                            HtmlCompressor.compress(content) : content);
                        }
                    }
                }
            }

            //活动发布

        } catch (Exception e) {


            LOG.error("调度报错：", e);
        }

    }

    public static void main(String[] args) {

        int i = 1;
        i = i++ + i++ + i++;
        System.out.println(i);
        int j = 1;
        j = ++j + ++j + ++j;
        System.out.println(j);
    }

    public HtmlBuilder getHtmlBuilder() {
        return htmlBuilder;
    }

    public void setHtmlBuilder(HtmlBuilder htmlBuilder) {
        this.htmlBuilder = htmlBuilder;
    }

    public CmsAdvService getCmsAdvService() {
        return cmsAdvService;
    }

    public void setCmsAdvService(CmsAdvService cmsAdvService) {
        this.cmsAdvService = cmsAdvService;
    }

    public CmsAdcolumnDataService getCmsAdcolumnDataService() {
        return cmsAdcolumnDataService;
    }

    public void setCmsAdcolumnDataService(CmsAdcolumnDataService cmsAdcolumnDataService) {
        this.cmsAdcolumnDataService = cmsAdcolumnDataService;
    }

    public List getCmsAdcolumnList() {
        return cmsAdcolumnList;
    }

    public void setCmsAdcolumnList(List cmsAdcolumnList) {
        this.cmsAdcolumnList = cmsAdcolumnList;
    }

    public CmsAdcolumn getAdcolumn() {
        return adcolumn;
    }

    public void setAdcolumn(CmsAdcolumn adcolumn) {
        this.adcolumn = adcolumn;
    }

	/*public HashMap getPathConfig() {
		return pathConfig;
	}

	public void setPathConfig(HashMap pathConfig) {
		this.pathConfig = pathConfig;
	}*/

    public CmsAdv getCmsAdv() {
        return cmsAdv;
    }

    public void setCmsAdv(CmsAdv cmsAdv) {
        this.cmsAdv = cmsAdv;
    }

    public PromotionPublish getPromotionPublish() {
        return promotionPublish;
    }

    public void setPromotionPublish(PromotionPublish promotionPublish) {
        this.promotionPublish = promotionPublish;
    }

    public CmsSiteService getCmsSiteService() {
        return cmsSiteService;
    }

    public void setCmsSiteService(CmsSiteService cmsSiteService) {
        this.cmsSiteService = cmsSiteService;
    }


}
