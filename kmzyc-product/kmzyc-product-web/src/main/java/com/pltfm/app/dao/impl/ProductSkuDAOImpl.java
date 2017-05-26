package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.pltfm.app.vobject.CouponProduct;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.dao.ProductSkuDAO;
import com.pltfm.app.vobject.CarryStockOutDetailVO;
import com.pltfm.app.vobject.ProductAndSku;
import com.pltfm.app.vobject.ProductSku;
import com.pltfm.app.vobject.ProductSkuExample;
import com.pltfm.app.vobject.ProductStock;
import com.pltfm.sys.model.SysModelUtil;
import com.kmzyc.commons.page.Page;

@Repository("productSkuDao")
public class ProductSkuDAOImpl extends BaseDao<ProductSku> implements ProductSkuDAO {

    public int countByExample(ProductSkuExample example) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject("PRODUCT_SKU.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(ProductSkuExample example) throws SQLException {
        return sqlMapClient.delete("PRODUCT_SKU.ibatorgenerated_deleteByExample", example);
    }

    public int deleteByPrimaryKey(Long productSkuId) throws SQLException {
        ProductSku key = new ProductSku();
        key.setProductSkuId(productSkuId);
        return sqlMapClient.delete("PRODUCT_SKU.ibatorgenerated_deleteByPrimaryKey", key);
    }

    public void insert(ProductSku record) throws SQLException {
        sqlMapClient.insert("PRODUCT_SKU.ibatorgenerated_insert", record);
    }

    public void insertSelective(ProductSku record) throws SQLException {
        sqlMapClient.insert("PRODUCT_SKU.ibatorgenerated_insertSelective", record);
    }

    public List selectByExample(ProductSkuExample example) throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_SKU.ibatorgenerated_selectByExample", example);
    }

    public ProductSku selectByPrimaryKey(Long productSkuId) throws SQLException {
        ProductSku key = new ProductSku();
        key.setProductSkuId(productSkuId);
        return (ProductSku) sqlMapClient.queryForObject("PRODUCT_SKU.ibatorgenerated_selectByPrimaryKey", key);
    }

    public int updateByExampleSelective(ProductSku record, ProductSkuExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        return sqlMapClient.update("PRODUCT_SKU.ibatorgenerated_updateByExampleSelective", parms);
    }

    public int updateByExample(ProductSku record, ProductSkuExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        return sqlMapClient.update("PRODUCT_SKU.ibatorgenerated_updateByExample", parms);
    }

    public int updateByPrimaryKeySelective(ProductSku record) throws SQLException {
        return sqlMapClient.update("PRODUCT_SKU.ibatorgenerated_updateByPrimaryKeySelective", record);
    }

    public int updateByPrimaryKey(ProductSku record) throws SQLException {
        return sqlMapClient.update("PRODUCT_SKU.ibatorgenerated_updateByPrimaryKey", record);
    }

    private static class UpdateByExampleParms extends ProductSkuExample {
        private Object record;

        public UpdateByExampleParms(Object record, ProductSkuExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }

    public Map<Long, ProductAndSku> findProduct(String productSkuCode) throws SQLException {
        return sqlMapClient.queryForMap("PRODUCT_SKU.findProductInfoBySkuCode", productSkuCode, "productId");
    }

    @Override
    public Page selectPageByVo(Page page, ProductSku vo) throws SQLException {
        // 总条数
        Integer count = (Integer)sqlMapClient.queryForObject("PRODUCT_SKU.getProductSkuCount", vo);
        int pageCount = 1;
        // 总页数
        if (count > 1) {
            pageCount = (count - 1) / page.getPageSize() + 1;
        }
        page.setRecordCount(count);
        page.setPageCount(pageCount);
        List pageList = sqlMapClient.queryForList("PRODUCT_SKU.searchPageByVo", vo);
        page.setDataList(pageList);
        return page;
    }

    @Override
    public String findMaxSkuCodeByCateCode(String cateCode) throws SQLException {
        Object obj = sqlMapClient.queryForObject("PRODUCT_SKU.findMaxSkuCodeByCateCode", cateCode);
        return obj == null ? null : obj.toString();
    }

    @Override
    public void updateUnitWeightByPrimaryKey(List<ProductSku> productSkuList) throws SQLException {
        super.batchUpdateData("PRODUCT_SKU.updateWeightByPrimaryKey", productSkuList);
    }

    public List<Long> selectSkuIdByManyCategory(List<Long> categoryIdList) throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_SKU_ATTR.selectSkuIdByManyCategory", categoryIdList);

    }

    @Override
    public List<Long> selectSkuIdsByProductIdList(List<Long> productIdList) throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_SKU.selectSkuIdsByProductIdList", productIdList);
    }

    public List<String> selectSkuCodeByManySkuId(List<Long> skuIdList) throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_SKU_ATTR.selectSkuCodeByManySkuId", skuIdList);
    }

    @Override
    public List<ProductSku> selectByExample(ProductSkuExample example, int pageNo, int pageSize) throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_SKU.ibatorgenerated_selectByExample", example, (pageNo - 1) * pageSize, pageSize);
    }

    @Override
    public Map<Long, ProductSku> getSkuIdAndCodeMap(List<Long> skuIdList) throws SQLException {
        return sqlMapClient.queryForMap("PRODUCT_SKU.getSkuIdAndCodeMap", skuIdList, "productSkuId");
    }

    @Override
    public List<ProductSku> findProductSkuBySkuCodes(List<String> productSkuCodeList) throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_SKU.findProductSkuBySkuCodes", productSkuCodeList);
    }

    @Override
    public void updateStock(List<ProductStock> stockList) throws SQLException {
        super.batchUpdateDataNotGen("PRODUCT_SKU.updateStock", stockList);
    }

    @Override
    public List<ProductSku> findSameSkuBarCodeProductSku(Map<String, Object> map) throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_SKU.findSameSkuBarCodeProductSku", map);
    }

    @Override
    public List<Map<String, String>> getProductSkuDetailsMap(List<Long> productSkuIds, int skip, int max) throws SQLException {
        if (max == 0) {
            return sqlMapClient.queryForList("PRODUCT_SKU.getProductSkuDetailsMap", productSkuIds);
        } else {
            return sqlMapClient.queryForList("PRODUCT_SKU.getProductSkuDetailsMap", productSkuIds, skip, max);
        }
    }

    @Override
    public List<CouponProduct> selectCouponProductBySkuId(String skuCode) throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_SKU.selectCouponProductBySkuId", skuCode);
    }
}
