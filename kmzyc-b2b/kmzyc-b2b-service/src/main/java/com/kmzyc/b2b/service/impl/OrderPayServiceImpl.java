package com.kmzyc.b2b.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kmzyc.b2b.model.OrderPayStatement;
import com.kmzyc.b2b.model.PayCommonObject;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.service.LoginService;
import com.kmzyc.b2b.service.OrderMainService;
import com.kmzyc.b2b.service.OrderPayService;
import com.kmzyc.b2b.service.OrderPayStatementService;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.exception.ServiceException;
import com.kmzyc.order.remote.OrderPayRemoteService;
import com.kmzyc.user.remote.service.CustomerRemoteService;
import com.pltfm.app.util.OrderDictionaryEnum;

// import com.km.framework.common.util.RemoteTool;

@Service
public class OrderPayServiceImpl implements OrderPayService {
  // private static Logger log = LoggerFactory.getLogger(OrderPayServiceImpl.class);
  private static Logger log = LoggerFactory.getLogger(OrderPayServiceImpl.class);
  
  private static DateTimeFormatter df =DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");// 设置日期格式
  
  @Resource(name = "orderPayStatementServiceImpl")
  private OrderPayStatementService orderPayStatementService;
  @Resource(name = "orderMainServiceImpl")
  private OrderMainService orderMainService;
  @Resource(name = "loginServiceImp")
  private LoginService loginService;

  @Resource
  private OrderPayRemoteService orderPayRemoteService;
  @Resource
  private CustomerRemoteService customerRemoteService;

