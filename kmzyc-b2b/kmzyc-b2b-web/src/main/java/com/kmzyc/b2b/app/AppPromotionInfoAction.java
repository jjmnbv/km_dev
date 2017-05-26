package com.kmzyc.b2b.app;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.km.framework.page.Pagination;
import com.kmzyc.b2b.model.CmsPromotionTask;
import com.kmzyc.b2b.model.ProductSku;
import com.kmzyc.b2b.model.PromotionInfo;
import com.kmzyc.b2b.model.PromotionProduct;
import com.kmzyc.b2b.service.ProductPriceService;
import com.kmzyc.b2b.service.ProductSkuService;
import com.kmzyc.b2b.service.PromotionInfoService;
import com.kmzyc.b2b.service.PromotionProductService;
import com.kmzyc.b2b.util.PromotionInfoUtil;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.framework.constants.AppIndexStaticParams;
import com.kmzyc.framework.constants.InterfaceResultCode;
import com.kmzyc.framework.constants.SkillTime;
import com.kmzyc.framework.exception.ServiceException;
import com.kmzyc.promotion.app.util.ListUtils;
import com.kmzyc.promotion.optimization.vo.ProductPromotion;
import com.kmzyc.promotion.util.PromotionCacheUtil;
import com.kmzyc.util.StringUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;

/**
 * 手机端活动action
 *
 * @author hewenfeng
 */
@SuppressWarnings("unchecked")
@Controller("appPromotionInfoAction")
@Scope("prototype")
public class AppPromotionInfoAction extends AppBaseAction {

    /**
     *
     */
    private static final long serialVersionUID = 7328812574126655520L;

    // private static final Logger LOGGER = Logger.getLogger(AppShopCarAction.class);
    private static Logger logger = LoggerFactory.getLogger(AppPromotionInfoAction.class);

    private ReturnResult<Object> returnResult;
    @Resource
    private PromotionInfoService promotionInfoService;
    @Resource
    private PromotionProductService promotionProductService;
    @Resource
    ProductPriceService productPriceService;
    @Resource
    private PromotionCacheUtil promotionCacheUtil;

    @Resource
    private ProductSkuService productSkuService;


    private Pagination page;
    private Long promotionId;
    private int numperpage;
    private int currentPage;
    private Long productSkuId;

    /**
     * 初始化参数
     */
    private void initParams() throws Exception {
        JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
        String promotionIdStr = jsonParams.getString("promotionId");
        productSkuId = jsonParams.getLong("productSkuId");
        if (!StringUtil.isEmpty(promotionIdStr)) {
            promotionId = Long.valueOf(promotionIdStr);
        }
        numperpage = jsonParams.getIntValue("ps");
        currentPage = jsonParams.getIntValue("pn");
        if (numperpage != 0) {
            page = new Pagination(currentPage < 1 ? 1 : currentPage, 5, numperpage, null);
        }
    }


    /**
     * 获取活动详细规则信息
     */
    public String getPromotionRule() {
        try {
            initParams();
            PromotionInfo info = promotionInfoService.getPromotionInfoById(promotionId);
            if (info == null) {
                throw new Exception(promotionId + "活动不存在");
            }
            returnResult = new ReturnResult<Object>(InterfaceResultCode.SUCCESS, "获取活动规则信息成功",
                    info);
        } catch (Exception e) {
            logger.error("获取活动规则信息失败", e);
            returnResult = new ReturnResult<Object>(InterfaceResultCode.FAILED,
                    "获取活动规则信息失败。" + e.getMessage(), null);
        }
        clearPrams();
        return SUCCESS;
    }

    /**
     * 获取app首页活动列表
     */
    public String getPromotionImgs() {
        try {
            List<CmsPromotionTask> list = promotionInfoService.getCmsPromotionTaskList();
            JSONArray jsonArray = null;
            if (ListUtils.isNotEmpty(list)) {
                jsonArray = new JSONArray();
                String cmsPath = ConfigurationUtil.getString("cmsPath") + "/";
                for (CmsPromotionTask task : list) {
                    JSONObject json = new JSONObject();
                    json.put("promotionId", task.getId());
                    json.put("startTime", task.getBeginTime());
                    json.put("endTime", task.getEndTime());
                    json.put("imageFile", cmsPath + task.getImagesFile());
                    json.put("imageFile2", cmsPath + task.getImagesFile2());
                    json.put("imageFile3", cmsPath + task.getImagesFile3());
                    json.put("imageFile4", cmsPath + task.getImagesFile4());
                    json.put("promotionName", task.getName());
                    jsonArray.add(json);
                }
            }
            returnResult = new ReturnResult<Object>(InterfaceResultCode.SUCCESS, "获取首页促销活动信息成功",
                    jsonArray);
        } catch (Exception e) {
            logger.error("",e);
            returnResult = new ReturnResult<>(InterfaceResultCode.FAILED,
                    "获取首页促销活动信息失败。" + e.getMessage(), null);
        }

        return SUCCESS;
    }

