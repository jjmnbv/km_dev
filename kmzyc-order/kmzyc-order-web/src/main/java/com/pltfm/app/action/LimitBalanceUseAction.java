package com.pltfm.app.action;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.kmzyc.commons.exception.ActionException;

import redis.clients.jedis.JedisCluster;

@Controller("limitBalanceUseAction")
@SuppressWarnings("unchecked")
public class LimitBalanceUseAction extends BaseAction {
    private static final long serialVersionUID = -6242987825376233555L;
    private static Logger logger = LoggerFactory.getLogger(LimitBalanceUseAction.class);
    @Resource
    private JedisCluster jedisCluster;
    String key = "micr:user:balance:use:limit";
    private BigDecimal meetAmt;//订单金额
    private BigDecimal usableAmt;// 余额最大使用数

    /**
     *
     * 
     * @return
     * @throws ActionException
     */
    public String limitBalanceUse() throws ActionException {
        try {
            JSONObject json = new JSONObject();
            json.put("meetAmt",meetAmt);
            json.put("usableAmt",usableAmt);
            jedisCluster.set(key,json.toString());

        }catch(Exception e){
            logger.error("设置余额最大使用数失败:"+e.getMessage());
        }


        return "success";
    }


    public BigDecimal getMeetAmt() {
        return meetAmt;
    }

    public void setMeetAmt(BigDecimal meetAmt) {
        this.meetAmt = meetAmt;
    }

    public BigDecimal getUsableAmt() {
        return usableAmt;
    }

    public void setUsableAmt(BigDecimal usableAmt) {
        this.usableAmt = usableAmt;
    }
}
