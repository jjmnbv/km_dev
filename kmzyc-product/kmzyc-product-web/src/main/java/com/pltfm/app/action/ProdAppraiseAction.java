package com.pltfm.app.action;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import javax.annotation.Resource;

import com.google.common.collect.Lists;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.Action;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.maps.AuditStatusMap;
import com.pltfm.app.service.AppraiseAddtoContentService;
import com.pltfm.app.service.AppraiseReplyService;
import com.pltfm.app.service.ProdAppraiseService;
import com.pltfm.app.vobject.AppraiseAddtoContent;
import com.pltfm.app.vobject.AppraiseReply;
import com.pltfm.app.vobject.ProdAppraise;
import com.kmzyc.commons.page.Page;

/**
 * 产品评价
 *
 * @author tanyunxing
 */
@Controller("prodAppraiseAction")
@Scope(value = "prototype")
public class ProdAppraiseAction extends BaseAction {

    private static final long serialVersionUID = -3878540069040099863L;

    private ProdAppraise prodApp = new ProdAppraise();

    private ProdAppraise selectProdApp = new ProdAppraise();

    private AppraiseReply reply = new AppraiseReply();

    private AppraiseAddtoContent content = new AppraiseAddtoContent();

    @Resource
    private ProdAppraiseService prodAppraiseService;

    @Resource
    private AppraiseReplyService appraiseReplyService;

    @Resource
    private AppraiseAddtoContentService appraiseAddtoContentService;

    String rtnMessage; // 返回的信息

    private Long prodAppId;

    // 批量删除时传的Id
    private Long[] delId;

    private String type;

    private Short statusArg;

    private Long[] addContentIds;//追加评论表id

    private String ckType;

    private File excelFile;

    /**
     * 显示评价列表
     *
     * @return
     */
    public String showList() {
        try {
            prodAppraiseService.searchByPage(page, selectProdApp);
            setAuditStatusMap();
        } catch (Exception e) {
            logger.error("显示评价列表失败，", e);
        }
        return Action.SUCCESS;
    }

    /**
     * 查看某个评价的详细信息
     *
     * @return
     */
    public String toView() {
        setAuditStatusMap();

        try {
            prodApp = prodAppraiseService.findByPrimaryKey(prodAppId);
            getRequest().setAttribute("replyList", appraiseReplyService.findByAppraiseId(prodAppId, (short) 0));
            getRequest().setAttribute("addContentList", appraiseAddtoContentService.findByAppId(prodAppId));
        } catch (Exception e) {
            logger.error("查看某个评价的详细信息失败，", e);
        }
        return type;
    }

    /**
     * 修改评价的审核状态
     *
     * @return
     */
    public String toAudit() {
        try {
            this.type = "replyView";
            prodAppraiseService.updateCheckStatus(prodApp);
            this.setRtnMessage("审核成功！");
        } catch (Exception e) {
            logger.error("修改评价的审核状态失败，", e);
            this.setRtnMessage("审核失败！");
        }
        prodApp = new ProdAppraise();
        return this.toView();
    }

    /**
     * 删除
     *
     * @return
     */
    public String deleteProdApp() {
        try {
            prodAppraiseService.deleteByPrimaryKey(delId);
            this.setRtnMessage("删除成功！");
        } catch (Exception e) {
            logger.error("删除失败", e);
            this.setRtnMessage("删除失败！");
        }
        return this.showList();
    }

    /**
     * 评价回复列表
     *
     * @return
     */
    public String findNeedReplyAppr() {
        try {
            prodAppraiseService.searchReplyPage(page, selectProdApp);
            setAuditStatusMap();
        } catch (Exception e) {
            logger.error("评价回复列表失败", e);
        }
        return Action.SUCCESS;
    }

