package com.kmzyc.supplier.action.product;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.km.framework.page.Pagination;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.supplier.action.SupplierBaseAction;
import com.kmzyc.supplier.model.ShopCategorys;
import com.kmzyc.supplier.model.SuppliersInfo;
import com.kmzyc.supplier.service.ProductImageService;
import com.kmzyc.supplier.service.ProductMainService;
import com.kmzyc.supplier.service.ProductSkuService;
import com.kmzyc.supplier.service.ShopProductCategoryService;
import com.kmzyc.supplier.service.SupplierInfoService;
import com.kmzyc.supplier.vo.ProductMainVo;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.opensymphony.xwork2.Action;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.enums.IsValidStatus;
import com.pltfm.app.enums.ProductStatus;
import com.pltfm.app.fobject.SkuCheckAttr;
import com.pltfm.app.maps.YesOrNoMap;
import com.pltfm.app.vobject.OperationAttr;
import com.pltfm.app.vobject.Product;
import com.pltfm.app.vobject.ProductAttr;
import com.pltfm.app.vobject.ProductImage;
import com.pltfm.app.vobject.ProductSku;
import com.pltfm.app.vobject.ViewProductSku;

@Controller("productMainAction")
@Scope("prototype")
public class ProductMainAction extends SupplierBaseAction {

    private Logger logger = LoggerFactory.getLogger(ProductMainAction.class);

    @Resource
    private ProductMainService productMainService;

    @Resource
    private ProductSkuService productSkuService;

    @Resource
    private ShopProductCategoryService shopProductCategoryService;

    @Resource
    private SupplierInfoService supplierInfoService;

    @Resource
    private ProductImageService productImageService;

    private Product product;

    private ProductMainVo productMainVo;

    private ViewProductSku viewProductSku;

    private List<OperationAttr> operationAttrList;

    private String isExsitProduct;

    private String productSkuId;

    private String productId;

    private String type;

    private SuppliersInfo suppliersInfo;

    private Map<Long, String> skuImagesMap;

    private Map<Long, String> productDefaultImgMap;

    private Map<Long, Double> productDefaultPriceMap;

    private Map<Long, Long> productDefaultStockMap;

    private List<SkuCheckAttr> skuCheckAttrs;

    // 要删除的SKU
    private String toDeleteSkuIds;

    private List<String> oldskuCheckedId;

    private Integer dataType;

    // css路径
    private String cssPath = ConfigurationUtil.getString("CSS_JS_PATH");

    // 图片预览时的绝对路径
    private String imagePath = ConfigurationUtil.getString("picturePreviewPath");

    private String deleteImgIds;

    private List<String> exsitImgIds;

    private String auditStatus;

    // 供应商店铺的默认分类
    private ShopCategorys defaultShopCategory;

    private String defaultShopCateId;

    private String defaultShopCateName;

    private String shopcategoryIdForQuery; // 查询列表页的本店分类id

    private String rtnMessage;

    /**
     * 存储店内分类id
     */
    private String shopCategoryId;

    public String showList() {
        Pagination page = getPagination(Constants.VIEW_PAGE, Integer.parseInt(getPageSize()));
        if (product == null) {
            product = new Product();
        }
        Long supplierId = getSupplyId();
        product.setShopCode(supplierId.toString());

        if (StringUtils.isNotBlank(auditStatus)) {
            product.setStatus(auditStatus);
        } else {
            product.setStatus(ProductStatus.UP.getStatus());//出售中的商品状态：3：已上架
        }

        try {
            pagintion = productMainService.searchPage(product, shopcategoryIdForQuery, page);
            suppliersInfo = supplierInfoService.selectByPrimaryKey(supplierId);
        } catch (Exception e) {
            logger.error("商品发布列表查询错误：" + e.getMessage(), e);
            return ERROR;
        }

        setProductStatusMap();
        setShopCategoryForList(null);
        return SUCCESS;
    }

