package com.pltfm.cms.service;

import com.kmzyc.commons.page.Page;
import com.pltfm.cms.vobject.CmsSiteData;

import java.util.List;

public interface CmsSiteDataService {

    /**
     * 根据用户id与站点id查询站点数据
     *
     * @param userId 用户id
     * @param siteId 站点id
     * @return 站点数据集合
     * @throws Exception 异常
     */
    public List queryByUserIdAndSiteId(Integer userId, Integer siteId) throws Exception;

    /**
     * 分页查询站点与数据信息
     *
     * @param cmsUserSite 站点与数据信息实体
     * @param page        分页实体
     * @throws Exception 异常
     * @return 返回值
     */
    Page queryForPage(CmsSiteData cmsSiteData, Page page) throws Exception;

    /**
     * 根据用户id、站点id和数据类型查询站点数据
     *
     * @param userId   用户id
     * @param siteId   站点id
     * @param dataType 数据类型
     * @return 站点数据集合
     * @throws Exception 异常
     */
    public List<CmsSiteData> queryByUserIdAndSiteIdAndDataType(Integer userId, Integer siteId, Integer dataType) throws Exception;

    /**
     * 添加站点数据信息
     *
     * @param dataIds  数据信息集合
     * @param dataType 数据类型集合
     * @param userId   用户Id
     * @param siteId   站点Id
     * @param created  当前用户Id
     * @throws Exception sql异常
     * @return 无返回值
     */
    public void addCmsSiteData(List<Integer> dataIds, Integer dataType, Integer userId, Integer siteId, Integer created) throws Exception;

    /**
     * 删除站点数据
     *
     * @return 返回值
     * @throws Exception sql异常
     */
    public Integer delCmsSiteData(List<Integer> userSiteDataIds) throws Exception;

    List listToString(Integer userId, Integer dataType) throws Exception;

    void addCSD(Integer dataId, Integer dataType, Integer userId, Integer siteId, Integer created) throws Exception;

}
