package com.pltfm.cms.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ActionContext;
import com.pltfm.cms.service.CmsPageService;
import com.pltfm.cms.service.CmsWindowDataService;
import com.pltfm.cms.service.LotteryPrizeService;
import com.pltfm.cms.vobject.CmsPage;
import com.pltfm.cms.vobject.CmsWindowData;
import com.pltfm.cms.vobject.LotteryAwards;
import com.pltfm.cms.vobject.LotteryPrize;

@Component("lotteryPrizeActin")
@Scope("prototype")
public class CmsLotteryPrizeAction extends BaseAction {
	private static Logger logger = LoggerFactory.getLogger(CmsLotteryPrizeAction.class);
    @Resource(name = "lotteryPrizeService")
    private LotteryPrizeService lotteryPrizeService;
    private LotteryPrize lotteryPrize = new LotteryPrize();
    private LotteryAwards lotteryAwards = new LotteryAwards();

    @Resource(name = "cmsPageService")
    private CmsPageService cmsPageService;//cmsPageService接口

    @Resource(name = "cmsWindowDataService")
    private CmsWindowDataService cmsWindowDataService;//cmsWindowDataService接口
    private String checkeds;//多选框
    private Page page;//分页组
    private List<Integer> lotteryPrizeId;//id集合
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
            CmsPage cmsPage = new CmsPage();
            cmsPage.setWindwId(this.windowId);
            List list1 = cmsPageService.selectBpageId(cmsPage);
            List dataIdsList = new ArrayList();
            if (null != list1 && list1.size() > 0) {
                for (int i = 0; i < list1.size(); i++) {
                    dataIdsList.add(((CmsPage) list1.get(i)).getPageId());
                }
            }
            CmsWindowData cmsWindowData = new CmsWindowData();
            cmsWindowData.setPageIds(dataIdsList);
            cmsWindowData.setDataType(9);
            cmsWindowData.setSiteId(siteId);
            //取的抽奖活动id
            CmsWindowData cms = cmsWindowDataService.queryWindowDataId(cmsWindowData);
            if (cms != null) {
                lotteryAwards.setLuckDrawId(cms.getDataId());
            }

            page = lotteryPrizeService.searchPageByVo(page, lotteryAwards);
            List<CmsWindowData> dataList = this.cmsWindowDataService.queryByWindowIdDataType(this.windowId, 10, siteId);
            List list = page.getDataList();
            if (dataList.size() != 0) {
                for (int i = 0; i < list.size(); i++) {
                    LotteryAwards lotteryPrize = (LotteryAwards) list.get(i);
                    for (int j = 0; j < dataList.size(); j++) {
                        CmsWindowData cmsWinData = dataList.get(j);
                        if (lotteryPrize.getAwardsId().equals(cmsWinData.getDataId())) {
                            lotteryPrize.setFlag(1);
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("CmsLotteryPrizeAction.queryForPage异常：" + e.getMessage(), e);
        }
        return "lotteryPrizeActinList";
    }

    /**
     * 跳转数据列表页面
     */
    public String opLotteryPrizeActinList() {
        return "opLotteryPrizeActinList";
    }

    //检测抽奖是否绑定了
    public void checkLotteryPrize() {
        try {
            Integer siteId = (Integer) session.get("siteId");
            Integer ok = 0;
            CmsPage cmsPage = new CmsPage();
            cmsPage.setWindwId(this.windowId);
            List list1 = cmsPageService.selectBpageId(cmsPage);
            List dataIdsList = new ArrayList();
            if (null != list1 && list1.size() > 0) {
                for (int i = 0; i < list1.size(); i++) {
                    dataIdsList.add(((CmsPage) list1.get(i)).getPageId());
                }
            }
            CmsWindowData cmsWindowData = new CmsWindowData();
            cmsWindowData.setPageIds(dataIdsList);
            cmsWindowData.setDataType(9);
            cmsWindowData.setSiteId(siteId);
            //取的抽奖活动id
            CmsWindowData cms = cmsWindowDataService.queryWindowDataId(cmsWindowData);
            if (cms != null) {
                ok = 1;
            }
            super.writeJson(ok);
        } catch (Exception e) {
            logger.error("CmsLotteryPrizeAction.checkLotteryPrize异常：" + e.getMessage(), e);
        }
    }

    public LotteryPrizeService getLotteryPrizeService() {
        return lotteryPrizeService;
    }

    public void setLotteryPrizeService(LotteryPrizeService lotteryPrizeService) {
        this.lotteryPrizeService = lotteryPrizeService;
    }

    public LotteryPrize getLotteryPrize() {
        return lotteryPrize;
    }

    public void setLotteryPrize(LotteryPrize lotteryPrize) {
        this.lotteryPrize = lotteryPrize;
    }

    public CmsWindowDataService getCmsWindowDataService() {
        return cmsWindowDataService;
    }

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public void setCmsWindowDataService(CmsWindowDataService cmsWindowDataService) {
        this.cmsWindowDataService = cmsWindowDataService;
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

    public LotteryAwards getLotteryAwards() {
        return lotteryAwards;
    }

    public void setLotteryAwards(LotteryAwards lotteryAwards) {
        this.lotteryAwards = lotteryAwards;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public List<Integer> getLotteryPrizeId() {
        return lotteryPrizeId;
    }

    public void setLotteryPrizeId(List<Integer> lotteryPrizeId) {
        this.lotteryPrizeId = lotteryPrizeId;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public Integer getWindowId() {
        return windowId;
    }

    public void setWindowId(Integer windowId) {
        this.windowId = windowId;
    }

    public CmsPageService getCmsPageService() {
        return cmsPageService;
    }

    public void setCmsPageService(CmsPageService cmsPageService) {
        this.cmsPageService = cmsPageService;
    }
}
