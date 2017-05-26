/** 
 * project : B2C 康美健康商城
 * module : elasticsearch-analysis-dynamic-synonym 
 * package : com.bellszhu.elasticsearch.plugin.action 
 * date: 2016年8月30日下午5:11:14 
 * Copyright (c) 2016, KM@km.com All Rights Reserved. 
 */ 
package com.bellszhu.elasticsearch.plugin.action;

import static org.elasticsearch.rest.RestRequest.Method.GET;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.Loggers;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.rest.BaseRestHandler;
import org.elasticsearch.rest.BytesRestResponse;
import org.elasticsearch.rest.RestChannel;
import org.elasticsearch.rest.RestController;
import org.elasticsearch.rest.RestRequest;
import org.elasticsearch.rest.RestStatus;

import com.bellszhu.elasticsearch.plugin.synonym.analysis.DynamicSynonymTokenFilterFactory;

/** 
 * 接收同义词词库刷新请求action
 *
 * @author   KM 
 * @date   2016年8月30日 下午5:11:14 
 * @version   
 * @see       
 */
public class SynonymReloadAction extends BaseRestHandler{
	
	private static ESLogger logger = Loggers.getLogger(SynonymReloadAction.class);	
	/**
	 * 允许重新加载词库的许可标识
	 */
	private static boolean permit = true;
	
	@Inject
	public SynonymReloadAction(Settings settings, RestController controller, Client client) {
		super(settings, controller, client);		
        controller.registerHandler(GET, "/synonym/reload", this);
	}

	@Override
	public void handleRequest(RestRequest request, RestChannel channel, Client client) throws Exception {
		if(!permit){
			logger.info("同义词库正在加载中，请稍后重试！");
			channel.sendResponse(new BytesRestResponse(RestStatus.ACCEPTED,"{\"errorCode\":\"003\",\"errorDesc\":\"同义词库正在加载中，请稍后重试！\"}"));
			return;
		}
		channel.sendResponse(new BytesRestResponse(RestStatus.ACCEPTED,"{\"result\":\"已成功接收到词库重新加载请求！\"}"));
		try {
			permit=false;
			//重新加载同义词库
			DynamicSynonymTokenFilterFactory.reloadSynonym();
		} finally{
			permit=true;
		}
		
		
	}

}
  