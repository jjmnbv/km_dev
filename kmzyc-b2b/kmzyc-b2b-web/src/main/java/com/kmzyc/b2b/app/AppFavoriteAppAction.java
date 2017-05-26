package com.kmzyc.b2b.app;

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
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.kmzyc.b2b.model.Favorite;
import com.kmzyc.b2b.model.FavoriteResponse;
import com.kmzyc.b2b.service.OrderAssessDetailService;
import com.kmzyc.b2b.service.ProductPriceService;
import com.kmzyc.b2b.service.ProductSkuService;
import com.kmzyc.b2b.service.ProductStockService;
import com.kmzyc.b2b.service.member.MyFavoriteService;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.framework.constants.InterfaceResultCode;
import com.kmzyc.zkconfig.ConfigurationUtil;

@SuppressWarnings("unchecked")
@Scope("prototype")
@Controller("favoriteGoodsAction")
public class AppFavoriteAppAction extends AppBaseAction {

    /**
     * 序列号
     */
    private static final long serialVersionUID = -5498560341208506850L;
    // private static final Logger logger = Logger.getLogger(AppFavoriteAppAction.class);
    private static Logger logger = LoggerFactory.getLogger(AppFavoriteAppAction.class);

    @Resource(name = "myFavoriteServiceImpl")
    private MyFavoriteService myFavoriteService;

    @Resource(name = "productPriceService")
    private ProductPriceService productPriceService;

    @Resource(name = "productStockServiceImpl")
    private ProductStockService productStockService;

    @Resource(name = "productSkuServiceImpl")
    private ProductSkuService productSkuService;
    @Resource
    private OrderAssessDetailService orderAssessDetailService;


    private ReturnResult<Map<String, Object>> returnResult;
    private Long uid;
    private String token;
    private String message = "请求参数错误";
    private String code = InterfaceResultCode.FAILED;

    // private String productImgServerUrl = ConfigurationUtil.getString("PRODUCT_IMG_PATH");

    /**
     * 添加商品到收藏夹
     * 
     * @return
     */
    public void insertFavorite() {
        JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
        if (null != jsonParams && !jsonParams.isEmpty()) {
            uid = Long.parseLong(getUserid());
            String contentCode = jsonParams.getString("contentCode");
            String price = jsonParams.getString("price");// 当前销售价格
            Favorite favorite = new Favorite(uid, contentCode, new BigDecimal(price));
            try {
                boolean isSaved = myFavoriteService.isSavedFavorite(uid, contentCode);
                if (isSaved) {
                    message = "该商品已经收藏";
                    code = InterfaceResultCode.FAILED;
                    returnResult = new ReturnResult<Map<String, Object>>(code, message, null);
                    printJsonString(returnResult);
                } else {
                    myFavoriteService.insertFavorite(favorite);
                    code = InterfaceResultCode.SUCCESS;
                    message = "成功";
                }
            } catch (SQLException e) {
                message = "失败";
                code = InterfaceResultCode.FAILED;
                logger.error("添加商品至收藏夹出错：" + e.getMessage(), e);
            }
        }
        returnResult = new ReturnResult<Map<String, Object>>(code, message, null);
        printJsonString(returnResult);
    }

