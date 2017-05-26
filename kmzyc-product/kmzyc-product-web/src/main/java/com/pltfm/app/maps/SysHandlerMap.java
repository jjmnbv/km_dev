package com.pltfm.app.maps;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.pltfm.sys.bean.SysUserBean;
import com.pltfm.sys.model.SysUser;
/**
 * 系统用户map
 * @author luoyi
 * @since 2013/09/11
 *
 */
@Service("sysHandlerMap")
public class SysHandlerMap {
	
	private static Map<Integer, String> map = new LinkedHashMap<Integer, String>();

	public static Map<Integer, String> getMap() {
		if (map == null) {
			map = new LinkedHashMap<Integer, String>();
		}
		return map;
	}
	
	@PostConstruct
	@SuppressWarnings({ "unchecked", "unused" })
	private void setMap(){
		try {
			SysUserBean sysUserBean = new SysUserBean();
			
			SysUser sysUser = new SysUser();
			//根据条件查询系统用户
			List<SysUser> handlerList =  sysUserBean.getSysUserList(sysUser);
            //排除system用户
            handlerList.stream().filter(user -> user.getUserId() != 0).forEach(user -> {//排除system用户
                map.put(user.getUserId(), user.getUserName());
            });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void setValue(Integer key,String value){
		SysHandlerMap.map.put(key, value);
	}

	public static String getValue(Integer key) {
		if (map == null) {
			getMap();
		}
		return map.get(key);
	}
}
