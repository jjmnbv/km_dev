package com.kmzyc.supplier.dao;

import java.sql.SQLException;
import java.util.List;

import com.km.framework.page.Pagination;
import com.kmzyc.supplier.vo.ProductDraftVo;
import com.pltfm.app.vobject.ProductAttrDraft;
import com.pltfm.app.vobject.ProductAttrDraftExample;
import com.pltfm.app.vobject.ProductDraft;
import com.pltfm.app.vobject.ProductDraftExample;

public interface ProductDraftDao {

    /**
     * 分页查找余额明细
     *
     * @param page
     * @return
     * @throws SQLException
     */
    Pagination findProductMainListByPage(Pagination page) throws SQLException;

    List selectByExampleWithoutBLOBs(ProductDraftExample example) throws SQLException;

    ProductDraftVo findProductMainVoByProductId(Long productId) throws SQLException;

    List<ProductAttrDraft> selectByExample(ProductAttrDraftExample exa) throws SQLException;

    ProductDraft findSingleProduct(ProductDraft productDraft) throws SQLException;

    ProductDraft selectByPrimaryKey(Long id) throws SQLException;
}
