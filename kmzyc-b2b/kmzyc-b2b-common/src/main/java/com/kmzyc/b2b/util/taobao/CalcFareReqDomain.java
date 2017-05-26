package com.kmzyc.b2b.util.taobao;

public class CalcFareReqDomain {
  // 买家地区ID 收货地址区域ID
  private String areaIds;
  // 商品ID,为整数必须大于0,从这里开始包含下面的参数都是描述订单下面的商品信息，一个订单可以包含多个商品，商品之间的信息用逗号隔开
  private String itemIds;
  // 宝贝SKU ID,可以为NULL或空字符，如果不为空 必须是整数。如果输入的不是整数当作没有skuId处理 单个订单包含多个宝贝时用逗号隔开
  private String skuids;
  // 商品用户ID，必须为大于0的整数。 单个订单包含多个商品时商品的用户ID用逗号隔开
  private String userId;
  // 商品所挂的运费模板ID，必须为正整数，当没有挂运费模板时请输入0
  private String templateIds;
  // 商品是否是否卖家包邮，必须为“true”或“false”
  private String isSellerPay;
  // 单位为分，写入的时候请注意把统一价从元单位转换成分
  private String unifiedPost;// 全国统一快递费用可以为NULL或空字符，不为空时且不是正整数则当作空处理;
  // 单位为分，写入的时候请注意把统一价从元单位转换成分
  private String unifiedEms;// 全国统一EMS费用可以为NULL或空字符，不为空时且不是正整数 则当作空处理。
  // 商品ID,为整数必须大于0,从这里开始包含下面的参数都是描述订单下面的商品信息，一个订单可以包含多个商品，商品之间的信息用逗号隔开
  private String itemCounts;
  // 产品重量
  // 可以不输入，如果输入必须是有效的数值，数值的需要小于等于9999.999大于等于0最多含3位小数的数值；如果输入的值不是合法的数值当前查询会失败
  private String itemWeights;

  public String getAreaIds() {
    return areaIds;
  }

  public void setAreaIds(String areaIds) {
    this.areaIds = areaIds;
  }

  public String getItemIds() {
    return itemIds;
  }

  public void setItemIds(String itemIds) {
    this.itemIds = itemIds;
  }

  public String getSkuids() {
    return skuids;
  }

  public void setSkuids(String skuids) {
    this.skuids = skuids;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getTemplateIds() {
    return templateIds;
  }

  public void setTemplateIds(String templateIds) {
    this.templateIds = templateIds;
  }

  public String getIsSellerPay() {
    return isSellerPay;
  }

  public void setIsSellerPay(String isSellerPay) {
    this.isSellerPay = isSellerPay;
  }

  public String getUnifiedPost() {
    return unifiedPost;
  }

  public void setUnifiedPost(String unifiedPost) {
    this.unifiedPost = unifiedPost;
  }

  public String getUnifiedEms() {
    return unifiedEms;
  }

  public void setUnifiedEms(String unifiedEms) {
    this.unifiedEms = unifiedEms;
  }

  public String getItemCounts() {
    return itemCounts;
  }

  public void setItemCounts(String itemCounts) {
    this.itemCounts = itemCounts;
  }

  public String getItemWeights() {
    return itemWeights;
  }

  public void setItemWeights(String itemWeights) {
    this.itemWeights = itemWeights;
  }
}
