package com.pltfm.app.remote.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.order.remote.OrderAlterQryRemoteService;
import com.pltfm.app.dao.OrderAlterDAO;
import com.pltfm.app.dao.OrderAlterPayStatementDAO;
import com.pltfm.app.entities.OrderAlter;
import com.pltfm.app.entities.OrderItem;
import com.pltfm.app.entities.OrderMain;
import com.pltfm.app.service.OrderAlterOperateStatementService;
import com.pltfm.app.service.OrderItemQryService;
import com.pltfm.app.service.OrderQryService;
import com.pltfm.app.service.OrderReturnService;
import com.pltfm.app.util.OrderAlterDictionaryEnum;
import com.pltfm.app.util.SysConstants;
import com.pltfm.app.vobject.OrderAlterVo;
import com.pltfm.sys.util.ErrorCode;

@Service("orderAlterQryRemoteService")
@SuppressWarnings("unchecked")
public class OrderAlterQryRemoteServiceImpl implements OrderAlterQryRemoteService {
  private static Logger logger = Logger.getLogger(OrderAlterQryRemoteServiceImpl.class);
  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;
  @Resource
  private OrderAlterOperateStatementService orderAlterOperateStatementService;
  @Resource
  private OrderAlterDAO orderAlterDAO;
  @Resource
  private OrderReturnService orderReturnService;
  @Resource
  private OrderItemQryService orderItemQryService;
  @Resource
  private OrderAlterPayStatementDAO orderAlterPayStatementDAO;
  @Resource
  private OrderQryService orderQryService;

