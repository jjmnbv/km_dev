package com.kmzyc.promotion.app.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.dao.ProductRelationDAO;
import com.kmzyc.promotion.app.enums.ProductRelationDelStatus;
import com.kmzyc.promotion.app.enums.ProductRelationStatus;
import com.kmzyc.promotion.app.service.CmsProductUpShelfService;
import com.kmzyc.promotion.app.service.ProductRelationDetailService;
import com.kmzyc.promotion.app.service.ProductRelationService;
import com.kmzyc.promotion.app.service.ProductService;
import com.kmzyc.promotion.app.vobject.Product;
import com.kmzyc.promotion.app.vobject.ProductRelation;
import com.kmzyc.promotion.app.vobject.ProductRelationAndDetail;
import com.kmzyc.promotion.app.vobject.ProductRelationDetailExample;
import com.kmzyc.promotion.app.vobject.ProductRelationExample;
import com.kmzyc.promotion.app.vobject.ProductSku;
import com.kmzyc.promotion.app.vobject.ProductRelationExample.Criteria;
import com.kmzyc.promotion.exception.ServiceException;

@Component("productRelationService")
@SuppressWarnings("unchecked")
public class ProductRelationServiceImpl implements ProductRelationService {
    // 日志记录
    private static final Logger logger = LoggerFactory.getLogger(ProductRelationServiceImpl.class);
    @Resource
    private ProductRelationDAO productRelationDAO;
    @Resource
    private ProductRelationDetailService productRelationDetailService;
    @Resource
    private ProductService productService;
    @Resource
    private CmsProductUpShelfService cmsProductUpShelfService;

    @Override
    public Long saveProductRelation(ProductRelation productRelation) throws SQLException {

        Long id = productRelationDAO.insertProductRelation(productRelation);
        return id;
    }

    @Override
    public void getProductRelationByMainSkuId(ProductRelation productRelation, Page page)
            throws SQLException {

        ProductRelationExample example = new ProductRelationExample();
        Criteria criteria = example.createCriteria();
        criteria.andMainSkuIdEqualTo(productRelation.getMainSkuId());
        criteria.andDelStatusEqualTo(ProductRelationDelStatus.NOTDEL.getStatus().shortValue());
        Short typeMin = 1;
        Short typeMax = 3;
        criteria.andRelationTypeBetween(typeMin, typeMax);
        example.setOrderByClause("relation_type  asc ,relation_id desc");
        List<ProductRelation> relations = productRelationDAO.selectByExample(example, page);
        int count = productRelationDAO.countByExample(example);
        page.setDataList(relations);
        page.setRecordCount(count);

    }

    @Override
    public void getProductPackageByMainSkuId(ProductRelation productRelation, Page page)
            throws SQLException {

        ProductRelationExample example = new ProductRelationExample();
        Criteria criteria = example.createCriteria();
        criteria.andMainSkuIdEqualTo(productRelation.getMainSkuId());
        criteria.andDelStatusEqualTo(ProductRelationDelStatus.NOTDEL.getStatus().shortValue());
        Short type = 0;
        criteria.andRelationTypeEqualTo(type);
        example.setOrderByClause("relation_type  asc ,relation_id desc");
        List<ProductRelation> relations = productRelationDAO.selectByExample(example, page);
        int count = productRelationDAO.countByExample(example);
        page.setDataList(relations);
        page.setRecordCount(count);

    }

    @Override
    public int batchDelProductRelation(List<Long> detailIdlist) throws SQLException {

        return productRelationDAO.batchDelProductRelation(detailIdlist);
    }

