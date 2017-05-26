package com.pltfm.app.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.express.entities.ExpressCompany;
import com.kmzyc.express.util.ErrorCode;
import com.pltfm.app.dao.ExpressCompanyDAO;
import com.pltfm.app.service.ExpressCompanyService;

@Service("expressCompanyService")
@Scope("singleton")
public class ExpressCompanyServiceImpl implements ExpressCompanyService {
  private static Logger logger = Logger.getLogger(ExpressCompanyServiceImpl.class);

  @Resource
  private ExpressCompanyDAO expressCompanyDAO;

  @Override
  public List<ExpressCompany> queryAllEnableExpressCompany() throws ServiceException {
    try {
      return expressCompanyDAO.queryAllEnableExpressCompany();
    } catch (Exception e) {
      logger.error("获取可用的物流公司信息异常：" + e.getMessage());
      throw new ServiceException(ErrorCode.EXPRESS_COM_ERR, "获取可用的物流公司信息异常：" + e.getMessage());
    }
  }

  @Override
  public ExpressCompany selectExpressCompanyByCode(String code) throws ServiceException {
    try {
      return expressCompanyDAO.selectExpressCompanyByCode(code);
    } catch (Exception e) {
      logger.error("根据物流公司编码获取物流公司基本信息异常：logisticsCode=" + code + e.getMessage());
      throw new ServiceException(ErrorCode.EXPRESS_COM_ERR, "根据物流公司编码获取物流公司基本信息异常："
          + e.getMessage());
    }
  }


}
