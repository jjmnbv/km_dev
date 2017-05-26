package com.kmzyc.b2b.dao;

import java.util.List;

import com.pltfm.app.vobject.ViewSkuAttr;

public interface ViewSkuAttrDao {
  public List<ViewSkuAttr> findBySkuId(Long skuId);
}
