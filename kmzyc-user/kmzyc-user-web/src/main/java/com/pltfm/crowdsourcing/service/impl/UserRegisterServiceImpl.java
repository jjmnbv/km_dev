/*
 * package com.pltfm.crowdsourcing.service.impl;
 * 
 * import java.math.BigDecimal; import java.util.Date; import java.util.HashMap; import
 * java.util.Map;
 * 
 * import javax.annotation.Resource;
 * 
 * import org.slf4j.Logger; import org.slf4j.LoggerFactory; import
 * org.springframework.stereotype.Service; import
 * org.springframework.transaction.annotation.Transactional;
 * 
 * import com.kmzyc.b2b.model.User; import com.km.commons.redisutil.RedisTemplate; import
 * com.km.crowdsourcing.common.GlobalVariable; import com.km.crowdsourcing.model.InstitutionInfo;
 * import com.km.crowdsourcing.model.InstitutionUser; import
 * com.km.crowdsourcing.model.RegisterUser; import
 * com.km.crowdsourcing.service.GlobalVariableService; import
 * com.km.crowdsourcing.service.UserRegisterService; import com.pltfm.app.dao.LoginInfoDAO; import
 * com.pltfm.app.util.DateTimeUtils; import com.pltfm.app.util.StringUtils; import
 * com.pltfm.app.vobject.LoginInfo; import com.pltfm.crowdsourcing.dao.InstitutionInfoDao; import
 * com.pltfm.crowdsourcing.dao.InstitutionUserDao; import
 * com.pltfm.crowdsourcing.process.RegistProcess; import
 * com.pltfm.remote.service.CustomerRemoteService;
 * 
 * 
 * @Service("userRegisterService") public class UserRegisterServiceImpl implements
 * UserRegisterService {
 * 
 * private static final Logger logger = LoggerFactory.getLogger(UserRegisterServiceImpl.class);
 * 
 * @Resource CustomerRemoteService customerRemoteService;
 * 
 * @Resource(name = "institutionUserDao") private InstitutionUserDao institutionUserDao;
 * 
 * @Resource(name = "loginInfoDAO") private LoginInfoDAO loginInfoDAO;
 * 
 * @Resource(name = "institutionInfoDao") private InstitutionInfoDao institutionInfoDao;
 * 
 * 
 * @Resource(name = "registProcess") private RegistProcess registProcess;
 * 
 * @Resource(name = "globalVariableService") private GlobalVariableService globalVariableService;
 * 
 * @Resource private RedisTemplate redisTemplate;
 * 
 * @Override
 * 
 * @Transactional(rollbackFor = Exception.class) public Map<String, String> register(RegisterUser
 * registerUser) throws Exception { Map<String, String> map = null;
 * 
 * if (checkUserExists(registerUser.getMobile())) { map = new HashMap<String, String>();
 * map.put("institutionUser", "exist"); return map; } LoginInfo loginInfo = new LoginInfo();
 * loginInfo.setLoginAccount(registerUser.getLoginAccount());
 * loginInfo.setLoginPassword(registerUser.getLoginPassword());
 * loginInfo.setMobile(registerUser.getMobile()); loginInfo.setN_CustomerTypeId(1); // 注册的设备，1：pc
 * 2：wap 3：app loginInfo.setRegister_Device(2); // 注册的平台，1：机构 loginInfo.setRegister_Platfrom(1); map
 * = customerRemoteService.registerLoginInfo(loginInfo, 3); String code =
 * registerUser.getInstitutionCode();
 * 
 * if (map.containsKey("loginId")) { Long loginId = Long.valueOf(map.get("loginId"));
 * 
 * 
 * InstitutionInfo institution = null; if (!StringUtils.isEmpty(code)) { institution =
 * institutionInfoDao.queryInsInfoByInsCode(code); } GlobalVariable globalVariable =
 * globalVariableService.getGlobalVariable(); if (!StringUtils.isEmpty(institution) &&
 * institution.getStatus() == 1 && institution.getActState() == 1 && new
 * Date().before(institution.getSpreadEndDate()) && new
 * Date().after(institution.getSpreadStartDate()) &&
 * "1".equals(globalVariable.getIsFunctionValid())) { try { if
 * (redisTemplate.tryLock(map.get("loginId"))) { InstitutionUser institutionUser = new
 * InstitutionUser(); institutionUser.setClearingAmount(institution.getRegistRebate());
 * institutionUser.setInstitutionCode(registerUser.getInstitutionCode());
 * institutionUser.setLoginId(loginId); Date cdate = DateTimeUtils.getCalendarInstance().getTime();
 * institutionUser.setCreateTime(cdate); institutionUser.setLastModifyDate(cdate); if
 * (institution.getRegistRebate().compareTo(BigDecimal.ZERO) > 0) { // 未结算
 * institutionUser.setClearingStatus((short) 0); } else { // 无须结算
 * institutionUser.setClearingStatus((short) 3); } institutionUser.setStatus((short) 1);
 * institutionUserDao.insertSelective(institutionUser); map.put("institutionUser", "success"); }
 * else { logger.info("key:{},加锁失败，机构用户申请缓存正在创建。请稍候在试。", map.get("loginId")); return map; } }
 * finally { redisTemplate.release(map.get("loginId")); } } else { if
 * (StringUtils.isEmpty(institution)) { logger.info("机构编码不存在,机构编码：{},注册loginId:{}", code,
 * map.get("loginId")); } else if (institution.getStatus() == 0) {
 * logger.info("机构状态为无效,机构编码：{},状态：{},注册loginId:{}", code, institution.getStatus(),
 * map.get("loginId")); } else if (institution.getStatus() == 0) {
 * logger.info("机构用户被冻结,机构编码：{},状态：{},注册loginId:{}", code, institution.getStatus(),
 * map.get("loginId")); } else if ("0".equals(globalVariable.getIsFunctionValid())) {
 * logger.info("机构功能关闭,机构编码：{},状态：{},注册loginId:{}", code, institution.getStatus(),
 * map.get("loginId")); } else { logger.info("机构推广截时间过期,机构编码：{},推广截止日期：{},注册loginId:{}", code,
 * institution.getSpreadEndDate(), map.get("loginId")); } map.put("institutionUser", "success");
 * map.put("mobileIntegral", "no"); }
 * 
 * } else if (map.containsKey("loginAccount")) { map.put("institutionUser", "exist"); } else {
 * map.put("institutionUser", "error"); }
 * 
 * return map;
 * 
 * }
 * 
 * @Override public boolean checkUserExists(String mobie) { int i = 0; try { i =
 * institutionUserDao.checkUserExists(mobie); if (i > 0) { return true; } } catch (Exception e) {
 * logger.error("查询手机号是否注册异常手机号码：{}", mobie, e); return false; } return false; }
 * 
 * 
 * @Override public InstitutionInfo goRegister(String code) { InstitutionInfo institution = null; if
 * (!StringUtils.isEmpty(code)) { try { institution =
 * institutionInfoDao.queryInsInfoByInsCode(code); } catch (Exception e) {
 * logger.error("跳转注册页面获取机构信息失败机构编码：{}", code, e); } } return institution; }
 * 
 * @Override public void mobileIntegral(String loginId, String mobile, String couponID) { User
 * userReg = new User(); userReg.setLoginId(Long.valueOf(loginId)); // 注册送积分优惠券
 * userReg.setLoginAccount("m_" + mobile); userReg.setMobile(mobile); //
 * registProcess.registerIntegral(userReg, couponID); }
 * 
 * }
 */
