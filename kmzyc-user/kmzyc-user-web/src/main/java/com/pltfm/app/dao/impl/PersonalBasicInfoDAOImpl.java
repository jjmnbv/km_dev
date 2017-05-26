package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.PersonalBasicInfoDAO;
import com.pltfm.app.vobject.Customer;
import com.pltfm.app.vobject.PersonalBasicInfo;
import com.pltfm.app.vobject.Specialist;
import com.pltfm.app.vobject.SyncPeronalInfoVO;
import com.pltfm.sys.model.SysModelUtil;

/**
 * 个人基本信息处理类
 * 
 * @author cjm
 * @since 2013-7-9
 */
@Component(value = "personalBasicInfoDAO")
public class PersonalBasicInfoDAOImpl implements PersonalBasicInfoDAO {
  @Resource(name = "sqlMapClient")
  /**
   * 数据库操作对象
   */
  private SqlMapClient sqlMapClient;

  public SqlMapClient getSqlMapClient() {
    return sqlMapClient;
  }

  public void setSqlMapClient(SqlMapClient sqlMapClient) {
    this.sqlMapClient = sqlMapClient;
  }



  /**
   * 获取基本信息总条数
   * 
   * @param GWL 基本信息实体
   * @return 返回值
   * @throws SQLException 异常
   */
  public int selectCountByVo(PersonalBasicInfo vo) throws SQLException {
    List list = sqlMapClient.queryForList("PERSONAL_BASIC_INFO.selectCountByVo", vo);

    SysModelUtil countResult = (SysModelUtil) list.get(0);
    // 总条数
    int recs = countResult.getTheCount().intValue();
    return recs;
  }

  /**
   * 根据vo条件查询分类信息page
   * 
   * @param GWL 分页类
   * @param vo 信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public List selectPageByVo(PersonalBasicInfo vo) throws SQLException {
    List pageList = sqlMapClient.queryForList("PERSONAL_BASIC_INFO.searchPageByVo", vo);
    return pageList;
  }

  /**
   * 添加专家信息
   * 
   * @param 专家信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public Integer insertPersonalBasicInfo(PersonalBasicInfo p) throws SQLException {
    // TODO Auto-generated method stub
    Object keyObject =
        sqlMapClient.insert("PERSONAL_BASIC_INFO.ibatorgenerated_insertSelective", p);
    return (Integer) keyObject;
  }


  /**
   * 删除专家信息
   * 
   * @param Rank 专家信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public Integer deletePersonalBasicInfo(PersonalBasicInfo p) throws SQLException {
    return sqlMapClient.delete("PERSONAL_BASIC_INFO.abatorgenerated_deleteByPrimaryKey", p);

  }

  /**
   * 修改专家信息
   * 
   * @param Rank 专家信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public Integer udpatePersonalBasicInfo(PersonalBasicInfo p) throws SQLException {
    return sqlMapClient.update("PERSONAL_BASIC_INFO.abatorgenerated_updateByPrimaryKeySelective",
        p);
  }

  /**
   * 根据登录Id修改个人信息
   * 
   * @param Rank 专家信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public Integer udpatePersonalBasicInfoByLoginId(PersonalBasicInfo p) throws SQLException {
    return sqlMapClient
        .update("PERSONAL_BASIC_INFO.abatorgenerated_updateByPrimaryKeySelectiveByLoginId", p);
  }

  /**
   * 跟据专类型父id查询
   * 
   * @param 专家信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public PersonalBasicInfo getParentId(Integer personalId) throws SQLException {
    PersonalBasicInfo personalBasicInfo = new PersonalBasicInfo();
    personalBasicInfo.setN_PersonalId(personalId);
    return (PersonalBasicInfo) sqlMapClient
        .queryForObject("PERSONAL_BASIC_INFO.abatorgenerated_selectparentidKey", personalBasicInfo);
  }

  /**
   * 跟据专家类型id查询
   * 
   * @param 专家信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public PersonalBasicInfo getPersonalId(Integer personalId) throws SQLException {
    PersonalBasicInfo personalBasicInfo = new PersonalBasicInfo();
    personalBasicInfo.setN_PersonalId(personalId);
    return (PersonalBasicInfo) sqlMapClient.queryForObject(
        "PERSONAL_BASIC_INFO.abatorgenerated_selectByPrimaryKey", personalBasicInfo);
  }

  /**
   * 跟据登录id查询
   * 
   * @param 专家信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public PersonalBasicInfo getLogin(Integer login) throws SQLException {
    PersonalBasicInfo personalBasicInfo = new PersonalBasicInfo();
    personalBasicInfo.setN_LoginId(login);
    return (PersonalBasicInfo) sqlMapClient
        .queryForObject("PERSONAL_BASIC_INFO.abatorgenerated_getLogin", personalBasicInfo);
  }

  /**
   * 按条件查询个人基本信息总数量
   * 
   * @param vo
   * @return 返回值
   */
  @Override
  public int selectCountByPersonalBasicInfo(PersonalBasicInfo vo) throws SQLException {
    List list = sqlMapClient.queryForList("PERSONAL_BASIC_INFO.selectCountByPersonalBasicInfo", vo);

    SysModelUtil countResult = (SysModelUtil) list.get(0);
    // 总条数
    int recs = countResult.getTheCount().intValue();
    return recs;
  }

