package com.pltfm.cms.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kmzyc.commons.exception.FileUploadException;
import com.kmzyc.commons.page.Page;
import com.pltfm.cms.dao.CmsInformationDAO;
import com.pltfm.cms.parse.PathConstants;
import com.pltfm.cms.service.CmsInformationService;
import com.pltfm.cms.util.FileOperateUtils;
import com.pltfm.cms.vobject.CmsInformation;
import com.pltfm.cms.vobject.CmsInformationExample;
import com.pltfm.cms.vobject.CmsInformationType;
import com.pltfm.cms.vobject.UploadFile;

@Component("cmsInformationService")
public class CmsInformationServiceImp implements CmsInformationService {
    @Resource(name = "cmsInformationDAO")
    public CmsInformationDAO cmsInformationDAO;
    /*@Resource(name = "templateConfig")
	private Map<String, String> templateConfig;*/

    @Override
    public Page queryForPage(CmsInformation cmsInformation, Page page)
            throws Exception {
        if (page == null) {
            page = new Page();
        }
        if (cmsInformation == null) {
            cmsInformation = new CmsInformation();
        }
        // 根据条件获取文章信息总条数countByExample
        int totalNum = cmsInformationDAO.countByExample(cmsInformation);

        if (totalNum != 0) {
            page.setRecordCount(totalNum);
            // 设置查询开始结束索引
            cmsInformation.setStartIndex(page.getStartIndex());
            cmsInformation.setEndIndex(page.getStartIndex()
                    + page.getPageSize());
            List list = cmsInformationDAO.queryForPage(cmsInformation);
            //获取资讯内容
            List list1 = new ArrayList();
            CmsInformation infor;
            for (int i = 0; i < list.size(); i++) {
                infor = (CmsInformation) list.get(i);
                String path = PathConstants.informPath(infor.getSiteId());
                File file = new File(path + "/"
                        + infor.getInforId() + ".html");
                if (file.exists() == true) {
                    if (file.isDirectory() == true) {
                        list1.add(infor);
                    } else {
                        BufferedReader buff = new BufferedReader(new InputStreamReader(
                                new FileInputStream(file), "utf-8"));
                        String s = null;
                        String content = "";
                        while ((s = buff.readLine()) != null) {
                            content += s + "\r\n";
                        }
                        buff.close();
                        infor.setContent_content(content);
                        list1.add(infor);
                    }
                }
            }
            page.setDataList(list1);
        } else {
            page.setRecordCount(0);
            page.setDataList(null);
        }
        return page;
    }

    //静态页面
    @Override
    public Page queryStaticHolidayList(CmsInformation cmsInformation, Page page)
            throws Exception {
        if (page == null) {
            page = new Page();
        }
        if (cmsInformation == null) {
            cmsInformation = new CmsInformation();
        }
        // 根据条件获取文章信息总条数countByExample
        int totalNum = cmsInformationDAO.queryStaticHolidayCount(cmsInformation);

        if (totalNum != 0) {
            page.setRecordCount(totalNum);
            // 设置查询开始结束索引
            cmsInformation.setStartIndex(page.getStartIndex());
            cmsInformation.setEndIndex(page.getStartIndex()
                    + page.getPageSize());
            page.setDataList(cmsInformationDAO.queryStaticHolidayList(cmsInformation));
        } else {
            page.setRecordCount(0);
            page.setDataList(null);
        }
        return page;
    }

    @Override
    public List inforTypeList(CmsInformationType inforType) throws Exception {
        return cmsInformationDAO.inforTypeList(inforType);
    }

    @Override
    public void add(UploadFile fileImage, CmsInformation cmsInformation) throws Exception {
        String path = PathConstants.informPath(cmsInformation.getSiteId());
        FileOperateUtils.checkAndCreateDirs(path);

        if (fileImage != null) {
            String imageName = getImageName(fileImage, cmsInformation.getSiteId());
            if (imageName != null) {
                cmsInformation.setImgUploading(imageName);
            }
        }
        cmsInformationDAO.insert(cmsInformation);
        cmsInformationDAO.updateByPrimaryKey(cmsInformation);
        File file = new File(path + "/"
                + cmsInformation.getInforId() + ".html");
        if (null != file) {
            OutputStreamWriter writer = new OutputStreamWriter(
                    new FileOutputStream(file), "UTF-8");
            BufferedWriter bWriter = new BufferedWriter(writer);
            if (cmsInformation.getTemplateType() == 3) {
                bWriter.write(cmsInformation.getInformContent());
            } else {
                bWriter.write(cmsInformation.getContent_content());
            }

            bWriter.close();
        }

    }

