package com.kmzyc.b2b.ajax;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.framework.action.BaseAction;
import com.km.framework.page.Pagination;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.service.LoginService;
import com.kmzyc.b2b.service.member.ConsultService;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.framework.constants.InterfaceResultCode;
import com.kmzyc.framework.filter.XssHttpServletRequestWrapperNew;
import com.kmzyc.framework.sensitive.SensitiveType;
import com.kmzyc.framework.sensitive.SensitiveWordFilter;
import com.pltfm.app.vobject.Consult;

/**
 * 提供给CMS的关于咨询的接口
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings("serial")
@Controller
@Scope("prototype")
public class ConsultInterfaceAction extends BaseAction {

    // private static Logger logger = Logger.getLogger(ConsultInterfaceAction.class);
    private static Logger logger = LoggerFactory.getLogger(ConsultInterfaceAction.class);

    // 返回页面对象
    private ReturnResult returnResult;

    @Resource(name = "consultServiceImpl")
    private ConsultService consultService;

    @Resource(name = "loginServiceImp")
    private LoginService loginService;

    @Resource(name = "sensitiveWordFilter")
    private SensitiveWordFilter sensitiveWordFilter;

    // 产品咨询类型
    private String consultType;

    // 咨询内容
    private String consultContent;

    // 咨询对应的skuid
    private String productSkuId;

    // 咨询对应的SKUCODE
    private String productSkuCode;

    private String pageIndex;

    /**
     * CMS调用，保存咨询的接口
     * 
     * @return
     * @throws Exception
     */
    public String saveConsult() throws Exception {
        // 判断当前用户是否登陆
        HttpServletRequest request = ServletActionContext.getRequest();
        // boolean isLogin = this.isLogin(request);
        // 如果登陆了
        if (this.isSensitive(consultContent)) {
            returnResult = new ReturnResult(InterfaceResultCode.HAVE_SENSITIVE, "包含敏感词汇", null);
            return SUCCESS;
        }
        // 取得用户
        User user = loginService.verifyLogin(request);
        try {
            Consult c = consultService.saveConsult(consultType, user, consultContent, productSkuId,
                    productSkuCode);
            if (c.getConsultId() != null) {
                returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", c);
            }
        } catch (Exception e) {
            logger.error("保存失败" + e.getMessage(), e);
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, "保存失败", null);
        }
        return SUCCESS;
    }

    // 判断当前用户是否登陆
    private boolean isLogin(HttpServletRequest request) {
        boolean flag = false;
        User user = loginService.verifyLogin(request);
        if (user != null) {
            flag = true;
        }
        return flag;
    }

    /**
     * 分页查询咨询
     * 
     * @return
     */
    public String findConsultByCondition() {
        try {
            Map<String, Object> map = new HashMap<String, Object>();

            if (consultType != null && !consultType.equals("0")
                    && StringUtils.isNumeric(consultType)) {
                map.put("type", consultType);
            }

            if (consultContent != null) {
                map.put("consultContent", consultContent);
            }
            if (productSkuId != null && StringUtils.isNumeric(productSkuId)) {
                map.put("productSkuid", productSkuId);
            }
            // 测试
            // map.put("type", 3);
            // map.put("productSkuid", 3373);
            // map.put("consultContent", "可以");
            Pagination page = this.getPagination(5, 10);
            if (pageIndex != null) {
                if (!pageIndex.equals("1")) {
                    page.setStartindex((Integer.parseInt(pageIndex.toString()) - 1) * 10 + 1);
                    page.setEndindex(Integer.parseInt(pageIndex.toString()) * 10);
                }
            }
            page.setObjCondition(map);
            Pagination consultPage = consultService.findConsultListByPage(page);

            returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", consultPage);
        } catch (Exception e) {
            logger.error("根据SKUID查询咨询出错：" + e.getMessage(), e);
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", null);
        }
        return SUCCESS;
    }

    /**
     * 是否包含敏感词汇 true :包含 false:不包含
     * 
     * @param keyword
     * @return
     */
    private boolean isSensitive(String keyword) {
        boolean flag = false;
        if (sensitiveWordFilter.doFilt(keyword, SensitiveType.commit)) {
            flag = true;
        }
        return flag;
    }

    public ReturnResult getReturnResult() {
        return returnResult;
    }

    public void setReturnResult(ReturnResult returnResult) {
        this.returnResult = returnResult;
    }

    public String getConsultType() {
        return consultType;
    }

    public void setConsultType(String consultType) {
        consultType = XssHttpServletRequestWrapperNew.xssEncode(consultType);
        consultType = XssHttpServletRequestWrapperNew.HTMLEncode(consultType);
        this.consultType = consultType;
    }

    public String getConsultContent() {
        return consultContent;
    }

    public void setConsultContent(String consultContent) {
        consultContent = XssHttpServletRequestWrapperNew.xssEncode(consultContent);
        consultContent = XssHttpServletRequestWrapperNew.HTMLEncode(consultContent);
        this.consultContent = consultContent;
    }

    public String getProductSkuId() {
        return productSkuId;
    }

    public void setProductSkuId(String productSkuId) {
        productSkuId = XssHttpServletRequestWrapperNew.xssEncode(productSkuId);
        productSkuId = XssHttpServletRequestWrapperNew.HTMLEncode(productSkuId);
        this.productSkuId = productSkuId;
    }

    public String getProductSkuCode() {
        return productSkuCode;
    }

    public void setProductSkuCode(String productSkuCode) {
        productSkuCode = XssHttpServletRequestWrapperNew.xssEncode(productSkuCode);
        productSkuCode = XssHttpServletRequestWrapperNew.HTMLEncode(productSkuCode);
        this.productSkuCode = productSkuCode;
    }

    public String getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(String pageIndex) {
        pageIndex = XssHttpServletRequestWrapperNew.xssEncode(pageIndex);
        pageIndex = XssHttpServletRequestWrapperNew.HTMLEncode(pageIndex);
        this.pageIndex = pageIndex;
    }

}
