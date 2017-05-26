package com.kmzyc.b2b.service.member;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.km.framework.page.Pagination;
import com.kmzyc.b2b.model.AppraiseReply;
import com.kmzyc.b2b.model.ProdAppraise;
import com.kmzyc.b2b.model.User;
import com.pltfm.app.vobject.AppraiseAddtoContent;

/**
 * Description:商品评价信息服务接口 User: lishiming Date: 13-10-17 下午3:45 Since: 1.0
 */
public interface ProdAppraiseService {

  /**
   * 计算用户某审核状态下的评价总数
   * 
   * @param userId
   * @param checkStatusList
   * @return
   * @throws java.sql.SQLException
   */
  public AppraiseReply countProdAppraiseByUserId(Long userId, List<Integer> checkStatusList)
      throws SQLException;

  /**
   * 计算用户已被回复的评价总数
   * 
   * @param userId
   * @return
   * @throws SQLException
   */
  public Long countRepliedProdAppraiseByUserId(Long userId) throws SQLException;

  /**
   * 
   * @param replyManId 回复人ID
   * @param replyContent 回复内容
   * @param appraiseId 被回复的ID
   * @param appraiseManId 被回复人ID
   * @param appraiseManNickName 被回复人的昵称
   * @throws SQLException
   */
  public void saveAppraiseReply(User replyUser, String replyContent, String appraiseId,
      String appraiseManId, String appraiseManNickName) throws Exception;

  /**
   * 根据条件查询产品的咨询
   * 
   * @param prodAppraise
   * @return
   * @throws Exception
   */
  public List<ProdAppraise> findProdAppraiseByCondition(ProdAppraise prodAppraise) throws Exception;

  /**
   * 对产品进行追评价
   */
  public void appendProdAppraiseByAppriaseId(AppraiseAddtoContent appraiseAdd) throws Exception;

  /**
   * 查找评价订单
   * 
   * @return
   * @throws Exception
   */
  public Pagination findAppraiseOrder(Pagination page) throws Exception;

  public com.kmzyc.b2b.model.AppraiseAddtoContent
    findAppendByOrderAndSku(com.kmzyc.b2b.model.AppraiseAddtoContent appraiseAddToCondition)
      throws SQLException;
  
  public int findProdAppraiseByOrderDetailId(Map map) throws SQLException;
}
