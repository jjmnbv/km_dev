package com.pltfm.app.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.vobject.ProductRelationDetail;
import com.pltfm.app.vobject.ProductRelationDetailView;
import com.kmzyc.commons.page.Page;

public interface ProductRelationDetailService {

    void saveProductRelationDetail(List<ProductRelationDetail> details) throws ServiceException;

    /**
     * 根据relationId 获得  相对应的  ProductRelationDetail，以及ProductmainTied  的map
     *
     * @param id
     * @return
     * @throws SQLException
     */
    List<ProductRelationDetailView> getProductRelationDetailProductSku(Long id, Page page) throws ServiceException;

    /**
     * 根据关联子表的主键，删除关联字表的记录
     *
     * @param relationDetailId
     * @return
     * @throws SQLException
     */
    int batchDelProductRelationDetailByRelationDetailId(List<Long> relationDetailId) throws ServiceException;

    /**
     * 根据relationId  查询出关联子单的信息
     *
     * @param relationId
     * @return
     * @throws SQLException
     */
    List<ProductRelationDetail> queryProductRelationDetailByRelationId(Long relationId) throws ServiceException;

    /**
     * 在关联子单中 根据relationDetailId 更新其价格
     *
     * @param detail
     * @throws SQLException
     */
    void updateProductRelationDetailPrice(ProductRelationDetail detail) throws ServiceException;

    /**
     * 根据套餐id查询子表信息
     *
     * @param productRelationId
     * @return
     */
    List<ProductRelationDetail> productRelationDetailList(Long productRelationId) throws ServiceException;

    /**
     * 定制管理-套餐管理-修改保存套餐(根据套餐子表id删除信息)
     *
     * @return
     * @throws Exception
     */
    int delRelationDetailById(Long detailId) throws ServiceException;
}
