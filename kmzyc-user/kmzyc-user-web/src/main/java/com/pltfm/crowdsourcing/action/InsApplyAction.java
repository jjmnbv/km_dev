package com.pltfm.crowdsourcing.action;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;

import com.km.commons.general.model.ResultData;
import com.km.crowdsourcing.common.GlobalVariable;
import com.km.crowdsourcing.model.Bagman;
import com.km.crowdsourcing.model.InstitutionApplyRecord;
import com.km.crowdsourcing.model.InstitutionApplyRecordCriteria;
import com.km.crowdsourcing.model.InstitutionImage;
import com.km.crowdsourcing.service.BagmanService;
import com.km.crowdsourcing.service.GlobalVariableService;
import com.km.crowdsourcing.service.InstitutionApplyRecordService;
import com.km.crowdsourcing.service.InstitutionImageService;
import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.vobject.Message;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.action.BaseAction;
import com.pltfm.sys.model.SysUser;

/**
 * 
 * @ClassName: insApplyAction
 * @Description: 机构审核管理
 * @author xys
 * @date 2016年3月18日
 * @version 1.0
 */
@Controller("insApplyAction")
@Scope(value = "prototype")
public class InsApplyAction extends BaseAction implements ModelDriven {

    private static final long serialVersionUID = 4248109542989699382L;

    private static Logger logger = LoggerFactory.getLogger(InsApplyAction.class);

    private InstitutionApplyRecordCriteria institutionApplyRecordCriteria;
    private InstitutionApplyRecord institutionApplyRecord;

    private int flag;

    private Bagman bagman;

    private Message message;

    private ResultData ResultData;


    private static final String showCrowdSourcingImage = ConfigurationUtil.getString("img_path");
    private static final String imgUploadPath = ConfigurationUtil.getString("IMG_UPLOAD_PATH");



    private GlobalVariable globalVariable = new GlobalVariable();
    @Resource
    private GlobalVariableService globalVariableService;

    @Resource
    private InstitutionImageService institutionImageService;

    @Resource
    private BagmanService bagmanService;

    // 短信邮件对象
    @Resource
    private Destination emailMsg;

    @Resource
    private JmsTemplate jmsTemplate;


    @Resource
    private InstitutionApplyRecordService institutionApplyRecordService;


    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Bagman getBagman() {
        return bagman;
    }

    public void setBagman(Bagman bagman) {
        this.bagman = bagman;
    }

    @Override
    public Object getModel() {
        institutionApplyRecordCriteria = new InstitutionApplyRecordCriteria();
        return institutionApplyRecordCriteria;
    }

    /**
     * @Title: gotoInsAppList @Description: 跳转审批列表页面 @return String @throws
     */
    public String gotoInsAppList() {
        if (page != null) {
            page.setRecordCount(1); // 初始总条数需大于零，否则取出的pageSize会被默认赋值为 20
        } else {
            page = new Page();
            institutionApplyRecordCriteria.setAuditStatus((short) 0);// 默认显示审核中的
            page.setRecordCount(1); // 初始总条数需大于零，否则取出的pageSize会被默认赋值为 20
        }
        int pageIndex = page.getPageNo();
        if (pageIndex == 0) pageIndex = 1;
        int pageSize = page.getPageSize();

        int startIndex = (pageIndex - 1) * pageSize + 1;
        int endIndex = pageSize * pageIndex;
        institutionApplyRecordCriteria.setStartIndex(startIndex < 0 ? 0 : startIndex);
        institutionApplyRecordCriteria.setEndIndex(endIndex < 0 ? 20 : endIndex);


        List<InstitutionApplyRecord> dataList = null;
        int count = 0;
        try {
            count = institutionApplyRecordService
                    .selectApplyRecordCount(institutionApplyRecordCriteria);
            if (count > 0) dataList = institutionApplyRecordService
                    .selectApplyRecordList(institutionApplyRecordCriteria);
        } catch (SQLException e) {
            e.printStackTrace();
            return ERROR;
        }
        page.setRecordCount(count);
        page.setDataList(dataList);
        return SUCCESS;
    }


