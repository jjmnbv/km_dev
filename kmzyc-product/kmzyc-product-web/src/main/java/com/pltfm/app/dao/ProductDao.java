package com.pltfm.app.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.pltfm.app.vobject.Product;
import com.pltfm.app.vobject.ProductAttr;
import com.pltfm.app.vobject.ProductExample;
import com.pltfm.app.vobject.ProductSkuForExport;

public interface ProductDao {

    /**
     * 保存商品基本信息
     *
     * @param product
     */
    Long insertProduct(Product product);

    int countByExample(ProductExample example) throws SQLException;

    List selectByExample(ProductExample example, int skip, int max) throws SQLException;

    List selectByExampleForPrice(ProductExample example, int skip, int max) throws SQLException;

    int countByExampleForPV(ProductExample example) throws SQLException;

    List selectByExampleForPV(ProductExample example, int skip, int max) throws SQLException;

    List selectForCertificateByExample(ProductExample example, int skip, int max) throws SQLException;

    Product selectByPrimaryKey(Long productId) throws SQLException;

    int selectProductByName(String productName) throws SQLException;

    /**
     * 修改商品基本信息
     *
     * @param record
     * @param example
     * @return
     * @throws SQLException
     */
    int updateByExample(Product record, ProductExample example) throws SQLException;

    int updateByPrimaryKey(Product product) throws SQLException;

    /**
     * 批量更新
     *
     * @param statementName
     * @param parameterList
     * @return
     * @throws SQLException
     */
    int batchUpdateForProduct(String statementName, List<Product> parameterList) throws SQLException;

    List<Product> selectSkuByProductId(Product product) throws SQLException;

    /**
     * 根据产品id查询产品的相关字段
     * <note>
     * 含：
     * PRODUCT_ID,
     * PROCUCT_NAME,
     * STATUS,CHANNEL,
     * BRAND_ID,
     * DRUG_CATE_ID,
     * PRODUCT_SUBTITLE,
     * POSTIL,
     * PRODUCT_TYPE,
     * 同时会根据PRODUCT_ID查询对应的sku列表
     * </note>
     *
     * @param product
     * @return
     * @throws SQLException
     */
    Product selectProductAndSkuByProductId(Product product) throws SQLException;

    long deleteProductById(Long productId) throws SQLException;

    /**
     * 查询可以上架的产品个数
     */
    List selectCountShelf(ProductExample product) throws SQLException;

    int batchAuditProduct(List<Long> productIdList) throws SQLException;

    Map<Long, ProductAttr> checkOperationAttr(List<Long> productIdList) throws SQLException;

    /**
     * 根据产品编号 ，查询产品信息
     *
     * @param productNo
     * @return
     * @throws SQLException
     */
    Product queryProductByProductNo(String productNo) throws SQLException;

    /**
     * 查询sku条数
     *
     * @param example
     * @return
     * @throws SQLException
     */
    int countBySkuExample(ProductExample example) throws SQLException;

    /**
     * 查询sku数据
     *
     * @param example
     * @param skip
     * @param max
     * @return
     * @throws SQLException
     */
    List selectBySkuExample(ProductExample example, int skip, int max) throws SQLException;

    List<ProductSkuForExport> selectByExampleForExport(ProductExample example) throws SQLException;

    List<Integer> getProductIdByBrandId(Long brandId) throws SQLException;

    /**
     * 根据skuId查询产品main信息
     *
     * @param productSkuId
     * @return
     * @throws SQLException
     */
    Product selectBySkuId(Long productSkuId) throws SQLException;

    /**
     * 根据供应商ID查询所属产品ID
     *
     * @param supplierIds
     * @return
     * @throws SQLException
     */
    List<Product> getProductIdListForSuppliers(List<Long> supplierIds) throws SQLException;

    /**
     * 违规下架的产品再次上架
     *
     * @param productId 产品id
     * @return 上架的违规下架的产品个数
     * @throws SQLException
     */
    int updateIllegalProductUpShelfById(Long productId) throws SQLException;

    /**
     * 产品违规下架
     *
     * @param product 违规产品
     *                <note>
     *                productId     违规产品id
     *                reasons       违规下架原因
     *                </note>
     * @return 下架产品个数
     * @throws SQLException
     */
    int updateIllegalProductDownShelfById(Product product) throws SQLException;

    int deleteByPrimaryKey(Long productId) throws SQLException;

}