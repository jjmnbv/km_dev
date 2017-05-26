package com.kmzyc.promotion.app.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.dao.ViewProductSkuDAO;
import com.kmzyc.promotion.app.enums.ProductStatus;
import com.kmzyc.promotion.app.service.ViewProductSkuService;
import com.kmzyc.promotion.app.vobject.ViewProductSku;
import com.kmzyc.promotion.app.vobject.ViewProductSkuExample;
import com.kmzyc.promotion.app.vobject.ViewProductSkuExample.Criteria;

/**
 * 
 * @author tanyunxing
 * 
 */
@Service("viewProductSkuService")
@SuppressWarnings("unchecked")
public class ViewProductSkuServiceImpl implements ViewProductSkuService {
    // 日志记录
    private static final Logger logger = LoggerFactory.getLogger(ViewProductSkuServiceImpl.class);

    @Resource
    private ViewProductSkuDAO viewProductSkuDao;

    @Override
    public void searchByPage(Page page, ViewProductSku viewProductSku, String type)
            throws Exception {

        int pageIndex = page.getPageNo();
        if (pageIndex == 0)
            pageIndex = 1;

        int pageSize = page.getPageSize();
        int skip = (pageIndex - 1) * pageSize;
        int max = pageSize;

        ViewProductSkuExample example = new ViewProductSkuExample();

        String proName =
                viewProductSku.getProcuctName() == null || viewProductSku.getProcuctName().isEmpty()
                        ? "%%" : "%" + viewProductSku.getProcuctName() + "%";

        String proSkuNo = viewProductSku.getProductSkuCode() == null
                || viewProductSku.getProductSkuCode().isEmpty() ? "%%"
                        : "%" + viewProductSku.getProductSkuCode() + "%";
        // 其他地方校验新增
        Long categoryId = viewProductSku.getCategoryId() == null
                || viewProductSku.getCategoryId().intValue() == 0 ? null
                        : viewProductSku.getCategoryId();

        Criteria c = example.createCriteria();
        if (!"ALL".equals(type)) {
            // 产品状态(2-5)=AUDIT("2","已审核,待上架"),SYSDOWN("5","系统下架");
            c.andStatusBetween(ProductStatus.AUDIT.getStatus(), ProductStatus.SYSDOWN.getStatus());
        }
        c.andProcuctNameLike(proName).andProductSkuCodeLike(proSkuNo);
        c.andSkuStatusEqualTo("1");
        if (categoryId != null) {
            c.andCategoryIdEqualTo(categoryId);
        }
        if (viewProductSku.getStatus() != null && !viewProductSku.getStatus().equals("")) {
            c.andStatusEqualTo(viewProductSku.getStatus());
        }

        if (viewProductSku.getProductNo() != null && !viewProductSku.getProductNo().isEmpty()) {
            c.andProductNoLike(viewProductSku.getProductNo());
        }

        example.setOrderByClause(" PRODUCT_ID DESC, PRODUCT_SKU_ID desc ");

        try {
            int count = viewProductSkuDao.countByExample(example);
            List<ViewProductSku> list = viewProductSkuDao.selectByExample(example, skip, max);
            page.setDataList(list);
            page.setRecordCount(count);
        } catch (SQLException e) {
            logger.error("searchByPage异常：", e);
            throw e;
        }
    }