    /**
     * 获取单品的促销信息
     */
    public String getGoodsPromotionInfo() {
        try {
            initParams();
            if (productSkuId == null) {
                returnResult = new ReturnResult<Object>(InterfaceResultCode.FAILED,
                        "获取商品促销信息失败。skuId为空", null);
            } else {
                ProductSku sku = new ProductSku();
                sku.setProductSkuId(productSkuId);
                promotionInfoService.setProductPricePromotionInfoByDB(sku, new Date());
                // JSONObject newjson = new JSONObject();
                // newjson.put(promotionInfoService.PROMOTION_PRICE,sku.getFinalPrice());
                // newjson.put(PromotionInfoService.PROMOTION_INFO,
                // PromotionInfoUtil.toJsonArray(sku.getPromotionInfoList()));
                returnResult = new ReturnResult<Object>(InterfaceResultCode.SUCCESS, "获取商品促销信息成功",
                        PromotionInfoUtil.toJsonArray(sku.getPromotionInfoList()));
            }
        } catch (ServiceException e) {
            returnResult = new ReturnResult<Object>(InterfaceResultCode.FAILED,
                    "获取商品促销信息失败。" + e.getErrorString(), null);
        } catch (Exception e) {
            returnResult = new ReturnResult<Object>(InterfaceResultCode.FAILED,
                    "获取商品促销信息失败。" + e.getMessage(), null);
        }

        return SUCCESS;
    }

    /**
     * 获取单品的价格
     */
    public String getGoodsPrice() {
        try {
            initParams();
            if (productSkuId == null) {
                returnResult = new ReturnResult<Object>(InterfaceResultCode.FAILED,
                        "获取商品最终价格失败。skuId为空", null);
            } else {
                ProductSku sku = new ProductSku();
                sku.setProductSkuId(productSkuId);
                promotionInfoService.setProductPricePromotionInfoByDB(sku, new Date());
                // JSONObject newjson = new JSONObject();
                // newjson.put(PromotionInfoService.PROMOTION_PRICE,sku.getFinalPrice());
                // newjson.put(promotionInfoService.PROMOTION_INFO,
                // PromotionInfoUtil.toJsonArray(sku.getPromotionInfoList()));
                returnResult = new ReturnResult<Object>(InterfaceResultCode.SUCCESS, "获取商品最终价格成功。",
                        sku.getFinalPrice());
            }
        } catch (ServiceException e) {
            returnResult = new ReturnResult<Object>(InterfaceResultCode.FAILED,
                    "获取商品最终价格失败。" + e.getErrorString(), null);
        } catch (Exception e) {
            returnResult = new ReturnResult<Object>(InterfaceResultCode.FAILED,
                    "获取商品最终价格失败。" + e.getMessage(), null);
        }

        return SUCCESS;
    }

    /**
     * 获取单品的价格和促销
     */
    public String getGoodsPromotionInfoAndPrice() {
        try {
            initParams();
            if (productSkuId == null) {
                returnResult = new ReturnResult<Object>(InterfaceResultCode.FAILED,
                        "获取商品最终价格失败。skuId为空", null);
            } else {
                ProductSku sku = new ProductSku();
                sku.setProductSkuId(productSkuId);
                promotionInfoService.setProductPricePromotionInfoByDB(sku, new Date());
                JSONObject newjson = new JSONObject();
                newjson.put(PromotionInfoService.PROMOTION_PRICE, sku.getFinalPrice());
                newjson.put(PromotionInfoService.PROMOTION_INFO,
                        PromotionInfoUtil.toJsonArray(sku.getPromotionInfoList()));
                returnResult = new ReturnResult<Object>(InterfaceResultCode.SUCCESS,
                        "获取商品最终价格和促销信息成功。", newjson);
            }
        } catch (ServiceException e) {
            returnResult = new ReturnResult<Object>(InterfaceResultCode.FAILED,
                    "获取商品最终价格和促销信息失败。" + e.getErrorString(), null);
        } catch (Exception e) {
            returnResult = new ReturnResult<Object>(InterfaceResultCode.FAILED,
                    "获取商品最终价格和促销信息失败。" + e.getMessage(), null);
        }

        return SUCCESS;
    }


