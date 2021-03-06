package com.kmzyc.promotion.app.vobject;

import java.io.Serializable;
import java.util.Date;

public class ProductErpRelation implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -3034930631788278816L;
    /**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database
     * column PRODUCT_ERP_RELATION.PRID
     * 
     * @ibatorgenerated Wed Nov 19 15:20:24 CST 2014
     */
    private Long prid;
    /**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database
     * column PRODUCT_ERP_RELATION.PRODUCT_SKU_ID
     * 
     * @ibatorgenerated Wed Nov 19 15:20:24 CST 2014
     */
    private Long productSkuId;
    /**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database
     * column PRODUCT_ERP_RELATION.SYS_CODE
     * 
     * @ibatorgenerated Wed Nov 19 15:20:24 CST 2014
     */
    private String sysCode;
    /**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database
     * column PRODUCT_ERP_RELATION.ERP_PRO_CODE
     * 
     * @ibatorgenerated Wed Nov 19 15:20:24 CST 2014
     */
    private String erpProCode;
    /**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database
     * column PRODUCT_ERP_RELATION.STATUS
     * 
     * @ibatorgenerated Wed Nov 19 15:20:24 CST 2014
     */
    private Integer status;
    /**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database
     * column PRODUCT_ERP_RELATION.MODIFY_TIME
     * 
     * @ibatorgenerated Wed Nov 19 15:20:24 CST 2014
     */
    private Date modifyTime;
    /**
     * This field was generated by Apache iBATIS ibator. This field corresponds to the database
     * column PRODUCT_ERP_RELATION.MODIFY_PERSON
     * 
     * @ibatorgenerated Wed Nov 19 15:20:24 CST 2014
     */
    private Long modifyPerson;

    private ProductSku productSku;

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column PRODUCT_ERP_RELATION.PRID
     * 
     * @return the value of PRODUCT_ERP_RELATION.PRID
     * @ibatorgenerated Wed Nov 19 15:20:24 CST 2014
     */
    public Long getPrid() {
        return prid;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column PRODUCT_ERP_RELATION.PRID
     * 
     * @param prid the value for PRODUCT_ERP_RELATION.PRID
     * @ibatorgenerated Wed Nov 19 15:20:24 CST 2014
     */
    public void setPrid(Long prid) {
        this.prid = prid;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column PRODUCT_ERP_RELATION.PRODUCT_SKU_ID
     * 
     * @return the value of PRODUCT_ERP_RELATION.PRODUCT_SKU_ID
     * @ibatorgenerated Wed Nov 19 15:20:24 CST 2014
     */
    public Long getProductSkuId() {
        return productSkuId;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column PRODUCT_ERP_RELATION.PRODUCT_SKU_ID
     * 
     * @param productSkuId the value for PRODUCT_ERP_RELATION.PRODUCT_SKU_ID
     * @ibatorgenerated Wed Nov 19 15:20:24 CST 2014
     */
    public void setProductSkuId(Long productSkuId) {
        this.productSkuId = productSkuId;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column PRODUCT_ERP_RELATION.SYS_CODE
     * 
     * @return the value of PRODUCT_ERP_RELATION.SYS_CODE
     * @ibatorgenerated Wed Nov 19 15:20:24 CST 2014
     */
    public String getSysCode() {
        return sysCode;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column PRODUCT_ERP_RELATION.SYS_CODE
     * 
     * @param sysCode the value for PRODUCT_ERP_RELATION.SYS_CODE
     * @ibatorgenerated Wed Nov 19 15:20:24 CST 2014
     */
    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column PRODUCT_ERP_RELATION.ERP_PRO_CODE
     * 
     * @return the value of PRODUCT_ERP_RELATION.ERP_PRO_CODE
     * @ibatorgenerated Wed Nov 19 15:20:24 CST 2014
     */
    public String getErpProCode() {
        return erpProCode;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column PRODUCT_ERP_RELATION.ERP_PRO_CODE
     * 
     * @param erpProCode the value for PRODUCT_ERP_RELATION.ERP_PRO_CODE
     * @ibatorgenerated Wed Nov 19 15:20:24 CST 2014
     */
    public void setErpProCode(String erpProCode) {
        this.erpProCode = erpProCode;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column PRODUCT_ERP_RELATION.STATUS
     * 
     * @return the value of PRODUCT_ERP_RELATION.STATUS
     * @ibatorgenerated Wed Nov 19 15:20:24 CST 2014
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column PRODUCT_ERP_RELATION.STATUS
     * 
     * @param status the value for PRODUCT_ERP_RELATION.STATUS
     * @ibatorgenerated Wed Nov 19 15:20:24 CST 2014
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column PRODUCT_ERP_RELATION.MODIFY_TIME
     * 
     * @return the value of PRODUCT_ERP_RELATION.MODIFY_TIME
     * @ibatorgenerated Wed Nov 19 15:20:24 CST 2014
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column PRODUCT_ERP_RELATION.MODIFY_TIME
     * 
     * @param modifyTime the value for PRODUCT_ERP_RELATION.MODIFY_TIME
     * @ibatorgenerated Wed Nov 19 15:20:24 CST 2014
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method returns the value of the
     * database column PRODUCT_ERP_RELATION.MODIFY_PERSON
     * 
     * @return the value of PRODUCT_ERP_RELATION.MODIFY_PERSON
     * @ibatorgenerated Wed Nov 19 15:20:24 CST 2014
     */
    public Long getModifyPerson() {
        return modifyPerson;
    }

    /**
     * This method was generated by Apache iBATIS ibator. This method sets the value of the database
     * column PRODUCT_ERP_RELATION.MODIFY_PERSON
     * 
     * @param modifyPerson the value for PRODUCT_ERP_RELATION.MODIFY_PERSON
     * @ibatorgenerated Wed Nov 19 15:20:24 CST 2014
     */
    public void setModifyPerson(Long modifyPerson) {
        this.modifyPerson = modifyPerson;
    }

    public ProductSku getProductSku() {
        return productSku;
    }

    public void setProductSku(ProductSku productSku) {
        this.productSku = productSku;
    }
}
