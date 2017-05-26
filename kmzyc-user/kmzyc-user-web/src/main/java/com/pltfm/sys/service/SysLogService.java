package com.pltfm.sys.service;

import com.kmzyc.commons.page.Page;
import com.pltfm.sys.model.SysLog;

/**
 * 类 sysLogServiceImpl 日志类
 *
 * @author
 * @version 2.1
 * @since JDK1.5
 */
public interface SysLogService {
  /**
   * 根据vo条件查询日志
   * 
   * @param Page,SysLog
   * @return Page
   * @exception Exception
   */
  public Page searchPageByVo(Page pageParam, SysLog vo) throws Exception;



  /**
   * 添加日志，有问题抛出异常
   * 
   * @param SysLog
   * @return void
   * @exception Exception
   */
  public void addLogInfo_e(SysLog vo) throws Exception;



  /**
   * 添加日志
   * 
   * @param SysLog
   * @return void
   * @exception Exception
   */
  public void addLogInfo(SysLog vo) throws Exception;
}
