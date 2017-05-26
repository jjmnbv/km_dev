package com.kmzyc.b2b.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kmzyc.b2b.dao.KeyWordsDao;
import com.kmzyc.b2b.model.KeyWords;
import com.kmzyc.b2b.service.KeyWordsService;

@Service
public class KeyWordsServiceImpl implements KeyWordsService {
    @Resource(name = "KeyWordsDao")
    KeyWordsDao keyWordsDao;

    // 获取过滤关键字
    @Override
    public List<KeyWords> findKeyWords() throws Exception {

        return keyWordsDao.findKeyWords();

    }

}
