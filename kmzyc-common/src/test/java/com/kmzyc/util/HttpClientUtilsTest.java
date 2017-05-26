package com.kmzyc.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kmzyc.util.HttpClientUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.*;

/**
 * @author min
 * @create 2016-11-30 15:19
 */
public class HttpClientUtilsTest {
    Logger logger= LoggerFactory.getLogger(HttpClientUtilsTest.class);
    @Test
    public void get() throws Exception {
        logger.info("start");

        CountDownLatch start = new CountDownLatch(1);
        int count = 10;
        CountDownLatch end=new CountDownLatch(count);

        for (int i = 0; i < count; i++) {
//            System.out.println(HttpClientUtils.get("http://www.baidu.com").length());
            new Thread(() -> {
                try {
                    start.await();
                    System.out.println(HttpClientUtils.get("http://www.baidu.com"));
                    end.countDown();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        start.countDown();
        end.await();
    }

    @Test
    public void get1() throws Exception {

    }

    @Test
    public void get2() throws Exception {

    }

    @Test
    public void get3() throws Exception {

    }

    @Test
    public void post() throws Exception {

    }

    @Test
    public void post1() throws Exception {

    }

    @Test
    public void post2() throws Exception {

    }

    @Test
    public void post3() throws Exception {

    }

}
