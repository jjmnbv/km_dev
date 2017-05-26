package com.pltfm.sys.util;

import org.jdom.*;
import org.jdom.output.*;
import org.jdom.input.*;
import java.io.*;
import java.net.URL;

/**
 * <p>Title: 封装JDom</p>
 * <p>Description: 封装JDom，提供常用方法
 * @version 1.0
 */

public class JDomUtil
{
    public JDomUtil()
    {
    }
    /**
     * 根据XML字符串建立JDom的Document对象
     * @param xmlString XML格式的字符串
     * @return Document返回建立的JDom的Document对象，建立不成功返回null
     */
    public static Document getDocument(String xmlString)
    {
        try
        {
            SAXBuilder builder = new SAXBuilder();
            Document anotherDocument = builder.build(new StringReader(xmlString));
            return anotherDocument;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据xml字符串建立Jdom的Document对象
     * @param xmlString XML格式的字符串
     * @return Document返回建立的Jdom的Document对象，建立不成功返回null
     */
    public static Document getDocument(File file)
    {
        try
        {
            SAXBuilder builder = new SAXBuilder();
            Document anotherDocument = builder.build(file);
            return anotherDocument;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 根据xml流建立jdom的document对象
     * @param ins InputStream XML字符流
     * @return Document 返回建立的jdom的document对象，建立不成功返回null
     */
    public static Document getDocument(InputStream ins)
    {
        try
        {
            SAXBuilder builder = new SAXBuilder();
            Document anotherDocument = builder.build(ins);
            return anotherDocument;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据url链接 建立jdom的document对象
     * @param urlStr String URL链接
     * @return Document 返回建立的jdom的document对象，建立不成功返回null
     */
    public static Document getDocumentByURL(String urlStr)
    {
        try
        {
            URL url = new URL(urlStr);
            InputStream ins = url.openStream();
            SAXBuilder builder = new SAXBuilder();
            Document anotherDocument = builder.build(ins);
            return anotherDocument;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据xml文件名 建立jdom的document对象
     * @param fileName XML格式的字符串
     * @return Document 返回建立的jdom的document对象，建立不成功返回null
     */
    public static Document getDocumentByFile(String fileName)
    {
        try
        {
            SAXBuilder builder = new SAXBuilder();
            Document anotherDocument = builder.build(new File(fileName));
            return anotherDocument;
        }
        catch (Exception e)
        {
            System.out.println("parse file error:"+fileName);
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 这个方法将jdom Elements对象转换成字符串
     * @param xmlDoc 将要被转换的jdom对象
     * @return String
     */
    public static String toXML(Element xmlDoc)
    {
        return toXML(xmlDoc, "UTF-8", false);
    }
    /**
     * 将jdom Element对象转换成字符串
     * @param xmlDoc 将要被转换的jdom对象
     * @param encoding 输出字符串使用的编码
     * @param newline 是否换行
     * @return String
     */
    public static String toXML(Element xmlDoc, String encoding, boolean newline)
    {
        ByteArrayOutputStream byteRep = new ByteArrayOutputStream();
        Format format = Format.getPrettyFormat();
        format.setEncoding(encoding);
        XMLOutputter docWriter = new XMLOutputter(format);
        try
        {
            docWriter.output(xmlDoc, byteRep);
        }
        catch (Exception e)
        {
        }
        return byteRep.toString();
    }
    /**
     * 将JDom Document对象转换成字符串
     * @param xmlDoc 将要被转换的JDom对象
     * @return String
     */
    public static String toXML(Document xmlDoc)
    {
        return toXML(xmlDoc, "UTF-8", false);
    }
    /**
     * 将JDom Document对象转换成字符串
     * @param xmlDoc 将要被转换的JDom对象
     * @param encoding 输出字符串使用的编码
     * @param newline 是否换行
     * @return String
     */
    public static String toXML(Document xmlDoc, String encoding, boolean newline)
    {
        ByteArrayOutputStream byteRep = new ByteArrayOutputStream();
        Format format = Format.getPrettyFormat();
        format.setEncoding(encoding);
        XMLOutputter docWriter = new XMLOutputter(format);
        try
        {
            docWriter.output(xmlDoc, byteRep);
        }
        catch (Exception e)
        {
        }
        return byteRep.toString();
    }
    /**
     * 取出List中的数据为数组
     * @param list List
     * @return String[]
     */
    public static String[] getListText(java.util.List list)
    {
        String result[] = new String[list.size()];
        for (int i=0;i<list.size();i++)
        {
            result[i] = ((Element)list.get(i)).getText().trim();
        }
        return result;
    }
}
