package com.kmzyc.b2b.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.b2b.model.RefData;
import com.kmzyc.b2b.vo.WxKmrsShopInfo;
import com.kmzyc.b2b.vo.WxReservation;
import com.km.framework.page.Pagination;

public interface WxReservationService {

  // 查询店铺信息
  public List<WxKmrsShopInfo> findShopInfo() throws SQLException;

  // 查询微信优惠券信息 分页
  public Pagination findWxCouponListByPage(Pagination page) throws SQLException;

  // 查询微信优惠券信息导出 不用分页
  public List<WxReservation> findWxCouponList(Map map) throws SQLException;

  // 查询移动充值信息
  public List<RefData> findRefDataList(Map map) throws SQLException;
}
