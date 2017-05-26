package com.kmzyc.framework.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.kmzyc.zkconfig.ConfigurationUtil;

@Component
public class LoginFilterMap {
    static Logger logger = LoggerFactory.getLogger(LoginFilterMap.class);
    private static List<String> appUnLoginCheckList = new ArrayList<String>();

    private static Map<String, String> map = new HashMap<String, String>();

    public static Map<String, String> getMap() {
        if (map == null) {
            map = new HashMap<String, String>();
        }
        return map;
    }

    public static List<String> getAppUnLoginCheckList() {
        if (null == appUnLoginCheckList) {
            appUnLoginCheckList = new ArrayList<String>();
        }
        return appUnLoginCheckList;
    }

    @SuppressWarnings("unused")
    @PostConstruct
    private void setMap() {
        try {
            String loginResponse = ConfigurationUtil.getString("loginUnCheck");
            if (null != loginResponse) {
                StringTokenizer loginResponses = new StringTokenizer(loginResponse, ",", false);
                while (loginResponses.hasMoreElements()) {
                    map.put(loginResponses.nextToken(), "loginUnCheck");
                }
            }

            String appLinks = ConfigurationUtil.getString("appUnLoginCheck");
            if (null != appLinks) {
                StringTokenizer skAppLink = new StringTokenizer(appLinks, ",", false);
                while (skAppLink.hasMoreElements()) {
                    appUnLoginCheckList.add(skAppLink.nextToken());
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
