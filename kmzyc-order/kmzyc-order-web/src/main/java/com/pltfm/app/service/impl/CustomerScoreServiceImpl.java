package com.pltfm.app.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kmzyc.user.remote.service.UserGrowingService;
import com.pltfm.app.service.CustomerScoreService;

@Service("customerScoreService")
public class CustomerScoreServiceImpl implements CustomerScoreService {
  private Logger log = Logger.getLogger(CustomerScoreServiceImpl.class);
  
  @Autowired
  private UserGrowingService  userGrowingService;
  
  /*@Override
  public Integer addUserScore(String ruleCode, Integer scoreType, String loginAccount,
      Map<String, String> paramsMap) {
    try {
        return userGrowingService.updateUserScoreInfo(ruleCode, scoreType, loginAccount, paramsMap);
    } catch (ServiceException e) {
      log.error("增加用户库存Service异常！", e);
    } catch (Exception e) {
      log.error("增加用户库存异常！", e);
    }
    return 0;
  }*/

}
