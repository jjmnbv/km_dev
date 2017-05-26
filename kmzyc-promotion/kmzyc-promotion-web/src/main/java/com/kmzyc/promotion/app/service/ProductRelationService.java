package com.kmzyc.promotion.app.service;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.vobject.Product;
import com.kmzyc.promotion.app.vobject.ProductRelation;
import com.kmzyc.promotion.app.vobject.ProductRelationAndDetail;
import com.kmzyc.promotion.exception.ServiceException;

public interface ProductRelationService {

    /**
     * 保存关联主单信息
     * 
     * @return
     * @throws SQLException
     */
    public Long saveProductRelation(ProductRelation productRelation) throws SQLException;

    /**
     * 根据主产品的id查询出关联主表出来
     * 
     * @param productRelation
     * @return
     * @throws SQLException
     */

    public void getProductRelationByMainSkuId(ProductRelation productRelation, Page page)
            throws SQLException;

    /**
     * 批量删除产品关联组合 主单中的信息
     * 
     * @param detailIdlist
     * @return
     * @throws SQLException
     */

    public int batchDelProductRelation(List<Long> detailIdlist) throws SQLException;

    /**
     * 根据产品关联的主键id 批量删除对应的字段信息
     * 
     * @param relationId
     * @return
     * @throws SQLException
     */

    public int batchDelProductRelationDetailByDetailId(List<Long> relationId) throws SQLException;

    /**
     * 批量删除套餐
     * 
     * @param relationId
     * @return
     * @throws SQLException
     */

    public void batchDelPackage(List<Long> relationId) throws ServiceException;

    /**
     * 根据skuID 查询出 其套餐 信息
     * 
     * @param skuId
     * @return
     * @throws SQLException
     */

    public List<ProductRelationAndDetail> selectProductRelationAndDetailPackageList(Long skuId)
            throws SQLException;

    /**
     * 根据skuId 查询出其推荐信息
     * 
     * @param skuId
     * @return
     * @throws SQLException
     */

    public List<ProductRelationAndDetail> selectProductRelationAndDetailRecommendList(Long skuId)
            throws SQLException;

    /**
     * 根据relationId 查询出想对应的productRelation 对象出来
     * 
     * @param relationId
     * @return
     * @throws SQLException
     */
    public ProductRelation queryProductRelation(Long relationId) throws SQLException;

    /**
     * 根据SKUID 查询产品关联表、明细表
     * 
     * @param skuId
     * @return
     * @throws SQLException
     */
    public List<ProductRelationAndDetail> selectProductRelationAndDetailBySkuId(Long skuId)
            throws Exception;

    /**
     * 根据PRODUCT_ID 查询产品关联主副表中包含SKU所属PRODUCT列表
     * 
     * @param productIdList
     * @return
     * @throws SQLException
     */
    public List<Long> selectProductRelationAndDetailByProductId(List<Long> productIdList)
            throws Exception;

    /**
     * 根据relationId 变更总的套餐价格
     * 
     * @param relation
     * @throws SQLException
     */
    public void updateProductRelationTotalRelaitonPriceByRelationId(ProductRelation relation)
            throws SQLException;

    /**
     * 根据relationId 更改套餐的状态
     * 
     * @param relations
     * @return
     * @throws SQLException
     */
    public int batchDelProductRelationStatus(List<Long> relations) throws SQLException;

    /**
     * 更新关联组合的状态
     * 
     * @return
     * @throws SQLException
     */

    public int updateProductRelationStatus(Long id, String status) throws SQLException;

    /**
     * 批量更新productRelation 集合的编辑状态
     * 
     * @param list
     * @return
     * @throws SQLException
     */
    public int batchUpdateProductRealtionEdible(List<ProductRelation> list) throws SQLException;

    /**
     * 根据产品的relationId 更新相应的信息
     * 
     * @param productRelation
     * @return
     * @throws SQLException
     */
    public int updateProductRelation(ProductRelation productRelation) throws SQLException;

    /**
     * 更新产品的可编辑状态
     * 
     * @param id
     * @return
     * @throws SQLException
     */
    public int updateProductRelationEditable(Long id, String status) throws SQLException;

    /**
     * 根据被关联的产品skuId，查询出关联主子表信息
     * 
     * @param skuId
     * @return
     * @throws SQLException
     */
    public List<ProductRelationAndDetail> selectProductRelationAndDetailsByRelaitonSkuId(Long skuId)
            throws SQLException;

    public void getProductPackageByMainSkuId(ProductRelation productRelation, Page page)
            throws SQLException;

    public String upOrDownShelf(List<Long> operateList, String operateType) throws SQLException;

    public boolean downShelfPackages(List<Product> list);;

}
