package com.kmzyc.supplier.ajax;

import com.kmzyc.supplier.model.ShopMain;
import com.kmzyc.supplier.model.ShopMainDraft;
import com.kmzyc.supplier.model.SuppliersInfo;
import com.kmzyc.supplier.action.SupplierBaseAction;
import com.kmzyc.supplier.enums.ShopAuditStatus;
import com.kmzyc.supplier.enums.ShopMainStatus;
import com.kmzyc.supplier.enums.SuppliersStatus;
import com.kmzyc.supplier.service.MerchantInfoService;
import com.kmzyc.supplier.service.ShopMainDraftService;
import com.kmzyc.supplier.service.ShopMainService;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.vobject.AreaDict;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;

@Controller("shopMainAjaxAction")
@Scope("prototype")
public class ShopMainAjaxAction extends SupplierBaseAction {

    private static Logger logger = LoggerFactory.getLogger(ShopMainAjaxAction.class);

    @Resource
    private ShopMainService shopMainService;

    @Resource
    private ShopMainDraftService shopMainDraftService;

    @Resource
    private MerchantInfoService merchantInfoService;

    private ShopMain shopMain;

    private ShopMainDraft shopMainDraft;

    private String shopId;

    private String[] shopIds;

    public String choiceArea() {
        Map<String, Object> jsonMap = new HashMap();
        try {
            List<AreaDict> areaList = null;
            Integer id = Integer.valueOf(getRequest().getParameter("id"));
            if ("province_change".equals(getRequest().getParameter("identity"))) {
                areaList = getAreaDictService().findCityByProvince(id);
            } else {
                areaList = getAreaDictService().findCountyByProvince(id);
            }
            jsonMap.put("areaList", areaList);
            writeJson(jsonMap);
        } catch (Exception e) {
            logger.error("错误信息，", e);
        }

        return null;
    }

    public String addShopMain() {
        Map<String, Object> jsonMap = new HashMap();
        try {
            //TODO 此处优化时需要联合查询union
            Long supplierId = getSupplyId();
            int count = shopMainService.findShopMain(supplierId);
            int countDraft = shopMainDraftService.findShopMainDraft(supplierId);
            if (count > 0 || countDraft > 0) {
                jsonMap.put("flag", false);
                jsonMap.put("msg", "你在同一站点，只能开设一个店铺!");
                writeJson(jsonMap);
                return null;
            }

            Map<String, Object> conditionMap = new HashMap<String, Object>();
            conditionMap.put("shopName", shopMainDraft.getShopName());
            conditionMap.put("supplierId", super.getSupplyId());
            if (shopMainDraftService.selectRepeatName(conditionMap) > 0) {
                jsonMap.put("flag", false);
                jsonMap.put("msg", "【" + shopMainDraft.getShopName() + "】该店铺名已存在，请重命名!");
                writeJson(jsonMap);
                return null;
            }

            shopMainDraft.setApplyUser(getLoginUserId());
            shopMainDraft.setApplyTime(new Date());
            shopMainDraft.setSupplierId(getSupplyId());
            shopMainDraft.setAuditStatus(ShopAuditStatus.APPLY.getStatus());
            shopMainDraft.setRemark("审核中，审核时间大概需要1~3个工作日。请耐心等候。");
            shopMainDraftService.insertShopMainDraft(shopMainDraft);
            jsonMap.put("flag", true);
            jsonMap.put("msg", "店铺新增成功!");
        } catch (Exception e) {
            logger.error("新增店铺错误：" , e);
            jsonMap.put("flag", false);
            jsonMap.put("msg", "店铺新增失败!");
        }

        writeJson(jsonMap);
        return null;
    }

    public String updateShopMain() {
        Map<String, Object> jsonMap = new HashMap();
        shopMain.setApplyUser(getLoginUserId());
        shopMain.setApplyTime(new Date());
        shopMain.setSupplierId(getSupplyId());
        shopMain.setAuditStatus(ShopAuditStatus.EDIT.getStatus());//修改后变为“编辑”状态

        try {
            int count = shopMainService.updateShopMain(shopMain);
            boolean haveDone = count > 0;
            jsonMap.put("shopId", shopMain.getShopId());
            jsonMap.put("flag", haveDone);
            jsonMap.put("msg", haveDone ? "店铺修改成功!" : "店铺修改失败!");
        } catch (Exception e) {
            logger.error("新增店铺错误：", e);
            jsonMap.put("flag", false);
            jsonMap.put("msg", "店铺修改失败!");
        }

        writeJson(jsonMap);
        return null;
    }

    public String deleteShopMainMsg() {
        try {
            shopMain = new ShopMain();
            shopMain.setShopId(Long.valueOf(shopId));
            shopMain.setRemark("");//消息置空
            shopMainService.updateShopMain(shopMain);
        } catch (Exception e) {
            logger.error("删除店铺备注错误：", e);
        }

        return null;
    }

