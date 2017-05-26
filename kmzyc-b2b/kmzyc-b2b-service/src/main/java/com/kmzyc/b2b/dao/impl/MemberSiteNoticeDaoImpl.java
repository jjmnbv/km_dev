package com.kmzyc.b2b.dao.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.b2b.dao.MemberSiteNoticeDao;
import com.kmzyc.b2b.model.SiteNoticeDetail;
import com.km.framework.persistence.impl.DaoImpl;

/**
 * 会员站内通知实现类
 * 
 * @author luoyi
 * @createDate 2013/10/09
 */
@SuppressWarnings("unchecked")
@Repository("MemberSiteNoticeDao")
public class MemberSiteNoticeDaoImpl extends DaoImpl implements MemberSiteNoticeDao {

  private static Logger logger = LoggerFactory.getLogger(MemberSiteNoticeDaoImpl.class);

  @Resource
  protected SqlMapClient sqlMapClient;

  /**
   * 根据用户ID和消息id的map,删除站内通知
   * 
   * @return 返回影响行数
   */
  public int deleteSiteNoticeByMessageIdAndUserId(Map<String, Object> messageIdAndUserIdMap) {
    try {
      return sqlMapClient.delete("BNES_MESSAGE_CENTER.deleteMessageCenterById",
          messageIdAndUserIdMap);
    } catch (SQLException e) {
      logger.error("删除站内通知失败。" + e.getMessage());
    }
    return 0;
  }

  /**
   * 根据通知的ID，查找站内通知的详情
   * 
   * @param messageId
   * @return
   */
  public SiteNoticeDetail findSiteNoticeDetail(Integer messageId) {
    try {
      SiteNoticeDetail detail =
          (SiteNoticeDetail) sqlMapClient.queryForObject(
              "BNES_MESSAGE_CENTER.ibatorgenerated_getMessageId", messageId);
      if (detail.getStatus() == 0) {// 如果状态为未读,更改状态
        // 更新状态为已查看
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("messageId", messageId);
        paramsMap.put("checkDate", new Date());
        sqlMapClient.update("BNES_MESSAGE_CENTER.isReaderMessageByMessageId", paramsMap);
      }
      return detail;
    } catch (SQLException e) {
      logger.error("查找站内通知的详情失败。" + e.getMessage());
    }
    return null;
  }

  @Override
  public void updateNoticeStatus(Integer messageId) {
    try {
      sqlMapClient.update("BNES_MESSAGE_CENTER.updateNoticeStatus", messageId);
    } catch (SQLException e) {
      logger.error("更新状态失败。" + e.getMessage());
    }
  }

  /**
   * 分页查询通知列表
   * 
   * @return
   * @throws SQLException
   */
  @Override
  public List<SiteNoticeDetail> queryMessageListByPage(Map<String, Object> params)
      throws SQLException {
    return sqlMapClient.queryForList("BNES_MESSAGE_CENTER.SQL_QUERY_MESSAGE_LIST_BY_PAGE", params);
  }

  /**
   * 获取个人消息个数
   * 
   * @return
   * @throws SQLException
   */
  public Integer getMessageCount(Map<String, Object> params) throws SQLException {
    return (Integer) sqlMapClient.queryForObject("BNES_MESSAGE_CENTER.SQL_GET_MESSAGE_COUNT",
        params);
  }

  /**
   * 删除通知
   * 
   * @param msgId
   * @return
   * @throws SQLException
   */
  @Override
  public boolean deleteMessage(Map<String, Object> params) throws SQLException {
    return sqlMapClient.delete("BNES_MESSAGE_CENTER.SQL_DELETE_MESSAGE", params) > 0;
  }

  /**
   * 更新通知为已读
   * 
   * @param msgIds
   * @param uid
   * @return
   * @throws SQLException
   */
  @Override
  public boolean updateMessageReaded(Map<String, Object> params) throws SQLException {
    return sqlMapClient.update("BNES_MESSAGE_CENTER.SQL_UPDATE_MESSAGE_READED", params) > 0;
  }

  /**
   * 获取消息
   * 
   * @param msgId
   * @param uid
   * @return
   * @throws SQLException
   */
  @Override
  public SiteNoticeDetail getMessage(Map<String, Object> params) throws SQLException {
    return (SiteNoticeDetail) sqlMapClient.queryForObject("BNES_MESSAGE_CENTER.SQL_QUERY_MESSAGE",
        params);
  }

  @Override
  public List<Map<String, Object>> queryMessage4AppByPage(Map<String, Object> params)
      throws SQLException {
    return (List<Map<String, Object>>) sqlMapClient.queryForList(
        "BNES_MESSAGE_CENTER.APP_QUERY_MESSAGE_LIST_BY_PAGE", params);
  }
}
