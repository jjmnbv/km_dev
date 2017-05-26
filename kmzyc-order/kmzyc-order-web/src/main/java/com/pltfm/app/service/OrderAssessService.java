package com.pltfm.app.service;

import java.util.List;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.entities.OrderAssessDetail;
import com.pltfm.app.entities.OrderAssessInfo;

@SuppressWarnings("unchecked")
public interface OrderAssessService {
  /**
   * 分页查询时的总数
   * 
   * @param map
   * @return
   * @throws ServiceException
   */
  int listCount(Map map) throws ServiceException;

  /**
   * 分页查询list
   * 
   * @param map
   * @return
   * @throws ServiceException
   */
  List listOrder(Map map) throws ServiceException;

  /**
   * 保存对象
   * 
   * @return
   * @throws ServiceException
   */
  Long save(OrderAssessInfo record) throws ServiceException;

  /**
   * 批量插入订单评价明细
   * 
   * @param oadList
   * @throws ServiceException
   */
  void insertList(List<OrderAssessDetail> oadList) throws ServiceException;

  /**
   * 通过评价ID查询评价明细
   * 
   * @param assessInfoID
   * @return
   * @throws ServiceException
   */
  List<OrderAssessDetail> selectDetailByAssessID(Long assessInfoID) throws ServiceException;

  /**
   * 通过orderCode查询订单评价
   * 
   * @param orderCode
   * @return
   * @throws ServiceException
   */
  OrderAssessInfo selectByOrderCode(String orderCode) throws ServiceException;

  /**
   * 删除订单评价
   * 
   * @param orderCode
   * @throws ServiceException
   */
  public void delete(String orderCode) throws ServiceException;

  /**
   * 生成订单评价
   * 
   * @param orderAssessInfo 评价实体
   * @param assessType 维度类型
   * @param assessName 维度名称
   * @param state 得分数组
   * @param orderCode 订单编码
   * @throws ServiceException
   */
  public void createAssessInfo(OrderAssessInfo orderAssessInfo, String[] assessType,
      String[] assessName, String[] state, String orderCode) throws ServiceException;

  /**
   * 删除评价并更新订单状态
   * 
   * @param orderCode
   * @throws ServiceException
   */
  public void deleteAssessInfo(String orderCode) throws ServiceException;

  /**
   * 批量插入订单评价
   * 
   * @param oaList
   */
  public int bathInsertOrderAssess(List<Object> oaList) throws ServiceException;
}
