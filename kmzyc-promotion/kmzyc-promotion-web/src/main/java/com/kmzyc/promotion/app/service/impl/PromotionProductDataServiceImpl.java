package com.kmzyc.promotion.app.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.kmzyc.promotion.app.bean.ResultMessage;
import com.kmzyc.promotion.app.dao.PromotionProductDataDAO;
import com.kmzyc.promotion.app.service.PromotionProductDataService;
import com.kmzyc.promotion.exception.ServiceException;
import com.kmzyc.promotion.optimization.vo.PromotionProductData;

@Service("PromotionProductDataService")
public class PromotionProductDataServiceImpl implements PromotionProductDataService {
    @Resource
    private PromotionProductDataDAO promotionProductDataDAO;

    protected Logger logger = LoggerFactory.getLogger(PromotionProductDataServiceImpl.class);

    @Override
    public List<JSONObject> queryGantProduct(Long promotionId, Long skuId) throws ServiceException {
        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put("promotionId", String.valueOf(promotionId));
            map.put("productSkuId", String.valueOf(skuId));
            return promotionProductDataDAO.queryGantProduct(map);
        } catch (SQLException e) {
            logger.error("查询附赠商品信息异常", e);
            return null;
        }
    }

    @Override
    public List<PromotionProductData> queryListByPromotionId(Long promotionId)
            throws ServiceException {
        try {
            return promotionProductDataDAO.queryListByPromotionId(promotionId);
        } catch (SQLException e) {
            logger.error("查询附赠商品信息异常", e);
            return null;
        }
    }

    @Override
    public List<PromotionProductData> queryListBypromotionProductId(Long promotionProductId)
            throws ServiceException {
        try {
            return promotionProductDataDAO.queryListBypromotionProductId(promotionProductId);
        } catch (SQLException e) {
            logger.error("根据promotionProductId查询附赠商品信息异常", e);
            return null;
        }
    }

    @Override
    public void addPromotionProductDataList(List<PromotionProductData> resultList)
            throws ServiceException {
        try {
            promotionProductDataDAO.addPromotionProductDataList(resultList);
        } catch (SQLException e) {
            logger.error("添加附赠商品信息异常", e);
        }

    }

    @Override
    public ResultMessage updatePromotionProductData(PromotionProductData promotionProductData)
            throws ServiceException {
        ResultMessage rmsg = new ResultMessage();
        rmsg.setMark(0);
        rmsg.setIsSuccess(true);
        try {
            int count = promotionProductDataDAO.updateByPrimaryKeySelective(promotionProductData);
            if (count < 1) {
                rmsg.setIsSuccess(false);
                rmsg.setMark(3);
                return rmsg;
            }

        } catch (Exception e) {
            logger.error("updatePromotionProductData异常：", e);
            rmsg.setMark(3);
            rmsg.setIsSuccess(false);
        }
        return rmsg;
    }

    @Override
    public ResultMessage deletePromotionProductData(PromotionProductData promotionProductData)
            throws ServiceException {
        ResultMessage rmsg = new ResultMessage();
        rmsg.setMark(0);
        rmsg.setIsSuccess(true);
        try {
            int count = promotionProductDataDAO
                    .deletePromotionProductData(promotionProductData.getPromotionProductDataId());
            if (count < 1) {
                rmsg.setIsSuccess(false);
                rmsg.setMark(3);
                return rmsg;
            }

        } catch (Exception e) {
            logger.error("deletePromotionProductData异常：", e);
            rmsg.setMark(3);
            rmsg.setIsSuccess(false);
        }
        return rmsg;
    }
}
