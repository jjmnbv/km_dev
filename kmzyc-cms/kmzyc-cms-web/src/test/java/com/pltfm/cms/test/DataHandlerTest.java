package com.pltfm.cms.test;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pltfm.cms.parse.DataFetcher;
import com.pltfm.cms.parse.DataHandler;
import com.pltfm.cms.vobject.CmsPage;
import com.pltfm.cms.vobject.CmsWindow;

import junit.framework.TestCase;

public class DataHandlerTest extends TestCase {

	private ApplicationContext context;
	
	private DataHandler dataHandler;
	
	@Override
	protected void setUp() throws Exception {
	    /*
		context = new ClassPathXmlApplicationContext("config/spring-context-application.xml");
		dataHandler = (DataHandler)context.getBean("dataHandler");*/
	}
	
	@Test
	public void testGetData(){
	    /*
		CmsPage page = new CmsPage();
		page.setPageId(2);
		page.setKeywords("test");
		page.setDescribe("test");
		page.setTemplatePath("D:\\code\\cms\\template\\page.html");
		List<Object> list = dataHandler.getWindows(page);
		for (Object o:list) {
			CmsWindow win = (CmsWindow)o;
			System.out.print("WindowId: " + win.getWindowId());
			System.out.print("\tName: " + win.getName());
			System.out.println("\tPath: " + win.getPath());
		}
		Map<String, Object> data = dataHandler.getWindowData((CmsWindow)list.get(0));
		System.out.println("data: " + data.size());*/
	}
}
