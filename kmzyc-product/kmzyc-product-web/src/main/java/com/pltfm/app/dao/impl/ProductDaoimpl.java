package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;


import com.pltfm.app.vobject.ProductSkuForExport;
import org.springframework.stereotype.Repository;

import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.dao.ProductDao;
import com.pltfm.app.vobject.Product;
import com.pltfm.app.vobject.ProductAttr;
import com.pltfm.app.vobject.ProductExample;

@Repository("productDao")
public class ProductDaoimpl extends BaseDao implements ProductDao {

    @Override
    public Long insertProduct(Product product) {
        return (Long) getSqlMapClientTemplate().insert("productmapper.product_insert", product);
    }

    @Override
    public int countByExample(ProductExample example) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject("productmapper.product_countByExampleByUser", example);
        return count.intValue();
    }

    public int countBySkuExample(ProductExample example) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject("productmapper.product_countBySkuExample", example);
        return count.intValue();
    }

    @Override
    public List selectByExample(ProductExample example, int skip, int max) throws SQLException {
        return sqlMapClient.queryForList("productmapper.product_selectByExampleByUser", example, skip, max);
    }

    @Override
    public List selectByExampleForPrice(ProductExample example, int skip, int max) throws SQLException {
        return sqlMapClient.queryForList("productmapper.product_selectByExampleByUserForPrice", example, skip, max);
    }

    @Override
    public List<ProductSkuForExport> selectByExampleForExport(ProductExample example) throws SQLException {
        return sqlMapClient.queryForList("productmapper.selectByExampleForExport", example);
    }

    public List selectBySkuExample(ProductExample example, int skip, int max) throws SQLException {
        return sqlMapClient.queryForList("productmapper.product_selectBySkuExample", example, skip, max);
    }

    @Override
    public int updateByExample(Product record, ProductExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        return sqlMapClient.update("productmapper.product_updateByExample", parms);
    }

    private static class UpdateByExampleParms extends ProductExample {
        private Object record;

        public UpdateByExampleParms(Object record, ProductExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }

    @Override
    public Product selectByPrimaryKey(Long productId) throws SQLException {
        Product product = new Product();
        product.setId(productId);
        return (Product) sqlMapClient.queryForObject("productmapper.product_selectByPrimaryKey2", product);
    }

    @Override
    public int selectProductByName(String productName) throws SQLException {
        return (Integer) sqlMapClient.queryForObject("productmapper.selectProductByName", productName);
    }

    @Override
    public int updateByPrimaryKey(Product product) throws SQLException {
        return sqlMapClient.update("productmapper.product_updateByPrimaryKey", product);
    }

    @Override
    public int batchUpdateForProduct(String statementName, List<Product> parameterList) throws SQLException {
        return super.batchUpdateData(statementName, parameterList);
    }

    @Override
    public List<Product> selectSkuByProductId(Product product) throws SQLException {
        return queryForList("productmapper.product_selectById", product);
    }

    @Override
    public Product selectProductAndSkuByProductId(Product product) throws SQLException {
        return (Product) sqlMapClient.queryForObject("productmapper.product_selectById", product);
    }

    @Override
    public long deleteProductById(Long productId) throws SQLException {
        return sqlMapClient.delete("productmapper.deleteObject", productId);
    }

    @Override
    public List selectCountShelf(ProductExample product) throws SQLException {
        return sqlMapClient.queryForList("productmapper.product_selectCountByExample", product);
    }

    @Override
    public int batchAuditProduct(List<Long> productIdList) throws SQLException {
        return super.batchUpdateData("productmapper.product_batchAuditProduct", productIdList);
    }

    @Override
    public Map<Long, ProductAttr> checkOperationAttr(List<Long> productIdList) throws SQLException {
        return sqlMapClient.queryForMap("PRODUCT_ATTR.selectProductAttrByProductIdList", productIdList, "productId");
    }

    public Product queryProductByProductNo(String productNo) throws SQLException {
        return (Product) sqlMapClient.queryForObject("productmapper.queryProductByProductNo", productNo);
    }

    @Override
    public List selectForCertificateByExample(ProductExample example, int skip, int max) throws SQLException {
        return sqlMapClient.queryForList("productmapper.product_selectByExampleByUserForCertificate", example, skip, max);
    }

    @Override
    public List<Integer> getProductIdByBrandId(Long brandId) throws SQLException {
        return sqlMapClient.queryForList("productmapper.getProductIdByBrandId", brandId);
    }

    @Override
    public Product selectBySkuId(Long productSkuId) throws SQLException {
        return (Product) sqlMapClient.queryForObject("productmapper.product_queryInfo", productSkuId);
    }

    @Override
    public List<Product> getProductIdListForSuppliers(List<Long> supplierIds) throws SQLException {
        return sqlMapClient.queryForList("productmapper.getProductListForSuppliers", supplierIds);
    }

    @Override
    public int updateIllegalProductUpShelfById(Long productId) throws SQLException {
        return sqlMapClient.update("productmapper.illegalProduct_upShelfById", productId);
    }

    @Override
    public int updateIllegalProductDownShelfById(Product product) throws SQLException {
        return sqlMapClient.update("productmapper.illegalProduct_downShelfById", product);
    }

    @Override
    public List selectByExampleForPV(ProductExample example, int skip, int max) throws SQLException {
        return sqlMapClient.queryForList("productmapper.product_selectByExampleByUserForPV", example, skip, max);
    }

    @Override
    public int countByExampleForPV(ProductExample example) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject("productmapper.product_countByExampleForPVByUser", example);
        return count.intValue();
    }

    public int deleteByPrimaryKey(Long productId) throws SQLException {
        Product key = new Product();
        key.setProductId(productId.intValue());
        return sqlMapClient.delete("productmapper.ibatorgenerated_deleteByPrimaryKey", key);
    }

}