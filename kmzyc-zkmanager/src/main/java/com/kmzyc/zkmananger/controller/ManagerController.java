package com.kmzyc.zkmananger.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.km.commons.config.core.ConfigurationUtil;
import com.kmzyc.zkmananger.common.Permission;

@Controller("managerController")
@RequestMapping("index")
public class ManagerController {
    private static final String urip = "/";
    static final String charsetName = "utf-8";
    private final Logger logger = LoggerFactory.getLogger(ManagerController.class);

    @Autowired
    private CuratorFramework zkClient;

    @RequestMapping(value = "/**")
    public ModelAndView toindex(HttpServletRequest request, HttpServletResponse response) {


        ModelAndView mav = null;

        try {
            String path = getPath(request);
            Stat stat = zkClient.checkExists().forPath(path);
            if (stat != null) {
                mav = new ModelAndView("manager");
                byte[] btys = zkClient.getData().forPath(path);
                if (btys != null) {
                    String data = new String(btys, "utf-8");
                    mav.addObject("dataValue", data);
                }
                List<String> childerNodes = zkClient.getChildren().forPath(path);
                mav.addObject("path", path);
                mav.addObject("childerNodes", childerNodes);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("", e);
        }
        return mav;

    }


    private String getPath(HttpServletRequest request) {
        String uri = null;
        try {
            uri = URLDecoder.decode(request.getRequestURI(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("获取路径错误", e);
        }
        uri = uri.substring(uri.indexOf("/index") + 6, uri.length());
        while (uri.endsWith("/")) {
            uri = uri.substring(0, uri.length() - 1);
        }
        return uri;
    }


    @RequestMapping(value = "/add", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @Permission(login = true)
    public String add(HttpServletRequest request, HttpServletResponse response) {
        String path = getParameterByUtf8("path", request);
        String nodeName = getParameterByUtf8("nodeName", request);
        JSONObject json = new JSONObject();
        Stat stat = null;
        String newPath = path + urip + nodeName;
        try {
            stat = zkClient.checkExists().forPath(newPath);
            if (stat != null) {
                json.put("code", "directoryExists");// 路径已经存在
            } else {
                String creatRe = zkClient.create().forPath(newPath);
                json.put("code", "success");// 路径已经存在
                json.put("creatRe", creatRe);// 路径已经存在
                // System.out.println(creatRe);
            }
        } catch (Exception e) {
            // e.printStackTrace();
            logger.error("", e);
            json.put("code", "exception");// 异常
        }
        return json.toJSONString();
    }

    @RequestMapping(value = "/out", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @Permission(login = true)
    public String out(HttpServletRequest request, HttpServletResponse response) {
        String path = getParameterByUtf8("path", request);
        // 导出目录
        String url = getParameterByUtf8("url", request);
        JSONObject json = new JSONObject();
        // 校验路径格式
        if (url == null || !url.startsWith("/") || url.indexOf("\\") > 0) {
            json.put("code", "urlFormatError"); // 路径格式不正确
            return json.toJSONString();
        }
        if (url.lastIndexOf("/") == url.length() - 1) {
            url = url.substring(0, url.length() - 1);
        }
        Stat stat = null;
        try {
            stat = zkClient.checkExists().forPath(url);
            if (stat != null) {
                SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd-HHmmss");
                // 文件导出路径
                String exportUrl = ConfigurationUtil.getString("exportUrl");
                String newUrl = exportUrl + dateFormater.format(new Date()) + url;
                boolean result = copyFolder(url, newUrl);
                if (result) {
                    json.put("code", "success"); // 导出成功
                } else {
                    json.put("code", "outError"); // 导出失败
                }
            } else {
                json.put("code", "noUrl");// 路径已经存在
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("", e);
            json.put("code", "exception");// 异常
        }
        return json.toJSONString();
    }

    /**
     * 从oldPath导出目录至newPath add by songmiao
     *
     * @param oldPath 目录原地址
     * @param newPath 导出地址
     */
    @SuppressWarnings("finally")
    public boolean copyFolder(String oldPath, String newPath) {
        boolean resultFlag = false;
        try {
            int index = oldPath.lastIndexOf(".");
            String fileFormat = ConfigurationUtil.getString("fileFormat");
            String oldPathFormat = oldPath.substring((index + 1), oldPath.length());

            if (fileFormat.indexOf(oldPathFormat) < 0) {
                // 如果文件夹不存在，则建立新文件夹
                (new File(newPath)).mkdirs();
                // 读取整个文件夹的内容到file字符串数组，下面设置一个游标i，不停地向下移开始读这个数组
                List<String> file = zkClient.getChildren().forPath(oldPath);
                // 临时文件指针
                String temp = "";
                for (int i = 0; i < file.size(); i++) {
                    // 检查是否以“/”结尾
                    if (oldPath.endsWith("/")) {
                        temp = oldPath + file.get(i);
                    } else {
                        temp = oldPath + "/" + file.get(i);
                    }
                    int one = temp.toString().lastIndexOf(".");
                    String format = temp.toString().substring((one + 1), temp.toString().length());
                    // 如果游标遇到文件夹
                    if (one < 0 || fileFormat.indexOf(format) < 0) {
                        copyFolder(oldPath + "/" + file.get(i), newPath + "/" + file.get(i));
                    } else {
                        FileOutputStream output = null;
                        try {
                            output = new FileOutputStream(newPath + "/" + file.get(i));
                            // 读取文件
                            byte[] btys = zkClient.getData().forPath(temp);
                            // 将文件写入输出流
                            output.write(btys);
                            output.flush();
                        } finally {
                            if (output != null) output.close();
                        }

                    }
                }
                resultFlag = true;
            } else { // 路径为文件
                int indexN = newPath.lastIndexOf("/");
                String fileUrl = newPath.substring(0, indexN);
                (new File(fileUrl)).mkdirs();
                FileOutputStream output = new FileOutputStream(newPath);
                String path = oldPath.replace("\\", "/");
                // 读取文件
                byte[] btys = zkClient.getData().forPath(path);
                // 将文件写入输出流
                output.write(btys);
                output.flush();
                output.close();
                resultFlag = true;
            }

        } catch (Exception e) {
            resultFlag = false;
            e.printStackTrace();
            logger.error("", e);
        }
        return resultFlag;
    }

    @RequestMapping(value = "/del", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @Permission(login = true)
    public String del(HttpServletRequest request, HttpServletResponse response) {
        String path = getParameterByUtf8("path", request);
        JSONObject json = new JSONObject();
        try {
            zkClient.delete().forPath(path);
            json.put("code", "success");// 路径已经存在
        } catch (KeeperException.NotEmptyException e) {
            json.put("code", "directoryNotEmpty");// 目录不为空
            logger.warn("directoryNotEmpty", e);
        } catch (Exception e) {
            json.put("code", "exception");// 异常
            logger.error("", e);
        }
        return json.toJSONString();
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    @Permission(login = true)
    public String edit(HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        try {
            String path = getParameterByUtf8("path", request);
            String nodeCont = getParameterByUtf8("nodeCont", request);
            zkClient.setData().forPath(path, nodeCont.getBytes(charsetName));
            json.put("code", "success");
        } catch (Exception e) {
            e.printStackTrace();
            json.put("code", "exception");// 异常
            logger.error("", e);
        }
        return json.toJSONString();
    }


    @RequestMapping(value = "/login", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String login(HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        try {
            String username = getParameterByUtf8("username", request);
            String password = getParameterByUtf8("password", request);
            if (username.equals(ConfigurationUtil.getString("username"))
                    && password.equals(ConfigurationUtil.getString("password"))) {
                json.put("code", "success");
                request.getSession().setAttribute("loginName", username);
            } else {
                json.put("code", "fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("", e);
            json.put("code", "exception");// 异常
        }
        return json.toJSONString();
    }

    /**
     * 根据参数名获取utf-8格式的参数 add by songmiao 2016-1-6
     */
    public String getParameterByUtf8(String parameterName, HttpServletRequest request) {
        String parameter = null;
        try {
            parameter = decode(request.getParameter(parameterName), "utf-8");

        } catch (UnsupportedEncodingException e) {
            logger.error("获取参数异常", e);
            e.printStackTrace();
        }
        return parameter;

    }

    public static String decode(String s, String enc) throws UnsupportedEncodingException {

        boolean needToChange = false;
        int numChars = s.length();
        StringBuffer sb = new StringBuffer(numChars > 500 ? numChars / 2 : numChars);
        int i = 0;

        if (enc.length() == 0) {
            throw new UnsupportedEncodingException("URLDecoder: empty string enc parameter");
        }

        char c;
        byte[] bytes = null;
        while (i < numChars) {
            c = s.charAt(i);
            switch (c) {
                case '%':
                    try {
                        if (bytes == null) bytes = new byte[(numChars - i) / 3];
                        int pos = 0;

                        while (((i + 2) < numChars) && (c == '%')) {
                            int v = Integer.parseInt(s.substring(i + 1, i + 3), 16);
                            if (v < 0)
                                throw new IllegalArgumentException(
                                        "URLDecoder: Illegal hex characters in escape (%) pattern - negative value");
                            bytes[pos++] = (byte) v;
                            i += 3;
                            if (i < numChars) c = s.charAt(i);
                        }

                        if ((i < numChars) && (c == '%'))
                            throw new IllegalArgumentException(
                                    "URLDecoder: Incomplete trailing escape (%) pattern");

                        sb.append(new String(bytes, 0, pos, enc));
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException(
                                "URLDecoder: Illegal hex characters in escape (%) pattern - "
                                        + e.getMessage());
                    }
                    needToChange = true;
                    break;
                default:
                    sb.append(c);
                    i++;
                    break;
            }
        }

        return (needToChange ? sb.toString() : s);
    }
}
