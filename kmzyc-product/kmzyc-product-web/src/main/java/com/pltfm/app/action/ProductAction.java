package com.pltfm.app.action;

import com.google.common.collect.Lists;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.opensymphony.xwork2.Action;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.enums.CategoryStatus;
import com.pltfm.app.enums.MsgOperation;
import com.pltfm.app.enums.ProductOperateType;
import com.pltfm.app.enums.ProductStatus;
import com.pltfm.app.enums.ProductType;
import com.pltfm.app.maps.CertificateTypeMap;
import com.pltfm.app.maps.ProductOperateMaps;
import com.pltfm.app.service.CategoryService;
import com.pltfm.app.service.CmsProductUpShelfService;
import com.pltfm.app.service.ProductAttrService;
import com.pltfm.app.service.ProductCertificateService;
import com.pltfm.app.service.ProductDraftService;
import com.pltfm.app.service.ProductOperateLogService;
import com.pltfm.app.service.ProductPriceRecordService;
import com.pltfm.app.service.ProductService;
import com.pltfm.app.service.ProductSkuAttrService;
import com.pltfm.app.service.ProductSkuService;
import com.pltfm.app.service.ViewProductSkuService;
import com.pltfm.app.service.ViewSkuAttrService;
import com.pltfm.app.util.HtmlFilter;
import com.pltfm.app.util.ProductListType;
import com.pltfm.app.vobject.Category;
import com.pltfm.app.vobject.OperationAttr;
import com.pltfm.app.vobject.Product;
import com.pltfm.app.vobject.ProductAttr;
import com.pltfm.app.vobject.ProductCertificate;
import com.pltfm.app.vobject.ProductOperateLog;
import com.pltfm.app.vobject.ProductPriceRecord;
import com.pltfm.app.vobject.ProductSkuForExport;
import com.pltfm.app.vobject.ViewProductSku;
import com.pltfm.app.vobject.ViewSkuAttr;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

@Controller(value = "productAction")
@Scope(value = "prototype")
public class ProductAction extends BaseAction {

    Logger log = Logger.getLogger(ProductAction.class);

    private String certificateViewPath = ConfigurationUtil.getString("certificateFileViewPath");

    private Page page;

    private Product product;

    private Product productForSelectPara;

    private Long[] upProductId;

    private String[] upChannel;

    private String[] upStatus;

    private String checkedIntro;

    private List<ProductOperateLog> productOperateLogList;

    private List<ProductPriceRecord> recordList;

    @Resource
    private ProductOperateLogService productOperateLogService;

    @Resource
    private ProductPriceRecordService productPriceRecordService;

    @Resource
    private ProductService productService;

    @Resource
    private ProductDraftService productDraftService;

    @Resource
    private ProductSkuService productSkuService;

    @Resource
    private ProductAttrService productAttrService;

    @Resource
    private ProductCertificateService productCertificateService;

    @Resource
    private CmsProductUpShelfService cmsProductUpShelfService;

    @Resource
    private ProductSkuAttrService productSkuAttrService;

    @Resource
    private CategoryService categoryService;

    private List<OperationAttr> operationAttrList;

    private Integer dataType;

    private String drugCategoryKey;

    // 产品保存后的产品Id
    private Long productId;

    @Resource
    private ViewProductSkuService viewProductSkuService;

    @Resource
    private ViewSkuAttrService viewSkuAttrService;

    // 决定跳转哪个页面 price：跳转至价格显示页面
    private String type;

    private List<String> skuCheckedId;

    private List<String> oldskuCheckedId;

    // 要删除的SKU
    private String toDeleteSkuIds;

    private String isExsitProduct;

    // 批注信息
    private String postilStr;

    private String[] ckChannel;

    /**
     * 去上传产品图片
     * 
     * @return
     */
    public String toUploadSkuProductImage() {
        try {
            List<ViewProductSku> skuList = viewProductSkuService.findByProductId(productId);
            Map<ViewProductSku, List<ViewSkuAttr>> productSkuList = new LinkedHashMap();

            for (ViewProductSku v : skuList) {
                productSkuList.put(v, viewSkuAttrService.findBySkuId(v.getProductSkuId()));
            }
            super.getRequest().setAttribute("productSkuList", productSkuList);
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
            productForSelectPara = new Product();
        }
    }

    /**
     * 产品列表
     * 
     * @return
     */
    public String showList() {
        initPageAndProductForSelectPara();

        try {
            // 需要按SKU查询，要改
            productService.searchPage(page, productForSelectPara, type, getLoginUserId());
            // //查询列表初始化类目级联查询下拉框
            initShowCategoryList(productForSelectPara);
            setProductBrandMap();
            setProductStatusMap();
            // 产品类型map
            setProductTypeMap();
        } catch (Exception e) {
            logger.error("查询产品列表失败:", e);
            return ERROR;
        }

        if (ProductListType.PRICE.equals(type) || ProductListType.PRICE_SUCCESS.equals(type)
                || ProductListType.PRICE_FAIL_FROM_OFFICIAL.equals(type)) {
            return ProductListType.PRICE;
        }
        if (ProductListType.WEIGHT.equals(type) || ProductListType.WEIGHT_SUCCESS.equals(type)) {
            return ProductListType.WEIGHT;
        }

        return SUCCESS;
    }

