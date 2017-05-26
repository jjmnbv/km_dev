package com.pltfm.app.service;

import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.fobject.ShopBrowseInfo;


/**
 * 店铺浏览量业务处理类
 *
 * @author Administrator
 *         20150414
 */
public interface ShopBrowserService {

    /**
     * 调用接口按条件查询店铺浏览量列表
     *
     * @param conditionPara
     * @return
     * @throws ServiceException
     */
    ShopBrowseInfo queryBrowseInfoList(ShopBrowseInfo conditionPara) throws ServiceException;

    /**
     * 依据时间条件查询 折线图所需要的数据
     *
     * @param conditionPara
     * @return
     * @throws ServiceException
     */
    Map<String, String> queryGroupData(ShopBrowseInfo conditionPara) throws ServiceException;

}