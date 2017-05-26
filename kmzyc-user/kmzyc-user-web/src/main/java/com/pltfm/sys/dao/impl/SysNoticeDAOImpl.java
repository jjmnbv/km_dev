package com.pltfm.sys.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.commons.page.Page;
import com.pltfm.sys.dao.SysNoticeDAO;
import com.pltfm.sys.model.SysModelUtil;
import com.pltfm.sys.model.SysNotice;
import com.pltfm.sys.model.SysNoticeExample;

@Component(value = "sysNoticeDAO")
public class SysNoticeDAOImpl implements SysNoticeDAO {
  @Resource(name = "sqlMapClient")
  /**
   * 数据库操作对象
   */
  private SqlMapClient sqlMapClient;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_notice
   *
   * @ibatorgenerated Wed Jan 27 15:44:06 CST 2010
   */
  @Override
public int countByExample(SysNoticeExample example) throws SQLException {
    Integer count =
        (Integer) sqlMapClient.queryForObject("sys_notice.ibatorgenerated_countByExample", example);
    return count;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_notice
   *
   * @ibatorgenerated Wed Jan 27 15:44:06 CST 2010
   */
  @Override
public int deleteByExample(SysNoticeExample example) throws SQLException {
    int rows = sqlMapClient.delete("sys_notice.ibatorgenerated_deleteByExample", example);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_notice
   *
   * @ibatorgenerated Wed Jan 27 15:44:06 CST 2010
   */
  @Override
public int deleteByPrimaryKey(Integer noticeId) throws SQLException {
    SysNotice key = new SysNotice();
    key.setNoticeId(noticeId);
    int rows = sqlMapClient.delete("sys_notice.ibatorgenerated_deleteByPrimaryKey", key);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_notice
   *
   * @ibatorgenerated Wed Jan 27 15:44:06 CST 2010
   */
  @Override
public Integer insert(SysNotice record) throws SQLException {
    Object newKey = sqlMapClient.insert("sys_notice.ibatorgenerated_insert", record);
    return (Integer) newKey;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_notice
   *
   * @ibatorgenerated Wed Jan 27 15:44:06 CST 2010
   */
  @Override
public Integer insertSelective(SysNotice record) throws SQLException {
    Object newKey = sqlMapClient.insert("sys_notice.ibatorgenerated_insertSelective", record);
    return (Integer) newKey;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_notice
   *
   * @ibatorgenerated Wed Jan 27 15:44:06 CST 2010
   */
  @Override
@SuppressWarnings("unchecked")
  public List<SysNotice> selectByExample(SysNoticeExample example) throws SQLException {
    List<SysNotice> list =
        sqlMapClient.queryForList("sys_notice.ibatorgenerated_selectByExample", example);
    return list;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_notice
   *
   * @ibatorgenerated Wed Jan 27 15:44:06 CST 2010
   */
  @Override
public SysNotice selectByPrimaryKey(Integer noticeId) throws SQLException {
    SysNotice key = new SysNotice();
    key.setNoticeId(noticeId);
    SysNotice record = (SysNotice) sqlMapClient
        .queryForObject("sys_notice.ibatorgenerated_selectByPrimaryKey", key);
    return record;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_notice
   *
   * @ibatorgenerated Wed Jan 27 15:44:06 CST 2010
   */
  @Override
public int updateByExampleSelective(SysNotice record, SysNoticeExample example)
      throws SQLException {
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows = sqlMapClient.update("sys_notice.ibatorgenerated_updateByExampleSelective", parms);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_notice
   *
   * @ibatorgenerated Wed Jan 27 15:44:06 CST 2010
   */
  @Override
public int updateByExample(SysNotice record, SysNoticeExample example) throws SQLException {
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows = sqlMapClient.update("sys_notice.ibatorgenerated_updateByExample", parms);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_notice
   *
   * @ibatorgenerated Wed Jan 27 15:44:06 CST 2010
   */
  @Override
public int updateByPrimaryKeySelective(SysNotice record) throws SQLException {
    int rows =
        sqlMapClient.update("sys_notice.ibatorgenerated_updateByPrimaryKeySelective", record);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table sys_notice
   *
   * @ibatorgenerated Wed Jan 27 15:44:06 CST 2010
   */
  @Override
public int updateByPrimaryKey(SysNotice record) throws SQLException {
    int rows = sqlMapClient.update("sys_notice.ibatorgenerated_updateByPrimaryKey", record);
    return rows;
  }

  /**
   * This class was generated by Apache iBATIS ibator. This class corresponds to the database table
   * sys_notice
   *
   * @ibatorgenerated Wed Jan 27 15:44:06 CST 2010
   */
  private static class UpdateByExampleParms extends SysNoticeExample {
    private Object record;

    public UpdateByExampleParms(Object record, SysNoticeExample example) {
      super(example);
      this.record = record;
    }

    public Object getRecord() {
      return record;
    }
  }



  /**
   * 根据条件分页获取列表信息
   *
   * @param Page,SysNotice
   * @return Page
   */
  @Override
public Page selectPageByVo(Page page, SysNotice vo) throws SQLException {
    // get page count
    SysModelUtil countResult =
        (SysModelUtil) sqlMapClient.queryForObject("sys_notice.getCount", vo);
    int recs = countResult.getTheCount().intValue();
    // System.out.println("*************** recs="+recs);
    int pagecount = 1;
    if (recs > 1) pagecount = (recs - 1) / page.getPageSize() + 1;
    page.setRecordCount(recs);
    page.setPageCount(pagecount);
    // get page data list
    List pageList = sqlMapClient.queryForList("sys_notice.searchPageByVo", vo);
    // System.out.println("***************************** notice page size()="+pageList.size());
    page.setDataList(pageList);
    return page;
  }



  /**
   * 根据条件分页获取我的公告列表信息
   *
   * @param Page,SysNotice
   * @return Page
   */
  @Override
public Page searchMyNoticePageByVo(Page page, SysNotice vo) throws SQLException {
    // get page count
    SysModelUtil countResult =
        (SysModelUtil) sqlMapClient.queryForObject("sys_notice.getMyNoticeCount", vo);
    int recs = countResult.getTheCount().intValue();
    // System.out.println("*************** recs="+recs);
    int pagecount = 1;
    if (recs > 1) pagecount = (recs - 1) / page.getPageSize() + 1;
    page.setRecordCount(recs);
    page.setPageCount(pagecount);
    // get page data list
    List pageList = sqlMapClient.queryForList("sys_notice.searchMyNoticePageByVo", vo);
    // System.out.println("***************************** notice page size()="+pageList.size());
    page.setDataList(pageList);
    return page;
  }



  /**
   * 获取我的公告列表信息
   *
   * @param Integer
   * @return List
   */
  @Override
public List<SysNotice> selectMyNoticeList(Integer userId) throws SQLException {
    List<SysNotice> list = sqlMapClient.queryForList("sys_notice.selectMyNoticeList", userId);
    return list;
  }
}
