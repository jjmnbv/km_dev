package com.pltfm.cms.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.TestCase;

import com.pltfm.cms.parse.DataFetcher;
import com.pltfm.cms.vobject.CmsPage;
import com.pltfm.cms.vobject.CmsWindow;

public class DataFetchTest{
	
	
   private static ApplicationContext context;
	
	private static DataFetcher dataFetcher;
	
	public static void setUp() throws Exception {
		/*context = new ClassPathXmlApplicationContext("config/spring-context-application.xml");
		dataFetcher = (DataFetcher)context.getBean("dataFetcher");*/
	}
	
	public static void testTemp(){
	/*	System.out.println("============");
		CmsPage page = new CmsPage();
		page.setPageId(2);
		page.setKeywords("test");
		page.setDescribe("test");
		page.setTemplatePath("D:\\code\\cms\\template\\page.html");
		List<Object> list = dataFetcher.getWindow(page);
		System.out.println(list.size());*/
	}
	
	public static void main(String[] args) {
	   System.out.print("aaaaaaaaaa");
	}
}
