package com.pltfm.app.action;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.annotation.Resource;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.OMNode;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.b2b.vo.EraInfo;
import com.kmzyc.commons.page.Page;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.service.AccountInfoService;
import com.pltfm.app.service.EraInfoService;
import com.pltfm.app.service.LoginInfoService;
import com.pltfm.app.util.ConfigureUtils;
import com.pltfm.app.util.RSA;
import com.pltfm.app.vobject.LoginInfo;

import net.sf.json.JSONObject;

/**
 * 康美中药城会员action
 * 
 * @author lijianjun
 * @since 15.2.2
 * 
 */
@SuppressWarnings({"serial", "rawtypes"})
@Scope(value = "prototype")
@Controller
public class EraInfoAction extends BaseAction implements ModelDriven {
  Logger logger = LoggerFactory.getLogger(EraInfoAction.class);

  @Resource(name = "eraInfoServiceImpl")
  private EraInfoService eraInfoService;
  @Resource(name = "accountInfoService")
  private AccountInfoService accountInfoService;
  @Resource(name = "loginInfoService")
  private LoginInfoService loginInfoService;

  private EraInfo eraInfo;
  private LoginInfo loginInfo;

  // 时代会员请求地址
  /*private static final String SOAPBINDINGADDRESS =
      ConfigurationUtil.getString("SOAPBINDINGADDRESS");*/
  private static final String SOAPBINDINGADDRESS =
                  ConfigurationUtil.getString("sdWebPath");
  // 远程时代接口请求命名空间
//  private static final String QNAMESPACE = ConfigurationUtil.getString("QNAMESPACE");
  private static final String QNAMESPACE = "http://tempuri.org/";
  // 获取远程时代会员信息请求action
//  private static final String AOAPACTION = ConfigurationUtil.getString("AOAPACTION");
  private static final String AOAPACTION = "http://tempuri.org/IMemberInfo/MemberGetInfo";
  // 获取远程时代会员请求方法
/*  private static final String AOAPElEMENT =
      ConfigurationUtil.getString("AOAPElEMENT");*/
  private static final String AOAPElEMENT = "MemberGetInfo";



  // 康美中药城会员分页列表
  public String pageList() {
    if (page == null) {
      page = new Page();
    }
    try {
      page = eraInfoService.selectEraInfoList(page, eraInfo);
    } catch (Exception e) {
      logger.error("获取时代会员列表异常" + e.getMessage(), e);
    }
    return "pageSuccess";
  }

  // 查询时代会员信息
  public String queryEraInfoDetail() {
    if (eraInfo.getEraInfoId() != null) {
      try {
        // 获取时代信息
        eraInfo = eraInfoService.selectEraInfoDetail(eraInfo.getEraInfoId().longValue());
        // 获取验证的康美会员信息LoginId
        loginInfo = loginInfoService.getLoginId(eraInfo.getLoginId().intValue());
      } catch (Exception e) {
        logger.error("获取时代会员信息异常" + e.getMessage(), e);
      }
    }
    return "detailSuccess";
  }

