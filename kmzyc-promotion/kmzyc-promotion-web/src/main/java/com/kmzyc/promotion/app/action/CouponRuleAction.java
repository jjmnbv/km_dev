package com.kmzyc.promotion.app.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.enums.SuppliersStatus;
import com.kmzyc.promotion.app.enums.SuppliersType;
import com.kmzyc.promotion.app.maps.CouponRuleStatusMap;
import com.kmzyc.promotion.app.maps.CouponSupplierTypeMap;
import com.kmzyc.promotion.app.service.CategoryService;
import com.kmzyc.promotion.app.service.CouponProductService;
import com.kmzyc.promotion.app.service.CouponService;
import com.kmzyc.promotion.app.service.SupplierAuditService;
import com.kmzyc.promotion.app.vobject.Category;
import com.kmzyc.promotion.app.vobject.Coupon;
import com.kmzyc.promotion.app.vobject.CouponProduct;
import com.kmzyc.supplier.model.MerchantInfoOrSuppliers;
import com.opensymphony.xwork2.ModelDriven;


@SuppressWarnings("rawtypes")
@Controller("couponRuleAction")
@Scope(value = "prototype")
public class CouponRuleAction extends BaseAction implements ModelDriven {

    private static final long serialVersionUID = 1L;
    // 日志记录
    private static final Logger logger = LoggerFactory.getLogger(CouponRuleAction.class);
    /**
     * 优惠券接口类
     */
    @Resource(name = "CouponService")
    private CouponService CouponService;
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
    // 供应商
    @Resource
    private SupplierAuditService supplierAuditService;

    // 优惠券实体
    private Coupon coupon;
    // 分页实体
    private Page page;
    // 规则编辑方式
    private String viewType;
    // 类目列表
    private List<Category> cateList;
    // 返回的json节点
    private String nodes;
    // 接受优惠的类目Id
    private String categoryIds;
    // 页面上选择的产品列表
    private List<CouponProduct> couponList;
    // 规则id
    private String couponId;
    // 已拥有的类别Id
    private List<CouponProduct> haveCate;
    // 已选择的规则
    private String couponProductId;
    // 新编辑的优惠券规则名称
    private String newCouponName;
    // 查询的条件
    private MerchantInfoOrSuppliers selectSuppliersInfo = new MerchantInfoOrSuppliers();
    // 供应商
    private MerchantInfoOrSuppliers supplierObj;
    // 商品
    private CouponProduct couponProduct;
    private String[] useLimitsTypes;


