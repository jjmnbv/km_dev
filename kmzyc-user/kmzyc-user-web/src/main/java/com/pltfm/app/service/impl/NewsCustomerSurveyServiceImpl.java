package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.dao.NewsCustomerSurveyDAO;
import com.pltfm.app.service.NewsCustomerSurveyService;
import com.pltfm.app.util.ListUtils;
import com.pltfm.app.vobject.NewsCustomerSurvey;

/**
 * 调查记录信息处理
 * 
 * @author gwl
 * @since 2013-07-08
 */
@Component(value = "newsCustomerSurveyService")
public class NewsCustomerSurveyServiceImpl implements NewsCustomerSurveyService {
  @Resource(name = "newsCustomerSurveyDAO")
  private NewsCustomerSurveyDAO newsCustomerSurveyDAO;

  /***
   * 
   * 删除调查记录
   */
  @Override
public int delete(List<Integer> customerSurveyIds) throws SQLException {
    Integer count = 0;
    if (ListUtils.isNotEmpty(customerSurveyIds)) {
      for (Integer id : customerSurveyIds) {
        NewsCustomerSurvey newsCustomerSurvey = new NewsCustomerSurvey();
        newsCustomerSurvey.setCustomerSurveyId(id);
        count += newsCustomerSurveyDAO.deleteByExample(newsCustomerSurvey);
      }
    }
    return count;
  }


  /***
   * 
   * 添加调查记录
   */
  @Override
public Integer insert(NewsCustomerSurvey record) throws SQLException {
    return newsCustomerSurveyDAO.insert(record);
  }

  /***
   * 
   * 跟据id查询调查记录
   */
  @Override
public NewsCustomerSurvey selectByPrimaryKey(Integer customerSurveyId) throws SQLException {
    return newsCustomerSurveyDAO.selectByPrimaryKey(customerSurveyId);
  }

  /***
   * 
   * 修改调查记录
   */
  @Override
public Integer updateByPrimaryKeySelective(NewsCustomerSurvey record) throws SQLException {
    return newsCustomerSurveyDAO.updateByPrimaryKeySelective(record);
  }

  /**
   * 分页查询调查记录信息
   * 
   * @param 调查记录信息实体
   * @return
   * @throws Exception 异常
   */
  @Override
public Page searchPageByVo(Page pageParam, NewsCustomerSurvey vo) throws Exception {
    if (pageParam == null) {
      pageParam = new Page();
    }
    if (vo == null) {
      vo = new NewsCustomerSurvey();
    }
    int totalNum = newsCustomerSurveyDAO.selectCountByVo(vo);
    pageParam.setRecordCount(totalNum);
    vo.setSkip(pageParam.getStartIndex());
    vo.setMax(pageParam.getStartIndex() + pageParam.getPageSize());
    pageParam.setDataList(newsCustomerSurveyDAO.selectPageByVo(vo));
    return pageParam;
  }


  public NewsCustomerSurveyDAO getNewsCustomerSurveyDAO() {
    return newsCustomerSurveyDAO;
  }


  public void setNewsCustomerSurveyDAO(NewsCustomerSurveyDAO newsCustomerSurveyDAO) {
    this.newsCustomerSurveyDAO = newsCustomerSurveyDAO;
  }



}
