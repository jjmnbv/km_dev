package com.pltfm.sys.util;
//package com.pltfm.index.util;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//
//import com.wutka.jox.JOXBeanInputStream;
//import com.wutka.jox.JOXBeanOutputStream;
//
//public class BeanXMLMapping {
//
//	public static Object fromXML(String xml, Class className) {
//
//		ByteArrayInputStream xmlData = new ByteArrayInputStream(xml.getBytes());
//		JOXBeanInputStream joxIn = new JOXBeanInputStream(xmlData);
//		try {
//			return (Object) joxIn.readObject(className);
//		} catch (IOException exc) {
//			exc.printStackTrace();
//			return null;
//		} finally {
//			try {
//				xmlData.close();
//				joxIn.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	public static String toXML(Object bean) {
//
////		ByteArrayOutputStream xmlData = new ByteArrayOutputStream();
////		JOXBeanOutputStream joxOut = new JOXBeanOutputStream(xmlData);
////		try {
////			joxOut.writeObject(beanName(bean), bean);
////			return xmlData.toString();
////		} catch (IOException exc) {
////			exc.printStackTrace();
////			return null;
////		} finally {
////			try {
////				xmlData.close();
////				joxOut.close();
////			} catch (Exception e) {
////				e.printStackTrace();
////			}
////		}
//		return null;
//	}
//
//	private static String beanName(Object bean) {
//
//		String fullClassName = bean.getClass().getName();
//		String classNameTemp = fullClassName.substring(
//		fullClassName.lastIndexOf(".") + 1,
//		fullClassName.length()
//		);
//
//		return classNameTemp.substring(0, 1)
//		+ classNameTemp.substring(1);
//	}
//
//}