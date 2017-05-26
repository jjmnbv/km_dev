package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.dao.ProductmainTiedDAO;
import com.pltfm.app.vobject.ProductmainTied;
import com.kmzyc.commons.page.Page;

@Component("productMainTiedDao")
public class ProductMainTiedDAOImpl extends BaseDao implements ProductmainTiedDAO {

    @Override
    public List<ProductmainTied> selectList(ProductmainTied prod, Page page) throws SQLException {
        return  sqlMapClient.queryForList("PRODUCTMAINTIED.selectlist", prod, (page.getPageNo() - 1)
                        * page.getPageSize(), page.getPageSize());
    }

    public List<ProductmainTied> selectListTaoCan(ProductmainTied prod, Page page) throws SQLException {
        return sqlMapClient.queryForList("PRODUCTMAINTIED.selectlistTaoCan", prod,
                (page.getPageNo() - 1) * page.getPageSize(), page.getPageSize());
    }

    public List<ProductmainTied> selectListTaoCanZiYing(ProductmainTied prod, Page page) throws SQLException {
        return sqlMapClient.queryForList("PRODUCTMAINTIED.selectlistTaoCanZiYing", prod, 
                (page.getPageNo() - 1) * page.getPageSize(), page.getPageSize());

    }

    public List<ProductmainTied> selectListByUser(ProductmainTied prod, Page page) throws SQLException {
        return sqlMapClient.queryForList("PRODUCTMAINTIED.selectlistByUser", prod, (page.getPageNo() - 1)
                        * page.getPageSize(), page.getPageSize());
    }

    public List<ProductmainTied> selectListByUserByRelation(ProductmainTied prod, Page page) throws SQLException {
        return sqlMapClient.queryForList("PRODUCTMAINTIED.selectlistByUserByRelation", prod, (page.getPageNo() - 1)
                        * page.getPageSize(), page.getPageSize());
    }

    public ProductmainTied selectExampleBySku(Long id) throws SQLException {
        return (ProductmainTied) sqlMapClient.queryForObject("PRODUCTMAINTIED.selectExampleByMainSku", id);
    }

    public int countItem(ProductmainTied prod) throws SQLException {
        return (Integer) sqlMapClient.queryForObject("PRODUCTMAINTIED.countItem", prod);
    }

    public int countItemTaoCan(ProductmainTied prod) throws SQLException {
        return (Integer) sqlMapClient.queryForObject("PRODUCTMAINTIED.countItemTaoCan", prod);
    }

    public int countItemTaoCanZiying(ProductmainTied prod) throws SQLException {
        return (Integer) sqlMapClient.queryForObject("PRODUCTMAINTIED.countItemTaoCanZiYing", prod);
    }

    public int countItemByUser(ProductmainTied prod) throws SQLException {
        return (Integer) sqlMapClient.queryForObject("PRODUCTMAINTIED.countItemByUser", prod);
    }

    public int countItemByUserByRelation(ProductmainTied prod) throws SQLException {
        return (Integer) sqlMapClient.queryForObject("PRODUCTMAINTIED.countItemByUserByRelation", prod);
    }

    public List<ProductmainTied> selectListExcepMainsku(ProductmainTied prod, Page page) throws SQLException {
        return sqlMapClient.queryForList("PRODUCTMAINTIED.selectListExceptMainSku", prod,
                (page.getPageNo() - 1) * page.getPageSize(), page.getPageSize());
    }

    public int countItemExceptMainsku(ProductmainTied prod) throws SQLException {
        return (Integer) sqlMapClient.queryForObject("PRODUCTMAINTIED.countItemExceptMainsku", prod);
    }

    public Map<String, ProductmainTied> getProductSkuMapBySkucode(List<String> list) throws SQLException {
        return sqlMapClient.queryForMap("PRODUCTMAINTIED.selectProductSkuAndQuantity", list, "productSkuCode");
    }

    @Override
    public Map<Long, ProductmainTied> getProductSkuMapBySkuId(List<Long> skuIdlist) throws SQLException {
        return sqlMapClient.queryForMap("PRODUCTMAINTIED.selectProductMainBySkuId", skuIdlist, "productSkuId");
    }

}