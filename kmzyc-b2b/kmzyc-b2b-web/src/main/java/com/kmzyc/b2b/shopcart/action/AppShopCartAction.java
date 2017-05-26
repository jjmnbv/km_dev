package com.kmzyc.b2b.shopcart.action;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.JavaBeanSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.alipay.api.internal.util.StringUtils;
import com.kmzyc.b2b.app.AppBaseAction;
import com.kmzyc.b2b.app.AppJsonUtils;
import com.kmzyc.b2b.service.ShopCartInfoService;
import com.kmzyc.b2b.shopcart.util.ShopcartConstants;
import com.kmzyc.b2b.shopcart.vo.AppShopCartOperation;
import com.kmzyc.b2b.shopcart.vo.Gift;
import com.kmzyc.b2b.shopcart.vo.NormalCartProduct;
import com.kmzyc.b2b.shopcart.vo.SellerShop;
import com.kmzyc.b2b.shopcart.vo.SellerShopList;
import com.kmzyc.b2b.shopcart.vo.ShopCart;
import com.kmzyc.b2b.shopcart.vo.ShopCartItem;
import com.kmzyc.b2b.shopcart.vo.ShopCartProduct;
import com.kmzyc.b2b.shopcart.vo.ShopCartProductReminder;
import com.kmzyc.b2b.shopcart.vo.ShopCartUserInfo;
import com.kmzyc.b2b.util.NumberUtil;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.framework.constants.InterfaceResultCode;
import com.kmzyc.framework.exception.ServiceException;
import com.kmzyc.promotion.optimization.vo.ProductPromotion;
import com.kmzyc.promotion.optimization.vo.Promotion;
import com.kmzyc.promotion.optimization.vo.PromotionProductData;
import com.kmzyc.supplier.model.SupplierFare;
import com.kmzyc.util.StringUtil;

@Controller("appShopCartAction")
public class AppShopCartAction extends AppBaseAction {

    private static final long serialVersionUID = -3398467138350913970L;
    @Resource
    private ShopCartInfoService shopCartInfoService;
    // static Logger logger = Logger.getLogger(AppShopCartAction.class);
    private static Logger logger = LoggerFactory.getLogger(AppShopCartAction.class);
    private ReturnResult<Object> returnResult;
    private AppShopCartOperation operation;


    /**
     * 初始化请求 公共参数 转换成 AppShopCartOperation 对象
     */
    public void init() {
        JSONObject json = AppJsonUtils.getJsonObject(getRequest());
        operation = JSONObject.toJavaObject(json, AppShopCartOperation.class);
        operation.setIslogin(true);
        // 设置用户ID
        String uid = super.getUserid();
        if (StringUtil.isEmpty(uid)) {
            uid = (String) getRequest().getAttribute("kmAppId");
            operation.setIslogin(false); // uid为空，则表示未登录
        }
        operation.setUid(uid);
        if (StringUtil.isEmpty(operation.getPid())) {
            operation.setPid("");
        }


        // 设置数量
        if (StringUtil.isEmpty(operation.getPnum()) || Integer.valueOf(operation.getPnum()) <= 0) {
            operation.setPnum(1);
        }

    }

    /**
     * 添加商品到购物车,支持批量
     *
     * <pre>
     * 必输项：
     * uid (登录用户ID)
     * pid (单品/套餐ID 以逗号分开，如果为单笔可以不加逗号)
     * ptype (产品类型 c:套餐,n:单品)
     * pnum (数量)
     *
     * <pre>
     */
    public void addProduct() {

        try {

            // 初始请求参数
            this.init();

            // 必输项校验
            if (StringUtil.isEmpty(operation.getUid()) || StringUtil.isEmpty(operation.getSid()) ||
                    StringUtil.isEmpty(operation.getPtype())) {
                operation.setErrorInfo("必输项为空");
            }

            if (!StringUtil.isEmpty(operation.getPtype()) &&
                    !ShopcartConstants.S_SC_SKUTYPE_C.equals(operation.getPtype()) &&
                    !ShopcartConstants.S_SC_SKUTYPE_N.equals(operation.getPtype())) {
                operation.setErrorInfo("商品类型错误");
            }

            // 校验不通过
            if (!StringUtil.isEmpty(operation.getErrorInfo())) {
                returnResult = new ReturnResult<Object>(InterfaceResultCode.FAILED,
                        operation.getErrorInfo(), operation.toJsonString());
                print(returnResult);
                return;
            }


            // 用户信息
            ShopCartUserInfo user = new ShopCartUserInfo(operation.getUid(), operation.getIslogin(),
                    operation.getUtype(), null);

            // 添加的商品
            String[] skuIds = operation.getSid().split(ShopcartConstants.B_SC_SEPARATOR);

            // mkw add 20151010 去除数组重复数据
            Set<String> set = new TreeSet<String>();
            Collections.addAll(set, skuIds);
            skuIds = set.toArray(new String[set.size()]);
            // end

            boolean r = false;

            if (ShopcartConstants.S_SC_SKUTYPE_C.equals(operation.getPtype())) {
                // 添加套餐
                r = shopCartInfoService.addComposition(user, operation.getPnum(), skuIds[0]);

            } else if (ShopcartConstants.S_SC_SKUTYPE_N.equals(operation.getPtype())) {
                // 添加单品
                r = shopCartInfoService.addProduct(user, "APP", operation.getPnum(), skuIds);

            } else {

                logger.info(" AppShopCartAction.addProduct() 添加购物车成功,商品类型错误");

            }

            returnResult =
                    r ? new ReturnResult<Object>(InterfaceResultCode.SUCCESS, "添加购物车成功", null) :
                            new ReturnResult<Object>(InterfaceResultCode.FAILED, "添加购物车失败", null);

        } catch (ServiceException e) {
            logger.info(" AppShopCartAction.addProduct() " + e.getErrorString(), e);
            returnResult = new ReturnResult<Object>(String.valueOf(e.getErrorCode()),
                    e.getErrorString(), operation.toJsonString());
        } catch (Exception e) {
            logger.info(" AppShopCartAction.addProduct() 添加购物车异常", e);
            returnResult = new ReturnResult<Object>(InterfaceResultCode.FAILED, "添加购物车失败", e);
        } finally {
            print(returnResult);
        }
    }

