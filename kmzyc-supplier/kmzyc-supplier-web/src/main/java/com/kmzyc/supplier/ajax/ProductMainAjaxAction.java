package com.kmzyc.supplier.ajax;

import com.kmzyc.supplier.model.SuppliersInfo;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.kmzyc.supplier.action.SupplierBaseAction;
import com.kmzyc.supplier.service.CategoryService;
import com.kmzyc.supplier.service.ProductDraftService;
import com.kmzyc.supplier.service.ProductMainService;
import com.kmzyc.supplier.service.ProductSkuService;
import com.kmzyc.supplier.service.SupplierInfoService;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.enums.DraftType;
import com.pltfm.app.enums.ProductStatus;
import com.pltfm.app.vobject.Category;
import com.pltfm.app.vobject.Product;
import com.pltfm.app.vobject.ProductDraft;
import com.pltfm.app.vobject.ProductSku;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

@Controller("productMainAjaxAction")
@Scope("prototype")
public class ProductMainAjaxAction extends SupplierBaseAction {

    private static Logger logger = LoggerFactory.getLogger(ProductMainAjaxAction.class);

    @Resource
    private ProductMainService productMainService;

    @Resource
    private ProductDraftService productDraftService;

    @Resource
    private ProductSkuService productSkuService;

    @Resource
    private CategoryService categoryService;

    @Resource
    private SupplierInfoService supplierInfoService;

    private String[] productIds;

    private String productId;

    private Long productSkuId;

    private String freeStatus;

    private Double freight;

    private SuppliersInfo suppliersInfo;

    public String downShelf() {
        Map<String, Object> jsonMap = new HashMap();
        List<Product> list = Arrays.stream(productIds).map(id -> {
            Product product = new Product();
            product.setId(Long.valueOf(id));
            product.setStatus(ProductStatus.DOWN.getStatus());
            product.setArchiveTime(new Date());
            return product;
        }).collect(Collectors.toList());

        try {
            boolean haveDownSuccess = productMainService.productDownShelf(list);
            jsonMap.put("flag", haveDownSuccess);
            jsonMap.put("msg", haveDownSuccess ? "下架成功！" : "下架失败！");
        } catch (Exception e) {
            logger.error("下架失败,", e);
            jsonMap.put("flag", false);
            jsonMap.put("msg", "下架失败!");
        }

        writeJson(jsonMap);
        return null;
    }

    public String upShelf() {
        Map<String, Object> jsonMap = new HashMap();
        List<Product> list = Arrays.stream(productIds).map(id -> {
            Product product = new Product();
            product.setId(Long.valueOf(id));
            product.setStatus(ProductStatus.UP.getStatus());
            product.setUpTime(new Date());
            return product;
        }).collect(Collectors.toList());

        try {
            suppliersInfo = supplierInfoService.selectByPrimaryKey(getSupplyId());//查询供应商信息
            //如果关闭了供应商业务此供应商不能上架操作
            if (suppliersInfo == null || suppliersInfo.getBusinessStatus() == 1) {
                jsonMap.put("flag", false);
                jsonMap.put("msg", "您已经被禁用此操作!");
                writeJson(jsonMap);
                return null;
            }

            ResultMessage result = productMainService.productUpShelf(list);
            jsonMap.put("flag", result.getIsSuccess());
            jsonMap.put("msg", result.getIsSuccess() ? "上架成功！" : result.getMessage());
        } catch (Exception e) {
            logger.error("上架失败,", e);
            jsonMap.put("flag", false);
            jsonMap.put("msg", "上架失败!");
        }

        writeJson(jsonMap);
        return null;
    }

    public String deleteProduct() {
        Map<String, Object> jsonMap = new HashMap();
        List<Product> list = Arrays.stream(productIds).map(id -> {
            Product product = new Product();
            product.setId(Long.valueOf(id));
            product.setStatus(ProductStatus.DELETE.getStatus());
            return product;
        }).collect(Collectors.toList());

        try {
            if (CollectionUtils.isNotEmpty(list)) {
                productMainService.updateProductBatch(list);
            }
            jsonMap.put("flag", true);
            jsonMap.put("msg", "操作成功！");
        } catch (Exception e) {
            logger.error("删除商品失败：", e);
            jsonMap.put("flag", false);
            jsonMap.put("msg", "删除失败！");
        }

        writeJson(jsonMap);
        return null;
    }

