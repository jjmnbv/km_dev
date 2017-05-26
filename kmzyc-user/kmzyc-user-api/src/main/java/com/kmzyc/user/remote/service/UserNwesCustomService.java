package com.kmzyc.user.remote.service;

import java.io.Serializable;

import com.pltfm.app.vobject.NwesCustomService;

/***
 * 客服远程接口
 */
public interface UserNwesCustomService extends Serializable {
  /****
   * @param 客服信息
   * @return 返回值
   * @throws Exception 异常
   **/
  public int addUserNwesCustom(NwesCustomService nwesCustomService) throws Exception;
}
