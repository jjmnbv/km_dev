package com.pltfm.app.service;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.vobject.BnesMessageCenter;
import com.pltfm.app.vobject.LoginInfo;

/**
 * 
 * 
 * 消息Service接口
 * 
 */
public interface BnesMessageCenterService {
  /**
   * 
   * 
   * 添加消息
   * 
   */
  Integer insert(BnesMessageCenter record) throws SQLException;

  /**
   * 添加消息
   */
  public void insertAll(int infoPromptId) throws SQLException;

  /**
   * 跟据登录ID取得登录名
   * 
   * @param vo 信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public LoginInfo getLoginName(Integer n_LoginId) throws SQLException;

  /**
   * 
   * 
   * 删除消息
   * 
   */
  int delete(List<Integer> ids) throws SQLException;

  /**
   * 
   * 
   * 修改消息
   * 
   */
  int update(BnesMessageCenter record) throws SQLException;

  /**
   * 分页查询消息信息
   * 
   * @param 消息信息实体
   * @return
   * @throws Exception 异常
   */
  public Page searchPageByVo(Page pageParam, BnesMessageCenter bnesMessageCenter) throws Exception;

  /**
   * 跟据信息id查询消息信息
   * 
   * @param 消息信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public BnesMessageCenter getMessageId(Integer messageId) throws SQLException;

  /**
   * 取得登录名
   * 
   * @param vo 信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */

  public List getLoginAll() throws SQLException;

  /**
   * 取得所有消息
   * 
   * @param vo 信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public List getBnesInfoPrompAll() throws SQLException;

}
