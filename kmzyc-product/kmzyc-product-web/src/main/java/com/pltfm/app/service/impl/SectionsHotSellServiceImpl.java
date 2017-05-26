package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.dao.SectionsDetailDAO;
import com.pltfm.app.service.SectionsHotSellService;
import com.pltfm.app.util.CodeUtils;
import com.pltfm.app.vobject.ProductHotSellInfoCache;
import com.whalin.MemCached.MemCachedClient;

@Service("sectionsHotSellService")
public class SectionsHotSellServiceImpl implements SectionsHotSellService {
	
	@Resource
	private SectionsDetailDAO sectionsDetailDao;
	
	@Resource
	private MemCachedClient memCachedClient;
	
	@PostConstruct
	@Override
	public void injectHotSellZYCProduct() throws ServiceException {
        try {
            List<ProductHotSellInfoCache> list = sectionsDetailDao.findHotSellZYCProducts();
            if(list==null || list.isEmpty()) return;
            List<Map<String,Object>> li = new ArrayList<Map<String,Object>>(list.size());
            for(ProductHotSellInfoCache p : list){
                Map<String,Object> obj =  new HashMap<String,Object>();
                obj.put("productSkuId", p.getProductSkuId());
                obj.put("imgPath", p.getImgPath());
                obj.put("price", p.getPrice());
                obj.put("productName", p.getProductName());
                obj.put("productSkuCode", p.getProductSkuCode());
                li.add(obj);
            }

            memCachedClient.set(CodeUtils.HOT_PRODUCTS_ZYC_KEY, li);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }
	
	@PostConstruct
	@Override
    public void injectHotSellB2BProduct() throws ServiceException {
        try {
            List<ProductHotSellInfoCache> list = sectionsDetailDao.findHotSellB2BProducts();
            if(list==null || list.isEmpty()) return;
            List<Map<String,Object>> li = new ArrayList<Map<String,Object>>(list.size());
            for(ProductHotSellInfoCache p : list){
                Map<String,Object> obj =  new HashMap<String,Object>();
                obj.put("productSkuId", p.getProductSkuId());
                obj.put("imgPath", p.getImgPath());
                obj.put("price", p.getPrice());
                obj.put("productName", p.getProductName());
                obj.put("productSkuCode", p.getProductSkuCode());

                li.add(obj);
            }

            memCachedClient.set(CodeUtils.HOT_PRODUCTS_B2B_KEY, li);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

}
