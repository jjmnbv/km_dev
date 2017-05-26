package com.pltfm.cms.service.impl;

import com.pltfm.app.dao.ViewPromotionDAO;
import com.pltfm.app.service.ViewPromotionService;
import com.kmzyc.commons.page.Page;
import com.pltfm.app.vobject.ViewPromotion;
import com.pltfm.cms.dao.CmsPromotionTaskDAO;
import com.pltfm.cms.parse.HtmlBuilder;
import com.pltfm.cms.parse.PathConstants;
import com.pltfm.cms.service.CmsPromotionService;
import com.pltfm.cms.util.FileOperateUtils;
import com.pltfm.cms.vobject.CmsPromotionTask;
import com.pltfm.cms.vobject.UploadFile;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

@Component("cmsPromotionService")
public class CmsPromotionServiceImp implements CmsPromotionService {
    @Resource(name = "cmsPromotionTaskDAO")
    private CmsPromotionTaskDAO cmsPromotionTaskDAO;
    @Resource(name = "htmlBuilder")
    private HtmlBuilder htmlBuilder;
    @Resource(name = "viewPromotionService")
    private ViewPromotionService viewPromotionService;
    /**
     * 活动信息DAO接口
     */
    @Resource(name = "viewPromotionDAO")
    private ViewPromotionDAO viewPromotionDAO;


    @Override
    public Page queryForPage(CmsPromotionTask cmspt, Page page)
            throws Exception {

        if (page == null) {
            page = new Page();
        }
        if (cmspt == null) {
            cmspt = new CmsPromotionTask();
        }

        int totalNum = cmsPromotionTaskDAO.countByExample(cmspt);

        if (totalNum != 0) {
            page.setRecordCount(totalNum);
            // 设置查询开始结束索引
            cmspt.setStartIndex(page.getStartIndex());
            cmspt.setEndIndex(page.getStartIndex() + page.getPageSize());
            page.setDataList(cmsPromotionTaskDAO.queryForPage(cmspt));
        } else {
            page.setRecordCount(0);
            page.setDataList(null);
        }
        return page;
    }

    //过往活动
    public List queryExpirePromotion() throws SQLException {
        return cmsPromotionTaskDAO.queryExpirePromotion();
    }

    public ViewPromotion byId(int id) throws Exception {
        return viewPromotionDAO.selectByPrimaryKey(id);
    }


    @Override
    @Transactional
    public int promotionAdd(UploadFile file, UploadFile file2, UploadFile file3, UploadFile file4, CmsPromotionTask cmspt) throws Exception {
        String path = PathConstants.promotion(cmspt.getSiteId());
        FileOperateUtils.checkAndCreateDirs(path);
        String imagePath = PathConstants.imagePut();
        //文件写入
        String path2 = PathConstants.advPath(cmspt.getSiteId());
        FileOperateUtils.checkAndCreateDirs(path2);
        path2 = PathConstants.advTempPath(cmspt.getSiteId());
        FileOperateUtils.checkAndCreateDirs(path2);
        if (file != null) {
            String name = FileOperateUtils.upload(file, imagePath);
            cmspt.setImagesFile(name); //小图
        }
        if (file2 != null) {
            String name = FileOperateUtils.upload(file2, imagePath);
            cmspt.setImagesFile2(name);//大图
        }
        if (file3 != null) {
            String name = FileOperateUtils.upload(file3, imagePath);
            cmspt.setImagesFile3(name);//活动展示图一
        }
        if (file4 != null) {
            String name = FileOperateUtils.upload(file4, imagePath);
            cmspt.setImagesFile4(name);//活动展示图二
        }
        //如果不选 择活动的话，直接给ID设置为0
        if (null == cmspt.getId()) {
            cmspt.setId(cmsPromotionTaskDAO.getSeqId());
        }
        int s = cmsPromotionTaskDAO.insert(cmspt);
        String outPath = path + "/" + cmspt.getId() + ".html";
        //不再需要发布
        FileOperateUtils.writer(outPath, cmspt.getContent());
        return s;
    }

