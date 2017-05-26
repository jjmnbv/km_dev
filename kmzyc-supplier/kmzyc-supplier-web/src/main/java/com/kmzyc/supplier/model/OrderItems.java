package com.kmzyc.supplier.model;

import com.pltfm.app.vobject.OrderItemVo;

import java.io.Serializable;
import java.util.List;

/**
 * orderitem 继承订单系统orderitemVo 为了符合新改版需求 需要显示单品层级的促销活动
 *
 * @author KM
 */
public class OrderItems extends OrderItemVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单 单品 参与的优惠活动 单品的活动也可以叠加
     */
    private List<OrderPreferentialVo> preferentialList;

    /**
     * 产品新增显示列 商家货号
     */
    private String sellerSkuCode;


    public List<OrderPreferentialVo> getPreferentialList() {
        return preferentialList;
    }

    public void setPreferentialList(List<OrderPreferentialVo> preferentialList) {
        this.preferentialList = preferentialList;
    }

    public String getSellerSkuCode() {
        return sellerSkuCode;
    }

    public void setSellerSkuCode(String sellerSkuCode) {
        this.sellerSkuCode = sellerSkuCode;
    }

}