    /**
     * 查找是否有草稿表中是否有相同的记录
     *
     * @return
     */
    public String findSameProductFromDraft() {
        try {
            ProductDraft pd = productDraftService.findByProductIdWithOutClob(Long.valueOf(productId));
            String result = "";
            if (pd == null) {
                result = "0";
            } else if (DraftType.UPDATE.getStatus().equals(pd.getOpType())) {
                result = "1";
            } else {
                result = "2";
            }
            writeJson(result);
        } catch (Exception e) {
            logger.error("查找是否有草稿表中是否有相同的记录失败，", e);
        }

        return null;
    }

    public String selectCategory() {
        //TODO Id null字符串
        Map jsonMap = new HashMap();
        Category category = new Category();
        category.setCategoryId(Long.valueOf(getRequest().getParameter("id")));
        try {
            if (category.getCategoryId() == 0) {//如果是0,则返回空
                jsonMap.put("categoryList", null);
            } else {
                List<Category> categoryList = categoryService.queryCategoryChildrenList(category, null);
                jsonMap.put("categoryList", categoryList);
            }
            writeJson(jsonMap);
        } catch (Exception e) {
            logger.error("selectCategory失败，", e);
        }

        return null;
    }

    /**
     * 选择供应商二级目录
     * <note>
     *      应二级目录佣金修改
     * </note>
     *
     * @return
     */
    public String selectSecondCategoryWithSupplyAvailable() {
        Map<String, Object> jsonMap = new HashMap();
        Map<String, Object> param = new HashMap();
        String id = getRequest().getParameter("id");
        if (StringUtils.isEmpty(id)) {
            logger.error("选择供应商二级目录失败，没有提供一级目录id。");
            return null;
        }

        param.put("parentId", Long.valueOf(id));
        param.put("supplierId", getSupplyId());
        try {
            jsonMap.put("categoryList", categoryService.selectCategoryWithSupplyAvailable(param));
            writeJson(jsonMap);
        } catch (Exception e) {
            logger.error("选择供应商二级目录失败，", e);
        }

        return null;
    }

    public String findProductDetailPagePath() {
        try {
            List<ProductSku> list = productSkuService.findIsValidProductSkusByProductId(Long.valueOf(productId));
            String detailPath = "";
            if (CollectionUtils.isNotEmpty(list)) {
                String path = ConfigurationUtil.getString("CMS_PRODUCT_PATH");
                detailPath = path + list.get(0).getProductSkuId() + ".html";
            } else {
                detailPath = "fail";
            }
            writeStr(detailPath);
        } catch (Exception e) {
            logger.error("findProductDetailPagePath失败，", e);
        }

        return null;
    }

    public String previewProductInfoPage() {
        Map jsonMap = new HashMap();
        try {
            String pageUrl = productMainService.previewProductInfoPage(getRequest().getParameter("id"));
            logger.info("产品预览地址:{}.", pageUrl);
            jsonMap.put("productPageUrl", pageUrl);
            writeJson(jsonMap);
        } catch (Exception e) {
            logger.error("previewProductInfoPage失败，", e);
        }

        return null;
    }

    /**
     * 更新SKU运费
     *
     * @return
     */
    public String updateSkuFreight() {
        ProductSku sku = new ProductSku();
        sku.setProductSkuId(productSkuId);
        sku.setFreeStatus(freeStatus);
        sku.setProductId(Long.valueOf(productId));
        if (freight != null) {
            sku.setFreight(freight);
        }
        int result = 0;
        try {
            result = productSkuService.updateProductSku(sku);
            productSkuService.upShelfForSku(sku);
        } catch (Exception e) {
            logger.error("更新SKU运费失败，", e);
        }

        writeJson(result);
        return null;
    }

    public String[] getProductIds() {
        String[] temp = productIds;
        return temp;
    }

    public void setProductIds(String[] productIds) {
        this.productIds = productIds;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Long getProductSkuId() {
        return productSkuId;
    }

    public void setProductSkuId(Long productSkuId) {
        this.productSkuId = productSkuId;
    }

    public String getFreeStatus() {
        return freeStatus;
    }

    public void setFreeStatus(String freeStatus) {
        this.freeStatus = freeStatus;
    }

    public Double getFreight() {
        return freight;
    }

    public void setFreight(Double freight) {
        this.freight = freight;
    }

    public SuppliersInfo getSuppliersInfo() {
        return suppliersInfo;
    }

    public void setSuppliersInfo(SuppliersInfo suppliersInfo) {
        this.suppliersInfo = suppliersInfo;
    }

}
