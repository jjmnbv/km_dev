package com.kmzyc.supplier.service.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.supplier.model.ShopCategorys;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.supplier.dao.ShopCategoryDao;
import com.kmzyc.supplier.service.ShopCategoryService;
import com.kmzyc.supplier.util.CodeUtils;

import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service("shopCategoryService")
public class ShopCategoryServiceImpl implements ShopCategoryService {

    @Resource(name = "shopCategoryDao")
    private ShopCategoryDao shopCategoryDao;

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public ShopCategorys saveShopCategory(ShopCategorys para) throws ServiceException {
        para.setCreateTime(new Date());
        para.setStatus(1); // 默认为有效的

        // 新增一级类目
        if (1 == para.getCategoryLevel()) {
            para.setParentCategoryId(0l);
        }

        try {
            // 查询该父级类目最大的子级类目编号 如果是一级类目,则为undefined
            String maxCode = shopCategoryDao.queryMaxChildCodeByParentId(para.getParentCategoryId());
            if (StringUtils.isEmpty(maxCode)) {
                // 默认一级类目从01开始
                if (para.getCategoryCode() == null || para.getCategoryCode().isEmpty()) {
                    para.setCategoryCode("01");
                    // 二级类目从 父编码的两位数 +01 类似于0101
                } else if (para.getCategoryCode().length() == 2) {
                    para.setCategoryCode(para.getCategoryCode() + "01");
                    // 三级类目 父编码的四位数+后面序列号 类似于010101 暂时店内分类没有三级类目,可做扩展
                } else if (para.getCategoryCode().length() == 4) {
                    para.setCategoryCode(para.getCategoryCode() + "01");
                }
            } else {
                // 若该项目已有子级类目,则将子级类目的编码往上递增+1
                para.setCategoryCode(CodeUtils.getCode(maxCode));
            }

            Object obj = shopCategoryDao.insert(para);
            if (obj != null) {
                return queryCategoryById((Long) obj);
            }
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
        return null;
    }

    @Override
    public ShopCategorys queryCategoryById(long categoryId) throws ServiceException {
        try {
            return shopCategoryDao.queryByPrimaryKey(categoryId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    public String queryShopCategoryName(List<Long> shopCategoryIdList) throws ServiceException {
        try {
            return shopCategoryDao.queryShopCategoryName(shopCategoryIdList);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ShopCategorys> queryShopCategoryList(ShopCategorys condition) throws ServiceException {
        try {
            return shopCategoryDao.queryShopCategoryList(condition);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean queryIsExistCategoryName(ShopCategorys condition) throws ServiceException {
        try {
            Integer result = shopCategoryDao.queryExistRepeatCategoryName(condition);
            return result > 0;
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void updateShopCategoryList(ShopCategorys para) throws ServiceException {
        try {
            shopCategoryDao.udpateShopCategory(para);
            // 如果修改的一级类目并且修改为未推荐,则其下的所有子级类目全部做更新
            if (1 == para.getCategoryLevel()) {
                shopCategoryDao.updateIsSuggestByParent(para);
            }
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public String isRelatedWithProduct(ShopCategorys condition) throws ServiceException {
        try {
            Integer count = shopCategoryDao.queryIsRelationWithOfficial(condition);
            if (count != null && count > 0) {
                return "official";
            }
            count = shopCategoryDao.queryIsRelationWithDraft(condition);
            if (count != null && count > 0) {
                return "draft";
            }
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
        return "none";
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void deleteShopCategory(ShopCategorys para) throws ServiceException {
        try {
            shopCategoryDao.deleteShopCategory(para);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ShopCategorys> queryShopCategoryByParentId(long parentId, long shopId) throws ServiceException {
        ShopCategorys condition = new ShopCategorys();
        condition.setParentCategoryId(parentId);
        condition.setShopId(shopId);
        try {
            return shopCategoryDao.queryCategoryByParentId(condition);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void updateShopCategorySortNo(long sourceCategoryId, int sourceSortNo, long targetCategoryId, int targetSortNo)
            throws ServiceException {
        try {
            shopCategoryDao.updateSortNoByCategoryId(sourceCategoryId, targetSortNo);
            shopCategoryDao.updateSortNoByCategoryId(targetCategoryId, sourceSortNo);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean queryIsExistSortNo(ShopCategorys para) throws ServiceException {
        try {
            Integer count = shopCategoryDao.queryIsExistSortNo(para);
            boolean isExist = false;
            if (count > 0) {
                isExist = true;
            }
            return isExist;
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ShopCategorys queryDefaultShopCategoryByShopId(long shopId) throws ServiceException {
        try {
            return shopCategoryDao.queryDefaultShopCategory(shopId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean queryIsExistShopCategoryCreateBySelf(long shopId) throws ServiceException {
        try {
            Integer count = shopCategoryDao.isExistShopCateCreateBySelf(shopId);
            return count != null && count > 0;
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public Map<String, String> addDefaultShopCategoryForShop(Long shopId, Long loginUserId)
            throws ServiceException {
        Map<String, String> returnMap = new HashMap<String, String>();
        // 默认添加成功
        String returnCode = "returnCode";
        String returnResult = "0_添加商品页为shopId=" + shopId + "添加默认店内分类成功";
        try {
            if (shopId == null) {
                returnResult = "0_shopId为空,店铺并不存在!";
                returnMap.put(returnCode, returnResult);
                return returnMap;
            }

            // 如果有自己的二级分类,则不添加默认分类
            if (queryIsExistShopCategoryCreateBySelf(shopId)) {
                returnResult = "2_shopId已经存在自己的二级分类,不给其默认分类!";
                returnMap.put(returnCode, returnResult);
                return returnMap;
            }

            // 先查询该店铺是否存在默认分类
            Integer count = shopCategoryDao.queryIsExistDefaultCategory(shopId);
            if (count != null && count > 0) {
                returnResult = "1_shopId=" + shopId + "已经存在默认店内分类";
                returnMap.put(returnCode, returnResult);
                return returnMap;
            }

            // 没有则添加默认分类(包含顶级和子级,这个默认分类可以编辑,但是不可以删除)
            boolean isAddSuccess = true; // 是否添加成功
            ShopCategorys paraParent = afterSetSomeProperty(shopId, Constants.DEFAULT_SHOP_CAGEORY_LEVEL_FIRST,
                    loginUserId, Constants.DEFAULT_SHOP_CAGEORY_PARENTID);
            Long shopCategoryId = shopCategoryDao.insert(paraParent);
            if (shopCategoryId != null) {
                // 为其添加默认的子级分类
                ShopCategorys childCategory = afterSetSomeProperty(shopId, Constants.DEFAULT_SHOP_CAGEORY_LEVEL_SECOND,
                        loginUserId, shopCategoryId);
                shopCategoryId = shopCategoryDao.insert(childCategory);
                if (shopCategoryId != null) {
                    log.info(returnResult);
                } else {
                    isAddSuccess = false;
                }
            } else {
                isAddSuccess = false;
            }

            if (!isAddSuccess) {
                returnResult = "-1_shopId=" + shopId + "添加商品页系统分配顶级分类发生异常!";
            }
        } catch (SQLException e) {
            returnResult = "-1_shopId=" + shopId + "添加商品页系统分配默认分类发生异常!";
            log.error(returnResult, e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        } catch (ServiceException e) {
            returnResult = "-2_shopId=" + shopId + "添加商品页系统查询是否有自己的二级分类发生异常!";
            log.error(returnResult, e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        returnMap.put(returnCode, returnResult);
        return returnMap;

    }

    private ShopCategorys afterSetSomeProperty(long shopId, int categoryLevel, long loginUserId, long parentCategoryId)
            throws ServiceException {
        // 没有则添加默认分类(包含顶级和子级,这个默认分类可以编辑,但是不可以删除)
        ShopCategorys para = new ShopCategorys();
        para.setShopId(shopId);
        para.setCreateTime(new Date());
        para.setCategoryLevel(categoryLevel);
        para.setIsDefault(Constants.SHOP_CAGEORY_DEFAULT);
        para.setIsExpandAll(Constants.DEFAULT_SHOP_CAGEORY_EXPAND);
        para.setIsSuggest(Constants.DEFAULT_SHOP_CAGEORY_SUGGEST);
        para.setCategoryName(Constants.DEFAULT_SHOP_CAGEORY_NAME);
        para.setSortno(Constants.DEFAULT_SHOP_CAGEORY_SORTNO);
        para.setModifUser(loginUserId);
        para.setStatus(Constants.DEFAULT_SHOP_CAGEORY_STATUS); // 默认为有效的
        para.setParentCategoryId(parentCategoryId);

        try {
            // 查询该父级类目最大的子级类目编号 如果是一级类目,则为undefined
            String maxCode = shopCategoryDao.queryMaxChildCodeByParentId(para.getParentCategoryId());
            if (StringUtils.isEmpty(maxCode)) {
                // 默认一级类目从01开始
                if (para.getCategoryCode() == null || para.getCategoryCode().isEmpty()) {
                    para.setCategoryCode("01");
                    // 二级类目从 父编码的两位数 +01 类似于0101
                } else if (para.getCategoryCode().length() == 2) {
                    para.setCategoryCode(para.getCategoryCode() + "01");
                    // 三级类目 父编码的四位数+后面序列号 类似于010101 暂时店内分类没有三级类目,可做扩展
                } else if (para.getCategoryCode().length() == 4) {
                    para.setCategoryCode(para.getCategoryCode() + "01");
                }
            } else {
                // 若该项目已有子级类目,则将子级类目的编码往上递增+1
                para.setCategoryCode(CodeUtils.getCode(maxCode));
            }
            return para;
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }
}
