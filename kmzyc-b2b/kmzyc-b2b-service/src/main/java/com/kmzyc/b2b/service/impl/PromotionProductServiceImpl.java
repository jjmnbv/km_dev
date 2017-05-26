package com.kmzyc.b2b.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kmzyc.b2b.dao.PromotionProductDao;
import com.kmzyc.b2b.model.PromotionInfo;
import com.kmzyc.b2b.model.PromotionProduct;
import com.kmzyc.b2b.service.PromotionProductService;
import com.kmzyc.b2b.vo.CarProduct;
import com.kmzyc.framework.exception.ServiceException;
import com.km.framework.page.Pagination;

@Service("promotionProductService")
public class PromotionProductServiceImpl implements PromotionProductService {
    @Resource
    PromotionProductDao promotionProductDao;

    @Override
    public Pagination getPromotionProductList(Pagination page, PromotionInfo promotion) {
        page = promotionProductDao.getPromotionProduct(page, promotion);
        return page;
    }

    /**
     * 获取加价购商品
     * 
     * @param prizeData 活动规则奖励数据
     */
    public Map<Long, CarProduct> getIncreaseProduct(Long prizeData) throws ServiceException {

        try {
            return promotionProductDao.getIncreaseProduct(prizeData);
        } catch (SQLException e) {
            throw new ServiceException(0, "获取加价购商品发生异常", e);
        }
    }

    /**
     * 获取赠品
     * 
     * @param promotionId 活动ID
     * @return
     */
    public Map<Long, CarProduct> getGiftProductByPromotion(Long promotionId)
            throws ServiceException {

        try {
            return promotionProductDao.getGiftProductByPromotion(promotionId);
        } catch (SQLException e) {

            throw new ServiceException(0, "获取赠品商品发生异常", e);
        }
    }

    /**
    * 
  */
    @Override
    public List<PromotionProduct> getPromotionProductBySku(HashMap map) throws SQLException {

        return promotionProductDao.getPromotionProductBySku(map);
    }

}
