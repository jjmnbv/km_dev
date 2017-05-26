package com.pltfm.app.service;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.vobject.LoginInfo;
import com.pltfm.app.vobject.ScoreInfo;
import com.pltfm.app.vobject.ScoreInfoExample;

/**
 * 积分明细业务逻辑实现接口
 * 
 * @author zhl
 * @since 2013-07-24
 */
public interface ScoreInfoService {
  /**
   * 添加积分明细信息
   * 
   * @param scoreInfo 积分明细实体
   * @return
   * @throws Exception 异常信息
   */
  public void addScoreInfo(ScoreInfo scoreInfo) throws Exception;

  /**
   * 多条删除积分明细信息
   * 
   * @param infoIds 积分明细主键集合
   * @return
   * @throws Exception 异常
   */
  public Integer deleteScoreInfo(List<String> infoIds) throws Exception;

  /**
   * 删除单条积分明细信息
   * 
   * @param infoId 积分明细主键
   * @return
   * @throws Exception
   */
  public Integer deleteByPrimaryKey(Integer infoId) throws Exception;

  /**
   * 通过主键修改积分明细
   * 
   * @param scoreInfo 积分明细实体
   * @return
   * @throws Exception
   */
  public Integer updateScoreInfo(ScoreInfo scoreInfo) throws Exception;

  /**
   * 多条件查询积分明细
   * 
   * @param example 查询实体
   * @return
   * @throws Excetpion 异常
   */
  public List queryScoreInfo(ScoreInfoExample example) throws Exception;

  /**
   * 通过积分明细规则主键查询积分明细信息
   * 
   * @param infoId 积分明细主键
   * @return
   * @throws Exception
   */
  public ScoreInfo queryByPrimaryKey(Integer infoId) throws Exception;

  /***
   * 分页查询
   * 
   * @param scoreInfo 客户积分规则实体
   * @return
   * @throws Exception 异常
   */
  public Page queryForPageList(ScoreInfo scoreInfo, Page page) throws Exception;

  /**
   * 跟据登录ID取得登录名
   * 
   * @param vo 信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public LoginInfo getLoginName(Integer n_LoginId) throws SQLException;
}
