package com.pltfm.app.service;


import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.vobject.AftersaleReturnOrder;
import com.pltfm.sys.model.SysUser;
import com.kmzyc.commons.page.Page;

public interface AftersaleReturnOrderService {

    /**
     * 分页列表
     *
     * @param page
     * @param order
     * @throws Exception
     */
    void searchPage(Page page, AftersaleReturnOrder order) throws ServiceException;

    /**
     * 根据主键获取退货单
     *
     * @param returnId
     * @return
     * @throws Exception
     */
    AftersaleReturnOrder findByPrimaryKey(Long returnId, String handleResult) throws ServiceException;

    /**
     * 更新
     *
     * @param record
     * @throws Exception
     */
    int updateObject(AftersaleReturnOrder record, SysUser user, boolean changeReturnOrder) throws ServiceException;

}