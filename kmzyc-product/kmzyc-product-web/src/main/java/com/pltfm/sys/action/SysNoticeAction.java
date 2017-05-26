package com.pltfm.sys.action;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import com.pltfm.sys.model.SysDept;
import com.pltfm.sys.model.SysNotice;
import com.pltfm.sys.model.SysNoticeSend;
import com.pltfm.sys.model.SysUser;
import com.pltfm.sys.bean.SysDeptBean;
import com.pltfm.sys.bean.SysNoticeBean;
import com.pltfm.sys.bean.SysUserBean;
import com.pltfm.sys.util.DatetimeUtil;
import com.kmzyc.commons.page.Page;
import com.pltfm.sys.util.XiuSystemConstant;

/**
 * 类 SysNoticeAction  公告Action类
 *
 * @author  
 * @version 2.1
 * @since   JDK1.5
 */
public class SysNoticeAction extends ActionSupport implements ModelDriven {
	Logger log = Logger.getLogger(this.getClass());
	SysNotice sysnotice = new SysNotice();
    String rtnMessage;

    Page page = new Page();    //分页变量
    int paramPageSize = XiuSystemConstant.PARAM_PAGE_SIZE;    //接受页面中传过来的每页显示条数的参数
    String[] delId;            //删除操作id
	List dataList;
	String userIdStr;
	String userNameStr;
	
	
	
	
	
    /**
     * 按分页获取列表
     *
     * @param      
     * @return     String
     * @exception  
     */
    public String gotoList() throws Exception {
    	log.warn("================ in SysNoticeAction gotoList()");
    	try{
	    	SysNoticeBean bean = new SysNoticeBean();
	    	this.page.setPageSize(this.getParamPageSize());  //设置每页显示条数
	    	sysnotice.setIsEnable("1");
	        Page returnPage = bean.searchPageByVo(this.getPage(),sysnotice);
	        this.setPage(returnPage);
    	} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
    	
    	return SUCCESS;
    }
    
    
    
