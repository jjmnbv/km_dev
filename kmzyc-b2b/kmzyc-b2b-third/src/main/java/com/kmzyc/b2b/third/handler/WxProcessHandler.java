package com.kmzyc.b2b.third.handler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kmzyc.b2b.third.model.wechat.ArticleItem;
import com.kmzyc.b2b.third.model.wechat.ArticleMsg;
import com.kmzyc.b2b.third.model.wechat.TextMsg;
import com.kmzyc.b2b.third.util.ThirdConstant;
import com.kmzyc.b2b.third.util.WxMsgXmlUtil;

/**
 * 微信处理业务类
 * 
 * @author Administrator 2014-05-12
 * 
 */
public class WxProcessHandler {

  private static final Logger log = LoggerFactory.getLogger(WxProcessHandler.class);
  
  /**
   * 返回需要推送出去的消息String类型的xml格式
   * 
   * @param request
   * @return
   */
  public static String processHandler(HttpServletRequest request) {
    String responseContent = "抱歉,系统正忙,请稍后再试!";
    String fromUser = "";

    try {
      Map<String, String> msgData = WxMsgXmlUtil.parseXmlToMap(request);

      log.info("WxProcessService处理微信推送过来的xml转换成map-------->" + msgData);

      fromUser = msgData.get("FromUserName");
      String msgType = msgData.get("MsgType");

      String createTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
      String defaultText = ThirdConstant.WX_MENU_TEXT;

      log.info("WxProcessService获取得到的msgType---------->" + msgType + ",用户id--->" + fromUser);

      if (ThirdConstant.WX_EVENT_TYPE.equals(msgType)) {
        String event = msgData.get("Event");
        log.info("WxProcessService进入到事件推送的处理...................." + event);
        // 如果是关注事件
        if (ThirdConstant.WX_EVENT_TYPE_SUBSCRIBE.equals(event.trim())) {

          log.info("WxProcessService进入到关注事件的处理================" + event);
          // 给用户发送一条纯文本消息,显示菜单内容
          defaultText = "嗨,终于等到您了!欢迎您关注康美云!/::)";
          TextMsg text = new TextMsg(fromUser, ThirdConstant.WX_USERNAME, createTime, defaultText);
          responseContent = WxMsgXmlUtil.textMsgToXml(text);
        }
      } else {
        ArticleMsg article = new ArticleMsg();
        article.setFromUserName(ThirdConstant.WX_USERNAME);
        article.setToUserName(fromUser);
        article.setCreateTime(createTime);
        article.setMsgType(ThirdConstant.WX_MSG_TYPE_NEWS);
        ArticleItem item =
            new ArticleItem(
                "康美云玛咖MACA 云南丽江 正品原装天然玛咖精片60g(0.5g/片*120片)",
                "98%超高玛咖含量，增强男性持久力量，改善疲劳恢复体力，温润肾虚提升睡眠，重塑男人强健活力,燃烧激情",
                "http://img.kmb2b.com/product/upload/product/3363/4501/20140324160635888678_4501_2.jpg",
                "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc7bd3ceb78662e29&redirect_uri=http%3a%2f%2fwww.kmb2b.com%2fthird%2fwxLogin.action&response_type=code&scope=snsapi_base&state=123#wechat_redirect");
        article.getArticles().add(item);

        article.setArticleCount(String.valueOf(article.getArticles().size()));

        responseContent = WxMsgXmlUtil.articlesMsgToXml(article);
      }

      // //若是文本消息
      // if(ThirdConstant.WX_MSG_TYPE_TEXT.equals(msgType)){
      // //可以在用户关注时推送一条消息,类似于数字对应菜单的,之后可在这边具体判断执行具体操作
      // String content=msgData.get("Content").trim();
      // if("1".equals(content)){
      //					
      // ArticleMsg article=new ArticleMsg();
      // article.setFromUserName(ThirdConstant.WX_USERNAME);
      // article.setToUserName(fromUser);
      // article.setCreateTime(createTime);
      // article.setMsgType(ThirdConstant.WX_MSG_TYPE_NEWS);
      // ArticleItem item=new
      // ArticleItem("甘亩仑 阿克苏红枣（袋装） 精品 1000g/袋","精挑细选优质枣，核小肉厚，果色鲜艳，送礼自用都很好","http://img.kmb2b.com/product/upload/product/3544/4682/2014050918284336094_4682_1.jpg","http://www.kmb2b.com/products/4682.html");
      // article.getArticles().add(item);
      //					
      // article.setArticleCount(String.valueOf(article.getArticles().size()));
      //					
      // responseContent=WxMsgXmlUtil.articlesMsgToXml(article);
      // }else if("2".equals(content)){
      // ArticleMsg article=new ArticleMsg();
      // article.setFromUserName(ThirdConstant.WX_USERNAME);
      // article.setToUserName(fromUser);
      // article.setCreateTime(createTime);
      // article.setMsgType(ThirdConstant.WX_MSG_TYPE_NEWS);
      // ArticleItem item=new
      // ArticleItem("康美药业 枸杞子 150g/瓶","宁夏产 滋阴养肾 养肝明目 补气补血 无硫磺熏制 粒大肉厚枸杞王","http://img.kmb2b.com/product/upload/product/2162/3300/20131223171526885144_3300_1.jpg","http://www.kmb2b.com/products/3300.html");
      // article.getArticles().add(item);
      // article.setArticleCount(String.valueOf(article.getArticles().size()));
      // responseContent=WxMsgXmlUtil.articlesMsgToXml(article);
      // }else{
      // defaultText="/::)抱歉,康美云暂时还不能解答您的问题!\n"+ThirdConstant.WX_MENU_TEXT;
      // TextMsg text=new TextMsg(fromUser,
      // ThirdConstant.WX_USERNAME,createTime, defaultText);
      // responseContent=WxMsgXmlUtil.textMsgToXml(text);
      // }
      //				
      // //如果是事件类型
      // }else if(ThirdConstant.WX_EVENT_TYPE.equals(msgType)){
      // String event=msgData.get("Event");
      // log.info("WxProcessService进入到事件推送的处理...................."+event);
      // //如果是关注事件
      // if(ThirdConstant.WX_EVENT_TYPE_SUBSCRIBE.equals(event.trim())){
      //					
      // log.info("WxProcessService进入到关注事件的处理================"+event);
      // //给用户发送一条纯文本消息,显示菜单内容
      // defaultText="嗨,终于等到您了!欢迎您关注康美云!\n"+ThirdConstant.WX_MENU_TEXT;
      // TextMsg text=new TextMsg(fromUser,
      // ThirdConstant.WX_USERNAME,createTime, defaultText);
      // responseContent=WxMsgXmlUtil.textMsgToXml(text);
      // //如果是菜单点击事件
      // }else if(ThirdConstant.WX_EVENT_TYPE_CLICK.equals(event)){
      //					
      // String eventKey=msgData.get("EventKey");
      //					
      // log.info("进入到菜单点击事件处理过程========="+eventKey+"===="+event);
      //					
      // //点击菜单---每日热卖
      // if(ThirdConstant.WX_KEY_DAYSALEHOT.equals(eventKey)){
      // ArticleMsg article=new ArticleMsg();
      // article.setFromUserName(ThirdConstant.WX_USERNAME);
      // article.setToUserName(fromUser);
      // article.setCreateTime(createTime);
      // article.setMsgType(ThirdConstant.WX_MSG_TYPE_NEWS);
      //						
      // ArticleItem item=new
      // ArticleItem("甘亩仑 阿克苏红枣（袋装） 精品 1000g/袋","精挑细选优质枣，核小肉厚，果色鲜艳，送礼自用都很好","http://img.kmb2b.com/product/upload/product/3544/4682/2014050918284336094_4682_1.jpg","http://www.kmb2b.com/products/4682.html");
      //						
      // ArticleItem item1=new ArticleItem();
      // item1.setUrl("http://www.kmb2b.com/products/3282.html");
      // item1.setTitle("康美药业 菊皇茶 6.5g*10小包");
      // item1.setPicUrl("http://img.kmb2b.com/product/upload/product/2144/3282/20140214150155496445_3282_5.jpg");
      // item1.setDescription("康美明星产品，润喉养颜，纯天然，真草本，四季养生皆宜");
      //						
      // ArticleItem item2=new ArticleItem();
      // item2.setUrl("http://www.kmb2b.com/products/4237.html");
      // item2.setTitle("精品枸杞 宁夏中宁枸杞 产地直销 500g");
      // item2.setPicUrl("http://img.kmb2b.com/product/upload/product/3099/4237/20140312152736536315_4237_5.jpg");
      // item2.setDescription("原生态无污染 特级贡果 滋阴养肾 养肝明目");
      //						
      // ArticleItem item3=new ArticleItem();
      // item3.setUrl("http://www.kmb2b.com/products/3281.html");
      // item3.setTitle("康美药业 玫瑰花 100g/瓶");
      // item3.setPicUrl("http://img.kmb2b.com/product/upload/product/2143/3281/20140219172203731493_3281_5.jpg");
      // item3.setDescription("广西产 美容养颜 通经活络");
      //						
      // ArticleItem item4=new ArticleItem();
      // item4.setUrl("http://www.kmb2b.com/products/3232.html");
      // item4.setTitle("美澳健 美顺子牌芦荟通畅胶囊");
      // item4.setPicUrl("http://img.kmb2b.com/product/upload/product/2086/3232/20140310103117146413_3232_5.jpg");
      // item4.setDescription("清火顺肠，无毒一身轻");
      //						
      // ArticleItem item5=new ArticleItem();
      // item5.setUrl("http://www.kmb2b.com/products/3383.html");
      // item5.setTitle("康美药业 西洋参片 60g/盒");
      // item5.setPicUrl("http://img.kmb2b.com/product/upload/product/2245/3383/2014040211522800530_3383_5.jpg");
      // item5.setDescription("原料加拿大进口，参味浓郁，品质上乘，滋补养生佳品");
      //											
      // article.getArticles().add(item);
      // article.getArticles().add(item1);
      // article.getArticles().add(item2);
      // article.getArticles().add(item3);
      // article.getArticles().add(item4);
      // article.getArticles().add(item5);
      //												
      // article.setArticleCount(String.valueOf(article.getArticles().size()));
      //						
      // responseContent=WxMsgXmlUtil.articlesMsgToXml(article);
      // }
      //					
      // //可以类似于唯品会,发送最大限度的图文消息,做产品的推广了解
      // log.info("事件推送--->点击菜单时触发!");
      //				
      // //用户取消关注
      // }else if(ThirdConstant.WX_EVENT_TYPE_UNSUBSCRIBE.equals(event)){
      //					
      // //之后可以对账户做解绑操作
      // log.info("WxProcessService进入到取消关注康美云微信服务号");
      //				
      // //其它事件类型,比如:地理位置消息
      // }else{
      // log.info("事件推送--->其它事件类型暂时还未处理!");
      // }
      //			
      // //图片消息
      // }else if(ThirdConstant.WX_MSG_TYPE_IMAGE.equals(msgType)){
      // defaultText="抱歉,康美云暂时还看不懂图片!/::)\n"+ThirdConstant.WX_MENU_TEXT;
      // TextMsg text=new TextMsg(fromUser,
      // ThirdConstant.WX_USERNAME,createTime, defaultText);
      // responseContent=WxMsgXmlUtil.textMsgToXml(text);
      //				
      // //链接消息
      // }else if(ThirdConstant.WX_MSG_TYPE_LINK.equals(msgType)){
      // defaultText="抱歉,康美云暂时还看不懂链接消息!/::)\n"+ThirdConstant.WX_MENU_TEXT;
      // TextMsg text=new TextMsg(fromUser,
      // ThirdConstant.WX_USERNAME,createTime, defaultText);
      // responseContent=WxMsgXmlUtil.textMsgToXml(text);
      //				
      // //音乐消息
      // }else if(ThirdConstant.WX_MSG_TYPE_MUSIC.equals(msgType)){
      // defaultText="抱歉,康美云暂时还看不懂音乐消息!/::)\n"+ThirdConstant.WX_MENU_TEXT;
      // TextMsg text=new TextMsg(fromUser,
      // ThirdConstant.WX_USERNAME,createTime, defaultText);
      // responseContent=WxMsgXmlUtil.textMsgToXml(text);
      //				
      // //地理位置消息
      // }else if(ThirdConstant.WX_MSG_TYPE_LOCATION.equals(msgType)){
      // defaultText="抱歉,康美云暂时还看不懂地理位置消息!/::)\n"+ThirdConstant.WX_MENU_TEXT;
      // TextMsg text=new TextMsg(fromUser,
      // ThirdConstant.WX_USERNAME,createTime, defaultText);
      // responseContent=WxMsgXmlUtil.textMsgToXml(text);
      //				
      // //语音消息
      // }else if(ThirdConstant.WX_MSG_TYPE_VOICE.equals(msgType)){
      // defaultText="抱歉,康美云暂时还看不懂语音消息!/::)\n"+ThirdConstant.WX_MENU_TEXT;
      // TextMsg text=new TextMsg(fromUser,
      // ThirdConstant.WX_USERNAME,createTime, defaultText);
      // responseContent=WxMsgXmlUtil.textMsgToXml(text);
      // }

    } catch (Exception e) {
      log.error("WxProcessService 发生异常." + e.getMessage(), e);
      TextMsg text =
          new TextMsg(fromUser, ThirdConstant.WX_USERNAME, new SimpleDateFormat(
              "yyyy-MM-dd hh:mm:ss").format(new Date()), "/::~抱歉,系统正忙,请稍后再试!");
      responseContent = WxMsgXmlUtil.textMsgToXml(text);
    }
    return responseContent;
  }
}
