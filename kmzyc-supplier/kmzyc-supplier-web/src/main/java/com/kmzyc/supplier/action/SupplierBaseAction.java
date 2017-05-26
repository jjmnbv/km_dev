package com.kmzyc.supplier.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.km.framework.action.BaseAction;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.supplier.maps.ApprovalTypeMap;
import com.kmzyc.supplier.maps.DraftTypeMap;
import com.kmzyc.supplier.maps.ExpressStatusMap;
import com.kmzyc.supplier.maps.IsNoticeMap;
import com.kmzyc.supplier.maps.ProductStatusMap;
import com.kmzyc.supplier.maps.ShopAuditStatusMap;
import com.kmzyc.supplier.maps.ShopMainServiceTypeMap;
import com.kmzyc.supplier.maps.ShopMainStatusMap;
import com.kmzyc.supplier.maps.ShopMainTypeMap;
import com.kmzyc.supplier.maps.SiteTypeMap;
import com.kmzyc.supplier.model.ShopCategorys;
import com.kmzyc.supplier.model.ShopMain;
import com.kmzyc.supplier.model.SuppliersAvailableCategorys;
import com.kmzyc.supplier.model.SuppliersInfo;
import com.kmzyc.supplier.service.AreaDictService;
import com.kmzyc.supplier.service.CategoryService;
import com.kmzyc.supplier.service.ProdBrandService;
import com.kmzyc.supplier.service.ShopCategoryService;
import com.kmzyc.supplier.service.ShopMainService;
import com.kmzyc.supplier.service.SupplierCategorysService;
import com.kmzyc.supplier.service.SupplierInfoService;
import com.kmzyc.supplier.service.WareHouseService;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.opensymphony.xwork2.ActionContext;
import com.pltfm.app.vobject.Category;
import com.pltfm.app.vobject.ProdBrand;
import com.pltfm.app.vobject.WarehouseInfo;

public class SupplierBaseAction extends BaseAction {

    @Resource
    private CategoryService categoryService;

    @Resource
    private ShopCategoryService shopCategoryService;

    @Resource
    private AreaDictService areaDictService;

    @Resource
    private SupplierCategorysService supplierCategorysService;

    @Resource
    private ShopMainService shopMainService;

    @Resource
    protected ProdBrandService prodBrandService;

    @Resource
    private WareHouseService warehouseService;

    @Resource
    private SupplierInfoService supplierInfoService;

    // 店内分类的树节点数据
    private String jsonDataForTreeNode;

    private static Logger logger = LoggerFactory.getLogger(SupplierBaseAction.class);

    // CSS/JS服务器路径
    private final String cssJsPath = ConfigurationUtil.getString("CSS_JS_PATH");

    // 图片预览时的绝对路径
    private final String imagePath = ConfigurationUtil.getString("picturePreviewPath");

    // 用户头像预览地址
    private final String userImagePath = ConfigurationUtil.getString("USER_IMG_PATH");

    // 资质文件预览路径
    private final String supplierPath = ConfigurationUtil.getString("supplierPreviewPath");

    private String pageSize = "10";// 每页显示的大小

    private int pageSize1 = 10;

    protected String json;

    public Map getSessionMap() {
        return ActionContext.getContext().getSession();
    }

    @Override
    public HttpSession getSession() {
        return ServletActionContext.getRequest().getSession();
    }

    /**
     * 获取登陆ID
     *
     * @return
     */
    public Long getLoginUserId() {
        return (Long) getSession().getAttribute(Constants.SESSION_USER_ID);
    }

    /**
     * 获取登陆用户名
     *
     * @return
     */
    public String getLoginUserName() {
        return (String) getSession().getAttribute(Constants.SESSION_USER_NAME);
    }

    /**
     * 获取登陆供应商ID
     *
     * @return
     */
    public Long getSupplyId() {
        return (Long) getSession().getAttribute(Constants.SESSION_SUPPLIER_ID);
    }

