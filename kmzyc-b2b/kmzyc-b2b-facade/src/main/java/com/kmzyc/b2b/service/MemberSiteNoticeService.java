package com.kmzyc.b2b.service;

import java.sql.SQLException;
import java.util.Map;

import com.km.framework.page.Pagination;
import com.kmzyc.b2b.model.SiteNoticeDetail;

/**
 * 站内通知消息
 * 
 * @author luoyi
 * @createDate 2013/10/09
 */
public interface MemberSiteNoticeService {

  /**
   * 查找登录用户的所有个人通知消息
   * 
   * @param page
   * @return
   * @throws SQLException
   */
  public abstract Pagination findSiteNoticeByUserId(Pagination page) throws SQLException;

  /**
   * 查找登录用户的所有个人通知消息
   * 
   * @param page
   * @return
   * @throws SQLException
   */
  public abstract Pagination findLatestSiteNoticeByUserId(Pagination page) throws SQLException;

  /**
   * 根据用户ID和消息id的map,删除站内通知
   * 
   * @return 返回影响行数
   */
  public abstract int deleteSiteNoticeByMessageId(Map<String, Object> messageIdAndUserIdMap);

  /**
   * 根据通知的ID，查找站内通知的详情
   * 
   * @param messageId
   * @return
   */
  public abstract SiteNoticeDetail findSiteNoticeDetail(Integer messageId);

  /**
   * 查询会员指定状态的消息总数
   * 
   * @param userId
   * @param status
   * @return
   * @throws SQLException
   */
  public Long countNewNotice(Map<String, Object> para) throws SQLException;

  /**
   * 获取消息总数
   * 
   * @param userId
   * @return
   * @throws SQLException
   */
  public Integer countNoticeNum(Long userId) throws SQLException;

  /**
   * 更新站内通知状态
   * 
   * @param messageId
   */
  public void updateNoticeStatus(Integer messageId);

}
