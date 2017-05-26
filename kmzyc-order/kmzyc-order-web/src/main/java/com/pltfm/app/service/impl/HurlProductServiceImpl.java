package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.dao.HurlProductDAO;
import com.pltfm.app.entities.HurlProduct;
import com.pltfm.app.entities.HurlProductCriteria;
import com.pltfm.app.entities.HurlProductExample;
import com.pltfm.app.service.DiffAdjService;
import com.pltfm.app.service.HurlProductService;

@SuppressWarnings("unchecked")
@Service("hurlProductService")
public class HurlProductServiceImpl implements HurlProductService {

  @Resource
  private HurlProductDAO hurlProductDAO;

  @Resource
  private DiffAdjService diffAdjService;

  @Override
  public int countByExample(HurlProductExample example) throws SQLException {
    return hurlProductDAO.countByExample(example);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
  public int deleteByExample(HurlProductExample example) throws SQLException {
    return hurlProductDAO.deleteByExample(example);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
  public Long insert(HurlProduct record) throws SQLException {
    return hurlProductDAO.insert(record);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
  public List selectByExample(HurlProductExample example) throws SQLException {

    List<HurlProduct> list = null;
    list = hurlProductDAO.selectByExample(example);

    // 查询结果为空则查询第一页数据返回
    if (list.size() == 0 && example.getStartIndex() != 0) {
      example.setEndIndex(example.getEndIndex() - example.getStartIndex());
      example.setStartIndex(0);
      list = hurlProductDAO.selectByExample(example);
    }
    return list;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
  public int updateByExample(HurlProduct record, HurlProductExample example) throws SQLException {
    return hurlProductDAO.updateByExample(record, example);
  }


  @Override
  public String hurlExport(String sno, String filePath, HurlProductCriteria hurlProductCriteria)
      throws SQLException, ServiceException {
    // if(StringUtils.isEmpty(hurlIds)){
    // return null;
    // }
    hurlProductCriteria.setSettlementNo(sno);
    HurlProductExample example = HurlProductCriteria.converToExample(hurlProductCriteria);

    int count = countByExample(example);
    List<HurlProduct> dataList = null;
    if (count > 0) {
      example.setStartIndex(0);
      example.setEndIndex(count + 1);
      dataList = selectByExample(example);
    }
    filePath = diffAdjService.exportExcel(sno, 1, dataList);
    return filePath;
  }

  @Override
  public Map selectHurlSum(HurlProductCriteria hurlProductCriteria) throws ServiceException {
    try {
      return hurlProductDAO.selectHurlSum(hurlProductCriteria);
    } catch (SQLException e) {
      throw new ServiceException(e.getErrorCode(), e.getMessage());
    }
  }
}
