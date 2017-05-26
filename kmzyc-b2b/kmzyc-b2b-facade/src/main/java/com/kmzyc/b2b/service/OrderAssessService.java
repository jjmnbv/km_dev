package com.kmzyc.b2b.service;

import com.km.framework.page.Pagination;
import com.kmzyc.b2b.model.OrderMain;
import com.kmzyc.b2b.vo.AppraisePoint;
import com.kmzyc.framework.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public interface OrderAssessService {

  /**
   * 订单打分、产品打分、产品订单综合评分
   * 
   * @param orderMain
   * @param prodAssess
   * @param contents
   * @param OrderAssess
   * @return
   */
  boolean saveOrderAssessAndProductAssess(OrderMain orderMain, String prodAssess,
                                          List<String> contents, String OrderAssess,
                                          List<String> appendContents, List<String> appraiseId) throws Exception;


  /**
   * 对产品评价的查询
   * 
   */
  Pagination findAppraisListByPage(Pagination page) throws SQLException;

  List<AppraisePoint> findAppraisPointBySkuId(Integer skuId) throws Exception;

  /**
   * 查询产品总评分
   * 
   * @param skuId
   * @return
   * @throws ServiceException
   */
  List<AppraisePoint> queryAssessPoint(Long skuId) throws ServiceException;

  /**
   * 判断订单的评价是否齐全，如果追加评价的个数等于订单的详细个数 则齐全 返回追平个数
   */
  int checkOrderAssessComplete(List<String> appraiseIdList) throws Exception;

  //  public int getScoreByCondition(String ruleCode, Integer scoreType, String loginAccount,
 //     HashMap map) throws Exception;

  //  public int checkAppriseComplete(List<String> appraiseIdList) throws Exception;

}
