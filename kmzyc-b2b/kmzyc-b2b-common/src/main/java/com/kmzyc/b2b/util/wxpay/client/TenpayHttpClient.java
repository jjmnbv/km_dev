package com.kmzyc.b2b.util.wxpay.client;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

import com.kmzyc.b2b.util.pay.XmlConverUtil;
import com.kmzyc.b2b.util.wxpay.HttpClientUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;

/**
 * 财付通http或者https网络通信客户端
 * 
 * @author xlg
 * 
 */
public class TenpayHttpClient {

    private static final String USER_AGENT_VALUE = "Mozilla/4.0 (compatible; MSIE 6.0; Windows XP)";

    /**
     * jks别名
     */
    private String jksAlias;

    /**
     * jks密码
     */
    private String jksPassword = "";

    /**
     * ca证书文件
     */
    private File caFile;

    /**
     * 证书文件
     */
    private File certFile;

    /**
     * jks文件
     */
    private File jksFile;

    /** 证书密码 */
    private String certPasswd;

    /** 请求内容，无论post和get，都用get方式提供 */
    private String reqContent;

    /** 应答内容 */
    private String resContent;

    /** 请求方法 */
    private String method;

    /** 错误信息 */
    private String errInfo;

    /** 超时时间,以秒为单位 */
    private int timeOut;

    /** http应答编码 */
    private int responseCode;

    /** 字符编码 */
    private String charset;

    private InputStream inputStream;
    /** 是否是微信支付 */
    private boolean isWxPay;
    /** 微信支付MAP */
    private Map<String, String> reParams;

    /**
     * 1财付通、2微信
     * 
     * @param source
     */
    public TenpayHttpClient(int source) {
        if (1 == source) {
            this.reqContent = "";
            this.resContent = "";
            this.method = "POST";
            this.errInfo = "";
            this.timeOut = 30;// 30秒
            this.responseCode = 0;
            this.inputStream = null;
            this.jksAlias = "tenpay";
            this.caFile =
                    new File(getClass().getResource("/").getPath() + "//config//pay//tenPay.pem");
            this.certFile =
                    new File(getClass().getResource("/").getPath() + "//config//pay//tenPay.pfx");
            this.jksFile =
                    new File(getClass().getResource("/").getPath() + "//config//pay//tenPay.jks");
            this.certPasswd = ConfigurationUtil.getString("ten_pay_partner");
            this.charset = ConfigurationUtil.getString("ten_pay_input_charset");
        } else if (2 == source) {
            this.reqContent = "";
            this.resContent = "";
            this.method = "POST";
            this.errInfo = "";
            this.timeOut = 30;// 30秒
            this.responseCode = 0;
            this.inputStream = null;
            this.jksAlias = "tenpay";
            this.jksPassword = "";
            this.caFile =
                    new File(getClass().getResource("/").getPath() + "//config//pay//wxPay.pem");
            this.certFile =
                    new File(getClass().getResource("/").getPath() + "//config//pay//wxPay.p12");
            this.jksFile =
                    new File(getClass().getResource("/").getPath() + "//config//pay//wxPay.jks");
            this.certPasswd = ConfigurationUtil.getString("wx_pay_partner");
            this.charset = ConfigurationUtil.getString("wx_pay_input_charset");
            this.isWxPay = true;
        }
    }

    /**
     * 
     * @return
     */
    public String getResContent() {
        try {
            this.doResponse();
        } catch (IOException e) {
            this.errInfo = e.getMessage();
        }
        return this.resContent;
    }

    /**
     * 执行http调用。true:成功 false:失败
     * 
     * @return boolean
     */
    public boolean call() {
        boolean isRet = false;
        // http
        if (null == this.caFile && null == this.certFile) {
            try {
                this.callHttp();
                isRet = true;
            } catch (IOException e) {
                this.errInfo = e.getMessage();
            }
            return isRet;
        }

        // https
        try {
            this.callHttps();
            isRet = true;
        } catch (UnrecoverableKeyException e) {
            this.errInfo = e.getMessage();
        } catch (KeyManagementException e) {
            this.errInfo = e.getMessage();
        } catch (CertificateException e) {
            this.errInfo = e.getMessage();
        } catch (KeyStoreException e) {
            this.errInfo = e.getMessage();
        } catch (NoSuchAlgorithmException e) {
            this.errInfo = e.getMessage();
        } catch (IOException e) {
            this.errInfo = e.getMessage();
        }
        return isRet;
    }

    protected void callHttp() throws IOException {
        if ("POST".equals(this.method.toUpperCase())) {
            String url = HttpClientUtil.getURL(this.reqContent);
            String queryString = null;
            if (isWxPay) {
                queryString = XmlConverUtil.maptoXml(reParams);
            } else {
                queryString = HttpClientUtil.getQueryString(this.reqContent);
            }
            byte[] postData = queryString.getBytes(this.charset);
            this.httpPostMethod(url, postData);
            return;
        }
        this.httpGetMethod(this.reqContent);
    }

