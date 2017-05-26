package com.pltfm.cms.dao;

import com.pltfm.cms.vobject.LotteryLuckDraw;

import java.sql.SQLException;
import java.util.List;

public interface LotteryLuckDrawDAO {
    /***
     *
     * 分页查询总数
     * */
    public int lotteryLuckDrawCount(LotteryLuckDraw lotterLuck) throws SQLException;

    /***
     *
     * 分页查询
     * */
    public List lotteryLuckDrawSelect(LotteryLuckDraw lotterLuck) throws SQLException;

    /***
     *
     * id查询
     * */
    public LotteryLuckDraw selectByPrimaryKey(Integer luckDrawId) throws SQLException;

    /***
     *
     * id集合查询
     * */
    public List selectList(LotteryLuckDraw lotterLuck) throws SQLException;
}