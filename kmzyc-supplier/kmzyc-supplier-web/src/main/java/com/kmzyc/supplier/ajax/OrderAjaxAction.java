package com.kmzyc.supplier.ajax;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.kmzyc.supplier.action.SupplierBaseAction;
import com.kmzyc.supplier.service.OrderService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.pltfm.app.entities.OrderCarry;
import com.pltfm.app.util.DateTimeUtils;

@Controller("orderAjaxAction")
@Scope("prototype")
public class OrderAjaxAction extends SupplierBaseAction {

    private static Logger logger = LoggerFactory.getLogger(OrderAjaxAction.class);

    @Resource
    private OrderService orderService;

    private List<OrderCarry> orderCarryList;


    private String orderBeginDate; // 下单开始时间,用作结转查询

    private String orderEndDate; // 下单结束时间,用作结转查询

    public String batckCarryOver() {
        String msg = "订单批量结转成功";
        try {
            // 原先为结转所有订单,现在改为结转指定时间范围的待结转订单 20151012 update mlq
            Date beginDate = null;
            Date endDate = null;
            if (!StringUtils.isBlank(orderBeginDate) && !"null".equals(orderBeginDate)) {
                beginDate = DateTimeUtils.parseDate(orderBeginDate, "yyyy-MM-dd HH:mm:ss");
            }
            if (!StringUtils.isBlank(orderEndDate) && !"null".equals(orderEndDate)) {
                endDate = DateTimeUtils.parseDate(orderEndDate, "yyyy-MM-dd HH:mm:ss");
            }
            orderCarryList = orderService.carryOver(beginDate, endDate, super.getSupplyId(), null);
            if (CollectionUtils.isEmpty(orderCarryList)) {
                writeStr("error");
                return null;
            }

            OrderCarry oc = orderCarryList.get(0);
            if (oc == null || oc.getHandleId() == null) {
                writeStr("error");
                return null;
            }

            msg = oc.getHandleId().toString();
            logger.info("批量订单结转结果提示=" + oc.getHandleId());
        } catch (Exception e) {
            logger.error("carryOver:" + e.getMessage(), e);
            msg = "订单批量结转失败!";
        }

        writeStr(msg);
        return null;
    }

    public List<OrderCarry> getOrderCarryList() {
        return orderCarryList;
    }

    public void setOrderCarryList(List<OrderCarry> orderCarryList) {
        this.orderCarryList = orderCarryList;
    }

    public String getOrderBeginDate() {
        return orderBeginDate;
    }

    public void setOrderBeginDate(String orderBeginDate) {
        this.orderBeginDate = orderBeginDate;
    }

    public String getOrderEndDate() {
        return orderEndDate;
    }

    public void setOrderEndDate(String orderEndDate) {
        this.orderEndDate = orderEndDate;
    }

}
