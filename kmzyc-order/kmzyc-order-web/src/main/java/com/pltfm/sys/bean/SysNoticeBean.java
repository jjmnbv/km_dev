package com.pltfm.sys.bean;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.kmzyc.commons.page.Page;
import com.pltfm.sys.dao.SysNoticeDAO;
import com.pltfm.sys.dao.SysNoticeDAOImpl;
import com.pltfm.sys.dao.SysNoticeSendDAO;
import com.pltfm.sys.dao.SysNoticeSendDAOImpl;
import com.pltfm.sys.model.SysNotice;
import com.pltfm.sys.model.SysNoticeSend;
import com.pltfm.sys.model.SysNoticeSendExample;
import com.pltfm.sys.util.DatetimeUtil;

/**
 * 类 SysNoticeBean 公告类
 * 
 * @author
 * @version 2.1
 * @since JDK1.5
 */
@SuppressWarnings("unchecked")
public class SysNoticeBean extends BaseBean {

  Logger log = Logger.getLogger(this.getClass());

  public SysNoticeBean() {
    super(); // 加载总的sqlmap配置文件
  }

  /**
   * 根据vo条件查询公告信息page
   * 
   * @param Page ,SysNotice
   * @return Page
   * @exception Exception
   */
  public Page searchPageByVo(Page pageParam, SysNotice vo) throws Exception {
    int pageNo = pageParam.getPageNo();// 当前查询第几页
    if (pageNo == 0) pageNo = 1; // 首次查询第一页
    int pageSize = pageParam.getPageSize(); // 每页显示记录数
    int skip = (pageNo - 1) * pageSize + 1;
    int max = (pageNo - 1) * pageSize + pageSize;
    vo.setSkip(skip);
    vo.setMax(max);
    Page page = null;
    // 处理like 条件
    if (vo.getNoticeTitle() != null && !vo.getNoticeTitle().equals(""))
      vo.setNoticeTitle("%" + vo.getNoticeTitle() + "%");
    else
      vo.setNoticeTitle(null);
    try {
      SysNoticeDAOImpl dao = new SysNoticeDAOImpl(sqlMap);
      page = dao.selectPageByVo(pageParam, vo);
      page.setPageNo(pageNo);// 当前查询第几页
    } catch (Exception e) {
      log.error(e);
      log.warn(e.getMessage());
      throw e;
    }
    return page;
  }

  /**
   * 新增公告
   * 
   * @param SysNotice
   * @return Integer
   * @exception
   */
  public Integer doSave(SysNotice sysnotice) throws Exception {
    try {
      SysNoticeDAOImpl dao = new SysNoticeDAOImpl(sqlMap);
      dao.insert(sysnotice);
    } catch (SQLException e) {
      log.error(e);
      log.warn(e.getMessage());
      throw e;
    }
    return sysnotice.getNoticeId();
  }

  /**
   * 删除所选 (软删除)
   * 
   * @param String []
   * @return void
   */
  public void doDelete(String[] ids) throws Exception {
    try {
      sqlMap.startTransaction();
      SysNoticeDAOImpl dao = new SysNoticeDAOImpl(sqlMap);
      // 开始循环删除
      if (ids != null && ids.length > 0) {
        for (int i = 0; i < ids.length; i++) {
          SysNotice vo = new SysNotice();
          vo.setNoticeId(Integer.valueOf(ids[i]));
          vo.setIsEnable("0");
          dao.updateByPrimaryKeySelective(vo);
        }
      }
      sqlMap.commitTransaction();
    } catch (SQLException e) {
      log.error(e);
      log.warn(e.getMessage());
      throw e;
    } finally {
      try {
        sqlMap.endTransaction();
      } catch (SQLException e) {
        log.error(e);
      }
    }
  }

  /**
   * 获取详细信息
   * 
   * @param Integer
   * @return SysNotice
   * @exception Exception
   */
  public SysNotice getDetail(Integer id) throws Exception {
    SysNotice sysNotice = null;
    try {
      SysNoticeDAOImpl dao = new SysNoticeDAOImpl(sqlMap);
      sysNotice = dao.selectByPrimaryKey(id);
    } catch (SQLException e) {
      log.error(e);
      log.warn(e.getMessage());
      throw e;
    }
    return sysNotice;
  }

  /**
   * 修改
   * 
   * @param SysNotice
   * @return SysNotice
   * @exception Exception
   */
  public SysNotice doUpdate(SysNotice sysnotice) throws Exception {
    try {
      SysNoticeDAOImpl dao = new SysNoticeDAOImpl(sqlMap);
      dao.updateByPrimaryKeySelective(sysnotice);
    } catch (SQLException e) {
      log.error(e);
      throw e;
    }
    return sysnotice;
  }

