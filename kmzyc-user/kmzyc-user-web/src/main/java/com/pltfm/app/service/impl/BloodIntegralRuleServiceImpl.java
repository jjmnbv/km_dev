package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.dao.BloodIntegralRuleDAO;
import com.pltfm.app.dao.BnesCustomerTypeDAO;
import com.pltfm.app.service.BloodIntegralRuleService;
import com.pltfm.app.util.ListUtils;
import com.pltfm.app.vobject.BloodIntegralRule;
import com.pltfm.app.vobject.BnesCustomerTypeQuery;

/**
 * 经验规则信息处理
 * 
 * @author gwl
 * @since 2013-07-08
 */
@Component(value = "bloodIntegralRuleService")
public class BloodIntegralRuleServiceImpl implements BloodIntegralRuleService {
  @Resource(name = "bloodIntegralRuleDAO")
  private BloodIntegralRuleDAO bloodIntegralRuleDAO;
  /**
   * 专家下的子级类别
   */
  @Resource(name = "bnesCustomerTypeDAO")
  private BnesCustomerTypeDAO bnesCustomerTypeDAO;

  /***
   * 
   * id删除经验规则
   */
  @Override
public int delete(List<Integer> integralRuleIds) throws SQLException {
    Integer count = 0;
    if (ListUtils.isNotEmpty(integralRuleIds)) {
      for (Integer id : integralRuleIds) {
        // BloodIntegralRule bloodIntegralRule=new BloodIntegralRule();
        // bloodIntegralRule.setIntegralRuleId(Integer.valueOf(id));
        count += bloodIntegralRuleDAO.delete(id);
      }
    }
    return count;
  }

  /***
   * 
   * 添加经验规则
   */
  @Override
public int insert(BloodIntegralRule bloodIntegralRule) throws SQLException {
    bloodIntegralRule.setClientType(2);
    return bloodIntegralRuleDAO.insert(bloodIntegralRule);
  }

  /**
   * 查询经验规则总数量
   * 
   * @param vo
   * @return 返回值
   */
  @Override
public Integer selectCountByVo() throws SQLException {
    BloodIntegralRule vo = new BloodIntegralRule();
    return bloodIntegralRuleDAO.selectCountByVo(vo);
  }

  /***
   * 
   * ID查询经验规则
   */
  @Override
public BloodIntegralRule selectByPrimaryKey(Integer integralRuleId) throws SQLException {
    return bloodIntegralRuleDAO.selectByPrimaryKey(integralRuleId);
  }

  /**
   * 查询最新记录ID
   * 
   * @param vo
   * @return 返回值
   */
  @Override
public BloodIntegralRule maxIntegralRuleId() throws SQLException {
    return bloodIntegralRuleDAO.maxIntegralRuleId();
  }

  /**
   * 多条件查询经验规则信息
   */
  @Override
public List queryIntegralRule(BloodIntegralRule bloodIntegralRule) throws Exception {
    return bloodIntegralRuleDAO.selectByExample(bloodIntegralRule);
  }

  /**
   * 查询专家下的子级类别集合
   */
  @Override
public List<BnesCustomerTypeQuery> queryByComm() {
    return bnesCustomerTypeDAO.findParentList(2);
  }

  /***
   * 
   * 修改经验规则
   */
  @Override
public int update(BloodIntegralRule bloodIntegralRule) throws SQLException {
    return bloodIntegralRuleDAO.update(bloodIntegralRule);
  }

  /**
   * 分页查询经验规则
   * 
   * @param 经验规则实体
   * @return
   * @throws Exception 异常
   */
  @Override
public Page searchPageByVo(Page pageParam, BloodIntegralRule vo) throws Exception {
    if (pageParam == null) {
      pageParam = new Page();
    }
    if (vo == null) {
      vo = new BloodIntegralRule();
    }
    int totalNum = bloodIntegralRuleDAO.selectCountByVo(vo);
    pageParam.setRecordCount(totalNum);
    vo.setSkip(pageParam.getStartIndex());
    vo.setMax(pageParam.getStartIndex() + pageParam.getPageSize());
    pageParam.setDataList(bloodIntegralRuleDAO.selectPageByVo(vo));
    return pageParam;
  }



  public BloodIntegralRuleDAO getBloodIntegralRuleDAO() {
    return bloodIntegralRuleDAO;
  }

  public void setBloodIntegralRuleDAO(BloodIntegralRuleDAO bloodIntegralRuleDAO) {
    this.bloodIntegralRuleDAO = bloodIntegralRuleDAO;
  }

  public BnesCustomerTypeDAO getBnesCustomerTypeDAO() {
    return bnesCustomerTypeDAO;
  }

  public void setBnesCustomerTypeDAO(BnesCustomerTypeDAO bnesCustomerTypeDAO) {
    this.bnesCustomerTypeDAO = bnesCustomerTypeDAO;
  }
}
