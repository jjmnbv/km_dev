package com.pltfm.app.service.impl;

import com.pltfm.app.dao.ViewShopMainDAO;
import com.pltfm.app.service.ViewShopMainService;
import com.pltfm.app.vobject.ViewShopMain;

import org.springframework.stereotype.Component;

import java.util.List;

import javax.annotation.Resource;

@Component(value = "viewShopMainService")
public class ViewShopMainServiceImpl implements ViewShopMainService {

    @Resource(name = "viewShopMainDAO")
    public ViewShopMainDAO viewShopMainDAO;

    public List queryShopMianList(ViewShopMain viewShopMain) throws Exception {
        return viewShopMainDAO.queryShopMianList(viewShopMain);
    }

    public ViewShopMainDAO getViewShopMainDAO() {
        return viewShopMainDAO;
    }

    public void setViewShopMainDAO(ViewShopMainDAO viewShopMainDAO) {
        this.viewShopMainDAO = viewShopMainDAO;
    }


}