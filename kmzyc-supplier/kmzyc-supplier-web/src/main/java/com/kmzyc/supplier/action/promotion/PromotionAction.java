package com.kmzyc.supplier.action.promotion;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.km.framework.page.Pagination;
import com.kmzyc.commons.page.Page;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.promotion.app.enums.PromotionTypeEnums;
import com.kmzyc.promotion.app.vobject.Message;
import com.kmzyc.promotion.app.vobject.PromotionInfo;
import com.kmzyc.promotion.app.vobject.PromotionProduct;
import com.kmzyc.promotion.app.vobject.PromotionRuleData;
import com.kmzyc.promotion.remote.service.PromotionRemoteService;
import com.kmzyc.supplier.action.SupplierBaseAction;
import com.kmzyc.supplier.maps.MeetDataTypeMap;
import com.kmzyc.supplier.maps.SellUpTypeMap;
import com.kmzyc.supplier.service.CategoryService;
import com.pltfm.app.enums.ProductChannel;
import com.pltfm.app.vobject.Category;
import com.pltfm.app.vobject.ProdBrand;

public class PromotionAction extends SupplierBaseAction {

    private Logger logger = LoggerFactory.getLogger(PromotionAction.class);

    private PromotionInfo promotion;

    protected Page pagee;

    @Resource
    private PromotionRemoteService promotionRemoteService;

    @Resource
    private CategoryService categoryService;

    private Message message = new Message();

    private String promotionId;

    private Map<Long, String> exclusionPromotionMap;

    private List<PromotionInfo> exclusionPromotionMapList;

    private String type;

    private String prizeData;

    private String promotionType;

    private PromotionProduct promotionProduct;

    private String selectedIds;

    private String productId;

    private ProdBrand prodBrand;

    // 活动id查询条件
    private String queryPromotionId;

    // 类目信息
    private Category category;

    private String categoryString;

    // 活动审核状态： 审核or未审核
    private String status;

    // 活动时间状态：未上线or正在进行or已经结束
    private String timeStatus;

    // 促销信息
    private String ruleData;

    // 卖光后操作类型
    private String sellUpType;

    // 满足条件类型 件or元
    private String meetDataType;

    // 删除的活动id
    private String promotionIds;

    /**
     * 查询促销活动列表
     *
     * @return
     */
    public String queryPromotionList() {
        String result = "";
        pagee = this.getPagee();
        Pagination page = getPagination(Constants.VIEW_PAGE, Integer.parseInt(getPageSize()));
        setBaseData();
        if (promotion == null) {
            promotion = new PromotionInfo();
        }
        if (StringUtils.isNotBlank(queryPromotionId)) {
            promotion.setPromotionId(Long.valueOf(queryPromotionId.trim()));
        }
        if (StringUtils.isNotBlank(status)) {
            promotion.setStatus(Integer.valueOf(status));
        }
        if (StringUtils.isNotBlank(timeStatus)) {
            promotion.setOnlineStatus(Integer.valueOf(timeStatus));
        }
        if (promotion.getPromotionId() != null) {
            promotion.setEndTime(null);
            promotion.setStartTime(null);
        }
        result = "success";
        if (promotion.getNature() == null) {
            promotion.setNature(1);
        } else if (promotion.getNature() == 2) {
            result = "increaseList";
        }
        promotion.setStartIndex(page.getStartindex());
        promotion.setEndIndex(page.getEndindex());
        promotion.setSupplierId(getSupplyId());
        pagee = promotionRemoteService.getPromotionInfoListByPage(pagee, promotion);
        pagintion = page;
        pagintion.setTotalpage(pagee.getPageCount());
        pagintion.setTotalRecords(pagee.getRecordCount());

        return result;
    }

