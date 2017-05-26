package com.pltfm.crowdsourcing.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.commons.general.model.ResultData;
import com.km.crowdsourcing.model.Bagman;
import com.km.crowdsourcing.model.QrcodeApplyRecord;
import com.km.crowdsourcing.model.QrcodeApplyRelation;
import com.km.crowdsourcing.service.BagmanService;
import com.km.crowdsourcing.service.QrcodeApplyRecordService;
import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.action.BaseAction;
import com.pltfm.crowdsourcing.dao.BagmanDao;
import com.pltfm.sys.model.SysUser;

/**
 * 
 * @ClassName: CrowdBagManAction
 * @Description: 众包机构业务员(地推人员)管理
 * @author weijl
 * @date 2016年3月15日 上午11:31:17
 * @version 1.0
 */
@Controller("codesManageAction")
@Scope(value = "prototype")
public class CodesManageAction extends BaseAction implements ModelDriven {

  private static final long serialVersionUID = 4248109542989699382L;

  private static Logger logger = LoggerFactory.getLogger(CodesManageAction.class);

  private ResultData ResultData;

  private QrcodeApplyRecord qrcodeApplyRecord;

  @Resource
  private QrcodeApplyRecordService applyRecordService;

  @Resource
  private BagmanService bagmanService;

  @Resource
  private BagmanDao bagmanDao;

  public ResultData getMessage() {
    return ResultData;
  }

  public void setResultData(ResultData ResultData) {
    this.ResultData = ResultData;
  }

  @Override
  public Object getModel() {
    return new QrcodeApplyRecord();
  }

  public QrcodeApplyRecord getQrcodeApplyRecord() {
    return qrcodeApplyRecord;
  }

  public void setQrcodeApplyRecord(QrcodeApplyRecord qrcodeApplyRecord) {
    this.qrcodeApplyRecord = qrcodeApplyRecord;
  }

  public String instCodesManage() {
    if (page == null) {
      page = new Page();
    }
    if (qrcodeApplyRecord == null) qrcodeApplyRecord = new QrcodeApplyRecord();
    try {
      List<QrcodeApplyRecord> records = null;
      int count = applyRecordService.countManageList(qrcodeApplyRecord);
      page.setRecordCount(count);
      if (count > 0) {
        qrcodeApplyRecord.setStartIndex(page.getPageSize() * (page.getPageNo() - 1) + 1);
        qrcodeApplyRecord.setEndIndex(page.getPageSize() * page.getPageNo());
        records = applyRecordService.codeManageList(qrcodeApplyRecord);
      }
      page.setDataList(records);
    } catch (Exception e) {
      logger.error("获取机构码申请列表异常{}", e);
    }
    return SUCCESS;
  }

  public void getCodes() {
    SysUser sysuser = (SysUser) httpServletRequest.getSession().getAttribute("sysUser");
    String bagmanId = this.getRequest().getParameter("_bagmanId");
    String codeApplyCount = this.getRequest().getParameter("_codeCount");
    String bagmanName = this.getRequest().getParameter("_bagmanName");
    qrcodeApplyRecord = new QrcodeApplyRecord();
    ResultData = new ResultData();
    try {
      if (StringUtils.isNotEmpty(bagmanName) && StringUtils.isEmpty(bagmanId)) {
        Bagman bagman = new Bagman();
        bagman.setName(bagmanName.trim());
        bagman = bagmanDao.selectByBagman(bagman);
        if (bagman != null) bagmanId = bagman.getId().toString();
      }
      if (StringUtils.isNotEmpty(bagmanId))
        qrcodeApplyRecord.setBagmanId(Long.parseLong(bagmanId));
      else {
        ResultData.setCode("-3");
        ResultData.setMessage("请选择业务员！");
        this.writeJson(ResultData);
        return;
      }
      if (StringUtils.isNotEmpty(codeApplyCount))
        qrcodeApplyRecord.setInstitutionCodeCount(Long.parseLong(codeApplyCount.trim()));
      qrcodeApplyRecord.setCreateDate(new Date());
      qrcodeApplyRecord.setOperator(sysuser.getUserName());
      applyRecordService.applyInstCodes(qrcodeApplyRecord);

      ResultData.setCode("1");
      ResultData.setMessage("领用机构码成功!");
    } catch (Exception e) {
      if (e instanceof NumberFormatException) {
        ResultData.setCode("-2");
        ResultData.setMessage("请输入大于0的整数！");
      } else {
        logger.error("申请机构码异常：{}", e);
        ResultData.setCode("-1");
        ResultData.setMessage("领用机构码异常,请联系管理员!");
      }
    }

    this.writeJson(ResultData);
  }

  public void ajaxBagManInfos() {
    List<Map<String, Object>> maninfos = null;
    try {
      maninfos = bagmanService.ajaxBagManInfos();
      Map<String, Object> commap = new LinkedHashMap<String, Object>();
      for (Map<String, Object> info : maninfos) {

        commap.put(info.get("ID").toString(), info.get("NAME"));

      }
      this.writeJson(commap);
    } catch (Exception e) {
      logger.error("异步获取业务员信息异常", e);
    }
  }

  public void ajaxQrcodes4Record() {
    List<QrcodeApplyRelation> result = new ArrayList<QrcodeApplyRelation>();
    String recordId = this.getRequest().getParameter("recordId");
    if (StringUtils.isEmpty(recordId)) {
      this.writeJson(null);
      return;
    }
    QrcodeApplyRelation relation = new QrcodeApplyRelation();
    relation.setInstitutionCodeApplyId(Long.parseLong(recordId));
    try {
      result = applyRecordService.selectByRelation(relation);
    } catch (Exception e) {
      logger.error("异步获取机构码列表异常", e);
    }
    this.writeJson(result);
  }

  public String showQrCodesPage() {
    String recordId = this.getRequest().getParameter("recordId");
    if (StringUtils.isEmpty(recordId)) {
      return ERROR;
    }
    List<String> codeArray = new ArrayList<String>();
    QrcodeApplyRelation relation = new QrcodeApplyRelation();
    relation.setInstitutionCodeApplyId(Long.parseLong(recordId));
    try {
      List<QrcodeApplyRelation> result = applyRecordService.selectByRelation(relation);
      for (QrcodeApplyRelation apply : result) {
        codeArray.add(apply.getInstitutionCode());
      }
      this.getRequest().setAttribute("result", result);
      this.getRequest().setAttribute("codeArray", codeArray);
    } catch (Exception e) {
      logger.error("异步获取机构码列表异常", e);
    }
    return SUCCESS;
  }

}
