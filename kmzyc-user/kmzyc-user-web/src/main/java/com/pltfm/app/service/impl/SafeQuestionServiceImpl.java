package com.pltfm.app.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.dao.SafeQuestionDAO;
import com.pltfm.app.service.BnesAnswerInfoService;
import com.pltfm.app.service.SafeQuestionService;
import com.pltfm.app.util.ListUtils;
import com.pltfm.app.vobject.BnesAnswerInfo;
import com.pltfm.app.vobject.SafeQuestion;

/**
 * 安全问题业务处理类
 * 
 * @author ljh
 * @since 2013-7-12
 */
@Service(value = "safeQuestionService")
public class SafeQuestionServiceImpl implements SafeQuestionService {

  @Resource(name = "safeQuestionDao")
  private SafeQuestionDAO safeQuestionDao;

  @Resource(name = "bnesAnswerInfoService")
  private BnesAnswerInfoService bnesAnswerInfoService;

  public SafeQuestionDAO getSafeQuestionDao() {
    return safeQuestionDao;
  }

  public void setSafeQuestionDao(SafeQuestionDAO safeQuestionDao) {
    this.safeQuestionDao = safeQuestionDao;
  }

  /**
   * 添加一条安全问题记录
   * 
   * @param safeQuestion 安全问题实体
   * @return 安全问题主键
   * @throws Exception 异常
   */

  @Override
  @Transactional(rollbackFor = Exception.class)
  public Integer addSafeQuestion(SafeQuestion safeQuestion) throws Exception {

    return safeQuestionDao.insertSafeQuestion(safeQuestion);
  }

  /**
   * 根据安全问题主键查询出安全问题记录
   * 
   * @param id 安全问题主键
   * @return 安全问题记录
   * @throws Exception 异常
   */

  @Override
  public SafeQuestion queryOneQuestion(int id) throws Exception {

    return safeQuestionDao.queryOneQuestion(id);

  }

  /**
   * 更新一条安全问题记录
   * 
   * @param safeQuestion 安全问题实体
   * @return 安全问题主键
   * @throws Exception 异常
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Integer updateOneQuestion(SafeQuestion safeQuestion) throws Exception {
    return safeQuestionDao.updateOneQuestion(safeQuestion);
  }


  /**
   * 根据安全问题名称查询安全问题集合
   * 
   * @param safeQuestion 安全问题实体
   * @param page 分页实体
   * @return 安全问题实体
   * @throws Exception 异常
   */
  @Override
  public List<SafeQuestion> queryQuestionByName(SafeQuestion safeQuestion, Page page)
      throws Exception {
    return safeQuestionDao.queryQuestionByName(safeQuestion, page);
  }

  /**
   * 计算总的条数
   * 
   * @param safeQuestion 安全问题实体
   * @return 总条数
   * @throws Exception 异常
   */

  @Override
  public int countItem(SafeQuestion safeQuestion) throws Exception {

    return safeQuestionDao.countItem(safeQuestion);

  }

  /**
   * 删除所选的安全问题集合
   * 
   * @param questionId 安全问题主键集合
   * @throws Exception 异常
   */
  @Override
  public Integer delBySelected(List<Integer> questionId) throws Exception {
    Integer rows = 0;
    List<BnesAnswerInfo> bnesAnswerInfoList = null;
    if (ListUtils.isNotEmpty(questionId)) {
      for (Integer id : questionId) {
        // 根据安全问题ID查询安全问题答案是否有记录，有则不是能删除。
        bnesAnswerInfoList = bnesAnswerInfoService.qeueryBySafeQuestionId(id);
        if (bnesAnswerInfoList != null && bnesAnswerInfoList.size() > 0) {
          return rows;
        }
        rows += safeQuestionDao.delSafeQuestion(id);
      }
    }
    return rows;
  }

  /**
   * 查询全部安全问题
   */
  @Override
  public List selectByAll() throws Exception {
    return safeQuestionDao.selectByAll();
  }

  public BnesAnswerInfoService getBnesAnswerInfoService() {
    return bnesAnswerInfoService;
  }

  public void setBnesAnswerInfoService(BnesAnswerInfoService bnesAnswerInfoService) {
    this.bnesAnswerInfoService = bnesAnswerInfoService;
  }

}
