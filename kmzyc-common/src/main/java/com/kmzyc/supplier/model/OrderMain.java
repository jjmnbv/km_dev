package com.kmzyc.supplier.model;


import com.pltfm.app.vobject.OrderItemVo;
import com.pltfm.app.vobject.OrderMainVo;
import com.pltfm.app.vobject.ViewSkuAttr;

import java.util.List;

/**
 * 该类继承订单系统中的那个主订单实体,后期由于需求变动,新增一些查询显示属性
 *
 * @author KM
 */
public class OrderMain extends OrderMainVo implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单列表默认显示的图片路径
     */
    private String defaultImagePath;

    /**
     * 尾款支付截止时间
     */
    private String finallyPayTime;

    /**
     * 订单下的产品标题
     */
    private String productTitle;

    /**
     * 订单下的sku产品的基本信息
     */
    private List<ViewSkuAttr> viewSkuAttrs;


    /**
     * 订单下面的订单产品详情
     */
    private List<OrderItemVo> orderItemList;

    public String getDefaultImagePath() {
        return defaultImagePath;
    }

    public void setDefaultImagePath(String defaultImagePath) {
        this.defaultImagePath = defaultImagePath;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public List<ViewSkuAttr> getViewSkuAttrs() {
        return viewSkuAttrs;
    }

    public void setViewSkuAttrs(List<ViewSkuAttr> viewSkuAttrs) {
        this.viewSkuAttrs = viewSkuAttrs;
    }

    public List<OrderItemVo> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItemVo> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public String getFinallyPayTime() {
        return finallyPayTime;
    }

    public void setFinallyPayTime(String finallyPayTime) {
        this.finallyPayTime = finallyPayTime;
    }
}