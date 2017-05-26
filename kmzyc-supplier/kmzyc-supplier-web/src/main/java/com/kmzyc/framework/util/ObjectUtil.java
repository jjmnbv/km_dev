package com.kmzyc.framework.util;

import net.sf.json.JSONObject;

/**
 * Object 与 Json转换类
 * @author Administrator
 *
 */
public class ObjectUtil {
	public static <T> Object string2Object(String str, Class<T> clazz) {
		return JSONObject.toBean(JSONObject.fromObject(str), clazz);
	}

}