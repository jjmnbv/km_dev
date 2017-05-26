package com.pltfm.remote.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.kmzyc.user.remote.service.UserMessageCenterService;
import com.pltfm.app.dao.BnesMessageCenterDAO;
import com.pltfm.app.util.ListUtils;
import com.pltfm.app.vobject.BnesMessageCenter;

/**
 * 消息中心接口实现类
 * 
 * @author gwl
 * @since 2013-08-06
 */
@Component(value = "userMessageCenterService")
public class UserMessageCenterServiceImpl implements UserMessageCenterService {
  /***
   * 消息中心DAO接口
   */
  @Resource(name = "bnesMessageCenterDAO")
  private BnesMessageCenterDAO bnesMessageCenterDAO;
  /**
   * 日志类
   */
  Logger log = LoggerFactory.getLogger(this.getClass());

  /**
   * 传入消息ID查询消息详细信息并更新查询状态为已读远
   * 
   * @param messageId 主键
   * @return 返回值
   * @throws Exception 异常
   */
  @Override
public BnesMessageCenter getMessageId(Integer messageId) throws Exception {
    BnesMessageCenter b = new BnesMessageCenter();
    b.setMessageId(messageId);
    b.setStatus(1);// 设为已读
    b.setCheckDate(new Date());
    bnesMessageCenterDAO.update(b);
    return bnesMessageCenterDAO.getMessageId(messageId);
  }

  /**
   * 传入消息ID更新查询状态为已读远
   * 
   * @param messageIds(List<Integer>消息id集合) 主键
   * @return 返回值
   * @throws Exception 异常
   */
  @Override
public int updateBnesMessageCenter(List<Integer> messageIds) throws Exception {
    Integer count = 0;
    if (ListUtils.isNotEmpty(messageIds)) {
      for (Integer id : messageIds) {
        BnesMessageCenter b = new BnesMessageCenter();
        b.setMessageId(id);
        b.setStatus(1);// 设为已读
        b.setCheckDate(new Date());
        count += bnesMessageCenterDAO.update(b);
      }
    }
    return count;

  }

  /**
   * 跟据传入消息ID删除信息
   * 
   * @param messageIds(List<Integer>消息id集合) 主键
   * @return 返回值
   * @throws Exception 异常
   */
  @Override
public int deleteBnesMessageCenter(List<Integer> messageIds) throws Exception {
    Integer count = 0;
    if (ListUtils.isNotEmpty(messageIds)) {
      for (Integer id : messageIds) {
        BnesMessageCenter bnesMessageCenter = new BnesMessageCenter();
        bnesMessageCenter.setMessageId(id);
        count += bnesMessageCenterDAO.delete(bnesMessageCenter);
      }
    }
    return count;
  }

  /**
   * 查询客户消息信息远程接口
   * 
   * @param BnesMessageCenter 消息实体
   * @param pageParam 分布类
   * @param type 消息类型
   * @throws Exception 异常信息
   */
  @Override
public List<BnesMessageCenter> searchPageByVo(Page pageParam, BnesMessageCenter bnesMessageCenter)
      throws Exception {
    List<BnesMessageCenter> list = null;
    if (pageParam == null) {
      pageParam = new Page();
    }
    if (bnesMessageCenter == null) {
      bnesMessageCenter = new BnesMessageCenter();
    }
    int totalNum = bnesMessageCenterDAO.selectCountByVo(bnesMessageCenter);
    pageParam.setRecordCount(totalNum);
    bnesMessageCenter.setSkip(pageParam.getStartIndex());
    bnesMessageCenter.setMax(pageParam.getStartIndex() + pageParam.getPageSize());
    list = bnesMessageCenterDAO.selectPageByVo(bnesMessageCenter);
    return list;
  }

  public BnesMessageCenterDAO getBnesMessageCenterDAO() {
    return bnesMessageCenterDAO;
  }

  public void setBnesMessageCenterDAO(BnesMessageCenterDAO bnesMessageCenterDAO) {
    this.bnesMessageCenterDAO = bnesMessageCenterDAO;
  }
}
