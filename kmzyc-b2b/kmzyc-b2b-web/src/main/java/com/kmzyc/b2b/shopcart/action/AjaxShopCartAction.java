package com.kmzyc.b2b.shopcart.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import com.km.framework.action.BaseAction;
import com.kmzyc.b2b.model.ViewProductRelationPurchase;
import com.kmzyc.b2b.service.ShopCartInfoService;
import com.kmzyc.b2b.service.ViewProductRelationPurchaseService;
import com.kmzyc.b2b.shopcart.util.ShopUtil;
import com.kmzyc.b2b.shopcart.vo.ShopCartUserInfo;
import com.kmzyc.b2b.shopcart.vo.SubTotal;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.framework.ajax.AjaxUtil;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.constants.InterfaceResultCode;
import com.kmzyc.framework.exception.ServiceException;
import com.kmzyc.util.StringUtil;

/**
 * 添加购物车接口
 * 
 * @author luotao
 * 
 */
@Controller("ajaxShopCartAction")
@Scope("prototype")
@SuppressWarnings("unchecked")
public class AjaxShopCartAction extends BaseAction {
    private static final long serialVersionUID = 634069768736379L;

    @Resource
    private ShopCartInfoService shopCartInfoService;
    private static Logger logger = LoggerFactory.getLogger(AjaxShopCartAction.class);
    private ReturnResult<Object> returnResult;
    @Resource
    ViewProductRelationPurchaseService viewProductRelationPurchaseService;
    private String productVary; // 产品数量

    /***
     * 添加套餐
     * 
     * @return
     */
    public String addSuitToShopCart() {
        try {
            Integer count = 0;
            if (StringUtil.isEmpty(productVary)) {
                count = 1;
            } else {
                count = Integer.valueOf(productVary);
            }
            if (count <= 0) {
                count = 1;
            }
            HttpServletRequest request = this.getRequest();
            String loginType = (String) getSession().getAttribute(Constants.SESSION_B2B_OR_ERA);
            HttpSession session = request.getSession();
            Object sessionUserId = session.getAttribute(Constants.SESSION_USER_ID);
            boolean isLogin = sessionUserId != null;
            String uid = ShopUtil.getUid(request, getResponse());
            String suitId = request.getParameter("suitId");
            ShopCartUserInfo user = new ShopCartUserInfo(uid, isLogin, loginType, null);
            shopCartInfoService.addComposition(user, 1, suitId);
            returnResult = new ReturnResult<Object>(InterfaceResultCode.SUCCESS, "添加购物车成功！", null);
        } catch (ServiceException e) {
            returnResult = new ReturnResult<Object>(String.valueOf(e.getErrorCode()),
                    e.getErrorString(), null);
        } catch (Exception e) {
            logger.info("cms 添加套餐接口调用失败", e);
            returnResult = new ReturnResult<Object>(InterfaceResultCode.FAILED, "添加购物车失败！", e);
        }
        return SUCCESS;

    }

    /***
     * 添加单品
     * 
     * @return
     */
    public String addProductToShopCart() {
        try {
            HttpServletRequest request = this.getRequest();
            String loginType = (String) getSession().getAttribute(Constants.SESSION_B2B_OR_ERA);
            String uid = ShopUtil.getUid(getRequest(), getResponse());
            HttpSession session = request.getSession();
            Object sessionUserId = session.getAttribute(Constants.SESSION_USER_ID);
            boolean isLogin = sessionUserId != null;
            int count = 1;
            if (!StringUtil.isEmpty(productVary)) {
                count = Integer.parseInt(productVary);
                if (count <= 0) {
                    count = 1;
                }
            }
            String skuIdsStr = request.getParameter("skuIds");
            String[] skuIds = skuIdsStr.split(",");

            // mkw add 20151009 去除数组重复数据
            Set<String> set = Sets.newHashSet(skuIds);// new TreeSet<String>();
            // Collections.addAll(set, skuIds);
            skuIds = set.toArray(new String[set.size()]);
            // end

            String activityChannel = request.getParameter("activityChannel");
            if (StringUtil.isEmpty(activityChannel)) {
                activityChannel = "";
            }

            /*
             * JSONObject rulejson = JSONObject.parseObject(ShopCartUtil.getCookieValue(request,
             * "sruleId"));
             */
            ShopCartUserInfo user = new ShopCartUserInfo(uid, isLogin, loginType, null);
            shopCartInfoService.addProduct(user, activityChannel, count, skuIds);
            returnResult = new ReturnResult<Object>(InterfaceResultCode.SUCCESS, "添加购物车成功。", null);
        } catch (ServiceException e) {
            logger.error("addProductToShopCart添加购物车失败", e);
            returnResult =
                    new ReturnResult<Object>(e.getErrorCode() + "", e.getErrorString(), null);
        } catch (Exception e) {
            logger.error("addProductToShopCart添加购物车失败", e);
            returnResult = new ReturnResult<Object>(InterfaceResultCode.FAILED, "添加购物车失败", e);
        }

        return SUCCESS;
    }

