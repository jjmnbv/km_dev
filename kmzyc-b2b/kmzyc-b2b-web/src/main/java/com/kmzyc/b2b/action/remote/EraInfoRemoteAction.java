package com.kmzyc.b2b.action.remote;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.tempuri.IMemberInfo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.km.framework.common.util.MD5;
import com.kmzyc.b2b.ajax.UserBaseAction;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.service.EraInfoService;
import com.kmzyc.b2b.service.LoginService;
import com.kmzyc.b2b.shopcart.util.ShopCartUtil;
import com.kmzyc.b2b.util.CheckEraInfoMap;
import com.kmzyc.b2b.util.HttpUtils;
import com.kmzyc.b2b.util.pay.util.RSA;
import com.kmzyc.b2b.vo.EraInfo;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.framework.constants.InterfaceResultCode;
import com.kmzyc.util.HttpClientUtils;
import com.kmzyc.util.StringUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;

/**
 * 时代用户对外接口
 */
@Controller("eraInfoRemote")
public class EraInfoRemoteAction extends UserBaseAction {

    private static Logger logger = LoggerFactory.getLogger(EraInfoRemoteAction.class);


    @Resource(name = "loginServiceImp")
    private LoginService loginService;

    @Resource
    private EraInfoService eraInfoService;

    /**
     * 更新时代会员信息
     *
     * @return
     */
    public void updateEraInfo() {

        Map<String, String> rsMap = new HashMap<>();

        try {

            String sign = this.getRequest().getParameter("Sign");
            String datas = this.getRequest().getParameter("Datas");

            logger.info("直销系统调用:更新时代会员信息开始,签名({}),更新数据({})", sign, datas);

            if (StringUtil.isEmpty(sign) || StringUtil.isEmpty(datas)) {
                rsMap.put("success", "0");
                rsMap.put("messageCode", "1"); // 0：系统异常 1：参数错误 2:签名错误
            } else {

                sign = StringUtil.toNormalStr(sign);
                datas = StringUtil.toNormalStr(datas);
                boolean flag = false;
                try {
                    flag = RSA.verify(datas, sign, ConfigurationUtil.getString("sd_b2b_public_key"),
                            "utf-8");
                } catch (Exception e) {
                    logger.error("更新时代会员异常:验签异常", e);
                }

                if (flag) {
                    JSONArray array = JSONArray.parseArray(datas);
                    if (!StringUtil.isEmpty(array) && array.size() <= 50) {
                        rsMap = this.eraInfoService.updateEraInfo(array);
                    } else {
                        rsMap.put("success", "0");
                        rsMap.put("messageCode", "1"); // 0：系统异常 1：参数错误 2:签名错误
                    }
                } else {
                    rsMap.put("success", "0");
                    rsMap.put("messageCode", "2"); // 0：系统异常 1：参数错误 2:签名错误
                }
            }
        } catch (Exception e) {
            logger.error("更新时代会员异常", e);
            rsMap.put("success", "0");
            rsMap.put("messageCode", "0"); // 0：系统异常 1：参数错误 2:签名错误
        }

        try {
            this.getResponse().getWriter().println(JSONObject.toJSONString(rsMap));
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.info("直销系统调用:更新时代会员信息结束({})", JSONObject.toJSONString(rsMap));

    }

    /**
     * 时代会员登录入口 易创网使用
     */
    public String timesLogin() {
        HttpServletResponse response = this.getResponse();
        HttpServletRequest request = this.getRequest();
        response.setHeader("P3P",
                "CP='IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT'");


        // 检查是否是从时代发过来的请求
        if (!this.checkTrustNt()) {
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, "未受信任站点", "");
            return SUCCESS;
        }

        String dateTime = request.getParameter("dateTime");
        String number = request.getParameter("number");
        String sign = request.getParameter("sign");

        // 参数校验
        if (StringUtil.isEmpty(dateTime) || StringUtil.isEmpty(number)
                || StringUtil.isEmpty(sign)) {
            logger.error("参数错误number={},dateTime={},sign={}", number, dateTime, sign);
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, "参数错误", "");
            return SUCCESS;
        }