    @Override
    public CmsPromotionTask cmspbyId(Integer id, Integer siteId) throws Exception {
        //int siteId=(Integer) ActionContext.getContext().getSession().get("siteId");
        CmsPromotionTask cmspt = cmsPromotionTaskDAO.selectByPrimaryKey(id, siteId);
        Map categoryMap = viewPromotionService.getPromotionCategory();
        cmspt.setRuleName((String) categoryMap.get(cmspt.getPromotionTypeId()));
        String path = PathConstants.promotion(cmspt.getSiteId());
        String imageOut = PathConstants.cmsPicPath();
        if (cmspt.getImagesFile() != null) {
            cmspt.setImagesFile(imageOut + "/" + cmspt.getImagesFile());
        }
        if (cmspt.getImagesFile2() != null) {
            cmspt.setImagesFile2(imageOut + "/" + cmspt.getImagesFile2());
        }
        if (cmspt.getImagesFile3() != null) {
            cmspt.setImagesFile3(imageOut + "/" + cmspt.getImagesFile3());
        }
        if (cmspt.getImagesFile4() != null) {
            cmspt.setImagesFile4(imageOut + "/" + cmspt.getImagesFile4());
        }
        File file = new File(path + "/" + id + ".html");
        if (file.exists() == true)//判断该模板文件是否存在
        {
            if (file.isDirectory() == true)
                return cmspt;
            BufferedReader buff = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
            String s = null;
            String content = "";
            while ((s = buff.readLine()) != null) {
                content += s + "\r\n";
            }
            buff.close();
            cmspt.setContent(content);
        }
        return cmspt;
    }

    /**
     * 根据活动id和站点id查询单条活动信息
     */
    @Override
    public CmsPromotionTask cmspByIdAndSiteId(Integer id, Integer siteId) throws Exception {
        CmsPromotionTask cmspt = cmsPromotionTaskDAO.selectByPrimaryKey(id, siteId);
        Map categoryMap = viewPromotionService.getPromotionCategory();
        cmspt.setRuleName((String) categoryMap.get(cmspt.getPromotionTypeId()));
        String path = PathConstants.promotion(cmspt.getSiteId());
        String imageOut = PathConstants.cmsPicPath();


        if (cmspt.getImagesFile() != null) {
            cmspt.setImagesFile(imageOut + "/" + cmspt.getImagesFile());
        }
        if (cmspt.getImagesFile2() != null) {
            cmspt.setImagesFile2(imageOut + "/" + cmspt.getImagesFile2());
        }
        if (cmspt.getImagesFile3() != null) {
            cmspt.setImagesFile3(imageOut + "/" + cmspt.getImagesFile3());
        }
        if (cmspt.getImagesFile4() != null) {
            cmspt.setImagesFile4(imageOut + "/" + cmspt.getImagesFile4());
        }
        File file = new File(path + "/" + id + ".html");
        if (file.exists() == true)//判断该模板文件是否存在
        {
            if (file.isDirectory() == true)
                return cmspt;
            BufferedReader buff = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
            String s = null;
            String content = "";
            while ((s = buff.readLine()) != null) {
                content += s + "\r\n";
            }
            buff.close();
            cmspt.setContent(content);
        }
        return cmspt;

    }

    //没有路径
    @Override
    public CmsPromotionTask queryPromo(Integer id, Integer siteId) throws Exception {
        //int siteId=(Integer) ActionContext.getContext().getSession().get("siteId");
        CmsPromotionTask cmspt = cmsPromotionTaskDAO.selectByPrimaryKey(id, siteId);
        Map categoryMap = viewPromotionService.getPromotionCategory();
        cmspt.setRuleName((String) categoryMap.get(cmspt.getPromotionTypeId()));
        return cmspt;
    }


    public CmsPromotionTask cmspAjaxbyId(Integer id, Integer siteId) throws Exception {
        //int siteId=(Integer) ActionContext.getContext().getSession().get("siteId");
        CmsPromotionTask cmspt = cmsPromotionTaskDAO.selectByPrimaryKey(id, siteId);

        return cmspt;
    }

    @Override
    public int promotionDelete(Integer id, Integer siteId) throws Exception {
        //int siteId=(Integer) ActionContext.getContext().getSession().get("siteId");
        return cmsPromotionTaskDAO.deleteByPrimaryKey(id, siteId);
    }

