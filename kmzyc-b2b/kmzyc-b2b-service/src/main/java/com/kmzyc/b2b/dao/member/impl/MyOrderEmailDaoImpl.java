package com.kmzyc.b2b.dao.member.impl;

import java.sql.SQLException;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.b2b.dao.member.MyOrderEmailDao;
import com.kmzyc.b2b.model.OrderMain;
import com.kmzyc.b2b.model.ProductImage;
import com.kmzyc.b2b.service.impl.AccountInfoServiceImp;
import com.km.framework.persistence.impl.DaoImpl;

@Component
public class MyOrderEmailDaoImpl extends DaoImpl implements MyOrderEmailDao {

  //static Logger logger = Logger.getLogger(MyOrderEmailDao.class);
  private static Logger logger = LoggerFactory.getLogger(MyOrderEmailDaoImpl.class);

  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  /**
   * 根据skuId查找产品的默认图片信息（主图）
   * 
   * @param sqlId
   * @param skuCode
   * @return
   */
  public ProductImage findDefaultProductImageBySkuCode(String sqlId, String skuCode)
      throws SQLException {
    ProductImage productImage = null;
    try {
      productImage = (ProductImage) this.sqlMapClient.queryForObject(sqlId, skuCode);
    } catch (Exception e) {
      logger.error("查找产品图片信息出现异常，异常原因：" + e.getMessage(), e);
      throw new SQLException(e.getMessage(), e);
    }
    return productImage;
  }

  /**
   * 订单跟踪根据手机号码和订单编号查询
   */
  public OrderMain findDilet(String sqlId, Map newConditon) throws SQLException {
    OrderMain orderMain = null;
    try {
      orderMain = (OrderMain) this.sqlMapClient.queryForObject(sqlId, newConditon);
    } catch (Exception e) {
      logger.error("根据手机号和订单号查询订单出现异常，异常原因：" + e.getMessage(), e);
      throw new SQLException(e.getMessage(), e);
    }
    return orderMain;
  }

  public Integer findemailorMod(String sqlId, Map newConditon) throws SQLException {
    Integer orderMainCount = null;
    try {
      orderMainCount = (Integer) this.sqlMapClient.queryForObject(sqlId, newConditon);
    } catch (Exception e) {
      logger.error("根据邮箱和手机号查询订单出现异常，异常原因：" + e.getMessage(), e);
      throw new SQLException(e.getMessage(), e);
    }
    return orderMainCount;
  }
}
