package com.pltfm.cms.service;

import com.kmzyc.commons.page.Page;
import com.pltfm.cms.vobject.CmsAdcolumn;
import com.pltfm.cms.vobject.CmsAdv;
import com.pltfm.cms.vobject.UploadFile;

import java.sql.SQLException;
import java.util.List;

public interface CmsAdvService {
    /**
     * 分页查询广告数据信息
     *
     * @param cmsWindowData 广告位数据信息实体
     * @throws Exception 异常
     * @return 返回值
     */
    public Page queryForPage(CmsAdv cmsAdcolumn, Page page) throws Exception;

    /**
     * 添加广告数据信息
     *
     * @param cmsWindowData 广告数据信息实体
     * @throws Exception 异常
     * @return 返回值
     */
    public int addCmsAdv(UploadFile file, UploadFile file2, UploadFile file3, UploadFile file4, UploadFile file5, CmsAdv cmsAdv) throws Exception;


    public List adcolumnList(int i) throws Exception;

    public CmsAdv byid(Integer id) throws Exception;

    public Integer update(UploadFile file, UploadFile file2, UploadFile file3, UploadFile file4, UploadFile file5, UploadFile file6, CmsAdv cmsAdv) throws Exception;

    public Integer delete(Integer id) throws Exception;

    //返回广告列表
    public List queryAdvList(CmsAdv cmsAdv) throws SQLException;

    //自动检查广告是否过期
    public List checkAllAdv() throws SQLException;

    //自动检查广告类型对模版
    //  public String   Adv_Temp(int id) throws Exception;

    public CmsAdv findAdv(Integer id) throws Exception;

    public Integer countByAdvname(CmsAdv cmsAdv) throws Exception;

    public int addSupplierCmsAdv(CmsAdv cmsAdv) throws Exception;

    public CmsAdv byId(Integer id) throws Exception;

    /**
     * @return 修改供应商广告
     */

    public Integer updateSupplierAdv(CmsAdv cmsAdv) throws Exception;

    public CmsAdv byIdToSupplier(Integer id) throws Exception;

    /**
     * 根据广告查询条件获取记录数
     *
     * @param cmsAdv 广告
     * @return 记录数
     */
    public Integer countByAdv(CmsAdv cmsAdv) throws Exception;

    public void delSelAdv(Integer[] ids) throws SQLException;

    public List queryUpdateAdvTempList(CmsAdcolumn cmsAdcolumn)
            throws SQLException;
}
