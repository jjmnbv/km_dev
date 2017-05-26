package com.pltfm.sys.bean;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.kmzyc.commons.page.Page;
import com.pltfm.sys.dao.SysLogDAOImpl;
import com.pltfm.sys.model.SysLog;

/**
 * 类 SysLogBean 日志类
 * 
 * @author
 * @version 2.1
 * @since JDK1.5
 */
public class SysLogBean extends BaseBean {

  Logger log = Logger.getLogger(this.getClass());

  public SysLogBean() {
    super(); // 加载总的sqlmap配置文件
  }

  /**
   * 根据vo条件查询日志
   * 
   * @param Page ,SysLog
   * @return Page
   * @exception Exception
   */
  public Page searchPageByVo(Page pageParam, SysLog vo) throws Exception {
    int pageNo = pageParam.getPageNo();// 当前查询第几页
    if (pageNo == 0) pageNo = 1;// 首次查询第一页
    int pageSize = pageParam.getPageSize(); // 每页显示记录数
    int skip = (pageNo - 1) * pageSize + 1;
    int max = (pageNo - 1) * pageSize + pageSize;
    Page page = null;
    try {
      SysLogDAOImpl dao = new SysLogDAOImpl(sqlMap);
      vo.setSkip(skip);
      vo.setMax(max);
      page = dao.selectPageByVo(pageParam, vo);
      page.setPageNo(pageNo);
    } catch (SQLException e) {
      log.error(e);
      log.warn(e.getMessage());
      throw e;
    }
    return page;
  }

  /**
   * 添加日志，有问题抛出异常
   * 
   * @param SysLog
   * @return void
   * @exception Exception
   */
  public void addLogInfo_e(SysLog vo) throws Exception {
    try {
      SysLogDAOImpl dao = new SysLogDAOImpl(sqlMap);
      dao.insert(vo);
    } catch (SQLException e) {
      log.error(e);
      throw e;
    }
  }

  /**
   * 添加日志
   * 
   * @param SysLog
   * @return void
   * @exception Exception
   */
  public void addLogInfo(SysLog vo) throws Exception {
    try {
      SysLogDAOImpl dao = new SysLogDAOImpl(sqlMap);
      dao.insert(vo);
    } catch (SQLException e) {
      log.error(e);
      throw e;
    }
  }
}
