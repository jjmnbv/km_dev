package com.kmzyc.supplier.ajax;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.supplier.model.SuppliersInfo;
import com.kmzyc.supplier.action.SupplierBaseAction;
import com.kmzyc.supplier.service.CategoryService;
import com.kmzyc.supplier.service.ProductDraftService;
import com.kmzyc.supplier.service.ProductDraftSkuService;
import com.kmzyc.supplier.service.SupplierInfoService;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.enums.AuditStatus;
import com.pltfm.app.enums.DraftType;
import com.pltfm.app.enums.ProductStatus;
import com.pltfm.app.fobject.SkuCheckAttr;
import com.pltfm.app.vobject.ProdBrand;
import com.pltfm.app.vobject.ProductAttrDraft;
import com.pltfm.app.vobject.ProductDraft;
import com.pltfm.app.vobject.ProductSkuDraft;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

@Controller("productDraftAjaxAction")
@Scope("prototype")
public class ProductDraftAjaxAction extends SupplierBaseAction {

    private static Logger logger = LoggerFactory.getLogger(ProductDraftAjaxAction.class);

    @Resource
    private ProductDraftService productDraftService;

    @Resource
    private ProductDraftSkuService productDraftSkuService;

    @Resource
    private SupplierInfoService supplierInfoService;

    @Resource
    private CategoryService categoryService;

    private List<Long> productIds;

    private Long productId;

    private String rtnMessage;

    private String type;

    private Long[] upProductId;

    private Integer dataType;

    private ProductDraft product;

    private List<SkuCheckAttr> skuCheckAttrs;

    //要删除的SKU
    private String toDeleteSkuIds;

    private List<String> oldskuCheckedId;

    private Double price;

    private Double pvValue;

    private Long categoryId;

    // SKU商品的销售单价
    private String[] skuPrice;

    // SKU商品的pv
    private String[] skuPvValue;

    // SKU商品的市场价
    private String[] skuMarkPrice;

    // 要更新的skuId
    private String[] productSkuId;

    private String[] productSkuCode;

    // SKU商品的重量
    private String[] skuWeight;

    private String checkIds;

    private String auditStatus;

    private SuppliersInfo suppliersInfo;

    private List<ProductAttrDraft> resultList;

    /**
     * 存储店内分类ID的
     */
    private String shopCategoryId;

    /**
     * 提交申请审核or取消审核
     *
     * @return
     */
    public String upAduit() {
        ProductDraft pd = new ProductDraft();
        pd.setProductId(productId);
        String successMsg = "";
        String failMsg = "";
        if ("0".equals(type.trim())) {
            pd.setStatus(ProductStatus.UNAUDIT.getStatus());
            pd.setPriceStatus(ProductStatus.UNAUDIT.getStatus());
            pd.setToCheckTime(new Timestamp(System.currentTimeMillis()));
            //提交审核时，将上一次审核不通过的原因清空  bug[1711]  --zhoujiwei
            pd.setReasons("");
            pd.setPriceReasons("");
            successMsg = "提交审核成功！";
            failMsg = "提交审核失败！";
        } else {
            successMsg = "取消审核成功！";
            failMsg = "取消审核失败！";
            pd.setStatus(ProductStatus.DRAFT.getStatus());
            pd.setPriceStatus(ProductStatus.DRAFT.getStatus());
        }
        boolean flag = false;
        Map<String, Object> jsonMap = new HashMap();

        try {
            productDraftService.updateObject(pd, "submitAudit");
            flag = true;
            jsonMap.put("flag", flag);
            jsonMap.put("msg", flag ? successMsg : failMsg);
            writeJson(jsonMap);
        } catch (Exception e) {
            logger.error("提交申请审核or取消审核失败，", e);
            this.setRtnMessage("系统发生错误,提交审核失败！");
        }

        return null;
    }

    public String deleteProductDraft() {
        ProductDraft pd = new ProductDraft();
        pd.setProductId(productId);
        boolean flag = false;
        Map<String, Object> jsonMap = new HashMap();

        try {
            productDraftService.deleteProductDraft(pd);
            flag = true;
        } catch (Exception e) {
            logger.error("删除草稿商品失败，", e);
            flag = false;
        }

        jsonMap.put("flag", flag);
        jsonMap.put("msg", flag ? "删除成功" : "删除失败");
        writeJson(jsonMap);
        return null;
    }