  @Override
  public List listOrderAlterOperates(String alterCode) throws ServiceException {
    List list = null;
    try {
      list = orderAlterOperateStatementService.listOrderAlterOperates(alterCode);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTER_OPERATE_ASSEMBLY_RETURNS_ERROR,
          "根据退换货批次号获取退换货操作流水发生错误：" + e.getMessage());
    }
    return list;
  }

  /**
   * 查询退换货，提供给咨询工具
   * 
   * @param map
   * @return
   * @throws ServiceException
   */
  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
  public List queryOrderAlterListForConsultation(Map<String, Object> map) throws ServiceException {
    try {
      return orderAlterDAO.selectOrderReturnByMap(map);
    } catch (Exception e) {
      logger.error("获取退换货列表异常", e);
      throw new ServiceException(ErrorCode.INTER_RETURNS_QUERY_ERROR, "获取退换货列表异常：" + e.getMessage());
    }
  }

  @Override
  public List<OrderAlterVo> queryOrderAlterListByMap(Map<String, Object> map) throws ServiceException {
    try {
      List<OrderAlterVo> alterList = new ArrayList<OrderAlterVo>();
      alterList =  orderReturnService.listAlter(map);
      return alterList;
    } catch (Exception e) {
      logger.error("获取退换货列表异常", e);
      throw new ServiceException(ErrorCode.INTER_RETURNS_QUERY_ERROR, "获取退换货列表异常：" + e.getMessage());
    }
  }

  @Override
  public int countOrderAlterListByMap(Map<String, Object> map) throws ServiceException {
    try {
      return orderReturnService.listCount(map);
    } catch (Exception e) {
      logger.error("统计退换货数量异常", e);
      throw new ServiceException(ErrorCode.INTER_RETURNS_QUERY_ERROR, "统计退换货数量异常：" + e.getMessage());
    }
  }

  @Override
  public OrderAlter getOrderAlterByCode(String orderCode) throws ServiceException {
    try {
      return orderAlterOperateStatementService.getOrderAlterByCode(orderCode);
    } catch (Exception e) {
      logger.error("查询退换货明细异常", e);
      throw new ServiceException(ErrorCode.INTER_RETURNS_QUERY_ERROR, "查询退换货明细异常：" + e.getMessage());
    }
  }

  @Override
  public List listOrderAlterPays(String alterCode) throws ServiceException {
    List list = null;
    try {
      list = orderAlterOperateStatementService.listOrderAlterPays(alterCode);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTER_OPERATE_ASSEMBLY_RETURNS_ERROR,
          "根据退换货批次号获取退换货支付流水发生错误：" + e.getMessage());
    }
    return list;
  }

  @Override
  public int checkOrderAlter(Integer operatorType, String operator, String alterCode, Short type,
      BigDecimal fareSubsidy,BigDecimal returnMoney,BigDecimal returnFare,BigDecimal returnSum, BigDecimal preferentialAmount, String comment)
      throws ServiceException {
    try {
      
      OrderAlter oa = orderAlterOperateStatementService.getOrderAlterByCode(alterCode);
      if(oa.getAlterType()==4){
        //预售超时审核
        int result =orderReturnService.alterCKYS(operatorType.equals(1)?operator:SysConstants.SYS, alterCode, type, comment);
        if(result!=1)
          return 0;
        else
          return 1;        
      }else {
        
        orderReturnService.alterCK(operatorType.equals(1)?operator:SysConstants.SYS, alterCode, type, fareSubsidy, returnMoney,returnFare,returnSum,
            preferentialAmount, comment);
      }
      return 1;
      /*  
      if (operatorType.equals(1)) {// 操作类型1位供应商
        orderReturnService.alterCK(operator, alterCode, type, fareSubsidy, returnMoney,returnFare,returnSum,
            preferentialAmount, comment);
      } else {
        orderReturnService.alterCK(SysConstants.SYS, alterCode, type, fareSubsidy, returnMoney,returnFare,returnSum,
            preferentialAmount, comment);
      }
      return 1;
      */
    } catch (Exception e) {
      e.printStackTrace();
    }
    return 0;
  }

  @Override
  public int changeAlterStatus(Integer operatorType, String sysOperate, OrderAlter oa)
      throws ServiceException {
    int result = 0 ;
    try {
       
       /*if (operatorType.equals(1)) {
         orderAlterOperateStatementService.changeAlterStatus(sysOperate, oa);
       } else {
        orderAlterOperateStatementService.changeAlterStatus(SysConstants.SYS, oa);
       }*/
      OrderAlter newOrderAlter = oa;
      if(newOrderAlter.getProposeStatus().intValue() == OrderAlterDictionaryEnum.Propose_Status.Backpay
          .getKey()){
          //确认退款
          OrderAlter oldOrderAlter = orderAlterDAO.selectByAlterCode(newOrderAlter.getOrderAlterCode());
          OrderMain omain = orderQryService.getOrderByCode(oldOrderAlter.getOrderCode());
          if(omain.getOrderType()==7){
            //预售订单
            
            Map map =orderAlterOperateStatementService.changeAlterStatusYS(operatorType.equals(1)?sysOperate:SysConstants.SYS, oa);
            if(map.containsKey("1")&&!map.get("1").toString().contains("失败")){
              result = 1;
            }else{ 
              result = 0;
            }
            
          }else {
            result=orderAlterOperateStatementService.changeAlterStatus(operatorType.equals(1)?sysOperate:SysConstants.SYS, oa);
          }
          
      }else {
        //非确认退款走原来路径
        result=orderAlterOperateStatementService.changeAlterStatus(operatorType.equals(1)?sysOperate:SysConstants.SYS, oa);
      }
      
      if(result!=1)
        result=0;
      return result;
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }


  @Override
  public OrderItem getOrderItemById(Long itemId) throws ServiceException {
    try {
      return orderItemQryService.getOrderItemById(itemId);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTER_OPERATE_ASSEMBLY_RETURNS_ERROR,
          "根据退换货批次号获取商品详情发生错误：" + e.getMessage());
    }
  }

  @Override
  public List getPhotoByBatchNo(String photoBatchNo) throws ServiceException {
    try {
      return orderReturnService.getPhotoByBatchNo(photoBatchNo);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTER_OPERATE_ASSEMBLY_RETURNS_ERROR,
          "根据退换货号获取商品图片发生错误：" + e.getMessage());
    }
  }

  @Override
  public boolean checkIsAdditional(String orderAlterCode) throws ServiceException {
    try {
      return orderAlterPayStatementDAO.checkIsAdditional(orderAlterCode);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTER_OPERATE_ASSEMBLY_RETURNS_ERROR,
          "根据退换货号判断是否需要补单异常：" + e.getMessage());
    }
  }

  @Override
  public boolean isSuit(String orderCode) throws ServiceException {
    try {
      return orderItemQryService.isSuit(orderCode);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTER_OPERATE_ASSEMBLY_RETURNS_ERROR,
          "根据退换货号判断订单是否包含活动商品 发生错误：" + e.getMessage());
    }
  }

  @Override
  public BigDecimal selectReturnFare(String orderCode) throws ServiceException {
    BigDecimal returnfareSum = BigDecimal.ZERO;
    returnfareSum = orderAlterOperateStatementService.selectReturnFare(orderCode);
    return returnfareSum;
  }
}
