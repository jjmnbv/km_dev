package com.pltfm.app.service;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.vobject.LatestLogin;

/**
 * 最近登录信息业务逻辑接口
 * 
 * @author cjm
 * @since 2013-7-24
 */
public interface LatestLoginService {
  /**
   * 根据vo条件查询分类信息page
   * 
   * @param pageParam 分页实体
   * @param vo 最近登录实体
   * @return 返回值
   * @throws Exception
   */
  Page searchPageByVo(Page pageParam, LatestLogin vo) throws Exception;

  /**
   * 添加最近登录
   * 
   * @param record 最近登录实体
   * @return 返回值
   * @throws Exception 异常
   */
  Integer addLatestLogin(LatestLogin latestLogin) throws Exception;

}
