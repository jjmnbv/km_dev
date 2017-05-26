package com.pltfm.app.action;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;
import com.kmzyc.supplier.enums.SuppliersType;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.opensymphony.xwork2.Action;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.enums.AuditStatus;
import com.pltfm.app.enums.DraftType;
import com.pltfm.app.enums.MsgOperation;
import com.pltfm.app.enums.ProductOperateType;
import com.pltfm.app.enums.ProductStatus;
import com.pltfm.app.enums.ProductType;
import com.pltfm.app.fobject.SkuCheckAttr;
import com.pltfm.app.maps.CertificateTypeMap;
import com.pltfm.app.service.CmsProductUpShelfService;
import com.pltfm.app.service.OperationAttrService;
import com.pltfm.app.service.ProductAttrDraftService;
import com.pltfm.app.service.ProductCertificateDraftService;
import com.pltfm.app.service.ProductDraftService;
import com.pltfm.app.service.ProductOperateLogService;
import com.pltfm.app.service.ProductService;
import com.pltfm.app.service.ProductSkuAttrDraftService;
import com.pltfm.app.service.ProductSkuDraftService;
import com.pltfm.app.service.ProductSkuService;
import com.pltfm.app.util.FileUploadUtils;
import com.pltfm.app.util.ProductListType;
import com.pltfm.app.vobject.OperationAttr;
import com.pltfm.app.vobject.ProductAttrDraft;
import com.pltfm.app.vobject.ProductCertificateDraft;
import com.pltfm.app.vobject.ProductDraft;
import com.pltfm.app.vobject.ProductOperateLog;
import com.pltfm.app.vobject.ProductSkuDraft;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import javax.annotation.Resource;

@Controller(value = "productDraftAction")
@Scope(value = "prototype")
public class ProductDraftAction extends BaseAction {

    protected Logger log = LoggerFactory.getLogger(ProductDraftAction.class);

    private Page page;

    private String certificateViewPath = ConfigurationUtil.getString("certificateFileViewPath");

    private ProductDraft product;

    private ProductDraft productForSelectPara;

    private List<OperationAttr> operationAttrList;

    private List<SkuCheckAttr> skuCheckAttrs;

    private List<String> oldskuCheckedId;

    private List<Long> productIds;

    private Long productId;

    private String productNo;

    private Integer dataType;
    @Resource
    private ProductOperateLogService productOperateLogService;

    @Resource
    private ProductDraftService productDraftService;

    @Resource
    private ProductAttrDraftService productAttrDraftService;

    @Resource
    private OperationAttrService operationAttrService;

    @Resource
    private CmsProductUpShelfService cmsProductUpShelfService;

    @Resource
    private ProductService productService;

    @Resource
    private ProductSkuService productSkuService;

    @Resource
    private ProductSkuDraftService productSkuDraftService;

    @Resource
    private ProductCertificateDraftService productCertificateDraftService;

    @Resource
    private ProductSkuAttrDraftService productSkuAttrDraftService;

    private String type;

    private String rtnMessage;

    private String drugCategoryKey;

    private String auditStatus;

    private String reasonText;

    // 要从正式库拿过来的SKU
    private String[] oldSkuCode;

    // 要删除的SKU
    private String toDeleteSkuIds;

    // 批量审核的Id
    private String[] productIdChk;

    // 药品资质文件相关信息
    private List<Long> certificateFileIds;

    private Integer[] certificateType;

    private File[] certificateFiles;

    private String[] certificateFilesFileName;

    private String[] upProductIds;

    private String[] ckChannel;

    private Long bCategoryId;// 一级类目

    private Long mCategoryId;// 二级类目

    // 新增产品基础信息记录
    public String addProduct() {
        HashMap<String, String> result = new HashMap();
        result.put("isSuccess", "1");
        try {
            if (ArrayUtils.isNotEmpty(certificateFiles) && ProductType.OTC.getKey().equals(product.getProductType())) {
                // 文件绝对路径
                String savePath = FileUploadUtils.createCertificateSavePath();
                // db路径
                String relativePath = savePath.substring(ConfigurationUtil.getString("certificateFilePath").length());
                List<ProductCertificateDraft> cerList = handleCertificateFile(savePath, relativePath);
                product.setCerfificateList(cerList);
            }

            product.setCreateUser(Long.valueOf(getLoginUserId()));
            product.setCreateTime(new Date());
            product.setStatus(ProductStatus.DRAFT.getStatus());// 草稿
            product.setOpType(DraftType.ADD.getStatus());
            product.setPriceStatus(ProductStatus.DRAFT.getStatus());
            product.setImageStatus(ProductStatus.DRAFT.getStatus());
            productId = productDraftService.insertProduct(product, skuCheckAttrs);
            result.put("productId", productId.toString());
        } catch (Exception e) {
            logger.error("新增产品失败", e);
            result.put("isSuccess", "0");
            result.put("msg", "新增产品失败");
        }
        writeJson(result);
        return null;
    }

