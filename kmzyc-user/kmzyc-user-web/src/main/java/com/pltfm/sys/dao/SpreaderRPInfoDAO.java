package com.pltfm.sys.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.vobject.SpreaderRPInfoCriteria;

@SuppressWarnings("unchecked")
public interface SpreaderRPInfoDAO {

  /**
   * 查询需要清除的用户红包信息列表
   * 
   * @return
   * @throws ServiceException
   */
  public List<HashMap> selectUserRpInfoList(SpreaderRPInfoCriteria criteria) throws SQLException;

}
