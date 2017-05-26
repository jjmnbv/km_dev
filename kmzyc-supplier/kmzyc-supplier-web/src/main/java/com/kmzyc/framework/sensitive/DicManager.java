package com.kmzyc.framework.sensitive;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.WeakHashMap;

import javax.annotation.Resource;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.km.search.commons.trie.DTrie;
import com.km.search.commons.trie.TagWordItem;
import com.kmzyc.supplier.dao.SensitiveDao;

/**
 * 敏感词过滤词典工具类
 * 用于初始化或者重建敏感词字典
 * @author river
 *
 */
@Component("dicManager")
public class DicManager implements ApplicationListener{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DicManager.class);
	// 临时存储敏感词库
	private static WeakHashMap<String, DTrie> tempDics = new WeakHashMap<String, DTrie>(3);
	// 存储敏感词库
	private static WeakHashMap<String, DTrie> dics = new WeakHashMap<String, DTrie>(3);
	
	private volatile boolean building = false;
	
	@Resource
	private SensitiveDao sensitiveDao;
	
	public boolean init() {
		tempDics.putAll(dics);
		building = true; // 开始初始化字典
		dics.clear();
		EnumSet<SensitiveType> types = EnumSet.allOf(SensitiveType.class);
		for (SensitiveType t : types) {
			DTrie trie = new DTrie();
			boolean success = createDic(t, trie);
			if (success) {
				dics.put(t.name(), trie);
				LOGGER.info("创建 " + t.name() + " 敏感词字典成功。");
			} else {
				LOGGER.info("创建 " + t.name() + " 敏感词字典失败。");
				// 尝试获取旧字典
				trie = tempDics.get(t.name());
				if (null != trie)
					dics.put(t.name(), trie);
			}
		}
		building = false; // 初始化字典结束
		tempDics.clear();
		return true;
	}
	
	private static final Object LOCK = "lock";
	
	public DTrie getDic(SensitiveType type) {
		DTrie trie = null;
		if (building) {
			trie = tempDics.get(type.name());
		} else {
			trie = dics.get(type.toString());
			synchronized (LOCK) {
				if (null == trie) {
					trie = new DTrie();
					createDic(type, trie);
				}
			}
		}
		return trie;
	}
	
	
	private boolean createDic(SensitiveType type, DTrie trie) {
		boolean success = false;
		try {
			List<String> words = sensitiveDao.getSensitiveWordByType(type.getCode());
			if (null == words || words.isEmpty())
				return success;
			
			List<TagWordItem> items = new ArrayList<TagWordItem>(words.size());
			for (String w : words) {
				TagWordItem item = new TagWordItem();
				item.word = w;
				items.add(item);
			}
			success = trie.create(items); // 生成DTRIE数据结构
		} catch (Exception e) {
			LOGGER.info("创建 " + type.name() + " 敏感词字典失败。", e);
		}
		return success;
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof ContextRefreshedEvent) {
			ContextRefreshedEvent cre = (ContextRefreshedEvent) event;
			if(cre.getApplicationContext().getParent() == null){//root application context  
				init();
		    }
		}
	}
}
