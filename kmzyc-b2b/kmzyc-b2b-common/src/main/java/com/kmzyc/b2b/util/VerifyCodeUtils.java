package com.kmzyc.b2b.util;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Random;

import javax.imageio.ImageIO;

public class VerifyCodeUtils {
  private static final String VERIFY_CODES = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXZY";
  private static Random random = new Random();

  private static String generateVerifyCode() {
    StringBuilder verifyCode = new StringBuilder();
    for (int i = 0; i < 4; i++) {
      verifyCode.append(VERIFY_CODES.charAt(random.nextInt(35)));
    }
    return verifyCode.toString();
  }

  /**
   * 生成算式及结果 0:算式 1:结果
   * 
   * @return
   */
  private static String[] genFormula() {
    String[] result = new String[2];
    int formula = random.nextInt(3);// 0+ 1- 2x
    int[] num = new int[2];
    if (0 == formula) {
      num[0] = random.nextInt(40) + 10;
      num[1] = random.nextInt(50) + 1;
      result[0] = num[0] + " 加 " + num[1];
      result[1] = String.valueOf(num[0] + num[1]);
    } else if (1 == formula) {
      num[0] = random.nextInt(50) + 50;
      num[1] = random.nextInt(num[0]);
      result[0] = num[0] + " 减 " + num[1];
      result[1] = String.valueOf(num[0] - num[1]);
    } else {
      num[0] = random.nextInt(9) + 5;
      num[1] = random.nextInt(5) + (num[0] > 9 ? 3 : 7);
      result[0] = num[0] + (num[0] > 9 ? " 乘 " : "  乘 ") + num[1];
      result[1] = String.valueOf(num[0] * num[1]);
    }
    return result;
  }

  /**
   * 输出指定验证码图片流
   * 
   * @param w
   * @param h
   * @param os
   * @param isNum
   * @throws IOException
   */
  public static String outputImage(int w, int h, OutputStream os, boolean isNum) throws IOException {
    String code, result;
    if (isNum) {
      code = generateVerifyCode();
      result = code;
    } else {
      String[] strs = genFormula();
      code = strs[0];
      result = strs[1];
    }
    int verifySize = code.length();
    BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
    Random rand = new Random();
    Graphics2D g2 = image.createGraphics();
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    Color[] colors = new Color[5];
    Color[] colorSpaces =
        new Color[] {Color.WHITE, Color.CYAN, Color.GRAY, Color.LIGHT_GRAY, Color.MAGENTA,
            Color.ORANGE, Color.PINK, Color.YELLOW};
    float[] fractions = new float[colors.length];
    for (int i = 0; i < colors.length; i++) {
      colors[i] = colorSpaces[rand.nextInt(colorSpaces.length)];
      fractions[i] = rand.nextFloat();
    }
    Arrays.sort(fractions);

    g2.setColor(Color.GRAY);// 设置边框色
    g2.fillRect(0, 0, w, h);

    Color c = getRandColor(200, 250);
    g2.setColor(c);// 设置背景色
    g2.fillRect(0, 2, w, h - 4);

    // 绘制干扰线
    Random random = new Random();
    g2.setColor(getRandColor(160, 200));// 设置线条的颜色
    for (int i = 0; i < 20; i++) {
      int x = random.nextInt(w - 1);
      int y = random.nextInt(h - 1);
      int xl = random.nextInt(6) + 1;
      int yl = random.nextInt(12) + 1;
      g2.drawLine(x, y, x + xl + 40, y + yl + 20);
    }

    // 添加噪点
    float yawpRate = 0.05f;// 噪声率
    int area = (int) (yawpRate * w * h);
    for (int i = 0; i < area; i++) {
      int x = random.nextInt(w);
      int y = random.nextInt(h);
      int rgb = getRandomIntColor();
      image.setRGB(x, y, rgb);
    }

    shear(g2, w, h, c);// 使图片扭曲
    Color sColor = getRandColor(100, 160);
    g2.setColor(sColor);
    int fontSize = h - 4;
    Font font = new Font("Algerian", Font.ITALIC, fontSize);
    Font f = new Font("Serif", Font.PLAIN, h - 40);
    g2.setFont(font);
    char[] chars = code.toCharArray();
    for (int i = 0; i < verifySize; i++) {
      if (chars[i] != '加' && chars[i] != '减' && chars[i] != '乘') {
        AffineTransform affine = new AffineTransform();
        affine.setToRotation(Math.PI / 8 * rand.nextDouble() * (rand.nextBoolean() ? 1 : -1),
            (w / verifySize) * i + fontSize / 2, h / 2);
        g2.setTransform(affine);
        g2.setFont(font);
        g2.setColor(sColor);
      } else {
        g2.setFont(f);
        g2.setColor(Color.RED);
      }
      g2.drawChars(chars, i, 1, ((w - 15) / verifySize) * i + 5, h / 2 + fontSize / 2 - 10);
    }
    g2.dispose();
    ImageIO.write(image, "jpg", os);
    return result;
  }

  private static Color getRandColor(int fc, int bc) {
    if (fc > 255) fc = 255;
    if (bc > 255) bc = 255;
    int r = fc + random.nextInt(bc - fc);
    int g = fc + random.nextInt(bc - fc);
    int b = fc + random.nextInt(bc - fc);
    return new Color(r, g, b);
  }

  private static int getRandomIntColor() {
    int[] rgb = getRandomRgb();
    int color = 0;
    for (int c : rgb) {
      color = color << 8;
      color = color | c;
    }
    return color;
  }

  private static int[] getRandomRgb() {
    int[] rgb = new int[3];
    for (int i = 0; i < 3; i++) {
      rgb[i] = random.nextInt(255);
    }
    return rgb;
  }

  private static void shear(Graphics g, int w1, int h1, Color color) {
    shearX(g, w1, h1, color);
    shearY(g, w1, h1, color);
  }

  private static void shearX(Graphics g, int w1, int h1, Color color) {
    int period = random.nextInt(2);
    int frames = 1;
    int phase = random.nextInt(2);
    for (int i = 0; i < h1; i++) {
      double d =
          (double) (period >> 1)
              * Math.sin((double) i / (double) period + (6.2831853071795862D * (double) phase)
                  / (double) frames);
      g.copyArea(0, i, w1, 1, (int) d, 0);
      g.setColor(color);
      g.drawLine((int) d, i, 0, i);
      g.drawLine((int) d + w1, i, w1, i);
    }
  }

  private static void shearY(Graphics g, int w1, int h1, Color color) {
    int period = random.nextInt(40) + 10; // 50;
    int frames = 20;
    int phase = 7;
    for (int i = 0; i < w1; i++) {
      double d =
          (double) (period >> 1)
              * Math.sin((double) i / (double) period + (6.2831853071795862D * (double) phase)
                  / (double) frames);
      g.copyArea(i, 0, 1, h1, 0, (int) d);
      g.setColor(color);
      g.drawLine(i, (int) d, i, 0);
      g.drawLine(i, (int) d + h1, i, h1);
    }
  }
}
