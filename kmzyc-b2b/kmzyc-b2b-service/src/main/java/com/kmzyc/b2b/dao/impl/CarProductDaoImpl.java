package com.kmzyc.b2b.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.km.framework.persistence.impl.DaoImpl;
import com.kmzyc.b2b.dao.CarProductDao;
import com.kmzyc.b2b.vo.CarProduct;
import com.kmzyc.framework.exception.ServiceException;
import com.kmzyc.supplier.model.SuppliersInfo;

@Component("carProductDao")
@SuppressWarnings("unchecked")
public class CarProductDaoImpl extends DaoImpl implements CarProductDao {

  private static Logger logger= LoggerFactory.getLogger(CarProductDao.class);
  @Resource
  protected SqlMapClient sqlMapClient;

  @Override
public CarProduct findBySkuId(Long productSkuId) {
    CarProduct carProduct = null;
    Map<String, Object> map = null;
    try {
      map =
          (Map<String, Object>) sqlMapClient.queryForObject("CarProduct.findCarProductBySkuId",
              productSkuId);
      if (map == null) return null;
      carProduct = toCarProduct(map);
    } catch (Exception e) {
      logger.error("",e);
      return null;
    }
    return carProduct;
  }

  /**
   * Map转换
   * 
   * @param map
   * @return
   */
  private CarProduct toCarProduct(Map<String, Object> map) {
    CarProduct carProduct = new CarProduct();
    Long productSkuID = Long.valueOf(map.get("PRODUCTSKUID").toString());
    String skuCode = (String) map.get("PRODUCTSKUCODE");
    Long productId = Long.valueOf(map.get("PRODUCTID").toString());
    String productNo = (String) map.get("PRODUCTNO");
    BigDecimal price = (BigDecimal) map.get("PRICE");
    BigDecimal costPrice = (BigDecimal) map.get("COSTPRICE");
    String skuStatus = (String) map.get("SKUSTATUS");
    String productStatus = (String) map.get("PRODUCTSTATUS");
    Integer stockCount =
        map.get("STOCKCOUNT") == null ? 0 : Integer.parseInt(map.get("STOCKCOUNT").toString());
    BigDecimal unitWeight = (BigDecimal) map.get("UNITWEIGHT");
    String name = map.get("NAME") == null ? "" : (String) map.get("NAME");
    String title = map.get("TITLE") == null ? "" : (String) map.get("TITLE");
    String imagePath = map.get("IMAGEPATH") == null ? "" : (String) map.get("IMAGEPATH");
    String productDesc = map.get("PRODUCTDESC") == null ? "" : (String) map.get("PRODUCTDESC");
    String supplierCode = map.get("SUPPLIERCODE") == null ? "" : (String) map.get("SUPPLIERCODE");
    String channel = map.get("CHANNEL") == null ? "" : (String) map.get("CHANNEL");
    // m.category_id,m.brand_id,
    Long categoryId =
        map.get("CATEGORY_ID") == null ? null : Long.valueOf(map.get("CATEGORY_ID").toString());
    Long brandId =
        map.get("BRAND_ID") == null ? null : Long.valueOf(map.get("BRAND_ID").toString());
    Integer supplierType =
        map.get("SUPPLIERTYPE") == null ? 0 : Integer.parseInt(map.get("SUPPLIERTYPE").toString());

    carProduct.setProductSkuId(productSkuID);
    carProduct.setProductID(productId);
    carProduct.setCostPrice(costPrice);
    carProduct.setPrice(price);
    carProduct.setFinalPrice(price);
    carProduct.setIsOutOfStock(!"1".equals(skuStatus) || !"3".equals(productStatus));
    carProduct.setStockCount(stockCount);
    carProduct.setProductSkuCode(skuCode);
    carProduct.setProductNo(productNo);
    carProduct.setName(name);
    carProduct.setTitle(title);
    carProduct.setImagePath(imagePath);
    carProduct.setUnitWeight(unitWeight);
    carProduct.setProductDesc(productDesc);
    carProduct.setSupplierCode(supplierCode);
    carProduct.setSellerId(Long.parseLong(supplierCode));
    carProduct.setSupplierType(supplierType);
    carProduct.setChannel(channel);
    carProduct.setBrandId(brandId);
    carProduct.setCategoryId(categoryId);
    carProduct.setProductType(Integer.parseInt(map.get("PRODUCTTYPE").toString()));
    return carProduct;
  }

  /**
   * 查询产品商家ID，须检查上下架、库存
   * 
   * @param productSkuId
   * @param amount
   * @return
   * @throws SQLException
   */
  @Override
public Map<String, String> queryAbleCarProduct(Long skuId, Integer amount, String channel)
      throws SQLException {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("skuId", skuId);
    params.put("amount", amount);
    params.put("channel", channel);
    return (Map<String, String>) sqlMapClient.queryForObject("CarProduct.SQL_QUERY_ABLE_PRODUCT",
        params);
  }

