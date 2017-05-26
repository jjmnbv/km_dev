package com.pltfm.cms.parse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.opensymphony.xwork2.ActionSupport;
import com.pltfm.app.vobject.ViewPromotion;
import com.pltfm.cms.service.CmsPromotionService;
import com.pltfm.cms.util.FileOperateUtils;
import com.pltfm.cms.vobject.CmsPromotionTask;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

/**
 * 页面发布
 *
 * @author zhl
 * @since 2013-09-12
 */
@SuppressWarnings("serial")
@Component(value = "promotionPublish")
@Scope(value = "prototype")
public class PromotionPublish extends ActionSupport {
	private static Logger logger = LoggerFactory.getLogger(PromotionPublish.class);
    @Resource(name = "cmsPromotionService")
    private CmsPromotionService cmsPromotionService;
    @Resource(name = "htmlBuilder")
    private HtmlBuilder htmlBuilder;
    private CmsPromotionTask cmspt;

    public String promotionPublish(Integer promotionId, Integer siteId) {
        ViewPromotion promotion = null;
        CmsPromotionTask cmsPromotionTask = null;
        try {
            cmsPromotionTask = this.cmsPromotionService.cmspAjaxbyId(promotionId, siteId);
            promotion = cmsPromotionService.byId(promotionId);
            if (promotion == null) {
                return "notFound";
            }
            /*Integer siteId=null;
            if(cmsPromotionTask!=null&&cmsPromotionTask.getSiteId()!=null){
				siteId=cmsPromotionTask.getSiteId();
			}*/
            //获取模板路径
            promotion.setPath(PathConstants.promotion(siteId) + "/" + promotion.getPromotionId() + ".html");
            //获取内容
            String html = htmlBuilder.promotionParse(promotion, cmsPromotionTask);
            if (html == null || html.equals(""))//发布失败
            {
                return "error";
            } else {
                //组装发布路径
                String outPath = null;
                if (!cmsPromotionTask.getOutput().contains("/")) {
                    outPath = PathConstants.publishPath(siteId) + "/" + cmsPromotionTask.getOutput();
                } else {
                    outPath = PathConstants.publishPath(siteId) + "/" + cmsPromotionTask.getOutput().substring(0, cmsPromotionTask.getOutput().lastIndexOf("/"));
                    //判断输入文件夹是否存在
                    FileOperateUtils.checkAndCreateDirs(outPath);
                    //完整的输出路径
                    outPath = outPath + cmsPromotionTask.getOutput().substring(cmsPromotionTask.getOutput().lastIndexOf("/"));
                }
                //写入文件
                FileOperateUtils.writer(outPath, html);
                cmsPromotionTask.setStatus(1);
                this.cmsPromotionService.promotionUpdate(cmsPromotionTask);
                return "success";
            }
        } catch (Exception e) {
        	logger.error("PromotionPublish.promotionPublish异常：" + e.getMessage(), e);
            return "error";
        }

    }

    /**
     * 定时发布
     */
    public String publish(Integer siteId) {
        int suc = 0;
        try {
            System.out.println("--------------------- 活动扫描！---------------------");
            cmspt = cmsPromotionService.publishObj();
            if (cmspt != null) {
                suc = cmsPromotionService.publish(cmspt.getId(), siteId);
                if (suc == 2) {
                    System.out.println("--------------------- 此活动已取消！---------------------");
                }
                if (suc == 0) {
                    System.out.println("--------------------- 发布失败！---------------------");
                } else {
                    cmspt.setStatus(1);
                    int promotionUpdate = cmsPromotionService.promotionUpdate(cmspt);
                    if (promotionUpdate == 1) {
                        System.out.println("--------------------- 发布成功！---------------------");
                    } else {
                        System.out.println("--------------------- 发布失败！---------------------");
                    }
                }
            } else {
                System.out.println("--------------------- 当前无活动需发布！---------------------");
            }
        } catch (Exception e) {
        	logger.error("PromotionPublish.publish异常：" + e.getMessage(), e);
        }
        return null;
    }

    public String close(Integer siteId) {
        try {
            int re = cmsPromotionService.close(siteId);
            if (re == 1) {
                System.out.println("------------------活动关闭成功------------------");
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        return null;
    }


    public void promotion(Integer siteId) {
        publish(siteId);
        close(siteId);
    }

    public HtmlBuilder getHtmlBuilder() {
        return htmlBuilder;
    }

    public void setHtmlBuilder(HtmlBuilder htmlBuilder) {
        this.htmlBuilder = htmlBuilder;
    }

}
