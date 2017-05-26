package com.kmzyc.promotion.app.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.dao.ProductmainTiedDAO;
import com.kmzyc.promotion.app.service.ProductMainTiedService;
import com.kmzyc.promotion.app.vobject.ProductmainTied;

@Service("productMainTiedService")
public class ProductMainTiedServiceImpl implements ProductMainTiedService {
    @Resource(name = "productMainTiedDAO")
    private ProductmainTiedDAO productMainTiedDAO;

    public ProductMainTiedServiceImpl() {
        super();
    }

    @Override
    public void selectList(ProductmainTied prod, Page page) throws Exception {

        if (prod.getStatus() != null) {
            if (prod.getStatus().length() == 0) {
                prod.setStatus(null);
            }
        }

        if (prod.getKeyword() != null) {

            if (prod.getKeyword().length() == 0) {
                prod.setKeyword(null);
            }
        }

        if (prod.getProductNo() != null) {

            if (prod.getProductNo().length() == 0) {

                prod.setProductNo(null);
            }
        }

        if (prod.getProductSkuCode() != null) {
            if (prod.getProductSkuCode().length() == 0) {
                prod.setProductSkuCode(null);
            }
        }

        if (prod.getProcuctName() != null) {

            if (prod.getProcuctName().length() == 0) {
                prod.setProcuctName(null);

            }
        }

        if (prod.getBrandId() != null) {
            if (prod.getBrandId().intValue() == 0) {

                prod.setBrandId(null);
            }

        }
        if (prod.getCategoryId() != null) {

            if (prod.getCategoryId().intValue() == 0) {
                prod.setCategoryId(null);

            }

        }

        List<ProductmainTied> list = productMainTiedDAO.selectList(prod, page);
        page.setDataList(list);
        page.setRecordCount(productMainTiedDAO.countItem(prod));

    }

    @Override
    public void selectList(ProductmainTied prod, Page page, Integer userId) throws Exception {
        prod.setUserId(String.valueOf(userId));
        if (prod.getStatus() != null) {
            if (prod.getStatus().length() == 0) {
                prod.setStatus(null);
            }
        }

        if (prod.getKeyword() != null) {

            if (prod.getKeyword().length() == 0) {
                prod.setKeyword(null);
            }
        }

        if (prod.getProductNo() != null) {

            if (prod.getProductNo().length() == 0) {

                prod.setProductNo(null);
            }
        }

        if (prod.getProductSkuCode() != null) {
            if (prod.getProductSkuCode().length() == 0) {
                prod.setProductSkuCode(null);
            }
        }

        if (prod.getProcuctName() != null) {

            if (prod.getProcuctName().length() == 0) {
                prod.setProcuctName(null);

            }
        }
        if (prod.getBrandId() != null) {
            if (prod.getBrandId().intValue() == 0) {

                prod.setBrandId(null);
            }

        }
        if (prod.getCategoryId() != null) {

            if (prod.getCategoryId().intValue() == 0) {
                prod.setCategoryId(null);

            }

        }

        List<ProductmainTied> list = productMainTiedDAO.selectListByUser(prod, page);
        page.setDataList(list);
        page.setRecordCount(productMainTiedDAO.countItemByUser(prod));

    }

    @Override
    public void selectListByRelation(ProductmainTied prod, Page page, Integer userId)
            throws Exception {
        prod.setUserId(String.valueOf(userId));
        if (prod.getStatus() != null) {
            if (prod.getStatus().length() == 0) {
                prod.setStatus(null);
            }
        }

        if (prod.getKeyword() != null) {

            if (prod.getKeyword().length() == 0) {
                prod.setKeyword(null);
            }
        }

        if (prod.getProductNo() != null) {

            if (prod.getProductNo().length() == 0) {

                prod.setProductNo(null);
            }
        }

        if (prod.getProductSkuCode() != null) {
            if (prod.getProductSkuCode().length() == 0) {
                prod.setProductSkuCode(null);
            }
        }

        if (prod.getProcuctName() != null) {

            if (prod.getProcuctName().length() == 0) {
                prod.setProcuctName(null);

            }
        }

        if (prod.getBrandId() != null) {
            if (prod.getBrandId().intValue() == 0) {

                prod.setBrandId(null);
            }

        }
        if (prod.getCategoryId() != null) {

            if (prod.getCategoryId().intValue() == 0) {
                prod.setCategoryId(null);

            }

        }

        List<ProductmainTied> list = productMainTiedDAO.selectListByUserByRelation(prod, page);
        page.setDataList(list);
        page.setRecordCount(productMainTiedDAO.countItemByUserByRelation(prod));

    }

    @Override
    public ProductmainTied findObjectBySku(Long id) throws Exception {
        ProductmainTied product = productMainTiedDAO.selectExampleBySku(id);
        return product;
    }

    @Override
    public int countItem(ProductmainTied prod) throws Exception {

        return productMainTiedDAO.countItem(prod);
    }

    @Override
    public void selectListNotMainSKU(ProductmainTied prod, Page page) throws Exception {
        if (prod.getStatus() != null) {
            if (prod.getStatus().length() == 0) {
                prod.setStatus(null);
            }
        }

        if (prod.getKeyword() != null) {

            if (prod.getKeyword().length() == 0) {
                prod.setKeyword(null);
            }
        }

        if (prod.getProductNo() != null) {

            if (prod.getProductNo().length() == 0) {

                prod.setProductNo(null);
            }
        }

        if (prod.getProductSkuCode() != null) {
            if (prod.getProductSkuCode().length() == 0) {
                prod.setProductSkuCode(null);
            }
        }

        if (prod.getProcuctName() != null) {

            if (prod.getProcuctName().length() == 0) {
                prod.setProcuctName(null);

            }
        }

        if (prod.getBrandId() != null) {
            if (prod.getBrandId().intValue() == 0) {

                prod.setBrandId(null);
            }

        }
        if (prod.getCategoryId() != null) {

            if (prod.getCategoryId().intValue() == 0) {
                prod.setCategoryId(null);

            }

        }
        List<ProductmainTied> list = productMainTiedDAO.selectListExcepMainsku(prod, page);
        page.setDataList(list);
        page.setRecordCount(productMainTiedDAO.countItemExceptMainsku(prod));

    }

    @Override
    public int countItemExceptMainsku(ProductmainTied prod) throws Exception {
        return productMainTiedDAO.countItemExceptMainsku(prod);
    }

    @Override
    public Map<String, ProductmainTied> getProductSkuMapBySkucode(List<String> list)
            throws SQLException {
        return productMainTiedDAO.getProductSkuMapBySkucode(list);

    }

    @Override
    public ProductmainTied getProductmainTied(String skuCode) throws SQLException {

        return productMainTiedDAO.getProductmainTied(skuCode);

    }

    @Override
    public List<ProductmainTied> queryProductMainTiedsBySkuIdList(List<Long> skuList)
            throws SQLException {
        return productMainTiedDAO.queryProductMainTiedsBySkuIdList(skuList);
    }

}