    /**
     * 跳转上传图片页面
     *
     * @return
     */
    public String toUploadSkuProductImage() {
        try {
            ProductDraft conditionProduct = new ProductDraft();
            conditionProduct.setProductId(productId);
            this.product = productDraftService.findSingleProductAndSkuAndAttrValues(conditionProduct);
            List<ProductSkuDraft> skuList = product.getProductSkuDrafts();

            super.getRequest().setAttribute("productSkuList", skuList);
            super.getRequest().setAttribute("swfUploadCounts", skuList.size());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Action.SUCCESS;
    }

    /**
     * 初始化page和productForSelectPara
     */
    private void initPageAndProductForSelectPara() {
        if (page == null) {
            page = new Page();
        }
        if (productForSelectPara == null) {
            productForSelectPara = new ProductDraft();
        }
    }

    /**
     * 产品列表或者产品审核列表
     */
    public String showList() {
        String returnType = type;
        initPageAndProductForSelectPara();

        try {
            if (StringUtils.isNotEmpty(type)) {
                if (type.indexOf("product") >= 0) {// 产品列表
                    List<Short> opTypes = new ArrayList<Short>();
                    opTypes.add(DraftType.ADD.getStatus());
                    opTypes.add(DraftType.UPDATE.getStatus());
                    opTypes.add(DraftType.SAFE.getStatus());
                    productForSelectPara.setOpTypes(opTypes);
                } else if (type.indexOf("price") >= 0) { // 价格列表
                    if ("priceSuccessFromOfficial".equals(type)) {
                        productForSelectPara.setProductNo(productNo);
                    }
                    List<Short> opTypes = new ArrayList<Short>();
                    opTypes.add(DraftType.ADD.getStatus());
                    opTypes.add(DraftType.UPDATE.getStatus());
                    opTypes.add(DraftType.ALONEPRICE.getStatus());
                    opTypes.add(DraftType.SAFE.getStatus());
                    productForSelectPara.setOpTypes(opTypes);
                    returnType = "price";
                } else if (type.indexOf("weight") >= 0) { // 重量
                    returnType = "weight";
                } else if (type.indexOf("audit") >= 0) { // 产品需要审核的列表
                    List<Short> opTypes = new ArrayList<Short>();
                    opTypes.add(DraftType.ADD.getStatus());
                    opTypes.add(DraftType.UPDATE.getStatus());
                    opTypes.add(DraftType.SAFE.getStatus());
                    productForSelectPara.setOpTypes(opTypes);
                    productForSelectPara.setStatus(ProductStatus.UNAUDIT.getStatus());
                    returnType = "audit";
                } else if (type.indexOf("priDraft") >= 0) { // 价格需要审核的列表
                    productForSelectPara.setPriceStatus(ProductStatus.UNAUDIT.getStatus());
                    returnType = "priDraft";
                }
            }

            productForSelectPara.setUserId(String.valueOf(getLoginUserId()));
            productForSelectPara.setBCategoryId(bCategoryId);
            productForSelectPara.setMCategoryId(mCategoryId);
            productDraftService.searchPage(page, productForSelectPara, returnType);
            // 查询列表初始化类目级联查询下拉框
            initShowCategoryListForDraft(productForSelectPara);
            getDraftTypeMap();
            getProductDraftMap();
            setProductStatusMap();
            setProductTypeMap();
            setProductBrandMap();
            setSupplyTypeMap();
            setCategoryAuditMap();
        } catch (Exception e) {
            logger.error("产品草稿列表出错，type为[{}]," + e.getMessage(), type, e);
            return ERROR;
        }

        return returnType;
    }

    /**
     * 价格列表或者价格审核列表
     *
     * @return
     */
    public String showAuditList() {
        String returnType = type;
        initPageAndProductForSelectPara();

        try {
            if (type.indexOf("price") >= 0) { // 价格列表
                List<Short> opTypes = new ArrayList<Short>();
                opTypes.add(DraftType.ADD.getStatus());
                opTypes.add(DraftType.UPDATE.getStatus());
                opTypes.add(DraftType.ALONEPRICE.getStatus());
                opTypes.add(DraftType.SAFE.getStatus());
                productForSelectPara.setOpTypes(opTypes);
                if ("priceSuccessFromOfficial".equals(type)) {
                    productForSelectPara.setProductNo(productNo);
                }
                returnType = "price";
            } else if (type.indexOf("priDraft") >= 0) { // 价格需要审核的列表
                productForSelectPara.setPriceStatus(ProductStatus.UNAUDIT.getStatus());
                returnType = "priDraft";
            }

            // 需要按SKU查询，要改
            productForSelectPara.setUserId(String.valueOf(getLoginUserId()));
            productForSelectPara.setBCategoryId(bCategoryId);
            productForSelectPara.setMCategoryId(mCategoryId);
            productDraftService.searchPageByUserForAudit(page, productForSelectPara, type);
            // 查询列表初始化类目级联查询下拉框
            initShowCategoryListForDraft(productForSelectPara);

            getDraftTypeMap();
            getProductDraftMap();
            setProductStatusMap();
            setProductTypeMap();
        } catch (Exception e) {
            log.error("查询产品重量价格列表失败：" + e.getMessage(), e);
            return ERROR;
        }

        return returnType;
    }

    /**
     * 单个产品的SKU以及SKU属性和属性值
     *
     * @return
     */
    public String findProductSkusAndAttrValues() {
        try {
            ProductDraft conditionProduct = new ProductDraft();
            conditionProduct.setProductId(productId);
            product = productDraftService.findSingleProductAndSkuAndAttrValues(conditionProduct);
            if (product == null) {
                return ERROR;
            }

            if (ProductListType.PRICE.equals(type)) {
                Integer supplierType = supplierAuditService.getSupplierTypeByProductDraft(productId);
                getRequest().setAttribute("sell", SuppliersType.SELL.getStatus().intValue() == supplierType);
                getRequest().setAttribute("support", SuppliersType.SUPPORT.getStatus().intValue() == supplierType
                        || SuppliersType.EMTER.getStatus().intValue() == supplierType);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
        return type;
    }

    /**
     * 产品提交申请审核
     *
     * @return
     */
    public String submitDraftTheAudit() {
        ProductDraft pd = new ProductDraft();
        pd.setProductId(productId);
        pd.setStatus(ProductStatus.UNAUDIT.getStatus());
        pd.setToCheckTime(new Timestamp(System.currentTimeMillis()));
        logger.info("用户[{}]开始提交申请审核产品[{}].", new Object[] {getLoginUserId(), productId});
        try {
            productDraftService.updateObject(pd);
            setRtnMessage("提交审核成功！");
        } catch (Exception e) {
            logger.error("提交申请审核失败，productId为{}，", productId, e);
            setRtnMessage("系统发生错误,提交审核失败！");
        }
        type = "product";
        return showAuditList();
    }

    /**
     * 产品批量提交审核
     *
     * @return
     */
    public String batchSubmitDraftTheAudit() {
        ProductDraft pd = null;
        List<ProductDraft> list = new ArrayList<ProductDraft>();
        for (Long pId : productIds) {
            pd = new ProductDraft();
            pd.setProductId(pId);
            pd.setStatus(ProductStatus.UNAUDIT.getStatus());
            pd.setToCheckTime(new Timestamp(System.currentTimeMillis()));
            list.add(pd);
        }
        logger.info("用户[{}]开始批量提交申请审核产品[{}].",
                new Object[] {getLoginUserId(), Arrays.asList(productIds)});
        try {
            productDraftService.batchSubmitDraftTheAudit(list);
            setRtnMessage("提交审核成功！");
        } catch (Exception e) {
            logger.error("产品批量提交审核失败，productIds{}，", productIds, e);
            setRtnMessage("系统发生错误,提交审核失败！");
        }
        type = "product";
        return showAuditList();
    }

    /**
     * 产品撤销审核
     *
     * @return
     */
    public String submitDraftTheUnAudit() {
        ProductDraft pd = new ProductDraft();
        pd.setProductId(productId);
        pd.setStatus(ProductStatus.DRAFT.getStatus());
        logger.info("用户[{}]开始撤销审核产品[{}].", new Object[] {getLoginUserId(), productId});
        try {
            productDraftService.updateObject(pd);
            this.setRtnMessage("取消审核成功！");
        } catch (Exception e) {
            logger.error("产品撤销审核失败，productId{}，", productId, e);
            this.setRtnMessage("系统发生错误,取消审核失败！");
        }
        type = "product";
        return showAuditList();
    }

    /**
     * 产品详情页面
     *
     * @return
     */
    public String viewProductForAuditDraft() {
        // 代码重复
        viewProductDraft();
        return Action.SUCCESS;
    }

    /**
     * 对产品进行审核
     *
     * @return
     */
    public String auditProductDraft() {
        try {
            logger.info("用户[{}]开始进行审核产品[{}].", new Object[] {getLoginUserId(), productId});
            ProductDraft prodTmp = new ProductDraft();
            prodTmp.setCheckTime(new Date());
            prodTmp.setCheckUser(Long.valueOf(super.getLoginUserId()));
            prodTmp.setProductId(productId);
            prodTmp.setStatus(auditStatus);
            prodTmp.setReasons(reasonText);
            productDraftService.updateObject(prodTmp);
            setRtnMessage("审核成功！");
        } catch (Exception e) {
            // String msg
            logger.error("产品审核，productId={},auditStatus={},reasonText={}, 错误信息：{}", new Object[] {
                    productId, auditStatus, reasonText, e});
            setRtnMessage("系统发生错误，请稍后再试或联系管理员！");
        }
        type = "audit";
        return showList();
    }

    /**
     * 批量审核
     *
     * @return
     */
    public String batchAuditProductDraft() {
        ProductDraft pd = null;
        if (ArrayUtils.isEmpty(productIdChk)) {
            setRtnMessage("请选择要审核的产品！");
        } else {
            logger.info("用户[{}]开始批量审核产品[{}].", new Object[] {getLoginUserId(), Arrays.asList(productIdChk)});
            List<ProductDraft> list = new ArrayList<ProductDraft>();
            for (String productId : productIdChk) {
                pd = new ProductDraft();
                pd.setCheckTime(new Date());
                pd.setCheckUser(Long.valueOf(super.getLoginUserId()));
                pd.setProductId(Long.valueOf(productId));
                pd.setStatus(auditStatus);
                pd.setReasons(reasonText);
                list.add(pd);
            }

            try {
                productDraftService.batchUpdateObject(list);
                setRtnMessage("审核成功！");
            } catch (ServiceException e) {
                setRtnMessage("系统发生错误，请稍后再试或联系管理员！");
                logger.error("批量审核失败，auditStatus={},reasonText={},productIdChk={},错误信息：{}",
                        new Object[] {auditStatus, reasonText, productIdChk, e});
            }
        }
        type = "audit";
        return showList();
    }

    /**
     * 产品上架
     *
     * @return
     */
    public String upshelfProductDraft() {
        ResultMessage rm = new ResultMessage();
        logger.info("用户[{}]开始上架产品[{}].", new Object[] {getLoginUserId(), productIds});
        try {
            rm = productDraftService.upShelf(productIds, super.getLoginUserId().longValue());

            if (rm.getIsSuccess()) {
                setRtnMessage("上架成功！");
            } else {
                setRtnMessage(rm.getMessage());
            }
        } catch (Exception e) {
            setRtnMessage("系统发生错误，请联系管理员或稍后再试！");
            logger.error("产品上架失败：", e);
        }
        type = "product";

        if (rm.getIsSuccess()) {
            try {
                // CMS上架
                List<Integer> productIdIntList = productIds.stream().map(id -> Integer.valueOf(id.intValue())).collect(Collectors.toList());
                cmsProductUpShelfService.productUpShelfByCms(productIdIntList);
            } catch (Exception e1) {
                e1.printStackTrace();
            }

            try {
                List<Long> skuIdList = productSkuService.selectSkuIdsByProductIdList(productIds);
                if (skuIdList != null && !skuIdList.isEmpty()) {
                    updateSkuNotify(skuIdList);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }

            // 保存日志
            List<ProductOperateLog> logList = productIds.stream()
                            .map(tempProductId -> new ProductOperateLog(tempProductId, getLoginUserId().longValue(),ProductOperateType.UP.getStatus()))
                            .collect(Collectors.toList());
            try {
                productOperateLogService.batchSaveProductOperateLog(logList);
            } catch (ServiceException e) {
                logger.error("批量保存日志失败，产品={},操作人={},错误信息：{}",
                        new Object[] {new ArrayList<>(Arrays.asList(productIds)), getLoginUserId(), e});
            }
        }

        return this.showList();
    }

    /**
     * 删除
     *
     * @return
     */
    public String deleteProductDraft() {
        try {
            logger.info("用户[{}]开始删除产品[{}].", new Object[] {getLoginUserId(), productId});
            ProductDraft pd = productDraftService.findByProductIdWithOutClob(productId);
            String isExistProduct = "1";
            if (pd != null && DraftType.ADD.getStatus().equals(pd.getOpType())) {
                isExistProduct = "0";
            }

            productDraftService.deleteProductDraftByProductId(productId, isExistProduct);
            this.setRtnMessage("操作成功！");
        } catch (Exception e) {
            this.setRtnMessage("系统发生错误，请联系管理员或稍后再试！");
            e.printStackTrace();
        }
        return this.showList();
    }

    /**
     * 查看详情页面
     *
     * @return
     */
    public String viewProductDraft() {
        try {
            product = productDraftService.findByProductId(productId);
            // 基本属性（类目属性）
            getRequest().setAttribute("cateAttrList", productAttrDraftService.findCategoryAttr(productId));
            // sku属性
            getRequest().setAttribute("skuAttrList", productAttrDraftService.findSkuAttr(productId));
            // 自定义属性
            getRequest().setAttribute("deniAttrList", productAttrDraftService.findDefinitionAttr(productId));
            // 运营属性
            getRequest().setAttribute("opAttrList", productAttrDraftService.findOperationAttr(productId));

            ProductDraft conditionProduct = new ProductDraft();
            conditionProduct.setProductId(productId);
            // sku列表
            getRequest().setAttribute("skuList",
                    productDraftService.findSingleProductAndSkuAndAttrValues(conditionProduct).getProductSkuDrafts());

            if (ProductType.OTC.getKey().equals(product.getProductType())) {
                List<ProductCertificateDraft> certificateList = productCertificateDraftService.findByProductId(productId);
                product.setCerfificateList(certificateList);
                getCertificateOTCMAP();
            }
            setCategoriesName(product.getCategoryId());
            setProductBrandMap();
            setApprovalTypeMap();
            getProductTypeMap();
        } catch (Exception e) {
            log.error("查看产品详情出错，" + e.getMessage(), e);
        }
        return Action.SUCCESS;
    }

    /**
     * 获取产品详情，并进入更新页面
     *
     * @return
     */
    public String toProductDraftUpdate() {
        try {
            setCheckedId(productId);

            product = productDraftService.findByProductId(productId);
            setApprovalTypeMap();
            // 取产品属性
            List<ProductAttrDraft> productAttrList = productAttrDraftService.findProductDraftAttrByProductId(productId);
            // 设置目录属性radio,select,checkbox多item的值
            productAttrDraftService.changeCategoryAttrValue(productAttrList);
            // 基本属性，sku属性
            product.setProductAttrDraftList(productAttrList);
            ProductDraft conditionProduct = new ProductDraft();
            conditionProduct.setProductId(productId);
            // sku列表
            product.setProductSkuDrafts(productDraftService.findSingleProductAndSkuAndAttrValues(conditionProduct).getProductSkuDrafts());
            // 取运营属性列表
            operationAttrList = operationAttrService.queryOperationAttrList();
            productAttrDraftService.changeOperationAttrValue(productId, operationAttrList);

            if (ProductType.OTC.getKey().equals(product.getProductType())) {
                getCertificateOTCMAP();
                getRequest().setAttribute("isOTC", true);
                handleProductCertificateDraft();
            }

            // sku checkbox新增的规格
            getRequest().setAttribute("skuNewAttrList", productSkuAttrDraftService.findAndChangeSkuNewAttr(productId));

            setCategoriesIdByDraft(product, product.getCategoryId());
            bCategoryId = product.getBCategoryId();
            mCategoryId = product.getMCategoryId();
            setIsNoticeMap();
            setProductBrandMap();
            getAllCategoryList(product.getBCategoryId(), product.getMCategoryId());
            // 商户信息
            getSupplierMap();
            getProductTypeMap();
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
        return Action.SUCCESS;
    }

    /**
     * 产品草稿表的更新
     *
     * @return
     */
    public String productDraftUpdate() {
        if (dataType == 7) {
            type = "product";
            return showList();
        }
        product.setBCategoryId(bCategoryId);
        product.setMCategoryId(mCategoryId);
        productId = product.getProductId();
        String script = "";
        boolean needChange = false;
        int index = -1;
        logger.info("用户[{}]开始更新草稿产品[{}].", new Object[] {getLoginUserId(), productId});
        try {
            product.setModifUser(getLoginUserId().longValue());
            product.setModifTime(new Date());
            product.setStatus(ProductStatus.DRAFT.getStatus());// 草稿
            switch (dataType) {
                case 1:
                    productDraftService.updateObject(product);
                    script = "<script type='text/javascript' charset='utf-8' >alert('基本信息修改成功！');</script>";
                    break;
                case 2:
                    productAttrDraftService.updateCategoryAttrValue(product, product.getProductAttrDraftList());
                    script = "<script type='text/javascript' charset='utf-8' >alert('基本属性修改成功！');</script>";
                    break;
                case 3:
                    if (StringUtils.isNotEmpty(toDeleteSkuIds)) {
                        toDeleteSkuIds = toDeleteSkuIds.substring(0, toDeleteSkuIds.length() - 1);
                    }
                    productDraftService.updateSkuAttrValue(product, product.getProductNo(),
                            product.getProductAttrDraftList(), skuCheckAttrs, oldskuCheckedId, toDeleteSkuIds);
                    script ="<script type='text/javascript' charset='utf-8' >alert('SKU规格修改成功！');</script>";
                    needChange = true;
                    index = 1;
                    break;
                case 4:
                    productAttrDraftService.updateDefinitionAttrValue(product, productId,
                            product.getProductAttrDraftList());
                    script = "<script type='text/javascript' charset='utf-8' >alert('自定义属性修改成功！');</script>";
                    break;
                case 5:
                    productAttrDraftService.updateOperationAttrValue(product, productId,
                            product.getOperationAttrIds());
                    script = "<script type='text/javascript' charset='utf-8' >alert('运营属性修改成功！');</script>";
                    break;
                case 6:
                    productDraftService.updateObject(product);
                    script = "<script type='text/javascript' charset='utf-8' >alert('商品介绍修改成功！');</script>";
                    break;
                case 8:
                    if (ArrayUtils.isNotEmpty(certificateFiles)) {
                        // 相对路径
                        String relativePath = productCertificateDraftService.findPathByProductId(productId);
                        // 文件的绝对路径
                        String savePath = ConfigurationUtil.getString("certificateFilePath") + relativePath;

                        List<ProductCertificateDraft> toAddList = handleCertificateFile(savePath, relativePath);
                        productDraftService.updateProductCertificateFiles(product, toAddList, certificateFileIds);
                    }
                    script = "<script type='text/javascript' charset='utf-8' >alert('资质文件修改成功！');</script>";
                    needChange = true;
                    index = 2;
                    break;
                default:
                    script = "<script type='text/javascript' charset='utf-8' >alert('无效类型！');</script>";
                    break;
            }
        } catch (Exception e) {
            log.error("修改草稿产品出错：" + e.getMessage(), e);
            script = "<script type='text/javascript' charset='utf-8' >alert('操作失败！');</script>";
        }
        if (!needChange) {
            strWriteJson(script);
        }
        if (index == 1) {
            return toUpdateProductSku();
        } else if (index == 2) {
            return toUpdateProductCertificateSingle();
        }

        return null;
    }

    /**
     * 处理资质图片
     * @param savePath      保存路径
     * @param relativePath  db路径
     * @return
     * @throws IOException
     */
    private List<ProductCertificateDraft> handleCertificateFile(String savePath, String relativePath) throws IOException {
        List<ProductCertificateDraft> result = new ArrayList();
        ProductCertificateDraft pcd = null;
        // 获取文件后缀名
        String fileExt = "";
        // 文件名
        String fileName = "";

        for (int i = 0; i < certificateFiles.length; i++) {
            if (certificateFiles[i] != null) {
                fileExt = certificateFilesFileName[i].substring(certificateFilesFileName[i].lastIndexOf(".") + 1).toLowerCase();
                fileName = DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS")
                                + new Random().nextInt(1000) + "_" + certificateType[i] + "." + fileExt;

                File deskFile = new File(savePath, fileName);
                FileUtils.copyFile(certificateFiles[i], deskFile);
                pcd = new ProductCertificateDraft();
                pcd.setProductId(productId);
                pcd.setFileName(certificateFilesFileName[i]);
                pcd.setFilePath(relativePath + fileName);
                pcd.setFileType(certificateType[i]);
                result.add(pcd);
            }
        }
        return result;
    }

    /**
     * 单独的更新产品SKU
     *
     * @return
     */
    public String toUpdateProductSku() {
        try {
            // 取产品属性
            List<ProductAttrDraft> attrList = productAttrDraftService.findProductDraftAttrByProductId(productId);
            productAttrDraftService.changeCategoryAttrValue(attrList);
            product.setProductAttrDraftList(attrList);
            ProductDraft conditionProduct = new ProductDraft();
            conditionProduct.setProductId(productId);
            product.setProductSkuDrafts(productDraftService.findSingleProductAndSkuAndAttrValues(conditionProduct).getProductSkuDrafts());
            // 自定义的sku规格
            getRequest().setAttribute("skuNewAttrList", productSkuAttrDraftService.findAndChangeSkuNewAttr(productId));
        } catch (Exception e) {
            logger.error("单独的更新产品SKU失败：", e);
            return ERROR;
        }
        return "skuList";
    }

    /**
     * 单独更新资质文件
     * @return
     */
    public String toUpdateProductCertificateSingle() {
        try {
            handleProductCertificateDraft();

        } catch (Exception e) {
            logger.error("单独更新资质文件失败：", e);
        }
        return "productCertificate";
    }

    private void handleProductCertificateDraft() {
        //1.获取当前产品下的资质文件数据
        List<ProductCertificateDraft> certificateList = productCertificateDraftService.findByProductId(productId);

        //2.获取otc所有文件数据
        Map<Integer, String> map = null;
        if (ProductType.OTC.getKey().equals(product.getProductType())) {
            getCertificateOTCMAP();
            map = CertificateTypeMap.getOTCMAP();
        }

        //3.处理资质文件数据
        if (CollectionUtils.isNotEmpty(certificateList) && certificateList.size() != 4) {
            Map<Integer, String> finalMap = Maps.newHashMap(map);
            //3.1 移除已经有的资质文件数据
            certificateList.stream().forEach(draft -> finalMap.remove(draft.getFileType()));
            //3.2 添加剩余的资质文件数据
            certificateList.addAll(finalMap.entrySet().stream().map(entry -> {
                ProductCertificateDraft p = new ProductCertificateDraft();
                p.setFileType(entry.getKey());
                return p;
            }).collect(Collectors.toList()));
        } else if (CollectionUtils.isEmpty(certificateList)){
            Map<Integer, String> finalMap = Maps.newHashMap(map);
            certificateList = finalMap.entrySet().stream().map(entry -> {
                ProductCertificateDraft p = new ProductCertificateDraft();
                p.setFileType(entry.getKey());
                return p;
            }).collect(Collectors.toList());
        }
        product.setCerfificateList(certificateList);
    }

    /**
     * 产品正式表的更新，向草稿表插入数据
     *
     * @return
     */
    public String productUpdateToAddDraft() {
        Map jsonMap = new HashMap();
        try {
            product.setCreateTime(new Date());
            product.setCreateUser(super.getLoginUserId().longValue());
            product.setOpType(DraftType.UPDATE.getStatus());
            product.setStatus(ProductStatus.DRAFT.getStatus());
            product.setPriceStatus(ProductStatus.AUDIT.getStatus());
            product.setImageStatus(ProductStatus.AUDIT.getStatus());
            if (certificateFiles != null && certificateFiles.length > 0) {
                // 该产品下的资质文件路径
                String path = productCertificateDraftService.findPathByProductId(product.getProductId());
                // 文件的绝对路径
                String savePath = ConfigurationUtil.getString("certificateFilePath") + path;
                // 相对路径
                String relativePath = path;
                List<ProductCertificateDraft> toAddList = new ArrayList<ProductCertificateDraft>();
                ProductCertificateDraft pcd = null;
                for (int i = 0; i < certificateFiles.length; i++) {
                    if (certificateFiles[i] != null) {
                        File deskFile = new File(savePath, certificateFilesFileName[i]);
                        FileUtils.copyFile(certificateFiles[i], deskFile);
                        pcd = new ProductCertificateDraft();

                        if (certificateFileIds != null) {
                            pcd.setPscId(certificateFileIds.get(i));
                        }
                        pcd.setProductId(product.getProductId());
                        pcd.setFileName(certificateFilesFileName[i]);
                        pcd.setFilePath(relativePath + certificateFilesFileName[i]);
                        pcd.setFileType(certificateType[i]);
                        toAddList.add(pcd);
                    }
                }
                product.setCerfificateList(toAddList);
            }

            productDraftService.insertFromProduct(product, skuCheckAttrs);
            productForSelectPara = new ProductDraft();
            productForSelectPara.setProductNo(product.getProductNo());
            jsonMap.put("result", true);
            jsonMap.put("msg", "保存成功!");
        } catch (Exception e) {
            logger.error("产品正式表的更新，向草稿表插入数据:", e);
            jsonMap.put("result", false);
            jsonMap.put("msg", "保存失败!");
        }

        type = "product";
        super.writeJson2(jsonMap);
        return null;
    }

    /**
     * 产品价格的申请审核
     *
     * @return
     */
    public String gotoSubmitAuditProductPrice() {
        ProductDraft pd = new ProductDraft();
        pd.setProductId(productId);
        pd.setPriceStatus(auditStatus);
        logger.info("用户[{}]开始申请审核产品[{}]价格.", new Object[] {getLoginUserId(), productId});
        try {
            productDraftService.updateObject(pd);
            this.setRtnMessage("操作成功！");
        } catch (Exception e) {
            this.setRtnMessage("系统发生错误，操作失败，请稍后再试或联系管理员！");
            e.printStackTrace();
        }

        type = "price";
        return this.showAuditList();
    }

    /**
     * 批量提交产品价格的申请审核
     *
     * @return
     */
    public String batchAuditProductPrice() {
        Map jsonMap = new HashMap();
        logger.info("用户[{}]开始批量申请审核产品[{}]价格.", new Object[] {getLoginUserId(), productIds});
        try {
            if (productSkuDraftService.checkSkuPriceAndWeight(productIds)) {
                jsonMap.put("result", Boolean.FALSE);
                jsonMap.put("msg", "有商品的价格、重量或者pv未填写，不能申请审核!");
                writeJson2(jsonMap);
                return null;
            }

            ProductDraft pd = null;
            for (Long productIdTmp : productIds) {
                pd = new ProductDraft();
                pd.setProductId(productIdTmp);
                pd.setPriceStatus(AuditStatus.AUDIT.getStatus());
                productDraftService.updateObject(pd);
            }

            jsonMap.put("result", true);
            jsonMap.put("msg", "批量申请审核成功!");
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("result", false);
            jsonMap.put("msg", "批量申请审核失败!");
        }
        writeJson2(jsonMap);
        return null;
    }

    /**
     * 发布产品价格
     *
     * @return
     */
    public void releaseProductPrice() {
        long operateUser = getLoginUserId().longValue();
        logger.info("用户[{}]开始发布产品[{}]价格.", new Object[] {operateUser, productId});
        ResultMessage resultMessage = new ResultMessage();
        try {
            resultMessage = productDraftService.releaseProductPrice(productId, operateUser);
            if (!resultMessage.getIsSuccess()) {
                writeJson(resultMessage);
                return;
            }
            resultMessage.setIsSuccess(Boolean.TRUE);
            resultMessage.setMessage("价格发布成功！");
        } catch (Exception e) {
            logger.error("发布产品价格失败: ", e);
            resultMessage.setIsSuccess(Boolean.FALSE);
            resultMessage.setMessage("系统发生错误，发布失败，请稍后再试或联系管理员！");
        }

        if (resultMessage.getIsSuccess()) {
            try {
                // CMS上架接口
                cmsProductUpShelfService.productUpShelfByCms(Lists.newArrayList(productId.intValue()));
            } catch (Exception e1) {
                logger.error("CMS上架接口失败:", e1);
            }

            try {
                List<Long> skuIdList = productSkuService.selectSkuIdsByProductIdList(Lists.newArrayList(productId));
                if (skuIdList != null && !skuIdList.isEmpty()) {
                    updateSkuNotify(skuIdList);
                }
            } catch (Exception e2) {
                logger.error("推送MQ消息给搜索引擎失败{}。", e2);
            }

            try {
                // 保存日志
                List<ProductOperateLog> logList = Lists.newArrayList(new ProductOperateLog(productId,
                        operateUser, ProductOperateType.RELEASE_PRODUCT_PRICE.getStatus()));
                productOperateLogService.batchSaveProductOperateLog(logList);
            } catch (ServiceException e) {
                logger.error("批量保存日志失败，产品={},操作人={},错误信息：{}",
                        new Object[] {productId, operateUser, e});
            }
        }
        writeJson(resultMessage);
    }

    private void updateSkuNotify(List<Long> skuIdList) {
        productService.executeUpdateCachePrice(skuIdList);
        productService.changeProductInfoNotify(skuIdList, MsgOperation.ADD.getType());
        productService.updateCachePrice(skuIdList);
        productSkuService.updateProductSkuCache(skuIdList);
    }

    /**
     * 批量发布产品价格
     *
     * @return
     */
    public String batchReleaseProductPrice() {
        ResultMessage rmsg = new ResultMessage();
        Map jsonMap = new HashMap();
        long loginId = getLoginUserId().longValue();
        logger.info("用户[{}]开始批量发布产品[{}]价格.", new Object[] {loginId, productIds});
        try {
            rmsg = productDraftService.batchReleaseProductPrice(productIds, loginId);
            jsonMap.put("result", rmsg.getIsSuccess());
            jsonMap.put("msg", rmsg.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("result", false);
            jsonMap.put("msg", "批量发布价格失败!");
        }
        writeJson2(jsonMap);

        if (rmsg.getIsSuccess()) {
            try {
                // CMS上架接口
                List<Integer> productIdIntList = productIds.stream().map(id -> Integer.valueOf(id.intValue())).collect(Collectors.toList());
                cmsProductUpShelfService.productUpShelfByCms(productIdIntList);
            } catch (Exception e1) {
                e1.printStackTrace();
            }

            try {
                List<Long> skuIdList = productSkuService.selectSkuIdsByProductIdList(productIds);
                if (skuIdList != null && !skuIdList.isEmpty()) {
                    productService.executeUpdateCachePrice(skuIdList);
                    productService.changeProductInfoNotify(skuIdList, MsgOperation.ADD.getType());
                    productSkuService.updateProductSkuCache(skuIdList);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }

            try {
                // 保存日志
                List<ProductOperateLog> logList = productIds.stream()
                        .map(tempProductId -> new ProductOperateLog(tempProductId, loginId, ProductOperateType.RELEASE_PRODUCT_PRICE.getStatus()))
                        .collect(Collectors.toList());
                productOperateLogService.batchSaveProductOperateLog(logList);
            } catch (ServiceException e) {
                logger.error("批量保存日志失败，产品={},操作人={},错误信息：{}",
                        new Object[] {productId, loginId, e});
            }
        }

        return null;
    }

    /**
     * 查找是否有草稿表中是否有相同的记录
     *
     * @return
     */
    public void findSameProductFromDraft() {
        try {
            ProductDraft pd = productDraftService.findByProductIdWithOutClob(productId);
            if (pd == null) {
                strWriteJson("0");
                return;
            }
            if (DraftType.UPDATE.getStatus().equals(pd.getOpType())) {
                strWriteJson("1");
                return;
            }
            strWriteJson("2");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 产品预览
     * 
     * @return
     */
    public String previewProductDraftInfoPage() {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        String pageUrl = null;
        try {
            pageUrl = productDraftService.previewProductDraftInfoPage(getRequest().getParameter("id"));
            logger.info("产品预览地址:", pageUrl);
        } catch (Exception e) {
            logger.error("产品预览失败:", e);
        }

        jsonMap.put("pageUrl", pageUrl);
        writeJson(jsonMap);
        return null;
    }

    /**
     * 批量删除
     *
     * @return
     */
    public String batchDeleteProductDraft() {
        Map jsonMap = new HashMap();
        List<Long> productIdList = new ArrayList<Long>();
        logger.info("用户[{}]开始批量删除草稿产品[{}].",
                new Object[] {getLoginUserId(), Arrays.asList(upProductIds)});
        try {
            for (int i = 0; i < upProductIds.length; i++) {
                productIdList.add(Long.valueOf(upProductIds[i]));
            }
            ResultMessage rmsg = productDraftService.batchDeleteProductDraftByPId(productIdList);
            if (rmsg.getIsSuccess()) {
                jsonMap.put("result", true);
                jsonMap.put("msg", "商品删除成功!");
            } else {
                jsonMap.put("result", false);
                jsonMap.put("msg", "商品删除失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("result", false);
            jsonMap.put("msg", "商品删除失败!");
        }
        writeJson2(jsonMap);

        return null;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public ProductDraft getProduct() {
        return product;
    }

    public void setProduct(ProductDraft product) {
        this.product = product;
    }

    public List<OperationAttr> getOperationAttrList() {
        return operationAttrList;
    }

    public void setOperationAttrList(List<OperationAttr> operationAttrList) {
        this.operationAttrList = operationAttrList;
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

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public ProductDraft getProductForSelectPara() {
        return productForSelectPara;
    }

    public void setProductForSelectPara(ProductDraft productForSelectPara) {
        this.productForSelectPara = productForSelectPara;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRtnMessage() {
        return rtnMessage;
    }

    public void setRtnMessage(String rtnMessage) {
        this.rtnMessage = rtnMessage;
    }

    public String getDrugCategoryKey() {
        return drugCategoryKey;
    }

    public void setDrugCategoryKey(String drugCategoryKey) {
        this.drugCategoryKey = drugCategoryKey;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getReasonText() {
        return reasonText;
    }

    public void setReasonText(String reasonText) {
        this.reasonText = reasonText;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public String getToDeleteSkuIds() {
        return toDeleteSkuIds;
    }

    public void setToDeleteSkuIds(String toDeleteSkuIds) {
        this.toDeleteSkuIds = toDeleteSkuIds;
    }

    public String[] getOldSkuCode() {
        return oldSkuCode;
    }

    public void setOldSkuCode(String[] oldSkuCode) {
        this.oldSkuCode = oldSkuCode;
    }

    public String[] getProductIdChk() {
        return productIdChk;
    }

    public void setProductIdChk(String[] productIdChk) {
        this.productIdChk = productIdChk;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public Integer[] getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(Integer[] certificateType) {
        this.certificateType = certificateType;
    }

    public File[] getCertificateFiles() {
        return certificateFiles;
    }

    public void setCertificateFiles(File[] certificateFiles) {
        this.certificateFiles = certificateFiles;
    }

    public List<Long> getCertificateFileIds() {
        return certificateFileIds;
    }

    public void setCertificateFileIds(List<Long> certificateFileIds) {
        this.certificateFileIds = certificateFileIds;
    }

    public String[] getCertificateFilesFileName() {
        return certificateFilesFileName;
    }

    public void setCertificateFilesFileName(String[] certificateFilesFileName) {
        this.certificateFilesFileName = certificateFilesFileName;
    }

    public String[] getUpProductIds() {
        return upProductIds;
    }

    public void setUpProductIds(String[] upProductIds) {
        this.upProductIds = upProductIds;
    }

    public String[] getCkChannel() {
        return ckChannel;
    }

    public void setCkChannel(String[] ckChannel) {
        this.ckChannel = ckChannel;
    }

    public List<SkuCheckAttr> getSkuCheckAttrs() {
        return skuCheckAttrs;
    }

    public void setSkuCheckAttrs(List<SkuCheckAttr> skuCheckAttrs) {
        this.skuCheckAttrs = skuCheckAttrs;
    }

    public Long getbCategoryId() {
        return bCategoryId;
    }

    public void setbCategoryId(Long bCategoryId) {
        this.bCategoryId = bCategoryId;
    }

    public Long getmCategoryId() {
        return mCategoryId;
    }

    public void setmCategoryId(Long mCategoryId) {
        this.mCategoryId = mCategoryId;
    }

    public String getCertificateViewPath() {
        return certificateViewPath;
    }

    public void setCertificateViewPath(String certificateViewPath) {
        this.certificateViewPath = certificateViewPath;
    }
}
