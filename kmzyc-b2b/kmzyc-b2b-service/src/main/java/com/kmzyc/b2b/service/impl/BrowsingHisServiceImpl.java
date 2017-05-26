package com.kmzyc.b2b.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.km.framework.page.Pagination;
import com.kmzyc.b2b.dao.BrowsingHisDao;
import com.kmzyc.b2b.model.BrowsingHis;
import com.kmzyc.b2b.service.BrowsingHisService;
import com.kmzyc.util.StringUtil;

import redis.clients.jedis.JedisCluster;

@SuppressWarnings("unchecked")
@Service
public class BrowsingHisServiceImpl implements BrowsingHisService {
    static Logger logger = LoggerFactory.getLogger(BrowsingHisService.class);
    @Resource(name = "browsingHisDaoImpl")
    private BrowsingHisDao browsingHisDao;

    @Resource(name = "jedisCluster")
    private JedisCluster jedisCluster;

    @Resource(name = "taskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;

    /**
     * 添加商品浏览记录
     */
    @Override
    public void addBrowsingHis(BrowsingHis browsingHis) throws Exception {

        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    browsingHisDao.addBrowsingHis(browsingHis);
                } catch (SQLException e) {
                    logger.error("添加商品浏览记录异常", e);
                }
            }
        });
    }

    /**
     * 根据登录id查询商品code和浏览时间
     */
    @Override
    public List<BrowsingHis> queryBrowsingHis(Map<String, Object> map) throws Exception {

        try {
            List<BrowsingHis> bhlist;
            String str;

            String key = "B2B_browsingHis_" + map.get("loginId") + "_" + map.get("rowNum");

            if (!StringUtil.isEmpty(str = jedisCluster.get(key))) {
                bhlist = JSONArray.parseArray(str, BrowsingHis.class);
            } else {
                bhlist = browsingHisDao.queryBrowsingHis(map);
                jedisCluster.set(key, JSONArray.toJSONString(bhlist));
                jedisCluster.expire(key, 300);
            }
            return bhlist;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 根据登录id查询商品code和浏览时间
     */
    @Override
    public Pagination queryBrowsingHis(Pagination page) throws Exception {


        Pagination returnpage = browsingHisDao.findByPage(
                "BNES_BROWSING_HIS.ibatorgenerated_query_bnes_browsing_his_list",
                "BNES_BROWSING_HIS.ibatorgenerated_query_bnes_browsing_his_count", page);
        return returnpage;
        // return (List<BrowsingHis>) browsingHisDao.queryBrowsingHis(page);

    }

    /**
     * 根据id查询浏览记录的总个数
     */
    @Override
    public Long queryBrowsingHisCount(Map<String, Object> map) throws Exception {

        return browsingHisDao.queryBrowsingHisCount(map);
    }

    /**
     * 根据登录id清空删除浏览记录
     */

    @Override
    public void deleteBrowingHisById(int loginId) throws Exception {

        browsingHisDao.deleteBrowingHisById(loginId);
    }

    /**
     * 根据登录id清空对应的浏览记录ID
     */

    @Override
    public void deleteBrowingHisByBrowingId(Map<String, Object> map) throws Exception {

        browsingHisDao.deleteBrowingHisByBrowingId(map);
    }

    /**
     * 获取是否已存在记录
     */
    @Override
    public int queryBycontentCode(Map<String, Object> map) throws Exception {

        return browsingHisDao.queryByContentCode(map);
    }

    /**
     * 修改浏览记录
     */
    @Override
    public void updateBrowsingHis(Map<String, Object> map) throws Exception {

        browsingHisDao.updateBrowsingHis(map);
    }
}
