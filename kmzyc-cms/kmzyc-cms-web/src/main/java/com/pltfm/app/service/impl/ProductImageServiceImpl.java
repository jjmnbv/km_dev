package com.pltfm.app.service.impl;

import com.pltfm.app.dao.ProductImageDAO;
import com.pltfm.app.service.ProductImageService;
import com.pltfm.app.vobject.ProductImage;
import com.pltfm.app.vobject.ProductImageExample;

import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

/**
 * 产品图片业务逻辑实现类
 *
 * @author cjm
 * @since 2013-9-22
 */
@Component(value = "productImageService")
public class ProductImageServiceImpl implements ProductImageService {
    /**
     * 产品图片DAO接口
     */
    @Resource(name = "productImageDAO")
    private ProductImageDAO productImageDAO;

    /**
     * 根据产品图片条件查询产品数据
     *
     * @param example 产品图片条件类
     * @throws Exception sql异常
     * @return 返回值
     */
    @Override
    public List<ProductImage> selectBySkuId(Integer skuId) throws Exception {
        ProductImageExample example = new ProductImageExample();
        example.createCriteria().andSkuIdEqualTo(skuId);
        return productImageDAO.selectByExample(example);
    }

    /**
     * 根据产品图片主键查询产品数据
     *
     * @param imageId 产品图片主键
     * @throws SQLException sql异常
     * @return 返回值
     */
    @Override
    public ProductImage selectByPrimaryKey(Integer imageId) throws Exception {
        return productImageDAO.selectByPrimaryKey(imageId);
    }

    public ProductImageDAO getProductImageDAO() {
        return productImageDAO;
    }

    public void setProductImageDAO(ProductImageDAO productImageDAO) {
        this.productImageDAO = productImageDAO;
    }

}
