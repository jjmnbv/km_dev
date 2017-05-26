package com.kmzyc.supplier.model;

import com.pltfm.app.vobject.Product;

import java.io.Serializable;

/**
 * 库存管理参数实体
 * producStock类
 *
 * @author Administrator
 */
public class ProductStock implements Serializable {

    private static final long serialVersionUID = -5164149906041310951L;

    /**
     * 库存流水号id
     */
    private Long stockId;

    /**
     * 仓库编号
     */
    private Long warehouseId;

    /**
     * 产品编号
     */
    private Long productId;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 产品编号
     */
    private String productNo;

    /**
     * sku Id值
     */
    private Long skuAttributeId;

    /**
     * sku Id所对应的属性编号
     */
    private String skuAttValue;

    /**
     * 库存数量
     */
    private Long stockQuality;

    /**
     * 备注
     */
    private String remark;

    /**
     * 产品的main表,product对象
     */
    private Product product;

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public Long getSkuAttributeId() {
        return skuAttributeId;
    }

    public void setSkuAttributeId(Long skuAttributeId) {
        this.skuAttributeId = skuAttributeId;
    }

    public String getSkuAttValue() {
        return skuAttValue;
    }

    public void setSkuAttValue(String skuAttValue) {
        this.skuAttValue = skuAttValue;
    }

    public Long getStockQuality() {
        return stockQuality;
    }

    public void setStockQuality(Long stockQuality) {
        this.stockQuality = stockQuality;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}