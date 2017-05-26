package com.pltfm.app.service;

import com.kmzyc.commons.exception.ServiceException;

public interface ReturnFundForKJService {
  
  public String returnFund(String orderCode) throws ServiceException;

}
