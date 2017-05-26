package com.kmzyc.b2b.dao;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.b2b.model.EmMsgSendTask;

public interface EmMsgSendTaskDAO {

  List<EmMsgSendTask> selectEmMsgSendTaskByMobile(String Mobile, String type) throws SQLException;

}
