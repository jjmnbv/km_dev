package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.EnumSet;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.dao.ICacheClient;
import com.pltfm.app.dao.KeyWordsDAO;
import com.pltfm.app.dao.RedisCache;
import com.pltfm.app.dataobject.KeyWordsDO;
import com.pltfm.app.service.KeyWordsService;
import com.pltfm.app.util.KeyWordsTypeEnum;
import com.pltfm.app.util.SerializeUtil;
import com.pltfm.app.vobject.KeyWordsQuery;

/**
 * 数据访问对象实现类
 * 
 * @since 2013-10-24
 */
@Component(value = "keyWordsService")
public class KeyWordsServiceImpl implements KeyWordsService {

  /**
   * 最近登录DAO接口
   */
  @Resource(name = "keyWordsDAO")
  private KeyWordsDAO keyWordsDAO;
  @Resource(name = "cache")
  private ICacheClient cache;
  @Resource(name = "redisCacheManager")
  private RedisCache redisCacheManager;

  /**
   * 增加序列化对象Redis缓存
   */

  @Override
public Integer setAllRedisKeys(short wordsType) {
    Integer result = 0;
    short status = 1;


    EnumSet<KeyWordsTypeEnum> keySet = EnumSet.allOf(KeyWordsTypeEnum.class);


    try {
      KeyWordsDO keyWordsDO = new KeyWordsDO();
      // 关键字类型 1---搜索 2---咨询3--评价
      keyWordsDO.setStatus(status);
      keyWordsDO.setWordsType(wordsType);
      List<KeyWordsDO> list = keyWordsDAO.findListByExample(keyWordsDO);

      for (KeyWordsTypeEnum e : keySet) {
        if (e.getWordsType() == wordsType) if (list.size() > 0) {
          this.set(e.name().getBytes(), SerializeUtil.serialize(list));
        } else {
          this.del(e.name());
        }
      }


      result = 1;

    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }



    return result;
  }

  /**
   * 设置Redis缓存
   */
  @Override
public String set(byte[] key, byte[] value) {
    return redisCacheManager.setShardedJedis(key, value);
  }

  /**
   * 获取Redis缓存
   */
  @Override
public byte[] get(byte[] key) {
    return redisCacheManager.get(key);
  }

  /**
   * 删除Redis缓存
   */
  @Override
public Long del(String key) {
    return redisCacheManager.del(key);
  }

  /**
   * 获取缓存对象
   */
  @Override
public String getkey(String key) {

    return (String) cache.get(key);

  }

  @Override
public String getRedisKeys(String key) {

    return redisCacheManager.getRedisCacheInfo(key);// redisCache.get(key);
  }

