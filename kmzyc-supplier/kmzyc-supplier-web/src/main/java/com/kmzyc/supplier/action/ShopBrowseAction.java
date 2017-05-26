package com.kmzyc.supplier.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.framework.constants.Constants;
import com.kmzyc.supplier.model.ShopBrowseInfo;
import com.kmzyc.supplier.model.ShopMain;
import com.kmzyc.supplier.service.ShopBrowserService;
import com.kmzyc.supplier.service.ShopDecorationService;
import com.kmzyc.supplier.service.ShopMainService;
import com.kmzyc.supplier.service.SupplierInfoService;

/**
 * 店铺浏览量处理
 *
 * @author Administrator
 */
@Controller("shopBrowseAction")
@Scope("prototype")
public class ShopBrowseAction extends SupplierBaseAction {

    private static Logger log = LoggerFactory.getLogger(ShopBrowseAction.class);

    @Resource
    private ShopBrowserService shopBrowserService;

    @Resource
    private ShopMainService shopMainService;

    @Resource
    private SupplierInfoService supplierInfoService;

    @Resource
    private ShopDecorationService shopDecorationService;

    private ShopBrowseInfo shopBrowseInfo;

    private String shopId;

    private String startDate;
    
    private String endDate;

    private String searchFromButton;

    /**
     * 查询列表
     */
    public String queryShopBrowseList() {
        try {
            pagintion = this.getPagination(Constants.VIEW_PAGE, Integer.parseInt(getPageSize()));
            if (shopBrowseInfo == null) {
                shopBrowseInfo = new ShopBrowseInfo();
                shopBrowseInfo.setTimeTypeQuery("0");
            }
            setTimeTypeMap();
            String tips = "";

            // 判断店铺是否存在并且是否已发布
            Map<String, Object> shopMainInfo = getShopMainBySupplierId(getSupplyId());
            if (shopMainInfo.containsKey("shopId")) {
                shopBrowseInfo.setShopId(String.valueOf(shopMainInfo.get("shopId")));
                // 检查是否已经发布
                if (!shopMainInfo.containsKey("url")) {
                    tips = "noPublish";
                    getRequest().setAttribute("tips", tips);
                    return "success";
                }
            } else {
                tips = "noShop";
                getRequest().setAttribute("tips", tips);
                return "success";
            }

            //检查店铺浏览量开启状态
            boolean isOpenBrowseStatus = isOpenShopBrowseStatus(getSupplyId());

            // 确实没有开启
            if (isOpenBrowseStatus == false) {
                getRequest().setAttribute("isOpenBrowseStatus", true);
                return "success";
            }
            String searchFromButton = getRequest().getParameter("searchFromButton");

            if (searchFromButton != null && "Y".equals(searchFromButton)) {
                pagintion.setPage(1);
            }

            shopBrowseInfo.setPn(pagintion.getPage());
            shopBrowseInfo.setPs(Integer.parseInt(getPageSize()));

            if (startDate != null && !startDate.isEmpty() || !"null".equals(startDate)) {
                shopBrowseInfo.setStartDate(startDate);
            }

            if (endDate != null && !endDate.isEmpty() || !"null".equals(endDate)) {
                shopBrowseInfo.setEndDate(endDate);
            }

            log.info("调用接口查询店铺浏览量传递参数如下: shopid=" + shopBrowseInfo.getShopId() + ",title="
                    + shopBrowseInfo.getTitleForQuery() + ",url=" + shopBrowseInfo.getTitleForQuery()
                    + ",startDate=" + shopBrowseInfo.getStartDate() + ",endDate="
                    + shopBrowseInfo.getEndDate() + ",ps=" + shopBrowseInfo.getPs() + ",pn="
                    + shopBrowseInfo.getPs() + ",timeType=" + shopBrowseInfo.getTimeTypeQuery());

            ShopBrowseInfo returnData = shopBrowserService.queryBrowseInfoList(shopBrowseInfo);

            if (returnData != null) {
                getRequest().setAttribute("totalCount", returnData.getMatches());
                pagintion.setRecordList(returnData.getEntity());
                pagintion.setTotalRecords(returnData.getNgroups());
            }

            // 分析折线图数据
            Map<String, String> groupData = shopBrowserService.queryGroupData(shopBrowseInfo);
            if (groupData.containsKey("errorCode")) {
                log.info("调用接口查询店铺浏览量折线图数据接口发生错误.详情=" + groupData.get("errorCode"));
                getRequest().setAttribute("groupDataError", groupData.get("errorCode"));
                return "success";
            }
            String dataStr = groupData.get("dataList");
            if (groupData.get("dataList") == null || "null".equals(groupData.get("dataList"))) {
                dataStr = null;
            }
            getRequest().setAttribute("groupData", dataStr);
            getRequest().setAttribute("groupDataSize", groupData.get("dataSize"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    /**
     * 更新店铺浏览量状态
     */
    public String openShopBrowseStatusByAjax() {
        boolean flag = false;
        try {
            boolean result = supplierInfoService.updateShopBrowseStatus(getSupplyId(), (short) 2);

            int returnResult = 0;

            PrintWriter out = getResponse().getWriter();

            // 更新状态失败 0
            if (!result) {
                returnResult = 1;
                out.print(returnResult);
                out.flush();
                out.close();
                return null;
            }

            ShopMain shopMain = shopMainService.findShopMainById(Long.valueOf(shopId));

            String dataType = shopMain.getTemplateType().toString();
            if (shopMain.getTemplateType() == null || !shopMain.getTemplateType().toString().isEmpty()) {
                dataType = "2";
            }
            String url = shopDecorationService.publishTemplet(shopMain, dataType);
            if (StringUtils.isNotBlank(url)) flag = true;
            if (!flag) {
                returnResult = 2;
            }
            out.print(returnResult);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询时间类型 0 所有：起止时间为空（不限时间范围）； 1 当天：起止时间都自动选择当前日期； 2 最近七天：开始时间为当前日期减去6天，截止时间为当天； 3
     * 当月：开始时间为当月1日，截止时间为当天； 4 上月：开始时间为上月1日，截止时间为上月最后一天；
     */
    private void setTimeTypeMap() {
        Map map = new HashMap();
        map.put("0", "所有");
        map.put("1", "当天");
        map.put("2", "最近七天");
        map.put("3", "当月");
        map.put("4", "上月");
        getRequest().setAttribute("timeTypeMap", map);
    }

    public ShopBrowseInfo getShopBrowseInfo() {
        return shopBrowseInfo;
    }

    public void setShopBrowseInfo(ShopBrowseInfo shopBrowseInfo) {
        this.shopBrowseInfo = shopBrowseInfo;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getSearchFromButton() {
        return searchFromButton;
    }

    public void setSearchFromButton(String searchFromButton) {
        this.searchFromButton = searchFromButton;
    }

}