    /**
     * 删除购物车商品,支持批量
     *
     * <pre>
     *    必输项：
     *    uid (登录用户ID)
     *    pid (单品/套餐ID 以逗号分开，如果为单笔可以不加逗号，
     *            单笔 n_ 开头，套装 c_ 开头)
     * </pre>
     */
    public void deleteProduct() {

        try {
            // 初始化请求参数
            this.init();

            // 必输项校验
            if (StringUtil.isEmpty(operation.getUid()) || StringUtil.isEmpty(operation.getPid())) {
                operation.setErrorInfo("必输项为空");
            }

            String[] fields = operation.getPid().split(ShopcartConstants.B_SC_SEPARATOR);
            for (String str : fields) {
                if (!str.startsWith(ShopcartConstants.B_SC_SKUHEAD_C_) &&
                        !str.startsWith(ShopcartConstants.B_SC_SKUHEAD_N_)) {
                    operation.setErrorInfo(str + "_格式错误");
                }
            }

            // 校验不通过
            if (!StringUtil.isEmpty(operation.getErrorInfo())) {
                returnResult = new ReturnResult<Object>(InterfaceResultCode.FAILED,
                        operation.getErrorInfo(), operation.toJsonString());
                print(returnResult);
                return;
            }

            // 删除
            boolean r = shopCartInfoService.deleteProduct(operation.getUid(), fields);

            returnResult =
                    r ? new ReturnResult<Object>(InterfaceResultCode.SUCCESS, "删除购物车商品成功", null) :
                            new ReturnResult<Object>(InterfaceResultCode.FAILED, "删除购物车商品失败", null);

        } catch (ServiceException e) {
            logger.info(" AppShopCartAction.deleteProduct() " + e.getErrorString(), e);
            returnResult = new ReturnResult<Object>(String.valueOf(e.getErrorCode()),
                    e.getErrorString(), operation.toJsonString());
        } catch (Exception e) {
            logger.info(" AppShopCartAction.deleteProduct() 删除购物车商品异常", e);
            returnResult = new ReturnResult<Object>(InterfaceResultCode.FAILED, "删除购物车商品失败", e);
        } finally {
            print(returnResult);
        }
    }

    /**
     * 修改购物车商品数量
     *
     * <pre>
     *    必输项：
     *    uid (登录用户ID)
     *    pid (单品/套餐ID)
     *    pnum (数量)
     *
     *    可为空：
     *    ptype (产品类型 c:套餐,n:单品) ,为空，则默认pid=产品类型+pid
     *
     * <pre>
     */
    public void updateProductNumber() {

        try {

            // 初始化请求参数
            this.init();

            // 必输项校验
            if (StringUtil.isEmpty(operation.getUid()) || StringUtil.isEmpty(operation.getPid())) {
                operation.setErrorInfo("必输项为空");
            }

            // 校验不通过
            if (!StringUtil.isEmpty(operation.getErrorInfo())) {
                returnResult = new ReturnResult<Object>(InterfaceResultCode.FAILED,
                        operation.getErrorInfo(), operation.toJsonString());
                print(returnResult);
                return;
            }

            String field = "";
            if (ShopcartConstants.S_SC_SKUTYPE_C.equals(operation.getPtype())) {

                field = ShopcartConstants.B_SC_SKUHEAD_C_ + operation.getPid();
            } else if (ShopcartConstants.S_SC_SKUTYPE_N.equals(operation.getPtype())) {

                field = ShopcartConstants.B_SC_SKUHEAD_N_ + operation.getPid();
            } else {

                field = operation.getPid();
            }

            // 修改
            boolean r = shopCartInfoService
                    .updateProductInShopCar(operation.getUid(), field, operation.getPnum());

            returnResult =
                    r ? new ReturnResult<Object>(InterfaceResultCode.SUCCESS, "修改购物车商品数量成功", null) :
                            new ReturnResult<Object>(InterfaceResultCode.FAILED, "修改购物车商品数量失败",
                                    null);

        } catch (ServiceException e) {
            logger.info(" AppShopCartAction.updateProductNumber() " + e.getErrorString(), e);
            returnResult = new ReturnResult<Object>(String.valueOf(e.getErrorCode()),
                    e.getErrorString(), operation.toJsonString());
        } catch (Exception e) {
            logger.info(" AppShopCartAction.updateProductNumber() 修改购物车商品数量异常", e);
            returnResult = new ReturnResult<Object>(InterfaceResultCode.FAILED, "修改购物车商品数量失败", e);
        } finally {
            print(returnResult);
        }
    }

