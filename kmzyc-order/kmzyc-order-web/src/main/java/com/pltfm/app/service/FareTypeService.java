package com.pltfm.app.service;

import java.util.List;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.entities.FareTypeWithBLOBs;

@SuppressWarnings("unchecked")
public interface FareTypeService {

  /**
   * 查询
   */
  public List list() throws ServiceException;

  /**
   * 保存
   */
  public void save(FareTypeWithBLOBs example) throws ServiceException;

  /**
   * 修改
   */
  public void update(FareTypeWithBLOBs example) throws ServiceException;

  /**
   * 删除
   */
  public void delete(Long typeId) throws ServiceException;

  /**
   * id查询
   */
  public FareTypeWithBLOBs getById(Long typeId) throws ServiceException;

  /**
   * 检查序号是否存在
   * 
   * @return
   */
  public Boolean checkTypeId(Long typeId) throws ServiceException;

  /**
   * 检查名称是否存在
   * 
   * @return
   */
  public Boolean checkName(Long typeId, String name) throws ServiceException;

  /**
   * 检查适用站点是否存在
   * 
   * @return
   */
  public Boolean checkSite(Long typeId, String site) throws ServiceException;
}
