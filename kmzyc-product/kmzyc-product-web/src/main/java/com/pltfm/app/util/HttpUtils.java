package com.pltfm.app.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.pltfm.app.fobject.ShopBrowseInfo;

import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;


public class HttpUtils {

    private static String proxyHost = null;
    private static Integer proxyPort = null;
	
	private static final String URL_PARAM_CONNECT_FLAG = "&";
	private static final int SIZE = 1024 * 1024;

	private HttpUtils() {
	}
	
	/**
	 * 生成URL
	 * 
	 * @param map
	 * @return
	 */
	private static String getUrl(Map map) {
		if (null == map || map.keySet().size() == 0) {
			return ("");
		}
		StringBuffer url = new StringBuffer();
		Set keys = map.keySet();
		for (Iterator i = keys.iterator(); i.hasNext();) {
			String key = String.valueOf(i.next());
			if (map.containsKey(key)) {
				Object val = map.get(key);
				String str = val != null ? val.toString() : "";
				try {
					str = URLEncoder.encode(str, "GBK");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				url.append(key).append("=").append(str).append(URL_PARAM_CONNECT_FLAG);
			}
		}
		String strURL = "";
		strURL = url.toString();
		if (URL_PARAM_CONNECT_FLAG.equals("" + strURL.charAt(strURL.length() - 1))) {
			strURL = strURL.substring(0, strURL.length() - 1);
		}
		return (strURL);
	}

	/**
     * Do POST request
     * @param url
     * @param parameterMap
     * @return
     * @throws Exception 
     */
    public static JSONObject doPost(String url, Map<String, Object> parameterMap,String charSet) throws Exception {
        String parameter = "";
        if (parameterMap != null) {
            List<String> paramList = parameterMap.entrySet().stream()
                    .map(entry -> entry.getKey() + "=" + entry.getValue() != null ? entry.getValue().toString() : "")
                    .collect(Collectors.toList());
            parameter = StringUtils.join(paramList, "&");
        }
        
        System.out.println("POST parameter : " + parameter);
        
        URL localURL = new URL(url);
        
        URLConnection connection = openConnection(localURL);
        HttpURLConnection httpURLConnection = (HttpURLConnection)connection;
        
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Accept-Charset", charSet);
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;chartset=utf-8");
        httpURLConnection.setRequestProperty("Content-Length", String.valueOf(parameter.length()));
        
        OutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;
        
        try {
            outputStream = httpURLConnection.getOutputStream();
            outputStreamWriter = new OutputStreamWriter(outputStream, "utf-8");
            
            outputStreamWriter.write(parameter.toString());
            outputStreamWriter.flush();
            
            if (httpURLConnection.getResponseCode() >= 300) {
                throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
            }
            
            inputStream = httpURLConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            reader = new BufferedReader(inputStreamReader);
            
            while ((tempLine = reader.readLine()) != null) {
                resultBuffer.append(tempLine);
            }
            return JSONObject.fromObject(resultBuffer.toString());
        } finally {
            if (outputStreamWriter != null) {
                outputStreamWriter.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            if (reader != null) {
                reader.close();
            }
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    private static URLConnection openConnection(URL localURL) throws IOException {
        URLConnection connection;
        if (proxyHost != null && proxyPort != null) {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
            connection = localURL.openConnection(proxy);
        } else {
            connection = localURL.openConnection();
        }
        return connection;
    }
	
	public String getProxyHost() {
		return proxyHost;
	}


	public Integer getProxyPort() {
		return proxyPort;
	}

}