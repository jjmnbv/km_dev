package com.pltfm.cms.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.pltfm.app.util.DateTimeUtils;
import com.kmzyc.commons.page.Page;
import com.pltfm.cms.dao.CmsAdvDAO;
import com.pltfm.cms.dao.CmsSiteDAO;
import com.pltfm.cms.dao.CmsSupplierAdcolumnDAO;
import com.pltfm.cms.parse.PathConstants;
import com.pltfm.cms.service.CmsAdcolumnDataService;
import com.pltfm.cms.service.CmsAdvService;
import com.pltfm.cms.util.FileOperateUtils;
import com.pltfm.cms.vobject.CmsAdcolumn;
import com.pltfm.cms.vobject.CmsAdv;
import com.pltfm.cms.vobject.CmsSite;
import com.pltfm.cms.vobject.CmsSupplierAdcolumn;
import com.pltfm.cms.vobject.UploadFile;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

@Component("cmsAdvService")
public class CmsAdvServiceImp implements CmsAdvService {
	private static Logger logger = LoggerFactory.getLogger(CmsAdvServiceImp.class);

    @Resource(name = "cmsAdvDAO")
    private CmsAdvDAO cmsAdvDAO;
    /*@Resource(name="templateConfig")
    private Map<String, String> templateConfig;*/

    @Resource(name = "cmsAdcolumnDataService")
    private CmsAdcolumnDataService cmsAdcolumnDataService;
    @Resource(name = "cmsSupplierAdcolumnDAO")
    private CmsSupplierAdcolumnDAO cmsSupplierAdcolumnDAO;
    @Resource(name = "cmsSiteDAO")
    private CmsSiteDAO cmsSiteDAO;


    /*  查  */
    @Override
    public Page queryForPage(CmsAdv cmsAd, Page page)
            throws Exception {

        if (page == null) {
            page = new Page();
        }
        if (cmsAd == null) {
            cmsAd = new CmsAdv();
        }
        // 根据条件获取广告信息总条数countByExample
        int totalNum = cmsAdvDAO.countByExample(cmsAd);

        if (totalNum != 0) {
            page.setRecordCount(totalNum);
            // 设置查询开始结束索引
            cmsAd.setStartIndex(page.getStartIndex());
            cmsAd.setEndIndex(page.getStartIndex() + page.getPageSize());
            page.setDataList(cmsAdvDAO.queryForPage(cmsAd));
        } else {
            page.setRecordCount(0);
            page.setDataList(null);
        }
        return page;
    }

