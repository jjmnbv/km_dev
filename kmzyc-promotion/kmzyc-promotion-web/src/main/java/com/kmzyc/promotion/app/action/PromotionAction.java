package com.kmzyc.promotion.app.action;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.kmzyc.promotion.app.enums.PromotionShopSort;
import com.kmzyc.promotion.app.enums.PromotionStatus;
import com.kmzyc.promotion.app.enums.PromotionTypeEnums;
import com.kmzyc.promotion.app.maps.MeetDataTypeMap;
import com.kmzyc.promotion.app.maps.SellUpTypeMap;
import com.kmzyc.promotion.app.service.CouponService;
import com.kmzyc.promotion.app.service.ProductPriceCacheService;
import com.kmzyc.promotion.app.service.PromotionInfoService;
import com.kmzyc.promotion.app.service.PromotionProductDataService;
import com.kmzyc.promotion.app.service.PromotionProductService;
import com.kmzyc.promotion.app.service.PromotionRuleDataService;
import com.kmzyc.promotion.app.service.PromotionTypeService;
import com.kmzyc.promotion.app.util.ListUtils;
import com.kmzyc.promotion.app.vobject.Coupon;
import com.kmzyc.promotion.app.vobject.Message;
import com.kmzyc.promotion.app.vobject.PromotionInfo;
import com.kmzyc.promotion.app.vobject.PromotionProduct;
import com.kmzyc.promotion.app.vobject.PromotionRuleData;
import com.kmzyc.promotion.optimization.cache.PromotionProcess;
import com.kmzyc.promotion.optimization.vo.PromotionProductData;

public class PromotionAction extends BaseAction {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Resource
    private PromotionProductService promotionProductService;
    @Resource
    private PromotionTypeService promotionTypeService;
    @Resource
    private CouponService couponService;
    @Resource
    private PromotionInfoService promotionInfoService;
    @Resource
    private ProductPriceCacheService productPriceCacheService;
    @Resource
    private PromotionRuleDataService promotionRuleDataService;
    @Resource
    private PromotionProcess promotionProcess;
    @Resource
    private PromotionProductDataService promotionProductDataService;
    private PromotionInfo promotion;
    private String ruleData; // 促销信息
    private Map<Long, String> exclusionPromotionMap;
    private Message message = new Message();
    // 日志记录
    protected Logger logger = LoggerFactory.getLogger(PromotionAction.class);
    private String operationType; // 判断返回页面类型 ，0 为活动管理页面 1为活动审核页面
    private String onlineStatus; // 时间状态，专门用作查询条件

    private String startTime;
    private String endTime;

    /**
     * 删除活动
     * 
     * @return String @exception
     */
    public void deletePromotion() throws Exception {
        try {
            message = promotionInfoService.deletePromotion(promotion);
        } catch (Exception e) {
            message.setCode(10);
            message.setTitle(e.getMessage());
            // logger.error("删除活动异常 promotionId：" + promotion.getPromotionId());
            logger.error("删除活动异常 promotionId：{}", promotion.getPromotionId(), e);
        }
        JSONObject json = (JSONObject) JSONObject.toJSON(message);
        this.writeJson(json);
    }

    private void setBaseData() {
        this.getRequest().setAttribute("promotionTypeMap",
                promotionTypeService.getPromotionTypeMap());
        this.getRequest().setAttribute("meetDataMap", MeetDataTypeMap.getMap());
        this.getRequest().setAttribute("sellUpTypeMap", SellUpTypeMap.getMap());
    }

