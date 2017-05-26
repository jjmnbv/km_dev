package com.pltfm.sys.util;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class CutImageUpload {
	 private String srcpath ;
     
	    private String subpath ;
	    
	    //===剪切点X坐标
	    private int x ;
	    
	    private int y ;    
	      
	    //===剪切点宽度
	    private int width ;
	     
	    private int height;
	    
	    public CutImageUpload(){
	            
	    }  
	    public CutImageUpload(int x,int y,int width,int height){
	         this.x = x ;
	         this.y = y ;
	         this.width = width ;   
	         this.height = height ;
	    }
	    
	    /** *//** 
	     * 对图片裁剪，并把剪完的新图片保存
	     */
	    public void cut() throws IOException{ 
	         
	        FileInputStream is = null ;
	        ImageInputStream iis =null ;
	     
	        try{   
	            //读取图片文件
	            is = new FileInputStream(srcpath); 
	            
	            /**//*
	             * 返回包含所有当前已注册 ImageReader 
	             * 生成能够解码指定格式。参数：formatName - 包含非正式格式名称
	             * （例如 "jpeg" 或 "tiff"）等
	             */
	            Iterator it =ImageIO.getImageReadersByFormatName("jpg");  
	            ImageReader reader = (ImageReader)it.next(); 
	            //获取图片流
	            iis = ImageIO.createImageInputStream(is);
	               
	            /**//* 
	             * <p>iis:读取源Դ.true:只想前搜索 </p>.将它标记为‘只想前搜索’
	             * 此设置意味着包含在输入源中的图像将只按顺序读取，可能允许 reader
	             * 避免缓存包含与以前已经读取的图像关联的数据的那些输入部分。
	             */
	            reader.setInput(iis,true) ;
	            
	            /**//* 
	             * <p>描述如何对流进行解码的类<p>.用于指定如何在输入时从Java Image I/O 
	             * 框架的上下文中的流转换一幅图像或一组图像。用于特定图像格式的插件
	             * 将从其 ImageReader 实现的 getDefaultReadParam 方法中返回
	             * ImageReadParam 的实例
	             */
	            ImageReadParam param = reader.getDefaultReadParam(); 
	             
	            /**//*
	             * 图片裁剪区域。Rectangle 制定了坐标空间中的一个区域，通过Rectangle 对象
	             * 的坐上顶点的坐标(x，y)、宽度和高度可以定义这个区域
	             */ 
	            Rectangle rect = new Rectangle(x, y, width, height); 
	            
	              
	            //提供一个 BufferedImage，将其用作解码像素数据的目标
	            param.setSourceRegion(rect); 

	            /**//*
	             * 使用所提供的 ImageReadParam 读取通过索引 imageIndex 制定的对象，并将
	             * 它作为一个完整的 BufferedImage 返回。
	             */
	            BufferedImage bi = reader.read(0,param);                
	      
	            //保存新图片
	            ImageIO.write(bi, "jpg", new File(subpath));     
	        }
	        
	        finally{
	            if(is!=null)
	               is.close() ;       
	            if(iis!=null)
	               iis.close();  
	        } 
	        
	         
	     
	    }

	    public int getHeight(){
	        return height;
	    }

	    public void setHeight(int height){
	        this.height = height;
	    }

	    public String getSrcpath(){
	        return srcpath;
	    }

	    public void setSrcpath(String srcpath){
	        this.srcpath = srcpath;
	    }

	    public String getSubpath(){
	        return subpath;
	    }

	    public void setSubpath(String subpath){
	        this.subpath = subpath;
	    }

	    public int getWidth(){
	        return width;
	    }

	    public void setWidth(int width){
	        this.width = width;
	    }

	    public int getX(){
	        return x;
	    }

	    public void setX(int x){
	        this.x = x;
	    }

	    public int getY(){
	        return y;
	    }

	    public void setY(int y){
	        this.y = y;
	    } 

}
