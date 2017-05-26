package com.kmzyc.b2b.model;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kmzyc.framework.exception.ObjectTransformException;

public class OrderAlter {
    // private static Logger logger = Logger.getLogger(OrderAlter.class);
    private static Logger logger = LoggerFactory.getLogger(OrderAlter.class);
    /***
     * 图片地址列表
     */
    private List potoUrls;

    /**
     * 物理主键
     */
    private Long orderAlterId;
    /**
     * 外键，订单号
     */
    private String orderCode;
    /**
     * 操作人
     */
    private String proposer;
    /**
     * 审核人
     */
    private String checker;
    /**
     * 状态
     */
    private Short proposeStatus;
    /**
     * 申请时间
     */
    private Date createDate;
    /**
     * 审核时间
     */
    private Date checkDate;
    /**
     * 描述
     */
    private String alterComment;
    /**
     * 商品返回方式
     */
    private Short backType;
    /**
     * 地址
     */
    private String address;
    /**
     * 姓名
     */
    private String name;
    /**
     * 电话
     */
    private String phone;
    /**
     * 完成时间
     */
    private Date finishDate;
    /**
     * 应退金额
     */
    private BigDecimal ruturnMoney;
    /**
     * 补贴运费
     */
    private BigDecimal fareSubsidy;
    /**
     * 应退总金额
     */
    private BigDecimal ruturnSum;
    /**
     * 业务主键
     */
    private String orderAlterCode;
    /**
     * 出库单号
     */
    private BigDecimal stockOutId;
    /**
     * 配送单号
     */
    private BigDecimal distributionId;
    /**
     * 物流公司订单号
     */
    private String logisticsOrderNo;
    /**
     * 物流单号
     */
    private String customerLogisticsNo;
    /**
     * 外键，订单明细号
     */
    private Long orderItemId;
    /**
     * 服务类型
     */
    private Short alterType;
    /**
     * 数量
     */
    private Long alterNum;
    /**
     * 上传图片批次号
     */
    private String photoBatchNo;
    /**
     * 凭据
     */
    private Long evidence;
    /**
     * 运费方案号
     */
    private String logisticsCode;
    private String customerLogisticsCode;

    /**
     * 退货返运费
     */
    private BigDecimal returnFare;

    public BigDecimal getReturnFare() {
        return returnFare;
    }

    public void setReturnFare(BigDecimal returnFare) {
        this.returnFare = returnFare;
    }

