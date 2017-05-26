package com.pltfm.crowdsourcing.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.km.crowdsourcing.model.QrcodeApplyRecord;


public interface QrcodeApplyRecordDao {

  Long insert(QrcodeApplyRecord record) throws SQLException;

  int updateByPrimaryKey(QrcodeApplyRecord record) throws SQLException;

  /**
   * @Title: codeManageList @Description: 查询机构码管理列表 @param @param
   * record @param @return @param @throws SQLException @return List<QrcodeApplyRecord> @throws
   */
  List<QrcodeApplyRecord> codeManageList(QrcodeApplyRecord record) throws SQLException;

  /**
   * @Title: countManageList @Description: 查询机构码管理记录数 @param @param
   * record @param @return @param @throws SQLException @return int @throws
   */
  int countManageList(QrcodeApplyRecord record) throws SQLException;


  Map<String, Object> selectBagmanIdByInsCode(String insCode) throws SQLException;
}