    /**
     * 查询促销活动列表审核
     *
     * @return
     */
    public String queryPromotionListAudit() {
        String result = "";
        pagee = getPagee();
        Pagination page = getPagination(Constants.VIEW_PAGE, Integer.parseInt(getPageSize()));

        setBaseData();
        if (promotion == null) {
            promotion = new PromotionInfo();
        }
        if (StringUtils.isNotBlank(queryPromotionId)) {
            promotion.setPromotionId(Long.valueOf(queryPromotionId.trim()));
        }
        if (StringUtils.isNotBlank(status)) {
            promotion.setStatus(Integer.valueOf(status));
        }
        if (StringUtils.isNotBlank(timeStatus)) {
            promotion.setPromotionPriority(Integer.valueOf(timeStatus));
        }
        result = "success";
        if (promotion.getNature() == null) {
            promotion.setNature(1);
        } else if (promotion.getNature() == 2) {
            result = "increaseList";
        }

        promotion.setStartIndex(page.getStartindex());
        promotion.setEndIndex(page.getEndindex());
        promotion.setSupplierId(getSupplyId());
        pagee = promotionRemoteService.getPromotionInfoListByPage(pagee, promotion);
        pagintion = page;
        pagintion.setTotalpage(pagee.getPageCount());
        pagintion.setTotalRecords(pagee.getRecordCount());
        Date nowDate = new Date();
        this.getRequest().setAttribute("nowDate", nowDate);
        return result;
    }

    /**
     * 审核或撤销审核活动
     *
     * @return String
     * @throws
     */
    public void updateIssuePromotion() throws Exception {
        boolean haveDone = false;
        Map<String, Object> jsonMap = new HashMap();
        String title = "";
        try {
            int result = promotionRemoteService.updateIssuePromotion(Long.valueOf(promotionId));
            haveDone = true;
            switch (result) {
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
                default:
                    title = "操作失败，无效类型";
                    break;
            }
        } catch (Exception e) {
            haveDone = false;
            logger.error("审核或撤销审核活动出现异常，", e);
            message.setCode(10);
            message.setTitle(e.getMessage());
        }
        jsonMap.put("flag", haveDone);
        jsonMap.put("msg", title);
        writeJson(jsonMap);
    }

    /**
     * 删除活动
     *
     * @return String
     * @throws
     */
    public String deletePromotion() throws Exception {
        try {
            promotionRemoteService.deletePromotions(promotionIds);
        } catch (Exception e) {
            logger.error("删除活动出现异常，", e);
            message.setCode(10);
            message.setTitle(e.getMessage());
        }
        return queryPromotionList();

    }

    /**
     * 撤销活动
     */
    public void updatePromotionEndTime() {
        PromotionInfo p = new PromotionInfo();
        p.setPromotionId(Long.valueOf(promotionId));
        boolean haveDone = false;
        Map jsonMap = new HashMap();
        String title = "";
        try {
            int result = promotionRemoteService.updatePromotionEndTime(p);
            haveDone = true;
            switch (result) {
                case 0:
                    title = "操作成功 ！";
                    break;
                case 1:
                    title = "操作失败。 ";
                    break;
                default:
                    title = "无效类型。";
                    break;
            }
        } catch (Exception e) {
            haveDone = false;
            title = "操作失败。 ";
            logger.error("撤销活动出现异常，", e);
            message.setCode(10);
            message.setTitle(e.getMessage());
        }

        jsonMap.put("flag", haveDone);
        jsonMap.put("msg", title);
        writeJson(jsonMap);
    }

    /**
     * 修改更新活动优先级序号
     */
    public void updatePromotionPriority() {
        JSONObject json = new JSONObject();
        if (promotionRemoteService.updatePromotionPriority(promotion) == 0) {
            promotionRemoteService.updatePromotion(promotion);
            json.put("code", 0);
            json.put("title", "修改成功");
        } else {
            json.put("code", 1);
            json.put("title", "序号不能重复");
        }
        strWriteJson(json.toJSONString());
    }

