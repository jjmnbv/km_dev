package com.kmzyc.promotion.app.service.impl;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.dao.ProdBrandDAO;
import com.kmzyc.promotion.app.maps.ProductBrandMap;
import com.kmzyc.promotion.app.service.ProdBrandService;
import com.kmzyc.promotion.app.vobject.ProdBrand;
import com.kmzyc.promotion.app.vobject.ProdBrandExample;
import com.kmzyc.promotion.app.vobject.ProdBrandExample.Criteria;
import com.kmzyc.promotion.exception.ServiceException;
import com.kmzyc.zkconfig.ConfigurationUtil;

/**
 * 产品品牌业务实现类
 * 
 * @author tanyunxing
 * 
 */
@Service("prodBrandService")
@SuppressWarnings("unchecked")
public class ProdBrandServiceImpl implements ProdBrandService {
    // 日志记录
    private static final Logger logger = LoggerFactory.getLogger(ProdBrandServiceImpl.class);
    @Resource
    private ProdBrandDAO prodBrandDao;

    @Override
    public void searchPage(Page page, ProdBrand prod) throws Exception {

        int pageIndex = page.getPageNo();

        if (pageIndex == 0)
            pageIndex = 1;

        int pageSize = page.getPageSize();

        int skip = (pageIndex - 1) * pageSize;

        int max = pageSize;

        String brandName = prod.getBrandName() == null || prod.getBrandName().isEmpty() ? "%%"
                : "%" + prod.getBrandName() + "%";

        String chnSpell = prod.getChnSpell() == null || prod.getChnSpell().isEmpty() ? "%%"
                : "%" + prod.getChnSpell() + "%";

        ProdBrandExample exm = new ProdBrandExample();

        Criteria c = exm.createCriteria();

        c.andBrandNameLike(brandName).andChnSpellLike(chnSpell);

        if (prod.getEngName() != null && !prod.getEngName().isEmpty()) {
            c.andEngNameLike("%" + prod.getEngName() + "%");
        }

        exm.setOrderByClause(" BRAND_ID desc");

        try {
            int count = prodBrandDao.countByExample(exm);
            List<ProdBrand> list = prodBrandDao.selectByExample(exm, skip, max);

            page.setDataList(list);
            page.setRecordCount(count);

        } catch (SQLException e) {
            logger.error("调用prodBrandDao.selectByExample方法异常：", e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public void deleteByPrimaryKey(String[] brandId) throws Exception {
        if (brandId != null && brandId.length > 0) {
            for (String s : brandId) {
                String str = prodBrandDao.checkProductCounts(Long.valueOf(s));
                if (str != null && !str.isEmpty()) {
                    throw new Exception("【" + str + "】品牌下有商品，不能删除！");
                }
            }

            for (String s : brandId) {
                ProdBrand pb = prodBrandDao.selectByPrimaryKey(Long.valueOf(s));
                File f = new File(
                        ConfigurationUtil.getString("pictureUploadPath") + pb.getLogoPath());
                if (f.exists()) {
                    f.delete();
                }
                ProductBrandMap.removeValue(Long.valueOf(s));
            }

        }
        prodBrandDao.deleteByPrimaryKeyBatch(brandId);
    }

    @Override
    public Long addProdBrand(ProdBrand record) throws Exception {
        return prodBrandDao.insert(record);
    }

    @Override
    public ProdBrand findProdBrandById(Long id) throws Exception {
        return prodBrandDao.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public void updateProdBrand(ProdBrand prod) throws ServiceException {
        try {
            prodBrandDao.updateByPrimaryKeySelective(prod);
        } catch (Exception e) {
            logger.error("调用prodBrandDao.updateByPrimaryKeySelectiv方法异常：", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ProdBrand> findAllValidBrand() throws Exception {
        ProdBrandExample exm = new ProdBrandExample();
        exm.createCriteria().andIsValidEqualTo("1");
        return prodBrandDao.selectByExample(exm);
    }

    @Override
    public int findRepeatName(String name) throws Exception {
        ProdBrandExample exm = new ProdBrandExample();
        exm.createCriteria().andBrandNameEqualTo(name);
        return prodBrandDao.countByExample(exm);
    }

    @Override
    public int findUpdateRepeatName(Long brandId, String name) throws Exception {
        ProdBrandExample exm = new ProdBrandExample();
        exm.createCriteria().andBrandNameEqualTo(name).andBrandIdNotEqualTo(brandId);
        return prodBrandDao.countByExample(exm);
    }

}
