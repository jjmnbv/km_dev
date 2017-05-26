package com.kmzyc.b2b.service.member;

import java.sql.SQLException;
import java.util.List;

import com.km.framework.page.Pagination;
import com.kmzyc.b2b.model.User;
import com.pltfm.app.vobject.Consult;

/**
 * Description:咨询信息服务接口 User: lishiming Date: 13-10-17 下午3:45 Since: 1.0
 */
public interface ConsultService {

  /**
   * 计算用户某审核状态下的咨询总数
   * 
   * @param userId
   * @param checkStatusList
   * @return
   * @throws SQLException
   */
  public Long countConsultByUserId(Long userId, List<Integer> checkStatusList) throws SQLException;

  /**
   * 保存咨询信息
   * 
   * @throws Exception
   * 
   */
  public Consult saveConsult(String consultType, User user, String consultContent,
      String productSkuId, String productSkuCode) throws Exception;

  /**
   * 
   * 分页查询产品咨询数据
   */
  public Pagination findConsultListByPage(Pagination page) throws Exception;
}
