package com.kmzyc.promotion.app.dao.impl;


import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSONObject;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.promotion.app.dao.BaseDao;
import com.kmzyc.promotion.app.dao.PromotionProductDataDAO;
import com.kmzyc.promotion.app.vobject.PromotionProduct;
import com.kmzyc.promotion.app.vobject.PromotionProductDataExample;
import com.kmzyc.promotion.optimization.vo.PromotionProductData;


@Repository("promotionProductDataDAO")
@SuppressWarnings({"unchecked", "unused"})
public class PromotionProductDataDAOImpl extends BaseDao<PromotionProductData>
    implements
      PromotionProductDataDAO {

  @Override
  public void addPromotionProductDataList(List<PromotionProductData> promotionProductDataList)
      throws SQLException {

    super.batchInsertData(promotionProductDataList,
        "PROMOTION_PRODUCT_DATA.ibatorgenerated_insertSelective");

  }

  @Resource
  private SqlMapClient sqlMapClient;

  @Override
  public List<JSONObject> queryGantProduct(Map<String, String> map) throws SQLException {

    return this.sqlMapClient.queryForList("PROMOTION_RULE_DATA.queryGantProduct", map);
  }

  @Override
  public List<PromotionProductData> queryListByPromotionId(Long promotionId) throws SQLException {

    HashMap map = new HashMap();
    map.put("promotionId", promotionId);
    return this.sqlMapClient.queryForList("PROMOTION_PRODUCT_DATA.queryGantProductListByMap", map);
  }

  @Override
  public List<PromotionProductData> queryListBypromotionProductId(Long promotionProductId)
      throws SQLException {

    HashMap map = new HashMap();
    map.put("promotionProductId", promotionProductId);
    return this.sqlMapClient.queryForList(
        "PROMOTION_PRODUCT_DATA.queryGantProductListByPromotionProductId", map);
  }

  public int updateByPrimaryKeySelective(PromotionProductData record) throws SQLException {
    int rows =
        sqlMapClient.update("PROMOTION_PRODUCT_DATA.ibatorgenerated_updateByPrimaryKeySelective",
            record);
    return rows;
  }

  public int deletePromotionProductData(Long promotionProductDataId) throws SQLException {
    PromotionProductData key = new PromotionProductData();
    key.setPromotionProductDataId(promotionProductDataId);
    int rows =
        sqlMapClient.delete("PROMOTION_PRODUCT_DATA.ibatorgenerated_deleteByPrimaryKey", key);
    return rows;
  }

  @Override
  public Integer selectCountByPromotionId(Long promotionId) throws SQLException {
    Integer rows =
        (Integer) sqlMapClient.queryForObject(
            "PROMOTION_PRODUCT_DATA.getPromotionProductDataCount", promotionId);
    return rows;
  }

  @Override
  public void copyPromotionProductData(Long newId, Long promotionProductId, Long productSkuId,
      Long oldId) throws SQLException {
    Map<String, Long> map = new HashMap<String, Long>();
    map.put("newPromotionId", newId);
    map.put("oldPromotionId", oldId);
    map.put("promotionProductId", promotionProductId);
    map.put("productSkuId", productSkuId);

    sqlMapClient.insert("PROMOTION_PRODUCT_DATA.copyPromotionProductData", map);// 复制活动产品数据表

  }

  @Override
  public void copyPromotionProductDataByPromotionId(Long newId, Long promotionId)
      throws SQLException {
    Map<String, Long> map = new HashMap<String, Long>();
    map.put("newPromotionId", newId);
    map.put("oldPromotionId", promotionId);

    sqlMapClient.insert("PROMOTION_PRODUCT_DATA.copyPromotionProductDataByPromotionId", map);// 复制活动产品数据表

  }

  /**
   * 根据条件批量删除
   */
  public int deleteByExample(PromotionProductDataExample example) throws SQLException {
    int rows =
        sqlMapClient.delete("PROMOTION_PRODUCT_DATA.ibatorgenerated_deleteByExample", example);
    return rows;
  }

  @Override
  public int deleteByPromotionId(Long promotionId) throws SQLException {
    PromotionProductData key = new PromotionProductData();
    key.setPromotionId(promotionId);
    int rows =
        sqlMapClient.delete("PROMOTION_PRODUCT_DATA.ibatorgenerated_deleteByPromotionId", key);
    return rows;
  }

  public void updatePPdStatusByPPId(PromotionProduct record) throws SQLException {
    sqlMapClient.update("PROMOTION_PRODUCT_DATA.ibatorgenerated_updateByPPd", record);
  }

  @Override
  public PromotionProduct queryByPromotionProductId(Long promotionProductId) throws SQLException {
    return (PromotionProduct) sqlMapClient.queryForObject("PROMOTION_PRODUCT.getPromotionProduct",
        promotionProductId);

  }
}