    //获取图片名称
    public String getImageName(UploadFile fileImage, Integer siteId) throws FileUploadException {
        String imagePath = PathConstants.imagePut();
        String imagename = "";
        String path = PathConstants.advPath(siteId);

        FileOperateUtils.checkAndCreateDirs(path);
        path = PathConstants.advTempPath(siteId);
        FileOperateUtils.checkAndCreateDirs(path);
        if (fileImage != null) {
            imagename = FileOperateUtils.upload(fileImage, imagePath);
            //
        }

        return imagename;
    }

    //添加静态页面
    @Override
    public void addStaticHoliday(CmsInformation cmsInformation) throws Exception {
        String path = PathConstants.staticHolidayTemplatePath(cmsInformation.getSiteId());
        FileOperateUtils.checkAndCreateDirs(path);
        cmsInformationDAO.insert(cmsInformation);
        File file = new File(path + "/"
                + cmsInformation.getInforId() + ".html");
        OutputStreamWriter writer = new OutputStreamWriter(
                new FileOutputStream(file), "UTF-8");
        BufferedWriter bWriter = new BufferedWriter(writer);

        //	if (cmsInformation.getTemplateType() == 9) {
        //		bWriter.write(cmsInformation.getInformContent());
        //	} else {
        bWriter.write(cmsInformation.getContent_content());
        //	}


        bWriter.close();
    }

    //修改静态页面
    @Override
    public Integer updateStaticHoliday(CmsInformation cmsInformation) throws Exception {
        String path = PathConstants.staticHolidayTemplatePath(cmsInformation.getSiteId());
        FileOperateUtils.checkAndCreateDirs(path);
        int re = cmsInformationDAO.updateByPrimaryKeySelective(cmsInformation);
        if (cmsInformation.getContent_content() != null) {
            File file = new File(path + "/"
                    + cmsInformation.getInforId() + ".html");
            OutputStreamWriter writer = new OutputStreamWriter(
                    new FileOutputStream(file), "UTF-8");
            BufferedWriter bWriter = new BufferedWriter(writer);

            //	if (cmsInformation.getTemplateType() == 9) {
            //		bWriter.write(cmsInformation.getInformContent());
            //	} else {
            bWriter.write(cmsInformation.getContent_content());
            //	}
            bWriter.close();
        }
        return re;
    }


