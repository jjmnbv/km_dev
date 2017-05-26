package com.pltfm.crowdsourcing.dao;

import java.sql.SQLException;
import java.util.List;

import com.km.crowdsourcing.model.QrcodeApplyRelation;


public interface QrcodeApplyRelationDao {

  /**
   * @Title: bathInsertRelations @Description: 批量插入二维码与地退人员关系表 @param @param
   * list @param @return @param @throws SQLException @return int @throws
   */
  void bathInsertRelations(List<Long> list) throws SQLException;

  /**
   * @Title: selectByRelation @Description: 条件查询二维码、业务员关系数据 @param @return @param @throws
   * SQLException @return List<QrcodeApplyRelation> @throws
   */
  List<QrcodeApplyRelation> selectByRelation(QrcodeApplyRelation relation) throws SQLException;
}
