package com.pltfm.app.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.jms.Destination;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;

import com.km.framework.mq.Sender;
import com.km.framework.mq.bean.KmMsg;
import com.kmzyc.commons.exception.ActionException;
import com.kmzyc.commons.page.Page;
import com.kmzyc.framework.constants.EmailSendType;
import com.kmzyc.framework.constants.MessageConstants;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.map.EnchashmentResourceEnumMap;
import com.pltfm.app.map.EnchashmentStatusEnumMap;
import com.pltfm.app.service.AccountInfoService;
import com.pltfm.app.service.BnesAcctEnchashmentService;
import com.pltfm.app.service.PublishMessageService;
import com.pltfm.app.util.Constants;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.util.EnchashmentStatusEnumType;
import com.pltfm.app.vobject.AccountInfo;
import com.pltfm.app.vobject.BnesAcctEnchashment;
import com.pltfm.app.vobject.BnesInfoPrompt;
import com.pltfm.sys.model.SysUser;

import sun.misc.BASE64Decoder;


/**
 * 余额提现action类
 * 
 * @author lijianjun
 * @since 15-04-22
 */
@SuppressWarnings("unchecked")
@Controller
@Scope(value = "prototype")
public class BnesAcctEnchashmentAction extends BaseAction implements ModelDriven {

    private static final long serialVersionUID = -6862089143309418798L;
    // 日志类
    Logger logger = LoggerFactory.getLogger(BnesAcctEnchashmentAction.class);
    // 分页对象
    private Page page;
    // 余额取现实体
    private BnesAcctEnchashment bnesAcctEnchashment = new BnesAcctEnchashment();

    // 审核状态
    private Short enchashmentStatus;

    private String fromConfirmMenu;

    // 时间范围查询条件
    private String dateRange;

    // 总金额
    private BigDecimal totalAmount;

    private Map<String, String> map = new HashMap<String, String>();

    private List<BnesAcctEnchashment> exportDateList;



    @Resource
    private Destination emailMsg;

    @Resource
    private JmsTemplate jmsTemplate;

    @Resource(name = "publishMessageService")
    private PublishMessageService publishMessageService;

    @Resource(name = "bnesAcctEnchashmentService")
    private BnesAcctEnchashmentService bnesAcctEnchashmentService;

    @Resource(name = "accountInfoService")
    private AccountInfoService accountInfoService;

    // 余额提现列表
    public String PageList() {
        // 审核通过，拒绝跳转
        String ifBack = (String) this.httpServletRequest.getSession().getAttribute("ifback");
        if (null == ifBack) {
            this.httpServletRequest.getSession().setAttribute("queryC", bnesAcctEnchashment);
        }

        if (null != ifBack && "back".equals(ifBack)) {
            bnesAcctEnchashment = (BnesAcctEnchashment) this.httpServletRequest.getSession()
                    .getAttribute("queryC");
            this.httpServletRequest.getSession().setAttribute("ifback", null);
            this.httpServletRequest.getSession().setAttribute("queryC", new BnesAcctEnchashment());
        }

        try {
            if (page == null) {
                page = new Page();
            }
            // 余额提现审核状态map绑定到request对象
            super.getRequest().setAttribute("enchashmentStatusEnumMap",
                    EnchashmentStatusEnumMap.getMap());
            super.getRequest().setAttribute("enchashmentResourceEnumMap",
                    EnchashmentResourceEnumMap.getMap());
            // 提现确认后返回处理，提现确认页面只需要显示审核通过的
            if (StringUtils.isNotBlank(fromConfirmMenu) && "yes".equals(fromConfirmMenu)) {
                super.getRequest().setAttribute("isStatus", 1);
                // bnesAcctEnchashment.setEnchashmentStatus(Short
                // .valueOf(EnchashmentStatusEnumType.Status_Pass.getType()));
            }
            // 条件分页查询取现列表
            bnesAcctEnchashmentService.queryBnesAcctEnchashmentListByPage(bnesAcctEnchashment,
                    page);
            if (StringUtils.isNotBlank(fromConfirmMenu)) {
                this.setTotalAmount(bnesAcctEnchashmentService
                        .queryEnchashmentTotalAmount(bnesAcctEnchashment));
            } else {
                this.setTotalAmount(bnesAcctEnchashmentService
                        .queryEnchashmentTotalAmountForFinish(bnesAcctEnchashment));
            }
        } catch (Exception e) {
            logger.error("获取取现信息列表失败" + e.getMessage(), e);
        }
        if (StringUtils.isNotBlank(fromConfirmMenu) && "yes".equals(fromConfirmMenu)) {
            return "confirmPageListFromMenu";
        } else if (StringUtils.isNotBlank(fromConfirmMenu) && "no".equals(fromConfirmMenu)) {
            return "confirmPageList";
        } else {
            return "PageList";
        }

    }

