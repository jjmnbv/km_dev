package com.pltfm.app.dao;

import com.pltfm.app.vobject.FailureTimeDefi;

import java.sql.SQLException;

/**
 * 手机随机码信息处理接口
 * 
 * @author cjm
 * @since 2013-7-10
 */
public interface FailuerTimeDefiDAO {
  /**
   * 根据主键查询失效时间定义信息
   * 
   * @param n_PersonalityId 手机随机码ID
   * @return 返回值
   * @throws Exception 异常
   */
  FailureTimeDefi selectByPrimaryKey(Integer failure_Time_Id) throws SQLException;


}
