package com.pltfm.sys.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kmzyc.commons.page.Page;
import com.pltfm.sys.dao.SysLogDAO;
import com.pltfm.sys.model.SysLog;
import com.pltfm.sys.service.SysLogService;

/**
 * 类 sysLogServiceImpl 日志类
 *
 * @author
 * @version 2.1
 * @since JDK1.5
 */
@Service
public class SysLogServiceImpl implements SysLogService {
  @Resource(name = "sysLogDAO")
  public SysLogDAO sysLogDAO;

  /**
   * 根据vo条件查询日志
   * 
   * @param Page,SysLog
   * @return Page
   * @exception Exception
   */
  @Override
public Page searchPageByVo(Page pageParam, SysLog vo) throws Exception {
    int pageNo = pageParam.getPageNo();// 当前查询第几页
    if (pageNo == 0) pageNo = 1;// 首次查询第一页
    int pageSize = pageParam.getPageSize(); // 每页显示记录数
    int skip = (pageNo - 1) * pageSize + 1;
    int max = (pageNo - 1) * pageSize + pageSize;
    Page page = null;
    vo.setSkip(skip);
    vo.setMax(max);
    page = sysLogDAO.selectPageByVo(pageParam, vo);
    page.setPageNo(pageNo);
    return page;
  }



  /**
   * 添加日志，有问题抛出异常
   * 
   * @param SysLog
   * @return void
   * @exception Exception
   */
  @Override
public void addLogInfo_e(SysLog vo) throws Exception {
    sysLogDAO.insert(vo);
  }



  /**
   * 添加日志
   * 
   * @param SysLog
   * @return void
   * @exception Exception
   */
  @Override
public void addLogInfo(SysLog vo) throws Exception {
    sysLogDAO.insert(vo);
  }
}
