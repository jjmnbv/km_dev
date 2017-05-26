package com.pltfm.app.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.dao.ReserverInfoDAO;
import com.pltfm.app.service.ReserverInfoService;
import com.pltfm.app.vobject.ReserverInfo;

@Service("reserverInfoService")
public class ReserverInfoServiceImpl implements ReserverInfoService {
  // 预备金申请记录dao
  @Resource(name = "reserverInfoDAO")
  private ReserverInfoDAO reserverInfoDAO;


  // 获取所有预备金账户
  @Override
public List<ReserverInfo> selectAllReserverInfo() throws Exception {
    return reserverInfoDAO.selectAllReserverInfo();
  }

  // 分页查询开通了预备金列表
  public Page selectReserverInfoAll(Page page, ReserverInfo vo) throws SQLException {
    // 获取条件下的总条数
    int countInfo = reserverInfoDAO.selectCountByInfoAll(vo);
    page.setRecordCount(countInfo);
    // 设置查询开始结束索引
    int max = page.getStartIndex() + page.getPageSize();
    vo.setMinNum(page.getStartIndex());
    vo.setMaxNum(max);
    page.setDataList(reserverInfoDAO.selectReserverInfoAll(vo));
    return page;
  }


  // 分页查询预备金列表
  public Page selectReserverInfo(Page page, ReserverInfo vo) throws SQLException {
    // 获取条件下的总条数
    int countInfo = reserverInfoDAO.selectCountByInfo(vo);
    page.setRecordCount(countInfo);
    // 设置查询开始结束索引
    int max = page.getStartIndex() + page.getPageSize();
    vo.setMinNum(page.getStartIndex());
    vo.setMaxNum(max);
    page.setDataList(reserverInfoDAO.selectReserverInfo(vo));
    return page;
  }

  // 根据预备金账户id删除
  @Override
public int deleteByPrimaryKey(BigDecimal reserveId) throws SQLException {
    // TODO Auto-generated method stub
    return 0;
  }

  // 添加预备金账户
  @Override
public void insertSelective(ReserverInfo record) throws SQLException {
    reserverInfoDAO.insertSelective(record);
  }

  // 根据预备金账户id查询
  @Override
public ReserverInfo selectByPrimaryKey(ReserverInfo record) throws SQLException {
    return reserverInfoDAO.selectByPrimaryKey(record);
  }

  // 修改预备金账户信息
  @Override
public int updateByPrimaryKey(ReserverInfo record) throws SQLException {
    return reserverInfoDAO.updateByPrimaryKey(record);
  }

}
