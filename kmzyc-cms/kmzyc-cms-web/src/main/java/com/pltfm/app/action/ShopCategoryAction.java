package com.pltfm.app.action;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.kmzyc.supplier.model.ShopCategorys;
import com.pltfm.app.service.ShopCategorysService;
import com.pltfm.app.util.Constants;
import com.pltfm.cms.parse.PathConstants;
import com.pltfm.cms.service.CmsWindowDataService;
import com.pltfm.cms.vobject.CmsWindowData;

/**
 * 店铺分类请求处理
 *
 * @author Administrator
 */
@Controller("shopCategoryAction")
public class ShopCategoryAction extends BaseAction {
	private static Logger logger = LoggerFactory.getLogger(ShopCategoryAction.class);
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Resource(name = "shopCategorysService")
    private ShopCategorysService shopCategoryService;

    @Resource(name = "cmsWindowDataService")
    private CmsWindowDataService cmsWindowDataService;

    /**
     * 店铺id
     */
    private long shopId;

    /**
     * 为了防止权限蹿乱,给一个shopI作为标识
     */
    private long shopI;
    /**
     * 类目id
     */
    private long shopCategoryId;
    /**
     * 父级类目id
     */
    private long parentId;


    /**
     * 准备的树数据
     */
    private String jsonDataForTree;

    /**
     * 窗口ID
     */
    private Integer windowId;

    /**
     * 自定义数据窗口对象
     */
    private CmsWindowData cmsWindowData;

    /**
     * 店内分类ID 字符串数组
     */
    private String shopCategoryIdStr;

    /**
     * 是否展开
     */
    private String isExpand;

    /**
     * 按层级查询全部的类目
     *
     * @return
     */
    private String nodes;

    private String checkDataIdS;

    /**
     * 店铺所有类目
     */
    public String gotoShopCategory() {
        try {
//			Map session = ActionContext.getContext().getSession();
//			CmsSite cmsSite = null;
//			if(session!=null&&session.get("siteId")!=null){
//				 cmsSite = PathConstants.getCmsSite((Integer)session.get("siteId"));
//			}

            ShopCategorys c = new ShopCategorys();
            c.setShopId(shopI);

            List<ShopCategorys> list = shopCategoryService.queryShopCategoryList(c);

            nodes = JSON.toJSONString(list);
            CmsWindowData cmsWindowData = new CmsWindowData();
            cmsWindowData.setWindowId(windowId);
            //店铺类目
            cmsWindowData.setDataType(13);
            //	cmsWindowData.setSiteId((Integer)session.get("siteId"));
            checkDataIdS = cmsWindowDataService.queryByWindowData(cmsWindowData);
        } catch (Exception e) {
            logger.error("ShopCategoryAction.gotoShopCategory异常：" + e.getMessage(), e);
        }
        return "categoryList";
    }


    /**
     * 级联效果
     */
    public String queryShopCategoryByAjax() {
        try {
            List<ShopCategorys> list = shopCategoryService.queryShopCategoryListByParentId(parentId);
            writeJson(list);
        } catch (Exception e) {
            logger.error("ShopCategoryAction.queryShopCategoryByAjax异常：" + e.getMessage(), e);
        }
        return null;
    }


    /**
     * 可视化编辑店内分类
     */
    public String toAddShopCategoryForWindow() {
        try {
            //获得所有重点推荐的类目
            List<ShopCategorys> list = shopCategoryService.getAllShopCategorysForTree(shopId);

            StringBuffer sb = new StringBuffer();

            //默认展开选中
            for (ShopCategorys shopCategorys : list) {
                //若是推荐,则默认选中
                if (1 == shopCategorys.getIsSuggest()) {
                    sb.append(shopCategorys.getShopCategoryId() + ",");
                }
            }

            shopCategoryIdStr = sb.toString();
            jsonDataForTree = JSON.toJSONString(list);


            if (cmsWindowData == null) {
                cmsWindowData = new CmsWindowData();
            }
            cmsWindowData.setWindowId(windowId);
            cmsWindowData.setDataType(6);

            List<CmsWindowData> dataList = cmsWindowDataService.queryWindowDataList(cmsWindowData);
            if (dataList != null && dataList.size() > 0) {
                cmsWindowData = dataList.get(0);
            }
            //并且将窗口ID传入 方便查询数据,好做显示


        } catch (Exception e) {
            logger.error("ShopCategoryAction.toAddShopCategoryForWindow异常：" + e.getMessage(), e);
        }
        return SUCCESS;
    }


