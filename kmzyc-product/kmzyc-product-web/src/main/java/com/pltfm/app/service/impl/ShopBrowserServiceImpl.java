package com.pltfm.app.service.impl;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kmzyc.zkconfig.ConfigurationUtil;
import com.kmzyc.commons.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import com.pltfm.app.fobject.ShopBrowseInfo;
import com.pltfm.app.fobject.ShopBrowseInfoDetail;
import com.pltfm.app.service.ShopBrowserService;
import com.pltfm.app.util.HttpUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("shopBrowserService")
public class ShopBrowserServiceImpl implements ShopBrowserService{
	
	private static Logger logger = Logger.getLogger(ShopBrowserServiceImpl.class);

	@Override
	public ShopBrowseInfo queryBrowseInfoList(ShopBrowseInfo conditionPara) throws ServiceException {
		ShopBrowseInfo returnData=new ShopBrowseInfo();
		Map paraMap=new HashMap();
		if(StringUtils.isNotBlank(conditionPara.getShopId())){
			paraMap.put("shopid", conditionPara.getShopId());
		}
		if(StringUtils.isNotBlank(conditionPara.getTitleForQuery())){
			paraMap.put("title", conditionPara.getTitleForQuery().trim());
		}
		String startDate=null;
		String endDate=null;
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(conditionPara.getStartDate()!=null && !conditionPara.getStartDate().isEmpty()){
			startDate=conditionPara.getStartDate()+" 00:00";
		}
		if(conditionPara.getEndDate()!=null && !conditionPara.getEndDate().isEmpty()){
			endDate=conditionPara.getEndDate()+" 23:59";
		}else{
			endDate=dateFormat.format(new Date());
		}
		paraMap.put("startDate", startDate);
		paraMap.put("endDate", endDate);
		paraMap.put("pn", conditionPara.getPn());
		paraMap.put("ps", conditionPara.getPs());
		String requestUrl=ConfigurationUtil.getString("url_shopBrowseList");
		logger.info("调用接口查询店铺浏览量传递参数如下: shopId="+conditionPara.getShopId()
                +",title="+conditionPara.getTitleForQuery()
                +",url="+conditionPara.getTitleForQuery()
                +",startDate="+startDate+",endDate="+endDate
                +",ps="+paraMap.get("ps")+",pn="+paraMap.get("pn"));

        try {
            if(StringUtils.isNotBlank(conditionPara.getUrlForQuery())){
                paraMap.put("url", URLEncoder.encode(conditionPara.getUrlForQuery(), "utf-8"));
            }
            JSONObject resultList=HttpUtils.doPost(requestUrl,paraMap,"utf-8");
            String code=resultList.getString("code");  //返回码
            String message=resultList.getString("message"); //文字说明

		    //返回码 200正常  400参数请求有误  500服务端代码异常
            if(!"200".equals(code)){
                returnData.setErrorMsg("code码="+code+",具体详情="+message);
                logger.info("调用接口返回异常 ,详情如下:  "+returnData.getErrorMsg());
                return returnData;
            }

            JSONObject contentData=(JSONObject) resultList.get("content");
            logger.info("content=" + contentData);

            //转换成我们需要的实体类
            JSONArray entityData=(JSONArray)contentData.get("entity");
            //List<ShopBrowseInfoDetail> infoList=JSONArray.toList(entityData,ShopBrowseInfoDetail.class);
            returnData=(ShopBrowseInfo)JSONObject.toBean(contentData, ShopBrowseInfo.class);
            returnData.setEntity(entityData);
            return returnData;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

	@Override
	public Map<String, String> queryGroupData(ShopBrowseInfo conditionPara) throws ServiceException {
		Map paraMap=new HashMap();
        if(StringUtils.isNotBlank(conditionPara.getShopId())){
            paraMap.put("shopid", conditionPara.getShopId());
        }
        if(StringUtils.isNotBlank(conditionPara.getTitleForQuery())){
            paraMap.put("title", conditionPara.getTitleForQuery().trim());
        }

		String startDate=null;
		String endDate=null;
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(conditionPara.getStartDate()!=null && !conditionPara.getStartDate().isEmpty()){
			startDate=conditionPara.getStartDate()+" 00:00";
		}
		if(conditionPara.getEndDate()!=null && !conditionPara.getEndDate().isEmpty()){
			endDate=conditionPara.getEndDate()+" 23:59";
		}else{
			endDate=dateFormat.format(new Date());
		}
		paraMap.put("startDate", startDate);
		paraMap.put("endDate", endDate);
		paraMap.put("pn", conditionPara.getPn());
		paraMap.put("ps", conditionPara.getPs());
		String requestUrl= ConfigurationUtil.getString("url_shopBrowseGroupData");

		logger.info("调用接口查询店铺浏览量折线图数据传递参数如下: shopId="+conditionPara.getShopId()
                +",title="+conditionPara.getTitleForQuery()+",url="+conditionPara.getTitleForQuery()
                +",startDate="+startDate+",endDate="+endDate+",ps="+paraMap.get("ps")+",pn="+paraMap.get("pn"));

        try {
            if(StringUtils.isNotBlank(conditionPara.getUrlForQuery())){
                paraMap.put("url", URLEncoder.encode(conditionPara.getUrlForQuery(), "utf-8"));
            }
            JSONObject resultList=HttpUtils.doPost(requestUrl,paraMap,"utf-8");
            String code=resultList.getString("code");  //返回码
            String message=resultList.getString("message"); //文字说明
            Map<String,String> returnMap=new HashMap<String,String>();

            //返回码 200正常  400参数请求有误  500服务端代码异常
            if(!"200".equals(code)){
                logger.info("调用接口返回异常 详情如下:  code码="+code+",具体详情="+message);
                returnMap.put("errorCode", code+",message="+message);
                return returnMap;
            }

            JSONObject contentData=(JSONObject) resultList.get("content");
            logger.info("content="+contentData);
            //转换成我们需要的实体类 (这是数据列表部分)
            JSONObject group=(JSONObject)((JSONObject)contentData.get("entity")).get("groups");
            if(group==null || group.size()<1){
                returnMap.put("dataList",null);
                returnMap.put("dataSize", "0");
                return returnMap;
            }

            List<ShopBrowseInfoDetail> groupDataList=new ArrayList<ShopBrowseInfoDetail>();
            int i=0;
            Iterator it = group.keys();
            // 遍历jsonObject数据，添加到Map对象
            while (it.hasNext()) {
                String key = String.valueOf(it.next());
                Integer count=(Integer)group.get(key);
                key=key.substring(0,key.indexOf('T'));
                ShopBrowseInfoDetail temp=new ShopBrowseInfoDetail();
                temp.setGroupDate(key);
                temp.setGroupTotalCount(count);
                groupDataList.add(temp);
                i++;
            }

            String jsonData=JSONArray.fromObject(groupDataList).toString();
            System.out.println("分组折现数据="+group+",group.size="+group.size());
            logger.info("折线图数据="+group+",group.size="+group.size());
            logger.info("折线图数据对应的list转换为json数据="+jsonData);
            returnMap.put("dataSize", String.valueOf(group.size()));
            returnMap.put("dataList", jsonData);
            return returnMap;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

}