    /**
     * 获取首页信息 add by songmiao 2015-9-16 包括轮播、秒杀、推荐商品 、活动专场、百宝箱、主题馆
     */
    public void getIndexInfo() {
        JSONObject resultJson = new JSONObject();// 最终返回结果
        JSONObject returnObject = new JSONObject(); // returnObject
        JSONArray jsonArray = new JSONArray(); // 各子集公用赋值array
        List<ProductSku> productSkuList = new ArrayList<ProductSku>(); // 公用sku列表

        try {
            resultJson.put("code", InterfaceResultCode.SUCCESS);
            /******************************* 轮播 和 活动专题 列表 ****************************************************/
            List<CmsPromotionTask> list = promotionInfoService.getCmsPromotionTaskList();
            JSONArray jsonArrayBanner = new JSONArray(); // 轮播
            JSONArray jsonArrayPromotion = new JSONArray(); // 活动专场
            if (ListUtils.isNotEmpty(list)) {
                for (CmsPromotionTask task : list) {
                    int i = 0;
                    int j = 0;
                    JSONObject json = new JSONObject();
                    json.put("promotionId", task.getId() == null ? "" : task.getId()); // 活动id
                    if (task.getImagesFile() != null) {
                        json.put("imgUrl", getImagePathIOSCms(task.getImagesFile()));// 图片路径
                    }
                    json.put("path", task.getOutput() == null ? "" : task.getOutput());// 自定义输出路径
                    if (task.getOperateType() == 0) {// 轮播
                        if (i < 5) {
                            jsonArrayBanner.add(json);
                        }
                        i++;
                    } else { // 专场
                        if (j < 2) {
                            jsonArrayPromotion.add(json);
                        }
                        j++;
                    }
                    if (i >= 5 && j >= 2) {
                        break;
                    }
                }
            }
            returnObject.put("banner", jsonArrayBanner);
            returnObject.put("promotion", jsonArrayPromotion);

            /******************************* 秒杀列表 **************************************************************/
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            String nowTime = sdf.format(new Date());
            String skillwindowName = SkillTime.getSkillsIndexType();
            String todayStatus = "0";// 正在进行
            // 获取当前秒杀场次类型对应的星期
            String week = SkillTime.getSkillsWeekType(todayStatus);
            HashMap<String, String> skucondition = new HashMap<String, String>();
            skucondition.put("windowName", skillwindowName);
            skucondition.put("week", week);
            // 根据窗口名称查询秒杀商品skuId ，参数为窗口名 目前写死,当前系统日期所对应的星期
            String skuIds = productSkuService.getSeckillProducts(skucondition);
            if (skuIds != null && !skuIds.equals("")) {
                // 根据skuID 查询商品列表
                productSkuList = productSkuService.getProductBySkuIds(skuIds);
            }
            for (ProductSku p : productSkuList) {
                JSONObject afJson = new JSONObject();
                afJson.put("productSkuId", p.getProductSkuId());
                afJson.put("imgUrl", getImagePathIOS(p.getDefaultProductSkuImgPath()));// 商品图片
                ProductPromotion promotion = promotionCacheUtil
                        .getProductPromtoionInfoCahce(p.getProductSkuId().toString());
                if (promotion != null) {
                    // 如存在活动 用计算后的价格代替原价格
                    p.setPrice(promotion.calculateFinalPrice(p.getPrice()));
                }
                afJson.put("productName", p.getProductName());// 商品名称
                afJson.put("productTitle", p.getProductTitle());// 商品标题
                afJson.put("price", p.getPrice());
                if (p.getPvalue() != null) {
                    afJson.put("pv", p.getPvalue());
                }
                afJson.put("marketPrice", p.getMarketPrice());
                afJson.put("productStatus",
                        p.getStatus()); // 产品状态 0:草稿、1:待审核、2:待上架、4:下架、5:系统下架、3:上架 6:
                // 审核不通过, -1 :删除, -2违规下架
                jsonArray.add(afJson);
            }
            returnObject.put("seckill", jsonArray);
            returnObject.put("nowTime", nowTime);
            returnObject.put("seckillStartTime", getSkillDateStr(SkillTime.getSkillsStartTime()));
            returnObject.put("seckillEndTime", getSkillDateStr(SkillTime.getSkillsEndTime()));
            /************************************* 推荐商品开始查询 ****************************************************/
            // jsonArray = new JSONArray();
            // productSkuList =
            // productSkuService.getRecommendProduct(AppIndexStaticParams.RECOMMEND_COLUMN_NAME);// 栏目名写死
            // for (ProductSku p : productSkuList) {
            // JSONObject afJson = new JSONObject();
            // ProductPromotion productPromotion =
            // promotionCacheUtil.getProductPromtoionInfoCahce(p.getProductSkuId().toString());
            // if (productPromotion != null) {
            // // 如存在活动 用计算后的价格代替原价格
            // p.setPrice(productPromotion.calculateFinalPrice(p.getPrice()));
            // }
            // afJson.put("productSkuId", p.getProductSkuId());
            // afJson.put("imgUrl", p.getDefaultProductSkuImgPath());// 商品图片
            // afJson.put("productName", p.getProductName());
            // afJson.put("price", p.getPrice());
            // jsonArray.add(afJson);
            // }
            // returnObject.put("product", jsonArray);
            /************************************* 百宝箱开始查询 ****************************************************/
            jsonArray = new JSONArray();
            productSkuList = productSkuService
                    .getWindowDatas(AppIndexStaticParams.JBOX_WINDOW_NAME);
            for (ProductSku p : productSkuList) {
                JSONObject afJson = new JSONObject();
                afJson.put("path", p.getProductSkuCode());
                if (p.getDefaultProductSkuImgPath() != null) {
                    afJson.put("imgUrl",
                            getImagePathIOSCms(p.getDefaultProductSkuImgPath()));// 商品图片
                }
                afJson.put("name", p.getProductName());
                jsonArray.add(afJson);
            }
            returnObject.put("jBox", jsonArray);
            /************************************* 广告位开始查询 ****************************************************/
            jsonArray = new JSONArray();
            productSkuList = productSkuService
                    .getWindowDatas(AppIndexStaticParams.ADVERTISING_POSITION_WINDOW_NAME);
            for (ProductSku p : productSkuList) {
                JSONObject afJson = new JSONObject();
                afJson.put("path", p.getProductSkuCode());
                if (p.getDefaultProductSkuImgPath() != null) {
                    afJson.put("imgUrl",
                            getImagePathIOSCms(p.getDefaultProductSkuImgPath()));// 商品图片
                }
                afJson.put("name", p.getProductName());
                jsonArray.add(afJson);
            }
            returnObject.put("advert", jsonArray);
            /************************************* 专题馆开始查询 ****************************************************/
            jsonArray = new JSONArray();
            productSkuList = productSkuService
                    .getWindowDatas(AppIndexStaticParams.THEME_PAVILION_WINDOW_NAME);
            for (ProductSku p : productSkuList) {
                JSONObject afJson = new JSONObject();
                afJson.put("path", p.getProductSkuCode());
                if (p.getDefaultProductSkuImgPath() != null) {
                    afJson.put("imgUrl",
                            getImagePathIOSCms(p.getDefaultProductSkuImgPath()));// 商品图片
                }
                afJson.put("name", p.getProductName());
                jsonArray.add(afJson);
            }
            returnObject.put("thme", jsonArray);
            resultJson.put("returnObject", returnObject);
        } catch (Exception e) {
            logger.error("获取首页信息失败", e);
            resultJson.put("code", InterfaceResultCode.FAILED);
        } finally {
            // System.out.println(resultJson.toString());
            printString(resultJson.toJSONString());
        }


    }

