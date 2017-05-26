package com.kmzyc.b2b.service.member;

import java.sql.SQLException;

import com.km.framework.page.Pagination;
import com.kmzyc.b2b.vo.ScoreView;
import com.kmzyc.framework.exception.ServiceException;

public interface ScoreInfoService {

  /**
   * 根据查询条件进行分页查询
   * 
   * @param page
   * @return
   * @throws SQLException
   */
  public Pagination findPointsRecordByPage(Pagination page) throws SQLException;


  /**
   * 兑换优惠券
   * 
   * @throws Exception
   */
//  public int exChangeCoupon(Long loginId, Long couponId, Double scoreNumber, Integer couponValue)
//      throws ServiceException;

  /**
   * 获取我的积分详情
   * 
   * @param n_LoginId
   * @return
   * @throws Exception
   */
  public ScoreView findDetailMyScoreByLoginId(Integer n_LoginId) throws Exception;

}
