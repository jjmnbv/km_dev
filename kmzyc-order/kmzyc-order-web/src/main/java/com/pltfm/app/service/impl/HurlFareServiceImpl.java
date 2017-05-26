package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.dao.HurlFareDAO;
import com.pltfm.app.entities.HurlFare;
import com.pltfm.app.entities.HurlFareCriteria;
import com.pltfm.app.entities.HurlFareExample;
import com.pltfm.app.service.DiffAdjService;
import com.pltfm.app.service.HurlFareService;

@SuppressWarnings("unchecked")
@Service("hurlFareService")
public class HurlFareServiceImpl implements HurlFareService {

  @Resource
  private HurlFareDAO hurlFareDAO;

  @Resource
  private DiffAdjService diffAdjService;

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
  public int countByExample(HurlFareExample example) throws SQLException {
    return hurlFareDAO.countByExample(example);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
  public int deleteByExample(HurlFareExample example) throws SQLException {
    return hurlFareDAO.deleteByExample(example);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
  public Long insert(HurlFare record) throws SQLException {
    return hurlFareDAO.insert(record);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
  public List selectByExample(HurlFareExample example) throws SQLException {

    List<HurlFare> list = null;
    list = hurlFareDAO.selectByExample(example);

    // 查询结果为空则查询第一页数据返回
    if (list.size() == 0 && example.getStartIndex() != 0) {
      example.setEndIndex(example.getEndIndex() - example.getStartIndex());
      example.setStartIndex(0);
      list = hurlFareDAO.selectByExample(example);
    }
    return list;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
  public int updateByExample(HurlFare record, HurlFareExample example) throws SQLException {
    return hurlFareDAO.updateByExample(record, example);
  }

  @Override
  public String hurlFareExport(String sno, String filePath, HurlFareCriteria hurlFareCriteria)
      throws SQLException, ServiceException {
    hurlFareCriteria.setSettlementNo(sno);
    HurlFareExample example = HurlFareCriteria.converToExample(hurlFareCriteria);

    int count = countByExample(example);
    List<HurlFare> dataList = null;
    if (count > 0) {
      example.setStartIndex(0);
      example.setEndIndex(count + 1);
      dataList = selectByExample(example);
    }
    filePath = diffAdjService.exportExcel(sno, 2, dataList);
    return filePath;
  }

  @Override
  public Map hurlFareSumResult(HurlFareCriteria hurlFareCriteria) throws ServiceException {
    try {
      return hurlFareDAO.hurlFareSumResult(hurlFareCriteria);
    } catch (SQLException e) {
      throw new ServiceException(e.getErrorCode(), e.getMessage());
    }
  }
}
