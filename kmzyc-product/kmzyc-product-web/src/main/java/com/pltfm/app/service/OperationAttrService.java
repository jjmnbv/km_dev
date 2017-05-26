package com.pltfm.app.service;

import java.util.List;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.vobject.OperationAttr;
import com.kmzyc.commons.page.Page;

public interface OperationAttrService {

    /**
     * @param page
     * @param attr
     * @throws ServiceException
     */
    void searchPage(Page page, OperationAttr attr) throws ServiceException;


    /**
     * 查询运营属性列表
     *
     * @return List<OperationAttr> 运营属性列表
     * @throws ServiceException
     */
    List<OperationAttr> queryOperationAttrList() throws ServiceException;

    /**
     * 查询运营属性
     *
     * @param operationAttrId 运营属性ID
     * @return OperationAttr 运营属性对象
     * @throws ServiceException
     */
    OperationAttr queryOperationAttr(Long operationAttrId) throws ServiceException;

    /**
     * 更新运营属性
     *
     * @param record
     * @throws ServiceException
     */
    void updateOperationAttr(OperationAttr record) throws ServiceException;

    /**
     * 删除运营属性
     *
     * @param operationAttrId
     * @return
     * @throws ServiceException
     */
    String delete(String[] operationAttrId) throws ServiceException;

    /**
     * 新增
     *
     * @param record
     * @throws ServiceException
     */
    void saveOperationAttr(OperationAttr record) throws ServiceException;

    /**
     * 检查是否重命名
     *
     * @param name
     * @param operationAttrId
     * @return
     * @throws ServiceException
     */
    boolean checkRepeatName(String name, Long operationAttrId) throws ServiceException;

}
