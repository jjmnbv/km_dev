package com.kmzyc.b2b.dao.impl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.b2b.dao.EmMsgSendTaskDAO;
import com.kmzyc.b2b.model.EmMsgSendTask;

@Component
public class EmMsgSendTaskDAOImpl implements EmMsgSendTaskDAO {

    @Resource(name = "sqlMapClient")
    private SqlMapClient sqlMapClient;

    public EmMsgSendTaskDAOImpl() {

    }

    public EmMsgSendTaskDAOImpl(SqlMapClient sqlMapClient) {
        super();
        this.sqlMapClient = sqlMapClient;
    }



    @Override
    public List<EmMsgSendTask> selectEmMsgSendTaskByMobile(String Mobile, String type)
            throws SQLException {

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 时间 格式
        sdf.format(date);
        Map<String, String> map = new HashMap<String, String>();
        map.put("nowDate", sdf.format(date));
        map.put("mobile", Mobile);
        map.put("sendType1", type);
        List<EmMsgSendTask> list =
                sqlMapClient.queryForList("EM_MSG_SEND_TASK.selectEmMsgSendTaskByMobile", map);
        return list;
    }



}
