package com.pltfm.app.service;

import java.util.List;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.vobject.MobileCodeInf;

/**
 * 手机随机码信息业务逻辑接口
 * 
 * @author cjm
 * @since 2013-7-10
 */
public interface MobileCodeInfService {
  /**
   * 添加手机随机码
   * 
   * @param record 手机随机码实体
   * @return 返回值
   * @throws Exception 异常
   */
  Integer addMobileCodeInf(MobileCodeInf mobileCodeInf) throws Exception;

  /**
   * 修改手机随机码
   * 
   * @param record 手机随机码实体
   * @return 返回值
   * @throws Exception 异常
   */
  Integer updateMobileCodeInf(MobileCodeInf mobileCodeInf) throws Exception;

  /**
   * 根据主键查询单条手机随机码信息
   * 
   * @param n_PersonalityId 手机随机码ID
   * @return 返回值
   * @throws Exception 异常
   */
  MobileCodeInf selectByPrimaryKey(Integer n_CellPhoneTattedCodeId) throws Exception;

  /**
   * 根据主键进行删除单条手机随机码
   * 
   * @param n_PersonalityId 手机随机码ID
   * @return 返回值
   * @throws Exception 异常
   */
  Integer deleteByPrimaryKey(List<Integer> n_CellPhoneTattedCodeIds) throws Exception;

  /**
   * 根据vo条件查询分类信息page
   * 
   * @param pageParam 分页实体
   * @param vo 手机随机码实体
   * @return 返回值
   * @throws Exception
   */
  Page searchPageByVo(Page pageParam, MobileCodeInf vo) throws Exception;

}
