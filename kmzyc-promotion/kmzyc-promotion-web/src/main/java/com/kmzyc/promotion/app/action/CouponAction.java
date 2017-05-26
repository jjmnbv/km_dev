package com.kmzyc.promotion.app.action;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.enums.CouponStatus;
import com.kmzyc.promotion.app.maps.CouponGrantFlowTypeMap;
import com.kmzyc.promotion.app.maps.CouponGrantTypeMap;
import com.kmzyc.promotion.app.maps.CouponRuleStatusMap;
import com.kmzyc.promotion.app.maps.CouponStatusMap;
import com.kmzyc.promotion.app.maps.CouponSupplierTypeMap;
import com.kmzyc.promotion.app.service.CategoryService;
import com.kmzyc.promotion.app.service.CouponGrantFlowService;
import com.kmzyc.promotion.app.service.CouponProductService;
import com.kmzyc.promotion.app.service.CouponService;
import com.kmzyc.promotion.app.service.ProductService;
import com.kmzyc.promotion.app.service.ViewProductSkuService;
import com.kmzyc.promotion.app.vobject.Category;
import com.kmzyc.promotion.app.vobject.Coupon;
import com.kmzyc.promotion.app.vobject.CouponGrantFlow;
import com.kmzyc.promotion.app.vobject.CouponProduct;
import com.kmzyc.promotion.app.vobject.Message;
import com.kmzyc.promotion.app.vobject.Product;
import com.kmzyc.promotion.app.vobject.ViewProductSku;
import com.kmzyc.supplier.enums.SuppliersType;
import com.kmzyc.supplier.model.MerchantInfoOrSuppliers;
import com.kmzyc.user.remote.service.CustomerRemoteService;
import com.kmzyc.user.remote.service.UserGrowingService;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.dataobject.UserInfoDO;

@SuppressWarnings({"unchecked", "rawtypes"})
@Controller("couponAction")
@Scope(value = "prototype")
public class CouponAction extends BaseAction implements ModelDriven {

    private static final long serialVersionUID = 1L;
    // 日志记录
    private static final Logger logger = LoggerFactory.getLogger(CouponAction.class);

    @Resource
    private UserGrowingService userGrowingService;

    @Resource
    private CustomerRemoteService customerRemoteService;

    /**
     * 优惠券接口类
     */
    @Resource(name = "CouponService")
    private CouponService CouponService;

    /**
     * 产品接口
     */
    @Resource(name = "productServiceImpl")
    private ProductService productServiceImpl;

    /**
     * 类目业务逻辑接口
     */
    @Resource(name = "categoryService")
    private CategoryService categoryService;

    /**
     * 优惠券产品接口
     */
    @Resource(name = "couponProductImpl")
    private CouponProductService couponProductImpl;

    /**
     * 产品sku接口
     */
    @Resource
    private ViewProductSkuService viewProductSkuService;

    /**
     * 优惠券流水
     */
    @Resource
    private CouponGrantFlowService couponGrantFlowService;

    // 优惠券实体
    private Coupon coupon;

    // 接受到的产品实体id
    private String productArry;

    // 接收到的lev实体id
    private String leveArry;

    // 接受到的custom实体id
    private String customArry;

    // 分页实体
    private Page page;

    // 消息实体
    private Message message = new Message();

    // 接受优惠的
    private String couponProductId;

    // 查看的优惠券实体
    private Integer couponId;

    // 接受优惠的类目Id
    private String categoryIds;

    // 页面上选择的产品列表
    private List<CouponProduct> couponList;

    // 已经选择过的产品id；
    private String haveChoosedPro;

    // 已经选择过的会员Lev
    private String haveChoosedLev;

    // 已经选择过的
    private String haveChoosedCustom;

    // 产品实体
    private Product product;

    // 类目列表
    private List<Category> cateList;

    // 返回的json节点
    private String nodes;

    // 查看方式
    private String viewType;

    // 包含的客户实体
    private List<UserInfoDO> userList = Lists.newArrayList();