    public String showPriceList() {
        Pagination page = getPagination(Constants.VIEW_PAGE, Integer.parseInt(getPageSize()));
        if (product == null) {
            product = new Product();
        }
        product.setShopCode(getSupplyId().toString());

        try {
            pagintion = productMainService.searchPage(product, null, page);
            getAllCategoryList(product.getBCategoryId(), product.getMCategoryId());
        } catch (Exception e) {
            logger.error("商品价格与重量列表查询错误：" + e.getMessage());
            return ERROR;
        }
        setProductBrandMap();
        setProductStatusMap();
        setApprovalTypeMap();
        return SUCCESS;
    }

    public String viewProduct() {
        if (product == null) {
            product = new Product();
        }
        Long productId = product.getId();

        try {
            productMainVo = productMainService.findProductMainVoByProductId(productId);
            List<ProductAttr> productAttrList = productMainService.getProductAttrList(productId);
            productMainService.setProductAttrValue(productAttrList);
            // 设置目录属性含（按顺序排序：sku属性，基本属性，运营属性，自定义属性）
            productMainVo.getProduct().setProductAttrList(productAttrList);
            // sku规格列表
            productMainVo.getProduct().setProductSkus(productMainService.getProductSkuAttrList(productId));
            // 获取其所属的店内分类
            Map<String, String> categoryMap = shopProductCategoryService.getProductShopCategory(productId, Boolean.FALSE);
            getRequest().setAttribute("shopCategoryName", categoryMap.get("shopCategoryName"));

            getRequest().setAttribute("shopId", getRequest().getParameter("shopId"));
            getRequest().setAttribute("showType", getRequest().getParameter("showType"));
            // 新增的sku规格
            getRequest().setAttribute("skuNewAttrList", productSkuService.findAndChangeSkuNewAttr(productId));
        } catch (Exception e) {
            logger.error("商品信息查询错误：" , e);
            return ERROR;
        }
        setApprovalTypeMap();
        setProductBrandMap();
        return SUCCESS;
    }

    public String toProductUpdate() {
        try {
            Long productId = product.getId();
            product = productMainService.findProductById(productId);
            setApprovalTypeMap();
            // 取产品属性
            List<ProductAttr> productAttrList = productMainService.getProductAttrList(productId);
            productMainService.setCategoryAttrValue(productAttrList);
            product.setProductAttrList(productAttrList);
            // sku列表
            product.setProductSkus(productMainService.getProductSkuAttrList(productId));
            // 取运营属性列表
            operationAttrList = productMainService.getOperationAttrList();
            productMainService.setOperationAttrValue(productId, operationAttrList);
            Long categoryId = product.getCategoryId();
            setCategoriesName(categoryId);
            // 店内分类
            Long shopId = getSupplierShopId();
            boolean isHasShop = shopId != null;
            if (isHasShop) {
                //店内分类树
                setShopCategoryList();

                Map<String, String> categoryMap = shopProductCategoryService.getProductShopCategory(productId, Boolean.TRUE);

                // 商品的店铺分类及其店铺默认分类
                defaultShopCategory = getDefaultShopCategory(shopId);
                shopProductCategoryService.handleDefaultShopCategory(defaultShopCategory, Boolean.FALSE, categoryMap);
                defaultShopCateId = categoryMap.get("defaultShopCateId");
                defaultShopCateName = categoryMap.get("defaultShopCateName");
                shopCategoryId = categoryMap.get("shopCategoryId");
                getRequest().setAttribute("shopCategoryName", categoryMap.get("shopCategoryName"));
                getRequest().setAttribute("isExistShopCateCreateBySelf", getIsExistShopCateCreateBySelf(shopId));
            }
            getRequest().setAttribute("isHasShop", isHasShop);
            getRequest().setAttribute("skuNewAttrList", productSkuService.findAndChangeSkuNewAttr(productId));
        } catch (Exception e) {
            logger.error("商品信息修改查询错误：" , e);
            return ERROR;
        }

        setIsNoticeMap();
        setProductBrandMap();
        return SUCCESS;
    }

