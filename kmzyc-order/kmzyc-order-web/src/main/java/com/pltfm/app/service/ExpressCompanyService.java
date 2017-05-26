package com.pltfm.app.service;

import java.util.List;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.express.entities.ExpressCompany;

public interface ExpressCompanyService {

  /**
   * 查询所有已启用的物流公司
   * 
   * @return
   * @throws ServiceException
   */
  List<ExpressCompany> queryAllEnableExpressCompany() throws ServiceException;

  /**
   * 根据快递公司编码获取快递公司基本信息
   * 
   * @param code
   * @return
   * @throws ServiceException
   */
  ExpressCompany selectExpressCompanyByCode(String code) throws ServiceException;

}
