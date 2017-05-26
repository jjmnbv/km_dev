package com.kmzyc.b2b.util.Ebank.bankOfChina.service.impl;

// package com.kmzyc.b2b.util.Ebank.bankOfChina.service.impl;
//
// import com.kmzyc.b2b.util.Ebank.bankOfChina.service.RequestOfBOCService;
// import com.kmzyc.b2b.util.Ebank.tradeModel.BOCRefundObject;
// import com.kmzyc.b2b.util.Ebank.tradeModel.QueryOrderOfBackObj;
// import com.kmzyc.b2b.util.Ebank.tradeModel.QueryOrderTradeItem;
// import com.kmzyc.b2b.util.Ebank.tradeModel.RefundOfBackObject;
// import com.kmzyc.zkconfig.ConfigurationUtil;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.util.EntityUtils;
//import org.dom4j.Document;
//import org.dom4j.DocumentException;
//import org.dom4j.Element;
//import org.dom4j.io.SAXReader;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import java.io.StringReader;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 商户用于发送对银行交易请求的接口
// *
// * @author lvxingxing 2013-11-12
// */
//@Service("requestOfBOCService")
//public class RequestOfBOCServiceImpl implements RequestOfBOCService {
//
//    // static Logger logger = Logger.getLogger(RequestOfBOCServiceImpl.class);
//    private static Logger logger = LoggerFactory.getLogger(RequestOfBOCServiceImpl.class);
//
//    @Override
//    public RefundOfBackObject sendRefundTrade(BOCRefundObject bocro) throws Exception {
//        RefundOfBackObject rofb;
//        String baseUrl = ConfigurationUtil.getString("BC_RefundOrderReqURL");
//        List<NameValuePair> params = new ArrayList<>();
//        params.add(new BasicNameValuePair("merchantNo", bocro.getMerchantNo()));
//        params.add(new BasicNameValuePair("mRefundSeq", bocro.getmRefundSeq()));
//        params.add(new BasicNameValuePair("curCode", bocro.getCurCode()));
//        params.add(new BasicNameValuePair("refundAmount", bocro.getRefundAmount()));
//        params.add(new BasicNameValuePair("orderNo", bocro.getOrderNo()));
//        params.add(new BasicNameValuePair("signData", bocro.getSignData()));
//        Element element = this.analysisXML(baseUrl, params);
//        List<Element> orders = element.elements();
//        rofb = new RefundOfBackObject();
//        for (Element el : orders) {
//            List<Element> orderContexts = el.elements();
//            for (Element orderEl : orderContexts) {
//                if ("msgId".equals(orderEl.getName())) {
//                    rofb.setMsgId(orderEl.getText());
//                } else if ("hdlSts".equals(orderEl.getName())) {
//                    rofb.setHdlSts(orderEl.getText());
//                } else if ("bdFlg".equals(orderEl.getName())) {
//                    rofb.setBdFlg(orderEl.getText());
//                } else if ("rtnCd".equals(orderEl.getName())) {
//                    rofb.setRtnCd(orderEl.getText());
//                } else if ("orderTrans".equals(orderEl.getName())) {
//                    List<Element> orderContextTrans = orderEl.elements();
//                    for (Element orderElTrans : orderContextTrans) {
//                        if ("merchantNo".equals(orderElTrans.getName())) {
//                            rofb.setMerchantNo(orderElTrans.getText());
//                        } else if ("mRefundSeq".equals(orderElTrans.getName())) {
//                            rofb.setmRefundSeq(orderElTrans.getText());
//                        } else if ("curCode".equals(orderElTrans.getName())) {
//                            rofb.setCurCode(orderElTrans.getText());
//                        } else if ("refundAmount".equals(orderElTrans.getName())) {
//                            rofb.setRefundAmount(orderElTrans.getText());
//                        } else if ("orderNo".equals(orderElTrans.getName())) {
//                            rofb.setOrderNo(orderElTrans.getText());
//                        } else if ("orderSeq".equals(orderElTrans.getName())) {
//                            rofb.setOrderSeq(orderElTrans.getText());
//                        } else if ("orderAmount".equals(orderElTrans.getName())) {
//                            rofb.setOrderAmount(orderElTrans.getText());
//                        } else if ("bankTranSeq".equals(orderElTrans.getName())) {
//                            rofb.setBankTranSeq(orderElTrans.getText());
//                        } else if ("tranTime".equals(orderElTrans.getName())) {
//                            rofb.setTranTime(orderElTrans.getText());
//                        } else if ("signData".equals(orderElTrans.getName())) {
//                            rofb.setSignData(orderElTrans.getText());
//                        }
//                    }
//
//                }
//            }
//        }
//        logger.debug("中行反馈文件XML解析完毕！");
//        return rofb;
//    }
//
//    /**
//     * 查询订单接口
//     *
//     * @param orderNos
//     * @param signData
//     * @return
//     * @throws Exception
//     */
//    @Override
//    public QueryOrderOfBackObj queryOrderTrade(String orderNos, String signData) throws Exception {
//        QueryOrderOfBackObj qoob;
//        List<QueryOrderTradeItem> tradeItem = new ArrayList<>();
//        QueryOrderTradeItem qoti;
//        String baseUrl = ConfigurationUtil.getString("BC_QueryOrderReqURL");
//        String merchantNo = ConfigurationUtil.getString("BC_MerNo");
//        List<NameValuePair> params = new ArrayList<>();
//        params.add(new BasicNameValuePair("merchantNo", merchantNo));
//        params.add(new BasicNameValuePair("orderNos", orderNos));
//        params.add(new BasicNameValuePair("signData", signData));
//        Element element = this.analysisXML(baseUrl, params);
//        List<Element> orders = element.elements();
//        qoob = new QueryOrderOfBackObj();
//        for (Element el : orders) {
//            List<Element> orderContexts = el.elements();
//            if ("header".equals(el.getName())) {
//                for (Element orderEl : orderContexts) {
//                    if ("msgId".equals(orderEl.getName())) {
//                        qoob.setMsgId(orderEl.getText());
//                    } else if ("hdlSts".equals(orderEl.getName())) {
//                        qoob.setHdlSts(orderEl.getText());
//                    } else if ("bdFlg".equals(orderEl.getName())) {
//                        qoob.setBdFlg(orderEl.getText());
//                    } else if ("rtnCd".equals(orderEl.getName())) {
//                        qoob.setRtnCd(orderEl.getText());
//                    }
//                }
//            } else {
//                for (Element orderElorderT : orderContexts) {
//                    qoti = new QueryOrderTradeItem();
//                    if ("orderTrans".equals(orderElorderT.getName())) {
//                        List<Element> orderContextsTrans = orderElorderT.elements();
//                        for (Element orderEl : orderContextsTrans) {
//                            if ("merchantNo".equals(orderEl.getName())) {
//                                qoti.setMerchantNo(orderEl.getText());
//                            } else if ("orderNo".equals(orderEl.getName())) {
//                                qoti.setOrderNo(orderEl.getText());
//                            } else if ("orderSeq".equals(orderEl.getName())) {
//                                qoti.setOrderSeq(orderEl.getText());
//                            } else if ("orderStatus".equals(orderEl.getName())) {
//                                qoti.setOrderStatus(orderEl.getText());
//                            } else if ("cardTyp".equals(orderEl.getName())) {
//                                qoti.setCardTyp(orderEl.getText());
//                            } else if ("acctNo".equals(orderEl.getName())) {
//                                qoti.setAcctNo(orderEl.getText());
//                            } else if ("holderName".equals(orderEl.getName())) {
//                                qoti.setHolderName(orderEl.getText());
//                            } else if ("ibknum".equals(orderEl.getName())) {
//                                qoti.setIbknum(orderEl.getText());
//                            } else if ("payTime".equals(orderEl.getName())) {
//                                qoti.setPayTime(orderEl.getText());
//                            } else if ("payAmount".equals(orderEl.getName())) {
//                                qoti.setPayAmount(orderEl.getText());
//                            } else if ("visitorIp".equals(orderEl.getName())) {
//                                qoti.setVisitorIp(orderEl.getText());
//                            } else if ("visitorRefer".equals(orderEl.getName())) {
//                                qoti.setVisitorRefer(orderEl.getText());
//                            }
//                        }
//                    }
//                    tradeItem.add(qoti);
//                }
//
//            }
//            qoob.setOrderTradeItem(tradeItem);
//        }
//        return qoob;
//    }
//
//    /**
//     * 发送请求到银行网关
//     *
//     * @param baseURL
//     * @param params
//     * @return
//     * @throws Exception
//     */
//    private Element analysisXML(String baseURL, List<NameValuePair> params) throws Exception {
//        HttpEntity httpentity = new UrlEncodedFormEntity(params, "UTF-8");
//        HttpPost httpRequest = new HttpPost(baseURL);
//        httpRequest.setEntity(httpentity);
//        HttpClient httpclient = HttpClientBuilder.create().build();// new DefaultHttpClient();
//        HttpResponse httpResponse = httpclient.execute(httpRequest);
//        // 获取返回数据
//        HttpEntity entity = httpResponse.getEntity();
//        String xml = EntityUtils.toString(entity);
//        if (xml.contains("您所在查询的IP地址有误")) {
//            String msg = "中行对账系统提示信息:您所在查询的IP地址被限制访问，请联系中行将服务器地址加入到访问列表中后再进行操作。";
//            logger.error(msg);
//            throw new RuntimeException(msg);
//        }
//        xml = xml.trim();
//        logger.debug("中行退款返回文件HTTP获取成功:xml长度:" + xml.length());
//        StringReader stringReader = new StringReader(xml);
//        Document doc = null;
//        try {
//            doc = new SAXReader().read(stringReader);
//        } catch (DocumentException e) {
//            String msg = "对中行的反馈文件XML解析失败,异常信息：" + e.getMessage();
//            logger.error(msg);
//            logger.error("xml内容:" + xml);
//            throw new RuntimeException(msg);
//        }
//        Element element = doc.getRootElement();
//        return element;
//    }
//}
