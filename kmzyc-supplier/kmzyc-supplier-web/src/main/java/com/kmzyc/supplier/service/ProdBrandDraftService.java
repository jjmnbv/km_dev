package com.kmzyc.supplier.service;

import com.kmzyc.commons.exception.ServiceException;
import com.km.framework.page.Pagination;
import com.pltfm.app.vobject.ProdBrandDraft;

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
     * @param prodBrandDraft    查询条件
     * @param page              分页数据（页尺寸、页码）
     * @return                  分页数据
     */
    Pagination showProdBrandDraftList(ProdBrandDraft prodBrandDraft, Pagination page) throws ServiceException;

    /**
     * 根据品牌草稿id和供应商对应的shopCode查询品牌草稿信息
     *
     * @param prodBrandDraft    品牌草稿id和shopCode
     * @return                  品牌草稿信息
     */
    ProdBrandDraft getProdBrandDraft(ProdBrandDraft prodBrandDraft) throws ServiceException;

    /**
     * 保存品牌草稿
     *
     * @param prodBrandDraft    品牌草稿信息
     * @return                  品牌id
     */
    Long saveProdBrandDraft(ProdBrandDraft prodBrandDraft) throws ServiceException;

    /**
     * 修改品牌草稿
     *
     * @param prodBrandDraft    品牌草稿信息
     * @param preLogoPath       上一次logo图片路径
     * @return                  修改条数
     */
    int updateProdBrandDraft(ProdBrandDraft prodBrandDraft, String preLogoPath) throws ServiceException;

    /**
     * 通过品牌id和品牌所在的商品code删除品牌草稿
     *
     * @param prodBrandDraft    品牌草稿信息
     * @return                  修改条数
     */
    int deleteProdBrandDraft(ProdBrandDraft prodBrandDraft) throws ServiceException;

    /**
     * 检查品牌名称在品牌正式库中是否存在
     *
     * @param brandName 品牌名称
     * @return  存在true，不存在false
     * @throws ServiceException
     */
    boolean isExistsBrandName(String brandName) throws ServiceException;
}
