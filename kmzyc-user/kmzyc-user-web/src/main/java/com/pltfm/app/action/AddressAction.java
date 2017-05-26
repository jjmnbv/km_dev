package com.pltfm.app.action;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.service.AccountInfoService;
import com.pltfm.app.service.AddressService;
import com.pltfm.app.service.LoginInfoService;
import com.pltfm.app.util.ConfigureUtils;
import com.pltfm.app.util.StringUtils;
import com.pltfm.app.util.Token;
import com.pltfm.app.vobject.AccountInfo;
import com.pltfm.app.vobject.Address;
import com.pltfm.app.vobject.LoginInfo;


/**
 * 收货地址action处理类
 * 
 * @author zhl
 * @since 2013-07-11
 */
@Component(value = "addressAction")
@Scope(value = "prototype")
public class AddressAction extends BaseAction implements ModelDriven {
   
  private static final long serialVersionUID = -579941341825593085L;
  
  @Autowired
  private AddressService addressService;
  @Resource(name = "loginInfoService")
  private LoginInfoService loginInfoService;
  @Autowired
  private AccountInfoService accountInfoService;

  /** 分页对象 **/
  private Page page;
  private Address address;
  /** 需要管理的某一个账号的登录id **/
  private String accountLoginId;
  /** 地址信息主键 **/
  private String addressId;
  /** 地址信息主键集合 **/
  private List<String> addressIds;
  /** 公共页面返回列表类型 **/
  private Integer showType;
  private Integer login_Id;
  private Integer viewOnly;
  
  private  Map<String,String> clearResult ;
  

  /**
   * 进入添加地址信息页面
   * 
   * @return
   */
  public String operateAdd() {
    return "addSuccess";
  }

  /**
   * 保存地址信息
   * 
   * @return
   */
  @Token
  public String operateSave() {
    try {
      if (address != null && address.getN_addressId() != null) {
        operateUpdate();
      } else {
        addressService.addAddress(address);
        this.addActionMessage(ConfigureUtils.getMessageConfig("address.save.success"));
      }
      // address = new Address();
      return queryPageList();
    } catch (Exception e) {
      e.printStackTrace();
      this.addActionError(ConfigureUtils.getMessageConfig("address.operate.fail"));
      return "addSuccess";
    }
  }

  /**
   * 修改地址信息
   * 
   * @return
   */
  public String operateUpdate() {
    try {
      addressService.updateAddress(address);
      this.addActionMessage(ConfigureUtils.getMessageConfig("address.update.success"));
      return "updateSuccess";
    } catch (Exception e) {
      e.printStackTrace();
      this.addActionError(ConfigureUtils.getMessageConfig("address.operate.fail"));
      return "addSuccess";
    }
  }

  /**
   * 分页查询收货地址信息
   */
  public String queryPageList() {
    try {
      if (page == null) {
        page = new Page();
      }
      if (address == null) {
        address = new Address();
      }
      if (null != showType) {
        LoginInfo loginfos = loginInfoService.getLoginId(login_Id);
        if (null != loginfos) {
          String customerNames = StringUtils.isStringNull(loginfos.getLoginAccount());
          address.setAccountLogin(customerNames);
        }
      }

      address.setCellphone(null);
      page = addressService.queryForPageList(address, page);
      return "querySuccess";
    } catch (Exception e) {
      e.printStackTrace();
      this.addActionError(ConfigureUtils.getMessageConfig("address.query.fail"));
      return "querySuccess";
    }
  }

  /**
   * 通过主键查找地址信息
   * 
   * @return
   */
  public String operateEdit() {
    try {
      address = addressService.queryByPrimaryKey(Long.valueOf(accountLoginId),
          Integer.valueOf(addressId));
      return "editSuccess";
    } catch (Exception e) {
      e.printStackTrace();
      this.addActionError(ConfigureUtils.getMessageConfig("address.query.fail"));
      return "addSuccess";
    }
  }

  /**
   * 多条删除地址信息
   * 
   * @return
   */
  public String operateDeleteAll() {
    try {
      addressService.deleteAddressList(Long.valueOf(accountLoginId), addressIds);
      this.addActionMessage(ConfigureUtils.getMessageConfig("address.delete.success"));
      address.setLoginId(Integer.valueOf(accountLoginId));
      return queryPageList();
    } catch (Exception e) {
      e.printStackTrace();
      this.addActionError(ConfigureUtils.getMessageConfig("address.delete.fail"));
      return queryPageList();
    }
  }

  /**
   * 单条删除地址信息
   * 
   * @return
   */
  public String operateDelete() {
    try {
      addressService.deleteAddress(Long.valueOf(accountLoginId), Integer.valueOf(addressId));
      this.addActionMessage(ConfigureUtils.getMessageConfig("address.delete.success"));
      return queryPageList();
    } catch (Exception e) {
      e.printStackTrace();
      this.addActionError(ConfigureUtils.getMessageConfig("address.delete.fail"));
      return queryPageList();
    }
  }
  
