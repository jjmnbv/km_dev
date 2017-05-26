package com.kmzyc.supplier.action.product;

import com.km.framework.page.Pagination;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.supplier.action.SupplierBaseAction;
import com.kmzyc.supplier.service.ProductSkuService;
import com.kmzyc.supplier.service.ProductStockService;
import com.pltfm.app.vobject.Product;
import com.pltfm.app.vobject.ProductStock;
import com.pltfm.app.vobject.ViewProductSku;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;


@Controller("productStockAction")
@Scope("prototype")
public class ProductStockAction extends SupplierBaseAction {

    private Logger logger = LoggerFactory.getLogger(ProductStockAction.class);

    @Resource
    private ProductStockService productStockService;

    @Resource(name = "productSkuService")
    private ProductSkuService productSkuService;

    /**
     * 供sku查询的参数实体
     */
    private ViewProductSku queryParaForProductSku;

    /**
     * 查询的参数实体
     */
    private ProductStock queryParaForProductStock;

    /**
     * 新增或者修改时的参数实体
     */
    private ProductStock productStock;

    /**
     * 标志是否按库存预警来查询
     */
    private String isLessThanQuantity;

    private String pageB;

    private String endindexB;

    private String startindexB;

    private long stockId;

    private long quantity;

    /**
     * 按条件查询库存列表
     *
     * @return
     */
    public String showStockList() {
        Pagination pagination = getPagination(Constants.VIEW_PAGE, Integer.parseInt(super.getPageSize()));
        if (queryParaForProductStock == null) {
            queryParaForProductStock = new ProductStock();
        }
        //限定只查当前登录供应商的商品库存列表
        if (queryParaForProductStock.getProduct() == null) {
            queryParaForProductStock.setProduct(new Product());
        }
        queryParaForProductStock.getProduct().setShopCode(getSupplyId().toString());
        // 库存预警查询条件
        if (StringUtils.isNotEmpty(isLessThanQuantity) && isLessThanQuantity.indexOf("Y") > -1) {
            queryParaForProductStock.setEndQuantity(10L);
        }

        try {
            pagintion = productStockService.searchPage(queryParaForProductStock, pagination);
            setWareHouseInfoMap();
            setProductStatusMap();
        } catch (Exception e) {
            logger.error("查询供应商商品库存报错：" + e.getMessage(), e);
            return ERROR;
        }

        return SUCCESS;
    }

    /**
     * 商品库存更新
     *
     * @return
     */
    public String updateProductStockByAjax() {
        if (productStock == null) {
            productStock = new ProductStock();
        }
        productStock.setStockId(stockId);
        productStock.setStockQuality(quantity);

        try {
            int result = productStockService.updateProductStock(productStock);
            if (result == 0) {
                logger.error("更新库存失败，更新0条数据!");
                writeJson("0");
            } else {
                writeJson("1");
            }
        } catch (Exception e) {
            logger.error("更新库存报错：" + e.getMessage(), e);
            writeJson("-1");
        }
        return null;
    }

    /**
     * 去修改库存信息
     * <note>
     *     该方法暂未启用
     * </note>
     *
     * @return
     */
    public String toUpdateProductStock() {
        String stockId = getRequest().getParameter("stockId");
        try {
            productStock = productStockService.queryByStockId(stockId);
            setSupplierWareHouseInfoMap();
        } catch (Exception e) {
            logger.error("根据库存Id查询库存实体报错:" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 更新库存信息
     * <note>
     *     该方法暂未启用
     * </note>
     *
     * @return
     */
    public String updateProductStock() {
        try {
            productStockService.updateProductStock(productStock);
        } catch (Exception e) {
            logger.error("更新库存报错:" + e.getMessage(), e);
            return ERROR;
        }

        return showStockList();
    }


    /**
     * 去添加库存,将属于该供应商的仓库列表加载出来
     * <note>
     *     该方法暂未启用
     * </note>
     *
     * @return
     */
    public String toAddProductStock() {
        setSupplierWareHouseInfoMap();
        return SUCCESS;
    }


    /**
     * 添加库存
     * <note>
     *     该方法暂未启用
     * </note>
     *
     * @return
     */
    public String addProductStock() {
        try {
            productStockService.saveProductStock(productStock);
        } catch (Exception e) {
            logger.error("新增加库存失败：" + e.getMessage(), e);
            return ERROR;
        }

        return showStockList();
    }

    /**
     * 查询所有可添加库存的商品
     * <note>
     *     该方法暂未启用
     * </note>
     *
     * @return
     */
    public String showAllEnableStockSkuProduct() {
        Pagination page = this.getPagination(Constants.VIEW_PAGE, 10);
        if (queryParaForProductSku == null) {
            queryParaForProductSku = new ViewProductSku();
        }
        setProductStatusMap();

        try {
            pagintion = productSkuService.searchEnableStockByPage(getSupplyId(), queryParaForProductSku, page);
        } catch (Exception e) {
            logger.error("查询所有可添加库存的商品失败：" + e.getMessage(), e);
            return ERROR;
        }

        return SUCCESS;
    }

    /**
     * 检查sku产品在指定的仓库是否已在库存表中已有记录
     * <note>
     *     该方法暂未启用
     * </note>
     * @return
     */
    public String isExistSkuValueStock() {
        Map<String, Object> jsonMap = new HashMap();

        try {
            int count = productStockService.isExistSkuStock(productStock.getWarehouseId().toString(),
                    productStock.getSkuAttValue().toString());
            if (count > 0) {
                jsonMap.put("isExist", true);
            }
        } catch (Exception e) {
            jsonMap.put("errorMsg", e.getMessage());
            logger.error("检查sku产品在指定的仓库是否已在库存表中已有记录失败：" + e.getMessage(), e);
        }

        writeJson(jsonMap);
        return null;
    }


    public ProductStock getQueryParaForProductStock() {
        return queryParaForProductStock;
    }

    public void setQueryParaForProductStock(ProductStock queryParaForProductStock) {
        this.queryParaForProductStock = queryParaForProductStock;
    }

    public ProductStock getProductStock() {
        return productStock;
    }

    public void setProductStock(ProductStock productStock) {
        this.productStock = productStock;
    }

    public ViewProductSku getQueryParaForProductSku() {
        return queryParaForProductSku;
    }

    public void setQueryParaForProductSku(ViewProductSku queryParaForProductSku) {
        this.queryParaForProductSku = queryParaForProductSku;
    }

    public String getPageB() {
        return pageB;
    }

    public void setPageB(String pageB) {
        this.pageB = pageB;
    }

    public String getEndindexB() {
        return endindexB;
    }

    public void setEndindexB(String endindexB) {
        this.endindexB = endindexB;
    }

    public String getStartindexB() {
        return startindexB;
    }

    public void setStartindexB(String startindexB) {
        this.startindexB = startindexB;
    }

    public String getIsLessThanQuantity() {
        return isLessThanQuantity;
    }

    public void setIsLessThanQuantity(String isLessThanQuantity) {
        this.isLessThanQuantity = isLessThanQuantity;
    }

    public long getStockId() {
        return stockId;
    }

    public void setStockId(long stockId) {
        this.stockId = stockId;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

}
