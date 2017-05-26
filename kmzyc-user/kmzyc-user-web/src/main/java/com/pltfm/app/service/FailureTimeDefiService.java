package com.pltfm.app.service;

import com.pltfm.app.vobject.FailureTimeDefi;

/**
 * 手机随机码信息业务逻辑接口
 * 
 * @author cjm
 * @since 2013-7-10
 */
public interface FailureTimeDefiService {


  /**
   * 根据主键查询失效时间定义信息
   * 
   * @param n_PersonalityId 手机随机码ID
   * @return 返回值
   * @throws Exception 异常
   */
  FailureTimeDefi selectByPrimaryKey(Integer failure_Time_Id) throws Exception;



}
