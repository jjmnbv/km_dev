package com.pltfm.cms.test;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pltfm.cms.parse.DataFetcher;
import com.pltfm.cms.vobject.CmsPage;

import junit.framework.TestCase;

public class DataFetcherTest extends TestCase {

	private ApplicationContext context;
	
	private DataFetcher dataFetcher;
	
	@Override
	protected void setUp() throws Exception {
		/*context = new ClassPathXmlApplicationContext("config/spring-context-application.xml");
		dataFetcher = (DataFetcher)context.getBean("dataFetcher");*/
	}
	
	@Test
	public void testFetch(){
	    /*	CmsPage page = new CmsPage();
		page.setPageId(2);
		page.setKeywords("test");
		page.setDescribe("test");
		page.setTemplatePath("D:\\code\\cms\\template\\page.html");
		List<Object> list = dataFetcher.getWindow(page);
		System.out.println(list.size());*/
	}
}
