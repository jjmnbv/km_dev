package com.kmzyc.supplier.dao;

import java.sql.SQLException;
import java.util.List;

import com.km.framework.page.Pagination;
import com.pltfm.app.vobject.ProductImageDraftExample;
import com.pltfm.app.vobject.ProductImage;
import com.pltfm.app.vobject.ProductSkuAttrDraft;
import com.pltfm.app.vobject.ProductSkuAttrExample;
import com.pltfm.app.vobject.ProductSkuDraft;
import com.pltfm.app.vobject.ProductSkuDraftExample;
import com.pltfm.app.vobject.ProductSkuExample;

public interface ProductDraftSkuDAO {

    /**
     * 分页查找发布产品SKU列表
     *
     * @param page
     * @return
     * @throws SQLException
     */
    Pagination findProductSkuListByPage(Pagination page) throws SQLException;

    List selectByExample(ProductSkuExample example) throws SQLException;

    List<ProductSkuAttrDraft> selectByExample(ProductSkuAttrExample example) throws SQLException;

    ProductSkuDraft findSingleSkuAndAttrValue(Long productSkuId) throws SQLException;

    List selectByExample(ProductImageDraftExample example) throws SQLException;

    List<ProductImage> selectByExampleForImages(ProductImageDraftExample exa) throws SQLException;

    List selectByExample(ProductSkuDraftExample example) throws SQLException;

    List<ProductSkuAttrDraft> findSkuNewAttr(Long productId) throws SQLException;
}