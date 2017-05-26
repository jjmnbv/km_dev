package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kmzyc.product.remote.service.SaleRankRemoteService;
import com.pltfm.app.dao.CategoryDAO;
import com.pltfm.app.service.CategoryService;
import com.pltfm.app.vobject.Category;
import com.pltfm.app.vobject.CategoryDetailInfo;
import com.pltfm.app.vobject.CategoryExample;


@Component(value = "categoryService")
public class CategoryServiceImpl implements CategoryService {
	private static Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Resource(name = "categoryDAO")
    private CategoryDAO categoryDAO;

    @Autowired
    private SaleRankRemoteService saleRankService;


    @Override
    public List selectCategoryParent(String categoryIds) throws SQLException {
        return categoryDAO.selectCategoryParent(categoryIds);
    }

    /**
     * 按层级查询全部的物理类目
     */
    @Override
    public List queryCategoryList(Category category) throws Exception {
        return categoryDAO.queryCategoryList(category);
    }

    /**
     * 按层级查询全部的运营类目
     */
    @Override
    public List queryCategoryPhyList(Category category) throws Exception {
        return categoryDAO.queryCategoryPhyList(category);
    }

    /**
     * 根据类目父Id查询类目
     */
    @Override
    public List selectByparentId(Integer parentId) throws Exception {
        CategoryExample example = new CategoryExample();
        example.createCriteria().andParentIdEqualTo(parentId);
        return categoryDAO.selectByExample(example);
    }

    /**
     * 根据类目Id查询单个类目
     */
    @Override
    public Category selectByPrimaryKey(Integer categoryId) throws Exception {
        return categoryDAO.selectByPrimaryKey(categoryId);
    }

    /**
     * 通过产品主键查询产品所属类目信息
     *
     * @param productId 产品主键信息
     * @return 产品所属类目信息
     * @throws Exception 异常信息
     */
    @Override
    public CategoryDetailInfo selectByProductId(Integer productId) throws Exception {
        return categoryDAO.selectByProductId(productId);
    }

    /**
     * 根据类目查询排行榜产品
     */
    @Override
    public List<Integer> selectByCategoryIds(List<Integer> categoryIds) {
        List<Long> categorys = new ArrayList<Long>();
        List<Integer> productInfoIds = null;
        //SaleRankRemoteService remote = null;
        for (Integer id : categoryIds) {
            categorys.add(Long.valueOf(id));
        }
        try {
            //	 remote = (SaleRankRemoteService) RemoteTool.getRemote("02", "saleRankService");
            productInfoIds = saleRankService.getProuductCountByCategoryIdList(categorys);

        } catch (Exception e) {
            logger.error("CategoryServiceImpl.selectByCategoryIds异常：" + e.getMessage(), e);
        }

        return productInfoIds;
    }

    /**
     * 查询三级物理类目
     */
    @Override
    public List querySubCategory() throws Exception {
        return categoryDAO.querySubCategory();
    }

    public CategoryDAO getCategoryDAO() {
        return categoryDAO;
    }

    public void setCategoryDAO(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }


}
