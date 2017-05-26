package com.kmzyc.user.remote.service;

import java.io.Serializable;
import java.util.List;

// import com.kmzyc.commons.page.Page;
import com.kmzyc.commons.page.Page;
import com.pltfm.app.vobject.BnesMessageCenter;

/**
 * 消息中心接口
 * 
 * @author gwl
 * @since 2013-08-06
 */
@SuppressWarnings("unused")
public interface UserMessageCenterService extends Serializable {
  /**
   * 跟据传入消息ID删除信息
   * 
   * @param messageIds(List<Integer>消息id集合) 主键
   * @return 返回值
   * @throws Exception 异常
   */
  public int deleteBnesMessageCenter(List<Integer> messageIds) throws Exception;

  /**
   * 传入消息ID查询消息详细信息并更新查询状态为已读远
   * 
   * @param messageId 主键
   * @return 返回值
   * @throws Exception 异常
   */
  public BnesMessageCenter getMessageId(Integer messageId) throws Exception;

  /**
   * 传入消息ID更新查询状态为已读远
   * 
   * @param messageIds(List<Integer>消息id集合) 主键
   * @return 返回值
   * @throws Exception 异常
   */
  public int updateBnesMessageCenter(List<Integer> messageIds) throws Exception;

  /**
   * 查询客户消息信息远程接口
   * 
   * @param bnesMessageCenter 消息实体
   * @param pageParam 分布类
   * @throws Exception 异常信息
   */
  public List<BnesMessageCenter> searchPageByVo(Page pageParam, BnesMessageCenter bnesMessageCenter)
      throws Exception;
}
