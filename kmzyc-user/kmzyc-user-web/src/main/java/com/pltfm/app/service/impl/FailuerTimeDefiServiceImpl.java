package com.pltfm.app.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.pltfm.app.dao.FailuerTimeDefiDAO;
import com.pltfm.app.service.FailureTimeDefiService;
import com.pltfm.app.vobject.FailureTimeDefi;

/**
 * 手机随机码信息业务逻辑类
 * 
 * @author cjm
 * @since 2013-7-10
 */
@Component(value = "failureTimeDefiService")
public class FailuerTimeDefiServiceImpl implements FailureTimeDefiService {
  /**
   * 手机随机码信息DAO接口
   */
  @Resource(name = "failuerTimeDefiDAO")
  private FailuerTimeDefiDAO failuerTimeDefiDAO;

  public FailuerTimeDefiDAO getFailuerTimeDefiDAO() {
    return failuerTimeDefiDAO;
  }

  public void setFailuerTimeDefiDAO(FailuerTimeDefiDAO failuerTimeDefiDAO) {
    this.failuerTimeDefiDAO = failuerTimeDefiDAO;
  }

  @Override
  public FailureTimeDefi selectByPrimaryKey(Integer failure_Time_Id) throws Exception {
    // TODO Auto-generated method stub
    return failuerTimeDefiDAO.selectByPrimaryKey(failure_Time_Id);
  }



}
