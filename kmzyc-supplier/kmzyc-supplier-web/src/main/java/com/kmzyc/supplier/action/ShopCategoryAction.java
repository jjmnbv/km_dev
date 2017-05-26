package com.kmzyc.supplier.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.km.framework.page.Pagination;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.supplier.model.ShopCategorys;
import com.kmzyc.supplier.model.ShopMain;
import com.kmzyc.supplier.service.ProductMainService;
import com.kmzyc.supplier.service.ShopCategoryService;
import com.kmzyc.supplier.service.ShopProductCategoryService;
import com.pltfm.app.vobject.Product;

/**
 * 店内分类管理 请求处理类
 *
 * @author Administrator
 */
@Controller("shopCategoryAction")
@Scope("prototype")
public class ShopCategoryAction extends SupplierBaseAction {

    private static Logger logger = LoggerFactory.getLogger(ShopCategoryAction.class);

    @Resource
    private ShopCategoryService shopCategoryService;

    @Resource(name = "productMainService")
    private ProductMainService productMainService;

    @Resource(name = "shopProductCategoryService")
    private ShopProductCategoryService shopProductCategoryService;

    /**
     * 新增以及修改的参数实体
     */
    private ShopCategorys shopCategory;

    /**
     * 店铺ID
     */
    private Long shopId;

    /**
     * 类目级别
     */
    private String categoryLevel;

    /**
     * 父级类目名称
     */
    private String parentCategoryName;

    /**
     * 为树控件存储的数据
     */
    private String jsonDataForTreeNode;

    /**
     * 新增时做父类id传参
     */
    private long categoryId;

    /**
     * 是否做推荐
     */
    private String isSuggest;

    /**
     * 修改时用于是否重名标识
     */
    private String originalName;

    /**
     * 交换序号时所用的类目id和排序号
     */
    private long targetCategoryId;

    private int sourceSortNo;

    private int targetSortNo;

    private ShopMain shopMain;

    /**
     * 产品查询参数
     */
    private Product productPara;

    /**
     * 存储一连串的店内分类ID,用于未分类商品和已分类商品
     */
    private String shopCategoryIds;

    /**
     * tab 的显示内容
     */
    private String showType;

    /**
     * 提示信息
     */
    private String msgTip;

    private int originalSortNo;


