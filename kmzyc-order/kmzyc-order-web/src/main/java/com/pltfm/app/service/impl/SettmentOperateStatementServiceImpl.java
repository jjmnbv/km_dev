package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.dao.SettmentOperateStatementDAO;
import com.pltfm.app.entities.SettmentOperateStatement;
import com.pltfm.app.entities.SettmentOperateStatementExample;
import com.pltfm.app.service.SettmentOperateStatementService;

@SuppressWarnings("unchecked")
@Service("settmentOperateStatementService")
public class SettmentOperateStatementServiceImpl implements SettmentOperateStatementService {

  @Resource
  private SettmentOperateStatementDAO settmentOperateStatementDAO;

  @Override
  public int countByExample(SettmentOperateStatementExample example) throws SQLException {
    return settmentOperateStatementDAO.countByExample(example);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
  public int deleteByExample(SettmentOperateStatementExample example) throws SQLException {
    return settmentOperateStatementDAO.deleteByExample(example);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
  public Long insert(SettmentOperateStatement record) throws SQLException {
    return settmentOperateStatementDAO.insert(record);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
  public List selectByExample(SettmentOperateStatementExample example) throws SQLException {
    return settmentOperateStatementDAO.selectByExample(example);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
  public int updateByExample(SettmentOperateStatement record,
      SettmentOperateStatementExample example) throws SQLException {
    return settmentOperateStatementDAO.updateByExample(record, example);
  }
}
