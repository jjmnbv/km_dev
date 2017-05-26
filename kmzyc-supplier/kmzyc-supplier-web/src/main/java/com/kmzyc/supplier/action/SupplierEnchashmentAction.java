package com.kmzyc.supplier.action;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.framework.constants.Constants;
import com.kmzyc.supplier.model.BnesAcctEnchashment;
import com.kmzyc.supplier.model.BnesAcctEnchashmentCriteria;
import com.kmzyc.supplier.service.SupplierEnchashmentService;

@Controller("supplierEnchashmentAction")
@Scope(value = "prototype")
public class SupplierEnchashmentAction extends SupplierBaseAction {

    @Resource
    private SupplierEnchashmentService supplierEnchashmentService;

    private static Logger logger = LoggerFactory.getLogger(ApplySupplierAction.class);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private String startDate;

    private String endDate;

    private BnesAcctEnchashmentCriteria bnesAcctEnchashmentCriteria;

    private BnesAcctEnchashment bnesAcctEnchashment;

    private BigDecimal enchashmentId;

    /**
     * 提现申请列表
     *
     * @return
     */
    public String searchEnchashmentByPage() {

        // 登录的用户
        Long userId = getLoginUserId();
        try {
            pagintion = getPagination(Constants.VIEW_PAGE, Integer.parseInt(super.getPageSize()));
            if (bnesAcctEnchashmentCriteria == null) bnesAcctEnchashmentCriteria = new BnesAcctEnchashmentCriteria();
            if (StringUtils.isNotEmpty(startDate)) bnesAcctEnchashmentCriteria.setStartDate(sdf.parse(startDate));
            if (StringUtils.isNotEmpty(endDate)) bnesAcctEnchashmentCriteria.setEndDate(sdf.parse(endDate));
            bnesAcctEnchashmentCriteria.setnLoginId(userId);
            pagintion = supplierEnchashmentService.searchEnchashmentPage(bnesAcctEnchashmentCriteria, pagintion);

        } catch (Exception e) {
            logger.error("提现管理列表错误：", e);
            return ERROR;
        }

        return SUCCESS;
    }

    /**
     * 根据Id查看提现情况
     *
     * @return
     */
    public String searchEnchashmentById() {
        try {
            bnesAcctEnchashment = supplierEnchashmentService.searchBnesAcctEnchashmentById(enchashmentId);
        } catch (Exception e) {
            logger.error("提现管理列表错误：", e);
            return ERROR;
        }
        return SUCCESS;
    }

    public BnesAcctEnchashmentCriteria getBnesAcctEnchashmentCriteria() {
        return bnesAcctEnchashmentCriteria;
    }

    public void setBnesAcctEnchashmentCriteria(
            BnesAcctEnchashmentCriteria bnesAcctEnchashmentCriteria) {
        this.bnesAcctEnchashmentCriteria = bnesAcctEnchashmentCriteria;
    }

    public BnesAcctEnchashment getBnesAcctEnchashment() {
        return bnesAcctEnchashment;
    }

    public void setBnesAcctEnchashment(BnesAcctEnchashment bnesAcctEnchashment) {
        this.bnesAcctEnchashment = bnesAcctEnchashment;
    }

    public BigDecimal getEnchashmentId() {
        return enchashmentId;
    }

    public void setEnchashmentId(BigDecimal enchashmentId) {
        this.enchashmentId = enchashmentId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

}