    @Override
    public void searchPageByUserId(Page page, ViewProductSku viewProductSku, String type,
            Integer loginUserId) throws Exception {

        int pageIndex = page.getPageNo();
        if (pageIndex == 0)
            pageIndex = 1;

        int pageSize = page.getPageSize();
        int skip = (pageIndex - 1) * pageSize;
        int max = pageSize;

        ViewProductSkuExample example = new ViewProductSkuExample();
        example.setUserId(String.valueOf(loginUserId));
        String proTitle = viewProductSku.getProductTitle() == null
                || viewProductSku.getProductTitle().isEmpty() ? "%%"
                        : "%" + viewProductSku.getProductTitle() + "%";

        String proSkuNo = viewProductSku.getProductSkuCode() == null
                || viewProductSku.getProductSkuCode().isEmpty() ? "%%"
                        : "%" + viewProductSku.getProductSkuCode() + "%";
        // 其他地方校验新增
        Long categoryId = viewProductSku.getCategoryId() == null
                || viewProductSku.getCategoryId().intValue() == 0 ? null
                        : viewProductSku.getCategoryId();

        Criteria c = example.createCriteria();
        if (!"ALL".equals(type)) {
            // 产品状态(2-5)=AUDIT("2","已审核,待上架"),SYSDOWN("5","系统下架");
            c.andStatusBetween(ProductStatus.AUDIT.getStatus(), ProductStatus.SYSDOWN.getStatus());
        }
        c.andProductTitleLike(proTitle).andProductSkuCodeLike(proSkuNo);
        c.andSkuStatusEqualTo("1");
        if (categoryId != null) {
            c.andCategoryIdEqualTo(categoryId);
        }
        if (viewProductSku.getStatus() != null && !viewProductSku.getStatus().equals("")) {
            c.andStatusEqualTo(viewProductSku.getStatus());
        }

        if (viewProductSku.getProductNo() != null && !viewProductSku.getProductNo().isEmpty()) {
            c.andProductNoLike(viewProductSku.getProductNo());
        }
        example.setOrderByClause(" PRODUCT_ID DESC, PRODUCT_SKU_ID desc ");

        try {
            int count = viewProductSkuDao.countByExampleByUser(example);
            List<ViewProductSku> list = viewProductSkuDao.selectByExampleByUser(example, skip, max);

            for (ViewProductSku pd : list) {
                pd.setProcuctName(pd.getProcuctName().replaceAll(" ", "&nbsp;"));
                pd.setProductTitle(pd.getProductTitle().replaceAll(" ", "&nbsp;"));
            }

            page.setDataList(list);
            page.setRecordCount(count);
        } catch (SQLException e) {
            logger.error("viewProductSkuDao.selectByExampleByUser调用异常：", e);
            throw e;
        }
    }

    @Override
    public void searchByPageAndStatus(Page page, ViewProductSku viewProductSku, String type)
            throws Exception {

        int pageIndex = page.getPageNo();
        if (pageIndex == 0)
            pageIndex = 1;

        int pageSize = page.getPageSize();
        int skip = (pageIndex - 1) * pageSize;
        int max = pageSize;

        ViewProductSkuExample example = new ViewProductSkuExample();
        Criteria c = example.createCriteria();
        // String proName = viewProductSku.getProcuctName() == null
        // || viewProductSku.getProcuctName().isEmpty() ? "%%" : "%"
        // + viewProductSku.getProcuctName() + "%";

        if (StringUtils.isNotBlank(viewProductSku.getProductTitle())) {
            c.andProductTitleLike(viewProductSku.getProductTitle().trim());
        }
        if (StringUtils.isNotBlank(viewProductSku.getProductSkuCode())) {
            c.andProductSkuCodeLike(viewProductSku.getProductSkuCode().trim());
        }
        if (StringUtils.isNotBlank(viewProductSku.getSearchBrandName())) {
            example.setSearchBrandName(viewProductSku.getSearchBrandName().trim());
        }
        // String proSkuNo = viewProductSku.getProductSkuCode() == null
        // || viewProductSku.getProductSkuCode().isEmpty() ? "%%" : "%"
        // + viewProductSku.getProductSkuCode() + "%";

        if (viewProductSku.getCategoryId() != null && viewProductSku.getCategoryId() != 0) // 产品所属类目
            c.andCategoryIdEqualTo(viewProductSku.getCategoryId());
        if (viewProductSku.getMCategoryId() != null && viewProductSku.getCategoryId() == null)
            example.setmCategoryId(viewProductSku.getMCategoryId());
        if (viewProductSku.getBCategoryId() != null && viewProductSku.getMCategoryId() == null)
            example.setbCategoryId(viewProductSku.getBCategoryId());


        example.setOrderByClause(" PRODUCT_ID DESC, PRODUCT_SKU_ID desc ");

        try {
            int count = viewProductSkuDao.countByExampleForStockIn(example);
            List<ViewProductSku> list =
                    viewProductSkuDao.selectByExampleForStockIn(example, skip, max);
            for (ViewProductSku pd : list) {
                if (StringUtils.isNotBlank(pd.getProductTitle())) {
                    pd.setProductTitle(pd.getProductTitle().replaceAll(" ", "&nbsp;"));
                }
            }

            page.setDataList(list);
            page.setRecordCount(count);
        } catch (SQLException e) {
            logger.error("viewProductSkuDao.selectByExampleForStockIn调用异常：", e);
            throw e;
        }

    }

