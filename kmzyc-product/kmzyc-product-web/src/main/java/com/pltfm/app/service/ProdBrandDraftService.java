package com.pltfm.app.service;

import com.pltfm.app.vobject.ProdBrandDraft;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;

import java.sql.SQLException;


/**
 * 功能：品牌草稿服务
 *
 * @author Zhoujiwei
 * @since 2015/11/19 16:04
 */
public interface ProdBrandDraftService {

    /**
     * 查询品牌草稿审核列表
     *
     * @param page           分页数据
     * @param prodBrandDraft 查询条件
     */
    void showProdBrandDraftList(Page page, ProdBrandDraft prodBrandDraft) throws ServiceException;

    /**
     * 根据品牌草稿id查询品牌草稿信息
     *
     * @param brandId 品牌草稿id
     * @return 品牌草稿信息
     */
    ProdBrandDraft getProdBrandDraftById(Long brandId) throws ServiceException;

    /**
     * 品牌审核不通过
     *
     * @param prodBrandDraft
     * @return
     * @throws SQLException
     */
    void refuseProdBrandDraft(ProdBrandDraft prodBrandDraft) throws ServiceException;

    /**
     * 品牌审核不通过
     *
     * @param record
     * @return
     * @throws SQLException
     */
    void passProdBrandDraft(ProdBrandDraft record) throws ServiceException;
}
