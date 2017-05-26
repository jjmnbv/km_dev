package com.pltfm.app.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

@Controller("toOutSideAction")
public class ToOutSideAction extends BaseAction {
  private Logger log = Logger.getLogger(ToOutSideAction.class);
  private String url = "www.sina.com";

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  /**
	 * 
	 */
  private static final long serialVersionUID = -3443492705871254938L;

  @SuppressWarnings("unused")
  public String to() {
    // HttpServletRequest request = this.getHttpServletRequest();
    HttpServletResponse response = this.getHttpServletResponse();
    String p0_Cmd = "Buy";
    String p1_MerId = "10001126856";
    String p2_Order = "131023161949230120";
    String p3_Amt = "36.00";
    String p4_Cur = "CNY";
    String p5_Pid = "T000323238";
    String p6_Pcat = "KangMeisaleGoods";
    String p7_Pdesc = "KangMeigoods";
    String p8_Url = "http://aabbcc.kmzyw.com.cn/index.jsp";
    String p9_SAF = "0";
    String pa_MP = "订单支付";
    String pd_FrpId = "";
    String pr_NeedResponse = "1";
    String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl";
    String fix = "<script>document.forms['yeepay'].submit();</script>";
    String hmac = null;
    // hmac = PaymentForOnlineService.getReqMd5HmacForOnlinePayment(p0_Cmd,
    // p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc,
    // p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, keyValue);
    String htmlStr =
        "<form name=\"yeepay\" action='https://www.yeepay.com/app-merchant-proxy/node ' method='POST' target=\"_blank\"><input type='hidden' name='p0_Cmd'   value='Buy'><input type='hidden' name='p1_MerId' value='10001126856'><input type='hidden' name='p2_Order' value=''><input type='hidden' name='p3_Amt'   value='0.01'><input type='hidden' name='p4_Cur'   value='CNY'><input type='hidden' name='p5_Pid'   value='productname'><input type='hidden' name='p6_Pcat'  value='producttype'><input type='hidden' name='p7_Pdesc' value='productdesc'><input type='hidden' name='p8_Url'   value='http://aabbcc.kmzyw.com.cn/index.jsp'><input type='hidden' name='p9_SAF'   value='0'><input type='hidden' name='pa_MP'    value='userId or other'><input type='hidden' name='pd_FrpId' value=''><input type='hidden' name=\"pr_NeedResponse\"  value=\"1\"><input type='hidden' name='hmac'     value='"
            + hmac + "'>" + fix;
    try {
      response.setCharacterEncoding("GB2312");
      PrintWriter out = response.getWriter();
      // this.getRequest().setAttribute(arg0, arg1);
      out.write(htmlStr);
      out.flush();
      out.close();
      // this.getHttpServletRequest().getRequestDispatcher(htmlStr).forward(request,
      // response);
    } catch (IOException e) {
      // Auto-generated catch block
      log.error("IO流异常！", e);
    }
    // return "redirect";
    return null;
  }
}
