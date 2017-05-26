package com.kmzyc.mailmobile.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kmzyc.mailmobile.dao.KeyWordsDao;
import com.kmzyc.mailmobile.model.KeyWords;
import com.kmzyc.mailmobile.service.KeyWordsService;

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
