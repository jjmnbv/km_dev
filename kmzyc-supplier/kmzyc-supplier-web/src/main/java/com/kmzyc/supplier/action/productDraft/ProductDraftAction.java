package com.kmzyc.supplier.action.productDraft;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.km.framework.page.Pagination;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.supplier.action.SupplierBaseAction;
import com.kmzyc.supplier.maps.DraftTypeMap;
import com.kmzyc.supplier.model.ShopCategorys;
import com.kmzyc.supplier.model.ShopMain;
import com.kmzyc.supplier.model.SuppliersInfo;
import com.kmzyc.supplier.service.ProductDraftService;
import com.kmzyc.supplier.service.ProductDraftSkuService;
import com.kmzyc.supplier.service.ProductImageDraftService;
import com.kmzyc.supplier.service.ProductImageService;
import com.kmzyc.supplier.service.ShopCategoryService;
import com.kmzyc.supplier.service.ShopProductCategoryService;
import com.kmzyc.supplier.service.SupplierInfoService;
import com.kmzyc.supplier.vo.ProductDraftVo;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.opensymphony.xwork2.Action;
import com.pltfm.app.enums.DraftType;
import com.pltfm.app.enums.IsValidStatus;
import com.pltfm.app.enums.ProductAttrType;
import com.pltfm.app.enums.ProductStatus;
import com.pltfm.app.enums.ProductType;
import com.pltfm.app.fobject.SkuCheckAttr;
import com.pltfm.app.vobject.CategoryAttrValue;
import com.pltfm.app.vobject.OperationAttr;
import com.pltfm.app.vobject.ProductAttrDraft;
import com.pltfm.app.vobject.ProductDraft;
import com.pltfm.app.vobject.ProductImage;
import com.pltfm.app.vobject.ProductImageDraft;
import com.pltfm.app.vobject.ProductSkuDraft;
import com.pltfm.app.vobject.ViewProductSku;

@Controller("productDraftAction")
@Scope("prototype")
public class ProductDraftAction extends SupplierBaseAction {

    private Logger logger = LoggerFactory.getLogger(ProductDraftAction.class);

    @Resource
    private ProductDraftService productDraftService;

    @Resource
    private ProductDraftSkuService productDraftSkuService;

    @Resource
    private ShopProductCategoryService shopProductCategoryService;

    @Resource
    private SupplierInfoService supplierInfoService;

    @Resource
    private ProductImageDraftService productImageDraftService;

    @Resource
    private ProductImageService productImageService;

    @Resource
    private ShopCategoryService shopCategoryService;

    private ProductDraft product;

    private ProductDraftVo productDraftVo;

    private ViewProductSku viewProductSku;

    private ProductSkuDraft productSkuDraft;

    private String productId;

    private String productSkuId;

    private String rtnMessage;

    private String type;

    private List<SkuCheckAttr> skuCheckAttrs;

    private List<OperationAttr> operationAttrList;

    private Integer dataType;

    private String[] ckChannel;

    // 要删除的SKU
    private String toDeleteSkuIds;

    private List<String> oldskuCheckedId;

    // 添加的店内分类Id
    private String shopCategoryId;

    private SuppliersInfo suppliersInfo;

    // 产品状态
    private String auditStatus;

    // 图片预览时的绝对路径
    private String imagePath = ConfigurationUtil.getString("picturePreviewPath");

    // css路径
    private String cssPath = ConfigurationUtil.getString("CSS_JS_PATH");

    private String deleteImgIds;

    private List<String> exsitImgIds;

    // 供应商店铺的默认分类
    private ShopCategorys defaultShopCategory;

    private String defaultShopCateId;

    private String defaultShopCateName;

    private String shopcategoryIdForQuery; // 查询列表页的本店分类id

