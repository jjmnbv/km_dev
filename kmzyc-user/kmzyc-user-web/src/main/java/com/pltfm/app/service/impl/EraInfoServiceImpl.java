package com.pltfm.app.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kmzyc.b2b.vo.EraInfo;
import com.kmzyc.commons.page.Page;
import com.pltfm.app.dao.AccountInfoDAO;
import com.pltfm.app.dao.EraInfoDAO;
import com.pltfm.app.dao.HealthYgenericInfoDAO;
import com.pltfm.app.dao.LoginInfoDAO;
import com.pltfm.app.dao.PersonalBasicInfoDAO;
import com.pltfm.app.dao.PersonalityInfoDAO;
import com.pltfm.app.service.EraInfoService;
import com.pltfm.app.service.SaltInfoService;
import com.pltfm.app.service.UserLevelService;


@Service
public class EraInfoServiceImpl implements EraInfoService {
  Logger logger  = LoggerFactory.getLogger(EraInfoServiceImpl.class);

  @Resource(name = "eraInfoDAOImpl")
  private EraInfoDAO eraInfoDAO;
  
  /**
   * 个人信息DAO接口
   */
  @Resource(name = "personalBasicInfoDAO")
  private PersonalBasicInfoDAO personalBasicInfoDAO;
  /**
   * 客户等级业务逻辑接口
   */
  @Resource(name = "userLevelService")
  private UserLevelService userLevelService;

  /**
   * 账户信息DAO接口
   */
  @Resource(name = "accountInfoDAO")
  private AccountInfoDAO accountInfoDAO;
  /**
   * 健康信息DAO接口
   */
  @Resource(name = "healthYgenericInfoDAO")
  private HealthYgenericInfoDAO healthYgenericInfoDAO;

  /**
   * 登录信息DAO接口
   */
  @Resource(name = "loginInfoDAO")
  private LoginInfoDAO loginInfoDAO;
  
  @Resource
  private SaltInfoService saltInfoService;
  
  /**
   * 个性信息DAO接口
   */
  @Resource(name = "personalityInfoDAO")
  private PersonalityInfoDAO personalityInfoDAO;

  // 分页条件查询时代会员列表
  public Page selectEraInfoList(Page page, EraInfo eraInfo) throws Exception {
    // 获取时代会员总数
    int count = eraInfoDAO.selectEraInfoCount(eraInfo);
    page.setRecordCount(count);
    // 设置查询开始结束索引
    int max = page.getStartIndex() + page.getPageSize();
    eraInfo.setStartIndex(page.getStartIndex());
    eraInfo.setEndIndex(max);
    page.setDataList(eraInfoDAO.selectEraInfoList(eraInfo));
    return page;
  }

  // 查询时代会员信息
  @Override
public EraInfo selectEraInfoDetail(Long eraInfoId) throws Exception {
    return eraInfoDAO.selectByPrimaryKey(eraInfoId);
  }
  
  //查询时代会员信息
 @Override
public EraInfo selectEraInfoDetail(String eraNo) throws Exception {
   return eraInfoDAO.selectByEraNo(eraNo);
 }
  
  

  // 更新时代会员信息
  public int updateEraInfo(EraInfo eraInfo) throws Exception {
    return eraInfoDAO.updateByPrimaryKeySelective(eraInfo);
  }
}