  /**
   * 查询可用套餐，须检查上下架、库存
   * 
   * @param suitId
   * @param amount
   * @return
   * @throws SQLException
   */
  @Override
public List<Map<String, String>> queryAbleComposition(Long suitId, String channel)
      throws SQLException {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("suitId", suitId);
    params.put("channel", channel);
    return sqlMapClient.queryForList(
        "CarProduct.SQL_QUERY_ABLE_COMPOSITION", params);
  }

  /**
   * 批量
   * 
   * @param params
   * @return
   * @throws SQLException
   */
  @Override
public List<Long> queryAbleProductSkuForBatch(Map<Long, Integer> paramsList) throws SQLException {
    List<Long> skuidList = new ArrayList<Long>();
    sqlMapClient.startBatch();
    Iterator<Map.Entry<Long, Integer>> keysIt = paramsList.entrySet().iterator();
    while (keysIt.hasNext()) {
      Map.Entry<Long, Integer> entry = keysIt.next();
      Long skuid = entry.getKey();
      Map<String, Object> param = new HashMap<String, Object>(2);
      param.put("skuId", skuid);
      param.put("amount", entry.getValue());
      Long skuId =
          (Long) sqlMapClient.queryForObject("CarProduct.SQL_QUERY_ABLE_PRODUCT_SKUID_FOR_BATCH",
              param);
      if (null != skuId) {
        skuidList.add(skuId);
      }
    }
    sqlMapClient.executeBatch();
    return skuidList;
  }

  /**
   * 查询产品类型
   * 
   * @param skudId
   * @return
   * @throws ServiceException
   */
  @Override
public Integer queryProductType(Long skudId) throws SQLException {
    return (Integer) sqlMapClient.queryForObject("CarProduct.SQL_QUERY_PRODUCT_TYPE", skudId);
  }

  /**
   * 查询产品类型
   * 
   * @param skudId
   * @return
   * @throws ServiceException
   */
  @Override
public Map<Long, Integer> queryBatchProductType(List<Long> skudIds) throws SQLException {
    Map<Long, Integer> rsMap = null;
    List<Map<String, Object>> rsList =
        sqlMapClient.queryForList("CarProduct.SQL_QUERY_BATCH_PRODUCT_TYPE", skudIds);
    if (null != rsList && !rsList.isEmpty()) {
      rsMap = new HashMap<Long, Integer>();
      for (Map<String, Object> temp : rsList) {
        rsMap.put(Long.parseLong(temp.get("SKUID").toString()), Integer.parseInt(temp.get(
            "PRODUCTTYPE").toString()));
      }
    }
    return rsMap;
  }

  /**
   * 查询套餐内产品类型
   * 
   * @param comId
   * @return
   * @throws ServiceException
   */
  @Override
public Map<Long, Integer> queryProductTypeByComId(Long suitId) throws SQLException {
    Map<Long, Integer> rsMap = null;
    List<Map<String, Object>> rsList =
        sqlMapClient.queryForList("CarProduct.SQL_QUERY_PRODUCT_TYPE_BY_COM_ID", suitId);
    if (null != rsList && !rsList.isEmpty()) {
      rsMap = new HashMap<Long, Integer>();
      for (Map<String, Object> temp : rsList) {
        rsMap.put(Long.parseLong(temp.get("SKUID").toString()), Integer.parseInt(temp.get(
            "PRODUCTTYPE").toString()));
      }
    }
    return rsMap;
  }

  /**
   * 根据供应商ID查询商家名称
   * 
   * @param sellerId
   * @return
   * @throws SQLException
   */
  @Override
public String queryCorporateNameBySupplierId(Long sellerId) throws SQLException {
    return (String) sqlMapClient.queryForObject(
        "CarProduct.SQL_QUERY_CORPORATE_NAME_BY_SUPPLIER_ID", sellerId);
  }

  /**
   * 批量查询产品最大库存
   * 
   * @param skuIds
   * @return
   * @throws ServiceException
   */
  @Override
public Map<Long, Integer> queryBatchMaxStock(List<Long> skuIds) throws SQLException {
    return sqlMapClient.queryForMap("CarProduct.SQL_QUERY_BATCH_MAX_STOCK", skuIds, "skuId",
        "stock");
  }

  @Override
  public SuppliersInfo selectByPrimaryKey(Long sellerId) throws SQLException {
    SuppliersInfo key = new SuppliersInfo();
    key.setSupplierId(sellerId);
    SuppliersInfo record =
        (SuppliersInfo) sqlMapClient.queryForObject(
            "CarProduct.ibatorgenerated_selectByPrimaryKey", key);
    return record;
  }

  @Override
  public CarProduct queryTimeProduct(String productId) throws SQLException {

    return (CarProduct) sqlMapClient.queryForObject(
        "CarProduct.SQL_QUERY_BATCH_PRODUCT_PRICE_INFO", productId);
  }
}
