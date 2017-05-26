package com.pltfm.cms.service;

import com.kmzyc.commons.page.Page;
import com.pltfm.cms.vobject.CmsPage;
import com.pltfm.cms.vobject.CmsTheme;

import java.sql.SQLException;
import java.util.List;

public interface CmsPageService {
    /**
     * 分页查询
     */
    public Page getAllPage(CmsPage cmsPage, Page page) throws Exception;

    public List selectBpageId(CmsPage cmsPage) throws Exception;

    //修改供应商PAGE模板
    public boolean upOthTop(CmsPage cmsPage, Integer pid) throws Exception;

    //添加供应商paot头部
    public boolean addShopTheme(CmsPage cmsPage, CmsTheme cmsTheme) throws Exception;

    /**
     * 新增CmsPage
     */
    public boolean addPage(CmsPage cmsPage) throws Exception;

    //修改供应商PAGE模板
    public boolean upPageTheme(CmsPage cmsPage, CmsTheme cmsTheme) throws Exception;

    //添加供应商paot头部
    public Integer addOthTop(CmsPage cmsPage, Integer pid) throws Exception;

    /**
     * 套餐发布类型查询页面
     */
    public List<CmsPage> selectByPrimaryPublishType2(CmsPage page)
            throws Exception;

    /**
     * 新增专题页
     */
    public boolean addTheme(CmsPage cmsPage, CmsTheme cmsTheme) throws Exception;

    /**
     * 供应商根据发布类型查询页面
     *
     * @param publishType 发布类型
     */
    public List<CmsPage> selectByPrimaryPublishType1(CmsPage page)
            throws Exception;

    /**
     * 返回CmsPage对象
     */
    public CmsPage getCmsPageById(Integer cmsPageId) throws Exception;

    /**
     * 删除某个CmsPage对象
     */
    public int delCmsPage(CmsPage cmsPage) throws Exception;

    /**
     * 更新CmsPage对象
     */
    public int updateCmsPage(CmsPage cmsPage) throws Exception;

    /**
     * 根据ID删除CmsPage对象
     */
    public int delCmsPageById(Integer pageId) throws Exception;

    /**
     * 删除选中的CmsPage对象
     */
    public int delAllPage(String ids) throws Exception;

    /**
     * 根据name查询
     */
    public List getByName(CmsPage cmsPage) throws Exception;

    /**
     * 根据发布类型查询页面
     *
     * @param publishType 发布类型
     */
    public List<CmsPage> selectByPrimaryPublishType(CmsPage page)
            throws Exception;

    // 备份线上数据
    public void insertPageOnline(CmsPage page) throws Exception;

    /**
     * 检查名字的惟一性
     */
    public String checkName(CmsPage cmsPage) throws Exception;

    /**
     * 输出路径验证（联接活动、页面、资讯、广告）
     */
    public Integer byPage(CmsPage cmsPage) throws Exception;

    public List findAllPage() throws SQLException;
}
