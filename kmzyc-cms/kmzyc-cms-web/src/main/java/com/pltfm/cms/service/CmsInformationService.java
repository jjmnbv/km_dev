package com.pltfm.cms.service;

import com.kmzyc.commons.page.Page;
import com.pltfm.cms.vobject.CmsInformation;
import com.pltfm.cms.vobject.CmsInformationType;
import com.pltfm.cms.vobject.UploadFile;

import java.sql.SQLException;
import java.util.List;

public interface CmsInformationService {

    /**
     * 分页查询文章数据信息
     *
     * @param cmsWindowData 文章数据信息实体
     * @throws Exception 异常
     * @return 返回值
     */
    public Page queryForPage(CmsInformation cmsInformation, Page page) throws Exception;

    /**
     * 查询文章类型数据信息
     *
     * @throws Exception 异常
     * @return 返回值
     */
    public List inforTypeList(CmsInformationType inforType) throws Exception;

    /**
     * 查询多个文章类型数据信息
     *
     * @throws Exception 异常
     * @return 返回值
     */
    public List inforTypesList(List dataIds) throws Exception;

    /**
     * 添加文章数据信息
     *
     * @param cmsinformation 文章数据信息实体
     * @throws Exception 异常
     */
    public void add(UploadFile fileImage, CmsInformation cmsInformation) throws Exception;

    /**
     * 根据ID查文章数据信息
     *
     * @param cmsinformation 文章数据id
     * @throws Exception 异常
     * @return 返回值
     */
    public CmsInformation byid(boolean flag, Integer inforid) throws Exception;


    /**
     * 修改文章数据信息
     *
     * @param cmsinformation 文章数据信息实体
     * @throws Exception 异常
     * @return 返回值
     */
    public Integer update(UploadFile fileImage, CmsInformation cmsInformation) throws Exception;

    /**
     * 删除文章数据信息
     *
     * @param cmsinformation 文章数据ID
     * @throws Exception 异常
     * @return 返回值
     */
    public void delete(Integer inforid) throws Exception;

    /**
     * 查文章数据output
     *
     * @throws Exception 异常
     * @return 返回值
     */
    public int byinfor(CmsInformation cmsInformation) throws Exception;

    /**
     * 名字惟一性
     */
    public Integer countByInforname(CmsInformation cmsInformation) throws Exception;

    public Page queryStaticHolidayList(CmsInformation cmsInformation, Page page) throws Exception;

    public void addStaticHoliday(CmsInformation cmsInformation) throws Exception;

    public Integer updateStaticHoliday(CmsInformation cmsInformation) throws Exception;

    public CmsInformation queryStaticHolidayPage(Integer inforid) throws Exception;

    public void staticHolidayDelDatas(List dataIds) throws Exception;

    public Integer deleteInfoByType(String val) throws SQLException;


}