    /**
     * 删除购物车所有选中的商品
     *
     * <pre>
     *    必输项：
     *    uid (登录用户ID)
     *
     * <pre>
     */
    public void deleteAllCheckProduct() {
        try {
            // 初始化请求参数
            this.init();

            // 必输项校验
            if (StringUtil.isEmpty(operation.getUid())) {
                operation.setErrorInfo("必输项为空");
            }

            // 校验不通过
            if (!StringUtil.isEmpty(operation.getErrorInfo())) {
                returnResult = new ReturnResult<Object>(InterfaceResultCode.FAILED,
                        operation.getErrorInfo(), operation.toJsonString());
                print(returnResult);
                return;
            }

            boolean r = shopCartInfoService.removeChecked(operation.getUid());

            returnResult = r ?
                    new ReturnResult<Object>(InterfaceResultCode.SUCCESS, "删除购物车所有选中商品成功", null) :
                    new ReturnResult<Object>(InterfaceResultCode.FAILED, "删除购物车所有选中商品失败", null);

        } catch (ServiceException e) {
            logger.info(" AppShopCartAction.deleteAllCheckProduct() " + e.getErrorString(), e);
            returnResult = new ReturnResult<Object>(String.valueOf(e.getErrorCode()),
                    e.getErrorString(), operation.toJsonString());
        } catch (Exception e) {
            logger.info(" AppShopCartAction.deleteAllCheckProduct() 删除购物车所有选中商品异常", e);
            returnResult = new ReturnResult<Object>(InterfaceResultCode.FAILED, "删除购物车所有选中商品失败", e);
        } finally {
            print(returnResult);
        }
    }

    /**
     * 全选购物车所有商品
     *
     * <pre>
     *    必输项：
     *    uid (登录用户ID)
     *    checked(是否选中（true/false）)
     *
     * </pre>
     */
    public void checkAllProduct() {
        try {
            // 初始化请求参数
            this.init();

            // 必输项校验
            if (StringUtil.isEmpty(operation.getUid())) {
                operation.setErrorInfo("必输项为空");
            }

            // 校验不通过
            if (!StringUtil.isEmpty(operation.getErrorInfo())) {
                returnResult = new ReturnResult<Object>(InterfaceResultCode.FAILED,
                        operation.getErrorInfo(), operation.toJsonString());
                print(returnResult);
                return;
            }

            boolean r = shopCartInfoService
                    .checkAllProduct(operation.getUid(), operation.getChecked());

            // mkw 20151016 modif
            // returnResult =
            // r
            // ? new ReturnResult<Object>(InterfaceResultCode.SUCCESS, "全选购物车所有商品成功", null)
            // : new ReturnResult<Object>(InterfaceResultCode.FAILED, "全选购物车所有商品失败", null);

            returnResult = new ReturnResult<Object>(InterfaceResultCode.SUCCESS, "全选购物车所有商品成功",
                    null);
            // end

        } catch (ServiceException e) {
            logger.info(" AppShopCartAction.checkAllProduct() " + e.getErrorString(), e);
            returnResult = new ReturnResult<Object>(String.valueOf(e.getErrorCode()),
                    e.getErrorString(), operation.toJsonString());
        } catch (Exception e) {
            logger.info(" AppShopCartAction.checkAllProduct() 选中购物车商品异常", e);
            returnResult = new ReturnResult<Object>(InterfaceResultCode.FAILED, "全选购物车所有商品失败", e);
        } finally {
            print(returnResult);
        }
    }

    /**
     * 选中购物车商品,支持批量
     *
     * <pre>
     *    必输项：
     *    pid (单品/套餐ID 以逗号分开，如果为单笔可以不加逗号，
     *            单笔 n_ 开头，套装 c_ 开头)
     *    uid (登录用户ID)
     *    checked(是否选中（true/false）)
     *
     * </pre>
     */
    public void checkProduct() {
        try {
            // 初始化请求参数
            this.init();

            // 必输项校验
            if (StringUtil.isEmpty(operation.getUid()) || StringUtil.isEmpty(operation.getPid())) {
                operation.setErrorInfo("必输项为空");
            }

            String[] fields = operation.getPid().split(ShopcartConstants.B_SC_SEPARATOR);
            for (String str : fields) {
                if (!str.startsWith(ShopcartConstants.B_SC_SKUHEAD_C_) &&
                        !str.startsWith(ShopcartConstants.B_SC_SKUHEAD_N_)) {
                    operation.setErrorInfo(str + "格式错误");
                }
            }

            // 校验不通过
            if (!StringUtil.isEmpty(operation.getErrorInfo())) {
                returnResult = new ReturnResult<Object>(InterfaceResultCode.FAILED,
                        operation.getErrorInfo(), operation.toJsonString());
                print(returnResult);
                return;
            }
            shopCartInfoService
                    .checkShopAllProduct(operation.getUid(), fields, operation.getChecked());


            // mkw 20151016 modif
            // returnResult =
            // r
            // ? new ReturnResult<Object>(InterfaceResultCode.SUCCESS, "选中购物车商品成功", null)
            // : new ReturnResult<Object>(InterfaceResultCode.FAILED, "选中购物车商品失败", null);

            returnResult = new ReturnResult<Object>(InterfaceResultCode.SUCCESS, "选中购物车商品成功", null);
            // end

        } catch (ServiceException e) {
            logger.info(" AppShopCartAction.checkProduct() " + e.getErrorString(), e);
            returnResult = new ReturnResult<Object>(String.valueOf(e.getErrorCode()),
                    e.getErrorString(), operation.toJsonString());
        } catch (Exception e) {
            logger.info(" AppShopCartAction.checkProduct() 选中购物车商品异常", e);
            returnResult = new ReturnResult<Object>(InterfaceResultCode.FAILED, "选中购物车商品失败", e);
        } finally {
            print(returnResult);
        }
    }