    /**
     * 查询促销活动列表
     * 
     * @return
     */
    public String queryPromotionList() {

        String result = "";
        page = this.getPage();
        setBaseData();
        if (promotion == null) {
            promotion = new PromotionInfo();
        }

        if (startTime != null && !startTime.isEmpty()) {
            promotion.setStartTime(getDate(startTime));
        }
        if (endTime != null && !endTime.isEmpty()) {
            promotion.setEndTime(getDate(endTime));
        }

        if (onlineStatus != null && !onlineStatus.isEmpty()) {
            promotion.setPromotionPriority(Integer.valueOf(onlineStatus));
        }
        if (promotion.getPromotionName() != null) {
            promotion.setPromotionName(promotion.getPromotionName().trim());
        }
        result = "success";
        if (promotion.getNature() == null) {
            promotion.setNature(1);
        } else if (promotion.getNature() == 2) {
            result = "increaseList";
        }

        promotion.setUserId(String.valueOf(getLoginUserId()));
        page = promotionInfoService.queryPromotionList(page, promotion);

        return result;
    }

    public String queryCheckPromotionList() throws ParseException {
        String result = "";
        page = this.getPage();
        setBaseData();
        if (promotion == null) {
            promotion = new PromotionInfo();
        }
        if (startTime != null && !startTime.isEmpty()) {
            promotion.setStartTime(getDate(startTime));
        }
        if (endTime != null && !endTime.isEmpty()) {
            promotion.setEndTime(getDate(endTime));
        }
        if (onlineStatus != null && !onlineStatus.isEmpty()) {
            promotion.setPromotionPriority(Integer.valueOf(onlineStatus));
        }
        if (promotion.getPromotionName() != null) {
            promotion.setPromotionName(promotion.getPromotionName().trim());
        }
        result = "success";
        if (promotion.getNature() == null) {
            promotion.setNature(1);
        } else if (promotion.getNature() == 2) {
            result = "increaseList";
        }
        promotion.setPromotionDescribe("  ");
        promotion.setUserId(String.valueOf(getLoginUserId()));
        page = promotionInfoService.queryPromotionList(page, promotion);

        return result;
    }

    /**
     * 修改促销活动
     * 
     * @return
     */
    public String updatePromotion() {
        if (message == null) {
            message = new Message();
        }
        if (promotion.getProductFilterType() == null && promotion.getStatus() != null
                && promotion.getStatus() != null
                && promotion.getStatus() == PromotionStatus.NOT_ISSUE.getValue().intValue()) {
            promotion.setProductFilterSql(null);
            promotion.setProductFilterType(2);
        }
        if (promotion.getProductFilterSql() != null && !promotion.getProductFilterSql().isEmpty()) {
            String[] productFilers = promotion.getProductFilterSql().split(",");
            StringBuffer productFiltersSql = new StringBuffer();
            for (String productFilter : productFilers) {
                if (productFilter != null && !productFilter.trim().isEmpty()) {
                    productFiltersSql.append(productFilter).append(",");
                }
            }
            promotion.setProductFilterSql(productFiltersSql.toString());
        }
        promotionInfoService.updatePromotion(promotion);
        message.setModule("promotionAdd");
        return SUCCESS;
    }

    /**
     * 查询存在时间冲突的活动列表
     */
    public String selectConflictPromotion() {
        setBaseData();
        // 当活动类型为全部商家时，互斥活动选择列表查询所有商家类型活动
        if (promotion != null && promotion.getShopSort() != null && promotion.getShopSort()
                .intValue() == PromotionShopSort.ALL.getValue().intValue()) {
            promotion.setShopSort(null);
        }
        List<PromotionInfo> list = promotionInfoService.queryMutexPromotionList(promotion);
        this.getRequest().setAttribute("dataList", list);
        return SUCCESS;
    }

