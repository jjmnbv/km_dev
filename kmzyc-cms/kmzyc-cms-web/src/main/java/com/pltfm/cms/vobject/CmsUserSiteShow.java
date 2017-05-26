package com.pltfm.cms.vobject;

import java.io.Serializable;
import java.util.List;

/**
 * 用户所拥有站点信息展示
 *
 * @author zhl
 * @since 2013-11-21
 */
public class CmsUserSiteShow implements Serializable {
    private Integer userId;
    private String username;
    private List<CmsSite> siteList;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<CmsSite> getSiteList() {
        return siteList;
    }

    public void setSiteList(List<CmsSite> siteList) {
        this.siteList = siteList;
    }
}
