package com.pltfm.app.service;


import java.util.List;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.vobject.ProdAppraise;
import com.kmzyc.commons.page.Page;

/**
 * @author tanyunxing
 */
public interface ProdAppraiseService {

    /**
     * 列表显示数据
     *
     * @param page
     * @param prodApp
     * @throws ServiceException
     */
    void searchByPage(Page page, ProdAppraise prodApp) throws ServiceException;

    /**
     * 根据主键获取评价对象
     *
     * @return
     * @throws ServiceException
     */
    ProdAppraise findByPrimaryKey(Long prodAppId) throws ServiceException;

    /**
     * 更行评价的审核状态
     *
     * @param record
     * @throws ServiceException
     */
    int updateCheckStatus(ProdAppraise record) throws ServiceException;

    /**
     * 根据主键删除
     *
     * @param prodAppId
     * @throws ServiceException
     */
    void deleteByPrimaryKey(Long[] prodAppId) throws ServiceException;

    /**
     * 查找已通过审核的评价
     *
     * @param page
     * @param prodApp
     * @throws ServiceException
     */
    void searchReplyPage(Page page, ProdAppraise prodApp) throws ServiceException;

    /**
     * 新增评价
     *
     * @param prodApp
     * @throws ServiceException
     */
    void saveProductAppraise(ProdAppraise prodApp) throws ServiceException;

    /**
     * Excel导入数据
     *
     * @param list
     * @return
     * @throws ServiceException
     */
    void insertDataForExcel(List<ProdAppraise> list) throws ServiceException;

    /**
     * 查找已存在的用户
     *
     * @param checkUsers
     * @return
     * @throws ServiceException
     */
    List<String> findIsExistUserName(List<String> checkUsers) throws ServiceException;

    /**
     * 查找sku的评论  每页10条
     *
     * @param isTimeSort true时间排序/false评分排序
     * @param sort       desc降序/asc升序
     * @param skuId      sku的id
     * @param pageNumber 评论页数
     * @return
     * @throws ServiceException
     */
    List<Map> queryProductAppraise(Boolean isTimeSort, String sort, Long skuId, int pageNumber) throws ServiceException;

    /**
     * 统计当前skuId下的评论条数
     *
     * @param skuId sku的id
     * @return
     * @throws ServiceException
     */
    int countProductAppraise(Long skuId) throws ServiceException;

}
