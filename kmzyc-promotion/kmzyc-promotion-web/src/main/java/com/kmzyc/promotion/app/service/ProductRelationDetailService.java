package com.kmzyc.promotion.app.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.vobject.ProductRelationDetail;

public interface ProductRelationDetailService {

    public int saveProductRelationDetail(List<ProductRelationDetail> details) throws SQLException;

    /**
     * 根据产品关联的 主表的 主键id 批量删除 字表对应的字段信息
     * 
     * @param relationId
     * @return
     * @throws SQLException
     */

    public int batchDelProductRelationDetailByRelationId(List<Long> relationId) throws SQLException;

    /**
     * 根据 relationId 查出 产品关联子表的关于 productRelationDeatil map 对象
     * 
     * @param relationId
     * @return
     * @throws SQLException
     */

    public Map<Long, ProductRelationDetail> getProductRelationDetailByRelationId(Long relationId)
            throws SQLException;

    /**
     * 根据relationId 获得 相对应的 ProductRelationDetail，以及ProductmainTied 的map
     * 
     * @param id
     * @return
     * @throws SQLException
     */
    public void getProductRelationDetailProductSku(Long id, Page page) throws SQLException;

    /**
     * 根据关联子表的主键，删除关联字表的记录
     * 
     * @param relationDetailId
     * @return
     * @throws SQLException
     */

    public int batchDelProductRelationDetailByRelationDetailId(List<Long> relationDetailId)
            throws SQLException;

    /**
     * 批量保存字表信息
     * 
     * @param details
     * @return
     * @throws SQLException
     */
    public int batchSaveProductRealtionDetail(List<ProductRelationDetail> details)
            throws SQLException;

    /**
     * 根据relationId 查询出关联子单的信息
     * 
     * @param relationId
     * @return
     * @throws SQLException
     */

    public List<ProductRelationDetail> queryProductRelationDetailByRelationId(Long relationId)
            throws SQLException;

    /**
     * 在关联子单中 根据relationDetailId 更新其价格
     * 
     * @param detail
     * @throws SQLException
     */
    public void updateProductRelationDetailPrice(ProductRelationDetail detail) throws SQLException;

    /**
     * 根据 detailId 查询对于的子单记录
     * 
     * @param relationId
     * @return
     * @throws SQLException
     */

    public ProductRelationDetail queryProductRelationDetailByDetailId(Long relationId)
            throws SQLException;

}
