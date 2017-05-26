package com.kmzyc.search.facade.request.transverter;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kmzyc.search.config.Channel;
import com.kmzyc.search.facade.constants.Constants;
import com.kmzyc.search.facade.constants.ESSortParam;
import com.kmzyc.search.facade.constants.HTTPParam;
import com.kmzyc.search.facade.constants.SupplierType;
import com.kmzyc.search.facade.request.IRequestTransverter;
import com.kmzyc.search.facade.util.JsonUtil;
import com.kmzyc.search.param.DocFieldName;

/**
 * b2b 关键字模糊搜索参数组装类
 * 
 * @author KM
 *
 */
public class RequestParamsTransverter implements IRequestTransverter {

    protected static final Logger LOG = LoggerFactory.getLogger(RequestParamsTransverter.class);

    public static final String QUERY_STRING_TEMP =
            "{\"bool\":{\"must\":{\"query_string\":{\"fields\":[\"prodTitle^1\",\"subtitle^0.5\",\"prodName^0.2\",\"brandName^0.2\",\"keyword^0.1\",\"ocName_ik^0.1\"],"
                    + "\"query\":\"<<keyword>>\",\"use_dis_max\":false,\"default_operator\": \"AND\"}},\"should\":[{\"range\":{\"promotion\":{\"boost\":5,\"gt\":0}}},{\"range\":{\"skupv\":{\"boost\":5,\"gt\":0}}}]}}";

    protected Channel channel;

