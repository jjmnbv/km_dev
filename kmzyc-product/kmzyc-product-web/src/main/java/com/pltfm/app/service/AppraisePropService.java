package com.pltfm.app.service;

import java.util.List;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.vobject.AppraiseProp;
import com.kmzyc.commons.page.Page;

public interface AppraisePropService {

    /**
     * 分页显示输数据
     *
     * @param page
     * @param prop
     * @throws ServiceException
     */
    void searchPage(Page page, AppraiseProp prop, List<Long> notPropIds) throws ServiceException;

    /**
     * 获取某些属性
     *
     * @param propIds
     * @return
     * @throws ServiceException
     */
    List<AppraiseProp> findBySomePropIds(List<Long> propIds) throws ServiceException;

    /**
     * 根据类目Id获取评价属性
     *
     * @param cateId
     * @return
     * @throws ServiceException
     */
    List<AppraiseProp> selectByCategoryId(Long cateId) throws ServiceException;

    /**
     * 获取单个属性对象
     *
     * @param propId
     * @return
     * @throws ServiceException
     */
    AppraiseProp findByPrimaryKey(Long propId) throws ServiceException;

    /**
     * 更新单个对象
     *
     * @param prop
     * @throws ServiceException
     */
    void updatePropAndValues(AppraiseProp prop) throws ServiceException;

    /**
     * 添加
     *
     * @param prop
     * @throws ServiceException
     */
    void addPropAndValues(AppraiseProp prop) throws ServiceException;

    /**
     * 删除
     *
     * @param propId
     * @throws ServiceException
     */
    void deletePropAndValues(Long[] propId) throws ServiceException;

}