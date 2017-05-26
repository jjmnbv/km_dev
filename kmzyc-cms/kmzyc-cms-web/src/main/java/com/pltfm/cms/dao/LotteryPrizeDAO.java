package com.pltfm.cms.dao;

import com.pltfm.cms.vobject.LotteryAwards;

import java.sql.SQLException;
import java.util.List;

public interface LotteryPrizeDAO {
    /***
     *
     * 分页查询总数
     * */
//	public int lotteryPrizeCount(LotteryPrize lotterPrize) throws SQLException;

    /***
     *
     * 跟据windowId的类型查询
     * */
    List selectListAll(Integer windowId) throws SQLException;
    /***
     *
     * 分页查询
     * */
    //public List lotteryPrizeSelect(LotteryPrize lotterPrize) throws SQLException;

    /***
     *
     * id集合查询
     * */
    public List selectList(LotteryAwards lotteryAwards) throws SQLException;

    /***
     *
     * 分页查询总数
     * */
    public int lotteryAwardsPrizeCount(LotteryAwards lotteryAwards) throws SQLException;

    /***
     *
     * 分页查询
     * */
    public List lotteryAwardsPrizeSelect(LotteryAwards lotteryAwards) throws SQLException;

    /***
     *
     * id查询
     * */
    public LotteryAwards selectByPrimaryKey(Integer aId) throws SQLException;
}