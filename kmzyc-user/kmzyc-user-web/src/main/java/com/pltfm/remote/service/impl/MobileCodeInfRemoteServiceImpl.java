package com.pltfm.remote.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.kmzyc.user.remote.service.MobileCodeInfRemoteService;
import com.pltfm.app.dao.MobileCodeInfDAO;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.util.RemoteInvoking;
import com.pltfm.app.vobject.MobileCodeInf;

/**
 * 手机随机码信息远程接口实现类
 * 
 * @author cjm
 * @since 2013-8-16
 */
@Component(value = "mobileCodeInfRemoteService")
public class MobileCodeInfRemoteServiceImpl implements MobileCodeInfRemoteService {
    /**
     * 日志类
     */
    Logger log = LoggerFactory.getLogger(this.getClass());
    /**
     * 手机随机码DAO接口
     */
    @Resource(name = "mobileCodeInfDAO")
    private MobileCodeInfDAO mobileCodeInfDAO;

    /**
     * 添加手机随机码
     * 
     * @param mobileCodeInf 手机随机码实体
     * @param type 1、产品 2、订单 3、B2B
     * @return 返回值
     * @throws Exception 异常
     */
    @Override
    public Integer addMobileCodeInf(MobileCodeInf mobileCodeInf, Integer type) throws Exception {


        /*
         * Integer ran =r.nextInt(9999)+1000; String code = ran.toString(); if(code.length()==5){
         * code = code.substring(0,code.length()-1); }
         * 
         * mobileCodeInf.setTattedCode(code);
         */

        Date d = DateTimeUtils.getCalendarInstance().getTime();
        // mobileCodeInf.setN_FailureTimeValue(30);
        mobileCodeInf.setD_CreateDate(d);

        mobileCodeInf.setLastSendTattedcodeTime(DateTimeUtils.getDateTime(d));

        // log.warn(RemoteInvoking.remoteInvokingType(type));

        return mobileCodeInfDAO.insert(mobileCodeInf);
    }

    /**
     * 根据条件查询单条手机随机码信息
     * 
     * @param mobileCodeInf 手机随机码实体
     * @param type 1、产品 2、订单 3、B2B
     * @return 返回值
     * @throws Exception 异常
     */
    @Override
    public MobileCodeInf selectByMobileCodeInf(MobileCodeInf mobileCodeInf, Integer type)
            throws Exception {
        MobileCodeInf mobileCode = null;

        mobileCode = mobileCodeInfDAO.selectByMobileCodeInf(mobileCodeInf);
        if (mobileCode != null) {
            Date lastSendTattedcodeTime = DateTimeUtils
                    .parseDate(mobileCode.getLastSendTattedcodeTime(), "yyyy-MM-dd HH:mm:ss");

            // 最后发送随机码时间 毫秒
            long lastMinute = DateTimeUtils.getMillis(lastSendTattedcodeTime);

            // 当前时间 毫秒
            long todayMinute =
                    DateTimeUtils.getMillis(DateTimeUtils.getCalendarInstance().getTime());

            long ss = (todayMinute - lastMinute) / (1000); // 共计秒数

            int mm = (int) ss / 60; // 共计分钟数

            // 但时间超过失效时间值则无效
            if (mm > mobileCode.getN_FailureTimeValue()) {
                mobileCode = null;
            }
        }

        log.warn(RemoteInvoking.remoteInvokingType(type));
        return mobileCode;
    }

    /**
     * 更新手机随机码状态为已经验证
     * 
     * @param mobileCodeInf 手机随机码实体
     * @param type 1、产品 2、订单 3、B2B
     * @return 返回值
     * @throws Exception 异常updateByPrimaryKeySelective
     */
    @Override
    public Integer updateMobileCodeInfByPrimaryKey(MobileCodeInf mobileCodeInf, Integer type)
            throws Exception {
        // TODO Auto-generated method stub
        int row = 0;
        if (mobileCodeInf.getTattedCode() != null) {
            row = mobileCodeInfDAO.isupdatecode(mobileCodeInf);
        }
        return row;
    }

}
