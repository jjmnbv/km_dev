package com.pltfm.app.dao;

import com.pltfm.app.vobject.Customer;
import com.pltfm.app.vobject.PersonalBasicInfo;
import com.pltfm.app.vobject.Specialist;
import com.pltfm.app.vobject.SyncPeronalInfoVO;

import java.sql.SQLException;
import java.util.List;

/**
 * 专家基本信息处理接口
 * 
 * @author cjm
 * @since 2013-7-9
 */
public interface PersonalBasicInfoDAO {
  /**
   * 条件查询个人信息 anthor lijianjun date：150420
   * 
   * @param vo
   * @return
   * @throws Exception
   */
  List<PersonalBasicInfo> queryPersonalInfoList(PersonalBasicInfo vo) throws Exception;

  /**
   * 添加专家信息
   * 
   * @param Rank 专家信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public Integer insertPersonalBasicInfo(PersonalBasicInfo p) throws SQLException;

  /**
   * 删除专家信息
   * 
   * @param Rank 专家信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public Integer deletePersonalBasicInfo(PersonalBasicInfo p) throws SQLException;

  /**
   * 修改专家信息
   * 
   * @param Rank 专家信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public Integer udpatePersonalBasicInfo(PersonalBasicInfo p) throws SQLException;

  /**
   * 根据登录Id修改个人信息
   * 
   * @param p
   * @return
   * @throws SQLException
   */
  public Integer udpatePersonalBasicInfoByLoginId(PersonalBasicInfo p) throws SQLException;

  /**
   * 跟据专类型父id查询
   * 
   * @param Rank 专家信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public PersonalBasicInfo getParentId(Integer personalId) throws SQLException;

  /**
   * 跟据专家id查询
   * 
   * @param Rank 专家信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public PersonalBasicInfo getPersonalId(Integer personalId) throws SQLException;

  /**
   * 按条件查询专家基本信息总数量
   * 
   * @param vo
   * @return 返回值
   */
  int selectCountByVo(PersonalBasicInfo vo) throws SQLException;

  /**
   * 根据vo条件查询分类信息page
   * 
   * @param page 分页类
   * @param vo 信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */

  public List selectPageByVo(PersonalBasicInfo vo) throws SQLException;

  /**
   * 按条件查询个人基本信息总数量
   * 
   * @param vo
   * @return 返回值
   */
  int selectCountByPersonalBasicInfo(PersonalBasicInfo vo) throws SQLException;

  /**
   * 根据PersonalBasicInfo条件查询分类信息page
   * 
   * @param page 分页类
   * @param PersonalBasicInfo 个人信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */

  List selectPageByPersonalBasicInfo(PersonalBasicInfo vo) throws SQLException;

  /**
   * 根据个人信息ID查询个人信息
   * 
   * @param customer 个人信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  Customer selectPageByCustomer(Customer customer) throws SQLException;

  /**
   * 添加个人信息
   * 
   * @param Rank 个人信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  Integer insert(PersonalBasicInfo p) throws SQLException;

  /**
   * 跟据登录id查询
   * 
   * @param Rank 专家信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public PersonalBasicInfo getLogin(Integer login) throws SQLException;

  /**
   * 根据个人信息ID查询专家个人信息
   * 
   * @param Specialist 专家信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public Specialist selectPageBySpecialist(Specialist specialist) throws SQLException;

  List selectPageByPersonalBasicInfoForRebate(PersonalBasicInfo vo) throws SQLException;

  int selectCountByPersonalBasicInfoForRebate(PersonalBasicInfo vo) throws SQLException;

  List<SyncPeronalInfoVO> queryPersonalInfoByAccountLogin(List<String> lstAccountLogin)
      throws SQLException;
  
  List searchLoginIdPageInfoList(PersonalBasicInfo vo) throws SQLException;
  
  public int selectLoginIdPageInfoCount(PersonalBasicInfo vo) throws SQLException;

  int selectPersonalBasicInfoCount(PersonalBasicInfo vo) throws SQLException ;
  
  List selectPersonalBasicInfoByPage(PersonalBasicInfo vo) throws SQLException; 
  
  PersonalBasicInfo getPersonalInfoByLogId(Integer login) throws SQLException;
  
  
  
}
