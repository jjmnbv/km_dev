package com.kmzyc.promotion.app.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.kmzyc.promotion.app.bean.ResultMessage;
import com.kmzyc.promotion.exception.ServiceException;
import com.kmzyc.promotion.optimization.vo.PromotionProductData;

public interface PromotionProductDataService {

  /**
   * 查询附赠信息
   * 
   * @param promotionId
   * @param skuId
   * @return
   */
  public List<JSONObject> queryGantProduct(Long promotionId, Long skuId) throws ServiceException;

  public List<PromotionProductData> queryListByPromotionId(Long promotionId)
      throws ServiceException;

  public List<PromotionProductData> queryListBypromotionProductId(Long promotionProductId)throws ServiceException;

  public void addPromotionProductDataList(List<PromotionProductData> resultList)throws ServiceException;

  public ResultMessage updatePromotionProductData(PromotionProductData promotionProductData)throws ServiceException;

  public ResultMessage deletePromotionProductData(PromotionProductData promotionProductData)throws ServiceException;

}
