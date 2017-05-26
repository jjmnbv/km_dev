package com.pltfm.app.service.impl;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.dao.ProductRelationDetailDAO;
import com.pltfm.app.dao.ProductmainTiedDAO;
import com.pltfm.app.service.ProductRelationDetailService;
import com.pltfm.app.vobject.ProductRelationDetail;
import com.pltfm.app.vobject.ProductRelationDetailExample;
import com.pltfm.app.vobject.ProductRelationDetailExample.Criteria;
import com.pltfm.app.vobject.ProductRelationDetailView;
import com.pltfm.app.vobject.ProductmainTied;
import com.kmzyc.commons.page.Page;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component("productRelationDetailService")
public class ProductRelationDetailServiceImpl implements ProductRelationDetailService {

    private static Logger logger = Logger.getLogger(ProductRelationDetailServiceImpl.class);

    @Resource
    private ProductRelationDetailDAO productRelationDetailDao;

    @Resource
    private ProductmainTiedDAO productMainTiedDao;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void saveProductRelationDetail(List<ProductRelationDetail> details) throws ServiceException {
        try {
            productRelationDetailDao.batchSaveProductRelationDetail(details);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ProductRelationDetailView> getProductRelationDetailProductSku(Long id, Page page)
            throws ServiceException {
        ProductRelationDetailExample example = new ProductRelationDetailExample();
        Criteria criteria = example.createCriteria();
        criteria.andRelationIdEqualTo(id);
        example.setOrderByClause("relation_detail_id  desc");

        try {
            int count = productRelationDetailDao.countByExample(example);

            // 获得套餐下所有的关联产品 总的 关联产品数量
            page.setRecordCount(count);

            //分页查询出所有的关联产品
            List<ProductRelationDetail> productRelationList = productRelationDetailDao.selectByExample(example, page);

            List<Long> productSkuIdList = productRelationList.stream().map(ProductRelationDetail::getRelationSkuId)
                    .collect(Collectors.toList());

            Map<Long, ProductmainTied> productMap = productMainTiedDao.getProductSkuMapBySkuId(productSkuIdList);
            List<ProductRelationDetailView> viewList = new ArrayList<ProductRelationDetailView>();
            for (ProductRelationDetail detail : productRelationList) {
                ProductRelationDetailView view = new ProductRelationDetailView();
                ProductmainTied productmainTied = productMap.get(detail.getRelationSkuId());

                view.setRelationDetailId(detail.getRelationDetailId());
                view.setNewPrice(detail.getRelationSkuPrice());
                view.setBrandName(productmainTied.getBrandName());
                view.setPrice(productmainTied.getPrice());//取得的是sku表的价格
                //view.setPrice(detail.getRelationSkuPrice());//取得套餐里面商品的单价
                view.setProcuctName(productmainTied.getProcuctName());
                view.setProductSkuCode(productmainTied.getProductSkuCode());
                view.setProductNo(productmainTied.getProductNo());
                view.setSkuId(productmainTied.getProductSkuId());
                view.setStatus(Integer.parseInt(productmainTied.getStatus()));
                view.setProductTitle(productmainTied.getProductTitle());
                view.setProductSkus(productmainTied.getProductSkus());
                view.setProductCount(detail.getProductCount());
                view.setProdBrand(productmainTied.getProdBrand());
                viewList.add(view);
            }
            page.setDataList(viewList);
            return viewList;
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    public List<ProductRelationDetail> productRelationDetailList(Long productRelationId)
            throws ServiceException {
        ProductRelationDetailExample example = new ProductRelationDetailExample();
        Criteria criteria = example.createCriteria();
        criteria.andRelationIdEqualTo(productRelationId);
        example.setOrderByClause("relation_detail_id  desc");

        try {
            return productRelationDetailDao.selectByExample(example);
        } catch (Exception e) {
            logger.error("根据套餐id查询套餐子信息出现异常" + e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public int batchDelProductRelationDetailByRelationDetailId(List<Long> relationDetailId)
            throws ServiceException {
        try {
            return productRelationDetailDao.batchDelProductRelationDetailByRelationDetailId(relationDetailId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ProductRelationDetail> queryProductRelationDetailByRelationId(Long relationId)
            throws ServiceException {
        ProductRelationDetailExample example = new ProductRelationDetailExample();
        Criteria criteria = example.createCriteria();
        criteria.andRelationIdEqualTo(relationId);

        try {
            return productRelationDetailDao.selectByExample(example);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void updateProductRelationDetailPrice(ProductRelationDetail detail) throws ServiceException {
        try {
            productRelationDetailDao.updateByPrimaryKeySelective(detail);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public int delRelationDetailById(Long detailId) throws ServiceException {
        try {
            return productRelationDetailDao.deleteByPrimaryKey(detailId);
        } catch (Exception e) {
            logger.error("根据套餐子表id删除信息出现异常" + e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

}