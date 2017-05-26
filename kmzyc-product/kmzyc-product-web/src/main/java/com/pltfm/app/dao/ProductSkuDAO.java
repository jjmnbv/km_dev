package com.pltfm.app.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.pltfm.app.vobject.CarryStockOutDetailVO;
import com.pltfm.app.vobject.CouponProduct;
import com.pltfm.app.vobject.ProductAndSku;
import com.pltfm.app.vobject.ProductSku;
import com.pltfm.app.vobject.ProductSkuExample;
import com.pltfm.app.vobject.ProductStock;
import com.kmzyc.commons.page.Page;

public interface ProductSkuDAO {

    int countByExample(ProductSkuExample example) throws SQLException;

    int deleteByExample(ProductSkuExample example) throws SQLException;

    int deleteByPrimaryKey(Long productSkuId) throws SQLException;

    void insert(ProductSku record) throws SQLException;

    void insertSelective(ProductSku record) throws SQLException;

    List selectByExample(ProductSkuExample example) throws SQLException;

    List selectByExample(ProductSkuExample skuExample, int pageNo, int pageSize) throws SQLException;

    ProductSku selectByPrimaryKey(Long productSkuId) throws SQLException;

    int updateByExampleSelective(ProductSku record, ProductSkuExample example) throws SQLException;

    int updateByExample(ProductSku record, ProductSkuExample example) throws SQLException;

    int updateByPrimaryKeySelective(ProductSku record) throws SQLException;

    void updateStock(List<ProductStock> stockList) throws SQLException;

    int updateByPrimaryKey(ProductSku record) throws SQLException;

    /**
     * 根据SKU，查找商品信息Dao
     */
    Map<Long, ProductAndSku> findProduct(String productSkuId) throws SQLException;

    /**
     * 获取产品SKU信息列表
     *
     * @param page 分页对象
     * @param vo   产品SKU信息实体
     * @return 分页信息列表
     * @throws Exception 异常
     */
    Page selectPageByVo(Page page, ProductSku vo) throws SQLException;

    /**
     * 找寻最大的SKU编号
     *
     * @param cateCode
     * @return
     * @throws SQLException
     */
    String findMaxSkuCodeByCateCode(String cateCode) throws SQLException;

    /**
     * 更新SKU商品重量
     *
     * @param productSkuList
     * @throws SQLException
     */
    void updateUnitWeightByPrimaryKey(List<ProductSku> productSkuList) throws SQLException;

    /**
     * 根据产品ID集合，查询其所有SKUID集合
     *
     * @param productIdList
     * @return
     * @throws SQLException
     */
    List<Long> selectSkuIdsByProductIdList(List<Long> productIdList) throws SQLException;

    /***
     * 根据skuId 的集合查询出所对应的skuCode list
     *
     * @param skuId
     * @return
     * @throws SQLException
     */
    List<String> selectSkuCodeByManySkuId(List<Long> skuId) throws SQLException;

    // 根据目录查询出所有skuId的产品出来
    List<Long> selectSkuIdByManyCategory(List<Long> categoryIdList) throws SQLException;

    Map<Long, ProductSku> getSkuIdAndCodeMap(List<Long> skuIdList) throws SQLException;

    List<ProductSku> findProductSkuBySkuCodes(List<String> productSkuCodeList) throws SQLException;

    List<ProductSku> findSameSkuBarCodeProductSku(Map<String, Object> map) throws SQLException;

    /**
     * 根据商品skuid获取商品sku详细信息
     *
     * @param productSkuIds 商品skuid集合
     * @return
     * @throws SQLException
     */
    List<Map<String, String>> getProductSkuDetailsMap(List<Long> productSkuIds, int skip, int max)
            throws SQLException;

    /**
     * 根据SKUCODE查询优惠券产品列表
     *
     * @param skuCode
     * @return
     * @throws SQLException
     */
    List<CouponProduct> selectCouponProductBySkuId(String skuCode) throws SQLException;
}