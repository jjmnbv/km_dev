package com.kmzyc.b2b.third.model.wechat;

/**
 * 推送图文消息的具体子项
 * 
 * @author Administrator
 * 
 */
public class ArticleItem {

  /*
   * 标题
   */
  private String Title;
  /*
   * 描述信息
   */
  private String Description;
  /*
   * 图片url
   */
  private String PicUrl;
  /*
   * 实际的链接url
   */
  private String Url;

  public ArticleItem() {}

  public ArticleItem(String title, String description, String picURl, String url) {
    this.Title = title;
    this.Description = description;
    this.PicUrl = picURl;
    this.Url = url;
  }

  public String getTitle() {
    return Title;
  }

  public void setTitle(String title) {
    Title = title;
  }

  public String getDescription() {
    return Description;
  }

  public void setDescription(String description) {
    Description = description;
  }

  public String getPicUrl() {
    return PicUrl;
  }

  public void setPicUrl(String picUrl) {
    PicUrl = picUrl;
  }

  public String getUrl() {
    return Url;
  }

  public void setUrl(String url) {
    Url = url;
  }
}
