package com.pltfm.cms.service;

import com.kmzyc.commons.page.Page;
import com.pltfm.cms.vobject.CmsPromotionAttr;
import com.pltfm.cms.vobject.UploadFile;

public interface CmsPromotionAttrService {

    /**
     * 添加活动图片数据信息
     *
     * @throws Exception 异常
     * @return 返回值
     */
    public Integer add(CmsPromotionAttr cmsp) throws Exception;

    /**
     * 分页查询活动图片数据信息
     *
     * @param cmsWindowData 活动图片数据信息实体
     * @throws Exception 异常
     * @return 返回值
     */
    public Page queryForPage(CmsPromotionAttr cmsp, Page page) throws Exception;


    public int addpromotion(UploadFile file, UploadFile file2, UploadFile file3, UploadFile file4, CmsPromotionAttr cmsp) throws Exception;

    public int updatePromotion(UploadFile file, UploadFile file2, UploadFile file3, UploadFile file4, CmsPromotionAttr cmsp) throws Exception;


    public CmsPromotionAttr byid(Integer id) throws Exception;

    public int delete(Integer id) throws Exception;

}
