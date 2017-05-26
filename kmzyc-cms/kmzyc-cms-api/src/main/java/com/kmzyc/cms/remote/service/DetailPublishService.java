package com.kmzyc.cms.remote.service;

import java.util.List;

/**
 * 产品详细页面发布远程接口
 * 
 * @author gongyan
 * @since 2013-10-08
 */
public interface DetailPublishService {
    /**
     * 产品详细信息发布
     * 
     * @param productList 产品主键集合
     * @return success 成功 fail 失败
     * @throws Exception 异常信息
     */
    public String detailPublish(List<Integer> productList) throws Exception;

    /**
     * 预览产品详细
     * 
     * @param productId 产品主键
     * @return 预览产品详细页面地址
     * @throws Exception 异常信息
     */
    public String viewDetailPublish(Integer productId) throws Exception;

    /**
     * 预览草稿状态产品信息
     * 
     * @param productId 产品主键
     * @return 预览产品详细页面地址
     * @throws Exception 异常信息
     */
    public String viewProductDraftPublish(Integer productId) throws Exception;

    /**
     * 根据产品列表搜索所有需重新发布的页面
     * 
     * @param productList 产品主键集合
     * @return
     * @throws Exception 异常信息
     */
    public String publishAllPage(List<Integer> productList) throws Exception;

    /**
     * 所有页面
     */
    public List<Integer> findAllPage();

    /**
     * 更新所有页面
     */
    public void remotePublishAllPage(String sqlParam, List<Integer> cmsPageList);



    /** 根据站点得到不同的类目 */
    @SuppressWarnings("rawtypes")
    public List queryCategoryBySite(String engName);

    public void remotePublishPartPage(List<Integer> productList);

    /**
     * 根据页面Id发布页面
     * 
     * @param pageId
     * @return 为true时这发布成功,为false时这发布失败
     */
    public boolean pageIdParse(Integer pageId);
    
    
    /**
     * 促销调用CMS预售详情页发布
     * @param productList 产品SKUID
     * @return 为true时这发布成功,为false时这发布失败
     */
    public boolean remotePublishYsPage(List<Long> productList);
    
    /**
     * 促销调用CMS普通详情页发布
     * @param productList 产品SKUID
     * @return 为true时这发布成功,为false时这发布失败
     */
    public boolean remotePublishNormalPage(List<Long> productList);
    
    
    
        
    
}