    /**
     * 回复
     *
     * @return
     */
    public String replyAppr() {
        try {
            if ("1".equals(prodApp.getReplyContent())) {//如果是已经是回复了就信息修改操作
                AppraiseReply re = new AppraiseReply();
                re.setApprReplyId(reply.getApprReplyId());
                re.setReplyStatus(Short.parseShort("0"));
                re.setReplyContent(reply.getReplyContent());
                appraiseReplyService.updateByAppraiseReplyId(re);
            } else {
                reply.setCustId(Long.valueOf(super.getLoginUserId()));
                reply.setReplyPerson(super.getLoginUserName());
                reply.setReplyDate(new Date());
                reply.setApprReplyParId(Long.valueOf(0));
                reply.setReplyStatus((short) 0);
                reply.setReplyStyle((short) 0);
                prodApp.setReplyContent("1");
                appraiseReplyService.saveReply(reply, prodApp);
            }
            this.setRtnMessage("回复成功！");
        } catch (Exception e) {
            logger.error("回复失败", e);
            this.setRtnMessage("回复失败，请联系管理员！");
        }
        return this.findNeedReplyAppr();
    }

    /**
     * 评价回复表集合
     *
     * @return
     */
    public String showReplyList() {
        try {
            appraiseReplyService.searchByPage(page, reply);
            setAuditStatusMap();
        } catch (Exception e) {
            logger.error("评价回复表集合失败", e);
        }
        return Action.SUCCESS;
    }

    /**
     * 删除回复
     *
     * @return
     */
    public String deleteAppReply() {
        try {
            appraiseReplyService.deleteBatch(delId);
            this.setRtnMessage("删除成功！");
        } catch (Exception e) {
            logger.error("删除回复失败", e);
            this.setRtnMessage("删除失败，请联系管理员！");
        }
        return this.showReplyList();
    }

    /**
     * 审核回复信息
     *
     * @return
     */
    public String appReplyToAudit() {
        try {
            if (this.statusArg == 1) {
                appraiseReplyService.updatePass(delId);
            } else if(this.statusArg == 2) {
                appraiseReplyService.updateUnPass(delId);
            }
            this.setRtnMessage("操作成功！");
        } catch (Exception e) {
            logger.error("审核回复信息失败", e);
            this.setRtnMessage("操作失败，请联系管理员！");
        }
        return this.showReplyList();
    }

    /**
     * 评价列表的审核回复操作
     *
     * @return
     */
    public String appraiseReplyToAudit() {
        try {
            this.type = "replyView";
            if (this.statusArg == 1) {
                appraiseReplyService.updatePass(delId);
            } else if (this.statusArg == 2) {
                appraiseReplyService.updateUnPass(delId);
            }
            prodApp.setCheckStatus(null);
            prodApp.setElite(null);
            prodAppraiseService.updateCheckStatus(prodApp);
            this.setRtnMessage("操作成功！");
        } catch (Exception e) {
            logger.error("评价列表的审核回复操作失败", e);
            this.setRtnMessage("操作失败，请联系管理员！");
        }
        return this.toView();
    }

    /**
     * 获取追加内容集合
     *
     * @return
     */
    public String showAddtoContentList() {
        try {
            appraiseAddtoContentService.searchPage(page, content);
            setAuditStatusMap();
        } catch (Exception e) {
            logger.error("获取追加内容集合失败", e);
        }
        return Action.SUCCESS;
    }

    /**
     * 删除追加内容
     *
     * @return
     */
    public String deleteAddtoContent() {
        try {
            appraiseAddtoContentService.deleteBatch(delId);
            this.setRtnMessage("删除成功！");
        } catch (Exception e) {
            logger.error("删除追加内容失败", e);
            this.setRtnMessage("删除失败，请联系管理员！");
        }
        return this.showAddtoContentList();
    }

