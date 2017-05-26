package com.pltfm.app.service;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.vobject.MdicalExcusieInfo;

/**
 * 个人基本信息处理接口
 * 
 * @author gwl
 * @since 2013-07-08
 */
public interface MdicalExcusieInfoService {

  /**
   * 添加询医务专属信息
   * 
   * @param 询医务专属实体
   * @return 返回值
   * @throws Exception异常
   */
  public Integer addMdicalExcusieInfo(MdicalExcusieInfo mdicalExcusieInfo) throws SQLException;

  /**
   * 删除询医务专属信息
   * 
   * @param 询医务专属信息实体
   * @return 返回值
   * @throws Exception异常
   */
  public Integer deleteMdicalExcusieInfo(List<String> n_mmeids) throws SQLException;

  /**
   * 分页查询医务信息
   * 
   * @param 医务信息实体
   * @return
   * @throws Exception 异常
   */
  public Page searchPageByVo(Page pageParam, MdicalExcusieInfo mdicalExcusieInfo) throws Exception;

  /**
   * 修改询医务专属信息
   * 
   * @param 询医务专属信息实体
   * @return 返回值
   * @throws Exception异常
   */
  public Integer udpateMdicalExcusieInfo(MdicalExcusieInfo mdicalExcusieInfo) throws SQLException;

  /**
   * 跟据个人id查询医务专属信息
   * 
   * @param MdicalExcusieInfo 医务专属信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public MdicalExcusieInfo getPersonal_id(Integer n_personal_id) throws SQLException;

}