    /**
     * 设置商品参加的活动
     *
     * <pre>
     *    必输项：
     *    pid （产品ID）
     *    promotionid (活动ID)
     *    uid (登录用户ID)
     * </pre>
     */
    public void updateProductPromotion() {

        try {
            // 初始化请求参数
            this.init();

            // 必输项校验
            if (StringUtil.isEmpty(operation.getUid()) || StringUtil.isEmpty(operation.getPid()) ||
                    StringUtil.isEmpty(operation.getPromotionid())) {
                operation.setErrorInfo("必输项为空");
            }

            if (!StringUtil.isEmpty(operation.getPid()) &&
                    !operation.getPid().startsWith(ShopcartConstants.B_SC_SKUHEAD_C_) &&
                    !operation.getPid().startsWith(ShopcartConstants.B_SC_SKUHEAD_N_)) {
                operation.setErrorInfo(operation.getPid() + "格式错误");
            }

            if (!StringUtil.isEmpty(operation.getPromotionid())) {
                try {
                    Long.valueOf(operation.getPromotionid());
                } catch (Exception e) {
                    operation.setErrorInfo(operation.getPid() + "格式错误");
                }
            }

            // 校验不通过
            if (!StringUtil.isEmpty(operation.getErrorInfo())) {
                returnResult = new ReturnResult<Object>(InterfaceResultCode.FAILED,
                        operation.getErrorInfo(), operation.toJsonString());
                print(returnResult);
                return;
            }

            boolean r = shopCartInfoService
                    .choosePromotionInfo(operation.getUid(), operation.getPid(),
                            operation.getPromotionid());
            returnResult =
                    r ? new ReturnResult<Object>(InterfaceResultCode.SUCCESS, "设置商品参加的活动成功", null) :
                            new ReturnResult<Object>(InterfaceResultCode.FAILED, "设置商品参加的活动失败",
                                    null);

        } catch (ServiceException e) {
            logger.info(" AppShopCartAction.updateProductPromotion() " + e.getErrorString(), e);
            returnResult = new ReturnResult<Object>(String.valueOf(e.getErrorCode()),
                    e.getErrorString(), operation.toJsonString());
        } catch (Exception e) {
            logger.info(" AppShopCartAction.updateProductPromotion() 设置商品参加的活动异常", e);
            returnResult = new ReturnResult<Object>(InterfaceResultCode.FAILED, "设置商品参加的活动失败", e);
        } finally {
            print(returnResult);
        }
    }

    /**
     * 选择加价购商品
     *
     * <pre>
     *    uid (登录用户ID)
     *    pid ({skuId（商品ID）:skuNum(商品数量)} json字符串)
     *    itemid(ShopCartItemId)
     * </pre>
     */
    @SuppressWarnings("unchecked")
    public void chooseIncreaseProduct() {
        try {
            // 初始化请求参数
            this.init();

            // 必输项校验
            if (StringUtil.isEmpty(operation.getUid()) ||
                    StringUtil.isEmpty(operation.getItemid())) {
                operation.setErrorInfo("必输项为空");
            }

            // 校验不通过
            if (!StringUtil.isEmpty(operation.getErrorInfo())) {
                returnResult = new ReturnResult<Object>(InterfaceResultCode.FAILED,
                        operation.getErrorInfo(), operation.toJsonString());
                print(returnResult);
                return;
            }

            Map<Integer, Integer> map = JSONObject
                    .parseObject("{" + operation.getPmap() + "}", Map.class);

            boolean r = shopCartInfoService
                    .chooseGift(operation.getUid(), operation.getItemid(), map);

            returnResult =
                    r ? new ReturnResult<Object>(InterfaceResultCode.SUCCESS, "选择加价购商品成功", null) :
                            new ReturnResult<Object>(InterfaceResultCode.FAILED, "选择加价购商品失败", null);

        } catch (ServiceException e) {
            logger.info(" AppShopCartAction.chooseIncreaseProduct() " + e.getErrorString(), e);
            returnResult = new ReturnResult<Object>(String.valueOf(e.getErrorCode()),
                    e.getErrorString(), operation.toJsonString());
        } catch (Exception e) {
            logger.info(" AppShopCartAction.chooseIncreaseProduct() 选择加价购商品异常", e);
            returnResult = new ReturnResult<Object>(InterfaceResultCode.FAILED, "选择加价购商品失败", e);
        } finally {
            print(returnResult);
        }
    }