    public String productUpdate() {
        ResultMessage resultMessage = new ResultMessage();
        try {
            productMainService.productUpdate(getSupplyId(), getLoginUserId(), product,
                    skuCheckAttrs, oldskuCheckedId, toDeleteSkuIds, shopCategoryId);

            // 有新增sku
            if (CollectionUtils.isNotEmpty(skuCheckAttrs)) {
                resultMessage.setMark(2);
                productId = product.getId().toString();
                type = "productUpdate";
            } else if (ProductStatus.UP.getStatus().equals(product.getStatus())) {
                productMainService.productUpShelf(Lists.newArrayList(product));
            }
            resultMessage.setIsSuccess(Boolean.TRUE);
            resultMessage.setMessage("操作成功！");
        } catch (Exception e) {
            logger.error("商品更新错误：" , e);
            resultMessage.setMessage("系统发生错误，请重新操作！");
        }

        writeJson(resultMessage);
        return null;
    }

    public String findAllProductMainSku() {
        if (viewProductSku == null) {
            viewProductSku = new ViewProductSku();
        }
        Pagination page = getPagination(Constants.VIEW_PAGE, Integer.parseInt(getPageSize()));
        viewProductSku.setSkuStatus(IsValidStatus.VALID.getStatus());

        try {
            pagintion = productSkuService.searchPage(getSupplyId(), viewProductSku, page);
        } catch (Exception e) {
            logger.error("商品发布SKU列表查询错误：" , e);
            return ERROR;
        }

        setProductStatusMap();
        return Action.SUCCESS;
    }

    public String findAllProductSkuForFreight() {
        if (viewProductSku == null) {
            viewProductSku = new ViewProductSku();
        }
        Pagination page = getPagination(Constants.VIEW_PAGE, Integer.parseInt(getPageSize()));
        viewProductSku.setSkuStatus(IsValidStatus.VALID.getStatus());

        try {
            pagintion = productSkuService.searchForFreightPage(getSupplyId(), viewProductSku, page);
            getRequest().setAttribute("freeStatusMap", YesOrNoMap.getMap());
        } catch (Exception e) {
            logger.error("商品运费SKU列表查询错误：", e);
            return ERROR;
        }

        setProductStatusMap();
        return Action.SUCCESS;
    }

    /**
     * 更新图片
     * 
     * @return
     */
    public String toUploadImage() {
        Product p = new Product();
        p.setId(Long.valueOf(productId));

        try {
            List<ProductSku> skuList = productMainService.findSingleProductAndSkusAndAttrValuesAndImages(p);
            getRequest().setAttribute("skuList", skuList);
        } catch (Exception e) {
            logger.error("商品查询错误：", e);
            return ERROR;
        }

        return SUCCESS;
    }

    /**
     * 根据SkuId查找图片
     * 
     * @return
     */
    public String findProductImagesBySkuId() {
        try {
            List<ProductImage> imageList = productSkuService.findAllBySkuId(Long.valueOf(productSkuId));
            getRequest().setAttribute("imageList", imageList);
        } catch (Exception e) {
            logger.error("商品图片查询错误：", e);
            return ERROR;
        }

        return SUCCESS;
    }

    public String findSingleProduct() {
        try {
            List<ViewProductSku> productSkuList = productSkuService.findProductAndSkuAttrByProductId(Long.valueOf(productId));
            getRequest().setAttribute("productSkuList", productSkuList);
        } catch (Exception e) {
            logger.error("商品价格与重量查询错误：", e);
            return ERROR;
        }

        return SUCCESS;
    }

    public String toModifySkuFreight() {
        try {
            viewProductSku = productSkuService.findSingleSkuAndAttr(Long.valueOf(productSkuId));
            getRequest().setAttribute("freeStatusMap", YesOrNoMap.getMap());
        } catch (Exception e) {
            logger.error("去更新sku重量失败：" + e.getMessage(), e);
            return ERROR;
        }

        return SUCCESS;
    }

