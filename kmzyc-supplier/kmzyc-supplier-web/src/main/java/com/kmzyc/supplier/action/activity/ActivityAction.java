package com.kmzyc.supplier.action.activity;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.framework.page.Pagination;
import com.kmzyc.b2b.vo.UserBaseInfo;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.supplier.action.SupplierBaseAction;
import com.kmzyc.supplier.enums.ActivitySupplierStatus;
import com.kmzyc.supplier.maps.ActivitySupplierTypeMap;
import com.kmzyc.supplier.maps.SupplierEntryStatusMap;
import com.kmzyc.supplier.model.AccountBasics;
import com.kmzyc.supplier.model.AccountInfo;
import com.kmzyc.supplier.service.AccountService;
import com.kmzyc.supplier.service.ActivityService;
import com.kmzyc.supplier.service.ActivitySkuService;
import com.kmzyc.supplier.service.ActivitySupplierService;
import com.kmzyc.supplier.service.MerchantInfoService;
import com.kmzyc.supplier.service.RechargeDetailsService;
import com.kmzyc.supplier.util.RedisLock;
import com.kmzyc.supplier.vo.ActivityInfoVo;
import com.kmzyc.supplier.vo.ActivitySkuVo;
import com.kmzyc.user.remote.service.CustomerRemoteService;
import com.kmzyc.user.remote.service.TrationListRemoteService;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.enums.ActivityEntryStatus;
import com.pltfm.app.enums.ActivityStatus;
import com.pltfm.app.enums.ActivityType;
import com.pltfm.app.enums.IsValidStatus;
import com.pltfm.app.maps.ActivityLevelMap;
import com.pltfm.app.maps.ActivityStatusMap;
import com.pltfm.app.maps.ActivityTypeMap;
import com.pltfm.app.vobject.ActivityInfo;
import com.pltfm.app.vobject.ActivitySku;
import com.pltfm.app.vobject.BnesAcctTransactionQuery;
import com.pltfm.app.vobject.ViewProductSku;

/**
 * 功能：活动action
 *
 * @author Damon
 * @since 2016/3/17 15:07
 */
@Controller("activityAction")
@Scope("prototype")
public class ActivityAction extends SupplierBaseAction {

    private Logger log = LoggerFactory.getLogger(ActivityAction.class);

    @Resource
    private ActivitySupplierService activitySupplierService;

    @Resource
    private ActivityService activityService;

    @Resource
    private ActivitySkuService activitySkuService;

    @Resource
    private AccountService accountService;

    @Resource
    private RechargeDetailsService rechargeDetailsService;

    @Resource
    private MerchantInfoService merchantInfoService;

    @Resource
    private RedisLock redisLock;


    @Resource
    private CustomerRemoteService customerRemoteService;

    @Resource
    private TrationListRemoteService trationListRemoteService;

    private ActivityInfo activity;

    /**
     * 活动查询参数
     */
    private ActivityInfoVo activityParam;

    /**
     * 活动类型
     */
    private String activityType;

    /**
     * 活动id
     */
    private String activityId;

    /**
     * 供应商对应活动类型
     */
    private String activitySupplierType;

    /**
     * 活动详情  view查看、save新增、update修改
     */
    private String entryType;

    /**
     * sku查询参数
     */
    private ViewProductSku viewProductSku;

    /**
     * 品牌id集合
     */
    private String brandIds;

    /**
     * 返回信息
     */
    private ResultMessage resultMessage = new ResultMessage();

    /**
     * 单个参与活动的sku
     */
    private ActivitySku activitySku;

    /**
     * 新增参与活动的商品
     */
    private List<ActivitySkuVo> newActivitySkuList;

    /**
     * 已参与活动的商品
     */
    private List<ActivitySkuVo> existActivitySkuList;

    /**
     * 删除已参加活动的商品
     */
    private String deleteSkuId;

    /**
     * 已选择的sku商品
     */
    private String haveSkuId;

    /**
     * 支付密码
     */
    private String paymentPwd;

    /**
     * 活动总费用
     */
    private BigDecimal activityTotalPrice = new BigDecimal("0.00");

    private static final String PREFIX_REFUND_LOCK = "/locks/refund";

    private static final String PREFIX_PAY_LOCK = "/locks/pay";

    /**
     * amount充值金额
     */
    private String amount;

    /**
     * 付款http前缀
     */
    private final String payHttp = ConfigurationUtil.getString("PAY_HTTP");

    /**
     * 支付出现问题http前缀
     */
    private final String rootPath = ConfigurationUtil.getString("SUPPLIER_ROOT_PATH");

