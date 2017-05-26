package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.supplier.model.ShopCategorys;
import com.pltfm.app.dao.ShopCategoryDao;

@Repository("shopCategoryDao")
public class ShopCategoryDaoImpl implements ShopCategoryDao {
    @Resource(name = "sqlMapClient")
    private SqlMapClient sqlMapClient;

    @Override
    public List<ShopCategorys> queryShopCategoryList(ShopCategorys shopCategorys) throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_SHOP_CATEGORYS.queryShopCategoryList", shopCategorys);
    }

    //所有已绑定的店铺类目
    @Override
    public List queryBandShopCategoryList(String categoryIds) throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_SHOP_CATEGORYS.queryBandShopCategoryList", categoryIds);
    }

    //查询父类下的子类
    @Override
    public List queryChildrenByParentId(long categoryId) throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_SHOP_CATEGORYS.queryChildrenByParentId", categoryId);
    }


    @Override
    public ShopCategorys queryByPrimaryKey(long categoryId) throws SQLException {
        ShopCategorys condition = new ShopCategorys();
        condition.setShopCategoryId(categoryId);
        return (ShopCategorys) sqlMapClient.queryForObject("PRODUCT_SHOP_CATEGORYS.ibatorgenerated_selectByPrimaryKey", condition);
    }

    @Override
    public List<ShopCategorys> queryShopCategoryForTree(ShopCategorys condition)
            throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_SHOP_CATEGORYS.queryListForTree", condition);
    }


    @Override
    public List<ShopCategorys> queryCategoryByParentId(ShopCategorys condition)
            throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_SHOP_CATEGORYS.queryListByParentId", condition);
    }

    @Override
    public List<ShopCategorys> queryShopCategoryForList(ShopCategorys condition)
            throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_SHOP_CATEGORYS.queryForList", condition);
    }

    @Override
    public void updateIsExpandBatchById(String[] idArrays) throws SQLException {
        sqlMapClient.update("PRODUCT_SHOP_CATEGORYS.updateIsExpandAllBatchById", idArrays);
    }

    @Override
    public void updateExpandByShopId(ShopCategorys condition) throws SQLException {
        sqlMapClient.update("PRODUCT_SHOP_CATEGORYS.updateExpandAllByShopId", condition);
    }

    @Override
    public void updateIsSuggestBatchById(String[] idArrays) throws SQLException {
        sqlMapClient.update("PRODUCT_SHOP_CATEGORYS.updateIsSuggestBatchById", idArrays);
    }

    @Override
    public void updateSuggestByShopId(ShopCategorys condition)
            throws SQLException {
        sqlMapClient.update("PRODUCT_SHOP_CATEGORYS.updateSuggestByShopId", condition);
    }

}
