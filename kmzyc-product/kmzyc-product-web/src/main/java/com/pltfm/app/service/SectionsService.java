package com.pltfm.app.service;

import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.vobject.Sections;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;

/**
 * 栏目管理业务逻辑接口
 *
 * @author humy
 * @since 2013-7-9
 */
public interface SectionsService {

    void searchPage(Page page, Sections sections) throws ServiceException;

    /**
     * 添加栏目
     *
     * @param sections 栏目基本信息
     * @return 返回值
     * @throws Exception 异常
     */
    void addSections(Sections sections) throws ServiceException;

    /**
     * 删除栏目
     *
     * @param sectionsId 栏目基本信息
     * @return 返回值
     * @throws Exception 异常
     */
    ResultMessage delSections(String sectionsId) throws ServiceException;

    /**
     * 修改栏目
     *
     * @param sections 栏目基本信息
     * @return 返回值
     * @throws Exception 异常
     */
    void updateSections(Sections sections) throws ServiceException;

    /**
     * 预览栏目
     *
     * @param sectionsId
     * @return
     */
    Sections preSectionsById(Long sectionsId) throws ServiceException;

    /**
     * 校验sectionscode是否重复
     *
     * @param sectionsCode
     * @return
     */
    int checkSectionsRpeat(String sectionsCode) throws ServiceException;

    /**
     * 校验sectionsName是否重复
     *
     * @param sectionsName
     * @return
     * @throws Exception
     */
    ResultMessage checkSectionsName(String sectionsName) throws ServiceException;

    /**
     * 校验identification是否重复
     *
     * @param identification
     * @return
     * @throws Exception
     */
    ResultMessage checkIdentification(String identification) throws ServiceException;

    ResultMessage checkSectionsNameByModify(String name, Long sectionsId) throws ServiceException;

    ResultMessage checkIdentificationByModify(String identification, Long sectionsId) throws ServiceException;
}
