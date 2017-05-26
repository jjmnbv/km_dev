package com.kmzyc.b2b.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.km.framework.persistence.Dao;
import com.kmzyc.b2b.model.SiteNoticeDetail;

/**
 * 会员站内通知Dao接口
 * 
 * @author luoyi
 * @createDate 2013/10/09
 */
public interface MemberSiteNoticeDao extends Dao {

  /**
   * 根据用户ID和消息id的map,删除站内通知
   * 
   * @return 返回影响行数
   */
  public int deleteSiteNoticeByMessageIdAndUserId(Map<String, Object> messageIdAndUserIdMap);

  /**
   * 根据通知的ID，查找站内通知的详情
   * 
   * @param messageId
   * @return
   */
  public SiteNoticeDetail findSiteNoticeDetail(Integer messageId);

  /**
   * 更新站内通知状态
   * 
   * @param messageId
   */
  public void updateNoticeStatus(Integer messageId);

  /**
   * 分页查询通知列表
   * 
   * @return
   * @throws SQLException
   */
  public List<SiteNoticeDetail> queryMessageListByPage(Map<String, Object> params)
      throws SQLException;

  /**
   * 获取个人消息个数
   * 
   * @return
   * @throws SQLException
   */
  public Integer getMessageCount(Map<String, Object> params) throws SQLException;

  /**
   * 删除通知
   * 
   * @param msgId
   * @return
   * @throws SQLException
   */
  public boolean deleteMessage(Map<String, Object> params) throws SQLException;

  /**
   * 更新通知为已读
   * 
   * @param msgIds
   * @param uid
   * @return
   * @throws SQLException
   */
  public boolean updateMessageReaded(Map<String, Object> params) throws SQLException;

  /**
   * 获取消息
   * 
   * @param msgId
   * @param uid
   * @return
   * @throws SQLException
   */
  public SiteNoticeDetail getMessage(Map<String, Object> params) throws SQLException;

  /**
   * app获取消息通道
   * 
   * @param params
   * @return
   * @throws SQLException
   */
  public List<Map<String, Object>> queryMessage4AppByPage(Map<String, Object> params)
      throws SQLException;
}
