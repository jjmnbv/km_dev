package com.pltfm.cms.action;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.zkconfig.ConfigurationUtil;
import com.kmzyc.commons.page.Page;
import com.pltfm.cms.service.CmsPromotionAttrService;
import com.pltfm.cms.vobject.CmsPromotionAttr;

@Component("cmsPromotionAttrAction")
@Scope("prototype")
public class CmsPromotionAttrAction extends UploadFileAction {
	private static Logger logger = LoggerFactory.getLogger(CmsPromotionAttrAction.class);
    @Resource(name = "cmsPromotionAttrService")
    CmsPromotionAttrService cmsPromotionAttrService;
    private Page page;
    private CmsPromotionAttr pro;
    private Integer proid;
    private Integer[] levelId;

    public String List() {
        try {
            page = cmsPromotionAttrService.queryForPage(pro, page);
            return "Success";
        } catch (Exception e) {
            logger.error("CmsPromotionAttrAction.List活动AttrList信息异常：" + e.getMessage(), e);
            return "Error";
        }
    }

    public String add() {

        buildFile();
        try {
            int s = cmsPromotionAttrService.addpromotion(file, file2, file3, file4, pro);
            pro = new CmsPromotionAttr();
            if (s == 0) {
                this.addActionMessage(ConfigurationUtil.getString("add.fail"));
                return List();

            }
        } catch (Exception e) {
            logger.error("CmsPromotionAttrAction.add活动Attr添加异常：" + e.getMessage(), e);
            this.addActionMessage(ConfigurationUtil.getString("add.fail"));
            return "Error";
        }
        this.addActionMessage(ConfigurationUtil.getString("add.success"));
        return List();
    }


    public String Byid() {
        try {

            pro = cmsPromotionAttrService.byid(proid);
        } catch (Exception e) {
            logger.error("CmsPromotionAttrAction.Byid活动AttrByid异常：" + e.getMessage(), e);
            return "Error";
        }
        return "ByidSuccess";
    }

    public String Update() {
        buildFile();
        try {
            cmsPromotionAttrService.updatePromotion(file, file2, file3, file4, pro);
            pro = new CmsPromotionAttr();
            this.addActionMessage(ConfigurationUtil.getString("update.success"));
        } catch (Exception e) {
            this.addActionMessage(ConfigurationUtil.getString("update.fail"));
            logger.error("CmsPromotionAttrAction.Update活动Attr修改异常：" + e.getMessage(), e);
            return List();
        }

        return List();
    }


    /**
     * 根据ID删除活动图片信息
     */
    public String Delete() {
        try {
            int s = cmsPromotionAttrService.delete(proid);
            if (s > 0) {
                this.addActionMessage(ConfigurationUtil.getString("delete.success"));
            } else {
                this.addActionMessage(ConfigurationUtil.getString("delete.fail"));
                return List();
            }
        } catch (Exception e) {
            logger.error("CmsPromotionAttrAction.Delete根据ID删除活动Attr图片信息异常：" + e.getMessage(), e);
            this.addActionMessage(ConfigurationUtil.getString("delete.fail"));
            return List();
        }
        return List();
    }


    /**
     * 根据ID数组删除广告位信息
     */
    public String All_Delete() {
        try {
            for (int i = 0; i < levelId.length; i++) {
                cmsPromotionAttrService.delete(levelId[i]);
            }
            this.addActionMessage(ConfigurationUtil.getString("delete.success"));
        } catch (Exception e) {
            this.addActionMessage(ConfigurationUtil.getString("delete.fail"));
            logger.error("CmsPromotionAttrAction.All_Delete根据ID数组删除活动Attr信息异常：" + e.getMessage(), e);
            return List();
        }
        return List();
    }


    public CmsPromotionAttrService getCmsPromotionAttrService() {
        return cmsPromotionAttrService;
    }

    public void setCmsPromotionAttrService(
            CmsPromotionAttrService cmsPromotionAttrService) {
        this.cmsPromotionAttrService = cmsPromotionAttrService;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public CmsPromotionAttr getPro() {
        return pro;
    }

    public void setPro(CmsPromotionAttr pro) {
        this.pro = pro;
    }



    public Integer getProid() {
        return proid;
    }

    public void setProid(Integer proid) {
        this.proid = proid;
    }

    public Integer[] getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer[] levelId) {
        this.levelId = levelId;
    }

}
