package com.pltfm.cms.service;

import com.kmzyc.commons.page.Page;
import com.pltfm.cms.vobject.CmsStyles;
import com.pltfm.cms.vobject.UploadFile;

/**
 * 风格业务逻辑接口
 *
 * @author cjm
 * @since 2014-8-22
 */
public interface CmsStylesService {
    /**
     * 分页查询风格数据信息
     *
     * @param cmsStyles 风格数据实体
     * @param page      分页实体
     * @throws Exception 异常
     * @return 返回值
     */
    Page queryForPage(CmsStyles cmsStyles, Page page) throws Exception;

    /**
     * 添加风格
     */
    void addStyles(CmsStyles cmsStyles, UploadFile fileImage) throws Exception;

    /**
     * 删除风格
     */
    void delStyles(Integer cmsStylesId) throws Exception;

    /**
     * 修改风格
     */
    void updateStyles(CmsStyles cmsStyles, UploadFile fileImage) throws Exception;

    /**
     * 根据Id单条数据
     */
    CmsStyles queryByCmsStylesId(Integer cmsStylesId) throws Exception;

    /**
     * 根据模板Id查询单条数据
     */
    CmsStyles selectByTemplateId(Integer templateId) throws Exception;
}