    /**
     * 审核或者撤销审核一个活动
     */
    public void updateIssuePromotion() {
        JSONObject json = new JSONObject();
        Long promotionId = promotion.getPromotionId();
        int code = 0;
        String title = "";
        try {
            code = promotionInfoService.updataPromotionStatus(promotionId);
            /*
             * 0：操作成功 1：操作失败，活动已上线，无法进行该操作，如果活动有问题，你可以撤销活动！ 2：操作失败，活动存在特价价格小于等于0的商品
             */
            switch (code) {
                case 0:
                    title = "操作成功 ！";
                    break;
                case 1:
                    title = "操作失败。活动已上线，无法进行该操作，如果活动有问题，你可以撤销活动！ ";
                    break;
                case 2:
                    title = "操作失败，活动存在特价价格小于等于0的商品。 ";
                    break;
                case 3:
                    title = "操作失败，活动信息不完整。 ";
                    break;
                case 4:
                    title = "操作失败，没有指定商品。 ";
                    break;
                case 5:
                    title = "操作失败,存在没有指定附赠商品的商品。 ";

            }
        } catch (Exception e) {
            logger.error("审核或者撤销审核一个活动出现异常", e);
            code = 4;
            title = e.getMessage();
        }
        json.put("success", code == 0);
        json.put("title", title);
        this.strWriteJson(json.toJSONString());
    }

    public void updatePromotionEndTime() {
        JSONObject json = new JSONObject();
        promotion.setEndTime(new Date());
        try {
            promotionInfoService.updatePromotion(promotion);

            json.put("code", 0);
        } catch (Exception e) {
            logger.error("撤销活动异常 promotionId：{}", promotion.getPromotionId(), e);
            json.put("code", 1);
        }

        this.strWriteJson(json.toJSONString());
    }

    /**
     * 修改更新活动优先级序号
     */
    public void updatePromotionPriority() {
        JSONObject json = new JSONObject();
        try {
            if (promotionInfoService.checkPromotionPriorityIsVaild(promotion)) {
                promotionInfoService.updatePromotion(promotion);

                json.put("code", 0);
                json.put("title", "修改成功");
            } else {
                json.put("code", 1);
                json.put("title", "序号不能重复");
            }
        } catch (Exception e) {
            logger.error("变更活动优先级{}异常：", promotion.getPromotionId(), e);
        }
        this.strWriteJson(json.toJSONString());
    }

    public void checkPromoitonTimeInCoupon() {
        JSONObject json = new JSONObject();
        Date start = promotion.getStartTime();
        Date end = promotion.getEndTime();
        Long pid = promotion.getPromotionId();
        long startTime = start.getTime();
        long endTime = end.getTime();
        boolean isSuccess = false;
        try {
            // pe.createCriteria().andPromotionRuleIdEqualTo(promotion.getPromotionRuleId());
            List<PromotionRuleData> list = promotionRuleDataService.selectPromotionRuleList(pid);
            int i = 0;
            if (ListUtils.isNotEmpty(list)) {
                long couponStartTime = 0;
                long couponEndTime = 0;
                do {
                    PromotionRuleData ruleDate = list.get(i);
                    Long couponId = ruleDate.getEntityId();
                    Coupon coupon = couponService.queryCouponById(couponId);
                    if (coupon.getStarttime() != null && coupon.getEndtime() != null) {
                        couponStartTime = coupon.getStarttime().getTime();
                        couponEndTime = coupon.getEndtime().getTime();
                        isSuccess = startTime >= couponStartTime && startTime <= couponEndTime
                                && endTime >= couponStartTime && endTime <= couponEndTime;
                    } else {
                        isSuccess = true;
                    }

                    i++;
                } while (isSuccess && i < list.size());

            }

        } catch (NumberFormatException e) {
            logger.error("优惠券数字类型异常：", e);
        } catch (Exception e) {
            logger.error("优惠券异常：", e);
        }
        json.put("isSuccess", isSuccess);
        this.strWriteJson(json.toJSONString());
    }

    public void copyPromotion() {
        JSONObject json = new JSONObject();
        boolean isSuccess = false;
        try {
            promotionInfoService.copyPromotion(promotion.getPromotionId());
            isSuccess = true;
        } catch (Exception e) {
            logger.error("复制活动发生异常！", e);
        }
        json.put("isSuccess", isSuccess);
        this.strWriteJson(json.toJSONString());
    }

    // 更新单个活动缓存
    public void creatCache() {
        JSONObject json = new JSONObject();
        try {
            productPriceCacheService.notifyByPromotionInfoId(promotion.getPromotionId());
            json.put("code", 0);
        } catch (Exception e) {
            json.put("code", 1);
        }
        this.strWriteJson(json.toJSONString());
    }

