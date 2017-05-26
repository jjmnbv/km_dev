package com.kmzyc.mframework.mobile.hb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;

import com.kmzyc.zkconfig.ConfigurationUtil;

public class HBSmsService {

    private static String hexString = "0123456789ABCDEF";
    private final static String user = ConfigurationUtil.getString("hb_loginName");
    private final static String passWord = ConfigurationUtil.getString("hb_password");
    private final static String url = ConfigurationUtil.getString("hb_sms_url");


    public static String sendMessage(String phone, String content) {

        String rs = "";
        try {
            content = encode(content);
            String ul = url + "un=" + user + "&pw=" + passWord + "&da=" + phone + "&sm=" + content
                    + "&dc=15&rd=1";
            URL urlclass = new URL(ul);
            try {
                URLConnection connection = urlclass.openConnection();
                BufferedReader in =
                        new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuffer sb = new StringBuffer();
                String line = null;
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }
                rs = sb.toString();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public static String encode(String str) throws UnsupportedEncodingException {
        // 根据默认编码获取字节数组
        byte[] bytes = str.getBytes("GB2312");
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        // 将字节数组中每个字节拆解成2位16进制整数
        for (int i = 0; i < bytes.length; i++) {
            sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
            sb.append(hexString.charAt((bytes[i] & 0x0f)));
        }
        return sb.toString();
    }

}
