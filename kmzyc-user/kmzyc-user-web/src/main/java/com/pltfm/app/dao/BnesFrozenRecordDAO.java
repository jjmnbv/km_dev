package com.pltfm.app.dao;

import com.pltfm.app.vobject.BnesFrozenRecord;

import java.sql.SQLException;
import java.util.List;

/**
 * 账户冻结DAO接口
 * 
 * @author gwl
 * @since 2013-07-08
 */
public interface BnesFrozenRecordDAO {
  /**
   * 
   * 
   * 添加账户冻结
   * 
   */
  Integer insert(BnesFrozenRecord record) throws SQLException;

  /**
   * 
   * 
   * 删除账户冻结
   * 
   */
  int delete(BnesFrozenRecord record) throws SQLException;

  /**
   * 
   * 
   * 修改账户冻结
   * 
   */
  int update(BnesFrozenRecord record) throws SQLException;

  /**
   * 按条件查询登录账号基本信息总数量
   * 
   * @param vo
   * @return 返回值
   */

  int selectCountByVo(BnesFrozenRecord bnesFrozenRecord) throws SQLException;

  /**
   * 根据vo条件登录账号查询分类信息page
   * 
   * @param page 分页类
   * @param vo 信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */

  public List selectPageByVo(BnesFrozenRecord bnesFrozenRecord) throws SQLException;

  /**
   * 跟据账户冻结id查询账户冻结信息
   * 
   * @param 账户冻结实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public BnesFrozenRecord getFrozenRecordId(Integer frozenRecordId) throws SQLException;

  /**
   * 通过账户冻结实体查询账户冻结信息
   * 
   * @param bnesFrozenRecord 账户冻结解冻实体
   * @return
   * @throws SQLException 异常
   */
  public Integer selectCountByAccount(BnesFrozenRecord bnesFrozenRecord) throws SQLException;

  /**
   * 分页查询账户冻结解冻信息
   * 
   * @param bnesFrozenRecord 账户冻结解冻实体
   * @return
   * @throws SQLException 异常
   */
  public List selectPageByAccount(BnesFrozenRecord bnesFrozenRecord) throws SQLException;

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
