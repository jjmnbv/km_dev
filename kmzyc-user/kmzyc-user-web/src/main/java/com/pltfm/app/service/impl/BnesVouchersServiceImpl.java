package com.pltfm.app.service.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.pltfm.app.dataobject.BnesVouchersDO;
import com.pltfm.app.service.BnesVouchersService;
import com.pltfm.app.vobject.BnesVouchersQuery;

/**
 * 数据访问对象接口
 * 
 * @since 2013-07-26
 */
public class BnesVouchersServiceImpl implements BnesVouchersService {

  /**
   * 插入数据
   * 
   * @param bnesVouchersDO
   * @return 插入数据的主键
   */
  public void insertBnesVouchersDO(BnesVouchersDO bnesVouchersDO) throws DataAccessException {

  }

  /**
   * 统计记录数
   * 
   * @param bnesVouchersDO
   * @return 查出的记录数
   */
  public Integer countBnesVouchersDOByExample(BnesVouchersDO bnesVouchersDO)
      throws DataAccessException {
    return null;
  }

  /**
   * 统计记录数
   * 
   * @param bnesVouchersQuery
   * @return 查出的记录数
   */
  public Integer countBnesVouchersQueryByExample(BnesVouchersQuery bnesVouchersQuery)
      throws DataAccessException {
    return null;
  }

  /**
   * 获取对象列表
   * 
   * @param bnesVouchersDO
   * @return 对象列表
   */
  public List<BnesVouchersDO> findListByExample(BnesVouchersDO bnesVouchersDO)
      throws DataAccessException {
    return null;
  }

  /**
   * 获取对象列表
   * 
   * @param bnesVouchersQuery
   * @return 对象列表
   */
  public List<BnesVouchersQuery> findListByExample(BnesVouchersQuery bnesVouchersQuery)
      throws DataAccessException {
    return null;
  }

}
