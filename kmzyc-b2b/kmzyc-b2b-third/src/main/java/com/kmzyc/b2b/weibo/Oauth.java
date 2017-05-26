package com.kmzyc.b2b.weibo;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kmzyc.b2b.third.util.ThirdConstant;
import com.kmzyc.b2b.third.util.URLEncodeUtils;
import com.kmzyc.b2b.weibo.http.AccessToken;
import com.kmzyc.b2b.weibo.http.BASE64Encoder;
import com.kmzyc.b2b.weibo.json.JSONException;
import com.kmzyc.b2b.weibo.json.JSONObject;
import com.kmzyc.b2b.weibo.model.PostParameter;
import com.kmzyc.b2b.weibo.model.WeiboException;
import com.kmzyc.zkconfig.ConfigurationUtil;

public class Oauth extends Weibo {

    private static Logger logger= LoggerFactory.getLogger(Oauth.class);
    /**
	 * 
	 */
    private static final long serialVersionUID = 7003420545330439247L;
    // ----------------------------针对站内应用处理SignedRequest获取accesstoken----------------------------------------
    public String access_token;
    public String user_id;

    public String getToken() {
        return access_token;
    }

    /*
     * 解析站内应用post的SignedRequest split为part1和part2两部分
     */
    public String parseSignedRequest(String signed_request) throws IOException,
            InvalidKeyException, NoSuchAlgorithmException {
        String[] t = signed_request.split("\\.", 2);
        // 为了和 url encode/decode 不冲突，base64url 编码方式会将
        // '+'，'/'转换成'-'，'_'，并且去掉结尾的'='。 因此解码之前需要还原到默认的base64编码，结尾的'='可以用以下算法还原
        int padding = (4 - t[0].length() % 4);
        for (int i = 0; i < padding; i++)
            t[0] += "=";
        String part1 = t[0].replace("-", "+").replace("_", "/");

        SecretKey key =
                new SecretKeySpec(ConfigurationUtil.getString("sina_client_SERCRET").getBytes(),
                        "hmacSHA256");
        Mac m;
        m = Mac.getInstance("hmacSHA256");
        m.init(key);
        m.update(t[1].getBytes());
        String part1Expect = BASE64Encoder.encode(m.doFinal());

        @SuppressWarnings("restriction")
        sun.misc.BASE64Decoder decode = new sun.misc.BASE64Decoder();
        @SuppressWarnings("restriction")
        String s = new String(decode.decodeBuffer(t[1]));
        if (part1.equals(part1Expect)) {
            return ts(s);
        } else {
            return null;
        }
    }

    /*
     * 处理解析后的json解析
     */
    public String ts(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            access_token = jsonObject.getString("oauth_token");
            user_id = jsonObject.getString("user_id");
        } catch (JSONException e) {
            logger.error(e.getMessage(),e);
        }
        return access_token;

    }

    /*----------------------------Oauth接口--------------------------------------*/

    public AccessToken getAccessTokenByCode(String code) throws WeiboException {
        return new AccessToken(client.post(
                ConfigurationUtil.getString("sina_accessTokenURL"),
                new PostParameter[] {
                        new PostParameter("client_id", ConfigurationUtil
                                .getString("sina_client_ID")),
                        new PostParameter("client_secret", ConfigurationUtil
                                .getString("sina_client_SERCRET")),
                        new PostParameter("grant_type", "authorization_code"),
                        new PostParameter("code", code),
                        new PostParameter("redirect_uri", ConfigurationUtil
                                .getString("sina_redirect_URI"))}, false));
    }

    public String authorize(String response_type, String state) throws WeiboException {
        String url =
                ConfigurationUtil.getString("sina_authorizeURL").trim()
                        + "?client_id="
                        + ConfigurationUtil.getString("sina_client_ID").trim()
                        + "&redirect_uri="
                        + URLEncodeUtils.encodeURL(ConfigurationUtil.getString("sina_redirect_URI")
                                .trim()) + "&response_type=" + response_type + "&state=" + state;

        return url;
    }

    /**
     * 由于有需要传入参数,重载该方法
     * 
     * @param response_type
     * @param state
     * @param isComeFromBindManage
     * @return
     * @throws WeiboException
     */
    public String authorizeOverload(String response_type, String state,
            String isComeFromBindManage, String clientIp) throws WeiboException {
        String url =
                ConfigurationUtil.getString("sina_authorizeURL").trim()
                        + "?client_id="
                        + ConfigurationUtil.getString("sina_client_ID").trim()
                        + "&redirect_uri="
                        + URLEncodeUtils.encodeURL(ConfigurationUtil.getString("sina_redirect_URI")
                                .trim()
                                + "?"
                                + ThirdConstant.IS_COMEFROMBINDMANAGE
                                + "="
                                + isComeFromBindManage + "&clientIp=" + clientIp)
                        + "&response_type=" + response_type + "&state=" + state;

        return url;
    }

    public String authorize(String response_type, String state, String scope) throws WeiboException {
        String url =
                ConfigurationUtil.getString("sina_redirect_URI").trim() + "?client_id="
                        + ConfigurationUtil.getString("sina_client_ID").trim() + "&redirect_uri="
                        + ConfigurationUtil.getString("sina_redirect_URI").trim()
                        + "&response_type=" + response_type + "&state=" + state + "&scope=" + scope;
        System.out.println(url);
        return url;
    }
}