    /**
     * 审核追加内容
     *
     * @return
     */
    public String addToContentToAudit() {
        try {
            if (this.statusArg == 1) {
                appraiseAddtoContentService.updatePass(delId);
            } else if (this.statusArg == 2) {
                appraiseAddtoContentService.updateUnPass(delId);
            }
            this.setRtnMessage("操作成功！");
        } catch (Exception e) {
            logger.error("审核追加内容失败", e);
            this.setRtnMessage("操作失败，请联系管理员！");
        }
        return this.showAddtoContentList();
    }

    /**
     * 评价列表审核追加内容
     *
     * @return
     */
    public String addToContentToAuditAppraise() {
        try {
            this.type = "replyView";
            if (this.statusArg == 1) {
                appraiseAddtoContentService.updatePass(addContentIds);
            } else if (this.statusArg == 2) {
                appraiseAddtoContentService.updateUnPass(addContentIds);
            }
            prodApp.setElite(null);
            prodApp.setCheckStatus(null);
            prodAppraiseService.updateCheckStatus(prodApp);
            this.setRtnMessage("操作成功！");
        } catch (Exception e) {
            logger.error("评价列表审核追加内容失败", e);
            this.setRtnMessage("操作失败，请联系管理员！");
        }
        return this.toView();
    }

    /**
     * 取消和设置精华帖
     *
     * @return
     */
    public String updateAppraiseElite() {
        try {
            prodApp.setCheckStatus(null);
            if ("1".equals(ckType)) {
                prodApp.setElite(Short.parseShort("1"));//设置精华帖
            } else {
                prodApp.setElite(Short.parseShort("0"));//取消设置精华帖
            }
            int count = prodAppraiseService.updateCheckStatus(prodApp);
            if (count > 0) {
                getResponse().getWriter().print("1");
            } else {
                getResponse().getWriter().print("0");
            }

        } catch (Exception e) {
            logger.error("取消和设置精华帖失败", e);
        }
        return null;
    }