    /**
     * 删除加价购商品
     *
     * <pre>
     *    必输项：
     *    uid (登录用户ID)
     *    itemid(ShopCartItemId)
     *    pid(产品ID)=dataid
     * </pre>
     */
    public void deleteIncreaseProduct() {
        try {
            // 初始化请求参数
            this.init();

            // 必输项校验
            if (StringUtil.isEmpty(operation.getUid()) ||
                    StringUtil.isEmpty(operation.getDataid()) ||
                    StringUtil.isEmpty(operation.getItemid())) {
                operation.setErrorInfo("必输项为空");
            }

            // 校验不通过
            if (!StringUtil.isEmpty(operation.getErrorInfo())) {
                returnResult = new ReturnResult<Object>(InterfaceResultCode.FAILED,
                        operation.getErrorInfo(), operation.toJsonString());
                print(returnResult);
                return;
            }

            boolean r = shopCartInfoService
                    .deleteGift(operation.getUid(), operation.getItemid(), operation.getDataid());

            returnResult =
                    r ? new ReturnResult<Object>(InterfaceResultCode.SUCCESS, "删除加价购商品成功", null) :
                            new ReturnResult<Object>(InterfaceResultCode.FAILED, "删除加价购商品失败", null);

        } catch (ServiceException e) {
            logger.info(" AppShopCartAction.deleteIncreaseProduct() " + e.getErrorString(), e);
            returnResult = new ReturnResult<Object>(String.valueOf(e.getErrorCode()),
                    e.getErrorString(), operation.toJsonString());
        } catch (Exception e) {
            logger.info(" AppShopCartAction.deleteIncreaseProduct() 删除加价购商品异常", e);
            returnResult = new ReturnResult<Object>(InterfaceResultCode.FAILED, "删除加价购商品失败", e);
        } finally {
            print(returnResult);
        }

    }

    /**
     * 选择赠品
     *
     * <pre>
     *    必输项：
     *    uid (登录用户ID)
     *    itemid (ShopCartItemId)
     *    presents（满赠条件）
     * </pre>
     */
    public void choosePresentGift() {
        try {
            // 初始化请求参数
            this.init();

            // 必输项校验
            if (StringUtil.isEmpty(operation.getUid()) ||
                    StringUtil.isEmpty(operation.getPresents()) ||
                    StringUtil.isEmpty(operation.getItemid())) {
                operation.setErrorInfo("必输项为空");
            }

            // 校验不通过
            if (!StringUtil.isEmpty(operation.getErrorInfo())) {
                returnResult = new ReturnResult<Object>(InterfaceResultCode.FAILED,
                        operation.getErrorInfo(), operation.toJsonString());
                print(returnResult);
                return;
            }

            boolean r = shopCartInfoService
                    .choosePresents(operation.getUid(), operation.getItemid(),
                            operation.getPresents());

            returnResult =
                    r ? new ReturnResult<Object>(InterfaceResultCode.SUCCESS, "选择赠品成功", null) :
                            new ReturnResult<Object>(InterfaceResultCode.FAILED, "选择赠品失败", null);

        } catch (ServiceException e) {
            logger.info(" AppShopCartAction.choosePresentGift() " + e.getErrorString(), e);
            returnResult = new ReturnResult<Object>(String.valueOf(e.getErrorCode()),
                    e.getErrorString(), operation.toJsonString());
        } catch (Exception e) {
            logger.info(" AppShopCartAction.choosePresentGift()选择赠品异常", e);
            returnResult = new ReturnResult<Object>(InterfaceResultCode.FAILED, "选择赠品失败", e);
        } finally {
            print(returnResult);
        }
    }

    /**
     * 合并购物车
     */
    public void mergeShopCart() {

        try {
            // 初始化请求参数
            this.init();

            // 必输项校验
            if (StringUtil.isEmpty(operation.getUid()) ||
                    StringUtil.isEmpty(operation.getTempuid())) {
                operation.setErrorInfo("必输项为空");
            }

            // 校验不通过
            if (!StringUtil.isEmpty(operation.getErrorInfo())) {
                returnResult = new ReturnResult<Object>(InterfaceResultCode.FAILED,
                        operation.getErrorInfo(), operation.toJsonString());
                print(returnResult);
                return;
            }

            boolean r = shopCartInfoService
                    .mergeShopCar(operation.getUid(), operation.getTempuid());

            returnResult =
                    r ? new ReturnResult<Object>(InterfaceResultCode.SUCCESS, "合并购物车成功", null) :
                            new ReturnResult<Object>(InterfaceResultCode.FAILED, "合并购物车失败", null);

        } catch (ServiceException e) {
            logger.info(" AppShopCartAction.mergeShopCart() " + e.getErrorString(), e);
            returnResult = new ReturnResult<Object>(String.valueOf(e.getErrorCode()),
                    e.getErrorString(), operation.toJsonString());
        } catch (Exception e) {
            logger.info(" AppShopCartAction.mergeShopCart()合并购物车异常", e);
            returnResult = new ReturnResult<Object>(InterfaceResultCode.FAILED, "合并购物车失败", e);
        } finally {
            print(returnResult);
        }
    }

    /**
     * 获取购物车JSON对象
     */
    public JSONObject getShopCartJsonObject() {
        ShopCartUserInfo info = new ShopCartUserInfo(operation.getUid(), operation.getIslogin(),
                operation.getUtype(), null);
        return this.getShopcartInfo(info);
    }


    public ReturnResult<Object> getReturnResult() {
        return returnResult;
    }

    public void setReturnResult(ReturnResult<Object> returnResult) {
        this.returnResult = returnResult;
    }

    public AppShopCartOperation getOperation() {
        return operation;
    }

    public void setOperation(AppShopCartOperation operation) {
        this.operation = operation;
    }

