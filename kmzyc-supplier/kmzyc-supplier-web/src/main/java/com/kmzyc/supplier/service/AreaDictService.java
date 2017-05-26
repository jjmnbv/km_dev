package com.kmzyc.supplier.service;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.vobject.AreaDict;

import java.util.List;

/**
 * 地区接口
 */
public interface AreaDictService {

    /**
     * 获取某个省下的市
     *
     * @param provinceId
     * @return
     */
    List<AreaDict> findCityByProvince(Integer provinceId) throws ServiceException;

    /**
     * 获取某个市下的区县
     *
     * @param cityId
     * @return
     */
    List<AreaDict> findCountyByProvince(Integer cityId) throws ServiceException;

    /**
     * 根据主键获取地区
     *
     * @param id
     * @return
     */
    AreaDict findSingleById(Integer id) throws ServiceException;

}