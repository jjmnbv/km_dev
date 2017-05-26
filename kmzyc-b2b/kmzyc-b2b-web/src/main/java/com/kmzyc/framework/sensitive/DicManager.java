package com.kmzyc.framework.sensitive;

import java.util.*;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.km.search.commons.trie.DTrie;
import com.km.search.commons.trie.TagWordItem;
import com.kmzyc.b2b.dao.SensitiveDao;
import com.kmzyc.b2b.model.KeyWords;
import com.kmzyc.b2b.service.KeyWordsService;

/**
 * 敏感词过滤词典工具类 用于初始化或者重建敏感词字典
 * 
 * @author river
 * 
 */
@Component("dicManager")
public class DicManager implements ApplicationListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(DicManager.class);

  // 临时存储敏感词库
  private static WeakHashMap<String, DTrie> tempDics = new WeakHashMap<String, DTrie>(3);
  // 存储敏感词库
  private static WeakHashMap<String, DTrie> dics = new WeakHashMap<String, DTrie>(3);

  private static volatile boolean building = false;

  @Resource
  private SensitiveDao sensitiveDao;
  @Resource(name = "keyWordsServiceImpl")
  private KeyWordsService keyWordsService;
  private List<KeyWords> listKeyWords = new ArrayList<KeyWords>();
  private Map<String, String> map = new HashMap<String, String>();

  public boolean init() throws Exception {
    listKeyWords = keyWordsService.findKeyWords();
    for (int i = 0; i < listKeyWords.size(); i++) {
      map.put(listKeyWords.get(i).getKeyWords(), listKeyWords.get(i).getRepWords());
    }
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
        if (null != trie) dics.put(t.name(), trie);
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
      if (null == words || words.isEmpty()) return success;

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

  public String replace(String param) {
    for (String oj : map.keySet()) {
      String key = oj;
      param = param.replaceAll(key, map.get(key));
      String lowerStr = param.toLowerCase();
      StringBuilder sb = new StringBuilder("");
      int fromIndex = 0;
      int endIndex = 0;
      while ((endIndex = lowerStr.indexOf(key.toLowerCase(), fromIndex)) >= 0) {
        sb.append(param.substring(fromIndex, endIndex)).append(map.get(key));
        fromIndex = endIndex + key.length();
      }
      sb.append(param.substring(fromIndex));
      param = sb.toString();
    }
    return param;
  }

  @Override
  public void onApplicationEvent(ApplicationEvent event) {
    try {
      if (event instanceof ContextRefreshedEvent) {
        ContextRefreshedEvent cre = (ContextRefreshedEvent) event;
        if (cre.getApplicationContext().getParent() == null) {// root
          // application
          // context
          init();
        }
      }
    } catch (Exception e) {
      LOGGER.error("初始化敏感词库失败：" + e.getMessage(), e);
    }
  }
}