    static SerializeConfig mapping = new SerializeConfig();

    static {
        // SellerShop
        Map<String, String> shopCartMapping = new HashMap<String, String>();
        shopCartMapping.put("reductionMoney", "reductionMoney");
        shopCartMapping.put("additionalMoney", "additionalMoney");
        // shopCartMapping.put("uncalculateMoney", "uncalculateMoney");
        shopCartMapping.put("checkTotalMoney", "checkTotalMoney");
        shopCartMapping.put("allMoney", "allMoney");
        // shopCartMapping.put("productCount", "productCount");
        shopCartMapping.put("checkedProductCount", "checkedProductCount");
        // shopCartMapping.put("sellerShopSet", "sellerShopList");
        shopCartMapping.put("productPvalue", "productPvalue");
        shopCartMapping.put("giftStockMap", "giftStockMap");// 赠品加价购商品库存信息

        // shopCartMapping.put("supplierFareMap", "supplierFareMap");

        // Promotion.class
        Map<String, String> promotionMapping = new HashMap<String, String>();
        promotionMapping.put("id", "id");
        promotionMapping.put("name", "name");
        promotionMapping.put("typeName", "typeName");


        // SellerShop
        Map<String, String> shopMapping = new HashMap<String, String>();
        shopMapping.put("sellerId", "id");
        shopMapping.put("shopName", "name");
        shopMapping.put("freight", "freight");
        shopMapping.put("shopCartItemSet", "itemList");
        shopMapping.put("reductionMoney", "reductionMoney");
        shopMapping.put("additionalMoney", "additionalMoney");
        shopMapping.put("uncalculateMoney", "uncalculateMoney");
        shopMapping.put("checkTotalMoney", "checkTotalMoney");
        shopMapping.put("allMoney", "allMoney");
        shopMapping.put("productCount", "productCount");
        shopMapping.put("checkedProductCount", "checkedProductCount");
        shopMapping.put("freePrice", "freePrice");
        // mkw 20160119 增加PV
        shopMapping.put("productPvalue", "productPvalue");
        shopMapping.put("firstHeavyFreight", "firstHeavyFreight");

        // ShopCartItem
        Map<String, String> shopItemMapping = new HashMap<String, String>();
        shopItemMapping.put("id", "id");
        shopItemMapping.put("promotionName", "promotionName");
        shopItemMapping.put("promotionType", "promotionType");
        shopItemMapping.put("promotionTypeName", "promotionTypeName");
        shopItemMapping.put("meet", "meet");
        shopItemMapping.put("ruleGifts", "ruleGifts");// map
        shopItemMapping.put("rulePresents", "rulePresents");// map
        shopItemMapping.put("gifts", "gifts");
        shopItemMapping.put("defaultPresents", "presents");// 已选赠品key
        shopItemMapping.put("coupon", "coupon");// 已送优惠券
        shopItemMapping.put("countChoosed", "countChoosed");// 已选奖品数量
        shopItemMapping.put("giftCount", "giftCount");// 可选奖品数量
        shopItemMapping.put("checkTotalMoney", "checkTotalMoney");// 选择商品的总金额（计算了活动的）
        shopItemMapping.put("carProductSet", "productList");// 产品列表
        shopItemMapping.put("describe", "describe");// 活动满足后的描述 (已减5元)
        // mkw 20160119 增加PV
        shopItemMapping.put("productPvalue", "productPvalue");

        // ShopCartProduct
        Map<String, String> shopCartProductMapping = new HashMap<String, String>();
        shopCartProductMapping.put("id", "id");
        shopCartProductMapping.put("title", "title");
        shopCartProductMapping.put("img", "img");
        shopCartProductMapping.put("count", "count");
        shopCartProductMapping.put("check", "check");
        shopCartProductMapping.put("finalPrice", "finalPrice");
        shopCartProductMapping.put("productPriceTotal", "productPriceTotal");
        shopCartProductMapping.put("pid", "pid");
        shopCartProductMapping.put("pvalue", "pvalue");
        shopCartProductMapping.put("orderPromotionId", "orderPromotionId");
        shopCartProductMapping.put("orderPromtotions", "orderPromtotions");
        shopCartProductMapping.put("pricePromtotion", "pricePromtotion");
        shopCartProductMapping.put("productList", "childProductList");
        shopCartProductMapping.put("productReminder", "reminder");
        shopCartProductMapping.put("freight", "freight");
        shopCartProductMapping.put("stockCount", "stockCount");
        // mkw 20160119 增加附赠活动
        shopCartProductMapping.put("affixProductList", "affixProductList"); // 附赠商品

        // mkw 20160331 增加限购
        shopCartProductMapping.put("min", "minCount"); // 最小购买
        shopCartProductMapping.put("maxApp", "maxCount"); // 最大购买
        shopCartProductMapping.put("userPurchase", "userPurchase"); // 已购买

        // mkw 20160119 附赠商品
        Map<String, String> promotionProductDataImgMapping = new HashMap<String, String>();
        promotionProductDataImgMapping.put("productSkuId", "productSkuId");
        promotionProductDataImgMapping.put("appNum", "num");
        promotionProductDataImgMapping.put("appImagePath", "img");
        promotionProductDataImgMapping.put("appProductTitle", "productTitle");
        promotionProductDataImgMapping.put("stockCount", "stockCount");

        // NormalCartProduct
        Map<String, String> taocanProductMapping = new HashMap<String, String>();
        taocanProductMapping.put("productSkuId", "id");
        taocanProductMapping.put("title", "title");
        taocanProductMapping.put("img", "img");
        taocanProductMapping.put("amount", "count");
        taocanProductMapping.put("finalPrice", "finalPrice");
        // mkw 20160119 增加PV
        shopCartProductMapping.put("pvalue", "pvalue");

        // gift
        Map<String, String> giftMapping = new HashMap<String, String>();
        giftMapping.put("amount", "amount");
        giftMapping.put("dataId", "dataId");
        giftMapping.put("id", "id");
        giftMapping.put("appImg", "img");
        giftMapping.put("stockCount", "stockCount");
        giftMapping.put("maxAmount", "maxAmount");
        giftMapping.put("meetData", "meetData");
        giftMapping.put("name", "name");
        giftMapping.put("price", "price");


        // ShopCartProductReminder
        Map<String, String> reminderMapping = new HashMap<String, String>();
        reminderMapping.put("code", "code");
        reminderMapping.put("message", "message");
        reminderMapping.put("normal", "normal");


        mapping.put(ShopCart.class, new JavaBeanSerializer(ShopCart.class, shopCartMapping));
        mapping.put(Promotion.class, new JavaBeanSerializer(Promotion.class, promotionMapping));
        mapping.put(SellerShop.class, new JavaBeanSerializer(SellerShop.class, shopMapping));
        mapping.put(ShopCartItem.class,
                new JavaBeanSerializer(ShopCartItem.class, shopItemMapping));
        mapping.put(ShopCartProduct.class,
                new JavaBeanSerializer(ShopCartProduct.class, shopCartProductMapping));
        mapping.put(NormalCartProduct.class,
                new JavaBeanSerializer(NormalCartProduct.class, taocanProductMapping));
        mapping.put(PromotionProductData.class,
                new JavaBeanSerializer(PromotionProductData.class, promotionProductDataImgMapping));
        mapping.put(Gift.class, new JavaBeanSerializer(Gift.class, giftMapping));
        mapping.put(ShopCartProductReminder.class,
                new JavaBeanSerializer(ShopCartProductReminder.class, reminderMapping));
        mapping.put(Date.class, new SimpleDateFormatSerializer("yyyy年MM月dd日 HH时mm分ss秒"));
        mapping.put(BigDecimal.class, new ObjectSerializer() {
            @Override
            public void write(JSONSerializer serializer, Object object, Object fieldName,
                              Type fieldType) throws IOException {
                if (object == null) {
                    serializer.getWriter().writeNull();
                    return;
                }
                // serializer.getContext().get
                BigDecimal data = (BigDecimal) object;
                String text = NumberUtil.toBigDecimal(data).toString();
                serializer.write(text);
            }
        });
        /*
         * mapping.put(HashMap.class, new ObjectSerializer() {
         * 
         * @Override public void write(JSONSerializer serializer, Object object, Object fieldName,
         * Type fieldType) throws IOException { if (object == null) {
         * serializer.getWriter().writeNull(); return; } Map<?, ?> data = (Map<?, ?>) object;
         * serializer.write(data.values()); } });
         */
    }