    public String deleteShopMain() {
        Map<String, Object> jsonMap = new HashMap();
        try {
            int count = shopMainService.deleteShopMainById(Long.valueOf(shopId));
            boolean haveDone = count > 0;
            jsonMap.put("flag", haveDone);
            jsonMap.put("msg", haveDone ? "店铺删除成功!" : "店铺删除失败!");
        } catch (Exception e) {
            logger.error("删除店铺错误：", e);
            jsonMap.put("flag", false);
            jsonMap.put("msg", "店铺删除失败!");
        }

        writeJson(jsonMap);
        return null;
    }

    public String openShop() {
        Map<String, Object> jsonMap = new HashMap();
        List<Long> shopIdList = Stream.of(shopIds).map(shopId->Long.valueOf(shopId)).collect(Collectors.toList());

        try {
            int count = shopMainService.updateStatus(shopIdList, ShopMainStatus.OPEN.getStatus());
            boolean haveDone = count > 0;
            jsonMap.put("flag", haveDone);
            jsonMap.put("msg", haveDone ? "店铺开启成功!" : "店铺开启失败!");
        } catch (Exception e) {
            logger.error("店铺开启错误：", e);
            jsonMap.put("flag", false);
            jsonMap.put("msg", "店铺开启失败!");
        }

        writeJson(jsonMap);
        return null;
    }

    public String closeShop() {
        Map<String, Object> jsonMap = new HashMap();
        List<Long> shopIdList = Stream.of(shopIds).map(shopId->Long.valueOf(shopId)).collect(Collectors.toList());

        try {
            int count = shopMainService.updateStatus(shopIdList, ShopMainStatus.CLOSE.getStatus());
            boolean haveDone = count > 0;
            jsonMap.put("flag", haveDone);
            jsonMap.put("msg", haveDone ? "店铺关闭成功!" : "店铺关闭失败!");
        } catch (Exception e) {
            logger.error("店铺关闭错误：", e);
            jsonMap.put("flag", false);
            jsonMap.put("msg", "店铺关闭失败!");
        }

        writeJson(jsonMap);
        return null;
    }

    public String applyShop() {
        Map<String, Object> jsonMap = new HashMap();
        List<Long> shopIdList = Stream.of(shopIds).map(shopId->Long.valueOf(shopId)).collect(Collectors.toList());

        try {
            int count = shopMainService.updateAuditStatus(shopIdList, ShopAuditStatus.APPLY.getStatus());
            boolean haveDone = count > 0;
            jsonMap.put("flag", haveDone);
            jsonMap.put("msg", haveDone ? "店铺申请成功!" : "店铺申请失败!");
        } catch (Exception e) {
            logger.error("店铺申请错误：", e);
            jsonMap.put("flag", false);
            jsonMap.put("msg", "店铺申请失败!");
        }

        writeJson(jsonMap);
        return null;
    }

    public String publishHomePage() {
        Map<String, Object> jsonMap = new HashMap();
        List<Long> shopIdList = Stream.of(shopIds).map(shopId->Long.valueOf(shopId)).collect(Collectors.toList());

        try {
            ResultMessage result = shopMainService.publishShopMainHomePage(shopIdList);
            if (result == null) {
                logger.error("返回为空。");
                jsonMap.put("flag", false);
                jsonMap.put("msg", "店铺主页发布失败!");
                writeJson(jsonMap);
                return null;
            }

            if (result.getIsSuccess()) {
                result = shopMainService.batchUpdateShopMainUrl((List<ShopMain>) result.getObject());
            }
            jsonMap.put("flag", result.getIsSuccess());
            jsonMap.put("msg", result.getIsSuccess() ? "店铺主页发布成功!" : "店铺主页发布失败!");
        } catch (Exception e) {
            logger.error("店铺主页发布错误：", e);
            jsonMap.put("flag", false);
            jsonMap.put("msg", "店铺主页发布失败!");
        }

        writeJson(jsonMap);
        return null;
    }

    public String cancelShop() {
        Map<String, Object> jsonMap = new HashMap();
        List<Long> shopIdList = Stream.of(shopIds).map(shopId->Long.valueOf(shopId)).collect(Collectors.toList());

        try {
            int count = shopMainService.updateAuditStatus(shopIdList, ShopAuditStatus.EDIT.getStatus());
            boolean haveDone = count > 0;
            jsonMap.put("flag", haveDone);
            jsonMap.put("msg", haveDone ? "店铺撤回申请成功!" : "店铺撤回申请失败!");
        } catch (Exception e) {
            logger.error("店铺撤回申请错误：", e);
            jsonMap.put("flag", false);
            jsonMap.put("msg", "店铺撤回申请失败!");
        }

        writeJson(jsonMap);
        return null;
    }

    public String deleteFilePath() {
        Map<String, Object> jsonMap = new HashMap();
        try {
            ShopMain shopMainTmp = shopMainService.findShopMainById(Long.valueOf(getRequest().getParameter("id")));
            if (shopMainTmp == null || shopMainTmp.getFilePath() == null) {
                jsonMap.put("flag", false);
                writeJson(jsonMap);
                return null;
            }

            shopMainTmp.setFilePath(null);
            ResultMessage rm = shopMainService.deleteShopMainFilePath(shopMainTmp);
            jsonMap.put("flag", rm.getIsSuccess());
            jsonMap.put("msg", rm.getMessage());
        } catch (Exception e) {
            logger.error("店铺展示图片删除错误：", e);
            jsonMap.put("flag", false);
        }

        writeJson(jsonMap);
        return null;
    }

