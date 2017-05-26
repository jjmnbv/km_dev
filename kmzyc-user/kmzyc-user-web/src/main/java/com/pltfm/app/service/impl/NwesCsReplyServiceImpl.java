package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.pltfm.app.dao.NwesCsReplyDAO;
import com.pltfm.app.service.NwesCsReplyService;
import com.pltfm.app.util.ListUtils;
import com.pltfm.app.vobject.NwesCsReply;

/**
 * 服务回复信息处理
 * 
 * @author gwl
 * @since 2013-07-08
 */
@Component(value = "nwesCsReplyService")
public class NwesCsReplyServiceImpl implements NwesCsReplyService {
  @Resource(name = "nwesCsReplyDAO")
  private NwesCsReplyDAO nwesCsReplyDAO;

  /***
   * 
   * 删除回复信息
   */
  public int delete(List<Integer> replyIds) throws SQLException {
    Integer count = 0;
    if (ListUtils.isNotEmpty(replyIds)) {
      for (Integer id : replyIds) {
        NwesCsReply nwesCsReply = new NwesCsReply();
        nwesCsReply.setReplyId(id);
        count += nwesCsReplyDAO.delete(nwesCsReply);
      }
    }
    return count;
  }

  /***
   * 
   * 添加回复信息
   */
  public Integer insert(NwesCsReply nwesCsReply) throws SQLException {
    return nwesCsReplyDAO.insert(nwesCsReply);
  }

  /***
   * 
   * 跟据服务外键查询回复信息
   */
  public NwesCsReply getCustomerSurveyId(Integer customerSurveyId) throws SQLException {
    return nwesCsReplyDAO.getCustomerSurveyId(customerSurveyId);
  }

  /***
   * 
   * 跟据id查询回复信息
   */
  public NwesCsReply selectByPrimaryKey(Integer replyId) throws SQLException {
    return nwesCsReplyDAO.selectByPrimaryKey(replyId);
  }

  /***
   * 
   * 跟据服务外键查询回复信息总条数
   */
  public Integer getReplyCounts(Integer customerSurveyId) throws SQLException {
    return nwesCsReplyDAO.getReplyCounts(customerSurveyId);
  }
}
