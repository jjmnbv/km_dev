package com.kmzyc.b2b.action;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.framework.action.BaseAction;
import com.kmzyc.zkconfig.ConfigurationUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import sun.misc.BASE64Decoder;

@Controller("messageReturnAction")
@Scope("prototype")
public class MessageReturnAction extends BaseAction {

    /**
     * 推送格式：url? msgid=&cee=&res=&recvt=&note= 其中：msgid为消息编号，cee为发送号码，res为发送结果,note为状态备注，recvt为接收时间。
     */
    public String report, args, hb;

    /**
     * op=dr&id=128774299606789377&ex=150314091841209609&sa=18385408369&su=MK:0005&sd=20150315080000
     * & dd=20150316041500&bi=3&di=1&rp=5
     */
    public String op, dc, pi, ec, sa, da, mu, sm, id, ex, su, sd, dd, rp, bi, di;

    private static Logger logger = LoggerFactory.getLogger(MessageReturnAction.class);

    // private Logger logger = Logger.getLogger(MessageReturnAction.class);
    /**
     * http post方式提交
     */
    // private String HttpURL = ConfigurationUtil.getString("message_Return").toString();


    public void messageReturn() {
        try {
            String HttpURL = ConfigurationUtil.getString("message_Return").toString();
            logger.info("短信邮件地址：" + HttpURL);
            logger.info("mdkj返回消息编号args为:" + args);
            logger.info("jcxg返回消息编号report为:" + report);
            logger.info("hb返回消息编号id为:" + id);
            OutputStreamWriter out = null;
            HttpURL = HttpURL + "messageReturn.action?r=" + System.currentTimeMillis();
            // HttpURL = HttpURL + "messageReturn.action";
            if (report != null) { // 君诚
                report = java.net.URLEncoder.encode(report, "utf-8");
                HttpURL = HttpURL + "&report=" + report;
            } else if (args != null) {// 漫道
                args = java.net.URLEncoder.encode(args, "utf-8");
                HttpURL = HttpURL + "&args=" + args;
            } else if (id != null) {// 昊博
                hb = op + "|" + dc + "|" + pi + "|" + ec + "|" + sa + "|" + da + "|" + mu + "|" + sm
                        + "|" + id + "|" + ex + "|" + su + "|" + sd + "|" + dd + "|" + rp + "|" + bi
                        + "|" + di;
                hb = java.net.URLEncoder.encode(hb, "utf-8");
                HttpURL = HttpURL + "&hb=" + hb;
            }

            if (args != null || report != null || id != null) {
                logger.info("最终转发地址：" + HttpURL);
                URL realUrl = new URL(HttpURL);
                /* + java.net.URLEncoder.encode(report, "utf-8")); */
                URLConnection conn = realUrl.openConnection();
                conn.setRequestProperty("accept", "*/*");
                conn.setRequestProperty("connection", "Keep-Alive");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                out = new OutputStreamWriter(conn.getOutputStream(), "UTF8");
                /* out.write(params.toString()); */
                conn.getInputStream();
                out.flush();
                out.close();
                getResponse().getWriter().print(0);
                logger.info("b2b转发成功 ");
            } else {
                logger.info("参数都为NULLb2b不转发 ");
            }
        } catch (Exception e) {
            logger.info("b2b转发短信邮件出错：" + e.getMessage());
        }
    }



