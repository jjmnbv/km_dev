package com.kmzyc.supplier.service.impl;


import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.kmzyc.commons.exception.ServiceException;

import org.springframework.stereotype.Service;

import com.kmzyc.supplier.model.KeyWords;
import com.kmzyc.supplier.dao.KeyWordsDao;
import com.kmzyc.supplier.service.KeyWordsService;

@Service
public class KeyWordsServiceImpl implements KeyWordsService {

	@Resource
	private KeyWordsDao keyWordsDao;
	
	@Override
	public List<KeyWords> findKeyWords() throws ServiceException {
        try {
            return keyWordsDao.findKeyWords();
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

}