package com.pltfm.crowdsourcing.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;
import javax.jms.Destination;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.km.crowdsourcing.model.Bagman;
import com.km.crowdsourcing.model.InstitutionApplyRecord;
import com.km.crowdsourcing.model.InstitutionApplyRecordCriteria;
import com.km.crowdsourcing.model.InstitutionInfo;
import com.km.crowdsourcing.service.BagmanService;
import com.km.crowdsourcing.service.InstitutionApplyRecordService;
import com.km.crowdsourcing.service.QrcodeApplyRecordService;
import com.kmzyc.promotion.exception.ServiceException;
import com.kmzyc.user.remote.service.CustomerRemoteService;
import com.pltfm.app.util.Constants;
import com.pltfm.app.vobject.LoginInfo;
import com.pltfm.crowdsourcing.dao.BagmanDao;
import com.pltfm.crowdsourcing.dao.InstitutionApplyRecordDao;
import com.pltfm.crowdsourcing.dao.InstitutionImageDao;
import com.pltfm.crowdsourcing.dao.InstitutionInfoDao;


/**
 * 
 * @ClassName: InstitutionApplyRecordServiceImpl
 * @Description: 机构审批记录接口实现类
 * @author xys
 * @date 2016年3月16日
 * @version 1.0
 */
@Service(value = "institutionApplyRecordService")
public class InstitutionApplyRecordServiceImpl implements InstitutionApplyRecordService {
  private Logger logger = LoggerFactory.getLogger(InstitutionApplyRecordServiceImpl.class);
  @Resource
  private InstitutionApplyRecordDao institutionApplyRecordDao;

  @Resource
  private InstitutionInfoDao institutionInfoDao;

  @Resource
  private InstitutionImageDao institutionImageDao;

  @Resource
  private BagmanService bagmanService;

  @Resource
  private BagmanDao bagmanDao;

  @Resource
  private CustomerRemoteService customerRemoteService;

  @Resource
  private QrcodeApplyRecordService qrcodeApplyRecordService;

  // 短信邮件对象
  @Resource
  private Destination emailMsg;

  @Resource
  private JmsTemplate jmsTemplate;

