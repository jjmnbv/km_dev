package com.pltfm.cms.dao.impl;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.cms.dao.LotteryLuckDrawDAO;
import com.pltfm.cms.vobject.LotteryLuckDraw;

import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

@Component(value = "lotteryLuckDrawDAO")
public class LotteryLuckDrawDAOImpl implements LotteryLuckDrawDAO {
    @Resource(name = "sqlMapClient")
    private SqlMapClient sqlMapClient;

    /***
     *
     * 分页查询总数
     * */
    public int lotteryLuckDrawCount(LotteryLuckDraw lotterLuck) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject("LOTTERY_LUCK_DRAW.selectLotteryLuckDrawCountByVo", lotterLuck);
        return count.intValue();
    }

    /***
     *
     * id查询
     * */
    public LotteryLuckDraw selectByPrimaryKey(Integer luckDrawId) throws SQLException {
        LotteryLuckDraw lotterLuck = new LotteryLuckDraw();
        lotterLuck.setLuckDrawId(luckDrawId);
        LotteryLuckDraw record = (LotteryLuckDraw) sqlMapClient.queryForObject("LOTTERY_LUCK_DRAW.ibatorgenerated_selectByPrimaryKey", lotterLuck);
        return record;
    }

    /***
     *
     * id集合查询
     * */
    public List selectList(LotteryLuckDraw lotterLuck) throws SQLException {
        List list = sqlMapClient.queryForList("LOTTERY_LUCK_DRAW.ibatorgenerated_selectList", lotterLuck);
        return list;
    }

    /***
     *
     * 分页查询
     * */
    public List lotteryLuckDrawSelect(LotteryLuckDraw lotterLuck) throws SQLException {
        List list = sqlMapClient.queryForList("LOTTERY_LUCK_DRAW.searchLotteryLuckDrawPageByVo", lotterLuck);
        return list;
    }

    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }

    public void setSqlMapClient(SqlMapClient sqlMapClient) {
        this.sqlMapClient = sqlMapClient;
    }

}