package com.kmzyc.promotion.app.dao;


import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSONObject;
import com.kmzyc.promotion.app.vobject.PromotionProduct;
import com.kmzyc.promotion.app.vobject.PromotionProductDataExample;
import com.kmzyc.promotion.optimization.vo.PromotionProductData;


@Repository
public interface PromotionProductDataDAO {

  /**
   * 批量增加附赠赠品
   * 
   * @param promotionProductDataList
   * @throws SQLException
   */
  public void addPromotionProductDataList(List<PromotionProductData> promotionProductDataList)
      throws SQLException;

  public List<JSONObject> queryGantProduct(Map<String, String> map) throws SQLException;

  public List<PromotionProductData> queryListByPromotionId(Long promotionId)throws SQLException;

  public List<PromotionProductData> queryListBypromotionProductId(Long promotionProductId)throws SQLException;

  public int updateByPrimaryKeySelective(PromotionProductData promotionProductData)throws SQLException;

  public int deletePromotionProductData(Long promotionProductDataId)throws SQLException;

  public Integer selectCountByPromotionId(Long promotionId)throws SQLException;
  /**
   * 复制附赠活动赠品  （无统一赠品时）
   * @param newId 复制的新活动id
   * @param promotionProductId  新 promotionProductId 
   * @param productSkuId 活动商品skuId
   * @param oldId 老活动id
   * @throws SQLException
   * add by songmiao 2015/11/23
   */
  public void copyPromotionProductData(Long newId, Long promotionProductId,Long productSkuId,Long oldId)throws SQLException;
  /**
   * 复制附赠活动赠品  （有统一赠品时）
   * @param newId
   * @param promotionId
   * add by songmiao 2015/11/24
   */
  public void copyPromotionProductDataByPromotionId(Long newId, Long promotionId) throws SQLException;
  /**
   * 根据条件批量删除
   * @param example
   * @return
   * @throws SQLException
   * add by songmiao 2015/11/24 
   */
  int deleteByExample(PromotionProductDataExample example) throws SQLException;

  public int  deleteByPromotionId(Long promotionId)throws SQLException;

  public void updatePPdStatusByPPId(PromotionProduct pp)throws SQLException;

  public PromotionProduct queryByPromotionProductId(Long promotionProductId)throws SQLException;
}
