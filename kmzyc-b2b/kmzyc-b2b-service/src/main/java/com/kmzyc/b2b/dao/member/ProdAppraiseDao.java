package com.kmzyc.b2b.dao.member;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.km.framework.persistence.Dao;
import com.kmzyc.b2b.model.AppraiseAddtoContent;
import com.kmzyc.b2b.model.ProdAppraise;

/**
 * Description:商品评价信息数据访问接口 User: lishiming Date: 13-10-17 下午3:45 Since: 1.0
 */
public interface ProdAppraiseDao extends Dao {

  /**
	 * 
	 */
  public int selectCountPersonBySkuId(ProdAppraise prodAppraise) throws SQLException;

  public int selectPointBySkuId(ProdAppraise prodAppraise) throws SQLException;

  /**
   * 根据实体查询对应的List
   */
  public List<ProdAppraise> findProdAppList(ProdAppraise prodAppraise) throws SQLException;

  public ProdAppraise findProdAppByOrderAndSku(ProdAppraise prodAppraise) throws SQLException;

  public AppraiseAddtoContent findAppendByOrderAndSku(AppraiseAddtoContent appraiseAdd)
      throws SQLException;

  public int findProdAppraiseByOrderDetailId(Map map) throws SQLException;
  
  


}