  @Override
public Page findKeyWordsQueryByExample(Page pageParam, KeyWordsQuery keyWordsQuery) {

    if (pageParam == null) {
      pageParam = new Page();
    }
    if (keyWordsQuery == null) {
      keyWordsQuery = new KeyWordsQuery();
    }
    int total;
    try {
      total = keyWordsDAO.countKeyWordsQueryByExample(keyWordsQuery);
      pageParam.setRecordCount(total);
      // 设置查询开始结束索引
      int max = pageParam.getStartIndex() + pageParam.getPageSize();
      keyWordsQuery.setMixNum(pageParam.getStartIndex());
      keyWordsQuery.setMaxNum(max);

      pageParam.setDataList(keyWordsDAO.findListByExample(keyWordsQuery));
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    // .countBnesAcctTransactionQueryByExample(bnesAcctTransactionQuery);

    return pageParam;

  }

  /**
   * 插入数据
   * 
   * @param keyWordsDO
   * @return 插入数据的主键
   */
  @Override
public Integer insertKeyWordsDO(KeyWordsDO keyWordsDO) {


    Integer rowNum = 0;
    try {
      rowNum = keyWordsDAO.insertKeyWordsDO(keyWordsDO);
      this.setAllRedisKeys(keyWordsDO.getWordsType());
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }



    return rowNum;
  }

  /**
   * 统计记录数
   * 
   * @param keyWordsDO
   * @return 查出的记录数
   */
  @Override
public Integer countKeyWordsDOByExample(KeyWordsDO keyWordsDO) {
    Integer countNum = 0;
    try {
      countNum = keyWordsDAO.countKeyWordsDOByExample(keyWordsDO);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return countNum;
  }

  /**
   * 统计记录数
   * 
   * @param keyWordsQuery
   * @return 查出的记录数
   */
  @Override
public Integer countKeyWordsQueryByExample(KeyWordsQuery keyWordsQuery) {
    Integer countNum = 0;
    try {
      countNum = keyWordsDAO.countKeyWordsQueryByExample(keyWordsQuery);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return countNum;
  }

  /**
   * 更新记录
   * 
   * @param keyWordsDO
   * @return 受影响的行数
   */
  @Override
public Integer updateKeyWordsDO(KeyWordsDO keyWordsDO) {
    Integer updateNum = 0;
    try {
      updateNum = keyWordsDAO.updateKeyWordsDO(keyWordsDO);
      this.setAllRedisKeys(keyWordsDO.getWordsType());
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return updateNum;
  }

  /**
   * 获取对象列表
   * 
   * @param keyWordsDO
   * @return 对象列表
   */
  @Override
@SuppressWarnings("unchecked")
  public List<KeyWordsDO> findListByExample(KeyWordsDO keyWordsDO) {
    List<KeyWordsDO> listKeyWordsDO = null;
    try {
      listKeyWordsDO = keyWordsDAO.findListByExample(keyWordsDO);

    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return listKeyWordsDO;
  }

  /**
   * 获取对象列表
   * 
   * @param keyWordsQuery
   * @return 对象列表
   */

  @Override
public List<KeyWordsQuery> findListByExample(KeyWordsQuery keyWordsQuery) {
    List<KeyWordsQuery> listKeyWordsQuery = null;
    try {
      listKeyWordsQuery = keyWordsDAO.findListByExample(keyWordsQuery);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return listKeyWordsQuery;
  }

  /**
   * 根据主键获取keyWordsDO
   * 
   * @param keyWordsId
   * @return keyWordsDO
   */
  @Override
public KeyWordsDO findKeyWordsDOByPrimaryKey(Integer keyWordsId) {
    KeyWordsDO keyWordsDO = null;

    try {
      keyWordsDO = keyWordsDAO.findKeyWordsDOByPrimaryKey(keyWordsId);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return keyWordsDO;
  }

  /**
   * 删除记录
   * 
   * @param keyWordsId
   * @return 受影响的行数
   */
  @Override
public Integer deleteKeyWordsDOByPrimaryKey(Integer keyWordsId) {
    Integer deleteNum = 0;
    KeyWordsDO keyWordsDO = null;
    try {
      keyWordsDO = keyWordsDAO.findKeyWordsDOByPrimaryKey(keyWordsId);

      deleteNum = keyWordsDAO.deleteKeyWordsDOByPrimaryKey(keyWordsId);
      this.setAllRedisKeys(keyWordsDO.getWordsType());
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return deleteNum;
  }

  /**
   * 删除全部记录
   * 
   * @param keyWordsId
   * @return 受影响的行数
   */
  @Override
public Integer deleteAllKeyWords(List<Integer> keyWordsIds) {
    Integer deleteNum = 0;
    for (Integer keyWordsId : keyWordsIds) {

      try {
        KeyWordsDO keyWordsDO = keyWordsDAO.findKeyWordsDOByPrimaryKey(keyWordsId);
        deleteNum += keyWordsDAO.deleteKeyWordsDOByPrimaryKey(keyWordsId);
        this.setAllRedisKeys(keyWordsDO.getWordsType());


      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    return deleteNum;
  }

  public KeyWordsDAO getKeyWordsDAO() {
    return keyWordsDAO;
  }

  public void setKeyWordsDAO(KeyWordsDAO keyWordsDAO) {
    this.keyWordsDAO = keyWordsDAO;
  }

  public ICacheClient getCache() {
    return cache;
  }

  public void setCache(ICacheClient cache) {
    this.cache = cache;
  }

  public RedisCache getRedisCacheManager() {
    return redisCacheManager;
  }

  public void setRedisCacheManager(RedisCache redisCacheManager) {
    this.redisCacheManager = redisCacheManager;
  }

}