    /**
     * 获取推荐产品列表 add by songmiao 2015-9-16
     */
    public void getRecommendProducts() {
        JSONObject resultJson = new JSONObject();// 最终返回结果
        JSONObject returnObject = new JSONObject(); // returnObject
        JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
        try {
            // 分页参数 每页条数，如果为空 默认为10
            numperpage = (jsonParams == null || jsonParams.get("pageNum") == null ||
                    jsonParams.get("pageNum").toString().isEmpty()) ? 10 :
                    jsonParams.getIntValue("pageNum");
            // 分页参数 当前页数，如果为空 默认为1
            currentPage = (jsonParams == null || jsonParams.get("pageNo") == null ||
                    jsonParams.get("pageNo").toString().isEmpty()) ? 1 :
                    jsonParams.getIntValue("pageNo");
            page = new Pagination(currentPage < 1 ? 1 : currentPage, 5, numperpage, null);
            resultJson.put("code", InterfaceResultCode.SUCCESS);
            JSONArray jsonArray = new JSONArray();
            page = productSkuService
                    .getRecommendProduct(page, AppIndexStaticParams.RECOMMEND_COLUMN_NAME);// 栏目名写死
            List<ProductSku> productSkuList = page.getRecordList();
            if (page.getRecordList() != null) {
                for (ProductSku p : productSkuList) {
                    JSONObject afJson = new JSONObject();
                    ProductPromotion productPromotion = promotionCacheUtil
                            .getProductPromtoionInfoCahce(p.getProductSkuId().toString());
                    if (productPromotion != null) {
                        // 如存在活动 用计算后的价格代替原价格
                        p.setPrice(productPromotion.calculateFinalPrice(p.getPrice()));
                    }
                    afJson.put("productSkuId", p.getProductSkuId());
                    afJson.put("imgUrl", getImagePathIOS(p.getDefaultProductSkuImgPath()));// 商品图片
                    afJson.put("productName", p.getProductName());
                    afJson.put("productTitle", p.getProductTitle());
                    afJson.put("price", p.getPrice());
                    afJson.put("Integral", p.getPvalue() == null ? 0 : p.getPvalue());
                    jsonArray.add(afJson);
                }
                returnObject.put("totalNum", page.getTotalRecords());
                returnObject.put("pageNo", currentPage);
                returnObject.put("pageNum", numperpage);
                returnObject.put("productList", jsonArray);
                resultJson.put("returnObject", returnObject);
            }
        } catch (Exception e) {
            logger.error("获取推荐商品列表失败", e);
            resultJson.put("code", InterfaceResultCode.FAILED);
        } finally {
            // System.out.println(resultJson.toString());
            printString(resultJson.toJSONString());
        }
    }

