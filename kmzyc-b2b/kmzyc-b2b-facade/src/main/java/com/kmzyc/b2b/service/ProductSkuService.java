package com.kmzyc.b2b.service;

import com.km.framework.page.Pagination;
import com.kmzyc.b2b.model.ProductImage;
import com.kmzyc.b2b.model.ProductSku;
import com.kmzyc.b2b.model.Productmain;
import com.kmzyc.b2b.shopcart.vo.CartProduct;
import com.kmzyc.b2b.vo.ShopCategorys;
import com.kmzyc.framework.exception.ServiceException;
import com.pltfm.app.vobject.ProductAttr;
import com.pltfm.app.vobject.ViewSkuAttr;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public interface ProductSkuService {

  /**
   * 根据skuId查询ProductSku
   * 
   * @param skuId
   * @return
   * @throws SQLException
   */
  ProductSku findProductSkuById(Long skuId) throws ServiceException;

  /**
   * 根据skuCode查询categotyId
   * 
   * @param productSkuCode
   * @return
   * @throws ServiceException
   */
  Long findCategotyIdBySkuCode(String productSkuCode) throws ServiceException;

  /**
   * 获取某个SKU商品下所有的图片
   * 
   * @param productSkuId
   * @return
   * @throws Exception
   */
  List<ProductImage> findAllBySkuId(Long productSkuId) throws Exception;

  /**
   * 通过产品主键查询产品sku属性信息
   * 
   * @param productId 产品主键
   * @return sku属性信息
   * @throws ServiceException 异常信息
   */
  List<ProductAttr> queryProductAttrByProductId(Long productId) throws ServiceException;

  /**
   * 通过产品主键查询产品个性信息
   * 
   * @param productId 产品主键
   * @return 属性值集合信息
   * @throws Exception 异常
   */
  List<ViewSkuAttr> findAttrAndValueByProductId(Long productId) throws Exception;

  /**
     * 
     */
  List<ProductSku> findSkuListByProductId(Long productId) throws Exception;

  String findProductWareHouseBySkuId(Long productSkuId) throws ServiceException;

  /**
   * 通过产品的SKUCODE查询产品的相关信息 （productmain,sku,product_imge,SKU个性属性）
   */
  List<ProductSku> findProductDetailByCode(String productSkuCode) throws ServiceException;

  /**
   * 通过供应商ID 以及条件查询产品的排行。
   */
  List<ProductSku> findProductRankByCondition(Map mapCondition) throws ServiceException;

  /**
   * 通过供应商ID，查询店铺的类目以及推荐类目
   */
  List<ShopCategorys> findCategorysByShopId(Map condition, String recommed)
      throws ServiceException;

  /**
   * 通过供应商ID ，以及条件查询产品的收藏排行
   */
  List<ProductSku> findProductFavRankByCondition(Map mapCondition) throws ServiceException;

  /**
   * 通过供应商ID ，产品的上架时间来查询产品的排行
   */
  List<ProductSku> findProductUpTimeRankByCondition(Map mapCondition)
      throws ServiceException;

  /** 查询商品库存、状态、价格 详情页面专用 */
  Map<String, Object> querySkuInfoForDetailPage(String skuId);

  /**
   * 查询返佣率
   * 
   * @param ids
   * @return
   * @throws ServiceException
   */
  Map<String, BigDecimal> queryBatchComRatio(List<Long> ids) throws ServiceException;

  /**
   * 查询活动产品信息
   * 
   * @param ids
   * @return
   * @throws ServiceException
   */
  Map<Long, CartProduct> queryPromotionProducts(List<Long> ids) throws ServiceException;

  String getSeckillProducts(HashMap<String, String> skucondition) throws ServiceException;

  List<ProductSku> getProductBySkuIds(String skuIds) throws ServiceException;

  Pagination getRecommendProduct(Pagination page, String string) throws ServiceException;

  List<ProductSku> getWindowDatas(String jboxWindowName) throws ServiceException;

  /**
   * 根据productRelationType类型及productSkuId组成Map查询套餐或组合
   * 
   * @param typeAndSkuIdMap
   * @param productRelationType
   * @return
   * @throws ServiceException
   */
  List findProductRelation(Map typeAndSkuIdMap, String productRelationType);

  /**
   * 查询产品信息包含所属商家类型
   * 
   * @param productId
   * @return
   * @throws ServiceException
   */
  Productmain findProductSupplyType(Long productId) throws ServiceException;


  /* public String querySkuInfoForFanLi(Long productSkuId) throws ServiceException; */

  /**
   * 获取某个产品下所有sku的默认图片
   * 
   * @param productId
   * @return
   * @throws Exception
   */
  List<ProductImage> findAllIsDefaultByProductId(Long productId) throws Exception;

}
