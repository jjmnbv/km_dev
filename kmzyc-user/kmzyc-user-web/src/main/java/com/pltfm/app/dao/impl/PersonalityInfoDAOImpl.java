package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.commons.page.Page;
import com.pltfm.app.dao.PersonalityInfoDAO;
import com.pltfm.app.dataobject.UserInfoDO;
import com.pltfm.app.vobject.PersonalityInfo;
import com.pltfm.app.vobject.PersonalityInfoExample;
import com.pltfm.sys.model.SysModelUtil;

/**
 * 个人个性信息处理类
 * 
 * @author cjm
 * @since 2013-7-9
 */
@SuppressWarnings({"unchecked", "unused"})
@Component(value = "personalityInfoDAO")
public class PersonalityInfoDAOImpl implements PersonalityInfoDAO {
  /**
   * 数据库操作对象
   */
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;



  /**
   * 添加个人个性信息
   * 
   * @param record 个人个性信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public Integer insert(PersonalityInfo record) throws SQLException {
    Object newKey = sqlMapClient.insert("PERSONALITY_INFO.abatorgenerated_insert", record);
    return (Integer) newKey;

  }

  /**
   * 修改个人个性信息
   * 
   * @param record 个人个性信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public int updateByPrimaryKey(PersonalityInfo record) throws SQLException {
    int rows = sqlMapClient.update("PERSONALITY_INFO.abatorgenerated_updateByPrimaryKey", record);
    return rows;
  }

  /**
   * 动态修改个人个性信息
   * 
   * @param record 个人个性信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public int updateByPrimaryKeySelective(PersonalityInfo record) throws SQLException {
    int rows =
        sqlMapClient.update("PERSONALITY_INFO.abatorgenerated_updateByPrimaryKeySelective", record);
    return rows;
  }

  /**
   * 按个人个性信息条件查询
   * 
   * @param example 个人个性信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public List selectByExample(PersonalityInfoExample example) throws SQLException {
    return sqlMapClient.queryForList("PERSONALITY_INFO.abatorgenerated_selectByExample", example);
  }

  /**
   * 根据个人个性主键查询单条商户基本信息
   * 
   * @param nCommercialTenantId 个人个性主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public PersonalityInfo selectByPrimaryKey(Integer n_PersonalityId) throws SQLException {
    PersonalityInfo key = new PersonalityInfo();
    key.setN_PersonalityId(n_PersonalityId);
    PersonalityInfo record = (PersonalityInfo) sqlMapClient
        .queryForObject("PERSONALITY_INFO.abatorgenerated_selectByPrimaryKey", key);
    return record;
  }

  /**
   * 按个人个性信息条件进行删除
   * 
   * @param example 个人个性信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public int deleteByExample(PersonalityInfoExample example) throws SQLException {
    int rows = sqlMapClient.delete("PERSONALITY_INFO.abatorgenerated_deleteByExample", example);
    return rows;
  }

  /**
   * 根据个人个性主键删除商户基本信息
   * 
   * @param nCommercialTenantId 个人个性主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public int deleteByPrimaryKey(Integer n_PersonalityId) throws SQLException {
    PersonalityInfo key = new PersonalityInfo();
    key.setN_PersonalityId(n_PersonalityId);
    int rows = sqlMapClient.delete("PERSONALITY_INFO.abatorgenerated_deleteByPrimaryKey", key);
    return rows;
  }

  /**
   * 按个人个性信息条件查询总条数
   * 
   * @param example 个人个性信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public int countByExample(PersonalityInfoExample example) throws SQLException {
    return (Integer) sqlMapClient.queryForObject("PERSONALITY_INFO.abatorgenerated_countByExample",
        example);
  }

  /**
   * 按头衔id查询是否存在头衔
   * 
   * @param rankId 头衔id
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public int countRank(Integer rankId) throws SQLException {
    PersonalityInfo p = new PersonalityInfo();
    p.setN_RankId(rankId);
    Integer count = (Integer) sqlMapClient.queryForObject("PERSONALITY_INFO.selectRank", p);
    return count.intValue();
  }

  /**
   * 动态按个人个性信息条件进行修改
   * 
   * @param record 个人个性信息实体
   * @param example 个人个性信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public int updateByExampleSelective(PersonalityInfo record, PersonalityInfoExample example)
      throws SQLException {
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows =
        sqlMapClient.update("PERSONALITY_INFO.abatorgenerated_updateByExampleSelective", parms);
    return rows;
  }

  /**
   * 按个人个性信息条件进行修改
   * 
   * @param record 个人个性信息实体
   * @param example 个人个性信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public int updateByExample(PersonalityInfo record, PersonalityInfoExample example)
      throws SQLException {
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows = sqlMapClient.update("PERSONALITY_INFO.abatorgenerated_updateByExample", parms);
    return rows;
  }

  /**
   * This class was generated by Abator for iBATIS. This class corresponds to the database table
   * PERSONALITY_INFO 修改条件参数类
   * 
   * @abatorgenerated Tue Jul 09 10:08:15 CST 2013
   */
  private static class UpdateByExampleParms extends PersonalityInfoExample {
    private Object record;

