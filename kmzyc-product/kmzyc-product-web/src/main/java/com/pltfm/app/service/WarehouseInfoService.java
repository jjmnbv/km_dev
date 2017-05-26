package com.pltfm.app.service;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.vobject.WarehouseInfo;
import com.kmzyc.commons.page.Page;

import java.util.List;

public interface WarehouseInfoService {

    /**
     * 分页 查找数据
     *
     * @param page
     * @param warehouseInfo
     */
    void searchPage(Page page, WarehouseInfo warehouseInfo) throws ServiceException;

    /**
     * 添加仓库
     *
     * @param warehouseInfo
     * @return
     * @throws ServiceException
     */
    Long insert(WarehouseInfo warehouseInfo) throws ServiceException;

    WarehouseInfo findWarehouseInfoById(Long id) throws ServiceException;

    ResultMessage checkWarehouseInfoByName(String name) throws ServiceException;

    ResultMessage checkWarehouseNameByModify(String name, Long warehouseId) throws ServiceException;

    List<WarehouseInfo> findAllWarehouseInfo(String status) throws ServiceException;

    boolean updateWarehouseInfoById(WarehouseInfo warehouseInfo) throws ServiceException;

    /**
     * 启用仓库
     *
     * @param warehouseInfoList
     * @return
     * @throws ServiceException
     */
    boolean startWarehouse(List<Object> warehouseInfoList) throws ServiceException;

    /**
     * 停用仓库
     *
     * @param warehouseInfoList
     * @return
     * @throws ServiceException
     */
    boolean stopWarehouse(List<Object> warehouseInfoList) throws ServiceException;

    /**
     * 将已启用的仓库装入Map
     *
     * @param warehouseInfoList
     * @return
     */
    void setWarehouseMap(List<Object> warehouseInfoList, String status) throws ServiceException;

    /**
     * 将新增并且已启用的仓库装入Map
     *
     * @param id
     * @return
     */
    void setWarehouseMapByKey(Long id) throws ServiceException;

    /**
     * 对于修改单个仓库实体启用仓库装入Map
     *
     * @param warehouseInfo
     */
    void setWarehouseMapByEntity(WarehouseInfo warehouseInfo) throws ServiceException;

    /**
     * 停用仓库时，检查与单据的关联关系
     *
     * @return
     * @throws ServiceException
     */
    ResultMessage checkWarehouseInfoForStop(List<Long> warehouseIdList) throws ServiceException;
}
