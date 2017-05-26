package com.kmzyc.supplier.service.impl;

import com.google.common.collect.Maps;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.supplier.model.ShopCategorys;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.supplier.dao.ShopProductCategoryDAO;
import com.kmzyc.supplier.model.ShopProductCategory;
import com.kmzyc.supplier.model.example.ShopProductCategoryExample;
import com.kmzyc.supplier.service.ShopCategoryService;
import com.kmzyc.supplier.service.ShopProductCategoryService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

@Service("shopProductCategoryService")
public class ShopProductCategoryServiceImpl implements ShopProductCategoryService {

    @Resource
    private ShopProductCategoryDAO shopProductCategoryDao;

    @Resource
    private ShopCategoryService shopCategoryService;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void insert(ShopProductCategory para) throws ServiceException {
        try {
            shopProductCategoryDao.insertSelective(para);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void deleteByProductId(long productId) throws ServiceException {
        ShopProductCategoryExample example = new ShopProductCategoryExample();
        example.createCriteria().andProductIdEqualTo(productId);
        try {
            shopProductCategoryDao.deleteByExample(example);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void updateByProductId(long productId, String[] shopCategoryIdArray) throws ServiceException {
        deleteByProductId(productId);

        insert(productId, shopCategoryIdArray);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void insert(long productId, String[] shopCategoryIdArray) throws ServiceException {
        if (ArrayUtils.isEmpty(shopCategoryIdArray)) {
            return;
        }

        for (int i = 0; i < shopCategoryIdArray.length; i++) {
            ShopProductCategory para = new ShopProductCategory();
            para.setProductId(productId);
            para.setShopCategoryId(Long.valueOf(shopCategoryIdArray[i]));
            insert(para);
        }
    }

    @Override
    public List<ShopProductCategory> queryByProductId(long productId) throws ServiceException {
        ShopProductCategoryExample example = new ShopProductCategoryExample();
        example.createCriteria().andProductIdEqualTo(productId);
        try {
            return shopProductCategoryDao.selectByExample(example);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Map<String, String> getProductShopCategory(Long productId, Boolean needShopCategoryId) throws ServiceException {
        List<ShopProductCategory> list = queryByProductId(productId);
        Map<String, String> result = Maps.newHashMap();
        if (CollectionUtils.isNotEmpty(list)) {
            List<Long> shopCategoryIdList = list.stream().map(category -> category.getShopCategoryId()).collect(Collectors.toList());
            if (needShopCategoryId) {
                result.put("shopCategoryId", StringUtils.join(shopCategoryIdList, ","));
            }

            result.put("shopCategoryName", shopCategoryService.queryShopCategoryName(shopCategoryIdList));
        }

        return result;
    }

    @Override
    public void handleDefaultShopCategory(ShopCategorys defaultShopCategory, Boolean isAdd, Map<String, String> result)
            throws ServiceException {
        String defaultShopCateId = "-1";
        String defaultShopCateName = "";

        if (defaultShopCategory != null && CollectionUtils.isNotEmpty(defaultShopCategory.getShopCategoryChildrenList())) {
            for (ShopCategorys children : defaultShopCategory.getShopCategoryChildrenList()) {
                if (children != null && Constants.IS_DEFAULT_FOR_SHOPCATEGORY.equals(children.getIsDefault())) {
                    defaultShopCateId = children.getShopCategoryId().toString();
                    defaultShopCateName = children.getCategoryName();
                    break;
                }
            }
        }

        if (isAdd) {//进入新增时
            result.put("shopCategoryId", defaultShopCateId);
        } else if (StringUtils.isBlank(result.get("shopCategoryId"))) {//进入修改且商品的店铺分类id为空时
            result.put("shopCategoryId", defaultShopCateId);
            result.put("shopCategoryName", defaultShopCateName);
        }
        result.put("defaultShopCateId", defaultShopCateId);
        result.put("defaultShopCateName", defaultShopCateName);
    }

}