    /***
     * 删除购物车产品
     * 
     * @return
     */

    public String deleteProductFromShopCart() {
        String uid = ShopUtil.getUid(getRequest(), getResponse());
        Map<String, Object> map;
        boolean rs = false;
        try {
            // HttpServletResponse response = this.getResponse();
            HttpServletRequest request = getRequest();
            // if (json != null && json.containsKey(productSkuId.toString())) {
            // json.remove(productSkuId.toString());
            // shopCartInfoService.saveSpreadRules(uid, json);
            // Cookie cook2 = new Cookie("sruleId", json.toString());
            // cook2.setDomain(cookieDomain);
            // cook2.setPath("/");
            // cook2.setMaxAge(-1);
            // response.addCookie(cook2);
            // }
            String productId = request.getParameter("productId");
            rs = shopCartInfoService.deleteProduct(uid, productId);
            if (rs) {
                returnResult = new ReturnResult<Object>(InterfaceResultCode.SUCCESS, "删除成功", null);
                return SUCCESS;
            }
        } catch (Exception e) {
            logger.error("deleteProductFromShopCart ERROR", e);
        }
        returnResult = new ReturnResult<Object>(InterfaceResultCode.FAILED, "删除失败", null);
        return NONE;
    }


    /**
     * 删除选中商品
     * 
     * @return
     */
    public String removeChecked() {
        try {
            String uid = ShopUtil.getUid(getRequest(), getResponse());
            shopCartInfoService.removeChecked(uid);
            returnResult = new ReturnResult<Object>(InterfaceResultCode.SUCCESS, "", null);
        } catch (Exception e) {
            LOG.error("删除选中商品发生异常", e);
            returnResult = new ReturnResult<Object>(InterfaceResultCode.FAILED, "操作失败", null);
        }
        return SUCCESS;
    }

    /**
     * 通用修改购物车商品数量
     * 
     * @return
     */
    public String updateProductNum() {
        try {
            String count = this.getRequest().getParameter("count");//
            String id = this.getRequest().getParameter("id");//
            int countInt = 0;
            if (StringUtil.isEmpty(count)) {
                countInt = 1;
            } else {
                countInt = Integer.parseInt(count);
            }
            String uid = ShopUtil.getUid(getRequest(), getResponse());
            shopCartInfoService.updateProductInShopCar(uid, id, countInt);
            returnResult = new ReturnResult<Object>(InterfaceResultCode.SUCCESS, "操作成功", null);
        } catch (Exception e) {
            LOG.error("修改购物车单品数量发生异常", e);
            returnResult = new ReturnResult<Object>(InterfaceResultCode.FAILED, "操作失败", null);
        }
        return SUCCESS;
    }

    /**
     * 统计购物车商品数量
     */
    public String countShopCartProductNum() {
        JSONObject json = new JSONObject();
        try {

            String uid = ShopUtil.getUid(getRequest(), getResponse());
            int count = shopCartInfoService.countShopCartProductNum(uid);
            json.put("countProductNum", String.valueOf(count));
        } catch (Exception e) {
            LOG.error("统计购物车商品数量发生异常", e);
            json.put("countProductNum", "0");
            returnResult = new ReturnResult<Object>(InterfaceResultCode.SUCCESS, "", json);
        }
        returnResult = new ReturnResult<Object>(InterfaceResultCode.SUCCESS, "", json);
        return SUCCESS;
    }

    /**
     * 更新是否选中
     */
    public void updateProductIsCheckedInShopCart() {
        String selectIds = this.getRequest().getParameter("selectIds");// 产品ID或套餐ID
        // String sid = this.getRequest().getParameter("sellerId");// 商铺ID
        JSONObject json = new JSONObject();
        boolean isSuccess = false;
        if (StringUtil.isEmpty(selectIds)) {
            isSuccess = false;
        } else {
            String uid = ShopUtil.getUid(getRequest(), getResponse()); // 获取套餐
            isSuccess = shopCartInfoService.checkOneProduct(uid, selectIds);
        }
        json.put("isSuccess", isSuccess);
        AjaxUtil.writeJSONToResponse(json);
    }


    /**
     * 全选
     * 
     * @return
     * @throws Exception
     */
    public String checkAll() throws Exception {
        String checked = getRequest().getParameter("checked").toLowerCase();
        String rs = ShopUtil.FAILED;
        if (!StringUtil.isEmpty(checked)) {
            String uid = ShopUtil.getUid(getRequest(), getResponse());
            shopCartInfoService.checkAllProduct(uid, checked.equals("true"));
            rs = ShopUtil.SUCCESS;
        }
        returnResult = new ReturnResult<Object>(rs, "", null);
        return SUCCESS;
    }