  @Override
  public int insertInsAppRecord(InstitutionApplyRecord institutionApplyRecord)
      throws ServiceException {
    try {
      int id = institutionApplyRecordDao.insertInsAppRecord(institutionApplyRecord);
      return id;
    } catch (Exception e) {
      logger.error("institutionCode:{},插入机构审批记录异常", institutionApplyRecord.getInstitutionCode(), e);
      return 0;
    }
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public int auditInsAppRecord(InstitutionApplyRecord institutionApplyRecord) {
    if(null == institutionApplyRecord){
        logger.error("auditInsAppRecord:{},审批机构申请异常,传入的参数为空!"); 
        return 0;
    }
    int resultCode = 0;
    String code = null;
    try {
        code = institutionApplyRecord.getInstitutionCode();
      // 修改审批记录
      institutionApplyRecordDao.updateInsAppRecord(institutionApplyRecord);
      institutionApplyRecord =
          institutionApplyRecordDao.queryAuditingByInsId(institutionApplyRecord.getId());
      if (institutionApplyRecord == null) {// 查询不到数据
        logger.error("institutionCode:{},查询机构申请信息异常", code);
        return resultCode;
      }

      // 通过记录生成机构用户信息
      generatoInstitution4ApplyRecord(institutionApplyRecord);

      // 操作完成，设置返回code为1
      resultCode = 1;
      return resultCode;
    } catch (Exception e) {
      logger.error("institutionCode:{},审批机构申请异常", code, e);
      return resultCode;
    }
  }

  /**
   * @Title: generatoInstitution4ApplyRecord @Description: 通过记录生成机构用户信息 @param @param
   * institutionApplyRecord @param @throws Exception @param @throws SQLException @return
   * void @throws
   */
  private void generatoInstitution4ApplyRecord(InstitutionApplyRecord institutionApplyRecord)
      throws Exception {
    // 新建用户（机构管理员）返回注册loginId
    Long time = System.currentTimeMillis();
    String password = institutionApplyRecord.getPassword();
    String insCodeNo = institutionApplyRecord.getInstitutionCode().substring(2,
        institutionApplyRecord.getInstitutionCode().length());
    String loginAccount = "km_" + insCodeNo + time;
    LoginInfo loginInfo = new LoginInfo();
    loginInfo.setLoginPassword(password);
    loginInfo.setLoginAccount(loginAccount);
    loginInfo.setN_CustomerTypeId(1);
    // 注册的设备，1：pc 2：wap 3：app
    loginInfo.setRegister_Device(2);
    // 注册的平台，1：机构
    loginInfo.setRegister_Platfrom(1);
    Map<String, String> registResultMap = customerRemoteService.registerLoginInfo(loginInfo, 3);
    Long loginId = Long.parseLong(registResultMap.get("loginId"));



    // 修改业务员推广机构数量 ； 审核通过，insert机构信息、推广机构数量（+1）
    if (institutionApplyRecord.getAuditeState() != null && institutionApplyRecord.getAuditeState()
        .equals(Constants.INST_APPLY_RECORD_AUDIT_STATE_ALREADY)) {
      InstitutionInfo institutionInfo = new InstitutionInfo();
      institutionInfo.setInstitutionCode(institutionApplyRecord.getInstitutionCode());
      institutionInfo.setInstitutionName(institutionApplyRecord.getInstitutionName());
      institutionInfo.setInstitutionAddress(institutionApplyRecord.getInstitutionAddress());
      institutionInfo.setInstitutionContactor(institutionApplyRecord.getInstitutionContactor());
      institutionInfo.setInstitutionPhonenumber(institutionApplyRecord.getInstitutionPhonenumber());
      institutionInfo.setRegistRebate(institutionApplyRecord.getRegistRebate());
      institutionInfo.setSpreadStartDate(institutionApplyRecord.getSpreadStartDate());
      institutionInfo.setSpreadEndDate(institutionApplyRecord.getSpreadEndDate());
      institutionInfo.setActState((short) 0);
      institutionInfo.setStatus((short) 1);
      institutionInfo.setBagmanId(institutionApplyRecord.getBagmanId());
      institutionInfo.setPassword(institutionApplyRecord.getPassword());
      institutionInfo.setCreateDate(institutionApplyRecord.getAuditeDate());
      institutionInfo.setLoginId(loginId);
      institutionInfo.setAuditeDate(institutionApplyRecord.getAuditeDate());
      institutionInfo.setAuditeMemo(institutionApplyRecord.getAuditeMemo());
      institutionInfo.setAuditorLoginId(institutionApplyRecord.getAuditorLoginId());
      institutionInfo.setAuditorName(institutionApplyRecord.getAuditorName());
      institutionInfoDao.insertInsInfo(institutionInfo);
      bagmanDao.updateByBagManId(institutionApplyRecord.getBagmanId());
    }

  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public Map<String, String> submitInsApply(InstitutionApplyRecord institutionApplyRecord,
      String CS_SESSION_USER_ID) throws Exception {
    Map<String, String> remap = new HashMap<String, String>();

    // 校验机构信息表
    InstitutionInfo institutionIfo = null;
    institutionIfo = institutionInfoDao.queryInsInfoByInsCodeOrInsName(institutionApplyRecord);
    if (institutionIfo != null && institutionIfo.getInstitutionCode() != null
        && institutionApplyRecord.getInstitutionCode()
            .equals(institutionIfo.getInstitutionCode())) {// 机构信息表中已存在相同编号的机构，不能申请！
      remap.put("code", "1");
      remap.put("message", "机构编码：" + institutionApplyRecord.getInstitutionCode() + "已存在，无法新建申请！");
      return remap;
    }
    if (institutionIfo != null && institutionIfo.getInstitutionName() != null
        && institutionApplyRecord.getInstitutionName()
            .equals(institutionIfo.getInstitutionName())) {// 机构信息表中已存在相同名称的机构，不能申请！
      remap.put("code", "2");
      remap.put("message", "机构名称：" + institutionApplyRecord.getInstitutionName() + "已存在，无法新建申请！");
      return remap;
    }
    // 校验机构申请表
    InstitutionApplyRecord QueryinstitutionApplyRecord = null;
    QueryinstitutionApplyRecord =
        institutionApplyRecordDao.queryAuditingByInsCodeOrInsName(institutionApplyRecord);
    if (QueryinstitutionApplyRecord != null
        && QueryinstitutionApplyRecord.getInstitutionCode() != null && institutionApplyRecord
            .getInstitutionCode().equals(QueryinstitutionApplyRecord.getInstitutionCode())) {// 相同编号的机构正在审核中，不能申请！
      remap.put("code", "3");
      remap.put("message", "机构编码：" + institutionApplyRecord.getInstitutionCode() + "审核中，无法新建申请！");
      return remap;
    }
    if (QueryinstitutionApplyRecord != null
        && QueryinstitutionApplyRecord.getInstitutionName() != null && institutionApplyRecord
            .getInstitutionName().equals(QueryinstitutionApplyRecord.getInstitutionName())) {// 相同名称的机构正在审核中，不能申请！
      remap.put("code", "4");
      remap.put("message", "机构名称：" + institutionApplyRecord.getInstitutionName() + "审核中，无法新建申请！");
      return remap;
    }

    // 检查机构有效性（编码属于当前业务员）
    String bagmanId = "";

    Map<String, Object> bagmanMap = qrcodeApplyRecordService
        .selectBagmanIdByInsCode(institutionApplyRecord.getInstitutionCode());
    if (bagmanMap != null) {
      bagmanId = String.valueOf(bagmanMap.get("BAGMAN_ID"));
    }
    if (bagmanId.equals("")) {// 机构码无对应的业务员
      logger.error("提交机构申请institutionCode:{}，不存在", institutionApplyRecord.getInstitutionCode());
      remap.put("code", "5");
      remap.put("message", "机构编码：" + institutionApplyRecord.getInstitutionCode() + "不存在");
      return remap;
    } else if (!bagmanId.equals(CS_SESSION_USER_ID)) {// 此机构码不属于当前登录的业务员
      logger.error("提交机构申请institutionCode:{}，不属于当前业务员{}",
          institutionApplyRecord.getInstitutionCode(), CS_SESSION_USER_ID);
      remap.put("code", "6");
      remap.put("message", "机构编码：" + institutionApplyRecord.getInstitutionCode() + "不属于当前业务员");
      return remap;
    }


    int applyId = institutionApplyRecordDao.insertInsAppRecord(institutionApplyRecord);
    // 当申请记录初始状态即为已审核（由于全局变量设置自动审核）时，直接生成机构用户信息
    if (applyId != 0 && Objects.equals(institutionApplyRecord.getAuditeState(),
            Constants.INST_APPLY_RECORD_AUDIT_STATE_ALREADY)) {
      logger.info("自动审核，直接生成机构用户信息，机构用户为：{}", institutionApplyRecord.getBagmanName());
      institutionApplyRecord.setAuditeDate(institutionApplyRecord.getCreateDate());// 审核时间即为创建时间
      generatoInstitution4ApplyRecord(institutionApplyRecord);
      Bagman bagman = bagmanService.selectByPrimaryKey(institutionApplyRecord.getBagmanId());
      // 发送短信
      sendMessage(institutionApplyRecord.getInstitutionName(), bagman.getMobile());
    }

    remap.put("code", "200");
    remap.put("applyId", applyId + "");
    remap.put("institutionCode", institutionApplyRecord.getInstitutionCode());
    remap.put("message", "提交成功");
    return remap;
  }

  @Override
  public int selectApplyRecordCount(InstitutionApplyRecordCriteria institutionApplyRecordCriteria)
      throws SQLException {
    int count = 0;
    try {
      count = institutionApplyRecordDao.selectApplyRecordCount(institutionApplyRecordCriteria);
    } catch (SQLException e) {
      logger.error("查询机构审核信息数量" + institutionApplyRecordCriteria.toString(), e);
      throw new ServiceException(0, "审核推广者记录数量", e);
    }
    return count;
  }

  @Override
  public List<InstitutionApplyRecord> selectApplyRecordList(
      InstitutionApplyRecordCriteria institutionApplyRecordCriteria) throws SQLException {
    List<InstitutionApplyRecord> lt = null;
    try {
      lt = institutionApplyRecordDao.selectApplyRecordList(institutionApplyRecordCriteria);
    } catch (SQLException e) {
      logger.error("查询机构审核列表" + institutionApplyRecordCriteria.toString(), e);
      throw new ServiceException(0, "查询机构审核列表", e);
    }
    return lt;
  }

  @Override
  public InstitutionApplyRecord selectApplyRecord(
      InstitutionApplyRecordCriteria institutionApplyRecordCriteria) throws Exception {
    InstitutionApplyRecord institutionApplyRecord = null;
    try {
      institutionApplyRecord =
          institutionApplyRecordDao.selectApplyRecord(institutionApplyRecordCriteria);
    } catch (SQLException e) {
      logger.error("查询机构审核列表" + institutionApplyRecordCriteria.toString(), e);
    }
    return institutionApplyRecord;
  }

  @Override
  public InstitutionApplyRecord selectApplyRecordByInsCode(
      InstitutionApplyRecordCriteria institutionApplyRecordCriteria) throws Exception {
    InstitutionApplyRecord institutionApplyRecord = null;
    try {
      institutionApplyRecord =
          institutionApplyRecordDao.selectApplyRecordByInsCode(institutionApplyRecordCriteria);
    } catch (SQLException e) {
      logger.error("查询机构审核列表" + institutionApplyRecordCriteria.toString(), e);
    }
    return institutionApplyRecord;
  }

  @Override
  public Map<String, String> checkInsCodeAndName(InstitutionApplyRecord institutionApplyRecord,
      String CS_SESSION_USER_ID) throws Exception {
    Map<String, String> remap = new HashMap<String, String>();
    // 校验机构信息表
    InstitutionInfo institutionIfo = null;
    institutionIfo = institutionInfoDao.queryInsInfoByInsCodeOrInsName(institutionApplyRecord);
    if (institutionIfo != null && institutionIfo.getInstitutionCode() != null
        && institutionApplyRecord.getInstitutionCode()
            .equals(institutionIfo.getInstitutionCode())) {// 机构信息表中已存在相同编号的机构，不能申请！
      remap.put("code", "1");
      remap.put("message", "机构编码(" + institutionApplyRecord.getInstitutionCode() + ")已存在。无法新建申请！");
      return remap;
    }
    if (institutionIfo != null && institutionIfo.getInstitutionName() != null
        && institutionApplyRecord.getInstitutionName()
            .equals(institutionIfo.getInstitutionName())) {// 机构信息表中已存在相同名称的机构，不能申请！
      remap.put("code", "2");
      remap.put("message", "机构名称(" + institutionApplyRecord.getInstitutionName() + ")已存在。无法新建申请！");
      return remap;
    }
    // 校验机构申请表
    InstitutionApplyRecord QueryinstitutionApplyRecord = null;
    QueryinstitutionApplyRecord =
        institutionApplyRecordDao.queryAuditingByInsCodeOrInsName(institutionApplyRecord);
    if (QueryinstitutionApplyRecord != null
        && QueryinstitutionApplyRecord.getInstitutionCode() != null && institutionApplyRecord
            .getInstitutionCode().equals(QueryinstitutionApplyRecord.getInstitutionCode())) {// 相同编号的机构正在审核中，不能申请！
      remap.put("code", "3");
      remap.put("message", "机构编码(" + institutionApplyRecord.getInstitutionCode() + ")审核中。无法新建申请！");
      return remap;
    }
    if (QueryinstitutionApplyRecord != null
        && QueryinstitutionApplyRecord.getInstitutionName() != null && institutionApplyRecord
            .getInstitutionName().equals(QueryinstitutionApplyRecord.getInstitutionName())) {// 相同名称的机构正在审核中，不能申请！
      remap.put("code", "4");
      remap.put("message", "机构名称(" + institutionApplyRecord.getInstitutionName() + ")审核中。无法新建申请！");
      return remap;
    }
    // 检查机构有效性（编码属于当前业务员）
    String bagmanId = "";

    Map<String, Object> bagmanMap = qrcodeApplyRecordService
        .selectBagmanIdByInsCode(institutionApplyRecord.getInstitutionCode());
    if (bagmanMap != null) {
      bagmanId = String.valueOf(bagmanMap.get("BAGMAN_ID"));
    }
    if (!institutionApplyRecord.getInstitutionCode().equals("") && bagmanId.equals("")) {// 机构码无对应的业务员
      logger.error("提交机构申请institutionCode:{}，不存在", institutionApplyRecord.getInstitutionCode());
      remap.put("code", "5");
      remap.put("message", "机构编码：" + institutionApplyRecord.getInstitutionCode() + "不存在");
      return remap;
    } else if (!institutionApplyRecord.getInstitutionCode().equals("")
        && !bagmanId.equals(CS_SESSION_USER_ID)) {// 此机构码不属于当前登录的业务员
      logger.error("提交机构申请institutionCode:{}，不属于当前业务员{}",
          institutionApplyRecord.getInstitutionCode(), CS_SESSION_USER_ID);
      remap.put("code", "6");
      remap.put("message", "机构编码：" + institutionApplyRecord.getInstitutionCode() + "不属于当前业务员");
      return remap;
    }

    remap.put("code", "200");
    return remap;
  }


  @Override
  public Map<String, String> checkInsName(InstitutionApplyRecord institutionApplyRecord)
      throws Exception {
    Map<String, String> remap = new HashMap<String, String>();
    // 校验机构信息表
    InstitutionInfo institutionIfo = null;
    institutionIfo = institutionInfoDao.queryInsInfoByInsName(institutionApplyRecord);
    if (institutionIfo != null && institutionIfo.getInstitutionCode() != null
        && !institutionApplyRecord.getInstitutionCode()
            .equals(institutionIfo.getInstitutionCode())) {// 机构信息表已存在另一条相同名字的机构
      remap.put("code", "1");
      remap.put("message", "机构名称'" + institutionApplyRecord.getInstitutionName() + "'已存在。");
      return remap;
    }
    // 校验机构申请表
    InstitutionApplyRecord QueryinstitutionApplyRecord = null;
    QueryinstitutionApplyRecord =
        institutionApplyRecordDao.queryAuditingByInsName(institutionApplyRecord);
    if (QueryinstitutionApplyRecord != null
        && QueryinstitutionApplyRecord.getInstitutionName() != null && institutionApplyRecord
            .getInstitutionName().equals(QueryinstitutionApplyRecord.getInstitutionName())) {// 相同名称的机构正在审核中，不能申请！
      remap.put("code", "2");
      remap.put("message", "机构名称'" + institutionApplyRecord.getInstitutionName() + "'审核中。");
      return remap;
    }
    remap.put("code", "0");
    remap.put("message", "机构名称'" + institutionApplyRecord.getInstitutionName() + "'有效。");
    return remap;
  }

  // 发送短信方法
  public void sendMessage(String insCod, String mobile) {

  }
}
