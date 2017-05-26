package com.kmzyc.b2b.shopcart.vo;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;
import com.kmzyc.util.StringUtil;

/**
 * App购物车参数 vo
 * 
 * @author mkw
 * 
 */
public class AppShopCartOperation implements Serializable {

    private static final long serialVersionUID = 1L;

    private String uid; // 用户ID
    private String utype; // 空：普通会员， "01":会员 ，"02":时代会员
    private boolean islogin = false;// 是否登录
    private String pid;// 产品ID
    private String ptype;// 产品类型 c:套餐,n:单品
    private int pnum;// 产品数量
    private boolean checked = false;// 是否选中
    private String promotionid;// 促销活动ID
    private String itemid;//
    private String presents;// 满赠条件
    private String sid; // 添加商品 ，商品SKUID或套装ID
    private String pmap; // 选择加价购商品 商品ID及数量
    private String dataid; // 删除加价购商品 加价购产品DATAID
    private String tempuid; // 临时用户ID
    private String channel;// 渠道号

    private StringBuilder errorInfo = new StringBuilder(); // 错误信息



    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUtype() {
        return utype;
    }

    public void setUtype(String utype) {
        this.utype = utype;
    }

    public boolean getIslogin() {
        return islogin;
    }

    public void setIslogin(boolean islogin) {
        this.islogin = islogin;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPtype() {
        return ptype;
    }

    public void setPtype(String ptype) {
        this.ptype = ptype;
    }

    public int getPnum() {
        return pnum;
    }

    public void setPnum(int pnum) {
        this.pnum = pnum;
    }

    public boolean getChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getPromotionid() {
        return promotionid;
    }

    public void setPromotionid(String promotionid) {
        this.promotionid = promotionid;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getPresents() {
        return presents;
    }

    public void setPresents(String presents) {
        this.presents = presents;
    }

    public String getErrorInfo() {
        return errorInfo.toString();
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }



    public String getTempuid() {
        return tempuid;
    }

    public void setTempuid(String tempuid) {
        this.tempuid = tempuid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }



    public String getPmap() {
        return pmap;
    }

    public void setPmap(String pmap) {
        this.pmap = pmap;
    }



    public String getDataid() {
        return dataid;
    }

    public void setDataid(String dataid) {
        this.dataid = dataid;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    /**
     * 设置错误信息
     * 
     * @param errorInfo
     */
    public void setErrorInfo(String errorInfo) {
        if (StringUtil.isEmpty(errorInfo)) {
            errorInfo = "";
        }
        if (this.errorInfo.toString().endsWith("|")) {
            this.errorInfo.append(errorInfo);
        } else {
            if (!StringUtil.isEmpty(this.errorInfo.toString())) {
                this.errorInfo.append("|");
            }
            this.errorInfo.append(errorInfo);
        }
    }

    public String toJsonString() {

        return JSONObject.toJSONString(this);
    }
}
