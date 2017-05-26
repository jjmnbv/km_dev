package com.pltfm.cms.service;

import com.kmzyc.commons.page.Page;
import com.pltfm.cms.vobject.CmsPageVisualization;
import com.pltfm.cms.vobject.UploadFile;

import java.util.List;

/**
 * 页面可视化窗口数据绑定业务逻辑接口
 *
 * @author cjm
 * @since 2014-8-21
 */
public interface CmsPageVisualizationService {
    /**
     * 分页查询页面可视化窗口数据信息
     *
     * @param cmsPageVisualization 页面可视化窗口数据实体
     * @param page                 分页实体
     * @throws Exception 异常
     * @return 返回值
     */
    Page queryForPage(CmsPageVisualization cmsPageVisualization, Page page) throws Exception;

    /**
     * 添加页面可视化窗口数据
     *
     * @param cmsPageVisualizations 页面可视化窗口数据集合
     * @param uploadFile            图片集合
     * @param pageId                页面Id
     * @param created               创建人Id
     * @param siteId                站点Id
     */
    void addCmsPageVisualization(List<CmsPageVisualization> cmsPageVisualizations, List<UploadFile> uploadFile,
                                 Integer stylesId, Integer created, Integer siteId) throws Exception;

    /**
     * 删除可视化窗口数据
     */
    void delCmsPageVisualization(Integer visualizationId) throws Exception;

    /**
     * 根据风格Id查询窗口
     */
    List<CmsPageVisualization> queryBystylesId(Integer stylesId) throws Exception;
}
