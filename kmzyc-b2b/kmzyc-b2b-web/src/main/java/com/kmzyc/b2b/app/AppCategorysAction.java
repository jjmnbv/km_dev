package com.kmzyc.b2b.app;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.kmzyc.b2b.model.Categorys;
import com.kmzyc.b2b.service.CategorysService;
import com.kmzyc.b2b.util.SqlJoinUtil;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.constants.InterfaceResultCode;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.cms.vobject.CmsWindowData;
import com.whalin.MemCached.MemCachedClient;

@SuppressWarnings("unchecked")
@Controller("appCategorysAction")
public class AppCategorysAction extends AppBaseAction {

    private static final long serialVersionUID = -2773308956586274834L;

    // private static Logger logger = Logger.getLogger(AppCategorysAction.class);
    private static Logger logger = LoggerFactory.getLogger(AppCategorysAction.class);
    private ReturnResult returnResult;// 接口返回对象

    @Resource(name = "categorysService")
    private CategorysService categorysService;

    @Resource(name = "memCachedClient")
    private MemCachedClient memCachedClient;

    // private static final String categoryWindowList = ConfigurationUtil
    // .getString("categoryWindowList");
    // private String cmsPath = ConfigurationUtil.getString("cmsPath");

    // private static final String product_img_path =
    // ConfigurationUtil.getString("PRODUCT_IMG_PATH");

    /**
     * gy
     * 
     * 所有类目 输入：无 输出：类目集合
     */

