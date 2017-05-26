package com.kmzyc.supplier.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.bean.ResultMessage;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kmzyc.supplier.model.SuppliersAvailableCategorys;
import com.kmzyc.supplier.dao.CategoryDAO;
import com.kmzyc.supplier.service.CategoryService;
import com.pltfm.app.vobject.Category;
import com.kmzyc.promotion.app.vobject.PromotionInfo;

/**
 * 产品类别业务逻辑类
 */
@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private CategoryDAO categoryDAO;

    @Override
    public List<Category> queryCategoryParentList(Category category) throws ServiceException {
        try {
            return categoryDAO.queryCategoryParentList(category);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Category> queryCategoryChildrenList(Category category, List<SuppliersAvailableCategorys> categoryList)
            throws ServiceException {
        Map paramMap = new HashMap();
        try {
            paramMap.put("parentId", category.getCategoryId());
            if (categoryList != null) {
                paramMap.put("categoryList", categoryList);
            }
            return categoryDAO.selectByExample(paramMap);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Category> queryCategoryList(Category category) throws ServiceException {
        try {
            return categoryDAO.queryCategoryList(category);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Category> selectCategoryWithSupplyAvailable(Map<String, Object> map) throws ServiceException {
        try {
            return categoryDAO.selectCategoryWithSupplyAvailable(map);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Integer promotionIsLock(PromotionInfo promotion) throws ServiceException {
        try {
            return categoryDAO.promotionIsLock(promotion);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ResultMessage checkSkuPvValue(Double skuPrice, Double skuPvValue, Long categoryId) throws ServiceException {
        ResultMessage resultMessage = new ResultMessage();
        try {
            BigDecimal rebate = categoryDAO.getCategoryPvDefaultRebate(categoryId);
            //pv比例不为空
            if (rebate != null && !checkSinglePvValue(skuPvValue, skuPrice, rebate, resultMessage)) {
                return resultMessage;
            }
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
        resultMessage.setIsSuccess(Boolean.TRUE);
        return resultMessage;
    }

    private Double computeMaxPvValue(Double skuPrice, BigDecimal rebate) {
        return rebate.multiply(new BigDecimal(skuPrice))
                .divide(new BigDecimal(1), 1, BigDecimal.ROUND_HALF_UP)
                .doubleValue();
    }

    private boolean checkSinglePvValue(Double pvValue, Double price, BigDecimal rebate, ResultMessage resultMessage) {
        double maxPvValue = computeMaxPvValue(price, rebate);
        if (pvValue == 1) {
            return Boolean.TRUE;
        }else if (pvValue < 1 || (pvValue > maxPvValue && maxPvValue > 1)) {
            resultMessage.setIsSuccess(Boolean.FALSE);
            resultMessage.setMessage("推广服务费取值范围为：1<=推广服务费<=销售价("+price
                    +")*当前类目推广服务费比例("+(rebate.doubleValue()*100)
                    +"%)，必须<="+maxPvValue);
            return Boolean.FALSE;
        } else if (maxPvValue < 1 && pvValue > 1) {
            resultMessage.setIsSuccess(Boolean.FALSE);
            resultMessage.setMessage("该商品的推广服务费为1，因当前销售价("+price
                    +")*当前类目推广服务费比例("+(rebate.doubleValue()*100)
                    +"%)=("+maxPvValue+"),推广服务费不能小于1！");
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    @Override
    public ResultMessage checkAllSkuPvValue(String[] skuPrice, String[] skuPvValue, Long categoryId)
            throws ServiceException {
        ResultMessage resultMessage = new ResultMessage();
        try {
            BigDecimal rebate = categoryDAO.getCategoryPvDefaultRebate(categoryId);
            //pv比例不为空
            if (rebate == null || skuPrice == null || skuPvValue == null || skuPrice.length != skuPvValue.length) {
                log.error("无法校验所有sku的pv，没有找到相关数据categoryId{},rebate{},skuPrice{},skuPvValue{}",
                        new Object[]{categoryId, rebate, skuPrice, skuPvValue});
                resultMessage.setIsSuccess(Boolean.TRUE);
                return resultMessage;
            }

            int length = skuPrice.length;
            for (int i = 0; i < length; i++) {
                if (!checkSinglePvValue(Double.valueOf(skuPvValue[i]), Double.valueOf(skuPrice[i]), rebate, resultMessage)) {
                    return resultMessage;
                }
            }
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
        resultMessage.setIsSuccess(Boolean.TRUE);
        return resultMessage;
    }
}
