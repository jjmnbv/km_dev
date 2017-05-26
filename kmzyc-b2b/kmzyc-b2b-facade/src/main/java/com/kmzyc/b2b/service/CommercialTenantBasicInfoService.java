package com.kmzyc.b2b.service;

import com.kmzyc.b2b.model.CommercialTenantBasicInfo;
import com.kmzyc.framework.exception.ServiceException;

public interface CommercialTenantBasicInfoService {

  public CommercialTenantBasicInfo queryByLoginId(Long loginID) throws ServiceException;

  public CommercialTenantBasicInfo queryByLoginIdCache(Long loginID);

}
