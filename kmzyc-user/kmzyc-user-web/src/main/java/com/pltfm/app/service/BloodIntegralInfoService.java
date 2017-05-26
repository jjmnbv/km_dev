package com.pltfm.app.service;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.vobject.BloodIntegralInfo;
import com.pltfm.app.vobject.LoginInfo;

/***
 * 积分明细Service接口
 */
public interface BloodIntegralInfoService {
  /***
   * 
   * 删除积分明细
   */
  int delete(List<Integer> integralInfoIds) throws SQLException;

  /***
   * 
   * 添加积分明细
   */
  Integer insert(BloodIntegralInfo bloodIntegralInfo) throws SQLException;

  /**
   * 分页查询积分明细
   * 
   * @param 积分明细实体
   * @return
   * @throws Exception 异常
   */
  public Page searchPageByVo(Page pageParam, BloodIntegralInfo vo) throws Exception;

  /**
   * 跟据登录ID取得登录名
   * 
   * @param vo 信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public LoginInfo getLoginName(Integer n_LoginId) throws SQLException;
}
