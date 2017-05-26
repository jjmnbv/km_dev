package com.pltfm.app.service.impl;

import com.kmzyc.supplier.model.ShopCategorys;
import com.pltfm.app.dao.ShopCategoryDao;
import com.pltfm.app.service.ShopCategorysService;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

@Component(value = "shopCategorysService")
public class ShopCategorysServiceImpl implements ShopCategorysService {

    @Resource
    private ShopCategoryDao shopCategoryDao;

    public ShopCategorys queryByPrimaryKey(long categoryId) throws SQLException {
        return shopCategoryDao.queryByPrimaryKey(categoryId);
    }

    @Override
    public List<ShopCategorys> getAllShopCategorysForTree(Long shopId)
            throws Exception {
        ShopCategorys condition = new ShopCategorys();
        condition.setShopId(shopId);
        condition.setParentCategoryId(Long.valueOf(0));
        return shopCategoryDao.queryShopCategoryForTree(condition);
    }

    @Override
    public List<ShopCategorys> queryShopCategoryListByParentId(
            long parentId) throws Exception {
        ShopCategorys condition = new ShopCategorys();
        condition.setParentCategoryId(parentId);
        return shopCategoryDao.queryCategoryByParentId(condition);
    }

    @Override
    public ShopCategorys queryShopCategoryById(long shopCategoryId)
            throws Exception {
        return shopCategoryDao.queryByPrimaryKey(shopCategoryId);
    }

    @Override
    public List<ShopCategorys> querySuggestShopCategoryForTree(long shopId)
            throws Exception {
        ShopCategorys condition = new ShopCategorys();
        condition.setShopId(shopId);
        condition.setIsSuggest(1);
        return shopCategoryDao.queryShopCategoryForTree(condition);
    }

    @Override
    public List<ShopCategorys> getAllShopCategorysForList(Long shopId)
            throws Exception {
        ShopCategorys condition = new ShopCategorys();
        condition.setShopId(shopId);
        return shopCategoryDao.queryShopCategoryForList(condition);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateIsExpandById(String[] idArrays, long shopId) throws Exception {
        ShopCategorys condition = new ShopCategorys();
        condition.setShopId(shopId);
        condition.setIsExpandAll("0");
        if (idArrays == null) {
            shopCategoryDao.updateExpandByShopId(condition);
            return;
        }
        shopCategoryDao.updateExpandByShopId(condition);
        shopCategoryDao.updateIsExpandBatchById(idArrays);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateSuggestBatchId(String[] idArrays, long shopId, String isExpand)
            throws Exception {
        ShopCategorys condition = new ShopCategorys();
        condition.setShopId(shopId);
        condition.setIsExpandAll(isExpand);
        shopCategoryDao.updateExpandByShopId(condition);

        condition.setIsSuggest(0);
        shopCategoryDao.updateSuggestByShopId(condition);
        //不为空,则更新其推荐状态
        if (idArrays != null) {
            shopCategoryDao.updateIsSuggestBatchById(idArrays);
        }

    }

    @Override
    public List<ShopCategorys> queryShopCategoryList(ShopCategorys shopCategorys) throws Exception {
        // TODO Auto-generated method stub

        return shopCategoryDao.queryShopCategoryList(shopCategorys);
    }

}
