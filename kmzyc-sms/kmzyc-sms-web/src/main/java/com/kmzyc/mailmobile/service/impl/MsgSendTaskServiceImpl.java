package com.kmzyc.mailmobile.service.impl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kmzyc.mailmobile.dao.MsgSendTaskDao;
import com.kmzyc.mailmobile.model.MsgSendTask;
import com.kmzyc.mailmobile.service.MsgSendTaskService;
import com.kmzyc.zkconfig.ConfigurationUtil;

@Service("msgSendTaskService")
public class MsgSendTaskServiceImpl implements MsgSendTaskService {

    @Resource(name = "msgSendTaskDaoImpl")
    private MsgSendTaskDao msgSendTaskDao;

    private final static String timelyCount = ConfigurationUtil.getString("timelyCount");
    private final static String timingCount = ConfigurationUtil.getString("timingCount");// 定时
    private final static String timingTimeOut = ConfigurationUtil.getString("timingTimeOut");// 定时
    private final static String timelyTimeOut = ConfigurationUtil.getString("timelyTimeOut");


    @SuppressWarnings("unchecked")
    @Override
    public List<MsgSendTask> getMsgSendTaskListTiming() throws SQLException {
        List<MsgSendTask> msgSendTaskList = new ArrayList<MsgSendTask>();
        Map<String, Object> conditionMap = new HashMap<String, Object>();
        // System.out.println(new Date()+"   timelyCount"+timelyCount+"  timingCount"+timingCount);
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String time = sDateFormat.format(new Date());
        conditionMap.put("sendTime", new Date());// 系统当前时间
        conditionMap.put("timingTimeOut", timingTimeOut); // 定时超时分钟数
        conditionMap.put("timingCount", timingCount); // 定时发送次数
        msgSendTaskList =
                msgSendTaskDao.findByProperty(
                        "MsgSendTask.queryMsgSendTaskByTiming", conditionMap);
        return msgSendTaskList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MsgSendTask> getMsgSendTaskListTimely() throws SQLException {
        List<MsgSendTask> msgSendTaskList = new ArrayList<MsgSendTask>();
        Map<String, Object> conditionMap = new HashMap<String, Object>();
        // System.out.println(new Date()+"   timelyCount"+timelyCount+"  timingCount"+timingCount);
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String time = sDateFormat.format(new Date());
        conditionMap.put("createTime", new Date());// 系统当前时间
        conditionMap.put("timelyCount", timelyCount); // 立即发送次数
        conditionMap.put("timelyTimeOut", timelyTimeOut); // 及时时超时分钟数
        msgSendTaskList =
                msgSendTaskDao.findByProperty(
                        "MsgSendTask.queryMsgSendTaskByTimely", conditionMap);
        return msgSendTaskList;
    }


    @Override
    public Long saveMsgSendTask(MsgSendTask msgSendTask) throws SQLException {
        return msgSendTaskDao.saveMsgSendTask(msgSendTask);
    }

    @Override
    public void updateMsgSendTask(MsgSendTask msgSendTask) throws SQLException {
        msgSendTaskDao.updateMsgSendTask(msgSendTask);
    }

    @Override
    public MsgSendTask SelectMsgSendTask(Map map) throws SQLException {
        MsgSendTask msgSendTask = msgSendTaskDao.SelectMsgSendTask(map);
        return msgSendTask;
    }


}
