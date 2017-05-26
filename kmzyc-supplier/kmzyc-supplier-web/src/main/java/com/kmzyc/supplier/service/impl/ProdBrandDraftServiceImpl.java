package com.kmzyc.supplier.service.impl;

import com.kmzyc.zkconfig.ConfigurationUtil;
import com.kmzyc.supplier.dao.ProdBrandDraftDAO;
import com.kmzyc.supplier.service.ProdBrandDraftService;
import com.kmzyc.commons.exception.ServiceException;
import com.km.framework.page.Pagination;
import com.pltfm.app.vobject.ProdBrandDraft;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能：品牌草稿服务
 *
 * @author Zhoujiwei
 * @since 2015/11/20 16:49
 */
@Service("prodBrandDraftService")
public class ProdBrandDraftServiceImpl implements ProdBrandDraftService {

    @Resource
    private ProdBrandDraftDAO prodBrandDraftDAO;

    @Override
    public Pagination showProdBrandDraftList(ProdBrandDraft prodBrandDraft, Pagination page)
            throws ServiceException {
        Map<String, Object> condition = new HashMap();
        condition.put("shopCode", prodBrandDraft.getShopCode());
        condition.put("status", prodBrandDraft.getStatus());
        page.setObjCondition(condition);
        try {
            prodBrandDraftDAO.findPaginationByPage(page);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
        return page;
    }

    @Override
    public ProdBrandDraft getProdBrandDraft(ProdBrandDraft prodBrandDraft) throws ServiceException {
        try {
            return prodBrandDraftDAO.getProdBrandDraft(prodBrandDraft);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public Long saveProdBrandDraft(ProdBrandDraft prodBrandDraft) throws ServiceException {
        try {
            return prodBrandDraftDAO.insert(prodBrandDraft);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public int updateProdBrandDraft(ProdBrandDraft prodBrandDraft, String preLogoPath) throws ServiceException {
        try {
            int i = prodBrandDraftDAO.updateProdBrandDraft(prodBrandDraft);
            //之前的logo路径不为空，当前的logo路径也不为空，删除之前的文件
            if (StringUtils.isNotEmpty(preLogoPath) && StringUtils.isNotEmpty(prodBrandDraft.getLogoPath())) {
                deleteLogoFile(preLogoPath);
            }
            return i;
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    public boolean isExistsBrandName(String brandName) throws ServiceException {
        try {
            return prodBrandDraftDAO.isExistsBrandName(brandName);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public int deleteProdBrandDraft(ProdBrandDraft prodBrandDraft) throws ServiceException {
        try {
            ProdBrandDraft brandDraft = getProdBrandDraft(prodBrandDraft);
            int i = prodBrandDraftDAO.deleteProdBrandDraft(prodBrandDraft);
            deleteLogoFile(brandDraft.getLogoPath());
            return i;
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 删除logo文件路径
     *
     * @param logoFilePath logo文件路径
     */
    private void deleteLogoFile(String logoFilePath) {
        File f = new File(ConfigurationUtil.getString("pictureUploadPath") + logoFilePath);
        if (f.exists()) {
            f.delete();
        }
    }
}
