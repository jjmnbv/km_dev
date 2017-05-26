package com.kmzyc.b2b.ajax;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.framework.action.BaseAction;
import com.kmzyc.b2b.model.Favorite;
import com.kmzyc.b2b.service.ProductPriceService;
import com.kmzyc.b2b.service.ProductSkuService;
import com.kmzyc.b2b.service.member.MyFavoriteService;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.constants.InterfaceResultCode;

@SuppressWarnings("serial")
@Controller
@Scope("prototype")
public class FavoriteInterfaceAction extends BaseAction {
  // private static Logger logger = Logger.getLogger(FavoriteInterfaceAction.class);
  private static Logger logger = LoggerFactory.getLogger(FavoriteInterfaceAction.class);
  @Resource(name = "myFavoriteServiceImpl")
  private MyFavoriteService myFavoriteService;
  @Resource(name = "productSkuServiceImpl")
  private ProductSkuService productSkuService;
  @Resource(name = "productPriceService")
  private ProductPriceService productPriceService;
  // 返回至页面的对象
  @SuppressWarnings("rawtypes")
  private ReturnResult returnResult;
  private String productSkuCode;

  /**
   * 判断用户是否已收藏该商品
   * 
   * @return
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  public String isSavedFavorite() {
    Long userId = (Long) getSession().getAttribute(Constants.SESSION_USER_ID);
    if (userId == null) {
      logger.info("收藏商品前未登录");
      returnResult = new ReturnResult(InterfaceResultCode.NOT_LOGIN, "未登录", null);
      return SUCCESS;
    }
    String productSkuCode = getRequest().getParameter("productSkuCode");
    try {
      boolean isSaved = myFavoriteService.isSavedFavorite(userId, productSkuCode);
      // 返回至页面的对象
      returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", isSaved);
    } catch (SQLException e) {
      logger.error("判断用户是否已收藏该商品出错：" + e.getMessage(), e);
      // 返回至页面的对象
      returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", null);
    }
    return SUCCESS;
  }

  /**
   * 添加商品至收藏夹
   * 
   * @return
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  public String insertFavorite() {
    Long userId = (Long) getSession().getAttribute(Constants.SESSION_USER_ID);
    if (userId == null) {
      logger.info("收藏商品前未登录");
      returnResult = new ReturnResult(InterfaceResultCode.NOT_LOGIN, "未登录", null);
      return SUCCESS;
    }
    String productSkuCode = getRequest().getParameter("productSkuCode");
    String price = getRequest().getParameter("price");// 当前销售价格
    Favorite favorite = new Favorite(userId, productSkuCode, new BigDecimal(price));
    try {
      myFavoriteService.insertFavorite(favorite);

      // 返回至页面的对象
      returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", null);
    } catch (SQLException e) {
      logger.error("添加商品至收藏夹出错：" + e.getMessage(), e);
      // 返回至页面的对象
      returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", null);
    }
    return SUCCESS;
  }

  /**
   * 查收藏店铺的用户数量·
   * 
   * @return
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  public String findFavoriteShopUserCount() {
    // shopCode实为店铺ID
    String shopCode = getRequest().getParameter("shopCode");

    try {
      int count = myFavoriteService.findFavoriteShopUserCount(shopCode);
      // 返回至页面的对象
      returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", count);
    } catch (SQLException e) {
      logger.error("查收藏店铺的用户数量出错：" + e.getMessage(), e);
      // 返回至页面的对象
      returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", null);
    }

    return SUCCESS;
  }

  /**
   * 添加店铺至收藏夹
   * 
   * @return
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  public String insertFavoriteShop() {
    Long userId = (Long) getSession().getAttribute(Constants.SESSION_USER_ID);
    if (userId == null) {
      logger.info("收藏店铺前未登录");
      returnResult = new ReturnResult(InterfaceResultCode.NOT_LOGIN, "未登录", null);
      return SUCCESS;
    }
    // shopCode实为店铺ID
    String shopCode = getRequest().getParameter("shopCode");
    if (shopCode == null || shopCode.equals("")) {
      logger.info("收藏店铺ID为空");
      returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", null);
      return SUCCESS;
    }
    Favorite favorite = new Favorite(userId, shopCode);

    try {
      boolean isSaved = myFavoriteService.isSavedFavoriteShop(userId, shopCode);
      if (isSaved) {
        returnResult = new ReturnResult(InterfaceResultCode.SAVED, "成功", "该店铺已保存过了");
        return SUCCESS;
      }
    } catch (SQLException e1) {
      logger.error("添加店铺至收藏夹出错：" + e1.getMessage(), e1);
      returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", null);
    }

    try {
      myFavoriteService.insertFavorite(favorite);
      // 返回至页面的对象
      returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", null);
    } catch (SQLException e) {
      logger.error("添加店铺至收藏夹出错：" + e.getMessage(), e);
      // 返回至页面的对象
      returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", null);
    }
    return SUCCESS;
  }

  /**
   * 查询该用户收藏夹中同类目的商品（前N个，默认为所有）
   * 
   * @return
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  public String getRelatedFavorites() {
    Long userId = (Long) getSession().getAttribute(Constants.SESSION_USER_ID);
    if (userId == null) {
      logger.info("收藏商品前未登录");
      returnResult = new ReturnResult(InterfaceResultCode.NOT_LOGIN, "未登录", null);
      return SUCCESS;
    }
    String productSkuCode = getRequest().getParameter("productSkuCode");
    // 获取前N个收藏
    String num = getRequest().getParameter("number");
    Integer number = StringUtils.isBlank(num) ? null : Integer.valueOf(num);
    try {
      // 商品物理类目
      Long categoryId = productSkuService.findCategotyIdBySkuCode(productSkuCode);
      // 查询该用户收藏夹中同类目的商品
      List<Favorite> favoriteList =
          myFavoriteService.findFavoritesByUserAndCategory(userId, categoryId, number);
      try {
        favoriteList = productPriceService.getPriceBatch(favoriteList);
      } catch (Exception e) {}
      // 返回至页面的对象
      returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", favoriteList);
    } catch (Exception e) {
      logger.error("查询该用户收藏夹中同类目的商品出错：" + e.getMessage(), e);
      returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", null);
    }
    return SUCCESS;
  }

  /**
   * 获取用户收藏列表
   * 
   * @return
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  public String getMyFavoriteProductList() {
    pagintion = this.getPagination(5, pagintion.getNumperpage());
    Long userId = getUserId();
    try {
      List<Favorite> myFavoriteList = null;
      if (userId == null) {
        logger.info("获取用户收藏列表前未登录");
        returnResult = new ReturnResult(InterfaceResultCode.NOT_LOGIN, "未登录", null);
        return SUCCESS;
      }
      pagintion = myFavoriteService.findFavoriteGoodsByUser(userId, pagintion);
      myFavoriteList = pagintion.getRecordList();
      productPriceService.getPriceBatch(myFavoriteList, true);
      Map<String, Object> map = new HashMap<String, Object>();
      map.put("pagintion", pagintion);
      map.put("myFavoriteList", myFavoriteList);
      returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", map);
    } catch (Exception e) {
      logger.error("获取用户收藏列表失败：" + e.getMessage(), e);
      returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", e.getMessage());
    }
    return SUCCESS;
  }



  /**
   * 根据SKUID和USERID进行单品的取消收藏
   * 
   * @return
   */
  public String wapdeleteFavoriteBySku() {
    HttpServletRequest request = ServletActionContext.getRequest();
    Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);
    logger.info("删除单品收藏记录，参数：skucode：" + this.productSkuCode + "，userID：" + userId);
    try {
      logger.info("删除收藏记录，skucode为【" + this.productSkuCode + "】");
      Map<String, Object> map = new HashMap<String, Object>();
      map.put("loginId", userId);
      map.put("skuCode", this.productSkuCode);
      myFavoriteService.deleteFavoriteByCode(map);
      returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "删除我的收藏成功", "1");
    } catch (Exception e) {
      logger.error("删除收藏记录出错：" + e.getMessage(), e);
      returnResult = new ReturnResult(InterfaceResultCode.FAILED, "删除我的收藏失败", "2");
      return ERROR;
    }
    return SUCCESS;
  }



  private Long getUserId() {
    Object userId = getSession().getAttribute(Constants.SESSION_USER_ID);
    if (userId == null) return null;
    return Long.valueOf(userId.toString());
  }

  @SuppressWarnings("rawtypes")
  public ReturnResult getReturnResult() {
    return returnResult;
  }

  @SuppressWarnings("rawtypes")
  public void setReturnResult(ReturnResult returnResult) {
    this.returnResult = returnResult;
  }

  public String getProductSkuCode() {
    return productSkuCode;
  }

  public void setProductSkuCode(String productSkuCode) {
    this.productSkuCode = productSkuCode;
  }
}
