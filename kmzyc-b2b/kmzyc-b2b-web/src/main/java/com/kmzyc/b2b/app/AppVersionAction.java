package com.kmzyc.b2b.app;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.kmzyc.b2b.model.CmsAppManager;
import com.kmzyc.b2b.service.AppVersionService;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.constants.InterfaceResultCode;

@Scope("prototype")
@Controller("appVersionAction")
public class AppVersionAction extends AppBaseAction {

  // private static Logger logger = Logger.getLogger(AppVersionAction.class);
  private static Logger logger = LoggerFactory.getLogger(AppVersionAction.class);
  /**
   * 序列化
   */
  private static final long serialVersionUID = 3748493139163071352L;

  private String message = "请求参数错误";
  private String code = InterfaceResultCode.FAILED;
  private ReturnResult<Map<String, Object>> returnResult;

  @Resource(name = "appVersionServiceImpl")
  private AppVersionService appVersionService;

  private Integer IS_COERCE = 1;// 强制更新是否，1为是，2为否

  private String APP_TYPE_ANDROID = "02";
  private String APP_TYPE_IOS = "01";

  /**
   * 查询会员的版本信息
   * 
   * @return
   */
  public void queryVersion() {
    try {
      Integer versionCode;
      JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());

      if (null != jsonParams && !jsonParams.isEmpty()) {
        logger.info("开始检查版本信息，传入的参数为：版本号：【" + jsonParams.getString("version") + "】，系统类型【"
                + jsonParams.getString("appType") + "】");
        String version = jsonParams.getString("version");
        String appType = jsonParams.getString("appType");
        if (appType == null) {
          message = "appType 不能为空,参数不合法";
          returnResult = new ReturnResult<Map<String, Object>>(code, message, null);
        }
        if (Constants.APP_TYPE_IOS.equalsIgnoreCase(appType)) {// ios情况
          versionCode = 1;//
          if (version == null) {
            message = "version 不能为空,参数不合法";
            returnResult = new ReturnResult<Map<String, Object>>(code, message, null);
          }
        } else {
          versionCode = jsonParams.getInteger("versionCode");
          if (versionCode == null) {
            message = "versionCode 不能为空,参数不合法";
            returnResult = new ReturnResult<Map<String, Object>>(code, message, null);
          }
        }
        logger.info("传入的参数version为【" + version + "】，平台类型appType为：【" + appType + "】,versionCode为:【"
            + versionCode + "】");
        List<CmsAppManager> cmsAppManagerListTemp = new ArrayList<CmsAppManager>();
        boolean isHightestVersion = true;
        CmsAppManager cmsAppManager;
        List<CmsAppManager> cmsAppManagerList;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("isCoerce", 2);
        if (Constants.APP_TYPE_IOS.equalsIgnoreCase(appType)) {// ios情况
          BigDecimal versionNum = new BigDecimal(version);
          cmsAppManagerList = appVersionService.queryCmsAppManagerList("IOS", version);// 获取版本号与传入版本号不同的数据
          logger.info("与当前IOS版本不同的版本有【" + cmsAppManagerList.size() + "】个");
          for (int i = 0; i < cmsAppManagerList.size(); i++) {
            BigDecimal versionTemp = new BigDecimal(cmsAppManagerList.get(i).getVersion());
            if (versionTemp.compareTo(versionNum) == 1) {// BigDecimal
              // 比较大小0表示等于，-1表示小于，1表示大于
              cmsAppManagerListTemp.add(cmsAppManagerList.get(i));// 筛选出比传入版本号更高版本的数据
            }
          }
          if (cmsAppManagerListTemp != null && cmsAppManagerListTemp.size() != 0) {
            for (int i = 0; i < cmsAppManagerListTemp.size(); i++) {
              cmsAppManager = cmsAppManagerListTemp.get(i);
              logger.info("比传入的版本更高版本的数数据信息：版本号【" + cmsAppManager.getVersionCode()
                  + "】,是否强制更新 1：是;2：否。【" + cmsAppManager.getIsCoerce() + "】");
              BigDecimal versionTmp = new BigDecimal(cmsAppManagerListTemp.get(i).getVersion());
              if (IS_COERCE.compareTo(cmsAppManagerListTemp.get(i).getIsCoerce()) == 0) {
                map.put("isCoerce", 1);// 更高版本号中只要有一个版本是强制更新就返回强制更新
              }
              if (versionTmp.compareTo(versionNum) == 1) {
                isHightestVersion = false;
                versionNum = versionTmp;
                map.put("version", cmsAppManager.getVersion());
                map.put("versionCode", cmsAppManager.getVersionCode());
                map.put("downLoad", cmsAppManager.getDownLoad());
                map.put("remark", cmsAppManager.getRemark());
                map.put("isLatestEdition", "2");
              }
            }
            if (isHightestVersion) {
              map.put("isLatestEdition", "1");
              map.put("version", null);
              map.put("downLoad", null);
              map.put("remark", null);
              map.put("isCoerce", null);
              map.put("versionCode", null);
            }
          } else {
            map.put("isLatestEdition", "1");
            map.put("version", null);
            map.put("downLoad", null);
            map.put("remark", null);
            map.put("isCoerce", null);
            map.put("versionCode", null);
          }
        } else {
          cmsAppManagerList = appVersionService.querCmsAppManagerListAndroid(versionCode);// 获取版本号与传入版本号不同的数据
          logger.info("与当前android版本不同的版本有【" + cmsAppManagerList.size() + "】个");

          for (int i = 0; i < cmsAppManagerList.size(); i++) {
            if (versionCode.compareTo(cmsAppManagerList.get(i).getVersionCode()) < 0) {
              cmsAppManagerListTemp.add(cmsAppManagerList.get(i));// 筛选出比传入版本号更高版本的数据
            }
          }
          logger.info("比传入版本号高的版本共有【" + cmsAppManagerListTemp.size() + "】个");
          if (cmsAppManagerListTemp != null && cmsAppManagerListTemp.size() != 0) {
            for (int i = 0; i < cmsAppManagerListTemp.size(); i++) {
              cmsAppManager = cmsAppManagerListTemp.get(i);
              // logger.info("名称为【" + cmsAppManager.getName() +
              // "】的是否强制更新属性为：（1为是，2为否）【" +
              // cmsAppManager.getIsCoerce() + "】");
              if (IS_COERCE.compareTo(cmsAppManagerListTemp.get(i).getIsCoerce()) == 0) {
                map.put("isCoerce", 1);// 更高版本号中只要有一个版本是强制更新就返回强制更新
              }
              if (versionCode.compareTo(cmsAppManager.getVersionCode()) < 0) {
                isHightestVersion = false;
                versionCode = cmsAppManager.getVersionCode();
                map.put("version", cmsAppManager.getVersion());
                map.put("versionCode", cmsAppManager.getVersionCode());
                map.put("downLoad", cmsAppManager.getDownLoad());
                map.put("remark", cmsAppManager.getRemark());
                map.put("isLatestEdition", "2");
              }
            }
            if (isHightestVersion) {
              map.put("isLatestEdition", "1");
              map.put("version", null);
              map.put("downLoad", null);
              map.put("remark", null);
              map.put("isCoerce", null);
              map.put("versionCode", null);
            }
          } else {
            map.put("isLatestEdition", "1");
            map.put("version", null);
            map.put("downLoad", null);
            map.put("remark", null);
            map.put("isCoerce", null);
            map.put("versionCode", null);
          }
        }

        returnResult =
            new ReturnResult<Map<String, Object>>(InterfaceResultCode.SUCCESS, "成功", map);
      } else {
        returnResult = new ReturnResult<Map<String, Object>>(code, message, null);
      }
    } catch (Exception e) {
      logger.error("内部出错" + e.getMessage(), e);
      returnResult = new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED, "失败", null);
    }

    printJsonString(returnResult);
  }

  /**
   * 检查更新版本
   * 
   * @return
   */
  public void queryUpdateVersion() {
    try {
      JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
      // 更新版本信息
      Map<String, Object> map = new HashMap<String, Object>();
      CmsAppManager cmsAppManager = null;
      Integer isCoerce = 1;
      // 接口调用结构200：成功，0:失败
      String code = InterfaceResultCode.FAILED;
      // 返回信息
      String message = "失败";

      if (null != jsonParams && !jsonParams.isEmpty()) {
        logger.info("开始检查版本信息，传入的参数为：" + jsonParams.toJSONString());
        String appType = jsonParams.getString("userType");
        String version = jsonParams.getString("userId");
        // 判断参数是否为空
        if (StringUtils.isEmpty(version) || StringUtils.isEmpty(appType)) {
          message = "userid,apptype为空或参数不合法";
          returnResult = new ReturnResult<Map<String, Object>>(code, message, null);
        } else {
          if (appType.equals(APP_TYPE_ANDROID)) {
            appType = Constants.APP_TYPE_ANDROID.toUpperCase();
          } else if (appType.equals(APP_TYPE_IOS)) {
            appType = Constants.APP_TYPE_IOS.toUpperCase();
          } else {
            appType = null;
          }
          if (appType == null) {
            message = "appType参数不合法";
            returnResult = new ReturnResult<Map<String, Object>>(code, message, null);
          } else {
            logger.info("传入的参数userid为【" + version + "】，平台类型appType为：【" + appType + "】 ");
            // 验证传过来的版本号是否存在
            cmsAppManager = appVersionService.getByVersionAndType(appType, version);
            if (cmsAppManager == null) {
              message = "userid 不存在,参数不合法";
              returnResult = new ReturnResult<Map<String, Object>>(code, message, null);
            } else {// 参数验证通过后查找到需要更新版本返回
              List<CmsAppManager> lasterAppList =
                  appVersionService.getLasterAppList(appType, cmsAppManager.getVersionCode());
              code = InterfaceResultCode.SUCCESS;
              message = "成功";
              if (lasterAppList != null && lasterAppList.size() > 0) {
                cmsAppManager = lasterAppList.get(0);
                // 有一个版本需要强制更新，则返回强制更新
                for (CmsAppManager app : lasterAppList) {
                  if (app.getIsCoerce() == 1) {
                    isCoerce = 3;
                  }
                }
              } else {
                // 没有新版本，无需更新
                isCoerce = 0;
              }
            }
          }
        }
      }
      // cmsAppManager不为空返回需要更新的版本
      if (cmsAppManager != null) {
        map.put("userCode", cmsAppManager.getVersionCode().toString());
        map.put("userId", cmsAppManager.getVersion());
        map.put("userType", isCoerce);
        map.put("remark", cmsAppManager.getName());
        map.put("userAddr", cmsAppManager.getDownLoad());
      }
      returnResult = new ReturnResult<Map<String, Object>>(code, message, map);
    } catch (Exception e) {
      logger.error("内部出错" + e.getMessage(), e);
      returnResult = new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED, "失败", null);
    }
    printJsonString(returnResult);
  }


  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public ReturnResult<Map<String, Object>> getReturnResult() {
    return returnResult;
  }

  public void setReturnResult(ReturnResult<Map<String, Object>> returnResult) {
    this.returnResult = returnResult;
  }

}