    /**
     * 查询所有分类
     *
     * @return
     */
    public String showShopCategoryList() {
        if (shopCategory == null) {
            shopCategory = new ShopCategorys();
        }

        // 如果为空
        if (shopId == null) {
            Map<String, Object> shopInfoMap = getShopMainBySupplierId(getSupplyId());
            if (!shopInfoMap.containsKey("shopId")) {
                msgTip = "noShop";
                return SUCCESS;
            }
            shopId = MapUtils.getLong(shopInfoMap, "shopId");
        }


        shopCategory.setShopCategoryId(null);
        shopCategory.setShopId(shopId);
        showType = "categoryList";
        try {
            List<ShopCategorys> list = shopCategoryService.queryShopCategoryList(shopCategory);
            jsonDataForTreeNode = JSON.toJSONString(list);
        } catch (Exception e) {
            logger.error("ShopCategoryAction的showShopCategoryList查询类目树发生异常===>" + e.getMessage(), e);
            e.printStackTrace();
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 去到类目新增页面
     *
     * @return
     */
    public String toShopCategoryAdd() {
        Map<String, Object> jsonMap = new HashMap();
        // 若为一级类目新增,则设置其父类ID为0,不是则查库
        if (shopCategory == null) {
            shopCategory = new ShopCategorys();
        }
        if ("1".equals(categoryLevel)) {
            shopCategory.setCategoryLevel(1);
            shopCategory.setParentCategoryId((long) 0);
        } else {
            try {
                ShopCategorys parent = shopCategoryService.queryCategoryById(categoryId);
                shopCategory.setCategoryCode(parent.getCategoryCode());
                shopCategory.setParentCategoryId(parent.getShopCategoryId());
                shopCategory.setCategoryLevel(2);
                shopCategory.setIsSuggest(parent.getIsSuggest()); // 是否推荐的值根据父类的来
                isSuggest = parent.getIsSuggest().toString();
                parentCategoryName = parent.getCategoryName();

                jsonMap.put("categoryCode", shopCategory.getCategoryCode());
                jsonMap.put("isSuggest", isSuggest);
                jsonMap.put("parentCategoryName", parentCategoryName);
                jsonMap.put("parentIsExpand", parent.getIsExpandAll());
            } catch (Exception e) {
                logger.error("ShopCategoryAction的toShopCategoryAdd查询父级类目发生异常===>" + e.getMessage(), e);
                return ERROR;
            }
        }

        jsonMap.put("categoryLevel", shopCategory.getCategoryLevel());
        jsonMap.put("parentCategoryId", shopCategory.getParentCategoryId());
        jsonMap.put("shopId", shopId);
        jsonMap.put("isDefault", shopCategory.getIsDefault());
        writeJson(jsonMap);
        return null;
    }

    /**
     * 去准备更新的页面
     *
     * @return
     */
    public String toShopCategoryUpdate() {
        Map jsonMap = new HashMap();
        try {
            shopCategory = shopCategoryService.queryCategoryById(categoryId);
            originalName = shopCategory.getCategoryName(); // 标识修改的时候是否重名
            originalSortNo = shopCategory.getSortno(); // 标识修改的时候是否覆盖排序号
            if (shopCategory.getParentCategoryId() > 0) {
                isSuggest = shopCategoryService.queryCategoryById(shopCategory.getParentCategoryId())
                        .getIsSuggest().toString();
                jsonMap.put("parentSuggest", isSuggest);
            }

            // 此处可以查询和该类目同一级别的其它类目的所有排序号,为了避免排序号输成一样的,导致排序不正常
            jsonMap.put("originalName", originalName);
            jsonMap.put("originalSortNo", originalSortNo);
            jsonMap.put("shopCategory", JSON.toJSON(shopCategory));
        } catch (Exception e) {
            logger.error("ShopCategoryAction的toUpdateShopCategory查询方法发生异常=====>" + e.getMessage(), e);
            return ERROR;
        }
        writeJson(jsonMap);
        return null;
    }

    /**
     * 更新店内类目信息
     *
     * @return
     */
    public String updateShopCategoryByAjax() {
        Map jsonMap = new HashMap();
        String result = "true";
        String key = "result";
        try {
            shopCategory.setModifUser(super.getLoginUserId());
            shopCategory.setModifTime(new Date());

            // 如果名字未修改并且排序号也未修改直接 修改全部的
            if (originalName.equals(shopCategory.getCategoryName()) && originalSortNo == shopCategory.getSortno()) {
                shopCategoryService.updateShopCategoryList(shopCategory);
                shopCategory = null;

                jsonMap.put(key, result);
                writeJson(jsonMap);
                return null;
            }

            // 类目名若是修改需要验证名称是否重复
            if (!originalName.equals(shopCategory.getCategoryName())) {
                boolean isRepeat = shopCategoryService.queryIsExistCategoryName(shopCategory);
                if (isRepeat) {
                    jsonMap.put(key, "repeat");
                    writeJson(jsonMap);
                    return null;
                }
            } else if (originalSortNo == shopCategory.getSortno()) {
                // 可以直接修改
                shopCategoryService.updateShopCategoryList(shopCategory);
                shopCategory = null;

                jsonMap.put(key, result);
                writeJson(jsonMap);
                return null;
            }

            boolean isExistSortNo = shopCategoryService.queryIsExistSortNo(shopCategory);
            if (isExistSortNo) {
                jsonMap.put(key, "repeat_sort");
                writeJson(jsonMap);
                return null;
            }

            // 不重名则修改
            shopCategoryService.updateShopCategoryList(shopCategory);
            shopCategory = null;
        } catch (Exception e) {
            result = "error";
            logger.error("ShopCategoryAction的updateShopCategory方法发生异常=====>" + e.getMessage(), e);
        }

        jsonMap.put(key, result);
        writeJson(jsonMap);
        return null;
    }

    /**
     * 删除店内分类类目
     *
     * @return
     */
    public String deleteShopCategoryByAjax() {
        Map<String, Object> jsonMap = new HashMap();
        String key = "result";
        String result = "true";

        shopCategory.setSupplierId(getSupplyId().toString());
        try {
            String resultFlag = shopCategoryService.isRelatedWithProduct(shopCategory);
            // 若是无引用,则可删
            if ("none".equals(resultFlag)) {
                shopCategoryService.deleteShopCategory(shopCategory);
            } else {
                result = resultFlag;
            }
        } catch (Exception e) {
            result = "error";
            logger.error("ShopCategoryAction的deleteShopCategory方法发生异常=====>" + e.getMessage(), e);
        }

        jsonMap.put(key, result);
        writeJson(jsonMap);
        return null;
    }

    /**
     * 保存类目信息
     *
     * @return ajax
     */
    public String saveShopCategoryByAjax() {
        Map jsonMap = new HashMap();
        String result = "true";
        String key = "result";
        try {
            // shopCategory需要包含父级类目id以及店铺id,以及需要查询的名称
            shopCategory.setShopId(shopId);
            boolean flag = shopCategoryService.queryIsExistCategoryName(shopCategory);
            boolean isExistSortNo = shopCategoryService.queryIsExistSortNo(shopCategory);
            if (flag) {
                result = "repeat"; // 名字重复
            } else if (isExistSortNo) {
                result = "repeat_sort"; // 排序号重复
            } else {
                // 如果名字不重复,则可以新增
                shopCategory.setModifUser(this.getLoginUserId()); // 获取当前的创建/修改者
                shopCategory.setIsSuggest(Integer.valueOf(isSuggest));
                shopCategoryService.saveShopCategory(shopCategory);
                jsonMap.put("shopId", shopCategory.getShopId());
                shopCategory = null;
            }
            showType = "categoryList";
        } catch (Exception e) {
            result = "error";
            logger.error("ShopCategoryAction的isExistName方法发生异常=====>" + e.getMessage(), e);
        }
        jsonMap.put(key, result);
        writeJson(jsonMap);
        return null;
    }

    /**
     * 更新排序号 ajax
     *
     * @return
     */
    public String updateSortNoByAjax() {
        Map jsonMap = new HashMap();
        String result = "true";
        String key = "result";

        try {
            shopCategoryService.updateShopCategorySortNo(categoryId, sourceSortNo, targetCategoryId, targetSortNo);
        } catch (Exception e) {
            result = "error";
            logger.error("ShopCategoryAction的updateSortNoByAjax方法发生异常=====>" + e.getMessage(), e);
        }
        jsonMap.put(key, result);
        writeJson(jsonMap);
        return null;
    }

    /**
     * 店内分类管理 一开始加载 显示店内分类管理 加载所有店内分类以及未分类和已分类商品
     *
     * @return
     */
    public String toShopCategoryManage() {
        try {
            showType = "categoryList";
            // 获得店内所有分类
            if (shopCategory == null) {
                shopCategory = new ShopCategorys();
            }

            shopCategory.setShopCategoryId(null);
            shopCategory.setShopId(shopId);
            List<ShopCategorys> list = shopCategoryService.queryShopCategoryList(shopCategory);
            if (list == null || list.size() < 1) {
                jsonDataForTreeNode = "none";
            } else {
                jsonDataForTreeNode = JSON.toJSONString(list);
            }
            this.pagintion = this.queryUnrelationProductList();
            getRequest().setAttribute("relationList", queryRelationProductList().getRecordList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    /**
     * 将产品和店内分类相关联
     *
     * @return
     */
    public String relationProductWithShopCategoryByAjax() {
        Map jsonMap = new HashMap();
        String result = "true";
        String key = "result";
        try {
            // 如果字段为空,则说明要清空之前的店内分类
            if (StringUtils.isBlank(shopCategoryIds)) {
                shopProductCategoryService.deleteByProductId(productPara.getId());

                jsonMap.put(key, result);
                writeJson(jsonMap);
                return null;
            }

            shopProductCategoryService.updateByProductId(productPara.getId(), StringUtils.split(shopCategoryIds, ","));
        } catch (Exception e) {
            result = "error";
            logger.error("ShopCategoryAction的relationProductWithShopCategoryByAjax方法发生异常=====>" + e.getMessage(), e);
        }
        jsonMap.put(key, result);
        writeJson(jsonMap);
        return null;
    }


    /**
     * 分页查询未分类商品和已分类商品的公共方法
     * <p>
     *      改版后只查询已分类商品(已分类商品包含所有的已上架的,过滤掉已删除的),包括默认分类商品在内
     *      需求要求重新将未分类和已分类都加载出来
     * </p>
     * @return
     */
    public String showProductListForShopCategory() {
        try {
            if (shopCategory == null) {
                shopCategory = new ShopCategorys();
            }

            shopCategory.setShopCategoryId(null);
            shopCategory.setShopId(shopId);
            List<ShopCategorys> list = shopCategoryService.queryShopCategoryList(shopCategory);
            if (list == null || list.size() < 1) {
                jsonDataForTreeNode = "none";
            } else {
                jsonDataForTreeNode = JSON.toJSONString(list);
            }

            if ("relationList".equals(showType)) {
                this.pagintion = queryRelationProductList();
                getRequest().setAttribute("relationList", pagintion.getRecordList());
                // 未分类也要查一次
                pagintion.setRecordList(queryUnrelationProductList().getRecordList());
            } else if ("unRelationList".equals(showType)) {
                pagintion = queryUnrelationProductList();
                // 已分类商品也查一次
                Pagination relationList = this.queryRelationProductList();
                getRequest().setAttribute("relationList", relationList.getRecordList());
            }
        } catch (Exception e) {
            logger.error("ShopCategoryAction的showProductListForShopCategory方法发生异常=====>" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }


    /**
     * 该类的方法查询未关联的商品列表
     * <note>
     *     该方法暂未启用
     * </note>
     *
     * @return
     */
    private Pagination queryUnrelationProductList() {
        Pagination page = getPagination(Constants.VIEW_PAGE, Integer.parseInt(super.getPageSize()));
        if (productPara == null) productPara = new Product();
        productPara.setShopCode(getSupplyId().toString());
        try {
            return productMainService.queryUnRelationWithShopCategoryPage(productPara, page);
        } catch (Exception e) {
            logger.error("ShopCategoryAction的queryUnrelationProductList方法发生异常=====>" + e.getMessage(), e);
            return null;
        }
    }

    /**
     * 该类的公共方法查询已关联的商品列表 20150911 update 改版后该方法暂未启用
     *
     * @return
     */
    private Pagination queryRelationProductList() {
        Pagination page = getPagination(Constants.VIEW_PAGE, Integer.parseInt(super.getPageSize()));
        if (productPara == null) productPara = new Product();
        productPara.setShopCode(getSupplyId().toString());
        try {
            return productMainService.queryRelationWithShopCategoryPage(productPara, page);
        } catch (Exception e) {
            logger.error("ShopCategoryAction的queryRelationProductList方法发生异常=====>" + e.getMessage(), e);
            return null;
        }
    }

    public ShopCategorys getShopCategory() {
        return shopCategory;
    }

    public void setShopCategory(ShopCategorys shopCategory) {
        this.shopCategory = shopCategory;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getCategoryLevel() {
        return categoryLevel;
    }

    public void setCategoryLevel(String categoryLevel) {
        this.categoryLevel = categoryLevel;
    }

    public String getParentCategoryName() {
        return parentCategoryName;
    }

    public void setParentCategoryName(String parentCategoryName) {
        this.parentCategoryName = parentCategoryName;
    }

    @Override
    public String getJsonDataForTreeNode() {
        return jsonDataForTreeNode;
    }

    @Override
    public void setJsonDataForTreeNode(String jsonDataForTreeNode) {
        this.jsonDataForTreeNode = jsonDataForTreeNode;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getIsSuggest() {
        return isSuggest;
    }

    public void setIsSuggest(String isSuggest) {
        this.isSuggest = isSuggest;
    }

    public long getTargetCategoryId() {
        return targetCategoryId;
    }

    public void setTargetCategoryId(long targetCategoryId) {
        this.targetCategoryId = targetCategoryId;
    }

    public int getSourceSortNo() {
        return sourceSortNo;
    }

    public void setSourceSortNo(int sourceSortNo) {
        this.sourceSortNo = sourceSortNo;
    }

    public int getTargetSortNo() {
        return targetSortNo;
    }

    public void setTargetSortNo(int targetSortNo) {
        this.targetSortNo = targetSortNo;
    }

    public ShopMain getShopMain() {
        return shopMain;
    }

    public void setShopMain(ShopMain shopMain) {
        this.shopMain = shopMain;
    }

    public Product getProductPara() {
        return productPara;
    }

    public void setProductPara(Product productPara) {
        this.productPara = productPara;
    }

    public String getShopCategoryIds() {
        return shopCategoryIds;
    }

    public void setShopCategoryIds(String shopCategoryIds) {
        this.shopCategoryIds = shopCategoryIds;
    }

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public String getMsgTip() {
        return msgTip;
    }

    public void setMsgTip(String msgTip) {
        this.msgTip = msgTip;
    }

    public int getOriginalSortNo() {
        return originalSortNo;
    }

    public void setOriginalSortNo(int originalSortNo) {
        this.originalSortNo = originalSortNo;
    }

}
