package com.pltfm.cms.service.impl;

import com.kmzyc.commons.page.Page;
import com.pltfm.cms.dao.CmsTemplateDAO;
import com.pltfm.cms.parse.PathConstants;
import com.pltfm.cms.service.CmsTemplateServce;
import com.pltfm.cms.util.FileOperateUtils;
import com.pltfm.cms.vobject.CmsTemplate;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

@Component("cmsTemplateServce")
public class CmsTemplateServceImpl implements CmsTemplateServce {
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    @Resource(name = "cmsTemplateDAO")
    private CmsTemplateDAO cmsTemplateDAO;

    @Override
    public List queryTemplateList() throws Exception {
        // TODO Auto-generated method stub
        return cmsTemplateDAO.selectByExample(null);
    }


    public CmsTemplateDAO getCmsTemplateDAO() {
        return cmsTemplateDAO;
    }

    public void setCmsTemplateDAO(CmsTemplateDAO cmsTemplateDAO) {
        this.cmsTemplateDAO = cmsTemplateDAO;
    }


    @Override
    public Page queryForPage(CmsTemplate cmsTemplate, Page page)
            throws Exception {
        // TODO Auto-generated method stub
        if (page == null)
            page = new Page();
        // 根据条件获取广告信息总条数
        int totalNum = cmsTemplateDAO.countByExample(cmsTemplate);
        if (totalNum != 0) {
            page.setRecordCount(totalNum);
            // 设置查询开始结束索引
            cmsTemplate.setStartIndex(page.getStartIndex());
            cmsTemplate.setEndIndex(page.getStartIndex() + page.getPageSize());
            page.setDataList(cmsTemplateDAO.queryForPage(cmsTemplate));
        } else {
            page.setRecordCount(0);
            page.setDataList(null);
        }
        return page;
    }


    @Override
    public Page queryVarTemplateList(CmsTemplate cmsTemplate, Page page)
            throws SQLException {
        // TODO Auto-generated method stub
        if (page == null)
            page = new Page();
        // 根据条件获取广告信息总条数
        int totalNum = cmsTemplateDAO.queryVarTemplateCounts(cmsTemplate);
        if (totalNum != 0) {
            page.setRecordCount(totalNum);
            // 设置查询开始结束索引
            cmsTemplate.setStartIndex(page.getStartIndex());
            cmsTemplate.setEndIndex(page.getStartIndex() + page.getPageSize());
            page.setDataList(cmsTemplateDAO.queryVarTemplateList(cmsTemplate));
        } else {
            page.setRecordCount(0);
            page.setDataList(null);
        }
        return page;
    }

    @Override
    public Page queryTemplateFilterBandTheme(CmsTemplate cmsTemplate, Page page)
            throws Exception {
        // TODO Auto-generated method stub
        if (page == null)
            page = new Page();

        int totalNum = cmsTemplateDAO.queryVarTemplateCounts(cmsTemplate);
        if (totalNum != 0) {
            page.setRecordCount(totalNum);
            // 设置查询开始结束索引
            cmsTemplate.setStartIndex(page.getStartIndex());
            cmsTemplate.setEndIndex(page.getStartIndex() + page.getPageSize());
            page.setDataList(cmsTemplateDAO.queryTemplateFilterBandTheme(cmsTemplate));
        } else {
            page.setRecordCount(0);
            page.setDataList(null);
        }
        return page;
    }

    @Override
    public Page queryTemplateFilterBandWindow(CmsTemplate cmsTemplate, Page page)
            throws Exception {
        // TODO Auto-generated method stub
        if (page == null)
            page = new Page();

        int totalNum = cmsTemplateDAO.queryVarTemplateCounts(cmsTemplate);
        if (totalNum != 0) {
            page.setRecordCount(totalNum);
            // 设置查询开始结束索引
            cmsTemplate.setStartIndex(page.getStartIndex());
            cmsTemplate.setEndIndex(page.getStartIndex() + page.getPageSize());
            page.setDataList(cmsTemplateDAO.queryTemplateFilterBandWindow(cmsTemplate));
        } else {
            page.setRecordCount(0);
            page.setDataList(null);
        }
        return page;
    }

    @Override
    @Transactional
    public void addTemplate(CmsTemplate cmsTemplate) throws Exception {
        // TODO Auto-generated method stub

        this.cmsTemplateDAO.insert(cmsTemplate);
        //文件写入
        String path = PathConstants.templatePath(cmsTemplate.getSiteId());
        FileOperateUtils.checkAndCreateDirs(path);
        File file = new File(path);
        File tempFile = new File(file, cmsTemplate.getTemplateId() + ".html");
        FileUtils.writeStringToFile(tempFile, cmsTemplate.getContent(), "utf-8");
    }

