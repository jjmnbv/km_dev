package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.BnesAnswerInfoDAO;
import com.pltfm.app.vobject.BnesAnswerInfo;
import com.pltfm.app.vobject.BnesAnswerInfoExample;
import com.pltfm.sys.model.SysModelUtil;

/**
 * 安全问题答案信息处理类
 * 
 * @author cjm
 * @since 2013-8-6
 */
@Component(value = "bnesAnswerInfoDAO")
public class BnesAnswerInfoDAOImpl implements BnesAnswerInfoDAO {
  /**
   * 数据库操作对象
   */
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;


  /**
   * 添加安全问题答案信息
   * 
   * @param record 安全问题答案信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public Integer insert(BnesAnswerInfo record) throws SQLException {
    Object newKey = sqlMapClient.insert("BNES_ANSWER_INFO.abatorgenerated_insert", record);
    return (Integer) newKey;
  }


  /**
   * 修改安全问题答案信息
   * 
   * @param record 安全问题答案信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int updateByPrimaryKey(BnesAnswerInfo record) throws SQLException {
    int rows = sqlMapClient.update("BNES_ANSWER_INFO.abatorgenerated_updateByPrimaryKey", record);
    return rows;
  }

  /**
   * 动态修改安全问题答案信息
   * 
   * @param record 安全问题答案信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int updateByPrimaryKeySelective(BnesAnswerInfo record) throws SQLException {
    int rows =
        sqlMapClient.update("BNES_ANSWER_INFO.abatorgenerated_updateByPrimaryKeySelective", record);
    return rows;
  }

  /**
   * 按安全问题答案信息条件查询
   * 
   * @param example 安全问题答案信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  public List selectByExample(BnesAnswerInfoExample example) throws SQLException {
    List list =
        sqlMapClient.queryForList("BNES_ANSWER_INFO.abatorgenerated_selectByExample", example);
    return list;
  }

  /**
   * 根据安全问题答案主键查询单条安全问题答案基本信息
   * 
   * @param nCommercialTenantId 安全问题答案主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  public BnesAnswerInfo selectByPrimaryKey(Integer answerInfoId) throws SQLException {
    BnesAnswerInfo key = new BnesAnswerInfo();
    key.setAnswerInfoId(answerInfoId);
    BnesAnswerInfo record = (BnesAnswerInfo) sqlMapClient
        .queryForObject("BNES_ANSWER_INFO.abatorgenerated_selectByPrimaryKey", key);
    return record;
  }

  /**
   * 根据安全问题id账户查id询单些问题是否已存在
   * 
   * @param nCommercialTenantId 安全问题id
   * @return 返回值
   * @throws SQLException sql异常
   */
  public List selectByPrimaryKey(BnesAnswerInfo bnesAnswerInfo) throws Exception {
    List list = sqlMapClient.queryForList("BNES_ANSWER_INFO.abatorgenerated_getSafeQuestion_Id",
        bnesAnswerInfo);
    return list;
  }

  /**
   * 根据安全问题答案主键查询单条安全问题答案基本信息
   * 
   * @param nCommercialTenantId 安全问题答案主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int deleteByExample(BnesAnswerInfoExample example) throws SQLException {
    int rows = sqlMapClient.delete("BNES_ANSWER_INFO.abatorgenerated_deleteByExample", example);
    return rows;
  }

  /**
   * 按安全问题答案信息条件进行删除
   * 
   * @param example 安全问题答案信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int deleteByPrimaryKey(Integer answerInfoId) throws SQLException {
    BnesAnswerInfo key = new BnesAnswerInfo();
    key.setAnswerInfoId(answerInfoId);
    int rows = sqlMapClient.delete("BNES_ANSWER_INFO.abatorgenerated_deleteByPrimaryKey", key);
    return rows;
  }

  /**
   * 根据安全问题答案主键删除商户基本信息
   * 
   * @param nCommercialTenantId 安全问题答案主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int countByExample(BnesAnswerInfoExample example) throws SQLException {
    Integer count = (Integer) sqlMapClient
        .queryForObject("BNES_ANSWER_INFO.abatorgenerated_countByExample", example);
    return count.intValue();
  }

  /**
   * 按安全问题答案信息条件查询总条数
   * 
   * @param example 安全问题答案信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int updateByExampleSelective(BnesAnswerInfo record, BnesAnswerInfoExample example)
      throws SQLException {
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows =
        sqlMapClient.update("BNES_ANSWER_INFO.abatorgenerated_updateByExampleSelective", parms);
    return rows;
  }

  /**
   * 动态按安全问题答案信息条件进行修改
   * 
   * @param record 安全问题答案信息实体
   * @param example 安全问题答案信息条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int updateByExample(BnesAnswerInfo record, BnesAnswerInfoExample example)
      throws SQLException {
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows = sqlMapClient.update("BNES_ANSWER_INFO.abatorgenerated_updateByExample", parms);
    return rows;
  }

  /**
   * 按条件查询安全问题答案信息总数量
   * 
   * @param vo
   * @return 返回值
   */
  @Override
  public int selectCountByVo(BnesAnswerInfo vo) throws SQLException {
    List list = sqlMapClient.queryForList("BNES_ANSWER_INFO.getAccountInfoCount", vo);

    SysModelUtil countResult = (SysModelUtil) list.get(0);
    // 总条数
    int recs = countResult.getTheCount().intValue();
    return recs;
  }

  /**
   * 根据vo条件查询分类信息page
   * 
   * @param page 分页类
   * @param vo 安全问题答案信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
  public List selectPageByVo(BnesAnswerInfo vo) throws SQLException {
    List pageList = sqlMapClient.queryForList("BNES_ANSWER_INFO.searchPageByVo", vo);
    return pageList;
  }


  /**
   * This class was generated by Abator for iBATIS. This class corresponds to the database table
   * BNES_ANSWER_INFO 修改条件参数类
   * 
   * @abatorgenerated Tue Aug 06 09:20:47 CST 2013
   */
  private static class UpdateByExampleParms extends BnesAnswerInfoExample {
    private Object record;

    public UpdateByExampleParms(Object record, BnesAnswerInfoExample example) {
      super(example);
      this.record = record;
    }

    public Object getRecord() {
      return record;
    }
  }
}