    public RequestParamsTransverter(Channel channel) {
        this.channel = channel;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String convert(Object param) {
        if (param == null) {
            return null;
        }
        Map<String, String[]> searchParams = (Map<String, String[]>) param;
        JSONObject json = new JSONObject();
        // 添加查询参数
        String[] qs = searchParams.remove(HTTPParam.q.name());
        if (ArrayUtils.isNotEmpty(qs) && StringUtils.isNotBlank(qs[0])) {
            // 查询语句处理
            JSONObject queryJO = JSONObject.parseObject(QUERY_STRING_TEMP);
            queryJO.getJSONObject("bool").getJSONObject("must").getJSONObject("query_string")
                    .put("query", qs[0].replaceAll("\"", "\\\"").replaceAll("/", "\\\\/"));
            json.put("query", queryJO);
        }
        // 添加排序参数
        String[] sort = searchParams.remove(HTTPParam.sort.name());
        JSONArray sortJSONArray = new JSONArray();
        if (ArrayUtils.isNotEmpty(sort)) {
            // 排序处理
            JSONObject sortJson = ESSortParam.getSortText(Integer.parseInt(sort[0]));
            if (sortJson != null && !sortJson.isEmpty()) {
                sortJSONArray.add(sortJson);
            } else {
                sortJSONArray.add(JSONObject.parse("{\"_score\":\"desc\"}"));
            }
        } else {
            sortJSONArray.add(JSONObject.parse("{\"_score\":\"desc\"}"));
        }
        sortJSONArray.add(JSONObject.parse("{\"id\":\"asc\"}"));
        json.put("sort", sortJSONArray);

        // 设置过滤参数
        JSONObject filters = getFilter(searchParams.remove(HTTPParam.f.name()));
        if (filters != null) {
            json.put("post_filter", filters);
        }
        // facet
        String[] facet = searchParams.remove("facet");
        if (ArrayUtils.isNotEmpty(facet) && Boolean.valueOf(facet[0])) {
            json.put("aggs", getFacet());
        }

        // 设置高亮
        String[] highlight = searchParams.remove("highlight");
        if (ArrayUtils.isNotEmpty(highlight) && "true".equals(highlight[0])) {
            json.put("highlight", getHighlight());
        }

        String[] pageNum = searchParams.remove(HTTPParam.pn.name());
        String[] rows = searchParams.remove(HTTPParam.ps.name());

        json.put("size", rows == null ? 20 : Integer.valueOf(rows[0]));
        int start =
                pageNum == null ? 0 : (Integer.valueOf(pageNum[0]) - 1) * json.getInteger("size");
        json.put("from", start);

        return json.toJSONString();
    }

    protected JSONObject getFilter(String[] filters) {

        JSONArray jsonA = new JSONArray();
        // TODO PC端过滤掉预售商品的搜索
        // TODO add by zhoulinhong on 20160828
        jsonA.add(
                JSONObject.parse("{\"bool\": {\"must_not\": {\"term\": {\"promotion\": \"2\"}}}}"));

        if (ArrayUtils.isEmpty(filters) || StringUtils.isBlank(filters[0])) {

            return JsonUtil.jsonPut(new JSONObject(), "and", jsonA);
        }

        // 过滤条件
        String filtParamText = filters[0];
        try {
            filtParamText = URLDecoder.decode(filtParamText, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOG.error("decode filtParamText fail", e);
        }
        Iterator<String> it = Splitter.on("&").trimResults().split(filtParamText).iterator();
        Map<String, List<String>> filterTemp = Maps.newHashMap();

        while (it.hasNext()) {
            String filter = it.next();
            JSONObject filtJson = new JSONObject();
            // 价格与库存。只能有一个过滤值
            if (filter.startsWith("price=") || filter.startsWith("stock=")) {
                String[] prFiltes = filter.split("=");
                if (prFiltes[1].endsWith("以上")) {
                    JSONObject gteJson = JsonUtil.jsonPut(new JSONObject(), "gte",
                            Integer.valueOf(prFiltes[1].replace("以上", "")));
                    filtJson.put("range", JsonUtil.jsonPut(new JSONObject(), prFiltes[0], gteJson));
                } else if (prFiltes[1].contains("-")) {
                    String[] prVals = prFiltes[1].split("-");
                    JSONObject prValJson = new JSONObject();
                    prValJson.put("gte", Integer.valueOf(prVals[0]));
                    if (!"*".equals(prVals[1])) {
                        prValJson.put("lte", Integer.valueOf(prVals[1]));
                    }
                    filtJson.put("range",
                            JsonUtil.jsonPut(new JSONObject(), prFiltes[0], prValJson));
                }
                // 20150616 add begin 筛选自营和代销 自营的shopcode为221,代销的为subType=3
            } else if (filter.startsWith("kmSelf=")) {
                JSONArray kmSelfJsonA = new JSONArray();
                kmSelfJsonA.add(JsonUtil.jsonPut(new JSONObject(), "term",
                        JsonUtil.jsonPut(new JSONObject(), "supType", SupplierType.T3.getCode())));
                kmSelfJsonA.add(JsonUtil.jsonPut(new JSONObject(), "term",
                        JsonUtil.jsonPut(new JSONObject(), "scode", Constants.KM_SHOP_CODE)));
                filtJson.put("or", kmSelfJsonA);
                // 处理自营的筛选逻辑 20150616 add end
            } else {
                // 可以多值过滤的
                String[] pairs = filter.split("=");
                String paramName = pairs[0];
                if (!filterTemp.containsKey(paramName)) {
                    List<String> values = Lists.newArrayList();
                    filterTemp.put(paramName, values);
                }
                // 属性过滤
                if (filter.startsWith("attr=")) {
                    List<String> filterParam = Splitter.on("=").trimResults().splitToList(filter);
                    if (filterParam.size() > 1) {
                        String pair = filterParam.get(1);
                        pair = pair.replace("_", "=");
                        if (!filterTemp.get(paramName).contains(pair)) {
                            filterTemp.get(paramName).add(pair);
                        }
                        JSONArray attJsonA = new JSONArray();
                        for (String param : filterTemp.get(paramName)) {
                            attJsonA.add(JsonUtil.jsonPut(new JSONObject(), "term", JsonUtil
                                    .jsonPut(new JSONObject(), DocFieldName.ATTRVAL_CP, param)));
                        }
                        filtJson.put("or", attJsonA);
                    }
                } else {
                    // 其它过滤条件，例如品牌
                    if (pairs.length == 2) {
                        String paramVal = pairs[1];
                        if (!filterTemp.get(paramName).contains(paramVal)) {
                            filterTemp.get(paramName).add(paramVal);
                        }
                        JSONArray paramJson = new JSONArray();
                        for (String param : filterTemp.get(paramName)) {
                            paramJson.add(JsonUtil.jsonPut(new JSONObject(), "term",
                                    JsonUtil.jsonPut(new JSONObject(), paramName, param)));
                        }
                        filtJson.put("or", paramJson);
                    }
                }
            }
            jsonA.add(filtJson);
        }
        return JsonUtil.jsonPut(new JSONObject(), "and", jsonA);
    }

    protected JSONObject getFacet() {
        JSONObject facetJson = new JSONObject();
        facetJson.put(DocFieldName.ATTRNAME, JSONObject.parseObject(
                "{\"terms\":{\"field\":\"" + DocFieldName.ATTRNAME + "\",\"size\":100}}"));
        facetJson.put(DocFieldName.ATTRVAL_CP, JSONObject.parseObject(
                "{\"terms\":{\"field\":\"" + DocFieldName.ATTRVAL_CP + "\",\"size\":100}}"));
        facetJson.put(DocFieldName.SECOND_O_CODE, JSONObject.parseObject(
                "{\"terms\":{\"field\":\"" + DocFieldName.SECOND_O_CODE + "\",\"size\":100}}"));
        facetJson.put(DocFieldName.THIRD_O_CODE, JSONObject.parseObject(
                "{\"terms\":{\"field\":\"" + DocFieldName.THIRD_O_CODE + "\",\"size\":100}}"));
        facetJson.put(DocFieldName.BRAND_NAME, JSONObject.parseObject(
                "{\"terms\":{\"field\":\"" + DocFieldName.BRAND_NAME + "\",\"size\":100}}"));
        return facetJson;
    }

    protected JSONObject getHighlight() {
        JSONObject highJson = new JSONObject();
        highJson.put("pre_tags", new String[] {"<font color=\'red\'>"});
        highJson.put("post_tags", new String[] {"</font>"});
        highJson.put("fields", JsonUtil.jsonPut(new JSONObject(), "prodTitle", new JSONObject()));
        return highJson;
    }

}
