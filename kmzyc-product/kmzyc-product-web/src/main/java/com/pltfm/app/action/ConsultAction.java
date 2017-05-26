package com.pltfm.app.action;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.enums.ConsultCheckStatus;
import com.pltfm.app.enums.ConsultReplyStatus;
import com.pltfm.app.maps.ConsultStatusMap;
import com.pltfm.app.maps.ConsultTypeMap;
import com.pltfm.app.service.ConsultService;
import com.pltfm.app.vobject.Consult;
import com.pltfm.app.vobject.Message;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller("consultAction")
@Scope(value = "prototype")
public class ConsultAction extends BaseAction implements ModelDriven {

    @Resource
    private ConsultService ConsultService;

    private Message message;

    /**
     * 分页类
     */
    private Page page;

    /**
     * 咨询条件实体
     */
    private Consult searchConsut;

    private Consult newSearchConsut = new Consult();

    /**
     * 咨询id
     */
    private BigDecimal pre_consultId;

    /**
     * 进行处理的页面类型
     */
    private String viewType;

    private Long consultIdVal;

    private String ckType;

    /**
     * 分页列表
     *
     * @return
     */
    public String gotoQueryConsultList() {
        try {
            if (page == null) {
                page = new Page();
            }
            //获取咨询类型列表
            getStaticMap();
            page = ConsultService.queryConsultList(page, newSearchConsut);
        } catch (Exception e) {
            logger.error("分页列表失败，", e);
            return ERROR;
        }
        return SUCCESS;
    }

    private void getStaticMap() {
        super.getRequest().setAttribute("ConsultTypeMap", ConsultTypeMap.getMap());
        super.getRequest().setAttribute("ConsultReplyMap", ConsultStatusMap.getReplyMap());
        super.getRequest().setAttribute("ConsultCheckMap", ConsultStatusMap.getCheckMap());
    }