    protected void callHttps() throws IOException, CertificateException, KeyStoreException,
            NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException {
        if (!jksFile.isFile()) {
            X509Certificate cert = (X509Certificate) HttpClientUtil.getCertificate(this.caFile);
            FileOutputStream out = new FileOutputStream(jksFile);
            // store jks file
            HttpClientUtil.storeCACert(cert, this.jksAlias, this.jksPassword, out);
            out.close();
        }

        FileInputStream trustStream = new FileInputStream(jksFile);
        FileInputStream keyStream = new FileInputStream(this.certFile);
        SSLContext sslContext =
                HttpClientUtil.getSSLContext(trustStream, this.jksPassword, keyStream,
                        this.certPasswd);

        // 关闭流
        keyStream.close();
        trustStream.close();
        if ("POST".equals(this.method.toUpperCase())) {
            String url = HttpClientUtil.getURL(this.reqContent);
            String queryString = null;
            byte[] postData = null;
            if (isWxPay) {
                queryString = XmlConverUtil.maptoXml(reParams);
            } else {
                queryString = HttpClientUtil.getQueryString(this.reqContent);
            }
            postData = queryString.getBytes(this.charset);
            this.httpsPostMethod(url, postData, sslContext);
            return;
        }
        this.httpsGetMethod(this.reqContent, sslContext);
    }

    /**
     * 以http post方式通信
     * 
     * @param url
     * @param postData
     * @throws IOException
     */
    protected void httpPostMethod(String url, byte[] postData) throws IOException {
        HttpURLConnection conn = HttpClientUtil.getHttpURLConnection(url);
        this.doPost(conn, postData);
    }

    /**
     * 以http get方式通信
     * 
     * @param url
     * @throws IOException
     */
    protected void httpGetMethod(String url) throws IOException {
        HttpURLConnection httpConnection = HttpClientUtil.getHttpURLConnection(url);
        this.setHttpRequest(httpConnection);
        httpConnection.setRequestMethod("GET");
        this.responseCode = httpConnection.getResponseCode();
        this.inputStream = httpConnection.getInputStream();
    }

    /**
     * 以https get方式通信
     * 
     * @param url
     * @param sslContext
     * @throws IOException
     */
    protected void httpsGetMethod(String url, SSLContext sslContext) throws IOException {
        SSLSocketFactory sf = sslContext.getSocketFactory();
        HttpsURLConnection conn = HttpClientUtil.getHttpsURLConnection(url);
        conn.setSSLSocketFactory(sf);
        this.doGet(conn);
    }

    /**
     * 以https post方式通信
     * 
     * @param url
     * @param sslContext
     * @throws IOException
     */
    protected void httpsPostMethod(String url, byte[] postData, SSLContext sslContext)
            throws IOException {
        SSLSocketFactory sf = sslContext.getSocketFactory();
        HttpsURLConnection conn = HttpClientUtil.getHttpsURLConnection(url);
        conn.setSSLSocketFactory(sf);
        this.doPost(conn, postData);
    }

    /**
     * 设置http请求默认属性
     * 
     * @param httpConnection
     */
    protected void setHttpRequest(HttpURLConnection httpConnection) {
        httpConnection.setConnectTimeout(this.timeOut * 1000);
        httpConnection.setRequestProperty("User-Agent", TenpayHttpClient.USER_AGENT_VALUE);
        httpConnection.setUseCaches(false);
        httpConnection.setDoInput(true);
        httpConnection.setDoOutput(true);

    }

    /**
     * 处理应答
     * 
     * @throws IOException
     */
    protected void doResponse() throws IOException {
        if (null == this.inputStream) {
            return;
        }
        this.resContent = HttpClientUtil.inputStreamTOString(this.inputStream, this.charset);
        this.inputStream.close();
    }

    /**
     * post请求
     * 
     * @param conn
     * @param postData
     * @throws IOException
     */
    protected void doPost(HttpURLConnection conn, byte[] postData) throws IOException {
        conn.setRequestMethod("POST");
        this.setHttpRequest(conn);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        BufferedOutputStream out = new BufferedOutputStream(conn.getOutputStream());
        final int len = 1024; // 1KB
        HttpClientUtil.doOutput(out, postData, len);
        out.close();
        this.responseCode = conn.getResponseCode();
        this.inputStream = conn.getInputStream();
    }

    /**
     * get请求
     * 
     * @param conn
     * @throws IOException
     */
    protected void doGet(HttpURLConnection conn) throws IOException {
        conn.setRequestMethod("GET");
        this.setHttpRequest(conn);
        this.responseCode = conn.getResponseCode();
        this.inputStream = conn.getInputStream();
    }

    /**
     * 请求内容
     * 
     * @param reqContent 表求内容
     */
    public void setReqContent(String reqContent) {
        this.reqContent = reqContent;
    }

    /**
     * 错误信息
     * 
     * @return
     */
    public String getErrInfo() {
        return errInfo;
    }

    /**
     * 应答编码
     * 
     * @return
     */
    public int getResponseCode() {
        return responseCode;
    }

    public Map<String, String> getReParams() {
        return reParams;
    }

    public void setReParams(Map<String, String> reParams) {
        this.reParams = reParams;
    }
}
