package com.pltfm.app.service.impl;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.kmzyc.zkconfig.ConfigurationUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pltfm.app.dao.ProdBrandDAO;
import com.pltfm.app.maps.ProductBrandMap;
import com.pltfm.app.service.ProdBrandService;
import com.pltfm.app.vobject.ProdBrand;
import com.pltfm.app.vobject.ProdBrandExample;
import com.pltfm.app.vobject.ProdBrandExample.Criteria;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;

/**
 * 产品品牌业务实现类
 *
 * @author tanyunxing
 */
@Service("prodBrandService")
public class ProdBrandServiceImpl implements ProdBrandService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ProdBrandDAO prodBrandDao;

    @Override
    public void searchPage(Page page, ProdBrand prod) throws ServiceException {
        int pageIndex = page.getPageNo();
        if (pageIndex == 0)
            pageIndex = 1;
        int pageSize = page.getPageSize();
        int skip = (pageIndex - 1) * pageSize;
        int max = pageSize;

        ProdBrandExample exm = new ProdBrandExample();
        Criteria c = exm.createCriteria();
        if (StringUtils.isNotEmpty(prod.getBrandName())) {
            c.andBrandNameLike("%" + prod.getBrandName() + "%");
        }
        if (StringUtils.isNotEmpty(prod.getChnSpell())) {
            c.andChnSpellLike("%" + prod.getChnSpell() + "%");
        }
        if (StringUtils.isNotEmpty(prod.getEngName())) {
            c.andEngNameLike("%" + prod.getEngName() + "%");
        }
        exm.setOrderByClause(" BRAND_ID desc");

        try {
            int count = prodBrandDao.countByExample(exm);
            List<ProdBrand> list = prodBrandDao.selectByExample(exm, skip, max);
            page.setDataList(list);
            page.setRecordCount(count);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void deleteByPrimaryKey(String[] brandId) throws ServiceException {
        if (ArrayUtils.isEmpty(brandId)) {
            logger.error("没有找到要删除的品牌id。");
            return;
        }

        try {
            for (String s : brandId) {
                String str = prodBrandDao.checkProductCounts(Long.valueOf(s));
                if (str != null && !str.isEmpty()) {
                    throw new ServiceException("【" + str + "】品牌下有商品，不能删除！");
                }
            }

            for (String s : brandId) {
                ProdBrand pb = prodBrandDao.selectByPrimaryKey(Long.valueOf(s));
                File f = new File(ConfigurationUtil.getString("pictureUploadPath") + pb.getLogoPath());
                if (f.exists()) {
                    f.delete();
                }
                ProductBrandMap.removeValue(Long.valueOf(s));
            }

            prodBrandDao.deleteByPrimaryKeyBatch(brandId);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public Long addProdBrand(ProdBrand record) throws ServiceException {
        try {
            return prodBrandDao.insert(record);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ProdBrand findProdBrandById(Long id) throws ServiceException {
        try {
            return prodBrandDao.selectByPrimaryKey(id);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void updateProdBrand(ProdBrand prod) throws ServiceException {
        try {
            prodBrandDao.updateByPrimaryKeySelective(prod);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ProdBrand> findAllValidBrand() throws ServiceException {
        ProdBrandExample exm = new ProdBrandExample();
        exm.createCriteria().andIsValidEqualTo("1");

        try {
            return prodBrandDao.selectByExample(exm);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int findRepeatName(String name) throws ServiceException {
        ProdBrandExample exm = new ProdBrandExample();
        exm.createCriteria().andBrandNameEqualTo(name);

        try {
            return prodBrandDao.countByExample(exm);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int findUpdateRepeatName(Long brandId, String name) throws ServiceException {
        ProdBrandExample exm = new ProdBrandExample();
        exm.createCriteria().andBrandNameEqualTo(name).andBrandIdNotEqualTo(brandId);

        try {
            return prodBrandDao.countByExample(exm);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    public boolean checkProdBrandIsFromSupplier(Long brandId) throws ServiceException {
        try {
            return prodBrandDao.checkProdBrandIsFromSupplier(brandId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public int updateProdBrandDraft(ProdBrand prodBrand) throws ServiceException {
        try {
            return prodBrandDao.updateProdBrandDraft(prodBrand);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }
}
