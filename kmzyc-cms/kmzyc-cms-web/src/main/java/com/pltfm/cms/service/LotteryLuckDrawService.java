package com.pltfm.cms.service;

import com.kmzyc.commons.page.Page;
import com.pltfm.cms.vobject.LotteryLuckDraw;

import java.util.List;

public interface LotteryLuckDrawService {
    /***
     * 多条件查询
     * @return
     * @throws Exception
     *             异常
     */
    public Page searchPageByVo(Page pageParam, LotteryLuckDraw vo) throws Exception;

    /***
     *
     * id查询
     * */
    public LotteryLuckDraw selectByPrimaryKey(Integer luckDrawId) throws Exception;

    /***
     *
     * id集合查询
     * */
    public List selectList(List dataIds) throws Exception;
}
