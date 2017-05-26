package com.pltfm.sys.service;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.vobject.SpreaderRPInfoCriteria;

/**
 * 推广者服务接口
 * 
 * @author xys
 *
 */
public interface SpreaderInfoService {

  /**
   * 清除微商用户红包（截止清除日期仍未使用的红包） 参数：活动开始时间、活动结束时间、红包清理时间
   * 
   * @return
   * @throws ServiceException
   */
  public void cleanMicroBussinessRP(SpreaderRPInfoCriteria criteria) throws ServiceException;
}
