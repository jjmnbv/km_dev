package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.kmzyc.commons.page.Page;
import com.pltfm.app.dao.AddressDAO;
import com.pltfm.app.dao.PersonalBasicInfoDAO;
import com.pltfm.app.service.AccountInfoService;
import com.pltfm.app.service.AddressService;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.util.ListUtils;
import com.pltfm.app.util.StringUtils;
import com.pltfm.app.vobject.AccountInfo;
import com.pltfm.app.vobject.Address;
import com.pltfm.app.vobject.AddressExample;
import com.pltfm.app.vobject.PersonalBasicInfo;

import redis.clients.jedis.JedisCluster;


/**
 * 收货地址业务逻辑处理类
 * 
 * @author zhl
 * @since 2013-07-12
 */
@Component(value = "addressService")
public class AddressServiceImpl implements AddressService {
    
  private static final Logger log = Logger.getLogger(AddressService.class);

  /** redis存入用户收货地址信息列表，后缀加上loginId */
    @Value("${b2b.com.kmzyc.user.address}")
  private String userAddress;

  /** redis存入用户默认收货地址信息 */
    @Value("${b2b.com.kmzyc.user.address.default.id}")
  private String userDefaultAddressId;

  /** redis存入用户收货地址信息主键自增id，全局所有用户使用一个 */
    @Value("${b2b.com.kmzyc.user.address.id}")
  private String addressId;

  /** redis存入用户收货地址信息主键自增id初始值: 99999999 */
    @Value("${b2b.com.kmzyc.user.address.id.init.value}")
  private String addressIdInitValue;

/*  @Autowired
  private RedisTemplate redisTemplate;*/

  @Autowired
  private AccountInfoService accountInfoService;

  @Resource(name = "addressDAO")
  private AddressDAO addressDAO;
  
  /**
   * 个人信息DAO接口
   */
  @Resource(name = "personalBasicInfoDAO")
  private PersonalBasicInfoDAO personalBasicInfoDAO;
  
  @Resource
  private JedisCluster jedis;
  
  private static  AtomicBoolean isClearingFlag =  new  AtomicBoolean( false );
  
  //清理缓存保留结果
  private static Map<String,String> clearResultMap = new ConcurrentHashMap<String, String>(); 
  
  @PostConstruct
  public void init() {
    // 给新增地址数据信息 自增id 设置 初始化值，之后获取使用incr方法获取结果做为address primary key
    // 不存在才初始化
    if (!jedis.exists(addressId)) {
        jedis.set(addressId, addressIdInitValue);
    }
  }

  /**
   * 添加收货地址信息
   * 
   * @param address 收货地址实体
   * @throws Exception 异常
   */
  @Override
public Integer addAddress(Address address) throws Exception {
    // 获取主键
    int primaryKey =  jedis.incr(addressId).intValue();
    Date dateTime = DateTimeUtils.getCalendarInstance().getTime();
    address.setN_addressId(primaryKey);
    address.setD_createdate(dateTime);
    address.setD_lastupdate(dateTime);

    saveOrUpdateAddressCache(address);
    
    try{
    //添加收货地址到数据库
   /* if(address!=null&&address.getStatus()==0){
        addressDAO.updateStatusByAccount(address);
    }*/
    addressDAO.insert(address);
    }catch(Exception e){
        log.error("添加收货地址失败!"+e.getMessage());
    }

    return primaryKey;
  }