    @Override
    public CmsTemplate getTemplateById(Integer templateId) throws Exception {
        // TODO Auto-generated method stub
        CmsTemplate cmsTemplate = this.cmsTemplateDAO.selectByPrimaryKey(templateId);
        String path = PathConstants.templatePath(cmsTemplate.getSiteId());
        File file = new File(path, cmsTemplate.getTemplateId() + ".html");
        if (file.exists())//判断该模板文件是否存在
        {
            cmsTemplate.setContent(FileUtils.readFileToString(file, "utf-8"));
        }else{
            logger.error("模板{}不存在",file.getAbsolutePath());
            cmsTemplate.setContent(String.format("模板%s不存在",file.getAbsolutePath()));
        }
        return cmsTemplate;
    }

    @Override
    @Transactional
    public void updateTemplate(CmsTemplate cmsTemplate) throws Exception {
        // TODO Auto-generated method stub
        CmsTemplate template = getTemplateById(cmsTemplate.getTemplateId());
        String path = PathConstants.templatePath(template.getSiteId());
        FileOperateUtils.checkAndCreateDirs(path);
        //读取文件
        File file = new File(path, template.getTemplateId() + ".html");
        FileUtils.writeStringToFile(file, cmsTemplate.getContent(), "utf-8");
        cmsTemplate.setCreated(template.getCreated());
        cmsTemplate.setCreateDate(template.getCreateDate());
        this.cmsTemplateDAO.updateByPrimaryKey(cmsTemplate);
    }

    @Override
    @Transactional
    public void deleteTemplateById(Integer templateId) throws Exception {
        // TODO Auto-generated method stub
        CmsTemplate template = getTemplateById(templateId);
        this.cmsTemplateDAO.deleteByPrimaryKey(templateId);
        String path = PathConstants.templatePath(template.getSiteId());
        File file = new File(path, template.getTemplateId() + ".html");
        if (file.exists()) {
            file.delete();
        }
    }

    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();//递归删除目录中的子目录下
            if(children!=null)
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }


    @Override
    @Transactional
    public void deleteAll(String ids) throws Exception {

        // TODO Auto-generated method stub
        String[] id = ids.split(",");
        for (int i = 0; i < id.length; i++) {
            deleteTemplateById(Integer.valueOf(id[i]));
        }
    }

    @Override
    public List getByName(CmsTemplate cmsTemplate) throws Exception {
        // TODO Auto-generated method stub
        return cmsTemplateDAO.getByNameOrPid(cmsTemplate);
    }


    @Override
    public String check(CmsTemplate cmsTemplate) throws Exception {
        // TODO Auto-generated method stub
        if (cmsTemplate.getTemplateId() != null) {
            CmsTemplate template = this.getTemplateById(cmsTemplate.getTemplateId());
            if (!template.getName().equals(cmsTemplate.getName())) {
                List list = this.getByName(cmsTemplate);
                if (list.size() > 0)
                    return "fail";
            }
        } else {
            List list = this.getByName(cmsTemplate);
            if (list.size() > 0)
                return "fail";
        }
        return "success";
    }

    /**
     * 通过站点以及模板类型查询模板信息
     */
    public List selectBySiteIdType(CmsTemplate cmsTemplate) throws Exception {
        return cmsTemplateDAO.selectBySiteIdType(cmsTemplate);
    }


    @Override
    public List querySeletedTemplateList(String sql) throws Exception {
        // TODO Auto-generated method stub
        return cmsTemplateDAO.querySeletedTemplateList(sql);
    }

    public Page queryBandWindowList(CmsTemplate cmsTemplate, Page page) throws Exception {
        // TODO Auto-generated method stub
        if (page == null)
            page = new Page();
        // 根据条件获取广告信息总条数
        int totalNum = cmsTemplateDAO.queryBandWindowCounts(cmsTemplate);
        if (totalNum != 0) {
            page.setRecordCount(totalNum);
            // 设置查询开始结束索引
            cmsTemplate.setStartIndex(page.getStartIndex());
            cmsTemplate.setEndIndex(page.getStartIndex() + page.getPageSize());
            page.setDataList(cmsTemplateDAO.queryBandWindowList(cmsTemplate));
        } else {
            page.setRecordCount(0);
            page.setDataList(null);
        }
        return page;
    }


}