    public void yxMessageReturn() {
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            int len = request.getContentLength();
            logger.info("数据流长度---:" + len);
            // 获取HTTP请求的输入流
            if (len == -1) {
            } else {
                InputStream is = request.getInputStream();
                Document document = new SAXReader().read(is);
                Element root = document.getRootElement();

                Object obj = parse(root); // 返回类型未知，已知DOM结构的时候可以强制转换
                JSONObject json = JSONObject.fromObject(obj);
                JSONArray jsonArr = new JSONArray();
                if ("net.sf.json.JSONObject".equals(json.get("msg").getClass().getName())) {
                    jsonArr.add(json.get("msg"));
                } else {
                    jsonArr = json.getJSONArray("msg");
                }
                StringBuilder yxParam = new StringBuilder();
                String yxParams;
                String p1;
                String p2;
                String p3;
                String p4;
                String p5;
                for (int i = 0; i < jsonArr.size(); i++) {
                    JSONObject jo = (JSONObject) jsonArr.get(i);
                    if (i == 0) {
                        p1 = (String) jo.get("param1");
                        p2 = getFromBASE64((String) jo.get("param2"));
                        p3 = (String) jo.get("param3");
                        p4 = (String) jo.get("param4");
                        p5 = (String) jo.get("param5");
                        yxParams = p1 + "|" + p2 + "|" + p3 + "|" + p4 + "|" + p5;
                        yxParam.append(yxParams);
                    } else {
                        p1 = (String) jo.get("param1");
                        p2 = getFromBASE64((String) jo.get("param2"));
                        p3 = (String) jo.get("param3");
                        p4 = (String) jo.get("param4");
                        p5 = (String) jo.get("param5");
                        yxParams = p1 + "|" + p2 + "|" + p3 + "|" + p4 + "|" + p5;
                        yxParam.append(";" + yxParams.toString());
                    }
                }
                String HttpURL = ConfigurationUtil.getString("message_Return").toString();
                HttpURL = HttpURL + "messageReturn.action?r=" + System.currentTimeMillis();
                String yxP = java.net.URLEncoder.encode(yxParam.toString(), "utf-8");
                HttpURL = HttpURL + "&yxParam=" + yxP;
                logger.info("悦信参数为：" + yxP);
                OutputStreamWriter out = null;
                URL realUrl = new URL(HttpURL);
                URLConnection conn = realUrl.openConnection();
                conn.setRequestProperty("accept", "*/*");
                conn.setRequestProperty("connection", "Keep-Alive");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                out = new OutputStreamWriter(conn.getOutputStream(), "UTF8");
                conn.getInputStream();
                out.flush();
                out.close();
                getResponse().getWriter().print("OK");
                logger.info("b2b转发成功 ");
            }
        } catch (Exception e) {
            logger.info("转发失败 " + e.getMessage());
        }
    }

    public static Object parse(Element root) {
        List<?> elements = root.elements();
        if (elements.size() == 0) {
            // 没有子元素
            return root.getTextTrim();
        } else {
            // 有子元素
            String prev = null;
            boolean guess = true; // 默认按照数组处理

            Iterator<?> iterator = elements.iterator();
            while (iterator.hasNext()) {
                Element elem = (Element) iterator.next();
                String name = elem.getName();
                if (prev == null) {
                    prev = name;
                } else {
                    guess = name.equals(prev);
                    break;
                }
            }
            iterator = elements.iterator();
            if (guess) {
                List<Object> data = new ArrayList<Object>();
                while (iterator.hasNext()) {
                    Element elem = (Element) iterator.next();
                    data.add(parse(elem));
                }
                return data;
            } else {
                Map<String, Object> data = new HashMap<String, Object>();
                while (iterator.hasNext()) {
                    Element elem = (Element) iterator.next();
                    data.put(elem.getName(), parse(elem));
                }
                return data;
            }
        }
    }


    public String getFromBASE64(String s) {
        if (s == null)
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(s);
            return new String(b, "utf-8");
        } catch (Exception e) {
            return null;
        }
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }


    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getDc() {
        return dc;
    }

    public void setDc(String dc) {
        this.dc = dc;
    }

    public String getPi() {
        return pi;
    }

    public void setPi(String pi) {
        this.pi = pi;
    }

    public String getEc() {
        return ec;
    }

    public void setEc(String ec) {
        this.ec = ec;
    }

    public String getSa() {
        return sa;
    }

    public void setSa(String sa) {
        this.sa = sa;
    }

    public String getDa() {
        return da;
    }

    public void setDa(String da) {
        this.da = da;
    }

    public String getMu() {
        return mu;
    }

    public void setMu(String mu) {
        this.mu = mu;
    }

    public String getSm() {
        return sm;
    }

    public void setSm(String sm) {
        this.sm = sm;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEx() {
        return ex;
    }

    public void setEx(String ex) {
        this.ex = ex;
    }

    public String getSu() {
        return su;
    }

    public void setSu(String su) {
        this.su = su;
    }

    public String getSd() {
        return sd;
    }

    public void setSd(String sd) {
        this.sd = sd;
    }

    public String getDd() {
        return dd;
    }

    public void setDd(String dd) {
        this.dd = dd;
    }

    public String getRp() {
        return rp;
    }

    public void setRp(String rp) {
        this.rp = rp;
    }

    public String getBi() {
        return bi;
    }

    public void setBi(String bi) {
        this.bi = bi;
    }

    public String getDi() {
        return di;
    }

    public void setDi(String di) {
        this.di = di;
    }

    public String getHb() {
        return hb;
    }

    public void setHb(String hb) {
        this.hb = hb;
    }


}
