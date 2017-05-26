package com.kmzyc.b2b.dao;

import java.util.List;

public interface SensitiveDao {

  public List<String> getSensitiveWordByType(int type);
}