        String token = "";
        String msg = "";
        // 验证字符串是否一致，一致则进行授权交易
        if (this.checkSign(dateTime, number, "2.00vWf4", sign)) {

            if (!this.checkAuthorization(number, dateTime, sign)) {
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, "授权失败", "");
            }

        } else {
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, "签名错误", "");
            return SUCCESS;
        }

        try {
            IMemberInfo im = loginService.findWebservice();// 获取时代系统接口

            if (number.length() > 3) {
                String jsonString = im.memberGetInfo(number);

                EraInfo einfo = this.loginService.getEraInfoOBJ(JSONObject.parseObject(jsonString));
                // 查询时代会员是否存在于商城库中（如果不存在，则添加时代会员信息到商城库中，如果存在，则更新商城中的时代会员信息）
                Map<String, Object> map = new HashMap();
                map.put("eraNo", einfo.getEraNo());
                EraInfo eraInfo = null;
                User userInfo = null;
                try {
                    eraInfo = eraInfoService.selectEranInfoById(map);
                } catch (SQLException e) {
                    logger.error("时代会员联合登录：根据eraNo:{}查询时代信息异常", einfo.getEraNo());
                    returnResult = new ReturnResult(InterfaceResultCode.FAILED, "系统异常", "");
                    return SUCCESS;
                }
                if (null == eraInfo) {
                    Boolean usrR = loginService.insertEraInfo(einfo);

                    if (usrR) {
                        try {
                            eraInfo = eraInfoService.selectEranInfoById(map);
                            if (null != eraInfo) {
                                userInfo = loginService
                                        .queryUserByLoginId(eraInfo.getnLoginId().toString());
                            }
                        } catch (SQLException e) {
                            logger.error("时代会员联合登录：根据eraNo:{}查询时代信息异常", einfo.getEraNo());
                            returnResult = new ReturnResult(InterfaceResultCode.FAILED, "系统异常", "");
                            return SUCCESS;
                        }

                    } else {
                        logger.error("时代会员联合登录：注册时代会员({})失败", einfo.getEraNo());
                        returnResult = new ReturnResult(InterfaceResultCode.FAILED, "登录失败", "");
                        return SUCCESS;
                    }
                } else {
                    einfo.setEraInfoId(eraInfo.getEraInfoId());
                    einfo.setLoginId(new BigDecimal(eraInfo.getnLoginId()));
                    Boolean usrR = loginService.updateEraInfo(einfo);
                    if (!usrR) {
                        logger.error("时代会员联合登录：更新时代会员({})信息异常", einfo.getEraNo());
                        returnResult = new ReturnResult(InterfaceResultCode.FAILED, "登录失败", "");
                        return SUCCESS;
                    }

                    userInfo = loginService.queryUserByLoginId(eraInfo.getnLoginId().toString());
                    userInfo.setEarInfo(einfo);
                }

                // 每天首次登录送积分，记录登录IP
                if (null != userInfo && 1 == userInfo.getStatus()) {
                    String ip = HttpUtils.getIP(request);
                    writeLoginCoodie(userInfo, false, ip);
                    loginService.doAfterLogin(userInfo,
                            ShopCartUtil.getTempUserId(getRequest(), getResponse()), null, ip);
                }

                logger.info("时代会员联合登录：" + number + "登陆成功");

                returnResult = new ReturnResult("1", "登录成功!", "");
                return SUCCESS;

            } else {
                logger.info(
                        "时代会员联合登录：" + number + "调用远程接口登陆返回码为：" + CheckEraInfoMap.getValue(number));
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, "登录失败", "");
                return SUCCESS;
            }

        } catch (Exception e) {
            logger.error("时代会员联合登录：登录异常", e);
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, "登录异常", "");
            return SUCCESS;
        }
    }

    /**
     * 根据dateTime,number,key生成签名串与客户端传过来的字符串进行比较
     *
     * @param dateTime
     * @param number
     * @param key
     * @return true--一致，false -- 不相同
     */
    public boolean checkSign(String dateTime, String number, String key, String sign) {
        // 根据dateTime，number,key生成签名串
        String signStr = "dateTime=" + dateTime + "&number=" + number + "&key=" + key;
        String mySign = MD5.getMD5Str(signStr).toUpperCase();// 生成签名串
        // 验证签名串是否一致
        if (mySign.equals(sign)) {
            return true;
        }
        logger.error("时代会员联合登录：生成的签名串=({})与客户端传入的签名=({})不一致，验证失败！", mySign, sign);
        return false;
    }

    /**
     * 校验请求的地址是否为受任务链接
     * 
     * @return
     */
    public boolean checkTrustNt() {

        // 测试先注释
        String urlStr = ConfigurationUtil.getString("sd_login_url");
        HashMap<String, String> ntMap = new HashMap();
        String[] urls = urlStr.split(",");
        for (int i = 0; i < urls.length; i++) {
            ntMap.put(urls[i], urls[i]);
        }

        // 检查是否是从时代发过来的请求
        String referer = this.getRequest().getHeader("Referer");
        int indexD = referer.indexOf(":");
        String tempStr = referer.substring(indexD + 3);
        int indexG = tempStr.indexOf("/") + indexD + 3;
        String requestUrl = referer.substring(0, indexG);

        if (!StringUtil.isEmpty(ntMap) && !ntMap.containsKey(requestUrl)) {
            logger.info("时代会员联合登录：站点:{} 来源不明！拒绝登录", requestUrl);
            return false;
        }

        return true;
    }

    /**
     * 获取授权token 校验授权
     * 
     * @param number
     * @param dateTime
     * @param sign
     * @return
     */
    public boolean checkAuthorization(String number, String dateTime, String sign) {


        Map<String, Object> parMap = new HashMap<>();
        parMap.put("number", number);
        parMap.put("dateTime", dateTime);
        parMap.put("sign", sign);
        String result = null;
        try {
            result = HttpClientUtils.get(ConfigurationUtil.getString("sd_url") + "/Oauth/GetToken",
                    parMap);
            logger.info("时代会员联合登录：获取授权TOKEN={}", result);
        } catch (Exception e) {
            logger.error("时代会员联合登录：获取授权TOKEN异常", e);
        }

        JSONObject tokenJson = JSONObject.parseObject(result);

        // 如果授权成功,则调用远程接口取得用户信息
        if (!StringUtil.isEmpty(tokenJson) && !"null".equals(tokenJson.getString("Token"))
                && !StringUtil.isEmpty(tokenJson.getString("Token"))) {

            // 查询用户信息
            String getuserInfoUrl =
                    ConfigurationUtil.getString("sd_url").concat("/Oauth/GetMemberInfo");
            parMap = new HashMap<>();
            parMap.put("token", tokenJson.getString("Token"));
            try {
                result = HttpClientUtils.get(getuserInfoUrl, parMap);
                logger.info("时代会员联合登录：获取用户信息={}", result);
            } catch (Exception e) {
                logger.error("时代会员联合登录：获取用户信息异常", e);
            }

            JSONObject userInfoJson = JSONObject.parseObject(result);
            if (!userInfoJson.containsKey("Number")
                    || StringUtil.isEmpty(userInfoJson.getString("Number"))) {

                logger.info("时代会员联合登录：根据TOKEN获取用户信息失败,json={},Mgs={}", userInfoJson,
                        userInfoJson.getString("Mgs"));
                return false;
            }

        } else {
            logger.info("时代会员联合登录：获取授权TOKEN失败={}", result);
            return false;
        }

        return true;
    }

    private ReturnResult<HashMap<String, Object>> returnResult;

    public ReturnResult<HashMap<String, Object>> getReturnResult() {
        return returnResult;
    }

    public void setReturnResult(ReturnResult<HashMap<String, Object>> returnResult) {
        this.returnResult = returnResult;
    }

}
