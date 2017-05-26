package com.kmzyc.promotion.app.service;

import java.util.List;

import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.vobject.ProdBrand;

/**
 * 产品品牌业务接口
 * 
 * @author tanyunxing
 * 
 */
public interface ProdBrandService {

    /**
     * 分页 查找数据
     * 
     * @param page
     * @param prod
     */
    public void searchPage(Page page, ProdBrand prod) throws Exception;

    /**
     * 根据主键删除品牌
     * 
     * @param brandId
     * @return
     * @throws Exception
     */
    public void deleteByPrimaryKey(String[] brandId) throws Exception;

    /**
     * 添加产品品牌
     * 
     * @param prod
     * @throws Exception
     */
    public Long addProdBrand(ProdBrand record) throws Exception;

    /**
     * 根据编号查找编号
     * 
     * @param id
     * @throws Exception
     */
    public ProdBrand findProdBrandById(Long id) throws Exception;

    /**
     * 更新产品
     * 
     * @param prod
     * @throws Exception
     */
    public void updateProdBrand(ProdBrand prod) throws Exception;

    /**
     * 获取所有有效的品牌
     * 
     * @return
     * @throws Exception
     */
    public List<ProdBrand> findAllValidBrand() throws Exception;

    /**
     * 查询是否有重复的品牌名
     * 
     * @param name
     * @return >0：有；=0：无
     * @throws Exception
     */
    public int findRepeatName(String name) throws Exception;

    /**
     * 查询更新时是否有重复的品牌名
     * 
     * @param brandId
     * @param name
     * @return
     * @throws Exception
     */
    public int findUpdateRepeatName(Long brandId, String name) throws Exception;

}
