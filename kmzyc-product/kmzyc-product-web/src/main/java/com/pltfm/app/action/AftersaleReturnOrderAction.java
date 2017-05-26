package com.pltfm.app.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.Action;
import com.pltfm.app.maps.ReturnOrExchangeHandleResultMap;
import com.pltfm.app.maps.ReturnOrExchangeStatusMap;
import com.pltfm.app.service.AftersaleReturnOrderService;
import com.pltfm.app.vobject.AftersaleReturnOrder;
import com.pltfm.sys.model.SysUser;
import com.kmzyc.commons.page.Page;

/**
 * @author tanyunxing
 */
@Controller("aftersaleReturnOrderAction")
@Scope(value = "prototype")
public class AftersaleReturnOrderAction extends BaseAction {

    AftersaleReturnOrder order = new AftersaleReturnOrder();

    String rtnMessage;

    @Resource
    private AftersaleReturnOrderService aftersaleReturnOrderService;

    // 退货单主键
    private Long returnId;

    // 处理结果
    private String handleResult;

    // 处理状态
    private String orderStatus;

    // 条码
    private String inputBarCode;

    private String orderCode;

    private boolean changeReturnOrder = false;

    /**
     * 分页列表
     *
     * @return
     */
    public String showList() {
        try {
            aftersaleReturnOrderService.searchPage(page, order);

            // 获取单据状态集合
            super.getRequest().setAttribute("statusMap", ReturnOrExchangeStatusMap.getMap());
            // 获取处理结果集合
            super.getRequest().setAttribute("handleResultMap", ReturnOrExchangeHandleResultMap.getMap());
        } catch (Exception e) {
            e.printStackTrace();
            return Action.ERROR;
        }

        return Action.SUCCESS;
    }

    /**
     * 处理单个退货单
     *
     * @return
     */
    public String gotoEdit() {
        try {
            order = aftersaleReturnOrderService.findByPrimaryKey(returnId, handleResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Action.SUCCESS;
    }

    /**
     * 更新
     *
     * @return
     */
    public String edit() {
        int result = -1;
        try {
            SysUser user = super.getLoginUser();
            order.setEndTime(new Date());
            result = aftersaleReturnOrderService.updateObject(order, user, changeReturnOrder);
        } catch (Exception e) {
            logger.error("更新失败，", e);
        }
        strWriteJson(result+"");
        return null;
    }

    /**
     * 更新退货单状态
     *
     * @return
     */
    public String editStatus() {
        AftersaleReturnOrder newOrder = new AftersaleReturnOrder();
        newOrder.setReturnId(returnId);
        if ("2".equals(orderStatus)) {
            newOrder.setBarCode(inputBarCode);
        }
        if (!StringUtils.isEmpty(orderCode)) {
            newOrder.setOrderCode(orderCode);
        }
        newOrder.setStatus(orderStatus);

        try {
            aftersaleReturnOrderService.updateObject(newOrder, super.getLoginUser(), false);
            strWriteJson("success");
        } catch (Exception e) {
            e.printStackTrace();
            strWriteJson("fail");
        }
        return null;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public AftersaleReturnOrder getOrder() {
        return order;
    }

    public void setOrder(AftersaleReturnOrder order) {
        this.order = order;
    }

    public String getRtnMessage() {
        return rtnMessage;
    }

    public void setRtnMessage(String rtnMessage) {
        this.rtnMessage = rtnMessage;
    }

    public Long getReturnId() {
        return returnId;
    }

    public void setReturnId(Long returnId) {
        this.returnId = returnId;
    }

    public String getHandleResult() {
        return handleResult;
    }

    public void setHandleResult(String handleResult) {
        this.handleResult = handleResult;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getInputBarCode() {
        return inputBarCode;
    }

    public void setInputBarCode(String inputBarCode) {
        this.inputBarCode = inputBarCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public boolean isChangeReturnOrder() {
        return changeReturnOrder;
    }

    public void setChangeReturnOrder(boolean changeReturnOrder) {
        this.changeReturnOrder = changeReturnOrder;
    }

}