    public String showPriceList() {
        initPageAndProductForSelectPara();

        try {
            // 需要按SKU查询，要改
            productService.searchPageByUserIdForPrice(page, productForSelectPara, type, getLoginUserId());
            // 查询列表初始化类目级联查询下拉框
            initShowCategoryList(productForSelectPara);
            setProductBrandMap();
            setProductStatusMap();
        } catch (Exception e) {
            logger.error("查询(showPriceList)列表失败:", e);
            return ERROR;
        }

        if (ProductListType.PRICE.equals(type) || ProductListType.PRICE_SUCCESS.equals(type)
                || ProductListType.PRICE_FAIL_FROM_OFFICIAL.equals(type)) {
            return ProductListType.PRICE;
        }

        return SUCCESS;
    }

    // 待审核产品列表
    public String auditProductShow() {
        initPageAndProductForSelectPara();

        try {
            if (StringUtils.isBlank(productForSelectPara.getStatus())) {
                // 待审核
                productForSelectPara.setStatus(ProductStatus.UNAUDIT.getStatus());
            }
            // 需要按SKU查询
            productService.searchPage(page, productForSelectPara, type, getLoginUserId());
            // 查询列表初始化类目级联查询下拉框
            initShowCategoryList(productForSelectPara);
            setProductBrandMap();
            setProductStatusByAuditMap();
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
        return SUCCESS;
    }

    public String viewProductForAudit() {
        try {
            productId = product.getId();
            setCheckedId(productId);
            product = productService.findProductById(productId);
            setCategoriesName(product.getCategoryId());
            setProductBrandMap();
            setApprovalTypeMap();

            List<ProductAttr> productAttrList = productService.getProductAttrList(productId);
            productService.setProductAttrValue(productAttrList);
            product.setProductAttrList(productAttrList);
            product.setProductSkus(productService.getProductSkuAttrList(productId));
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }

        setIsNoticeMap();
        setProductBrandMap();
        getBcategoryList(Long.valueOf(0));
        return SUCCESS;
    }

    /**
     * 到产品分类添加页
     * 
     * @return
     */
    public String toProductCategory() {
        getBcategoryList(Long.valueOf(0));
        getProductTypeMap();

        return SUCCESS;
    }

    public String toProductAdd() {
        try {
            Long categoryId = product.getCategoryId();
            setCategoriesName(categoryId);
            product.setCategoryName(getRequest().getAttribute("sCategoryName").toString());
            // 取类目属性列表
            product.setCategoryAttrList(productService.getCategoryAttrList(categoryId));
            // 取运营属性列表
            operationAttrList = productService.getOperationAttrList();
        } catch (Exception e) {
            log.error("添加产品前的操作失败，" + e.getMessage(), e);
            return ERROR;
        }

        setIsNoticeMap();
        // 产品分类
        getBcategoryList(Long.valueOf(0));
        getProductTypeMap();
        if (ProductType.OTC.getKey().equals(product.getProductType())) {
            getCertificateOTCMAP();
            getRequest().setAttribute("isOTC", true);
        }
        setApprovalTypeMap();
        return SUCCESS;
    }

    public String viewProduct() {
        try {
            Long id = product.getId();
            setCheckedId(id);
            product = productService.findProductById(id);
            setCategoriesName(product.getCategoryId());
            setProductBrandMap();
            setApprovalTypeMap();

            List<ProductAttr> productAttrList = productService.getProductAttrList(id);
            productService.setProductAttrValue(productAttrList);
            // 销售属性(sku属性),类目属性（基本属性）
            product.setProductAttrList(productAttrList);
            // sku列表
            product.setProductSkus(productService.getProductSkuAttrList(id));

            if (ProductType.OTC.getKey().equals(product.getProductType())) {
                List<ProductCertificate> certificateDraftList = productCertificateService.findByProductId(id);
                product.setCerfificateList(certificateDraftList);
                getCertificateOTCMAP();
            }
        } catch (Exception e) {
            logger.error("查看产品失败：", e);
            return ERROR;
        }

        getProductTypeMap();
        setIsNoticeMap();
        setProductBrandMap();
        getBcategoryList(Long.valueOf(0));
        return SUCCESS;
    }

    /**
     * 
     * 根据产品编号查看产品信息
     * 
     * @return
     */
    public String viewProductByProductNo() {

        return viewProduct();
    }

    public String toProductUpdate() {
        try {
            productId = product.getId();
            productDraftService.copyOfficialDataToDraft(productId, isExsitProduct);
            setCheckedId(productId);

            product = productService.findProductById(productId);
            setApprovalTypeMap();
            // 取产品属性
            List<ProductAttr> productAttrList = productService.getProductAttrList(productId);
            productService.setCategoryAttrValue(productAttrList);
            product.setProductAttrList(productAttrList);
            product.setProductSkus(productService.getProductSkuAttrList(productId));
            // 取运营属性列表
            operationAttrList = productService.getOperationAttrList();
            productService.setOperationAttrValue(productId, operationAttrList);

            setCategoriesId(product, product.getCategoryId());

            if (ProductType.OTC.getKey().equals(product.getProductType())) {
                List<ProductCertificate> certificateDraftList =
                        productCertificateService.findByProductId(productId);
                Map<Integer, String> map = null;
                if (ProductType.OTC.getKey().equals(product.getProductType())) {
                    getCertificateOTCMAP();
                    map = CertificateTypeMap.getOTCMAP();
                    getRequest().setAttribute("isOTC", true);
                }

                if (certificateDraftList != null && certificateDraftList.size() != 4) {
                    boolean flag = true;
                    for (Integer cerType : map.keySet()) {
                        flag = true;
                        for (ProductCertificate pcd : certificateDraftList) {
                            if (cerType.equals(pcd.getFileType())) {
                                flag = false;
                                break;
                            }
                        }
                        if (flag) {
                            ProductCertificate p = new ProductCertificate();
                            p.setFileType(cerType);
                            certificateDraftList.add(p);
                        }
                    }
                }
                product.setCerfificateList(certificateDraftList);
            }

            getRequest().setAttribute("skuNewAttrList", productSkuAttrService.findAndChangeSkuNewAttr(productId));
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }

        getProductTypeMap();
        setIsNoticeMap();
        setProductBrandMap();
        getBcategoryList(Long.valueOf(0));
        // 中类
        getMcategoryList(product.getBCategoryId());
        // 小类
        getScategoryList(product.getMCategoryId());
        return SUCCESS;
    }

    // 新增产品基础信息记录
    public String addProduct() throws Exception {
        try {
            product.setCreateUser(getLoginUserId());
            product.setCreateTime(new Date());
            product.setStatus(ProductStatus.DRAFT.getStatus());// 草稿
            this.productId = productService.insertProduct(product, skuCheckedId);
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
        return this.toUploadSkuProductImage();
    }

    // 修改产品信息
    public String updateProduct() {
        if (dataType == 7) {
            return showList();
        }
        String script = "";
        boolean flag = false;
        boolean bl = true;
        if (product != null) {
            product.setModifTime(new Date());
            product.setModifUser(getLoginUserId());
            logger.info("用户[{}]开始修改产品[{}]信息.", new Object[] {getLoginUserId(), product.getId()});
        }
        try {
            switch (dataType) {
                case 1:
                    productService.updateProductById(product);
                    script ="<script type='text/javascript' charset='utf-8' >alert('基本信息修改成功！');</script>";
                    break;
                case 2:
                    productService.updateCategoryAttrValue(product.getProductAttrList());
                    script ="<script type='text/javascript' charset='utf-8' >alert('基本属性修改成功！');</script>";
                    break;
                case 3:
                    if (!StringUtils.isEmpty(toDeleteSkuIds)) {
                        toDeleteSkuIds = toDeleteSkuIds.substring(0, toDeleteSkuIds.length() - 1);
                    }
                    productService.updateSkuAttrValue(product.getId(), product.getProductNo(),
                            product.getProductAttrList(), skuCheckedId, oldskuCheckedId,
                            toDeleteSkuIds);
                    script = "<script type='text/javascript' charset='utf-8' >alert('SKU规格修改成功！');</script>";
                    flag = true;
                    break;
                case 4:
                    productService.updateDefinitionAttrValue(product.getId(),
                            product.getProductAttrList());
                    script = "<script type='text/javascript' charset='utf-8' >alert('自定义属性修改成功！');</script>";
                    break;
                case 5:
                    productService.updateOperationAttrValue(product.getId(),
                            product.getOperationAttrIds());
                    script = "<script type='text/javascript' charset='utf-8' >alert('运营属性修改成功！');</script>";
                    break;
                case 6:
                    productService.updateProductById(product);
                    script = "<script type='text/javascript' charset='utf-8' >alert('商品介绍修改成功！');</script>";
                    break;
                default:
                    script = "<script type='text/javascript' charset='utf-8' >alert('无效类型！');</script>";
                    break;
            }

        } catch (Exception e) {
            bl = false;
            e.printStackTrace();
            script = "<script type='text/javascript' charset='utf-8' >alert('操作失败！');</script>";
        }
        if (bl) {
            Product p = new Product();
            p.setId(product.getId());
            if (p.getMarketPrice() != null) {
                p.setMarketPrice(new BigDecimal(0));
            }
            if (p.getCostPrice() != null) {
                p.setCostPrice(new BigDecimal(0));
            }
            p.setModifUser(getLoginUserId());
            p.setModifTime(new Date());
            p.setStatus(ProductStatus.DRAFT.getStatus());// 草稿
            try {
                productService.updateProductById(p);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        writeJson(script);
        if (flag) {
            return this.toUpdateProductSku();
        }

        return null;
    }

    public String toUpdateProductSku() {
        try {
            // 取产品属性
            List<ProductAttr> productAttrList = productService.getProductAttrList(product.getId());
            productService.setCategoryAttrValue(productAttrList);
            product.setProductAttrList(productAttrList);
            product.setProductSkus(productService.getProductSkuAttrList(product.getId()));
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
        return "skuList";
    }

    // 删除产品
    public String deleteProduct() {
        String id = getRequest().getParameter("id");
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        if (org.apache.commons.lang3.StringUtils.isBlank(id)) {
            jsonMap.put("result", Boolean.FALSE);
            jsonMap.put("msg", "产品id不能为空！");
            writeJson(jsonMap);
            return null;
        }
        logger.info("用户[{}]开始删除产品[{}].", new Object[] {getLoginUserId(), id});

        try {
            ResultMessage rmsg = productService.deleteProductById(Long.valueOf(id));
            jsonMap.put("result", rmsg.getIsSuccess());
            jsonMap.put("msg", rmsg.getMessage());
        } catch (Exception e) {
            logger.error("删除产品[{}]失败，失败信息：{}.", new Object[] {id, e});
            jsonMap.put("result", Boolean.FALSE);
            jsonMap.put("msg", "系统错误，删除失败!");
        }
        writeJson(jsonMap);
        return null;
    }

    // 提交审核产品
    public String submitTheAudit() {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        String id = getRequest().getParameter("id");
        if (org.apache.commons.lang3.StringUtils.isBlank(id)) {
            jsonMap.put("result", Boolean.FALSE);
            jsonMap.put("msg", "产品id不能为空！");
            writeJson(jsonMap);
            return null;
        }
        logger.info("用户[{}]开始提交审核产品[{}].", new Object[] {getLoginUserId(), id});

        try {
            boolean isSuccess = productService.submitTheAudit(Long.valueOf(id));
            String msg = null;
            if (isSuccess) {
                msg = "提交审核成功！";
            } else {
                msg = "提交审核失败！";
            }
            jsonMap.put("result", isSuccess);
            jsonMap.put("msg", msg);
        } catch (Exception e) {
            logger.error("提交审核产品[{}]失败，失败信息：{}.", new Object[] {id, e});
            return ERROR;
        }

        writeJson(jsonMap);
        return null;
    }

    // 审核产品
    public String auditProduct() {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        String id = getRequest().getParameter("id");
        if (org.apache.commons.lang3.StringUtils.isBlank(id)) {
            jsonMap.put("result", Boolean.FALSE);
            jsonMap.put("msg", "产品id不能为空！");
            writeJson(jsonMap);
            return null;
        }
        logger.info("用户[{}]开始审核产品[{}].", new Object[] {getLoginUserId(), id});

        try {
            boolean isSuccess = productService.auditProductById(Long.valueOf(id));
            String msg = null;
            if (isSuccess) {
                msg = "审核成功！";
            } else {
                msg = "审核失败！";
            }
            jsonMap.put("result", isSuccess);
            jsonMap.put("msg", msg);
        } catch (Exception e) {
            logger.error("审核产品[{}]失败，失败信息：{}.", new Object[] {id, e});
            return ERROR;
        }

        writeJson(jsonMap);
        return null;
    }

    // 批量审核产品
    public String batchAuditProduct() {
        ResultMessage message = null;
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        if (ArrayUtils.isEmpty(upProductId)) {
            jsonMap.put("result", Boolean.FALSE);
            jsonMap.put("msg", "产品id不能为空！");
            writeJson(jsonMap);
            return null;
        }

        List<Long> list = Lists.newArrayList(upProductId);
        logger.info("用户[{}]开始批量审核产品[{}].", new Object[] {getLoginUserId(), list});

        try {
            message = productService.batchAuditProduct(list);
            String msg = null;
            if (message.getIsSuccess()) {
                msg = "审核成功！";
            } else {
                msg = "审核失败！";
            }
            jsonMap.put("result", message.getIsSuccess());
            jsonMap.put("msg", msg);
        } catch (Exception e) {
            logger.error("批量审核产品[{}]失败，失败信息：{}.", new Object[] {list, e});
            jsonMap.put("result", false);
            jsonMap.put("msg", e.getMessage());
        }

        writeJson(jsonMap);
        return null;
    }

    /**
     * 产品上架
     */
    public String upShelf() {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        if (ArrayUtils.isEmpty(upProductId)) {
            jsonMap.put("result", Boolean.FALSE);
            jsonMap.put("msg", "产品id不能为空！");
            writeJson(jsonMap);
            return null;
        }

        Date upTime = new Date();
        Product prodTmp = null;
        List<Product> list = new ArrayList<Product>();
        for (int i = 0; i < upProductId.length; i++) {
            prodTmp = new Product();
            prodTmp.setId(upProductId[i]);
            prodTmp.setStatus(ProductStatus.UP.getStatus());
            prodTmp.setUpTime(upTime);
            list.add(prodTmp);
        }
        logger.info("用户[{}]开始上架产品[{}].", new Object[] {getLoginUserId(), Arrays.asList(upProductId)});
        ResultMessage resultMsg = null;
        try {
            // 上架之前需要判断价格是否添加，属性是否添加，SKU是否添加
            resultMsg = productService.upShelf(list);
            jsonMap.put("result", resultMsg.getIsSuccess());
            jsonMap.put("msg", resultMsg.getMessage());
        } catch (Exception e) {
            resultMsg = new ResultMessage();
            jsonMap.put("result", Boolean.FALSE);
            jsonMap.put("msg", "系统问题，上架失败!");
            logger.error("批量产品上架失败，list={},错误信息：{}", new Object[] {list, e});
        }

        writeJson(jsonMap);
        // 上架失败，直接返回
        if (!resultMsg.getIsSuccess()) {
            return null;
        }

        // 用于通知搜索引擎MQ消息的productId集合
        List<Long> ids = new ArrayList<Long>();
        // CMS通知产品ID集合
        List<Integer> cms_ids = new ArrayList<Integer>();
        for (Long id : upProductId) {
            ids.add(id);
            cms_ids.add(id.intValue());
        }

        try {
            // CMS上架接口
            cmsProductUpShelfService.productUpShelfByCms(cms_ids);
        } catch (Exception e1) {
            logger.error("CMS上架接口失败，产品upProductId={},错误信息：{}", new Object[] {upProductId, e1});
            return ERROR;
        }

        try {
            List<Long> skuIdList = productSkuService.selectSkuIdsByProductIdList(ids);
            if (CollectionUtils.isNotEmpty(skuIdList)) {
                productService.executeUpdateCachePrice(skuIdList);
                productService.changeProductInfoNotify(skuIdList, MsgOperation.ADD.getType());
                productService.updateCachePrice(skuIdList);
                productSkuService.updateProductSkuCache(skuIdList);
            }
        } catch (Exception e2) {
            logger.error("通知搜索引擎MQ消息失败，产品upProductId={},错误信息：{}", new Object[] {upProductId, e2});
            return ERROR;
        }

        // 保存日志
        List<ProductOperateLog> logList =
                Arrays.asList(upProductId).stream()
                        .map(tempProductId -> new ProductOperateLog(tempProductId, getLoginUserId().longValue(),
                                ProductOperateType.UP.getStatus()))
                        .collect(Collectors.toList());
        try {
            productOperateLogService.batchSaveProductOperateLog(logList);
        } catch (ServiceException e) {
            logger.error("批量保存日志失败，产品={},操作人={},错误信息：{}",
                    new Object[] {new ArrayList<>(Arrays.asList(upProductId)), getLoginUserId(), e});
        }
        return null;
    }

    /**
     * 产品下架
     */
    public String downShelf() {
        boolean haveDownSuccess = Boolean.FALSE;
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        Date archiveTime = new Date();
        Product prodTmp = null;
        List<Product> list = new ArrayList<Product>();
        for (Long id : upProductId) {
            prodTmp = new Product();
            prodTmp.setId(id);
            prodTmp.setStatus(ProductStatus.DOWN.getStatus());
            prodTmp.setArchiveTime(archiveTime);
            list.add(prodTmp);
        }
        logger.info("用户[{}]开始下架产品[{}].",
                new Object[] {getLoginUserId(), Arrays.asList(upProductId)});

        try {
            haveDownSuccess = productService.downShelf(list);
            jsonMap.put("result", haveDownSuccess);
            jsonMap.put("msg", haveDownSuccess ? "下架成功！" : "下架失败！");
            writeJson(jsonMap);
        } catch (Exception e) {
            logger.error("批量下架产品失败，产品upProductId={},错误信息：{}", new Object[] {upProductId, e});
            return ERROR;
        }

        if (!haveDownSuccess) {
            return null;
        }

        // 通知下架产品给搜索引擎
        List<Long> needDeleteProductIndexList = Lists.newArrayList(upProductId);

        try {
            List<Long> deleteSkuIdList = productSkuService.selectSkuIdsByProductIdList(needDeleteProductIndexList);
            productService.changeProductInfoNotify(deleteSkuIdList, MsgOperation.DELETE.getType());
            productSkuService.updateProductSkuCache(deleteSkuIdList);
        } catch (Exception e2) {
            logger.error("通知下架产品给搜索引擎失败，产品upProductId={},错误信息：{}", new Object[] {upProductId, e2});
            return ERROR;
        }

        // 保存日志
        List<ProductOperateLog> logList = Lists.newArrayList(upProductId).stream()
                        .map(tempProductId -> new ProductOperateLog(tempProductId, getLoginUserId()
                                .longValue(), ProductOperateType.DOWN.getStatus()))
                        .collect(Collectors.toList());
        try {
            productOperateLogService.batchSaveProductOperateLog(logList);
        } catch (ServiceException e) {
            logger.error("批量保存日志失败，产品={},操作人={},错误信息：{}",
                    new Object[] {Lists.newArrayList(upProductId), getLoginUserId(), e});
        }

        return null;
    }

    public String selectCategory() {
        Map jsonMap = new HashMap();
        Category category = new Category();
        category.setCategoryId(Long.valueOf(getRequest().getParameter("id")));
        category.setStatus(CategoryStatus.VALID.getStatus());

        try {
            if (category.getCategoryId() == 0) {// 如果是0,则返回空
                jsonMap.put("categoryList", null);
            } else {
                List<Category> categoryList = categoryService.queryCategoryChildrenList(category);
                jsonMap.put("categoryList", categoryList);
            }
            writeJson(jsonMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String checkProductName() {
        Map jsonMap = new HashMap();
        try {
            ResultMessage result = productService.checkProductName(getRequest().getParameter("name"));
            jsonMap.put("result", result.getIsSuccess());
            jsonMap.put("msg", result.getMessage());
            writeJson(jsonMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String previewProductInfoPage() {
        Map jsonMap = new HashMap();
        try {
            String skuId = getRequest().getParameter("skuId");
            String productId = getRequest().getParameter("id");
            if (StringUtils.isNotBlank(skuId)) {
                Long newProductId = productSkuService.queryProductSkuList(Long.valueOf(skuId)).getProductId();
                productId = newProductId.toString();
            }
            String pageUrl = productService.previewProductInfoPage(productId);
            log.info(new StringBuilder("产品预览地址:").append(pageUrl));
            jsonMap.put("pageUrl", pageUrl);
            writeJson(jsonMap);
        } catch (Exception e) {
            log.error(new StringBuilder("产品预览失败:").append(e));
            jsonMap.put("pageUrl", null);
            writeJson(jsonMap);
        }

        return null;
    }

    public String findProductSkuByProductId() {
        try {
            getRequest().setAttribute("productSkuList",
                    viewProductSkuService.findProductAndSkuAttrByProductId(productId));
            setProductStatusMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Action.SUCCESS;
    }

    /**
     * 过滤产品介绍的富文本，过滤掉标签以及特殊字符，进行校验
     * 
     * @return
     */
    public String checkIntroduce() throws Exception {
        boolean result;
        String msg = null;
        if (StringUtils.isNotEmpty(checkedIntro)) {
            HtmlFilter filter = new HtmlFilter();
            String a = filter.filterHtml(checkedIntro);
            String finalString = filter.filterEngAndChina(a);
            result = productService.checkIntro(finalString);
            if (result) {
                msg = "right";
                this.writeJson(msg);
            } else {
                msg = "wrong";
                this.writeJson(msg);
            }
        }
        return null;
    }

    /**
     * 导出
     * 
     * @return
     */
    public String exportProductInfo() {
        FileInputStream f = null;
        ByteArrayInputStream bais = null;
        try {
            initPageAndProductForSelectPara();
            List<ProductSkuForExport> exportList = productService.exportProductInfo(productForSelectPara, type);
            productService.exportExcelForProductSku(exportList);
            File file = new File(ConfigurationUtil.getString("exportExcelPath") + File.separator + "productinfo.xls");
            f = new FileInputStream(file);
            byte[] fb = new byte[f.available()];
            f.read(fb);
            ServletActionContext.getResponse().setHeader("Content-disposition",
                    "attachment; filename=" + new String("商品信息表.xls".getBytes("gb2312"), "iso8859-1"));
            bais = new ByteArrayInputStream(fb);
            int b;
            while ((b = bais.read()) != -1) {
                ServletActionContext.getResponse().getOutputStream().write(b);
            }
            ServletActionContext.getResponse().getOutputStream().flush();

            f.close();
            bais.close();
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        } finally {
            try {
                if (f != null) {
                    f.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (bais != null) {
                    bais.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 修改批注信息
     * 
     * @return
     */
    public String updateProductPostil() {
        String result = "-1";
        try {
            Product pr = new Product();
            pr.setId(productId);
            pr.setPostil(postilStr);
            productService.updateProductById(pr);
            result = "1";
        } catch (Exception e) {
            log.error("产品预览失败:", e);
        }
        writeJson(result);
        return null;
    }

    public String findCertificateFilesProduct() {
        try {
            initPageAndProductForSelectPara();

            // 需要按SKU查询，要改
            productService.searchCertificateProductPageByUserId(page, productForSelectPara, type, getLoginUserId());
            // 查询列表初始化类目级联查询下拉框
            initShowCategoryList(productForSelectPara);
            setProductBrandMap();
            setProductStatusMap();
            if ("OTC".equals(type)) {
                super.getCertificateOTCMAP();
            }

            getProductTypeMap();
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
        return Action.SUCCESS;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Page getPage() {
        return page;
    }

    public Long[] getUpProductId() {
        return upProductId;
    }

    public void setUpProductId(Long[] upProductId) {
        this.upProductId = upProductId;
    }

    public String[] getUpStatus() {
        return upStatus;
    }

    public void setUpStatus(String[] upStatus) {
        this.upStatus = upStatus;
    }

    public CategoryService getCategoryService() {
        return categoryService;
    }

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public List<OperationAttr> getOperationAttrList() {
        return operationAttrList;
    }

    public String[] getUpChannel() {
        return upChannel;
    }

    public void setOperationAttrList(List<OperationAttr> operationAttrList) {
        this.operationAttrList = operationAttrList;
    }

    public ProductAttrService getProductAttrService() {
        return productAttrService;
    }

    public void setProductAttrService(ProductAttrService productAttrService) {
        this.productAttrService = productAttrService;
    }

    public void setUpChannel(String[] upChannel) {
        this.upChannel = upChannel;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCheckedIntro() {
        return checkedIntro;
    }

    public void setCheckedIntro(String checkedIntro) {
        this.checkedIntro = checkedIntro;
    }

    public List<String> getSkuCheckedId() {
        return skuCheckedId;
    }

    public void setSkuCheckedId(List<String> skuCheckedId) {
        this.skuCheckedId = skuCheckedId;
    }

    public String getDrugCategoryKey() {
        return drugCategoryKey;
    }

    public void setDrugCategoryKey(String drugCategoryKey) {
        this.drugCategoryKey = drugCategoryKey;
    }

    public List<String> getOldskuCheckedId() {
        return oldskuCheckedId;
    }

    public void setOldskuCheckedId(List<String> oldskuCheckedId) {
        this.oldskuCheckedId = oldskuCheckedId;
    }

    public String getToDeleteSkuIds() {
        return toDeleteSkuIds;
    }

    public void setToDeleteSkuIds(String toDeleteSkuIds) {
        this.toDeleteSkuIds = toDeleteSkuIds;
    }

    public Product getProductForSelectPara() {
        return productForSelectPara;
    }

    public void setProductForSelectPara(Product productForSelectPara) {
        this.productForSelectPara = productForSelectPara;
    }

    public String getIsExsitProduct() {
        return isExsitProduct;
    }

    public void setIsExsitProduct(String isExsitProduct) {
        this.isExsitProduct = isExsitProduct;
    }

    public String getPostilStr() {
        return postilStr;
    }

    public void setPostilStr(String postilStr) {
        this.postilStr = postilStr;
    }

    public String[] getCkChannel() {
        return ckChannel;
    }

    public void setCkChannel(String[] ckChannel) {
        this.ckChannel = ckChannel;
    }

    public List<ProductOperateLog> getProductOperateLogList() {
        return productOperateLogList;
    }

    public void setProductOperateLogList(List<ProductOperateLog> productOperateLogList) {
        this.productOperateLogList = productOperateLogList;
    }

    public List<ProductPriceRecord> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<ProductPriceRecord> recordList) {
        this.recordList = recordList;
    }

    public String getCertificateViewPath() {
        return certificateViewPath;
    }

    public void setCertificateViewPath(String certificateViewPath) {
        this.certificateViewPath = certificateViewPath;
    }

    /**
     * 违规产品重新上架
     */
    public void illegalUp() {
        logger.info("用户[{}]开始对违规下架的产品[{}]上架.", new Object[] {getLoginUserId(), productId});
        ResultMessage resultMessage = null;
        if (product == null || product.getProductId() == null) {
            resultMessage = new ResultMessage();
            resultMessage.setMessage("违规产品重新上架失败,请先选择已违规下架的产品！");
        } else {
            resultMessage = productService.illegalUp(product);
        }

        writeJson(resultMessage);
        // 上架失败，直接返回
        if (!resultMessage.getIsSuccess()) {
            return;
        }

        Integer productId = product.getProductId();
        try {
            // CMS上架接口
            cmsProductUpShelfService.productUpShelfByCms(new ArrayList<Integer>(Arrays
                    .asList(new Integer[] {productId})));
        } catch (Exception e1) {
            logger.error("CMS上架接口失败，产品productId={},错误信息：{}", new Object[] {productId, e1});
            return;
        }

        try {
            List<Long> skuIdList = productSkuService.selectSkuIdsByProductIdList(Lists.newArrayList(Long.valueOf(productId)));
            if (CollectionUtils.isNotEmpty(skuIdList)) {
                productService.executeUpdateCachePrice(skuIdList);
                productService.changeProductInfoNotify(skuIdList, MsgOperation.ADD.getType());
                productService.updateCachePrice(skuIdList);
                productSkuService.updateProductSkuCache(skuIdList);
            }
        } catch (Exception e2) {
            logger.error("通知搜索引擎MQ消息失败，产品productId={},错误信息：{}", new Object[] {productId, e2});
        }

        // 保存日志
        ProductOperateLog log =
                new ProductOperateLog(productId.longValue(), getLoginUserId().longValue(),
                        ProductOperateType.UP.getStatus());
        try {
            productOperateLogService.saveProductOperateLog(log);
        } catch (ServiceException e) {
            logger.error("批量保存日志失败，产品={},操作人={},错误信息：{}", new Object[] {productId,
                    getLoginUserId(), e});
        }
    }

    /**
     * 违规产品下架
     */
    public void illegalDown() {
        ResultMessage resultMessage = null;
        logger.info("用户[{}]开始对产品[{}]违规下架.", new Object[] {getLoginUserId(), productId});
        if (product == null || product.getProductId() == null
                || StringUtils.isEmpty(product.getReasons())) {
            resultMessage = new ResultMessage();
            resultMessage.setMessage("违规产品下架失败,请先选择需违规下架的产品！");
        } else {
            resultMessage = productService.illegalDown(product);
        }

        writeJson(resultMessage);
        // 下架失败，直接返回
        if (!resultMessage.getIsSuccess()) {
            return;
        }

        Integer productId = product.getProductId();

        /*try {
            // CMS下架接口
            cmsProductDownShelfService.productDownShelfByCms(new ArrayList<Integer>(Arrays
                    .asList(new Integer[] {productId})));
        } catch (Exception e1) {
            logger.error("CMS下架接口失败，产品productId={},错误信息：{}", new Object[] {productId, e1});
            return;
        }*/

        try {
            // 通知下架产品给搜索引擎
            List<Long> deleteSkuIdList = productSkuService.selectSkuIdsByProductIdList(Lists.newArrayList(Long.valueOf(productId)));
            productService.changeProductInfoNotify(deleteSkuIdList, MsgOperation.DELETE.getType());
            productSkuService.updateProductSkuCache(deleteSkuIdList);
        } catch (Exception e2) {
            logger.error("通知下架产品给搜索引擎失败，产品productId={},错误信息：{}", new Object[] {productId, e2});
        }

        // 保存日志
        ProductOperateLog log =
                new ProductOperateLog(productId.longValue(), getLoginUserId().longValue(),
                        ProductOperateType.ILLEGAL_DOWN.getStatus());
        try {
            productOperateLogService.saveProductOperateLog(log);
        } catch (ServiceException e) {
            logger.error("批量保存日志失败，产品={},操作人={},错误信息：{}", new Object[] {productId,
                    getLoginUserId(), e});
        }
    }

    /**
     * 产品PV查询
     * 
     * @return
     */
    public String showPVList() {
        try {
            initPageAndProductForSelectPara();

            // 需要按SKU查询，要改
            productService.searchPageByUserIdForPV(page, productForSelectPara, type, getLoginUserId());
            // 查询列表初始化类目级联查询下拉框
            initShowCategoryList(productForSelectPara);
            setProductBrandMap();
            setProductPVSearchStatusMap();
        } catch (Exception e) {
            logger.error("查询(showPriceList)列表失败:", e);
            return ERROR;
        }

        if (ProductListType.PRICE.equals(type) || ProductListType.PRICE_SUCCESS.equals(type)
                || ProductListType.PRICE_FAIL_FROM_OFFICIAL.equals(type)) {
            return ProductListType.PRICE;
        }

        return SUCCESS;
    }

    public String showOperation() {
        if (productId == null) {
            return ERROR;
        }

        try {
            productOperateLogList = productOperateLogService.getProductOperateLogList(productId);
            recordList = productPriceRecordService.getRecordList(productId);
            getRequest().setAttribute("productOperateType", ProductOperateMaps.getMap());
        } catch (ServiceException e) {
            return ERROR;
        }
        return SUCCESS;
    }
}
