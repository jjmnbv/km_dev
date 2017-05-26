package com.kmzyc.b2b.shopcart.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.km.framework.persistence.impl.DaoImpl;
import com.kmzyc.b2b.shopcart.dao.ShopCartInfoDao;
import com.kmzyc.b2b.shopcart.vo.NormalCartProduct;
import com.kmzyc.supplier.model.SupplierFare;

@Component("shopCartInfoDao")
public class ShopCartInfoDaoImpl extends DaoImpl implements ShopCartInfoDao {
    @Resource
    protected SqlMapClient sqlMapClient;


    /**
     * 批量查询套餐
     * 
     * @param suitIds
     */
    @Override
    public List<Map<String, String>> getComsiotionList(String[] suitIds) throws SQLException {

        return sqlMapClient.queryForList("NormalCartProduct.SQL_COMIOTION_PRODUCT_LIST", suitIds);
    }


    @Override
    public List<NormalCartProduct> getProductList(String[] productIds) throws SQLException {

        @SuppressWarnings("unchecked")
        List<NormalCartProduct> cpList =
                sqlMapClient.queryForList("NormalCartProduct.SQL_PRODUCT_LIST", productIds);
        return cpList;
    }



    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Integer> queryStockBatch(List<?> skuIds) throws SQLException {

        return sqlMapClient.queryForMap("NormalCartProduct.queryStockBatch", skuIds, "SKUID",
                "STOCK");
    }

    @Override
    public List<SupplierFare> selectSupplierFareInfoList(List<?> ids) throws SQLException {

        return sqlMapClient.queryForList("NormalCartProduct.selectSupplierFareInfoList", ids);
    }
}