    private void setBaseData() {
        getRequest().setAttribute("promotionTypeMap", getPromotionTypeMap());
        Map<String, String> channelMap = new LinkedHashMap();
        channelMap.put(ProductChannel.B2B.getStatus(), ProductChannel.B2B.getTitle());
        getRequest().setAttribute("channelMap", channelMap);
        getRequest().setAttribute("meetDataMap", MeetDataTypeMap.getMap());
        getRequest().setAttribute("sellUpTypeMap", SellUpTypeMap.getMap());
    }

    /**
     * 获取活动详细信息
     *
     * @return
     */
    public String toUpdataPromotionNew() {
        try {
            promotion = promotionRemoteService.getPromotionById(promotion.getPromotionId());
            ruleData = getRuleData(promotion);
            exclusionPromotionMap = promotionRemoteService.selectExclusionPromotion(promotion.getPromotionId());
            List<PromotionInfo> exclusionPromotionMapList = new ArrayList();
            PromotionInfo p = null;
            pagee = this.getPagee();
            if (exclusionPromotionMap != null) {
                for (Map.Entry<Long, String> entry : exclusionPromotionMap.entrySet()) {
                    String id = entry.getKey().toString();
                    p = new PromotionInfo();
                    p.setSellerId(Long.valueOf(id.toString()));
                    p.setPromotionName(entry.getValue());
                    exclusionPromotionMapList.add(p);
                }
            }
            promotion.setPromotionNote(ruleData);
            pagee.setDataList(exclusionPromotionMapList);
            setBaseData();
        } catch (Exception e) {
            logger.error("获取活动详细信息出现异常，", e);
            return ERROR;
        }

        return SUCCESS;
    }

    /**
     * 添加活动
     */
    public void addPromotionNew() {
        Map jsonMap = new HashMap();
        boolean flag = false;
        String title = "";
        Long id = 0l;
        try {
            // 添加活动
            String ruleData = prizeData;
            promotion.setPromotionType(Integer.valueOf(promotionType));
            promotion.setCreaterUser(Integer.valueOf(getLoginUserId().toString()));
            if (promotion.getPromotionTitle() != null) {
                promotion.setPromotionTitle(promotion.getPromotionTitle().trim());
            }
            // 指定入住商家
            promotion.setShopSort(2);
            promotion.setSupplierId(getSupplyId());
            if (sellUpType != null && !sellUpType.isEmpty()) {
                promotion.setSellUpType(Integer.valueOf(sellUpType));
            }
            id = promotionRemoteService.addPromotionNew(promotion, ruleData, meetDataType);
            flag = true;
            title = "操作成功";
        } catch (Exception e) {
            logger.error("添加活动出现异常，", e);
            title = "操作失败";
        }
        jsonMap.put("flag", flag);
        jsonMap.put("msg", title);
        jsonMap.put("id", id);
        writeJson(jsonMap);
    }

    /**
     * 初始化活动类型
     *
     * @return
     */
    private Map<Integer, String> getPromotionTypeMap() {
        Map<Integer, String> map = new HashMap();
        PromotionTypeEnums[] ts = PromotionTypeEnums.values();
        for (PromotionTypeEnums pt : ts) {
            if (pt.getValue() != 4 && pt.getValue() != 11 && pt.getValue() != 12) {
                map.put(pt.getValue(), pt.getTitle());
            }
        }
        return map;
    }

