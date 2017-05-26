package com.pltfm.cms.vobject;


public class CmsPageWindowQry {
    private Integer pageId;

    //页面名称
    private String pageName;
    //页面路径
    private String pagePath;
    //页面路径输出路径
    private String pageOutputPath;

    private Integer winId;
    //窗口名称
    private String winName;
    //窗口路径
    private String winPath;

    private Integer tempId;
    //模版名称
    private String tempName;
    //模版路径
    private String tempPath;


    public String getPageName() {
        return pageName;
    }


    public void setPageName(String pageName) {
        this.pageName = pageName;
    }


    public String getPagePath() {
        return pagePath;
    }


    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }


    public String getPageOutputPath() {
        return pageOutputPath;
    }


    public void setPageOutputPath(String pageOutputPath) {
        this.pageOutputPath = pageOutputPath;
    }


    public String getWinName() {
        return winName;
    }


    public void setWinName(String winName) {
        this.winName = winName;
    }


    public String getWinPath() {
        return winPath;
    }


    public void setWinPath(String winPath) {
        this.winPath = winPath;
    }


    public String getTempName() {
        return tempName;
    }


    public void setTempName(String tempName) {
        this.tempName = tempName;
    }


    public String getTempPath() {
        return tempPath;
    }


    public void setTempPath(String tempPath) {
        this.tempPath = tempPath;
    }


    public Integer getPageId() {
        return pageId;
    }


    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }


    public Integer getWinId() {
        return winId;
    }


    public void setWinId(Integer winId) {
        this.winId = winId;
    }


    public Integer getTempId() {
        return tempId;
    }


    public void setTempId(Integer tempId) {
        this.tempId = tempId;
    }

}