    // 获取提现信息详情
    public String gotoDetail() {
        try {
            if (bnesAcctEnchashment.getEnchashmentId() == null) {
                logger.error("提现ID为空");
                return ERROR;
            }
            // 余额提现审核状态map绑定到request对象
            super.getRequest().setAttribute("enchashmentStatusEnumMap",
                    EnchashmentStatusEnumMap.getMap());
            super.getRequest().setAttribute("enchashmentResourceEnumMap",
                    EnchashmentResourceEnumMap.getMap());
            // 获取供应商取现详情 / 和机构取现详情
            if ((Constants.ENCHASHMENT_RESOURCE_GYS == bnesAcctEnchashment.getEnchashmentResource()
                    .intValue())
                    || (Constants.ENCHASHMENT_RESOURCE_JG == bnesAcctEnchashment
                            .getEnchashmentResource().intValue())) {
                bnesAcctEnchashment = bnesAcctEnchashmentService
                        .selectByPrimaryKey(bnesAcctEnchashment.getEnchashmentId().intValue());
            }
            // 获取微商详情
            if (Constants.ENCHASHMENT_RESOURCE_VS == bnesAcctEnchashment.getEnchashmentResource()
                    .intValue()) {
                bnesAcctEnchashment = bnesAcctEnchashmentService
                        .selectByPrimaryKeyVS(bnesAcctEnchashment.getEnchashmentId().intValue());
            }

        } catch (Exception e) {
            logger.error("获取提现信息详情失败" + e.getMessage(), e);
        }
        return "gotoDetail";
    }

    // 提现审核
    public String checkEnchashment() {
        SysUser sysuser = (SysUser) session.get("sysUser");
        String remarks = bnesAcctEnchashment.getRemarks();
        try {
            // 获取提现申请记录
            bnesAcctEnchashment = bnesAcctEnchashmentService.selectByPrimaryKeyEnchashment(
                    bnesAcctEnchashment.getEnchashmentId().intValue());
            // 获取账户信息
            AccountInfo accountInfo = accountInfoService
                    .selectByPrimaryKey(bnesAcctEnchashment.getAccountId().intValue());
            bnesAcctEnchashment.setEnchashmentStatus(enchashmentStatus);
            bnesAcctEnchashment.setRemarks(remarks);
            bnesAcctEnchashment.setAuditId(sysuser.getUserId().toString());
            bnesAcctEnchashment.setAuditName(sysuser.getUserName());
            bnesAcctEnchashment.setModifyDate(DateTimeUtils.getCalendarInstance().getTime());
            if (enchashmentStatus == Constants.ENCHASHMENT_STATUS_FALSE) {
                // 审核拒绝
                bnesAcctEnchashmentService.rejectEnchashment(bnesAcctEnchashment, accountInfo);
                // 审核拒绝站内提醒，并发送短信
                String promptContent = "您的提现申请已被拒绝。您的取现申请已经被拒绝，拒绝原因为" + remarks + "。";
                String promptTitle = "您的提现申请已被拒绝。";
                remindEnchashment(accountInfo, promptContent, promptTitle,
                        EmailSendType.MSG_ENCHASHMENTREFUSE.getStatus());
            } else {
                // 审核通过
                bnesAcctEnchashmentService.updateByPrimaryKeySelective(bnesAcctEnchashment);
            }
        } catch (Exception e) {
            logger.error("审核提现失败" + e.getMessage(), e);
        }
        bnesAcctEnchashment = new BnesAcctEnchashment();
        return this.PageList();
    }