    private ViewProductSku viewProductSku = new ViewProductSku();

    // 已拥有的类别Id
    private List<CouponProduct> haveCate;

    // 客户个体
    private UserInfoDO user;

    // 启用停用标示
    private int startParam;

    // 已经选择过的供应商id；
    private String haveChoosedSuplly;

    // 优惠券流水
    private CouponGrantFlow couponGrantFlow;

    private MerchantInfoOrSuppliers suppliersInfo;

    // 接受到的供应商实体id
    private String supplyArry;
    private List<MerchantInfoOrSuppliers> supplierList;

    // 优惠券优化部分调用端口1
    private String callPath;
    private String code;
    private String supplierTypes;

    /**
     * 进入优惠券新增页面
     * 
     * @return
     * @throws Exception
     */
    public String couponShow() throws Exception {
        // 初始化下拉框
        super.getRequest().setAttribute("CouponGrantTypeMap", CouponGrantTypeMap.getMap());
        // 初始化类目列表
        Category c = new Category();
        c.setParentId(new Long(0));
        c.setIsPhy(1);
        c.setStatus(1);
        cateList = categoryService.queryCategoryList(c);
        nodes = JSON.toJSONString(cateList);
        Date date = new Date();
        // 设置优惠券默认开始时间和结束时间
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.MONTH, 1);
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        Date monthAfter = sim.parse(sim.format(ca.getTime()));
        Date monthNow = sim.parse(sim.format(date));
        coupon.setStarttime(monthNow);
        coupon.setEndtime(monthAfter);
        return SUCCESS;
    }

    /**
     * 进入优惠券查看页面
     */
    public String queryCouponDetail() throws Exception {
        if (coupon.getCouponId() != null) {
            coupon = CouponService.queryCouponById(coupon.getCouponId());
        }
        Category c = new Category();
        c.setParentId(new Long(0));
        c.setIsPhy(1);
        c.setStatus(1);
        // 填充供应商信息
        supplierList = Lists.newArrayList();
        haveChoosedSuplly = coupon.getShopCode();
        if (haveChoosedSuplly != null && !haveChoosedSuplly.equals("")) {
            String[] supIdList = haveChoosedSuplly.split(",");
            for (String supplierId : supIdList) {
                MerchantInfoOrSuppliers suppiler =
                        supplierAuditService.findSuppliersInfoBySuppId(Long.valueOf(supplierId));
                supplierList.add(suppiler);
            }

        }

        // 查询类别
        cateList = categoryService.queryCategoryList(c);
        nodes = JSON.toJSONString(cateList);
        super.getRequest().setAttribute("CouponGrantTypeMap", CouponGrantTypeMap.getMap());
        super.getRequest().setAttribute("CouponStatus", CouponStatusMap.getMap());
        // 查询优惠券包含的skucode
        couponList = couponProductImpl.queryListByCouponId(coupon.getCouponId());

        UserInfoDO user = new UserInfoDO();
        // 查看单个会员
        if (!StringUtils.isEmpty(coupon.getCustomId())) {
            String customId[] = coupon.getCustomId().split(",");

            for (String custom : customId) {
                user.setLoginId(Integer.parseInt(custom.trim()));
                user = customerRemoteService.selectByPrimaryKey(Integer.parseInt(custom.trim()));
                userList.add(user);
            }
        }
        // 查询包含的类别id
        haveCate = couponProductImpl.queryCateListByCouponId(coupon.getCouponId());
        return SUCCESS;
    }

    /**
     * 优惠券查询方法
     */
    public String gotoQueryCouponList() {
        try {
            if (page == null) {
                page = new Page();
            }
            super.getRequest().setAttribute("CouponRuleStatus", CouponRuleStatusMap.getMap());
            super.getRequest().setAttribute("CouponSupplierType", CouponSupplierTypeMap.getMap());
            super.getRequest().setAttribute("CouponGrantType", CouponGrantTypeMap.getMap());
            super.getRequest().setAttribute("CouponStatus", CouponStatusMap.getMap());
            page = CouponService.queryCouponList(page, coupon);
        } catch (Exception e) {
            logger.error("优惠券查询方法异常：", e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 
     * 为了防止页面卡，对进入选择关联产品的页面初始化不搜索
     */
    public String gotoCouponProduct() {
        // 取得级联菜单

        // 大类
        product = new Product();
        getBcategoryList(new Long(0));
        if (product != null) {
            // 中类
            if (product.getmCategoryId() != null && product.getmCategoryId().intValue() != 0) {
                getMcategoryList(product.getbCategoryId());
            }
            // 小类
            if (product.getCategoryId() != null && product.getCategoryId().intValue() != 0) {
                getScategoryList(product.getmCategoryId());
            }
        }

        return SUCCESS;
    }

    /**
     * 进入选择关联产品页面
     * 
     * @return
     */
    public String chooseCouponProduct() {
        try {
            if (page == null) {
                page = new Page();
            }
            if (product == null) {
                product = new Product();
            }
            if (viewProductSku == null) {
                viewProductSku = new ViewProductSku();
            }
            // 新优惠券规则添加条件
            if ("1".equals(callPath)) {
                // 等于null 说明是入驻商家
                if (StringUtils.isBlank(supplierTypes)) {
                    // 查询当前入驻商家产品
                    viewProductSku.setShopCode(code);
                } else if ("2".equals(supplierTypes)) {
                    // 自营代销的产品 传入入 驻的类别
                    viewProductSku.setSupplierType(SuppliersType.SELL.getStatus().intValue());
                }
            }
            // 标记新版本优惠券适用
            viewProductSku.setEditCode(1);

            viewProductSku.setCategoryId(product.getCategoryId());
            if (product.getbCategoryId() != null) {
                viewProductSku.setBCategoryId(product.getbCategoryId());
            }
            if (product.getmCategoryId() != null) {
                viewProductSku.setMCategoryId(product.getmCategoryId());
            }
            viewProductSkuService.searchShelfByPageByUser(page, viewProductSku, "B2B",
                    getLoginUserId());
            // 大类
            getBcategoryList(new Long(0));
            // 中类
            if (product.getbCategoryId() != null && product.getbCategoryId().intValue() != 0) {
                getMcategoryList(product.getbCategoryId());
            }

            // 小类
            if (product.getmCategoryId() != null && product.getmCategoryId().intValue() != 0) {
                getScategoryList(product.getmCategoryId());
            }
            // getCategoryList();
        } catch (Exception e) {
            logger.error("查询类目信息产品信息异常：", e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * ajax 方法，将获取的id 转化为table表格返回值
     * 
     * @return
     * @throws Exception
     * @throws NumberFormatException
     */
    public String getIdreturnTable() throws NumberFormatException, Exception {
        Set<String> old = new HashSet<String>();
        // 筛选
        if (StringUtils.isNotBlank(haveChoosedPro)) {
            int index = haveChoosedPro.indexOf(",");
            if (index > 0) {
                String haveChoosedPros[] = haveChoosedPro.split(",");
                for (String a : haveChoosedPros) {
                    old.add(a);
                }
            }
        }
        if (StringUtils.isNotBlank(productArry)) {
            int index = productArry.indexOf(",");
            if (index > 0) {
                String haveChoosedPros[] = productArry.split(",");
                for (String a : haveChoosedPros) {
                    old.add(a);
                }
            }
        }

        String printTable = "";
        Iterator<String> it = old.iterator();
        int i = 0;
        while (it.hasNext()) {
            String value = it.next();
            viewProductSku = viewProductSkuService.findByProductSkucode(value);
            printTable += " <tr id=tr" + i
                    + "><td style='width: 156px'><input type='checkbox' name='couponProduct.relatedId'"
                    + "value='" + viewProductSku.getProductSkuCode()
                    + "' id='couponProductId' ></td><td style='width: 263px'><span name='haveChoosedPro'>"
                    + viewProductSku.getProductSkuCode() + "</span></td><td style='width: 151px;' >"
                    + viewProductSku.getProcuctName()
                    + "</td><td style='width: 170px;' ><input class='delBtn' onclick=\"del('tr" + i
                    + "')\"></input></td> </tr>";
            i++;
        }
        this.writeJson(printTable);
        return null;
    }

    /**
     * 根据客户生成客户表
     * 
     * @return
     * @throws Exception
     */
    public String getRetruncustom() throws Exception {
        // CustomerRemoteService customerService =
        // (CustomerRemoteService) RemoteTool.getRemote("03", "customerRemoteService");
        Set<String> old = new HashSet<String>();
        // 筛选
        if (StringUtils.isNotBlank(haveChoosedLev)) {
            int index = haveChoosedLev.indexOf(",");
            if (index > 0) {
                String haveChoosedcustoms[] = haveChoosedLev.split(",");
                for (String a : haveChoosedcustoms) {
                    old.add(a);
                }
            }
        }
        if (StringUtils.isNotBlank(customArry)) {
            int index = customArry.indexOf(",");
            if (index > 0) {
                String haveChoosedcustoms[] = customArry.split(",");
                for (String a : haveChoosedcustoms) {
                    old.add(a);
                }
            }
        }

        String printTable = "";
        Iterator<String> it = old.iterator();
        int i = 0;
        String name = "";
        while (it.hasNext()) {
            String value = it.next();
            UserInfoDO info = new UserInfoDO();
            info.setLoginId(Integer.parseInt(value));
            info = customerRemoteService.selectByLoginId(info);
            name = info.getName() != null ? info.getName() : "";
            printTable += " <tr id=trcustom" + i
                    + "><td style='width: 69px'><input type='checkbox' name='coupon.customId' "
                    + "value='" + value
                    + "' id='couponProductId' ></td><td style='width: 113px'><div style='display:none'><span name='haveChoosedCustom'>"
                    + info.getLoginId() + "</span></div>" + info.getLoginAccount() + "</td><td  >"
                    + name
                    + "</td><td  style='width: 104px;' ><input class='delBtn'  onclick=\"delCustom('"
                    + i + "')\"></input></td></tr>";
            i++;
        }
        this.writeJson(printTable);
        return null;
    }

    /**
     * 选择个体会员
     * 
     */
    public String chooseCouponcustom() throws Exception {
        if (page == null) {
            page = new Page();
            user = new UserInfoDO();
            user.setStatus(1);
        }
        user.setStatus(1);
        // 查询总条数
        int pagecount = 1;
        // 总页数
        int count = customerRemoteService.byCountBasicUserInfo(user);
        if (count > 1) {
            pagecount = (count - 1) / page.getPageSize() + 1;
        }
        page.setRecordCount(count);
        page.setPageCount(pagecount);
        // 分页查询
        int pageNo = page.getPageNo();// 当前查询第几页
        if (pageNo == 0)
            pageNo = 1;// 首次查询第一页
        int pageSize = page.getPageSize(); // 每页显示记录数
        int skip = (pageNo - 1) * pageSize;
        int max = (pageNo - 1) * pageSize + pageSize;
        user.setStartIndex(skip);
        user.setEndIndex(max);
        // 只查询注册会员
        page.setPageNo(pageNo);// 当前查询第几页
        page.setDataList(customerRemoteService.queryBasicUserInfo(user));
        return SUCCESS;
    }

    /**
     * 校验优惠券能否删除
     * 
     * @return
     */
    public String canDelCoupon() throws Exception {
        String delCoupon[] = couponProductId.split(",");
        for (String delId : delCoupon) {
            if (delId != null) {
                coupon = CouponService.queryCouponById(Long.parseLong(delId));
                // 如果是未发放
                if ((coupon.getStatus() + "").equals(CouponStatus.NOTGIVE_COUPONSTATUS.getType())) {
                    this.writeJson("weifafang");

                } else if ((coupon.getStatus() + "")
                        .equals(CouponStatus.HAVEGIVE_COUPONSTATUS.getType())) {
                    this.writeJson("yifafang");
                    break;
                }
            }
        }
        return null;
    }

    public String checkCouponNameRepeat() throws SQLException {
        int result = CouponService.checkCouponNameRepeat(haveChoosedPro);
        this.writeJson(result);
        return null;
    }

    /**
     * 删除优惠券
     * 
     * @return
     * @throws Exception
     */
    public String doDelCoupon() throws Exception {
        String delCoupon[] = couponProductId.split(",");
        Coupon coupon = new Coupon();
        for (String couponId : delCoupon) {
            coupon.setCouponId(new Long(couponId));
            // 删除业务
            CouponService.delCoupon(coupon);
        }

        return this.gotoQueryCouponList();
    }

    /**
     * 停用或启用注册类型的优惠券
     * 
     * @return
     * @throws SQLException
     */
    public String satrOrPuseCoupon() throws SQLException {
        Coupon couponnew = new Coupon();
        // 需要启用
        if (startParam == 1) {
            couponnew.setCouponId(Long.parseLong(couponProductId));
            couponnew.setIsValide("2");
        } else {
            couponnew.setCouponId(Long.parseLong(couponProductId));
            couponnew.setIsValide("1");
        }
        CouponService.updateCouponById(couponnew);
        return this.gotoQueryCouponList();
    }

    public String testC() throws Exception {
        UserInfoDO user = new UserInfoDO();
        user.setLoginId(5643);
        UserInfoDO user1 = new UserInfoDO();
        user1.setLoginId(333);
        List<UserInfoDO> u = new ArrayList<UserInfoDO>();
        u.add(user);
        u.add(user1);

        // // CouponGrant couponGrant= new CouponGrant();
        // // couponGrant.setCouponId(464L);
        // // couponGrant.setCouponStatus(7L);
        // // couponGrant.setGrantUpdatetime(new Date());
        // // couponGrant.setCustomId(333);
        // OrderVo ordervo2 = new OrderVo();
        // ordervo2.setProductSkuCode("S001850650001");
        // ordervo2.setProductPrict(new BigDecimal(33));
        // List<OrderVo> orderList = new ArrayList<OrderVo>();
        // orderList.add(ordervo);
        // orderList.add(ordervo2);
        // couponRemoteService.getCanUseCoupon("4655", orderList, new
        // BigDecimal(33));

        return SUCCESS;
    }

    /**
     * ajax 方法，将获取的id 转化为table表格返回值,供应商使用
     * 
     * @return
     * @throws Exception
     * @throws NumberFormatException
     */
    public String getIdreturnTableForSupply() throws NumberFormatException, Exception {
        Set<String> old = new HashSet<String>();
        // 筛选
        if (StringUtils.isNotBlank(haveChoosedSuplly)) {
            int index = haveChoosedSuplly.indexOf(",");
            if (index > 0) {
                String haveChoosedPros[] = haveChoosedSuplly.split(",");
                for (String a : haveChoosedPros) {
                    old.add(a);
                }
            } else {
                if (haveChoosedSuplly.length() > 0) {
                    old.add(haveChoosedSuplly);
                }
            }
        }
        if (StringUtils.isNotBlank(supplyArry)) {
            int index = supplyArry.indexOf(",");
            if (index > 0) {
                String haveChoosedPros[] = supplyArry.split(",");
                for (String a : haveChoosedPros) {
                    old.add(a);
                }
            }
        }

        String printTable = "";
        Iterator<String> it = old.iterator();
        int i = 0;
        while (it.hasNext()) {
            String value = it.next();
            suppliersInfo = supplierAuditService.findSuppliersInfoBySuppId(Long.valueOf(value));
            printTable += " <tr id=tr" + i
                    + "><td style='width: 156px'><input type='checkbox' name='haveChoosedSuplly'"
                    + "value='" + suppliersInfo.getSupplierId()
                    + "' id='couponSupplyId' ></td><td style='width: 263px'><span name='shopCode'>"
                    + suppliersInfo.getSupplierId() + "</span></td><td style='width: 151px;' >"
                    + suppliersInfo.getCorporateName()
                    + "</td><td style='width: 170px;' ><input class='delBtn' onclick=\"del('tr" + i
                    + "')\"></input></td> </tr>";
            i++;
        }
        this.writeJson(printTable);
        return null;
    }

    /**
     * 进入选择关联供应商页面
     * 
     * @return
     */
    public String chooseCouponSupply() {
        try {
            if (page == null)
                page = new Page();
            if (suppliersInfo == null)
                suppliersInfo = new MerchantInfoOrSuppliers();
            if (haveChoosedSuplly != null && !haveChoosedSuplly.equals("")) {
                String end = haveChoosedSuplly.substring(haveChoosedSuplly.length() - 1,
                        haveChoosedSuplly.length());
                if (end.equals(","))
                    haveChoosedSuplly =
                            haveChoosedSuplly.substring(0, haveChoosedSuplly.length() - 1);

            }
            supplierAuditService.showSupplierLsit(suppliersInfo, page, haveChoosedSuplly);

        } catch (Exception e) {
            logger.error("查询供应商信息列表出现异常" + e.getMessage(), e);
        }
        return SUCCESS;
    }

    /**
     * 进入供应列表 为了防止页面卡，对进入选择关联产品的页面初始化不搜索
     */
    public String gotoCouponSupply() {
        // 取得级联菜单

        // 大类
        product = new Product();

        return SUCCESS;
    }



    /**
     * 进入优惠券流水查看页面
     */
    public String queryCouponGrantFlow() throws Exception {
        // 初始化下拉框
        super.getRequest().setAttribute("CouponGrantFlowTypeMap", CouponGrantFlowTypeMap.getMap());
        CouponGrantFlow couponGrantFlowExample = new CouponGrantFlow();
        if (couponGrantFlow != null) {
            if (couponGrantFlow.getOrderCode() != null
                    && !couponGrantFlow.getOrderCode().equals("")) {
                couponGrantFlowExample.setOrderCode(couponGrantFlow.getOrderCode());
            }
            if (couponGrantFlow.getCouponGrantId() != null) {
                couponGrantFlowExample.setCouponGrantId(couponGrantFlow.getCouponGrantId());
            }
            if (couponGrantFlow.getCouponGrantFlowType() != null) {
                couponGrantFlowExample
                        .setCouponGrantFlowType(couponGrantFlow.getCouponGrantFlowType());
            }
        }
        if (page == null) {
            page = new Page();
        }
        page = couponGrantFlowService.selectList(couponGrantFlowExample, page);
        return SUCCESS;
    }

    public CouponService getCouponService() {
        return CouponService;
    }

    public void setCouponService(CouponService couponService) {
        CouponService = couponService;
    }



    public String getCallPath() {
        return callPath;
    }

    public void setCallPath(String callPath) {
        this.callPath = callPath;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSupplierTypes() {
        return supplierTypes;
    }

    public void setSupplierTypes(String supplierTypes) {
        this.supplierTypes = supplierTypes;
    }



    @Override
    public Coupon getModel() {
        if (coupon == null)
            coupon = new Coupon();
        return coupon;
    }

    public ProductService getProductServiceImpl() {
        return productServiceImpl;
    }

    public void setProductServiceImpl(ProductService productServiceImpl) {
        this.productServiceImpl = productServiceImpl;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public String getProductArry() {
        return productArry;
    }

    public void setProductArry(String productArry) {
        this.productArry = productArry;
    }

    @Override
    public Page getPage() {
        return page;
    }

    @Override
    public void setPage(Page page) {
        this.page = page;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getCouponProductId() {
        return couponProductId;
    }

    public void setCouponProductId(String couponProductId) {
        this.couponProductId = couponProductId;
    }

    public String getHaveChoosedPro() {
        return haveChoosedPro;
    }

    public void setHaveChoosedPro(String haveChoosedPro) {
        this.haveChoosedPro = haveChoosedPro;
    }

    public List<CouponProduct> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<CouponProduct> couponList) {
        this.couponList = couponList;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public CategoryService getCategoryService() {
        return categoryService;
    }

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public List<Category> getCateList() {
        return cateList;
    }

    public void setCateList(List<Category> cateList) {
        this.cateList = cateList;
    }

    public String getNodes() {
        return nodes;
    }

    public void setNodes(String nodes) {
        this.nodes = nodes;
    }

    public String getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(String categoryIds) {
        this.categoryIds = categoryIds;
    }

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public String getViewType() {
        return viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    public CouponProductService getCouponProductImpl() {
        return couponProductImpl;
    }

    public void setCouponProductImpl(CouponProductService couponProductImpl) {
        this.couponProductImpl = couponProductImpl;
    }

    public String getHaveChoosedLev() {
        return haveChoosedLev;
    }

    public void setHaveChoosedLev(String haveChoosedLev) {
        this.haveChoosedLev = haveChoosedLev;
    }

    public String getLeveArry() {
        return leveArry;
    }

    public void setLeveArry(String leveArry) {
        this.leveArry = leveArry;
    }

    public ViewProductSkuService getViewProductSkuService() {
        return viewProductSkuService;
    }

    public void setViewProductSkuService(ViewProductSkuService viewProductSkuService) {
        this.viewProductSkuService = viewProductSkuService;
    }

    public ViewProductSku getViewProductSku() {
        return viewProductSku;
    }

    public void setViewProductSku(ViewProductSku viewProductSku) {
        this.viewProductSku = viewProductSku;
    }

    public List<CouponProduct> getHaveCate() {
        return haveCate;
    }

    public void setHaveCate(List<CouponProduct> haveCate) {
        this.haveCate = haveCate;
    }

    public UserInfoDO getUser() {
        return user;
    }

    public void setUser(UserInfoDO user) {
        this.user = user;
    }

    public String getHaveChoosedCustom() {
        return haveChoosedCustom;
    }

    public void setHaveChoosedCustom(String haveChoosedCustom) {
        this.haveChoosedCustom = haveChoosedCustom;
    }

    public String getCustomArry() {
        return customArry;
    }

    public void setCustomArry(String customArry) {
        this.customArry = customArry;
    }

    public List getUserList() {
        return userList;
    }

    public void setUserList(List userList) {
        this.userList = userList;
    }

    public int getStartParam() {
        return startParam;
    }

    public void setStartParam(int startParam) {
        this.startParam = startParam;
    }

    public CouponGrantFlow getCouponGrantFlow() {
        return couponGrantFlow;
    }

    public void setCouponGrantFlow(CouponGrantFlow couponGrantFlow) {
        this.couponGrantFlow = couponGrantFlow;
    }

    public String getHaveChoosedSuplly() {
        return haveChoosedSuplly;
    }

    public void setHaveChoosedSuplly(String haveChoosedSuplly) {
        this.haveChoosedSuplly = haveChoosedSuplly;
    }

    public MerchantInfoOrSuppliers getSuppliersInfo() {
        return suppliersInfo;
    }

    public void setSuppliersInfo(MerchantInfoOrSuppliers suppliersInfo) {
        this.suppliersInfo = suppliersInfo;
    }

    public String getSupplyArry() {
        return supplyArry;
    }

    public void setSupplyArry(String supplyArry) {
        this.supplyArry = supplyArry;
    }

    public List<MerchantInfoOrSuppliers> getSupplierList() {
        return supplierList;
    }

    public void setSupplierList(List<MerchantInfoOrSuppliers> supplierList) {
        this.supplierList = supplierList;
    }

}
