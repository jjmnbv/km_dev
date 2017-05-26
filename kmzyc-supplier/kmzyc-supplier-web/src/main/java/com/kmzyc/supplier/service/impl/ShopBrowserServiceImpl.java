package com.kmzyc.supplier.service.impl;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kmzyc.zkconfig.ConfigurationUtil;
import com.kmzyc.framework.http.HttpUtils;
import com.kmzyc.framework.util.ObjectUtil;
import com.kmzyc.supplier.service.ShopBrowserService;
import com.kmzyc.commons.exception.ServiceException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kmzyc.supplier.model.ShopBrowseInfo;
import com.kmzyc.supplier.model.ShopBrowseInfoDetail;

/**
 * @author Administrator
 */
@Service("shopBrowserService")
public class ShopBrowserServiceImpl implements ShopBrowserService {

    private static Logger logger = LoggerFactory.getLogger(ShopBrowserServiceImpl.class);

    @Override
    public ShopBrowseInfo queryBrowseInfoList(ShopBrowseInfo conditionPara) throws ServiceException {
        ShopBrowseInfo returnData = new ShopBrowseInfo();
        Map<String, Object> paraMap = new HashMap();
        if (StringUtils.isNotBlank(conditionPara.getShopId())  && !"null".equals(conditionPara.getShopId())) {
            paraMap.put("shopid", conditionPara.getShopId());
        }
        if (StringUtils.isNotBlank(conditionPara.getTitleForQuery())  && !"null".equals(conditionPara.getTitleForQuery())) {
            paraMap.put("title", conditionPara.getTitleForQuery().trim());
        }
        try {
            if (StringUtils.isNotBlank(conditionPara.getUrlForQuery())  && !"null".equals(conditionPara.getUrlForQuery())) {
                paraMap.put("url", URLEncoder.encode(conditionPara.getUrlForQuery(), "utf-8"));
            }
            String startDate = null;
            String endDate = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            if (StringUtils.isNotBlank(conditionPara.getStartDate())) {
                startDate = conditionPara.getStartDate() + " 00:00";
            }
            if (StringUtils.isNotBlank(conditionPara.getEndDate())) {
                endDate = conditionPara.getEndDate() + " 23:59";
            } else {
                endDate = dateFormat.format(new Date());
            }
            paraMap.put("startDate", startDate);
            paraMap.put("endDate", endDate);
            paraMap.put("pn", conditionPara.getPn());
            paraMap.put("ps", conditionPara.getPs());
            String requestUrl = ConfigurationUtil.getString("url_shopBrowseList");
            logger.info("调用接口查询店铺浏览量传递参数如下: shopid={},title={},url={},startDate={},endDate={},ps={},pn={}.",
                    new Object[]{conditionPara.getShopId(), conditionPara.getTitleForQuery(),
                            requestUrl, startDate, endDate, paraMap.get("ps"), paraMap.get("pn")});

            JSONObject resultList = HttpUtils.doPost(requestUrl, paraMap, "utf-8");
            String code = resultList.getString("code"); // 返回码
            String message = resultList.getString("message"); // 文字说明

            //返回码 200正常 400参数请求有误 500服务端代码异常
            if (!"200".equals(code)) {
                returnData.setErrorMsg("code码=" + code + ",具体详情=" + message);
                logger.error("调用接口返回异常,详情如下:  ", returnData.getErrorMsg());
                return returnData;
            }

            JSONObject contentData = (JSONObject) resultList.get("content");
            logger.info("调用成功,contentData.size=", contentData.size());
            // 转换成我们需要的实体类 (这是数据列表部分)
            JSONArray entityData = (JSONArray) contentData.get("entity");
            returnData = (ShopBrowseInfo) ObjectUtil.string2Object(contentData.toString(), ShopBrowseInfo.class);
            returnData.setEntity(entityData);
            return returnData;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Map<String, String> queryGroupData(ShopBrowseInfo conditionPara) throws ServiceException {
        Map<String, Object> paraMap = new HashMap();
        if (StringUtils.isNotBlank(conditionPara.getShopId())  && !"null".equals(conditionPara.getShopId())) {
            paraMap.put("shopid", conditionPara.getShopId());
        }
        if (StringUtils.isNotBlank(conditionPara.getTitleForQuery())  && !"null".equals(conditionPara.getTitleForQuery())) {
            paraMap.put("title", conditionPara.getTitleForQuery().trim());
        }

        try {
            if (StringUtils.isNotBlank(conditionPara.getUrlForQuery())  && !"null".equals(conditionPara.getUrlForQuery())) {
                paraMap.put("url", URLEncoder.encode(conditionPara.getUrlForQuery(), "utf-8"));
            }
            String startDate = null;
            String endDate = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            if (StringUtils.isNotBlank(conditionPara.getStartDate())) {
                startDate = conditionPara.getStartDate() + " 00:00";
            }
            if (StringUtils.isNotBlank(conditionPara.getEndDate())) {
                endDate = conditionPara.getEndDate() + " 23:59";
            } else {
                endDate = dateFormat.format(new Date());
            }
            paraMap.put("startDate", startDate);
            paraMap.put("endDate", endDate);
            paraMap.put("pn", conditionPara.getPn());
            paraMap.put("ps", conditionPara.getPs());

            String requestUrl = ConfigurationUtil.getString("url_shopBrowseGroupData");
            logger.info("调用接口查询店铺浏览量折线图数据传递参数如下: shopid={},title={},url={},startDate={},endDate={},ps={},pn={}.",
                    new Object[]{conditionPara.getShopId(), conditionPara.getTitleForQuery(),
                            requestUrl, startDate, endDate, paraMap.get("ps"), paraMap.get("pn")});

            JSONObject resultList = HttpUtils.doPost(requestUrl, paraMap, "utf-8");
            String code = resultList.getString("code"); // 返回码
            String message = resultList.getString("message"); // 文字说明
            Map<String, String> returnMap = new HashMap<String, String>();

            //返回码 200正常 400参数请求有误 500服务端代码异常
            if (!"200".equals(code)) {
                logger.error("调用接口返回异常 详情如下:  code码=" + code + ",具体详情=" + message);
                returnMap.put("errorCode", code + ",message=" + message);
                return returnMap;
            }

            JSONObject contentData = (JSONObject) resultList.get("content");
            logger.info("调用成功,contentData.size=" + contentData.size());
            // 转换成我们需要的实体类 (这是数据列表部分)
            JSONObject group = (JSONObject) ((JSONObject) contentData.get("entity")).get("groups");
            if (group == null || group.size() < 1) {
                returnMap.put("dataList", null);
                returnMap.put("dataSize", "0");
                return returnMap;
            }

            List<ShopBrowseInfoDetail> groupDataList = new ArrayList();
            int i = 0;
            Iterator it = group.keys();
            // 遍历jsonObject数据，添加到Map对象
            while (it.hasNext()) {
                String key = String.valueOf(it.next());
                Integer count = (Integer) group.get(key);
                key = key.substring(0, key.indexOf('T'));
                ShopBrowseInfoDetail temp = new ShopBrowseInfoDetail();
                temp.setGroupDate(key);
                temp.setGroupTotalCount(count);
                groupDataList.add(temp);
                i++;
            }

            String jsonData = JSONArray.fromObject(groupDataList).toString();
            logger.info("折线图数据对应的list转换为json数据的大致长度=" + jsonData.length());
            returnMap.put("dataSize", String.valueOf(group.size()));
            returnMap.put("dataList", jsonData);
            return returnMap;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }


    /**
     * 查询时间类型
     * <b>
     *     (此方法暂未启用)
     * </b>
     * <note>
     *     0 所有：起止时间为空（不限时间范围）；
     *     1 当天：起止时间都自动选择当前日期；
     *     2 最近七天：开始时间为当前日期减去6天，截止时间为当天；
     *     3 当月：开始时间为当月1日，截止时间为当天；
     *     4 上月：开始时间为上月1日，截止时间为上月最后一天；
     *     日期格式: yyyy-MM-dd HH:mm
     * </note>
     * @param timeTypeQuery
     */
    public void getDate(String timeTypeQuery) {
        Date currentTime = new Date();
        String startDate = null;
        String endDate = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        if ("1".equals(timeTypeQuery)) {
            startDate = new SimpleDateFormat("yyyy-MM-dd").format(currentTime) + " 00:00";
            endDate = dateFormat.format(currentTime);
        } else if ("2".equals(timeTypeQuery)) {
            Calendar newDate = Calendar.getInstance();
            newDate.setTime(currentTime);
            newDate.add(Calendar.DATE, -6);
            startDate = dateFormat.format(newDate.getTime());
            endDate = dateFormat.format(currentTime);
        } else if ("3".equals(timeTypeQuery)) {
            Calendar newDate = Calendar.getInstance();
            newDate.set(Calendar.DAY_OF_MONTH, 1);
            startDate = dateFormat.format(newDate.getTime());
            endDate = dateFormat.format(currentTime);
        } else if ("4".equals(timeTypeQuery)) {
            Calendar newDate = Calendar.getInstance();
            newDate.add(Calendar.MONTH, -1); // 设置月份为当前月-1
            newDate.set(Calendar.DATE, 1); // 设置日期1号
            startDate = dateFormat.format(newDate.getTime());

            // 获得上月的最后一天
            newDate.set(Calendar.DAY_OF_MONTH, newDate.getActualMaximum(Calendar.DAY_OF_MONTH));
            endDate = dateFormat.format(newDate.getTime());
        }
    }
}
