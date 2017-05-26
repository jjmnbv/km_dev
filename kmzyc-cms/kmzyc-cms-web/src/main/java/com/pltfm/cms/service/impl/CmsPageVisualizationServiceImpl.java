package com.pltfm.cms.service.impl;

import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.util.DateTimeUtils;
import com.kmzyc.commons.page.Page;
import com.pltfm.cms.dao.CmsPageVisualizationDAO;
import com.pltfm.cms.service.CmsPageVisualizationService;
import com.pltfm.cms.util.FileOperateUtils;
import com.pltfm.cms.vobject.CmsPageVisualization;
import com.pltfm.cms.vobject.UploadFile;

import org.springframework.stereotype.Component;

import java.util.List;

import javax.annotation.Resource;

/**
 * 页面可视化窗口数据绑定业务逻辑实现
 *
 * @author cjm
 * @since 2014-8-21
 */
@Component(value = "cmsPageVisualizationService")
public class CmsPageVisualizationServiceImpl implements CmsPageVisualizationService {
    /**
     * 页面可视化窗口数据绑定DAO
     */
    @Resource(name = "cmsPageVisualizationDAO")
    private CmsPageVisualizationDAO cmsPageVisualizationDAO;

    /**
     * 读取属性文件内容
     */
    /*@Resource(name = "templateConfig")
	private Map<String, String> templateConfig;*/
    @Override
    public Page queryForPage(CmsPageVisualization cmsPageVisualization,
                             Page page) throws Exception {
        if (page == null) {
            page = new Page();
        }
        if (cmsPageVisualization == null) {
            cmsPageVisualization = new CmsPageVisualization();
        }
        // 根据条件获取窗口数据信息总条数
        int totalNum = cmsPageVisualizationDAO.countByCmsPageVisualization(cmsPageVisualization);
        if (totalNum != 0) {
            page.setRecordCount(totalNum);
            // 设置查询开始结束索引
            cmsPageVisualization.setStartIndex(page.getStartIndex());
            cmsPageVisualization
                    .setEndIndex(page.getStartIndex() + page.getPageSize());
            page.setDataList(cmsPageVisualizationDAO.queryForPage(cmsPageVisualization));
        } else {
            page.setRecordCount(0);
            page.setDataList(null);
        }
        return page;
    }

    public CmsPageVisualizationDAO getCmsPageVisualizationDAO() {
        return cmsPageVisualizationDAO;
    }

    public void setCmsPageVisualizationDAO(
            CmsPageVisualizationDAO cmsPageVisualizationDAO) {
        this.cmsPageVisualizationDAO = cmsPageVisualizationDAO;
    }

    /**
     * 添加页面可视化窗口数据
     *
     * @param cmsPageVisualizations 页面可视化窗口数据集合
     * @param uploadFile            图片集合
     * @param pageId                页面Id
     * @param created               创建人Id
     * @param siteId                站点Id
     */
    @Override
    public void addCmsPageVisualization(
            List<CmsPageVisualization> cmsPageVisualizations,
            List<UploadFile> uploadFile, Integer stylesId, Integer created,
            Integer siteId) throws Exception {
        String imagePath = ConfigurationUtil.getString("imagePut");
        int num = 0;
        UploadFile file = null;
        for (int i = 1; i < cmsPageVisualizations.size(); i++) {
            CmsPageVisualization cmsPageVisualization = cmsPageVisualizations.get(i);
            if (uploadFile.get(num) != null) {
                file = uploadFile.get(num);
                if (file != null) {
                    String imgPath = FileOperateUtils.upload(file,
                            imagePath);
                    if (imgPath != null) {
                        cmsPageVisualization.setPic(imgPath);
                    }
                }
            }
            cmsPageVisualization.setCreated(created);
            cmsPageVisualization.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
            cmsPageVisualization.setSiteId(siteId);
            cmsPageVisualization.setStylesId(stylesId);
            num++;
            cmsPageVisualizationDAO.insert(cmsPageVisualization);
        }
    }

    /**
     * 删除可视化窗口数据
     */
    @Override
    public void delCmsPageVisualization(Integer visualizationId)
            throws Exception {
        cmsPageVisualizationDAO.deleteByPrimaryKey(visualizationId);
    }

    /**
     * 根据风格Id查询窗口
     */
    @Override
    public List<CmsPageVisualization> queryBystylesId(Integer stylesId)
            throws Exception {
        return cmsPageVisualizationDAO.queryByStylesId(stylesId);
    }
	
	
	
	/*public Map<String, String> getTemplateConfig() {
		return templateConfig;
	}
	public void setTemplateConfig(Map<String, String> templateConfig) {
		this.templateConfig = templateConfig;
	}
	*/

}
