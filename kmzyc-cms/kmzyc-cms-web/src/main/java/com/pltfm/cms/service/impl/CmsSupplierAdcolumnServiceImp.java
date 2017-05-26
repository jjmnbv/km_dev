package com.pltfm.cms.service.impl;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.kmzyc.supplier.model.ShopMain;
import com.pltfm.cms.dao.CmsAdcolumnDAO;
import com.pltfm.cms.dao.CmsSupplierAdcolumnDAO;
import com.pltfm.cms.parse.PathConstants;
import com.pltfm.cms.service.CmsSupplierAdcolumnService;
import com.pltfm.cms.util.FileOperateUtils;
import com.pltfm.cms.vobject.CmsAdcolumn;
import com.pltfm.cms.vobject.CmsAdv;
import com.pltfm.cms.vobject.CmsSupplierAdcolumn;
import com.pltfm.cms.vobject.CmsTemplate;


@Component("cmsSupplierAdcolumnService")
public class CmsSupplierAdcolumnServiceImp implements CmsSupplierAdcolumnService {

    @Resource(name = "cmsSupplierAdcolumnDAO")
    private CmsSupplierAdcolumnDAO cmsSupplierAdcolumnDAO;
    @Resource(name = "cmsAdcolumnDAO")
    private CmsAdcolumnDAO cmsAdcolumnDAO;

    @Override
    public void insert(CmsSupplierAdcolumn supplierAdcolumn)
            throws SQLException {
        cmsSupplierAdcolumnDAO.insert(supplierAdcolumn);

    }

    //供应商与广告对应关系

    @Override
    public void insertSupplierAdcolumn(CmsTemplate cmsTemplate, CmsAdcolumn cmsAdcolumn, CmsSupplierAdcolumn supplierAdcolumn) throws Exception {
        //添加数据
        cmsAdcolumnDAO.insert(cmsAdcolumn);
        supplierAdcolumn.setAdcolumnId(cmsAdcolumn.getAdcolumnId());


        cmsSupplierAdcolumnDAO.insert(supplierAdcolumn);

        //把供应商模板copy至广告供应商下
        //1.模版/template/template下读取文件
        String path = PathConstants.templatePath(cmsTemplate.getSiteId());
        File file = new File(path, cmsTemplate.getTemplateId() + ".html");
        if (file.exists()) {
            cmsTemplate.setContent(FileUtils.readFileToString(file, "utf-8"));
        }

        //2.写入/template/adv/supplier文件
        path = PathConstants.advSupplierPath(cmsTemplate.getSiteId());
        FileOperateUtils.checkAndCreateDirs(path);
        file = new File(path);
        File tempFile = new File(file, supplierAdcolumn.getSupplierAdcolumnId() + ".html");
        FileUtils.writeStringToFile(tempFile, cmsTemplate.getContent(), "utf-8");


    }

    public CmsSupplierAdcolumnDAO getCmsSupplierAdcolumnDAO() {
        return cmsSupplierAdcolumnDAO;
    }

    public void setCmsSupplierAdcolumnDAO(
            CmsSupplierAdcolumnDAO cmsSupplierAdcolumnDAO) {
        this.cmsSupplierAdcolumnDAO = cmsSupplierAdcolumnDAO;
    }

    public CmsAdcolumnDAO getCmsAdcolumnDAO() {
        return cmsAdcolumnDAO;
    }

    public void setCmsAdcolumnDAO(CmsAdcolumnDAO cmsAdcolumnDAO) {
        this.cmsAdcolumnDAO = cmsAdcolumnDAO;
    }

    @Override
    public List queryAdcolumnByShopMainId(ShopMain shopMain)
            throws SQLException {
        List<CmsAdcolumn> list = cmsSupplierAdcolumnDAO.queryAdcolumnByShopMainId(shopMain);
        return list;
    }

    @Override
    public List queryAdvByShopMainId(ShopMain shopMain) throws SQLException {
        List<CmsAdv> list = cmsSupplierAdcolumnDAO.queryAdvByShopMainId(shopMain);
        return list;
    }

    @Override
    public void update(CmsSupplierAdcolumn supplierAdcolumn)
            throws SQLException {
        cmsSupplierAdcolumnDAO.update(supplierAdcolumn);

    }

    @Override
    public Page queryList(Page page, CmsSupplierAdcolumn supplierAdcolumn)
            throws SQLException {
        if (page == null) {
            page = new Page();
        }

        int totalNum = cmsSupplierAdcolumnDAO.queryCount(supplierAdcolumn);
        if (totalNum != 0) {
            page.setRecordCount(totalNum);
            // 设置查询开始结束索引
            supplierAdcolumn.setStartIndex(page.getStartIndex());
            supplierAdcolumn.setEndIndex(page.getStartIndex() + page.getPageSize());
            page.setDataList(cmsSupplierAdcolumnDAO.queryList(supplierAdcolumn));
        } else {
            page.setRecordCount(0);
            page.setDataList(null);
        }
        return page;

    }

    @Override
    public CmsSupplierAdcolumn byId(CmsSupplierAdcolumn supplierAdcolumn)
            throws SQLException {
        return cmsSupplierAdcolumnDAO.byId(supplierAdcolumn);

    }

    @Override
    public Integer checkAdvIsUpload(CmsSupplierAdcolumn supplierAdcolumn)
            throws SQLException {

        return cmsSupplierAdcolumnDAO.checkAdvIsUpload(supplierAdcolumn);
    }

    @Override
    public CmsSupplierAdcolumn byAdcolumnId(
            CmsSupplierAdcolumn cmsSupplierAdcolumn) throws SQLException {
        // TODO Auto-generated method stub
        return cmsSupplierAdcolumnDAO.byAdcolumnId(cmsSupplierAdcolumn);
    }

    /***
     * 查询是否供应商下有广告
     * */
    @Override
    public List queryListAdv(Long supplierId) throws SQLException {

        List<CmsAdcolumn> list = cmsSupplierAdcolumnDAO.queryListAdv(supplierId);
        return list;
    }

    @Override
    public List querySupplierIdByAdcolumn(String str) throws SQLException {
        // TODO Auto-generated method stub
        return cmsSupplierAdcolumnDAO.querySupplierIdByAdcolumn(str);
    }
}
