package com.kmzyc.supplier.dao;

import java.sql.SQLException;
import java.util.List;

import com.km.framework.page.Pagination;
import com.kmzyc.supplier.vo.ProductMainVo;
import com.pltfm.app.vobject.Product;
import com.pltfm.app.vobject.ProductSku;

public interface ProductMainDao {

    /**
     * 分页查找发布产品列表
     *
     * @param page
     * @return
     * @throws SQLException
     */
    Pagination findProductMainListByPage(Pagination page) throws SQLException;

    /**
     * 分页查询未与店内分类相关联的产品列表 maliqun add
     *
     * @param page
     * @return
     * @throws SQLException
     */
    Pagination queryUnrelationWithShopCategoryByPage(Pagination page) throws SQLException;

    /**
     * 分页查询与店内分类相关联的产品列表 maliqun add
     *
     * @param page
     * @return
     * @throws SQLException
     */
    Pagination queryRelationWithShopCategoryByPage(Pagination page) throws SQLException;

    /**
     * 根据ID查询产品
     *
     * @param productId
     * @return
     * @throws Exception
     */
    ProductMainVo findProductMainVoByProductId(Long productId) throws SQLException;

    Product selectByPrimaryKey(Long productId) throws SQLException;

    int updateObject(Product product) throws SQLException;

    List<ProductSku> findSingleProductAndSkusAndAttrValues(Product product) throws SQLException;

    int updateBatch(List<Product> list) throws SQLException;

}
