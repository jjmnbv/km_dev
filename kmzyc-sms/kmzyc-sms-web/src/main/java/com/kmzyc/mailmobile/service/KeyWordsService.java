package com.kmzyc.mailmobile.service;

import java.util.List;

import com.kmzyc.mailmobile.model.KeyWords;

public interface KeyWordsService {
	List<KeyWords> findKeyWords() throws Exception;
}
