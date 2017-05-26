package com.kmzyc.framework.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;


@SuppressWarnings("serial")
public class VerifyCodeServlet extends HttpServlet {
	static Logger logger = LoggerFactory.getLogger(VerifyCodeServlet.class);

	private InputStream imageStream = new ByteArrayInputStream(new byte[0]);

	final String ENCODING_TOMCAT_URL_GBK = "GBK";

	Color getRandColor(int fc,int bc){//
		Random random = new Random();
		if (fc>255)
			fc=255;
		if (bc>255)
			bc=255;
		int r = fc+random.nextInt(bc-fc);
		int g = fc+random.nextInt(bc-fc);
		int b = fc+random.nextInt(bc-fc);
		return new Color(r,g,b);
	}

    public VerifyCodeServlet() {
        super();
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		perform(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		perform(request, response);
	}

	private void perform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setHeader("Pragma","No-cache");
		response.setHeader("Cache-Control","no-cache");
		response.setHeader("Cache-Control","no-store");
		response.setDateHeader("Expires", 0);


		response.setContentType("image/jpeg");
		response.addHeader("pragma","NO-cache");
		response.addHeader("Cache-Control","no-cache");
		response.addDateHeader("Expries",0);
		Random random = new Random();
		int width=60,
		height=21;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		g.setColor(getRandColor(180,250));
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("Times New Roman",Font.PLAIN,18));
		g.setColor(new Color(random.nextInt(130),random.nextInt(180),random.nextInt(200)));
		g.drawRect(0,0,width-1,height-1);

		String sRand="";
		for (int i=0;i<4;i++){
	    	String rand=String.valueOf(random.nextInt(10));
	    	sRand+=rand;
	    	g.setColor(new Color(50+random.nextInt(110),50+random.nextInt(110),50+random.nextInt(110)));//调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
	    	g.drawString(rand,13*i+6,16);
		}
		request.getSession(true).setAttribute("randomCode",sRand);

		//System.out.println("image.jsp-------"+"session ramdom "+session.getAttribute("random")+"-------"+"id:"+session.getId());
		g.dispose();
		try {
			ServletOutputStream outStream=null ;
			outStream = response.getOutputStream();
//			JPEGImageEncoder encoder =JPEGCodec.createJPEGEncoder(outStream);
			ImageIO.write(image,"jpg",outStream);
//			encoder.encode(image);
			outStream.close();
		} catch (IOException e) {
			logger.warn ("", e);
//			throw new Exception (e);
			System.out.println("error");
		}
	}

	public InputStream getImageStream() {
		return imageStream;
	}
}
