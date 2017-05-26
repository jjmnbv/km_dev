package com.kmzyc.b2b.service.impl;

import com.km.framework.page.Pagination;
import com.kmzyc.b2b.dao.MemberSiteNoticeDao;
import com.kmzyc.b2b.model.SiteNoticeDetail;
import com.kmzyc.b2b.service.MemberSiteNoticeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

/**
 * 站内通知消息实现service类
 * 
 * @author luoyi
 * @createDate 2013/10/09
 */
@Service("memberSiteNoticeService")
public class MemberSiteNoticeServiceImpl implements MemberSiteNoticeService {

    @Resource
    private MemberSiteNoticeDao memberSiteNoticeDao;

    // private static Logger logger = Logger.getLogger(MemberSiteNoticeServiceImpl.class);
    private static Logger logger = LoggerFactory.getLogger(MemberSiteNoticeServiceImpl.class);

    /**
     * 查找登录用户的所有个人通知消息
     * 
     * @param page
     * @return
     * @throws SQLException
     */
    @Override
    @SuppressWarnings("unchecked")
    public Pagination findSiteNoticeByUserId(Pagination page) throws SQLException {
        Map<String, Object> condition = (Map<String, Object>) page.getObjCondition();
        logger.info("开始查询userId=" + condition.get("userId") + "的站内消息列表,keyword:"
                + condition.get("keyword"));
        long startTime = System.currentTimeMillis();
        // 设置要连接的数据源为客户系统

        page =
                memberSiteNoticeDao.findByPage("BNES_MESSAGE_CENTER.searchPageByUserId",
                        "BNES_MESSAGE_CENTER.searchCountPageByUserId", page);
        logger.info("查询用户的站内消息列表结束，耗时" + (System.currentTimeMillis() - startTime) / 1000 + "秒");
        return page;
    }

    /**
     * 根据通知的ID，查找站内通知的详情
     * 
     * @param messageId
     * @return
     */
    public SiteNoticeDetail findSiteNoticeDetail(Integer messageId) {
        // 设置要连接的数据源为客户系统

        return memberSiteNoticeDao.findSiteNoticeDetail(messageId);
    }

    /**
     * 根据用户ID和消息id的map,删除站内通知
     * 
     * @return 返回影响行数
     */
    public int deleteSiteNoticeByMessageId(Map<String, Object> messageIdAndUserIdMap) {
        // 设置要连接的数据源为客户系统

        if (messageIdAndUserIdMap.containsKey("userId")) {
            logger.info("开始删除" + messageIdAndUserIdMap.get("userId") + "的站内消息.");
            int result =
                    memberSiteNoticeDao.deleteSiteNoticeByMessageIdAndUserId(messageIdAndUserIdMap);
            if (result > 0) {
                logger.info("成功删除" + messageIdAndUserIdMap.get("userId") + "的站内消息.");
                return result;
            } else {
                logger.info("删除" + messageIdAndUserIdMap.get("userId") + "的站内消息失败.");
            }
        }
        return 0;
    }

    /**
     * 查询会员指定状态的消息总数
     * 
     * @param para
     * @return
     * @throws SQLException
     */
    public Long countNewNotice(Map<String, Object> para) throws SQLException {
        // 设置要连接的数据源为客户系统

        return (Long) memberSiteNoticeDao.findById("BNES_MESSAGE_CENTER.countNew", para);
    }

    @Override
    public Integer countNoticeNum(Long userId) throws SQLException {

        Map<String, Object> para = new HashMap<>();
        para.put("userId", userId);
        return (Integer) memberSiteNoticeDao.findById("BNES_MESSAGE_CENTER.countNum", para);
    }

    @Override
    public Pagination findLatestSiteNoticeByUserId(Pagination page) throws SQLException {
        // 设置要连接的数据源为客户系统

        page =
                memberSiteNoticeDao.findByPage("BNES_MESSAGE_CENTER.getLatestSiteNotice",
                        "BNES_MESSAGE_CENTER.searchCountPageByUserId", page);
        return page;
    }

    public void updateNoticeStatus(Integer messageId) {

        memberSiteNoticeDao.updateNoticeStatus(messageId);
    }

}
