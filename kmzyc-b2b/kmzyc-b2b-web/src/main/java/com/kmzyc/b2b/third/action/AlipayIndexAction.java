package com.kmzyc.b2b.third.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.framework.action.BaseAction;
import com.kmzyc.b2b.third.util.AlipaySubmit;
import com.kmzyc.b2b.third.util.ThirdConstant;
import com.kmzyc.zkconfig.ConfigurationUtil;

@Controller("alipayIndexAction")
@Scope("prototype")
public class AlipayIndexAction extends BaseAction {
    private static final long serialVersionUID = 7731508186475761L;
    // private static Logger log = LoggerFactory.getLogger(AlipayIndexAction.class);
    private static Logger log = LoggerFactory.getLogger(AlipayIndexAction.class);

    /**
     * @throws Exception
     * 
     */
    public String alipayIndex() throws Exception {
        // //////////////////////////////////请求参数//////////////////////////////////////
        HttpServletRequest request = ServletActionContext.getRequest();

        // 目标服务地址
        String target_service = ConfigurationUtil.getString("alipay_target_service");
        // 必填，页面跳转同步通知页面路径 ,拼接起我们自己的参数(是否是从绑定管理处过来主动绑定)
        String return_url =
                ConfigurationUtil.getString("alipay_return_url") + "?"
                        + ThirdConstant.IS_COMEFROMBINDMANAGE + "="
                        + request.getParameter(ThirdConstant.IS_COMEFROMBINDMANAGE) + "&clientIp="
                        + request.getParameter("clientIp");
        log.info("return_url-------" + return_url);
        // 需http://格式的完整路径，不允许加?id=123这类自定义参数

        // 防钓鱼时间戳
        String anti_phishing_key = ConfigurationUtil.getString("alipay_anti_phishing_key");
        // 若要使用请调用类文件submit中的query_timestamp函数

        // 客户端的IP地址
        String exter_invoke_ip = ConfigurationUtil.getString("alipay_exter_invoke_ip");

        // 把请求参数打包成数组
        Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("service", "alipay.auth.authorize");
        sParaTemp.put("partner", ConfigurationUtil.getString("alipay_partner"));
        sParaTemp.put("_input_charset", ConfigurationUtil.getString("alipay_input_charset"));
        sParaTemp.put("target_service", target_service);
        sParaTemp.put("return_url", return_url);
        sParaTemp.put("anti_phishing_key", anti_phishing_key);
        sParaTemp.put("exter_invoke_ip", exter_invoke_ip);
        Map<String, String> sPara = AlipaySubmit.buildRequestPara(sParaTemp);
        log.info("sPara-------" + sPara);
        request.setAttribute("sPara", sPara);
        return "skip";
    }

}
