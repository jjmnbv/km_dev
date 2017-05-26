package com.kmzyc.user.remote.service;

import com.pltfm.app.vobject.BnesInfoPrompt;

/**
 * 站内通知相关接口
 * 
 * @author cjm
 * @since 2013-8-16
 */
public interface PublishMessageRemoteService {
  /**
   * 新建站内通知
   * 
   * @param infoPrompt
   * @throws Exception
   */
  void addMessage(BnesInfoPrompt infoPrompt) throws Exception;
}
