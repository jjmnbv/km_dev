package com.pltfm.cms.service.impl;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.exception.FileUploadException;
import com.kmzyc.commons.page.Page;
import com.pltfm.cms.dao.CmsStylesDAO;
import com.pltfm.cms.parse.PathConstants;
import com.pltfm.cms.service.CmsStylesService;
import com.pltfm.cms.util.FileOperateUtils;
import com.pltfm.cms.vobject.CmsStyles;
import com.pltfm.cms.vobject.CmsStylesExample;
import com.pltfm.cms.vobject.UploadFile;

/**
 * 风格业务逻辑接口实现类
 *
 * @author cjm
 * @since 2014-8-22
 */
@Component(value = "cmsStylesService")
public class CmsStylesServiceImpl implements CmsStylesService {

    /**
     * 风格DAO
     */
    @Resource(name = "cmsStylesDAO")
    private CmsStylesDAO cmsStylesDAO;

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

    /**
     * 分页查询风格列表
     */
    @Override
    public Page queryForPage(CmsStyles cmsStyles, Page page) throws Exception {
        if (page == null) {
            page = new Page();
        }
        if (cmsStyles == null) {
            cmsStyles = new CmsStyles();
        }
        // 根据条件获取窗口数据信息总条数
        int totalNum = cmsStylesDAO.countByCmsStyles(cmsStyles);
        if (totalNum != 0) {
            page.setRecordCount(totalNum);
            // 设置查询开始结束索引
            cmsStyles.setStartIndex(page.getStartIndex());
            cmsStyles
                    .setEndIndex(page.getStartIndex() + page.getPageSize());
            page.setDataList(cmsStylesDAO.queryForPage(cmsStyles));
        } else {
            page.setRecordCount(0);
            page.setDataList(null);
        }
        return page;
    }

    /**
     * 添加风格
     */
    @Override
    public void addStyles(CmsStyles cmsStyles, UploadFile fileImage) throws Exception {
        if (fileImage != null) {
            String imageName = getImageName(fileImage, cmsStyles.getSiteId());
            if (StringUtils.isNotEmpty(imageName)) {
                cmsStyles.setRemark(imageName);
            }
        }

        cmsStylesDAO.insert(cmsStyles);
        //取到路径并生成相应的html文件
        String path = PathConstants.stylesTemPath(cmsStyles.getSiteId());
        FileOperateUtils.checkAndCreateDirs(path);
        File wFile = new File(path, cmsStyles.getStylesId() + ".html");
        FileUtils.writeStringToFile(wFile, cmsStyles.getContent(), "utf-8");
    }

    /**
     * 删除风格
     */
    @Override
    public void delStyles(Integer cmsStylesId) throws Exception {
        cmsStylesDAO.deleteByPrimaryKey(cmsStylesId);
    }

    /**
     * 修改风格
     */
    @Override
    public void updateStyles(CmsStyles cmsStyles, UploadFile fileImage) throws Exception {
        if (fileImage != null) {
            String imageName = getImageName(fileImage, cmsStyles.getSiteId());
            if (StringUtils.isNotEmpty(imageName)) {
                cmsStyles.setRemark(imageName);
            }
        }
        String path = PathConstants.stylesTemPath(cmsStyles.getSiteId());
        FileOperateUtils.checkAndCreateDirs(path);
        File file = new File(path, cmsStyles.getStylesId() + ".html");
        FileUtils.writeStringToFile(file, cmsStyles.getContent(), "utf-8");

        cmsStylesDAO.updateByPrimaryKeySelective(cmsStyles);

    }

    /**
     * 根据Id单条数据
     */
    @Override
    public CmsStyles queryByCmsStylesId(Integer cmsStylesId) throws Exception {
        CmsStyles styles = cmsStylesDAO.selectByPrimaryKey(cmsStylesId);
        String path = PathConstants.stylesTemPath(styles.getSiteId());
        File file = new File(path, styles.getStylesId() + ".html");
        if (file.exists()) {
            styles.setContent(FileUtils.readFileToString(file, "utf-8"));
        }
        return styles;

    }

    /**
     * 根据模板Id查询单条数据
     */
    @Override
    public CmsStyles selectByTemplateId(Integer templateId) throws Exception {
        CmsStylesExample example = new CmsStylesExample();
        example.createCriteria().andTemplateIdEqualTo(templateId);
        List list = null;
        list = cmsStylesDAO.selectByExample(example);
        if (null != list && list.size() > 0) {
            return (CmsStyles) list.get(0);
        }
        return null;
    }


    public CmsStylesDAO getCmsStylesDAO() {
        return cmsStylesDAO;
    }

    public void setCmsStylesDAO(CmsStylesDAO cmsStylesDAO) {
        this.cmsStylesDAO = cmsStylesDAO;
    }


}
