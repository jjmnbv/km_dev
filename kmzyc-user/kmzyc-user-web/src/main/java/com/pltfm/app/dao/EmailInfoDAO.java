package com.pltfm.app.dao;

import com.pltfm.app.vobject.Coupons;
import com.pltfm.app.vobject.EmailInfo;
import com.pltfm.app.vobject.EmailInfoExample;

import java.sql.SQLException;
import java.util.List;

/**
 * 邮箱验证信息处理接口
 * 
 * @author cjm
 * @since 2013-7-23
 */
public interface EmailInfoDAO {
  /**
   * 添加账户邮箱验证信息
   * 
   * @param record 邮箱验证实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  Integer insert(EmailInfo record) throws SQLException;

  /**
   * 修改账户邮箱验证信息
   * 
   * @param record 邮箱验证实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  int updateByPrimaryKey(EmailInfo record) throws SQLException;

  /**
   * 动态修改账户邮箱验证信息
   * 
   * @param record 邮箱验证实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  int updateByPrimaryKeySelective(EmailInfo record) throws SQLException;

  /**
   * 按账户邮箱验证信息条件查询
   * 
   * @param example 邮箱验证条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  List selectByExample(EmailInfoExample example) throws SQLException;

  /**
   * 根据账户主键和随机码查询单条手机随机码信息
   * 
   * @param nCellPhoneTattedCodeId 邮箱验证主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  EmailInfo selectByPrimaryKey(Integer nEmailId) throws SQLException;

  /**
   * 根据账户ID和随机码查询是否有邮箱验证信息
   * 
   * @param nCellPhoneTattedCodeId 邮箱验证主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  EmailInfo selectByEmailInfo(EmailInfo emailInfo) throws SQLException;

  /**
   * 按账户邮箱验证信息条件进行删除
   * 
   * @param example 邮箱验证条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  int deleteByExample(EmailInfoExample example) throws SQLException;

  /**
   * 根据账户邮箱验证主键删除手机随机码信息
   * 
   * @param nCommercialTenantId 邮箱验证主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  int deleteByPrimaryKey(Integer nEmailId) throws SQLException;

  /**
   * 按账户邮箱验证信息条件查询总条数
   * 
   * @param example 邮箱验证条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  int countByExample(EmailInfoExample example) throws SQLException;

  /**
   * 动态按账户邮箱验证信息条件进行修改
   * 
   * @param record 邮箱验证实体
   * @param example 邮箱验证条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  int updateByExampleSelective(EmailInfo record, EmailInfoExample example) throws SQLException;

  /**
   * 按账户邮箱验证信息条件进行修改
   * 
   * @param record 邮箱验证实体
   * @param example 邮箱验证条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  int updateByExample(EmailInfo record, EmailInfoExample example) throws SQLException;

  /**
   * 按条件查询手机随机码信息总数量
   * 
   * @param vo 邮箱验证信息类
   * @return 返回值
   */
  int selectCountByVo(EmailInfo vo) throws SQLException;

  /**
   * 根据vo条件查询分类信息page
   * 
   * @param page 分页类
   * @param vo 邮箱验证信息类
   * @return 返回值
   * @throws SQLException sql异常
   */
  List selectPageByVo(EmailInfo vo) throws SQLException;


  int selectCountByCoupons(Coupons coupons) throws SQLException;

  List<Coupons> selectPageByCoupons(Coupons coupons) throws SQLException;
}
