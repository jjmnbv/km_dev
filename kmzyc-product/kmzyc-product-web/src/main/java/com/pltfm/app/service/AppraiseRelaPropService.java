package com.pltfm.app.service;

import com.kmzyc.commons.exception.ServiceException;

/**
 * 评价属性与物理类目关系业务接口
 *
 * @author tanyunxing
 */
public interface AppraiseRelaPropService {

    /**
     * 批量保存
     *
     * @throws ServiceException
     */
    void saveBatch(String[] categoryIds, String[] appraisePropIds) throws ServiceException;

    /**
     * 取消绑定的属性
     *
     * @param categoryIds
     * @throws ServiceException
     */
    void unBindProp(String[] categoryIds) throws ServiceException;

}
