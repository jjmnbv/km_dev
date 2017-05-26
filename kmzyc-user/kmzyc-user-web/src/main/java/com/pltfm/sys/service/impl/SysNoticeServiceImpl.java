package com.pltfm.sys.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.commons.page.Page;
import com.pltfm.sys.dao.SysNoticeDAO;
import com.pltfm.sys.dao.SysNoticeSendDAO;
import com.pltfm.sys.model.SysNotice;
import com.pltfm.sys.model.SysNoticeSend;
import com.pltfm.sys.model.SysNoticeSendExample;
import com.pltfm.sys.service.SysNoticeService;
import com.pltfm.sys.util.DatetimeUtil;

@Component(value = "sysNoticeService")
public class SysNoticeServiceImpl implements SysNoticeService {

  Logger log = LoggerFactory.getLogger(this.getClass());

  @Resource(name = "sqlMapClient")
  /**
   * 数据库操作对象
   */
  private SqlMapClient sqlMapClient;

  @Resource(name = "sysNoticeDAO")
  private SysNoticeDAO sysNoticeDAO;
  @Resource(name = "sysNoticeSendDAO")
  private SysNoticeSendDAO sysNoticeSendDAO;

  /**
   * 根据vo条件查询公告信息page
   * 
   * @param Page,SysNotice
   * @return Page
   * @exception Exception
   */
  @Override
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
      page = sysNoticeDAO.selectPageByVo(pageParam, vo);
      page.setPageNo(pageNo);// 当前查询第几页
    } catch (Exception e) {
      e.printStackTrace();
      log.warn(e.getMessage());
      throw e;
    }
    return page;
  }

  /**
   * 新增公告
   *
   * @param SysNotice @return Integer @exception
   */
  @Override
  public Integer doSave(SysNotice sysnotice) throws Exception {
    try {
      sysNoticeDAO.insert(sysnotice);
    } catch (SQLException e) {
      e.printStackTrace();
      log.warn(e.getMessage());
      throw e;
    }
    return sysnotice.getNoticeId();
  }

  /**
   * 删除所选 (软删除)
   * 
   * @param String[]
   * @return void
   */
  @Override
  public void doDelete(String[] ids) throws Exception {
    try {
      sqlMapClient.startTransaction();
      // 开始循环删除
      if (ids != null && ids.length > 0) {
        for (int i = 0; i < ids.length; i++) {
          SysNotice vo = new SysNotice();
          vo.setNoticeId(Integer.valueOf(ids[i]));
          vo.setIsEnable("0");
          sysNoticeDAO.updateByPrimaryKeySelective(vo);
        }
      }
      sqlMapClient.commitTransaction();
    } catch (SQLException e) {
      e.printStackTrace();
      log.warn(e.getMessage());
      throw e;
    } finally {
      try {
        sqlMapClient.endTransaction();
      } catch (SQLException e) {
        e.printStackTrace();
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
  @Override
  public SysNotice getDetail(Integer id) throws Exception {
    SysNotice sysNotice = null;
    try {
      sysNotice = sysNoticeDAO.selectByPrimaryKey(id);
    } catch (SQLException e) {
      e.printStackTrace();
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
  @Override
  public SysNotice doUpdate(SysNotice sysnotice) throws Exception {
    try {
      sysNoticeDAO.updateByPrimaryKeySelective(sysnotice);
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    }
    return sysnotice;
  }

  /**
   * 发送公告（操作公告操作员关系表 sys_notice_send）
   *
   * @param Integer,String
   * @return Integer
   * @exception Exception
   */
  @Override
  public void sendNotice(Integer noticeId, String userIdStr) throws Exception {
    log.warn("================ in SysNoticeBean addSysUserRole() ");
    try {
      sqlMapClient.startTransaction();
      // 删除该公告的旧关系
      SysNoticeSendExample exam = new SysNoticeSendExample();
      exam.createCriteria().andNoticeIdEqualTo(noticeId);
      sysNoticeSendDAO.deleteByExample(exam);
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
          sysNoticeSendDAO.insert(vo);
        }
      }
      log.warn("---------------------------------------- 添加新关系完毕！！！");
      sqlMapClient.commitTransaction();
    } catch (SQLException e) {
      e.printStackTrace();
      log.warn(e.getMessage());
      throw e;
    } finally {
      try {
        sqlMapClient.endTransaction();
      } catch (SQLException e) {
        e.printStackTrace();
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
  @Override
  public List getSendListByNoticeId(Integer noticeId) throws Exception {
    log.warn("================ in SysNoticeBean getSendListByNoticeId() ");
    List dataList = null;
    try {
      dataList = sysNoticeSendDAO.selectSendListByNotice(noticeId);
      log.warn("------------- sendList.size()=" + dataList.size());
    } catch (SQLException e) {
      e.printStackTrace();
      log.warn(e.getMessage());
      throw e;
    }
    return dataList;
  }

  /**
   * 根据vo条件查询我的公告信息page
   * 
   * @param Page,SysNotice
   * @return Page
   * @exception Exception
   */
  @Override
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
      page = sysNoticeDAO.searchMyNoticePageByVo(pageParam, vo);
      page.setPageNo(pageNo);// 当前查询第几页
    } catch (Exception e) {
      e.printStackTrace();
      log.warn(e.getMessage());
      throw e;
    }
    return page;
  }

  /**
   * 删除我的公告 (软删除)
   * 
   * @param String[]
   * @return void
   */
  @Override
  public void doDeleteMyNotice(String[] ids, Integer userId) throws Exception {
    try {
      sqlMapClient.startTransaction();
      // 开始循环删除
      if (ids != null && ids.length > 0) {
        for (int i = 0; i < ids.length; i++) {
          SysNoticeSend vo = new SysNoticeSend();
          vo.setNoticeId(Integer.valueOf(ids[i]));
          vo.setUserId(userId);
          vo.setIsEnable("0");
          sysNoticeSendDAO.updateByPrimaryKeySelective(vo);
        }
      }
      sqlMapClient.commitTransaction();
    } catch (SQLException e) {
      e.printStackTrace();
      log.warn(e.getMessage());
      throw e;
    } finally {
      try {
        sqlMapClient.endTransaction();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

  }

  /**
   * 修改已读状态
   * 
   * @param Integer,Integer
   * @return SysNotice
   * @exception Exception
   */
  @Override
  public SysNoticeSend setIsRead(Integer noticeId, Integer userId) throws Exception {
    SysNoticeSend vo = new SysNoticeSend();
    try {
      vo.setNoticeId(noticeId);
      vo.setUserId(userId);
      vo.setIsRead("1");
      sysNoticeSendDAO.updateByPrimaryKeySelective(vo);
    } catch (SQLException e) {
      e.printStackTrace();
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
  @Override
  public List getMyNoticeList(Integer userId) throws Exception {
    log.warn("================ in SysNoticeBean getMyNoticeList() ");
    List dataList = null;
    try {
      dataList = sysNoticeDAO.selectMyNoticeList(userId);
      log.warn("------------- myNoticeList.size()=" + dataList.size());
    } catch (SQLException e) {
      e.printStackTrace();
      log.warn(e.getMessage());
      throw e;
    }
    return dataList;
  }

}
