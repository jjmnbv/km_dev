package com.pltfm.app.service.impl;

import com.pltfm.app.dao.ProdBrandDAO;
import com.pltfm.app.service.ProdBrandService;
import com.kmzyc.commons.page.Page;
import com.pltfm.app.vobject.ProdBrand;
import com.pltfm.app.vobject.ProdBrandExample;

import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

/**
 * 品牌信息业务逻辑层接口实现类
 *
 * @author cjm
 * @since 2013-9-10
 */
@Component(value = "prodBrandService")
public class ProdBrandServiceImpl implements ProdBrandService {
    /**
     * 品牌信息DAO接口
     */
    @Resource(name = "prodBrandDAO")
    private ProdBrandDAO prodBrandDAO;

    /**
     * 分页查询品牌信息
     *
     * @param prodBrand 品牌信息实体
     * @param page      分页实体
     * @throws Exception 异常
     * @return 返回值
     */
    @Override
    public Page queryForPage(ProdBrand prodBrand, Page page) throws Exception {
        if (page == null) {
            page = new Page();
        }
        if (prodBrand == null) {
            prodBrand = new ProdBrand();
        }
        prodBrand.setIsValid("1");
        // 根据条件获取窗口数据信息总条数
        int totalNum = prodBrandDAO.countByProdBrand(prodBrand);
        if (totalNum != 0) {
            page.setRecordCount(totalNum);
            // 设置查询开始结束索引
            prodBrand.setStartIndex(page.getStartIndex());
            prodBrand.setEndIndex(page.getStartIndex() + page.getPageSize());
            page.setDataList(prodBrandDAO.queryForPage(prodBrand));
        } else {
            page.setRecordCount(0);
            page.setDataList(null);
        }
        return page;
    }

    /**
     * 根据品牌信息主键查询单条品牌信息
     *
     * @param brandId 品牌信息主键
     * @throws SQLException sql异常
     * @return 返回值
     */
    @Override
    public ProdBrand selectByPrimaryKey(Integer brandId) throws Exception {
        return prodBrandDAO.selectByPrimaryKey(brandId);
    }

    /**
     * 根据品牌主键集合查询品牌信息
     *
     * @param dataIds 品牌主键集合
     * @throws Exception sql异常
     * @return 返回值
     */
    @Override
    public List selectByDataIds(List dataIds) throws Exception {
        ProdBrandExample example = new ProdBrandExample();
        example.createCriteria().andBrandIdIn(dataIds).andIsValidEqualTo("1");

        return prodBrandDAO.selectByExample(example);
    }

    /**
     * 根据品牌条件信息查询品牌信息
     *
     * @param example 品牌条件信息
     * @throws Exception sql异常
     * @return 返回值
     */
    @Override
    public List selectByExample(ProdBrandExample example) throws Exception {
        return prodBrandDAO.selectByExample(example);
    }


    /**
     * 通过类目主键与渠道查询同类品牌信息
     *
     * @param prodBrand 类目主键
     * @throws Exception 异常信息
     */
    public List<ProdBrand> selectByCategoryIdAndChannel(ProdBrand prodBrand) throws Exception {
        return prodBrandDAO.selectByCategoryIdAndChannel(prodBrand);
    }

    public ProdBrandDAO getProdBrandDAO() {
        return prodBrandDAO;
    }

    public void setProdBrandDAO(ProdBrandDAO prodBrandDAO) {
        this.prodBrandDAO = prodBrandDAO;
    }


}