    /**
     * 获取秒杀商品列表 add by songmiao 2015-9-16 app秒杀页获取商品列表
     */
    public void getSeckillProducts() {
        JSONObject resultJson = new JSONObject();
        JSONObject returnObject = new JSONObject(); // returnObject
        // 获取系统当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        String nowTime = sdf.format(new Date());
        try {
            resultJson.put("code", InterfaceResultCode.SUCCESS);
            HashMap<String, String> params = new HashMap<String, String>();
            SkillTime.initSkillStatus(); // 初始化各场次状态
            /*************************************** 早间场开始查询 ***************************************************/
            params.put("windowName", SkillTime.MORNING_WINDOW);
            params.put("nodeType", "morning");
            params.put("startTime", getSkillDateStr(SkillTime.MORNING));
            params.put("endTime", getSkillDateStr(SkillTime.NOONBREAK));
            params.put("name", "早间场");
            params.put("status", SkillTime.morningStatus);
            params.put("todayStatus", SkillTime.morningTodayStatus);
            params.put("skillStartTime", SkillTime.MORNING);
            getSkillsJson(returnObject, params);
            /*************************************** 午休场开始查询 ***************************************************/
            params.put("windowName", SkillTime.NOONBREAK_WINDOW);
            params.put("nodeType", "noonBreak");
            params.put("startTime", getSkillDateStr(SkillTime.NOONBREAK));
            params.put("endTime", getSkillDateStr(SkillTime.AFTERNOON));
            params.put("name", "午休场");
            params.put("status", SkillTime.noonbreakStatus);
            params.put("todayStatus", SkillTime.noonbreakTodayStatus);
            params.put("skillStartTime", SkillTime.NOONBREAK);
            getSkillsJson(returnObject, params);
            /*************************************** 下午场开始查询 ***************************************************/
            params.put("windowName", SkillTime.AFTERNOON_WINDOW); // 窗口名 定死
            params.put("nodeType", "afternoon");
            params.put("startTime", getSkillDateStr(SkillTime.AFTERNOON)); // 时间段 定死
            params.put("endTime", getSkillDateStr(SkillTime.NIGHT)); // 时间段 定死
            params.put("name", "下午场");
            params.put("status", SkillTime.afternoonStatus);
            params.put("todayStatus", SkillTime.afternoonTodayStatus);
            params.put("skillStartTime", SkillTime.AFTERNOON);
            getSkillsJson(returnObject, params);

            /*************************************** 晚间场开始查询 ***************************************************/
            params.put("windowName", SkillTime.NIGHT_WINDOW);
            params.put("nodeType", "night");
            params.put("startTime", getSkillDateStr(SkillTime.NIGHT));
            params.put("endTime", getSkillDateStr(SkillTime.MIDNIGHT));
            params.put("name", "晚间场");
            params.put("status", SkillTime.nightStatus);
            params.put("todayStatus", SkillTime.nightTodayStatus);
            params.put("skillStartTime", SkillTime.NIGHT);
            getSkillsJson(returnObject, params);
            /*************************************** 午夜场开始查询 ***************************************************/
            params.put("windowName", SkillTime.MIDNIGHT_WINDOW);
            params.put("nodeType", "midnight");
            params.put("startTime", getSkillDateStr(SkillTime.MIDNIGHT));
            params.put("endTime", getSkillDateStr(SkillTime.MORNING));
            params.put("name", "午夜场");
            params.put("status", SkillTime.midnightStatus);
            params.put("todayStatus", SkillTime.midnightTodayStatus);
            params.put("skillStartTime", SkillTime.MIDNIGHT);
            getSkillsJson(returnObject, params);
            returnObject.put("nowTime", nowTime);
            resultJson.put("returnObject", returnObject);
        } catch (Exception e) {
            logger.error("获取秒杀产品列表失败", e);
            resultJson.put("code", InterfaceResultCode.FAILED);
        } finally {
            // System.out.println(resultJson.toJSONString());
            printString(resultJson.toJSONString());
        }
    }

