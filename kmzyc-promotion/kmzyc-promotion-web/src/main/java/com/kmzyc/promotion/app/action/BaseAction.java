package com.kmzyc.promotion.app.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.enums.CategoryStatus;
import com.kmzyc.promotion.app.enums.CategoryType;
import com.kmzyc.promotion.app.enums.CouponGrantDetailType;
import com.kmzyc.promotion.app.enums.ProductStatus;
import com.kmzyc.promotion.app.maps.ApprovalTypeMap;
import com.kmzyc.promotion.app.maps.AuditStatusMap;
import com.kmzyc.promotion.app.maps.CertificateTypeMap;
import com.kmzyc.promotion.app.maps.CouponStatusMap;
import com.kmzyc.promotion.app.maps.DistriButionInfoMap;
import com.kmzyc.promotion.app.maps.ProductBrandMap;
import com.kmzyc.promotion.app.maps.ProductStatusMap;
import com.kmzyc.promotion.app.maps.ProductTypeMap;
import com.kmzyc.promotion.app.maps.PurchaseInfoStatusMap;
import com.kmzyc.promotion.app.maps.PurchaseInfoTypeMap;
import com.kmzyc.promotion.app.maps.RelationTypeMap;
import com.kmzyc.promotion.app.maps.StockLogBillTypeMap;
import com.kmzyc.promotion.app.maps.StockLogOpTypeMap;
import com.kmzyc.promotion.app.maps.StockOutStatusMap;
import com.kmzyc.promotion.app.maps.StockOutTypeMap;
import com.kmzyc.promotion.app.maps.SysHandlerMap;
import com.kmzyc.promotion.app.maps.WarehouseStatusMap;
import com.kmzyc.promotion.app.service.CategoryService;
import com.kmzyc.promotion.app.service.ProdBrandService;
import com.kmzyc.promotion.app.service.SupplierAuditService;
import com.kmzyc.promotion.app.vobject.Category;
import com.kmzyc.promotion.app.vobject.ProdBrand;
import com.kmzyc.promotion.app.vobject.Product;
import com.kmzyc.promotion.sys.model.SysUser;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport {
    private static final long serialVersionUID = -2353079645208517728L;

    // 日志记录
    private static final Logger logger = LoggerFactory.getLogger(BaseAction.class);

    public static String ADD = "add";

    protected static final String MESSAGE = "message";

    protected static final String JSON_MESSAGE = "json_message";

    protected String json;
    @Resource
    protected CategoryService categoryService;

    @Resource
    protected ProdBrandService prodBrandService;

    @Resource
    protected SupplierAuditService supplierAuditService;

    protected Page page;

    private Long checkedId;

    public Page getPage() {
        if (page == null) {
            page = new Page();
        }
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    /**
     * 获取当前登陆的用户ID
     */
    protected Integer loginUserId;

    protected String submitType;

    public String getSubmitType() {
        return submitType;
    }

    public void setSubmitType(String submitType) {
        this.submitType = submitType;
    }

    protected HttpServletRequest getRequest() {
        return ServletActionContext.getRequest();
    }

    public HttpSession getSession() {
        return ServletActionContext.getRequest().getSession();
    }

    public Map<String, Object> getSessionMap() {
        return ActionContext.getContext().getSession();
    }

    public void printStream(String outstring) {
        PrintStream out = null;
        try {
            out = new PrintStream(getResponse().getOutputStream(), true, "UTF-8");
            out.print(outstring);
            out.flush();

        } catch (IOException e) {
            logger.error("printStream方法IO异常：", e);
        } catch (IllegalStateException e2) {
            logger.error("printStream方法异常：", e2);

        } finally {
            if (out != null)
                out.close();
        }
    }

    public PrintWriter getPrintWriter() throws IOException {
        return getResponse().getWriter();
    }

    /**
     * 将对象返回成Json字符串
     * 
     * @param object
     */
    protected void writeJson2(Object object) {
        PrintWriter print = null;
        try {
            print = getResponse().getWriter();
            print.write(JSON.toJSONString(object));
        } catch (Exception e) {
            logger.error("writeJson2(Object object)方法返回JSON字符串出错!", e);
        } finally {
            if (print != null) {
                print.flush();
                print.close();
            }
        }
    }

    public SysUser getLoginUser() {
        return (SysUser) getSessionMap().get("sysUser");
    }

    public Integer getLoginUserId() {
        return getLoginUser().getUserId();
    }

    public String getLoginUserName() {
        return getLoginUser().getUserName();
    }

    public String getVisitIP() {
        String ip = getRequest().getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = getRequest().getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = getRequest().getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = getRequest().getRemoteAddr();
        }
        return ip;
    }

    /**
     * 取得response
     * 
     * @return
     */

    protected HttpServletResponse getResponse() {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        return response;
    }

    /**
     * 直接输出J-SON代码.在B/S之间直接传递JSON代码是Ajax中一种比较实效的用法,JSON的定义如{name:"Tom",age:20}
     */
    protected void renderJson(String text) {
        render(text, "text/x-json;charset=UTF-8");
    }

    protected void getCategoryList() {
        Category category = new Category();
        category.setCategoryId(new Long(0));
        category.setStatus(CategoryStatus.VALID.getStatus());
        List<Category> categoryList = null;
        try {
            categoryList = categoryService.queryCategoryChildrenList(category);
        } catch (Exception e) {
            logger.error("getCategoryList异常：", e);
        }
        getRequest().setAttribute("categoryList", categoryList);
    }

    protected void getBcategoryList(Long cId) {
        Category category = new Category();
        category.setCategoryId(cId);
        category.setStatus(CategoryStatus.VALID.getStatus());
        category.setIsPhy(CategoryType.PHYSICS.getKey());
        List<Category> categoryList = null;
        try {
            categoryList = categoryService.queryCategoryChildrenList(category);
        } catch (Exception e) {
            logger.error("getBcategoryList异常：", e);
        }
        getRequest().setAttribute("categoryList", categoryList);
        getRequest().setAttribute("mCategoryList", Lists.newArrayList());
        getRequest().setAttribute("sCategoryList", Lists.newArrayList());
    }

    protected void getMcategoryList(Long cId) {
        List<Category> categoryList = getCatesByPrarentId(cId);
        getRequest().setAttribute("mCategoryList", categoryList);
    }

    protected List<Category> getCatesByPrarentId(Long cId) {
        Category category = new Category();
        category.setCategoryId(cId);
        category.setStatus(CategoryStatus.VALID.getStatus());
        List<Category> categoryList = null;
        try {
            categoryList = categoryService.queryCategoryChildrenList(category);
        } catch (Exception e) {
            logger.error("getCategoryList异常：", e);
        }
        return categoryList;
    }

    protected void getScategoryList(Long cId) {
        List<Category> categoryList = getCatesByPrarentId(cId);
        getRequest().setAttribute("sCategoryList", categoryList);
    }

    protected void getProductTypeMap() {
        getRequest().setAttribute("productTypeMap", ProductTypeMap.getMap());
    }

    protected void getRelationTypeMap() {
        getRequest().setAttribute("relationTypeMap", RelationTypeMap.getMap());
    }

    protected void getPackageMap() {
        getRequest().setAttribute("packageMap", RelationTypeMap.getPackageMap());
    }

    protected void getCertificateOTCMAP() {
        this.getRequest().setAttribute("certificateTypeMAP", CertificateTypeMap.getOTCMAP());
    }

    protected void getCertificateMDSINMAP() {
        this.getRequest().setAttribute("certificateTypeMAP", CertificateTypeMap.getMDSINMAP());
    }

    protected void getBrandList() {
        List<ProdBrand> brandList = null;
        try {
            brandList = prodBrandService.findAllValidBrand();
        } catch (Exception e) {
            logger.error("getBrandList异常：", e);
        }
        getRequest().setAttribute("brandList", brandList);
    }

    /**
     * 将对象返回成Json字符串
     * 
     * @param object
     */
    protected void writeJson(Object object) {
        String json = JSON.toJSONString(object);
        strWriteJson(json);
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
            logger.error("strWriteJson异常：", e);
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    /**
     * 以流的方式，在页面显示图片
     * 
     * @param path 图片路径
     */
    @SuppressWarnings("unused")
    protected void showPicture(String path) {
        if (path != null && !path.isEmpty()) {
            File file = new File(path);
            this.getResponse().reset();
            this.getResponse().setContentType("image/*");

            try {
                InputStream in = new FileInputStream(file);
                OutputStream out = this.getResponse().getOutputStream();

                byte[] bytearray = new byte[1024];
                int len = 0;
                while ((len = in.read(bytearray)) != -1) {
                    out.write(bytearray);
                }
                out.flush();// 必须清除流，否则图片不能正常显示
                in.close();
                out.close();
            } catch (FileNotFoundException e) {
                logger.error("showPicture异常:", e);
            } catch (IOException e) {
                logger.error("showPicture方法IO异常:", e);
            }
        }
    }

    protected void setProductStatusMap() {
        getRequest().setAttribute("productStatusMap", ProductStatusMap.getProductStatusMap());
    }

    protected void getProductRelationStatusMap() {

        getRequest().setAttribute("productRelationStatusMap",
                ProductStatusMap.getProductRelationMap());

    }

    protected void setProductStatusByAuditMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put(ProductStatus.UNAUDIT.getStatus(), ProductStatus.UNAUDIT.getTitle());
        map.put(ProductStatus.AUDIT.getStatus(), ProductStatus.AUDIT.getTitle());
        getRequest().setAttribute("productStatusByAuditMap", map);
    }

    protected void setApprovalTypeMap() {
        getRequest().setAttribute("approvalTypeMap", ApprovalTypeMap.getMap());
    }

    protected void setProductBrandMap() {
        getRequest().setAttribute("productBrandMap", ProductBrandMap.getMap());
    }

    protected void setStockLogOpTypeMap() {
        getRequest().setAttribute("stockLogOpTypeMap", StockLogOpTypeMap.getMap());
    }

    protected void setStockLogBillTypeMap() {
        getRequest().setAttribute("stockLogBillTypeMap", StockLogBillTypeMap.getMap());
    }

    /**
     * 优惠券发放类型
     */
    public void setCouponGrantType() {
        Map<String, String> grantTypeMap = new HashMap<String, String>();
        grantTypeMap.put(CouponGrantDetailType.MANUAL_COUPONGRANTDETAILTYPE.getType(),
                CouponGrantDetailType.MANUAL_COUPONGRANTDETAILTYPE.getTitle());
        grantTypeMap.put(CouponGrantDetailType.REGIST_COUPONGRANTDETAILTYPE.getType(),
                CouponGrantDetailType.REGIST_COUPONGRANTDETAILTYPE.getTitle());
        grantTypeMap.put(CouponGrantDetailType.ORDER_RELATEDACTIVITYGRANT.getType(),
                CouponGrantDetailType.ORDER_RELATEDACTIVITYGRANT.getTitle());
        grantTypeMap.put(CouponGrantDetailType.POINTEXCUT_COUPONGRANTDETAILTYPE.getType(),
                CouponGrantDetailType.POINTEXCUT_COUPONGRANTDETAILTYPE.getTitle());
        grantTypeMap.put(CouponGrantDetailType.LOTTERY_GRANT.getType(),
                CouponGrantDetailType.LOTTERY_GRANT.getTitle());
        grantTypeMap.put(CouponGrantDetailType.ANONYMOUS_GRANT.getType(),
                CouponGrantDetailType.ANONYMOUS_GRANT.getTitle());
        getRequest().setAttribute("couponGrantTypeMap", grantTypeMap);
    }

    /**
     * 优惠券状态
     */
    public void setCouponStatus() {
        getRequest().setAttribute("couponStatusMap", CouponStatusMap.getMap());
    }

    protected void setWarehouseStatusMap() {
        getRequest().setAttribute("warehouseStatusMap", WarehouseStatusMap.getMap());
    }

    protected String getUTF8Str(String str) throws UnsupportedEncodingException {
        // byte[] bytes = str.getBytes();
        return new String(str.getBytes("iso-8859-1"), "utf-8");
    }

    // 配送单到达状态
    protected void setDistriButionInfoMap() {
        getRequest().setAttribute("distriButionInfoMap", DistriButionInfoMap.getMap());
    }

    // 采购单状态
    protected void setPurchaseInfoStatusMap() {
        getRequest().setAttribute("purchaseInfoStatusMap", PurchaseInfoStatusMap.getMap());
    }

    // 采购单类型
    protected void setPurchaseInfoTypeMap() {
        getRequest().setAttribute("purchaseInfoTypeMap", PurchaseInfoTypeMap.getMap());
    }

    // 出库单审核状态
    protected void setStockOutStatusMap() {
        getRequest().setAttribute("stockOutStatusMap", StockOutStatusMap.getMap());
    }

    // 出库单类型
    protected void setStockOutTypeMap() {
        getRequest().setAttribute("stockOutTypeMap", StockOutTypeMap.getMap());
    }

    // 经手人
    protected void setSysHandlerMap() {
        getRequest().setAttribute("sysHandlerMap", SysHandlerMap.getMap());
    }

    protected void setAuditStatusMap() {
        getRequest().setAttribute("auditStatusMap", AuditStatusMap.getMap());
    }

    public String decoder(String str) {
        try {
            str = java.net.URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("decoder异常:", e);
        }
        return str;
    }

    /**
     * 直接输出.
     * 
     * @param contentType 内容的类型.html,text,xml,json,excel,word的值见后
     */
    protected void render(String text, String contentType) {
        try {
            HttpServletResponse response = getResponse();
            response.setCharacterEncoding("UTF-8");
            response.setContentType(contentType);
            response.getOutputStream().write(text.getBytes("UTF-8"));
        } catch (IOException e) {
            logger.error("render(String text, String contentType)方法异常", e);
        }
    }

    protected void setCategorysName(Long categoryId) {
        Category category = new Category();
        category.setCategoryId(categoryId);
        try {
            List<Category> categoryList = categoryService.queryCategoryParentList(category);
            getRequest().setAttribute("sCategoryName", categoryList.get(0).getCategoryName());
            getRequest().setAttribute("mCategoryName", categoryList.get(1).getCategoryName());
            getRequest().setAttribute("bCategoryName", categoryList.get(2).getCategoryName());
        } catch (Exception e) {
            logger.error("setCategorysName异常:", e);
        }
    }

    protected void setCategorysId(Product product, Long categoryId) {
        Category category = new Category();
        category.setCategoryId(categoryId);
        try {
            List<Category> categoryList = categoryService.queryCategoryParentList(category);
            product.setmCategoryId(categoryList.get(1).getCategoryId());
            product.setbCategoryId(categoryList.get(2).getCategoryId());
        } catch (Exception e) {
            logger.error("setCategorysId异常:", e);
        }
    }

    protected void getSupplierMap() {
        Map<Object, String> supplierMap = new LinkedHashMap<Object, String>();

        getRequest().setAttribute("shopMap", supplierMap);
    }

    public Long getCheckedId() {
        return checkedId;
    }

    public void setCheckedId(Long checkedId) {
        this.checkedId = checkedId;
    }

}