  /**
   * 此方法为以下情况调用 1、余额+优惠券支付==订单金额 2 、从银行回来 如果使用余额+优惠券支付，只用调用orderPayRemoteService
   * .batchPay(paymentVO);的时候paymentVO中的list传入优惠券支付流水就可以了 如果从银行回来 调用orderPayRemoteService
   * .batchPay(paymentVO);的时候paymentVO中的list传入优惠券和银行支付流水
   * 
   * @param orderCode
   * @param loginId
   * @param payCommon
   * @return
   * @throws ServiceException
   */
  @Override
public Boolean orderRemotePay(String orderCode, Long loginId, PayCommonObject payCommon)
      throws ServiceException {
    if (orderCode == null || loginId == null || loginId == 0) {
      return false;
    }

    try {
      Map<String, String> paramMap = new HashMap<>();
      paramMap.put("orderCode", orderCode);

      List<OrderPayStatement> orderPayStatementList =
          orderPayStatementService.getByOrderCode(paramMap);
      List<com.pltfm.app.entities.OrderPayStatement> remotePayStatementList = new ArrayList<>();

      com.pltfm.app.vobject.PaymentVO paymentVO = new com.pltfm.app.vobject.PaymentVO();
      paymentVO.setOrderCode(orderCode);
      paymentVO.setFlag(Constants.ORDER_PAY_FLAG_PAYMENT);

      User user = loginService.queryUserByLoginId(loginId.toString());
      if (user.getCustomerTypeId().intValue() == Constants.CUSTOMER_TYPE_TOURIST.intValue()) {
        paymentVO.setAccount("游客");
      } else {
        paymentVO.setAccount(user.getLoginAccount());
      }
      // OrderPayRemoteService orderPayRemoteService =
      // (OrderPayRemoteService) RemoteTool.getRemote(Constants.REMOTE_SERVICE_ORDER,
      // "orderPayService");
      if (null != orderPayStatementList && orderPayStatementList.size() > 0) {
        for (OrderPayStatement statement : orderPayStatementList) {
          if (statement.getPaymentWay().intValue() != Constants.PAY_METHOD_BALANCE.intValue()
              && statement.getState().intValue() != OrderDictionaryEnum.OrderPayState.Ready
                  .getKey()) {
            statement.setPayStatementNo(null);
            if (statement.getPaymentWay().intValue() != OrderDictionaryEnum.OrderPayMethod.Coupon
                .getKey()) {// 优惠券支付流水时，不改变状态，在batch中改变为1
              statement.setState(Constants.ORDER_PAY_STATE_SUCCESS);
            }
            remotePayStatementList.add(statement.transFormToRemoteOrderPayStatement());
          } else {
            paymentVO.setOrderMoney(statement.getOrderMoney());
          }

          // 尾款支付时，在支付流水信息里添加支付信息说明
          if (null != statement.getYsFlage() && statement.getYsFlage().equals("2")) {
            statement.setPayInfo("尾款支付的实际单号为" + orderCode + "a");
          }
        }

      }
      if (payCommon != null) {
        genThirdPayStatement(orderCode, payCommon, remotePayStatementList, user);
      }
      paymentVO.setOrderPayStatementList(remotePayStatementList);
      paymentVO.setLogInId(loginId);
      int result = 0;
      try {
        log.info("==订单支付回调:调用order远程接口batchPay开始==" + LocalDateTime.now().format(df));
        result = orderPayRemoteService.batchPay(paymentVO);
        log.info("==订单支付回调:调用order远程接口batchPay结束==" + LocalDateTime.now().format(df));
            } catch (com.kmzyc.commons.exception.ServiceException e) {
        log.error(e.getMessage());
      }
      if (result == 1) {
        try {
          if (1 == orderMainService.queryUserBuyNum(loginId)) {
            // CustomerRemoteService customerRemoteService =
            // (CustomerRemoteService) RemoteTool.getRemote(Constants.REMOTE_SERVICE_CUSTOMER,
            // "customerRemoteService");
            int rs = customerRemoteService.userFisrtShop(loginId.intValue());
            if (rs == 1) {
              log.info("受邀用户" + loginId + "首次购物分销业务处理成功");
            } else if (rs == -1) {
              log.error("受邀用户" + loginId + "首次购物分销业务处理发生异常");
            } else {
              log.info(loginId + "不是受邀用户");
            }
          }
        } catch (Exception e) {
          log.error("受邀用户首次购物分销业务处理发生异常", e);
        }
        return true;
      } else {
        return false;
      }
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }


  /**
   * 第三方支付流水
   * 
   * @param orderCode
   * @param payCommon
   * @param remotePayStatementList
   * @param user
   */
  private void genThirdPayStatement(String orderCode, PayCommonObject payCommon,
      List<com.pltfm.app.entities.OrderPayStatement> remotePayStatementList, User user) {
    com.pltfm.app.entities.OrderPayStatement remoteStatement =
        new com.pltfm.app.entities.OrderPayStatement();
    remoteStatement.setOrderCode(orderCode);
    remoteStatement.setAccount(user.getLoginAccount());
    remoteStatement.setState(Constants.ORDER_PAY_STATE_SUCCESS);
    remoteStatement.setBankName(payCommon.getBankChineseName());
    remoteStatement.setBankCode(payCommon.getBankId());
    remoteStatement.setOutsidePayStatementNo(payCommon.getTransitionNO());
    remoteStatement.setOrderMoney(new BigDecimal(payCommon.getMoneyStr()));
    remoteStatement.setFlag(Constants.ORDER_PAY_FLAG_PAYMENT);
    remoteStatement.setPaymentWay(Constants.PAY_METHOD_ONLINE_PLATFORM);
    remoteStatement.setCreateDate(new Date());
    remoteStatement.setEndDate(new Date());
    String[] extInfo = payCommon.getExtInfo().split("\\|");

    String platFormCode = null;
    String platFormName = null;
    String ysFlag = null;
    if (extInfo.length >= 3) {
      platFormCode = extInfo[2];
      platFormName = Constants.getPlatformName(platFormCode);
    }
    if(extInfo.length == 4){
      ysFlag = extInfo[3];
      if(null != ysFlag && "1".equals(ysFlag)){
        remoteStatement.setPayInfo("支付定金成功！支付号:"+orderCode);
      }else if(null != ysFlag && "2".equals(ysFlag)){
        remoteStatement.setPayInfo("支付尾款成功！支付号:"+orderCode+"a");
      }
    }
    remoteStatement.setPlatFormCode(platFormCode);
    remoteStatement.setPlatFormName(platFormName);
    remoteStatement.setYsFlage(ysFlag);//预售支付标识
    remotePayStatementList.add(remoteStatement);
  }


  @Override
  public Boolean orderRemotePayForYsDeposit(String orderCode, Long loginId,
      PayCommonObject payCommon) throws ServiceException {

    if (orderCode == null || loginId == null || loginId == 0) {
      return false;
    }
//    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
    try {
      Map<String, String> paramMap = new HashMap<>();
      paramMap.put("orderCode", orderCode);

      List<OrderPayStatement> orderPayStatementList =
          orderPayStatementService.getByOrderCode(paramMap);
      List<com.pltfm.app.entities.OrderPayStatement> remotePayStatementList = new ArrayList<>();

      com.pltfm.app.vobject.PaymentVO paymentVO = new com.pltfm.app.vobject.PaymentVO();
      paymentVO.setOrderCode(orderCode);
      paymentVO.setFlag(Constants.ORDER_PAY_FLAG_PAYMENT);
      
      //设置本次支付金额
      if(payCommon.getMoneyStr()!=null&&!"".equals(payCommon.getMoneyStr())){
    	  paymentVO.setOrderMoney(new BigDecimal(payCommon.getMoneyStr()));
      }

      User user = loginService.queryUserByLoginId(loginId.toString());
      if (user.getCustomerTypeId().intValue() == Constants.CUSTOMER_TYPE_TOURIST.intValue()) {
        paymentVO.setAccount("游客");
      } else {
        paymentVO.setAccount(user.getLoginAccount());
      }
      // OrderPayRemoteService orderPayRemoteService =
      // (OrderPayRemoteService) RemoteTool.getRemote(Constants.REMOTE_SERVICE_ORDER,
      // "orderPayService");
      if (null != orderPayStatementList && orderPayStatementList.size() > 0) {

        genThirdPayStatement(orderCode, payCommon, remotePayStatementList, user);
        paymentVO.setOrderPayStatementList(remotePayStatementList);
        paymentVO.setLogInId(loginId);
//        int result = 0;
        try {

//          result =
                  orderPayRemoteService.orderRemotePayForYsDeposit(paymentVO);

                } catch (com.kmzyc.commons.exception.ServiceException e) {
          log.error(e.getMessage());
        }
      }
    } catch (Exception e) {
      throw new ServiceException(e);
    }
    return true;
  }
}