    /**
     * 跳转处理咨询回复
     *
     * @return
     */
    public String gotoConsultReply() {
        try {
            searchConsut = ConsultService.queryConsultByConsultId(pre_consultId);
        } catch (Exception e) {
            logger.error("跳转处理咨询回复失败，", e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 处理咨询回复、咨询审查
     *
     * @return
     */
    public String replyConsult() {
        try {
            if (searchConsut != null) {
                if (viewType.equals("reply")) {
                    //设置回复人,已回复状态为1
                    searchConsut.setReplyPerson(super.getLoginUserId() + "");
                    searchConsut.setReplyStatus(Short.valueOf(ConsultReplyStatus.HAVERESPONSE.getStatus()));
                    Date now = new Date();
                    searchConsut.setReplyDate(now);
                    ConsultService.updateConsultByConsultId(searchConsut);
                    ConsultService.queryConsultByConsultId(new BigDecimal(searchConsut.getConsultId()));
                    //此处修改为自动审核
                    ConsultService.checkReplyBySys();
                }
                //通过,审核状态为2
                else if (viewType.equals("check")) {
                    searchConsut.setCheckStatus(Short.valueOf(ConsultCheckStatus.HAVEPASSED.getStatus()));
                    searchConsut.setCheckManId(new BigDecimal(super.getLoginUserId()));
                    searchConsut.setCheckMan(super.getLoginUserName() + "");
                    ConsultService.updateConsultByConsultId(searchConsut);
                    this.autoCheck();
                }
                //拒绝,审核状态为1
                else if (viewType.equals("refuse")) {
                    HttpServletRequest request = ServletActionContext.getRequest();
                    String newRemark = request.getParameter("remark"); //获取页面上的备注
                    newRemark = URLDecoder.decode(newRemark, "UTF-8"); //进行解码
                    searchConsut.setRemark(newRemark);
                    searchConsut.setConsultId(Long.parseLong(pre_consultId + ""));
                    searchConsut.setCheckStatus(Short.valueOf(ConsultCheckStatus.NOTPASSED.getStatus()));
                    searchConsut.setCheckManId(new BigDecimal(super.getLoginUserId()));
                    searchConsut.setCheckMan(super.getLoginUserId() + "");
                    ConsultService.updateConsultByConsultId(searchConsut);
                }
            }
        } catch (Exception e) {
            logger.error(" 处理咨询回复、咨询审查失败，", e);
            return ERROR;
        }
        searchConsut = new Consult();
        return this.gotoQueryConsultList();
    }

    /**
     * 进入审核页面
     *
     * @return
     */
    public String gotoConsultCheck() {
        try {
            getStaticMap();
            searchConsut = ConsultService.queryConsultByConsultId(pre_consultId);
        } catch (Exception e) {
            logger.error("进入审核页面失败，", e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 自动审核，将找出审核状态为0(待审核)，回复状态为1（已回复）的数据进行回复过滤
     *
     * @return
     */
    public String autoCheck() {
        try {
            ConsultService.checkReplyBySys();
            return gotoQueryConsultList();
        } catch (ServiceException e) {
            logger.error("自动审核，将找出审核状态为0(待审核)，回复状态为1（已回复）的数据进行回复过滤失败，", e);
            return ERROR;
        }
    }

    /**
     * 进入查看页面
     *
     * @return
     */
    public String gotoViewConsult() {
        getStaticMap();
        searchConsut = ConsultService.queryConsultByConsultId(pre_consultId);
        return SUCCESS;
    }

    /**
     * 修改回复
     *
     * @return
     * @throws Exception
     */
    public String replyConsultUpdate() throws Exception {
        try {
            searchConsut.setCheckManId(new BigDecimal(super.getLoginUserId()));
            searchConsut.setCheckMan(super.getLoginUserName() + "");
            ConsultService.updateConsultByConsultId(searchConsut);
        } catch (Exception e) {
            logger.error("修改回复，", e);
            return ERROR;
        }
        return this.gotoQueryConsultList();
    }

    /**
     * 咨询审核通过与不通过操作
     *
     * @return
     */
    public String consultCheckPass() {
        searchConsut.setConsultId(consultIdVal);
        //ckType为空就执行审核通过操作
        if (null == ckType) {
            searchConsut.setCheckStatus(Short.valueOf(ConsultCheckStatus.HAVEPASSED.getStatus()));
        } else {//执行审核不通过操作
            searchConsut.setCheckStatus(Short.valueOf(ConsultCheckStatus.NOTPASSED.getStatus()));
        }

        try {
            int count = ConsultService.updateConsultByConsultId(searchConsut);
            if (count > 0) {//修改成功
                getResponse().getWriter().print("1");
            } else {
                getResponse().getWriter().print("0");
            }
        } catch (Exception e) {
            logger.error("修改商品咨询状态出现异常" + e.getMessage(), e);
        }
        return null;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public ConsultService getConsultService() {
        return ConsultService;
    }

    public void setConsultService(ConsultService consultService) {
        ConsultService = consultService;
    }

    public Consult getSearchConsut() {
        return searchConsut;
    }

    public void setSearchConsut(Consult searchConsut) {
        this.searchConsut = searchConsut;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Object getModel() {
        searchConsut = new Consult();
        return searchConsut;
    }

    public BigDecimal getPre_consultId() {
        return pre_consultId;
    }

    public void setPre_consultId(BigDecimal pre_consultId) {
        this.pre_consultId = pre_consultId;
    }

    public String getViewType() {
        return viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    public String getCkType() {
        return ckType;
    }

    public void setCkType(String ckType) {
        this.ckType = ckType;
    }

    public Long getConsultIdVal() {
        return consultIdVal;
    }

    public void setConsultIdVal(Long consultIdVal) {
        this.consultIdVal = consultIdVal;
    }

    public Consult getNewSearchConsut() {
        return newSearchConsut;
    }

    public void setNewSearchConsut(Consult newSearchConsut) {
        this.newSearchConsut = newSearchConsut;
    }
}
