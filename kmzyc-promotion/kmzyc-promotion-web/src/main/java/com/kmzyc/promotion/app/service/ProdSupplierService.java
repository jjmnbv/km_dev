package com.kmzyc.promotion.app.service;

import java.util.List;

import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.vobject.ProdSupplier;

/**
 * 供应商业务接口
 * 
 * @author tanyunxing
 * 
 */
public interface ProdSupplierService {

    /**
     * 获取单个供应商
     * 
     * @param id 供应商编号
     * @return
     */
    public ProdSupplier findById(Long id) throws Exception;

    /**
     * 删除供应商
     * 
     * @param id 供应商编号
     * @return
     */
    public int deleteObject(Long id) throws Exception;

    /**
     * 更新供应商
     * 
     * @param prod 要更新的供应商对象
     * @return
     */
    public int updateObject(ProdSupplier prod) throws Exception;

    /**
     * 分页查询
     * 
     * @param proSuppliser
     * @param page
     */

    public void searchPage(ProdSupplier proSuppliser, Page page) throws Exception;

    /**
     * 添加供货商
     * 
     * @param prodSupplier 供货商对象
     * @throws Exception 异常
     */

    public void addSupplier(ProdSupplier prodSupplier) throws Exception;

    /**
     * 根据供应商 名称 查询出主键值不为 id 的结果
     * 
     * @param name
     * @param id
     * @return
     * @throws Exception
     */
    public List<ProdSupplier> selectProdSuppliersByNameNotId(String name, Long id) throws Exception;

}
