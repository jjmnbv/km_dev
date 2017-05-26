package com.kmzyc.mframework.mobile.etx;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kmzyc.mframework.mobile.etx.ETXSmsService;

import static org.junit.Assert.*;

/**
 * @author min
 * @create 2016-11-30 15:53
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/applicationContext*.xml")
public class ETXSmsServiceTest {
    @Test
    public void sendMessage() throws Exception {
        ETXSmsService smsService=new ETXSmsService();
        smsService.sendMessage("尊敬的用户，您的验证码为123546，如果不是您本人操作，请勿泄露，回复TD拒收。","18576432258");
    }

}
