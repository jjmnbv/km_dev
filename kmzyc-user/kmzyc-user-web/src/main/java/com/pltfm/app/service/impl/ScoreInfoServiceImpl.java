package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.dao.LoginInfoDAO;
import com.pltfm.app.dao.ScoreInfoDAO;
import com.pltfm.app.service.ScoreInfoService;
import com.pltfm.app.util.ListUtils;
import com.pltfm.app.vobject.LoginInfo;
import com.pltfm.app.vobject.ScoreInfo;
import com.pltfm.app.vobject.ScoreInfoExample;

/**
 * 积分明细业务逻辑实现类
 * 
 * @author zhl
 * @since 2013-07-24
 */
@Component(value = "scoreInfoService")
public class ScoreInfoServiceImpl implements ScoreInfoService {
  @Resource(name = "scoreInfoDAO")
  private ScoreInfoDAO scoreInfoDAO;
  @Resource(name = "loginInfoDAO")
  private LoginInfoDAO loginInfoDAO;

  public LoginInfoDAO getLoginInfoDAO() {
    return loginInfoDAO;
  }

  public void setLoginInfoDAO(LoginInfoDAO loginInfoDAO) {
    this.loginInfoDAO = loginInfoDAO;
  }

  /**
   * 添加积分明细信息
   */
  @Override
public void addScoreInfo(ScoreInfo scoreInfo) throws Exception {
    scoreInfoDAO.insert(scoreInfo);
  }

  /**
   * 通过主键删除积分明细信息
   */
  @Override
public Integer deleteByPrimaryKey(Integer infoId) throws Exception {
    return scoreInfoDAO.deleteByPrimaryKey(infoId);
  }

  /**
   * 多条删除积分明细信息
   */
  @Override
public Integer deleteScoreInfo(List<String> infoIds) throws Exception {
    Integer count = 0;
    if (ListUtils.isNotEmpty(infoIds)) {
      for (String infoId : infoIds) {
        count += scoreInfoDAO.deleteByPrimaryKey(Integer.valueOf(infoId));
      }
    }
    return count;
  }

  /**
   * 通过主键查询积分明细信息
   */
  @Override
public ScoreInfo queryByPrimaryKey(Integer infoId) throws Exception {
    return scoreInfoDAO.selectByPrimaryKey(infoId);
  }

  /**
   * 分页查询积分明细
   */
  public Page queryForPageList(ScoreInfo scoreInfo, Page page) throws Exception {
    // 查询收货地址总数并设置总数
    int totalNum = scoreInfoDAO.countByExample(scoreInfo);
    page.setRecordCount(totalNum);
    // 设置查询开始结束索引
    scoreInfo.setStartIndex(page.getStartIndex());
    scoreInfo.setEndIndex(page.getStartIndex() + page.getPageSize());
    page.setDataList(scoreInfoDAO.queryForPage(scoreInfo));
    return page;
  }

  /**
   * 多条件查询积分明细
   */
  @Override
public List queryScoreInfo(ScoreInfoExample example) throws Exception {
    return scoreInfoDAO.selectByExample(example);
  }

  /**
   * 通过主键修改积分明细
   */
  @Override
public Integer updateScoreInfo(ScoreInfo scoreInfo) throws Exception {
    return scoreInfoDAO.updateByPrimaryKey(scoreInfo);
  }

  /**
   * 跟据登录ID取得登录名
   * 
   * @param n_LoginId 信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public LoginInfo getLoginName(Integer n_LoginId) throws SQLException {
    return loginInfoDAO.selectByPrimaryKey(n_LoginId);
  }

  public ScoreInfoDAO getScoreInfoDAO() {
    return scoreInfoDAO;
  }

  public void setScoreInfoDAO(ScoreInfoDAO scoreInfoDAO) {
    this.scoreInfoDAO = scoreInfoDAO;
  }

}
