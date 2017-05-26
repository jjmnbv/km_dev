package com.kmzyc.supplier.action;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Controller("imageAction")
public class ImageAuthCodeAction extends ActionSupport{

    private ByteArrayInputStream inputStream;

    public final Font[] defFonts = {new Font("Serif", Font.ITALIC, 18),
            new Font("Serif", Font.ITALIC, 18), new Font("Serif", Font.ITALIC, 22),
            new Font("黑体", Font.BOLD, 16), new Font("rom7", Font.PLAIN, 20),
            new Font("Serif", Font.ITALIC, 22)};

    private char[] codeSequence = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
            'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
            'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9'};

    private Font getFont(int i) {
        Random random = new Random((long)Math.random() * 10000 * (i + 1));
        int a = random.nextInt(defFonts.length);
        AffineTransform trans = new AffineTransform();
        trans.scale(1 + random.nextFloat() * 0.2, 0.95 + random.nextFloat() * 0.65);
        return defFonts[a].deriveFont(trans);
    }
		
    public String execute() throws Exception{
        //在内存中创建图象
        int width=70, height=30;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //获取图形上下文
        Graphics g = image.getGraphics();
        //生成随机类
        Random random = new Random();
        //设定背景色
        g.setColor(getRandColor(200,250));
        g.fillRect(0, 0, width, height);
        //设定字体
        g.setFont(new Font("Times New Roman",Font.PLAIN,18));
        //随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
        g.setColor(getRandColor(160,200));
        for (int i=0;i<155;i++){
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x,y,x+xl,y+yl);
        }
        //取随机产生的认证码(6位数字)
        StringBuilder sRand=new StringBuilder();
        for (int i=0;i<4;i++){
            String rand = String.valueOf(codeSequence[random.nextInt(36)]);
            sRand.append(rand);
            g.setFont(getFont(i));
            //将认证码显示到图象中
            g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
            //调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
            g.drawString(rand, 16 * i + 2 + random.nextInt(3), 25);
        }
        int a=random.nextInt(20);
        int b=random.nextInt(100);
        int c=random.nextInt(100);
        int d=random.nextInt(10);
        int e=random.nextInt(100);
        int f=random.nextInt(10);
        int h=random.nextInt(100);
        int xPoints[]={a,h,d,f,b,e,c};
        int yPoints[]={d,c,b,a,h,f,e};
        int nPoints = 6;
        g.drawPolyline(xPoints, yPoints, nPoints);
        //将认证码存入SESSION
        ActionContext.getContext().getSession().put("veCode",sRand.toString());
        //图象生效
        g.dispose();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ImageOutputStream imageOut = ImageIO.createImageOutputStream(output);
        ImageIO.write(image, "JPEG", imageOut);
        imageOut.close();
        ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());
        this.setInputStream(input);
        return SUCCESS;
    }

    /**
     * 给定范围获得随机颜色
     * @param fc
     * @param bc
     * @return
     */
    private Color getRandColor(int fc,int bc){
        Random random = new Random();
        if(fc>255) fc=255;
        if(bc>255) bc=255;
        int r=fc+random.nextInt(bc-fc);
        int g=fc+random.nextInt(bc-fc);
        int b=fc+random.nextInt(bc-fc);
        return new Color(r,g,b);
    }

    public void setInputStream(ByteArrayInputStream inputStream) {
        this.inputStream = inputStream;
    }

    public ByteArrayInputStream getInputStream() {
        return inputStream;
    }

}