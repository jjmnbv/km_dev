package com.pltfm.app.dao;

import com.pltfm.app.vobject.MobileCodeInf;
import com.pltfm.app.vobject.MobileCodeInfExample;

import java.sql.SQLException;
import java.util.List;

/**
 * 手机随机码信息处理接口
 * 
 * @author cjm
 * @since 2013-7-10
 */
public interface MobileCodeInfDAO {
  /**
   * 按账户查询手机随机码信息总数量
   * 
   * @param vo 手机随机码信息实体
   * @return 返回值
   */
  int selectCountByVo(MobileCodeInf vo) throws SQLException;

  /**
   * 根据vo条件查询账户手机随机码信息page
   * 
   * @param page 分页类
   * @param vo 手机随机码信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  List selectPageByVo(MobileCodeInf vo) throws SQLException;

  /**
   * 添加账户手机随机码信息
   * 
   * @param record 手机随机码实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  Integer insert(MobileCodeInf record) throws SQLException;

  /**
   * 修改账户手机随机码信息
   * 
   * @param record 手机随机码实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  int updateByPrimaryKey(MobileCodeInf record) throws SQLException;

  int isupdatecode(MobileCodeInf record) throws SQLException;

  /**
   * 动态修改账户手机随机码信息
   * 
   * @param record 手机随机码实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  int updateByPrimaryKeySelective(MobileCodeInf record) throws SQLException;

  /**
   * 按账户手机随机码信息条件查询
   * 
   * @param example 手机随机码条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  List selectByExample(MobileCodeInfExample example) throws SQLException;

  /**
   * 根据手机随机码主键查询单条手机随机码信息
   * 
   * @param nCellPhoneTattedCodeId 手机随机码主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  MobileCodeInf selectByPrimaryKey(Integer nCellPhoneTattedCodeId) throws SQLException;


  /**
   * 根据手机随机码条件查询单条手机随机码信息
   * 
   * @param nCellPhoneTattedCodeId 手机随机码
   * @return 返回值
   * @throws SQLException sql异常
   */
  MobileCodeInf selectByMobileCodeInf(MobileCodeInf mobileCodeInf) throws SQLException;


  /**
   * 按账户手机随机码信息条件进行删除
   * 
   * @param example 手机随机码条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  int deleteByExample(MobileCodeInfExample example) throws SQLException;

  /**
   * 根据账户手机随机码主键删除手机随机码信息
   * 
   * @param nCommercialTenantId 手机随机码主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  int deleteByPrimaryKey(Integer nCellPhoneTattedCodeId) throws SQLException;

  /**
   * 按账户手机随机码信息条件查询总条数
   * 
   * @param example 手机随机码条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  int countByExample(MobileCodeInfExample example) throws SQLException;

  /**
   * 动态按账户手机随机码信息条件进行修改
   * 
   * @param record 手机随机码实体
   * @param example 手机随机码条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  int updateByExampleSelective(MobileCodeInf record, MobileCodeInfExample example)
      throws SQLException;

  /**
   * 按账户手机随机码信息条件进行修改
   * 
   * @param record 手机随机码实体
   * @param example 手机随机码条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  int updateByExample(MobileCodeInf record, MobileCodeInfExample example) throws SQLException;
}