    /* 页面添加广告 */
    @Override
    public int addCmsAdv(UploadFile file, UploadFile file2, UploadFile file3, UploadFile file4, UploadFile file5, CmsAdv cmsAdv) throws Exception {
        String imagePath = PathConstants.imagePut();
        //文件写入
        String path = PathConstants.advPath(cmsAdv.getSiteId());
        FileOperateUtils.checkAndCreateDirs(path);
        path = PathConstants.advTempPath(cmsAdv.getSiteId());
        FileOperateUtils.checkAndCreateDirs(path);
        if (file != null) {
            String name = FileOperateUtils.upload(file, imagePath);
            cmsAdv.setUploadAdvFile(name);
        }
        if (file2 != null) {
            String name = FileOperateUtils.upload(file2, imagePath);
            cmsAdv.setUploadAdvFile2(name);
        }
        if (file3 != null) {
            String name = FileOperateUtils.upload(file3, imagePath);
            cmsAdv.setUploadAdvFile3(name);
        }
        if (file4 != null) {
            String name = FileOperateUtils.upload(file4, imagePath);
            cmsAdv.setUploadAdvFile4(name);
        }
        if (file5 != null) {
            String name = FileOperateUtils.upload(file5, imagePath);
            cmsAdv.setP3(name);
        }
        int s = cmsAdvDAO.insert(cmsAdv);

        File contentfile = new File(path + "/" + cmsAdv.getAdvId() + ".html");
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(contentfile), "UTF-8");
        BufferedWriter bWriter = new BufferedWriter(writer);
        bWriter.write(cmsAdv.getContent());
        bWriter.close();
        return s;
    }


    /*供应商界面的添加广告 */
    @Override
    public int addSupplierCmsAdv(CmsAdv cmsAdv) throws Exception {
    /*	 String imagePath =PathConstants.imagePut();
            //文件写入
		    String path=PathConstants.advPath(cmsAdv.getSiteId());
			File files=new File(path);
			if(!files.exists())
			{
				files.mkdir();
			}
			path=PathConstants.advTempPath(cmsAdv.getSiteId());
			files=new File(path);
			if(!files.exists())
			{
				files.mkdir();
			}
	*/
        cmsAdv.setStatus(0);
        cmsAdv.setCreateDate(DateTimeUtils
                .getCalendarInstance().getTime());

        int s = cmsAdvDAO.insert(cmsAdv);

        //获取/adv/supplier至advTemp下
        Integer adcolumnId = cmsAdv.getAdcolumnId();
        CmsSupplierAdcolumn supplierAdcolumn = new CmsSupplierAdcolumn();
        supplierAdcolumn.setAdcolumnId(adcolumnId);
        supplierAdcolumn = cmsSupplierAdcolumnDAO.byAdcolumnId(supplierAdcolumn);

        String path = PathConstants.advSupplierPath(cmsAdv.getSiteId());
        File files = new File(path, supplierAdcolumn.getSupplierAdcolumnId() + ".html");
        if (files.exists()) {
            cmsAdv.setContent(FileUtils.readFileToString(files, "utf-8"));
        }

        path = PathConstants.advTempPath(cmsAdv.getSiteId());
        File contentfile = new File(path + "/" + cmsAdv.getAdvId() + ".html");
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(contentfile), "UTF-8");
        BufferedWriter bWriter = new BufferedWriter(writer);
        bWriter.write(cmsAdv.getContent());
        bWriter.close();
        return s;
    }

    /**
     * 根据ID查找AdvObject
     */

    @Override
    public CmsAdv byid(Integer id) throws Exception {
        CmsAdv cmsadv = cmsAdvDAO.byid(id);
        CmsSupplierAdcolumn cmsSupplierAdcolumn = new CmsSupplierAdcolumn();
        cmsSupplierAdcolumn.setAdcolumnId(cmsadv.getAdcolumnId());

        cmsSupplierAdcolumn = cmsSupplierAdcolumnDAO.byAdcolumnId(cmsSupplierAdcolumn);
        String imageOut = PathConstants.cmsPicPath();


        //区分供应商图片
        String supplierPicPath = PathConstants.cmsSupplierPicPath();
        if (null != cmsSupplierAdcolumn && cmsSupplierAdcolumn.getSupplierAdcolumnId() > 0) {
            imageOut = supplierPicPath;
        }


        if (cmsadv.getUploadAdvFile() != null) {

            cmsadv.setUploadAdvFile(imageOut + "/" + cmsadv.getUploadAdvFile());
        }
        if (cmsadv.getUploadAdvFile2() != null) {
            cmsadv.setUploadAdvFile2(imageOut + "/" + cmsadv.getUploadAdvFile2());
        }
        if (cmsadv.getUploadAdvFile3() != null) {
            cmsadv.setUploadAdvFile3(imageOut + "/" + cmsadv.getUploadAdvFile3());
        }
        if (cmsadv.getUploadAdvFile4() != null) {
            cmsadv.setUploadAdvFile4(imageOut + "/" + cmsadv.getUploadAdvFile4());
        }
        if (cmsadv.getP3() != null) {
            cmsadv.setP3(imageOut + "/" + cmsadv.getP3());
        }
        if (cmsadv.getUploadAdvFile6() != null) {
            cmsadv.setUploadAdvFile6(imageOut + "/" + cmsadv.getUploadAdvFile6());
        }
        String path = PathConstants.advTempPath(cmsadv.getSiteId());
//	    System.out.println(cmsadv.getUploadAdvFile());

        File file = new File(path, cmsadv.getAdvId() + ".html");

        String content = FileUtils.readFileToString(file, "utf-8");
        CmsAdcolumn cmsAdcolumn = cmsAdcolumnDataService.byid(cmsadv.getAdcolumnId());
        cmsadv.setAdname(cmsAdcolumn.getName());
        cmsadv.setContent(content);
        return cmsadv;

    }

    //供应商的广告查询(广告文件在产品服务器上)
    public CmsAdv byId(Integer id) throws Exception {
        CmsAdv cmsadv = cmsAdvDAO.byid(id);
        CmsSite site = cmsSiteDAO.selectByPrimaryKey(cmsadv.getSiteId());
        cmsadv.setSiteCode(site.getEngName());
        String path = PathConstants.advTempPath(cmsadv.getSiteId());

        String content = FileOperateUtils.reader(path + "/" + cmsadv.getAdvId() + ".html");
        CmsAdcolumn cmsAdcolumn = cmsAdcolumnDataService.byid(cmsadv.getAdcolumnId());
        cmsadv.setAdname(cmsAdcolumn.getName());
        cmsadv.setContent(content);

        return cmsadv;
    }


    //供应商的广告查询(广告文件在产品服务器上)
    public CmsAdv byIdToSupplier(Integer id) throws Exception {
        CmsAdv cmsadv = cmsAdvDAO.byid(id);
        String imageOut = PathConstants.supplierImagePut();
        if (cmsadv.getUploadAdvFile() != null) {
            cmsadv.setUploadAdvFile(imageOut + "/" + cmsadv.getUploadAdvFile());
        }
        if (cmsadv.getUploadAdvFile2() != null) {
            cmsadv.setUploadAdvFile2(imageOut + "/" + cmsadv.getUploadAdvFile2());
        }
        if (cmsadv.getUploadAdvFile3() != null) {
            cmsadv.setUploadAdvFile3(imageOut + "/" + cmsadv.getUploadAdvFile3());
        }
        if (cmsadv.getUploadAdvFile4() != null) {
            cmsadv.setUploadAdvFile4(imageOut + "/" + cmsadv.getUploadAdvFile4());
        }
        if (cmsadv.getP3() != null) {
            cmsadv.setP3(imageOut + "/" + cmsadv.getP3());
        }
        String path = PathConstants.advTempPath(cmsadv.getSiteId());
//	    System.out.println(cmsadv.getUploadAdvFile());
        String content = FileOperateUtils.reader(path + "/" + cmsadv.getAdvId() + ".html");
        CmsAdcolumn cmsAdcolumn = cmsAdcolumnDataService.byid(cmsadv.getAdcolumnId());
        cmsadv.setAdname(cmsAdcolumn.getName());
        cmsadv.setContent(content);

        return cmsadv;
    }


    /**
     * @return Ajax 广告位List 11/21 11:13 修改
     */
    @Override
    public List adcolumnList(int i) throws Exception {
        // TODO Auto-generated method stub
        return cmsAdvDAO.adcolumnList(i);
    }

    /**
     * 根据ID删除广告信息
     *
     * @return 影响行数
     */
    @Override
    public Integer delete(Integer id) throws Exception {
        // TODO Auto-generated method stub
        return cmsAdvDAO.deleteByPrimaryKey(id);
    }


    public void delSelAdv(Integer[] ids) throws SQLException {
        if (null != ids && ids.length > 0) {
            for (int i = 0; i < ids.length; i++) {
                cmsAdvDAO.deleteByPrimaryKey(ids[i]);
            }
        }
    }

    /**
     * @return 修改广告位
     */
    @Override
    public Integer update(UploadFile file, UploadFile file2, UploadFile file3, UploadFile file4, UploadFile file5, UploadFile file6, CmsAdv cmsAdv) throws Exception {

        String imagePath = PathConstants.imagePut();

        String path = PathConstants.advPath(cmsAdv.getSiteId());
        FileOperateUtils.checkAndCreateDirs(path);
        //path=path+"\\temp";
        path = PathConstants.advTempPath(cmsAdv.getSiteId());
        FileOperateUtils.checkAndCreateDirs(path);
        if (file != null) {
           /*String [] fname=file.getName().split("\\.");
           String imagetype="."+fname[fname.length-1];
           String imagename=new Date().getTime()+imagetype;*/
            String imagename = FileOperateUtils.upload(file, imagePath);
	      /*   System.out.println("上传文件名"+file.getName());  
	        System.out.println("上传文件类型"+file.getContentType());  */
            cmsAdv.setUploadAdvFile(imagename);
        }
        if (file2 != null) {
            // 设置上传文件目录
	       /*String [] fname=file2.getName().split("\\.");
	       String imagetype="."+fname[fname.length-1];
           String imagename=new Date().getTime()+imagetype;*/
            String imagename = FileOperateUtils.upload(file2, imagePath);
	      /*   System.out.println("上传文件名"+file.getName());  
	        System.out.println("上传文件类型"+file.getContentType());  */
            cmsAdv.setUploadAdvFile2(imagename);
        }
        if (file3 != null) {
            // 设置上传文件目录
	    
	       /*String [] fname=file3.getName().split("\\.");
	       String imagetype="."+fname[fname.length-1];
           String imagename=new Date().getTime()+imagetype;*/
            String imagename = FileOperateUtils.upload(file3, imagePath);
	        /* System.out.println("上传文件名"+file3.getName());  
	        System.out.println("上传文件类型"+file3.getContentType());  */
            cmsAdv.setUploadAdvFile3(imagename);
        }
        if (file4 != null) {
            // 设置上传文件目录
	      
	       /*String [] fname=file4.getName().split("\\.");
	       String imagetype="."+fname[fname.length-1];
           String imagename=new Date().getTime()+imagetype;*/
            String imagename = FileOperateUtils.upload(file4, imagePath);
	       /*  System.out.println("上传文件名"+file4.getName());  
	        System.out.println("上传文件类型"+file4.getContentType());  */
            cmsAdv.setUploadAdvFile4(imagename);
        }
        if (file5 != null) {
            // 设置上传文件目录

	       /*String [] fname=file5.getName().split("\\.");
	       String imagetype="."+fname[fname.length-1];
           String imagename=new Date().getTime()+imagetype;*/
            String imagename = FileOperateUtils.upload(file5, imagePath);
	       /*  System.out.println("上传文件名"+file4.getName());
	        System.out.println("上传文件类型"+file4.getContentType());  */
            cmsAdv.setP3(imagename);
        }
        if (file6 != null) {
            String imagename = FileOperateUtils.upload(file6, imagePath);
            cmsAdv.setUploadAdvFile6(imagename);
        }

        if (cmsAdv.getContent() != null) {
            File contentfile = new File(path + "/" + cmsAdv.getAdvId() + ".html");
            OutputStreamWriter writer = null;
            BufferedWriter bWriter = null;
            try {
                writer = new OutputStreamWriter(new FileOutputStream(contentfile), "UTF-8");
                bWriter = new BufferedWriter(writer);
                bWriter.write(cmsAdv.getContent());
            } finally {
                IOUtils.closeQuietly(bWriter);
                IOUtils.closeQuietly(writer);
            }


        }
        return cmsAdvDAO.updateByPrimaryKeySelective(cmsAdv);
    }



    /**
     * @return 修改供应商广告
     */
    @Override
    public Integer updateSupplierAdv(CmsAdv cmsAdv) throws SQLException {
        // TODO Auto-generated method stub

//		 String imagePath = PathConstants.imagePut();
        String path = PathConstants.advPath(cmsAdv.getSiteId());
        FileOperateUtils.checkAndCreateDirs(path);

        //path=path+"\\temp";
        path = PathConstants.advTempPath(cmsAdv.getSiteId());
        FileOperateUtils.checkAndCreateDirs(path);


        if (cmsAdv.getContent() != null) {
            File contentfile = new File(path + "/" + cmsAdv.getAdvId() + ".html");
            OutputStreamWriter writer = null;
            BufferedWriter bWriter = null;
            try {
                writer = new OutputStreamWriter(new FileOutputStream(contentfile), "UTF-8");

                bWriter = new BufferedWriter(writer);
                if (null != cmsAdv.getContent()) {
                    bWriter.write(cmsAdv.getContent());
                }
            } catch (IOException e) {
            	logger.error("CmsAdvServiceImp.updateSupplierAdv异常：" + e.getMessage(), e);
            } finally {
                IOUtils.closeQuietly(bWriter);
                IOUtils.closeQuietly(writer);
            }
        }

        cmsAdv.setModifyDate(DateTimeUtils
                .getCalendarInstance().getTime());
        return cmsAdvDAO.updateByPrimaryKeySelective(cmsAdv);
    }
	
	/* 获取基础模板 */
	/*	@Override
	public String Adv_Temp(int id) throws Exception {
	 String TempPath =new PathConstants().advPath();
	   if(id==1){
		   TempPath=TempPath+"/"+"index_adv_single.html";
	   }
	   if(id==2){
		   TempPath=TempPath+"/"+"index_adv_carousel.html";
	   }
	   if(id==3){
		   TempPath=TempPath+"/"+"index_adv_flash.html";
	   }
	  File file=new File(TempPath);
	   String content=null;
	   if(file.exists()){
		  content=org.apache.commons.io.FileUtils.readFileToString(file);
	   }
		return content;
	}
*/

    public CmsAdvDAO getCmsAdvDAO() {
        return cmsAdvDAO;
    }

    public void setCmsAdvDAO(CmsAdvDAO cmsAdvDAO) {
        this.cmsAdvDAO = cmsAdvDAO;
    }