    public UpdateByExampleParms(Object record, PersonalityInfoExample example) {
      super(example);
      this.record = record;
    }



    public Object getRecord() {
      return record;
    }
  }

  public SqlMapClient getSqlMapClient() {
    return sqlMapClient;
  }

  public void setSqlMapClient(SqlMapClient sqlMapClient) {
    this.sqlMapClient = sqlMapClient;
  }

  /**
   * 根据vo条件查询分类信息page
   * 
   * @param page 分页类
   * @param vo 个人个性信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
  public Page selectPageByVo(Page page, PersonalityInfo vo) throws SQLException {
    // 用List接收
    List list = sqlMapClient.queryForList("PERSONALITY_INFO.getPersonalityInfoCount", vo);

    SysModelUtil countResult = (SysModelUtil) list.get(0);
    // 总条数
    int recs = countResult.getTheCount().intValue();

    int pagecount = 1;
    // 总页数
    if (recs > 1) {
      pagecount = (recs - 1) / page.getPageSize() + 1;
    }
    page.setRecordCount(recs);
    page.setPageCount(pagecount);

    List pageList = sqlMapClient.queryForList("PERSONALITY_INFO.searchPageByVo", vo);

    page.setDataList(pageList);
    return page;
  }

  /**
   * 根据登录主键查询单条个人个性信息
   * 
   * @param nLoginId 个人个性主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
  public PersonalityInfo selectByPersonalityInfo(Integer nLoginId) throws SQLException {
    PersonalityInfo record = (PersonalityInfo) sqlMapClient
        .queryForObject("PERSONALITY_INFO.abatorgenerated_selectByPrimaryKeyLogin", nLoginId);
    return record;
  }

  /**
   * 根据登录ID更新上年度消费金额
   * 
   * @param nCommercialTenantId 个人个性主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
  public int updateLastYearByLoginId(Integer n_LoginId, double lastYear_Amount)
      throws SQLException {
    PersonalityInfo key = new PersonalityInfo();
    key.setN_LoginId(n_LoginId);
    key.setLastYear_Amount(lastYear_Amount);
    int rows = sqlMapClient.update("PERSONALITY_INFO.abatorgenerated_updateLastYearByLoginId", key);
    return rows;

  }

  /**
   * 更新用户表积分
   * 
   * @param params
   * @return
   * @throws SQLException
   */
  @Override
public int updateUserScore(Map<String, Object> params) throws SQLException {
    return sqlMapClient.update("PERSONALITY_INFO.SQL_UPDATE_USER_SCORE", params);
  }

  @Override
  public UserInfoDO queryUserScoreInfoByCardNum(String cardNum) throws SQLException {
    return (UserInfoDO) sqlMapClient
        .queryForObject("PERSONALITY_INFO.SQL_QUERY_USER_SCOREINFO_BY_CARDNUM", cardNum);
  }
}
