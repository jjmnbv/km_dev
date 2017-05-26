package com.kmzyc.b2b.action.member;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.framework.action.BaseAction;
import com.kmzyc.b2b.model.AccountInfo;
import com.kmzyc.b2b.model.Address;
import com.kmzyc.b2b.service.AccountInfoService;
import com.kmzyc.b2b.service.member.AddressService;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.constants.InterfaceResultCode;

/**
 * 会员收货地址
 * 
 * @author lijianjun
 * 
 */
@SuppressWarnings({"serial", "unchecked"})
@Controller("deliveryAddressAction")
@Scope("prototype")
public class DeliveryAddressAction extends BaseAction {
  // private static Logger logger = Logger.getLogger(DeliveryAddressAction.class);
  private static Logger logger = LoggerFactory.getLogger(DeliveryAddressAction.class);

  @Resource(name = "accountInfoServiceImp")
  private AccountInfoService accountinfoService;

  @Resource(name = "addressServiceImpl")
  private AddressService addressService;

  private String toPerform;
  private List<Address> addressList = new ArrayList<Address>();
  private Address address;
  private boolean status;
  // 返回至页面的对象
  private ReturnResult returnResult;

  /**
   * 获取对应登陆id收获地址数量
   * 
   * @return
   */
  public String isOverAddressCount() {
    // 获取缓存用户id
    Long userId = (Long) getSession().getAttribute(Constants.SESSION_USER_ID);
    logger.info("ajax获取当前用户收货地址数量,参数：userID：" + userId);
    int count = 0;
    try {
      count = addressService.queryAddressAccountById(userId.intValue());
      getResponse().getWriter().print(count);
    } catch (Exception e) {
      logger.error("获取收获地址数量sql异常：" + e.getMessage(), e);
    }
    return null;
  }

  /**
   * 进入收获地址页面并查询当前用户已设置的收获地址
   * 
   * @return
   * @throws SQLException
   */
  public String goDeliveryAddress() {
    // 获取缓存用户id
    HttpSession session = getSession();
    Long userId = (Long) session.getAttribute(Constants.SESSION_USER_ID);
    logger.info("初始化当前用户收货地址列表，参数：userID " + userId);
    try {
      this.addressList = addressService.findByLoginId(userId.intValue(), false);
    } catch (Exception e) {
      logger.error("初始化收货地址失败" + e.getMessage(), e);
      return ERROR;
    }
    // 绑定session
    getRequest().setAttribute("addressList", addressList);
    return SUCCESS;
  }

  /**
   * Wap进入收获地址页面并查询当前用户已设置的收获地址
   * 
   * @return
   * @throws SQLException
   */
  public String goWapDeliveryAddress() {
    // 获取缓存用户id
    HttpSession session = getSession();
    Long userId = (Long) session.getAttribute(Constants.SESSION_USER_ID);
    logger.info("初始化当前用户收货地址列表，参数：userID " + userId);
    try {
      this.addressList = addressService.findByLoginId(userId.intValue(), false);
    } catch (Exception e) {
      logger.error("初始化收货地址失败" + e.getMessage(), e);
      return ERROR;
    }
    // 绑定session
    getRequest().setAttribute("addressList", addressList);
    return SUCCESS;
  }

  /**
   * 进入修改和添加的收货地址页面
   * 
   * @throws SQLException
   * @return
   */
  public String goDeliveryAddressSet() {
    // 获取缓存用户id
    HttpSession session = getSession();
    Long userId = (Long) session.getAttribute(Constants.SESSION_USER_ID);
    logger.info("初始化编辑收货地址，参数：userID：" + userId);
    try {
      // 查询账号id
      AccountInfo accountInfo = accountinfoService.findByLoginId(userId);
      Integer checkAccountId = accountInfo.getNaccountId();
      // 是否存在addressId 不为空就获取当前对象 修改
      String addressId = getRequest().getParameter("addressId");
      if (StringUtils.isNotBlank(addressId)) {
        // 根据收货地址id查询单个收货地址
        address = addressService.findByNAddressID(userId, Integer.valueOf(addressId));
        if (checkAccountId != address.getAccountId()) {
          logger
              .error("非当前用户修改该收货地址，currentUserID：" + userId + ",收货地址id:" + address.getAddressId());
          return ERROR;
        }
        return SUCCESS;
      } else {
        // 添加
        // 获取参数accountId 为空判断并处理
        int accountId = address.getAccountId();
        if (accountId == 0) {
          // 初次添加收获地址获取accountId绑定
          address.setAccountId(checkAccountId);
        }
        if (checkAccountId != address.getAccountId()) {
          logger
              .error("非当前用户添加该收货地址，currentUserID：" + userId + ",收货地址id:" + address.getAddressId());
          return ERROR;
        }

      }
    } catch (Exception e) {
      logger.error("进入添加修改收货地址页面失败" + e.getMessage(), e);
      return ERROR;
    }
    return SUCCESS;
  }

