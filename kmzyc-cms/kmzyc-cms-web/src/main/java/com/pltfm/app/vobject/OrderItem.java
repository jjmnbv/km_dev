package com.pltfm.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderItem implements Serializable {

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column ORDER_ITEM.ORDER_ITEM_ID
     *
     * @abatorgenerated Thu Oct 24 19:17:37 CST 2013
     */
    private Long orderItemId;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column ORDER_ITEM.ORDER_CODE
     *
     * @abatorgenerated Thu Oct 24 19:17:37 CST 2013
     */
    private String orderCode;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column ORDER_ITEM.COMMODITY_NAME
     *
     * @abatorgenerated Thu Oct 24 19:17:37 CST 2013
     */
    private String commodityName;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column ORDER_ITEM.COMMODITY_CODE
     *
     * @abatorgenerated Thu Oct 24 19:17:37 CST 2013
     */
    private String commodityCode;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column ORDER_ITEM.COMMODITY_SKU
     *
     * @abatorgenerated Thu Oct 24 19:17:37 CST 2013
     */
    private String commoditySku;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column ORDER_ITEM.COMMODITY_BATCH_NUMBER
     *
     * @abatorgenerated Thu Oct 24 19:17:37 CST 2013
     */
    private BigDecimal commodityBatchNumber;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column ORDER_ITEM.COMMODITY_CALLED_PRICE
     *
     * @abatorgenerated Thu Oct 24 19:17:37 CST 2013
     */
    private BigDecimal commodityCalledPrice;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column ORDER_ITEM.COMMODITY_UNIT_PRICE
     *
     * @abatorgenerated Thu Oct 24 19:17:37 CST 2013
     */
    private BigDecimal commodityUnitPrice;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column ORDER_ITEM.COMMODITY_NUMBER
     *
     * @abatorgenerated Thu Oct 24 19:17:37 CST 2013
     */
    private Long commodityNumber;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column ORDER_ITEM.COMMODITY_SUM
     *
     * @abatorgenerated Thu Oct 24 19:17:37 CST 2013
     */
    private BigDecimal commoditySum;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column ORDER_ITEM.COMMODITY_CALLED_SUM
     *
     * @abatorgenerated Thu Oct 24 19:17:37 CST 2013
     */
    private BigDecimal commodityCalledSum;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column ORDER_ITEM.COMMODITY_DESCRIPTION
     *
     * @abatorgenerated Thu Oct 24 19:17:37 CST 2013
     */
    private String commodityDescription;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column ORDER_ITEM.WAREHOUSE_ID
     *
     * @abatorgenerated Thu Oct 24 19:17:37 CST 2013
     */
    private BigDecimal warehouseId;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column ORDER_ITEM.CREDITS
     *
     * @abatorgenerated Thu Oct 24 19:17:37 CST 2013
     */
    private Long credits;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column ORDER_ITEM.IMAGE_URL
     *
     * @abatorgenerated Thu Oct 24 19:17:37 CST 2013
     */
    private String imageUrl;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column ORDER_ITEM.COMMODITY_UNIT_WEIGHT
     *
     * @abatorgenerated Thu Oct 24 19:17:37 CST 2013
     */
    private BigDecimal commodityUnitWeight;

    /**
     * This field was generated by Abator for iBATIS.
     * This field corresponds to the database column ORDER_ITEM.SUIT_ID
     *
     * @abatorgenerated Thu Oct 24 19:17:37 CST 2013
     */
    private BigDecimal suitId;

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column ORDER_ITEM.ORDER_ITEM_ID
     *
     * @return the value of ORDER_ITEM.ORDER_ITEM_ID
     * @abatorgenerated Thu Oct 24 19:17:37 CST 2013
     */
    public Long getOrderItemId() {
        return orderItemId;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column ORDER_ITEM.ORDER_ITEM_ID
     *
     * @param orderItemId the value for ORDER_ITEM.ORDER_ITEM_ID
     * @abatorgenerated Thu Oct 24 19:17:37 CST 2013
     */
    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column ORDER_ITEM.ORDER_CODE
     *
     * @return the value of ORDER_ITEM.ORDER_CODE
     * @abatorgenerated Thu Oct 24 19:17:37 CST 2013
     */
    public String getOrderCode() {
        return orderCode;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column ORDER_ITEM.ORDER_CODE
     *
     * @param orderCode the value for ORDER_ITEM.ORDER_CODE
     * @abatorgenerated Thu Oct 24 19:17:37 CST 2013
     */
    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column ORDER_ITEM.COMMODITY_NAME
     *
     * @return the value of ORDER_ITEM.COMMODITY_NAME
     * @abatorgenerated Thu Oct 24 19:17:37 CST 2013
     */
    public String getCommodityName() {
        return commodityName;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column ORDER_ITEM.COMMODITY_NAME
     *
     * @param commodityName the value for ORDER_ITEM.COMMODITY_NAME
     * @abatorgenerated Thu Oct 24 19:17:37 CST 2013
     */
    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column ORDER_ITEM.COMMODITY_CODE
     *
     * @return the value of ORDER_ITEM.COMMODITY_CODE
     * @abatorgenerated Thu Oct 24 19:17:37 CST 2013
     */
    public String getCommodityCode() {
        return commodityCode;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column ORDER_ITEM.COMMODITY_CODE
     *
     * @param commodityCode the value for ORDER_ITEM.COMMODITY_CODE
     * @abatorgenerated Thu Oct 24 19:17:37 CST 2013
     */
    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column ORDER_ITEM.COMMODITY_SKU
     *
     * @return the value of ORDER_ITEM.COMMODITY_SKU
     * @abatorgenerated Thu Oct 24 19:17:37 CST 2013
     */
    public String getCommoditySku() {
        return commoditySku;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column ORDER_ITEM.COMMODITY_SKU
     *
     * @param commoditySku the value for ORDER_ITEM.COMMODITY_SKU
     * @abatorgenerated Thu Oct 24 19:17:37 CST 2013
     */
    public void setCommoditySku(String commoditySku) {
        this.commoditySku = commoditySku;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column ORDER_ITEM.COMMODITY_BATCH_NUMBER
     *
     * @return the value of ORDER_ITEM.COMMODITY_BATCH_NUMBER
     * @abatorgenerated Thu Oct 24 19:17:37 CST 2013
     */
    public BigDecimal getCommodityBatchNumber() {
        return commodityBatchNumber;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column ORDER_ITEM.COMMODITY_BATCH_NUMBER
     *
     * @param commodityBatchNumber the value for ORDER_ITEM.COMMODITY_BATCH_NUMBER
     * @abatorgenerated Thu Oct 24 19:17:37 CST 2013
     */
    public void setCommodityBatchNumber(BigDecimal commodityBatchNumber) {
        this.commodityBatchNumber = commodityBatchNumber;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column ORDER_ITEM.COMMODITY_CALLED_PRICE
     *
     * @return the value of ORDER_ITEM.COMMODITY_CALLED_PRICE
     * @abatorgenerated Thu Oct 24 19:17:37 CST 2013
     */
    public BigDecimal getCommodityCalledPrice() {
        return commodityCalledPrice;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column ORDER_ITEM.COMMODITY_CALLED_PRICE
     *
     * @param commodityCalledPrice the value for ORDER_ITEM.COMMODITY_CALLED_PRICE
     * @abatorgenerated Thu Oct 24 19:17:37 CST 2013
     */
    public void setCommodityCalledPrice(BigDecimal commodityCalledPrice) {
        this.commodityCalledPrice = commodityCalledPrice;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column ORDER_ITEM.COMMODITY_UNIT_PRICE
     *
     * @return the value of ORDER_ITEM.COMMODITY_UNIT_PRICE
     * @abatorgenerated Thu Oct 24 19:17:37 CST 2013
     */
    public BigDecimal getCommodityUnitPrice() {
        return commodityUnitPrice;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column ORDER_ITEM.COMMODITY_UNIT_PRICE
     *
     * @param commodityUnitPrice the value for ORDER_ITEM.COMMODITY_UNIT_PRICE
     * @abatorgenerated Thu Oct 24 19:17:37 CST 2013
     */
    public void setCommodityUnitPrice(BigDecimal commodityUnitPrice) {
        this.commodityUnitPrice = commodityUnitPrice;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column ORDER_ITEM.COMMODITY_NUMBER
     *
     * @return the value of ORDER_ITEM.COMMODITY_NUMBER
     * @abatorgenerated Thu Oct 24 19:17:37 CST 2013
     */
    public Long getCommodityNumber() {
        return commodityNumber;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column ORDER_ITEM.COMMODITY_NUMBER
     *
     * @param commodityNumber the value for ORDER_ITEM.COMMODITY_NUMBER
     * @abatorgenerated Thu Oct 24 19:17:37 CST 2013
     */
    public void setCommodityNumber(Long commodityNumber) {
        this.commodityNumber = commodityNumber;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column ORDER_ITEM.COMMODITY_SUM
     *
     * @return the value of ORDER_ITEM.COMMODITY_SUM
     * @abatorgenerated Thu Oct 24 19:17:37 CST 2013
     */
    public BigDecimal getCommoditySum() {
        return commoditySum;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column ORDER_ITEM.COMMODITY_SUM
     *
     * @param commoditySum the value for ORDER_ITEM.COMMODITY_SUM
     * @abatorgenerated Thu Oct 24 19:17:37 CST 2013
     */
    public void setCommoditySum(BigDecimal commoditySum) {
        this.commoditySum = commoditySum;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column ORDER_ITEM.COMMODITY_CALLED_SUM
     *
     * @return the value of ORDER_ITEM.COMMODITY_CALLED_SUM
     * @abatorgenerated Thu Oct 24 19:17:37 CST 2013
     */
    public BigDecimal getCommodityCalledSum() {
        return commodityCalledSum;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column ORDER_ITEM.COMMODITY_CALLED_SUM
     *
     * @param commodityCalledSum the value for ORDER_ITEM.COMMODITY_CALLED_SUM
     * @abatorgenerated Thu Oct 24 19:17:37 CST 2013
     */
    public void setCommodityCalledSum(BigDecimal commodityCalledSum) {
        this.commodityCalledSum = commodityCalledSum;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column ORDER_ITEM.COMMODITY_DESCRIPTION
     *
     * @return the value of ORDER_ITEM.COMMODITY_DESCRIPTION
     * @abatorgenerated Thu Oct 24 19:17:37 CST 2013
     */
    public String getCommodityDescription() {
        return commodityDescription;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column ORDER_ITEM.COMMODITY_DESCRIPTION
     *
     * @param commodityDescription the value for ORDER_ITEM.COMMODITY_DESCRIPTION
     * @abatorgenerated Thu Oct 24 19:17:37 CST 2013
     */
    public void setCommodityDescription(String commodityDescription) {
        this.commodityDescription = commodityDescription;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column ORDER_ITEM.WAREHOUSE_ID
     *
     * @return the value of ORDER_ITEM.WAREHOUSE_ID
     * @abatorgenerated Thu Oct 24 19:17:37 CST 2013
     */
    public BigDecimal getWarehouseId() {
        return warehouseId;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column ORDER_ITEM.WAREHOUSE_ID
     *
     * @param warehouseId the value for ORDER_ITEM.WAREHOUSE_ID
     * @abatorgenerated Thu Oct 24 19:17:37 CST 2013
     */
    public void setWarehouseId(BigDecimal warehouseId) {
        this.warehouseId = warehouseId;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column ORDER_ITEM.CREDITS
     *
     * @return the value of ORDER_ITEM.CREDITS
     * @abatorgenerated Thu Oct 24 19:17:37 CST 2013
     */
    public Long getCredits() {
        return credits;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column ORDER_ITEM.CREDITS
     *
     * @param credits the value for ORDER_ITEM.CREDITS
     * @abatorgenerated Thu Oct 24 19:17:37 CST 2013
     */
    public void setCredits(Long credits) {
        this.credits = credits;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column ORDER_ITEM.IMAGE_URL
     *
     * @return the value of ORDER_ITEM.IMAGE_URL
     * @abatorgenerated Thu Oct 24 19:17:37 CST 2013
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column ORDER_ITEM.IMAGE_URL
     *
     * @param imageUrl the value for ORDER_ITEM.IMAGE_URL
     * @abatorgenerated Thu Oct 24 19:17:37 CST 2013
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column ORDER_ITEM.COMMODITY_UNIT_WEIGHT
     *
     * @return the value of ORDER_ITEM.COMMODITY_UNIT_WEIGHT
     * @abatorgenerated Thu Oct 24 19:17:37 CST 2013
     */
    public BigDecimal getCommodityUnitWeight() {
        return commodityUnitWeight;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column ORDER_ITEM.COMMODITY_UNIT_WEIGHT
     *
     * @param commodityUnitWeight the value for ORDER_ITEM.COMMODITY_UNIT_WEIGHT
     * @abatorgenerated Thu Oct 24 19:17:37 CST 2013
     */
    public void setCommodityUnitWeight(BigDecimal commodityUnitWeight) {
        this.commodityUnitWeight = commodityUnitWeight;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method returns the value of the database column ORDER_ITEM.SUIT_ID
     *
     * @return the value of ORDER_ITEM.SUIT_ID
     * @abatorgenerated Thu Oct 24 19:17:37 CST 2013
     */
    public BigDecimal getSuitId() {
        return suitId;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method sets the value of the database column ORDER_ITEM.SUIT_ID
     *
     * @param suitId the value for ORDER_ITEM.SUIT_ID
     * @abatorgenerated Thu Oct 24 19:17:37 CST 2013
     */
    public void setSuitId(BigDecimal suitId) {
        this.suitId = suitId;
    }
}