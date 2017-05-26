package com.pltfm.app.service;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.dataobject.SuppliersInfoDO;

public interface SuppliersInfoService {

  public Page queryForPageList(SuppliersInfoDO suppliersInfoDO, Page page) throws Exception;

}
