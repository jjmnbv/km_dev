package com.pltfm.app.service;

import java.sql.SQLException;
import java.util.List;

import com.pltfm.app.vobject.BnesInfoPrompt;



public interface PublishMessageService {
  // 发布
  public void addMessage(BnesInfoPrompt infoPrompt) throws SQLException;

  // 列表
  public List selectList(BnesInfoPrompt infoPrompt) throws SQLException;



  public BnesInfoPrompt queryPrompt(BnesInfoPrompt infoPrompt) throws SQLException;

  // 修改
  public void updateMessage(BnesInfoPrompt infoPrompt, String loginId) throws SQLException;

  // 删除多条
  public void delete(List<Integer> infoPromptIds) throws SQLException;

  // 删除单条
  public void deleteById(Integer infoPromptId) throws SQLException;

  // 记录数
  public Integer selectListCount(BnesInfoPrompt infoPrompt) throws SQLException;

}