    /**
     * 获取登陆供应商名
     *
     * @return
     */
    public String getMerchantName() {
        return (String) getSession().getAttribute(Constants.SESSION_SUPPLIER_COMPANY);
    }

    protected void setProductBrandMap() {
        try {
            List<ProdBrand> list = prodBrandService.findAllValidBrand();
            Map<Long, String> map = new LinkedHashMap();
            for (ProdBrand brand : list) {
                map.put(brand.getBrandId(), brand.getBrandName());
            }
            getRequest().setAttribute("productBrandMap", map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void setProductStatusMap() {
        getRequest().setAttribute("productStatusMap", ProductStatusMap.getProductStatusMap());
    }

    protected void setProductDraftStatusMap() {
        getRequest().setAttribute("productDraftStatusMap", ProductStatusMap.getProductDraftMap());
    }

    protected void setApprovalTypeMap() {
        getRequest().setAttribute("approvalTypeMap", ApprovalTypeMap.getMap());
    }

    protected void setIsNoticeMap() {
        getRequest().setAttribute("isNoticeMap", IsNoticeMap.getMap());
    }

    protected void setDraftTypeMap() {
        getRequest().setAttribute("draftTypeMap", DraftTypeMap.getMap());
    }

    protected void setShopMainStatusMap() {
        getRequest().setAttribute("shopMainStatusMap", ShopMainStatusMap.getMap());
    }

    protected void setShopAuditStatusMap() {
        getRequest().setAttribute("shopAuditStatusMap", ShopAuditStatusMap.getMap());
    }

    protected void setSiteTypeMap() {
        getRequest().setAttribute("siteTypeMap", SiteTypeMap.getMap());
    }

    protected void setShopMainServiceTypeMap() {
        getRequest().setAttribute("shopMainServiceTypeMap", ShopMainServiceTypeMap.getMap());
    }

    protected void setShopMainTypeMap() {
        getRequest().setAttribute("shopMainTypeMap", ShopMainTypeMap.getMap());
    }

    protected void setExpressStatusMap() {
        getRequest().setAttribute("expressStatusMap", ExpressStatusMap.getMap());
    }

    protected List<SuppliersAvailableCategorys> getSupplierCategorysBySupplierId() {
        List<SuppliersAvailableCategorys> categoryList = null;
        try {
            categoryList = supplierCategorysService.findSupplierCategoriesBySupplierId(getSupplyId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categoryList;
    }

    protected void getBcategoryList(Long parentId) {
        List<Category> categoryList = null;
        try {
            Map<String, Object> param = new HashMap();
            param.put("parentId", parentId);
            param.put("supplierId", getSupplyId());
            categoryList = categoryService.selectCategoryWithSupplyAvailable(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getRequest().setAttribute("categoryList", categoryList);
        getRequest().setAttribute("mCategoryList", new ArrayList());
        getRequest().setAttribute("sCategoryList", new ArrayList());
    }

    protected void getMcategoryList(Long categoryId) {
        Category category = new Category();
        category.setCategoryId(categoryId);
        List<Category> categoryList = null;
        try {
            categoryList = categoryService.queryCategoryChildrenList(category, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getRequest().setAttribute("mCategoryList", categoryList);
    }

    protected void getScategoryList(Long categoryId) {
        Category category = new Category();
        category.setCategoryId(categoryId);
        List<Category> categoryList = null;
        try {
            categoryList = categoryService.queryCategoryChildrenList(category, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getRequest().setAttribute("sCategoryList", categoryList);
    }

    /**
     * 获取所有的类目信息
     * @param bCategoryId   一级类目
     * @param mCategoryId   二级类目
     */
    protected void getAllCategoryList(Long bCategoryId, Long mCategoryId) {
        getBcategoryList(Long.valueOf(0));

        if (bCategoryId != null && bCategoryId.intValue() != 0) {
            getMcategoryList(bCategoryId);
        }

        if (mCategoryId != null && mCategoryId.intValue() != 0) {
            getScategoryList(mCategoryId);
        }
    }

    protected void setCategoriesName(Long categoryId) {
        Category category = new Category();
        category.setCategoryId(categoryId);
        try {
            List<Category> categoryList = categoryService.queryCategoryParentList(category);
            if (categoryList != null && categoryList.size() > 2) {
                getRequest().setAttribute("sCategoryName", categoryList.get(0).getCategoryName());
                getRequest().setAttribute("mCategoryName", categoryList.get(1).getCategoryName());
                getRequest().setAttribute("mCategoryId", categoryList.get(1).getCategoryId());
                getRequest().setAttribute("bCategoryName", categoryList.get(2).getCategoryName());
                getRequest().setAttribute("bCategoryId", categoryList.get(2).getCategoryId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 字符集转换
     *
     * @param str
     * @return
     */
    protected String decoder(String str) {
        try {
            str = java.net.URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error(new StringBuilder("字符转码出错!").append(e.getMessage()).toString());
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 将对象返回成Json字符串
     *
     * @param object
     */
    protected void writeJson(Object object) {
        PrintWriter print = null;
        try {
            print = getResponse().getWriter();
            print.write(JSON.toJSONString(object));
        } catch (Exception e) {
            logger.error(new StringBuilder("返回JSON字符串出错!").append(e.getMessage()).toString());
            e.printStackTrace();
        } finally {
            if (print != null) {
                print.flush();
                print.close();
            }
        }
    }

    /**
     * 直接返回字符串
     *
     * @param msg
     */
    protected void writeStr(String msg) {
        PrintWriter print = null;
        try {
            print = getResponse().getWriter();
            print.write(msg);
        } catch (Exception e) {
            logger.error(new StringBuilder("返回JSON字符串出错!").append(e.getMessage()).toString());
            e.printStackTrace();
        } finally {
            if (print != null) {
                print.flush();
                print.close();
            }
        }
    }

    /**
     * 将字符串返回成Json字符串
     *
     * @param strJson
     */
    protected void strWriteJson(String strJson) {
        ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
        PrintWriter pw = null;
        try {
            pw = ServletActionContext.getResponse().getWriter();
            pw.write(strJson);
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    public PrintWriter getPrintWriter() throws IOException {
        return getResponse().getWriter();
    }

    /**
     * 以流的方式，在页面显示图片
     *
     * @param path 图片路径
     */
    protected void showPicture(String path) {
        if (path != null && !path.isEmpty()) {
            File file = new File(path);
            this.getResponse().reset();
            this.getResponse().setContentType("image/*");
            InputStream in = null;
            OutputStream out = null;
            try {
                in = new FileInputStream(file);
                out = this.getResponse().getOutputStream();
                byte[] bytearray = new byte[1024];
                int len = 0;
                while ((len = in.read(bytearray)) != -1) {
                    out.write(bytearray);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                    if (out != null) {
                        out.flush();
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected void setShopMainMapBySupplierId(Long supplierId) {
        try {
            Map<Long, String> baseActionShopMainMap = new LinkedHashMap();
            List<ShopMain> list = shopMainService.findAllShopMainBySupplierId(supplierId);
            for (ShopMain sm : list) {
                baseActionShopMainMap.put(sm.getShopId(), sm.getShopName());
            }
            super.getRequest().setAttribute("shopMainMap", baseActionShopMainMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取到所有可用的仓库信息
     */
    protected void setWareHouseInfoMap() {
        try {
            Map<String, String> allWarehouse = new LinkedHashMap();
            List<WarehouseInfo> warehouseList = warehouseService.queryAllEnableWarehouse();
            for (WarehouseInfo warehouse : warehouseList) {
                allWarehouse.put(String.valueOf(warehouse.getWarehouseId()), warehouse.getWarehouseName());
            }
            super.getRequest().setAttribute("warehouseMap", allWarehouse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取到供应商所有可用的仓库信息
     */
    protected void setSupplierWareHouseInfoMap() {
        try {
            Map<String, String> supplierWarehouse = new LinkedHashMap<String, String>();
            List<WarehouseInfo> warehouseList = warehouseService.queryWarehouseBySuppliersId(getSupplyId().toString());
            for (WarehouseInfo warehouse : warehouseList) {
                supplierWarehouse.put(String.valueOf(warehouse.getWarehouseId()), warehouse.getWarehouseName());
            }
            super.getRequest().setAttribute("suppliersWarehouseMap", supplierWarehouse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 店内分类数据
     */
    protected void setShopCategoryList() {
        try {
            List<ShopMain> list = shopMainService.findAllShopMainBySupplierId(getSupplyId());
            if (CollectionUtils.isNotEmpty(list)) {
                long shopId = list.get(0).getShopId();
                ShopCategorys condition = new ShopCategorys();
                condition.setShopId(shopId);
                condition.setShopCategoryId(null);
                List<ShopCategorys> categoryList = shopCategoryService.queryShopCategoryList(condition);
                jsonDataForTreeNode = JSON.toJSONString(categoryList);
            }
        } catch (Exception e) {
            logger.error("获取店内分类数据发生异常~" + e.getMessage(), e);
            e.printStackTrace();
        }

    }

    /**
     * 根据供应商ID查询店铺信息 通过map集合返回,需要什么放什么
     *  <note>
     *      shopId
     *      url
     *  </note>
     * @param supplierId
     * @return
     */
    protected Map<String, Object> getShopMainBySupplierId(Long supplierId) {
        Map<String, Object> infoMap = new HashMap();
        try {
            List<ShopMain> list = shopMainService.findAllShopMainBySupplierId(supplierId);
            if (CollectionUtils.isNotEmpty(list)) {
                for (ShopMain shopMain : list) {
                    infoMap.put("shopId", shopMain.getShopId());
                    if (StringUtils.isNotBlank(shopMain.getDefaultDomainUrl())) {
                        infoMap.put("url", shopMain.getDefaultDomainUrl());
                        break;
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return infoMap;
    }

    /**
     * 是否已经启用那个店铺浏览量状态
     *
     * @param supplierId
     * @return true 已经开启 false 未开启
     */
    public boolean isOpenShopBrowseStatus(Long supplierId) {
        try {
            SuppliersInfo s = supplierInfoService.selectByPrimaryKey(supplierId);
            return s == null || (s.getShopBrowseStatus() != null && s.getShopBrowseStatus() != 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 获取店铺的默认分类 包含其二级分类
     *
     * @param shopId
     * @return
     */
    public ShopCategorys getDefaultShopCategory(Long shopId) {
        try {
            if (shopId == null) {
                shopId = getSupplierShopId();
            }

            if (shopId == null) {
                return null;
            }
            return shopCategoryService.queryDefaultShopCategoryByShopId(shopId);
        } catch (Exception e) {
            logger.error("获取店内默认分类数据发生异常:", e);
            return null;
        }
    }

    /**
     * 查询是否有自己创建的分类
     *
     * @param shopId
     * @return
     */
    public boolean getIsExistShopCateCreateBySelf(Long shopId) {
        try {
            if (shopId == null) {
                shopId = getSupplierShopId();
            }
            if (shopId == null) {
                return false;
            }

            return shopCategoryService.queryIsExistShopCategoryCreateBySelf(shopId);
        } catch (Exception e) {
            logger.error("获取店内自己分类的数据发生异常:", e);
        }
        return false;
    }

    /**
     * 查询该店铺的所有分类,格式是返回列表形式
     *
     * @param shopId
     */
    protected void setShopCategoryForList(Long shopId) {
        try {
            if (shopId == null) {
                shopId = getSupplierShopId();
            }

            if (shopId == null) {
                super.getRequest().setAttribute("shopCategoryList", null);
                return;
            }

            List<ShopCategorys> allShopCategory = shopCategoryService.queryShopCategoryByParentId(0, shopId);
            super.getRequest().setAttribute("shopCategoryList", allShopCategory);
        } catch (Exception e) {
            logger.error("获取店内自己分类的列表数据发生异常~" + e.getMessage(), e);
            e.printStackTrace();
        }
    }

    /**
     * 获取供应商店铺ID
     *
     * @return
     */
    protected Long getSupplierShopId() throws ServiceException {
        List<ShopMain> list = shopMainService.findAllShopMainBySupplierId(getSupplyId());
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0).getShopId();
        }

        return null;
    }

    /**
     * 获取客户端IP
     *
     * @return
     */
    public String getIP() {
        HttpServletRequest request = getRequest();
        String ipAddress = null;
        ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1")) {
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                    ipAddress = inet.getHostAddress();
                } catch (Exception e) {
                    logger.error("获取客户端IP异常：", e);
                }
            }
        }
        if (ipAddress != null && ipAddress.indexOf(",") > 0) {
            ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
        }
        return ipAddress;
    }

    /**
     * 订单查询状态
     *
     * @return
     */
    public Map<String, String> getOrderStatusMapForQuery() {
        Map<String, String> orderStatusMap = new HashMap<String, String>();
        orderStatusMap.put("1", "未付款");
        orderStatusMap.put("2", "已付款");
        orderStatusMap.put("4", "已结转");
        // orderStatusMap.put("16", "已拆分");
        // orderStatusMap.put("18", "已拆分待结转");
        orderStatusMap.put("5", "已配送");
        orderStatusMap.put("6", "已完成");
        orderStatusMap.put("-1", "已取消");
        orderStatusMap.put("7", "已评价"); // 此处为了方便判断,当7的时候需要将access塞入进去做判断,并且实际查的订单状态是为6的,因为订单状态没有7,只是这里为了查询做了区分
        orderStatusMap.put("21", "订单风控中");
        orderStatusMap.put("22", "已锁库存");
        orderStatusMap.put("23", "待付尾款");
        return orderStatusMap;
    }

    public Map<String, String> getOrderStatusMapForOrderQuery() {
        Map<String, String> orderStatusMap = new HashMap<String, String>();
        orderStatusMap.put("2", "已付款");
        orderStatusMap.put("4", "已结转");
        orderStatusMap.put("5", "已配送");
        orderStatusMap.put("6", "已完成");
        orderStatusMap.put("-1", "已取消");
        orderStatusMap.put("21", "订单风控中");
        orderStatusMap.put("22", "已锁库存");
        return orderStatusMap;
    }

    /**
     * 订单列表需要
     */
    public void setOrderStatusyMapForQuery() {
        super.getRequest().setAttribute("orderStatusMapForQuery", getOrderStatusMapForQuery());
    }

    public void setOrderStatusyMapForOrderQuery() {
        super.getRequest().setAttribute("orderStatusMapForOrderQuery", getOrderStatusMapForOrderQuery());
    }

    public String getCssJsPath() {
        return cssJsPath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getSupplierPath() {
        return supplierPath;
    }

    public AreaDictService getAreaDictService() {
        return areaDictService;
    }

    public ShopMainService getShopMainService() {
        return shopMainService;
    }

    public void setShopMainService(ShopMainService shopMainService) {
        this.shopMainService = shopMainService;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageSize1() {
        return pageSize1;
    }

    public void setPageSize1(int pageSize1) {
        this.pageSize1 = pageSize1;
    }

    public String getJsonDataForTreeNode() {
        return jsonDataForTreeNode;
    }

    public void setJsonDataForTreeNode(String jsonDataForTreeNode) {
        this.jsonDataForTreeNode = jsonDataForTreeNode;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getUserImagePath() {
        return userImagePath;
    }

}
