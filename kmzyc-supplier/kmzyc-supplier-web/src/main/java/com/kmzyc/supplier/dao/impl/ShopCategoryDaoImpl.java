package com.kmzyc.supplier.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.supplier.model.ShopCategorys;
import com.kmzyc.supplier.dao.ShopCategoryDao;

@Repository("shopCategoryDao")
public class ShopCategoryDaoImpl implements ShopCategoryDao {

    @Resource
    private SqlMapClient sqlMapClient;

    @Override
    public Long insert(ShopCategorys paraObj) throws SQLException {
        return (Long) sqlMapClient.insert("SHOP_CATEGORYS.ibatorgenerated_insertSelective", paraObj);
    }

    @Override
    public String queryMaxChildCodeByParentId(long parentId) throws SQLException {
        Object obj = sqlMapClient.queryForObject("SHOP_CATEGORYS.queryMaxChildCodeByParentId", parentId);
        return obj == null ? null : obj.toString();
    }

    @Override
    public ShopCategorys queryByPrimaryKey(long categoryId) throws SQLException {
        ShopCategorys condition = new ShopCategorys();
        condition.setShopCategoryId(categoryId);
        return (ShopCategorys) sqlMapClient.queryForObject("SHOP_CATEGORYS.ibatorgenerated_selectByPrimaryKey", condition);
    }

    @Override
    public List<ShopCategorys> queryShopCategoryList(ShopCategorys condition) throws SQLException {
        return sqlMapClient.queryForList("SHOP_CATEGORYS.queryShopCategoryList", condition);
    }

    @Override
    public Integer queryExistRepeatCategoryName(ShopCategorys condition) throws SQLException {
        return (Integer) sqlMapClient.queryForObject("SHOP_CATEGORYS.queryExsistRepeatCategoryName", condition);
    }

    @Override
    public void udpateShopCategory(ShopCategorys para) throws SQLException {
        sqlMapClient.delete("SHOP_CATEGORYS.ibatorgenerated_updateByPrimaryKeySelective", para);
    }

    @Override
    public Integer queryIsRelationWithDraft(ShopCategorys condition) throws SQLException {
        return (Integer) sqlMapClient.queryForObject("SHOP_CATEGORYS.queryRelationWithDraft", condition);
    }

    @Override
    public Integer queryIsRelationWithOfficial(ShopCategorys condition) throws SQLException {
        return (Integer) sqlMapClient.queryForObject("SHOP_CATEGORYS.queryRelationWithOfficial", condition);
    }

    @Override
    public void updateIsSuggestByParent(ShopCategorys para) throws SQLException {
        sqlMapClient.update("SHOP_CATEGORYS.updateIsSuggestByParent", para);
    }

    @Override
    public void deleteShopCategory(ShopCategorys para) throws SQLException {
        sqlMapClient.delete("SHOP_CATEGORYS.deleteShopCategory", para);
    }

    @Override
    public List<ShopCategorys> queryCategoryByParentId(ShopCategorys condition) throws SQLException {
        return sqlMapClient.queryForList("SHOP_CATEGORYS.queryListByParentId", condition);
    }

    @Override
    public void updateSortNoByCategoryId(long categoryId, int sortNo) throws SQLException {
        ShopCategorys para = new ShopCategorys();
        para.setShopCategoryId(categoryId);
        para.setSortno(sortNo);
        sqlMapClient.update("SHOP_CATEGORYS.updateSortNo", para);
    }

    @Override
    public Integer queryIsExistSortNo(ShopCategorys para) throws SQLException {
        return (Integer) sqlMapClient.queryForObject("SHOP_CATEGORYS.queryIsExistSortNo", para);
    }

    @Override
    public ShopCategorys queryDefaultShopCategory(long shopId) throws SQLException {
        return (ShopCategorys) sqlMapClient.queryForObject("SHOP_CATEGORYS.queryDefaultShopCategory", shopId);
    }

    @Override
    public Integer isExistShopCateCreateBySelf(long shopId) throws SQLException {
        return (Integer) sqlMapClient.queryForObject("SHOP_CATEGORYS.queryIsExistShopCateCreateBySelf", shopId);
    }

    @Override
    public Integer queryIsExistDefaultCategory(long shopId) throws SQLException {
        return (Integer) sqlMapClient.queryForObject("SHOP_CATEGORYS.queryIsExistDefaultShopCategory", shopId);
    }

    @Override
    public String queryShopCategoryName(List<Long> shopCategoryIdList) throws SQLException {
        return (String) sqlMapClient.queryForObject("SHOP_CATEGORYS.queryShopCategoryName", shopCategoryIdList);
    }

}