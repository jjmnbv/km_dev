package com.kmzyc.b2b.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kmzyc.b2b.dao.CategorysDao;
import com.kmzyc.b2b.dao.CmsWindowDataDao;
import com.kmzyc.b2b.model.Categorys;
import com.kmzyc.b2b.service.CategorysService;

@Service("categorysService")
public class CategorysServiceImpl implements CategorysService {
    @Resource(name = "categorysDaoImpl")
    private CategorysDao categorysDao;
    @Resource(name = "cmsWindowDataDao")
    private CmsWindowDataDao cmsWindowDataDao;


    // 获取子类目列表
    public List<Categorys> queryCategorysSub(Map map) throws SQLException {

        List<Categorys> categorysList = categorysDao.queryCategorysSub(map);
        return categorysList;
    }

    // 所有商品父类
    public List selectCategoryParent(String categoryIds) throws SQLException {

        return categorysDao.selectCategoryParent(categoryIds);
    }

    // 获取所有窗口

    public List queryCategoryWindow(String windowFormat) throws SQLException {

        List list = cmsWindowDataDao.getWindowData(windowFormat);
        return list;

    }


    public CategorysDao getCategorysDao() {
        return categorysDao;
    }

    public void setCategorysDao(CategorysDao categorysDao) {
        this.categorysDao = categorysDao;
    }

    public CmsWindowDataDao getCmsWindowDataDao() {
        return cmsWindowDataDao;
    }

    public void setCmsWindowDataDao(CmsWindowDataDao cmsWindowDataDao) {
        this.cmsWindowDataDao = cmsWindowDataDao;
    }


}
