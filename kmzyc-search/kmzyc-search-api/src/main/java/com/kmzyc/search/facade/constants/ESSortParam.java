package com.kmzyc.search.facade.constants;

import com.alibaba.fastjson.JSONObject;
import com.kmzyc.search.param.DocFieldName;

public class ESSortParam {

    // 默认
    public static final int A = 0;

    // 价格
    public static final int B = 1; // ASC
    public static final int C = 2; // DESC
    // 时间
    public static final int D = 3; // ASC
    public static final int E = 4; // DESC
    // 销量
    public static final int F = 5; // ASC
    public static final int G = 6; // DESC
    // 促销
    public static final int H = 7; // ASC
    public static final int I = 8; // DESC
    // pv值
    public static final int J = 9;// asc
    public static final int K = 10;// desc

    public static JSONObject getSortText(int type) {
        JSONObject json = new JSONObject();
        switch (type) {
            case A:
                return json;
            case B:
                json.put(DocFieldName.PRICE, ORDER.asc);
                break;
            case C:
                json.put(DocFieldName.PRICE, ORDER.desc);
                break;
            case D:
                json.put(DocFieldName.UP_TIME, ORDER.asc);
                break;
            case E:
                json.put(DocFieldName.UP_TIME, ORDER.desc);
                break;
            case F:
                json.put(DocFieldName.SALES, ORDER.asc);
                break;
            case G:
                json.put(DocFieldName.SALES, ORDER.desc);
                break;
            case H:
                json.put(DocFieldName.PROMOTION, ORDER.asc);
                break;
            case I:
                json.put(DocFieldName.PROMOTION, ORDER.desc);
                break;
            case J:
                json.put(DocFieldName.SKUPV, ORDER.asc);
                break;
            case K:
                json.put(DocFieldName.SKUPV, ORDER.desc);
                break;
        }
        return json;
    }

    /**
     * 
     * @param type 排序参数
     * @param areaId 地区ID
     * @return
     */
    public static JSONObject getSortText(int type, int areaId) {
        JSONObject json = new JSONObject();
        switch (type) {
            case B:
                json.put(areaId + "_" + DocFieldName.PRICE, ORDER.asc);
                return json;
            case C:
                json.put(areaId + "_" + DocFieldName.PRICE, ORDER.desc);
                return json;
            default:
                return getSortText(type);
        }
    }

}