    // 将当前年月日拼接在传入字符串之前，秒杀时间使用
    private String getSkillDateStr(String dateStr) {
        if (dateStr == null) {
            return "";
        }
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return sdf.format(now) + " " + dateStr;
    }

    /**
     * 组装秒杀商品列表json返回数据 add by songmiao 2015-9-18
     *
     * @param json :拼装的json params{开始时间：startTime，结束时间：endTime，节点名称类型：nodeType，节点名称：name,窗口名：windowName,场次状态：status
     *             0:正在进行 1：等待开始 2：已经结束}
     */
    private JSONObject getSkillsJson(JSONObject resultJson, HashMap<String, String> params) {
        JSONArray productsJsonArrary = new JSONArray();
        List<ProductSku> productSkuList = new ArrayList<ProductSku>();
        ProductPromotion promotion;
        Map<String, ProductPromotion> productPromotions = new HashMap<String, ProductPromotion>();
        Set<String> skuIdSet = new HashSet<String>();
        JSONObject json = new JSONObject();
        try {
            // 根据窗口名称查询秒杀商品skuId ，参数为窗口名 目前写死，当前系统日期所对应的星期
            HashMap<String, String> skucondition = new HashMap<String, String>();
            String week = SkillTime.getSkillsWeekType(params.get("todayStatus"));
            skucondition.put("windowName", params.get("windowName"));
            skucondition.put("week", week);
            String todayStatus = params.get("todayStatus");
            String skillStartTime = params.get("skillStartTime");
            String skuIds = productSkuService.getSeckillProducts(skucondition);
            if (skuIds != null && !skuIds.equals("")) {
                // 根据skuID 查询商品列表
                productSkuList = productSkuService.getProductBySkuIds(skuIds);
                CollectionUtils.addAll(skuIdSet, skuIds.split(","));
                // 批量查询出商品参加活动 ,即将开始场次中,获取秒杀商品的活动价，bug:10839
                if (!todayStatus.equals("0")) {
                    // 不在进行的场次，获取场次开始时间，获取活动价
                    Date promotionStartTime = SkillTime
                            .getSkillStartDateStr(skillStartTime, todayStatus);
                    productPromotions = promotionCacheUtil
                            .getProductPromtoionInfoCahceByDate(skuIdSet, promotionStartTime);
                } else {
                    productPromotions = promotionCacheUtil.getProductPromtoionInfoCahce(skuIdSet);
                }
            }
            for (ProductSku p : productSkuList) {
                JSONObject afJson = new JSONObject();
                if (p.getStock() == null) {
                    p.setStock(BigDecimal.ZERO);
                }
                afJson.put("productSkuId", p.getProductSkuId());
                promotion = productPromotions.get(p.getProductSkuId().toString());
                if (promotion != null) {
                    // 如存在活动 用计算后的价格代替原价格
                    p.setPrice(promotion.calculateFinalPrice(p.getPrice()));
                }
                afJson.put("price", p.getPrice());// 最终价格
                afJson.put("markPrice", p.getMarketPrice());// 市场价
                afJson.put("stock", p.getStock());// 商品可用库存
                afJson.put("productStatus",
                        p.getStatus()); // 产品状态 0:草稿、1:待审核、2:待上架、4:下架、5:系统下架、3:上架 6:
                // 审核不通过, -1 :删除, -2违规下架
                if (promotion != null && promotion.getAvailableQuantity() != null) {
                    // 存在活动库存时取活动库存
                    if (Integer.parseInt(p.getStock().toString()) >
                            promotion.getAvailableQuantity()) {
                        // 如果活动库存小于实际库存则取活动库存作为可用库存
                        afJson.put("stock", promotion.getAvailableQuantity());
                    }
                }
                afJson.put("imgUrl", getImagePathIOS(p.getDefaultProductSkuImgPath()));// 商品图片
                afJson.put("productName", p.getProductName());// 商品名称
                afJson.put("productTitle", p.getProductTitle());// 商品标题
                if (p.getPvalue() != null) {
                    afJson.put("pv", p.getPvalue());// pv值
                }
                productsJsonArrary.add(afJson);
            }
            json.put("startTime", params.get("startTime"));
            json.put("endTime", params.get("endTime"));
            json.put("name", params.get("name"));
            json.put("status", params.get("status"));
            json.put("productList", productsJsonArrary);
            resultJson.put(params.get("nodeType").toString(), json);
        } catch (Exception e) {
            logger.error("组装秒杀" + params.get("name") + "场数据失败", e);
        }
        return resultJson;
    }

