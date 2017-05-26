package com.pltfm.app.dao;

import com.pltfm.app.vobject.MdicalExcusieInfo;

import java.sql.SQLException;
import java.util.List;

/**
 * 医务专属信息处理接口
 * 
 * @author gwl
 * @since 2013-07-08
 */
public interface MdicalExcusieInfoDAO {
  /**
   * 添加医务专属信息
   * 
   * @param mdicalExcusieInfo 客户等级实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public Integer insertMdicalExcusieInfo(MdicalExcusieInfo mdicalExcusieInfo) throws SQLException;

  /**
   * 删除医务专属信息
   * 
   * @param MdicalExcusieInfo 医务专属信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public Integer deleteMdicalExcusieInfo(MdicalExcusieInfo mdicalExcusieInfo) throws SQLException;

  /**
   * 按条件查询医务信息总数量
   * 
   * @param vo
   * @return 返回值
   */
  public int selectCountByVo(MdicalExcusieInfo mdicalExcusieInfo) throws SQLException;

  /**
   * 根据vo条件查询分类信息page
   * 
   * @param page 分页类
   * @param vo 信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */

  public List selectPageByVo(MdicalExcusieInfo mdicalExcusieInfo) throws SQLException;

  /**
   * 修改医务专属信息
   * 
   * @param MdicalExcusieInfo 医务专属信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public Integer udpateMdicalExcusieInfo(MdicalExcusieInfo mdicalExcusieInfo) throws SQLException;

  /**
   * 跟据主键id查询医务专属信息
   * 
   * @param MdicalExcusieInfo 医务专属信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public MdicalExcusieInfo getPersonal_id(Integer n_personal_id) throws SQLException;

  /**
   * 跟据个人id查询医务专属信息
   * 
   * @param MdicalExcusieInfo 医务专属信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  public MdicalExcusieInfo getPersonalId_FK(Integer value) throws SQLException;
}
