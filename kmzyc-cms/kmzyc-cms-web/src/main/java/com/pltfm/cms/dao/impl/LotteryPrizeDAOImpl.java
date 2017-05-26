package com.pltfm.cms.dao.impl;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.cms.dao.LotteryPrizeDAO;
import com.pltfm.cms.vobject.LotteryAwards;

import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

@Component(value = "lotteryPrizeDAO")
public class LotteryPrizeDAOImpl implements LotteryPrizeDAO {
    @Resource(name = "sqlMapClient")
    private SqlMapClient sqlMapClient;

    /***
     *
     * 分页查询总数
     * */
    /*public int lotteryPrizeCount(LotteryPrize lotterPrize) throws SQLException{
		 Integer count = (Integer)  sqlMapClient.queryForObject("LOTTERY_PRIZE.selectLotteryAwardsCountByVo", lotterPrize);
	        return count.intValue();
	}
*/
    /***
     *
     * 分页查询
     * */
	/*public List lotteryPrizeSelect(LotteryPrize lotterPrize) throws SQLException{
		 List list = sqlMapClient.queryForList("LOTTERY_PRIZE.searchLotteryAwardsPageByVo", lotterPrize);
	        return list;
	}*/


    /***
     *
     * 分页查询总数
     * */
    public int lotteryAwardsPrizeCount(LotteryAwards lotteryAwards) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject("LOTTERY_PRIZE.selectLotteryAwardsCountByVo", lotteryAwards);
        return count.intValue();
    }

    /***
     *
     * 分页查询
     * */
    public List lotteryAwardsPrizeSelect(LotteryAwards lotteryAwards) throws SQLException {
        List list = sqlMapClient.queryForList("LOTTERY_PRIZE.searchLotteryAwardsPageByVo", lotteryAwards);
        return list;
    }


    /***
     *
     * id查询
     * */
    public LotteryAwards selectByPrimaryKey(Integer aId) throws SQLException {
        LotteryAwards lotteryAwards = new LotteryAwards();
        lotteryAwards.setAwardsId(aId);
        LotteryAwards record = (LotteryAwards) sqlMapClient.queryForObject("LOTTERY_PRIZE.ibatorgenerated_selectByPrimaryKey", lotteryAwards);
        return record;
    }

    /***
     *
     * 跟据windowId的类型查询
     * */
    public List selectListAll(Integer windowId) throws SQLException {
        List list = sqlMapClient.queryForList("LOTTERY_PRIZE.ibatorgenerated_selectListAll", windowId);
        return list;
    }

    /***
     *
     * id集合查询
     * */
    public List selectList(LotteryAwards lotteryAwards) throws SQLException {
        List list = sqlMapClient.queryForList("LOTTERY_PRIZE.ibatorgenerated_selectList", lotteryAwards);
        return list;
    }

    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }

    public void setSqlMapClient(SqlMapClient sqlMapClient) {
        this.sqlMapClient = sqlMapClient;
    }
}