    /**
     * 财务提现节点相关操作
     * 
     * @return
     */
    public String finashEnchashment() {
        SysUser sysuser = (SysUser) session.get("sysUser");
        String strConfirmRemarks = bnesAcctEnchashment.getConfirmRemarks();
        try {
            // 获取提现申请记录
            bnesAcctEnchashment = bnesAcctEnchashmentService.selectByPrimaryKeyEnchashment(
                    bnesAcctEnchashment.getEnchashmentId().intValue());
            // 获取账户信息
            AccountInfo accountInfo = accountInfoService
                    .selectByPrimaryKey(bnesAcctEnchashment.getAccountId().intValue());
            // 设置提现更新信息
            bnesAcctEnchashment.setConfirmRemarks(strConfirmRemarks);
            bnesAcctEnchashment.setEnchashmentOperId(sysuser.getUserId().toString());
            bnesAcctEnchashment.setEnchashmentOperName(sysuser.getUserName());
            bnesAcctEnchashment.setFinashDate(DateTimeUtils.getCalendarInstance().getTime());
            bnesAcctEnchashment.setEnchashmentStatus(enchashmentStatus);
            if (EnchashmentStatusEnumType.Stuats_Complete.getType()
                    .equals(String.valueOf(enchashmentStatus))) {
                // 处理提现信息
                bnesAcctEnchashmentService.executeEnchashment(bnesAcctEnchashment, accountInfo);
                // 提现完成站内提醒，并发送短信
                String promptContent =
                        "您的提现申请已经受理完成，提现金额" + bnesAcctEnchashment.getEnchashmentAmount()
                                + "元将在10个工作日内进入您申请提现的银行账户中，请注意查收。";
                String promptTitle = "您的提现申请已经受理完成。";
                remindEnchashment(accountInfo, promptContent, promptTitle,
                        EmailSendType.MSG_ENCHASHMENTSUCCESS.getStatus());
            } else if (EnchashmentStatusEnumType.Status_Fail.getType()
                    .equals(String.valueOf(enchashmentStatus))) {
                // 拒绝提现
                bnesAcctEnchashmentService.rejectEnchashment(bnesAcctEnchashment, accountInfo);
                // 提现完成站内提醒，并发送短信
                String promptContent =
                        "您的提现申请受理失败！失败原因为： " + strConfirmRemarks.replaceAll("<[A-z/ =']*>", "");
                String promptTitle = "您的提现申请受理失败";
                remindEnchashment(accountInfo, promptContent, promptTitle,
                        EmailSendType.MSG_ENCHASHMENTERROR.getStatus());
            }
        } catch (Exception e) {
            logger.error("审核提现失败" + e.getMessage(), e);
        }
        bnesAcctEnchashment = new BnesAcctEnchashment();
        bnesAcctEnchashment.setEnchashmentStatus((short) 3);
        fromConfirmMenu = "no";
        return this.PageList();
    }

    private void remindEnchashment(AccountInfo accountInfo, String promptContent,
            String promptTitle, int smsMailType) {
        try {
            Date now = DateTimeUtils.getCalendarInstance().getTime();
            // 判断是供应商还是微商
            boolean isSupplier = null == bnesAcctEnchashment.getEnchashmentResource()
                    || Constants.ENCHASHMENT_RESOURCE_GYS == bnesAcctEnchashment
                            .getEnchashmentResource().intValue();
            // 发送短信
            List<String> numsList = new ArrayList<String>();
            // numsList.add("15889367600");
            numsList.add(accountInfo.getMobile());
            List<Long> userList = new ArrayList<Long>();
            userList.add(accountInfo.getN_LoginId().longValue());
            KmMsg kmMsg1 = new KmMsg("6000", false);// 报文编号为1000,参数2为是否回复
            kmMsg1.getMsgData().put("kmMsgType", MessageConstants.KMMSG_MOBIL);
            kmMsg1.getMsgData().put("smsmailType", smsMailType);
            kmMsg1.getMsgData().put("systemType", MessageConstants.KMMSG_SYSTEM_TYPE_B2B);
            kmMsg1.getMsgData().put("mobilePhones", numsList);
            kmMsg1.getMsgData().put("msgSendType", MessageConstants.EM_SEND_TYPE_IMM);
            kmMsg1.getMsgData().put("uidList", userList);
            Sender.send(kmMsg1, emailMsg, jmsTemplate);

            // 站内信
            BnesInfoPrompt bnesInfoPrompt = new BnesInfoPrompt();
            bnesInfoPrompt.setLoginId(accountInfo.getN_LoginId().toString());
            bnesInfoPrompt.setTitle(promptTitle);
            bnesInfoPrompt.setContent(promptContent);
            bnesInfoPrompt.setCreateDate(now);
            bnesInfoPrompt.setType(4);
            bnesInfoPrompt.setStatus(1);
            bnesInfoPrompt.setIsTime(1);
            bnesInfoPrompt.setCreateId(23);
            bnesInfoPrompt.setCustomerType(isSupplier ? 3 : 1);
            bnesInfoPrompt.setReleaseObject(2);
            bnesInfoPrompt.setReleaseDate(now);
            bnesInfoPrompt.setMessagePlatform(isSupplier ? 3 : 4);
            publishMessageService.addMessage(bnesInfoPrompt);
        } catch (Exception e) {
            logger.error("调用短信异常" + e.getMessage(), e);
        }
    }

