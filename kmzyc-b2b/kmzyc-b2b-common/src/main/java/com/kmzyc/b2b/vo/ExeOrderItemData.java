package com.kmzyc.b2b.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class ExeOrderItemData implements Serializable {

  private static final long serialVersionUID = -8299163003041246534L;
  private String product_id;// 商品编码
  private String shop_product_id;// 商家商品编码 捷科编码
  private String title;// 商品名称
  private String sku_id;// 规格编码 skuCode
  private String shop_sku_id;// 商家规格编码 捷科编码
  private String sku_name;// 规格名称
  private Long itemId;
  private BigDecimal qty_ordered;// 商品数量
  private BigDecimal price;// 商品单价
  private BigDecimal oid;// 子订单号（非必要）

  public String getProduct_id() {
    return product_id;
  }

  public void setProduct_id(String productId) {
    product_id = productId;
  }

  public String getShop_product_id() {
    return shop_product_id;
  }

  public void setShop_product_id(String shopProductId) {
    shop_product_id = shopProductId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getSku_id() {
    return sku_id;
  }

  public void setSku_id(String skuId) {
    sku_id = skuId;
  }

  public String getShop_sku_id() {
    return shop_sku_id;
  }

  public void setShop_sku_id(String shopSkuId) {
    shop_sku_id = shopSkuId;
  }

  public String getSku_name() {
    return sku_name;
  }

  public void setSku_name(String skuName) {
    sku_name = skuName;
  }

  public BigDecimal getQty_ordered() {
    return qty_ordered;
  }

  public void setQty_ordered(BigDecimal qtyOrdered) {
    qty_ordered = qtyOrdered;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public BigDecimal getOid() {
    return oid;
  }

  public void setOid(BigDecimal oid) {
    this.oid = oid;
  }

  public Long getItemId() {
    return itemId;
  }

  public void setItemId(Long itemId) {
    this.itemId = itemId;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("<item>");
    if (null != product_id)
      sb.append("<product_id><![CDATA[").append(product_id).append("]]></product_id>");
    if (null != shop_product_id)
      sb.append("<shop_product_id><![CDATA[").append(shop_product_id).append(
          "]]></shop_product_id>");
    if (null != title) sb.append("<title><![CDATA[").append(title).append("]]></title>");
    if (null != sku_id) sb.append("<sku_id><![CDATA[").append(sku_id).append("]]></sku_id>");
    if (null != shop_sku_id)
      sb.append("<shop_sku_id><![CDATA[").append(shop_sku_id).append("]]></shop_sku_id>");
    if (null != sku_name)
      sb.append("<sku_name><![CDATA[").append(sku_name).append("]]></sku_name>");
    if (null != qty_ordered)
      sb.append("<qty_ordered><![CDATA[").append(qty_ordered).append("]]></qty_ordered>");
    if (null != price) sb.append("<price><![CDATA[").append(price).append("]]></price>");
    if (null != oid) sb.append("<oid><![CDATA[").append(oid).append("]]></oid>");
    sb.append("</item>");
    return sb.toString();
  }
}
