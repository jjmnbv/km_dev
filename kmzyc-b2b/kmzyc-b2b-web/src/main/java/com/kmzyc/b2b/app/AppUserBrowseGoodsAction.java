package com.kmzyc.b2b.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.km.framework.page.Pagination;
import com.kmzyc.b2b.model.BrowsingHis;
import com.kmzyc.b2b.service.BrowsingHisService;
import com.kmzyc.b2b.service.ProductPriceService;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.framework.constants.InterfaceResultCode;
import com.kmzyc.zkconfig.ConfigurationUtil;

@SuppressWarnings({"unchecked", "serial"})
@Scope("prototype")
@Controller("appUserBrowseGoodsAction")
public class AppUserBrowseGoodsAction extends AppBaseAction {
    // private static Logger logger = Logger.getLogger(BrowsingHisInterfaceAction.class);
    private static Logger logger = LoggerFactory.getLogger(AppUserBrowseGoodsAction.class);
    @Resource(name = "productPriceService")
    private ProductPriceService productPriceService;
    @Resource(name = "browsingHisServiceImpl")
    private BrowsingHisService browsingHisService;
    private ReturnResult<Map<String, Object>> returnResult;

    // private String rootPath = ConfigurationUtil.getString("PRODUCT_IMG_PATH");



    /**
     * gy
     * 
     * 查询浏览记录 输入：用户ID,记录条数 输出：商品列表
     */



    public void getUserBrowseGoods() {
        try {
            JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());

            Map<String, Object> newConditon = new HashMap<String, Object>();
            newConditon.put("loginId", getUserid());
            // newConditon.put("loginId",jsonParams.getIntValue("loginId"));
            // map.put("rowNum", jsonParams.getIntValue("pageNum"));
            // map.put("startNum", jsonParams.getIntValue("pageNo"));
            newConditon.put("channel", ConfigurationUtil.getString("CHANNEL")); // 添加渠道查询条件

            int pageNum = jsonParams.getIntValue("pageNum");
            int pageNo = jsonParams.getIntValue("pageNo");


            Pagination page = this.getPagination(5, pageNum);// pageNum每页多少条
            // pageNo第几页
            page.setStartindex((pageNo - 1) * pageNum + 1);
            page.setEndindex((pageNum * pageNo));
            page.setObjCondition(newConditon);
            try {
                Pagination pgination = browsingHisService.queryBrowsingHis(page);
                List<BrowsingHis> browsingHisList = pgination.getRecordList();
                // 获取商品促销信息和价格
                // productPriceService.getPriceBatch(browsingHisList);
                // int userBrows = browsingHisList.size();
                Map<String, Object> map2 = new HashMap<String, Object>();
                List list = new ArrayList();
                for (int i = 0; i < browsingHisList.size(); i++) {
                    Map mapSon = new HashMap<String, String>();
                    mapSon.put("productSkuId", browsingHisList.get(i).getProductSkuId()); // 商品ID
                    mapSon.put("productName", browsingHisList.get(i).getProcuctName()); // 商品名称
                    mapSon.put("imgPath170_170", ConfigurationUtil.getString("PRODUCT_IMG_PATH")
                            + browsingHisList.get(i).getImgPath5()); // 商品图片URL　
                    mapSon.put("imgPath100_100", ConfigurationUtil.getString("PRODUCT_IMG_PATH")
                            + browsingHisList.get(i).getImgPath6()); // 商品图片URL　
                    // 商品商城价格
                    mapSon.put("finalPrice", browsingHisList.get(i).getFinalPrice());
                    list.add(mapSon);
                }
                if (list.size() < 1) {
                    returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "请求无数据", null);
                }
                map2.put("myFavoriteList", list);
                map2.put("totalNum", pgination.getTotalRecords());
                map2.put("pageNo", pageNo);
                map2.put("pageNum", pageNum);
                // 获取商品促销信息和价格
                returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", map2);
            } catch (Exception e) {
                logger.error("获取用户浏览商品记录" + e.getMessage(), e);
                returnResult =
                        new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED, "失败",
                                null);

            }
        } catch (Exception e) {
            logger.error("", e);
        }
        printJsonString(returnResult);
    }

    /**
     * gy 删除浏览记录 输入：用户ID 输出：成功标识
     * 
     * 
     * 
     */
    public void delUserBrowseGoods() {
        try {
            // 根据登录id清空浏览记录
            browsingHisService.deleteBrowingHisById(Integer.parseInt(getUserid()));
            returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", null);
        } catch (Exception e) {
            logger.error("新增浏览发生异常", e);
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", null);
        }
        printJsonString(returnResult);
    }

    /**
     * 添加浏览记录 输入：
     * 
     * @return
     */
    /*
     * public void saveUserBrowseGoods() { try { JSONObject jsonParams =
     * AppJsonUtils.getJsonObject(getRequest()); if (null != jsonParams && !jsonParams.isEmpty()) {
     * BrowsingHis browsingHis = new BrowsingHis();
     * browsingHis.setContentCode(jsonParams.getString("contentCode"));
     * browsingHis.setLoginId(Integer.parseInt(getUserid())); browsingHis.setBrowsingType(1);//
     * 浏览记录类型1为商品 2为商户 browsingHisService.addBrowsingHis(browsingHis); returnResult = new
     * ReturnResult(InterfaceResultCode.SUCCESS, "成功", null); } else { logger.info("参数为空!");
     * returnResult = new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED, "参数为空!",
     * null); } } catch (Exception e) { logger.error("新增浏览发生异常", e); returnResult = new
     * ReturnResult(InterfaceResultCode.FAILED, "失败", null); } printJsonString(returnResult); }
     */

    public ReturnResult<Map<String, Object>> getReturnResult() {
        return returnResult;
    }

    public void setReturnResult(ReturnResult<Map<String, Object>> returnResult) {
        this.returnResult = returnResult;
    }

}
