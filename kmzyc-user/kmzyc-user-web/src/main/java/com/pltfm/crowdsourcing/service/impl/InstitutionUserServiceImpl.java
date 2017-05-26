package com.pltfm.crowdsourcing.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.km.commons.general.model.Page;
import com.km.crowdsourcing.model.ClearingUser;
import com.km.crowdsourcing.model.InstitutionUser;
import com.km.crowdsourcing.service.InstitutionUserService;
import com.pltfm.app.dao.LoginInfoDAO;
import com.pltfm.crowdsourcing.dao.InstitutionUserDao;

/**
 * 
 * @ClassName: InstitutionInfoServiceImpl
 * @Description: 机构信息接口实现类
 * @author xys
 * @date 2016年3月17日
 * @version 1.0
 */
@Service(value = "institutionUserService")
public class InstitutionUserServiceImpl implements InstitutionUserService {
  private Logger logger = LoggerFactory.getLogger(InstitutionUserServiceImpl.class);
  @Resource
  InstitutionUserDao institutionUserDao;
  @Resource
  LoginInfoDAO loginInfoDao;

  @Override
  public Page searchPageByVo(InstitutionUser institutionUser, Page nPage) throws Exception {
    if (nPage == null) nPage = new Page();

    if (institutionUser == null) {
      institutionUser = new InstitutionUser();
    }
    nPage.setQueryCondition(institutionUser);
    int totalNum = institutionUserDao.selectCountByVo(nPage);
    if (totalNum < nPage.getPageSize()) {
      nPage.setPageIndex(1);
    }
    nPage.setRecordList((List) institutionUserDao.selectPageByVo(nPage));
    nPage.setTotalRecords(totalNum);
    return nPage;
  }



  @Override
  public int searchCountByVo(InstitutionUser institutionUser) throws SQLException, Exception {
    return 0;
  }



  @Override
  public String getinstitutionUserIds(InstitutionUser institutionUser)
      throws SQLException, Exception {
    Page nPage = new Page();
    nPage.setQueryCondition(institutionUser);
    return institutionUserDao.getinstitutionUserIds(nPage);

  }



  @Override
  public void updateById(InstitutionUser institutionUser) throws SQLException, Exception {
    institutionUserDao.updateByPrimaryKey(institutionUser);

  }



  @Override
  public void unClearByIds(String ids) throws SQLException, Exception {
    institutionUserDao.unClearByIds(ids);

  }

  @Override
  public List<ClearingUser> selectByClearingUser(List list) throws SQLException, Exception {
    return institutionUserDao.selectByClearingUserIds(list);
  }
}
