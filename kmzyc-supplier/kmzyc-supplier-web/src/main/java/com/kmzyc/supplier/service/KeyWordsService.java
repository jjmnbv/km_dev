package com.kmzyc.supplier.service;

import java.util.List;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.supplier.model.KeyWords;

public interface KeyWordsService {

	List<KeyWords> findKeyWords() throws ServiceException;
}
