package com.pltfm.app.service;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.vobject.ProdBrand;
import com.pltfm.app.vobject.ProdBrandExample;

import java.util.List;

/**
 * 品牌信息业务逻辑层接口
 *
 * @author cjm
 * @since 2013-9-10
 */
public interface ProdBrandService {
    /**
     * 分页查询品牌信息
     *
     * @param prodBrand 品牌信息实体
     * @param page      分页实体
     * @throws Exception 异常
     * @return 返回值
     */
    Page queryForPage(ProdBrand prodBrand, Page page) throws Exception;

    /**
     * 根据活动信息主键查询单条活动信息
     *
     * @param brandId 活动信息主键
     * @throws SException sql异常
     * @return 返回值
     */
    ProdBrand selectByPrimaryKey(Integer brandId) throws Exception;

    /**
     * 根据品牌主键集合查询品牌信息
     *
     * @param dataIds 品牌主键集合
     * @throws Exception sql异常
     * @return 返回值
     */
    List selectByDataIds(List dataIds) throws Exception;

    /**
     * 根据品牌条件信息查询品牌信息
     *
     * @param example 品牌条件信息
     * @throws Exception sql异常
     * @return 返回值
     */
    List selectByExample(ProdBrandExample example) throws Exception;


    /**
     * 通过类目主键与渠道查询同类品牌信息
     *
     * @param categoryId 类目主键
     * @param channel    渠道
     * @throws Exception 异常信息
     */
    public List<ProdBrand> selectByCategoryIdAndChannel(ProdBrand prodBrand) throws Exception;
}