  /**
   * 根据PersonalBasicInfo条件查询分类信息page
   * 
   * @param page 分页类
   * @param PersonalBasicInfo 个人信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
  public List selectPageByPersonalBasicInfo(PersonalBasicInfo vo) throws SQLException {
    List pageList =
        sqlMapClient.queryForList("PERSONAL_BASIC_INFO.searchPageByPersonalBasicInfo", vo);
    return pageList;
  }

  /**
   * 添加个人信息
   * 
   * @param Rank 个人信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public Integer insert(PersonalBasicInfo p) throws SQLException {
    Object newKey = sqlMapClient.insert("PERSONAL_BASIC_INFO.ibatorgenerated_insertSelective", p);
    return (Integer) newKey;
  }

  /**
   * 根据个人信息ID查询个人信息
   * 
   * @param customer 个人信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
  public Customer selectPageByCustomer(Customer customer) throws SQLException {
    return (Customer) sqlMapClient
        .queryForObject("PERSONAL_BASIC_INFO.abatorgenerated_selectByCustomer", customer);
  }

  /**
   * 根据个人信息ID查询专家个人信息
   * 
   * @param Specialist 专家信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
  public Specialist selectPageBySpecialist(Specialist specialist) throws SQLException {
    return (Specialist) sqlMapClient
        .queryForObject("PERSONAL_BASIC_INFO.abatorgenerated_selectBySpecialist", specialist);
  }

  @Override
  public List<PersonalBasicInfo> queryPersonalInfoList(PersonalBasicInfo vo) throws Exception {
    return sqlMapClient.queryForList("PERSONAL_BASIC_INFO.queryPersonalInfoList", vo);
  }

  @Override
  public List selectPageByPersonalBasicInfoForRebate(PersonalBasicInfo vo) throws SQLException {
    List pageList =
        sqlMapClient.queryForList("PERSONAL_BASIC_INFO.searchPageByPersonalBasicInfoForRebate", vo);
    return pageList;
  }

  @Override
  public int selectCountByPersonalBasicInfoForRebate(PersonalBasicInfo vo) throws SQLException {
    List list = sqlMapClient
        .queryForList("PERSONAL_BASIC_INFO.selectCountByPersonalBasicInfoForRebate", vo);

    SysModelUtil countResult = (SysModelUtil) list.get(0);
    // 总条数
    int recs = countResult.getTheCount().intValue();
    return recs;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<SyncPeronalInfoVO> queryPersonalInfoByAccountLogin(List<String> lstAccountLogin)
      throws SQLException {
    List<SyncPeronalInfoVO> result = sqlMapClient
        .queryForList("PERSONAL_BASIC_INFO.queryPersonalInfoInfoSync2Base", lstAccountLogin);
    return result;
  }

   
  /**
   * 按条件查询个人符合条件的登录ID总数量
   * 
   * @param vo
   * @return 返回值
   */
  @Override
  public int selectLoginIdPageInfoCount(PersonalBasicInfo vo) throws SQLException {
    @SuppressWarnings("rawtypes")
    List list = sqlMapClient.queryForList("PERSONAL_BASIC_INFO.selectLoginIdPageInfoCount", vo);

    SysModelUtil countResult = (SysModelUtil) list.get(0);
    // 总条数
    int recs = countResult.getTheCount().intValue();
    return recs;
  }
  
  
  /**
   * 按条件查询个人符合条件的登录ID信息
   * 
   * @param PersonalBasicInfo 个人信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
@SuppressWarnings("rawtypes")
@Override
  public List searchLoginIdPageInfoList(PersonalBasicInfo vo) throws SQLException {
   
    List pageList =
        sqlMapClient.queryForList("PERSONAL_BASIC_INFO.searchLoginIdPageInfoList", vo);
    return pageList;
  }
  
/**
 * 按条件查询个人基本信息总数量
 * 
 * @param vo
 * @return 返回值
 */
@Override
public int selectPersonalBasicInfoCount(PersonalBasicInfo vo) throws SQLException {
    Integer count = (Integer)sqlMapClient.queryForObject("PERSONAL_BASIC_INFO.selectPersonalInfoCount", vo);
    return count.intValue();
}

/**
 * 根据PersonalBasicInfo条件查询分类信息page
 * 
 * @param page 分页类
 * @param PersonalBasicInfo 个人信息实体
 * @return 返回值
 * @throws SQLException sql异常
 */
@Override
public List selectPersonalBasicInfoByPage(PersonalBasicInfo vo) throws SQLException {
  List pageList =
      sqlMapClient.queryForList("PERSONAL_BASIC_INFO.selectPersonalInfoByPage", vo);
  return pageList;
}

@SuppressWarnings("rawtypes")
@Override
public PersonalBasicInfo getPersonalInfoByLogId(Integer login) throws SQLException {
    PersonalBasicInfo personalBasicInfo = new PersonalBasicInfo();
    personalBasicInfo.setN_LoginId(login);
    
    List resultList =sqlMapClient
        .queryForList("PERSONAL_BASIC_INFO.getPersonalInfoByLogId", personalBasicInfo);
    
    if(resultList!=null){
        personalBasicInfo = (PersonalBasicInfo)resultList.get(0);
    }
    return personalBasicInfo;
}

  
}
