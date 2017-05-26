package com.kmzyc.b2b.third.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.b2b.third.model.taobao.Buyer;
import com.kmzyc.b2b.third.util.ThirdConstant;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.internal.util.WebUtils;
import com.taobao.api.request.UserShippingAddressesGetRequest;
import com.taobao.api.response.UserShippingAddressesGetResponse;

import net.sf.json.JSONObject;

/**
 * 天猫医药馆授权处理类
 * 
 * @author Administrator
 * 
 */
@Controller("tmallAuthorization")
@Scope("prototype")
public class TmallAuthorization extends ThirdLoginAction {
    /**
	 * 
	 */
    private static final long serialVersionUID = 4148700178777025291L;

    /**
	 * 
	 */
    private static Logger log = LoggerFactory.getLogger(TmallAuthorization.class);

    /**
     * 获取到code,按照Auth2.0的授权过程获得用户信息
     * 
     * @returnTmallAuthorization
     * @throws IOException
     */
    public String tmallAuthorization() throws IOException {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();

        String code = request.getParameter("code");

        if (null == code) {
            log.info("tmallAuthorization 获取code参数为空!");
            // 可去到一个中间页面,告诉用户取消了授权,是否去商城首页,还是返回天猫医药馆
            response.sendRedirect(ConfigurationUtil.getString("taobao_tmall_url"));
            return null;
        }

        // 相关的配置常量及凭据信息
        String clientId = ConfigurationUtil.getString("taobao_qt_appkey");
        String secret = ConfigurationUtil.getString("taobao_qt_secret");
        String tokenUrl = ConfigurationUtil.getString("taobao_normal_tokenurl");
        String env = ConfigurationUtil.getString("taobao_normal_env");

        // 封装获取accessToken需要请求的参数
        Map<String, String> param = new HashMap<String, String>();
        param.put("grant_type", "authorization_code");
        param.put("code", code);
        param.put("client_id", clientId);
        param.put("client_secret", secret);
        param.put("redirect_uri", ConfigurationUtil.getString("taobao_qt_redirecturl"));
        param.put("view", request.getParameter("view"));
        param.put("state", request.getParameter("state"));

        // 商品的信息是通过state参数带过来的,并且固定格式为item_id_num:18494134547,skuId:1235,quantity:1
        String productInfoStr = request.getParameter("state");

        String[] productInfoArray = productInfoStr.split(",");

        // item_id_num
        String num = productInfoArray[0].split(":")[1];

        // sku
        String sku = productInfoArray[1].split(":")[1];

        // 数量
        String quantity = productInfoArray[2].split(":")[1];

        String responseJson = WebUtils.doPost(tokenUrl, param, 3000, 3000);
        JSONObject tokenJson = JSONObject.fromObject(responseJson);
        String sessionKey = tokenJson.get("access_token").toString();
        // 做转码处理
        String userNick =
                java.net.URLDecoder.decode(tokenJson.get("taobao_user_nick").toString(), "utf-8");
        String userId = tokenJson.get("taobao_user_id").toString();

        // 获取用户收货地址
        TaobaoClient usaClient = new DefaultTaobaoClient(env, clientId, secret);
        UserShippingAddressesGetRequest usaReq = new UserShippingAddressesGetRequest();
        usaReq.setFields("address_id,receiver_name,phone,mobile,location.address,location.city");
        try {
            UserShippingAddressesGetResponse usaRep = usaClient.execute(usaReq, sessionKey);

            List<Buyer> userAddrList = null;
            // 若是收货地址为空
            if (null != usaRep.getShippingAddresses()) {
                userAddrList = new ArrayList<Buyer>();
                for (int i = 0; i < usaRep.getShippingAddresses().size(); i++) {
                    Buyer buyer = new Buyer();
                    buyer.setAddressId(usaRep.getShippingAddresses().get(i).getAddressId()
                            .toString());
                    buyer.setReceiverName(usaRep.getShippingAddresses().get(i).getReceiverName());
                    buyer.setMobile(usaRep.getShippingAddresses().get(i).getMobile());
                    buyer.setCity(usaRep.getShippingAddresses().get(i).getLocation().getCity());
                    buyer.setAddress(usaRep.getShippingAddresses().get(i).getLocation()
                            .getAddress());
                    // 将买家信息添加到集合中
                    userAddrList.add(buyer);
                }

                log.info("tmallAuthorization成功获取收货地址,一共有" + userAddrList.size() + "个收货地址!");
            }

            // 将需要用到的信息存入到session
            request.getSession().setAttribute(ThirdConstant.TMALL_USER_ID, "taobao_" + userId);
            request.getSession().setAttribute("sessionKey", sessionKey);
            request.getSession().setAttribute("userNick", userNick);
            request.getSession().setAttribute("receiveAddr", userAddrList);

            // 将商品的相关数据传入到目的action
            request.setAttribute("skuId", sku);
            request.setAttribute("quantity", quantity);
            request.setAttribute("item_id_num", num);

        } catch (ApiException e) {
            log.error("TmallAuthorization====> 淘宝API提供异常" + e.getMessage(), e);
            return "error";
        }
        return "toShopCart";
    }
}
