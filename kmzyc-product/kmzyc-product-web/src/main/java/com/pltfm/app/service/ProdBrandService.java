package com.pltfm.app.service;


import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.vobject.ProdBrand;
import com.kmzyc.commons.page.Page;

import java.util.List;

/**
 * 产品品牌业务接口
 *
 * @author tanyunxing
 */
public interface ProdBrandService {

    /**
     * 分页 查找数据
     *
     * @param page
     * @param prod
     */
    void searchPage(Page page, ProdBrand prod) throws ServiceException;

    /**
     * 根据主键删除品牌
     *
     * @param brandId
     * @return
     * @throws ServiceException
     */
    void deleteByPrimaryKey(String[] brandId) throws ServiceException;

    /**
     * 添加产品品牌
     *
     * @param record
     * @throws ServiceException
     */
    Long addProdBrand(ProdBrand record) throws ServiceException;

    /**
     * 根据编号查找编号
     *
     * @param id
     * @throws ServiceException
     */
    ProdBrand findProdBrandById(Long id) throws ServiceException;

    /**
     * 更新产品
     *
     * @param prod
     * @throws ServiceException
     */
    void updateProdBrand(ProdBrand prod) throws ServiceException;

    /**
     * 获取所有有效的品牌
     *
     * @return
     * @throws ServiceException
     */
    List<ProdBrand> findAllValidBrand() throws ServiceException;

    /**
     * 查询是否有重复的品牌名
     *
     * @param name
     * @return >0：有；=0：无
     * @throws ServiceException
     */
    int findRepeatName(String name) throws ServiceException;

    /**
     * 查询更新时是否有重复的品牌名
     *
     * @param brandId
     * @param name
     * @return
     * @throws ServiceException
     */
    int findUpdateRepeatName(Long brandId, String name) throws ServiceException;

    /**
     * 检查品牌是否来自供应商
     *
     * @param brandId 品牌id
     * @return
     * @throws ServiceException
     */
    boolean checkProdBrandIsFromSupplier(Long brandId) throws ServiceException;

    /**
     * 修改品牌草稿数据
     *
     * @param prodBrand 品牌
     * @return
     * @throws ServiceException
     */
    int updateProdBrandDraft(ProdBrand prodBrand) throws ServiceException;
}
