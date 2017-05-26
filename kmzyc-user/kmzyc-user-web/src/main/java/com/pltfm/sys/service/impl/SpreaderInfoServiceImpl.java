package com.pltfm.sys.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.user.remote.service.CustomerRemoteService;
import com.pltfm.app.vobject.BnesAcctTransactionQuery;
import com.pltfm.app.vobject.SpreaderRPInfoCriteria;
import com.pltfm.sys.dao.SpreaderRPInfoDAO;
import com.pltfm.sys.service.SpreaderInfoService;

/**
 * 推广者服务接口
 * 
 * @author xys
 *
 */
@Service("spreaderInfoService")
public class SpreaderInfoServiceImpl implements SpreaderInfoService {
  // 日志记录
  private static final Logger logger = LoggerFactory.getLogger(SpreaderInfoServiceImpl.class);
  @Resource
  private SpreaderRPInfoDAO spreaderRPInfoDAO;
  @Resource
  private CustomerRemoteService customerRemoteService;


  @Override
  public void cleanMicroBussinessRP(SpreaderRPInfoCriteria criteria) throws ServiceException {
    // 获取符合条件的用户loginId 和已获得的红包数量list(从活动开始日期起，截止结算日期止，未在线支付5元以上的用户)
    List<HashMap> todoListTemp = null;
    try {
      todoListTemp = spreaderRPInfoDAO.selectUserRpInfoList(criteria);
      while (todoListTemp != null) {// 如果查询的结果集不为空，则执行清除红包的逻辑（每次1000条）
        for (int i = 0; i < todoListTemp.size(); i++) {
          BnesAcctTransactionQuery query = new BnesAcctTransactionQuery();
          query.setLoginId(Integer.parseInt(todoListTemp.get(i).get("N_LOGIN_ID").toString()));
          query.setAmount(new BigDecimal(-5.18 * Integer.parseInt(todoListTemp.get(i).get("RPCOUNT").toString())));// 红包金额5.18*获得的红包个数（负数为减法）
          query.setContent("云店518红包清零");
          query.setType(-4);// 交易类型（-1,'云店注册红包',-2,'云店引荐红包',-3,'518现金红包'，-4,'清除518现金红包'）
          try {
            customerRemoteService.UpdateUserBalance(query, 2);
          } catch (Exception e) {
            logger.error("清除用户红包异常，loginId:" + todoListTemp.get(i).get("N_LOGIN_ID"), e);
            throw new ServiceException(0, "清除用户红包异常", null);
          }
        }

        // 线程休息1second，继续从数据库读出数据
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          logger.error("清除红包后，线程sleep异常", e);
        }
        todoListTemp = spreaderRPInfoDAO.selectUserRpInfoList(criteria);
      }

    } catch (SQLException e) {
      logger.error("获取需清除红包的用户信息异常", e);
      throw new ServiceException(0, "获取需清除红包的用户信息", null);
    }


  }
}
