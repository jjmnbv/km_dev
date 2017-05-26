package com.pltfm.cms.service;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.vobject.ViewPromotion;
import com.pltfm.cms.vobject.CmsPromotionTask;
import com.pltfm.cms.vobject.UploadFile;

import java.sql.SQLException;
import java.util.List;

public interface CmsPromotionService {


    /**
     * 分页查询活动数据信息
     *
     * @param CmsPromotionTask 活动数据信息实体
     * @throws Exception 异常
     * @return 返回值
     */
    public Page queryForPage(CmsPromotionTask cmspt, Page page) throws Exception;

    /**
     * 根据活动id和站点id查询单条活动信息
     */
    public CmsPromotionTask cmspByIdAndSiteId(Integer id, Integer siteId) throws Exception;


    /**
     * 添加活动数据信息
     *
     * @param CmsPromotionTask 活动数据信息实体
     * @throws Exception 异常
     * @return 返回值
     */
    public int promotionAdd(UploadFile file, UploadFile file2, UploadFile file3, UploadFile file4, CmsPromotionTask cmspt) throws Exception;


    /**
     * 根据ID查活动
     *
     * @param CmsPromotionTask 活动数据信息实体
     * @throws Exception 异常
     * @return 返回值
     */
    public CmsPromotionTask cmspbyId(Integer id, Integer siteId) throws Exception;

    /**
     * 根据ID查活动 ajax使用
     *
     * @param CmsPromotionTask 活动数据信息实体
     * @throws Exception 异常
     * @return 返回值
     */
    public CmsPromotionTask cmspAjaxbyId(Integer id, Integer siteId) throws Exception;

    /**
     * 根据活动ID 和站点ID查活动 ajax使用
     *
     * @param CmsPromotionTask 活动数据信息实体
     * @throws Exception 异常
     * @return 返回值
     */
    public CmsPromotionTask cmspById(CmsPromotionTask CMSP) throws Exception;


    /**
     * 根据ID删除
     *
     * @param CmsPromotionTask CMS表活动数据信息实体
     * @throws Exception 异常
     * @return 返回值
     */
    public int promotionDelete(Integer id, Integer siteId) throws Exception;

    /**
     * 修改活动数据
     *
     * @param CmsPromotionTask CMS表活动数据信息实体
     * @throws Exception 异常
     * @return 返回值
     */
    public int promotionUpdate(UploadFile file, UploadFile file2, UploadFile file3, UploadFile file4, CmsPromotionTask cmspt) throws Exception;

    /**
     * 修改活动数据
     *
     * @param CmsPromotionTask CMS表活动数据信息实体
     * @throws Exception 异常
     * @return 返回值
     */
    public int promotionUpdate(CmsPromotionTask cmspt) throws Exception;

    /**
     * output相同的条数
     *
     * @throws SQLException sql异常
     * @return 返回值
     */
    public int byTask(CmsPromotionTask cmspt) throws Exception;


    /**
     * 查当前时间小于等于系统时间的活动对象
     *
     * @throws SQLException sql异常
     * @return 返回值
     */
    public CmsPromotionTask publishObj() throws Exception;


    /**
     * 发布
     *
     * @throws SQLException sql异常
     * @return 返回值
     */
    public int publish(Integer id, Integer siteId) throws Exception;

    /**
     * 发布
     *
     * @throws SQLException sql异常
     * @return 返回值
     */
    public int close(Integer siteId) throws Exception;


    public CmsPromotionTask queryPromo(Integer id, Integer siteId) throws Exception;


    /**
     * 根据ID
     *
     * @param CmsPromotionTask 产品表活动数据信息实体
     * @throws Exception 异常
     * @return 返回值
     */
    public ViewPromotion byId(int id) throws Exception;


    public List queryExpirePromotion() throws Exception;

    /**
     * 获取所有的活动
     */
    public List getAllPromotionTask(CmsPromotionTask promotion) throws Exception;

}
