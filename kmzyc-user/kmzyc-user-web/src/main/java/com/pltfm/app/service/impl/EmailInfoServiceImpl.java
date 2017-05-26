package com.pltfm.app.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.dao.EmailInfoDAO;
import com.pltfm.app.service.EmailInfoService;
import com.pltfm.app.vobject.Coupons;

/**
 * 邮箱验证信息业务逻辑类
 * 
 * @author cjm
 * @since 2013-7-23
 */
@Component(value = "emailInfoService")
public class EmailInfoServiceImpl implements EmailInfoService {

  /**
   * 邮箱验证信息DAO接口
   */
  @Resource(name = "emailInfoDAO")
  private EmailInfoDAO emailInfoDAO;

  /** 通过依赖注入发邮件组件实例 **/
 /*删除邮件业务 @Resource(name = "mailSender")
  private JavaMailSenderImpl mailSender;*/

  /**
   * 根据vo条件查询分类信息page
   * 
   * @param pageParam 分页实体
   * @param vo 手机随机码实体
   * @return 返回值
   * @throws Exception
   */
/*删除邮件业务  @Override
  public Page searchPageByVo(Page pageParam, EmailInfo vo) throws Exception {
    if (pageParam == null) {
      pageParam = new Page();
    }
    if (vo == null) {
      vo = new EmailInfo();
    }
    // 获取客户积分规则总数
    int totalNum = emailInfoDAO.selectCountByVo(vo);
    pageParam.setRecordCount(totalNum);
    // 设置查询开始结束索引
    vo.setSkip(pageParam.getStartIndex());
    vo.setMax(pageParam.getStartIndex() + pageParam.getPageSize());
    pageParam.setDataList(emailInfoDAO.selectPageByVo(vo));

    return pageParam;
  }

  *//**
   * 添加邮箱随机码
   * 
   * @param record 邮箱随机码实体
   * @return 返回值
   * @throws Exception 异常
   *//*
  @Override
  public Integer addEmailInfo(EmailInfo emailInfo) throws Exception {
    Random r = new Random();
    // 随机码
    Integer ran = r.nextInt(9999) + 1000;


    String host = "10.1.0.209"; // 本机smtp服务器
    // String host = "smtp.163.com"; // 163的smtp服务器
    String from = "admin@132335mail.com"; // 邮件发送人的邮件地址
    final String username = "admin"; // 发件人的邮件帐户
    final String password = "admin"; // 发件人的邮件密码
    String code = ran.toString();
    if (code.length() == 5) {
      code = code.substring(0, code.length() - 1);
    }
    emailInfo.setTattedCode(code);
    emailInfo.setN_FailureTimeValue(30);

    Date d = DateTimeUtils.getCalendarInstance().getTime();

    emailInfo.setD_CreateDate(d);
    emailInfo.setLastSendTattedcodeTime(DateTimeUtils.getDateTime(d));


    // 创建Properties 对象
    Properties props = System.getProperties();

    // 添加smtp服务器属性
    props.put("mail.smtp.host", host);
    props.put("mail.smtp.auth", "true");

    // 创建邮件会话
    Session session = Session.getDefaultInstance(props, new Authenticator() {
      @Override
      public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
      }

    });

    try {
      // 定义邮件信息
      MimeMessage message = new MimeMessage(session);
      message.setFrom(new InternetAddress(from));
      message.addRecipient(Message.RecipientType.TO,
          new InternetAddress(emailInfo.getUrlAddress()));
      // message.setSubject(transferChinese("我有自己的邮件服务器了"));
      message.setSubject("随机码");
      message.setText(emailInfo.getTattedCode());

      // 发送消息
      session.getTransport("smtp").send(message);
      // Transport.send(message); //也可以这样创建Transport对象发送
      // System.out.println("SendMail Process Over!");

    } catch (MessagingException e) {
      e.printStackTrace();
    }

    return emailInfoDAO.insert(emailInfo);
  }*/

  /**
   * 根据vo条件查询分类信息page
   * 
   * @param pageParam 分页实体
   * @param vo 优惠劵实体
   * @return 返回值
   * @throws Exception
   */
  public Page searchPageByCoupons(Page pageParam, Coupons coupons) throws Exception {

    if (pageParam == null) {
      pageParam = new Page();
    }
    if (coupons == null) {
      coupons = new Coupons();
    }
    // 获取优惠劵总数据
    int totalNum = emailInfoDAO.selectCountByCoupons(coupons);
    pageParam.setRecordCount(totalNum);
    // 设置查询开始结束索引
    coupons.setSkip(pageParam.getStartIndex());
    coupons.setMax(pageParam.getStartIndex() + pageParam.getPageSize());
    pageParam.setDataList(emailInfoDAO.selectPageByCoupons(coupons));

    return pageParam;

  }

  public EmailInfoDAO getEmailInfoDAO() {
    return emailInfoDAO;
  }

  public void setEmailInfoDAO(EmailInfoDAO emailInfoDAO) {
    this.emailInfoDAO = emailInfoDAO;
  }

 /* public JavaMailSenderImpl getMailSender() {
    return mailSender;
  }

  public void setMailSender(JavaMailSenderImpl mailSender) {
    this.mailSender = mailSender;
  }*/

}
