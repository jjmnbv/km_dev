package com.kmzyc.b2b.third.model.wechat;

import java.util.ArrayList;
import java.util.List;

/**
 * 图文消息的xml实体
 * 
 * @author Administrator
 * 
 */
public class ArticleMsg extends BaseMsg {

  /*
   * 图文总数,最多为10,默认第一个item为大图
   */
  private String ArticleCount;

  /*
   * 具体的图文项数
   */
  private List<ArticleItem> Articles = new ArrayList<ArticleItem>();

  public String getArticleCount() {
    return ArticleCount;
  }

  public void setArticleCount(String articleCount) {
    ArticleCount = articleCount;
  }

  public List<ArticleItem> getArticles() {
    return Articles;
  }

  public void setArticles(List<ArticleItem> articles) {
    Articles = articles;
  }

}