    /**
     * 更新所有活动的产品缓存
     */
    public void updatePromotionCache() {
        try {
            List<PromotionInfo> list =
                    promotionInfoService.getExpiryPromotionListByTime(new Date());
            if (null != list && !list.isEmpty()) {
                for (PromotionInfo p : list) {
                    productPriceCacheService.notifyByPromotionInfoId(p.getPromotionId());
                    logger.info("活动" + p.getPromotionId() + "更新缓存成功！");
                }
            }
            this.strWriteJson("更新活动的产品缓存成功");
        } catch (Exception e) {
            this.strWriteJson("更新活动的产品缓存成功" + e.getMessage());
        }
    }

    public Map<Long, String> getExclusionPromotionMap() {
        return exclusionPromotionMap;
    }

    public void setExclusionPromotionMap(Map<Long, String> exclusionPromotionMap) {
        this.exclusionPromotionMap = exclusionPromotionMap;
    }

    /* new promotion 20140627 */
    public String toAddPromotionNew() {
        setBaseData();
        return SUCCESS;
    }

    public void addPromotionNew() {
        try {
            // 添加活动
            String promotionTitle = getRequest().getParameter("promotionTitle");
            String promotionType = getRequest().getParameter("promotionType");
            String shopSort = getRequest().getParameter("shopSort");
            String ruleData = getRequest().getParameter("ruleData");
            String shopCodes = getRequest().getParameter("shopCodes");
            String sellUpType = getRequest().getParameter("sellUpType");
            // 订单级活动满足条件类型，满 元/件数
            String meetDataType = getRequest().getParameter("meetDataType");
            promotion = new PromotionInfo();
            promotion.setCreaterUser(super.getLoginUserId());
            if (promotionTitle != null) { // 标题去空格
                promotion.setPromotionTitle(promotionTitle.trim());
            }

            promotion.setPromotionType(Integer.valueOf(promotionType));
            promotion.setShopSort(Integer.valueOf(shopSort));
            if (shopSort.equals("2")) { // 指定入驻商家
                promotion.setSupplierId(Long.valueOf(shopCodes));
            }
            if (sellUpType != null && !sellUpType.isEmpty()) { // 活动库存卖光后操作类型 1：停止销售直到活动结束；
                                                               // 2：恢复原价销售；
                promotion.setSellUpType(Integer.valueOf(sellUpType));
            }
            promotionInfoService.addPromotionNew(promotion, ruleData, meetDataType);
        } catch (Exception e) {
            logger.error("添加促销活动异常：", e);
            sendResult(1, "操作失败", e.getMessage());
        }
        sendResult(0, "操作成功", promotion.getPromotionId());
    }

    @SuppressWarnings("unchecked")
    public String toUpdataPromotionNew() {
        try {
            promotion = promotionInfoService.getPromotionById(promotion.getPromotionId());
            ruleData = getRuleData(promotion);
            promotion.setPromotionNote(ruleData);

            page = super.getPage();
            PromotionProduct promotionProduct = new PromotionProduct();
            promotionProduct.setCategory("-1");// 例外商品标识
            promotionProduct.setPromotionId(promotion.getPromotionId());
            setBaseData();
            page = promotionProductService.queryPromotionProductList(page, promotionProduct);
            List<PromotionProduct> rlist = page.getDataList();
            for (PromotionProduct pp : rlist) {
                List<PromotionProductData> list = promotionProductDataService
                        .queryListBypromotionProductId(pp.getPromotionProductId());
                pp.setPromotionProductDataList(list);
            }
            setBaseData();
        } catch (Exception e) {
            logger.error("跳转编辑活动页面异常：", e);
            return ERROR;
        }

        return SUCCESS;
    }

