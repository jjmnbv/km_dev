package com.pltfm.app.dao.impl;

import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.dao.ProductDictDAO;
import com.pltfm.app.vobject.ProductDict;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.SQLException;

@Repository("productDictDao")
public class ProductDictDAOImpl extends BaseDao<ProductDict> implements ProductDictDAO {

    @Override
    public BigDecimal getSupportPvProportion() throws SQLException {
        return (BigDecimal) sqlMapClient.queryForObject("PRODUCT_DICT.getSupportPvProportion");
    }
}
