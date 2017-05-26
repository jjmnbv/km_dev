package com.kmzyc.promotion.app.dao.impl;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.kmzyc.promotion.app.dao.BaseDao;
import com.kmzyc.promotion.app.dao.ProductDao;
import com.kmzyc.promotion.app.dao.ProductSkuDAO;
import com.kmzyc.promotion.app.vobject.Product;
import com.kmzyc.promotion.app.vobject.ProductAttr;
import com.kmzyc.promotion.app.vobject.ProductExample;
import com.kmzyc.promotion.app.vobject.ProductSku;

/**
 * 商品管理基础dao类
 * 
 * @author Administrator
 */
@Repository("productDao")
@SuppressWarnings({"unchecked", "unused"})
public class ProductDaoimpl extends BaseDao implements ProductDao {

    /*
     * (non-Javadoc)
     * 
     * @see com.kmzyc.promotion.app.dao.impl.ProductDao#insertProduct(com.kmzyc.promotion.app
     * .vobject .Product)
     */
    @Resource
    private ProductSkuDAO productSkuDAO;

    @Override
    public Long insertProduct(Product product) {
        // mkw 20150819 add

        // end
        return (Long) getSqlMapClientTemplate().insert("productmapper.product_insert", product);

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.kmzyc.promotion.app.dao.impl.ProductDao#getProducts(com.kmzyc.promotion.app.vobject
     * .Product )
     */
    @Override
    public List<Product> getProducts(Product product) {
        // mkw 20150819 add

        // end
        List<Product> l = queryForList("productmapper.getProducts", product);
        // System.out.println(l);
        return l;

    }

    @Override
    public int countByExample(ProductExample example) throws SQLException {
        // mkw 20150819 add

        // end
        Integer count = (Integer) sqlMapClient
                .queryForObject("productmapper.product_countByExampleByUser", example);
        return count.intValue();
    }

    @Override
    public int countByExampleForExport(ProductExample example) throws SQLException {
        // mkw 20150819 add

        // end
        Integer count = (Integer) sqlMapClient
                .queryForObject("productmapper.product_countByExampleByUserForExport", example);
        return count.intValue();
    }

    // @Override
    // public int countByExample(ProductExample example) throws SQLException {
    // Integer count = (Integer)
    // sqlMapClient.queryForObject("productmapper.product_countByExample",
    // example);
    // return count.intValue();
    // }

    @Override
    public int countBySkuExample(ProductExample example) throws SQLException {
        // mkw 20150819 add

        // end
        Integer count = (Integer) sqlMapClient
                .queryForObject("productmapper.product_countBySkuExample", example);
        return count.intValue();
    }

    @Override
    public List selectByExample(ProductExample example, int skip, int max) throws SQLException {
        // mkw 20150819 add

        // end
        List list = this.getSqlMapClient()
                .queryForList("productmapper.product_selectByExampleByUser", example, skip, max);
        return list;

    }

    @Override
    public List selectByExampleForPrice(ProductExample example, int skip, int max)
            throws SQLException {
        // mkw 20150819 add

        // end
        List list = this.getSqlMapClient().queryForList(
                "productmapper.product_selectByExampleByUserForPrice", example, skip, max);
        return list;

    }

    @Override
    public List selectByExampleForExport(ProductExample example) throws SQLException {
        // mkw 20150819 add

        // end
        List list = this.getSqlMapClient()
                .queryForList("productmapper.product_selectByExampleByUserForExport", example);
        return list;

    }

    // @Override
    // public List selectByExample(ProductExample example, int skip, int max)
    // throws SQLException {
    // List list =
    // this.getSqlMapClient().queryForList("productmapper.product_selectByExample",
    // example,skip,max);
    // return list;
    //
    // }

    @Override
    public List selectBySkuExample(ProductExample example, int skip, int max) throws SQLException {
        // mkw 20150819 add

        // end
        List list = this.getSqlMapClient().queryForList("productmapper.product_selectBySkuExample",
                example, skip, max);
        return list;
    }

    @Override
    public int updateByExample(Product record, ProductExample example) throws SQLException {
        // mkw 20150819 add

        // end
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("productmapper.product_updateByExample", parms);
        return rows;
    }

    private static class UpdateByExampleParms extends ProductExample {
        private final Object record;

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
        // mkw 20150819 add

