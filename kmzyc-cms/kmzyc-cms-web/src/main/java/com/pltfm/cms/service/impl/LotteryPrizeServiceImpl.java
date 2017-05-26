package com.pltfm.cms.service.impl;

import com.kmzyc.commons.page.Page;
import com.pltfm.cms.dao.LotteryPrizeDAO;
import com.pltfm.cms.service.LotteryPrizeService;
import com.pltfm.cms.vobject.LotteryAwards;

import org.springframework.stereotype.Component;

import java.util.List;

import javax.annotation.Resource;

@Component("lotteryPrizeService")
public class LotteryPrizeServiceImpl implements LotteryPrizeService {
    @Resource(name = "lotteryPrizeDAO")
    private LotteryPrizeDAO lotteryPrizeDAO;

    /***
     * 多条件查询
     * @return
     * @throws Exception
     *             异常
     */
    public Page searchPageByVo(Page pageParam, LotteryAwards vo) throws Exception {
        if (pageParam == null) {
            pageParam = new Page();
        }
        if (vo == null) {
            vo = new LotteryAwards();
        }
        int totalNum = lotteryPrizeDAO.lotteryAwardsPrizeCount(vo);
        pageParam.setRecordCount(totalNum);
        vo.setStartIndex(pageParam.getStartIndex());
        vo.setEndIndex(pageParam.getStartIndex() + pageParam.getPageSize());
        pageParam.setDataList(lotteryPrizeDAO.lotteryAwardsPrizeSelect(vo));
        return pageParam;
    }

    /***
     *
     * id集合查询
     * */
    public List selectList(List dataIds) throws Exception {
        LotteryAwards vo = new LotteryAwards();
        vo.setAwardsIds(dataIds);
        return lotteryPrizeDAO.selectList(vo);
    }

    /***
     *
     * 跟据windowId的类型查询
     * */
    public List selectListAll(Integer windowId) throws Exception {
        return lotteryPrizeDAO.selectListAll(windowId);
    }

    /***
     *
     * id查询
     * */
    public LotteryAwards selectByPrimaryKey(Integer aid) throws Exception {
        return lotteryPrizeDAO.selectByPrimaryKey(aid);

    }

    public LotteryPrizeDAO getLotteryPrizeDAO() {
        return lotteryPrizeDAO;
    }

    public void setLotteryPrizeDAO(LotteryPrizeDAO lotteryPrizeDAO) {
        this.lotteryPrizeDAO = lotteryPrizeDAO;
    }
}
