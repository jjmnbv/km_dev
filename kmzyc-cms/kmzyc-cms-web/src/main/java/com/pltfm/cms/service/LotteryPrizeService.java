package com.pltfm.cms.service;

import com.kmzyc.commons.page.Page;
import com.pltfm.cms.vobject.LotteryAwards;

import java.util.List;

public interface LotteryPrizeService {
    /***
     * 多条件查询
     * @return
     * @throws Exception
     *             异常
     */
    public Page searchPageByVo(Page pageParam, LotteryAwards vo) throws Exception;

    /***
     *
     * id查询
     * */
    public LotteryAwards selectByPrimaryKey(Integer id) throws Exception;

    /***
     *
     * id集合查询
     * */
    public List selectList(List dataIds) throws Exception;

    /***
     *
     * 跟据windowId的类型查询
     * */
    public List selectListAll(Integer windowId) throws Exception;
}
