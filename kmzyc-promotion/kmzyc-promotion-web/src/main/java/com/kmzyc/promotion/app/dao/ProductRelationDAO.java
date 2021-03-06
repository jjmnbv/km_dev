package com.kmzyc.promotion.app.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.vobject.ProductRelation;
import com.kmzyc.promotion.app.vobject.ProductRelationAndDetail;
import com.kmzyc.promotion.app.vobject.ProductRelationDetailExample;
import com.kmzyc.promotion.app.vobject.ProductRelationExample;

@SuppressWarnings("unchecked")
public interface ProductRelationDAO {
    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database
     * table PRODUCT_RELATION
     * 
     * @ibatorgenerated Sat Oct 12 17:19:56 CST 2013
     */
    int countByExample(ProductRelationExample example) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database
     * table PRODUCT_RELATION
     * 
     * @ibatorgenerated Sat Oct 12 17:19:56 CST 2013
     */
    int deleteByExample(ProductRelationExample example) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database
     * table PRODUCT_RELATION
     * 
     * @ibatorgenerated Sat Oct 12 17:19:56 CST 2013
     */
    int deleteByPrimaryKey(Long relationId) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database
     * table PRODUCT_RELATION
     * 
     * @ibatorgenerated Sat Oct 12 17:19:56 CST 2013
     */
    void insert(ProductRelation record) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database
     * table PRODUCT_RELATION
     * 
     * @ibatorgenerated Sat Oct 12 17:19:56 CST 2013
     */
    void insertSelective(ProductRelation record) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database
     * table PRODUCT_RELATION
     * 
     * @ibatorgenerated Sat Oct 12 17:19:56 CST 2013
     */
    List selectByExample(ProductRelationExample example, Page page) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database
     * table PRODUCT_RELATION
     * 
     * @ibatorgenerated Sat Oct 12 17:19:56 CST 2013
     */
    ProductRelation selectByPrimaryKey(Long relationId) throws SQLException;

    List selectByExample(ProductRelationExample example) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database
     * table PRODUCT_RELATION
     * 
     * @ibatorgenerated Sat Oct 12 17:19:56 CST 2013
     */
    int updateByExampleSelective(ProductRelation record, ProductRelationExample example)
            throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database
     * table PRODUCT_RELATION
     * 
     * @ibatorgenerated Sat Oct 12 17:19:56 CST 2013
     */
    int updateByExample(ProductRelation record, ProductRelationExample example) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database
     * table PRODUCT_RELATION
     * 
     * @ibatorgenerated Sat Oct 12 17:19:56 CST 2013
     */
    int updateByPrimaryKeySelective(ProductRelation record) throws SQLException;

    /**
     * This method was generated by Apache iBATIS ibator. This method corresponds to the database
     * table PRODUCT_RELATION
     * 
     * @ibatorgenerated Sat Oct 12 17:19:56 CST 2013
     */
    int updateByPrimaryKey(ProductRelation record) throws SQLException;

    /**
     * 插入主关联主表，所获得的插入的主键
     * 
     * @param record
     * @return
     * @throws SQLException
     */
    Long insertProductRelation(ProductRelation record) throws SQLException;

    /**
     * 批量删除产品关联组合 主单中的信息
     * 
     * @param detailIdlist
     * @return
     * @throws SQLException
     */

    public int batchDelProductRelation(List<Long> detailIdlist) throws SQLException;

    /**
     * 根据skuId 查询出其套餐信息
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
     * 根据skuId 查询出套餐 Map relationId 对应productrelation 的map 对象
     * 
     * @param skuId
     * @return
     * @throws SQLException
     */

    public Map<Long, ProductRelation> selectProductRelationPackageMapBySkuId(Long skuId)
            throws SQLException;

    /**
     * 根据skuId 查询出推荐 Map relationId 对应productrelation 的map 对象
     * 
     * @param skuId
     * @return
     * @throws SQLException
     */
    public Map<Long, ProductRelation> selectProductRelationRecommendMapBySkuId(Long skuId)
            throws SQLException;

    /**
     * 根据skuId 查询出 关联类型 为浏览行为 的 关联信息
     * 
     * @param skuId
     * @return
     * @throws SQLException
     */

    public Map<Long, ProductRelation> selectProductAndDetailGlanceList(Long skuId) throws Exception;

    /**
     * 根据skuId 查询出 关联类型 为购买行为 的 关联信息
     * 
     * @param skuId
     * @return
     * @throws Exception
     */

    public Map<Long, ProductRelation> selectProductAndDetailPurchaseList(Long skuId)
            throws Exception;

    /**
     * 根据SKUID 查询产品关联表、明细表
     * 
     * @param skuId
     * @return
     * @throws SQLException
     */
    public List<ProductRelationAndDetail> selectProductRelationAndDetailBySkuId(Long skuId)
            throws SQLException;

    /**
     * 根据PRODUCT_ID 查询产品关联主副表中包含SKU所属PRODUCT列表
     * 
     * @param productIdList
     * @return
     * @throws SQLException
     */
    public List<Long> selectProductRelationAndDetailByProductId(List<Long> productIdList)
            throws SQLException;

    /**
     * 根据relationId 更改套餐的状态
     * 
     * @param relations
     * @return
     * @throws SQLException
     */
    public int batchUpateProductRelationStatus(List<Long> relations) throws SQLException;

    /**
     * 根据realationID 更新创建时间
     * 
     * @param productRelation
     * @return
     * @throws SQLException
     */
    public int updateProductRelation(ProductRelation productRelation) throws SQLException;

    public int updateEditableByRelationId(Long relationId) throws SQLException;

    /**
     * 根据被关联的skuId 查询出 关联主表 ，子表信息
     * 
     * @param skuId
     * @return
     * @throws SQLException
     */
    public List<ProductRelationAndDetail> selectProductRelationAndDetailsByRelaitonSkuId(Long skuId)
            throws SQLException;

    BigDecimal getTotalRelationPrice(ProductRelationDetailExample example) throws SQLException;

    public int updateUnEditableByRelationId(Long id) throws SQLException;

    public int updateStatus(Long relationId, String status) throws SQLException;

    public int batchUpdateData(List<ProductRelation> list) throws SQLException;

}
