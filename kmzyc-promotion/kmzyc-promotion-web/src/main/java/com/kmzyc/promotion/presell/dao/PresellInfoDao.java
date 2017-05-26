package com.kmzyc.promotion.presell.dao;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.promotion.app.vobject.PromotionPresell;
import com.kmzyc.promotion.app.vobject.PromotionPresellProduct;

public interface PresellInfoDao {

  /**
   * 
   * 新增预售规则
   *
   * @author Administrator
   * @param promotionPresell
   * @return
   * @throws SQLException
   */
  public Long insertPresellRule(PromotionPresell promotionPresell) throws SQLException;

  /**
   * 
   * 新增预售产品
   *
   * @author Administrator
   * @param promotionPresellProduct
   * @throws SQLException
   */
  public void batchInsertPresellProduct(List<PromotionPresellProduct> listPresellProducts)
      throws SQLException;

  /**
   * 
   * 通过预售id查询预售详细信息
   *
   * @author Administrator
   * @param presellId
   * @return
   * @throws SQLException
   */
  public PromotionPresell findPresellInfoDetailById(Long presellId) throws SQLException;

  /**
   * 
   * 修改预售规则
   *
   * @author Administrator
   * @param promotionPresell
   * @return
   * @throws SQLException
   */
  public Integer updatePresellRule(PromotionPresell promotionPresell) throws SQLException;

  /**
   * 
   * 查询正在参加的预售商品及参加了商家活动中心报名（促销推广、图文推广、渠道推广）的商品
   *
   * @author Administrator
   * @param skuIds
   * @return
   */
  public int selectPresellAndActivityProductCount(List<Long> skuIds) throws SQLException;

}
