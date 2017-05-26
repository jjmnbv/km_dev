package com.pltfm.app.remote.impl;

import java.lang.reflect.Field;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.kmzyc.order.remote.SysConstantsRemoteService;
import com.pltfm.app.util.SysConstants;

@Service("sysConstantsRemoteService")
@Scope("singleton")
public class SysConstantsRemoteServiceImpl implements SysConstantsRemoteService {
  private Logger log = Logger.getLogger(SysConstantsRemoteServiceImpl.class);

  @Override
  public Object getConstantsValue(String key) {
    Object obj = null;
    try {
      Field field = SysConstants.class.getField(key);
      obj = field.get(field.getName());
    } catch (Exception e) {
      log.error("接口调用系统常量异常！", e);
    }
    return obj;
  }
}
