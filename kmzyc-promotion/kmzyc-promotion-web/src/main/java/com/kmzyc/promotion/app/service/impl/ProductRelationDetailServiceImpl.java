package com.kmzyc.promotion.app.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.dao.ProductRelationDetailDAO;
import com.kmzyc.promotion.app.dao.ProductmainTiedDAO;
import com.kmzyc.promotion.app.service.ProductRelationDetailService;
import com.kmzyc.promotion.app.vobject.ProductRelationDetail;
import com.kmzyc.promotion.app.vobject.ProductRelationDetailExample;
import com.kmzyc.promotion.app.vobject.ProductRelationDetailView;
import com.kmzyc.promotion.app.vobject.ProductmainTied;
import com.kmzyc.promotion.app.vobject.ProductRelationDetailExample.Criteria;

@Component("productRelationDetailService")
@SuppressWarnings("unchecked")
public class ProductRelationDetailServiceImpl implements ProductRelationDetailService {

    @Resource
    private ProductRelationDetailDAO productRelationDetailDAO;

    @Resource
    private ProductmainTiedDAO productMainTiedDAO;

    @Override
    public int saveProductRelationDetail(List<ProductRelationDetail> details) throws SQLException {
        int i = productRelationDetailDAO.batchSaveProductRelationDetail(details);

        return i;
    }

    @Override
    public int batchDelProductRelationDetailByRelationId(List<Long> relationId)
            throws SQLException {
        return productRelationDetailDAO.batchDelProductRelationDetailByRelationId(relationId);
    }

    @Override
    public Map<Long, ProductRelationDetail> getProductRelationDetailByRelationId(Long relationId)
            throws SQLException {

        return productRelationDetailDAO.getProductRelationDetailByRelationId(relationId);
    }

    @Override
    public void getProductRelationDetailProductSku(Long id, Page page) throws SQLException {

        ProductRelationDetailExample example = new ProductRelationDetailExample();
        Criteria criteria = example.createCriteria();
        criteria.andRelationIdEqualTo(id);
        example.setOrderByClause("relation_detail_id  desc");
        int count = productRelationDetailDAO.countByExample(example);

        // 获得套餐下所有的关联产品 总的 关联产品数量
        page.setRecordCount(count);

        // 分页查询出所有的关联产品
        List<ProductRelationDetail> productRelationList =
                productRelationDetailDAO.selectByExample(example, page);

        List<Long> productSkuIdList = new ArrayList<Long>();
        for (ProductRelationDetail detail : productRelationList) {
            productSkuIdList.add(detail.getRelationSkuId().longValue());

        }

        Map<Long, ProductmainTied> productMap =
                productMainTiedDAO.getProductSkuMapBySkuId(productSkuIdList);

        List<ProductRelationDetailView> viewList = new ArrayList<ProductRelationDetailView>();

        for (ProductRelationDetail detail : productRelationList) {

            // ProductRelationDetail relationDetail=
            // productRelationMap.get(detail.getRelationSkuId());
            ProductRelationDetailView view = new ProductRelationDetailView();
            ProductmainTied productmainTied = productMap.get(detail.getRelationSkuId().longValue());

            view.setRelationDetailId(detail.getRelationDetailId().longValue());
            view.setNewPrice(detail.getRelationSkuPrice());
            view.setBrandName(productmainTied.getBrandName());
            view.setPrice(productmainTied.getPrice());
            view.setProcuctName(productmainTied.getProcuctName());
            view.setProductSkuCode(productmainTied.getProductSkuCode());
            view.setProductNo(productmainTied.getProductNo());
            view.setSkuId(productmainTied.getProductSkuId());
            view.setStatus(Integer.valueOf(productmainTied.getStatus()));
            viewList.add(view);
        }

        page.setDataList(viewList);

    }

    @Override
    public int batchDelProductRelationDetailByRelationDetailId(List<Long> relationDetailId)
            throws SQLException {

        return productRelationDetailDAO
                .batchDelProductRelationDetailByRelationDetailId(relationDetailId);
    }

    @Override
    public int batchSaveProductRealtionDetail(List<ProductRelationDetail> details)
            throws SQLException {

        return productRelationDetailDAO.batchSaveProductRealtionDetail(details);
    }

    @Override
    public List<ProductRelationDetail> queryProductRelationDetailByRelationId(Long relationId)
            throws SQLException {
        ProductRelationDetailExample example = new ProductRelationDetailExample();
        Criteria criteria = example.createCriteria();
        criteria.andRelationIdEqualTo(relationId);

        List<ProductRelationDetail> list = productRelationDetailDAO.selectByExample(example);
        return list;
    }

    @Override
    public void updateProductRelationDetailPrice(ProductRelationDetail detail) throws SQLException {

        productRelationDetailDAO.updateByPrimaryKeySelective(detail);
    }

    @Override
    public ProductRelationDetail queryProductRelationDetailByDetailId(Long relationId)
            throws SQLException {

        ProductRelationDetailExample example = new ProductRelationDetailExample();
        example.createCriteria().andRelationDetailIdEqualTo(relationId);

        ProductRelationDetail detail =
                (ProductRelationDetail) productRelationDetailDAO.selectByExample(example).get(0);
        return detail;

    }

}