  // 更新时代会员信息
  public String refreshErainfo() {
    // webservice接口获取时代信息
    EndpointReference endpointReference = new EndpointReference(SOAPBINDINGADDRESS);
    try {
      // 创建一个OMFactory，下面的namespace、方法与参数均需由它创建
      OMFactory factory = OMAbstractFactory.getOMFactory();

      // 下面创建命名空间，如果你的WebService指定了targetNamespace属性的话，就要用这个
      // 对应于@WebService(targetNamespace = "http://www.mycompany.com")
      OMNamespace namespace = factory.createOMNamespace(QNAMESPACE, "xsd_String");
      // 下面创建的是参数对数，对应于@WebParam(name = "name")
      // 由于@WebParam没有指定targetNamespace，所以下面创建name参数时，用了null，否则你得赋一个namespace给它
      OMElement nameElement = factory.createOMElement("number", namespace);
      nameElement.setText(eraInfo.getEraNo());
      
      OMElement nameElement2 = factory.createOMElement("sign", namespace);
      
      String sign = RSA.sign("number="+eraInfo.getEraNo(), ConfigurationUtil.getString("sd_webservice_private_key"),"utf-8");
      
      nameElement2.setText(sign);

      
      // nameElement.addChild(factory.createOMText(nameElement, "A60365905"));
      // 下面创建一个method对象，"test"为方法名
      OMElement method = factory.createOMElement(AOAPElEMENT, namespace);
      method.addChild(nameElement);
      method.addChild(nameElement2);

      Options options = new Options();
      options.setAction(AOAPACTION); // 此处对应于@WebMethod(action = "http://www.mycompany.com/test")
      options.setTo(endpointReference);
      // 可以解决多次调用webservice后的连接超时异常
      // 开启对Session管理的支持
      options.setManageSession(true);
      // 设置Http客户端连接可以复用
      options.setProperty(HTTPConstants.REUSE_HTTP_CLIENT, true);
      // 设置超时
      options.setTimeOutInMilliSeconds(5000L);

      ServiceClient sender;
      sender = new ServiceClient();
      sender.setOptions(options);
      // 发送并得到结果，至此，调用成功，并得到了结果
      OMElement result = sender.sendReceive(method);
      // 关闭webservice连接池防止链接超时
      sender.cleanupTransport();
      // 下面的输出结果为<ns2:testResponse xmlns:ns2="http://www.mycompany.com">& lt;greeting>hello
      // java</greeting></ns2:testResponse>
      // 解析得到的QMElement值
      // 1遍历全部节点，将节点放入Map返回
      Iterator iter = result.getChildElements();
      // Map<String,String> map = new HashMap<String,String>();
      String returnVal = null;
      while (iter.hasNext()) {
        OMNode omNode = (OMNode) iter.next();
        if (omNode.getType() == OMNode.ELEMENT_NODE) {
          OMElement omElement = (OMElement) omNode;
          // String key = omElement.getLocalName().trim();
          returnVal = omElement.getText().trim();
          // map.put(key, value);
        }
      }
      if (StringUtils.isBlank(returnVal)) {
        // 绑定消息展示内容记录日志
        this.addActionMessage(ConfigureUtils.getMessageConfig("eraInfo.getInfo.false"));
        logger.info("获取时代信息失败");
      } else {
        // 得到的string类型转化为json格式
        JSONObject obj = JSONObject.fromObject(returnVal);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 更新时代信息到后台数据库
        // 生日
        eraInfo.setBirthday(sdf.parse(obj.get("Birthday").toString()));
        // 证件号码
        eraInfo.setCertificateNumber(obj.get("PaperNumber").toString());
        // 联系电话
        eraInfo.setContactInformation(obj.get("MobileTele").toString());
        // 时代等级名称
        eraInfo.setEraGradeName(obj.get("Level").toString());
        // 时代等级折扣
        eraInfo.setEraGradeRate(new BigDecimal(obj.get("Discount").toString()));
        // 时代会员编号
        eraInfo.setEraNo(obj.get("Number").toString());
        // 推荐人编号
        eraInfo.setRecommendedNo(obj.get("Direct").toString());
        // 性别
        eraInfo.setSex(obj.get("Sex").toString());
        // 真实姓名
        eraInfo.setName(obj.get("Name").toString());
        // 昵称
        eraInfo.setNickname(obj.get("PetName").toString());
        // 时代会员id
        eraInfo.setEraId( Long.valueOf(obj.get("ID").toString()));
        // 体验积分
        eraInfo.setExpIntegralValue(new BigDecimal(obj.get("Score").toString()));
        // 证件类型
        eraInfo.setPapertype(obj.getString("PaperType").toString());
        // 修改时间
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        eraInfo.setModifyDate(sdf.parse(sdf.format(new Date())));
        int rows = eraInfoService.updateEraInfo(eraInfo);
        if (rows > 0) {
          // 绑定消息展示内容记录日志
          this.addActionMessage(ConfigureUtils.getMessageConfig("eraInfo.update.success"));
          logger.info("更新同步时代信息成功");
        } else {
          this.addActionMessage(ConfigureUtils.getMessageConfig("eraInfo.update.false"));
          logger.info("更新同步时代信息失败");
        }
      }
    } catch (AxisFault e) {
      this.addActionMessage(ConfigureUtils.getMessageConfig("eraInfo.getInfo.false"));
      logger.error("axis2请求异常" + e.getMessage(), e);
    } catch (Exception e) {
      this.addActionMessage(ConfigureUtils.getMessageConfig("eraInfo.update.false"));
      logger.error("更新同步时代会员信息异常" + e.getMessage(), e);
    }
    return queryEraInfoDetail();
  }

  @Override
  public Object getModel() {
    eraInfo = new EraInfo();
    return eraInfo;
  }



  public LoginInfo getLoginInfo() {
    return loginInfo;
  }

  public void setLoginInfo(LoginInfo loginInfo) {
    this.loginInfo = loginInfo;
  }

  public EraInfo getEraInfo() {
    return eraInfo;
  }

  public void setEraInfo(EraInfo eraInfo) {
    this.eraInfo = eraInfo;
  }



}
