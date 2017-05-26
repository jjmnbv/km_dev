package com.kmzyc.b2b.third.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.km.framework.action.BaseAction;
import com.kmzyc.b2b.model.WxScanProduct;
import com.kmzyc.b2b.service.WxScanProductService;
import com.kmzyc.b2b.third.util.WxMsgXmlUtil;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.commons.exception.ServiceException;

/**
 * 微信扫码记录action处理器 20150921 mlq add
 * 
 * @author KM
 * 
 */
@Controller("wxScanProductAction")
@Scope("prototype")
public class WxScanProductAction extends BaseAction {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  // private static Logger logger = Logger.getLogger(WxScanProductAction.class);
  private static Logger logger = LoggerFactory.getLogger(WxScanProductAction.class);

  @Resource(name = "wxScanProductService")
  private WxScanProductService wxScanProductService;


  /**
   * 微信扫码记录增加
   * 
   * @return
   */
  public String saveWxScanRecord() {

    String code = "200";
    String message = "";
    boolean isCanSubmitReq = true;
    ReturnResult returnResult = new ReturnResult<Map<String, Object>>(code, message, null);
    PrintWriter out = null;
    try {
      out = super.getResponse().getWriter();

      // 直接转发,不做任何参数解析处理,纯xml格式方案 测试已通过 20151009 update 转发方解析后传入xml字符串数据给我们
      Map<String, String> msgData = WxMsgXmlUtil.parseForWxGroup(super.getRequest());

      if (msgData == null || msgData.size() < 1) {
        logger.error("传送数据为空或者解析xml出错,msgDataMap为null");
        message = "解析xml出错,msgDataMap为null";
        return null;
      }

      String openId = msgData.get("FromUserName");
      String keyStr = msgData.get("KeyStr");
      String keyStandard = msgData.get("KeyStandard");
      String scanScene = msgData.get("Scene");
      String sex = msgData.get("Sex");
      String city = msgData.get("City");
      String country = msgData.get("Country");
      String province = msgData.get("Province");

      // 解析后request parameter传入参数方式 测试已通过
      // String openId = super.getRequest().getParameter("openId");
      // String keyStr = super.getRequest().getParameter("KeyStr");
      // String keyStandard = super.getRequest().getParameter("KeyStandard");
      // String scanScene = super.getRequest().getParameter("Scene");
      // String sex = super.getRequest().getParameter("Sex");
      // String city = super.getRequest().getParameter("City");
      // String country = super.getRequest().getParameter("Country");
      // String province = super.getRequest().getParameter("Province");


      if (StringUtils.isBlank(openId)) {
        logger.info("传入的扫码者openId为空!");
        message = "传入的扫码者openId为空!";
        isCanSubmitReq = false;
      }

      if (StringUtils.isBlank(keyStr)) {
        logger.info("传入的条形码keyStr为空!");
        message = "传入的条形码keyStr为空!";
        isCanSubmitReq = false;
      }


      if (isCanSubmitReq) {
        // 可以做设置到数据库当中
        WxScanProduct para = new WxScanProduct();
        para.setOpenId(openId);
        para.setKeyStr(keyStr);
        para.setKeyStandard(keyStandard);
        para.setScanScene(scanScene);
        para.setCountry(country);
        para.setProvince(province);
        para.setSex(sex);
        para.setCity(city);
        wxScanProductService.saveWxScanProductRecord(para);
        code = "0";
        message = "新增扫码openId=" + openId + ",keyStr=" + keyStr + "的记录成功";
      }


    } catch (ServiceException e) {
      code = "200";
      message = "新增扫码记录出现异常!详情如下=" + e.getMessage();
      logger.error(message,e);
    } catch (IOException e) {
      code = "200";
      message = "IO流异常!详情如下=" + e.getMessage();
      logger.error(message,e);
    } catch (Exception e) {
      logger.error(e.getMessage(),e);
    } finally {
      returnResult.setCode(code);
      returnResult.setMessage(message);
      out.print(JSONObject.toJSONString(returnResult));

        out.flush();
        out.close();
    }
    return null;
  }

}