    public void getCategoryList() {
        // 窗口格式
        // String
        // appwindowFormat=ConfigurationUtil.getString("appWindowFormat");
        // 类目窗口列表
        String categoryWindowList = ConfigurationUtil.getString("categoryWindowList");
        String[] categoryWindowStr = categoryWindowList.split(",");
        List<CmsWindowData> list = null;
        if (null != categoryWindowStr && categoryWindowStr.length > 0) {
            List categoryList = new ArrayList();
            for (int i = 0; i < categoryWindowStr.length; i++) {
                // 窗口名称
                String categoryWindow = categoryWindowStr[i];
                try {
                    // 窗口数据所绑定的数据 '%%'
                    list = categorysService.queryCategoryWindow(categoryWindow);
                    // 窗口所对应的类目
                    List dataIdsList = new ArrayList();
                    // 窗口所对应的自定义数据
                    Map categoryAdvList = new HashMap();

                    for (CmsWindowData cmsWindowData : list) {
                        // 所绑数据为类目
                        if (cmsWindowData.getDataType() == 2) {
                            dataIdsList.add(cmsWindowData.getDataId());
                            // 所绑数据为自定义数据
                        } else {
                            categoryAdvList.put("advImg", ConfigurationUtil.getString("cmsPath")
                                    + File.separator + cmsWindowData.getUser_defined_picpath());
                            categoryAdvList.put("advPath", cmsWindowData.getUser_defined_url());
                        }
                    }
                    String cateStr = SqlJoinUtil.getSqlIn(dataIdsList, 1000, "CATEGORY_ID");
                    List<Categorys> bandCategoryList =
                            categorysService.selectCategoryParent(cateStr);
                    Map categList = subCategory(bandCategoryList, categoryAdvList);
                    categoryList.add(categList);
                } catch (SQLException e) {
                    logger.error("类目列表失败" + e.getMessage(), e);
                    returnResult = new ReturnResult(InterfaceResultCode.FAILED, "请求失败", null);
                }
                // 返回至页面的对象
                returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", categoryList);

            }
        } else {
            logger.error("类目列表类目窗口参数未设置");
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, "请求失败", null);
        }
        printJsonString(returnResult);

    }

    // 类目

    public Map subCategory(List<Categorys> categorys, Map advList) {
        Map<String, Object> categList1 = null;

        for (Categorys category : categorys) {
            // -级
            if (category.getParentId().longValue() == 0) {
                categList1 = new HashMap<String, Object>();
                Long categoryId1 = category.getCategoryId();
                Map<String, Object> categList2 = null;
                List list2 = new ArrayList();

                // 二级
                for (Categorys subCategory2 : categorys) {
                    categList2 = new HashMap();

                    if (subCategory2.getParentId().longValue() == categoryId1.longValue()) {
                        Long categoryId2 = subCategory2.getCategoryId();
                        Map<String, Object> categList3 = null;
                        List list3 = new ArrayList();
                        String product_img_path = ConfigurationUtil.getString("PRODUCT_IMG_PATH");
                        // 三级
                        for (Categorys subCategory3 : categorys) {
                            categList3 = new HashMap();

                            if (subCategory3.getParentId().longValue() == categoryId2.longValue()) {
                                categList3.put("categoryId", subCategory3.getCategoryId());
                                categList3.put("categoryCode", subCategory3.getCategoryCode());
                                categList3.put("categoryName", subCategory3.getCategoryName());

                                categList3.put("categoryImg", subCategory3.getFilePath() != null
                                        ? product_img_path + subCategory3.getFilePath()
                                        : null);
                                list3.add(categList3);

                            }
                        }
                        categList2.put("categoryId", subCategory2.getCategoryId());
                        categList2.put("categoryCode", subCategory2.getCategoryCode());
                        categList2.put("categoryName", subCategory2.getCategoryName());
                        categList2.put("categoryImg", subCategory2.getFilePath() != null
                                ? product_img_path + subCategory2.getFilePath()
                                : null);
                        list2.add(categList2);
                        categList2.put("subCategoryList", list3);

                    }
                }
                categList1.put("categoryId", category.getCategoryId());
                categList1.put("categoryCode", category.getCategoryCode());
                categList1.put("categoryName", category.getCategoryName());
                categList1.put("categoryAdvList", advList);
                categList1.put(
                        "categoryImg",
                        category.getFilePath() != null ? ConfigurationUtil
                                .getString("PRODUCT_IMG_PATH") + category.getFilePath() : null);

                categList1.put("subCategoryList", list2);

                break;
            }

        }
        return categList1;
    }

    /**
     * 接口：getSubClassGoods 入参：分类id 出参：请求结果，成功则返回下级分类信息，包括二级分类标识id、图标url、二级分类名称、 二级分类描述；失败则返回具体的描述信息
     */
    public String getSubClassGoods() {
        JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
        if (null != jsonParams && !jsonParams.isEmpty()) {
            String parentId = jsonParams.getString("parentId");
            if (StringUtils.isBlank(parentId)) {
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, "参数错误", null);
            } else {
                Map<String, String> map = new HashMap<String, String>();
                map.put("parentId", parentId);
                map.put("status", Constants.STATUS);
                String channel = ConfigurationUtil.getString("CHANNEL");
                map.put("channel", channel);
                try {
                    List<Categorys> categorysList = categorysService.queryCategorysSub(map);
                    if (categorysList == null || categorysList.size() < 1) {
                        returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "请求无数据", null);
                    } else {
                        List list = new ArrayList();
                        Map<String, Object> mapSubClass = new HashMap<String, Object>();
                        for (int i = 0; i < categorysList.size(); i++) {
                            Map mapSon = new HashMap<String, String>();
                            mapSon.put("categoryId", categorysList.get(i).getCategoryId());
                            mapSon.put("categoryName", categorysList.get(i).getCategoryName());
                            mapSon.put("categoryDesc", categorysList.get(i).getCategoryDesc());
                            list.add(mapSon);
                        }
                        mapSubClass.put("categorysList", list);
                        // 返回至页面的对象
                        returnResult =
                                new ReturnResult(InterfaceResultCode.SUCCESS, "成功", mapSubClass);
                    }
                } catch (SQLException e) {
                    logger.error("获取子类目列表失败" + e.getMessage(), e);
                    returnResult = new ReturnResult(InterfaceResultCode.FAILED, "请求失败", null);
                }
            }
        } else {
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, "参数为空", null);
        }

        return SUCCESS;
    }

    public ReturnResult getReturnResult() {
        return returnResult;
    }

    public void setReturnResult(ReturnResult returnResult) {
        this.returnResult = returnResult;
    }

    public MemCachedClient getMemCachedClient() {
        return memCachedClient;
    }

    public void setMemCachedClient(MemCachedClient memCachedClient) {
        this.memCachedClient = memCachedClient;
    }

}
