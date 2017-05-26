/** 
 * project : B2C 康美健康商城
 * module : elasticsearch-analysis-ik 
 * package : org.elasticsearch.indices.analysis 
 * date: 2016年8月5日上午9:35:10 
 * Copyright (c) 2016, KM@km.com All Rights Reserved. 
 */ 
package org.elasticsearch.indices.analysis;

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
import org.wltea.analyzer.dic.Dictionary;

import static org.elasticsearch.rest.RestRequest.Method.GET;

/** 
 * 接收ik分词刷新请求action
 * @author   KM 
 * @date   2016年8月5日 上午9:35:10 
 * @version   
 * @see       
 */
public class IKAnalysisSyncAction extends BaseRestHandler{
	
	private static ESLogger logger=Loggers.getLogger(IKAnalysisSyncAction.class);	
	
	/**
	 * 允许重新加载词库的许可标识
	 */
	private static boolean permit = true;
	
	
	@Inject
	public IKAnalysisSyncAction(Settings settings, RestController controller, Client client) {
		super(settings, controller, client);
        controller.registerHandler(GET, "/IKAnalysis/DicReload/{dictName}", this);
	}

	@Override
	public void handleRequest(RestRequest request, RestChannel channel, Client client) throws Exception {
		
		String dictName = request.param("dictName");
		if(null == dictName || "".equals(dictName)){
			logger.info("未指定刷新词库类型！");
			channel.sendResponse(new BytesRestResponse(RestStatus.ACCEPTED,"{\"errorCode\":\"001\",\"errorDesc\":\"未指定刷新词库类型！\"}"));
			return;
		}
		if(!permit){
			logger.info("词库正在加载中，请稍后重试！");
			channel.sendResponse(new BytesRestResponse(RestStatus.ACCEPTED,"{\"errorCode\":\"002\",\"errorDesc\":\"词库正在加载中，请稍后重试！\"}"));
			return;
		}
		channel.sendResponse(new BytesRestResponse(RestStatus.ACCEPTED,"{\"result\":\"已成功接收到词库重新加载请求！\"}"));
		try {
			permit=false;
			if("keyword".equals(dictName)){
				Dictionary.getSingleton().reloadKeyWordDict();			
			}else if("stopword".equals(dictName)){
				Dictionary.getSingleton().reloadStopWordDict();
			}
		} finally{
			permit=true;
		}
		
		
	
		
		
		
		
	}

}
  