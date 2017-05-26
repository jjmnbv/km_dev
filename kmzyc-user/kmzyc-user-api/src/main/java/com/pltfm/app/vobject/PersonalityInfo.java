package com.pltfm.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 个人个性信息类
 * 
 * @author cjm
 * @since 2013-7-10
 */
public class PersonalityInfo implements Serializable {
    /**
     * 个性主键
     */
    private Integer n_PersonalityId;
    /**
     * 登录主键
     */
    private Integer n_LoginId;
    /**
     * 客户头衔主键
     */
    private Integer n_RankId;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 个性签名
     */
    private String personalityAutograph;
    /**
     * 性格
     */
    private String character;
    /**
     * 总积分
     */
    private BigDecimal n_TotalIntegral;
    /**
     * 可用积分
     */
    private BigDecimal n_AvailableIntegral;
    /**
     * 头像照片
     */
    private String headSculpture;
    /**
     * 兴趣爱好
     */
    private String interest;
    /**
     * 微博地址
     */
    private String microblogAddress;
    /**
     * QQ号
     */
    private String qqNumber;
    /**
     * 信用值
     */
    private Integer n_IndividualCreditValue;
    /**
     * 经验值
     */
    private Integer n_EmpiricalValue;

    /**
     * 消费金额
     */
    private BigDecimal amountConsume;
    /**
     * 创建日期
     */
    private Date d_CreateDate;
    /**
     * 创建人
     */
    private Integer n_Created;
    /**
     * 修改日期
     */
    private Date d_ModifyDate;
    /**
     * 修改人
     */
    private Integer n_Modified;

    // ------------ for page
    /**
     * 最小值
     */
    private Integer skip;
    /**
     * 最大值
     */
    private Integer max;
    /**
     * 上年度消费金额
     */
    private Double lastYear_Amount;



    public Double getLastYear_Amount() {
        return lastYear_Amount;
    }

    public void setLastYear_Amount(Double lastYear_Amount) {
        this.lastYear_Amount = lastYear_Amount;
    }

    public Integer getSkip() {
        return skip;
    }

    public void setSkip(Integer skip) {
        this.skip = skip;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Integer getN_PersonalityId() {
        return n_PersonalityId;
    }

    public void setN_PersonalityId(Integer nPersonalityId) {
        n_PersonalityId = nPersonalityId;
    }

    public Integer getN_LoginId() {
        return n_LoginId;
    }

    public void setN_LoginId(Integer nLoginId) {
        n_LoginId = nLoginId;
    }

    public Integer getN_RankId() {
        return n_RankId;
    }

    public void setN_RankId(Integer nRankId) {
        n_RankId = nRankId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPersonalityAutograph() {
        return personalityAutograph;
    }

    public void setPersonalityAutograph(String personalityAutograph) {
        this.personalityAutograph = personalityAutograph;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getHeadSculpture() {
        return headSculpture;
    }

    public void setHeadSculpture(String headSculpture) {
        this.headSculpture = headSculpture;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getMicroblogAddress() {
        return microblogAddress;
    }

    public void setMicroblogAddress(String microblogAddress) {
        this.microblogAddress = microblogAddress;
    }

    public String getQqNumber() {
        return qqNumber;
    }

    public void setQqNumber(String qqNumber) {
        this.qqNumber = qqNumber;
    }

    public Integer getN_IndividualCreditValue() {
        return n_IndividualCreditValue;
    }

    public void setN_IndividualCreditValue(Integer nIndividualCreditValue) {
        n_IndividualCreditValue = nIndividualCreditValue;
    }

    public Integer getN_EmpiricalValue() {
        return n_EmpiricalValue;
    }

    public void setN_EmpiricalValue(Integer nEmpiricalValue) {
        n_EmpiricalValue = nEmpiricalValue;
    }

    public Date getD_CreateDate() {
        return d_CreateDate;
    }

    public void setD_CreateDate(Date dCreateDate) {
        d_CreateDate = dCreateDate;
    }

    public Integer getN_Created() {
        return n_Created;
    }

    public void setN_Created(Integer nCreated) {
        n_Created = nCreated;
    }

    public Date getD_ModifyDate() {
        return d_ModifyDate;
    }

    public void setD_ModifyDate(Date dModifyDate) {
        d_ModifyDate = dModifyDate;
    }

    public Integer getN_Modified() {
        return n_Modified;
    }

    public void setN_Modified(Integer nModified) {
        n_Modified = nModified;
    }

    public BigDecimal getN_TotalIntegral() {
        return n_TotalIntegral;
    }

    public void setN_TotalIntegral(BigDecimal n_TotalIntegral) {
        this.n_TotalIntegral = n_TotalIntegral;
    }

    public BigDecimal getN_AvailableIntegral() {
        return n_AvailableIntegral;
    }

    public void setN_AvailableIntegral(BigDecimal n_AvailableIntegral) {
        this.n_AvailableIntegral = n_AvailableIntegral;
    }

    public BigDecimal getAmountConsume() {
        return amountConsume;
    }

    public void setAmountConsume(BigDecimal amountConsume) {
        this.amountConsume = amountConsume;
    }
    
    
    
    
}
