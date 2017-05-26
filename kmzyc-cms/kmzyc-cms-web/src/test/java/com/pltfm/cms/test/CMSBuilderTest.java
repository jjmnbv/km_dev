package com.pltfm.cms.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pltfm.cms.parse.DataHandler;
import com.pltfm.cms.parse.DefaultHtmlBuilder;
import com.pltfm.cms.vobject.CmsPage;

public class CMSBuilderTest extends TestCase{
	private static Logger logger = LoggerFactory.getLogger(CMSBuilderTest.class);

	@Override
	protected void setUp() throws Exception {
	    /*
		context = new ClassPathXmlApplicationContext("config/spring-context-application.xml");*/
	}
	
	@Test
	public void testBuilder() throws Exception{
	    /*
		DefaultHtmlBuilder builder = (DefaultHtmlBuilder)context.getBean("htmlBuilder");
		CmsPage page = new CmsPage();
		page.setPageId(2);
		page.setKeywords("test");
		page.setDescribe("test");
		page.setTemplatePath("D:\\code\\cms\\template\\index.html");
		String html = builder.buildHtml(page);
		System.out.println(html);*/
	}

	/*@Test
	public void testBuilder(){
		DefaultHtmlBuilder builder = new DefaultHtmlBuilder();
		DataHandler dataHandler = new DataHandler();
		DataFetchTest fetch = new DataFetchTest();
	//	dataHandler.setDataFetcher(fetch);
		builder.setDataHandler(dataHandler);
		CmsPage page = new CmsPage();
		page.setKeywords("test");
		page.setDescribe("test");
		page.setTemplatePath("D:\\code\\cms\\template\\page.html");
		String html = builder.buildHtml(page);
		System.out.println(html);
	}*/
	/*
	@Test
	public void testBuilderEmptyContent() {
		Configuration config = null;
		try {
			config = FreeMarkerUtil.getFolderCfg("D:\\code\\cms\\template");
			try {
				Template template = config.getTemplate("empty.html");
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				Writer out = new OutputStreamWriter(stream);
				template.process(MapUtils.EMPTY_MAP, out);
				out.flush();
				System.out.println(stream.toString());
			} catch (IOException e) {
				logger.error("CMSBuilderTest.异常：" + e.getMessage(), e);
			} catch (TemplateException e) {
				logger.error("CMSBuilderTest.异常：" + e.getMessage(), e);
			}
		} catch (IOException e) {
			logger.error("CMSBuilderTest.异常：" + e.getMessage(), e);
		}
	}*/
}
