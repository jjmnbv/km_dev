package com.kmzyc.b2b.service;

import java.util.List;
import java.util.Map;

public interface OrderPvSyncRemoteService {
  /** 同步PV值 */
  public Map orderPvSync(List orderCode);
}
