package com.pltfm.cms.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ActionContext;
import com.pltfm.cms.service.CmsWindowDataService;
import com.pltfm.cms.service.LotteryLuckDrawService;
import com.pltfm.cms.vobject.CmsWindowData;
import com.pltfm.cms.vobject.LotteryLuckDraw;

@Component("lotteryActin")
@Scope("prototype")
public class CmsLotteryAction extends BaseAction {
	private static Logger logger = LoggerFactory.getLogger(CmsLotteryAction.class);
    @Resource(name = "lotteryLuckDrawService")
    private LotteryLuckDrawService lotteryLuckDrawService;
    private LotteryLuckDraw lotteryLuckDraw;
    @Resource(name = "cmsWindowDataService")
    private CmsWindowDataService cmsWindowDataService;//cmsWindowDataService接口
    private String checkeds;//多选框
    private Page page;//分页组
    private List<Integer> luckDrawIds;//id集合
    /**
     * 数据类型
     */
    private Integer dataType;
    /**
     * 窗口Id
     */
    private Integer windowId;
    /**
     * 页面Id
     */
    private Integer pageId;

    ActionContext actionContext = ActionContext.getContext();
    Map session = actionContext.getSession();
    //分页列表
    public String queryForPage() {
        try {
            Integer siteId = (Integer) session.get("siteId");
            page = lotteryLuckDrawService.searchPageByVo(page, lotteryLuckDraw);
            List<CmsWindowData> dataList = this.cmsWindowDataService.queryByWindowIdDataType(this.windowId, 9, siteId);
            List list = page.getDataList();
            if (dataList.size() != 0) {
                for (int i = 0; i < list.size(); i++) {
                    LotteryLuckDraw lotteryLuckDraw = (LotteryLuckDraw) list.get(i);
                    for (int j = 0; j < dataList.size(); j++) {
                        CmsWindowData cmsWinData = dataList.get(j);
                        if (lotteryLuckDraw.getLuckDrawId().equals(cmsWinData.getDataId())) {
                            lotteryLuckDraw.setFlag(1);
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("CmsLotteryAction.queryForPage异常:" + e.getMessage(), e);
        }
        return "lotteryActinList";
    }

    /**
     * 跳转数据列表页面
     */
    public String openLotteryActinListt() {
        return "opLotteryActinList";
    }

    //检测抽奖是否重复绑定了
    public void checkLottery() {
        try {
            Integer siteId = (Integer) session.get("siteId");
            List<CmsWindowData> dataList = this.cmsWindowDataService.queryByWindowIdDataType(this.windowId, 9, siteId);
            super.writeJson(dataList.size());
        } catch (Exception e) {
            logger.error("CmsLotteryAction.checkLottery检测抽奖是否重复绑定了异常" + e.getMessage(), e);
        }
    }

    public LotteryLuckDrawService getLotteryLuckDrawService() {
        return lotteryLuckDrawService;
    }

    public void setLotteryLuckDrawService(
            LotteryLuckDrawService lotteryLuckDrawService) {
        this.lotteryLuckDrawService = lotteryLuckDrawService;
    }

    public LotteryLuckDraw getLotteryLuckDraw() {
        return lotteryLuckDraw;
    }

    public void setLotteryLuckDraw(LotteryLuckDraw lotteryLuckDraw) {
        this.lotteryLuckDraw = lotteryLuckDraw;
    }

    public List<Integer> getLuckDrawIds() {
        return luckDrawIds;
    }

    public void setLuckDrawIds(List<Integer> luckDrawIds) {
        this.luckDrawIds = luckDrawIds;
    }

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public CmsWindowDataService getCmsWindowDataService() {
        return cmsWindowDataService;
    }

    public void setCmsWindowDataService(CmsWindowDataService cmsWindowDataService) {
        this.cmsWindowDataService = cmsWindowDataService;
    }

    public Integer getWindowId() {
        return windowId;
    }

    public void setWindowId(Integer windowId) {
        this.windowId = windowId;
    }

    public String getCheckeds() {
        return checkeds;
    }

    public void setCheckeds(String checkeds) {
        this.checkeds = checkeds;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }
}
