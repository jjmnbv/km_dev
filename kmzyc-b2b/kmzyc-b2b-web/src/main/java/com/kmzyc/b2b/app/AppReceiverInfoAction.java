package com.kmzyc.b2b.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.kmzyc.b2b.model.AccountInfo;
import com.kmzyc.b2b.model.Address;
import com.kmzyc.b2b.service.AccountInfoService;
import com.kmzyc.b2b.service.member.AddressService;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.framework.constants.InterfaceResultCode;
import com.kmzyc.util.StringUtil;

/***
 * 
 * @author Administrator 获取用户收货人列表
 */
@SuppressWarnings("unchecked")
@Scope("prototype")
@Controller("appReceiverInfoAction")
public class AppReceiverInfoAction extends AppBaseAction {
  private static final long serialVersionUID = 1L;
  private List<Address> addressList = new ArrayList<Address>();
  private String uid;// 用户ID
  @Resource(name = "addressServiceImpl")
  private AddressService addressService;

  @Resource(name = "accountInfoServiceImp")
  private AccountInfoService accountinfoService;

  private ReturnResult returnResult;

  // 登录用户IDkey前缀
  // private static Logger log = LoggerFactory.getLogger(AppReceiverInfoAction.class);
  private static Logger log = LoggerFactory.getLogger(AppReceiverInfoAction.class);

  /***
   * //获取用户收货人列表
   * 
   * @return
   * @throws Exception
   */
  public void getReceiverInfoList() {
    JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
    if (null != jsonParams && !jsonParams.isEmpty()) {
      uid = jsonParams.getString("uid");
      log.info("获取用户收获列表成功");
      try {
        int userid = Integer.parseInt(uid);
        addressList = addressService.findByLoginId(userid, false);
        returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "获取用户收获列表成功", addressList);
      } catch (Exception e) {
        log.error("app获取收货地址列表异常" + e.getMessage(), e);
        returnResult = new ReturnResult(InterfaceResultCode.FAILED, "获取用户收获列表异常", null);
      }
    } else {
      log.error("未登录!");
      returnResult =
          new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED, "未登录!", null);
    }
    printJsonString(returnResult);
  }


  /***
   * 
   * 编辑保存收货列表 saveReceiverInfo
   * 
   */

  public void saveReceiverInfo() {
    log.info("保存收货人修改收获人信息手机APP接口方法");
    String message = "用户未登录！";
    String code = InterfaceResultCode.FAILED;
    JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
    if (null != jsonParams && !jsonParams.isEmpty()) {
      int operationType = jsonParams.getIntValue("operationType");// 操作类型 1：新增；2：修改;3:删除
      String uid = jsonParams.getString("uid");
      int userId = Integer.parseInt(uid);
      String addressId = jsonParams.getString("addressId");
      Address address = new Address();
      String name = jsonParams.getString("name");
      String city = jsonParams.getString("city");
      String mobile = jsonParams.getString("mobile");
      String area = jsonParams.getString("area");
      String detailedAddress = jsonParams.getString("detailedAddress");
      String postalcode = jsonParams.getString("postalcode");
      String province = jsonParams.getString("province");
      String telePhone = jsonParams.getString("telePhone");
      int status = jsonParams.getIntValue("status");
      try {
        AccountInfo accountInfo = accountinfoService.findByLoginId(Long.valueOf(userId));

        Integer checkAccountId = accountInfo.getNaccountId();
        if (operationType == 1) {// 新增
          // 获取后台地址数量
          int countAddress = addressService.queryAddressAccountById(userId);

          if (StringUtil.isMobile(mobile)) {

            if (countAddress < 10) {
              address.setProvince(province);
              address.setCity(city);
              address.setArea(area);
              address.setName(name);
              address.setPostalcode(postalcode);
              address.setDetailedAddress(detailedAddress);
              address.setCellphone(mobile);
              address.setTelephone(telePhone);
              address.setAccountId(checkAccountId);
              address.setCreatedate(new Date());
              address.setLastupdate(new Date());
              address.setStatus(status);
              address.setLoginId(userId);
              Integer addressIds = addressService.save(address);
              address.setAddressId(addressIds);
              // 添加地址信息到数据库，并缓存到memcache
              addressService.addAddressTomem(uid, address);
              code = InterfaceResultCode.SUCCESS;
              message = "添加收获人信息成功";
            } else {
              message = "收货地址最多10个";
            }
          } else {
            message = "手机号码格式错误";
          }
        } else if (operationType == 2) {// 修改
          if (StringUtils.isNotBlank(addressId)) {
            int addId = Integer.parseInt(addressId);
            address = this.addressService.findByNAddressID(userId, addId);
            if (StringUtils.isNotBlank(province)) {
              address.setProvince(province);
            }
            if (StringUtils.isNotBlank(province)) {
              address.setCity(city);
            }
            if (StringUtils.isNotBlank(area)) {
              address.setArea(area);
            }
            if (StringUtils.isNotBlank(name)) {
              address.setName(name);
            }
            if (StringUtils.isNotBlank(postalcode)) {
              address.setPostalcode(postalcode);
            }
            if (StringUtils.isNotBlank(detailedAddress)) {
              address.setDetailedAddress(detailedAddress);
            }
            if (StringUtils.isNotBlank(mobile)) {
              address.setCellphone(mobile);
            }

            address.setAccountId(checkAccountId);
            address.setLoginId(userId);
            address.setAddressId(addId);
            if (StringUtils.isNotBlank(telePhone)) {
              address.setTelephone(telePhone);
            }
            if (status == 0 || status == 1) {
              address.setStatus(status);
            }
            if (StringUtils.isNotBlank(mobile) && StringUtil.isMobile(mobile)) {
              address.setCellphone(mobile);
            }
            address.setCreatedate(new Date());
            address.setLastupdate(new Date());

            if (StringUtil.isEmpty(mobile) || StringUtil.isMobile(mobile)) {

              // 修改收货人
              addressService.update(address);
              code = InterfaceResultCode.SUCCESS;
              message = "修改收获人信息成功";
              addressList = this.addressService.findByLoginId(userId, false);
            } else {
              message = "手机号码格式错误";
            }

          } else {
            message = "请求参数异常";
          }

        } else if (operationType == 3) {// 删除
          if (StringUtils.isNotBlank(addressId)) {
            addressService.delete(Long.valueOf(userId), Integer.parseInt(addressId));
            code = InterfaceResultCode.SUCCESS;
            message = "删除收获人信息成功";
          } else {
            message = "请求参数异常";
          }
        } else {
          message = "请求参数异常";
        }
      } catch (Exception e) {
        log.error("编辑收货地址异常" + e.getMessage(), e);
        message = "编辑收货地址异常!";
      }
    } else {
      log.error("参数异常!");
      message = "参数异常!";
    }
    returnResult = new ReturnResult(code, message, null);
    printJsonString(returnResult);
  }

  public ReturnResult getReturnResult() {
    return returnResult;
  }

  public void setReturnResult(ReturnResult returnResult) {
    this.returnResult = returnResult;
  }
}
