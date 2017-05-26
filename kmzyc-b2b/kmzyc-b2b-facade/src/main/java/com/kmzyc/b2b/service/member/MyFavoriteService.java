package com.kmzyc.b2b.service.member;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.km.framework.page.Pagination;
import com.kmzyc.b2b.model.Favorite;

public interface MyFavoriteService {

  /**
   * 查询收藏的商品
   * 
   * @param page
   * @return
   * @throws SQLException
   */
  public Pagination findFavoriteProductByPage(Pagination page) throws SQLException;
  /**
   * 查询收藏的商品
   * 
   * @param page
   * @return
   * @throws SQLException
   */
  public Pagination appFindFavoriteProductByPage(Pagination page) throws SQLException;

  /**
   * 查询收藏的店铺
   * 
   * @param page
   * @return
   * @throws SQLException
   */
  public Pagination findFavoriteShopByPage(Pagination page) throws SQLException;

  /**
   * 删除收藏记录
   * 
   * @param favoriteId
   * @throws SQLException
   */
  public void deleteFavorite(long favoriteId) throws SQLException;

  /**
   * 插入收藏记录
   * 
   * @param favorite
   * @throws SQLException
   */
  public void insertFavorite(Favorite favorite) throws SQLException;

  /**
   * 查询用户收藏夹中同类目商品N个
   * 
   * @param userId
   * @param categoryId 该值为空，则从收藏夹中的所有商品取
   * @param number 该值若为空，则获取所有满足条件记录
   * @throws SQLException
   */
  public List<Favorite> findFavoritesByUserAndCategory(Long userId, Long categoryId, Integer number)
      throws SQLException;

  /**
   * 判断用户是否已收藏该商品
   * 
   * @param userId
   * @param productSkuCode
   * @return
   * @throws SQLException
   */
  public boolean isSavedFavorite(Long userId, String productSkuCode) throws SQLException;

  public boolean isSavedFavoriteShop(Long userId, String shopCode) throws SQLException;

  /**
   * 收藏该店铺的用户数量
   * 
   * @param shopCode
   * @return
   * @throws SQLException
   */
  public int findFavoriteShopUserCount(String shopCode) throws SQLException;

  /**
   * 查询收藏夹中现货商品数量
   * 
   * @param userId
   * @return
   * @throws SQLException
   */
  public Long countInStockSku(Long userId) throws SQLException;

  /**
   * 获取我的收藏里面的降价商品数量
   * 
   * @return
   * @throws SQLException
   * @param userId
   */
  public Long countCutPriceSku(Long userId, List<Favorite> recourseList) throws SQLException;

  /**
   * 获取我的收藏里面的促销商品数量
   * 
   * @return
   * @throws SQLException
   * @param userId
   */
  public Long countPromotionSku(Long userId, List<Favorite> recourseList) throws SQLException;

  /**
   * 获取所有商品（并加载是否有促销信息等）。
   * 
   * @param userId
   * @return
   * @throws SQLException
   */
  public List<Favorite> findAllFavoriteGoods(Long userId) throws SQLException;

  public Pagination findFavoriteGoodsByUser(Long userId, Pagination page) throws SQLException;

  public Float showSupplierPoint(Long supplierId) throws Exception;

  /**
   * 删除收藏记录
   * 
   * @param favoriteId
   * @throws Exception
   */
  public void deleteFavoriteByCode(Map<String, Object> map) throws SQLException;

}
