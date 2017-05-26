package com.pltfm.sys.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.sys.bean.SysDeptBean;
import com.pltfm.sys.model.SysDept;
import com.pltfm.sys.model.SysUser;
import com.kmzyc.commons.page.Page;
import com.pltfm.sys.util.XiuSystemConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

/**
 * 类 SysDeptAction 部门Action类
 *
 * @author
 * @version 2.1
 * @since JDK1.5
 */
public class SysDeptAction extends ActionSupport implements ModelDriven {
	private static Logger logger = LoggerFactory.getLogger(SysDeptAction.class);
    SysDept sysdept = new SysDept();    //model
    String rtnMessage;

    List deptList;
    Page page = new Page(); // 分页变量
    int paramPageSize = XiuSystemConstant.PARAM_PAGE_SIZE; // 接受页面中传过来的每页显示条数的参数
    String[] delId; // 删除操作id
    String provinceNo;
    String cityNo;
    String isHaveSon;
    String upDeptName;


    /**
     * 获取所有部门列表
     *
     * @return String
     */
    public String gotoList() throws Exception {
        try {
            SysDeptBean bean = SysDeptBean.instance();
            deptList = bean.getAllDeptList();
        } catch (Exception e) {
        	logger.error("SysDeptAction.gotoList异常：" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }


    /**
     * 新增页面
     *
     * @return String
     */
    public String gotoAdd() throws Exception {
        try {
            return SUCCESS;
        } catch (Exception e) {
        	logger.error("SysDeptAction.gotoAdd异常：" + e.getMessage(), e);
            return ERROR;
        }

    }


    /**
     * 新增
     *
     * @return String
     */
    public void doSave() throws Exception {
        // log.warn("================ in SysDeptAction save() ");
        System.err.println("-----------------------!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!________------------------------");
        Map session = ActionContext.getContext().getSession();
        SysUser su = (SysUser) session.get("sysUser");
        sysdept.setCreateUser(su.getUserId());
        try {
            SysDeptBean bean = SysDeptBean.instance();
            bean.addSysDept(sysdept);
        } catch (Exception e) {
        	logger.error("SysDeptAction.doSave异常：" + e.getMessage(), e);
        }
    }


    /**
     * 获取详细信息
     *
     * @return String
     */
    public String getDetail() throws Exception {

        // log.warn("================ in SysDeptAction getDetail()");

        try {
            Assert.notNull(sysdept);
            Integer deptId = sysdept.getDeptId();
            SysDeptBean bean = SysDeptBean.instance();
            this.sysdept = bean.getSysDeptDetail(deptId);
            List sonList = bean.getSonDeptList(sysdept.getDeptId());
            if (sonList != null && sonList.size() > 0) {
                isHaveSon = "1";
            } else {
                isHaveSon = "0";
            }
            upDeptName = "";
            if ( sysdept.getUpDeptid() != null) {
                SysDept upDept = bean.getSysDeptDetail(sysdept.getUpDeptid());
                if (upDept != null && upDept.getDeptName() != null) {
                    upDeptName = upDept.getDeptName();
                }
            }

        } catch (Exception e) {
        	logger.error("SysDeptAction.getDetail异常：" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }


    /**
     * 修改
     *
     * @return String
     */
    public String doUpdate() throws Exception {
        // log.warn("================ in SysDeptAction doUpdate() ");
        Map session = ActionContext.getContext().getSession();
        SysUser su = (SysUser) session.get("sysUser");
        sysdept.setUpdateUser(su.getUserId());
        try {
            SysDeptBean bean = SysDeptBean.instance();
            sysdept = bean.updateSysDept(sysdept);    // 修改
        } catch (Exception e) {
        	logger.error("SysDeptAction.doUpdate异常：" + e.getMessage(), e);
            return ERROR;
        }
        setRtnMessage("updateOK");
        sysdept = new SysDept();
        return listSysDept();
    }


    /**
     * 删除
     *
     * @return String
     */
    public String doDelete() throws Exception {
        try {
            SysDeptBean bean = SysDeptBean.instance();
            Integer deptId = 0;
            if (sysdept != null && sysdept.getDeptId() != null) {
                deptId = sysdept.getDeptId();
            }
            bean.deleteSysDeptById(deptId);
        } catch (Exception e) {
        	logger.error("SysDeptAction.doDelete异常：" + e.getMessage(), e);
            return ERROR;
        }
        return gotoList();
    }

    /**
     * 除自己外的其他部门列表
     *
     * @return String
     */
    public String otherList() throws Exception {
        try {
            SysDeptBean bean = SysDeptBean.instance();
            deptList = bean.getOtherList(sysdept.getDeptId());
        } catch (Exception e) {
        	logger.error("SysDeptAction.otherList异常：" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }


    /**
     * 获取列表
     *
     * @return String
     */
    public String listSysDept() throws Exception {
        // log.warn("================ in SysDeptAction listSysDept()");
        try {
            SysDeptBean bean = SysDeptBean.instance();
            deptList = bean.getSysDeptList(sysdept);
        } catch (Exception e) {
        	logger.error("SysDeptAction.listSysDept异常：" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }


    public String getRtnMessage() {
        return rtnMessage;
    }

    public void setRtnMessage(String rtnMessage) {
        this.rtnMessage = rtnMessage;
    }

    public List getDeptList() {
        return deptList;
    }

    public void setDeptList(List deptList) {
        this.deptList = deptList;
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

    public String getCityNo() {
        return cityNo;
    }

    public void setCityNo(String cityNo) {
        this.cityNo = cityNo;
    }

    public String getProvinceNo() {
        return provinceNo;
    }

    public void setProvinceNo(String provinceNo) {
        this.provinceNo = provinceNo;
    }

    public String getIsHaveSon() {
        return isHaveSon;
    }

    public void setIsHaveSon(String isHaveSon) {
        this.isHaveSon = isHaveSon;
    }

    public String getUpDeptName() {
        return upDeptName;
    }

    public void setUpDeptName(String upDeptName) {
        this.upDeptName = upDeptName;
    }


    public String doDefault() throws Exception {
        return INPUT;
    }

    public Object getModel() {
        return sysdept;
    }
}
