package com.pltfm.app.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.dao.SuppliersInfoDAO;
import com.pltfm.app.dataobject.SuppliersInfoDO;
import com.pltfm.app.service.SuppliersInfoService;

@Component(value = "suppliersInfoService")
public class SuppliersInfoServiceImpl implements SuppliersInfoService {

  @Resource(name = "suppliersInfoDAO")
  private SuppliersInfoDAO suppliersInfoDAO;

  @Override
  public Page queryForPageList(SuppliersInfoDO suppliersInfoDO, Page page) throws Exception {
    // TODO Auto-generated method stub

    int totalNum = suppliersInfoDAO.countSuppliersInfoDOByExample(suppliersInfoDO);
    if (totalNum != 0) {
      page.setRecordCount(totalNum);
      // 设置查询开始结束索引
      suppliersInfoDO.setStartIndex(page.getStartIndex());
      suppliersInfoDO.setEndIndex(page.getStartIndex() + page.getPageSize());
      page.setDataList(suppliersInfoDAO.findListByExample(suppliersInfoDO));
    } else {
      page.setRecordCount(0);
      page.setDataList(null);
    }
    return page;
  }

  public SuppliersInfoDAO getSuppliersInfoDAO() {
    return suppliersInfoDAO;
  }

  public void setSuppliersInfoDAO(SuppliersInfoDAO suppliersInfoDAO) {
    this.suppliersInfoDAO = suppliersInfoDAO;
  }
}