    public String saveImage() {
        try {
            if (StringUtils.isNotBlank(deleteImgIds)) {
                List<Long> imgIdsList = Lists.newArrayList(StringUtils.split(deleteImgIds, ","))
                        .stream().map(imgId -> Long.valueOf(imgId))
                        .collect(Collectors.toList());
                productImageService.deleteImageWithFileByImageIds(imgIdsList);
            }

            if (CollectionUtils.isNotEmpty(exsitImgIds)) {
                String skuId = "";
                int index = 0;
                ProductImage pi = null;
                List<ProductImage> needUpdateList = new ArrayList();
                for (String imgId : exsitImgIds) {
                    String[] str = imgId.split("_");
                    if (!skuId.equals(str[0])) {
                        skuId = str[0];
                        index = 0;
                    }
                    pi = new ProductImage();
                    pi.setImageId(Long.valueOf(str[1]));
                    if (index == 0) {
                        pi.setIsDefault("0");
                    } else {
                        pi.setIsDefault("1");
                    }
                    pi.setSortno((short) (++index));
                    needUpdateList.add(pi);
                }

                if (CollectionUtils.isNotEmpty(needUpdateList)) {
                    productImageService.updateImageBatch(needUpdateList);
                }
            }

            if (ProductStatus.UP.getStatus().equals(auditStatus)) {
                Product p = new Product();
                p.setId(Long.valueOf(productId));
                p.setStatus(ProductStatus.UP.getStatus());
                p.setUpTime(new Date());
                productMainService.productUpShelf(Lists.newArrayList(p));
            }
        } catch (Exception e) {
            logger.error("保存图片失败，", e);
            return ERROR;
        }
        return showList();
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductMainVo getProductMainVo() {
        return productMainVo;
    }

    public void setProductMainVo(ProductMainVo productMainVo) {
        this.productMainVo = productMainVo;
    }

    public ViewProductSku getViewProductSku() {
        return viewProductSku;
    }

    public void setViewProductSku(ViewProductSku viewProductSku) {
        this.viewProductSku = viewProductSku;
    }

    public String getIsExsitProduct() {
        return isExsitProduct;
    }

    public void setIsExsitProduct(String isExsitProduct) {
        this.isExsitProduct = isExsitProduct;
    }

    public List<OperationAttr> getOperationAttrList() {
        return operationAttrList;
    }

    public void setOperationAttrList(List<OperationAttr> operationAttrList) {
        this.operationAttrList = operationAttrList;
    }

    public String getProductSkuId() {
        return productSkuId;
    }

    public void setProductSkuId(String productSkuId) {
        this.productSkuId = productSkuId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public HashMap<Long, String> getSkuImagesMap() {
        return (HashMap<Long, String>) skuImagesMap;
    }

    public void setSkuImagesMap(HashMap<Long, String> skuImagesMap) {
        this.skuImagesMap = skuImagesMap;
    }

    public Map<Long, String> getProductDefaultImgMap() {
        return productDefaultImgMap;
    }

    public void setProductDefaultImgMap(Map<Long, String> productDefaultImgMap) {
        this.productDefaultImgMap = productDefaultImgMap;
    }

    public Map<Long, Double> getProductDefaultPriceMap() {
        return productDefaultPriceMap;
    }

    public void setProductDefaultPriceMap(Map<Long, Double> productDefaultPriceMap) {
        this.productDefaultPriceMap = productDefaultPriceMap;
    }

    public Map<Long, Long> getProductDefaultStockMap() {
        return productDefaultStockMap;
    }

    public void setProductDefaultStockMap(Map<Long, Long> productDefaultStockMap) {
        this.productDefaultStockMap = productDefaultStockMap;
    }

    public List<SkuCheckAttr> getSkuCheckAttrs() {
        return skuCheckAttrs;
    }

    public void setSkuCheckAttrs(List<SkuCheckAttr> skuCheckAttrs) {
        this.skuCheckAttrs = skuCheckAttrs;
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

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public String getCssPath() {
        return cssPath;
    }

    public void setCssPath(String cssPath) {
        this.cssPath = cssPath;
    }

    @Override
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
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

    public String getRtnMessage() {
        return rtnMessage;
    }

    public void setRtnMessage(String rtnMessage) {
        this.rtnMessage = rtnMessage;
    }

    public String getShopcategoryIdForQuery() {
        return shopcategoryIdForQuery;
    }

    public void setShopcategoryIdForQuery(String shopcategoryIdForQuery) {
        this.shopcategoryIdForQuery = shopcategoryIdForQuery;
    }

}
