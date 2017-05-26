package com.kmzyc.mframework.mobile.jcxg;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

import com.kmzyc.mframework.logger.MessagelLogger;

public class Client {

    private String userName = "";
    private String password = "";
    private String pwd = "";

    private String url = "";

    public Client() {};

    /**
     * 构造函数
     * 
     * @param username
     * @param password
     * @param url
     * @throws UnsupportedEncodingException
     */
    public Client(String username, String password, String url)
            throws UnsupportedEncodingException {
        this.userName = username;
        this.password = password;
        this.pwd = MD5Utils.MD5Encode(username + MD5Utils.MD5Encode(password));
        this.url = url;
    }

    /**
     * 方法名称：mt 功 能：发送短信
     * 
     * @param content 发送内容
     * @param mobile 发送的手机号码，多个手机号为用半角 , 分开
     * @param dstime 定时时间 ，为空时表示立即发送，格式：yyyy-MM-dd HH:mm:ss
     * @param msgid 客户自定义消息ID
     * @param ext 用户自定义扩展
     * @param msgfmt 提交消息编码格式（UTF-8/GBK）置空时默认是UTF-8 返 回 值：若用户自定义消息ID，则返回用户的ID，否则系统随机生成一个任务ID
     * @throws UnsupportedEncodingException
     */
    public String mt(String content, String mobile, String dstime, String msgid, String ext,
            String msgfmt) throws UnsupportedEncodingException {
        String result = "";
        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuilder params = new StringBuilder();
        params.append("username=").append(userName).append("&password=").append(this.getPwd())
                .append("&mobile=").append(mobile).append("&content=").append(content)
                .append("&dstime=").append(dstime).append("&ext=").append(ext).append("&msgid=")
                .append(msgid).append("&msgfmt=").append(msgfmt);
        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF8");
            out.write(params.toString());
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF8"));
            String line = "";
            while ((line = in.readLine()) != null) {
                result += line + "\n";
            }
        } catch (Exception e) {
            MessagelLogger.info("发送异常:" + e.getMessage());
        } finally {
            try {
                if (null != out) {
                    out.close();
                }
                if (null != in) {
                    in.close();
                }
            } catch (IOException e) {
                MessagelLogger.info("关闭流异常:" + e.getMessage());
            }
        }
        return result;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
