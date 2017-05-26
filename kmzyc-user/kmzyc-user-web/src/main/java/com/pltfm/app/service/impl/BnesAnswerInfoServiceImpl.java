package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.dao.BnesAnswerInfoDAO;
import com.pltfm.app.service.BnesAnswerInfoService;
import com.pltfm.app.service.SafeQuestionService;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.vobject.BnesAnswerInfo;
import com.pltfm.app.vobject.BnesAnswerInfoExample;
import com.pltfm.app.vobject.SafeQuestion;

/**
 * 安全问题答案信息业务逻辑类
 * 
 * @author cjm
 * @since 2013-8-6
 */
@Component(value = "bnesAnswerInfoService")
public class BnesAnswerInfoServiceImpl implements BnesAnswerInfoService {

  /**
   * 安全问题答案信息DAO接口
   */
  @Resource(name = "bnesAnswerInfoDAO")
  private BnesAnswerInfoDAO bnesAnswerInfoDAO;
  /**
   * 安全问题业务逻辑接口
   */
  @Resource(name = "safeQuestionService")
  private SafeQuestionService safeQuestionService;

  /**
   * 添加安全问题答案信息
   * 
   * @param record 安全问题答案信息实体
   * @return 返回值
   * @throws Exception 异常
   */
  @Override
  public Integer addBnesAnswerInfo(BnesAnswerInfo bnesAnswerInfo) throws Exception {
    bnesAnswerInfo.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
    return bnesAnswerInfoDAO.insert(bnesAnswerInfo);
  }

  /**
   * 根据vo条件查询分类信息page
   * 
   * @param pageParam 分页实体
   * @param vo 账户信息实体
   * @return 返回值
   * @throws Exception
   */
  @Override
  public Page searchPageByVo(Page pageParam, BnesAnswerInfo vo) throws Exception {
    if (pageParam == null) {
      pageParam = new Page();
    }
    if (vo == null) {
      vo = new BnesAnswerInfo();
    }
    // 获取客户积分规则总数
    int totalNum = bnesAnswerInfoDAO.selectCountByVo(vo);
    pageParam.setRecordCount(totalNum);
    // 设置查询开始结束索引
    vo.setSkip(pageParam.getStartIndex());
    vo.setMax(pageParam.getStartIndex() + pageParam.getPageSize());

    pageParam.setDataList(bnesAnswerInfoDAO.selectPageByVo(vo));
    return pageParam;
  }

  /**
   * 修改账户信息
   * 
   * @param record 账户信息实体
   * @return 返回值
   * @throws Exception 异常
   */
  @Override
  public Integer updateBnesAnswerInfo(BnesAnswerInfo bnesAnswerInfo) throws Exception {
    return bnesAnswerInfoDAO.updateByPrimaryKeySelective(bnesAnswerInfo);
  }

  /**
   * 查询全部安全问题
   * 
   * @return
   * @throws Exception
   */
  @Override
  public List<SafeQuestion> qeuryBySafeQuestionAll() throws Exception {
    return safeQuestionService.selectByAll();
  }

  /**
   * 根据安全问题ID查询安全问题答案
   * 
   * @return
   * @throws Exception
   */
  @Override
  public List<BnesAnswerInfo> qeueryBySafeQuestionId(Integer safeQuestionId) throws Exception {
    List<BnesAnswerInfo> bnesAnswerInfoList = null;
    BnesAnswerInfoExample example = new BnesAnswerInfoExample();
    example.createCriteria().andSafeQuestionIdEqualTo(safeQuestionId);
    bnesAnswerInfoList = bnesAnswerInfoDAO.selectByExample(example);
    return bnesAnswerInfoList;
  }

  /**
   * 根据安全问题id账户查id询单些问题是否已存在
   * 
   * @param nCommercialTenantId 安全问题id
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public List<BnesAnswerInfo> selectByPrimaryKey(Integer safeQuestionId, Integer accountId)
      throws Exception {
    BnesAnswerInfo bnesAnswerInfo = new BnesAnswerInfo();
    bnesAnswerInfo.setAccountId(accountId);
    bnesAnswerInfo.setSafeQuestionId(safeQuestionId);
    return bnesAnswerInfoDAO.selectByPrimaryKey(bnesAnswerInfo);
  }

  public BnesAnswerInfoDAO getBnesAnswerInfoDAO() {
    return bnesAnswerInfoDAO;
  }

  public void setBnesAnswerInfoDAO(BnesAnswerInfoDAO bnesAnswerInfoDAO) {
    this.bnesAnswerInfoDAO = bnesAnswerInfoDAO;
  }

  public SafeQuestionService getSafeQuestionService() {
    return safeQuestionService;
  }

  public void setSafeQuestionService(SafeQuestionService safeQuestionService) {
    this.safeQuestionService = safeQuestionService;
  }


}