    /**
     * 获取商品促销信息 add by songmiao 2015-9-16 包括上下架库存活动限购信息
     */
    public void getProductPromotionInfo() {
        JSONObject resultJson = new JSONObject();// 返回结果
        String failReason = " 获取商品促销信息失败";
        try {
            JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
            String productSkuIdStr = jsonParams.getString("productSkuId");
            resultJson.put("code", InterfaceResultCode.SUCCESS);
            if (StringUtil.isEmpty(productSkuIdStr)) {
                resultJson.put("code", InterfaceResultCode.FAILED);
                resultJson.put("message", "productSkuId不能为空");
            } else {
                ProductSku sku = productSkuService
                        .findProductSkuById(Long.valueOf(productSkuIdStr.trim()));
                if (sku == null) {
                    failReason = "所查询sku不存在";
                    throw new Exception(promotionId + "sku不存在");
                }
                JSONObject json = promotionInfoService.getPromotionInfoByProductToJsonForApp(sku);
                resultJson.put("returnObject", json);
            }
        } catch (Exception e) {
            resultJson.put("code", InterfaceResultCode.FAILED);
            resultJson.put("message", failReason);
        } finally {
            System.out.println(resultJson.toJSONString());
            printString(resultJson.toJSONString());
        }

    }


    /**
     * app获取活动信息及活动的商品 add by songmiao 2015-9-17
     */
    public void getPromotionDetail() {
        JSONObject resultJson = new JSONObject();// 返回结果
        JSONObject returnObject = new JSONObject(); // returnObject
        resultJson.put("code", InterfaceResultCode.SUCCESS);// 成功时的code
        String failReason = "调用接口失败";// 失败原因
        try {
            JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
            // JSONObject jsonParams = new JSONObject();
            // jsonParams.put("promotionId", "26182");
            if (null != jsonParams && null != jsonParams.getString("promotionId") &&
                    !jsonParams.getString("promotionId").toString().isEmpty()) {
                String pId = jsonParams.getString("promotionId");
                // 分页参数 每页条数，如果为空 默认为10
                numperpage = (jsonParams.get("pageNum") == null ||
                        jsonParams.get("pageNum").toString().isEmpty()) ? 10 :
                        jsonParams.getIntValue("pageNum");
                // 分页参数 当前页数，如果为空 默认为1
                currentPage = (jsonParams.get("pageNo") == null ||
                        jsonParams.get("pageNo").toString().isEmpty()) ? 1 :
                        jsonParams.getIntValue("pageNo");
                if (numperpage != 0) {
                    page = new Pagination(currentPage < 1 ? 1 : currentPage, 5,
                            numperpage < 1 ? 1 : numperpage, null);
                }

                PromotionInfo info = promotionInfoService.getPromotionInfoById(Long.valueOf(pId));
                if (info == null) {
                    failReason = "所查询活动不存在";
                    throw new Exception(promotionId + "活动不存在");
                }
                // 分页查询活动商品
                page = promotionProductService.getPromotionProductList(page, info);
                List<PromotionProduct> list = page.getRecordList();
                ProductPromotion promotion = null;
                List<PromotionProduct> resultList = new ArrayList<PromotionProduct>();
                // 循环设置价格
                for (PromotionProduct pp : list) {
                    promotion = promotionCacheUtil
                            .getProductPromtoionInfoCahce(pp.getProductSkuId().toString());
                    if (promotion == null) {
                        continue;
                    }
                    // 限购信息
                    if (promotion.getMin() != null) {
                        pp.setMinBuy(promotion.getMin());
                    }
                    if (promotion.getMax() != null) {
                        pp.setMaxBuy(promotion.getMax());
                    }
                    if (promotion.getAvailableQuantity() != null) {
                        pp.setPromotionStock(promotion.getAvailableQuantity());
                    }
                    // 如存在活动 用计算后的价格代替原价格
                    pp.setPrice(promotion.calculateFinalPrice(pp.getPrice()));
                    // 产品活动标签
                    if (promotion.getLabelArray() != null) {
                        pp.setLabelArray(promotion.getLabelArray());
                    }
                    if (pp.getCategoryAttrName() != null) {
                        pp.setProductName(pp.getProductName() + " " + pp.getCategoryAttrName());
                    }

                    resultList.add(pp);
                }
                SimplePropertyPreFilter filter = new SimplePropertyPreFilter(PromotionProduct.class,
                        "imagePathIOS", "productSkuId", "marketPrice", "price", "productName",
                        "minBuy", "maxBuy", "promotionStock");
                // 添加活动信息
                returnObject.put("promotionName", info.getPromotionName()); // 活动名
                if (info.getSellUpType() != 0) {
                    returnObject.put("sellUpType", info.getSellUpType()); // 活动名
                }
                returnObject.put("promotionTypeName", info.getPromotionTypeName()); // 活动类型名
                returnObject.put("code", InterfaceResultCode.SUCCESS);
                returnObject.put("totalNum", page.getTotalRecords());// 商品总条数
                returnObject.put("pageNo", currentPage);
                returnObject.put("pageNum", numperpage);
                returnObject.put("recordList",
                        JSONArray.parseArray(JSONObject.toJSONString(resultList, filter))); // 商品列表
                resultJson.put("returnObject", returnObject);

            } else {
                resultJson.put("message", "传入参数非法");
                resultJson.put("code", InterfaceResultCode.FAILED);
            }

        } catch (Exception e) {
            logger.error("获取活动产品列表失败", e);
            resultJson.put("message", failReason);
            resultJson.put("code", InterfaceResultCode.FAILED);
            System.out.println(resultJson.toJSONString());
        } finally {
            // System.out.println(resultJson.toJSONString());
            printString(resultJson.toJSONString());

        }

    }