    /**
     * 新增公告
     *
     * @param      
     * @return     String
     * @exception  
     */
    public String doSave() throws Exception {
        log.warn("================ in SysNoticeAction doSave() ");
        Map session = ActionContext.getContext().getSession();
        SysUser su = (SysUser)session.get("sysUser");  
        sysnotice.setCreateUser(su.getUserId());
        sysnotice.setCreateDate(DatetimeUtil.getCalendarInstance().getTime());
        try{
        	//保存公告
	        SysNoticeBean bean = new SysNoticeBean();
	        Integer noticeId = bean.doSave(sysnotice);
	        log.warn("---------- added noticeId="+noticeId);
	        //发送公告（非草稿）
	        if(!"0".equals(sysnotice.getNoticeSt()))
	            bean.sendNotice(noticeId,userIdStr);
	        
    	} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
    	}
    	setRtnMessage("operateOK");
    	sysnotice = new SysNotice();
        return gotoList();
    }
	
	
        
    
    /**
     * 删除公告（将公告置为无效）
     *
     * @param      
     * @return     String
     * @exception  
     */
	public String doDelete() throws Exception {
		log.warn("================ in SysNoticeAction doDelete() ");
		try {
			SysNoticeBean bean = new SysNoticeBean();
			bean.doDelete(delId);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		rtnMessage = "deleteOk";
		return gotoList();
	}
	
	
	
	
    /**
     * 获取详细信息
     *
     * @param      
     * @return     String
     * @exception  
     */
	public String getDetail() throws Exception {
		 log.warn("================ in SysNoticeAction getDetail()");
		Integer noticeId = sysnotice.getNoticeId();
		try{
			//公告信息
			SysNoticeBean bean = new SysNoticeBean();
			this.sysnotice = bean.getDetail(noticeId);
		    //公告发送信息
			List sendList = bean.getSendListByNoticeId(sysnotice.getNoticeId());
			userIdStr = "";
			userNameStr = "";
			if(sendList!=null  &&  sendList.size()>0){
				for(int i=0; i<sendList.size(); i++){
					SysNoticeSend sendvo = (SysNoticeSend)sendList.get(i);
					userIdStr += sendvo.getUserId().toString() + ",";     //组装idStr
					userNameStr += sendvo.getSendRemark() + ",";     //组装nameStr
				}
				userIdStr = userIdStr.substring(0,userIdStr.lastIndexOf(","));
				userNameStr = userNameStr.substring(0,userNameStr.lastIndexOf(","));
			}
			log.warn("------------- userIdStr="+userIdStr);
			log.warn("------------- userNameStr="+userNameStr);

		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	
	
	
    /**
     * 修改草稿(不发送)
     *
     * @param      
     * @return     String
     * @exception  
     */
	public String doUpdate() throws Exception {
		log.warn("================ in SysNoticeAction doUpdate() ");
		Map session = ActionContext.getContext().getSession();
		SysUser su = (SysUser) session.get("sysUser");
		sysnotice.setUpdateUser(su.getUserId());
		sysnotice.setUpdateDate(DatetimeUtil.getCalendarInstance().getTime());
		try{
			//修改公告
			SysNoticeBean bean = new SysNoticeBean();
			sysnotice = bean.doUpdate(sysnotice);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		setRtnMessage("operateOK");
		sysnotice = new SysNotice();
		return gotoList();
	}
	
	
	
	
    /**
     * 保存修改并发送
     *
     * @param      
     * @return     String
     * @exception  
     */
	public String doUpdateAndSend() throws Exception {
		log.warn("================ in SysNoticeAction doUpdateAndSend() ");
		Map session = ActionContext.getContext().getSession();
		SysUser su = (SysUser) session.get("sysUser");
		sysnotice.setNoticeSt("1");
		sysnotice.setUpdateUser(su.getUserId());
		sysnotice.setUpdateDate(DatetimeUtil.getCalendarInstance().getTime());
		try{
			// 修改
			SysNoticeBean bean = new SysNoticeBean();
			sysnotice = bean.doUpdate(sysnotice);    
			//发送公告
	        bean.sendNotice(sysnotice.getNoticeId(),userIdStr);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		setRtnMessage("operateOK");
		sysnotice = new SysNotice();
		return gotoList();
	}
	
	
	
	
    /**
     * 置为无效
     *
     * @param      
     * @return     String
     * @exception  
     */
	public String doDisable() throws Exception {
		log.warn("================ in SysNoticeAction doDisable() ");
		Map session = ActionContext.getContext().getSession();
		SysUser su = (SysUser) session.get("sysUser");
		sysnotice.setUpdateUser(su.getUserId());
		sysnotice.setUpdateDate(DatetimeUtil.getCalendarInstance().getTime());
		sysnotice.setIsEnable("0");
		try{
			SysNoticeBean bean = new SysNoticeBean();
			sysnotice = bean.doUpdate(sysnotice);    // 修改
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		setRtnMessage("operateOK");
		sysnotice = new SysNotice();
		return gotoList();
	}
	
	
	
	
	
    /**
     * 按分页获取我的公告列表
     *
     * @param      
     * @return     String
     * @exception  
     */
    public String getMyNoticeList() throws Exception {
    	log.warn("================ in SysNoticeAction getMyNoticeList()");
    	Map session = ActionContext.getContext().getSession();
		SysUser su = (SysUser) session.get("sysUser");
    	try{
	    	SysNoticeBean bean = new SysNoticeBean();
	    	this.page.setPageSize(this.getParamPageSize());  //设置每页显示条数
	    	sysnotice.setIsEnable("1");
	    	sysnotice.setCreateUser(su.getUserId());    //用createUser字段代为存储发送关系表中的接收人id
	        Page returnPage = bean.searchMyNoticePageByVo(this.getPage(),sysnotice);
	        this.setPage(returnPage);
    	} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
    	return SUCCESS;
    }
	
	
	
    
    /**
     * 获取我的公告详细信息
     *
     * @param      
     * @return     String
     * @exception  
     */
	public String getMyNoticeDetail() throws Exception {
		log.warn("================ in SysNoticeAction getMyNoticeDetail()");
		Map session = ActionContext.getContext().getSession();
		SysUser su = (SysUser) session.get("sysUser");
		Integer noticeId = sysnotice.getNoticeId();
		try{
			//公告信息
			SysNoticeBean bean = new SysNoticeBean();
			this.sysnotice = bean.getDetail(noticeId);
		    //公告已读状态
            bean.setIsRead(sysnotice.getNoticeId(),su.getUserId());

		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
    
    
    
    
    /**
     * 删除我的公告（将公告置为无效）
     *
     * @param      
     * @return     String
     * @exception  
     */
	public String doDeleteMyNotice() throws Exception {
		log.warn("================ in SysNoticeAction doDeleteMyNotice() ");
		Map session = ActionContext.getContext().getSession();
		SysUser su = (SysUser) session.get("sysUser");
		try {
			SysNoticeBean bean = new SysNoticeBean();
			bean.doDeleteMyNotice(delId,su.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		rtnMessage = "deleteOk";
		return getMyNoticeList();
	}
	
	
	
	
    /**
     * 首页上获取我的未读公告
     *
     * @param      
     * @return     String
     * @exception  
     */
    public String ajaxGetHomeMainMyNotice() throws Exception {
    	log.warn("================ in SysNoticeAction ajaxGetHomeMainMyNotice()");
    	Map session = ActionContext.getContext().getSession();
		SysUser su = (SysUser) session.get("sysUser");
    	try{
	    	SysNoticeBean bean = new SysNoticeBean();
	        dataList = bean.getMyNoticeList(su.getUserId());
    	} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
    	return SUCCESS;
    }
	
	
	
	
	
	
    /**
     * getter  and  setter
     *
     */
    public String getRtnMessage() {
		return rtnMessage;
	}
	public void setRtnMessage(String rtnMessage) {
		this.rtnMessage = rtnMessage;
	}
	
    public Page getPage() {
        return page;
    }
    public void setPage(Page page) {
        this.page = page;
    }
    
    public int getParamPageSize() {
        return paramPageSize;
    }
    public void setParamPageSize(int paramPageSize) {
        this.paramPageSize = paramPageSize;
    }
    
    public String[] getDelId() {
    	return delId;
    }
    public void setDelId(String[] delId) {
    	this.delId = delId;
    }
	
    public List getDataList() {
        return dataList;
    }
    public void setDataList(List dataList) {
        this.dataList = dataList;
    }
    
    public String getUserIdStr() {
        return userIdStr;
    }
    public void setUserIdStr(String userIdStr) {
        this.userIdStr = userIdStr;
    }
	
    public String getUserNameStr() {
        return userNameStr;
    }
    public void setUserNameStr(String userNameStr) {
        this.userNameStr = userNameStr;
    }
	
	
    /**
     * struts2 Action default and model driven method
     *
     */
	public String doDefault() throws Exception{
        return INPUT;
    }

	public Object getModel() {
		return sysnotice;
	}
}