        // end
        Product product = new Product();
        product.setId(productId);
        Product record = (Product) sqlMapClient
                .queryForObject("productmapper.product_selectByPrimaryKey2", product);
        return record;
    }

    @Override
    public int selectProductByName(String productName) throws SQLException {
        // mkw 20150819 add

        // end
        int count = (Integer) sqlMapClient.queryForObject("productmapper.selectProductByName",
                productName);
        return count;
    }

    @Override
    public int updateByPrimaryKey(Product product) throws SQLException {
        // mkw 20150819 add

        // end
        int rows = sqlMapClient.update("productmapper.product_updateByPrimaryKey", product);
        return rows;
    }

    /**
     * 批量更新
     */
    @Override
    public int batchUpdateForProduct(String statementName, List<Product> parameterList)
            throws SQLException {
        // mkw 20150819 add

        // end
        return super.batchUpdateData(statementName, parameterList);
    }

    @Override
    public List<Product> selectSkuByProductId(Product product) throws SQLException {
        // mkw 20150819 add

        // end
        return queryForList("productmapper.product_selectById", product);
    }

    @Override
    public long deleteProductById(Long productId) throws SQLException {
        // mkw 20150819 add

        // end
        return sqlMapClient.delete("productmapper.deleteObject", productId);
    }

    /**
     * 单个产品上架
     */
    @Override
    public int updateShelfStatus(Product product) throws SQLException {
        // mkw 20150819 add

        // end
        return sqlMapClient.update("productmapper.product_updateShelfStatus", product);
    }

    @Override
    public String queryMaxProductNo(String inputCode) throws SQLException {
        // mkw 20150819 add

        // end
        Object obj =
                sqlMapClient.queryForObject("productmapper.product_queryMaxProductNo", inputCode);
        return obj == null ? null : obj.toString();
    }

    @Override
    public List selectCountShelf(ProductExample product) throws SQLException {
        // mkw 20150819 add

        // end
        List listCount =
                sqlMapClient.queryForList("productmapper.product_selectCountByExample", product);
        return listCount;
    }

    @Override
    public int batchAuditProduct(List<Long> productIdList) throws SQLException {
        // mkw 20150819 add

        // end
        return super.batchUpdateData("productmapper.product_batchAuditProduct", productIdList);
    }

    @Override
    public Map<Long, ProductAttr> checkOperationAttr(List<Long> productIdList) throws SQLException {
        // mkw 20150819 add

        // end
        return sqlMapClient.queryForMap("PRODUCT_ATTR.selectProductAttrByProductIdList",
                productIdList, "productId");
    }

    @Override
    public Product getProductBySku(Long productSkuId) throws Exception {
        ProductSku sku = productSkuDAO.selectByPrimaryKey(productSkuId);
        if (sku == null)
            return null;
        return this.selectByPrimaryKey(sku.getProductId());
    }

    @Override
    public Product queryProductByProductNo(String productNo) throws SQLException {
        // mkw 20150819 add

        // end
        Product product = (Product) sqlMapClient
                .queryForObject("productmapper.queryProductByProductNo", productNo);
        return product;
    }

    @Override
    public List selectForCertificateByExample(ProductExample example, int skip, int max)
            throws SQLException {
        // mkw 20150819 add

        // end
        return sqlMapClient.queryForList(
                "productmapper.product_selectByExampleByUserForCertificate", example, skip, max);
    }

    @Override
    public List<Integer> getProductIdByBrandId(Long brandId) throws SQLException {
        // mkw 20150819 add

        // end
        return sqlMapClient.queryForList("productmapper.getProductIdByBrandId", brandId);
    }

    /**
     * 根据skuid查询产品main信息
     */
    @Override
    public Product selectBySkuId(Long productSkuId) throws SQLException {
        // mkw 20150819 add

        // end
        Product product = (Product) sqlMapClient.queryForObject("productmapper.product_queryInfo",
                productSkuId);
        return product;
    }

    @Override
    public List<Map> getProducts(Collection<?> skuIds) throws SQLException {
        Map<String, Object> map = Maps.newHashMap();
        map.put("skuIds", skuIds.toArray(new String[skuIds.size()]));
        // mkw 20150819 add

        // end
        List<Map> cpList = sqlMapClient.queryForList("PRODUCT_SKU.SQL_PRODUCT_LIST", map);
        return cpList;
    }
}
