package com.kmzyc.b2b.service;

import com.kmzyc.b2b.model.QueryResult;

public interface QryOrderOnLineService {

  public QueryResult queryByOrder(String orderCode);

}