    /**
     * 获取用户收藏列表
     * 
     * @return
     */
    public void getMyFavoriteProductList() {
        // pagintion = this.getPagination(5, 10);
        try {
            JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
            if (null != jsonParams && !jsonParams.isEmpty()) {
                uid = Long.parseLong(getUserid());
                // queryType （0-全部、1-降价商品、2-现货）
                String queryType = jsonParams.getString("queryType");
                /*
                 * 修改接口传输参数多个修改为传输类型，旧代码去除 String promotion = jsonParams.getString("promotion");
                 * String depreciate = jsonParams.getString("depreciate"); String inStock =
                 * jsonParams.getString("inStock");
                 */
                // sql查询条件对象
                Map<String, Object> newConditon = new HashMap<String, Object>();
                newConditon.put("userId", uid);
                if (queryType != null) {// 全部
                    newConditon.put("myFavoriteType", queryType);
                }
                /*
                 * 修改接口传输参数多个修改为传输类型，旧代码去除 if (promotion != null && "promotion".equals(promotion)) {
                 * newConditon.put("promotion", promotion); } if (depreciate != null &&
                 * "depreciate".equals(depreciate)) { newConditon.put("depreciate", depreciate); }
                 * if (inStock != null && "inStock".equals(inStock)) { newConditon.put("inStock",
                 * inStock); }
                 */
                // 页码
                int pn = jsonParams.getIntValue("pageNo");
                // 每页条数
                int ps = jsonParams.getIntValue("pageNum");
                if (pn <= 0 || ps <= 0) {
                    pn = 1;
                    ps = 10;
                }
                this.page = pn;
                pagintion = this.getPagination(pn, ps);
                pagintion.setObjCondition(newConditon);
                try {
                    List<FavoriteResponse> myFavoriteList = new ArrayList<FavoriteResponse>();
                    pagintion = myFavoriteService.appFindFavoriteProductByPage(pagintion);
                    List<Favorite> myFavoriteListTemp = pagintion.getRecordList();
                    int favoriteNum = 0;
                    if (null != myFavoriteListTemp && !myFavoriteListTemp.isEmpty()) {
                        // favoriteNum = pagintion.getTotalRecords();
                        productPriceService.getPriceBatch(myFavoriteListTemp, true);
                        List<Long> skuIds = new ArrayList<Long>();
                        for (Iterator<Favorite> iterator = myFavoriteListTemp.iterator(); iterator
                                .hasNext();) {
                            Favorite favorite = iterator.next();
                            skuIds.add(favorite.getProductSkuId());
                        }
                        Map<Long, Integer> stockMap = productStockService.querySkuStock(skuIds);
                        for (Iterator<Favorite> iterator = myFavoriteListTemp.iterator(); iterator
                                .hasNext();) {
                            FavoriteResponse favoriteResponse = new FavoriteResponse();
                            Favorite favorite = iterator.next();
                            if ("0".equals(queryType)) {// 全部
                                favoriteResponse.setSkuId(favorite.getProductSkuId() + "");
                                // favoriteResponse.setContentNum(stockMap.get(favorite.getProductSkuId()));//库存数量
                                // favoriteResponse.setPStatus(favorite.getpStatus());//上下架状态
                                favoriteResponse.setContentCode(favorite.getContentCode());
                                favoriteResponse.setProductName(favorite.getProductName());
                                favoriteResponse.setFinalPrice(favorite.getFinalPrice());// 销售价
                                // favoriteResponse.setMarketPrice(favorite.getPriceCopy());//市场价
                                // 有降价的话，降价具体金额
                                if (favorite.getSpreadPrice().doubleValue() > 0) {
                                    favoriteResponse.setMarkdownPrice(favorite.getSpreadPrice());
                                }
                                // 产品状态（0:草稿、1:待审核、2:待上架、4:下架、5:系统下架、3:上架 6: 审核不通过, -1 :删除, -2违规下架）
                                if ("0".equals(favorite.getpStatus())
                                        || "1".equals(favorite.getpStatus())
                                        || "2".equals(favorite.getpStatus())
                                        || "4".equals(favorite.getpStatus())
                                        || "5".equals(favorite.getpStatus())
                                        || "6".equals(favorite.getpStatus())
                                        || "-1".equals(favorite.getpStatus())
                                        || "-2".equals(favorite.getpStatus())) {
                                    favoriteResponse.setPStatus("3");// 下架
                                } else if ("3".equals(favorite.getpStatus())
                                        && stockMap.get(favorite.getProductSkuId()) != null
                                        && stockMap.get(favorite.getProductSkuId()) > 0) {
                                    favoriteResponse.setPStatus("1");// 上架有库存
                                } else if ("3".equals(favorite.getpStatus())
                                        && stockMap.get(favorite.getProductSkuId()) != null
                                        && stockMap.get(favorite.getProductSkuId()) <= 0) {
                                    favoriteResponse.setPStatus("2");// 卖光了
                                } else {
                                    favoriteResponse.setPStatus("2");// 卖光了
                                }
                                if (favorite.getpValue().doubleValue() > 0) {
                                    favoriteResponse.setPValue(favorite.getpValue());// pv值
                                }
                                String productImgServerUrl =
                                        ConfigurationUtil.getString("PRODUCT_IMG_PATH");
                                favoriteResponse.setTag(favorite.getTag());
                                favoriteResponse.setFavoriteId(favorite.getFavoriteId());
                                favoriteResponse.setImgPath(productImgServerUrl
                                        + favorite.getImgPath());
                                favoriteResponse.setImgPath5(productImgServerUrl
                                        + favorite.getImgPath5());
                                myFavoriteList.add(favoriteResponse);
                            } else if ("1".equals(queryType)
                                    && (favorite.getSpreadPrice().doubleValue() > 0)
                                    && "3".equals(favorite.getpStatus())
                                    && stockMap.get(favorite.getProductSkuId()) != null
                                    && (stockMap.get(favorite.getProductSkuId()) > 0)) {// 降价(只有降价商品且库存大于0且必须为上架状态)
                                favoriteResponse.setSkuId(favorite.getProductSkuId() + "");
                                // favoriteResponse.setContentNum(stockMap.get(favorite.getProductSkuId()));//库存数量
                                // favoriteResponse.setPStatus(favorite.getpStatus());//上下架状态
                                favoriteResponse.setContentCode(favorite.getContentCode());
                                favoriteResponse.setProductName(favorite.getProductName());
                                favoriteResponse.setFinalPrice(favorite.getFinalPrice());// 销售价
                                // favoriteResponse.setMarketPrice(favorite.getPriceCopy());//市场价
                                // 有降价的话，降价具体金额
                                favoriteResponse.setMarkdownPrice(favorite.getSpreadPrice());
                                // 产品状态（0:草稿、1:待审核、2:待上架、4:下架、5:系统下架、3:上架 6: 审核不通过, -1 :删除, -2违规下架）
                                if ("0".equals(favorite.getpStatus())
                                        || "1".equals(favorite.getpStatus())
                                        || "2".equals(favorite.getpStatus())
                                        || "4".equals(favorite.getpStatus())
                                        || "5".equals(favorite.getpStatus())
                                        || "6".equals(favorite.getpStatus())
                                        || "-1".equals(favorite.getpStatus())
                                        || "-2".equals(favorite.getpStatus())) {
                                    favoriteResponse.setPStatus("3");// 下架
                                } else if ("3".equals(favorite.getpStatus())
                                        && stockMap.get(favorite.getProductSkuId()) != null
                                        && stockMap.get(favorite.getProductSkuId()) > 0) {
                                    favoriteResponse.setPStatus("1");// 上架有库存
                                } else if ("3".equals(favorite.getpStatus())
                                        && stockMap.get(favorite.getProductSkuId()) != null
                                        && stockMap.get(favorite.getProductSkuId()) <= 0) {
                                    favoriteResponse.setPStatus("2");// 卖光了
                                } else {
                                    favoriteResponse.setPStatus("2");// 卖光了
                                }
                                if (favorite.getpValue().doubleValue() > 0) {
                                    favoriteResponse.setPValue(favorite.getpValue());// pv值
                                }
                                favoriteResponse.setTag(favorite.getTag());
                                favoriteResponse.setFavoriteId(favorite.getFavoriteId());
                                String productImgServerUrl =
                                        ConfigurationUtil.getString("PRODUCT_IMG_PATH");
                                favoriteResponse.setImgPath(productImgServerUrl
                                        + favorite.getImgPath());
                                favoriteResponse.setImgPath5(productImgServerUrl
                                        + favorite.getImgPath5());
                                myFavoriteList.add(favoriteResponse);
                            } else if ("2".equals(queryType)
                                    && stockMap.get(favorite.getProductSkuId()) != null
                                    && (stockMap.get(favorite.getProductSkuId()) > 0)
                                    && ("3".equals(favorite.getpStatus()))) {// 现货(库存大于0且必须为上架状态)
                                favoriteResponse.setSkuId(favorite.getProductSkuId() + "");
                                // favoriteResponse.setContentNum(stockMap.get(favorite.getProductSkuId()));//库存数量
                                // favoriteResponse.setPStatus(favorite.getpStatus());//上下架状态
                                favoriteResponse.setContentCode(favorite.getContentCode());
                                favoriteResponse.setProductName(favorite.getProductName());
                                favoriteResponse.setFinalPrice(favorite.getFinalPrice());// 销售价
                                // favoriteResponse.setMarketPrice(favorite.getPriceCopy());//市场价
                                // 有降价的话，降价具体金额
                                if (favorite.getSpreadPrice().doubleValue() > 0) {
                                    favoriteResponse.setMarkdownPrice(favorite.getSpreadPrice());
                                }
                                // 产品状态（0:草稿、1:待审核、2:待上架、4:下架、5:系统下架、3:上架 6: 审核不通过, -1 :删除, -2违规下架）
                                if ("0".equals(favorite.getpStatus())
                                        || "1".equals(favorite.getpStatus())
                                        || "2".equals(favorite.getpStatus())
                                        || "4".equals(favorite.getpStatus())
                                        || "5".equals(favorite.getpStatus())
                                        || "6".equals(favorite.getpStatus())
                                        || "-1".equals(favorite.getpStatus())
                                        || "-2".equals(favorite.getpStatus())) {
                                    favoriteResponse.setPStatus("3");// 下架
                                } else if ("3".equals(favorite.getpStatus())
                                        && stockMap.get(favorite.getProductSkuId()) != null
                                        && stockMap.get(favorite.getProductSkuId()) > 0) {
                                    favoriteResponse.setPStatus("1");// 上架有库存
                                } else if ("3".equals(favorite.getpStatus())
                                        && stockMap.get(favorite.getProductSkuId()) != null
                                        && stockMap.get(favorite.getProductSkuId()) <= 0) {
                                    favoriteResponse.setPStatus("2");// 卖光了
                                } else {
                                    favoriteResponse.setPStatus("2");// 卖光了
                                }
                                if (favorite.getpValue().doubleValue() > 0) {
                                    favoriteResponse.setPValue(favorite.getpValue());// pv值
                                }
                                favoriteResponse.setTag(favorite.getTag());
                                favoriteResponse.setFavoriteId(favorite.getFavoriteId());
                                String productImgServerUrl =
                                        ConfigurationUtil.getString("PRODUCT_IMG_PATH");
                                favoriteResponse.setImgPath(productImgServerUrl
                                        + favorite.getImgPath());
                                favoriteResponse.setImgPath5(productImgServerUrl
                                        + favorite.getImgPath5());
                                myFavoriteList.add(favoriteResponse);
                            }

                        }
                    }
                    Map<String, Object> map = new HashMap<String, Object>();
                    List<FavoriteResponse> responseFavoriteResponse = new ArrayList();
                    if ("1".equals(queryType)) {
                        responseFavoriteResponse = getPaging(getPriceSort(myFavoriteList), pn, ps);
                    } else {
                        responseFavoriteResponse = getPaging(myFavoriteList, pn, ps);
                    }
                    map.put("myFavoriteList", responseFavoriteResponse);
                    map.put("favoriteNum", responseFavoriteResponse.size());
                    returnResult =
                            new ReturnResult<Map<String, Object>>(InterfaceResultCode.SUCCESS,
                                    "成功", map);
                } catch (Exception e) {
                    logger.error("查询常见问题出错：" + e.getMessage(), e);
                    returnResult =
                            new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED, "失败",
                                    null);
                }
            } else {
                returnResult = new ReturnResult<Map<String, Object>>(code, message, null);
            }
            printJsonString(returnResult);
        } catch (Exception e) {
            logger.error("查询常见问题出错：" + e.getMessage(), e);
            returnResult =
                    new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED, "失败", null);
            printJsonString(returnResult);
        }
    }

    public void deleteFavorite() {
        try {
            JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
            if (null != jsonParams && !jsonParams.isEmpty()) {
                long favoriteId = jsonParams.getLong("favoriteId");
                myFavoriteService.deleteFavorite(favoriteId);
                returnResult =
                        new ReturnResult<Map<String, Object>>(InterfaceResultCode.SUCCESS, "成功",
                                null);
            } else {
                returnResult =
                        new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED, "失败",
                                null);
            }
        } catch (Exception e) {
            logger.error("删除收藏记录出错：" + e.getMessage(), e);
            returnResult =
                    new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED, "失败", null);
        }
        printJsonString(returnResult);
    }

    /**
     * 查询产品.店铺是否收藏
     * 
     * @author houlin
     * @return
     */
    public void isFavorited() {
        JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
        if (null != jsonParams && !jsonParams.isEmpty()) {
            uid = Long.parseLong(getUserid());
            String operateObject = jsonParams.getString("operateObject"); // 传参类型：operateObject 1商品
                                                                          // 2店铺
            String contentId = jsonParams.getString("contentId");
            try {
                if ("1".equals(operateObject)) {
                    String contentCode =
                            productSkuService.findProductSkuById(Long.parseLong(contentId))
                                    .getProductSkuCode();
                    boolean isSaved = myFavoriteService.isSavedFavorite(uid, contentCode);
                    if (isSaved) {
                        returnResult =
                                new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
                                        "该商品已经收藏过了", null);
                        printJsonString(returnResult);
                    } else {
                        returnResult =
                                new ReturnResult<Map<String, Object>>(InterfaceResultCode.SUCCESS,
                                        "该商品未收藏", null);
                        printJsonString(returnResult);
                    }
                } else {
                    boolean isSaved = myFavoriteService.isSavedFavoriteShop(uid, contentId);
                    if (isSaved) {
                        returnResult =
                                new ReturnResult(InterfaceResultCode.FAILED, "该店铺已经收藏过了", null);
                        printJsonString(returnResult);
                    } else {
                        returnResult =
                                new ReturnResult<Map<String, Object>>(InterfaceResultCode.SUCCESS,
                                        "该店铺未收藏", null);
                        printJsonString(returnResult);
                    }
                }
            } catch (Exception e) {
                logger.error("查询常见问题出错：" + e.getMessage(), e);
                returnResult =
                        new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
                                "查询我的店铺、商品是否收藏失败", null);
                printJsonString(returnResult);
            }
        } else {
            returnResult =
                    new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED, "传参不正确", null);
        }
        printJsonString(returnResult);
    }



    /**
     * 查询我的收藏店铺列表
     * 
     * @author houlin
     * @return
     */
    public void myShopFavorite() {
        try {
            JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
            Map<String, Object> mapConditon = new HashMap<String, Object>();
            Map<String, Object> returnShop = new HashMap<String, Object>();
            if (null != jsonParams && !jsonParams.isEmpty()) {
                uid = Long.parseLong(getUserid());
                // 页码
                int pn = jsonParams.getIntValue("pageNo");
                // 每页条数
                int ps = jsonParams.getIntValue("pageNum");
                if (pn <= 0 || ps <= 0) {
                    pn = 1;
                    ps = 10;
                }
                this.page = pn;
                mapConditon.put("userId", uid);
                pagintion = this.getPagination(pn, ps);
                pagintion.setObjCondition(mapConditon);
                this.pagintion = myFavoriteService.findFavoriteShopByPage(pagintion);
                List<Favorite> favList = pagintion.getRecordList();
                List favResponse = new ArrayList();
                Map<String, Object> resultMap = null;
                boolean setScore = false;
                for (Favorite fav : favList) {
                    setScore = false;
                    Map<String, Object> mapJson = new HashMap<String, Object>();
                    mapJson.put("shopName", fav.getShopName());
                    mapJson.put("area", fav.getArea());
                    mapJson.put("logoImg", fav.getImgPath());
                    try {
                        resultMap =
                                orderAssessDetailService.queryShopScore(fav.getSupplierId());
                        if (null != resultMap && !resultMap.isEmpty()) {
                            mapJson.put("prodDescPoint", resultMap.get("assess_Type_one"));// 宝贝描述相符评分
                            mapJson.put("sendSpeedPoint", resultMap.get("assess_Type_two"));// 卖家发货速度评分
                            mapJson.put("dispSpeedPoint", resultMap.get("assess_Type_three"));// 物流配送速度评分
                            mapJson.put("servicePoint", resultMap.get("assess_Type_four"));// 售前售后服务评分
                            mapJson.put("point", resultMap.get("avergScore"));// 综合评分
                            setScore = true;
                        }
                    } catch (Exception e) {
                        logger.error("", e);
                    }
                    if (!setScore) {
                        mapJson.put("prodDescPoint", 5);// 宝贝描述相符评分
                        mapJson.put("sendSpeedPoint", 5);// 卖家发货速度评分
                        mapJson.put("dispSpeedPoint", 5);// 物流配送速度评分
                        mapJson.put("servicePoint", 5);// 售前售后服务评分
                        mapJson.put("point", 5);// 综合评分
                    }
                    mapJson.put("shopId", fav.getContentCode());
                    favResponse.add(mapJson);
                }
                returnShop.put("myFavorShop", favResponse);
                returnShop.put("totalNum", pagintion.getTotalRecords());
                returnShop.put("pageNo", pn);
                returnShop.put("pageNum", ps);
                returnResult =
                        new ReturnResult<Map<String, Object>>(InterfaceResultCode.SUCCESS,
                                "查询我的收藏店铺成功", returnShop);
            } else {
                returnResult = new ReturnResult<Map<String, Object>>(code, message, null);
            }
        } catch (Exception e) {
            logger.error("查询常见问题出错：" + e.getMessage(), e);
            returnResult =
                    new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED, "查询我的收藏店铺失败",
                            null);
        }
        printJsonString(returnResult);
    }



    /**
     * 收藏、取消收藏商品。收藏
     * 
     * @author houlin
     * @return
     */
    public void changeOrCancelFavorite() {
        JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
        if (null != jsonParams && !jsonParams.isEmpty()) {
            uid = Long.parseLong(getUserid());
            String contentId = jsonParams.getString("contentId");
            String operateObject = jsonParams.getString("operateObject"); // 传参类型：operateObject 1商品
                                                                          // 2店铺
            String type = jsonParams.getString("type"); // 传参类型：type 0取消 1收藏
            String price = jsonParams.getString("price");// 当前销售价格
            String contentCode = "";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("loginId", uid);
            if ("1".equals(operateObject.trim())) { // 商品
                try {
                    contentCode =
                            productSkuService.findProductSkuById(Long.parseLong(contentId))
                                    .getProductSkuCode();
                } catch (Exception e) {
                    logger.error("根据contentId查询商品的skuId出错：" + e.getMessage(), e);
                    returnResult =
                            new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
                                    "根据contentId查询商品的skuId失败", null);
                    printJsonString(returnResult);
                }
                if ("1".equals(type)) { // 收藏
                    logger.info("收藏skucode为：" + contentCode + "的商品");
                    Favorite favorite = new Favorite(uid, contentCode, new BigDecimal(price));
                    try {
                        boolean isSaved = myFavoriteService.isSavedFavorite(uid, contentCode);
                        if (isSaved) {
                            returnResult =
                                    new ReturnResult<Map<String, Object>>(
                                            InterfaceResultCode.FAILED, "该商品已经收藏", null);
                            printJsonString(returnResult);
                        } else {
                            myFavoriteService.insertFavorite(favorite);
                            returnResult =
                                    new ReturnResult<Map<String, Object>>(
                                            InterfaceResultCode.SUCCESS, "商品收藏成功", null);
                            printJsonString(returnResult);
                        }
                    } catch (SQLException e) {
                        logger.error("添加商品至收藏夹出错：" + e.getMessage(), e);
                        returnResult =
                                new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
                                        "添加商品至收藏夹出错", null);
                        printJsonString(returnResult);
                    }
                } else { // 取消收藏
                    logger.info("开始取消收藏skucode为：" + contentCode + "的商品");
                    map.put("skuCode", contentCode);
                    try {
                        myFavoriteService.deleteFavoriteByCode(map);
                    } catch (SQLException e) {
                        logger.error("删除产品收藏记录出错：" + e.getMessage(), e);
                        returnResult =
                                new ReturnResult(InterfaceResultCode.FAILED, "删除产品收藏记录出错", null);
                        printJsonString(returnResult);
                    }
                    returnResult =
                            new ReturnResult(InterfaceResultCode.SUCCESS, "删除产品收藏记录成功", null);
                    printJsonString(returnResult);
                }
            } else {// 店铺
                if ("1".equals(type)) {// 收藏店铺
                    logger.info("收藏shopId为：" + contentId + "的商品");
                    boolean isSaved;
                    try {
                        isSaved = myFavoriteService.isSavedFavoriteShop(uid, contentId);
                        if (isSaved) {
                            returnResult =
                                    new ReturnResult(InterfaceResultCode.SAVED, "成功", "该店铺已保存过了");
                            printJsonString(returnResult);
                        }
                        Favorite favorite = new Favorite(uid, contentId);
                        myFavoriteService.insertFavorite(favorite);
                    } catch (SQLException e) {
                        logger.error("根据contentId收藏商品店铺失败：" + e.getMessage(), e);
                        returnResult =
                                new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
                                        "根据contentId收藏商品店铺失败", null);
                        printJsonString(returnResult);
                    }
                    returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "店铺收藏成功", null);
                    printJsonString(returnResult);
                } else { // 取消收藏店铺
                    logger.info("取消收藏shopId为：" + contentId + "的商品");
                    map.put("skuCode", contentId);
                    try {
                        myFavoriteService.deleteFavoriteByCode(map);
                    } catch (SQLException e) {
                        logger.error("删除产品收藏记录出错：" + e.getMessage(), e);
                        returnResult =
                                new ReturnResult(InterfaceResultCode.FAILED, "删除产品收藏记录出错", null);
                        printJsonString(returnResult);
                    }
                    returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "取消收藏店铺成功", null);
                    printJsonString(returnResult);
                }
            }

        }

    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ReturnResult<Map<String, Object>> getReturnResult() {
        return returnResult;
    }

    public void setReturnResult(ReturnResult<Map<String, Object>> returnResult) {
        this.returnResult = returnResult;
    }

}
