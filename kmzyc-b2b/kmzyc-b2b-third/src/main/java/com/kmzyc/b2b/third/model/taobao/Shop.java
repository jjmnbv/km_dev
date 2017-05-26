package com.kmzyc.b2b.third.model.taobao;

import java.util.List;

public class Shop {
  Long num_iid;// 商品ID
  String pic_url;// 主图地址
  String title;// 商品标题
  String detail_url;// 商品地址
  String skuid;// skuid，用户已经选的规格ID
  String skualias;// sku 别名
  String skunum;// sku 库存数量
  String skuprice;// sku 价格(未使用优惠前)
  int shopnum;// 商品购买数量
  List<Ump> ump;// 优惠信息集合
  List<Buyer> buyer;// 买家收货信息集合
  String prices;// 商品总价
  String qiantai_appkey;
  String qiantai_secret;
  String qiantai_sessionKey;
  String houtai_appkey;
  String houtai_secret;
  String umpid;

  public String getUmpid() {
    return umpid;
  }

  public void setUmpid(String umpid) {
    this.umpid = umpid;
  }

  public String getQiantai_appkey() {
    return qiantai_appkey;
  }

  public void setQiantai_appkey(String qiantai_appkey) {
    this.qiantai_appkey = qiantai_appkey;
  }

  public String getQiantai_secret() {
    return qiantai_secret;
  }

  public void setQiantai_secret(String qiantai_secret) {
    this.qiantai_secret = qiantai_secret;
  }

  public String getQiantai_sessionKey() {
    return qiantai_sessionKey;
  }

  public void setQiantai_sessionKey(String qiantai_sessionKey) {
    this.qiantai_sessionKey = qiantai_sessionKey;
  }

  public String getHoutai_appkey() {
    return houtai_appkey;
  }

  public void setHoutai_appkey(String houtai_appkey) {
    this.houtai_appkey = houtai_appkey;
  }

  public String getHoutai_secret() {
    return houtai_secret;
  }

  public void setHoutai_secret(String houtai_secret) {
    this.houtai_secret = houtai_secret;
  }

  public List<Ump> getUmp() {
    return ump;
  }

  public void setUmp(List<Ump> ump) {
    this.ump = ump;
  }

  public int getShopnum() {
    return shopnum;
  }

  public void setShopnum(int shopnum) {
    this.shopnum = shopnum;
  }

  public String getDetail_url() {
    return detail_url;
  }

  public void setDetail_url(String detail_url) {
    this.detail_url = detail_url;
  }

  public Long getNum_iid() {
    return num_iid;
  }

  public void setNum_iid(Long num_iid) {
    this.num_iid = num_iid;
  }

  public String getPic_url() {
    return pic_url;
  }

  public void setPic_url(String pic_url) {
    this.pic_url = pic_url;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getSkuid() {
    return skuid;
  }

  public void setSkuid(String skuid) {
    this.skuid = skuid;
  }

  public String getSkualias() {
    return skualias;
  }

  public void setSkualias(String skualias) {
    this.skualias = skualias;
  }

  public String getSkunum() {
    return skunum;
  }

  public void setSkunum(String skunum) {
    this.skunum = skunum;
  }

  public String getSkuprice() {
    return skuprice;
  }

  public void setSkuprice(String skuprice) {
    this.skuprice = skuprice;
  }

  public List<Buyer> getBuyer() {
    return buyer;
  }

  public void setBuyer(List<Buyer> buyer) {
    this.buyer = buyer;
  }

  public String getPrices() {
    return prices;
  }

  public void setPrices(String prices) {
    this.prices = prices;
  }
}
