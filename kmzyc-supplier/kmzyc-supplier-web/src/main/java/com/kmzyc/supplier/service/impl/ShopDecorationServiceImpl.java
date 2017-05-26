package com.kmzyc.supplier.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.cms.remote.service.RemoteSupplierParseService;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.supplier.dao.ShopMainDAO;
import com.kmzyc.supplier.model.ShopMain;
import com.kmzyc.supplier.service.ShopDecorationService;

@Service("shopDecorationService")
public class ShopDecorationServiceImpl implements ShopDecorationService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
	private ShopMainDAO shopMainDAO;

    @Resource
    private RemoteSupplierParseService remoteSupplierParseService;

	@Override
	public String viewTemplet(ShopMain shopMain, String dataType) throws ServiceException {
        try {
            remoteSupplierParseService.remoteAddTheme(shopMain,Integer.parseInt(dataType));
            return remoteSupplierParseService.remoteViweParse(shopMain,Integer.parseInt(dataType));
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor=ServiceException.class)
	public String publishTemplet(ShopMain shopMain, String dataType) throws ServiceException {
        try {
            remoteSupplierParseService.remoteAddTheme(shopMain,Integer.parseInt(dataType));
            String url = remoteSupplierParseService.remoteParse(shopMain,Integer.parseInt(dataType));
            logger.info("发布模板CMS返回链接={}.", url);
            int count = 0;
            if(StringUtils.isNotBlank(url)){
                shopMain.setDefaultDomainUrl(url);
                count = shopMainDAO.updateDefaultDomainUrl(shopMain);
            }
            logger.info("修改结果为={}.", count);
            if(count>0 && StringUtils.isNotBlank(url)) {
                return url;
            }else{
                return null;
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
}
