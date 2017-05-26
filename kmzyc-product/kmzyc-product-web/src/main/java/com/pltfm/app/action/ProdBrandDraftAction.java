package com.pltfm.app.action;

import com.kmzyc.zkconfig.ConfigurationUtil;
import com.opensymphony.xwork2.Action;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.maps.ProdBrandDraftStatusMap;
import com.pltfm.app.maps.ProductBrandMap;
import com.pltfm.app.service.ProdBrandDraftService;
import com.pltfm.app.service.ProductService;
import com.pltfm.app.vobject.ProdBrandDraft;
import com.kmzyc.commons.page.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * 品牌草稿action
 *
 * @author zhoujiwei
 */
@Controller("prodBrandDraftAction")
@Scope(value = "prototype")
public class ProdBrandDraftAction extends BaseAction {

    private ProdBrandDraft prodBrandDraft = new ProdBrandDraft();

    String rtnMessage; // 返回的信息

    private String name;

    private Long prodBrandId;

    private String path = ConfigurationUtil.getString("picturePreviewPath");

    @Resource
    private ProdBrandDraftService prodBrandDraftService;

    @Resource
    private ProductService productService;

    /**
     * 查询品牌草稿审核列表
     *
     * @return
     */
    public String showProdBrandDraftList() {
        try {
            prodBrandDraftService.showProdBrandDraftList(page, prodBrandDraft);
            getRequest().setAttribute("prodBrandDraftStatusMap", ProdBrandDraftStatusMap.getProdBrandDraftStatusMap());
        } catch (Exception e) {
            logger.error("查询品牌草稿审核列表出错：" + e.getMessage(), e);
            return Action.ERROR;
        }

        return Action.SUCCESS;
    }

    /**
     * 查看品牌草稿数据
     *
     * @return
     */
    public String gotoProdBrandDraftView() {
        if (prodBrandId == null) {
            rtnMessage="请先选择品牌信息！！";
            return Action.SUCCESS;
        }

        try {
            prodBrandDraft = prodBrandDraftService.getProdBrandDraftById(prodBrandId);
        } catch (Exception e) {
            logger.error("查看品牌草稿数据失败,id为：" + prodBrandId + ",错误信息为：" + e.getMessage(), e);
            return Action.ERROR;
        }

        return Action.SUCCESS;
    }

    /**
     * 品牌审核拒绝
     *
     * @return
     */
    public void refuseProdBrandDraft() {
        ResultMessage resultMessage = new ResultMessage();
        if (prodBrandDraft == null || prodBrandDraft.getBrandId() == null
                || StringUtils.isEmpty(prodBrandDraft.getReasons())) {
            resultMessage.setMessage("品牌审核拒绝失败,请先选择品牌！");
            writeJson(resultMessage);
            return;
        }

        try {
            Long userId = Long.valueOf(getLoginUserId());
            prodBrandDraft.setCheckUser(userId);
            prodBrandDraft.setModifUser(userId);
            prodBrandDraftService.refuseProdBrandDraft(prodBrandDraft);
            resultMessage.setIsSuccess(Boolean.TRUE);
            resultMessage.setMessage("品牌审核拒绝成功！");
        } catch (Exception e) {
            logger.error("品牌审核拒绝失败,id为：" + prodBrandDraft.getBrandId() + ",错误信息为：" + e.getMessage(), e);
            resultMessage.setMessage("品牌审核拒绝失败！");
        }

        writeJson(resultMessage);
    }

    /**
     * 品牌审核通过
     *
     * @return
     */
    public void passProdBrandDraft() {
        ResultMessage resultMessage = new ResultMessage();
        if (prodBrandDraft == null || prodBrandDraft.getBrandId() == null
                || StringUtils.isEmpty(prodBrandDraft.getBrandName())) {
            resultMessage.setMessage("品牌审核通过失败,请先选择品牌！");
            writeJson(resultMessage);
            return;
        }


        try {
            //查找是否有相同的品牌
            int count = prodBrandService.findRepeatName(prodBrandDraft.getBrandName());
            if (count>0) {
                resultMessage.setMessage("无法添加重复品牌！");
                writeJson(resultMessage);
                return;
            }

            Long userId = Long.valueOf(getLoginUserId());
            prodBrandDraft.setCheckUser(userId);
            prodBrandDraft.setModifUser(userId);
            prodBrandDraftService.passProdBrandDraft(prodBrandDraft);
            resultMessage.setIsSuccess(Boolean.TRUE);
            resultMessage.setMessage("品牌审核通过成功！");

            //往品牌map里插入
            ProductBrandMap.setValue(prodBrandDraft.getBrandId(), prodBrandDraft.getBrandName());
        } catch (Exception e) {
            logger.error("查看品牌草稿数据失败,id为：" + prodBrandId + ",错误信息为：" + e.getMessage(), e);
            resultMessage.setMessage("品牌审核通过失败！");
        }

        writeJson(resultMessage);
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public String getRtnMessage() {
        return rtnMessage;
    }

    public void setRtnMessage(String rtnMessage) {
        this.rtnMessage = rtnMessage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getProdBrandId() {
        return prodBrandId;
    }

    public void setProdBrandId(Long prodBrandId) {
        this.prodBrandId = prodBrandId;
    }

    public ProdBrandDraft getProdBrandDraft() {
        return prodBrandDraft;
    }

    public void setProdBrandDraft(ProdBrandDraft prodBrandDraft) {
        this.prodBrandDraft = prodBrandDraft;
    }
}