    public String productuDeleteBatch() {
        ProductDraft pd = null;
        boolean flag = false;
        Map<String, Object> jsonMap = new HashMap();
        try {
            for (Long pid : upProductId) {
                pd = new ProductDraft();
                pd.setProductId(pid);
                productDraftService.deleteProductDraft(pd);
            }
            flag = true;
        } catch (Exception e) {
            logger.error("批量删除草稿商品失败，", e);
            flag = false;
        }

        jsonMap.put("flag", flag);
        jsonMap.put("msg", flag ? "删除成功" : "删除失败");
        writeJson(jsonMap);
        return null;
    }

    public String productDraftUpShelf() {
        Map jsonMap = new HashMap();
        try {
            suppliersInfo = supplierInfoService.selectByPrimaryKey(getSupplyId());//查询供应商信息
            //如果关闭了供应商业务此供应商不能上架操作
            if (suppliersInfo == null || suppliersInfo.getBusinessStatus() == 1) {
                jsonMap.put("flag", false);
                jsonMap.put("msg", "您已经被禁用此操作!");
                writeJson(jsonMap);
                return null;
            }

            ResultMessage rm = productDraftService.upShelf(upProductId, getLoginUserId(), getSupplyId());
            jsonMap.put("flag", rm.getIsSuccess());
            jsonMap.put("msg", rm.getIsSuccess() ? "上架成功!" : rm.getMessage());
        } catch (Exception e) {
            logger.error("productDraftUpShelf失败，", e);
            jsonMap.put("flag", false);
            jsonMap.put("msg", "系统发生错误，请联系管理员或稍后再试！");
        }

        writeJson(jsonMap);
        return null;
    }

    public String updatePriceAndWeight() {
        Map jsonMap = new HashMap();
        List<ProductSkuDraft> list = new ArrayList();
        ProductSkuDraft draft = null;
        for (int i = 0; i < skuPrice.length; i++) {
            draft = new ProductSkuDraft();
            if (StringUtils.isNotBlank(skuPrice[i])) {
                draft.setPrice(Double.valueOf(skuPrice[i]));
            }
            if (StringUtils.isNotBlank(skuMarkPrice[i])) {
                draft.setMarkPrice(Double.valueOf(skuMarkPrice[i]));
            }
            if (StringUtils.isNotBlank(skuWeight[i])) {
                draft.setUnitWeight(Double.valueOf(skuWeight[i]));
            }

            draft.setProductSkuId(Long.valueOf(productSkuId[i]));
            list.add(draft);
        }

        try {
            boolean haveDone = productDraftSkuService.updatePriceAndWeight(productId, list);
            jsonMap.put("flag", haveDone);
            jsonMap.put("msg", haveDone ? "价格重量修改成功!" : "价格重量修改失败!");
        } catch (Exception e) {
            logger.error("updatePriceAndWeight error ", e);
            jsonMap.put("flag", false);
            jsonMap.put("msg", "价格重量修改失败!");
        }

        writeJson(jsonMap);
        return null;
    }

    /**
     * 更新正式表中的价格
     *
     * @return
     */
    public String updateProductSKUPrice() {
        Map jsonMap = new HashMap();
        List<ProductSkuDraft> list = new ArrayList();
        ProductSkuDraft skuDraft = null;
        for (int i = 0; i < productSkuId.length; i++) {
            skuDraft = new ProductSkuDraft();
            skuDraft.setProductId(productId);
            skuDraft.setProductSkuCode(productSkuCode[i]);
            skuDraft.setProductSkuId(Long.valueOf(productSkuId[i]));
            if (StringUtils.isNotBlank(skuPrice[i])) {
                skuDraft.setPrice(Double.valueOf(skuPrice[i]));
            }
            if (StringUtils.isNotBlank(skuMarkPrice[i])) {
                skuDraft.setMarkPrice(Double.valueOf(skuMarkPrice[i]));
            }
            if (StringUtils.isNotBlank(skuWeight[i])) {
                skuDraft.setUnitWeight(Double.valueOf(skuWeight[i]));
            }
            skuDraft.setOpType(DraftType.ALONEPRICE.getStatus());
            skuDraft.setStatus(AuditStatus.UNAUDIT.getStatus());

            list.add(skuDraft);
        }

        try {
            boolean haveDone = productDraftSkuService.updateSingleSkuPrice(productId, list);
            jsonMap.put("flag", haveDone);
            jsonMap.put("msg", haveDone ? "价格重量修改成功!" : "价格重量修改失败!");
        } catch (Exception e) {
            logger.error("更新正式表中的价格失败：", e);
            jsonMap.put("flag", false);
            jsonMap.put("msg", "价格重量修改失败!");
        }

        writeJson(jsonMap);
        return null;
    }

