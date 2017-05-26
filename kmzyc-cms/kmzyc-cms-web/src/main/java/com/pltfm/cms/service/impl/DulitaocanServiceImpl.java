package com.pltfm.cms.service.impl;

import com.pltfm.app.dao.ViewProductInfoDAO;
import com.pltfm.app.util.ListUtils;
import com.pltfm.app.util.StringUtil;
import com.pltfm.app.vobject.ProductImage;
import com.pltfm.app.vobject.ProductRelation;
import com.pltfm.app.vobject.ViewProductInfo;
import com.pltfm.app.vobject.ViewSkuAttr;
import com.pltfm.cms.dao.DulitaocanDAO;
import com.pltfm.cms.service.DulitaocanService;
import com.pltfm.cms.vobject.CmsProductRelation;
import com.pltfm.cms.vobject.ProductRelationDetail;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

@Component("dulitaocanService")
public class DulitaocanServiceImpl implements DulitaocanService {

    @Resource(name = "dulitaocanDAO")
    private DulitaocanDAO dulitaocanDAO;

    public List selectRelationDetail(Long relationId) throws Exception {
        return dulitaocanDAO.selectRelationDetail(relationId);
    }

    public List selectSku(Long productSkuId) throws Exception {
        return dulitaocanDAO.selectSku(productSkuId);
    }

    public List selectImg(Long productSkuId) throws Exception {
        return dulitaocanDAO.selectImg(productSkuId);
    }

    public List selectRelation(ProductRelation productRelation) throws Exception {
        return dulitaocanDAO.selectRelation(productRelation);
    }

    /**
     * 产品视图DOA接口
     */
    @Resource(name = "viewProductInfoDAO")
    private ViewProductInfoDAO viewProductInfoDAO;


    /***
     * 详细套餐
     * */
    public List<CmsProductRelation> selectRelationDetailAll(Long skuId, String webSite) throws Exception {
        List<CmsProductRelation> cmsRelationListAll = new ArrayList();
        List<ProductRelationDetail> list = new ArrayList();
        //跟据SKUID查子表拿到套餐ID（从表中SKUID）
        //	List<ProductRelationDetail> RelationList = dulitaocanDAO.selectRelationAll(skuId);
        //	if(ListUtils.isNotEmpty(RelationList)){
        //	for (int is = 0; is < RelationList.size(); is++) {
        //ProductRelationDetail pr= RelationList.get(is);
        //通过从表中SKUID和主表中渠道ID
        CmsProductRelation r = new CmsProductRelation();
        //	r.setRelationId(pr.getRelationId());
        r.setMainSkuId(skuId);
        //r.setWebSite(StringUtil.packChannel(webSite));
        //查询套餐中的主表
        List<CmsProductRelation> cmsRelationList = dulitaocanDAO.selectCmsRelation(r);
        if (ListUtils.isNotEmpty(cmsRelationList)) {
            for (int ji = 0; ji < cmsRelationList.size(); ji++) {
                //套餐对象
                CmsProductRelation p = cmsRelationList.get(ji);
                // 套餐从表
                List<ProductRelationDetail> productRelationDetailList = dulitaocanDAO.selectRelationDetail(p.getRelationId());
                if (ListUtils.isNotEmpty(productRelationDetailList)) {

                    for (ProductRelationDetail productRelationDetail : productRelationDetailList) {

                        productRelationDetail.setTotalRelationPrice(p.getTotalRelationPrice());
                        List<ViewSkuAttr> v = dulitaocanDAO.selectSku(productRelationDetail.getRelationSkuId());

                        if (ListUtils.isNotEmpty(v)) {
                            productRelationDetail.setViewSkuAttrList(v);
                        }

                        int id = productRelationDetail.getRelationSkuId().intValue();
                        ViewProductInfo viewProductInfo = new ViewProductInfo();
                        viewProductInfo.setProductSkuId(id);
                        ViewProductInfo product = viewProductInfoDAO.selectSkuId(viewProductInfo);
                        if (product != null) {
                            productRelationDetail.setProduct(product);
                        }

                        List<ProductImage> Img = dulitaocanDAO.selectImg(productRelationDetail.getRelationSkuId());

                        if (ListUtils.isNotEmpty(Img)) {
                            productRelationDetail.setProductImageList(Img);
                        }
                        productRelationDetail.setRelationName(p.getRelationName());
                        productRelationDetail.setTotalRelationPrice(p.getTotalRelationPrice());
                        list.add(productRelationDetail);

                    }
                    p.setRelationDetailList(list);
                }
                cmsRelationListAll.add(p);
            }

        }
        //}
        //}
        return cmsRelationListAll;
    }


    public DulitaocanDAO getDulitaocanDAO() {
        return dulitaocanDAO;
    }

    public void setDulitaocanDAO(DulitaocanDAO dulitaocanDAO) {
        this.dulitaocanDAO = dulitaocanDAO;
    }

    public ViewProductInfoDAO getViewProductInfoDAO() {
        return viewProductInfoDAO;
    }

    public void setViewProductInfoDAO(ViewProductInfoDAO viewProductInfoDAO) {
        this.viewProductInfoDAO = viewProductInfoDAO;
    }
}
