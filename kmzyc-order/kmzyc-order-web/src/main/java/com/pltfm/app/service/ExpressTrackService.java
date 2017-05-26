package com.pltfm.app.service;

import java.util.List;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;

@SuppressWarnings("unchecked")
public interface ExpressTrackService {

  List queryExpressTrackListByPage(Map<String, String> paramMap) throws ServiceException;

  int queryExpressTrackCount(Map<String, String> paramMap) throws ServiceException;

}
