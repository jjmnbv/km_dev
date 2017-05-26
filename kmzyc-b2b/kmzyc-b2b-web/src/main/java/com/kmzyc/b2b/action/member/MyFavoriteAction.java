package com.kmzyc.b2b.action.member;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.framework.action.BaseAction;
import com.km.framework.page.Pagination;
import com.kmzyc.b2b.model.Favorite;
import com.kmzyc.b2b.service.member.MyFavoriteService;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.zkconfig.ConfigurationUtil;

/**
 * Description:会员中心-我的收藏访问入口 User: lishiming Date: 13-10-15 下午3:45 Since: 1.0
 */
@SuppressWarnings("serial")
@Controller
@Scope("prototype")
public class MyFavoriteAction extends BaseAction {
    // private static Logger logger = Logger.getLogger(MyFavoriteAction.class);
    private static Logger logger = LoggerFactory.getLogger(MyFavoriteAction.class);
    @Resource(name = "myFavoriteServiceImpl")
    private MyFavoriteService myFavoriteService;
    private static final String KEYWORD_TIPS = "商品名称";
    private Long favoriteId;
    @SuppressWarnings("rawtypes")
    private ReturnResult returnResult;

    /**
     * 查询会员的收藏店铺列表
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    public String queryFavoriteShopLists() {
        logger.info("查询我的收藏店铺列表");
        HttpServletRequest request = ServletActionContext.getRequest();
        Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);
        logger.info("查询会员收藏店铺列表,参数：userID：" + userId);
        if (userId == null) {
            logger.error("Session过期");
            return ERROR;
        }
        Pagination page = this.getPagination(5, 10);
        // 页面传入的查询条件
//        Map<String, String> objContion = (Map<String, String>) page.getObjCondition();
        // sql查询条件对象
        Map<String, Object> newConditon = new HashMap<String, Object>();
        // 解析并组装查询条件
        newConditon.put("userId", userId);
        // 设置查询条件
        page.setObjCondition(newConditon);
        try {
            this.pagintion = myFavoriteService.findFavoriteShopByPage(page);
            Favorite favorite = null;
            List<Favorite> list = pagintion.getRecordList();
            for (int i = 0; i < list.size(); i++) {
                favorite = list.get(i);
                try {
                    favorite.setPoint(myFavoriteService.showSupplierPoint(favorite.getSupplierId()));
                    // favorite.setPoint(5f);
                } catch (Exception e) {
                    logger.error("收藏店铺综合评分信息出错：" + e.getMessage(), e);
                }

            }

        } catch (SQLException e) {
            logger.error("查询会员的收藏店铺列表信息出错：" + e.getMessage(), e);
        }
        return SUCCESS;
    }

    /**
     * 查询会员的收藏商品列表
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    public String queryFavoriteLists() {
        logger.info("查询我的收藏列表");
        HttpServletRequest request = ServletActionContext.getRequest();
        Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);
        logger.info("查询会员收藏列表,参数：userID：" + userId);
        Pagination page = this.getPagination(5, 10);
        // 页面传入的查询条件
        Map<String, String> objContion = (Map<String, String>) page.getObjCondition();
        // sql查询条件对象
        Map<String, Object> newConditon = new HashMap<String, Object>();

        // 解析并组装查询条件
        newConditon.put("userId", userId);
        // 添加渠道查询条件
//        String channel = ConfigurationUtil.getString("CHANNEL");
//        newConditon.put("channel", channel);
        if (objContion != null) {
            if (KEYWORD_TIPS.equals(objContion.get("keyword"))) {
                newConditon.put("keyword", "");
            } else {
                newConditon.put("keyword", objContion.get("keyword"));
            }
            if (objContion.get("depreciate") != null) {
                newConditon.put("depreciate", objContion.get("depreciate"));
            }
            if (objContion.get("promotion") != null) {
                newConditon.put("promotion", objContion.get("promotion"));
            }
            if (objContion.get("inStock") != null) {
                newConditon.put("inStock", objContion.get("inStock"));
            }
        }

        // 设置查询条件
        page.setObjCondition(newConditon);
        try {
            this.pagintion = myFavoriteService.findFavoriteProductByPage(page);
            // 将商品的类目属性值List转换为String,中间通过空格相连
            List<Favorite> favoriteList = this.pagintion.getRecordList();
            if (favoriteList != null && favoriteList.size() > 0) {
                for (int i = 0; i < favoriteList.size(); i++) {
                    String categoryAttrValueStr = "";
                    List<Map<String, String>> categoryAttrValueList =
                            favoriteList.get(i).getCategoryAttrValueList();
                    if (categoryAttrValueList != null && categoryAttrValueList.size() > 0) {
                        for (int j = 0; j < categoryAttrValueList.size(); j++) {
                            categoryAttrValueStr +=
                                    categoryAttrValueList.get(j).get("categoryAttrValue") + "  ";
                        }
//                        categoryAttrValueStr.subSequence(0, categoryAttrValueStr.length() - 1);
                    }
                    favoriteList.get(i).setCategoryAttrValueStr(categoryAttrValueStr);
                }
            }

        } catch (SQLException e) {
            logger.error("查询会员的收藏列表信息出错：" + e.getMessage(), e);
            return ERROR;
        } catch (Exception e) {
            logger.error("查询会员的收藏列表信息出错：" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 删除收藏记录
     * 
     * @return
     */
    public String deleteFavorite() {
        HttpServletRequest request = ServletActionContext.getRequest();
        Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);
        logger.info("删除收藏记录，参数：favoriteId：" + this.favoriteId + "，userID：" + userId);
        try {
            logger.info("删除收藏记录，收藏Id为【" + this.favoriteId + "】");
            myFavoriteService.deleteFavorite(this.favoriteId);
        } catch (Exception e) {
            logger.error("删除收藏记录出错：" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 删除收藏店铺记录
     * 
     * @return
     */
    public String deleteFavoriteShop() {
        HttpServletRequest request = ServletActionContext.getRequest();
        Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);
        logger.info("删除收藏店铺记录，参数：favoriteId：" + this.favoriteId + "，userID：" + userId);
        try {
            logger.info("删除收藏店铺记录，收藏Id为【" + this.favoriteId + "】");
            myFavoriteService.deleteFavorite(this.favoriteId);
        } catch (Exception e) {
            logger.error("删除收藏店铺记录出错：" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    public Long getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(Long favoriteId) {
        this.favoriteId = favoriteId;
    }

    public String getProductImgServerUrl() {
        return ConfigurationUtil.getString("PRODUCT_IMG_PATH");
    }

    public String getProductDetailUrl() {
        return ConfigurationUtil.getString("CMS_PAGE_PATH");
    }

    public ReturnResult getReturnResult() {
        return returnResult;
    }

    public void setReturnResult(ReturnResult returnResult) {
        this.returnResult = returnResult;
    }

}
