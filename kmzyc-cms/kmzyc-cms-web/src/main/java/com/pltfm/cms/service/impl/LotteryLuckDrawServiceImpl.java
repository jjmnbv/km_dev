package com.pltfm.cms.service.impl;

import com.kmzyc.commons.page.Page;
import com.pltfm.cms.dao.LotteryLuckDrawDAO;
import com.pltfm.cms.service.LotteryLuckDrawService;
import com.pltfm.cms.vobject.LotteryLuckDraw;

import org.springframework.stereotype.Component;

import java.util.List;

import javax.annotation.Resource;

@Component(value = "lotteryLuckDrawService")
public class LotteryLuckDrawServiceImpl implements LotteryLuckDrawService {
    @Resource(name = "lotteryLuckDrawDAO")
    private LotteryLuckDrawDAO lotteryLuckDrawDAO;

    /***
     * 多条件查询
     * @return
     * @throws Exception
     *             异常
     */
    public Page searchPageByVo(Page pageParam, LotteryLuckDraw vo) throws Exception {
        if (pageParam == null) {
            pageParam = new Page();
        }
        if (vo == null) {
            vo = new LotteryLuckDraw();
        }
        int totalNum = lotteryLuckDrawDAO.lotteryLuckDrawCount(vo);
        pageParam.setRecordCount(totalNum);
        vo.setStartIndex(pageParam.getStartIndex());
        vo.setEndIndex(pageParam.getStartIndex() + pageParam.getPageSize());
        pageParam.setDataList(lotteryLuckDrawDAO.lotteryLuckDrawSelect(vo));
        return pageParam;
    }

    /***
     *
     * id查询
     * */
    public LotteryLuckDraw selectByPrimaryKey(Integer luckDrawId) throws Exception {
        return lotteryLuckDrawDAO.selectByPrimaryKey(luckDrawId);
    }

    /***
     *
     * id集合查询
     * */
    public List selectList(List dataIds) throws Exception {
        LotteryLuckDraw vo = new LotteryLuckDraw();
        vo.setLuckDrawIds(dataIds);
        return lotteryLuckDrawDAO.selectList(vo);
    }

    public LotteryLuckDrawDAO getLotteryLuckDrawDAO() {
        return lotteryLuckDrawDAO;
    }

    public void setLotteryLuckDrawDAO(LotteryLuckDrawDAO lotteryLuckDrawDAO) {
        this.lotteryLuckDrawDAO = lotteryLuckDrawDAO;
    }

}