    /**
     * 1:结算明细
     * 其他:活动平台页面
     */
    private Integer pageType;

    /**
     * 选择商品
     *
     * @return
     */
    public String getProductSkuList4Activity() {
        try {
            if (viewProductSku == null) {
                viewProductSku = new ViewProductSku();
            }
            pagintion = getPagination(Constants.VIEW_PAGE, Integer.parseInt(super.getPageSize()));
            viewProductSku.setSkuStatus(IsValidStatus.VALID.getStatus());
            pagintion = activitySkuService.selectProductSkuList4Activity(getSupplyId(), brandIds,haveSkuId,
                    viewProductSku, pagintion);

            getAllCategoryList(viewProductSku.getBCategoryId(), viewProductSku.getMCategoryId());
        } catch (Exception e) {
            log.error("商品SKU列表查询错误："+e.getMessage());
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 获取活动列表
     *
     * @return
     */
    public String getActivityList() {
        //分页信息
        Pagination page = getPagination(Constants.VIEW_PAGE, Integer.parseInt(super.getPageSize()));

        //查看列表类型
        ActivitySupplierStatus type = ActivitySupplierTypeMap.getActivitySupplierType(this.activitySupplierType);
        if (type == null) {
            //为空，设置为查所有，并且将类型设为1
            type = ActivitySupplierStatus.ALL;
            activitySupplierType="1";
        }

        //分页查询
        if (activityParam == null) {
            activityParam = new ActivityInfoVo();
        }
        try {
            pagintion = activityService.getActivityList(type, activityParam, getSupplyId(), page);
        } catch (ServiceException e) {
            log.error("获取活动列表失败，", e);
            return ERROR;
        }

        Map<Integer, String> activityMap = new LinkedHashMap<Integer, String>(ActivityStatusMap.getActivityMap());
        if (type == ActivitySupplierStatus.MY) {
            getRequest().setAttribute("entryStatusMap", SupplierEntryStatusMap.getMap());
            activityMap.remove(ActivityStatus.NOT_ENTRY_TIME.getStatus());
        }
        getRequest().setAttribute("activityStatusMap", activityMap);
        getRequest().setAttribute("activityTypeMap", ActivityTypeMap.getMap());
        return SUCCESS;
    }

    /**
     * 查看活动详情
     *
     * @return
     */
    public String viewActivityInfo() {
        if (!"view".equals(entryType)) {
            return SUCCESS;
        }

        try {
            //获取活动信息
            activity = activityService.getActivityById(activityId);
            //获取当前的sku信息
            existActivitySkuList = activitySkuService.getActivitySku(getSupplyId(), activityId);
            //获取报名总费用
            if (CollectionUtils.isNotEmpty(existActivitySkuList)) {
                for (ActivitySkuVo skuVo : existActivitySkuList) {
                    activityTotalPrice = activityTotalPrice.add(skuVo.getSkuTotalPrice());
                }
            }
            //activityTotalPrice = activitySupplierService.getActivityTotalPrice(getSupplyId(), activityId);
            getRequest().setAttribute("activityTypeMap", ActivityTypeMap.getMap());
            getRequest().setAttribute("activityLevelMap", ActivityLevelMap.getMap());
            return SUCCESS;
        } catch (ServiceException e) {
            log.error("查看活动详情失败，", e);
            return ERROR;
        }
    }

    /**
     * 撤销报名
     *
     * @return
     */
    public String cancelActivity() {
        //1.获取锁
        String nodeKey = PREFIX_REFUND_LOCK + "/" + activityId + "/" + getSupplyId();
        try {
            log.info("线程[{}]开始去获取到撤销报名锁[{}].", new Object[]{Thread.currentThread().getName(), nodeKey});
            if (!redisLock.tryLock(nodeKey)) {
                log.error("线程[{}],没有获取到撤销报名锁{}.", new Object[]{Thread.currentThread().getName(), nodeKey});
                resultMessage.setIsSuccess(Boolean.FALSE);
                resultMessage.setMessage("非法撤销报名！");
                writeJson(resultMessage);
                return null;
            }
            log.info("线程[{}]已获取到撤销报名锁{}.", new Object[]{Thread.currentThread().getName(), nodeKey});

            //2.校验是否满足撤销报名的条件
            activity = activityService.getActivityById(activityId);
            resultMessage = activityService.checkMatchCancel(activity, activityId, getSupplyId());
            if (!resultMessage.getIsSuccess()) {
                writeJson(resultMessage);
                return null;
            }

            //3.撤销报名
            activityService.cancelActivityEntry(activityId, getSupplyId(), getLoginUserId(), getLoginUserName());
        } catch (Exception e) {
            log.error("撤销报名失败，失败信息：{}", e);
            resultMessage.setMessage("撤销报名失败，请联系管理员！");
            writeJson(resultMessage);
            return null;
        } finally {
            log.info("线程[{}]释放撤销报名锁[{}].", new Object[]{Thread.currentThread().getName(), nodeKey});
            redisLock.release(nodeKey);
        }

        resultMessage.setIsSuccess(Boolean.TRUE);
        resultMessage.setMessage("撤销成功！");
        writeJson(resultMessage);
        return null;
    }

    /**
     * 检查是否具有报名权限
     */
    public String checkAuthority() {
        try {
            //查询活动信息
            activity = activityService.getActivityById(activityId);

            //1.校验报名活动权限
            resultMessage = activityService.checkAuthority(activity, getSupplyId(), activityId, Boolean.FALSE);
            if (!resultMessage.getIsSuccess()) {
                writeJson(resultMessage);
                return null;
            }

            //2.检查活动供应商数量上限 / 2表示邀请情况，邀请不校验商家数量
            if (resultMessage.getMark() != 2) {
                resultMessage = activityService.checkSupplier(activityId, Boolean.TRUE, resultMessage);
                if (!resultMessage.getIsSuccess()) {
                    writeJson(resultMessage);
                    return null;
                }
            }
            resultMessage.setIsSuccess(Boolean.TRUE);
        } catch (Exception e) {
            log.error("检查是否具有报名权限错误：", e);
            resultMessage.setMessage("报名失败，请联系系统管理员！");
            resultMessage.setIsSuccess(Boolean.FALSE);
        }

        writeJson(resultMessage);
        return null;
    }

    /**
     * 进入活动报名页面
     *
     * @return
     */
    public String toRegisterActivity() {
        //必须为新增
        if (!"save".equals(entryType)) {
            return SUCCESS;
        }

        try {
            //查询活动信息
            activity = activityService.getActivityById(activityId);
            //报名前校验，防止手动输入活动id情况
            resultMessage = activityService.checkAuthority(activity, getSupplyId(), activityId, Boolean.FALSE);
            if (resultMessage.getIsSuccess() && resultMessage.getMark() != 2) {//2表示邀请情况，邀请不校验商家数量
                resultMessage = activityService.checkSupplier(activityId, Boolean.TRUE, resultMessage);
            }
            if (!resultMessage.getIsSuccess()) {
                return SUCCESS;
            }

            getRequest().setAttribute("activityTypeMap", ActivityTypeMap.getMap());
            getRequest().setAttribute("activityLevelMap", ActivityLevelMap.getMap());
            getBcategoryList(0l);
            resultMessage.setIsSuccess(Boolean.TRUE);
            return SUCCESS;
        } catch (ServiceException e) {
            log.error("进入活动报名页面失败，", e);
            return ERROR;
        }
    }

    /**
     * 保存活动报名
     *
     * @return
     */
    public String saveRegisterActivity() {
        try {
            //查询活动信息
            activity = activityService.getActivityById(activityId);

            //1.校验报名活动权限
            resultMessage = activityService.checkAuthority(activity, getSupplyId(), activityId, Boolean.FALSE);
            if (!resultMessage.getIsSuccess()) {
                writeJson(resultMessage);
                return null;
            }

            //2.检查sku相关数据
            resultMessage = activityService.checkSku(activity, activityId, getSupplyId(),
                    newActivitySkuList, Boolean.FALSE, existActivitySkuList, resultMessage);
            if (!resultMessage.getIsSuccess()) {
                deleteNewPic(resultMessage);
                writeJson(resultMessage);
                return null;
            }

            //3.检查活动供应商数量上限 /2表示邀请情况，邀请不校验商家数量
            if (resultMessage.getMark() != 2) {
                resultMessage = activityService.checkSupplier(activityId, Boolean.FALSE, resultMessage);
                if (!resultMessage.getIsSuccess()) {
                    deleteNewPic(resultMessage);
                    writeJson(resultMessage);
                    return null;
                }
            }

            //4.保存参加活动，修改邀请活动
            activitySupplierService.saveSupplierEntry(activityId, getSupplyId(), getLoginUserId(),
                    activity, newActivitySkuList);
        } catch (Exception e) {
            log.error("保存活动报名错误：", e);
            //出现异常，若商家数量减一则补一
            if (resultMessage.getMark() == 0) {
                activityService.incrSupplierMax(activityId);
            }

            deleteNewPic(resultMessage);
            resultMessage.setMessage("报名失败，请联系系统管理员！");
            resultMessage.setIsSuccess(Boolean.FALSE);
            writeJson(resultMessage);
            return null;
        }

        resultMessage.setIsSuccess(Boolean.TRUE);
        resultMessage.setObject(null);
        writeJson(resultMessage);
        return null;
    }

    /**
     * 检查是否具有编辑权限
     */
    public String checkUpdate() {
        try {
            //查询活动信息
            activity = activityService.getActivityById(activityId);

            //1.校验报名活动权限
            resultMessage = activityService.checkAuthority(activity, getSupplyId(), activityId, Boolean.TRUE);
            if (!resultMessage.getIsSuccess()) {
                writeJson(resultMessage);
                return null;
            }

            //2.检查当前是否为撤销报名并且还是非邀请的情况下进行的修改,若是将检查供应商是否已经达到活动商家数量上限
            Map<String, Long> entryStatus = activitySupplierService.getActivitySupplierEntryStatus(getSupplyId(), activityId);
            if (MapUtils.isNotEmpty(entryStatus)
                    && entryStatus.get("entryStatus") != null
                    && entryStatus.get("entryStatus").intValue() == ActivityEntryStatus.CANCEL_ENTRY.getStatus().intValue()
                    && resultMessage.getMark() != 2) {
                resultMessage = activityService.checkSupplier(activityId, Boolean.TRUE, resultMessage);
                if (!resultMessage.getIsSuccess()) {
                    writeJson(resultMessage);
                    return null;
                }
            }
            resultMessage.setIsSuccess(Boolean.TRUE);
        } catch (Exception e) {
            log.error("检查是否具有报名权限错误：", e);
            resultMessage.setMessage("报名失败，请联系系统管理员！");
            resultMessage.setIsSuccess(Boolean.FALSE);
        }

        writeJson(resultMessage);
        return null;
    }

    /**
     * 进入修改活动报名页面
     *
     * @return
     */
    public String toUpdateRegisterActivity() {
        //必须为修改
        if (!"update".equals(entryType)) {
            return SUCCESS;
        }
        try {
            //1.查询活动信息
            activity = activityService.getActivityById(activityId);

            //2.检查活动报名活动权限
            resultMessage = activityService.checkAuthority(activity, getSupplyId(), activityId, Boolean.TRUE);
            if (!resultMessage.getIsSuccess()) {
                return SUCCESS;
            }

            //3.检查当前是否为撤销报名并且还是非邀请的情况下进行的修改,若是将检查供应商是否已经达到活动商家数量上限
            Map<String, Long> entryStatus = activitySupplierService.getActivitySupplierEntryStatus(getSupplyId(), activityId);
            if (MapUtils.isNotEmpty(entryStatus)
                    && entryStatus.get("entryStatus") != null
                    && entryStatus.get("entryStatus").intValue() == ActivityEntryStatus.CANCEL_ENTRY.getStatus().intValue()
                    && resultMessage.getMark() != 2) {
                resultMessage = activityService.checkSupplier(activityId, Boolean.TRUE, resultMessage);
                if (!resultMessage.getIsSuccess()) {
                    return SUCCESS;
                }
            }

            //4.获取当前的sku信息
            existActivitySkuList = activitySkuService.getActivitySku(getSupplyId(), activityId);
            if (CollectionUtils.isEmpty(existActivitySkuList)) {
                resultMessage.setMessage("商家没有报名此活动，无法修改！");
                return SUCCESS;
            }

            //5.获取总消费
            //activityTotalPrice = activitySupplierService.getActivityTotalPrice(getSupplyId(), activityId);
            if (CollectionUtils.isNotEmpty(existActivitySkuList)) {
                for (ActivitySkuVo skuVo : existActivitySkuList) {
                    activityTotalPrice = activityTotalPrice.add(skuVo.getSkuTotalPrice());
                }
            }
            getRequest().setAttribute("activityTypeMap", ActivityTypeMap.getMap());
            getRequest().setAttribute("activityLevelMap", ActivityLevelMap.getMap());
            resultMessage.setIsSuccess(Boolean.TRUE);
            return SUCCESS;
        } catch (ServiceException e) {
            log.error("进入修改活动报名页面失败，", e);
            return ERROR;
        }
    }

    /**
     * 修改活动报名
     *
     * @return
     */
    public String updateRegisterActivity() {
        try {
            //查询活动信息
            activity = activityService.getActivityById(activityId);

            //1.检查活动报名活动权限
            resultMessage = activityService.checkAuthority(activity, getSupplyId(), activityId, Boolean.TRUE);
            if (!resultMessage.getIsSuccess()) {
                writeJson(resultMessage);
                return null;
            }

            //2.检查sku相关数据
            resultMessage = activityService.checkSku(activity, activityId, getSupplyId(), newActivitySkuList,
                    Boolean.TRUE, existActivitySkuList, resultMessage);
            if (!resultMessage.getIsSuccess()) {
                deleteNewPic(resultMessage);
                writeJson(resultMessage);
                return null;
            }
            //3.检查当前是否为撤销报名并且还是非邀请的情况下进行的修改,若是将检查供应商是否已经达到活动商家数量上限
            Map<String, Long> entryStatus = activitySupplierService.getActivitySupplierEntryStatus(getSupplyId(), activityId);
            if (MapUtils.isNotEmpty(entryStatus)
                    && entryStatus.get("entryStatus") != null
                    && entryStatus.get("entryStatus").intValue() == ActivityEntryStatus.CANCEL_ENTRY.getStatus().intValue()
                    && resultMessage.getMark() != 2) {
                resultMessage = activityService.checkSupplier(activityId, Boolean.FALSE, resultMessage);
                if (!resultMessage.getIsSuccess()) {
                    deleteNewPic(resultMessage);
                    writeJson(resultMessage);
                    return null;
                }
            }

            //4.修改活动报名
            activitySupplierService.updateSupplierEntry(activityId, getSupplyId(), getLoginUserId(),
                    activity, newActivitySkuList, existActivitySkuList, deleteSkuId);
        } catch (Exception e) {
            log.error("修改活动报名失败：", e);
            if (resultMessage.getMark() == 0) {
                activityService.incrSupplierMax(activityId);
            }
            deleteNewPic(resultMessage);
            resultMessage.setIsSuccess(Boolean.FALSE);
            resultMessage.setMessage("报名失败，请联系系统管理员！");
            writeJson(resultMessage);
            return null;
        }

        deleteExitsPic(resultMessage);
        resultMessage.setObject(null);
        resultMessage.setIsSuccess(Boolean.TRUE);
        writeJson(resultMessage);
        return null;
    }

    /**
     * 进入追加推广活动页面
     *
     * @return
     */
    public String toAppendActivity() {
        try {
            //1.校验是否还可以追加推广
            activity = activityService.getActivityById(activityId);
            resultMessage = activityService.checkActivityAppend(activity, activityId);
            if (!resultMessage.getIsSuccess()) {
                return SUCCESS;
            }

            //2.获取当前商家参加活动的sku
            existActivitySkuList = activitySkuService.getActivitySku(getSupplyId(), activityId);
            if (CollectionUtils.isEmpty(existActivitySkuList)) {
                resultMessage.setMessage("没有找到商家报名活动的SKU信息！");
                resultMessage.setIsSuccess(Boolean.FALSE);
                return SUCCESS;
            }

            resultMessage.setIsSuccess(Boolean.TRUE);
            return SUCCESS;
        } catch (ServiceException e) {
            log.error("进入追加推广活动页面失败，", e);
            return ERROR;
        }
    }

    /**
     * 保存追加推广活动
     *
     * @return
     */
    public String saveAppendActivity() {
        try {
            //1.校验是否还可以追加推广
            activity = activityService.getActivityById(activityId);
            resultMessage = activityService.checkActivityAppend(activity, activityId);
            if (!resultMessage.getIsSuccess()) {
                writeJson(resultMessage);
                return null;
            }

            //2.检查sku是否符合条件
            resultMessage = activityService.checkAndHandlerAppendSku(existActivitySkuList, activityId, getLoginUserId());
            if (!resultMessage.getIsSuccess()) {
                writeJson(resultMessage);
                return null;
            }

            resultMessage = activityService.saveAppendSku(activityId, getSupplyId(),
                    getLoginUserId(), existActivitySkuList);
            writeJson(resultMessage);
        } catch (ServiceException e) {
            log.error("保存追加推广活动失败，", e);
        }
        return null;
    }

    /**
     * 进入追加推广的缴费页面
     *
     * @return
     */
    public String toAppendPayment() {
        Long merchantId = (Long) getSession().getAttribute(Constants.SESSION_MERCHANT_ID);
        if (!"append".equals(entryType)) {
            resultMessage.setMessage("类型错误！");
            resultMessage.setIsSuccess(Boolean.FALSE);
            return SUCCESS;
        }
        try {
            //1.校验是否还可以追加推广
            activity = activityService.getActivityById(activityId);
            resultMessage = activityService.checkActivityAppend(activity, activityId);
            if (!resultMessage.getIsSuccess()) {
                return SUCCESS;
            }

            //2.活动费用
            activityTotalPrice = activityService.getAppendTotalPrice(getSupplyId(), activityId);

            //3.商家余额
            AccountBasics accountBasics = rechargeDetailsService.findAccountBasicsByUserId(merchantId);
            getRequest().setAttribute("amountAvlibal",accountBasics.getAmountAvlibal());
            resultMessage.setIsSuccess(Boolean.TRUE);
        } catch (Exception e) {
            log.error("进入追加推广缴费失败，merchantId:{},错误信息{}", new Object[]{merchantId, e});
            resultMessage.setIsSuccess(Boolean.FALSE);
            resultMessage.setMessage("追加推广缴费失败。");
        }


        return SUCCESS;
    }

    /**
     * 计算当个sku总共费用（渠道推广时使用）
     */
    public String computeMoney() {
        Integer preSaleQuantity   = activitySku.getPreSaleQuantity();
        BigDecimal activityPrice  = activitySku.getActivityPrice();
        BigDecimal commissionRate = activitySku.getCommissionRate();

        BigDecimal allMoney = activitySkuService.computeChannelMoney(preSaleQuantity, activityPrice, commissionRate);
        writeStr(allMoney.toString());
        return null;
    }

    /**
     * 计算所有商品的总共费用
     */
    public String computeAllMoney() {
        String[] prices = getRequest().getParameterValues("allActivitySkuTotalPrice");
        if (ArrayUtils.isEmpty(prices)) {
            log.error("没有找到单个sku费用！");
            return null;
        }

        BigDecimal allMoney = BigDecimal.ZERO;
        for (String price : prices) {
            if (StringUtils.isNotBlank(price)) {
                allMoney = allMoney.add(new BigDecimal(price));
            }
        }
        writeStr(allMoney.toString());
        return null;
    }

    /**
     * 进入立即缴费页面
     *
     * @return
     */
    public String toPayment() {
        Long merchantId = (Long) getSession().getAttribute(Constants.SESSION_MERCHANT_ID);
        if (!"first".equals(entryType)) {
            resultMessage.setMessage("类型错误！");
            resultMessage.setIsSuccess(Boolean.FALSE);
            return SUCCESS;
        }

        try {
            //活动信息
            activity = activityService.getActivityById(activityId);
            //检查活动是否已经开始
            Timestamp activityStartTime = activity.getActivityStartTime();
            if (new Timestamp(System.currentTimeMillis()).compareTo(activityStartTime) >= 0) {
                resultMessage.setMessage("活动已开始，不能缴费！");
                resultMessage.setIsSuccess(Boolean.FALSE);
                return SUCCESS;
            }
            //活动费用
            activityTotalPrice = activitySupplierService.getActivityTotalPrice(getSupplyId(), activityId);
            if (activityTotalPrice == null || activityTotalPrice.doubleValue() == 0d) {
                resultMessage.setIsSuccess(Boolean.FALSE);
                resultMessage.setMessage("缴费失败，当前活动报名没有找到报名费用！");
                return SUCCESS;
            }
            //商家余额
            AccountBasics accountBasics = rechargeDetailsService.findAccountBasicsByUserId(merchantId);
            getRequest().setAttribute("amountAvlibal",accountBasics.getAmountAvlibal());
            resultMessage.setIsSuccess(Boolean.TRUE);
        } catch (Exception e) {
            log.error("进入立即缴费失败，merchantId:{},错误信息{}", new Object[]{merchantId, e});
            resultMessage.setIsSuccess(Boolean.FALSE);
            resultMessage.setMessage("立即缴费失败。");
        }

        return SUCCESS;
    }

    /**
     * 充值
     */
    public String toTopUp() {
        return SUCCESS;
    }

    /**
     * 添加充值流水记录
     */
    public String insertTopUp(){
        Map jsonMap = new HashMap();
        Integer type = 2, status = Constants.RECHARGE_FALSE;
        String content = "用户充值";
        BigDecimal bigDecimalAmount = new BigDecimal(amount);
        int rechargeOrOrderFlag = 1;
        boolean orderTimeOut = false;
        try {
            AccountInfo acccount = merchantInfoService.findByLoginId(super.getLoginUserId());
            BnesAcctTransactionQuery b = new BnesAcctTransactionQuery();
            b.setAccountId(acccount.getNaccountId());
            b.setType(type);
            b.setContent(content);
            b.setAmount(bigDecimalAmount);
            b.setStatus(status);
            b.setCreateDate(new Date());
            b.setModifyDate(new Date());
            Integer rechargeCode = trationListRemoteService.paymentAccount(b);
            String acctNum = activityService.bnesAcctTransactionById(rechargeCode);
            jsonMap.put("flag", true);
            jsonMap.put("topUpCode", acctNum);
            jsonMap.put("payMethod", Integer.parseInt(Constants.PAY_METHOD_ONLINE.toString()));
            jsonMap.put("payMoney", amount);
            jsonMap.put("rechargeOrOrderFlag", rechargeOrOrderFlag);
            jsonMap.put("orderTimeOut", orderTimeOut);
            jsonMap.put("loginId", acccount.getLoginId());
            jsonMap.put("loginAccount", acccount.getAccountAmount());
            log.info("添加充值流水记录成功:充值编号："+rechargeCode);
        } catch (Exception e) {
            log.error("添加充值流水记录失败:", e.getMessage(), e);
            jsonMap.put("flag", false);
            jsonMap.put("msg", "添加充值流水至用户系统失败");
        }
        writeJson(jsonMap);
        return null;
    }

    /**
     * 缴费
     */
    public String pay() throws Exception {
        //类型校验
        if (!"append".equals(entryType) && !"first".equals(entryType)) {
            resultMessage.setMessage("类型错误！");
            resultMessage.setIsSuccess(Boolean.FALSE);
            writeJson(resultMessage);
            return null;
        }

        //活动id校验
        if (StringUtils.isBlank(activityId)) {
            resultMessage.setMessage("请输入活动id号！");
            writeJson(resultMessage);
            return null;
        }

        try {
            //1.校验密码
            if (StringUtils.isBlank(paymentPwd)) {
                resultMessage.setMessage("支付密码不能为空！");
                writeJson(resultMessage);
                return null;
            }

            // 2.获取支付加盐器
            UserBaseInfo userBaseInfo = new UserBaseInfo();
            userBaseInfo.setLoginId(getLoginUserId());
            userBaseInfo.setPassword(paymentPwd);

            boolean rs = this.customerRemoteService.checkUserPayPassword(userBaseInfo);

            if (!rs) {
                resultMessage.setMessage("支付密码错误！");
                writeJson(resultMessage);
                return null;
            }

        } catch (Exception e) {
            log.error("校验密码：", e);
            resultMessage.setIsSuccess(Boolean.FALSE);
            resultMessage.setMessage("校验密码失败！请联系工作人员！");
            writeJson(resultMessage);
            return null;
        }

        String nodeKey = PREFIX_PAY_LOCK + "/" + entryType + "/" + activityId + "/" + getSupplyId();
        try {
            //2.获取锁
            log.info("线程[{}]开始去获取到支付锁[{}].", new Object[]{Thread.currentThread().getName(), nodeKey});
            if (!redisLock.tryLock(nodeKey)) {
                log.error("线程[{}],没有获取到支付锁{}.", new Object[]{Thread.currentThread().getName(), nodeKey});
                resultMessage.setIsSuccess(Boolean.FALSE);
                resultMessage.setMessage("支付异常，稍后再支付！");
                writeJson(resultMessage);
                return null;
            }
            log.info("线程[{}]已获取到支付锁{}.", new Object[]{Thread.currentThread().getName(), nodeKey});
            //3.首次缴费付款
            if ("first".equals(entryType)) {
                resultMessage = activityService.pay(activityId, getLoginUserName(), getSupplyId());
                if (!resultMessage.getIsSuccess()) {
                    writeJson(resultMessage);
                    return null;
                }
            } else if ("append".equals(entryType)) {
                //4.追加推广缴费付款
                resultMessage = activityService.payAppend(activityId, getLoginUserName(),
                        getSupplyId(), getLoginUserId());
                if (!resultMessage.getIsSuccess()) {
                    writeJson(resultMessage);
                    return null;
                }
            } else {
                resultMessage.setMessage("类型错误！");
                resultMessage.setIsSuccess(Boolean.FALSE);
                writeJson(resultMessage);
                return null;
            }
        } catch (Exception e) {
            log.error("缴费失败：", e);
            resultMessage.setIsSuccess(Boolean.FALSE);
            resultMessage.setMessage("缴费失败！请联系工作人员！");
            writeJson(resultMessage);
            return null;
        } finally {
            log.info("线程[{}]释放支付名锁[{}].", new Object[]{Thread.currentThread().getName(), nodeKey});
            redisLock.release(nodeKey);
        }

        resultMessage.setIsSuccess(Boolean.TRUE);
        writeJson(resultMessage);
        return null;
    }

    /**
     * 删除新增的图片
     *
     * @param resultMessage
     */
    private void deleteNewPic(ResultMessage resultMessage) {
        if (activity.getActivityType().intValue() == ActivityType.GRAPHIC_TYPE.getType().intValue()) {
            Object object = resultMessage.getObject();
            if (object != null && object instanceof List) {
                List fileList = (List) object;
                for (Object obj : fileList) {
                    if (obj instanceof String) {
                        File file = new File((String) obj);
                        if (file.exists()) {
                            file.delete();
                        }
                    }
                }
                resultMessage.setObject(null);
            }
        }
    }

    /**
     * 修改成功后删除修改过的图片
     *
     * @param resultMessage
     */
    private void deleteExitsPic(ResultMessage resultMessage) {
        if (activity.getActivityType().intValue() == ActivityType.GRAPHIC_TYPE.getType().intValue()) {
            Object object = resultMessage.getObject2();
            if (object != null && object instanceof List) {
                List fileList = (List) object;
                for (Object obj : fileList) {
                    if (obj instanceof String) {
                        File file = new File((String) obj);
                        if (file.exists()) {
                            file.delete();
                        }
                    }
                }
                resultMessage.setObject2(null);
            }
        }
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getActivitySupplierType() {
        return activitySupplierType;
    }

    public void setActivitySupplierType(String activitySupplierType) {
        this.activitySupplierType = activitySupplierType;
    }

    public void setActivityParam(ActivityInfoVo activityParam) {
        this.activityParam = activityParam;
    }

    public void setActivity(ActivityInfo activity) {
        this.activity = activity;
    }

    public ActivityInfo getActivity() {
        return activity;
    }

    public ActivityInfoVo getActivityParam() {
        return activityParam;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getEntryType() {
        return entryType;
    }

    public void setEntryType(String entryType) {
        this.entryType = entryType;
    }

    public ResultMessage getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(ResultMessage resultMessage) {
        this.resultMessage = resultMessage;
    }

    public ViewProductSku getViewProductSku() {
        return viewProductSku;
    }

    public void setViewProductSku(ViewProductSku viewProductSku) {
        this.viewProductSku = viewProductSku;
    }

    public String getBrandIds() {
        return brandIds;
    }

    public void setBrandIds(String brandIds) {
        this.brandIds = brandIds;
    }

    public ActivitySku getActivitySku() {
        return activitySku;
    }

    public void setActivitySku(ActivitySku activitySku) {
        this.activitySku = activitySku;
    }

    public List<ActivitySkuVo> getNewActivitySkuList() {
        return newActivitySkuList;
    }

    public void setNewActivitySkuList(List<ActivitySkuVo> newActivitySkuList) {
        this.newActivitySkuList = newActivitySkuList;
    }

    public List<ActivitySkuVo> getExistActivitySkuList() {
        return existActivitySkuList;
    }

    public void setExistActivitySkuList(List<ActivitySkuVo> existActivitySkuList) {
        this.existActivitySkuList = existActivitySkuList;
    }

    public String getDeleteSkuId() {
        return deleteSkuId;
    }

    public void setDeleteSkuId(String deleteSkuId) {
        this.deleteSkuId = deleteSkuId;
    }

    public BigDecimal getActivityTotalPrice() {
        return activityTotalPrice;
    }

    public void setActivityTotalPrice(BigDecimal activityTotalPrice) {
        this.activityTotalPrice = activityTotalPrice;
    }

    public String getPaymentPwd() {
        return paymentPwd;
    }

    public void setPaymentPwd(String paymentPwd) {
        this.paymentPwd = paymentPwd;
    }

    public String getHaveSkuId() {
        return haveSkuId;
    }

    public void setHaveSkuId(String haveSkuId) {
        this.haveSkuId = haveSkuId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPayHttp() {
        return payHttp;
    }

    public String getRootPath() {
        return rootPath;
    }

    public Integer getPageType() {
        return pageType;
    }

    public void setPageType(Integer pageType) {
        this.pageType = pageType;
    }
}