/*	public Map<String, String> getTemplateConfig() {
		return templateConfig;
	}
	public void setTemplateConfig(Map<String, String> templateConfig) {
		this.templateConfig = templateConfig;
	}*/


    //返回广告列表
    public List queryAdvList(CmsAdv cmsAdv) throws SQLException {
        return cmsAdvDAO.queryAdvList(cmsAdv);
    }

    //自动检查广告是否过期
    public List checkAllAdv() throws SQLException {
        return cmsAdvDAO.checkAllAdv();
    }

    @Override
    public CmsAdv findAdv(Integer id) throws Exception {
        //String imageOut =new PathConstants().imageOut();

        CmsAdv cmsadv = cmsAdvDAO.byid(id);

        String path = PathConstants.advTempPath(cmsadv.getSiteId());
        //    System.out.println(cmsadv.getUploadAdvFile());
        String content = FileOperateUtils.reader(path + "/" + cmsadv.getAdvId() + ".html");
        cmsadv.setContent(content);
        return cmsadv;
    }

    @Override
    public Integer countByAdvname(CmsAdv cmsAdv) throws Exception {
        // TODO Auto-generated method stub
        return cmsAdvDAO.countByAdvname(cmsAdv);
    }

    /**
     * 根据广告查询条件获取记录数
     *
     * @param cmsAdv 广告
     * @return 记录数
     */
    @Override
    public Integer countByAdv(CmsAdv cmsAdv) throws Exception {
        return cmsAdvDAO.countByAdv(cmsAdv);
    }


    public CmsAdcolumnDataService getCmsAdcolumnDataService() {
        return cmsAdcolumnDataService;
    }

    public void setCmsAdcolumnDataService(
            CmsAdcolumnDataService cmsAdcolumnDataService) {
        this.cmsAdcolumnDataService = cmsAdcolumnDataService;
    }

    public CmsSupplierAdcolumnDAO getCmsSupplierAdcolumnDAO() {
        return cmsSupplierAdcolumnDAO;
    }

    public void setCmsSupplierAdcolumnDAO(
            CmsSupplierAdcolumnDAO cmsSupplierAdcolumnDAO) {
        this.cmsSupplierAdcolumnDAO = cmsSupplierAdcolumnDAO;
    }

    public CmsSiteDAO getCmsSiteDAO() {
        return cmsSiteDAO;
    }

    public void setCmsSiteDAO(CmsSiteDAO cmsSiteDAO) {
        this.cmsSiteDAO = cmsSiteDAO;
    }

    @Override
    public List queryUpdateAdvTempList(CmsAdcolumn cmsAdcolumn)
            throws SQLException {

        return cmsAdvDAO.queryUpdateAdvTempList(cmsAdcolumn);
    }


}