    // 拼装app 产品图片绝对路径
    public String getImagePathIOS(String imgeUrl) {
        String productImgPath = ConfigurationUtil.getString("PRODUCT_IMG_PATH");
        String lastChar = productImgPath
                .substring(productImgPath.length() - 1, productImgPath.length());
        String firstChar = imgeUrl.substring(0, 1);
        if (lastChar.equals("/") || lastChar.equals("\\")) {
            productImgPath = productImgPath.substring(0, productImgPath.length() - 1);
        }
        if (!(firstChar.equals("/") || firstChar.equals("\\"))) {
            imgeUrl = "/" + imgeUrl;
        }
        return (productImgPath + imgeUrl).replace(".jpg", "_3.jpg");
    }

    // 拼装app cms图片绝对路径
    public String getImagePathIOSCms(String imgeUrl) {
        String productImgPath = ConfigurationUtil.getString("cmsPath_WAP");
        String lastChar = productImgPath
                .substring(productImgPath.length() - 1, productImgPath.length());
        String firstChar = imgeUrl.substring(0, 1);
        if (lastChar.equals("/") || lastChar.equals("\\")) {
            productImgPath = productImgPath.substring(0, productImgPath.length() - 1);
        }
        if (!(firstChar.equals("/") || firstChar.equals("\\"))) {
            imgeUrl = "/" + imgeUrl;
        }
        return productImgPath + imgeUrl;
    }

    public ReturnResult<Object> getReturnResult() {
        return returnResult;
    }

    public void setReturnResult(ReturnResult<Object> returnResult) {
        this.returnResult = returnResult;
    }

    public void clearPrams() {
        // page = null;
        // this.currentPage = 1;
        // this.numperpage = 0;
        // this.promotionId = null;
        // this.returnResult = null;
    }
}
