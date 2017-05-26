package com.kmzyc.b2b.service;

import java.util.List;

import com.kmzyc.b2b.model.KeyWords;

public interface KeyWordsService {
  List<KeyWords> findKeyWords() throws Exception;
}