  private Long saveOrUpdateAddressCache(Address address) throws Exception {
    // 考虑在service实现，操作cache
    String loginId = null;
    Integer tempId = address.getLoginId();
    if (tempId == null || tempId.intValue() == 0) {
      // 根据address accountid 来获取 loginId
      AccountInfo accountInfo = accountInfoService.selectByPrimaryKey(address.getN_accountId());
      if (accountInfo != null) {
        loginId = accountInfo.getN_LoginId().toString();
        address.setLoginId(accountInfo.getN_LoginId());
      }
    } else {
      loginId = tempId.toString();
    }
    if (StringUtils.isEmpty(loginId)) {
      throw new RuntimeException("用户未登录, loginId 为空");
    }

    String addressKey = userAddress.concat(loginId);
    String defaultAddressKey = userDefaultAddressId.concat(loginId);

    if (address.getStatus() == 0) {
      // 修改旧默认地址信息
      resetDefaultAddress(addressKey, defaultAddressKey);
      // 更新对应的value
      address.setIsChecked(Boolean.TRUE);
      // 更新默认地址id
      jedis.set(defaultAddressKey, String.valueOf(address.getN_addressId()));
    } else {
      // 去掉当前默认值
      String oldAddressId = jedis.get(defaultAddressKey);
      // redisCommands.del(defaultAddressKey);
      if (oldAddressId != null
          && address.getN_addressId().intValue() == Integer.parseInt(oldAddressId)) {
          jedis.del(defaultAddressKey);
      }
    }

    // 更新地址列表
    return jedis.hset(addressKey, String.valueOf(address.getN_addressId()),
        JSONObject.toJSONString(address));
  }

  /**
   * 修改旧默认地址信息
   * 
   * @param addressKey 用户列表地址key
   * @param defaultAddressKey 用户默认地址key
   */
  private void resetDefaultAddress(String addressKey, String defaultAddressKey) {
    String oldAddressId = jedis.get(defaultAddressKey);
    // redisCommands.del(defaultAddressKey);
    if (oldAddressId == null) {
      return;
    }

    String addressStr = jedis.hget(addressKey, oldAddressId);
    if (addressStr == null) {
      return;
    }

    Address oldAddress = JSONObject.parseObject(addressStr, Address.class);
    oldAddress.setStatus(1);
    oldAddress.setIsChecked(false);
    jedis.hset(addressKey, oldAddressId, JSONObject.toJSONString(oldAddress));
  }



  /**
   * 通过主键删除收货地址
   * 
   * @param addressIds 收货地址主键
   * @return
   * @throws Exception 异常
   */
  @Override
public Integer deleteAddressList(Long userId, List<String> addressIds) throws Exception {
    int count = 0;
    if (ListUtils.isNotEmpty(addressIds)) {
      for (String id : addressIds) {
        // count+=addressDAO.deleteByPrimaryKey(Integer.valueOf(id));
        count += deleteAddress(userId, Integer.valueOf(id)) ? 1 : 0;
        
        addressDAO.deleteByPrimaryKey(Integer.valueOf(id));
      }
    }
    
    return count;
  }

  /**
   * 单条删除收货地址
   * 
   * @param addressId 地址主键
   * @return
   * @throws Exception 异常
   */
  @Override
public boolean deleteAddress(Long userId, Integer addressId) throws Exception {
    // 默认不能删除
    String tempLoginId = userId.toString();
    String tempId = String.valueOf(addressId);
    String defaultId = jedis.get(userDefaultAddressId.concat(tempLoginId));
    if (defaultId != null && defaultId.equals(tempId)) {
      return false;
    }

    boolean isSuccess = false;
    try {
      isSuccess = jedis.hdel(userAddress.concat(tempLoginId), tempId).intValue() > 0;
      addressDAO.deleteByPrimaryKey(addressId);
    } catch(Exception e) {
      log.error(e.getMessage());;
    }

    return isSuccess;
  }

  /**
   * 更新收货地址
   * 
   * @param address 收货地址实体
   * @return
   * @throws Exception 异常
   */
  @Override
public Integer updateAddress(Address address) throws Exception {
      if(null == address){
          throw new Exception("方法updateAddress 传入的参数为空!");
      }
    // 更新默认收货地址
    Date dateTime = DateTimeUtils.getCalendarInstance().getTime();
    address.setD_lastupdate(dateTime);
    //更新redis中数据
    Integer i = saveOrUpdateAddressCache(address).intValue();
    try{
    //更新数据库默认收货地址
    if(address!=null&&address.getStatus()==0){
        addressDAO.updateStatusByAccount(address);
    }
     
    Integer k = addressDAO.updateByPrimaryKey(address);
    
    }catch(Exception e){
       log.error("更新收货地址失败!"+e.getMessage()); 
    }
    return i;
  
  }

  /**
   * 多条件查询收货地址信息
   * 
   * @param example
   * @return
   * @throws Exception
   */
  @Override
public List queryAddressList(AddressExample example) throws Exception {
    return  addressDAO.selectByExample(example);
  }

