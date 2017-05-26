package com.kmzyc.b2b.action.member;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.framework.action.BaseAction;
import com.km.framework.page.Pagination;
import com.kmzyc.b2b.service.member.ProdAppraiseService;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.zkconfig.ConfigurationUtil;

@SuppressWarnings("serial")
@Controller
@Scope("prototype")
public class MyEvaluateAction extends BaseAction {
    // private static Logger logger = Logger.getLogger(MyEvaluateAction.class);
    private static Logger logger = LoggerFactory.getLogger(MyEvaluateAction.class);

    @Resource(name = "prodAppraiseServiceImpl")
    private ProdAppraiseService prodAppraiseService;

    // private String productImgServerUrl = ConfigurationUtil.getString("PRODUCT_IMG_PATH");
    // private String productDetailUrl = ConfigurationUtil.getString("detailPath");

    private int isOrderList;

    // 标示页码
    private int pagenumber;

    public String queryNoEvaluateList() {
        // 获取缓存用户id
        HttpSession session = getSession();
        Long userId = (Long) session.getAttribute(Constants.SESSION_USER_ID);
        Pagination page = this.getPagination(5, 3);
        // sql查询条件对象
        Map<String, Object> newCondition = new HashMap<String, Object>();

        // 解析并组装查询条件
        newCondition.put("userId", userId);
        // newCondition.put("userId", 3763);

        newCondition.put("assessStatus", 1);
        // 添加渠道查询条件
        String channel = ConfigurationUtil.getString("CHANNEL");
        newCondition.put("channel", channel);
        // 设置查询条件
        page.setObjCondition(newCondition);

        try {
            this.pagintion = prodAppraiseService.findAppraiseOrder(page);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return SUCCESS;
    }

    public String queryIsEvaluateList() {
        // 获取缓存用户id
        HttpSession session = getSession();
        Long userId = (Long) session.getAttribute(Constants.SESSION_USER_ID);
        Pagination page = this.getPagination(5, 3);
        // sql查询条件对象
        Map<String, Object> newCondition = new HashMap<String, Object>();

        // 解析并组装查询条件
        newCondition.put("userId", userId);
        // 添加渠道查询条件
        String channel = ConfigurationUtil.getString("CHANNEL");
        newCondition.put("channel", channel);
        // 设置查询条件
        newCondition.put("assessStatus", 2);

        // 设置查询条件
        page.setObjCondition(newCondition);

        try {
            this.pagintion = prodAppraiseService.findAppraiseOrder(page);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return SUCCESS;
    }

    public String getProductImgServerUrl() {
        return ConfigurationUtil.getString("PRODUCT_IMG_PATH");
    }

    public String getProductDetailUrl() {
        return ConfigurationUtil.getString("detailPath");
    }

    public int getIsOrderList() {
        return isOrderList;
    }

    public void setIsOrderList(int isOrderList) {
        this.isOrderList = isOrderList;
    }

    public int getPagenumber() {
        return pagenumber;
    }

    public void setPagenumber(int pagenumber) {
        this.pagenumber = pagenumber;
    }

}
