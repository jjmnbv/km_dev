package com.kmzyc.express.dao;

import java.util.List;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.express.entities.ExpressCompany;

@SuppressWarnings("unchecked")
public interface ExpressCompanyDAO {
  /**
   * 查询快递公司
   * 
   * @param paramMap 查询参数
   * @return
   * @throws Exception
   */
  List queryExpressCompanyList(Map<String, String> paramMap) throws Exception;

  /**
   * 根据查询参数查询快递公司数量
   * 
   * @param paramMap
   * @return
   * @throws Exception
   */
  int queryExpressCompanyCount(Map<String, String> paramMap) throws Exception;

  /**
   * 根据快递公司编码获取快递公司基本信息
   * 
   * @param code
   * @return
   * @throws ServiceException
   */
  ExpressCompany selectExpressCompanyByCode(String code) throws Exception;

  /**
   * 查询所有可用物流公司
   * 
   * @return
   * @throws Exception
   */
  public List<ExpressCompany> queryAllEnableExpressCompany() throws Exception;
}