  /**
   * 通过主键获取收货地址信息
   * 
   * @param addressId 收货地址实体
   * @return
   * @throws Exception 异常
   */
  @Override
public Address queryByPrimaryKey(Long loginId, Integer addressId) throws Exception {
    Address address = null;
    String addressKey = userAddress.concat(String.valueOf(loginId));
    String addressJson = jedis.hget(addressKey, addressId.toString());

    if (addressJson == null) {
      // cache not exists, query db
        address = addressDAO.selectByPrimaryKey(addressId);
      // set cache

      if (address != null) {
          jedis.hset(addressKey, addressId.toString(), JSONObject.toJSONString(address));
      }

    } else {
      address = JSONObject.parseObject(addressJson, Address.class);
    }

    // 设置
    AccountInfo accountInfo = accountInfoService.selectByPrimaryLoginInfo(loginId.intValue());
    if(null != accountInfo){
    address.setAccountLogin(accountInfo.getAccountLogin());
    }
    return address;
  }

  /**
   * 分页查询收货地址信息
   * 
   * @param address 收货地址信息
   * @param page 分页对象
   * @return
   * @throws Exception 异常
   */
  @Override
public Page queryForPageList(Address address, Page page) throws Exception {
 /* if (address == null || StringUtils.isEmpty(address.getAccountLogin())
        && StringUtils.isEmpty(address.getCellphone())) {
      page.setRecordCount(0);
      page.setDataList(null);
      return page;
    }

    // 查询账号id
    AccountInfo accountInfo = accountInfoService.selectByPrimaryLoginInfo(address.getAccountLogin(),
        address.getCellphone());
    if (accountInfo == null) {
      page.setRecordCount(0);
      page.setDataList(null);
      return page;
    }

    // 传入loginId
    address.setLoginId(accountInfo.getN_LoginId());
    address.setAccountLogin(accountInfo.getAccountLogin());
    // 查询收货地址总数并设置总数
    int totalNum = byCountAddress(address);
    if (totalNum != 0) {
      page.setRecordCount(totalNum);
      // 设置查询开始结束索引
      address.setStartIndex(page.getStartIndex());
      address.setEndIndex(page.getStartIndex() + page.getPageSize());
      // 只能获取loginId 不能获取loginAccount
      List<Address> tempList = findByLoginId(accountInfo.getN_LoginId());
      for (Address temp : tempList) {
        temp.setAccountLogin(accountInfo.getAccountLogin());
      }
      page.setDataList(tempList);
    } else {
      page.setRecordCount(0);
      page.setDataList(null);
    }*/
      
    //查询收货地址总数并设置总数
      int totalNum = addressDAO.countByAddress(address);
      if(totalNum!=0){
          page.setRecordCount(totalNum);
         // 设置查询开始结束索引
          address.setStartIndex(page.getStartIndex());
          address.setEndIndex(page.getStartIndex()+page.getPageSize());
          page.setDataList(addressDAO.queryForPage(address));
      }else{
          page.setRecordCount(0);
          page.setDataList(null);
      }
       return page;


  }

  /**
   * 按照插叙条件查询收货地址总条数
   */
  @Override
public Integer byCountAddress(Address address) throws Exception {
    String addressKey = userAddress.concat(address.getLoginId().toString());
    if (jedis.exists(addressKey)) {
     
      try {
        return jedis.hlen(addressKey).intValue();
      } catch(Exception e){
          log.error(e.getMessage());
      }
    }

    List<Address> list = findByLoginId(address.getLoginId());// list != null
    return list.size();
    // return addressDAO.countByAddress(address);
  }

