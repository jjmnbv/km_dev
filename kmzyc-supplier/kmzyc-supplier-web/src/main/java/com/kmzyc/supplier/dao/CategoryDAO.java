package com.kmzyc.supplier.dao;

import com.kmzyc.promotion.app.vobject.PromotionInfo;
import com.kmzyc.supplier.model.Categorys;
import com.pltfm.app.vobject.Category;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface CategoryDAO {

    List<Category> queryCategoryParentList(Category record) throws SQLException;

    List selectByExample(Map paramMap) throws SQLException;

    /**
     * 申请供应商选择类目显示
     *
     * @return
     * @throws SQLException
     */
    List<Categorys> applySuppliersCategories() throws SQLException;

    /**
     * 申请供应商选择类目显示(新增佣金)
     *
     * @return
     * @throws SQLException
     */
    List<Categorys> applySuppliersCategoriesAll() throws SQLException;

    List<Category> queryCategoryList(Category category) throws SQLException;

    /**
     * 选择供应商一级或者二级目录（权限范围） <note> 因二级目录佣金修改 </note>
     *
     * @param map 类目基本信息
     * @return List<Category> 类目基本信息列表
     * @throws Exception
     */
    List<Category> selectCategoryWithSupplyAvailable(Map<String, Object> map) throws SQLException;

    Integer promotionIsLock(PromotionInfo promotion) throws SQLException;

    /**
     * 根据类目id获取当前类目id在pv值比例
     * @param categoryId
     * @return
     * @throws SQLException
     */
    BigDecimal getCategoryPvDefaultRebate(Long categoryId) throws SQLException;
}
