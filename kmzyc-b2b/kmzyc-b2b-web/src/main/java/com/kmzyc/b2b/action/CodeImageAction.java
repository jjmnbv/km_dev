package com.kmzyc.b2b.action;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.zkconfig.ConfigurationUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 废弃
 */
@Controller("CodeImageAction")
@Scope("prototype")
@SuppressWarnings("unchecked")
public class CodeImageAction extends ActionSupport {
  private static final long serialVersionUID = 1L;
  private String imgKey;
  private String imgCode;
  private ByteArrayInputStream inputStream;

  public String validCodeImg() throws Exception {
    String code = null;
    if (null != imgKey && imgKey.length() > 0)
      code = (String) ActionContext.getContext().getSession().get(imgKey);
    else
      code = (String) ActionContext.getContext().getSession().get("veCode");
    if (null != imgCode && imgCode.length() > 0 && imgCode.equals(code)) {
      ServletActionContext.getResponse().getWriter().print("1");
    } else {
      if (null != imgKey && imgKey.length() > 0)
        ActionContext.getContext().getSession().remove(imgKey);
      else
        ActionContext.getContext().getSession().remove("veCode");
    }
    return null;
  }

  @Override
public String execute() throws Exception {
    int width = 60, height = 20;
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    Graphics g = image.getGraphics();
    Random random = new Random();
    g.setColor(getRandColor(200, 250));
    g.fillRect(0, 0, width, height);
    g.setFont(new Font("Times New Roman", Font.PLAIN, 18));
    g.setColor(getRandColor(160, 200));
    for (int i = 0; i < 155; i++) {
      int x = random.nextInt(width);
      int y = random.nextInt(height);
      int xl = random.nextInt(12);
      int yl = random.nextInt(12);
      g.drawLine(x, y, x + xl, y + yl);
    }
    String sRand = "";
    for (int i = 0; i < 4; i++) {
      String rand = String.valueOf(random.nextInt(10));
      sRand += rand;
      g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random
          .nextInt(110)));
      g.drawString(rand, 13 * i + 6, 16);
    }


    String isTest = ConfigurationUtil.getString("isTestMobileVerifyCode");// 是否为测试环境
    if ("true".equals(isTest)) {
      sRand = "6666";
    }


    Map session = ActionContext.getContext().getSession();
    if (null != imgKey && imgKey.length() > 0) {
      session.remove(imgKey);
      session.remove("wapRegistCopy");
      session.put(imgKey, sRand);
      session.put("wapRegistCopy", sRand);
    } else {
      session.remove("veCode");
      session.remove("wapRegistCopy");
      session.put("veCode", sRand);
      session.put("wapRegistCopy", sRand);
    }
    g.dispose();
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    ImageOutputStream imageOut = ImageIO.createImageOutputStream(output);
    ImageIO.write(image, "JPEG", imageOut);
    imageOut.close();
    ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());
    this.setInputStream(input);
    return SUCCESS;
  }

  /*
   * 给定范围获得随机颜色
   */
  private Color getRandColor(int fc, int bc) {
    Random random = new Random();
    if (fc > 255) fc = 255;
    if (bc > 255) bc = 255;
    int r = fc + random.nextInt(bc - fc);
    int g = fc + random.nextInt(bc - fc);
    int b = fc + random.nextInt(bc - fc);
    return new Color(r, g, b);
  }

  public void setInputStream(ByteArrayInputStream inputStream) {
    this.inputStream = inputStream;
  }

  public ByteArrayInputStream getInputStream() {
    return inputStream;
  }

  public String getImgKey() {
    return imgKey;
  }

  public void setImgKey(String imgKey) {
    this.imgKey = imgKey;
  }

  public String getImgCode() {
    return imgCode;
  }

  public void setImgCode(String imgCode) {
    this.imgCode = imgCode;
  }
}
