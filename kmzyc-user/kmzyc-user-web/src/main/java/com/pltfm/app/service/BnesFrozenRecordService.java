package com.pltfm.app.service;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.vobject.BnesFrozenRecord;
import com.pltfm.app.vobject.CommercialTenantBasicInfo;
import com.pltfm.app.vobject.LoginInfo;
import com.pltfm.app.vobject.PersonalBasicInfo;

/**
 * 
 * 
 * 冻结帐户Service接口
 * 
 */
public interface BnesFrozenRecordService {
  /**
   * 
   * 
   * 添加冻结帐户信息
   * 
   */
  Integer insert(BnesFrozenRecord record) throws SQLException;

  /**
   * 
   * 
   * 删除冻结帐户信息
   * 
   */
  int delete(List<Integer> ids) throws SQLException;

  /**
   * 跟据登录id取得登录名
   * 
   * @param vo 信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public LoginInfo getLoginAccount(Integer loginId) throws SQLException;

  /**
   * 根据登录主键查询单条商户信息
   * 
   * @param loginId 登录信息ID
   * @return 返回值
   * @throws Exception 异常
   */
  public CommercialTenantBasicInfo selectloginId(Integer loginId) throws SQLException;

  /**
   * 
   * 
   * 修改冻结帐户信息
   * 
   */
  int update(BnesFrozenRecord record) throws SQLException;

  /**
   * 分页查询冻结帐户信息
   * 
   * @param 冻结帐户信息实体
   * @return
   * @throws Exception 异常
   */
  public Page searchPageByVo(Page pageParam, BnesFrozenRecord bnesFrozenRecord) throws Exception;

  /**
   * 跟据信息id查询冻结帐户信息
   * 
   * @param Rank 冻结帐户信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public BnesFrozenRecord getFrozenRecordId(Integer frozenRecordId) throws SQLException;

  /**
   * 取得登录名
   * 
   * @param page 分页类
   * @param vo 信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public List getLoginAll() throws SQLException;

  /**
   * 跟据登录id取得姓名
   * 
   * @param 个人基本信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public PersonalBasicInfo getLogin(Integer login) throws SQLException;

  /**
   * 冻结解冻操作
   * 
   * 
   *
   */
  public Integer updateStatus(BnesFrozenRecord record, Integer status) throws SQLException;

  /**
   * 分页查询账户冻结解冻信息
   * 
   * @param record
   * @return
   * @throws SQLException
   */
  public Page queryPageAccountFrozen(BnesFrozenRecord record, Page page) throws SQLException;


  /**
   * 通过登录账号查询登录账户冻结解冻记录信息
   * 
   * @param bnesFrozenRecord
   * @return
   * @throws SQLException
   */
  public BnesFrozenRecord selectByLoginAccount(BnesFrozenRecord bnesFrozenRecord)
      throws SQLException;
}
