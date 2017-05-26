package com.pltfm.cms.service.impl;

import com.kmzyc.zkconfig.ConfigurationUtil;
import com.kmzyc.commons.page.Page;
import com.pltfm.cms.dao.CmsPromotionAttrDAO;
import com.pltfm.cms.parse.PathConstants;
import com.pltfm.cms.service.CmsPromotionAttrService;
import com.pltfm.cms.util.FileOperateUtils;
import com.pltfm.cms.vobject.CmsPromotionAttr;
import com.pltfm.cms.vobject.UploadFile;

import org.springframework.stereotype.Component;

import java.util.Date;

import javax.annotation.Resource;

@Component("cmsPromotionAttrService")
public class CmsPromotionAttrServiceImp implements CmsPromotionAttrService {
    @Resource(name = "cmsPromotionAttrDAO")
    private CmsPromotionAttrDAO cmsPromotionAttrDAO;


    @Override
    public Integer add(CmsPromotionAttr cmsp) throws Exception {

        return cmsPromotionAttrDAO.insert(cmsp);
    }

    @Override
    public Page queryForPage(CmsPromotionAttr cmsp, Page page) throws Exception {

        if (page == null) {
            page = new Page();
        }
        if (cmsp == null) {
            cmsp = new CmsPromotionAttr();
        }
        // 根据条件获取广告信息总条数countByExample
        int totalNum = cmsPromotionAttrDAO.countByExample(cmsp);
        if (totalNum != 0) {
            page.setRecordCount(totalNum);
            // 设置查询开始结束索引
            cmsp.setStartIndex(page.getStartIndex());
            cmsp.setEndIndex(page.getStartIndex() + page.getPageSize());
            page.setDataList(cmsPromotionAttrDAO.queryForPage(cmsp));
        } else {
            page.setRecordCount(0);
            page.setDataList(null);
        }
        return page;
    }


    @Override
    public int addpromotion(UploadFile file, UploadFile file2,
                            UploadFile file3, UploadFile file4, CmsPromotionAttr cmsp)
            throws Exception {
        //String uploadPath =  templateConfig.get("configPath");
        String imagePath = ConfigurationUtil.getString("imagePut");
        //文件写入
        String path = PathConstants.advPath(null);
        //String path=templateConfig.get("configPath")+"\\adv";
        FileOperateUtils.checkAndCreateDirs(path);
        //path=path+"\\temp";
        path = PathConstants.advTempPath(null);
        FileOperateUtils.checkAndCreateDirs(path);
        if (file != null) {
            String name = FileOperateUtils.upload(file, imagePath);
            cmsp.setPromotionTinyIcon(name);  //小图
        }
        if (file2 != null) {
            String name = FileOperateUtils.upload(file2, imagePath);
            cmsp.setPromotionBigIcon(name);//大图
        }
        if (file3 != null) {
            String name = FileOperateUtils.upload(file3, imagePath);
            cmsp.setPromotionImgFirst(name);//活动展示图一
        }
        if (file4 != null) {
            String name = FileOperateUtils.upload(file4, imagePath);
            cmsp.setPromotionImgSecond(name);//活动展示图二
        }
        int s = cmsPromotionAttrDAO.insert(cmsp);

        return s;
    }

    @Override
    public CmsPromotionAttr byid(Integer id) throws Exception {
        String imageOut = ConfigurationUtil.getString("imageOut");
        CmsPromotionAttr cmspro = cmsPromotionAttrDAO.byid(id);

        if (cmspro.getPromotionTinyIcon() != null) {
            cmspro.setPromotionTinyIcon(imageOut + "/" + cmspro.getPromotionTinyIcon());
        }
        if (cmspro.getPromotionBigIcon() != null) {
            cmspro.setPromotionBigIcon(imageOut + "/" + cmspro.getPromotionBigIcon());
        }
        if (cmspro.getPromotionImgFirst() != null) {
            cmspro.setPromotionImgFirst(imageOut + "/" + cmspro.getPromotionImgFirst());
        }
        if (cmspro.getPromotionImgSecond() != null) {
            cmspro.setPromotionImgSecond(imageOut + "/" + cmspro.getPromotionImgSecond());
        }
        return cmspro;
    }


    @Override
    public int updatePromotion(UploadFile file, UploadFile file2, UploadFile file3, UploadFile file4, CmsPromotionAttr cmsp)
            throws Exception {
        String imagePath = ConfigurationUtil.getString("imagePut");
        // String path=templateConfig.get("configPath")+"\\adv";
        String path = PathConstants.advPath(null);
        FileOperateUtils.checkAndCreateDirs(path);
        //path=path+"\\temp";
        path = PathConstants.advTempPath(null);
        FileOperateUtils.checkAndCreateDirs(path);
        if (file != null) {
            String[] fname = file.getName().split("\\.");
            String imagetype = "." + fname[fname.length - 1];
            String imagename = new Date().getTime() + imagetype;
            FileOperateUtils.upload(file, imagePath);
          /*   System.out.println("上传文件名"+file.getName());
	        System.out.println("上传文件类型"+file.getContentType());  */
            cmsp.setPromotionTinyIcon(imagename);  //小图
        }
        if (file2 != null) {
            // 设置上传文件目录
            String[] fname = file2.getName().split("\\.");
            String imagetype = "." + fname[fname.length - 1];
            String imagename = new Date().getTime() + imagetype;
            FileOperateUtils.upload(file2, imagePath);
	      /*   System.out.println("上传文件名"+file.getName());  
	        System.out.println("上传文件类型"+file.getContentType());  */
            cmsp.setPromotionBigIcon(imagename);//大图
        }
        if (file3 != null) {
            // 设置上传文件目录
            String[] fname = file3.getName().split("\\.");
            String imagetype = "." + fname[fname.length - 1];
            String imagename = new Date().getTime() + imagetype;
            FileOperateUtils.upload(file3, imagePath);
	        /* System.out.println("上传文件名"+file3.getName());  
	        System.out.println("上传文件类型"+file3.getContentType());  */
            cmsp.setPromotionImgFirst(imagename);//活动展示图一
        }
        if (file4 != null) {
            // 设置上传文件目录
            String[] fname = file4.getName().split("\\.");
            String imagetype = "." + fname[fname.length - 1];
            String imagename = new Date().getTime() + imagetype;
            FileOperateUtils.upload(file4, imagePath);
	       /*  System.out.println("上传文件名"+file4.getName());  
	        System.out.println("上传文件类型"+file4.getContentType());  */
            cmsp.setPromotionImgSecond(imagename);//活动展示图二
        }
        return cmsPromotionAttrDAO.updateByExampleSelective(cmsp);
    }


    @Override
    public int delete(Integer id) throws Exception {

        return cmsPromotionAttrDAO.deleteById(id);
    }


    public CmsPromotionAttrDAO getCmsPromotionAttrDAO() {
        return cmsPromotionAttrDAO;
    }

    public void setCmsPromotionAttrDAO(CmsPromotionAttrDAO cmsPromotionAttrDAO) {
        this.cmsPromotionAttrDAO = cmsPromotionAttrDAO;
    }


}
