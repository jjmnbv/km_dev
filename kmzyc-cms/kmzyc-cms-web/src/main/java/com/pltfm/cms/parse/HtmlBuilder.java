package com.pltfm.cms.parse;

import com.pltfm.app.vobject.ViewPromotion;
import com.pltfm.cms.vobject.CmsPage;
import com.pltfm.cms.vobject.CmsPromotionTask;

import java.sql.SQLException;
import java.util.Map;

/**
 * 模块网页生成接口
 *
 * @author river
 */
public interface HtmlBuilder {

    /**
     * 将FREEMARKER模板解析生成网页
     *
     * @param template FREEMARKER模板内容
     * @return HTML
     */
    public String buildHtml(CmsPage page) throws Exception;

    public String getHtml(String path, Map<String, Object> data) throws Exception;

    public String promotionParse(ViewPromotion promotion, CmsPromotionTask cmsPromotionTask) throws Exception;
}