package com.pltfm.app.service;

import java.util.List;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;

@SuppressWarnings("unchecked")
public interface ExpressLogService {

  List queryExpressLogListByPage(Map<String, String> paramMap) throws ServiceException;

  int queryExpressLogCount(Map<String, String> paramMap) throws ServiceException;

}
