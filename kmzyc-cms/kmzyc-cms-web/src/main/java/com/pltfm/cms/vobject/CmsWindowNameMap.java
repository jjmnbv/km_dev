package com.pltfm.cms.vobject;

import java.io.Serializable;

/**
 * 窗口数据实体类
 *
 * @author gy
 * @since 2013-9-3
 */
public class CmsWindowNameMap implements Serializable {
    private static final long serialVersionUID = -7900596805019265235L;
    private String categoryWindowName;
    private String searchCategoryName;


    public String getCategoryWindowName() {
        return categoryWindowName;
    }

    public void setCategoryWindowName(String categoryWindowName) {
        this.categoryWindowName = categoryWindowName;
    }

    public String getSearchCategoryName() {
        return searchCategoryName;
    }

    public void setSearchCategoryName(String searchCategoryName) {
        this.searchCategoryName = searchCategoryName;
    }


}