    public String showProductDraftList() {
        ShopMain shop = null;
        Map<Long, String> baseActionShopMainMap = new LinkedHashMap();
        Long shopId = null;
        Long supplierId = getSupplyId();
        try {
            Pagination page = getPagination(Constants.VIEW_PAGE, Integer.parseInt(getPageSize()));
            if (product == null) {
                product = new ProductDraft();
            }
            product.setOpTypes(Lists.newArrayList(DraftType.ADD.getStatus(), DraftType.UPDATE.getStatus(), DraftType.SAFE.getStatus()));
            product.setShopCode(supplierId.toString());

            List<ShopMain> list = getShopMainService().findAllShopMainBySupplierId(supplierId);
            if (CollectionUtils.isNotEmpty(list)) {
                shop = list.get(0);
                baseActionShopMainMap.put(shop.getShopId(), shop.getShopName());
                shopId = shop.getShopId();
                getRequest().setAttribute("shopName", shop.getShopName());
            } else {
                getRequest().setAttribute("shopName", "");
            }
            getRequest().setAttribute("shopMainMap", baseActionShopMainMap);

            if (StringUtils.isNotBlank(auditStatus)) {
                product.setStatus(auditStatus);
            } else {
                auditStatus = ProductStatus.AUDIT.getStatus();
                product.setStatus(auditStatus);
            }

            pagintion = productDraftService.searchPage(product, shopcategoryIdForQuery, page);
            suppliersInfo = supplierInfoService.selectByPrimaryKey(supplierId);// 查询供应商信息
        } catch (Exception e) {
            logger.error("商品发布列表查询错误：" + e.getMessage(), e);
            return ERROR;
        }

        getAllCategoryList(product.getBCategoryId(), product.getMCategoryId());
        setProductBrandMap();
        setProductStatusMap();
        setProductDraftStatusMap();
        getRequest().setAttribute("DraftTypeMap", DraftTypeMap.getMap());
        setShopCategoryForList(shopId);
        return SUCCESS;
    }

    // 价格与重量列表
    public String showPriceAndWeightList() {
        try {

            Pagination page = getPagination(Constants.VIEW_PAGE, Integer.parseInt(getPageSize()));
            if (product == null) {
                product = new ProductDraft();
            }
            product.setShopCode(getSupplyId().toString());
            pagintion = productDraftService.searchPage(product, null, page);
        } catch (Exception e) {
            logger.error("价格与重量列表查询错误：" + e.getMessage(), e);
            return ERROR;
        }
        setProductBrandMap();
        setDraftTypeMap();
        setProductDraftStatusMap();
        return SUCCESS;
    }

    public String findAllProductDraftMainSku() {
        if (viewProductSku == null) {
            viewProductSku = new ViewProductSku();
        }

        try {
            viewProductSku.setSkuStatus(IsValidStatus.VALID.getStatus());
            Pagination page = getPagination(Constants.VIEW_PAGE, Integer.parseInt(getPageSize()));
            pagintion = productDraftSkuService.searchPage(getSupplyId(), viewProductSku, page);
        } catch (Exception e) {
            logger.error("商品发布SKU列表查询错误：" + e.getMessage(), e);
            return ERROR;
        }

        setProductStatusMap();
        return Action.SUCCESS;
    }

    /**
     * 提交申请审核
     */
    public String submitDraftTheAudit() {
        ProductDraft pd = new ProductDraft();
        pd.setProductId(Long.valueOf(productId));
        pd.setStatus(ProductStatus.UNAUDIT.getStatus());

        try {
            productDraftService.updateObject(pd, "submitAudit");
            setRtnMessage("提交审核成功！");
        } catch (Exception e) {
            logger.error("提交申请审核错误：" + e.getMessage());
            setRtnMessage("系统发生错误,提交审核失败！");
        }
        type = "product";
        return showProductDraftList();
    }

