package com.pltfm.cms.action;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.kmzyc.supplier.model.ShopCategorys;
import com.pltfm.app.service.ShopCategorysService;
import com.pltfm.cms.parse.PathConstants;
import com.pltfm.cms.service.CmsShopDataService;
import com.pltfm.cms.service.CmsWindowDataService;
import com.pltfm.cms.vobject.CmsShopData;
import com.pltfm.cms.vobject.CmsWindowData;

@Component(value = "productToptenzAction")
@Scope(value = "prototype")
public class ProductToptenzAction extends BaseAction {
	private static Logger logger = LoggerFactory.getLogger(ProductToptenzAction.class);

    @Resource(name = "cmsShopDataService")
    private CmsShopDataService cmsShopDataService;

    @Resource(name = "cmsWindowDataService")
    private CmsWindowDataService cmsWindowDataService;

    @Resource(name = "shopCategorysService")
    private ShopCategorysService shopCategorysService;

    public CmsShopData cmsShopData;

    public Long windowId;

    // 店铺id
    public Long shopId;
    public Long shopI;

    // 商品排行id
    public Long shopDataId;

    public CmsWindowData cmsWindowData;

    public String result;

//    public List<ShopCategorys> ShopCategorysList;

    private String categoryString;


    // 跳转至添加商品排行页面
    public String toAddProductToptenz() {

        try {
            // 当为修改时
            List<ShopCategorys> shopCategorysForTree = shopCategorysService.getAllShopCategorysForTree(shopId);
            ShopCategorys s = new ShopCategorys();
            s.setCategoryName("全部类目");
            s.setParentCategoryId(0l);
            s.setShopCategoryId(0l);
            shopCategorysForTree.add(s);

            categoryString = JSON.toJSONString(shopCategorysForTree);
            CmsWindowData cmsWindowDataQuery = new CmsWindowData();
            cmsWindowDataQuery.setWindowId(Integer.valueOf(windowId.toString()));
            cmsWindowDataQuery.setDataType(6);
            List<CmsWindowData> l = cmsWindowDataService.queryWindowDataList(cmsWindowDataQuery);
            if (l.size() > 0) {

                cmsShopData = cmsShopDataService.selectById(Long.valueOf(l.get(0).getDataId()));
                cmsWindowData = l.get(0);

            }
        } catch (NumberFormatException e) {
            logger.error("ProductToptenzAction.toAddProductToptenz异常：" + e.getMessage(), e);
        } catch (SQLException e) {
            logger.error("ProductToptenzAction.toAddProductToptenz异常：" + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("ProductToptenzAction.toAddProductToptenz异常：" + e.getMessage(), e);
        }
        return "success";
    }

    // 添加商品排行
    public String addProductToptenz() {
        // String result= "";
        String content = "";
        if (cmsShopData != null) {
            if (cmsShopData.getIsShow() == null || cmsShopData.getIsShow() != 1) {
                cmsShopData.setIsShow(0);
            }
            if (cmsShopData.getKeyword() != null) {
                content = content + cmsShopData.getKeyword();
            }
        }
        if (cmsWindowData != null) {
            if (cmsWindowData.getUser_defined_url() == null
                    || !cmsWindowData.getUser_defined_url().equals("1")) {
                cmsWindowData.setUser_defined_url("0");
            }
            if (cmsWindowData.getUser_defined_name() != null) {
                content = content + cmsWindowData.getUser_defined_name();
            }
        }


        boolean b = PathConstants.checkKeyWords(content);

        if (b) {
            result = "error";
            return toAddProductToptenz();
        } else {
            result = "success";
        }


        try {
            // 更新
            if (cmsShopData.getShopDataId() != null) {
                cmsShopDataService.update(cmsShopData);
                cmsWindowDataService.updateSort(cmsWindowData);

            } else {// 新增
                Integer id = cmsShopDataService.insertShopData(cmsShopData);
                cmsWindowData.setDataId(id);
                cmsWindowData.setDataType(6);
                cmsWindowDataService.addCmsWindowData(cmsWindowData);
                cmsWindowData.setDataType(12);
                cmsWindowDataService.addCmsWindowData(cmsWindowData);

            }

        } catch (SQLException e) {
            logger.error("ProductToptenzAction.addProductToptenz异常：" + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("ProductToptenzAction.addProductToptenz异常：" + e.getMessage(), e);
        }
        return toAddProductToptenz();

    }

    // 全部商品
    public String toAddAllProducts() {
        try {
            // 当为修改时

            List<ShopCategorys> shopCategorysForTree = shopCategorysService.getAllShopCategorysForTree(getShopId());
            categoryString = JSON.toJSONString(shopCategorysForTree);
            CmsWindowData cmsWindowDataQuery = new CmsWindowData();
            cmsWindowDataQuery.setWindowId(Integer.valueOf(windowId.toString()));
            cmsWindowDataQuery.setDataType(6);
            List<CmsWindowData> l = cmsWindowDataService.queryWindowDataList(cmsWindowDataQuery);
            if (l.size() > 0) {

                cmsShopData = cmsShopDataService.selectById(Long.valueOf(l.get(0).getDataId()));
                cmsWindowData = l.get(0);

            }
        } catch (NumberFormatException e) {
            logger.error("ProductToptenzAction.toAddAllProducts异常：" + e.getMessage(), e);
        } catch (SQLException e) {
            logger.error("ProductToptenzAction.toAddAllProducts异常：" + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("ProductToptenzAction.toAddAllProducts异常：" + e.getMessage(), e);
        }
        return "success";

    }

    // 添加全部商品
    public String addAllProducts() {
        Assert.notNull(cmsWindowData);
        if (cmsWindowData.getUser_defined_url() == null
                || !cmsWindowData.getUser_defined_url().equals("1")) {
            cmsWindowData.setUser_defined_url("0");
        }
        try {
            // 更新
            if (cmsShopData.getShopDataId() != null) {
                cmsShopDataService.update(cmsShopData);
                cmsWindowDataService.updateSort(cmsWindowData);
            } else {// 新增
                Integer id = cmsShopDataService.insertShopData(cmsShopData);
                cmsWindowData.setDataId(id);
                cmsWindowData.setDataType(6);
                cmsWindowDataService.addCmsWindowData(cmsWindowData);
                cmsWindowData.setDataType(12);
                cmsWindowDataService.addCmsWindowData(cmsWindowData);
            }

        } catch (Exception e) {
            logger.error("ProductToptenzAction.addAllProducts异常：" + e.getMessage(), e);
            return "error";
        }
        result = "success";
        windowId = Long.valueOf(cmsWindowData.getWindowId().toString());
        return toAddAllProducts();
    }

    public CmsShopData getCmsShopData() {
        return cmsShopData;
    }

    public void setCmsShopData(CmsShopData cmsShopData) {
        this.cmsShopData = cmsShopData;
    }

    public Long getWindowId() {
        return windowId;
    }

    public void setWindowId(Long windowId) {
        this.windowId = windowId;
    }

    public CmsWindowData getCmsWindowData() {
        return cmsWindowData;
    }

    public void setCmsWindowData(CmsWindowData cmsWindowData) {
        this.cmsWindowData = cmsWindowData;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getShopDataId() {
        return shopDataId;
    }

    public void setShopDataId(Long shopDataId) {
        this.shopDataId = shopDataId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

//    public List<ShopCategorys> getShopCategorysList() {
//        return ShopCategorysList;
//    }

//    public void setShopCategorysList(List<ShopCategorys> shopCategorysList) {
//        ShopCategorysList = shopCategorysList;
//    }

    public String getCategoryString() {
        return categoryString;
    }

    public void setCategoryString(String categoryString) {
        this.categoryString = categoryString;
    }

    public Long getShopI() {
        return shopI;
    }

    public void setShopI(Long shopI) {
        this.shopI = shopI;
    }

}
