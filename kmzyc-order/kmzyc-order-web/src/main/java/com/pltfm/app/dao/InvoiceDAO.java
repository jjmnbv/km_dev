package com.pltfm.app.dao;

import java.sql.SQLException;
import java.util.List;

import com.pltfm.app.entities.Invoice;
import com.pltfm.app.entities.InvoiceExample;

@SuppressWarnings("unchecked")
public interface InvoiceDAO {
  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.INVOICE
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  int countByExample(InvoiceExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.INVOICE
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  int deleteByExample(InvoiceExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.INVOICE
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  Long insert(Invoice record) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.INVOICE
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  Long insertSelective(Invoice record) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.INVOICE
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  List selectByExample(InvoiceExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.INVOICE
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  int updateByExampleSelective(Invoice record, InvoiceExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table KMORDER.INVOICE
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  int updateByExample(Invoice record, InvoiceExample example) throws SQLException;

  Invoice selectByPrimaryKey(Long invoiceId) throws SQLException;
}
