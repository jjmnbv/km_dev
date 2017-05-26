package com.pltfm.app.service;

import java.math.BigDecimal;
import java.util.Map;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.vobject.ReserverBill;

public interface ReserverBillService {
  /**
   * 分页查询账单
   * 
   * @param bill
   * @return
   * @throws Exception
   */
  Page queryReserverBillAll(Page page, ReserverBill bill) throws Exception;

  /**
   * 删除账单
   * 
   * @param billId
   * @return
   * @throws Exception
   */
  int deleteByPrimaryKey(BigDecimal billId) throws Exception;

  /**
   * 添加账单
   * 
   * @param record
   * @throws Exception
   */
  void insert(Map<String, Object> mapPra) throws Exception;



  /**
   * 查询账单
   * 
   * @param billId
   * @return
   * @throws Exception
   */
  ReserverBill selectByPrimaryKey(ReserverBill reserverBill) throws Exception;


  /**
   * 修改账单
   * 
   * @param record
   * @return
   * @throws Exception
   */
  int updateByPrimaryKeySelective(ReserverBill record) throws Exception;
}
