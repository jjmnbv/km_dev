package com.pltfm.app.service;

import java.sql.SQLException;
import java.util.List;

import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.vobject.SectionsDetail;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;

public interface SectionsDetailService {

    void searchPage(Page page, SectionsDetail sectionsDetail) throws ServiceException;

    /**
     * 新增栏目详情信息
     *
     * @param sectionsdetail 栏目信息实体
     * @return 分页栏目列表
     * @throws Exception 异常
     */
    void saveSectionsDetail(SectionsDetail sectionsdetail) throws ServiceException;

    /**
     * 根据栏目详情Id查询栏目实体
     *
     * @param sectionDetailId 栏目信息实体
     * @return
     * @throws Exception 异常
     */
    SectionsDetail querySectionsDetailById(String sectionDetailId) throws ServiceException;

    /**
     * 根据序列号进行栏目详情排序
     *
     * @param stno            栏目信息实体
     * @param sectionDetailId
     * @return
     * @throws Exception 异常
     */
    void updateSectionDetailByStno(String[] stno, String[] sectionDetailId) throws ServiceException;

    /**
     * 批量保存栏目明细
     *
     * @param sectionsDetailList
     * @return
     * @throws ServiceException
     */
    ResultMessage batchInsertSectionsDetail(List<SectionsDetail> sectionsDetailList) throws ServiceException;

    /**
     * 批量删除栏目明细
     *
     * @param sectionsDetailIds
     * @return
     * @throws ServiceException
     */
    ResultMessage batchDeleteSectionsDetail(List<Long> sectionsDetailIds) throws ServiceException;

    /**
     * 批量修改栏目明细
     *
     * @param sectionsDetailList
     * @return
     * @throws ServiceException
     */
    ResultMessage batchUpdateSectionsDetail(List<SectionsDetail> sectionsDetailList) throws ServiceException;

    /**
     * 根据产品ID查询栏目明细列表
     *
     * @param productId
     * @return
     * @throws SQLException
     */
    List<SectionsDetail> selectSectionsDetailByProductId(Long productId) throws ServiceException;
}
