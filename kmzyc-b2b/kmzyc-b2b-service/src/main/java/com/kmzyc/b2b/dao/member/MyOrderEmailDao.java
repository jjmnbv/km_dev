package com.kmzyc.b2b.dao.member;

import java.sql.SQLException;
import java.util.Map;

import com.km.framework.persistence.Dao;
import com.kmzyc.b2b.model.OrderMain;
import com.kmzyc.b2b.model.ProductImage;

public interface MyOrderEmailDao extends Dao {

  /**
   * 根据skuId查找产品的默认图片信息（主图）
   * 
   * @param sqlId
   * @param skuCode
   * @throws SQLException
   * @return
   */
  public ProductImage findDefaultProductImageBySkuCode(String sqlId, String skuCode)
      throws SQLException;

  /**
   * 订单跟踪根据手机号码和订单编号查询
   */
  public OrderMain findDilet(String sqlId, Map newConditon) throws SQLException;

  /**
   * 根据邮箱和手机号码进行查询
   * 
   * @param sqlId
   * @param newConditon
   * @return
   * @throws SQLException
   */
  public Integer findemailorMod(String sqlId, Map newConditon) throws SQLException;
}
