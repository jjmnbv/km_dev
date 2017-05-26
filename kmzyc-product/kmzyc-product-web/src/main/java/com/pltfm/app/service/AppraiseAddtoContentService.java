package com.pltfm.app.service;


import java.util.List;

import com.pltfm.app.vobject.AppraiseAddtoContent;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;

public interface AppraiseAddtoContentService {

    /**
     * 获取分页数据
     *
     * @param page
     * @param content
     * @return
     * @throws ServiceException
     */
    void searchPage(Page page, AppraiseAddtoContent content) throws ServiceException;

    /**
     * 获取某个评价的追加信息
     *
     * @param appraiseId
     * @return
     * @throws ServiceException
     */
    List<AppraiseAddtoContent> findByAppId(Long appraiseId) throws ServiceException;

    /**
     * 删除
     *
     * @param addContentIds
     * @throws ServiceException
     */
    void deleteBatch(Long[] addContentIds) throws ServiceException;

    /**
     * 审核通过
     *
     * @param addContentIds
     * @throws ServiceException
     */
    void updatePass(Long[] addContentIds) throws ServiceException;

    /**
     * 审核不通过
     *
     * @param addContentIds
     * @throws ServiceException
     */
    void updateUnPass(Long[] addContentIds) throws ServiceException;

    /**
     * 添加追加评价
     *
     * @param content
     * @throws ServiceException
     */
    boolean saveAddtoContent(AppraiseAddtoContent content) throws ServiceException;

}