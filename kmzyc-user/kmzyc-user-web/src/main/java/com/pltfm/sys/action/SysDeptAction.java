package com.pltfm.sys.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.util.ConfigureUtils;
import com.pltfm.sys.model.SysDept;
import com.pltfm.sys.model.SysUser;
import com.pltfm.sys.service.impl.SysDeptServiceImpl;
import com.pltfm.sys.util.XiuSystemConstant;

/**
 * 类 SysDeptAction 部门Action类
 * 
 * @author
 * @version 2.1
 * @since JDK1.5
 */
@SuppressWarnings({"rawtypes", "serial"})
public class SysDeptAction extends BaseAction implements ModelDriven {
  Logger log = LoggerFactory.getLogger(this.getClass());
  SysDept sysdept = new SysDept(); // model
  String rtnMessage;

  List deptList;
  Page page = new Page(); // 分页变量
  int paramPageSize = XiuSystemConstant.PARAM_PAGE_SIZE; // 接受页面中传过来的每页显示条数的参数
  String[] delId; // 删除操作id
  String provinceNo;
  String cityNo;
  String isHaveSon;
  String upDeptName;
  @Resource
  public SysDeptServiceImpl sysDeptServiceImpl;


  /**
   * 获取所有部门列表
   *
   * @param @return String @exception
   */
  public String gotoList() throws Exception {
    try {
      deptList = sysDeptServiceImpl.getAllDeptList();
    } catch (Exception e) {
      log.error("初始化部门列表失败" + e.getMessage(), e);
      return ERROR;
    }
    return SUCCESS;
  }



  /**
   * 新增页面
   *
   * @param @return String @exception
   */
  public String gotoAdd() throws Exception {
    try {
      return SUCCESS;
    } catch (Exception e) {
      log.error("进入添加部门页面失败" + e.getMessage(), e);
      return ERROR;
    }

  }


  /**
   * 新增部门提交
   *
   * @param @return String @exception
   */
  public void doSave() throws Exception {
    Map session = ActionContext.getContext().getSession();
    SysUser su = (SysUser) session.get("sysUser");
    sysdept.setCreateUser(su.getUserId());
    try {
      Integer sysid = sysDeptServiceImpl.addSysDept(sysdept);
      this.addActionMessage(ConfigureUtils.getMessageConfig("sysDept.add.success"));
      // 设置ajax返回参数1：执行添加成功
      super.writeJson(sysid);
      // ServletActionContext.getResponse().getWriter().write(1);
    } catch (Exception e) {
      log.error("添加部门异常" + e.getMessage(), e);
    }
  }



  /**
   * 获取部门详细信息（初始化）
   *
   * @param @return String @exception
   */
  public String getDetail() throws Exception {
    Integer deptId = sysdept.getDeptId();
    try {
      this.sysdept = sysDeptServiceImpl.getSysDeptDetail(deptId);
      List sonList = sysDeptServiceImpl.getSonDeptList(deptId);
      if (sonList != null && sonList.size() > 0) {
        isHaveSon = "1";
      } else {
        isHaveSon = "0";
      }
      upDeptName = "";
      if (sysdept != null && sysdept.getUpDeptid() != null) {
        SysDept upDept = sysDeptServiceImpl.getSysDeptDetail(sysdept.getUpDeptid());
        if (upDept != null && upDept.getDeptName() != null) {
          upDeptName = upDept.getDeptName();
        }
      }
    } catch (Exception e) {
      log.error("初始化部门信息失败" + e.getMessage(), e);
      return ERROR;
    }
    return SUCCESS;
  }



  /**
   * 修改部门信息提交
   *
   * @param @return String @exception
   */
  public String doUpdate() throws Exception {
    Map session = ActionContext.getContext().getSession();
    SysUser su = (SysUser) session.get("sysUser");
    sysdept.setUpdateUser(su.getUserId());
    try {
      sysdept = sysDeptServiceImpl.updateSysDept(sysdept); // 修改
      this.addActionMessage(ConfigureUtils.getMessageConfig("sysDept.update.success"));
    } catch (Exception e) {
      log.error("修改部门信息异常" + e.getMessage(), e);
      return ERROR;
    }
    sysdept = new SysDept();
    return listSysDept();
  }



  /**
   * 删除部门
   *
   * @param @return String @exception
   */
  public String doDelete() throws Exception {
    try {
      Integer deptId = 0;
      if (sysdept != null && sysdept.getDeptId() != null) {
        deptId = sysdept.getDeptId();
      }
      sysDeptServiceImpl.deleteSysDeptById(deptId);
      this.addActionMessage(ConfigureUtils.getMessageConfig("sysDept.delete.success"));
    } catch (Exception e) {
      log.error("部门删除异常" + e.getMessage(), e);
      return ERROR;
    }
    return gotoList();
  }

  /**
   * 获取除自己外的其他部门列表
   *
   * @param @return String @exception
   */
  public String otherList() throws Exception {
    try {
      deptList = sysDeptServiceImpl.getOtherList(sysdept.getDeptId());
    } catch (Exception e) {
      log.error("获取其他部门信息异常" + e.getMessage(), e);
      return ERROR;
    }
    return SUCCESS;
  }


  /**
   * 获取部门列表
   *
   * @param @return String @exception
   */
  public String listSysDept() throws Exception {
    try {
      deptList = sysDeptServiceImpl.getSysDeptList(sysdept);
    } catch (Exception e) {
      log.error("获取部门列表失败" + e.getMessage(), e);
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

  @Override
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

  @Override
public Object getModel() {
    return sysdept;
  }
}