    /**
     * 拼装优惠信息 ,更新活动页面使用 2015-7-31 add by songmiao
     * 
     * @param promotion
     * @return
     */
    public String getRuleData(PromotionInfo promotion) {
        StringBuffer sf = new StringBuffer();//
        try {
            if (promotion.getPromotionType() != null && promotion
                    .getPromotionType() == PromotionTypeEnums.GANT.getValue().intValue()) {
                if (promotion.getPromotionData() != null) {
                    List<PromotionProductData> ppdList = promotionProductDataService
                            .queryListByPromotionId(promotion.getPromotionId());
                    StringBuffer bf = new StringBuffer();
                    bf.append("统一赠品为：");
                    int i = 0;
                    for (PromotionProductData p : ppdList) {
                        if (i == 0) {
                            bf.append("skuId为")
                                    .append("<a title='点击预览' href='javascript:;' onclick='javascript:toProductSku("
                                            + p.getProductSkuId() + ")'>")
                                    .append(p.getProductSkuId()).append("</a>的商品")
                                    .append(p.getNum()).append("件");
                        } else {
                            bf.append(";skuId为")
                                    .append("<a title='点击预览' href='javascript:;' onclick='javascript:toProductSku("
                                            + p.getProductSkuId() + ")'>")
                                    .append(p.getProductSkuId()).append("</a>的商品")
                                    .append(p.getNum()).append("件");
                        }
                        i = i + 1;
                    }
                    return bf.toString();
                } else {
                    return "无统一赠品";
                }
            }
            if (promotion.getRuleDatas() == null || promotion.getRuleDatas().size() < 1) {
                String sellUpType =
                        promotion.getSellUpType() == 1 ? "，卖光后停止销售直到活动结束。" : "，卖光后恢复原价销售。";
                if (promotion.getPromotionType() == PromotionTypeEnums.SALE.getValue().intValue()) {
                    return promotion.getPromotionData() == null ? "无统一特价" + sellUpType
                            : "统一特价:" + promotion.getPromotionData() + sellUpType;
                } else if (promotion.getPromotionType() == PromotionTypeEnums.DISCOUNT.getValue()
                        .intValue()) {
                    return "活动价为现在价格的"
                            + Long.valueOf(promotion.getPromotionData()
                                    .multiply(BigDecimal.valueOf(10l)).intValue())
                            + "%" + sellUpType;
                } else if (promotion.getPromotionType() == PromotionTypeEnums.SALE_APP.getValue()
                        .intValue()) {
                    return promotion.getPromotionData() == null ? "无统一特价" + sellUpType
                            : "统一特价:" + promotion.getPromotionData() + sellUpType;
                }
            }
            List<PromotionRuleData> list = promotion.getRuleDatas();
            String meetDataType = "件";
            if (list.get(0).getMeetDataType() == 1) {
                meetDataType = "元";
            }
            String meetData = "";
            String num = "";
            if (promotion.getPromotionType() == PromotionTypeEnums.INCREASE.getValue().intValue()) {
                for (PromotionRuleData promotionRuleData : list) {
                    if (!meetData.equals(promotionRuleData.getMeetData().toString())
                            && !meetData.equals("")) {
                        sf.append("共" + num + "件;<br/>")
                                .append("满" + promotionRuleData.getMeetData() + meetDataType + ",加"
                                        + promotionRuleData.getPrizeData() + "元可得skuId为")
                                .append("<a title='点击预览' href='javascript:;' onclick='javascript:toProductSku("
                                        + promotionRuleData.getEntityId() + ")'>")
                                .append(promotionRuleData.getEntityId()).append("</a>的商品,");
                    } else {
                        if (meetData.equals("")) {
                            sf.append("满" + promotionRuleData.getMeetData() + meetDataType + " ,");
                        }
                        sf.append("加" + promotionRuleData.getPrizeData() + "元可得skuId为");
                        sf.append(
                                "<a title='点击预览' href='javascript:;' onclick='javascript:toProductSku("
                                        + promotionRuleData.getEntityId() + ")'>")
                                .append(promotionRuleData.getEntityId()).append("</a>的商品,");
                    }
                    meetData = promotionRuleData.getMeetData().toString();
                    num = promotionRuleData.getNum1().toString();
                }
                sf.append("共").append(num).append("件。");
            } else if (promotion.getPromotionType() == PromotionTypeEnums.GIFT.getValue()
                    .intValue()) {
                for (PromotionRuleData promotionRuleData : list) {
                    if (!meetData.equals(promotionRuleData.getMeetData().toString())
                            && !meetData.equals("")) {
                        sf.append(";<br/>满" + promotionRuleData.getMeetData() + meetDataType + ",赠")
                                .append("skuId为<a title='点击预览' href='javascript:;' onclick='javascript:toProductSku(")
                                .append(promotionRuleData.getEntityId()).append(")'>")
                                .append(promotionRuleData.getEntityId()).append("</a>的商品")
                                .append(promotionRuleData.getNum1()).append("件");
                    } else {
                        if (meetData.equals("")) {
                            sf.append("满" + promotionRuleData.getMeetData() + meetDataType);
                        }
                        sf.append(
                                ",赠skuId为<a title='点击预览' href='javascript:;' onclick='javascript:toProductSku(")
                                .append(promotionRuleData.getEntityId()).append(")'>")
                                .append(promotionRuleData.getEntityId()).append("</a>的商品")
                                .append(promotionRuleData.getNum1()).append("件");
                    }

                    meetData = promotionRuleData.getMeetData().toString();
                    num = promotionRuleData.getNum1().toString();
                }
                sf.append("。");
            } else {
                return "";
            }
        } catch (Exception e) {
            logger.error("拼装优惠数据信息异常promotionId ：{}", promotion.getPromotionId(), e);
            return ERROR;
        }
        return sf.toString();
    }

