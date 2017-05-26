package com.pltfm.app.action;

import com.alibaba.fastjson.JSON;
import com.km.framework.page.Pagination;
import com.kmzyc.commons.page.Page;
import com.kmzyc.supplier.maps.SuppliersTypeMap;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.pltfm.app.enums.CategoryAudit;
import com.pltfm.app.enums.CategoryStatus;
import com.pltfm.app.enums.CategoryType;
import com.pltfm.app.enums.ProductStatus;
import com.pltfm.app.maps.ActivityChannlMap;
import com.pltfm.app.maps.ActivityChargeTypeMap;
import com.pltfm.app.maps.ActivityIndustryList;
import com.pltfm.app.maps.ActivityLabelList;
import com.pltfm.app.maps.ActivityPaymentStatusMap;
import com.pltfm.app.maps.ActivityStatusMap;
import com.pltfm.app.maps.ActivityTypeMap;
import com.pltfm.app.maps.ApprovalTypeMap;
import com.pltfm.app.maps.AuditStatusMap;
import com.pltfm.app.maps.CertificateStatusMaps;
import com.pltfm.app.maps.CertificateTypeMap;
import com.pltfm.app.maps.CorrespondingDataStatusMap;
import com.pltfm.app.maps.DistriButionInfoMap;
import com.pltfm.app.maps.DraftTypeMap;
import com.pltfm.app.maps.EnterpriseStatusMap;
import com.pltfm.app.maps.IsNoticeMap;
import com.pltfm.app.maps.ProductBrandMap;
import com.pltfm.app.maps.ProductRelationEdibelMap;
import com.pltfm.app.maps.ProductRelationStatusMap;
import com.pltfm.app.maps.ProductRelationValidMap;
import com.pltfm.app.maps.ProductStatusMap;
import com.pltfm.app.maps.ProductTypeMap;
import com.pltfm.app.maps.PromotionStatusMap;
import com.pltfm.app.maps.PromotionTypeMap;
import com.pltfm.app.maps.RelationTypeMap;
import com.pltfm.app.maps.StockLogBillTypeMap;
import com.pltfm.app.maps.StockLogOpTypeMap;
import com.pltfm.app.maps.StockOutStatusMap;
import com.pltfm.app.maps.StockOutTypeMap;
import com.pltfm.app.maps.SupplierRecheckStatusMaps;
import com.pltfm.app.maps.SuppliersStatusMap;
import com.pltfm.app.maps.SupplyTypeMap;
import com.pltfm.app.maps.SysHandlerMap;
import com.pltfm.app.maps.SystemCodeMap;
import com.pltfm.app.maps.TiedSadeTypeMap;
import com.pltfm.app.maps.WarehouseInfoMap;
import com.pltfm.app.maps.WarehouseStatusMap;
import com.pltfm.app.maps.YesOrNoMap;
import com.pltfm.app.maps.ZFProductRelationValidMap;
import com.pltfm.app.service.AreaDictService;
import com.pltfm.app.service.CategoryService;
import com.pltfm.app.service.ProdBrandService;
import com.pltfm.app.service.SupplierAuditService;
import com.pltfm.app.vobject.AreaDict;
import com.pltfm.app.vobject.Category;
import com.pltfm.app.vobject.ProdBrand;
import com.pltfm.app.vobject.Product;
import com.pltfm.app.vobject.ProductDraft;
import com.pltfm.sys.model.SysUser;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("rawtypes")
public class BaseAction extends ActionSupport {

    private static final long serialVersionUID = -2353079645208517728L;

    protected Logger logger = LoggerFactory.getLogger(getClass());

    public static String ADD = "add";

    private Pagination page2;

    protected static final String MESSAGE = "message";

    protected static final String JSON_MESSAGE = "json_message";

    protected String json;

    @Resource
    protected CategoryService categoryService;

    @Resource
    protected ProdBrandService prodBrandService;

    @Resource
    protected AreaDictService areaDictService;

    @Resource
    protected SupplierAuditService supplierAuditService;
    @Resource(name = "logisticsMap")
    protected Map<String,String> logisticsMap;

    protected Page page = new Page();

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

    public Map getSessionMap() {
        return ActionContext.getContext().getSession();
    }