  /**
   * 发送公告（操作公告操作员关系表 sys_notice_send）
   * 
   * @param Integer ,String
   * @return Integer
   * @exception Exception
   */
  public void sendNotice(Integer noticeId, String userIdStr) throws Exception {
    log.warn("================ in SysNoticeBean addSysUserRole() ");
    try {
      sqlMap.startTransaction();
      // 删除该公告的旧关系
      SysNoticeSendDAO dao = new SysNoticeSendDAOImpl(sqlMap);
      SysNoticeSendExample exam = new SysNoticeSendExample();
      exam.createCriteria().andNoticeIdEqualTo(noticeId);
      dao.deleteByExample(exam);
      log.warn("---------------------------------------- 删除旧关系完毕！ noticeId=" + noticeId);
      // 添加该公告的新关系
      String[] userids = userIdStr.split(",");
      if (userids.length > 0) {
        for (int i = 0; i < userids.length; i++) {
          SysNoticeSend vo = new SysNoticeSend();
          vo.setNoticeId(noticeId);
          vo.setUserId(Integer.valueOf(userids[i]));
          vo.setIsEnable("1");
          vo.setIsRead("0");
          vo.setSendTime(DatetimeUtil.getCalendarInstance().getTime());
          dao.insert(vo);
        }
      }
      log.warn("---------------------------------------- 添加新关系完毕！！！");
      sqlMap.commitTransaction();
    } catch (SQLException e) {
      log.error(e);
      log.warn(e.getMessage());
      throw e;
    } finally {
      try {
        sqlMap.endTransaction();
      } catch (SQLException e) {
        log.error(e);
      }
    }
  }

  /**
   * 获取某个公告的发送关系列表
   * 
   * @param Integer
   * @return List
   * @exception Exception
   */
  public List getSendListByNoticeId(Integer noticeId) throws Exception {
    log.warn("================ in SysNoticeBean getSendListByNoticeId() ");
    List dataList = null;
    try {
      SysNoticeSendDAO dao = new SysNoticeSendDAOImpl(sqlMap);
      dataList = dao.selectSendListByNotice(noticeId);
      // SysNoticeSendExample exam = new SysNoticeSendExample();
      // exam.createCriteria().andNoticeIdEqualTo(noticeId).andIsEnableEqualTo("1");
      // dataList = dao.selectByExample(exam);
      log.warn("------------- sendList.size()=" + dataList.size());
    } catch (SQLException e) {
      log.error(e);
      log.warn(e.getMessage());
      throw e;
    }
    return dataList;
  }

  /**
   * 根据vo条件查询我的公告信息page
   * 
   * @param Page ,SysNotice
   * @return Page
   * @exception Exception
   */
  public Page searchMyNoticePageByVo(Page pageParam, SysNotice vo) throws Exception {
    int pageNo = pageParam.getPageNo();// 当前查询第几页
    if (pageNo == 0) pageNo = 1; // 首次查询第一页
    int pageSize = pageParam.getPageSize(); // 每页显示记录数
    int skip = (pageNo - 1) * pageSize + 1;
    int max = (pageNo - 1) * pageSize + pageSize;
    vo.setSkip(skip);
    vo.setMax(max);
    Page page = null;
    // 处理like 条件
    if (vo.getNoticeTitle() != null && !vo.getNoticeTitle().equals(""))
      vo.setNoticeTitle("%" + vo.getNoticeTitle() + "%");
    else
      vo.setNoticeTitle(null);
    try {
      SysNoticeDAOImpl dao = new SysNoticeDAOImpl(sqlMap);
      page = dao.searchMyNoticePageByVo(pageParam, vo);
      page.setPageNo(pageNo);// 当前查询第几页
    } catch (Exception e) {
      log.error(e);
      log.warn(e.getMessage());
      throw e;
    }
    return page;
  }

  /**
   * 删除我的公告 (软删除)
   * 
   * @param String []
   * @return void
   */
  public void doDeleteMyNotice(String[] ids, Integer userId) throws Exception {
    try {
      sqlMap.startTransaction();
      SysNoticeSendDAO dao = new SysNoticeSendDAOImpl(sqlMap);
      // 开始循环删除
      if (ids != null && ids.length > 0) {
        for (int i = 0; i < ids.length; i++) {
          SysNoticeSend vo = new SysNoticeSend();
          vo.setNoticeId(Integer.valueOf(ids[i]));
          vo.setUserId(userId);
          vo.setIsEnable("0");
          dao.updateByPrimaryKeySelective(vo);
        }
      }
      sqlMap.commitTransaction();
    } catch (SQLException e) {
      log.error(e);
      log.warn(e.getMessage());
      throw e;
    } finally {
      try {
        sqlMap.endTransaction();
      } catch (SQLException e) {
        log.error(e);
      }
    }
  }

  /**
   * 修改已读状态
   * 
   * @param Integer ,Integer
   * @return SysNotice
   * @exception Exception
   */
  public SysNoticeSend setIsRead(Integer noticeId, Integer userId) throws Exception {
    SysNoticeSend vo = new SysNoticeSend();
    try {
      SysNoticeSendDAO dao = new SysNoticeSendDAOImpl(sqlMap);
      vo.setNoticeId(noticeId);
      vo.setUserId(userId);
      vo.setIsRead("1");
      dao.updateByPrimaryKeySelective(vo);
    } catch (SQLException e) {
      log.error(e);
      throw e;
    }
    return vo;
  }

  /**
   * 获取主页上我的公告列表
   * 
   * @param Integer
   * @return List
   * @exception Exception
   */
  public List getMyNoticeList(Integer userId) throws Exception {
    log.warn("================ in SysNoticeBean getMyNoticeList() ");
    List dataList = null;
    try {
      SysNoticeDAO dao = new SysNoticeDAOImpl(sqlMap);
      dataList = dao.selectMyNoticeList(userId);
      log.warn("------------- myNoticeList.size()=" + dataList.size());
    } catch (SQLException e) {
      log.error(e);
      log.warn(e.getMessage());
      throw e;
    }
    return dataList;
  }
}
