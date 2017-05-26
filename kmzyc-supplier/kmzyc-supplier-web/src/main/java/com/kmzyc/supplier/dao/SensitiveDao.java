package com.kmzyc.supplier.dao;

import java.util.List;

public interface SensitiveDao {

	List<String> getSensitiveWordByType(int type);
}