    @Override
    public int promotionUpdate(UploadFile file, UploadFile file2, UploadFile file3, UploadFile file4, CmsPromotionTask cmspt) throws Exception {

        String path = PathConstants.promotion(cmspt.getSiteId());
        FileOperateUtils.checkAndCreateDirs(path);
        String imagePath = PathConstants.imagePut();

        if (file != null) {
            String name = FileOperateUtils.upload(file, imagePath);
            cmspt.setImagesFile(name); //小图
        }
        if (file2 != null) {
            String name = FileOperateUtils.upload(file2, imagePath);
            cmspt.setImagesFile2(name);//大图
        }
        if (file3 != null) {
            String name = FileOperateUtils.upload(file3, imagePath);
            cmspt.setImagesFile3(name);//活动展示图一
        }
        if (file4 != null) {
            String name = FileOperateUtils.upload(file4, imagePath);
            cmspt.setImagesFile4(name);//活动展示图二
        }
        int re = cmsPromotionTaskDAO.updateByExample(cmspt);
        if (cmspt.getContent() != null) {
            File filess = new File(path + "/" + cmspt.getId() + ".html");
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filess), "UTF-8");
            BufferedWriter bWriter = new BufferedWriter(writer);
            bWriter.write(cmspt.getContent());
            bWriter.close();
        }
        return re;
    }


    @Override
    public int promotionUpdate(CmsPromotionTask cmspt) throws Exception {
        // TODO Auto-generated method stub
        return cmsPromotionTaskDAO.updateByExample(cmspt);
    }


    @Override
    public CmsPromotionTask publishObj() throws Exception {
        return cmsPromotionTaskDAO.publishObj();
    }

    @Override
    public int publish(Integer id, Integer siteId) throws Exception {
        int pid = 0;
        //int siteId=(Integer) ActionContext.getContext().getSession().get("siteId");
        ViewPromotion promotion = null;
        promotion = viewPromotionDAO.selectByPrimaryKey(id);
        if (promotion == null) {
            cmsPromotionTaskDAO.deleteByPrimaryKey(id, siteId);
            return 2;
        }
        CmsPromotionTask cmspt = null;
        cmspt = cmsPromotionTaskDAO.selectByPrimaryKey(id, siteId);
        promotion.setPath(PathConstants.promotion(cmspt.getSiteId()) + "/" + promotion.getPromotionId() + ".html");
        String html = htmlBuilder.promotionParse(promotion, cmspt);
        if (html != null && !html.equals("")) {
            pid = 1;
            String path = PathConstants.promotionOut(cmspt.getSiteId());
            FileOperateUtils.checkAndCreateDirs(path);
            File file = new File(PathConstants.promotion(cmspt.getSiteId()) + "/" + id + ".html");
            if (file.exists() == true)//判断该模板文件是否存在
            {
                BufferedReader buff = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
                String s = null;
                String content = "";
                while ((s = buff.readLine()) != null) {
                    content += s + "\r\n";
                }
                buff.close();
                cmspt.setContent(content);
            }
            File filess = new File(path + "/" + cmspt.getOutput());
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filess), "UTF-8");
            BufferedWriter bWriter = new BufferedWriter(writer);
            bWriter.write(cmspt.getContent());
            bWriter.close();
        }
        return pid;
    }

    @Override
    public int close(Integer siteId) throws Exception {
        int re = 0;
        CmsPromotionTask cmspt = null;
        //int siteId=(Integer) ActionContext.getContext().getSession().get("siteId");
        cmspt = cmsPromotionTaskDAO.closeObj();
        if (cmspt == null) {
            System.err.println("------------------无活动需要关闭-------------------");
            return re;//无活动需要关闭
        } else {
            int s = cmsPromotionTaskDAO.deleteByPrimaryKey(cmspt.getId(), siteId);
            if (s > 0) {
                re = 1;
                System.err.println("------------------活动：" + cmspt.getName() + "已关闭-------------------");
            }
            return 1;//活动
        }
    }

    @Override
    public CmsPromotionTask cmspById(CmsPromotionTask CMSP) throws Exception {
        // TODO Auto-generated method stub
        //int siteId=(Integer) ActionContext.getContext().getSession().get("siteId");
        //CMSP.setSiteId(siteId);
        CmsPromotionTask cmspt = cmsPromotionTaskDAO.selectByIdAndSiteId(CMSP);
        return cmspt;
    }


    @Override
    public int byTask(CmsPromotionTask cmspt) throws Exception {

        return cmsPromotionTaskDAO.countByTask(cmspt);
    }

    @Override
    public List getAllPromotionTask(CmsPromotionTask promotion) throws Exception {
        // TODO Auto-generated method stub
        return cmsPromotionTaskDAO.getAllPromtionTask(promotion);
    }

    public ViewPromotionService getViewPromotionService() {
        return viewPromotionService;
    }

    public void setViewPromotionService(ViewPromotionService viewPromotionService) {
        this.viewPromotionService = viewPromotionService;
    }


}
