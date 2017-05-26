package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.dao.BloodIntegralInfoDAO;
import com.pltfm.app.dao.LoginInfoDAO;
import com.pltfm.app.service.BloodIntegralInfoService;
import com.pltfm.app.util.ListUtils;
import com.pltfm.app.vobject.BloodIntegralInfo;
import com.pltfm.app.vobject.LoginInfo;

/***
 * 经验明细Service实现
 */
@Component(value = "bloodIntegralInfoService")
public class BloodIntegralInfoServiceImpl implements BloodIntegralInfoService {
  @Resource(name = "bloodIntegralInfoDAO")
  private BloodIntegralInfoDAO bloodIntegralInfoDAO;
  @Resource(name = "loginInfoDAO")
  private LoginInfoDAO loginInfoDAO;

  public LoginInfoDAO getLoginInfoDAO() {
    return loginInfoDAO;
  }

  public void setLoginInfoDAO(LoginInfoDAO loginInfoDAO) {
    this.loginInfoDAO = loginInfoDAO;
  }

  /**
   * 
   * 经验明细
   */
  @Override
public int delete(List<Integer> vistingIds) throws SQLException {
    Integer count = 0;
    if (ListUtils.isNotEmpty(vistingIds)) {
      for (Integer id : vistingIds) {
        BloodIntegralInfo bloodIntegralInfo = new BloodIntegralInfo();
        bloodIntegralInfo.setIntegralInfoId(id);
        count += bloodIntegralInfoDAO.delete(bloodIntegralInfo);
      }
    }
    return count;
  }

  /***
   * 
   * 添加经验明细
   */
  @Override
public Integer insert(BloodIntegralInfo bloodIntegralInfo) throws SQLException {
    Object object = bloodIntegralInfoDAO.insert(bloodIntegralInfo);
    return (Integer) object;
  }

  /**
   * 分页查询经验明细
   * 
   * @param vo
   * @return
   * @throws Exception 异常
   */
  @Override
public Page searchPageByVo(Page pageParam, BloodIntegralInfo vo) throws Exception {
    if (pageParam == null) {
      pageParam = new Page();
    }
    if (vo == null) {
      vo = new BloodIntegralInfo();
    }
    int totalNum = bloodIntegralInfoDAO.selectCountByVo(vo);
    pageParam.setRecordCount(totalNum);
    vo.setSkip(pageParam.getStartIndex());
    vo.setMax(pageParam.getStartIndex() + pageParam.getPageSize());
    pageParam.setDataList(bloodIntegralInfoDAO.selectPageByVo(vo));
    return pageParam;
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

  public BloodIntegralInfoDAO getBloodIntegralInfoDAO() {
    return bloodIntegralInfoDAO;
  }

  public void setBloodIntegralInfoDAO(BloodIntegralInfoDAO bloodIntegralInfoDAO) {
    this.bloodIntegralInfoDAO = bloodIntegralInfoDAO;
  }
}
