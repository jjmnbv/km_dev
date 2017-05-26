package com.pltfm.app.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.dao.AccountInfoDAO;
import com.pltfm.app.dao.LoginInfoDAO;
import com.pltfm.app.dao.ReserverApplyInfoDAO;
import com.pltfm.app.service.ReserverApplyInfoServce;
import com.pltfm.app.vobject.ReserverApplyInfo;

@Component(value = "reserverApplyInfoServce")
public class ReserverApplyInfoServceImpl implements ReserverApplyInfoServce {
  // 预备金申请记录dao
  @Resource(name = "reserverApplyInfoDAO")
  private ReserverApplyInfoDAO reserverApplyInfoDAO;
  // 客户信息dao
  @Resource(name = "accountInfoDAO")
  private AccountInfoDAO accountInfoDAO;
  // 登陆信息dao
  @Resource(name = "loginInfoDAO")
  private LoginInfoDAO loginInfoDAO;

  // 分页条件查询
  public Page queryReserverApplyInfoList(Page page, ReserverApplyInfo record) throws Exception {
    // 获取条件查询下的总条数
    Integer totalCount = reserverApplyInfoDAO.pageQueryApplyInfoCount(record);
    // 设置总共多少条数据
    page.setRecordCount(totalCount);
    // 设置查询开始结束索引
    int max = page.getStartIndex() + page.getPageSize();
    record.setMinNum(page.getStartIndex());
    record.setMaxNum(max);
    // 设置查询条件下预备金列表
    page.setDataList(reserverApplyInfoDAO.pageQueryApplyInfo(record));
    return page;
  }

  // 根据申请记录id删除
  @Override
public int deleteByPrimaryKey(BigDecimal applyNotesId) throws SQLException {
    // TODO Auto-generated method stub
    return 0;
  }


  // 根据申请记录id查询申请记录
  @Override
public List<ReserverApplyInfo> selectByPrimaryKey(ReserverApplyInfo record) throws SQLException {
    return reserverApplyInfoDAO.selectByPrimaryKey(record);
  }

  // 根据申请记录id修改申请记录
  @Override
public int updateByPrimaryKey(ReserverApplyInfo record) throws SQLException {
    return reserverApplyInfoDAO.updateByPrimaryKey(record);
  }

}