  public String clearIncorrectCacheAddress(){
      try {
          HttpServletRequest request = ServletActionContext.getRequest();
          int pageSize = 100;
          int threadCount=80;
          boolean isDebug =false;
          int debugNum = 0;
          int debugLoginId =0;
          boolean isClear =false;
          String mobile="";
          
          if(!StringUtils.isEmpty(request.getParameter("pageSize"))){
              pageSize = Integer.parseInt(request.getParameter("pageSize"));
          }
          if(!StringUtils.isEmpty(request.getParameter("threadCount"))){
              threadCount = Integer.valueOf(request.getParameter("threadCount"));
          }
          
          if(!StringUtils.isEmpty(request.getParameter("isClear"))&&"true".equals(request.getParameter("isClear"))){
              isClear=true;
          }
          
          if(!StringUtils.isEmpty(request.getParameter("mobile"))){
              mobile = request.getParameter("mobile");
          }
          
          if(!StringUtils.isEmpty(request.getParameter("isDebug"))&&"true".equals(request.getParameter("isDebug"))){
              isDebug=true;
              if(!StringUtils.isEmpty(request.getParameter("debugNum"))){
                  debugNum = Integer.parseInt(request.getParameter("debugNum"));
              }
              if(!StringUtils.isEmpty(request.getParameter("debugLoginId"))){
                  debugLoginId = Integer.parseInt(request.getParameter("debugLoginId"));
              }
          }
          
          //如果是查错模式
          if(isDebug){
             AccountInfo accountInfo =  accountInfoService.selectByPrimaryLoginInfo(debugLoginId);
              
              if(accountInfo!=null){
                  Address address = new Address();
                  address.setAccountLogin(accountInfo.getLoginAccount());
                  address.setAddName(UUID.randomUUID().toString());
                  address.setArea("福田区");
                  address.setCellphone("15345673219");
                  address.setCity("深圳市");
                  address.setD_createdate(new Date());
                  address.setD_lastupdate(new Date());
                  address.setEmail("xx_xx@km.com");
                  address.setDetailedAddress("国际创新大厦7楼10号");
                  address.setIsChecked(false);
                  address.setLoginId(debugLoginId);
                  address.setN_accountId(accountInfo.getN_AccountId());
                  address.setName("张飞");
                  address.setPostalcode("538124");
                  address.setProvince("广东省");
                  address.setStatus(1);
                  address.setTelephone("0755-2345678");
                  
                  switch(debugNum){
                      case 1: address.setProvince("");break;
                      case 2: address.setArea("");;break;
                      case 3: address.setCity("");break;
                      case 4: address.setCellphone("1534567321");break;
                      case 5: address.setDetailedAddress("");break;
                      default:break;
                  }
                  addressService.addAddress(address);
              }
          }
          
          if(isClear){
              clearResult = addressService.clearIncorrectCacheAddress(pageSize,threadCount,mobile);    
          }else{
              //如果不存在直接导入传入一个不可能存在的电话号码，查看之前删除的结果
              clearResult = addressService.clearIncorrectCacheAddress(pageSize,threadCount,"XXXXXXXXXXX");
          }
          
          request.setAttribute("clearResult", clearResult);
          
      } catch (Exception e) {
        e.printStackTrace();
      }
      return "clearPage";
  }
  
  /**
   * 查询账户号
   */
  public void checkAddressNum() {
    try {

      super.writeJson(addressService.queryAddressCountByLoginId(login_Id));
    } catch (Exception e) {
        log.error("获取地址数量信息");
    }
  }
  

  @Override
public Page getPage() {
    return page;
  }

  public void setPage(Page page) {
    this.page = page;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public String getAddressId() {
    return addressId;
  }

  public void setAddressId(String addressId) {
    this.addressId = addressId;
  }

  public List<String> getAddressIds() {
    return addressIds;
  }

  public void setAddressIds(List<String> addressIds) {
    this.addressIds = addressIds;
  }

  public Integer getShowType() {
    return showType;
  }

  public void setShowType(Integer showType) {
    this.showType = showType;
  }


  public LoginInfoService getLoginInfoService() {
    return loginInfoService;
  }

  public void setLoginInfoService(LoginInfoService loginInfoService) {
    this.loginInfoService = loginInfoService;
  }

  public Integer getLogin_Id() {
    return login_Id;
  }

  public void setLogin_Id(Integer login_Id) {
    this.login_Id = login_Id;
  }

  public Integer getViewOnly() {
    return viewOnly;
  }

  public void setViewOnly(Integer viewOnly) {
    this.viewOnly = viewOnly;
  }

  @Override
  public Object getModel() {
    address = new Address();
    return address;
  }

  public String getAccountLoginId() {
    return accountLoginId;
  }

  public void setAccountLoginId(String accountLoginId) {
    this.accountLoginId = accountLoginId;
  }

}
