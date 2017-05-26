package com.pltfm.cms.service;

import com.pltfm.app.vobject.ProductRelation;
import com.pltfm.cms.vobject.CmsProductRelation;

import java.util.List;

public interface DulitaocanService {
    public List selectRelationDetail(Long relationId) throws Exception;

    public List selectSku(Long productSkuId) throws Exception;

    public List selectRelation(ProductRelation productRelation) throws Exception;

    public List selectImg(Long productSkuId) throws Exception;

    /***
     * 详细套餐
     * */
    public List<CmsProductRelation> selectRelationDetailAll(Long skuId, String webSite) throws Exception;
}
