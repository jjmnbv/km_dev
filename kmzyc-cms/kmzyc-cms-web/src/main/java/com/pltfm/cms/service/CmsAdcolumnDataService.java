package com.pltfm.cms.service;

import com.kmzyc.commons.page.Page;
import com.pltfm.cms.vobject.CmsAdcolumn;
import com.pltfm.cms.vobject.CmsAdcolumnExample;

import java.sql.SQLException;
import java.util.List;


public interface CmsAdcolumnDataService {

    public List<CmsAdcolumn> list(CmsAdcolumnExample example) throws Exception;

    /**
     * 添加广告位数据信息
     *
     * @throws Exception 异常
     * @return 返回值
     */
    public Integer addCmsAdcolumn(CmsAdcolumn cmsAdcolumn) throws Exception;

    /**
     * 分页查询广告位数据信息
     *
     * @param cmsWindowData 广告位数据信息实体
     * @throws Exception 异常
     * @return 返回值
     */
    public Page queryForPage(CmsAdcolumn cmsAdcolumn, Page page) throws Exception;

    /**
     * 广告位数据信息
     *
     * @param cmsWindowData 广告位ID
     * @throws Exception 异常
     * @return 返回值
     */
    public CmsAdcolumn byid(Integer Advid) throws Exception;

    /**
     * 修改广告位数据信息
     *
     * @param cmsWindowData 广告位ID
     * @throws Exception 异常
     * @return 返回值
     */
    public Integer update(CmsAdcolumn cmsAdcolumn) throws Exception;

    /**
     * 删除广告位数据信息
     *
     * @param cmsWindowData 广告位ID
     * @throws Exception 异常
     * @return 返回值
     */
    public Integer delete(Integer Advid) throws Exception;

    /**
     * 删除广告位数据信息前的查询
     *
     * @throws Exception 异常
     * @return 返回值
     */
    public Integer byAdId(Integer Advid) throws Exception;

    //返回所有广告位列表
    public List queryAdcolumnList() throws SQLException;

    /**
     * 广告位条件查询
     *
     * @throws Exception 异常
     * @return 返回值
     */
    public Integer byAdvcolumn(CmsAdcolumn cmsAdcolumn) throws Exception;


    /**
     * 广告位条件查询
     *
     * @throws Exception 异常
     * @return 返回值 name 存在条数
     */
    public Integer countByAdcolumnname(CmsAdcolumn cmsAdcolumn) throws Exception;

    public List queryAdcolumnListBySupplier(CmsAdcolumn cmsAdcolumn) throws SQLException;


}
