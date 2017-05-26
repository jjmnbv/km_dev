package com.pltfm.app.remote.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.order.remote.OrderCreateRemoteService;
import com.pltfm.app.dao.OrderMainDAO;
import com.pltfm.app.entities.OrderItem;
import com.pltfm.app.entities.OrderMain;
import com.pltfm.app.entities.OrderPayStatement;
import com.pltfm.app.service.AgencyCreateOrderService;
import com.pltfm.app.service.OrderQryService;
import com.pltfm.app.service.ReturnFundForKJService;
import com.pltfm.app.vobject.OrderPreferentialVO;
import com.pltfm.sys.util.ErrorCode;

@Service("orderCreateRemoteService")
public class OrderCreateRemoteServiceImpl implements OrderCreateRemoteService {
  private static final long serialVersionUID = 1L;
  @Resource
  private AgencyCreateOrderService orderService;
  @Resource
  private OrderMainDAO orderMainDAO;
  @Resource
  private ReturnFundForKJService returnFundForKJService;
  @Resource
  private OrderQryService orderQryService;

  @Override
  public String createOrder(OrderMain order, List<OrderItem> orderItemList,
      List<OrderPreferentialVO> orderPreferentialVOList,
      List<OrderPayStatement> orderPayStatementList) throws ServiceException {
    String result = null;
    try {
      result =
          orderService.agencyCreateOrder(order, orderItemList, orderPreferentialVOList,
              orderPayStatementList);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTER_SINGLE_ERROR, "生成订单（普通商品）发生错误：" + e.getMessage(),e);
    }
    return result;
  }

  @Override
  public String createOrderForPrize(OrderMain order, List<OrderItem> orderItemList,
      List<OrderPreferentialVO> orderPreferentialVOList,
      List<OrderPayStatement> orderPayStatementList) throws ServiceException {
    String result = null;
    try {
      result =
          orderService.agencyCreateOrderForPrize(order, orderItemList, orderPreferentialVOList,
              orderPayStatementList);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTER_SINGLE_ERROR, "生成订单（奖品）发生错误：" + e.getMessage());
    }
    return result;
  }

  @Override
  public String copyDateToOrder(String orderCode) throws ServiceException {
    String errorStr = "";
    if(null == orderCode || "".equals(orderCode)){
    	 throw new ServiceException(ErrorCode.INTER_SINGLE_ERROR,"订单编号为空!" );
    }
    try{
      Long omr = orderMainDAO.copyToOrdermain(orderCode);
      if(null != omr && omr.compareTo(1L)<0){
        errorStr = "插入到订单表记录数为0";
      }
      Long oir = orderMainDAO.copyToOrderitem(orderCode);
      if(null != oir && oir.compareTo(1L)<0){
        errorStr = "插入到订单明细表记录数为0";
      }
      Long oos = orderMainDAO.copyToOrderOperate(orderCode);
      if(null != oos && oos.compareTo(1L)<0){
        errorStr = "插入到订单操作流水表记录数为0";
      }
      Long ops = orderMainDAO.copyToOrderpaystatment(orderCode);
      
      // 砍价订单同步订单信息到总部系统
      /*删除总部会员对接 try {
        List<String> lstOrderCode = new ArrayList<String>();
        lstOrderCode.add(orderCode);
        orderQryService.syncOrderInfo2Base(lstOrderCode);
      } catch (Exception e) {
        errorStr = "订单支付成功同步订单信息到总部系统异常!";
      }*/
      if(!"".equals(errorStr)){
        throw new ServiceException(ErrorCode.INTER_SINGLE_ERROR,errorStr );
      }
      return "SUCCESS";
    }catch (Exception e) {
      throw new ServiceException(ErrorCode.INTER_SINGLE_ERROR, "数据拷贝到订单系统相关表失败！" + e.getMessage());
    }
    
  }

  @Override
  public String returnFund(String orderCode) throws ServiceException {
    
     try {
      orderMainDAO.copyToOrderpaystatment(orderCode);
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  
    String restr = returnFundForKJService.returnFund(orderCode);
    return restr;
  }

  @Override
  public String copyDateToOrderPayStatement(String orderCode) throws ServiceException {
    String errorStr = "";
    try {
      Long ops = orderMainDAO.copyToOrderpaystatment(orderCode);
      if(null != ops && ops.compareTo(1L)<0){
        errorStr = "插入到订单支付流水表记录数为0";
      }
      if(!"".equals(errorStr)){
        throw new ServiceException(ErrorCode.INTER_SINGLE_ERROR,errorStr );
      }
      return "SUCCESS";
    } catch (SQLException e) {
      throw new ServiceException(ErrorCode.INTER_SINGLE_ERROR, "数据拷贝到订单系统相关表失败！" + e.getMessage());
    }
   
    
  }

}