    /**
     * 审核机构申请 详情
     * 
     * @return
     */
    public String getAuditInsAppDetail() {
        String _aid = this.getRequest().getParameter("aid");
        if (StringUtils.isEmpty(_aid)) {
            return ERROR;
        }
        institutionApplyRecordCriteria.setId(new BigDecimal(_aid));

        try {
            setInstitutionApplyRecord(institutionApplyRecordService
                    .selectApplyRecord(institutionApplyRecordCriteria));
        } catch (Exception e) {
            return ERROR;
        }

        return SUCCESS;
    }

    // 机构审核提交
    public void auditInsApp() {
        ResultData = new ResultData();
        HttpServletRequest httpServletRequest = this.getRequest();
        String aid = httpServletRequest.getParameter("aid");
        String auditeState = httpServletRequest.getParameter("auditeState");
        String auditeMemo = httpServletRequest.getParameter("auditeMemo");
        String bagmanId = httpServletRequest.getParameter("bagmanId");
        String insCod = httpServletRequest.getParameter("insCode");
        // 获取当前登录人对象
        SysUser sysuser = (SysUser) httpServletRequest.getSession().getAttribute("sysUser");
        try {
            auditeMemo = URLDecoder.decode(auditeMemo, "UTF-8");
            InstitutionApplyRecord institutionApplyRecord = new InstitutionApplyRecord();
            institutionApplyRecord.setId(Integer.parseInt(aid));
            institutionApplyRecord.setAuditeState(Short.parseShort(auditeState));
            institutionApplyRecord.setAuditeMemo(auditeMemo);
            institutionApplyRecord.setAuditorLoginId(Long.valueOf(sysuser.getUserId()));
            institutionApplyRecord.setAuditorName(sysuser.getUserName());
            institutionApplyRecord.setAuditeDate(new Date());
            int auditResult =
                    institutionApplyRecordService.auditInsAppRecord(institutionApplyRecord);
            if (auditResult > 0) {
                ResultData.setCode("1");
                ResultData.setMessage("操作成功");
                Bagman bagman = bagmanService.selectByPrimaryKey(Long.parseLong(bagmanId));
                // 发送短信
                sendMessage(insCod, auditeMemo, bagman.getMobile(), sysuser.getUserId(),
                        auditeState);
            } else {
                ResultData.setCode("0");
                ResultData.setMessage("操作失败!");
            }
        } catch (Exception e) {
            ResultData.setCode("-1");
            ResultData.setMessage("机构审核系统异常!");
        }
        this.writeJson(ResultData);
    }

    // 根据aid，获取图片url信息
    public void getInsAppInfo() {
        ResultData = new ResultData();
        HttpServletRequest httpServletRequest = this.getRequest();
        Long aid = Long.parseLong(httpServletRequest.getParameter("aid"));
        try {
            Map<String, Object> dataList = new HashedMap();
            globalVariable = globalVariableService.getGlobalVariable();
            List<InstitutionImage> listImage = institutionImageService.selectByInsAppId(aid);
            dataList.put("imageUrls", listImage);
            dataList.put("showCrowdSourcingImage", showCrowdSourcingImage);
            ResultData.setCode("1");
            ResultData.setMessage("success");
            ResultData.setData(dataList);
        } catch (Exception e) {
            ResultData.setCode("-1");
            ResultData.setMessage("系统异常!");
        }
        this.writeJson(ResultData);
    }

    // 发送短信方法
    public void sendMessage(String insCod, String insAppMemo, String mobile, Integer sysuserId,
            String auditeState) {

    }

    public InstitutionApplyRecordCriteria getInstitutionApplyRecordCriteria() {
        return institutionApplyRecordCriteria;
    }

    public void setInstitutionApplyRecordCriteria(
            InstitutionApplyRecordCriteria institutionApplyRecordCriteria) {
        this.institutionApplyRecordCriteria = institutionApplyRecordCriteria;
    }

    public InstitutionApplyRecord getInstitutionApplyRecord() {
        return institutionApplyRecord;
    }

    public void setInstitutionApplyRecord(InstitutionApplyRecord institutionApplyRecord) {
        this.institutionApplyRecord = institutionApplyRecord;
    }


}
