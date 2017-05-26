package com.kmzyc.framework.email;

import java.security.Security;
import java.util.Date;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailSender {
  private static String loginname = "service@132335mail.com";

  private static String password = "service";
  private final static String SMTP_HOST = "132335mail.com"; //

  // private static Logger logger = Logger.getLogger(EmailSender.class);
  private static Logger logger = LoggerFactory.getLogger(EmailSender.class);

  @SuppressWarnings("restriction")
  public static void gmailSend(String subject, String body, String email) {
    Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
    Properties props = System.getProperties();
    props.setProperty("mail.smtp.host", SMTP_HOST);
    props.setProperty("mail.smtp.socketFactory.fallback", "false");
    props.setProperty("mail.smtp.port", "25");
    props.put("mail.smtp.auth", "false");
    // final String username = Loginname;
    // final String password = Password;
    Session session = Session.getInstance(props, new Authenticator() {
      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(loginname, password);
      }
    });

    // -- Create a new message --
    Message mimeMessage = new MimeMessage(session);

    // -- Set the FROM and TO fields --
    try {
      mimeMessage.setFrom(new InternetAddress("service@132335mail.com")); // @findex8.com

      mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email, false));
      mimeMessage.setSubject(subject);
      mimeMessage.setText(body);
      mimeMessage.setSentDate(new Date());// 发送日期
      Transport.send(mimeMessage);
      System.out.println("Message sent.");
    } catch (AddressException e) {
      logger.error("查询常见问题出错：" + e.getMessage(), e);
    } catch (MessagingException e) {
      logger.error("查询常见问题出错：" + e.getMessage(), e);
    }
  }

  @SuppressWarnings("restriction")
  public static void gmailHtmlSend(String title, String strPlainBody, String email,
      String strHtmlBody) {
    Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
    // final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
    // Get a Properties object
    Properties props = System.getProperties();
    props.setProperty("mail.smtp.host", SMTP_HOST);
    // props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
    props.setProperty("mail.smtp.socketFactory.fallback", "false");
    props.setProperty("mail.smtp.port", "25");
    // props.setProperty("mail.smtp.socketFactory.port", "25");
    props.put("mail.smtp.auth", "true");
    // final String username = Loginname;
    // final String password = Password;
    Session session = Session.getInstance(props, new Authenticator() {
      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(loginname, password);
      }
    });

    // -- Create a new message --
    Message mimeMessage = new MimeMessage(session);

    // -- Set the FROM and TO fields --
    try {
      mimeMessage.setFrom(new InternetAddress("service@132335mail.com"));

      mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email, false));
      mimeMessage.setSubject(title);

      // msg.setText(body);
      {
        // Create an "Alternative" Multipart message
        Multipart mp = new MimeMultipart("alternative");

        // Read text file, load it into a BodyPart, and add it to the
        // message.
        {
          BodyPart messageBodyPart = new MimeBodyPart();
          messageBodyPart.setContent(strPlainBody, "text/html;charset=UTF-8");
          mp.addBodyPart(messageBodyPart);
        }

        // Do the same with the HTML part
        {
          BodyPart messageBodyPart = new MimeBodyPart();
          messageBodyPart.setContent(strHtmlBody, "text/html;charset=UTF-8");
          mp.addBodyPart(messageBodyPart);
        }

        // Set the content for the message and transmit
        mimeMessage.setContent(mp);

      }
      mimeMessage.setSentDate(new Date());
      Transport.send(mimeMessage);
      System.out.println("Message sent.");
    } catch (AddressException e) {
      logger.error("查询常见问题出错：" + e.getMessage(), e);
    } catch (MessagingException e) {
      logger.error("查询常见问题出错：" + e.getMessage(), e);
    }
  }

  public static void main(String[] args) {

    String content =
        "尊敬的****（会员账号）：\n" + "\n     您设置的密码为：********（用户原来设置的密码），请牢记您的密码。**********"
            + "\n                    或通过在线客服联系我们";

    gmailSend("注册", content, "a@163.com");
  }

}
