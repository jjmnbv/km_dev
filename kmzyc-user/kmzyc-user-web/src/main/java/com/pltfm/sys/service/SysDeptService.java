package com.pltfm.sys.service;

import java.util.List;

import com.pltfm.sys.model.SysDept;

/**
 * 类 sysDeptServiceImpl 部门类
 *
 * @author
 * @version 2.1
 * @since JDK1.5
 */
public interface SysDeptService {
  /**
   * 根据vo条件查询部门列表
   *
   * @param SysDept
   * @return List
   * @exception Exception
   */
  @SuppressWarnings("rawtypes")
  public List getSysDeptList(SysDept vo) throws Exception;


  /**
   * 获取所有部门
   *
   * @param SysDept
   * @return List
   * @exception Exception
   */
  @SuppressWarnings("rawtypes")
  public List getAllDeptList() throws Exception;



  /**
   * 获取子部门列表
   *
   * @param Integer
   * @return List
   * @exception Exception
   */
  @SuppressWarnings("rawtypes")
  public List getSonDeptList(Integer upId) throws Exception;



  /**
   * 新增部门
   *
   * @param SysDept
   * @return Integer
   * @exception Exception
   */
  public Integer addSysDept(SysDept vo) throws Exception;



  /**
   * 根据deptId查询部门
   * 
   * @param Integer
   * @return SysDept
   * @exception Exception
   */
  public SysDept getSysDeptDetail(Integer id) throws Exception;



  /**
   * 修改部门
   * 
   * @param SysDept
   * @return SysDept
   * @exception Exception
   */
  public SysDept updateSysDept(SysDept sysDept) throws Exception;



  /**
   * 删除所选部门
   * 
   * @param String[]
   * @return void
   * @exception Exception
   */
  public void deleteSysDept(String[] sysDept) throws Exception;



  /**
   * 根据部门id删除部门
   * 
   * @param Integer
   * @return void
   * @exception Exception
   */
  public void deleteSysDeptById(Integer deptId) throws Exception;



  /**
   * 获取除自己外的所有
   *
   * @param Integer
   * @return List
   * @exception Exception
   */
  @SuppressWarnings("rawtypes")
  public List getOtherList(Integer deptId) throws Exception;
}