    public void sendResult(int code, String title, Object message) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("message", message);
        json.put("title", title);
        super.writeJson(json);
    }

    public String creatAllCacheForB2B() {
        try {
            productPriceCacheService.updateAllProductPriceCache();
        } catch (Exception e) {

            logger.error("creatAllCacheForB2B异常：", e);
        } // 更新b2b缓存
        return SUCCESS;
    }

    /**
     * 同步促销活动商品缓存
     */
    public void synPromotionCom() {
        JSONObject json = new JSONObject();
        boolean issuc = true;
        String title = "操作成功!";
        try {

            promotionInfoService.synPromotionCom(promotion.getPromotionId());

        } catch (Exception e) {
            issuc = false;
            title = "操作失败!";
            logger.error("synPromotionCom同步促销活动关联的商品缓存失败:", e);
        } finally {
            json.put(SUCCESS, issuc);
            json.put("title", title);
            this.strWriteJson(json.toJSONString());
        }
    }

    public Date getDate(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (dateStr != null && !dateStr.isEmpty()) {
            try {
                return format.parse(dateStr);
            } catch (ParseException e) {
                logger.error("时间转换异常", e);
            }
        }
        return null;
    }

    /**
     * 判断活动是否被锁
     * 
     * @throws Exception
     */
    public void promotionIsLock() throws Exception {
        try {
            if (promotion == null) {
                message.setCode(10);
            } else {
                Integer count = promotionInfoService.promotionIsLock(promotion);
                if (count == null) {
                    message.setCode(10);
                } else {
                    message.setCode(count);
                }
            }

        } catch (Exception e) {
            message.setCode(10);
            message.setTitle(e.getMessage());
            // logger.error("删除活动异常 promotionId：" + promotion.getPromotionId());
            logger.error("判断活动是否被锁 promotionId：{}", promotion.getPromotionId(), e);
        }
        JSONObject json = (JSONObject) JSONObject.toJSON(message);
        this.writeJson(json);
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public PromotionInfo getPromotion() {
        return promotion;
    }

    public void setPromotion(PromotionInfo promotion) {
        this.promotion = promotion;
    }

    public String getRuleData() {
        return ruleData;
    }

    public void setRuleData(String ruleData) {
        this.ruleData = ruleData;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

}
