package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.MobileCodeInfDAO;
import com.pltfm.app.vobject.MobileCodeInf;
import com.pltfm.app.vobject.MobileCodeInfExample;
import com.pltfm.sys.model.SysModelUtil;

/**
 * 手机随机码信息处理类
 * 
 * @author cjm
 * @since 2013-7-23
 */
@Component(value = "mobileCodeInfDAO")
public class MobileCodeInfDAOImpl implements MobileCodeInfDAO {
  /**
   * 数据库操作对象
   */
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;



  /**
   * 添加账户手机随机码信息
   * 
   * @param record 手机随机码实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public Integer insert(MobileCodeInf record) throws SQLException {
    Object newKey = sqlMapClient.insert("MOBILE_CODE_INF.abatorgenerated_insert", record);
    return (Integer) newKey;
  }

  /**
   * 修改账户手机随机码信息
   * 
   * @param record 手机随机码实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int updateByPrimaryKey(MobileCodeInf record) throws SQLException {
    int rows = sqlMapClient.update("MOBILE_CODE_INF.abatorgenerated_updateByPrimaryKey", record);
    return rows;
  }

  public int isupdatecode(MobileCodeInf record) throws SQLException {
    int rows = sqlMapClient.update("MOBILE_CODE_INF.abatorgenerated_isupdatecode", record);
    return rows;
  }

  /**
   * 动态修改账户手机随机码信息
   * 
   * @param record 手机随机码实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int updateByPrimaryKeySelective(MobileCodeInf record) throws SQLException {
    int rows =
        sqlMapClient.update("MOBILE_CODE_INF.abatorgenerated_updateByPrimaryKeySelective", record);
    return rows;
  }

  /**
   * 按账户手机随机码信息条件查询
   * 
   * @param example 手机随机码条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  public List selectByExample(MobileCodeInfExample example) throws SQLException {
    List list =
        sqlMapClient.queryForList("MOBILE_CODE_INF.abatorgenerated_selectByExample", example);
    return list;
  }

  /**
   * 根据手机随机码主键查询单条手机随机码信息
   * 
   * @param nCellPhoneTattedCodeId 手机随机码主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  public MobileCodeInf selectByPrimaryKey(Integer nCellPhoneTattedCodeId) throws SQLException {
    MobileCodeInf key = new MobileCodeInf();
    key.setN_CellPhoneTattedCodeId(nCellPhoneTattedCodeId);
    MobileCodeInf record = (MobileCodeInf) sqlMapClient
        .queryForObject("MOBILE_CODE_INF.abatorgenerated_selectByPrimaryKey", key);
    return record;
  }

  /**
   * 按账户手机随机码信息条件进行删除
   * 
   * @param example 手机随机码条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int deleteByExample(MobileCodeInfExample example) throws SQLException {
    int rows = sqlMapClient.delete("MOBILE_CODE_INF.abatorgenerated_deleteByExample", example);
    return rows;
  }

  /**
   * 根据账户手机随机码主键删除手机随机码信息
   * 
   * @param nCommercialTenantId 手机随机码主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int deleteByPrimaryKey(Integer nCellPhoneTattedCodeId) throws SQLException {
    MobileCodeInf key = new MobileCodeInf();
    key.setN_CellPhoneTattedCodeId(nCellPhoneTattedCodeId);
    int rows = sqlMapClient.delete("MOBILE_CODE_INF.abatorgenerated_deleteByPrimaryKey", key);
    return rows;
  }

  /**
   * 按账户手机随机码信息条件查询总条数
   * 
   * @param example 手机随机码条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int countByExample(MobileCodeInfExample example) throws SQLException {
    Integer count = (Integer) sqlMapClient
        .queryForObject("MOBILE_CODE_INF.abatorgenerated_countByExample", example);
    return count.intValue();
  }

  /**
   * 动态按账户手机随机码信息条件进行修改
   * 
   * @param record 手机随机码实体
   * @param example 手机随机码条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int updateByExampleSelective(MobileCodeInf record, MobileCodeInfExample example)
      throws SQLException {
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows =
        sqlMapClient.update("MOBILE_CODE_INF.abatorgenerated_updateByExampleSelective", parms);
    return rows;
  }

  /**
   * 按账户手机随机码信息条件进行修改
   * 
   * @param record 手机随机码实体
   * @param example 手机随机码条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int updateByExample(MobileCodeInf record, MobileCodeInfExample example)
      throws SQLException {
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows = sqlMapClient.update("MOBILE_CODE_INF.abatorgenerated_updateByExample", parms);
    return rows;
  }

  /**
   * 按条件查询手机随机码信息总数量
   * 
   * @param vo 手机随机码实体
   * @return 返回值
   */
  @Override
  public int selectCountByVo(MobileCodeInf vo) throws SQLException {
    List list = sqlMapClient.queryForList("MOBILE_CODE_INF.getMobileCodeInfCount", vo);

    SysModelUtil countResult = (SysModelUtil) list.get(0);

    // 总条数
    int recs = countResult.getTheCount().intValue();

    return recs;
  }

  /**
   * 根据vo条件查询分类信息page
   * 
   * @param page 分页类
   * @param vo 手机随机码实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
  public List selectPageByVo(MobileCodeInf vo) throws SQLException {
    List pageList = sqlMapClient.queryForList("MOBILE_CODE_INF.searchPageByVo", vo);
    return pageList;
  }

  /**
   * 根据手机随机码主键查询单条手机随机码信息
   * 
   * @param nCellPhoneTattedCodeId 手机随机码主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
  public MobileCodeInf selectByMobileCodeInf(MobileCodeInf mobileCodeInf) throws SQLException {
    MobileCodeInf record = (MobileCodeInf) sqlMapClient
        .queryForObject("MOBILE_CODE_INF.selectByMobileCodeInf", mobileCodeInf);
    return record;
  }


  /**
   * This class was generated by Abator for iBATIS. This class corresponds to the database table
   * MOBILE_CODE_INF 修改条件参数类
   * 
   * @abatorgenerated Tue Jul 23 09:07:31 CST 2013
   */
  private static class UpdateByExampleParms extends MobileCodeInfExample {
    private Object record;

    public UpdateByExampleParms(Object record, MobileCodeInfExample example) {
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


}
