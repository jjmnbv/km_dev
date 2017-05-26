package com.kmzyc.b2b.util.Ebank.bankOfChina.util;

// package com.kmzyc.b2b.util.Ebank.bankOfChina.util;
//
//import java.io.IOException;
//import java.security.GeneralSecurityException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//
//import com.bocnet.common.security.PKCS7Tool;
// import com.kmzyc.zkconfig.ConfigurationUtil;
// import com.kmzyc.b2b.util.Ebank.tradeModel.BOCPayCallBack;
//
//public class EBankUtil {
//    public static String buildForm(Map sPara, String gateway, String strMethod) {
//        List keys = new ArrayList(sPara.keySet());
//        StringBuilder sbHtml = new StringBuilder();
//        sbHtml.append((new StringBuilder(
//                "<form id=\"alipaysubmit\" name=\"alipaysubmit\" action=\"")).append(gateway)
//                .append("\" method=\"").append(strMethod).append("\">").toString());
//        for (int i = 0; i < keys.size(); i++) {
//            String name = (String) keys.get(i);
//            String value = (String) sPara.get(name);
//            sbHtml.append((new StringBuilder("<input type=\"hidden\" name=\"")).append(name)
//                    .append("\" value=\"").append(value).append("\"/>").toString());
//        }
//
//        sbHtml.append("<script>document.forms['alipaysubmit'].submit();</script>");
//        return sbHtml.toString();
//    }
//
//    public static BOCPayCallBack getPayBackObject(HttpServletRequest request) {
//        BOCPayCallBack bpcb = new BOCPayCallBack();
//        bpcb.setBankTranSeq(request.getParameter("bankTranSeq"));
//        bpcb.setCardTyp(request.getParameter("cardTyp"));
//        bpcb.setMerchantNo(request.getParameter("merchantNo"));
//        bpcb.setOrderIp(request.getParameter("orderIp"));
//        bpcb.setOrderNo(request.getParameter("orderNo"));
//        bpcb.setOrderRefer(request.getParameter("orderRefer"));
//        bpcb.setOrderSeq(request.getParameter("orderSeq"));
//        bpcb.setOrderStatus(request.getParameter("orderStatus"));
//        bpcb.setPayAmount(request.getParameter("payAmount"));
//        bpcb.setPayTime(request.getParameter("payTime"));
//        bpcb.setReturnActFlag(request.getParameter("returnActFlag"));
//        bpcb.setSignData(request.getParameter("signData"));
//        return bpcb;
//    }
//
//    /**
//     * @throws IOException
//     * @throws GeneralSecurityException
//     *
//     */
//    public static void verifyBank(String signature, byte[] data, String dn) throws IOException,
//            GeneralSecurityException {
//        PKCS7Tool tool =
//                PKCS7Tool.getVerifier(ConfigurationUtil.getString("BC_rootCertificatePath"));
//        tool.verify(signature, data, dn);
//    }
//}
