package com.kmzyc.b2b.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kmzyc.b2b.dao.ShopInfoDao;
import com.kmzyc.b2b.model.ShopCategory;
import com.kmzyc.b2b.model.ShopInfo;
import com.kmzyc.b2b.service.OrderAssessDetailService;
import com.kmzyc.b2b.service.ShopInfoService;
import com.kmzyc.commons.exception.ServiceException;

/**
 * 店铺信息处理类 20150917 mlq add
 * 
 * @author KM
 * 
 */
@Service("shopInfoService")
public class ShopInfoServiceImpl implements ShopInfoService {


  @Resource
  private ShopInfoDao shopInfoDao;

  @Resource(name = "orderAssessDetailService")
  private OrderAssessDetailService orderAssessDetailService;

  // private static Logger log = LoggerFactory.getLogger(ShopInfoServiceImpl.class);
  private static Logger log = LoggerFactory.getLogger(ShopInfoServiceImpl.class);

  @Override
  public ShopInfo queryShopInfoForApp(Integer shopId) throws ServiceException {
    try {
      ShopInfo shopInfo = shopInfoDao.queryShopInfoByIdForApp(shopId.longValue());
      if (shopInfo != null) {
        // 查询店铺的一些评分
        Map<String, Object> resultMap =
            orderAssessDetailService.queryShopScore(shopInfo.getSupplierId());
        shopInfo.setProdDescPoint((BigDecimal) resultMap.get("assess_Type_one")); // 宝贝描述相符评分
        shopInfo.setSendSpeedPoint((BigDecimal) resultMap.get("assess_Type_two"));// 卖家发货速度评分
        shopInfo.setDispSpeedPoint((BigDecimal) resultMap.get("assess_Type_three"));// 物流配送速度评分
        shopInfo.setServicePoint((BigDecimal) resultMap.get("assess_Type_four"));// 售前售后服务评分
        shopInfo.setScore((Float) resultMap.get("avergScore"));// 综合评分
        // 查询店铺内的所有分类
        shopInfo.setShopCategorysList(queryAllShopCategory(shopInfo.getShopId()));
      }
      return shopInfo;
    } catch (SQLException e) {
      log.error("查询店铺Id=" + shopId + "的信息出错!错误信息=" + e.getMessage());
      throw new ServiceException(0, "查询店铺Id=" + shopId + "的信息出错!");
    }
  }

  @Override
  public Map<String, Object> querySimpleShopInfoByMap(Map condition) throws ServiceException {
    try {
      return shopInfoDao.querySimleShopInfoByMap(condition);
    } catch (SQLException e) {
      log.error("查询店铺Id的简要信息出错!错误信息=" + e.getMessage());
      throw new ServiceException(0, "查询店铺Id的简要信息出错!");
    }
  }

  @Override
  public List<ShopCategory> queryAllShopCategory(Long shopId) throws ServiceException {
    try {
      return shopInfoDao.queryAllShopCategory(shopId);
    } catch (SQLException e) {
      log.error("查询店铺Id=" + shopId + "的所有店内分类信息出错!错误信息=" + e.getMessage());
      throw new ServiceException(0, "查询店铺Id=" + shopId + "的所有店内分类信息出错!");
    }

  }

  @Override
  public Map<Object, Object> queryShopFareBySupplierId(Long supplierId) throws ServiceException {
    try {
      return shopInfoDao.queryShopFareBySupplierId(supplierId);
    } catch (SQLException e) {
      log.error("查询供应商id=" + supplierId + "的店铺运费信息出错!错误信息=" + e.getMessage());
      throw new ServiceException(0, "查询供应商id=" + supplierId + "的店铺运费信息出错!");
    }

  }

}
