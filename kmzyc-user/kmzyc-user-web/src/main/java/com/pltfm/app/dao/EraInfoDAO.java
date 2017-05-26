package com.pltfm.app.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import com.kmzyc.b2b.vo.EraInfo;



public interface EraInfoDAO {
  /**
   * 分页条件查询康美中药城会员列表
   * 
   * @param eraInfo
   * @return
   * @throws SQLException
   */
  List<EraInfo> selectEraInfoList(EraInfo eraInfo) throws SQLException;

  /**
   * 查询时代会员总数
   * 
   * @param eraInfo
   * @return
   * @throws SQLException
   */
  int selectEraInfoCount(EraInfo eraInfo) throws SQLException;

  /**
   * 删除康美中药城
   * 
   * @param eraInfoId
   * @return
   * @throws SQLException
   */
  int deleteByPrimaryKey(Long eraInfoId) throws SQLException;

  /**
   * 添加
   * 
   * @param record
   * @throws SQLException
   */
  BigDecimal insert(EraInfo record) throws SQLException;

  /**
   * 根据主键id查询康美中药城信息
   * 
   * @param eraInfoId
   * @return
   * @throws SQLException
   */
  EraInfo selectByPrimaryKey(Long eraInfoId) throws SQLException;

  /**
   * 关联login_info 查询是否是时代用户
   * 
   * @param loginAccount
   * @return
   * @throws SQLException
   */
  EraInfo selectOverLoginInfoByLoginId(String loginAccount) throws SQLException;

  /**
   * 根据主键id查询康美中药城信息
   * 
   * @param eraInfoId
   * @return
   * @throws SQLException
   */
  EraInfo selectByPrimaryKeys(EraInfo record) throws SQLException;
  
  /**
   * 根据eraNo查询康美中药城信息
   * 
   * @param eraInfoId
   * @return
   * @throws SQLException
   */
  EraInfo selectByEraNo(String eraNo) throws SQLException;  

  /**
   * 修改
   * 
   * @param record
   * @return
   * @throws SQLException
   */
  int updateByPrimaryKeySelective(EraInfo record) throws SQLException;

  /**
   * 修改直销登录信息
   * 
   * @param record
   * @return
   * @throws SQLException
   */
  int updateByLoginIdSelective(EraInfo record) throws SQLException;
  
  /**
   * 添加或更新表数据
   * @param record
   * @return
   * @throws SQLException
   */
  public BigDecimal insertorUpdateEraInfo(EraInfo record) throws SQLException;
}