    @Override
    public int batchDelProductRelationDetailByDetailId(List<Long> relationId) throws SQLException {
        return productRelationDAO.batchDelProductRelation(relationId);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public void batchDelPackage(List<Long> relationId) throws ServiceException {

        try {
            productRelationDetailService.batchDelProductRelationDetailByRelationId(relationId);
        } catch (SQLException e) {

            throw new ServiceException(1, "批量删除关联失败");

        }

        try {
            batchDelProductRelation(relationId);
        } catch (SQLException e) {

            throw new ServiceException(1, "批量删除关联失败");
        }

    }

    @Override
    public List<ProductRelationAndDetail> selectProductRelationAndDetailPackageList(Long skuId)
            throws SQLException {

        return productRelationDAO.selectProductRelationAndDetailPackageList(skuId);
    }

    @Override
    public List<ProductRelationAndDetail> selectProductRelationAndDetailRecommendList(Long skuId)
            throws SQLException {
        return productRelationDAO.selectProductRelationAndDetailRecommendList(skuId);
    }


    @Override
    public ProductRelation queryProductRelation(Long relationId) throws SQLException {
        ProductRelationExample example = new ProductRelationExample();
        Criteria criteria = example.createCriteria();
        criteria.andRelationIdEqualTo(relationId);

        List<ProductRelation> list = productRelationDAO.selectByExample(example);

        return list.get(0);
    }

    @Override
    public List<ProductRelationAndDetail> selectProductRelationAndDetailBySkuId(Long skuId)
            throws Exception {
        return productRelationDAO.selectProductRelationAndDetailBySkuId(skuId);
    }

    @Override
    public List<ProductRelationAndDetail> selectProductRelationAndDetailsByRelaitonSkuId(Long skuId)
            throws SQLException {

        return productRelationDAO.selectProductRelationAndDetailsByRelaitonSkuId(skuId);

    }

    @Override
    public List<Long> selectProductRelationAndDetailByProductId(List<Long> productIdList)
            throws Exception {
        return productRelationDAO.selectProductRelationAndDetailByProductId(productIdList);
    }

    @Override
    public void updateProductRelationTotalRelaitonPriceByRelationId(ProductRelation relation)
            throws SQLException {

        productRelationDAO.updateByPrimaryKey(relation);

    }

    @Override
    public int batchDelProductRelationStatus(List<Long> relations) throws SQLException {

        int i = productRelationDAO.batchUpateProductRelationStatus(relations);
        return i;

    }

    @Override
    public String upOrDownShelf(List<Long> ids, String type) throws SQLException {
        String result = "";
        ProductRelation productRelation = new ProductRelation();
        for (int i = 0; i < ids.size(); i++) {
            productRelation = productRelationDAO.selectByPrimaryKey(ids.get(i));
            if (type.trim().equals("up")) {
                productRelation
                        .setStatus(Short.valueOf(ProductRelationStatus.UP.getStatus().toString()));
            } else {
                productRelation.setStatus(
                        Short.valueOf(ProductRelationStatus.DOWN.getStatus().toString()));
            }
            productRelationDAO.updateByPrimaryKey(productRelation);
        }
        if (type.trim().equals("up"))
            result = "上架成功！";
        else
            result = "下架成功！";
        return result;

    }

    @Override
    public int updateProductRelationStatus(Long relationId, String status) throws ServiceException {
        try {
            return productRelationDAO.updateStatus(relationId, status);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int batchUpdateProductRealtionEdible(List<ProductRelation> list) throws SQLException {
        try {
            return productRelationDAO.batchUpdateData(list);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int updateProductRelation(ProductRelation productRelation) throws SQLException {

        // 更新套餐总价
        ProductRelationDetailExample example = new ProductRelationDetailExample();
        com.kmzyc.promotion.app.vobject.ProductRelationDetailExample.Criteria criteria =
                example.createCriteria();
        criteria.andRelationIdEqualTo(productRelation.getRelationId());
        // BigDecimal totalRelationPrice =
        // productRelationDAO.getTotalRelationPrice(example);
        // productRelation.setTotalRelationPrice(totalRelationPrice);
        int i = productRelationDAO.updateProductRelation(productRelation);
        return i;
    }

    @Override
    public int updateProductRelationEditable(Long id, String status) throws SQLException {
        int i = 0;
        if (status.trim().equals("1")) {// 状态变为有效
            i = productRelationDAO.updateEditableByRelationId(id);
        } else {
            i = productRelationDAO.updateUnEditableByRelationId(id);
        }
        return i;

    }

    @Override
    public boolean downShelfPackages(List<Product> list) {
        boolean result = false;
        try {
            Long productId = 0L;
            List<ProductRelationAndDetail> productRelationList = null;
            List<Integer> prodIdList = new ArrayList<Integer>();
            for (int i = 0; i < list.size(); i++) {
                productId = list.get(i).getId();
                List<ProductSku> skuList = productService.getProductSkuAttrList(productId);
                for (ProductSku productSku : skuList) {
                    productRelationList = productRelationDAO
                            .selectProductRelationAndDetailBySkuId(productSku.getProductSkuId());
                    for (ProductRelationAndDetail productRelationAndDetail : productRelationList) {
                        ProductRelation productRelation = productRelationDAO.selectByPrimaryKey(
                                Long.valueOf(productRelationAndDetail.getRelationId()));
                        if (productRelation.getStatus().toString()
                                .equals(ProductRelationStatus.UP.getStatus().toString().trim())) {
                            productRelation.setStatus(Short
                                    .valueOf(ProductRelationStatus.DOWN.getStatus().toString()));
                        }
                        int count = productRelationDAO.updateByPrimaryKey(productRelation);
                        if (count > 0)
                            result = true;
                        else {
                            result = false;
                            return result;
                        }
                    }
                }
                // 调用cms 的页面进行刷新
                prodIdList.add(Integer.valueOf(productId.toString()));
            }
            // 调用cms 的页面进行刷新
            cmsProductUpShelfService.productUpShelfByCms(prodIdList);
        } catch (SQLException e) {
            result = false;
            logger.error("", e);
        } catch (Exception e) {
            logger.error("", e);
        }
        return true;
    }

}
