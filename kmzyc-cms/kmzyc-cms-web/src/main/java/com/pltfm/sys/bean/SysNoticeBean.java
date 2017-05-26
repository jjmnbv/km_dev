package com.pltfm.sys.bean;

import com.pltfm.sys.dao.SysNoticeDAO;
import com.pltfm.sys.dao.SysNoticeDAOImpl;
import com.pltfm.sys.dao.SysNoticeSendDAO;
import com.pltfm.sys.dao.SysNoticeSendDAOImpl;
import com.pltfm.sys.model.SysNotice;
import com.pltfm.sys.model.SysNoticeSend;
import com.pltfm.sys.model.SysNoticeSendExample;
import com.pltfm.sys.util.DatetimeUtil;
import com.kmzyc.commons.page.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.SQLException;
import java.util.List;


/**
 * 类 SysNoticeBean 公告类
 *
 * @author
 * @version 2.1
 * @since JDK1.5
 */
public class SysNoticeBean extends BaseBean {

	private static Logger logger = LoggerFactory.getLogger(SysNoticeBean.class);

    public SysNoticeBean() {
        super();  //加载总的sqlmap配置文件
    }

    private static SysNoticeBean instance;

    public static SysNoticeBean instance(){
        if(instance==null)
            instance=new SysNoticeBean();
        return instance;
    }

    /**
     * 根据vo条件查询公告信息page
     *
     * @return Page
     */
    public Page searchPageByVo(Page pageParam, SysNotice vo) throws Exception {
        int pageNo = pageParam.getPageNo();// 当前查询第几页
        if (pageNo == 0)
            pageNo = 1;                    // 首次查询第一页
        int pageSize = pageParam.getPageSize(); // 每页显示记录数
        int skip = (pageNo - 1) * pageSize + 1;
        int max = (pageNo - 1) * pageSize + pageSize;
        vo.setSkip(skip);
        vo.setMax(max);
        Page page = null;
        //处理like 条件
        if (vo.getNoticeTitle() != null && !vo.getNoticeTitle().equals(""))
            vo.setNoticeTitle("%" + vo.getNoticeTitle() + "%");
        else
            vo.setNoticeTitle(null);
        try {
            SysNoticeDAOImpl dao = SysNoticeDAOImpl.instance(sqlMap);
            page = dao.selectPageByVo(pageParam, vo);
            page.setPageNo(pageNo);// 当前查询第几页
        } catch (Exception e) {
            logger.error("SysNoticeBean.searchPageByVo异常：" + e.getMessage(), e);
            
            throw e;
        }
        return page;
    }


    /**
     * 新增公告
     *
     * @return Integer
     */
    public Integer doSave(SysNotice sysnotice) throws Exception {
        try {
            SysNoticeDAOImpl dao = SysNoticeDAOImpl.instance(sqlMap);
            dao.insert(sysnotice);
        } catch (SQLException e) {
            logger.error("SysNoticeBean.doSave异常：" + e.getMessage(), e);
            
            throw e;
        }
        return sysnotice.getNoticeId();
    }


    /**
     * 删除所选 (软删除)
     *
     * @return void
     */
    public void doDelete(String[] ids) throws Exception {
        try {
            sqlMap.startTransaction();
            SysNoticeDAOImpl dao = SysNoticeDAOImpl.instance(sqlMap);
            // 开始循环删除
            if (ids != null && ids.length > 0) {
                for (int i = 0; i < ids.length; i++) {
                    SysNotice vo = new SysNotice();
                    vo.setNoticeId(Integer.valueOf(ids[i]));
                    vo.setIsEnable("0");
                    dao.updateByPrimaryKeySelective(vo);
                }
            }
            sqlMap.commitTransaction();
        } catch (SQLException e) {
            logger.error("SysNoticeBean.doDelete异常：" + e.getMessage(), e);
            
            throw e;
        } finally {
            try {
                sqlMap.endTransaction();
            } catch (SQLException e) {
                logger.error("SysNoticeBean.doDelete异常：" + e.getMessage(), e);
            }
        }
    }


    /**
     * 获取详细信息
     *
     * @return SysNotice
     */
    public SysNotice getDetail(Integer id) throws Exception {
        SysNotice sysNotice = null;
        try {
            SysNoticeDAOImpl dao = SysNoticeDAOImpl.instance(sqlMap);
            sysNotice = dao.selectByPrimaryKey(id);
        } catch (SQLException e) {
            logger.error("SysNoticeBean.getDetail异常：" + e.getMessage(), e);
            
            throw e;
        }
        return sysNotice;
    }


    /**
     * 修改
     *
     * @return SysNotice
     */
    public SysNotice doUpdate(SysNotice sysnotice) throws Exception {
        try {
            SysNoticeDAOImpl dao = SysNoticeDAOImpl.instance(sqlMap);
            dao.updateByPrimaryKeySelective(sysnotice);
        } catch (SQLException e) {
            logger.error("SysNoticeBean.doUpdate异常：" + e.getMessage(), e);
            throw e;
        }
        return sysnotice;
    }


