package com.pltfm.app.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.dataobject.UserInfoDO;
import com.pltfm.app.vobject.PersonalityInfo;
import com.pltfm.app.vobject.PersonalityInfoExample;

/**
 * 个人个性信息处理接口
 * 
 * @author cjm
 * @since 2013-7-9
 */
@SuppressWarnings("unchecked")
public interface PersonalityInfoDAO {

  /**
   * 添加个人个性信息
   * 
   * @param record 个人个性信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  Integer insert(PersonalityInfo record) throws SQLException;

  /**
   * 按头衔id查询是否存在头衔
   * 
   * @param rankId 头衔id
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int countRank(Integer rankId) throws SQLException;

  /**
   * 修改个人个性信息
   * 
   * @param record 个人个性信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  int updateByPrimaryKey(PersonalityInfo record) throws SQLException;

  /**
   * 动态修改个人个性信息
   * 
   * @param record 个人个性信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  int updateByPrimaryKeySelective(PersonalityInfo record) throws SQLException;

  /**
   * 按个人个性信息条件查询
   * 
   * @param example 个人个性信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  List selectByExample(PersonalityInfoExample example) throws SQLException;

  /**
   * 根据个人个性主键查询单条个人个性信息
   * 
   * @param nCommercialTenantId 个人个性主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  PersonalityInfo selectByPrimaryKey(Integer n_PersonalityId) throws SQLException;


  /**
   * 根据登录主键查询单条个人个性信息
   * 
   * @param nLoginId 个人个性主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  PersonalityInfo selectByPersonalityInfo(Integer nLoginId) throws SQLException;

  /**
   * 按个人个性信息条件进行删除
   * 
   * @param example 个人个性信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  int deleteByExample(PersonalityInfoExample example) throws SQLException;

  /**
   * 根据个人个性主键删除商户基本信息
   * 
   * @param nCommercialTenantId 个人个性主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  int deleteByPrimaryKey(Integer n_PersonalityId) throws SQLException;

  /**
   * 按个人个性信息条件查询总条数
   * 
   * @param example 个人个性信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  int countByExample(PersonalityInfoExample example) throws SQLException;

  /**
   * 动态按个人个性信息条件进行修改
   * 
   * @param record 个人个性信息实体
   * @param example 个人个性信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  int updateByExampleSelective(PersonalityInfo record, PersonalityInfoExample example)
      throws SQLException;

  /**
   * 按个人个性信息条件进行修改
   * 
   * @param record 个人个性信息实体
   * @param example 个人个性信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  int updateByExample(PersonalityInfo record, PersonalityInfoExample example) throws SQLException;

  /**
   * 根据vo条件查询分类信息page
   * 
   * @param page 分页类
   * @param vo 个人个性信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  Page selectPageByVo(Page page, PersonalityInfo vo) throws SQLException;

  /**
   * 根据登录ID更新上年度消费金额
   * 
   * @param nCommercialTenantId 个人个性主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  int updateLastYearByLoginId(Integer n_LoginId, double lastYear_Amount) throws SQLException;

  /**
   * 更新积分
   * 
   * @param params
   * @return
   * @throws SQLException
   */
  public int updateUserScore(Map<String, Object> params) throws SQLException;

  /**
   * 根据用户卡号查询用户积分信息
   * 
   * @param cardNum
   * @return
   * @throws SQLException
   */
  public UserInfoDO queryUserScoreInfoByCardNum(String cardNum) throws SQLException;

}
