package com.kmzyc.promotion.app.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.dao.BaseDao;
import com.kmzyc.promotion.app.dao.ProductmainTiedDAO;
import com.kmzyc.promotion.app.vobject.ProductmainTied;

@Component("productMainTiedDAO")
@SuppressWarnings("unchecked")
public class ProductMainTiedDAOImpl extends BaseDao implements ProductmainTiedDAO {

    public ProductMainTiedDAOImpl() {
        super();
    }

    @Override
    public List<ProductmainTied> selectList(ProductmainTied prod, Page page) throws SQLException {
        // mkw 20150819 add

        // end
        List<ProductmainTied> list = sqlMapClient.queryForList("PRODUCTMAINTIED.selectlist", prod,
                (page.getPageNo() - 1) * page.getPageSize(), page.getPageSize());
        return list;

    }

    @Override
    public List<ProductmainTied> selectListByUser(ProductmainTied prod, Page page)
            throws SQLException {
        // mkw 20150819 add

        // end
        List<ProductmainTied> list = sqlMapClient.queryForList("PRODUCTMAINTIED.selectlistByUser",
                prod, (page.getPageNo() - 1) * page.getPageSize(), page.getPageSize());
        return list;
    }

    @Override
    public List<ProductmainTied> selectListByUserByRelation(ProductmainTied prod, Page page)
            throws SQLException {
        // mkw 20150819 add

        // end
        List<ProductmainTied> list =
                sqlMapClient.queryForList("PRODUCTMAINTIED.selectlistByUserByRelation", prod,
                        (page.getPageNo() - 1) * page.getPageSize(), page.getPageSize());
        return list;

    }

    @Override
    public ProductmainTied selectExampleBySku(Long id) throws SQLException {
        // mkw 20150819 add

        // end
        ProductmainTied productmain = (ProductmainTied) sqlMapClient
                .queryForObject("PRODUCTMAINTIED.selectExampleByMainSku", id);
        return productmain;
    }

    @Override
    public int countItem(ProductmainTied prod) throws SQLException {
        // mkw 20150819 add

        // end
        int i = (Integer) sqlMapClient.queryForObject("PRODUCTMAINTIED.countItem", prod);
        return i;
    }

    @Override
    public int countItemByUser(ProductmainTied prod) throws SQLException {
        // mkw 20150819 add

        // end
        int i = (Integer) sqlMapClient.queryForObject("PRODUCTMAINTIED.countItemByUser", prod);
        return i;
    }

    @Override
    public int countItemByUserByRelation(ProductmainTied prod) throws SQLException {
        // mkw 20150819 add

        // end
        int i = (Integer) sqlMapClient.queryForObject("PRODUCTMAINTIED.countItemByUserByRelation",
                prod);
        return i;
    }

    @Override
    public List<ProductmainTied> selectListExcepMainsku(ProductmainTied prod, Page page)
            throws SQLException {
        // mkw 20150819 add

        // end
        List<ProductmainTied> list =
                sqlMapClient.queryForList("PRODUCTMAINTIED.selectListExceptMainSku", prod,
                        (page.getPageNo() - 1) * page.getPageSize(), page.getPageSize());
        return list;
    }

    @Override
    public int countItemExceptMainsku(ProductmainTied prod) throws SQLException {
        // mkw 20150819 add

        // end
        int i = (Integer) sqlMapClient.queryForObject("PRODUCTMAINTIED.countItemExceptMainsku",
                prod);
        return i;
    }

    @Override
    public Map<String, ProductmainTied> getProductSkuMapBySkucode(List<String> list)
            throws SQLException {
        // mkw 20150819 add

        // end
        Map<String, ProductmainTied> map = sqlMapClient
                .queryForMap("PRODUCTMAINTIED.selectProductSkuAndQuantity", list, "productSkuCode");
        return map;

    }

    @Override
    public ProductmainTied getProductmainTied(String skuCode) throws SQLException {
        // mkw 20150819 add

        // end
        ProductmainTied productmainTied = (ProductmainTied) sqlMapClient
                .queryForObject("PRODUCTMAINTIED.selectProductMainBySkuCode", skuCode);
        return productmainTied;
    }

    @Override
    public Map<Long, ProductmainTied> getProductSkuMapBySkuId(List<Long> skuIdlist)
            throws SQLException {
        // mkw 20150819 add

        // end
        Map<Long, ProductmainTied> productMainMap = sqlMapClient
                .queryForMap("PRODUCTMAINTIED.selectProductMainBySkuId", skuIdlist, "productSkuId");
        return productMainMap;
    }

    @Override
    public List<ProductmainTied> queryProductMainTiedsBySkuIdList(List<Long> skuList)
            throws SQLException {
        // mkw 20150819 add

        // end
        List<ProductmainTied> list =
                sqlMapClient.queryForList("PRODUCTMAINTIED.selectProductMainBySkuId", skuList);
        return list;
    }

}
