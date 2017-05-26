package com.kmzyc.cms.remote.service;

import java.util.List;
import java.util.Map;

import com.pltfm.app.vobject.ProductRelation;

public interface RemoteDulitaocanService {
    /**
     * 套餐的发布接口
     * 
     * @author gwl 返回 Map key套餐ID 值为URL
     */
    @SuppressWarnings("rawtypes")
    public Map remoteParse(List<ProductRelation> ProductRelationList) throws Exception;

    /**
     * 套餐的预览接口
     * 
     * @author gwl 返回 URL
     */
    public String remoteViewdulitaocanParse(ProductRelation p) throws Exception;
}