    // 规则列表
    public String PageList() {
        try {
            if (page == null) {
                page = new Page();
            }
            if (coupon == null) {
                coupon = new Coupon();
            }
            super.getRequest().setAttribute("CouponRuleStatus", CouponRuleStatusMap.getMap());
            super.getRequest().setAttribute("CouponSupplierType", CouponSupplierTypeMap.getMap());
            page = CouponService.selectByCondition(page, coupon);
        } catch (Exception e) {
            logger.error("获取规则列表失败" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    // 查看规则详情
    public String pageShow() {
        // 初始化类目列表
        Category c = new Category();
        c.setParentId(new Long(0));
        c.setIsPhy(1);
        c.setStatus(1);
        try {
            // 查询类别
            cateList = categoryService.queryCategoryList(c);
            nodes = JSON.toJSONString(cateList);
            // 查看和修改 初始化优惠券规则
            if (viewType.equals("view")
                    || viewType.equals("show") && StringUtils.isNotBlank(couponId)) {
                // 获取规则
                if (coupon == null) {
                    coupon = new Coupon();
                }
                coupon.setCouponId(Long.parseLong(couponId));
                coupon = CouponService.selectCoupon(coupon);
                // 得到供应商id 多个供应商id和名称
                if (StringUtils.isNotBlank(coupon.getShopCode())) {
                    String[] a = coupon.getShopCode().split(",");
                    String cName = "";
                    for (int i = 0; i < a.length; i++) {
                        // 只有一个对象
                        supplierObj = supplierAuditService.queryBySupplierId(Long.valueOf(a[i]));
                        if (StringUtils.isBlank(cName)) {
                            cName = supplierObj.getCorporateName();
                        } else {
                            cName = cName + "," + supplierObj.getCorporateName();
                        }
                    }
                    supplierObj.setCorporateName(cName);
                }
                // 查询包含的类别id
                haveCate = couponProductImpl.queryCateListByCouponId(coupon.getCouponId());
                // 查询优惠券包含的skucode
                couponList = couponProductImpl.queryListByCouponId(coupon.getCouponId());
                super.getRequest().setAttribute("CouponRuleStatus", CouponRuleStatusMap.getMap());
                super.getRequest().setAttribute("CouponSupplierType",
                        CouponSupplierTypeMap.getMap());
            }
        } catch (Exception e) {
            logger.error("初始化优惠券规则信息失败" + e.getMessage(), e);
        }
        return "pageShow";
    }

    public String saveCoupon() {
        int couponRuleId = 0;
        try {
            if (couponProduct != null) {
                String relatedId = couponProduct.getRelatedId();
                if (StringUtils.isNotBlank(relatedId)) {
                    String relatedIdStr = relatedId.replaceAll(" ", "");
                    String[] a = relatedIdStr.split(",");
                    for (int i = 0; i < a.length; i++) {
                        if (couponList == null) {
                            couponList = new ArrayList<CouponProduct>();
                        }
                        CouponProduct couponProduct = new CouponProduct();
                        couponProduct.setRelatedId(a[i]);
                        couponList.add(couponProduct);
                    }
                }
            }
            // begin 规则使用限制，add by zhuyanling 20160418
            if (coupon != null) {
                if (useLimitsTypes != null && useLimitsTypes.length > 0) {
                    StringBuffer limitsTypeBuffer = new StringBuffer();
                    for (String limitsType : useLimitsTypes) {
                        if (StringUtils.isNotBlank(limitsType)) {
                            limitsTypeBuffer.append(limitsType).append(",");
                        }
                    }
                    coupon.setUseLimitsType(limitsTypeBuffer.toString().substring(0,
                            limitsTypeBuffer.length() - 1));
                } else {
                    coupon.setUseLimitsType("");
                }

            }
            // end 规则使用限制，add by zhuyanling 20160418
            // 如果是新增
            if (coupon != null && viewType.equals("add")) {
                couponRuleId = CouponService.insertRule(coupon, couponList, categoryIds);
            }
            // 修改
            else if (coupon != null && viewType.equals("view")) {
                CouponService.updateCoupon(coupon, couponList, categoryIds);
                couponRuleId = coupon.getCouponId().intValue();
            }
            // 注入定时任务
            if (coupon != null && coupon.getTimeType() == 1) {
                CouponService.creatJob(Long.valueOf(couponRuleId));
            }
        } catch (Exception e) {
            logger.error("优惠券规则操作失败" + e.getMessage(), e);
        }
        return this.PageList();
    }


    // 删除优惠券规则
    public String ruleDelete() {
        try {
            if (coupon == null) {
                coupon = new Coupon();
            }
            // 删除多选框选择规则
            if (StringUtils.isNotBlank(couponProductId)) {
                String delCoupon[] = couponProductId.split(",");
                for (String delCouponId : delCoupon) {
                    coupon.setCouponId(Long.parseLong(delCouponId));
                    CouponService.delCoupon(coupon);
                }
            }
            // 单条删除
            if (StringUtils.isNotBlank(couponId)) {
                // 查询规则是否能删除
                coupon.setCouponId(Long.parseLong(couponId));
                CouponService.delCoupon(coupon);
            }
        } catch (Exception e) {
            logger.error("规则删除失败" + e.getMessage(), e);
        }
        return this.PageList();
    }

    // 优惠券规则名称重复验证
    public String checkCouponName() {
        Map<String, String> map = Maps.newHashMap();
        try {
            if (viewType.equals("view")) {
                // 根据规则id获取当前规则的规则名称
                if (coupon == null) {
                    coupon = new Coupon();
                }
                coupon.setCouponId(Long.parseLong(couponId));
                coupon = CouponService.selectCoupon(coupon);
                map.put("oldCouponName", coupon.getCouponName());
            }
            // 绑定查询条件
            map.put("newCouponName", newCouponName);
            int row = CouponService.selectCouponName(map);
            this.writeJson(row);
        } catch (Exception e) {
            logger.error("验证优惠券名称重复失败" + e.getMessage(), e);
        }
        return null;
    }

    // 初始化入驻供应商列表
    public String checkSupplier() {
        if (page == null) {
            page = new Page();
        }
        try {
            // 入驻
            selectSuppliersInfo.setSupplierType(SuppliersType.SELL.getStatus());
            // 审核通过的
            selectSuppliersInfo.setStatus(SuppliersStatus.AUDIT.getStatus());
            supplierAuditService.showSupplierLsit(selectSuppliersInfo, page);
        } catch (Exception e) {
            logger.error("加载供应商列表" + e.getMessage(), e);
        }
        return "checkSupplier";
    }



    public CouponProduct getCouponProduct() {
        return couponProduct;
    }

    public void setCouponProduct(CouponProduct couponProduct) {
        this.couponProduct = couponProduct;
    }

    public MerchantInfoOrSuppliers getSupplierObj() {
        return supplierObj;
    }

    public void setSupplierObj(MerchantInfoOrSuppliers supplierObj) {
        this.supplierObj = supplierObj;
    }

    public MerchantInfoOrSuppliers getSelectSuppliersInfo() {
        return selectSuppliersInfo;
    }

    public void setSelectSuppliersInfo(MerchantInfoOrSuppliers selectSuppliersInfo) {
        this.selectSuppliersInfo = selectSuppliersInfo;
    }

    public String getNewCouponName() {
        return newCouponName;
    }

    public void setNewCouponName(String newCouponName) {
        this.newCouponName = newCouponName;
    }

    public String getCouponProductId() {
        return couponProductId;
    }

    public void setCouponProductId(String couponProductId) {
        this.couponProductId = couponProductId;
    }

    public List<CouponProduct> getHaveCate() {
        return haveCate;
    }

    public void setHaveCate(List<CouponProduct> haveCate) {
        this.haveCate = haveCate;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(String categoryIds) {
        this.categoryIds = categoryIds;
    }

    public List<CouponProduct> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<CouponProduct> couponList) {
        this.couponList = couponList;
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

    public String getViewType() {
        return viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }



    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    @Override
    public Page getPage() {
        return page;
    }

    @Override
    public void setPage(Page page) {
        this.page = page;
    }

    public String[] getUseLimitsTypes() {
        return useLimitsTypes;
    }

    public void setUseLimitsTypes(String[] useLimitsTypes) {
        this.useLimitsTypes = useLimitsTypes;
    }

    @Override
    public Object getModel() {
        return null;
    }

}
