package com.pltfm.cms.service;

import java.util.List;

public interface CmsImageService {

    /**
     * 返回所有的图片列表
     */
    public List imageList(Integer siteId) throws Exception;

    /**
     * 上传图片
     */
    public void uploadImage(List uploadFileList, Integer siteId) throws Exception;

    /**
     * 根据名字删除图片
     */
    public boolean deleteImgByName(String fileName, Integer siteId) throws Exception;

    /**
     * 根据名字修改图片（重新上传）
     */
    public String viewImgByName(String fileName, Integer siteId) throws Exception;
}