    /**
     * 修改促销活动
     *
     * @return
     */
    public void updatePromotion() {
        JSONObject json = new JSONObject();
        if (promotionRemoteService.updatePromotionPriority(promotion) == 0) {
            if (promotion.getMutexPromotionId() != null
                    && promotion.getMutexPromotionId().indexOf("all") > 0) {
                promotion.setMutexPromotionId(promotion.getMutexPromotionId().trim());
            }
            String idsString = "";
            if (promotion.getMutexPromotionId() != null) {
                String[] ids = promotion.getMutexPromotionId().split(",");
                idsString = StringUtils.join(Arrays.stream(ids)
                        .filter(id -> StringUtils.isNotBlank(id))
                        .collect(Collectors.toList()), ",");
            }
            if (idsString.length() > 0) {
                promotion.setMutexPromotionId(idsString);
            }
            if (promotion.getPromotionTitle() != null) {
                promotion.setPromotionTitle(promotion.getPromotionTitle().trim());
            }
            if (promotion.getPromotionName() != null) {
                promotion.setPromotionName(promotion.getPromotionName().trim());
            }
            promotionRemoteService.updatePromotion(promotion);
            json.put("code", 0);
            json.put("title", "修改成功");
            json.put("productType", promotion.getProductFilterType());
            PromotionInfo p = new PromotionInfo();
            p.setPromotionId(promotion.getPromotionId());
            promotion = p;
        } else {
            json.put("code", 1);
            json.put("title", "序号不能重复");

        }
        strWriteJson(json.toJSONString());

    }

    public void checkPromoitonTimeInCoupon() {
        JSONObject json = new JSONObject();
        boolean isSuccess = false;
        try {
            isSuccess = promotionRemoteService.checkPromoitonTimeInCoupon(promotion);
        } catch (Exception e) {
            logger.error("checkPromoitonTimeInCoupon出现异常，", e);
        }
        json.put("isSuccess", isSuccess);
        strWriteJson(json.toJSONString());
    }

    /**
     * 查询活动产品列表
     *
     * @return String 返回值
     */
    public String queryPromotionProductList() {
        Pagination page = getPagination(Constants.VIEW_PAGE, Integer.parseInt(getPageSize()));
        pagee = getPagee();
        pagee.setPageNo(page.getPage());

        try {
            if (promotionProduct == null) {
                return ERROR;
            }
            promotion = promotionRemoteService.getPromotionById(promotionProduct.getPromotionId());
            if (promotion == null) {
                return ERROR;
            }

            promotion.setPromotionName("");
            if (promotionProduct.getProductSkuCode() != null) {
                promotion.setPromotionName(promotionProduct.getProductSkuCode());
            }
            promotion.setPromotionId(promotionProduct.getPromotionId());
            pagee = this.getPagee();
            pagintion = page;
            pagee.setPageSize(page.getNumperpage());
            pagee = promotionRemoteService.queryPromotionProductList(pagee, promotion);
            pagintion.setTotalpage(pagee.getPageCount());
            pagintion.setTotalRecords(pagee.getRecordCount());
            pagintion.setNumperpage(pagee.getPageSize());
            pagintion.setTotalpage(pagee.getPageCount());
        } catch (Exception e) {
            logger.error("查询活动产品列表出现异常，", e);
            return ERROR;
        }
        return SUCCESS;
    }

