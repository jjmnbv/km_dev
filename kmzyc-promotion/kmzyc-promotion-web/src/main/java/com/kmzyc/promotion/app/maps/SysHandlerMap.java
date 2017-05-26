package com.kmzyc.promotion.app.maps;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kmzyc.promotion.sys.bean.SysUserBean;
import com.kmzyc.promotion.sys.model.SysUser;

/**
 * 系统用户map
 * 
 * @author luoyi
 * @since 2013/09/11
 * 
 */
@Service("sysHandlerMap")
public class SysHandlerMap {
    // 日志记录
    private static final Logger logger = LoggerFactory.getLogger(SysHandlerMap.class);

    private static Map<Integer, String> map = new LinkedHashMap<Integer, String>();

    public static Map<Integer, String> getMap() {
        if (map == null) {
            map = new LinkedHashMap<Integer, String>();
        }
        return map;
    }

    @PostConstruct
    @SuppressWarnings({"unchecked"})
    private void setMap() {
        try {
            SysUserBean sysUserBean = new SysUserBean();

            SysUser sysUser = new SysUser();
            // 根据条件查询系统用户
            List<SysUser> handlerList = sysUserBean.getSysUserList(sysUser);
            for (SysUser user : handlerList) {
                if (user.getUserId() != 0) {// 排除system用户
                    map.put(new Integer(user.getUserId()), user.getUserName());
                }
            }
        } catch (Exception e) {
            logger.error("setMap方法，根据条件查询系统用户异常：", e);
        }
    }

    public static void setValue(Integer key, String value) {
        SysHandlerMap.map.put(key, value);
    }

    public static String getValue(String key) {
        if (map == null) {
            getMap();
        }

        if (StringUtils.isBlank(key)) {

            return null;
        }

        return map.get(Integer.valueOf(key));
    }
}
