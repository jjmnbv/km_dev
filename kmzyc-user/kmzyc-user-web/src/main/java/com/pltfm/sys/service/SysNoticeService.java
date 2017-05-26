package com.pltfm.sys.service;

import java.util.List;

import com.kmzyc.commons.page.Page;
import com.pltfm.sys.model.SysNotice;
import com.pltfm.sys.model.SysNoticeSend;

public interface SysNoticeService {
  /**
   * 根据vo条件查询公告信息page
   * 
   * @param Page,SysNotice
   * @return Page
   * @exception Exception
   */
  public Page searchPageByVo(Page pageParam, SysNotice vo) throws Exception;

  /**
   * 新增公告
   *
   * @param SysNotice @return Integer @exception
   */
  public Integer doSave(SysNotice sysnotice) throws Exception;

  /**
   * 删除所选 (软删除)
   * 
   * @param String[]
   * @return void
   */
  public void doDelete(String[] ids) throws Exception;

  /**
   * 获取详细信息
   * 
   * @param Integer
   * @return SysNotice
   * @exception Exception
   */
  public SysNotice getDetail(Integer id) throws Exception;

  /**
   * 修改
   * 
   * @param SysNotice
   * @return SysNotice
   * @exception Exception
   */
  public SysNotice doUpdate(SysNotice sysnotice) throws Exception;

  /**
   * 发送公告（操作公告操作员关系表 sys_notice_send）
   *
   * @param Integer,String
   * @return Integer
   * @exception Exception
   */
  public void sendNotice(Integer noticeId, String userIdStr) throws Exception;

  /**
   * 获取某个公告的发送关系列表
   *
   * @param Integer
   * @return List
   * @exception Exception
   */
  public List getSendListByNoticeId(Integer noticeId) throws Exception;

  /**
   * 根据vo条件查询我的公告信息page
   * 
   * @param Page,SysNotice
   * @return Page
   * @exception Exception
   */
  public Page searchMyNoticePageByVo(Page pageParam, SysNotice vo) throws Exception;

  /**
   * 删除我的公告 (软删除)
   * 
   * @param String[]
   * @return void
   */
  public void doDeleteMyNotice(String[] ids, Integer userId) throws Exception;

  /**
   * 修改已读状态
   * 
   * @param Integer,Integer
   * @return SysNotice
   * @exception Exception
   */
  public SysNoticeSend setIsRead(Integer noticeId, Integer userId) throws Exception;

  /**
   * 获取主页上我的公告列表
   *
   * @param Integer
   * @return List
   * @exception Exception
   */
  public List getMyNoticeList(Integer userId) throws Exception;


}
