package com.pltfm.shiro.action;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.zkconfig.ConfigurationUtil;
import com.kmzyc.cms.remote.service.RemoteSupplierParseService;
import com.kmzyc.supplier.model.ShopMain;
import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Component(value = "authenSupplyAction")
public class AuthenSupplyAction extends ActionSupport {
	private static Logger logger = LoggerFactory.getLogger(AuthenSupplyAction.class);

    @Resource(name = "remoteSupplierParseService")
    private RemoteSupplierParseService remoteSupplierParseService;

    private String supplyUrl = null;
    private static final int SC_OK = 200;

    private static final Long TIMEOUT = 5000L;

    /**
     * 供应商店铺装修登录
     */
    public String supplyAuthen() {
        try {
            Subject currUser = SecurityUtils.getSubject();
            // 对用户名和密码进行验证
            UsernamePasswordToken token = new UsernamePasswordToken();
            String username = ServletActionContext.getRequest().getParameter("username");
            String password = ServletActionContext.getRequest().getParameter("password");
            String dataType = ServletActionContext.getRequest().getParameter("dataType");
            String shopSite = ServletActionContext.getRequest().getParameter("shopSite");
            String shopId = ServletActionContext.getRequest().getParameter("shopId");
            String supplierId = ServletActionContext.getRequest().getParameter("supplyid");

            token.setPassword(password.toCharArray());
            token.setUsername(username);

            ShopMain shopMain = new ShopMain();
            shopMain.setShopSite(shopSite);
            shopMain.setShopId(Long.valueOf(shopId));
            shopMain.setSupplierId(Long.valueOf(supplierId));
            currUser.login(token);
            if (currUser.isAuthenticated()) { // 认证通过，重定向到静态资源
                supplyUrl = remoteSupplierParseService.remoteAddTheme(shopMain, Integer.parseInt(dataType));
                currUser.getSession().setAttribute("supplyUsername", username);
                if (supplyUrl != null) {
                    supplyUrl = supplyUrl + "?" + System.currentTimeMillis();
                }

                // 构造httpclient的实例
                HttpClient htpc = new HttpClient();
                // 创建Get方法的实例
                // url需要传递参数并包含中文时，可以将参数转码（URLEncoder.encode(参数,"UTF-8")），与服务器端一样的编码格式
                GetMethod getMethod = new GetMethod(supplyUrl); // 链接的路径如：http://www.baidu.com
                // 使用系统提供的默认的恢复策略,此处HttpClient的恢复策略可以自定义（通过实现接口HttpMethodRetryHandler来实现）。
                getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
                try {
                    // 执行getMethod
                    int statusCode = 0;
                    // 当前时间
                    Long currentTime = System.currentTimeMillis();
                    // 执行时间
                    Long executeTime = 0L;

                    while (statusCode != SC_OK && executeTime < TIMEOUT) {
                        statusCode = htpc.executeMethod(getMethod);
                        //执行时间
                        executeTime = System.currentTimeMillis() - currentTime;
                    }
                    // 读取内容
                    //	byte[] responseBody = getMethod.getResponseBody();
                    // 处理内容
                    //	System.out.println(new String(responseBody));
                } catch (HttpException e) {
                    // 发生致命异常，可能是协议不对或者返回的内容有问题
                    logger.error("AuthenSupplyAction.supplyAuthen发生致命异常，可能是协议不对或者返回的内容有问题Please check your provided http address：" + e.getMessage(), e);
                } catch (IOException e) {
                    // 发生网络异常
                    logger.error("AuthenSupplyAction.supplyAuthen发生网络异常：" + e.getMessage(), e);
                } finally {
                    // 释放连接
                    getMethod.releaseConnection();
                }
                logger.info("供应商店铺装修登录返回SUCCESS");
                return SUCCESS;

            }
        } catch (Exception e) {
            logger.error("AuthenSupplyAction.supplyAuthen供应商店铺装修登录报错：" + e.getMessage(), e);
        }
        supplyUrl = ConfigurationUtil.getString("gysPortalPath");
        return ERROR;
    }

    public RemoteSupplierParseService getRemoteSupplierParseService() {
        return remoteSupplierParseService;
    }

    public void setRemoteSupplierParseService(RemoteSupplierParseService remoteSupplierParseService) {
        this.remoteSupplierParseService = remoteSupplierParseService;
    }

    public String getSupplyUrl() {
        return supplyUrl;
    }

    public void setSupplyUrl(String supplyUrl) {
        this.supplyUrl = supplyUrl;
    }
}
