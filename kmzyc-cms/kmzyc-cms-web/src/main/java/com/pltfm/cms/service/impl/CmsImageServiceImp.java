package com.pltfm.cms.service.impl;

import com.pltfm.cms.parse.PathConstants;
import com.pltfm.cms.service.CmsImageService;
import com.pltfm.cms.util.FileOperateUtils;
import com.pltfm.cms.vobject.UploadFile;

import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Component("cmsImageService")
public class CmsImageServiceImp implements CmsImageService {

    /**
     * 读取属性文件内容
     */
/*	@Resource(name = "templateConfig")
    private Map<String, String> templateConfig;*/
    @Override
    public boolean deleteImgByName(String fileName, Integer siteId) throws Exception {
        boolean result = false;//结果标志
        String path = PathConstants.imageOutPath(siteId);//图片目录路径.
        //删除文件
        String imagePath = path + "/" + fileName;
        File file = new File(imagePath);
        if (file.exists()) {
            result = file.delete();
        }
        return result;
    }

    /**
     * 图片集合
     */
    @Override
    public List imageList(Integer siteId) throws Exception {
        String path = PathConstants.imageOutPath(siteId);//图片目录路径

        //查找该目录下所有文件
        return orderByDate(path, siteId);
    }

    //按日期排序
    public List orderByDate(String fliePath, Integer siteId) {
        List list = null;
        String viewPath = PathConstants.imgViewPath(siteId);//图片访问 目录路径
        File file = new File(fliePath);
        File[] fs = file.listFiles();
        if (fs != null) {
            Arrays.sort(fs, (f1, f2) -> f1.lastModified() - f2.lastModified() == 0 ? 0 :
                    f1.lastModified() - f2.lastModified() > 0 ? 1 : -1);
            list = new ArrayList();
            for (int i = fs.length - 1; i > -1; i--) {
                if (i >= fs.length - 20) {
                    UploadFile f = new UploadFile();
                    f.setName(fs[i].getName());
                    f.setPath(viewPath + "/" + fs[i].getName());
                    list.add(f);
                }
            }
        }

        return list;
    }

    @Override
    public String viewImgByName(String fileName, Integer siteId) throws Exception {
        String path = PathConstants.imgViewPath(siteId);//图片访问 目录路径
        return path + "/" + fileName;
    }

    @Override
    public void uploadImage(List uploadFileList, Integer siteId) throws Exception {
        String path = PathConstants.imageOutPath(siteId);//图片目录路径
        //图片上传
        for (int i = 0; i < uploadFileList.size(); i++) {
            UploadFile uploadFile = (UploadFile) uploadFileList.get(i);
            FileOperateUtils.uploadFile(uploadFile, path);
        }
    }

//	@Override
//	public Page queryForPage(Integer siteId, Page page) throws Exception {
//		
//		return page;
//	}


}