    @Override
    public ViewProductSku findByProductSkuId(Long productSkuId) throws Exception {

        ViewProductSkuExample example = new ViewProductSkuExample();
        example.createCriteria().andProductSkuIdEqualTo(productSkuId);

        return (ViewProductSku) viewProductSkuDao.selectByExample(example).get(0);
    }

    @Override
    public List<ViewProductSku> findByProductId(Long productId) throws Exception {
        ViewProductSkuExample example = new ViewProductSkuExample();
        example.createCriteria().andProductIdEqualTo(productId).andSkuStatusEqualTo("1");

        return viewProductSkuDao.selectByExample(example);

    }

    @Override
    public List<ViewProductSku> findProductAndSkuAttrByProductId(Long productId) throws Exception {
        ViewProductSkuExample example = new ViewProductSkuExample();
        example.createCriteria().andProductIdEqualTo(productId).andSkuStatusEqualTo("1");
        return viewProductSkuDao.selectSKUAttrByExample(example);
    }

    @Override
    public ViewProductSku findByProductSkucode(String productSkuCode) throws Exception {
        ViewProductSkuExample example = new ViewProductSkuExample();
        example.createCriteria().andProductSkuCodeEqualTo(productSkuCode);
        return (ViewProductSku) viewProductSkuDao.selectByExample(example).get(0);
    }

    @Override
    public void searchShelfByPage(Page page, ViewProductSku viewProductSku, String type)
            throws Exception {

        int pageIndex = page.getPageNo();
        if (pageIndex == 0)
            pageIndex = 1;

        int pageSize = page.getPageSize();
        int skip = (pageIndex - 1) * pageSize;
        int max = pageSize;

        ViewProductSkuExample example = new ViewProductSkuExample();

        String proName =
                viewProductSku.getProcuctName() == null || viewProductSku.getProcuctName().isEmpty()
                        ? "%%" : "%" + viewProductSku.getProcuctName() + "%";

        String proSkuNo = viewProductSku.getProductSkuCode() == null
                || viewProductSku.getProductSkuCode().isEmpty() ? "%%"
                        : "%" + viewProductSku.getProductSkuCode() + "%";
        // 其他地方校验新增
        Long categoryId = viewProductSku.getCategoryId() == null
                || viewProductSku.getCategoryId().intValue() == 0 ? null
                        : viewProductSku.getCategoryId();

        Criteria c = example.createCriteria();
        c.andProcuctNameLike(proName).andProductSkuCodeLike(proSkuNo);
        c.andSkuStatusEqualTo("1");
        if (categoryId != null) {
            c.andCategoryIdEqualTo(categoryId);
        }

        if ("B2B".equals(type)) {
            c.andStatusEqualTo(ProductStatus.UP.getStatus());
        }
        if (viewProductSku.getStatus() != null && !viewProductSku.getStatus().equals("")) {
            c.andStatusEqualTo(viewProductSku.getStatus());
        }

        if (viewProductSku.getProductNo() != null && !viewProductSku.getProductNo().isEmpty()) {
            c.andProductNoLike(viewProductSku.getProductNo());
        }

        example.setOrderByClause(" PRODUCT_ID DESC, PRODUCT_SKU_ID desc ");

        try {
            int count = viewProductSkuDao.countByExample(example);
            List<ViewProductSku> list = viewProductSkuDao.selectByExample(example, skip, max);
            page.setDataList(list);
            page.setRecordCount(count);
        } catch (SQLException e) {
            logger.error("viewProductSkuDao.selectByExample调用异常：", e);
            throw e;
        }
    }