    public String uploadPasteImg() {
        try {
            String strImgBase64 = map.get("imgBase64");
            String imgSuffix = map.get("imgSuffix");
            // 获取存放文件路径
            String savePath = ConfigurationUtil.getString("uploadTxImages");
            String readPath = ConfigurationUtil.getString("showTxImage");

            // String savePath = "D:\\tomcatapp\\webapps\\user\\UploadImages";
            // String readPath = "/UploadImages";
            String fileName = UUID.randomUUID() + "." + imgSuffix;
            savePath = savePath + File.separatorChar + fileName;
            base64ToImg(strImgBase64, savePath);
            getResponse().getWriter().print(readPath.concat("/").concat(fileName));
        } catch (Exception e) {
            logger.error("上传图片失败" + e.getMessage(), e);
        }
        return null;
    }

    // 对字节数组字符串进行Base64解码并生成图片
    private void base64ToImg(String imgStr, String imgPath) {
        byte[] byteArr = null;
        OutputStream out = null;
        try {
            if (StringUtils.isNotEmpty(imgStr)) {
                BASE64Decoder decoder = new BASE64Decoder();
                byteArr = decoder.decodeBuffer(imgStr);
                for (int i = 0; i < byteArr.length; ++i) {
                    // 调整异常字节数据
                    if (byteArr[i] < 0) {
                        byteArr[i] += 256;
                    }
                }
                File file = new File(imgPath);
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                out = new FileOutputStream(imgPath);
                out.write(byteArr);
            }
        } catch (Exception e) {
            logger.error("上传图片失败" + e.getMessage(), e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    logger.error("上传图片失败" + e.getMessage(), e);
                }
            }
        }
    }

    // 余额提现列表
    public String exportDate() throws ActionException {
        try {
            exportDateList = bnesAcctEnchashmentService.queryExportDatas(bnesAcctEnchashment);
            for (BnesAcctEnchashment bae : exportDateList) {
                if (bae.getConfirmRemarks() != null && !"".equals(bae.getConfirmRemarks())) {
                    bae.setConfirmRemarks(delHTMLTag(bae.getConfirmRemarks()));
                }
            }
            // 用于处理页面导出excel文件名
            Map session = ActionContext.getContext().getSession();
            if (session.get("esourceName") != null) {
                session.clear();
            }
            if (bnesAcctEnchashment.getEnchashmentResource() != null
                    && bnesAcctEnchashment.getEnchashmentResource().doubleValue() == 0) {
                session.put("esourceName", "供应商提现申请明细表_");
            } else {
                session.put("esourceName", "提现申请明细表_");
            }
        } catch (Exception e) {
            logger.error("查询导出数据失败" + e.getMessage(), e);
            throw new ActionException("查询导出数据失败！" + e.getMessage());
        }
        return "exportData";
    }

    // 去除数据html标签
    public String delHTMLTag(String htmlStr) {
        String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
        String regEx_html = "</?(?!img|/?\\d)[^>]+>"; // 定义HTML标签的正则表达式 ,保留img标签

        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); // 过滤script标签

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); // 过滤style标签

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); // 过滤html标签

        return htmlStr.trim(); // 返回文本字符串
    }

    public Short getEnchashmentStatus() {
        return enchashmentStatus;
    }

    public void setEnchashmentStatus(Short enchashmentStatus) {
        this.enchashmentStatus = enchashmentStatus;
    }

    @Override
    public Page getPage() {
        return page;
    }

    @Override
    public void setPage(Page page) {
        this.page = page;
    }


    public BnesAcctEnchashment getBnesAcctEnchashment() {
        return bnesAcctEnchashment;
    }

    public void setBnesAcctEnchashment(BnesAcctEnchashment bnesAcctEnchashment) {
        this.bnesAcctEnchashment = bnesAcctEnchashment;
    }

    public String getFromConfirmMenu() {
        return fromConfirmMenu;
    }

    public void setFromConfirmMenu(String fromConfirmMenu) {
        this.fromConfirmMenu = fromConfirmMenu;
    }

    public String getDateRange() {
        return dateRange;
    }

    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
    }

    @Override
    public Object getModel() {
        return null;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public List<BnesAcctEnchashment> getExportDateList() {
        return exportDateList;
    }

    public void setExportDateList(List<BnesAcctEnchashment> exportDateList) {
        this.exportDateList = exportDateList;
    }

}
