package com.pltfm.app.service;

import java.util.List;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.vobject.TiedSade;

/**
 * 针对表TIED_SADE 表进行操作
 * 添加sku产品添加关联关系
 *
 * @author Administrator
 */
public interface ProductTiedService {

    /**
     * 根据tiedSade 的主SKU 查出 跟其搭配的  产品的  TiedSade 对象集合
     *
     * @param tiedSade TiedSade 对象
     * @throws ServiceException 异常
     */
    List<TiedSade> findListByExample(TiedSade tiedSade) throws ServiceException;

    /**
     * 根据tiedSade 的主键id 更新 搭配价格
     *
     * @param tiedSade 对象
     * @throws ServiceException 异常
     */
    void updatePriceByKey(TiedSade tiedSade) throws ServiceException;

    /**
     * 根据传进来的主键id数据批删除搭售记录
     *
     * @param list
     * @throws ServiceException
     */
    void delBatchByPrimaryKey(Long[] list) throws ServiceException;

    /**
     * 批量添加搭售产品信息
     *
     * @param list 要搭售的产品
     * @return 添加成功返回1，其他为 不成功
     * @throws ServiceException 异常
     */
    int insertTiedSaleByBatch(List<TiedSade> list) throws ServiceException;

    /**
     * 根据主键值  更新其搭售类型
     *
     * @param tiedSade 传入tiedSade 中的主键id，以及搭售类型
     * @throws ServiceException 异常
     */
    void updateTiedSaleType(TiedSade tiedSade) throws ServiceException;

    /**
     * 根据主键值 查询出一个主产品关系的子产品
     *
     * @param tiedSade
     * @return
     * @throws ServiceException
     */
    List<TiedSade> findConnectListByMainSku(TiedSade tiedSade) throws ServiceException;

    /**
     * 根据主键查询出 关联模块下 主产品的子产品
     *
     * @param tiedSade
     * @return
     * @throws ServiceException
     */
    List<TiedSade> findConnectListByExample(TiedSade tiedSade) throws ServiceException;

}