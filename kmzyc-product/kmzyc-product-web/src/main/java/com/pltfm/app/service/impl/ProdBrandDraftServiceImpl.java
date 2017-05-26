package com.pltfm.app.service.impl;

import com.pltfm.app.dao.ProdBrandDraftDAO;
import com.pltfm.app.service.ProdBrandDraftService;
import com.pltfm.app.vobject.ProdBrand;
import com.pltfm.app.vobject.ProdBrandDraft;
import com.pltfm.app.vobject.ProdBrandDraftExample;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

/**
 * 功能：品牌草稿服务
 *
 * @author Zhoujiwei
 * @since 2015/11/19 16:44
 */
@Service("prodBrandDraftService")
public class ProdBrandDraftServiceImpl implements ProdBrandDraftService {

    protected Logger logger = LoggerFactory.getLogger(ProdBrandDraftServiceImpl.class);

    @Resource
    private ProdBrandDraftDAO prodBrandDraftDAO;

    @Override
    public void showProdBrandDraftList(Page page, ProdBrandDraft prodBrandDraft) throws ServiceException {
        int pageIndex = page.getPageNo();
        if (pageIndex == 0) {
            pageIndex = 1;
        }
        int pageSize = page.getPageSize();
        int skip = (pageIndex - 1) * pageSize;
        int max = pageSize;

        ProdBrandDraftExample exm = new ProdBrandDraftExample();
        ProdBrandDraftExample.Criteria c = exm.createCriteria();
        //品牌名称
        if (StringUtils.isNotEmpty(prodBrandDraft.getBrandName())) {
            c.andBrandNameLike("%" + prodBrandDraft.getBrandName() + "%");
        }
        //状态
        if (StringUtils.isNotEmpty(prodBrandDraft.getStatus())) {
            c.andStatusEqualTo(prodBrandDraft.getStatus());
        }
        //申请时间
        if (prodBrandDraft.getBeforeApplyTime() != null && prodBrandDraft.getEndApplyTime() != null) {
            c.andApplyTimeBetween(prodBrandDraft.getBeforeApplyTime(), prodBrandDraft.getEndApplyTime());
        } else if (prodBrandDraft.getBeforeApplyTime() != null) {
            c.andApplyTimeGreaterThan(prodBrandDraft.getBeforeApplyTime());
        } else if (prodBrandDraft.getEndApplyTime() != null) {
            c.andApplyTimeLessThan(prodBrandDraft.getEndApplyTime());
        }
        //所属商家
        if (StringUtils.isNotEmpty(prodBrandDraft.getMerchantName())) {
            c.andShopCodeInSuppliersInfoLike(prodBrandDraft.getMerchantName());
        }
        //排序
        exm.setOrderByClause(" STATUS ASC,APPLY_TIME DESC");

        try {
            int count = prodBrandDraftDAO.countByExample(exm);
            List<ProdBrand> list = prodBrandDraftDAO.selectByExample(exm, skip, max);
            page.setDataList(list);
            page.setRecordCount(count);
        } catch (Exception e) {
            logger.error("查询品牌草稿列表失败，查询参数为【" + prodBrandDraft.toString() + "】,错误信息：" + e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public ProdBrandDraft getProdBrandDraftById(Long brandId) throws ServiceException {
        try {
            return prodBrandDraftDAO.getProdBrandDraftById(brandId);
        } catch (SQLException e) {
            logger.error("查询品牌草稿失败，brandId=" + brandId + ",错误信息：" + e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void refuseProdBrandDraft(ProdBrandDraft prodBrandDraft) throws ServiceException {
        try {
            prodBrandDraftDAO.refuseProdBrandDraft(prodBrandDraft);
        } catch (SQLException e) {
            logger.error("查询品牌草稿失败，参数为【" + prodBrandDraft.toString() + "】,错误信息：：" + e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void passProdBrandDraft(ProdBrandDraft prodBrandDraft) throws ServiceException {
        try {
            prodBrandDraftDAO.passProdBrandDraft(prodBrandDraft);
            prodBrandDraft = prodBrandDraftDAO.getProdBrandDraftById(prodBrandDraft.getBrandId());
            prodBrandDraftDAO.insertIntoProdBrand(prodBrandDraft);
        } catch (SQLException e) {
            logger.error("查询品牌草稿失败，为【" + prodBrandDraft.toString() + "】,错误信息：：" + e.getMessage());
            throw new ServiceException(e);
        }
    }
}
