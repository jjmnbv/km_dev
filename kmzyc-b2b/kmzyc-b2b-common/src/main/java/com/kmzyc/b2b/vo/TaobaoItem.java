package com.kmzyc.b2b.vo;

import java.io.Serializable;

import com.taobao.api.domain.Item;
import com.taobao.api.domain.PromotionDisplayTop;

public class TaobaoItem implements Serializable {
  private static final long serialVersionUID = -8533880602144852953L;

  private Item item;

  private PromotionDisplayTop promotionDisplayTop;

  public Item getItem() {
    return item;
  }

  public void setItem(Item item) {
    this.item = item;
  }

  public PromotionDisplayTop getPromotionDisplayTop() {
    return promotionDisplayTop;
  }

  public void setPromotionDisplayTop(PromotionDisplayTop promotionDisplayTop) {
    this.promotionDisplayTop = promotionDisplayTop;
  }

}
