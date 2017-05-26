package com.pltfm.remote.service.impl;

import com.kmzyc.util.SpringUtils;
import com.kmzyc.cms.remote.service.DetailPublishService;
import com.km.framework.mq.bean.KmMsg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 获取产品队列实现方法
 *
 * @author gy
 */

public class ProductQueue {
    /**
     * 接收到MQ消息后处理方法
     *
     */
    public static void receiveActivityQueue(KmMsg kmmsg) throws Exception {
        DetailPublishService detailPublishService= SpringUtils.getBean("detailPublishService");
        Map msgData = kmmsg.getMsgData();


        List<Integer> ids = null;
        Map map = (Map) msgData.get("data");
        //1 是商品
        Map map1 = (Map) map.get("1");
        if (!map1.isEmpty()) {
            ids = new ArrayList<Integer>(map1.size());

            Set<String> keySet = map1.keySet();
            for (String key : keySet) {
                ids.add(Integer.valueOf(key));
            }
        }
        //0是全场
        Object map2 = map.get("0");
        if (null != map2) {
            List<Integer> pageIds = detailPublishService.findAllPage();
            detailPublishService.remotePublishAllPage(null, pageIds);
        } else {
            if (null != ids && ids.size() > 0) {
                detailPublishService.remotePublishPartPage(ids);
            }


        }


    }








}