    /**
     * 导入
     * @return
     */
    public String uploadExcelFileForAppraise() {
        List<ProdAppraise> list =  new ArrayList();
        Set<String> userNameSet = new HashSet();
        ResultMessage message = new ResultMessage();
        ProdAppraise pa = null;
        String[] dateFormat = new String[]{"yyyy-MM-dd HH:mm", "yyyy/MM/dd HH:mm"};
        String tempVar = "";

        try {
            Workbook wb = Workbook.getWorkbook(excelFile);
            Sheet st = wb.getSheet(0);

            for (int i = 1; i < st.getRows(); i++) {
                pa = new ProdAppraise();

                //用户名
                tempVar = st.getCell(1, i).getContents();
                if (StringUtils.isEmpty(tempVar)) {
                    message.setMessage("导入失败，失败原因：有用户名未填写，请仔细检查数据！");
                    writeJson(message);
                    return null;
                }
                pa.setCustName(tempVar.trim());
                userNameSet.add(tempVar.trim());

                //评价内容
                tempVar = st.getCell(2, i).getContents();
                if (StringUtils.isEmpty(tempVar)) {
                    message.setMessage("导入失败，失败原因：有评价内容未填写，请仔细检查数据！");
                    super.writeJson(message);
                    return null;
                }
                pa.setAppraiseContent(tempVar.trim());

                //评价时间
                try {
                    DateCell dc = (DateCell) (st.getCell(3, i));
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
                    pa.setAppraiseDate(DateUtils.parseDate(sdf.format(dc.getDate()), dateFormat));
                } catch (Exception e) {
                    message.setMessage("导入失败，失败原因：评价时间填写错误或者未填写，请仔细检查数据！");
                    super.writeJson(message);
                    return null;
                }

                //产品名称
                tempVar = st.getCell(4, i).getContents();
                if (StringUtils.isEmpty(tempVar)) {
                    message.setMessage("导入失败，失败原因：有产品名称未填写，请仔细检查数据！");
                    writeJson(message);
                    return null;
                }
                pa.setProductName(tempVar.trim());

                //产品skuId
                try {
                    pa.setProductSkuId(Long.valueOf(st.getCell(5, i).getContents()));
                } catch (Exception e) {
                    message.setMessage("导入失败，失败原因：产品skuId填写错误或者未填写，请仔细检查数据！");
                    writeJson(message);
                    return null;
                }

                //分数
                try {
                    pa.setPoint(Short.valueOf(st.getCell(6, i).getContents()));
                } catch (Exception e) {
                    message.setMessage("导入失败，失败原因：分数填写错误或者未填写，请仔细检查数据！");
                    writeJson(message);
                    return null;
                }

                //满意度
                tempVar = st.getCell(7, i).getContents();
                if (StringUtils.isEmpty(tempVar)) {
                    message.setMessage("导入失败，失败原因：有满意度未填写，请仔细检查数据！");
                    super.writeJson(message);
                    return null;
                }
                pa.setSatisficing(tempVar);
                list.add(pa);
            }
        } catch (Exception e) {
            logger.error("读取评论表失败，", e);
            message.setMessage("系统发生错误，请稍后再试或联系管理员！");
            writeJson(message);
            return null;
        }

        try {
            //插入数据
            prodAppraiseService.insertDataForExcel(list);
        } catch (Exception e) {
            logger.error("批量插入评论数据失败，", e);

            try {
                //查找不存在的用户
                List<String> isExistUser = prodAppraiseService.findIsExistUserName(Lists.newArrayList(userNameSet));
                userNameSet.removeAll(isExistUser);
                if (userNameSet.size() > 0) {
                    message.setMessage("插入失败，以下用户不存在【" + StringUtils.join(userNameSet, ",") + "】");
                } else {
                    message.setMessage("系统发生错误，请稍后再试或联系管理员！");
                }
                writeJson(message);
                return null;
            } catch (Exception e1) {
                logger.error("查找不存在的用户失败，", e);
                message.setMessage("系统发生错误，请稍后再试或联系管理员！");
                writeJson(message);
                return null;
            }
        }

        message.setIsSuccess(true);
        message.setMessage("数据插入成功！总计插入" + list.size() + "条！");
        super.writeJson(message);
        return null;
    }

    public ProdAppraise getProdApp() {
        return prodApp;
    }

    public void setProdApp(ProdAppraise prodApp) {
        this.prodApp = prodApp;
    }

    public String getRtnMessage() {
        return rtnMessage;
    }

    public void setRtnMessage(String rtnMessage) {
        this.rtnMessage = rtnMessage;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Long getProdAppId() {
        return prodAppId;
    }

    public void setProdAppId(Long prodAppId) {
        this.prodAppId = prodAppId;
    }

    public Long[] getDelId() {
        return delId;
    }

    public void setDelId(Long[] delId) {
        this.delId = delId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AppraiseReply getReply() {
        return reply;
    }

    public void setReply(AppraiseReply reply) {
        this.reply = reply;
    }

    public Short getStatusArg() {
        return statusArg;
    }

    public void setStatusArg(Short statusArg) {
        this.statusArg = statusArg;
    }

    public AppraiseAddtoContent getContent() {
        return content;
    }

    public void setContent(AppraiseAddtoContent content) {
        this.content = content;
    }

    public Long[] getAddContentIds() {
        return addContentIds;
    }

    public void setAddContentIds(Long[] addContentIds) {
        this.addContentIds = addContentIds;
    }

    public String getCkType() {
        return ckType;
    }

    public void setCkType(String ckType) {
        this.ckType = ckType;
    }

    public ProdAppraise getSelectProdApp() {
        return selectProdApp;
    }

    public void setSelectProdApp(ProdAppraise selectProdApp) {
        this.selectProdApp = selectProdApp;
    }

    public File getExcelFile() {
        return excelFile;
    }

    public void setExcelFile(File excelFile) {
        this.excelFile = excelFile;
    }

}