package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.EmailInfoDAO;
import com.pltfm.app.vobject.Coupons;
import com.pltfm.app.vobject.EmailInfo;
import com.pltfm.app.vobject.EmailInfoExample;
import com.pltfm.sys.model.SysModelUtil;

/**
 * 邮箱验证信息处理类
 * 
 * @author cjm
 * @since 2013-7-23
 */
@Component(value = "emailInfoDAO")
public class EmailInfoDAOImpl implements EmailInfoDAO {

  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  /**
   * 添加账户邮箱验证信息
   * 
   * @param record 邮箱验证实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public Integer insert(EmailInfo record) throws SQLException {

    Object newKey = sqlMapClient.insert("EMAIL_INFO.abatorgenerated_insert", record);
    return (Integer) newKey;
  }

  /**
   * 修改账户邮箱验证信息
   * 
   * @param record 邮箱验证实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int updateByPrimaryKey(EmailInfo record) throws SQLException {
    int rows = sqlMapClient.update("EMAIL_INFO.abatorgenerated_updateByPrimaryKey", record);
    return rows;
  }

  /**
   * 动态修改账户邮箱验证信息
   * 
   * @param record 邮箱验证实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int updateByPrimaryKeySelective(EmailInfo record) throws SQLException {
    int rows =
        sqlMapClient.update("EMAIL_INFO.abatorgenerated_updateByPrimaryKeySelective", record);
    return rows;
  }

  /**
   * 按账户邮箱验证信息条件查询
   * 
   * @param example 邮箱验证条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  public List selectByExample(EmailInfoExample example) throws SQLException {
    List list = sqlMapClient.queryForList("EMAIL_INFO.abatorgenerated_selectByExample", example);
    return list;
  }

  /**
   * 根据邮箱验证主键查询单条手机随机码信息
   * 
   * @param nCellPhoneTattedCodeId 邮箱验证主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  public EmailInfo selectByPrimaryKey(Integer nEmailId) throws SQLException {
    EmailInfo key = new EmailInfo();
    key.setN_EmailId(nEmailId);
    EmailInfo record = (EmailInfo) sqlMapClient
        .queryForObject("EMAIL_INFO.abatorgenerated_selectByPrimaryKey", key);
    return record;
  }

  /**
   * 按账户邮箱验证信息条件进行删除
   * 
   * @param example 邮箱验证条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int deleteByExample(EmailInfoExample example) throws SQLException {
    int rows = sqlMapClient.delete("EMAIL_INFO.abatorgenerated_deleteByExample", example);
    return rows;
  }

  /**
   * 根据账户邮箱验证主键删除手机随机码信息
   * 
   * @param nCommercialTenantId 邮箱验证主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int deleteByPrimaryKey(Integer nEmailId) throws SQLException {
    EmailInfo key = new EmailInfo();
    key.setN_EmailId(nEmailId);
    int rows = sqlMapClient.delete("EMAIL_INFO.abatorgenerated_deleteByPrimaryKey", key);
    return rows;
  }

  /**
   * 按账户邮箱验证信息条件查询总条数
   * 
   * @param example 邮箱验证条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int countByExample(EmailInfoExample example) throws SQLException {
    Integer count =
        (Integer) sqlMapClient.queryForObject("EMAIL_INFO.abatorgenerated_countByExample", example);
    return count.intValue();
  }

  /**
   * 动态按账户邮箱验证信息条件进行修改
   * 
   * @param record 邮箱验证实体
   * @param example 邮箱验证条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int updateByExampleSelective(EmailInfo record, EmailInfoExample example)
      throws SQLException {
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows = sqlMapClient.update("EMAIL_INFO.abatorgenerated_updateByExampleSelective", parms);
    return rows;
  }

  /**
   * 按账户邮箱验证信息条件进行修改
   * 
   * @param record 邮箱验证实体
   * @param example 邮箱验证条件
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int updateByExample(EmailInfo record, EmailInfoExample example) throws SQLException {
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows = sqlMapClient.update("EMAIL_INFO.abatorgenerated_updateByExample", parms);
    return rows;
  }

  /**
   * 按条件查询手机随机码信息总数量
   * 
   * @param vo 邮箱验证信息类
   * @return 返回值
   */
  @Override
  public int selectCountByVo(EmailInfo vo) throws SQLException {
    List list = sqlMapClient.queryForList("EMAIL_INFO.getEmailInfoCount", vo);

    SysModelUtil countResult = (SysModelUtil) list.get(0);

    // 总条数
    int recs = countResult.getTheCount().intValue();

    return recs;
  }



  /**
   * 根据vo条件查询分类信息page
   * 
   * @param page 分页类
   * @param vo 邮箱验证信息类
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
  public List selectPageByVo(EmailInfo vo) throws SQLException {
    List pageList = sqlMapClient.queryForList("EMAIL_INFO.searchPageByVo", vo);
    return pageList;
  }

  /**
   * 根据账户主键和随机码查询单条手机随机码信息
   * 
   * @param nCellPhoneTattedCodeId 邮箱验证主键
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
  public EmailInfo selectByEmailInfo(EmailInfo emailInfo) throws SQLException {
    EmailInfo record =
        (EmailInfo) sqlMapClient.queryForObject("EMAIL_INFO.selectByEmailInfo", emailInfo);
    return record;
  }

  /**
   * 查询优惠券信息总数
   * 
   * @param
   * @return 返回值
   * @throws SQLException sql异常
   */
  public int selectCountByCoupons(Coupons coupons) throws SQLException {
    List list = sqlMapClient.queryForList("EMAIL_INFO.getCouponsCount", coupons);

    SysModelUtil countResult = (SysModelUtil) list.get(0);

    // 总条数
    int recs = countResult.getTheCount().intValue();

    return recs;
  }


  @SuppressWarnings("unchecked")
  public List<Coupons> selectPageByCoupons(Coupons coupons) throws SQLException {
    List<Coupons> pageList = sqlMapClient.queryForList("EMAIL_INFO.searchPageByCoupons", coupons);
    return pageList;
  }


  /**
   * This class was generated by Abator for iBATIS. This class corresponds to the database table
   * EMAIL_INFO 修改条件参数类
   * 
   * @abatorgenerated Tue Jul 23 16:15:39 CST 2013
   */
  private static class UpdateByExampleParms extends EmailInfoExample {
    private Object record;

    public UpdateByExampleParms(Object record, EmailInfoExample example) {
      super(example);
      this.record = record;
    }

    public Object getRecord() {
      return record;
    }
  }



}
