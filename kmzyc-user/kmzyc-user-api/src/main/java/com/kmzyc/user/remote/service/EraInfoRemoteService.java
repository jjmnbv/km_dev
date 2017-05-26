package com.kmzyc.user.remote.service;

import java.io.Serializable;
import java.util.List;

import com.kmzyc.b2b.vo.EraInfo;
import com.pltfm.app.vobject.LoginInfo;

public interface EraInfoRemoteService extends Serializable {

  /**
   * 添加时代直销会员
   * 
   * @param
   * @param
   * @return 返回值 Integer -1时代会员主键id和编号不能为空， -----0 失败，----成功 -主键ID值
   * @throws Exception 异常
   */
  Integer registerEraInfo(EraInfo eraInfo, Integer type) throws Exception;

  /**
   * 更新同步时代会员信息接口
   * 
   * @param eraInfo
   * @return
   * @throws Exception (-1:参数为空，1:修改成功，0：失败)updateByPrimaryKey
   */
  Integer updateEraInfo(EraInfo eraInfo) throws Exception;

  /**
   * 更新user时代会员信息(不需要同步)
   * 
   * @param eraInfo
   * @return
   * @throws Exception
   */
  Integer updateUseEraInfo(EraInfo eraInfo) throws Exception;



  /**
   * 更新会员账号信息
   * 
   * @param loginInfo
   * @return
   * @throws Exception (-1:参数为空，1:修改成功，0：失败)updateByPrimaryKey
   */
  Integer updateLogin(LoginInfo loginInfo) throws Exception;

  /**
   * 同步时代会员信息
   * @param eraInfos
   */
  void updateUserInfoForTimes(List<EraInfo> eraInfos) throws Exception;

}
