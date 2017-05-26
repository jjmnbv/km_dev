package com.pltfm.app.dao;

import com.pltfm.app.vobject.BnesInfoPrompt;

import java.sql.SQLException;
import java.util.List;

public interface PublishMessageDAO {
  // 发布消息
  public void addMessage(BnesInfoPrompt infoPrompt) throws SQLException;

  // 查询列表
  public List selectList(BnesInfoPrompt infoPrompt) throws SQLException;

  public BnesInfoPrompt queryPrompt(BnesInfoPrompt infoPrompt) throws SQLException;

  public void updateMessage(BnesInfoPrompt infoPrompt) throws SQLException;

  public void delete(BnesInfoPrompt infoPrompt) throws SQLException;

  public Integer selectListCount(BnesInfoPrompt infoPrompt) throws SQLException;

  public List selectBnesInfoPromp() throws SQLException;

}