    // 查看详情
    public String viewProductDraft() {
        if (type != null) {
            type = type.trim();
        }
        if (product == null) {
            product = new ProductDraft();
        }

        try {
            // 基础信息
            Long productId = product.getProductId();
            productDraftVo = productDraftService.findProductDraftVoByProductId(productId);
            // 获取属性列表
            List<ProductAttrDraft> productAttrList = productDraftService.getProductAttrDraftList(productId);

            // 目录基本属性
            List<ProductAttrDraft> cateAttrList = productAttrList.stream()
                    .filter(attr -> attr.getProductAttrType() == ProductAttrType.CATEGORY.getType().intValue() && attr.getIsSku() == 0)
                    .collect(Collectors.toList());
            getRequest().setAttribute("cateAttrList", cateAttrList);

            // 目录sku属性
            List<ProductAttrDraft> skuAttrList = productAttrList.stream()
                    .filter(attr -> attr.getProductAttrType() == ProductAttrType.CATEGORY.getType().intValue() && attr.getIsSku() == 1)
                    .collect(Collectors.toList());
            getRequest().setAttribute("skuAttrList", skuAttrList);

            // 自定义属性
            List<ProductAttrDraft> deniAttrList = productAttrList.stream()
                    .filter(attr -> attr.getProductAttrType() == ProductAttrType.DEFINITION.getType().intValue())
                    .collect(Collectors.toList());
            getRequest().setAttribute("deniAttrList", deniAttrList);

            // 运营属性
            List<ProductAttrDraft> opAttrList = productAttrList.stream()
                    .filter(attr -> attr.getProductAttrType() == ProductAttrType.OPERATION.getType().intValue())
                    .collect(Collectors.toList());
            getRequest().setAttribute("opAttrList", opAttrList);

            // sku规格列表
            getRequest().setAttribute("skuList",
                    productDraftService.findSingleProductAndSkusAndAttrValues(product).getProductSkuDrafts());
            // 获取其所属的店内分类
            Map<String, String> categoryMap = shopProductCategoryService.getProductShopCategory(productId, Boolean.FALSE);
            getRequest().setAttribute("shopCategoryName", categoryMap.get("shopCategoryName"));
        } catch (Exception e) {
            logger.error("商品信息详情查询错误：" + e.getMessage());
            return ERROR;
        }
        setProductBrandMap();
        setApprovalTypeMap();
        return SUCCESS;
    }

    public String toUpdateProdImageDraft() {
        return toEditSkuImages();
    }

