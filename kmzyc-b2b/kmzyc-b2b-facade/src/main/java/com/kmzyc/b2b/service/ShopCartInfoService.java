package com.kmzyc.b2b.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import com.kmzyc.b2b.shopcart.vo.ShopCart;
import com.kmzyc.b2b.shopcart.vo.ShopCartUserInfo;
import com.kmzyc.b2b.shopcart.vo.ShopcartCacheProduct;
import com.kmzyc.framework.exception.ServiceException;
import com.kmzyc.supplier.model.SupplierFare;

public interface ShopCartInfoService {

    /** 获取购物车页面信息 */

    public ShopCart generateSettlement(ShopCartUserInfo user);

    /** 购物车结算 */
    public ShopCart generateSettlement(ShopCartUserInfo user, String[] productIdArray);

    /** 立即够结算 */
    public ShopCart generateSettlement(ShopCartUserInfo user, ShopcartCacheProduct cacheProduct);

    /** 预售结算 */
    public ShopCart generateSettlementForPressell(ShopCartUserInfo user,
                                                  ShopcartCacheProduct cacheProduct, String buyType);

    /**
     * 删除商品
     * 
     * @param uid
     * @param skuId
     * @return
     * @throws ServiceException
     */
    public boolean deleteProduct(String uid, String skuId) throws ServiceException;

    /***
     * 批量删除购物车商品 普通商品n_开头，套装c_开头
     */
    public boolean deleteProduct(String uid, String[] skuIds) throws ServiceException;

    /**
     * 添加套餐到购物车
     * 
     * @param user
     * @param amount
     * @param suitId
     * @return
     * @throws ServiceException
     */
    public boolean addComposition(ShopCartUserInfo user, int amount,
                                  String suitId);



    /**
     * 更新购物车产品检查前库存和上下架
     * 
     * @param uid
     * @param skuId
     * @param amount
     * @throws ServiceException code：-1002数据异常、-1001库存不足、-1000已下架
     * @return
     */
    public void updateCarProductInShopCar(String uid, Long skuId, Integer amount);

    /**
     * 库存和上下架检查新增或更新购物车套餐
     * 
     * @param uid
     * @param amount
     * @param suitId
     * @return
     * @throws ServiceException code：-1002数据异常、-1001库存不足、-1000已下架
     */
    public void updateSuitInShopCarChecked(String uid, Long suitId, int amount);

    /***
     * 通用修改购物车商品数量
     */
    public boolean updateProductInShopCar(String uid, String id, Integer amount);

    /**
     * 只选一个商品 用户ID ,产品套餐ID ,是否选中,类型
     * */
    public boolean checkOneProduct(String uid, String id);


    public boolean checkAllProduct(String uid, boolean checked);


    public boolean choosePromotionInfo(String uid, String productId, String promotionId);

    public List<JSONObject> getItemGiftInfo(String uid, String itemId);

    public boolean chooseGift(String uid, String itemId, Map<Integer, Integer> map);

    public boolean checkShopAllProduct(String uid, String[] selectIdArray, boolean checked);

    public boolean deleteGift(String uid, String itemId, String giftId);

    public boolean choosePresents(String uid, String itemId, String presents);

    public Map<String, Integer> queryStockBatch(List<?> e);

    public boolean addProduct(ShopCartUserInfo user, String activityChannel,
                              int amount, String... skuIds);

    public boolean removeChecked(String uid);

    /**
     * 合并购物车
     * 
     * @param userId 登录用户id
     * @param tempId 临时id
     */
    public boolean mergeShopCar(String userId, String tempId);

    /**
     * 统计购物车商品数量
     * 
     * @param uid
     * @return
     */
    public int countShopCartProductNum(String uid);

    /**
     * 批量获取商家运费
     * 
     * @param keySet
     * @return
     */
    public Map<Long, SupplierFare> getSupplierFareMap(Set<Long> keySet);

}
