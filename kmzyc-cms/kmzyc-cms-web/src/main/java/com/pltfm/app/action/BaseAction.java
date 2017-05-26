package com.pltfm.app.action;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.CookiesAware;
import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.kmzyc.supplier.model.ShopCategorys;
import com.opensymphony.xwork2.ActionSupport;
import com.pltfm.app.service.ShopCategorysService;

public class BaseAction extends ActionSupport implements ServletRequestAware,
        ServletResponseAware, RequestAware, CookiesAware, ParameterAware,
        SessionAware, ApplicationAware {
	private static Logger logger = LoggerFactory.getLogger(BaseAction.class);

    /**
     *
     */
    private static final long serialVersionUID = -3436974352110732022L;

    @Resource(name = "shopCategorysService")
    private ShopCategorysService shopCategoryService;


    protected HttpServletRequest httpServletRequest;

    protected HttpServletResponse httpServletResponse;

    protected Map request;

    protected Map session;

    protected Map application;

    protected Map parameters;

    protected Map cookiesMap;

    protected int pageSize = 10;

    public String formatDouble(double s) {
        DecimalFormat fmt = new DecimalFormat("##0.00");
        return fmt.format(s);

    }

    /**
     * 将对象转换成JSON字符串，并响应回前台
     */


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

    /**
     * 取得response
     */

    protected HttpServletResponse getResponse() {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        return response;
    }

    /**
     * maliqun add 设置店铺一级分类
     */
    protected void setShopCategoryList(long shopId) {
        try {
            List<ShopCategorys> topList = shopCategoryService.queryShopCategoryListByParentId(shopId);
            ServletActionContext.getRequest().setAttribute("shopCategoryParentList", topList);
        } catch (Exception e) {
        	logger.error("BaseAction.setShopCategoryList异常：" + e.getMessage(), e);
        }
    }


    protected final Log log = LogFactory.getLog(getClass());

    @Override
    public void setApplication(Map arg0) {
        this.application = arg0;

    }

    @Override
    public void setSession(Map arg0) {
        this.session = arg0;

    }

    @Override
    public void setParameters(Map arg0) {
        this.parameters = arg0;

    }

    @Override
    public void setCookiesMap(Map arg0) {
        this.cookiesMap = arg0;

    }

    @Override
    public void setRequest(Map arg0) {
        this.request = arg0;

    }

    @Override
    public void setServletResponse(HttpServletResponse arg0) {
        this.httpServletResponse = arg0;

    }

    @Override
    public void setServletRequest(HttpServletRequest arg0) {
        // TODO Auto-generated method stub
        this.httpServletRequest = arg0;
    }

}
