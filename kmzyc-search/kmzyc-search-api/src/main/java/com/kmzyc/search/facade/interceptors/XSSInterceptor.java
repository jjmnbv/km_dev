package com.kmzyc.search.facade.interceptors;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.owasp.esapi.ESAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.common.collect.Maps;

/**
 * XSS攻击拦截器: 用户原始输入不处理，对输出到页面的内容进行XSS攻击过滤。
 * 
 * @author river
 * 
 */
public class XSSInterceptor extends HandlerInterceptorAdapter
{

	private static final Logger	LOG	= LoggerFactory
											.getLogger(XSSInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception
	{
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception
	{
		if (null != modelAndView)
		{
			try
			{
				ModelMap modelMap = modelAndView.getModelMap();
				Iterator<Entry<String, Object>> it = modelMap.entrySet()
						.iterator();
				Map<String, String> escapeMap = Maps.newHashMap();
				while (it.hasNext())
				{
					Entry<String, Object> entry = it.next();
					String key = entry.getKey();
					Object value = entry.getValue();
					if (null != value && value instanceof String)
					{
						String temp = value.toString();
						temp = ESAPI.encoder().encodeForHTML(temp);
						// temp = ESAPI.encoder().encodeForJavaScript(temp);
						escapeMap.put(key, temp);
					}
					if (null != value && "recommends".equals(key))
					{
						String query = MapUtils.getString(modelMap, "keyword");
						List<Map<String, Object>> recommends = (List<Map<String, Object>>) value;
						for (Map<String, Object> info : recommends)
						{
							int start = MapUtils.getIntValue(info, "start");
							int end = MapUtils.getIntValue(info, "end");
							String warpQuery = warpQuery(query, start, end);
							info.put("warpQuery", warpQuery);
						}
					}
				}
				modelMap.putAll(escapeMap);
			}
			catch (Exception e)
			{
				LOG.error("XSS攻击过滤出现异常：", e);
			}
		}
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception
	{
		super.afterCompletion(request, response, handler, ex);
	}

	/**
	 * <b>手机</b><del>中</del><b>的</b><del>洗衣机fdfdfdsfsdf</del>
	 * 
	 * @param query
	 * @param start
	 * @param end
	 * @return
	 */
	private String warpQuery(String query, int start, int end)
	{
		StringBuilder target = new StringBuilder();
		if (start > 0)
		{
			target.append("<del>");
			target.append(ESAPI.encoder().encodeForHTML(
					query.subSequence(0, start).toString()));
			target.append("</del>");
		}
		target.append("<b>"
				+ ESAPI.encoder().encodeForHTML(
						query.subSequence(start, end).toString()) + "</b>");
		if (end < query.length())
		{
			target.append("<del>");
			target.append(ESAPI.encoder().encodeForHTML(
					query.subSequence(end, query.length()).toString()));
			target.append("</del>");
		}
		return target.toString();
	}

}