    public String deleteLogoPath() {
        Map<String, Object> jsonMap = new HashMap();
        try {
            ShopMain shopMainTmp = shopMainService.findShopMainById(Long.valueOf(getRequest().getParameter("id")));
            if (shopMainTmp == null || shopMainTmp.getLogoPath() == null) {
                jsonMap.put("flag", false);
                writeJson(jsonMap);
                return null;
            }
            shopMainTmp.setLogoPath(null);
            ResultMessage rm = shopMainService.deleteShopMainFilePath(shopMainTmp);
            jsonMap.put("flag", rm.getIsSuccess());
            jsonMap.put("msg", rm.getMessage());
        } catch (Exception e) {
            logger.error("店铺LOGO删除错误：", e);
            jsonMap.put("flag", false);
        }

        writeJson(jsonMap);
        return null;
    }

    /**
     * 预览店铺主页
     *
     * @return
     */
    public String previewHomePage() {
        Map<String, Object> jsonMap = new HashMap();
        try {
            String pageUrl = shopMainService.previewHomePage(Long.valueOf(getRequest().getParameter("id")));
            logger.info("pageUrl={}.", pageUrl);
            jsonMap.put("pageUrl", pageUrl);
        } catch (Exception e) {
            logger.error("店铺预览错误：", e);
            jsonMap.put("pageUrl", null);
        }

        writeJson(jsonMap);
        return null;
    }

    /**
     * 供应商申请店铺
     *
     * @return
     */
    public String applySupplierShop() {
        Map<String, Object> jsonMap = new HashMap();
        shopMainDraft.setApplyTime(new Date());
        shopMainDraft.setAuditStatus(ShopAuditStatus.EDIT.getStatus());
        SuppliersInfo newSupplier = new SuppliersInfo();
        Map<String, Object> conditionMap = new HashMap();
        conditionMap.put("supplierId", shopMainDraft.getSupplierId());
        conditionMap.put("shopName", shopMainDraft.getShopName());

        try {
            if (shopMainDraftService.selectRepeatName(conditionMap) > 0) {
                jsonMap.put("flag", false);
                jsonMap.put("msg", "【" + shopMainDraft.getShopName() + "】该店铺名已存在,请重命名!");
                writeJson(jsonMap);
                return null;
            }

            int count = shopMainService.findShopMain(shopMainDraft.getSupplierId());
            int countDraft = shopMainDraftService.findShopMainDraft(shopMainDraft.getSupplierId());
            if (count > 0 || countDraft > 0) {
                jsonMap.put("flag", false);
                jsonMap.put("msg", "你在同一站点，只能开设一个店铺!");
                writeJson(jsonMap);
                return null;
            }

            Long shopId = shopMainDraftService.insertShopMainDraft(shopMainDraft);
            newSupplier.setSupplierId(shopMainDraft.getSupplierId());
            newSupplier.setStatus(SuppliersStatus.APPLYING.getStatus());//供应商提交审核状态
            merchantInfoService.finishUpdate(newSupplier);//修改供应商为提交状态
            jsonMap.put("flag", true);
            jsonMap.put("msg", "店铺新增成功!");
            jsonMap.put("shopId", shopId);
        } catch (Exception e) {
            logger.error("新增店铺错误：", e);
            jsonMap.put("flag", false);
            jsonMap.put("msg", "店铺新增失败!");
        }

        writeJson(jsonMap);
        return null;
    }

    /**
     * 保存商家介绍
     *
     * @return
     */
    public String updateShopMainDescribe() {
        Map<String, Object> jsonMap = new HashMap();
        try {
            if (null == shopMain.getShopId()) {
                jsonMap.put("flag", true);
                jsonMap.put("msg", "亲爱的商家，您好!您还没有申请店铺，请先去提交申请哦!");
                writeJson(jsonMap);
                return null;
            }

            boolean haveDone = shopMainService.updateShopMainDescribe(shopMain);
            jsonMap.put("flag", haveDone);
            jsonMap.put("msg", haveDone ? "商家介绍保存成功!" : "商家介绍保存失败!");
        } catch (Exception e) {
            logger.error("保存商家介绍失败，", e);
            jsonMap.put("flag", false);
            jsonMap.put("msg", "保存失败!");
        }

        writeJson(jsonMap);
        return null;
    }

    public ShopMain getShopMain() {
        return shopMain;
    }

    public void setShopMain(ShopMain shopMain) {
        this.shopMain = shopMain;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String[] getShopIds() {
        String[] temp = shopIds;
        return temp;
    }

    public void setShopIds(String[] shopIds) {
        this.shopIds = shopIds;
    }

    public ShopMainDraft getShopMainDraft() {
        return shopMainDraft;
    }

    public void setShopMainDraft(ShopMainDraft shopMainDraft) {
        this.shopMainDraft = shopMainDraft;
    }

}