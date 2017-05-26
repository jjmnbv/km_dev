package com.pltfm.sys.dao;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.commons.page.Page;
import com.pltfm.sys.model.SysNotice;
import com.pltfm.sys.model.SysNoticeExample;

@SuppressWarnings("unchecked")
public interface SysNoticeDAO {
  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_notice
   * 
   * @ibatorgenerated Wed Jan 27 15:44:06 CST 2010
   */
  int countByExample(SysNoticeExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_notice
   * 
   * @ibatorgenerated Wed Jan 27 15:44:06 CST 2010
   */
  int deleteByExample(SysNoticeExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_notice
   * 
   * @ibatorgenerated Wed Jan 27 15:44:06 CST 2010
   */
  int deleteByPrimaryKey(Integer noticeId) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_notice
   * 
   * @ibatorgenerated Wed Jan 27 15:44:06 CST 2010
   */
  Integer insert(SysNotice record) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_notice
   * 
   * @ibatorgenerated Wed Jan 27 15:44:06 CST 2010
   */
  Integer insertSelective(SysNotice record) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_notice
   * 
   * @ibatorgenerated Wed Jan 27 15:44:06 CST 2010
   */
  List<SysNotice> selectByExample(SysNoticeExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_notice
   * 
   * @ibatorgenerated Wed Jan 27 15:44:06 CST 2010
   */
  SysNotice selectByPrimaryKey(Integer noticeId) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_notice
   * 
   * @ibatorgenerated Wed Jan 27 15:44:06 CST 2010
   */
  int updateByExampleSelective(SysNotice record, SysNoticeExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_notice
   * 
   * @ibatorgenerated Wed Jan 27 15:44:06 CST 2010
   */
  int updateByExample(SysNotice record, SysNoticeExample example) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_notice
   * 
   * @ibatorgenerated Wed Jan 27 15:44:06 CST 2010
   */
  int updateByPrimaryKeySelective(SysNotice record) throws SQLException;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_notice
   * 
   * @ibatorgenerated Wed Jan 27 15:44:06 CST 2010
   */
  int updateByPrimaryKey(SysNotice record) throws SQLException;

  /**
   * 根据条件分页获取列表信息
   * 
   * @param Page ,SysNotice
   * @return Page
   */
    com.kmzyc.commons.page.Page selectPageByVo(Page page, SysNotice vo) throws SQLException;

  /**
   * 根据条件分页获取我的公告列表信息
   * 
   * @param Page ,SysNotice
   * @return Page
   */
    com.kmzyc.commons.page.Page searchMyNoticePageByVo(Page page, SysNotice vo) throws SQLException;

  /**
   * 获取我的公告列表信息
   * 
   * @param Integer
   * @return List
   */
  List selectMyNoticeList(Integer userId) throws SQLException;

}