    public String checkShopAllProduct() {
        String selectIds = this.getRequest().getParameter("selectIds");// 产品ID或套餐ID
        try {
            if (!StringUtil.isEmpty(selectIds)) {
                String checked = getRequest().getParameter("checked");
                if (!StringUtil.isEmpty(checked)) {
                    String[] selectIdArray = selectIds.split(",");
                    String uid = ShopUtil.getUid(getRequest(), getResponse());
                    shopCartInfoService.checkShopAllProduct(uid, selectIdArray,
                            Boolean.parseBoolean(checked));
                }
            }
        } catch (Exception e) {
            logger.error("选中购物车商品异常", e);
        }
        returnResult = new ReturnResult<Object>(InterfaceResultCode.SUCCESS, "", null);
        return SUCCESS;
    }


    public String selectPromotionInfo() {
        HttpServletRequest request = this.getRequest();
        String productId = request.getParameter("productId");
        String promotionId = request.getParameter("promotionId");
        String uid = ShopUtil.getUid(getRequest(), getResponse());
        shopCartInfoService.choosePromotionInfo(uid, productId, promotionId);
        returnResult = new ReturnResult<Object>(InterfaceResultCode.SUCCESS, "", null);
        return SUCCESS;
    }

    public String chooseGift() {
        try {
            HttpServletRequest request = this.getRequest();
            String uid = ShopUtil.getUid(request, getResponse());
            String gift = "{" + request.getParameter("giftIds").replace("-", ":") + "}";
            String itemId = request.getParameter("itemId");
            Map<Integer, Integer> map = JSONObject.parseObject(gift, Map.class);
            shopCartInfoService.chooseGift(uid, itemId, map);
            returnResult = new ReturnResult<Object>(InterfaceResultCode.SUCCESS, "", null);
        } catch (ServiceException e) {
            logger.error("chooseGift ERROR", e);
            returnResult = new ReturnResult<Object>(e.getErrorCode() + "", e.getMessage(), null);
        } catch (Exception e) {
            logger.error("", e);
            // e.printStackTrace();
            returnResult = new ReturnResult<Object>(InterfaceResultCode.FAILED, "系统异常", null);
        }
        return SUCCESS;
    }

    public String deleteGift() {
        HttpServletRequest request = this.getRequest();
        String itemId = request.getParameter("itemId");
        String giftId = request.getParameter("giftId");
        String uid = ShopUtil.getUid(request, getResponse());
        boolean deleteSuccess = shopCartInfoService.deleteGift(uid, itemId, giftId);
        returnResult = new ReturnResult<Object>(
                deleteSuccess ? InterfaceResultCode.SUCCESS : InterfaceResultCode.FAILED, null,
                null);
        return SUCCESS;
    }

    public String choosePresents() {
        HttpServletRequest request = this.getRequest();
        String itemId = request.getParameter("itemId");
        String presents = request.getParameter("presents");
        String uid = ShopUtil.getUid(request, getResponse());
        boolean chooseSuccess = shopCartInfoService.choosePresents(uid, itemId, presents);
        returnResult = new ReturnResult<Object>(
                chooseSuccess ? InterfaceResultCode.SUCCESS : InterfaceResultCode.FAILED, null,
                null);
        return SUCCESS;
    }


    public String getProductVary() {
        return productVary;
    }

    public void setProductVary(String productVary) {
        this.productVary = productVary;
    }

    public ReturnResult<Object> getReturnResult() {
        return returnResult;
    }

    public void setReturnResult(ReturnResult<Object> returnResult) {
        this.returnResult = returnResult;
    }

    private List<ViewProductRelationPurchase> getOtherBuy(String productSkuId) {
        // 购买了本商品的人还购买的商品
        return new ArrayList<ViewProductRelationPurchase>();
    }

    public void getIsLogin() {
        Boolean islogin = getSession().getAttribute(Constants.SESSION_USER_ID) != null;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("islogin", islogin);
        AjaxUtil.writeJSONToResponse(map);
    }

    public static void main(String[] args) {
        TreeSet ts = new TreeSet();
        ts.add("orange");
        ts.add("apple");
        ts.add("banana");
        ts.add("grape");

        Iterator it = ts.iterator();
        while (it.hasNext()) {
            String fruit = (String) it.next();
            System.out.println(fruit);
        }
        String ad = "{123:{code:1,error:sadfsad}}";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("123", new SubTotal());
        String ma = JSONObject.toJSONString(map);
        System.out.println(ma);
    }
}
