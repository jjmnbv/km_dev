package com.pltfm.remote.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.kmzyc.user.remote.service.UserNwesCustomService;
import com.pltfm.app.dao.NwesCsReplyDAO;
import com.pltfm.app.dao.NwesCustomServiceDAO;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.vobject.NwesCustomService;

/***
 * 客服远程接口实现类
 */
@Component(value = "userNwesCustomService")
public class UserNwesCustomServiceImpl implements UserNwesCustomService {
  /**
   * 日志类
   */
  Logger log = LoggerFactory.getLogger(this.getClass());
  /**
   * 服务信息DAO
   */
  @Resource(name = "nwesCustomServiceDAO")
  private NwesCustomServiceDAO nwesCustomServiceDAO;
  /**
   * 回复信息DAO
   */
  @Resource(name = "nwesCsReplyDAO")
  private NwesCsReplyDAO nwesCsReplyDAO;

  /***
   * 客服远程接口
   * 
   * @param 客服信息
   * @return 返回值
   * @throws Exception
   */
  public int addUserNwesCustom(NwesCustomService nwesCustomService) throws Exception {
    if (nwesCustomService != null) {
      nwesCustomService.setReplyStatus(0);
      nwesCustomService.setCustomServiceDate(DateTimeUtils.getCalendarInstance().getTime());
      return nwesCustomServiceDAO.insert(nwesCustomService);
    }
    return 0;
  }
}