    @Override
    public void searchShelfByPageByUser(Page page, ViewProductSku viewProductSku, String type,
            Integer userId) throws Exception {
        int pageIndex = page.getPageNo();
        if (pageIndex == 0)
            pageIndex = 1;

        int pageSize = page.getPageSize();
        int skip = (pageIndex - 1) * pageSize;
        int max = pageSize;

        ViewProductSkuExample example = new ViewProductSkuExample();
        if (viewProductSku.getCategoryId() != null) {
            example.setCategoryId(viewProductSku.getCategoryId());
        }
        if (viewProductSku.getBCategoryId() != null) {
            example.setbCategoryId(viewProductSku.getBCategoryId());
        }
        if (viewProductSku.getMCategoryId() != null) {
            example.setmCategoryId(viewProductSku.getMCategoryId());
        }
        String proName =
                viewProductSku.getProcuctName() == null || viewProductSku.getProcuctName().isEmpty()
                        ? "%%" : "%" + viewProductSku.getProcuctName() + "%";

        String proSkuNo = viewProductSku.getProductSkuCode() == null
                || viewProductSku.getProductSkuCode().isEmpty() ? "%%"
                        : "%" + viewProductSku.getProductSkuCode() + "%";
        // 其他地方校验新增
        Long categoryId = viewProductSku.getCategoryId() == null
                || viewProductSku.getCategoryId().intValue() == 0 ? null
                        : viewProductSku.getCategoryId();
        // 商家类别
        Integer supplierType = viewProductSku.getSupplierType() == null
                || viewProductSku.getSupplierType().intValue() == 0 ? null
                        : viewProductSku.getSupplierType();
        // 入驻商家code
        String shop = viewProductSku.getShopCode() == null || viewProductSku.getShopCode().isEmpty()
                ? null : viewProductSku.getShopCode();
        List<String> shopCode = new ArrayList<String>();
        Criteria c = example.createCriteria();
        c.andProcuctNameLike(proName).andProductSkuCodeLike(proSkuNo);
        c.andSkuStatusEqualTo("1");
        if (shop != null) {
            String[] a = shop.split(",");
            for (int i = 0; i < a.length; i++) {
                shopCode.add(a[i]);
            }
            c.andShopCodeIn(shopCode);
        }

        if (supplierType != null) {
            c.andSupplierTypeEqualTo(supplierType);
        }
        if (categoryId != null) {
            c.andCategoryIdEqualTo(categoryId);
        }
        if (viewProductSku.getEditCode() == 1) {
            if ("B2B".equals(type)) {
                c.andNewStatusEqualTo(ProductStatus.UP.getStatus());
            }
            if (viewProductSku.getStatus() != null && !viewProductSku.getStatus().equals("")) {
                c.andNewStatusEqualTo(viewProductSku.getStatus());
            }
        } else {
            if ("B2B".equals(type)) {
                c.andStatusEqualTo(ProductStatus.UP.getStatus());
            }
            if (viewProductSku.getStatus() != null && !viewProductSku.getStatus().equals("")) {
                c.andStatusEqualTo(viewProductSku.getStatus());
            }
        }


        if (viewProductSku.getProductNo() != null && !viewProductSku.getProductNo().isEmpty()) {
            c.andProductNoLike(viewProductSku.getProductNo());
        }

        example.setOrderByClause(" PRODUCT_ID DESC, PRODUCT_SKU_ID desc ");

        try {
            example.setUserId(String.valueOf(userId));
            int count = viewProductSkuDao.countByExample(example);
            List<ViewProductSku> list = viewProductSkuDao.selectByExample(example, skip, max);
            page.setDataList(list);
            page.setRecordCount(count);
        } catch (SQLException e) {
            logger.error("searchShelfByPageByUser异常：", e);
            throw e;
        }
    }

    @Override
    public List<ViewProductSku> queryProductSkuBycodes(String skus) throws Exception {
        List<ViewProductSku> listProduct = viewProductSkuDao.queryProductSkuBycodes(skus);
        return listProduct;
    }
}
