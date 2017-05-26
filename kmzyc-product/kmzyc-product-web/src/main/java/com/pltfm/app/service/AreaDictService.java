package com.pltfm.app.service;

import java.util.List;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.vobject.AreaDict;

/**
 * 地区接口
 *
 * @author tanyunxing
 */
public interface AreaDictService {

    /**
     * 获取所有的省
     *
     * @return
     */
    List<AreaDict> findAllProvince() throws ServiceException;

    /**
     * 获取某个省下的市
     *
     * @param provinceId
     * @return
     */
    List<AreaDict> findCityByProvince(Integer provinceId) throws ServiceException;

    /**
     * 根据主键获取地区
     *
     * @param id
     * @return
     */
    AreaDict findSingleById(Integer id) throws ServiceException;

    /**
     * 根据市级supperId获取上级省份
     *
     * @param id
     * @return
     * @throws ServiceException
     */
    AreaDict findProvinceBySupplierId(Integer id) throws ServiceException;

}