    /**
     * 产品草稿表的更新
     *
     * @return
     */
    public String productDraftUpdate() {
        if (dataType == 7) {
            type = "product";
            //return showProductDraftList();
        }

        Map<String, Object> jsonMap = new HashMap();
        productId = product.getProductId();
        boolean isUpdateSku = false;
        boolean resultFlag = false;
        String resultMsg = "";

        try {
            product.setModifUser(getLoginUserId());
            product.setModifTime(new Date());
            product.setStatus(ProductStatus.DRAFT.getStatus());//草稿
            product.setShopCode(getSupplyId().toString());

            switch (dataType) {
                case 1:
                    productDraftService.updateObject(product, shopCategoryId);
                    resultFlag = true;
                    resultMsg = "保存基本信息成功！";
                    break;
                case 2:
                    productDraftService.updateCategoryAttrValue(product, dataType);
                    resultFlag = true;
                    resultMsg = "基本属性修改成功！";
                    break;
                case 3:
                    product.setPriceStatus(ProductStatus.DRAFT.getStatus());
                    productDraftService.updateSkuAttrValue(product, skuCheckAttrs, oldskuCheckedId, toDeleteSkuIds, dataType);
                    skuCheckAttrs = null;
                    resultFlag = true;
                    resultMsg = "SKU规格修改成功！";
                    isUpdateSku = true;
                    break;
                case 4:
                    productDraftService.updateDefinitionAttrValue(product, dataType);
                    resultFlag = true;
                    resultMsg = "自定义属性修改成功！";
                    break;
                case 5:
                    productDraftService.updateOperationAttrValue(product, this.getLoginUserId(), product.getOperationAttrIds());
                    resultFlag = true;
                    resultMsg = "运营属性修改成功！";
                    break;
                case 6:
                    product.setIntroduce(full2HalfChange(product.getIntroduce()));
                    if (product.getIntroduceLazy() != null) {
                        product.setIntroduceLazy(full2HalfChange(product.getIntroduceLazy()));
                    }
                    productDraftService.updateObject(product, null);
                    resultFlag = true;
                    resultMsg = "商品介绍修改成功！";
                    break;
                default:
                    logger.error("无效类型。");
                    break;
            }

        } catch (Exception e) {
            logger.error("产品草稿表的更新失败：", e);
        }

        if (isUpdateSku) {
            return toUpdateProductSku();
        }

        product = new ProductDraft();
        product.setProductId(productId);
        jsonMap.put("flag", resultFlag);
        jsonMap.put("msg", resultMsg);
        writeJson(jsonMap);
        return null;
    }

