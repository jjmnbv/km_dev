package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.dao.SettlementRefundDAO;
import com.pltfm.app.entities.SettlementRefund;
import com.pltfm.app.entities.SettlementRefundCriteria;
import com.pltfm.app.entities.SettlementRefundExample;
import com.pltfm.app.service.DiffAdjService;
import com.pltfm.app.service.SettlementRefundService;

@SuppressWarnings("unchecked")
@Service("settlementRefundService")
public class SettlementRefundServiceImpl implements SettlementRefundService {
  @Resource
  private SettlementRefundDAO settlementRefundDAO;

  @Resource
  private DiffAdjService diffAdjService;

  @Override
  public int countByExample(SettlementRefundExample example) throws SQLException {
    return settlementRefundDAO.countByExample(example);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
  public int deleteByExample(SettlementRefundExample example) throws SQLException {
    return settlementRefundDAO.deleteByExample(example);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
  public Long insert(SettlementRefund record) throws SQLException {
    return settlementRefundDAO.insert(record);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
  public List selectByExample(SettlementRefundExample example) throws SQLException {

    List<SettlementRefund> list = null;
    list = settlementRefundDAO.selectByExample(example);

    // 查询结果为空则查询第一页数据返回
    if (list.size() == 0 && example.getStartIndex() != 0) {
      example.setEndIndex(example.getEndIndex() - example.getStartIndex());
      example.setStartIndex(0);
      list = settlementRefundDAO.selectByExample(example);
    }
    return list;

  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
  public int updateByExample(SettlementRefund record, SettlementRefundExample example)
      throws SQLException {
    return settlementRefundDAO.updateByExample(record, example);
  }

  @Override
  public String refundExport(String sno, String filePath,
      SettlementRefundCriteria settlementRefundCriteria) throws SQLException, ServiceException {
    settlementRefundCriteria.setSettlementNo(sno);
    SettlementRefundExample example =
        SettlementRefundCriteria.converToExample(settlementRefundCriteria);

    int count = countByExample(example);
    List<SettlementRefund> dataList = null;
    if (count > 0) {
      example.setStartIndex(0);
      example.setEndIndex(count + 1);
      dataList = selectByExample(example);
    }
    filePath = diffAdjService.exportExcel(sno, 3, dataList);
    return filePath;
  }

  @Override
  public Map refundSum(SettlementRefundCriteria settlementRefundCriteria) throws ServiceException {
    try {
      return settlementRefundDAO.refundSum(settlementRefundCriteria);
    } catch (Exception e) {
      throw new ServiceException(e.hashCode(), e.getMessage());
    }
  }

}
