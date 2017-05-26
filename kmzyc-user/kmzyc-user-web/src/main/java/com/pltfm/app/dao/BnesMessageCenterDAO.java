package com.pltfm.app.dao;


import com.pltfm.app.vobject.BnesMessageCenter;

import java.sql.SQLException;
import java.util.List;

/**
 * 
 * 
 * 消息DAO接口
 * 
 */
public interface BnesMessageCenterDAO {
  /**
   * 
   * 
   * 添加消息
   * 
   */
  Integer insert(BnesMessageCenter record) throws SQLException;

  /**
   * 
   * 
   * 删除消息
   * 
   */
  int delete(BnesMessageCenter record) throws SQLException;

  /**
   * 
   * 
   * 修改消息
   * 
   */
  int update(BnesMessageCenter record) throws SQLException;

  /**
   * 按条件查询基本信息总数量
   * 
   * @param vo
   * @return 返回值
   */
  int selectCountByVo(BnesMessageCenter bnesMessageCenter) throws SQLException;

  /**
   * 根据vo条件查询分类信息page
   * 
   * @param page 分页类
   * @param vo 信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */

  public List selectPageByVo(BnesMessageCenter bnesMessageCenter) throws SQLException;

  /**
   * 跟据信息id查询信息信息
   * 
   * @param 信息息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public BnesMessageCenter getMessageId(Integer messageId) throws SQLException;

}
