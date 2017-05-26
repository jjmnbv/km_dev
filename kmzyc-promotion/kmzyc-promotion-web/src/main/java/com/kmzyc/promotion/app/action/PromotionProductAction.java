package com.kmzyc.promotion.app.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.kmzyc.promotion.app.bean.ResultMessage;
import com.kmzyc.promotion.app.enums.CategoryStatus;
import com.kmzyc.promotion.app.enums.ProductStatus;
import com.kmzyc.promotion.app.enums.PromotionStatus;
import com.kmzyc.promotion.app.enums.PromotionTypeEnums;
import com.kmzyc.promotion.app.maps.MeetDataTypeMap;
import com.kmzyc.promotion.app.maps.SellUpTypeMap;
import com.kmzyc.promotion.app.service.ProductSkuService;
import com.kmzyc.promotion.app.service.PromotionInfoService;
import com.kmzyc.promotion.app.service.PromotionProductDataService;
import com.kmzyc.promotion.app.service.PromotionProductService;
import com.kmzyc.promotion.app.service.PromotionTypeService;
import com.kmzyc.promotion.app.vobject.Category;
import com.kmzyc.promotion.app.vobject.Message;
import com.kmzyc.promotion.app.vobject.ProductSku;
import com.kmzyc.promotion.app.vobject.PromotionInfo;
import com.kmzyc.promotion.app.vobject.PromotionProduct;
import com.kmzyc.promotion.app.vobject.PromotionRule;
import com.kmzyc.promotion.app.vobject.PromotionRuleData;
import com.kmzyc.promotion.optimization.vo.PromotionProductData;

@SuppressWarnings("unchecked")
public class PromotionProductAction extends BaseAction {

    private static final long serialVersionUID = 1L;

    @Resource
    private PromotionProductService promotionProductService;
    @Resource
    private PromotionProductDataService promotionProductDataService;
    @Resource
    private PromotionInfoService promotionInfoService;

    @Resource
    private ProductSkuService ProductSkuService;
    @Resource
    private PromotionTypeService promotionTypeService;

    private PromotionProduct promotionProduct;

    private ProductSku productSku;

    private PromotionInfo promotion;

    private PromotionRule promotionRule;
    // 日志记录
    protected Logger logger = LoggerFactory.getLogger(PromotionProductAction.class);

    /**
     * 返回结果
     */
    private Message message = new Message();

    /**
     * 查询活动例外商品列表
     * 
     * @return String 返回值
     */
    public String queryExceptionProductList() {
        try {
            if (promotionProduct == null) {
                promotionProduct = new PromotionProduct();
            }

            if (promotion == null) {
                return ERROR;
            }
            PromotionInfo promotionInfo =
                    promotionInfoService.getPromotionById(promotion.getPromotionId());
            promotion = promotionInfoService.promotionFilterProductName(promotion);
            promotion.setRuleDatas(promotionInfo.getRuleDatas());
            page = super.getPage();
            promotion.setPromotionType(promotionInfo.getPromotionType());
            promotion.setShopNames(promotionInfo.getShopNames());
            String ruleData = getRuleData(promotionInfo);
            promotion.setPromotionNote(ruleData);
            if (promotionInfo.getPromotionData() != null) {
                promotion.setPromotionData(promotionInfo.getPromotionData());
            }
            promotion.setStatus(promotionInfo.getStatus());
            if (promotion.getStatus().equals(PromotionStatus.ONLINE.getValue())) {
                promotion.setStartTime(promotionInfo.getStartTime());
                promotion.setEndTime(promotionInfo.getEndTime());
                promotion.setPromotionTitle(promotionInfo.getPromotionTitle());
                promotion.setProductFilterType(promotionInfo.getProductFilterType());
            }
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
        } catch (Exception e) {
            logger.error("查询活动例外商品列表queryExceptionProductList异常 :" + e);
            return ERROR;
        }
        return SUCCESS;
    }

    private void setBaseData() {
        this.getRequest().setAttribute("promotionTypeMap",
                promotionTypeService.getPromotionTypeMap());
        this.getRequest().setAttribute("meetDataMap", MeetDataTypeMap.getMap());
        this.getRequest().setAttribute("sellUpTypeMap", SellUpTypeMap.getMap());
    }

