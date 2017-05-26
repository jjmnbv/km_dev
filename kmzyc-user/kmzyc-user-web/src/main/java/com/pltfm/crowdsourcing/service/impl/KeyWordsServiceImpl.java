package com.pltfm.crowdsourcing.service.impl;

import java.sql.SQLException;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.km.crowdsourcing.service.KeyWordsService;


@Service("crowdKeyWordsService")
public class KeyWordsServiceImpl implements KeyWordsService {
  @Resource(name = "sqlMapClient")
  private SqlMapClient sqlMapClient;

  private Logger logger = LoggerFactory.getLogger(KeyWordsServiceImpl.class);

  // 获取过滤关键字
  @SuppressWarnings("unchecked")
  @Override
  public Map<String, String> queryKeyWorks() {
    try {
      return sqlMapClient.queryForMap("KeyWords.queryKeyWorks", null, "KEY_WORDS", "REP_WORDS");
    } catch (SQLException e) {
      logger.error("查询敏感词异常", e);
      return null;
    }
  }
}