    /**
     * 发送公告（操作公告操作员关系表 sys_notice_send）
     *
     * @return Integer
     */
    public void sendNotice(Integer noticeId, String userIdStr) throws Exception {
    	logger.warn("================ in SysNoticeBean addSysUserRole() ");
        try {
            sqlMap.startTransaction();
            // 删除该公告的旧关系
            SysNoticeSendDAO dao = SysNoticeSendDAOImpl.instance(sqlMap);
            SysNoticeSendExample exam = new SysNoticeSendExample();
            exam.createCriteria().andNoticeIdEqualTo(noticeId);
            dao.deleteByExample(exam);
            logger.warn("---------------------------------------- 删除旧关系完毕！ noticeId=" + noticeId);
            // 添加该公告的新关系
            String[] userids = userIdStr.split(",");
            if (userids.length > 0) {
                for (int i = 0; i < userids.length; i++) {
                    SysNoticeSend vo = new SysNoticeSend();
                    vo.setNoticeId(noticeId);
                    vo.setUserId(Integer.valueOf(userids[i]));
                    vo.setIsEnable("1");
                    vo.setIsRead("0");
                    vo.setSendTime(DatetimeUtil.getCalendarInstance().getTime());
                    dao.insert(vo);
                }
            }
            logger.warn("---------------------------------------- 添加新关系完毕！！！");
            sqlMap.commitTransaction();
        } catch (SQLException e) {
            logger.error("SysNoticeBean.sendNotice异常：" + e.getMessage(), e);
            
            throw e;
        } finally {
            try {
                sqlMap.endTransaction();
            } catch (SQLException e) {
                logger.error("SysNoticeBean.sendNotice异常：" + e.getMessage(), e);
            }
        }
    }


    /**
     * 获取某个公告的发送关系列表
     *
     * @return List
     */
    public List getSendListByNoticeId(Integer noticeId) throws Exception {
    	logger.warn("================ in SysNoticeBean getSendListByNoticeId() ");
        List dataList = null;
        try {
            SysNoticeSendDAO dao = SysNoticeSendDAOImpl.instance(sqlMap);
            dataList = dao.selectSendListByNotice(noticeId);
//			SysNoticeSendExample exam = new SysNoticeSendExample();
//			exam.createCriteria().andNoticeIdEqualTo(noticeId).andIsEnableEqualTo("1");
//			dataList = dao.selectByExample(exam);
            logger.warn("------------- sendList.size()=" + dataList.size());
        } catch (SQLException e) {
            logger.error("SysNoticeBean.getSendListByNoticeId异常：" + e.getMessage(), e);
            
            throw e;
        }
        return dataList;
    }


    /**
     * 根据vo条件查询我的公告信息page
     *
     * @return Page
     */
    public Page searchMyNoticePageByVo(Page pageParam, SysNotice vo) throws Exception {
        int pageNo = pageParam.getPageNo();// 当前查询第几页
        if (pageNo == 0)
            pageNo = 1;                    // 首次查询第一页
        int pageSize = pageParam.getPageSize(); // 每页显示记录数
        int skip = (pageNo - 1) * pageSize + 1;
        int max = (pageNo - 1) * pageSize + pageSize;
        vo.setSkip(skip);
        vo.setMax(max);
        Page page = null;
        //处理like 条件
        if (vo.getNoticeTitle() != null && !vo.getNoticeTitle().equals(""))
            vo.setNoticeTitle("%" + vo.getNoticeTitle() + "%");
        else
            vo.setNoticeTitle(null);
        try {
            SysNoticeDAOImpl dao = SysNoticeDAOImpl.instance(sqlMap);
            page = dao.searchMyNoticePageByVo(pageParam, vo);
            page.setPageNo(pageNo);// 当前查询第几页
        } catch (Exception e) {
            logger.error("SysNoticeBean.searchMyNoticePageByVo异常：" + e.getMessage(), e);
            
            throw e;
        }
        return page;
    }


    /**
     * 删除我的公告 (软删除)
     *
     * @return void
     */
    public void doDeleteMyNotice(String[] ids, Integer userId) throws Exception {
        try {
            sqlMap.startTransaction();
            SysNoticeSendDAO dao = SysNoticeSendDAOImpl.instance(sqlMap);
            // 开始循环删除
            if (ids != null && ids.length > 0) {
                for (int i = 0; i < ids.length; i++) {
                    SysNoticeSend vo = new SysNoticeSend();
                    vo.setNoticeId(Integer.valueOf(ids[i]));
                    vo.setUserId(userId);
                    vo.setIsEnable("0");
                    dao.updateByPrimaryKeySelective(vo);
                }
            }
            sqlMap.commitTransaction();
        } catch (SQLException e) {
            logger.error("SysNoticeBean.doDeleteMyNotice异常：" + e.getMessage(), e);
            
            throw e;
        } finally {
            try {
                sqlMap.endTransaction();
            } catch (SQLException e) {
                logger.error("SysNoticeBean.doDeleteMyNotice异常：" + e.getMessage(), e);
            }
        }
    }


    /**
     * 修改已读状态
     *
     * @return SysNotice
     */
    public SysNoticeSend setIsRead(Integer noticeId, Integer userId) throws Exception {
        SysNoticeSend vo = new SysNoticeSend();
        try {
            SysNoticeSendDAO dao = SysNoticeSendDAOImpl.instance(sqlMap);
            vo.setNoticeId(noticeId);
            vo.setUserId(userId);
            vo.setIsRead("1");
            dao.updateByPrimaryKeySelective(vo);
        } catch (SQLException e) {
            logger.error("SysNoticeBean.setIsRead异常：" + e.getMessage(), e);
            throw e;
        }
        return vo;
    }


    /**
     * 获取主页上我的公告列表
     *
     * @return List
     */
    public List getMyNoticeList(Integer userId) throws Exception {
    	logger.warn("================ in SysNoticeBean getMyNoticeList() ");
        List dataList = null;
        try {
            SysNoticeDAO dao = SysNoticeDAOImpl.instance(sqlMap);
            dataList = dao.selectMyNoticeList(userId);
            logger.warn("------------- myNoticeList.size()=" + dataList.size());
        } catch (SQLException e) {
            logger.error("SysNoticeBean.getMyNoticeList异常：" + e.getMessage(), e);
            
            throw e;
        }
        return dataList;
    }
}
