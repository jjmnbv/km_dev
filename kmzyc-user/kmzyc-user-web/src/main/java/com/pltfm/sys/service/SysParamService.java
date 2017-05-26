package com.pltfm.sys.service;

import java.util.List;

public interface SysParamService {
  /**
   * 根据平台编号和组名称查询该组中的所有参数 List (SysParam)
   * 
   * @param String,String
   * @return List
   * @exception Exception
   */
  public List getSysParamList(String sysCd, String paramGp) throws Exception;


}