    /**
     * 查询活动产品列表
     * 
     * @return String 返回值
     */
    public String queryPromotionProductList() {
        try {
            if (promotionProduct == null) {
                return ERROR;
            }
            promotion = promotionInfoService.getPromotionById(promotionProduct.getPromotionId());
            if (promotion == null) {
                return ERROR;
            }
            page = super.getPage();
            page = promotionProductService.queryPromotionProductList(page, promotionProduct);
            List<PromotionProduct> rlist = page.getDataList();
            for (PromotionProduct pp : rlist) {
                List<PromotionProductData> list = promotionProductDataService
                        .queryListBypromotionProductId(pp.getPromotionProductId());
                pp.setPromotionProductDataList(list);
            }
        } catch (Exception e) {
            logger.error("查询活动产品列表异常 :" + e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 查询活动例外产品列表
     * 
     * @return String 返回值
     */
    public String queryPromotionExceptionProductList() {
        try {
            if (promotionProduct == null) {
                return ERROR;
            }
            promotion = promotionInfoService.getPromotionById(promotionProduct.getPromotionId());
            if (promotion == null) {
                return ERROR;
            }
            page = super.getPage();
            promotionProduct.setCategory("-1");
            page = promotionProductService.queryPromotionProductList(page, promotionProduct);
            List<PromotionProduct> rlist = page.getDataList();
            for (PromotionProduct pp : rlist) {
                List<PromotionProductData> list = promotionProductDataService
                        .queryListBypromotionProductId(pp.getPromotionProductId());
                pp.setPromotionProductDataList(list);
            }
        } catch (Exception e) {
            logger.error("查询活动例外产品列表异常 :" + e);
            return ERROR;
        }
        return SUCCESS;
    }

    public PromotionInfo getPromotion() {
        return promotion;
    }

    public void setPromotion(PromotionInfo promotion) {
        this.promotion = promotion;
    }

    /**
     * 查询sku列表 (例外商品)
     * 
     * @return String 返回值
     */
    public String findAllProductSkuForException() {
        try {
            if (null != promotionProduct && promotionProduct.getPromotionId() != null) {
                promotion =
                        promotionInfoService.getPromotionById(promotionProduct.getPromotionId());
            }

            productSku.setUpStatus(ProductStatus.UP.getStatus());
            page = super.getPage();
            ProductSkuService.queryProductSkuList(page, productSku);
            // 大类
            getBcategoryList(new Long(0));

            // 中类
            if (productSku.getbCategoryId() != null
                    && productSku.getbCategoryId().intValue() != 0) {
                getMcategoryList(productSku.getbCategoryId());
            }

            // 小类
            if (productSku.getmCategoryId() != null
                    && productSku.getmCategoryId().intValue() != 0) {
                getScategoryList(productSku.getmCategoryId());
            }
            setProductBrandMap();

        } catch (Exception e) {
            logger.error("查询sku列表 findAllProductSkuForGrant异常 :" + e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 查询sku列表 findAllProductSkuForGrant
     * 
     * @return String 返回值
     */
    public String findAllProductSku() {
        try {
            if (null != promotionProduct && promotionProduct.getPromotionId() != null) {
                promotion =
                        promotionInfoService.getPromotionById(promotionProduct.getPromotionId());
            }

            productSku.setUpStatus(ProductStatus.UP.getStatus());
            page = super.getPage();
            ProductSkuService.queryProductSkuList(page, productSku);
            // 大类
            getBcategoryList(new Long(0));
            // 中类
            if (productSku.getbCategoryId() != null
                    && productSku.getbCategoryId().intValue() != 0) {
                getMcategoryList(productSku.getbCategoryId());
            }

            // 小类
            if (productSku.getmCategoryId() != null
                    && productSku.getmCategoryId().intValue() != 0) {
                getScategoryList(productSku.getmCategoryId());
            }

            // getCategoryList();
            setProductBrandMap();

        } catch (Exception e) {
            logger.error("查询sku列表 findAllProductSkuForGrant异常 :" + e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 查询sku列表
     * 
     * @return String 返回值
     */
    public String findAllProductSkuForGrant() {
        try {
            if (null != promotionProduct && promotionProduct.getPromotionId() != null) {
                promotion =
                        promotionInfoService.getPromotionById(promotionProduct.getPromotionId());
            }

            productSku.setUpStatus(ProductStatus.UP.getStatus());
            page = super.getPage();
            ProductSkuService.queryProductSkuList(page, productSku);
            // 大类
            getBcategoryList(new Long(0));
            // 中类
            if (productSku.getbCategoryId() != null
                    && productSku.getbCategoryId().intValue() != 0) {
                getMcategoryList(productSku.getbCategoryId());
            }

            // 小类
            if (productSku.getmCategoryId() != null
                    && productSku.getmCategoryId().intValue() != 0) {
                getScategoryList(productSku.getmCategoryId());
            }
            setProductBrandMap();

        } catch (Exception e) {
            logger.error("查询sku列表异常 :" + e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 增加活动产品
     * 
     * @return String @exception
     */
    public String addPromotionProduct() throws Exception {
        try {
            PromotionInfo info =
                    promotionInfoService.getPromotionById(promotionProduct.getPromotionId());
            if (info == null) {
                return ERROR;
            }
            // if (info.getStatus().equals(PromotionStatus.ISSUE.getValue()))
            // {// 已审核无法修改
            // return SUCCESS;
            // }
            BigDecimal defaultPrice = info.getPromotionData();
            Double price = null;
            if (defaultPrice != null
                    && (info.getPromotionType().equals(PromotionTypeEnums.SALE.getValue()) || info
                            .getPromotionType().equals(PromotionTypeEnums.SALE_APP.getValue()))) {
                price = defaultPrice.doubleValue();
            }
            promotionProductService.addPromotionProduct(promotionProduct, price, info);
        } catch (Exception e) {
            logger.error("增加活动产品异常 :" + e);
            return ERROR;
        }
        message.setModule("保存促销活动产品");
        message.setCode(0);
        return SUCCESS;
    }

    /**
     * 删除活动产品
     * 
     * @return String @exception
     */
    public String deletePromotionProduct() throws Exception {
        try {
            PromotionInfo info =
                    promotionInfoService.getPromotionById(promotionProduct.getPromotionId());
            if (info == null) {
                return ERROR;
            }
            if (info.getStatus().equals(PromotionStatus.ISSUE.getValue())) {// 已审核无法修改
                return SUCCESS;
            }
            promotionProductService.deletePromotionProduct(promotionProduct);
        } catch (Exception e) {
            logger.error("删除活动产品失败：", e);
            return ERROR;
        }
        message.setModule("删除促销活动产品");
        message.setCode(0);
        return SUCCESS;
    }

    /**
     * 
     * @return
     */
    public void updatePromotionProductPrice() {
        JSONObject json = new JSONObject();
        promotionProductService.updatePromotionProductPrice(promotionProduct);
        json.put("code", 0);
        this.strWriteJson(json.toJSONString());
    }

    /**
     * 
     * @return
     */
    public String updatePromotionProductForXianGou() {
        PrintWriter out = null;
        Map<String, Object> jsonMap = Maps.newHashMap();
        ResultMessage rmsg = new ResultMessage();

        // 获取流对象
        try {
            out = super.getPrintWriter();
        } catch (IOException e) {
            logger.error("updatePromotionProductForXianGou异常：", e);
            return null;
        }

        try {
            rmsg = promotionProductService.updatePromotionProductForXianGou(promotionProduct);
            jsonMap.put("result", rmsg.getIsSuccess());
            jsonMap.put("msg", rmsg.getMessage());
            out.print(JSON.toJSONString(jsonMap));
        } catch (Exception e) {
            logger.error("updatePromotionProductForXianGou异常：", e);
            jsonMap.put("result", false);
            jsonMap.put("msg", "修改失败!");
            out.print(JSON.toJSONString(jsonMap));
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }

        return null;
    }

    /**
     * add by songmiao 2015-8-5 已审核上线活动修改参加活动商品
     * 
     * @throws SQLException
     */
    public void updateIssuePromotionProduct() {
        JSONObject json = new JSONObject();
        Long promotionProductId = promotionProduct.getPromotionProductId();
        int code = 0;
        String title = "";
        try {
            code = promotionProductService.updataPromotionProductStatus(promotionProductId);
            /*
             * 0：操作成功 1：操作失败
             */
            switch (code) {
                case 0:
                    title = "操作成功 ！";
                    break;
                case 1:
                    title = "操作失败。 ";
                    break;
                case 2:
                    title = "操作失败，未指定附赠赠品。 ";
                    break;

            }
        } catch (Exception e) {
            logger.error("已审核上线活动修改参加活动商品updateIssuePromotionProduct异常 :" + e);
            code = 1;
            title = e.getMessage();
        }
        json.put("success", code == 0);
        json.put("title", title);
        this.strWriteJson(json.toJSONString());
    }


    public String selectCategory() {
        PrintWriter out = null;
        Map<String, Object> jsonMap = Maps.newHashMap();
        Category category = new Category();
        category.setCategoryId(new Long(super.getRequest().getParameter("id")));
        category.setStatus(CategoryStatus.VALID.getStatus());
        try {
            if (category.getCategoryId() == 0) {// 如果是0,则返回空
                jsonMap.put("categoryList", null);
            } else {
                List<Category> categoryList = categoryService.queryCategoryChildrenList(category);
                jsonMap.put("categoryList", categoryList);
            }
            out = super.getPrintWriter();
            out.print(JSON.toJSONString(jsonMap));
        } catch (IOException e) {
            logger.error("查询类目信息IO异常：", e);
        } catch (Exception e) {
            logger.error("查询类目信息异常：", e);
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }

        return null;
    }

    /**
     * 删除例外商品 add by songmiao 2015/11/25
     * 
     * @throws Exception
     */
    public void deleteExceptionProduct() throws Exception {
        try {
            promotionProductService.deletePromotionProduct(promotionProduct);
            message.setCode(0);
            message.setTitle("删除成功！");
        } catch (Exception e) {
            logger.error("删除例外商品", e);
            message.setCode(10);
            message.setTitle(e.getMessage());
        }
        JSONObject json = (JSONObject) JSONObject.toJSON(message);
        this.writeJson(json);
    }

    /**
     * 拼装优惠信息 ,更新活动页面使用 2015-7-31 add by songmiao
     * 
     * @param promotion
     * @return
     */
    public String getRuleData(PromotionInfo promotion) {
        StringBuffer sf = new StringBuffer();//
        if (promotion.getPromotionType() == PromotionTypeEnums.GANT.getValue().intValue()) {
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
                                .append(p.getProductSkuId()).append("</a>的商品");
                    } else {
                        bf.append(";skuId为")
                                .append("<a title='点击预览' href='javascript:;' onclick='javascript:toProductSku("
                                        + p.getProductSkuId() + ")'>")
                                .append(p.getProductSkuId()).append("</a>的商品");
                    }
                    i = i + 1;
                }
                return bf.toString();
            } else {
                return "无统一赠品";
            }
        }
        if (promotion.getRuleDatas() == null || promotion.getRuleDatas().size() < 1) {
            String sellUpType = promotion.getSellUpType() == 1 ? "，卖光后停止销售直到活动结束。" : "，卖光后恢复原价销售。";
            if (promotion.getPromotionType() == PromotionTypeEnums.SALE.getValue().intValue()) {
                return promotion.getPromotionData() == null ? "无统一特价" + sellUpType
                        : "统一特价:" + promotion.getPromotionData() + sellUpType;
            } else if (promotion.getPromotionType() == PromotionTypeEnums.DISCOUNT.getValue()
                    .intValue()) {
                return "活动价为现在价格的" + Long.valueOf(
                        promotion.getPromotionData().multiply(BigDecimal.valueOf(10l)).intValue())
                        + "%" + sellUpType;
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
        } else if (promotion.getPromotionType() == PromotionTypeEnums.GIFT.getValue().intValue()) {
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
        return sf.toString();
    }

    public PromotionProduct getPromotionProduct() {
        return promotionProduct;
    }

    public void setPromotionProduct(PromotionProduct promotionProduct) {
        this.promotionProduct = promotionProduct;
    }

    public ProductSku getProductSku() {
        return productSku;
    }

    public void setProductSku(ProductSku productSku) {
        this.productSku = productSku;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public PromotionRule getPromotionRule() {
        return promotionRule;
    }

    public void setPromotionRule(PromotionRule promotionRule) {
        this.promotionRule = promotionRule;
    }

}
