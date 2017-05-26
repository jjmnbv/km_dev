package com.kmzyc.supplier.dao.impl;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.promotion.app.vobject.PromotionInfo;
import com.kmzyc.supplier.model.Categorys;
import com.kmzyc.supplier.dao.CategoryDAO;
import com.pltfm.app.vobject.Category;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

@Repository("categoryDAO")
public class CategoryDAOImpl implements CategoryDAO {

    @Resource
    private SqlMapClient sqlMapClient;

    public List<Category> queryCategoryParentList(Category category) throws SQLException {
        return sqlMapClient.queryForList("CATEGORY.queryCategoryParentList", category);
    }

    @Override
    public List selectByExample(Map paramMap) throws SQLException {
        if (paramMap.get("categoryList") != null)
            return sqlMapClient.queryForList("CATEGORY.ibatorgenerated_selectByExample", paramMap);
        else
            return sqlMapClient.queryForList("CATEGORY.ibatorgenerated_selectByExample2", paramMap);
    }

    @Override
    public List<Categorys> applySuppliersCategories() throws SQLException {
        return sqlMapClient.queryForList("CATEGORY.applySuppliersCategories");
    }

    @Override
    public List<Categorys> applySuppliersCategoriesAll() throws SQLException {
        return sqlMapClient.queryForList("CATEGORY.applySuppliersCategoriesAll");
    }

    @Override
    public List<Category> queryCategoryList(Category category) throws SQLException {
        return sqlMapClient.queryForList("CATEGORY.queryCategoryList", category);
    }

    @Override
    public List<Category> selectCategoryWithSupplyAvailable(Map<String, Object> map) throws SQLException {
        return sqlMapClient.queryForList("CATEGORY.selectCategoryWithSupplyAvailable", map);
    }

    @Override
    public Integer promotionIsLock(PromotionInfo promotion) throws SQLException {
        return (Integer) sqlMapClient.queryForObject("CATEGORY.promotionIsLock",
                promotion.getPromotionId());
    }

    @Override
    public BigDecimal getCategoryPvDefaultRebate(Long categoryId) throws SQLException {
        return (BigDecimal) sqlMapClient.queryForObject("CATEGORY.getCategoryPvDefaultRebate", categoryId);
    }
}