    /**
     * 预售定金
     */
    private BigDecimal deposit;
    /**
     * 预售尾款
     */
    private BigDecimal finalmoney;
    /**
     * 定金补偿
     */
    private BigDecimal compensate;

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }


    public BigDecimal getFinalmoney() {
        return finalmoney;
    }

    public void setFinalmoney(BigDecimal finalmoney) {
        this.finalmoney = finalmoney;
    }

    public BigDecimal getCompensate() {
        return compensate;
    }

    public void setCompensate(BigDecimal compensate) {
        this.compensate = compensate;
    }

    /**
     * 物流公司名称
     */
    private String logisticsName;
    private String customerLogisticsName;

    private String province;
    private String city;
    private String area;
    private Integer zipcode;

    public Integer getZipcode() {
        return zipcode;
    }

    public void setZipcode(Integer zipcode) {
        this.zipcode = zipcode;
    }

    public String getLogisticsName() {
        return logisticsName;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }

    public String getCustomerLogisticsName() {
        return customerLogisticsName;
    }

    public void setCustomerLogisticsName(String customerLogisticsName) {
        this.customerLogisticsName = customerLogisticsName;
    }

    public String getLogisticsCode() {
        return logisticsCode;
    }

    public void setLogisticsCode(String logisticsCode) {
        this.logisticsCode = logisticsCode;
    }

    public String getCustomerLogisticsCode() {
        return customerLogisticsCode;
    }

    public void setCustomerLogisticsCode(String customerLogisticsCode) {
        this.customerLogisticsCode = customerLogisticsCode;
    }

    /**
     * 快捷地址号
     */
    private BigDecimal addressId;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 退货后优惠金额
     */
    private BigDecimal discountAmount;
    /**
     * 是否补回运费
     */
    private Short fareAdditional;
    /**
     * 建议补发优惠券面额
     */
    private BigDecimal preferentialAmount;
    /**
     * 
     */
    private OrderItem orderItem;

    public com.pltfm.app.entities.OrderAlter transFormToRemoteAddress()
            throws ObjectTransformException {
        com.pltfm.app.entities.OrderAlter alter = new com.pltfm.app.entities.OrderAlter();
        try {
            // 转换名称相同的属性
            BeanUtils.copyProperties(alter, this);
        } catch (IllegalAccessException e) {
            logger.error("将本地OrderMain对象转换为远程对象出错：" + e.getMessage(), e);
            throw new ObjectTransformException("将本地OrderMain对象转换为远程对象出错！");
        } catch (InvocationTargetException e) {
            logger.error("将本地OrderMain对象转换为远程对象出错：" + e.getMessage(), e);
            throw new ObjectTransformException("将本地OrderMain对象转换为远程对象出错！");
        }
        return alter;
    }

    private String logisticsStr;

    private String customerLogisticsStr;

    private String auditComment;

    private String qualityComment;

    /*--------------------------getters and setters--------------------------*/
    public Long getOrderAlterId() {
        return orderAlterId;
    }

    public void setOrderAlterId(Long orderAlterId) {
        this.orderAlterId = orderAlterId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getProposer() {
        return proposer;
    }

    public void setProposer(String proposer) {
        this.proposer = proposer;
    }

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    public Short getProposeStatus() {
        return proposeStatus;
    }

    public void setProposeStatus(Short proposeStatus) {
        this.proposeStatus = proposeStatus;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public String getAlterComment() {
        return alterComment;
    }

    public void setAlterComment(String alterComment) {
        this.alterComment = alterComment;
    }

    public Short getBackType() {
        return backType;
    }

    public void setBackType(Short backType) {
        this.backType = backType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public BigDecimal getRuturnMoney() {
        return ruturnMoney;
    }

    public void setRuturnMoney(BigDecimal ruturnMoney) {
        this.ruturnMoney = ruturnMoney;
    }

    public BigDecimal getFareSubsidy() {
        return fareSubsidy;
    }

    public void setFareSubsidy(BigDecimal fareSubsidy) {
        this.fareSubsidy = fareSubsidy;
    }

    public BigDecimal getRuturnSum() {
        return ruturnSum;
    }

    public void setRuturnSum(BigDecimal ruturnSum) {
        this.ruturnSum = ruturnSum;
    }

    public String getOrderAlterCode() {
        return orderAlterCode;
    }

    public void setOrderAlterCode(String orderAlterCode) {
        this.orderAlterCode = orderAlterCode;
    }

    public BigDecimal getStockOutId() {
        return stockOutId;
    }

    public void setStockOutId(BigDecimal stockOutId) {
        this.stockOutId = stockOutId;
    }

    public BigDecimal getDistributionId() {
        return distributionId;
    }

    public void setDistributionId(BigDecimal distributionId) {
        this.distributionId = distributionId;
    }

    public String getLogisticsOrderNo() {
        return logisticsOrderNo;
    }

    public void setLogisticsOrderNo(String logisticsOrderNo) {
        this.logisticsOrderNo = logisticsOrderNo;
    }

    public String getCustomerLogisticsNo() {
        return customerLogisticsNo;
    }

    public void setCustomerLogisticsNo(String customerLogisticsNo) {
        this.customerLogisticsNo = customerLogisticsNo;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Short getAlterType() {
        return alterType;
    }

    public void setAlterType(Short alterType) {
        this.alterType = alterType;
    }

    public Long getAlterNum() {
        return alterNum;
    }

    public void setAlterNum(Long alterNum) {
        this.alterNum = alterNum;
    }

    public String getPhotoBatchNo() {
        return photoBatchNo;
    }

    public void setPhotoBatchNo(String photoBatchNo) {
        this.photoBatchNo = photoBatchNo;
    }

    public Long getEvidence() {
        return evidence;
    }

    public void setEvidence(Long evidence) {
        this.evidence = evidence;
    }

    public BigDecimal getAddressId() {
        return addressId;
    }

    public void setAddressId(BigDecimal addressId) {
        this.addressId = addressId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Short getFareAdditional() {
        return fareAdditional;
    }

    public void setFareAdditional(Short fareAdditional) {
        this.fareAdditional = fareAdditional;
    }

    public BigDecimal getPreferentialAmount() {
        return preferentialAmount;
    }

    public void setPreferentialAmount(BigDecimal preferentialAmount) {
        this.preferentialAmount = preferentialAmount;
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

    public List getPotoUrls() {
        return potoUrls;
    }

    public void setPotoUrls(List potoUrls) {
        this.potoUrls = potoUrls;
    }

    public String getLogisticsStr() {
        return logisticsStr;
    }

    public void setLogisticsStr(String logisticsStr) {
        this.logisticsStr = logisticsStr;
    }

    public String getCustomerLogisticsStr() {
        return customerLogisticsStr;
    }

    public void setCustomerLogisticsStr(String customerLogisticsStr) {
        this.customerLogisticsStr = customerLogisticsStr;
    }

    public String getAuditComment() {
        return auditComment;
    }

    public void setAuditComment(String auditComment) {
        this.auditComment = auditComment;
    }

    public String getQualityComment() {
        return qualityComment;
    }

    public void setQualityComment(String qualityComment) {
        this.qualityComment = qualityComment;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
    /*--------------------------getters and setters--------------------------*/

}
