package com.pltfm.app.dao;

import com.pltfm.app.vobject.ProductImage;
import com.pltfm.app.vobject.ProductImageExample;

import java.sql.SQLException;
import java.util.List;

/**
 * 产品图片DAO接口
 *
 * @author cjm
 * @since 2013-9-22
 */
public interface ProductImageDAO {

    /**
     * 根据产品图片条件查询产品数据
     *
     * @param example 产品图片条件类
     * @throws SQLException sql异常
     * @return 返回值
     */
    List selectByExample(ProductImageExample example) throws SQLException;

    /**
     * 根据产品图片主键查询产品数据
     *
     * @param imageId 产品图片主键
     * @throws SQLException sql异常
     * @return 返回值
     */
    ProductImage selectByPrimaryKey(Integer imageId) throws SQLException;

}