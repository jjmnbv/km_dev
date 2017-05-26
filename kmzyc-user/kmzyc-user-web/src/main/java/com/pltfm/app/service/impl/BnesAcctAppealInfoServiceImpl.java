package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.dao.BnesAcctAppealInfoDAO;
import com.pltfm.app.service.BnesAcctAppealInfoService;
import com.pltfm.app.vobject.BnesAcctAppealInfo;
import com.pltfm.app.vobject.BnesAcctAppealInfoQry;


@Component(value = "bnesAcctAppealInfoService")
public class BnesAcctAppealInfoServiceImpl implements BnesAcctAppealInfoService {
  @Resource(name = "bnesAcctAppealInfoDAO")
  private BnesAcctAppealInfoDAO bnesAcctAppealInfoDAO;

  // 添加申诉
  @Override
public void insert(BnesAcctAppealInfo record) throws SQLException {

    bnesAcctAppealInfoDAO.insert(record);

  }

  // 添加申诉
  @Override
public int insertAcctAppealInfo(BnesAcctAppealInfo record) throws SQLException {
    return bnesAcctAppealInfoDAO.insertAcctAppealInfo(record);
  }

  /**
   * 分页查询申诉信息
   * 
   * @param BnesAcctAppealInfoQry 申诉实体
   * @return
   * @throws Exception 异常
   */
  @Override
public Page queryForPage(BnesAcctAppealInfoQry qry, Page page) throws Exception {
    int totalNum = bnesAcctAppealInfoDAO.countByExample(qry);
    page.setRecordCount(totalNum);
    qry.setSkip(page.getStartIndex());
    qry.setMax(page.getStartIndex() + page.getPageSize());

    page.setDataList(bnesAcctAppealInfoDAO.selectByExample(qry));
    return page;
  }


  // 综合查询
  @Override
public List selectByExample(BnesAcctAppealInfoQry qry) throws SQLException {
    List list = bnesAcctAppealInfoDAO.selectByExample(qry);
    return list;
  }

  // 记录数
  @Override
public int countByExample(BnesAcctAppealInfoQry qry) throws SQLException {
    return bnesAcctAppealInfoDAO.countByExample(qry);
  }

  // 根据主键返回一个对象
  @Override
public BnesAcctAppealInfo selectByPrimaryKey(Integer accountAppealId) throws SQLException {
    return bnesAcctAppealInfoDAO.selectByPrimaryKey(accountAppealId);
  }

  public BnesAcctAppealInfoDAO getBnesAcctAppealInfoDAO() {
    return bnesAcctAppealInfoDAO;
  }

  public void setBnesAcctAppealInfoDAO(BnesAcctAppealInfoDAO bnesAcctAppealInfoDAO) {
    this.bnesAcctAppealInfoDAO = bnesAcctAppealInfoDAO;
  }
}
