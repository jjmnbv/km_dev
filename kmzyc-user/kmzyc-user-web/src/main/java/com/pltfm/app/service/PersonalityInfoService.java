package com.pltfm.app.service;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.vobject.PersonalityInfo;
import com.pltfm.app.vobject.PersonalityInfoExample;

/**
 * 个人个性信息业务逻辑接口
 * 
 * @author cjm
 * @since 2013-7-9
 */
public interface PersonalityInfoService {

  /**
   * 添加个人个性信息
   * 
   * @param record 个人个性信息实体
   * @return 返回值
   * @throws Exception 异常
   */
  Integer addPersonalityInfo(PersonalityInfo personalityInfo) throws Exception;

  /**
   * 按头衔id查询是否存在头衔
   * 
   * @param rankId 头衔id
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int countRank(Integer rankId) throws Exception;

  /**
   * 修改个人个性信息
   * 
   * @param record 个人个性信息实体
   * @return 返回值
   * @throws Exception 异常
   */
  Integer updatePersonalityInfo(PersonalityInfo personalityInfo) throws Exception;

  /**
   * 获取个人个性信息列表
   * 
   * @param example 个人个性信息实体
   * @return 返回值
   * @throws Exception 异常
   */
  List<PersonalityInfo> getPersonalityInfoList(PersonalityInfo personalityInfo) throws Exception;

  /**
   * 根据登录主键查询单条个人个性信息
   * 
   * @param n_LoginId 登录信息ID
   * @return 返回值
   * @throws Exception 异常
   */
  PersonalityInfo selectByPrimaryKey(Integer n_LoginId) throws Exception;



  /**
   * 根据主键进行删除单条个人个性信息
   * 
   * @param n_PersonalityId 个人个性信息ID
   * @return 返回值
   * @throws Exception 异常
   */
  Integer deleteByPrimaryKey(Integer n_PersonalityId) throws Exception;

  /**
   * 获取个人个性信息总条数
   * 
   * @param example 个人个性信息实体
   * @return 返回值
   * @throws SQLException 异常
   */
  Integer countByExample(PersonalityInfoExample example) throws Exception;

  /**
   * 根据vo条件查询分类信息page
   * 
   * @param pageParam 分页实体
   * @param vo 个人个性信息实体
   * @return 返回值
   * @throws Exception
   */
  Page searchPageByVo(Page pageParam, PersonalityInfo vo) throws Exception;
  
  /**
   * 根据登录ID获取个人信息
   * @param loginId
   * @return
   * @throws SQLException
   */
  PersonalityInfo selectPersonalityInfoByLoginId(Integer loginId) throws SQLException ;

}