    /**
     * 保存店铺分类可视化操作
     */
    public String saveShopCategorySettingForWindow() {

        Map jsonMap = new HashMap();
        String key = "result";
        String result = "true";
        try {

            //对于文本输入做特殊字符的过滤
            String validStr = cmsWindowData.getUser_defined_name();

            boolean validResult = PathConstants.checkKeyWords(validStr);

            //若是包含,则提示其重新改动
            if (validResult) {
                result = "unValid";
                jsonMap.put(key, result);
                writeJson(jsonMap);
                return null;
            }

            String[] idArrays = null;
            if (shopCategoryIdStr != null && !shopCategoryIdStr.isEmpty()) {
                idArrays = shopCategoryIdStr.substring(0,
                        shopCategoryIdStr.length() - 1).split(",");
            }
            shopCategoryService.updateSuggestBatchId(idArrays, shopId, isExpand);

            // 先查window的数据,若是没有则新增,有的话则修改
            CmsWindowData condition = new CmsWindowData();
            condition.setWindowId(windowId);
            condition.setDataType(6);

            List<CmsWindowData> list = cmsWindowDataService.queryWindowDataList(condition);


            Integer uid = Constants.USER_B2B_ID;

            if (cmsWindowData.getUser_defined_name() == null || cmsWindowData.getUser_defined_name().isEmpty() || "".equals(cmsWindowData.getUser_defined_name())) {
                cmsWindowData.setUser_defined_name("");
            }

            if (list != null && list.size() > 0) {
                // 更新
                CmsWindowData dataOld = list.get(0);
                dataOld.setUser_defined_name(cmsWindowData
                        .getUser_defined_name());
                dataOld.setStatus(cmsWindowData.getStatus());
                dataOld.setModified(uid);
                dataOld.setModifyDate(new Date());

                cmsWindowDataService.updateCmsWindowData(dataOld);
            } else {

                // 新增
                cmsWindowData.setWindowId(windowId);
                cmsWindowData.setDataType(6);
                cmsWindowData.setRemark("店铺装修--店铺分类可视化窗口编辑数据存入");
                cmsWindowData.setCreated(uid);
                cmsWindowData.setCreateDate(new Date());

                cmsWindowDataService.addCmsWindowData(cmsWindowData);
            }

        } catch (Exception e) {
            result = "error";
            logger.error("ShopCategoryAction.saveShopCategorySettingForWindow异常：" + e.getMessage(), e);
        }

        jsonMap.put(key, result);
        writeJson(jsonMap);

        return null;
    }


    @Override
    public void writeJson(Object object) {
        String json = JSON.toJSONString(object);
        try {
            ServletActionContext.getResponse().setContentType(
                    "text/html;charset=utf-8");
            ServletActionContext.getResponse().getWriter().write(json);
            ServletActionContext.getResponse().getWriter().flush();
        } catch (IOException e) {

        }
    }

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public long getShopCategoryId() {
        return shopCategoryId;
    }

    public void setShopCategoryId(long shopCategoryId) {
        this.shopCategoryId = shopCategoryId;
    }


    public String getJsonDataForTree() {
        return jsonDataForTree;
    }


    public void setJsonDataForTree(String jsonDataForTree) {
        this.jsonDataForTree = jsonDataForTree;
    }

    public String getShopCategoryIdStr() {
        return shopCategoryIdStr;
    }


    public void setShopCategoryIdStr(String shopCategoryIdStr) {
        this.shopCategoryIdStr = shopCategoryIdStr;
    }


    public CmsWindowData getCmsWindowData() {
        return cmsWindowData;
    }


    public void setCmsWindowData(CmsWindowData cmsWindowData) {
        this.cmsWindowData = cmsWindowData;
    }


    public String getIsExpand() {
        return isExpand;
    }


    public void setIsExpand(String isExpand) {
        this.isExpand = isExpand;
    }


    public Integer getWindowId() {
        return windowId;
    }


    public void setWindowId(Integer windowId) {
        this.windowId = windowId;
    }


    public long getShopI() {
        return shopI;
    }


    public void setShopI(long shopI) {
        this.shopI = shopI;
    }


    public String getNodes() {
        return nodes;
    }


    public void setNodes(String nodes) {
        this.nodes = nodes;
    }


    public String getCheckDataIdS() {
        return checkDataIdS;
    }


    public void setCheckDataIdS(String checkDataIdS) {
        this.checkDataIdS = checkDataIdS;
    }


}