  /**
   * wap进入修改和添加的收货地址页面
   * 
   * @throws SQLException
   * @return
   */
  public String goWapDeliveryAddressSet() {
    // 获取缓存用户id
    HttpSession session = getSession();
    Long userId = (Long) session.getAttribute(Constants.SESSION_USER_ID);
    logger.info("初始化编辑收货地址，参数：userID：" + userId);
    try {
      AccountInfo accountInfo = accountinfoService.findByLoginId(userId);
      Integer checkAccountId = accountInfo.getNaccountId();
      // 是否存在addressId 不为空就获取当前对象 修改
      if (StringUtils.isNotBlank(getRequest().getParameter("addressId"))) {
        // 根据收货地址id查询单个收货地址
        address =
            addressService.findByNAddressID(userId,
                Integer.valueOf(getRequest().getParameter("addressId")));
        if (checkAccountId != address.getAccountId()) {
          logger
              .error("非当前用户修改该收货地址，currentUserID：" + userId + ",收货地址id:" + address.getAddressId());
          return ERROR;
        }
        return SUCCESS;
      } else {
        // 添加
        // 获取参数accountId 为空判断并处理
        int accountId = address.getAccountId();
        if (accountId == 0) {
          // 初次添加收获地址获取accountId绑定
          address.setAccountId(checkAccountId);
        }
        if (checkAccountId != address.getAccountId()) {
          logger
              .error("非当前用户添加该收货地址，currentUserID：" + userId + ",收货地址id:" + address.getAddressId());
          return ERROR;
        }

      }
    } catch (Exception e) {
      logger.error("进入添加修改收货地址页面失败" + e.getMessage(), e);
      return ERROR;
    }
    return SUCCESS;
  }

  /**
   * 添加和修改
   * 
   * @return
   * @throws Exception
   */
  public String editAction() {
    if (address.getAddressId() != 0) {
      return updateAddress();
    } else {
      return addDeliveryAddress();
    }
  }

  /**
   * 添加收货地址
   * 
   * @return
   */
  public String addDeliveryAddress() {
    address.setStatus(this.status ? 0 : 1);
    // 获取缓存用户id
    Long userId = (Long) getSession().getAttribute(Constants.SESSION_USER_ID);
    logger.info("新增收货地址：参数：userID：" + userId);
    // 获取accountId
    String accountId = getRequest().getParameter("address.accountId");
    try {
      // 根据loginId获取accoundId
      AccountInfo accountInfo = accountinfoService.findByLoginId(userId);
      Integer accoundid = accountInfo.getNaccountId();
      if (Integer.valueOf(accountId).compareTo(accoundid) != 0) {
        logger.error("非当前用户添加收货地址，currentUserID：" + userId);
        return ERROR;
      }
      address.setLoginId(userId.intValue());
      Integer backInt = addressService.save(address);
      if (backInt != 0) {
        return SUCCESS;
      }
    } catch (Exception e) {
      logger.error("添加收货地址信息出错：" + e.getMessage(), e);
      return ERROR;
    }
    return SUCCESS;
  }

  /**
   * 修改收货地址信息
   * 
   * @return
   */
  public String updateAddress() {
    logger.info("修改收货地址");
    Long userId = (Long) getSession().getAttribute(Constants.SESSION_USER_ID);
    // 是否默认
    address.setStatus(this.status ? 0 : 1);
    address.setLoginId(userId.intValue());
    // int backInt = 0;
    try {
      /* backInt = */addressService.update(address);
    } catch (Exception e) {
      logger.error("修改收获地址失败" + e.getMessage(), e);
      return ERROR;
    }
    // if (backInt == 0) {
    // return ERROR;
    // }
    return SUCCESS;
  }

  /**
   * 单条删除收货地址信息
   * 
   * @param id
   * @return
   */
  public String deleteById() {
    Integer addressId =
        StringUtils.isBlank(getRequest().getParameter("addressId")) ? null : Integer
            .valueOf(getRequest().getParameter("addressId"));
    // 获取缓存用户id
    HttpSession session = getSession();
    Long userId = (Long) session.getAttribute(Constants.SESSION_USER_ID);
    logger.info("单条删除收货地址，参数：addressID:" + userId);
    try {
      // 使用cache 之后 无法实现该操作
      // Integer loginId = addressService.findLoginIdByAddressId(addressId);
      // Long id = loginId.longValue();
      // if (id.compareTo(userId) != 0) {
      // logger.error("非当前用户删除收货地址，currentUserID：" + userId);
      // return ERROR;
      // }
      addressService.delete(userId, addressId);
    } catch (Exception e) {
      logger.error("删除收货地址信息出错" + e.getMessage(), e);
      return ERROR;
    }
    return SUCCESS;
  }

  /**
   * ajax收获地址页面并查询当前用户已设置的收获地址
   * 
   * @return
   * @throws SQLException
   */
  public String ajaxDeliveryAddress() {
    // 获取缓存用户id
    HttpSession session = getSession();
    Long userId = (Long) session.getAttribute(Constants.SESSION_USER_ID);
    logger.info("ajax初始化收货地址，参数：userID" + userId);
    try {
      this.addressList = addressService.findByLoginId(userId.intValue());
    } catch (Exception e) {
      returnResult = new ReturnResult(InterfaceResultCode.FAILED, "初始化收货地址失败", addressList);
      logger.error("初始化收货地址失败" + e.getMessage(), e);
    }
    returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "获取收货地址成功", addressList);
    return SUCCESS;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public boolean isStatus() {
    return status;
  }

  public void setStatus(boolean status) {
    this.status = status;
  }

  public List<Address> getAddressList() {
    for (Address a : addressList) {
      System.out.println(a.getAddressId());
    }

    return addressList;
  }

  public void setAddressList(List<Address> addressList) {

    this.addressList = addressList;
  }

  public String getToPerform() {
    return toPerform;
  }

  public void setToPerform(String toPerform) {
    this.toPerform = toPerform;
  }

  public ReturnResult getReturnResult() {
    return returnResult;
  }

  public void setReturnResult(ReturnResult returnResult) {
    this.returnResult = returnResult;
  }
}
