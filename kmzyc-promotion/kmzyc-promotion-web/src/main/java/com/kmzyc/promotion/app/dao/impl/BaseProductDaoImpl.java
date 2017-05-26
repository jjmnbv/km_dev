package com.kmzyc.promotion.app.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.km.framework.page.Pagination;
import com.km.framework.persistence.impl.DaoImpl;
import com.kmzyc.promotion.app.dao.BaseProductDao;
import com.kmzyc.promotion.app.vobject.BaseProduct;
import com.kmzyc.promotion.app.vobject.PromotionInfo;

@Component("baseProductDao")
@SuppressWarnings("unchecked")
public class BaseProductDaoImpl extends DaoImpl implements BaseProductDao {

  @Resource
  private SqlMapClient sqlMapClient;

  /**
   * 获取加价购商品价格
   * 
   * @param promotionId
   * @return
   * @throws SQLException
   */
  @Override
  public List<BaseProduct> getAddPriceCarProductInfoList(Long promotionId) throws SQLException {
    return sqlMapClient.queryForList("BaseProduct.getAddPriceCarProductInfoList", promotionId);
  }

  /**
   * 获取赠品
   * 
   * @param promotionId
   * @return
   * @throws SQLException
   */
  @Override
  public List<BaseProduct> getGiftProduct(Long promotionId) throws SQLException {
    return sqlMapClient.queryForList("BaseProduct.getGiftProduct", promotionId);
  }

  /**
   * 获取单个产品活动、价格、产品详细页
   * 
   * @param skuId
   * @return
   * @throws SQLException
   */
  @Override
  public BaseProduct getProductPriceInfo(Long skuId) throws SQLException {
    return (BaseProduct) sqlMapClient.queryForObject("BaseProduct.getProductPriceInfo", skuId);
  }

  /**
   * 分页查询促销产品价格
   * 
   * @param page
   * @param promotion
   * @return
   * @throws SQLException
   */
  @Override
  public Pagination getPromotionProduct(Pagination page, PromotionInfo promotion)
      throws SQLException {
    Integer selectProductType = promotion.getProductFilterType();
    Map<String, Object> conditionMap = new HashMap<String, Object>();
    String sql = "";
    String countSql = "";
    sql = "PROMOTION_PRODUCT.getProductInfo";
    countSql = "PROMOTION_PRODUCT.getProductInfoCount";
    String promotionSelectSql =
        promotion.getProductFilterSql() == null ? "" : promotion.getProductFilterSql();
    int start = 0, end = promotionSelectSql.length();
    if (promotionSelectSql.startsWith(",")) {
      start = start + 1;
    }
    if (promotionSelectSql.endsWith(",")) {
      end = end - 1;
    }
    if (promotion.getShopSort() == 2) {
      conditionMap.put("shopCodes", promotion.getSupplierId().toString());
    } else if (promotion.getShopSort() == 3) {
      // 康美自营代销
      conditionMap.put("shopCodes", "1");
    }
    promotionSelectSql = promotionSelectSql.substring(start, end);
    switch (selectProductType.intValue()) {
      case 1:
        break;
      case 2:
        sql = "PROMOTION_PRODUCT.getProductSkuIdBySku";
        countSql = "PROMOTION_PRODUCT.getProductSkuIdBySkuCount";
        conditionMap.put("promotionId", promotion.getPromotionId());
        break;
      case 3:
        conditionMap.put("categoryIds", promotionSelectSql.split(","));
        break;
      case 4:
        conditionMap.put("brandIds", promotionSelectSql.split(","));
        break;
      case 5:
        conditionMap.put("shopCodes", promotionSelectSql.split(","));
        break;
      default:
        break;
    }
    page.setObjCondition(conditionMap);
    page = super.findByPage(sql, countSql, page);
    return page;
  }

  /**
   * 批量获取产品价格信息
   * 
   * @param idList
   * @return
   * @throws SQLException
   */
  @Override
  public List<BaseProduct> queryBatchProductPriceInfo(List<Long> idList) throws SQLException {
    if (null == idList || idList.isEmpty()) {
      return null;
    }
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("suitIds", idList);
    return sqlMapClient.queryForList("BaseProduct.SQL_QUERY_BATCH_PRODUCT_PRICE_INFO", params);
  }

  /**
   * 查询套餐
   * 
   * @param params
   * @return
   * @throws SQLException
   */
  @Override
  public List<Map<String, String>> queryAbleComposition(Long suitId, Integer amount)
      throws SQLException {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("suitId", suitId);
    return sqlMapClient.queryForList("BaseProduct.SQL_QUERY_ABLE_COMPOSITION", params);
  }

  /**
   * 批量查询套餐
   * 
   * @param params
   * @return
   * @throws SQLException
   */
  @Override
  public List<Map<String, String>> queryBatchAbleComposition(Map<Long, Integer> suitIds)
      throws SQLException {
    Map<String, Object> params = new HashMap<String, Object>();
    List<Long> ids = new ArrayList<Long>(suitIds.keySet());
    params.put("suitIds", ids);
    return sqlMapClient.queryForList("BaseProduct.SQL_QUERY_BATCH_ABLE_COMPOSITION", params);
  }

  /**
   * 查询活动期间用户购买某产品数量
   * 
   * @param params
   * @return
   * @throws SQLException
   */
  @Override
  public Integer querySumUserBuySkuNum(Map<String, Object> params) throws SQLException {
    Object obj = sqlMapClient.queryForObject("BaseProduct.SQL_QUERY_SUM_USER_BUY_SKU_NUM", params);
    return obj == null ? 0 : Integer.parseInt(obj.toString());
  }

  /**
   * 批量获取产品价格
   * 
   * @param skuId
   * @return
   * @throws SQLException
   */
  @Override
  public BaseProduct queryProductPrice(Long skuId) throws SQLException {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("skuId", skuId);
    return (BaseProduct) sqlMapClient.queryForObject("BaseProduct.SQL_QUERY_PRODUCT_PRICE", params);
  }

  /**
   * 批量获取产品价格
   * 
   * @param idList
   * @return
   * @throws SQLException
   */
  @Override
  public List<BaseProduct> queryBatchProductPrice(List<Long> idList) throws SQLException {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("idList", idList);
    return sqlMapClient.queryForList("BaseProduct.SQL_QUERY_BATCH_PRODUCT_PRICE", params);
  }
}