    @Override
    public CmsInformation byid(boolean flag, Integer inforid) throws Exception {
        CmsInformation cmsInformation = cmsInformationDAO
                .selectByPrimaryKey(inforid);
        String path = PathConstants.informPath(cmsInformation.getSiteId());
        if (flag) {
            String imageOut = PathConstants.cmsPicPath();
            if (cmsInformation.getImgUploading() != null) {
                cmsInformation.setImgUploading(imageOut + "/" + cmsInformation.getImgUploading());
            }
        }
        File file = new File(path + "/"
                + cmsInformation.getInforId() + ".html");
        if (file.exists() == true)// 判断该模板文件是否存在
        {
            if (file.isDirectory() == true)
                return cmsInformation;
            BufferedReader buff = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file), "utf-8"));
            String s = null;
            String content = "";
            while ((s = buff.readLine()) != null) {
                content += s + "\r\n";
            }
            buff.close();
            cmsInformation.setContent_content(content);
        }

        return cmsInformation;
    }

    @Override
    public CmsInformation queryStaticHolidayPage(Integer inforid) throws Exception {
        CmsInformation cmsInformation = cmsInformationDAO
                .selectByPrimaryKey(inforid);
        String path = PathConstants.staticHolidayTemplatePath(cmsInformation.getSiteId());
        File file = new File(path + "/"
                + cmsInformation.getInforId() + ".html");
        if (file.exists() == true)// 判断该模板文件是否存在
        {
            if (file.isDirectory() == true)
                return cmsInformation;
            BufferedReader buff = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file), "utf-8"));
            String s = null;
            String content = "";
            while ((s = buff.readLine()) != null) {
                content += s + "\r\n";
            }
            buff.close();
            cmsInformation.setContent_content(content);
        }

        return cmsInformation;
    }

    @Override
    public Integer update(UploadFile fileImage, CmsInformation cmsInformation) throws Exception {
        CmsInformation cmsInformations = cmsInformationDAO
                .selectByPrimaryKey(cmsInformation.getInforId());
        String path = PathConstants.informPath(cmsInformations.getSiteId());
        FileOperateUtils.checkAndCreateDirs(path);
        if (fileImage != null) {
            String imageName = getImageName(fileImage, cmsInformations.getSiteId());
            if (imageName != null) {
                cmsInformation.setImgUploading(imageName);
            }
        }
        int re = cmsInformationDAO.updateByPrimaryKeySelective(cmsInformation);
        File file = new File(path + "/"
                + cmsInformation.getInforId() + ".html");
        OutputStreamWriter writer = new OutputStreamWriter(
                new FileOutputStream(file), "UTF-8");
        BufferedWriter bWriter = new BufferedWriter(writer);
        if (cmsInformation.getTemplateType() == 3) {
            bWriter.write(cmsInformation.getInformContent());
        } else {
            bWriter.write(cmsInformation.getContent_content());
        }
        bWriter.close();
        return re;
    }

    @Override
    public void delete(Integer inforid) throws Exception {
        // String uploadPath = templateConfig.get("configPath");
        CmsInformation cmsInformations = cmsInformationDAO
                .selectByPrimaryKey(inforid);

        String path = PathConstants.publishPath(cmsInformations.getSiteId());
        CmsInformation cmsInformation = cmsInformationDAO
                .selectByPrimaryKey(inforid);
        FileOperateUtils.delFile(path + "/"
                + cmsInformation.getInforId() + ".html");
        cmsInformationDAO.deleteByPrimaryKey(inforid);
    }


    @Override
    public void staticHolidayDelDatas(List dataIds) throws Exception {
        if (dataIds != null && dataIds.size() > 0) {
            for (int i = 0; i < dataIds.size(); i++) {
                delete((Integer) dataIds.get(i));
            }
        }


    }

    /**
     * 查询多个文章类型数据信息
     *
     * @return 返回值
     * @throws Exception 异常
     */
    @Override
    public List inforTypesList(List dataIds) throws Exception {
        CmsInformationExample example = new CmsInformationExample();
        example.createCriteria().andTypeIdIn(dataIds).andStatusEqualTo(0);
        example.setOrderByClause("publish_date desc ");
        //获取资讯内容
        List list = cmsInformationDAO.selectByExample(example);
        List list1 = new ArrayList();
        CmsInformation infor;
        for (int i = 0; i < list.size(); i++) {
            infor = (CmsInformation) list.get(i);
            String path = PathConstants.informPath(infor.getSiteId());
            File file = new File(path + "/"
                    + infor.getInforId() + ".html");
            if (file.exists() == true) {
                if (file.isDirectory() == true) {
                    list1.add(infor);
                } else {
                    BufferedReader buff = new BufferedReader(new InputStreamReader(
                            new FileInputStream(file), "utf-8"));
                    String s = null;
                    String content = "";
                    while ((s = buff.readLine()) != null) {
                        content += s + "\r\n";
                    }
                    buff.close();
                    infor.setContent_content(content);
                    list1.add(infor);
                }
            }
        }
        return list1;
    }

    @Override
    public int byinfor(CmsInformation cmsInformation) throws Exception {
        // TODO Auto-generated method stub
        return cmsInformationDAO.countByInfor(cmsInformation);
    }

    // 名字惟一性
    @Override
    public Integer countByInforname(CmsInformation cmsInformation)
            throws Exception {
        // TODO Auto-generated method stub
        return cmsInformationDAO.countInfor(cmsInformation);
    }

    public CmsInformationDAO getCmsInformationDAO() {
        return cmsInformationDAO;
    }

    public void setCmsInformationDAO(CmsInformationDAO cmsInformationDAO) {
        this.cmsInformationDAO = cmsInformationDAO;
    }

    /*	public Map<String, String> getTemplateConfig() {
            return templateConfig;
        }

        public void setTemplateConfig(Map<String, String> templateConfig) {
            this.templateConfig = templateConfig;
        }*/
    @Override
    public Integer deleteInfoByType(String val) throws SQLException {
        // TODO Auto-generated method stub
        return cmsInformationDAO.deleteInfoByType(val);
    }


}
