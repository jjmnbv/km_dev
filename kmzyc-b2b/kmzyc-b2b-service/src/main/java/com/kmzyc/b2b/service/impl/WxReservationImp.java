package com.kmzyc.b2b.service.impl;

import com.km.framework.page.Pagination;
import com.kmzyc.b2b.model.RefData;
import com.kmzyc.b2b.service.WxReservationService;
import com.kmzyc.b2b.vo.WxKmrsShopInfo;
import com.kmzyc.b2b.vo.WxReservation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

@Service
@SuppressWarnings("unchecked")
public class WxReservationImp implements WxReservationService {

  //private static Logger logger = Logger.getLogger(WxReservationImp.class);
  private static Logger logger = LoggerFactory.getLogger(WxReservationImp.class);

  // 微信优惠券信息
  @Resource
  private com.kmzyc.b2b.dao.WxReservationDao wxReservationDao;

  // 查询店铺信息
  public List<WxKmrsShopInfo> findShopInfo() throws SQLException {
    return wxReservationDao.findAllShopInfo();
  }

  // 查询微信优惠券信息
  public Pagination findWxCouponListByPage(Pagination page) throws SQLException {
    Map<String, Object> condition = (Map<String, Object>) page.getObjCondition();
    logger.info("开始查询微信优惠券列表,shopId:" + condition.get("shopId") + ",dateBegin:"
        + condition.get("dateBegin") + ",dateEnd:" + condition.get("dateEnd"));
    page =
        wxReservationDao.findByPage("WX_RESERVATION.selectWxCoupon",
            "WX_RESERVATION.countWxCoupon", page);
    return page;
  }

  @Override
  public List<WxReservation> findWxCouponList(Map map) throws SQLException {

    return wxReservationDao.findAllWxCoupon(map);
  }

  /**
   * 查询移动话费充值数据
   */
  @Override
  public List<RefData> findRefDataList(Map map) throws SQLException {
    return wxReservationDao.findRefList(map);
  }
}