    public void printStream(String outstring) {
        PrintStream out = null;
        try {
            out = new PrintStream(getResponse().getOutputStream(), true, "UTF-8");
            out.print(outstring);
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e2) {
            e2.printStackTrace();

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
            logger.error(new StringBuilder("返回JSON字符串出错!").append(e.getMessage()).toString());
            e.printStackTrace();
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

    /**
     * 获取一级目录
     * <p>
     * <note> 此方法中固定住二级和三级目录list为空,同时只取物理类目 </note>
     *
     * @param cId
     */
    protected void getBcategoryList(Long cId) {
        Category category = new Category();
        category.setCategoryId(cId);
        category.setStatus(CategoryStatus.VALID.getStatus());
        category.setIsPhy(CategoryType.PHYSICS.getKey());
        List<Category> categoryList = null;
        try {
            categoryList = categoryService.queryCategoryChildrenList(category);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getRequest().setAttribute("categoryList", categoryList);
        getRequest().setAttribute("mCategoryList", new ArrayList());
        getRequest().setAttribute("sCategoryList", new ArrayList());
    }

    /**
     * 获取1级目录 <note> 物理类目和运营类目都取 </note>
     */
    protected void getCategoryList() {
        getCategoryList(Long.valueOf(0), "categoryList");
    }

    /**
     * 获取2级目录
     *
     * @param cId 1级目录id
     */
    protected void getMcategoryList(Long cId) {
        getCategoryList(cId, "mCategoryList");
    }

    /**
     * 获取3级目录
     *
     * @param cId 2级目录id
     */
    protected void getScategoryList(Long cId) {
        getCategoryList(cId, "sCategoryList");
    }

    /**
     * 获取三级类目的选项值
     *
     * @param first     1级类目id
     * @param second    2级类目id
     */
    protected void getAllCategoryList(Long first, Long second) {
        // 一级目录
        getBcategoryList(Long.valueOf(0));
        if (first != null && first.intValue() != 0) {
            getMcategoryList(first);
        }
        if (second != null && second.intValue() != 0) {
            getScategoryList(second);
        }
    }

    /**
     * 获取某目录下的子目录列表
     *
     * @param cId           父级目录id
     * @param attributeName 子级目录列表属性名称
     */
    protected void getCategoryList(Long cId, String attributeName) {
        Category category = new Category();
        category.setCategoryId(cId);
        category.setStatus(CategoryStatus.VALID.getStatus());
        List<Category> categoryList = null;
        try {
            categoryList = categoryService.queryCategoryChildrenList(category);
        } catch (Exception e) {
            logger.error("获取目录列表失败，" + e.getMessage(), e);
        }
        getRequest().setAttribute(attributeName, categoryList);
    }

    /**
     * 查询类目及其父节点
     *
     * @param categoryId 类目id
     * @return
     */
    protected List<Category> getCategoryParentList(Long categoryId) {
        Category category = new Category();
        category.setCategoryId(categoryId);
        List<Category> categoryList = null;
        try {
            categoryList = categoryService.queryCategoryParentList(category);
        } catch (Exception e) {
            logger.error("查询类目及其父节点失败：" + e.getMessage(), e);
        }
        return categoryList;
    }

    protected void setCategoriesName(Long categoryId) {
        List<Category> categoryList = getCategoryParentList(categoryId);

        if (categoryList != null && categoryList.size() > 2) {
            getRequest().setAttribute("sCategoryName", categoryList.get(0).getCategoryName());
            getRequest().setAttribute("mCategoryName", categoryList.get(1).getCategoryName());
            getRequest().setAttribute("bCategoryName", categoryList.get(2).getCategoryName());
        }
    }

    protected void setCategoriesIdByDraft(ProductDraft productdraft, Long categoryId) {
        List<Category> categoryList = getCategoryParentList(categoryId);

        if (categoryList != null && categoryList.size() > 2) {
            productdraft.setMCategoryId(categoryList.get(1).getCategoryId());
            productdraft.setBCategoryId(categoryList.get(2).getCategoryId());
        }
    }

    protected void setCategoriesId(Product product, Long categoryId) {
        List<Category> categoryList = getCategoryParentList(categoryId);

        if (categoryList != null && categoryList.size() > 2) {
            product.setMCategoryId(categoryList.get(1).getCategoryId());
            product.setBCategoryId(categoryList.get(2).getCategoryId());
        }
    }

    /**
     * 初始化三级目录
     *
     * @param bCategoryId 1级目录id
     * @param mCategoryId 2级目录id
     * @param categoryId  3级目录id
     */
    protected void initCategoryList(Long bCategoryId, Long mCategoryId, Long categoryId) {
        // 一级目录
        getBcategoryList(Long.valueOf(0));

        // 二级目录
        if (bCategoryId != null && bCategoryId.intValue() != 0) {
            getMcategoryList(bCategoryId);
        }

        // 三级目录
        if (mCategoryId != null && mCategoryId.intValue() != 0) {
            getScategoryList(mCategoryId);
        }
    }

    /**
     * 查询product列表初始化类目级联查询下拉框
     *
     * @param productForSelectPara
     */
    protected void initShowCategoryList(Product productForSelectPara) {
        initCategoryList(productForSelectPara.getBCategoryId(), productForSelectPara.getMCategoryId(),
                productForSelectPara.getCategoryId());
    }

    /**
     * 查询productDraft列表初始化类目级联查询下拉框
     *
     * @param productForSelectPara
     */
    protected void initShowCategoryListForDraft(ProductDraft productForSelectPara) {
        initCategoryList(productForSelectPara.getBCategoryId(), productForSelectPara.getMCategoryId(),
                productForSelectPara.getCategoryId());
    }

    protected void getProductTypeMap() {
        getRequest().setAttribute("productTypeMap", ProductTypeMap.getMap());
    }

    protected void getDraftTypeMap() {
        getRequest().setAttribute("DraftTypeMap", DraftTypeMap.getMap());
    }

    protected void getProductDraftMap() {
        getRequest().setAttribute("productDraftStatusMap", ProductStatusMap.getProductDraftMap());
    }

    protected void getPackageMap() {
        getRequest().setAttribute("packageMap", RelationTypeMap.getPackageMap());
    }

    protected void getCertificateOTCMAP() {
        this.getRequest().setAttribute("certificateTypeMAP", CertificateTypeMap.getOTCMAP());
    }

    protected void getBrandList() {
        List<ProdBrand> brandList = null;
        try {
            brandList = prodBrandService.findAllValidBrand();
        } catch (Exception e) {
            e.printStackTrace();
        }
        getRequest().setAttribute("brandList", brandList);
    }

    // 获得省级区域列表
    protected void getAreaList() {
        List<AreaDict> areaList;
        try {
            areaList = areaDictService.findAllProvince();
            AreaDict areaDict = new AreaDict();
            areaDict.setAreaId(Integer.valueOf("1"));
            areaDict.setAreaName("全国");
            areaList.add(areaDict);
            getRequest().setAttribute("areaList", areaList);
            getRequest().setAttribute("cAeaList", new ArrayList<AreaDict>());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 根据父ID获得下级区域列表
    protected void getCreateList(Integer provinceId) {
        List<AreaDict> cAeaList;
        try {
            cAeaList = areaDictService.findCityByProvince(provinceId);
            getRequest().setAttribute("cAeaList", cAeaList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将对象返回成Json字符串
     *
     * @param object
     */
    protected void writeJson(Object object) {
        // SerializeConfig serializeConfig = new SerializeConfig();
        // serializeConfig.setAsmEnable(false);
        String json = JSON.toJSONString(object);
        // String json = JSON.toJSONString(object, serializeConfig,
        // SerializerFeature.PrettyFormat);
        // String json = JSON.toJSONStringWithDateFormat(object,
        // "yyyy-MM-dd HH:mm:ss");
        // String json = JSON.toJSONStringWithDateFormat(object,
        // "yyyy-MM-dd HH:mm:ss", SerializerFeature.PrettyFormat);
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
            e.printStackTrace();
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
                out.flush();// 必须清除流，否则图片不能正常显示
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 产品状态map：productStatusMap
     */
    protected void setProductStatusMap() {
        getRequest().setAttribute("productStatusMap", ProductStatusMap.getProductStatusMap());
    }

    protected void setProductPVSearchStatusMap() {
        getRequest().setAttribute("productPVSearchStatusMap", ProductStatusMap.getProductPVSearchStatusMap());
    }

    /**
     * 产品状态map：productRelationStatusMap所有状态
     */
    protected void getProductRelationStatusMap() {
        getRequest().setAttribute("productRelationStatusMap", ProductStatusMap.getProductRelationMap());
    }

    protected void setProductStatusByAuditMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put(ProductStatus.UNAUDIT.getStatus(), ProductStatus.UNAUDIT.getTitle());
        map.put(ProductStatus.AUDIT.getStatus(), ProductStatus.AUDIT.getTitle());
        getRequest().setAttribute("productStatusByAuditMap", map);
    }

    /**
     * 是与否状态
     */
    protected void setFreeStatusMap() {
        getRequest().setAttribute("freeStatusMap", YesOrNoMap.getMap());
    }

    protected void setApprovalTypeMap() {
        getRequest().setAttribute("approvalTypeMap", ApprovalTypeMap.getMap());
    }

    /**
     * 品牌map：productBrandMap
     */
    protected void setProductBrandMap() {
        getRequest().setAttribute("productBrandMap", ProductBrandMap.getMap());
    }

    protected void setStockLogOpTypeMap() {
        getRequest().setAttribute("stockLogOpTypeMap", StockLogOpTypeMap.getMap());
    }

    protected void setStockLogBillTypeMap() {
        getRequest().setAttribute("stockLogBillTypeMap", StockLogBillTypeMap.getMap());
    }

    protected void setProductTypeMap() {
        getRequest().setAttribute("productTypeMap", ProductTypeMap.getMap());
    }

    protected void setWarehouseMap() {
        getRequest().setAttribute("warehouseInfoMap", WarehouseInfoMap.getMap());
    }

    /**
     * 审核状态 map：warehouseInfoStatusMap
     */
    protected void setWarehouseForStatusMap() {
        getRequest().setAttribute("warehouseInfoStatusMap", WarehouseInfoMap.getStatusMap());
    }

    protected void setWarehouseStatusMap() {
        getRequest().setAttribute("warehouseStatusMap", WarehouseStatusMap.getMap());
    }

    protected void setIsNoticeMap() {
        getRequest().setAttribute("isNoticeMap", IsNoticeMap.getMap());
    }

    protected String getUTF8Str(String str) throws UnsupportedEncodingException {
        // byte[] bytes = str.getBytes();
        return new String(str.getBytes("iso-8859-1"), "utf-8");
    }

    // 配送单到达状态
    protected void setDistributionInfoMap() {
        getRequest().setAttribute("distriButionInfoMap", DistriButionInfoMap.getMap());
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

    // 快递公司
    protected void setLogisticsCompanyMap() {
        getRequest().setAttribute("logisticsCompanyMap", logisticsMap);
    }

    // 活动标签
    protected void setActivityLabelList() {
        getRequest().setAttribute("activityLabelList", ActivityLabelList.getList());
    }

    // 活动所属行业
    protected void setActivityIndustryList() {
        getRequest().setAttribute("activityIndustryList", ActivityIndustryList.getList());
    }

    /**
     * 活动报名商家审核状态
     */
    protected void setAuditStatusMap() {
        getRequest().setAttribute("auditStatusMap", AuditStatusMap.getMap());
    }

    /**
     * 活动报名商家缴费状态
     */
    protected void setActivityPaymentStatusMap() {
        getRequest().setAttribute("activityPaymentStatusMap", ActivityPaymentStatusMap.getMap());
    }

    /**
     * 活动状态
     */
    protected void setActivityStatusMap() {
        getRequest().setAttribute("activityStatusMap", ActivityStatusMap.getMap());
    }

    /**
     * 活动状态（去除草稿、撤销状态）
     */
    protected void setActivityOutDraftCanSellStatusMap() {
        getRequest().setAttribute("activityOutDraftCancellStatusMap", ActivityStatusMap.getMapOutDraftCancell());
    }

    /**
     * 活动类型
     */
    protected void setActivityTypeMap() {
        getRequest().setAttribute("activityTypeMap", ActivityTypeMap.getMap());
    }

    /**
     * 活动收费类型
     */
    protected void setActivityChargeTypeMap() {
        getRequest().setAttribute("activityChargeTypeMap", ActivityChargeTypeMap.getMap());
    }

    /**
     * 活动收费类型
     */
    protected void setActivityChannelMap() {
        getRequest().setAttribute("activityChannlMap", ActivityChannlMap.getMap());
    }

    /**
     * 产品搭售的搭售类型
     */
    public void getTiedSaleType() {
        getRequest().setAttribute("tiedSadeType", TiedSadeTypeMap.getTiedSaleTypeMap());
    }

    /**
     * 产品关联类型 map:productRelationType
     */
    public void getProductRelationType() {
        getRequest().setAttribute("productRelationType", ProductRelationStatusMap.getStatusMap());
    }

    /**
     * 关联产品的有效状态 map：productRelationValidStatus
     */
    public void getProductRelationValidStatus() {
        getRequest().setAttribute("productRelationValidStatus", ProductRelationValidMap.getStatusMap());
    }

    /**
     * 产品关联的可编辑状态 map：productRelationEdibelStatus
     */
    public void getProductRelationEdibleStatus() {
        getRequest().setAttribute("productRelationEdibelStatus", ProductRelationEdibelMap.getStatusMap());
    }

    public void setCategoryAuditMap() {
        Map<String, String> categoryAuditMap = new HashMap<String, String>();
        categoryAuditMap.put(CategoryAudit.INCORRECT.getKey(),
                CategoryAudit.INCORRECT.getValue());
        categoryAuditMap.put(CategoryAudit.CORRECT.getKey(),
                CategoryAudit.CORRECT.getValue());


        getRequest().setAttribute("categoryAuditMap", categoryAuditMap);
    }

    /**
     * 对接数据状态
     */
    protected void setCorrespondingDataStatusMap() {
        getRequest().setAttribute("correspondingDataStatusMap", CorrespondingDataStatusMap.getMap());
    }

    /**
     * 外部系统编码
     */
    protected void setSystemCodeMap() {
        getRequest().setAttribute("systemCodeMap", SystemCodeMap.getMap());
    }

    public String decoder(String str) {
        try {
            str = java.net.URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
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
            logger.error(e.getMessage(), e);
        }
    }

    protected void getSupplierMap() {
        Map<String, String> supplierMap = new LinkedHashMap<String, String>();
        try {
            Map<String, String> supplierMapTmp = supplierAuditService.supplierIdAndMerchantNameMap();
            if (supplierMapTmp != null && !supplierMapTmp.isEmpty()) {
                supplierMap.putAll(supplierMapTmp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        getRequest().setAttribute("shopMap", supplierMap);
    }

    /**
     * 根据产品类目判断 是否是按方抓药 maliqun add
     *
     * @param categoryId
     * @return
     */
    protected boolean isPrescription(long categoryId) {
        boolean flag = false;
        try {
            Category categoryParam = new Category();
            categoryParam.setCategoryId(categoryId);
            List<Category> categoryList;
            categoryList = categoryService.queryCategoryParentList(categoryParam);
            String categoryIdForPrescription = ConfigurationUtil.getString("categoryIdForPrescription");
            if (categoryList != null && categoryList.size() > 0) {
                for (Category category : categoryList) {
                    if (category.getCategoryId().toString().equals(categoryIdForPrescription)) {
                        flag = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }

    public Long getCheckedId() {
        return checkedId;
    }

    public void setCheckedId(Long checkedId) {
        this.checkedId = checkedId;
    }

    /**
     * 组方关联产品的有效状态
     */
    public void getZFProductRelationValidStatus() {
        getRequest().setAttribute("zfProductRelationValidStatus", ZFProductRelationValidMap.getStatusMap());
    }

    /**
     * 供应商申请状态 map:suppliersStatusMap
     */
    protected void setSuppliersStatusMap() {
        getRequest().setAttribute("suppliersStatusMap", SuppliersStatusMap.getMap());
    }

    /**
     * 供应商类型 map:SuppliersTypeMap
     */
    protected void setSuppliersTypeMap() {
        getRequest().setAttribute("SuppliersTypeMap", SuppliersTypeMap.getMap());
    }

    protected void setSupplyTypeMap() {
        getRequest().setAttribute("SupplyTypeMap", SupplyTypeMap.getMap());
    }

    public void setOrderStatusMapForOrderQuery() {
        getRequest().setAttribute("orderStatusMapForOrderQuery", getOrderStatusMapForOrderQuery());
    }

    public Map<String, String> getOrderStatusMapForOrderQuery() {
        Map<String, String> orderStatusMap = new HashMap<String, String>();
        orderStatusMap.put("2", "已付款");
        orderStatusMap.put("4", "已结转");
        orderStatusMap.put("5", "已配送");
        orderStatusMap.put("6", "已完成");
        orderStatusMap.put("-1", "已取消");
        orderStatusMap.put("22", "风控通过");
        orderStatusMap.put("21", "待风控评估");
        orderStatusMap.put("-3", "异常订单");
        orderStatusMap.put("20", "已锁库存");
        return orderStatusMap;
    }

    /**
     * 促销类型
     */
    protected void setPromotionTypeMap() {
        getRequest().setAttribute("promotionTypeMap", PromotionTypeMap.getMap());
    }

    /**
     * 促销审核状态
     */
    protected void setPromotionStatusMap() {
        getRequest().setAttribute("PromotionStatusMap", PromotionStatusMap.getMap());
    }

    /**
     * 企业状态
     */
    protected void setEnterpriseStatusMap() {
        getRequest().setAttribute("enterpriseStatusMap", EnterpriseStatusMap.getMap());
    }

    /**
     * 资质重审状态
     */
    protected void setSupplierRecheckStatusMap() {
        getRequest().setAttribute("supplierRecheckStatusMap", SupplierRecheckStatusMaps.getMap());
    }

    /**
     * 供应商资质验证状态Maps
     */
    protected void setCertificateStatusMaps() {
        getRequest().setAttribute("certificateStatusMaps", CertificateStatusMaps.getMap());
    }

}
