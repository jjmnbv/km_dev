package com.kmzyc.mframework.mobile.jxt;

/**
 * 吉信通短信发送service
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.kmzyc.mframework.logger.MessagelLogger;
import com.kmzyc.zkconfig.ConfigurationUtil;

public class JXTSmsService {
    private Logger logger = Logger.getLogger(JXTSmsService.class);

    private final static String userName = ConfigurationUtil.getString("jxt_userName");
    private final static String password = ConfigurationUtil.getString("jxt_password");
    private final static String smsurl = ConfigurationUtil.getString("jxt_sms_url");


    private String getSoapSmssend(String userid, String pass, String mobiles, String msg,
            String time) {
        try {
            logger.info("getSoapSmssend方法中发送的内容为：" + msg);
            String soap = "";
            soap = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                    + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                    + "<soap:Body>" + "<SendMessages xmlns=\"http://tempuri.org/\">" + "<uid>"
                    + userid + "</uid>" + "<pwd>" + pass + "</pwd>" + "<tos>" + mobiles + "</tos>"
                    + "<msg>" + msg + "</msg>" + "<otime>" + time + "</otime>" + "</SendMessages>"
                    + "</soap:Body>" + "</soap:Envelope>";
            return soap;
        } catch (Exception ex) {
            logger.error("连接超时" + ex.getMessage(), ex);
            return null;
        }
    }

    private InputStream getSoapInputStream(String userid, String pass, String mobiles, String msg,
            String time) throws Exception {
        URLConnection conn = null;
        InputStream is = null;
        try {
            String soap = getSoapSmssend(userid, pass, mobiles, msg, time);
            if (soap == null) {
                return null;
            }
            try {
                URL url = new URL(smsurl);
                conn = url.openConnection();
                conn.setUseCaches(false);
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Length", Integer.toString(soap.length()));
                conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
                conn.setRequestProperty("HOST", "service2.winic.org");
                conn.setRequestProperty("SOAPAction", "\"http://tempuri.org/SendMessages\"");

                OutputStream os = conn.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os, "utf-8");
                osw.write(soap);
                osw.flush();
            } catch (Exception ex) {
                logger.error("SmsSoap.openUrl error:" + ex.getMessage(), ex);
                throw ex;
            }
            try {
                is = conn.getInputStream();
            } catch (Exception ex1) {
                logger.error("SmsSoap.getUrl error:" + ex1.getMessage(), ex1);
                throw ex1;
            }

        } catch (Exception e) {
            logger.error("SmsSoap.InputStream error:" + e.getMessage(), e);
            throw e;
        }
        return is;
    }

    /**
     * 发送短信
     * 
     * @param mobiles
     * @param msg
     * @param time
     * @return
     */
    public String sendSms(String mobiles, String msg, String time) {
        MessagelLogger.info("开始发送短信通道为：【" + userName + "】手机为【" + mobiles + "】内容为【" + msg + "】");
        String result = "-12";
        InputStream is = null;
        try {
            Document doc;
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            is = getSoapInputStream(userName, password, mobiles, msg, time);
            if (is != null) {
                doc = db.parse(is);
                NodeList nl = doc.getElementsByTagName("SendMessagesResult");
                Node n = nl.item(0);
                result = n.getFirstChild().getNodeValue();
                MessagelLogger.info("发送短信到手机：【" + mobiles + "】的返回码为【" + result + "】");
            }
        } catch (Exception e) {
            result = "-12";
            MessagelLogger.error("短信验证发送错误:" + e.getMessage(), e);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                logger.error(e.getStackTrace(), e);
            }
        }
        return result;
    }
}