    /**
     * 全角转半角方法
     *
     * @param QJstr
     * @return
     */
    public String full2HalfChange(String QJstr) {
        StringBuffer outStrBuf = new StringBuffer("");
        String Tstr = "";
        byte[] b = null;
        for (int i = 0; i < QJstr.length(); i++) {
            Tstr = QJstr.substring(i, i + 1);
            // 全角空格转换成半角空格
            if (Tstr.equals("　")) {
                outStrBuf.append(" ");
                continue;
            }
            try {
                b = Tstr.getBytes("unicode");
                // 得到 unicode 字节数据
                if (b[2] == -1) {
                    // 表示全角
                    b[3] = (byte) (b[3] + 32);
                    b[2] = 0;
                    outStrBuf.append(new String(b, "unicode"));
                } else {
                    outStrBuf.append(Tstr);
                }
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } // end for.
        return outStrBuf.toString();

    }

    /**
     * 单独的更新产品SKU
     *
     * @return
     */
    public String toUpdateProductSku() {
        Long productId = product.getProductId();
        Short opType = product.getOpType();
        String productNo = product.getProductNo();
        product = new ProductDraft();
        product.setProductId(productId);
        product.setOpType(opType);
        product.setProductNo(productNo);

        try {
            //取产品属性
            List<ProductAttrDraft> productAttrList = productDraftService.getProductAttrList(productId);
            resultList = productAttrList.stream()
                    .filter(attrDraft -> attrDraft.getProductAttrType().intValue() == 1
                            && attrDraft.getIsSku().intValue() == 1)
                    .collect(Collectors.toList());

            productDraftService.setCategoryAttrValue(resultList);
            ProductDraft productDraft = productDraftService.findSingleProductAndSkusAndAttrValues(product);
            if (productDraft != null) {
                product.setProductSkuDrafts(productDraft.getProductSkuDrafts());
            }
            writeStr("<script type='text/javascript' charset='utf-8' >alert('SKU规格修改成功！');</script>");
        } catch (Exception e) {
            logger.error("单独的更新产品SKU失败，", e);
            return ERROR;
        }
        return "skuList";
    }

    /**
     * 检查是否有未填写的价格或者重量
     *
     * @return
     */
    public String gotoCheckSkuPriceAndWeight() {
        try {
            List<ProductSkuDraft> list = productDraftSkuService.findIsValidProductSkuByProductId(productId);
            long count = list.stream()
                    .filter(skuDraft -> skuDraft.getMarkPrice() == null || skuDraft.getPrice() == null || skuDraft.getUnitWeight() == null)
                    .count();
            writeStr(count > 0 ? "0" : "1");
        } catch (Exception e) {
            logger.error("检查是否有未填写的价格或者重量失败，", e);
        }

        return null;
    }

    /**
     * 提交产品价格的申请审核
     *
     * @return
     */
    public String gotoSubmitAuditProductPrice() {
        Map<String, Object> jsonMap = new HashMap();
        ProductDraft pd = new ProductDraft();
        pd.setProductId(productId);
        pd.setPriceStatus(auditStatus);

        try {
            int count = productDraftService.updateObject(pd, "submitAudit");
            boolean haveDone = count > 0;
            jsonMap.put("flag", haveDone);
            jsonMap.put("msg", haveDone ? "操作成功!" : "操作失败!");
        } catch (Exception e) {
            logger.error("提交产品价格的申请审核失败，", e);
            jsonMap.put("flag", false);
            jsonMap.put("msg", "操作失败!");
        }

        writeJson(jsonMap);
        return null;
    }

    public String deleteProductDraftById() {
        Map<String, Object> jsonMap = new HashMap();
        try {
            int count = productDraftService.deleteProductDraftByProductId(productId);
            boolean haveDone = count > 0;
            jsonMap.put("flag", haveDone);
            jsonMap.put("msg", haveDone ? "删除成功!" : "删除失败!");
        } catch (Exception e) {
            logger.error("deleteProductDraftById失败，", e);
            jsonMap.put("flag", false);
            jsonMap.put("msg", "删除失败!");
        }

        writeJson(jsonMap);
        return null;
    }

    public String releaseProductPrice() {
        Map jsonMap = new HashMap();
        try {
            ResultMessage resultMessage = productDraftService.releaseProductPrice(productId);
            jsonMap.put("flag", resultMessage.getIsSuccess());
            jsonMap.put("msg", resultMessage.getMessage());
        } catch (Exception e) {
            logger.error("releaseProductPrice失败，", e);
            jsonMap.put("flag", false);
            jsonMap.put("msg", "发布失败!");
        }

        writeJson(jsonMap);
        return null;
    }

    public String findAllBrandForJson() {
        try {
            List<ProdBrand> list = prodBrandService.findAllValidBrand();
            Map<String, String> brandMap = new LinkedHashMap();
            for (ProdBrand pd : list) {
                brandMap.put(pd.getBrandId().toString(), pd.getBrandName());
            }
            writeJson(brandMap);
        } catch (Exception e) {
            logger.error("findAllBrandForJson失败，", e);
        }

        return null;
    }

    public String previewProductDraftInfoPage() {
        Map jsonMap = new HashMap();
        try {
            String pageUrl = productDraftService.previewProductDraftInfoPage(productId.toString());
            logger.info("产品预览地址:" + pageUrl);
            jsonMap.put("productPageUrl", pageUrl);
        } catch (Exception e) {
            jsonMap.put("productPageUrl", "");
            logger.error("previewProductDraftInfoPage失败，", e);
        }

        writeJson(jsonMap);
        return null;
    }

    public String checkSkuPvValue() throws ServiceException{
        ResultMessage resultMessage = categoryService.checkSkuPvValue(price, pvValue, categoryId);
        writeJson(resultMessage);
        return null;
    }

    public String checkAllSkuPvValue() throws ServiceException {
        ResultMessage resultMessage = categoryService.checkAllSkuPvValue(skuPrice, skuPvValue, categoryId);
        writeJson(resultMessage);
        return null;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }

    public String getRtnMessage() {
        return rtnMessage;
    }

    public void setRtnMessage(String rtnMessage) {
        this.rtnMessage = rtnMessage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long[] getUpProductId() {
        Long[] temp = upProductId;
        return temp;
    }

    public void setUpProductId(Long[] upProductId) {
        this.upProductId = upProductId;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public ProductDraft getProduct() {
        return product;
    }

    public void setProduct(ProductDraft product) {
        this.product = product;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getToDeleteSkuIds() {
        return toDeleteSkuIds;
    }

    public void setToDeleteSkuIds(String toDeleteSkuIds) {
        this.toDeleteSkuIds = toDeleteSkuIds;
    }

    public List<String> getOldskuCheckedId() {
        return oldskuCheckedId;
    }

    public void setOldskuCheckedId(List<String> oldskuCheckedId) {
        this.oldskuCheckedId = oldskuCheckedId;
    }

    public Long getProductId() {
        return productId;
    }


    public String[] getSkuPrice() {
        String[] temp = skuPrice;
        return temp;
    }

    public void setSkuPrice(String[] skuPrice) {
        this.skuPrice = skuPrice;
    }

    public String[] getSkuMarkPrice() {
        String[] temp = skuMarkPrice;
        return temp;
    }

    public void setSkuMarkPrice(String[] skuMarkPrice) {
        this.skuMarkPrice = skuMarkPrice;
    }

    public String[] getProductSkuId() {
        String[] temp = productSkuId;
        return temp;
    }

    public void setProductSkuId(String[] productSkuId) {
        this.productSkuId = productSkuId;
    }

    public String[] getSkuWeight() {
        String[] temp = skuWeight;
        return temp;
    }

    public void setSkuWeight(String[] skuWeight) {
        this.skuWeight = skuWeight;
    }

    public String getCheckIds() {
        return checkIds;
    }

    public void setCheckIds(String checkIds) {
        this.checkIds = checkIds;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public List<ProductAttrDraft> getResultList() {
        return resultList;
    }

    public void setResultList(List<ProductAttrDraft> resultList) {
        this.resultList = resultList;
    }

    public String[] getProductSkuCode() {
        String[] temp = productSkuCode;
        return temp;
    }

    public void setProductSkuCode(String[] productSkuCode) {
        this.productSkuCode = productSkuCode;
    }

    public List<SkuCheckAttr> getSkuCheckAttrs() {
        return skuCheckAttrs;
    }

    public void setSkuCheckAttrs(List<SkuCheckAttr> skuCheckAttrs) {
        this.skuCheckAttrs = skuCheckAttrs;
    }

    public String getShopCategoryId() {
        return shopCategoryId;
    }

    public void setShopCategoryId(String shopCategoryId) {
        this.shopCategoryId = shopCategoryId;
    }

    public SuppliersInfo getSuppliersInfo() {
        return suppliersInfo;
    }

    public void setSuppliersInfo(SuppliersInfo suppliersInfo) {
        this.suppliersInfo = suppliersInfo;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPvValue() {
        return pvValue;
    }

    public void setPvValue(Double pvValue) {
        this.pvValue = pvValue;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String[] getSkuPvValue() {
        String[] temp = skuPvValue;
        return temp;
    }

    public void setSkuPvValue(String[] skuPvValue) {
        this.skuPvValue = skuPvValue;
    }

}
