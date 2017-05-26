package com.pltfm.crowdsourcing.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.km.commons.general.exception.KmServiceException;
import com.km.crowdsourcing.model.QrcodeApplyRecord;
import com.km.crowdsourcing.model.QrcodeApplyRelation;
import com.km.crowdsourcing.service.QrcodeApplyRecordService;
import com.pltfm.crowdsourcing.dao.QrcodeApplyRecordDao;
import com.pltfm.crowdsourcing.dao.QrcodeApplyRelationDao;

@Component(value = "qrcodeApplyRecordService")
@SuppressWarnings("unused")
public class QrcodeApplyRecordServiceImpl implements QrcodeApplyRecordService {

  @Resource
  private QrcodeApplyRecordDao qrcodeApplyRecordDao;

  @Resource
  private QrcodeApplyRelationDao qrcodeApplyRelationDao;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void applyInstCodes(QrcodeApplyRecord record) throws KmServiceException {
    try {
      Long recorId = qrcodeApplyRecordDao.insert(record);
      List<Long> recorIdList = new ArrayList<Long>();
      for (int i = 0; i < record.getInstitutionCodeCount().intValue(); i++) {
        recorIdList.add(recorId);
      }
      qrcodeApplyRelationDao.bathInsertRelations(recorIdList);
    } catch (Exception e) {
      throw new KmServiceException(e);
    }
  }

  @Override
  public List<QrcodeApplyRecord> codeManageList(QrcodeApplyRecord record)
      throws KmServiceException {
    try {
      return qrcodeApplyRecordDao.codeManageList(record);
    } catch (Exception e) {
      throw new KmServiceException(e);
    }
  }

  @Override
  public int countManageList(QrcodeApplyRecord record) throws KmServiceException {
    try {
      return qrcodeApplyRecordDao.countManageList(record);
    } catch (Exception e) {
      throw new KmServiceException(e);
    }
  }

  @Override
  public List<QrcodeApplyRelation> selectByRelation(QrcodeApplyRelation relation)
      throws KmServiceException {
    try {
      return qrcodeApplyRelationDao.selectByRelation(relation);
    } catch (Exception e) {
      throw new KmServiceException(e);
    }
  }

  @Override
  public Map<String, Object> selectBagmanIdByInsCode(String InsCode) throws KmServiceException {

    try {
      return qrcodeApplyRecordDao.selectBagmanIdByInsCode(InsCode);
    } catch (SQLException e) {
      throw new KmServiceException(e);
    }
  }

}
