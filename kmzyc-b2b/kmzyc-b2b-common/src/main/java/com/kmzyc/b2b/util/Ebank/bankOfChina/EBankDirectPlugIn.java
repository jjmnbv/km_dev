package com.kmzyc.b2b.util.Ebank.bankOfChina;

// package com.kmzyc.b2b.util.Ebank.bankOfChina;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.security.GeneralSecurityException;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.bocnet.common.security.PKCS7Tool;
// import com.kmzyc.zkconfig.ConfigurationUtil;
// import com.kmzyc.b2b.model.PayCommonObject;
// import com.kmzyc.b2b.util.Ebank.BaseBank;
// import com.kmzyc.b2b.util.Ebank.bankOfChina.util.EBankUtil;
// import com.kmzyc.b2b.util.Ebank.tradeModel.BOCPayCallBack;
// import com.kmzyc.b2b.util.Ebank.tradeModel.BankOfChinaPayObject;
// import com.kmzyc.b2b.util.Ebank.tradeModel.ProtocolPaySignObject;
//
//public class EBankDirectPlugIn extends BaseBank {
//    @Override
//    public String onPay(Object obj) throws Exception {
//
//        String keyStorePath = ConfigurationUtil.getString("BC_keyStorePath");
//        String keyStorePassword = ConfigurationUtil.getString("BC_keyStorePassword");
//        String keyPassword = ConfigurationUtil.getString("BC_keyPassword");
//
//        if (obj instanceof BankOfChinaPayObject) {
//            String merchantNo = ConfigurationUtil.getString("BC_MerNo");
//            BankOfChinaPayObject bankPayObject = null;
//            String orderURL = ConfigurationUtil.getString("BC_OnlineCallbackURL");
//            bankPayObject = (BankOfChinaPayObject) obj;
//            bankPayObject.setOrderUrl(orderURL);
//            Map<String, String> sParaTemp = new HashMap<String, String>();
//            byte[] data;
//            sParaTemp.put("merchantNo", merchantNo);// 请求命令,在线支付固定为Buy
//            sParaTemp.put("curCode", bankPayObject.getCurCode());
//            sParaTemp.put("orderAmount", bankPayObject.getOrderAmount());
//            sParaTemp.put("orderNo", bankPayObject.getOrderNo());
//            sParaTemp.put("orderNote", bankPayObject.getOrderNote());
//            sParaTemp.put("orderTime", bankPayObject.getOrderTime());
//            sParaTemp.put("orderTimeoutDate", bankPayObject.getOrderTimeoutDate());
//            sParaTemp.put("orderUrl", bankPayObject.getOrderUrl());
//            sParaTemp.put("payType", bankPayObject.getPayType());
//            String stringdata =
//                    bankPayObject.getMerchantNo() + "|" + bankPayObject.getCurCode() + "|"
//                            + bankPayObject.getOrderAmount() + "|" + bankPayObject.getOrderNo()
//                            + "|" + bankPayObject.getOrderTime();
//            data = stringdata.getBytes("UTF-8");
//            PKCS7Tool tool = PKCS7Tool.getSigner(keyStorePath, keyStorePassword, keyPassword);
//            // //商户签名数据
//            String signature = tool.sign(data);
//            sParaTemp.put("signData", signature);
//            // 发送银行支付地址
//            String EBankURL = ConfigurationUtil.getString("BC_OnlinePayReqURL");
//            return EBankUtil.buildForm(sParaTemp, EBankURL, "post");
//        } else if (obj instanceof ProtocolPaySignObject) {
//            String merchantNo = ConfigurationUtil.getString("BC_MerNo");
//            ProtocolPaySignObject ppso = null;
//            String orderURL = ConfigurationUtil.getString("BC_ProtocolSignCallbackURL");
//            ppso = (ProtocolPaySignObject) obj;
//            ppso.setAgreeUrl(orderURL);
//            Map<String, String> sParaTemp = new HashMap<String, String>();
//            byte[] data;
//            sParaTemp.put("merchantNo", merchantNo);// 请求命令,在线支付固定为Buy
//            sParaTemp.put("holderMerId", ppso.getHolderMerId());
//            sParaTemp.put("holderName", ppso.getHolderName());
//            sParaTemp.put("identityType", ppso.getIdentityType());
//            sParaTemp.put("identityNumber", ppso.getIdentityNumber());
//            sParaTemp.put("acctNo", ppso.getAcctNo());
//            sParaTemp.put("mobileNumber", ppso.getMobileNumber());
//            sParaTemp.put("agreeUrl", ppso.getAgreeUrl());
//            String stringdata =
//                    ppso.getMerchantNo() + "|" + ppso.getHolderMerId() + "|" + ppso.getHolderName()
//                            + "|" + ppso.getIdentityType() + "|" + ppso.getIdentityNumber() + "|"
//                            + ppso.getAcctNo() + "|" + ppso.getMobileNumber();
//            data = stringdata.getBytes("UTF-8");
//            PKCS7Tool tool = PKCS7Tool.getSigner(keyStorePath, keyStorePassword, keyPassword);
//            // //商户签名数据
//            String signature = tool.sign(data);
//            sParaTemp.put("signData", signature);
//            // 发送银行支付地址
//            String EBankURL = ConfigurationUtil.getString("BC_ProtocolSignReqURL");
//            return EBankUtil.buildForm(sParaTemp, EBankURL, "post");
//        } else
//            return null;
//
//    }
//
//    /**
//     * 解析银行反馈数据
//     */
//    @Override
//    public PayCommonObject payCallBack(HttpServletRequest request, HttpServletResponse response)
//            throws IOException, GeneralSecurityException {
//        BOCPayCallBack obcBack = new BOCPayCallBack();
//        obcBack.setBankTranSeq(request.getParameter("bankTranSeq"));
//        obcBack.setCardTyp(request.getParameter("cardType"));
//        obcBack.setMerchantNo(request.getParameter("merchantNo"));
//        obcBack.setOrderIp(request.getParameter("orderIp"));
//        obcBack.setOrderNo(request.getParameter("orderNo"));
//        obcBack.setOrderRefer(request.getParameter("orderRefer"));
//        obcBack.setOrderSeq(request.getParameter("orderSeq"));
//        obcBack.setOrderStatus(request.getParameter("orderStatus"));
//        obcBack.setPayAmount(request.getParameter("payAmount"));
//        obcBack.setPayTime(request.getParameter("payTime"));
//        obcBack.setPhoneNum(request.getParameter("phoneNum"));
//        obcBack.setReturnActFlag(request.getParameter("returnActFlag"));
//        obcBack.setSignData(request.getParameter("signData"));
//        PayCommonObject poj = new PayCommonObject();
//        // 验证银行签名
//        // byte [] data;
//        // String stringdata = obcBack.getMerchantNo()+"|"+obcBack.getOrderNo()
//        // +"|"+obcBack.getOrderSeq()+"|"+obcBack.getCardTyp()
//        // +"|"+obcBack.getPayTime()+"|"+obcBack.getOrderStatus()
//        // +"|"+obcBack.getPayAmount();
//        // data = stringdata.getBytes("UTF-8");
//        // EBankUtil.verifyBank(obcBack.getSignData(), data, null);
//        poj.setCurrencyCode("001");
//        poj.setMoneyStr(obcBack.getPayAmount());
//        poj.setOrderCode(obcBack.getOrderNo());
//        poj.setPayDateStr(obcBack.getPayTime());
//        if ("1".equals(obcBack.getOrderStatus())) {
//            poj.setPayStateCode("1");
//        }
//        poj.setTransitionNO(obcBack.getOrderSeq());
//        poj.setBankChineseName("中国银行");
//        poj.setBankId("BC");
//        PrintWriter out = response.getWriter();
//        out.println("SUCCESS");
//        out.flush();
//        out.close();
//        return poj;
//    }
//
//    @Override
//    public String protocolPay(Object obj) throws Exception {
//        ProtocolPaySignObject ppo = null;
//        if (obj instanceof ProtocolPaySignObject) {
//            String keyStorePath = ConfigurationUtil.getString("BC_keyStorePath");
//            String keyStorePassword = ConfigurationUtil.getString("BC_keyStorePassword");
//            String keyPassword = ConfigurationUtil.getString("BC_keyPassword");
//            String orderURL = ConfigurationUtil.getString("BC_OnlineCallbackURL");
//            ppo = (ProtocolPaySignObject) obj;
//            ppo.setAgreeUrl(orderURL);
//            Map<String, String> sParaTemp = new HashMap<String, String>();
//            byte[] data;
//            sParaTemp.put("merchantNo", ppo.getMerchantNo());// 请求命令,在线支付固定为Buy
//            sParaTemp.put("holderMerId", ppo.getHolderMerId());
//            sParaTemp.put("holderName", ppo.getHolderName());
//            sParaTemp.put("identityType", ppo.getIdentityType());
//            sParaTemp.put("identityNumber", ppo.getIdentityNumber());
//            sParaTemp.put("acctNo", ppo.getAcctNo());
//            sParaTemp.put("mobileNumber", ppo.getMobileNumber());
//            sParaTemp.put("agreeUrl", ppo.getAgreeUrl());
//            String stringdata =
//                    ppo.getMerchantNo() + "|" + ppo.getHolderMerId() + "|" + ppo.getHolderName()
//                            + "|" + ppo.getIdentityType() + "|" + ppo.getIdentityNumber() + "|"
//                            + ppo.getAcctNo() + "|" + ppo.getMobileNumber() + "|"
//                            + ppo.getAgreeUrl();
//            data = stringdata.getBytes("ISO-8859-1");
//            PKCS7Tool tool = PKCS7Tool.getSigner(keyStorePath, keyStorePassword, keyPassword);
//            // //商户签名数据
//            String signature = tool.sign(data);
//            sParaTemp.put("signData", signature);
//            // 发送银行支付地址
//            String EBankURL = ConfigurationUtil.getString("BCProtocolPayReqURL");
//            return EBankUtil.buildForm(sParaTemp, EBankURL, "post");
//        } else
//            return null;
//    }
//}