    public String queryPromotionProductListDetail() {
        Pagination page = getPagination(Constants.VIEW_PAGE, Integer.parseInt(getPageSize()));
        pagee = getPagee();
        pagee.setPageNo(page.getPage());

        try {
            if (promotionProduct == null) {
                return ERROR;
            }
            promotion = promotionRemoteService.getPromotionById(promotionProduct.getPromotionId());
            if (promotion == null) {
                return ERROR;
            }

            promotion.setPromotionName("");
            if (promotionProduct.getProductSkuCode() != null) {
                promotion.setPromotionName(promotionProduct.getProductSkuCode());
            }
            promotion.setPromotionId(promotionProduct.getPromotionId());
            pagee = this.getPagee();
            pagintion = page;
            pagee.setPageSize(page.getNumperpage());
            pagee = promotionRemoteService.queryPromotionProductList(pagee, promotion);
            pagintion.setTotalpage(pagee.getPageCount());
            pagintion.setTotalRecords(pagee.getRecordCount());
            pagintion.setNumperpage(pagee.getPageSize());
            pagintion.setTotalpage(pagee.getPageCount());
        } catch (Exception e) {
            logger.error("queryPromotionProductListDetail出现异常，", e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 更新活动商品价格
     *
     * @return
     */
    public void updatePromotionProductPrice() {
        try {
            JSONObject json = new JSONObject();
            promotionRemoteService.updatePromotionProductForXianGou(promotionProduct);
            json.put("code", 0);
            strWriteJson(json.toJSONString());
        } catch (Exception e) {
            logger.error("更新活动商品价格出现异常，", e);
        }
    }

    /**
     * 增加活动产品
     *
     * @return String
     * @throws
     */
    public String addPromotionProduct() throws Exception {
        JSONObject json = new JSONObject();
        try {
            String skuIds = promotionProduct.getProductSkuIds();
            PromotionInfo info = promotionRemoteService.getPromotionById(promotionProduct.getPromotionId());
            if (info == null) {
                return ERROR;
            }

            promotionRemoteService.addPromotionProduct(info.getPromotionId(), skuIds);
            json.put("code", 0);
            json.put("title", "修改成功");
        } catch (Exception e) {
            logger.error("增加活动产品出现异常，", e);
            return ERROR;
        }

        strWriteJson(json.toJSONString());
        return SUCCESS;
    }

    /**
     * 修改限购产品
     * @return
     */
    public String updatePromotionProductForXianGou() {
        JSONObject json = new JSONObject();
        try {
            Integer result = promotionRemoteService.updatePromotionProductForXianGou(promotionProduct);
            if (result == 1) {
                json.put("msg", "最小购买数必须小于最大购买数!");
            } else if (result == 2) {
                json.put("msg", "最小购买数必须小于最大购买数!");
            } else if (result == 3) {
                json.put("msg", "操作失败!");
            } else {
                json.put("msg", "修改成功!");
            }
            json.put("code", result);
        } catch (Exception e) {
            logger.error("修改限购产品出现异常，", e);
        }

        strWriteJson(json.toJSONString());
        return null;
    }

    /**
     * 查询存在时间冲突的活动列表
     */
    public String selectConflictPromotion() {
        pagee = this.getPagee();
        try {
            promotion.setSupplierId(getSupplyId());
            if (promotion.getPromotionIds() != null) {
                type = "check";
            }
            List<PromotionInfo> list = promotionRemoteService.queryMutexPromotionList(promotion);
            setBaseData();
            pagee.setDataList(list);
        } catch (Exception e) {
            logger.error("查询存在时间冲突的活动列表出现异常，", e);
        }

        return "success";
    }

    public String selectProductBrand() {
        Pagination page = getPagination(Constants.VIEW_PAGE, Integer.parseInt(getPageSize()));
        pagee = getPagee();
        pagee.setPageNo(page.getPage());
        try {
            if (prodBrand == null) {
                prodBrand = new ProdBrand();
            }
            pagee = prodBrandService.searchPage(prodBrand, pagee);
            pagintion = page;
            pagintion.setTotalpage(pagee.getPageCount());
            pagintion.setTotalRecords(pagee.getRecordCount());
            pagintion.setNumperpage(pagee.getPageSize());
            pagintion.setTotalpage(pagee.getPageCount());
            setBaseData();
        } catch (Exception e) {
            logger.error("查询产品品牌出现异常，", e);
        }

        return "success";
    }

    /**
     * 产品类目列表
     *
     * @return String 返回值
     */
    public String queryCategoryList() {
        String resultInfo = "success";
        try {
            if (category == null) {
                category = new Category();
            }
            category.setIsPhy(1);
            List<Category> list = categoryService.queryCategoryList(category);
            categoryString = JSON.toJSONString(list);
        } catch (Exception e) {
            logger.error("查询产品类目列表出现异常，", e);
            return ERROR;
        }

        return resultInfo;
    }

    /**
     * 拼装优惠信息 ,更新活动页面使用
     *
     * @param promotion
     * @return
     */
    public String getRuleData(PromotionInfo promotion) {
        StringBuffer sf = new StringBuffer();//
        if (promotion.getRuleDatas() == null || promotion.getRuleDatas().size() < 1) {
            String sellUpType = promotion.getSellUpType() == 1 ? "，卖光后停止销售直到活动结束。" : "，卖光后恢复原价销售。";
            if (promotion.getPromotionType() == PromotionTypeEnums.SALE.getValue().intValue()) {
                return promotion.getPromotionData() == null ? "无统一特价" + sellUpType : "统一特价:"
                        + promotion.getPromotionData() + sellUpType;
            } else if (promotion.getPromotionType() == PromotionTypeEnums.DISCOUNT.getValue()
                    .intValue()) {
                return "活动价为现在价格的"
                        + Long.valueOf(promotion.getPromotionData()
                                .multiply(BigDecimal.valueOf(10l)).intValue()) + "%" + sellUpType;
            }
        }
        if (promotion.getRuleDatas() == null || promotion.getRuleDatas().size() < 1)
            return "";
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
                            .append("<a title='点击预览' href='javascript:;' class='toProductSku' data-Id='"
                                    + promotionRuleData.getEntityId() + "'>")
                            .append(promotionRuleData.getEntityId()).append("</a>的商品,");
                } else {
                    if (meetData.equals("")) {
                        sf.append("满" + promotionRuleData.getMeetData() + meetDataType + " ,");
                    }
                    sf.append("加" + promotionRuleData.getPrizeData() + "元可得skuId为");
                    sf.append(
                            "<a title='点击预览' href='javascript:;' class='toProductSku' data-Id='"
                                    + promotionRuleData.getEntityId() + "'>")
                            .append(promotionRuleData.getEntityId()).append("</a>的商品,");
                }
                meetData = promotionRuleData.getMeetData().toString();
                num = promotionRuleData.getNum1().toString();
            }
            sf.append("共").append(num).append("件。");
        } else if (promotion.getPromotionType() == PromotionTypeEnums.GIFT.getValue().intValue()) {
            for (PromotionRuleData promotionRuleData : list) {
                if (!meetData.equals(promotionRuleData.getMeetData().toString())
                        && !meetData.equals("")) {
                    sf.append(
                            ";<br/>满" + Integer.valueOf(promotionRuleData.getMeetData().toString())
                                    + meetDataType + ",赠")
                            .append("skuId为<a title='点击预览' href='javascript:;' class='toProductSku' data-Id='")
                            .append(promotionRuleData.getEntityId()).append("'>")
                            .append(promotionRuleData.getEntityId()).append("</a>的商品")
                            .append(promotionRuleData.getNum1()).append("件");
                } else {
                    if (meetData.equals("")) {
                        sf.append("满" + promotionRuleData.getMeetData() + meetDataType);
                    }
                    sf.append(
                            ",赠skuId为<a title='点击预览' href='javascript:;' class='toProductSku'  data-Id='")
                            .append(promotionRuleData.getEntityId()).append("'>")
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
        return sf.toString();
    }

    public void synPromotionCom() {
        JSONObject json = new JSONObject();
        boolean isSuccess = true;
        String title = "操作成功!";
        try {
            setBaseData();
            promotionRemoteService.synPromotionCom(Long.parseLong(promotionId));
        } catch (Exception e) {
            isSuccess = false;
            title = "操作失败!";
            // logger.error("synPromotionCom同步促销活动关联的商品缓存失败" + e.getMessage());
            logger.error("同步促销活动关联的商品缓存失败出现异常，", e);
        } finally {
            json.put("flag", isSuccess);
            json.put("msg", title);
            this.strWriteJson(json.toJSONString());
        }
    }

    /**
     * 已审核上线活动修改参加活动商品
     *
     * @throws SQLException
     */
    public void updateIssuePromotionProduct() {
        JSONObject json = new JSONObject();
        int code = 0;
        String title = "";
        try {
            setBaseData();
            code = promotionRemoteService.updateIssuePromotionProduct(promotionProduct.getPromotionProductId());
            // 0：操作成功 1：操作失败
            switch (code) {
                case 0:
                    title = "操作成功 ！";
                    break;
                case 1:
                    title = "操作失败。 ";
                    break;
                default:
                    title = "无效类型。";
                    break;
            }
        } catch (Exception e) {
            logger.error("已审核上线活动修改参加活动商品出现异常，", e);
            code = 1;
            title = e.getMessage();
        }

        json.put("success", code == 0);
        json.put("title", title);
        strWriteJson(json.toJSONString());
    }

    /**
     * 判断活动是否被锁
     *
     * @throws Exception
     */
    public void promotionIsLock() throws Exception {
        try {
            Integer count = categoryService.promotionIsLock(promotion);
            message.setCode(count);
        } catch (Exception e) {
            message.setCode(10);
            message.setTitle(e.getMessage());
            logger.error("判断活动是否被锁出现异常，", e);
        }

        JSONObject json = (JSONObject) JSONObject.toJSON(message);
        writeJson(json);
    }

    public PromotionInfo getPromotion() {
        return promotion;
    }

    public void setPromotion(PromotionInfo promotion) {
        this.promotion = promotion;
    }

    public Page getPagee() {
        if (pagee == null) {
            pagee = new Page();
        }
        return pagee;
    }

    /**
     * 跳转到添加页面
     *
     * @return
     */

    public String toAddPromotionNew() {
        setBaseData();
        return SUCCESS;
    }

    public void setPagee(Page pagee) {
        this.pagee = pagee;
    }

    public String getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }

    public Map<Long, String> getExclusionPromotionMap() {
        return exclusionPromotionMap;
    }

    public void setExclusionPromotionMap(Map<Long, String> exclusionPromotionMap) {
        this.exclusionPromotionMap = exclusionPromotionMap;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrizeData() {
        return prizeData;
    }

    public void setPrizeData(String prizeData) {
        this.prizeData = prizeData;
    }

    public String getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(String promotionType) {
        this.promotionType = promotionType;
    }

    public PromotionProduct getPromotionProduct() {
        return promotionProduct;
    }

    public void setPromotionProduct(PromotionProduct promotionProduct) {
        this.promotionProduct = promotionProduct;
    }

    public String getSelectedIds() {
        return selectedIds;
    }

    public void setSelectedIds(String selectedIds) {
        this.selectedIds = selectedIds;
    }

    public List<PromotionInfo> getExclusionPromotionMapList() {
        return exclusionPromotionMapList;
    }

    public void setExclusionPromotionMapList(List<PromotionInfo> exclusionPromotionMapList) {
        this.exclusionPromotionMapList = exclusionPromotionMapList;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public ProdBrand getProdBrand() {
        return prodBrand;
    }

    public void setProdBrand(ProdBrand prodBrand) {
        this.prodBrand = prodBrand;
    }

    public String getCategoryString() {
        return categoryString;
    }

    public void setCategoryString(String categoryString) {
        this.categoryString = categoryString;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimeStatus() {
        return timeStatus;
    }

    public void setTimeStatus(String timeStatus) {
        this.timeStatus = timeStatus;
    }

    public String getRuleData() {
        return ruleData;
    }

    public void setRuleData(String ruleData) {
        this.ruleData = ruleData;
    }

    public String getSellUpType() {
        return sellUpType;
    }

    public void setSellUpType(String sellUpType) {
        this.sellUpType = sellUpType;
    }

    public String getMeetDataType() {
        return meetDataType;
    }

    public void setMeetDataType(String meetDataType) {
        this.meetDataType = meetDataType;
    }

    public String getPromotionIds() {
        return promotionIds;
    }

    public void setPromotionIds(String promotionIds) {
        this.promotionIds = promotionIds;
    }

    public String getQueryPromotionId() {
        return queryPromotionId;
    }

    public void setQueryPromotionId(String queryPromotionId) {
        this.queryPromotionId = queryPromotionId;
    }

}