  /**
   * 根据登录id查询收获地址信息
   * 
   * @throws Exception
   */
  private List<Address> findByLoginId(Integer id) throws Exception {
    List<Address> result = new ArrayList<Address>();
    boolean existDefaultAddress = false;
    // 默认地址redis key
    String defaultAddressKey = userDefaultAddressId.concat(id.toString());
    String addressKey = userAddress.concat(id.toString());
    List<String> addressList = null;
    try {
      addressList = jedis.hvals(addressKey);
    } catch(Exception e){
        log.error(e.getMessage());
    }

    if (addressList == null || addressList.isEmpty()) {
        jedis.del(defaultAddressKey);// 移除默认地址
      try {
        result = addressDAO.queryListByLoginId(id); // 查询db
      } catch (SQLException e) {
        throw new SQLException(e);
      }
      if (!result.isEmpty()) {
        // 放入cache
        for (Address temp : result) {
          String field = String.valueOf(temp.getN_addressId());
          if (temp.getStatus() == 0 || temp.getIsChecked()) {
            existDefaultAddress = true;
            temp.setIsChecked(true);// 设置选中
            jedis.set(defaultAddressKey, field);
          }
          temp.setLoginId(id);
          jedis.hset(addressKey, field, JSONObject.toJSONString(temp));
        }
        if (!existDefaultAddress) {
          Address temp = result.get(0);
          temp.setStatus(0);
          temp.setIsChecked(true);
          temp.setLoginId(id);
          // 远程更新默认地址与 列表值
          updateAddress(temp);
        }
      }
    } else {
      for (String address : addressList) {
        result.add(JSONObject.parseObject(address, Address.class));
      }
    }

    return result;
  }
  
@Override
@SuppressWarnings("unchecked")
public  Map<String ,String> clearIncorrectCacheAddress(int paramPageSize,int threadCount,String mobile) throws Exception {
    ConcurrentHashMap<String ,String> deleteResultMap = new ConcurrentHashMap<String, String>(); 
    try {
        if(isClearingFlag.compareAndSet(false, true)){
            log.info(isClearingFlag.get());
            int pageNo =1;
            final Date endDate = new Date();
            //查询参数
            PersonalBasicInfo queryParamInfo = new PersonalBasicInfo();
            //查询当前时间之前的所有注册用户
            queryParamInfo.setEndDate(endDate);
            
            //debug:
            if(!StringUtils.isEmpty(mobile)){
                queryParamInfo.setMobile(mobile);
            }
            
            //获取总的数据条数
            final int totalNum = personalBasicInfoDAO.selectLoginIdPageInfoCount(queryParamInfo);
            //创建线程池
            ExecutorService executors = Executors.newFixedThreadPool(threadCount);
            
            //设置分页对象相关属性
            Page pageParam= new Page();
            pageParam.setPageSize(paramPageSize);
            pageParam.setRecordCount(totalNum);
            
            do{
                pageParam.setPageNo(pageNo);
                queryParamInfo.setSkip(pageParam.getStartIndex());
                queryParamInfo.setMax(pageParam.getStartIndex() + pageParam.getPageSize());
                List<Integer> loginIdList =  personalBasicInfoDAO.searchLoginIdPageInfoList(queryParamInfo);
                executors.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                          executeClearCacheAddress(loginIdList,deleteResultMap);
                      } catch (Exception e) {
                         log.error(e.getMessage(), e);
                      }
                    }
                });
                pageNo++;
            }while((pageNo-1)*paramPageSize<totalNum);
            executors.shutdown();   
            executors.awaitTermination(5*24, TimeUnit.HOURS);
            isClearingFlag.getAndSet(false);
        }
    } catch (Exception e) {
        log.error(e.getMessage(),e);
        throw e;
    }finally{
        isClearingFlag.getAndSet(false);
        if(deleteResultMap!=null && !deleteResultMap.isEmpty()){
            clearResultMap.clear();
            clearResultMap.putAll(deleteResultMap);
        }
    }
    return clearResultMap; 
  }

   /**
     *多线程处理清理任务
   */
    @SuppressWarnings("unchecked")
    private void executeClearCacheAddress(final List<Integer> loginIdList,final ConcurrentHashMap<String ,String> deleteResultMap) throws Exception {
        String addressKey = null;
        String defaultAddressKey = null;
        Integer tempLoginId = null;
        Map<String,String>  tempAddressMap =null;
        String defaultAddressId = null;
        Address tempAddress = null;
        boolean delResult = false;
        
        try {
          if(loginIdList!=null && loginIdList.size()>0){
              for(int i =0 ;i<loginIdList.size();i++){
                  tempLoginId =loginIdList.get(i);
                  addressKey = userAddress.concat(tempLoginId.toString());
                  defaultAddressKey = userDefaultAddressId.concat(tempLoginId.toString());
                  //如果存在就判断是否合法，否则不处理
                  if(jedis.exists(addressKey)){
                      tempAddressMap = jedis.hgetAll(addressKey);
                      if(tempAddressMap!=null && !tempAddressMap.isEmpty()){
                          for(String strAddr:tempAddressMap.values()){
                              try {
                                tempAddress = JSONObject.parseObject(strAddr, Address.class); 
                                   //根据获取到的地址信息进行数据校验,如果不是合法地址需要进行处理
                                if(!checkIsValidAddr(tempAddress)){
                                    delResult = deleteAddressForClear(Long.valueOf(tempLoginId.toString()), tempAddress.getN_addressId());
                                    //如果删除成功， 删除默认地址缓存，并记录
                                    if(delResult){
                                        defaultAddressId = jedis.get(defaultAddressKey);
                                        if (defaultAddressId != null
                                              && tempAddress.getN_addressId().intValue() == Integer.parseInt(defaultAddressId)) {
                                            jedis.del(defaultAddressKey);
                                        }
                                        deleteResultMap.put(tempLoginId.toString().concat(",").concat(tempAddress.getN_addressId().toString()),strAddr);
                                      }else{
                                          deleteResultMap.put(tempLoginId.toString().concat(",").concat(tempAddress.getN_addressId().toString()).concat(",未处理"),strAddr); 
                                      }
                                      //重置
                                      delResult =false;
                                  }
                            } catch (Exception e) {
                               log.error(e.getMessage(),e);
                            }
                          } 
                      }
                  }
              }  
          }
      } catch (Exception ex) {
          log.error("执行地址清理发生异常:"+ex.getMessage(), ex);
      }
    }
    
    /**
     * 单条删除收货地址,用于清理错误的地址信息,默认地址也会被清理
     * 
     * @param addressId 地址主键
     * @return
     * @throws Exception 异常
     */
    private boolean deleteAddressForClear(Long userId, Integer addressId) throws Exception {
            // 默认不能删除
            String tempLoginId = userId.toString();
            String tempId = String.valueOf(addressId);
            boolean isSuccess = false;
           
            try {
              isSuccess = jedis.hdel(userAddress.concat(tempLoginId), tempId).intValue() > 0;
            } catch(Exception e){
                log.error(e.getMessage());
            }
            return isSuccess;
     }

    
    
  /**
   * 校验是否是合法的地址信息，
   * @return boolean  地址合法返回true，否则返回false
   */
  private boolean checkIsValidAddr(Address paramAddress){
      Pattern mobilePattern = Pattern.compile("^1[3|4|5|6|7|8]\\d{9}$");// 手机格式
      boolean flag =true;
      String mobile = paramAddress.getCellphone();
      if(StringUtils.isEmpty(mobile)){
          flag =false;
      }else if(!mobilePattern.matcher(mobile).matches()){
          flag =false;
      }else if(StringUtils.isEmpty(paramAddress.getProvince())){
          flag =false;
      }else if(StringUtils.isEmpty(paramAddress.getArea())){
          flag =false;
      }else if(StringUtils.isEmpty(paramAddress.getCity())){
          flag =false;
      }else if(StringUtils.isEmpty(paramAddress.getDetailedAddress())){
          flag =false;
      }
      return flag;
  }
  
  @Override
  public Integer queryAddressCountByLoginId(Integer loginId) throws Exception {
      int result = 0;
      String addressKey = userAddress.concat(String.valueOf(loginId));
      List<String> addressList = null;
      
      try {
        addressList = jedis.hvals(addressKey);
      } catch(Exception e){
          log.error(e.getMessage());
      }
      
      if(addressList!=null && !addressList.isEmpty()){
          result = addressList.size();
      }
      return result;
  }
  

  /**
   * 按照查询条件查询收货地址信息
   */
  @Override
public List queryListAddress(Address address) throws Exception {
    return null;// addressDAO.queryForPage(address);
  }

  public AddressDAO getAddressDAO() {
    return addressDAO;
  }

  public void setAddressDAO(AddressDAO addressDAO) {
    this.addressDAO = addressDAO;
  }


}
