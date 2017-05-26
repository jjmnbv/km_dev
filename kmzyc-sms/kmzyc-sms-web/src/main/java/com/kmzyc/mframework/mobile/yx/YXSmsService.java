package com.kmzyc.mframework.mobile.yx;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.km.framework.common.util.MD5;
import com.kmzyc.zkconfig.ConfigurationUtil;

import redis.clients.jedis.JedisCluster;

@Service("yxSmsService")
public class YXSmsService {
  @Resource(name="jedisCluster")
  private JedisCluster jedisCluster;
  private static Logger logger = Logger.getLogger(YXSmsService.class);

  private final static String user = ConfigurationUtil.getString("yx_loginName");
  private final static String passWord = ConfigurationUtil.getString("yx_password");
  private final static String url = ConfigurationUtil.getString("yx_sms_url");

  public String sendMessage(String mobile, String content) {

    JSONObject resultJson = new JSONObject();
    JSONArray datalist = new JSONArray();
    String re = null;
    try {

      resultJson.put("username", user);
      resultJson.put("key", MD5.getMD5Str(user + "#" + passWord));
      String[] mobiles = mobile.split(",");
      JSONObject obj = null;
      for (int i = 0; i < mobiles.length; i++) {
        obj = new JSONObject();
        obj.put("mobile", mobiles[i]);
        obj.put("mid", this.yxCode());
        obj.put("content", content);
        datalist.put(obj);
      }
      resultJson.put("dataList", datalist);
      re = SendSms(resultJson.toString());
    } catch (Exception e) {
      logger.info("发送出错：" + e.getMessage());
    }
    return re;
  }

  public String yxCode() {
    String num = null;
    try {
      if (null == jedisCluster.get("yx_code")) {
        jedisCluster.set("yx_code", "000000001");
        num = "000000001";
      } else {
        num = jedisCluster.get("yx_code");
        int n = Integer.parseInt(num) + 1;
        String number = n + "";
        int s = number.length();
        for (int i = 0; i < 9 - s; i++) {
          number = "0" + number;
        }
        jedisCluster.set("yx_code", number);
      }
    } catch (Exception e) {
      logger.error("发送出错：",e);
    }
    return num;
  }

  public static String SendSms(String jsondata) {
    String gcl = "";
    Map map = new HashMap();
    if (jsondata == null || jsondata.equals("")) {
      map.put("code", "2001");
      map.put("msg", "打包数据有误");
      logger.info("打包数据有误");
      return null;
    }
    try {
      jsondata = java.net.URLEncoder.encode(jsondata, "utf-8");
      System.out.println(jsondata);

    } catch (UnsupportedEncodingException e1) {
      e1.printStackTrace();
    }
    HttpClient httpclient = HttpClients.createDefault();
    HttpPost postMethod = new HttpPost(url);


    NameValuePair[] data =
        {new BasicNameValuePair("paramStr", jsondata), new BasicNameValuePair("fmt", "json")};
    UrlEncodedFormEntity httpEntity=new UrlEncodedFormEntity(Arrays.asList(data), UTF_8);
    postMethod.setEntity(httpEntity);
    try {
      HttpResponse response = httpclient.execute(postMethod);
      HttpEntity entity = response.getEntity();
      if(entity!=null){
        gcl =EntityUtils.toString(entity,UTF_8);
      }
//      InputStream resStream = postMethod.getResponseBodyAsStream();
//      if (postMethod.getStatusCode() == 200) {
//        BufferedReader br = new BufferedReader(new InputStreamReader(resStream, "utf-8"));
//        StringBuffer resBuffer = new StringBuffer();
//        String resTemp = "";
//        while ((resTemp = br.readLine()) != null) {
//          resBuffer.append(resTemp);
//        }
//        gcl = resBuffer.toString();
//      } else {
//        logger.info("S001:服务器异常,返回：" + postMethod.getStatusCode());
//      }
    } catch (Exception e) {
      logger.info("悦信发送错误Exception=" + e.getMessage());
      gcl = "";
    }
    return gcl;
  }

}