    public String toEditSkuImages() {
        try {
            productSkuDraft = productDraftSkuService.findSingleProductSku(Long.valueOf(productSkuId));
            getRequest().setAttribute("imageList", productDraftSkuService.findAllBySkuId(Long.valueOf(productSkuId)));
        } catch (Exception e) {
            logger.error("商品图片查询错误：" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    public String toUpdateSkuDraftIntroduce() {
        try {
            productSkuDraft = productDraftSkuService.findSingleProductSku(Long.valueOf(productSkuId));
        } catch (Exception e) {
            logger.error("toUpdateSkuDraftIntroduce错误：" + e.getMessage(), e);
            return ERROR;
        }
        return Action.SUCCESS;
    }

    /**
     * 根据SkuId查找图片
     */
    public String findImageDraftBySkuId() {
        try {
            List<ProductImage> imageList = productDraftSkuService.findAllBySkuId(Long.valueOf(productSkuId));
            getRequest().setAttribute("imageList", imageList);
        } catch (Exception e) {
            logger.error("根据SkuId查找图片错误：" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 向草稿表插入数据
     */
    public String productAddDraft() {
        try {
            product.setCreateUser(getLoginUserId());
            product.setCreateTime(new Date());
            product.setModifTime(new Date());
            product.setProductType(ProductType.NOTDRUG.getKey());// 非药品
            product.setStatus(ProductStatus.DRAFT.getStatus());// 草稿
            product.setOpType(DraftType.ADD.getStatus());
            product.setPriceStatus(ProductStatus.DRAFT.getStatus());
            product.setImageStatus(ProductStatus.DRAFT.getStatus());
            product.setShopCode(getSupplyId().toString());

            Long pid = productDraftService.insertProduct(product, skuCheckAttrs, shopCategoryId);
            if (pid != null) {
                productId = pid.toString();
            }
            setRtnMessage("操作成功！");
            type = "add";
        } catch (Exception e) {
            logger.error("向草稿表插入数据错误：" + e.getMessage());
            return ERROR;
        }
        return toUploadSkuProductImage();
    }

    /**
     * 产品草稿更新
     */
    public String modifyProductDraft() {
        if (DraftType.UPDATE.getStatus().equals(product.getOpType())) {
            product.setStatus(ProductStatus.AUDIT.getStatus());
            product.setPriceStatus(ProductStatus.AUDIT.getStatus());
        }

        try {
            productDraftService.productDraftModify(dataType, getLoginUserId(), product, skuCheckAttrs,
                            oldskuCheckedId, toDeleteSkuIds, shopCategoryId);
            if ("formImage".equals(type)) {
                productId = product.getProductId().toString();
                toUploadSkuProductImage();
                return "addImage";
            }
            setRtnMessage("操作成功！");
        } catch (Exception e) {
            setRtnMessage("系统发生错误，操作失败！");
            logger.error("产品草稿更新失败：" + e.getMessage(), e);
        }
        type = "product";
        product = new ProductDraft();
        return showProductDraftList();
    }

    /**
     * 到产品分类添加页
     */
    public String toProductDraftCategory() {
        getBcategoryList(Long.valueOf(0));

        if (product != null) {
            // 中类
            Long bCategoryId = product.getBCategoryId();
            if (bCategoryId != null) {
                getMcategoryList(bCategoryId);
            }

            // 小类
            Long mCategoryId = product.getMCategoryId();
            if (mCategoryId != null) {
                getScategoryList(mCategoryId);
            }
        }
        return SUCCESS;
    }

    /**
     * 到产品商品添加页
     */
    public String toProductDraftAdd() {
        try {
            Long categoryId = product.getCategoryId();
            // 取类目属性列表---未排序只是按照sortno排序，sku的未有此字段
            product.setCategoryAttrList(productDraftService.getCategoryAttrList(categoryId));
            // 取运营属性列表
            operationAttrList = productDraftService.getOperationAttrList();
            setCategoriesName(categoryId);
            // 改成get请求后，如果没有查询到第一级目录，回到上一商品分类页面
            if (getRequest().getAttribute("bCategoryName") == null) {
                return "toCategory";
            }

            // 在此处判断是否有默认分类,没有就分配,有就不分配
            Long shopId = getSupplierShopId();
            boolean isHasShop = shopId != null;
            if (isHasShop) {
                // 在供应商尚未申请店铺的时候,屏蔽掉新增商品页面店内分类选择项通过
                Map<String, String> returnMap = shopCategoryService.addDefaultShopCategoryForShop(shopId, getLoginUserId());
                logger.info(shopId + "在审核完成后添加默认分类返回结果=" + returnMap.get("returnCode"));

                // 取店内分类
                setShopCategoryList();

                defaultShopCategory = getDefaultShopCategory(shopId);
                Map<String, String> resultMap = Maps.newHashMap();
                shopProductCategoryService.handleDefaultShopCategory(defaultShopCategory, Boolean.TRUE, resultMap);
                shopCategoryId = resultMap.get("shopCategoryId");
                defaultShopCateId = resultMap.get("defaultShopCateId");
                defaultShopCateName = resultMap.get("defaultShopCateName");
            }
            getRequest().setAttribute("isHasShop", isHasShop);
            getRequest().setAttribute("isExistShopCateCreateBySelf", getIsExistShopCateCreateBySelf(shopId));
        } catch (Exception e) {
            logger.error("到产品商品添加页错误：", e);
            return ERROR;
        }
        setProductBrandMap();
        return SUCCESS;
    }

    // 跳转修改页面
    public String toProductDraftUpdate() {
        try {
            if ("formImage".equals(type)) {
                List<ProductImageDraft> list = productImageDraftService.findByProductId(product.getProductId());
                if (CollectionUtils.isNotEmpty(list)) {
                    List<Long> imageIds = list.stream().map(draft -> draft.getImageId()).collect(Collectors.toList());
                    productImageDraftService.deleteImageBatchById(imageIds);
                }
            }
            Long productId = product.getProductId();
            product = productDraftService.findProductDraftVoByProductId(productId).getProduct();
            setApprovalTypeMap();
            // 取产品属性
            List<ProductAttrDraft> productAttrList = productDraftService.getProductAttrList(productId);
            // 设置目录属性radio,select,checkbox多item的值
            productDraftService.setCategoryAttrValue(productAttrList);
            List<CategoryAttrValue> categoryAttrValueList = new ArrayList();
            productAttrList.stream()
                    .filter(productArrDraft -> CollectionUtils.isEmpty(productArrDraft.getCategoryAttrValueList()))
                    .forEach(productArrDraft -> productArrDraft.setCategoryAttrValueList(categoryAttrValueList));
            // 基本属性，sku属性
            product.setProductAttrDraftList(productAttrList);
            // 设置sku列表
            product.setProductSkuDrafts(productDraftService.findSingleProductAndSkusAndAttrValues(product).getProductSkuDrafts());
            // 取运营属性列表
            operationAttrList = productDraftService.getOperationAttrList();
            productDraftService.setOperationAttrValue(productId, operationAttrList);
            Long categoryId = product.getCategoryId();
            setCategoriesName(categoryId);

            Long shopId = getSupplierShopId();
            boolean isHasShop = shopId != null;
            if (isHasShop) {
                Map<String, String> resultMap = shopProductCategoryService.getProductShopCategory(productId, Boolean.TRUE);

                //店内分类树
                setShopCategoryList();

                // 商品的店铺分类及其店铺默认分类
                defaultShopCategory = getDefaultShopCategory(shopId);
                shopProductCategoryService.handleDefaultShopCategory(defaultShopCategory, Boolean.FALSE, resultMap);
                defaultShopCateId = resultMap.get("defaultShopCateId");
                defaultShopCateName = resultMap.get("defaultShopCateName");
                shopCategoryId = resultMap.get("shopCategoryId");
                getRequest().setAttribute("shopCategoryName", resultMap.get("shopCategoryName"));
                getRequest().setAttribute("isExistShopCateCreateBySelf", getIsExistShopCateCreateBySelf(shopId));
            }
            getRequest().setAttribute("isHasShop", isHasShop);
            getRequest().setAttribute("skuNewAttrList", productDraftSkuService.findAndChangeSkuNewAttr(productId));
        } catch (Exception e) {
            logger.error("商品信息修改查询错误：" + e.getMessage());
            return ERROR;
        }

        setIsNoticeMap();
        setProductBrandMap();
        return SUCCESS;
    }

    /**
     * 单个产品的SKU以及SKU属性和属性值
     */
    public String findProductSkusAndAttrValues() {
        try {
            ProductDraft productDraft = new ProductDraft();
            productDraft.setProductId(Long.valueOf(productId));
            productDraft.setSkuStatus(IsValidStatus.VALID.getStatus());
            product = productDraftService.findSingleProductAndSkusAndAttrValues(productDraft);
        } catch (Exception e) {
            logger.error("单个产品的SKU以及SKU属性和属性值错误：" + e.getMessage(), e);
            return ERROR;
        }
        return type;
    }

    /**
     * 跳转上传图片页面
     */
    public String toUploadSkuProductImage() {
        ProductDraft conditionProduct = new ProductDraft();
        conditionProduct.setProductId(Long.valueOf(productId));

        try {
            product = productDraftService.findSingleProductAndSkusAndAttrValues(conditionProduct);
            List<ProductSkuDraft> skuList = product.getProductSkuDrafts();
            getRequest().setAttribute("productSkuList", skuList);
            getRequest().setAttribute("swfUploadCounts", skuList.size());
        } catch (Exception e) {
            logger.error("跳转上传图片页面失败：" + e.getMessage(), e);
        }

        return type;
    }

    /**
     * 新增时修改排序图片
     */
    public String updateSortImage() {
        try {
            updateExistsImage();

            ProductDraft pd = new ProductDraft();
            pd.setProductId(Long.valueOf(productId));
            pd.setStatus(auditStatus);
            pd.setPriceStatus(auditStatus);
            productDraftService.updateObject(pd, "submitAudit");
        } catch (Exception e) {
            logger.error("新增产品图片保存出错：", e);
        }
        return Action.SUCCESS;
    }

    /**
     * 修改图片
     */
    public String updateImage() {
        try {
            if (StringUtils.isNotBlank(deleteImgIds)) {
                String[] imgIds = deleteImgIds.substring(1).split(",");
                List<Long> imgIdsList = Arrays.stream(imgIds).map(imgId -> Long.valueOf(imgId)).collect(Collectors.toList());
                productImageDraftService.deleteImageBatchById(imgIdsList);
            }

            updateExistsImage();
        } catch (Exception e) {
            logger.error("修改图片失败：" + e.getMessage(), e);
        }

        return this.showProductDraftList();
    }

    /**
     * 修改已经存在的图片
     */
    private void updateExistsImage() {
        if (CollectionUtils.isNotEmpty(exsitImgIds)) {
            String skuId = "";
            int index = 0;
            ProductImageDraft pi = null;
            List<ProductImageDraft> piList = new ArrayList();

            for (String imgId : exsitImgIds) {
                String[] str = imgId.split("_");
                if (!skuId.equals(str[0])) {
                    skuId = str[0];
                    index = 0;
                }
                pi = new ProductImageDraft();
                pi.setImageId(Long.valueOf(str[1]));
                if (index == 0) {
                    pi.setIsDefault("0");
                } else {
                    pi.setIsDefault("1");
                }
                pi.setSortno((short) (++index));
                piList.add(pi);
            }

            if (CollectionUtils.isNotEmpty(piList)) {
                productImageService.updateProductImageBatch(piList);
            }
        }
    }

    public String toAuditProduct() {
        ProductDraft pd = new ProductDraft();
        pd.setProductId(Long.valueOf(productId));
        pd.setStatus(auditStatus);
        pd.setPriceStatus(auditStatus);
        pd.setToCheckTime(new Timestamp(System.currentTimeMillis()));
        try {
            productDraftService.updateObject(pd, "submitAudit");
        } catch (Exception e) {
            logger.error("修改已经存在的图片失败：" + e.getMessage(), e);
            return ERROR;
        }

        return Action.SUCCESS;
    }

    public ProductDraft getProduct() {
        return product;
    }

    public void setProduct(ProductDraft product) {
        this.product = product;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public ProductDraftVo getProductDraftVo() {
        return productDraftVo;
    }

    public void setProductDraftVo(ProductDraftVo productDraftVo) {
        this.productDraftVo = productDraftVo;
    }

    public ViewProductSku getViewProductSku() {
        return viewProductSku;
    }

    public void setViewProductSku(ViewProductSku viewProductSku) {
        this.viewProductSku = viewProductSku;
    }

    public ProductSkuDraft getProductSkuDraft() {
        return productSkuDraft;
    }

    public void setProductSkuDraft(ProductSkuDraft productSkuDraft) {
        this.productSkuDraft = productSkuDraft;
    }

    public String getProductSkuId() {
        return productSkuId;
    }

    public void setProductSkuId(String productSkuId) {
        this.productSkuId = productSkuId;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public List<OperationAttr> getOperationAttrList() {
        return operationAttrList;
    }

    public void setOperationAttrList(List<OperationAttr> operationAttrList) {
        this.operationAttrList = operationAttrList;
    }

    public List<SkuCheckAttr> getSkuCheckAttrs() {
        return skuCheckAttrs;
    }

    public void setSkuCheckAttrs(List<SkuCheckAttr> skuCheckAttrs) {
        this.skuCheckAttrs = skuCheckAttrs;
    }

    public String[] getCkChannel() {
        String[] temp = ckChannel;
        return temp;
    }

    public void setCkChannel(String[] ckChannel) {
        this.ckChannel = ckChannel;
    }

    public String getShopCategoryId() {
        return shopCategoryId;
    }

    public void setShopCategoryId(String shopCategoryId) {
        this.shopCategoryId = shopCategoryId;
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

    public SuppliersInfo getSuppliersInfo() {
        return suppliersInfo;
    }

    public void setSuppliersInfo(SuppliersInfo suppliersInfo) {
        this.suppliersInfo = suppliersInfo;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    @Override
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getCssPath() {
        return cssPath;
    }

    public void setCssPath(String cssPath) {
        this.cssPath = cssPath;
    }

    public String getDeleteImgIds() {
        return deleteImgIds;
    }

    public void setDeleteImgIds(String deleteImgIds) {
        this.deleteImgIds = deleteImgIds;
    }

    public List<String> getExsitImgIds() {
        return exsitImgIds;
    }

    public void setExsitImgIds(List<String> exsitImgIds) {
        this.exsitImgIds = exsitImgIds;
    }

    public ShopCategorys getDefaultShopCategory() {
        return defaultShopCategory;
    }

    public void setDefaultShopCategory(ShopCategorys defaultShopCategory) {
        this.defaultShopCategory = defaultShopCategory;
    }

    public String getDefaultShopCateId() {
        return defaultShopCateId;
    }

    public void setDefaultShopCateId(String defaultShopCateId) {
        this.defaultShopCateId = defaultShopCateId;
    }

    public String getDefaultShopCateName() {
        return defaultShopCateName;
    }

    public void setDefaultShopCateName(String defaultShopCateName) {
        this.defaultShopCateName = defaultShopCateName;
    }

    public String getShopcategoryIdForQuery() {
        return shopcategoryIdForQuery;
    }

    public void setShopcategoryIdForQuery(String shopcategoryIdForQuery) {
        this.shopcategoryIdForQuery = shopcategoryIdForQuery;
    }

}