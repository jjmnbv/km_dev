package com.kmzyc.promotion.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.promotion.app.dao.BaseDao;
import com.kmzyc.promotion.app.dao.SuppliersCertificateDAO;
import com.kmzyc.supplier.model.SuppliersCertificate;
import com.kmzyc.supplier.model.example.SuppliersCertificateExample;

@Component("suppliersCertificateDAO")
@SuppressWarnings({"unchecked", "unused"})
public class SuppliersCertificateDAOImpl extends BaseDao implements SuppliersCertificateDAO {
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database table
   * SUPPLIERS_CERTIFICATE
   * 
   * @ibatorgenerated Tue Jan 14 13:36:08 CST 2014
   */
  @Resource
  private SqlMapClient sqlMapClient;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SUPPLIERS_CERTIFICATE
   * 
   * @ibatorgenerated Tue Jan 14 13:36:08 CST 2014
   */
  @Override
  public int countByExample(SuppliersCertificateExample example) throws SQLException {
    // mkw 20150819 add

    // end
    Integer count =
        (Integer) sqlMapClient.queryForObject(
            "SUPPLIERS_CERTIFICATE.ibatorgenerated_countByExample", example);
    return count.intValue();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SUPPLIERS_CERTIFICATE
   * 
   * @ibatorgenerated Tue Jan 14 13:36:08 CST 2014
   */
  @Override
  public int deleteByExample(SuppliersCertificateExample example) throws SQLException {
    // mkw 20150819 add

    // end
    int rows =
        sqlMapClient.delete("SUPPLIERS_CERTIFICATE.ibatorgenerated_deleteByExample", example);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SUPPLIERS_CERTIFICATE
   * 
   * @ibatorgenerated Tue Jan 14 13:36:08 CST 2014
   */
  @Override
  public int deleteByPrimaryKey(Long scId) throws SQLException {
    // mkw 20150819 add

    // end
    SuppliersCertificate key = new SuppliersCertificate();
    key.setScId(scId);
    int rows = sqlMapClient.delete("SUPPLIERS_CERTIFICATE.ibatorgenerated_deleteByPrimaryKey", key);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SUPPLIERS_CERTIFICATE
   * 
   * @ibatorgenerated Tue Jan 14 13:36:08 CST 2014
   */
  @Override
  public void insert(SuppliersCertificate record) throws SQLException {
    // mkw 20150819 add

    // end
    sqlMapClient.insert("SUPPLIERS_CERTIFICATE.ibatorgenerated_insert", record);
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SUPPLIERS_CERTIFICATE
   * 
   * @ibatorgenerated Tue Jan 14 13:36:08 CST 2014
   */
  @Override
  public void insertSelective(SuppliersCertificate record) throws SQLException {
    // mkw 20150819 add

    // end
    sqlMapClient.insert("SUPPLIERS_CERTIFICATE.ibatorgenerated_insertSelective", record);
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SUPPLIERS_CERTIFICATE
   * 
   * @ibatorgenerated Tue Jan 14 13:36:08 CST 2014
   */
  @Override
  public List selectByExample(SuppliersCertificateExample example) throws SQLException {
    // mkw 20150819 add

    // end
    List list =
        sqlMapClient.queryForList("SUPPLIERS_CERTIFICATE.ibatorgenerated_selectByExample", example);
    return list;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SUPPLIERS_CERTIFICATE
   * 
   * @ibatorgenerated Tue Jan 14 13:36:08 CST 2014
   */
  @Override
  public SuppliersCertificate selectByPrimaryKey(Long scId) throws SQLException {
    // mkw 20150819 add

    // end
    SuppliersCertificate key = new SuppliersCertificate();
    key.setScId(scId);
    SuppliersCertificate record =
        (SuppliersCertificate) sqlMapClient.queryForObject(
            "SUPPLIERS_CERTIFICATE.ibatorgenerated_selectByPrimaryKey", key);
    return record;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SUPPLIERS_CERTIFICATE
   * 
   * @ibatorgenerated Tue Jan 14 13:36:08 CST 2014
   */
  @Override
  public int updateByExampleSelective(SuppliersCertificate record,
      SuppliersCertificateExample example) throws SQLException {
    // mkw 20150819 add

    // end
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows =
        sqlMapClient
            .update("SUPPLIERS_CERTIFICATE.ibatorgenerated_updateByExampleSelective", parms);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SUPPLIERS_CERTIFICATE
   * 
   * @ibatorgenerated Tue Jan 14 13:36:08 CST 2014
   */
  @Override
  public int updateByExample(SuppliersCertificate record, SuppliersCertificateExample example)
      throws SQLException {
    // mkw 20150819 add

    // end
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows = sqlMapClient.update("SUPPLIERS_CERTIFICATE.ibatorgenerated_updateByExample", parms);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SUPPLIERS_CERTIFICATE
   * 
   * @ibatorgenerated Tue Jan 14 13:36:08 CST 2014
   */
  @Override
  public int updateByPrimaryKeySelective(SuppliersCertificate record) throws SQLException {
    // mkw 20150819 add

    // end
    int rows =
        sqlMapClient.update("SUPPLIERS_CERTIFICATE.ibatorgenerated_updateByPrimaryKeySelective",
            record);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SUPPLIERS_CERTIFICATE
   * 
   * @ibatorgenerated Tue Jan 14 13:36:08 CST 2014
   */
  @Override
  public int updateByPrimaryKey(SuppliersCertificate record) throws SQLException {
    // mkw 20150819 add

    // end
    int rows =
        sqlMapClient.update("SUPPLIERS_CERTIFICATE.ibatorgenerated_updateByPrimaryKey", record);
    return rows;
  }

  /**
   * This class was generated by Apache iBATIS ibator. This class corresponds to the database table
   * SUPPLIERS_CERTIFICATE
   * 
   * @ibatorgenerated Tue Jan 14 13:36:08 CST 2014
   */
  private static class UpdateByExampleParms extends SuppliersCertificateExample {
    private final Object record;

    public UpdateByExampleParms(Object record, SuppliersCertificateExample example) {
      super(example);
      this.record = record;
    }

    public Object getRecord() {
      return record;
    }
  }

  @Override
  public List<SuppliersCertificate> selectSuppIdList(Long suppId) throws SQLException {
    // mkw 20150819 add

    // end
    SuppliersCertificate suppliersCertificate = new SuppliersCertificate();
    suppliersCertificate.setSupplierId(suppId);
    return sqlMapClient.queryForList("SUPPLIERS_CERTIFICATE.ibatorgenerated_selectBySuppId",
        suppliersCertificate);
  }

  /**
   * 根据供应商id删除纸质文件
   */
  @Override
  public int deleteBySuppId(Long suppId) throws SQLException {
    // mkw 20150819 add

    // end
    SuppliersCertificate suppliersCertificate = new SuppliersCertificate();
    suppliersCertificate.setSupplierId(suppId);
    int row =
        sqlMapClient.delete("SUPPLIERS_CERTIFICATE.ibatorgenerated_deleteByPrimarySuppId",
            suppliersCertificate);
    return row;
  }

  @Override
  public SuppliersCertificate selectSuppIdPath(String path) throws SQLException {
    // mkw 20150819 add

    // end
    SuppliersCertificate suppliersCertificate = new SuppliersCertificate();
    suppliersCertificate.setFilePath(path);
    return (SuppliersCertificate) sqlMapClient.queryForObject(
        "SUPPLIERS_CERTIFICATE.ibatorgenerated_selectBySuppPath", suppliersCertificate);
  }
}
