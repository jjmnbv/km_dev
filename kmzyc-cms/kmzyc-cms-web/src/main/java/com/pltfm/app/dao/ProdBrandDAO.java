package com.pltfm.app.dao;

import com.pltfm.app.vobject.ProdBrand;
import com.pltfm.app.vobject.ProdBrandExample;

import java.sql.SQLException;
import java.util.List;

/**
 * 品牌信息DAO接口
 *
 * @author cjm
 * @since 2013-9-10
 */
public interface ProdBrandDAO {


    /**
     * 根据品牌信息主键查询单条品牌信息
     *
     * @param brandId 品牌信息主键
     * @throws SQLException sql异常
     * @return 返回值
     */
    ProdBrand selectByPrimaryKey(Integer brandId) throws SQLException;

    /**
     * 根据品牌条件信息查询品牌信息
     *
     * @param brandId 品牌条件信息
     * @throws SQLException sql异常
     * @return 返回值
     */
    List selectByExample(ProdBrandExample example) throws SQLException;


    /**
     * 分页查询品牌信息视图
     *
     * @param prodBrand 品牌信息视图实体
     * @throws SQLException 异常
     * @return 返回值
     */
    public List queryForPage(ProdBrand prodBrand) throws SQLException;

    /**
     * 窗口数据信息总数量
     *
     * @param viewPromotion 窗口数据信息实体
     * @throws SQLException 异常
     * @return 返回值
     */
    public Integer countByProdBrand(ProdBrand prodBrand) throws SQLException;

    /**
     * 通过类目主键与渠道查询同类品牌信息
     *
     * @param categoryId 类目主键
     * @param channel    渠道
     * @throws Exception 异常信息
     */
    List<ProdBrand> selectByCategoryIdAndChannel(ProdBrand prodBrand) throws SQLException;
}