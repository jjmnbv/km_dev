package com.pltfm.app.service;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.vobject.Coupons;

/**
 * 邮箱验证信息业务逻辑接口
 * 
 * @author cjm
 * @since 2013-7-23
 */
public interface EmailInfoService {
  /**
   * 根据vo条件查询分类信息page
   * 
   * @param pageParam 分页实体
   * @param vo 手机随机码实体
   * @return 返回值
   * @throws Exception
   */
  /*删除邮件业务  Page searchPageByVo(Page pageParam, EmailInfo vo) throws Exception;

  *//**
   * 添加邮箱随机码
   * 
   * @param record 邮箱随机码实体
   * @return 返回值
   * @throws Exception 异常
   *//*
  Integer addEmailInfo(EmailInfo emailInfo) throws Exception;
*/
  /**
   * 根据vo条件查询分类信息page
   * 
   * @param pageParam 分页实体
   * @param vo 优惠劵实体
   * @return 返回值
   * @throws Exception
   */
  Page searchPageByCoupons(Page pageParam, Coupons coupons) throws Exception;

}