    public void getShopcartInfo() {
        try {
            init();
            ShopCartUserInfo info = new ShopCartUserInfo(operation.getUid(), operation.getIslogin(),
                    null, null);
            JSONObject shopcartjson = getShopcartInfo(info);
            if (shopcartjson.isEmpty()) {
                returnResult = new ReturnResult<Object>(InterfaceResultCode.SUCCESS,
                        "获取购物车信息成功,内容为空", shopcartjson);
            } else {
                returnResult = new ReturnResult<Object>(InterfaceResultCode.SUCCESS, "获取购物车信息成功",
                        shopcartjson);
            }

        } catch (Exception e) {
            logger.error("", e);
            returnResult = new ReturnResult<Object>(InterfaceResultCode.FAILED, "获取购物车信息失败",
                    e.getMessage());
        }
        print(returnResult);
    }


    private JSONObject getShopcartInfo(ShopCartUserInfo info) {
        JSONObject jsonshopcart = new JSONObject();
        ShopCart shopcart = shopCartInfoService.generateSettlement(info);
        if (null == shopcart) {
            return jsonshopcart;
        }
        // mkw modif 20150923 当购物车信息为空时，返回成功
        Map<String, ShopCartProduct> productMap = shopcart.getProductMap();
        if (productMap == null) {
            return jsonshopcart;
        }
        // end

        SellerShopList list = shopcart.getSellerShopList();

        // 设置赠品加价购商品库存
        Map<String, Integer> giftStockMap = shopcart.getGiftStockMap();

        if (null != giftStockMap && !giftStockMap.isEmpty()) {
            Iterator<ShopCartItem> giftIterator = shopcart.getShopCartItemMap().values().iterator();
            while (giftIterator.hasNext()) {
                ShopCartItem item = giftIterator.next();
                // 可选赠品
                Map<String, List<Gift>> reuleMap = item.getRulePresents();
                if (null != reuleMap && !reuleMap.isEmpty()) {
                    Iterator<List<Gift>> giftInerator = reuleMap.values().iterator();
                    while (giftInerator.hasNext()) {
                        List<Gift> giftList = giftInerator.next();
                        for (Gift gift : giftList) {
                            Integer giftStock = giftStockMap.get(gift.getId());
                            if (null == giftStock) {
                                gift.setStockCount(0);
                                gift.setMaxAmount(0);
                            } else {
                                gift.setStockCount(giftStock);
                                gift.setMaxAmount(giftStock);
                            }
                        }
                    }
                }

                // 可选加价购
                Map<String, Gift> giftMap = item.getRuleGifts();
                if (null != giftMap && !giftMap.isEmpty()) {
                    Iterator<Gift> giftInerator = giftMap.values().iterator();
                    while (giftInerator.hasNext()) {
                        Gift gift = giftInerator.next();
                        // 默认可选数量为1
                        gift.setAmount(1);
                        Integer giftStock = giftStockMap.get(gift.getId());
                        if (null == giftStock) {
                            gift.setStockCount(0);
                            gift.setMaxAmount(0);
                        } else {
                            gift.setStockCount(giftStock);
                            gift.setMaxAmount(giftStock);
                        }
                    }
                }

                // 已选加价购
                Set<Gift> gifts = item.getGifts();
                if (null != gifts && !gifts.isEmpty()) {
                    Iterator<Gift> giftInerator = gifts.iterator();
                    while (giftInerator.hasNext()) {
                        Gift gift = giftInerator.next();

                        Integer giftStock = giftStockMap.get(gift.getId());
                        if (null == giftStock) {
                            gift.setStockCount(0);
                            gift.setMaxAmount(0);
                        } else {
                            gift.setStockCount(giftStock);
                            gift.setMaxAmount(giftStock);
                        }
                    }
                }
            }
        }
        // end


        Iterator<ShopCartProduct> iterator = productMap.values().iterator();
        while (iterator.hasNext()) {
            ShopCartProduct scp = iterator.next();
            // if (childPorductList == null || childPorductList.isEmpty()) {
            // continue;
            // }

            if (scp.getProductList() != null && scp.getProductList().size() > 0) {
                // 统计累计积分
                scp.setPvalue(scp.getPvalue().multiply(scp.getAmount()));

                // 设置套餐子商品标题
                for (NormalCartProduct n : scp.getProductList()) {
                    n.setAmount(n.getAmount() * scp.getAmount().intValue());
                    if (!StringUtils.isEmpty(n.getSkuAttrValue())) {
                        n.setTitle(n.getTitle() + " " + n.getSkuAttrValue());
                    }
                }
            } else {
                // 设置单品加收总运费
                scp.setFreight(scp.getFreightTotal());// bug 5223

                ProductPromotion pp = scp.getProductPromotion();
                if (pp != null) {

                    if (pp.getPricePromotionApp() != null) {
                        pp.setPricePromotion(pp.getPricePromotionApp());
                    }

                    // 设置附赠商品的图片路径、标题
                    List<PromotionProductData> pds = pp.getAffixProductList();
                    if (null != pds && pds.size() > 0) {
                        for (PromotionProductData pd : pds) {
                            // 图片全路径
                            pd.setAppImagePath(scp.getImgConnectPath(pd.getImagePath()));
                            pd.setAppNum(scp.getAmount().longValue() * pd.getNum());


                            // 标题
                            pd.setAppProductTitle(pd.getProductTitle() + " " +
                                    (StringUtils.isEmpty(pd.getSkuAttrValue()) ? "" :
                                            pd.getSkuAttrValue()));
                            // 设置库存
                            Integer giftStock = giftStockMap
                                    .get(String.valueOf(pd.getProductSkuId()).trim());
                            if (null == giftStock) {
                                pd.setStockCount(0);
                            } else {
                                pd.setStockCount(giftStock);
                            }
                        }
                    }
                }
            }


        }


        Set<SellerShop> sellerShopSet = list.getSellerShopSet();
        // 设置商家运费及免运费金额
        Map<Long, SupplierFare> sellerFare = shopcart.getSupplierFareMap();
        if (null != sellerFare) {
            for (SellerShop sellerShop : sellerShopSet) {
                SupplierFare supplierFare = sellerFare.get(sellerShop.getSellerId());
                if (null != supplierFare) {
                    sellerShop.setFirstHeavyFreight(supplierFare.getFirstHeavyFreight());
                    sellerShop.setFreePrice(supplierFare.getFreePrice());
                } else {
                    sellerShop.setFirstHeavyFreight(BigDecimal.ZERO);
                    sellerShop.setFreePrice(BigDecimal.ZERO);
                }
            }
        }
        // end

        jsonshopcart.put("shopList", sellerShopSet);
        // jsonshopcart.put("giftStock", shopcart.getGiftStockMap());
        jsonshopcart.put("shopcartTotal", shopcart);
        // jsonshopcart.put("shopList", sellerShopSet);
        return jsonshopcart;
    }

    public void print(Object obj) {
        try {
            JSONSerializer serializer = new JSONSerializer(mapping);
            serializer.config(SerializerFeature.DisableCircularReferenceDetect, true);
            serializer.write(obj);
            String jsonString = serializer.toString();
            printString(jsonString);
        } catch (Exception e) {
            logger.error("app输出购物车json异常", e);
            // e.printStackTrace();
        }
    }
    /*
     * public void printOut(Object obj) { JSONSerializer serializer = new JSONSerializer();
     * serializer.config(SerializerFeature.DisableCircularReferenceDetect, true);
     * serializer.write(obj); String jsonString = serializer.toString(); printString(jsonString); }
     */
}
