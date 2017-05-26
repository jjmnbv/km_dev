package com.pltfm.app.maps;

import java.io.IOException;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 活动行业map
 * 
 * @author xkj
 * 
 */
public class ActivityLabelList {

	private static final Logger log = LoggerFactory.getLogger(ActivityLabelList.class);

	private static LinkedList<String> list = null;

	public static LinkedList<String> getList() {
		OrderedProperties p = new OrderedProperties();
		try {
			if (list == null) {
				// 读取配置文件
				p.load(Thread.currentThread().getContextClassLoader()
						.getResourceAsStream("/config/activity_label.properties"));
                LinkedList<String> lists = new LinkedList<String>();
				for (Object key : p.keySet()) {
                    lists.add(p.getProperty((String) key));
				}
                list = lists;
			}
		} catch (IOException e) {
			log.error("加载activity_label.properties信息失败:" + e.getMessage());
		}
		return list;
	}

	public static LinkedList<String> getValue() {
		if (list == null) {
			getList();
		}
		return list;
	}

}