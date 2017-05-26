package com.pltfm.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pltfm.sys.dao.SysDeptDAO;
import com.pltfm.sys.model.SysDept;
import com.pltfm.sys.model.SysDeptExample;
import com.pltfm.sys.model.SysLog;
import com.pltfm.sys.service.SysDeptService;
import com.pltfm.sys.service.SysLogService;
import com.pltfm.sys.util.DatetimeUtil;

/**
 * 类 sysDeptServiceImpl 部门类
 *
 * @author
 * @version 2.1
 * @since JDK1.5
 */
@Service(value = "sysDeptService")
public class SysDeptServiceImpl implements SysDeptService {

  @Resource(name = "sysDeptDAO")
  public SysDeptDAO sysDeptDAO;
  @Resource
  private SysLogService sysLogServiceImpl;

  /**
   * 根据vo条件查询部门列表
   *
   * @param SysDept
   * @return List
   * @exception Exception
   */
  @SuppressWarnings("rawtypes")
  public List getSysDeptList(SysDept vo) throws Exception {
    SysDeptExample exam = new SysDeptExample(); // 组装where查询条件的对象
    // 组合条件 like
    String deptName = vo.getDeptName(); // 名称
    if (deptName != null && !deptName.equals(""))
      deptName = "%" + deptName + "%"; // like statement
    else
      deptName = "%%";
    exam.createCriteria().andDeptNameLike(deptName);
    exam.setOrderByClause("dept_cd asc");
    return sysDeptDAO.selectByExample(exam);
  }



  /**
   * 获取所有部门
   *
   * @param SysDept
   * @return List
   * @exception Exception
   */
  @SuppressWarnings("rawtypes")
  public List getAllDeptList() throws Exception {
    SysDeptExample exam = new SysDeptExample(); // 组装where查询条件的对象
    exam.createCriteria().andIsEnableEqualTo("1");
    exam.setOrderByClause("up_deptid asc , dept_cd asc");
    return sysDeptDAO.selectByExample(exam);
  }



  /**
   * 获取子部门列表
   *
   * @param Integer
   * @return List
   * @exception Exception
   */
  @SuppressWarnings("rawtypes")
  public List getSonDeptList(Integer upId) throws Exception {
    SysDeptExample exam = new SysDeptExample(); // 组装where查询条件的对象
    exam.createCriteria().andIsEnableEqualTo("1").andUpDeptidEqualTo(upId);
    exam.setOrderByClause("up_deptid asc , dept_cd asc");
    return sysDeptDAO.selectByExample(exam);
  }



  /**
   * 新增部门
   *
   * @param SysDept
   * @return Integer
   * @exception Exception
   */
  public Integer addSysDept(SysDept vo) throws Exception {
    // add
    vo.setCreateDate(DatetimeUtil.getCalendarInstance().getTime());
    Integer newid = sysDeptDAO.insert(vo);
    SysLog syslog = new SysLog();
    syslog.setSyslogTime(DatetimeUtil.getCalendarInstance().getTime());
    syslog.setSyslogUser(vo.getCreateUser());
    syslog.setTableName("sys_dept");
    syslog.setTableId(newid);
    syslog.setSyslogDiscrp("sys_dept insert");
    syslog.setSyslogType("0004");
    sysLogServiceImpl.addLogInfo(syslog);
    return vo.getDeptId();
  }



  /**
   * 根据deptId查询部门
   * 
   * @param Integer
   * @return SysDept
   * @exception Exception
   */
  public SysDept getSysDeptDetail(Integer id) throws Exception {
    SysDept sysDept = null;
    sysDept = sysDeptDAO.selectByPrimaryKey(id);
    return sysDept;
  }



  /**
   * 修改部门
   * 
   * @param SysDept
   * @return SysDept
   * @exception Exception
   */
  public SysDept updateSysDept(SysDept sysDept) throws Exception {
    // update
    sysDept.setUpdateDate(DatetimeUtil.getCalendarInstance().getTime());
    sysDeptDAO.updateByPrimaryKey(sysDept);
    SysLog vo = new SysLog();
    vo.setSyslogTime(DatetimeUtil.getCalendarInstance().getTime());
    vo.setSyslogType("0005");
    vo.setSyslogUser(sysDept.getUpdateUser());
    vo.setTableName("sys_dept");
    vo.setSyslogDiscrp("update sys_dept");
    vo.setTableId(sysDept.getDeptId());
    sysLogServiceImpl.addLogInfo(vo);
    return sysDept;
  }



  /**
   * 删除所选部门
   * 
   * @param String[]
   * @return void
   * @exception Exception
   */
  public void deleteSysDept(String[] sysDept) throws Exception {
    SysLog vo = new SysLog();
    // SysLogBean logbean = new SysLogBean();
    SysDept sysdept;
    // 开始循环删除
    if (sysDept != null && sysDept.length > 0) {
      for (int i = 0; i < sysDept.length; i++) {
        // do delete
        sysdept = sysDeptDAO.selectByPrimaryKey(Integer.valueOf(sysDept[i]));
        sysDeptDAO.deleteByPrimaryKey(Integer.valueOf(sysDept[i]));
        // set sys log
        vo.setSyslogTime(DatetimeUtil.getCalendarInstance().getTime());
        vo.setSyslogDiscrp("delete sys_dept");
        vo.setSyslogType("0006");
        vo.setSyslogUser(sysdept.getCreateUser());;
        vo.setTableName("sys_dept");
        vo.setTableId(Integer.valueOf(sysDept[i]));
        sysLogServiceImpl.addLogInfo(vo);
      }
    }
  }



  /**
   * 根据部门id删除部门
   * 
   * @param Integer
   * @return void
   * @exception Exception
   */
  public void deleteSysDeptById(Integer deptId) throws Exception {
    sysDeptDAO.deleteByPrimaryKey(deptId);
  }



  /**
   * 获取除自己外的所有
   *
   * @param Integer
   * @return List
   * @exception Exception
   */
  @SuppressWarnings("rawtypes")
  public List getOtherList(Integer deptId) throws Exception {
    SysDeptExample exam = new SysDeptExample(); // 组装where查询条件的对象
    exam.createCriteria().andIsEnableEqualTo("1");
    exam.createCriteria().andDeptIdGreaterThan(deptId);
    return sysDeptDAO.selectByDeptId(deptId);
  }

}
