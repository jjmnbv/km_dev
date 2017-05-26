package com.pltfm.cms.parse;

/**
 * 窗口数据类型
 *
 * @author river
 */
public enum DataType {
    /*information, //咨询
	productRank, //榜单产品
	product,	// 产品类型
	activity,	// 活动类型
	advert,		// 广告类型
	help,		// 帮助
	category,   // 类目
	window,		// 窗口
	brand,		// 品牌
	windowTitle,//自定义窗口数据类型 
	staticPath,      //静态页面存放路径
	jsCssPath,      //页面js、css样式路径
	cssAndJsPath,   //css、js样式存储路径
	cmsPath,        //cms系统图片路径
	picPath,        //pic样式存储路径
	detailPath,     //详细页面存储路径
	advPath,     //广告存储路径
	searchPath,  //搜索路径
	keywords,	    // 网页关键字
	description,    // 网页描述
	title, //标题
	promotionData,//活动数
	pageType , //页面类型
	*/

    //portal;
    supplierNews {//供应商资讯

        @Override
        String val() {
            return name();
        }
    },
    basicInfo {//供应商信息

        @Override
        String val() {
            return name();
        }
    },
    suppliersCertificate {//供应商资质

        @Override
        String val() {
            return name();
        }
    },
    productRelationDetail {//套餐

        @Override
        String val() {
            return name();
        }
    },
    productRelation {//套餐

        @Override
        String val() {
            return name();
        }
    },
    shopMain {//店铺

        @Override
        String val() {
            return name();
        }
    },
    productShopMain {//店铺

        @Override
        String val() {
            return name();
        }
    },

    productmain {//主一产品

        @Override
        String val() {
            return name();
        }
    },


    shopType {//店铺模板类型

        @Override
        String val() {
            return name();
        }
    },

    themeType {//店铺页面是可视还是发布

        @Override
        String val() {
            return name();
        }
    },
    cmsShopData {//cms关联供应商

        @Override
        String val() {
            return name();
        }
    },
    portalPath {
        @Override
        String val() {
            return name();
        }
    },
    information { //咨询

        @Override
        String val() {
            return name();
        }
    },
    productRank {//榜单产品

        @Override
        String val() {
            return name();
        }
    },
    product {// 产品类型

        @Override
        String val() {
            return name();
        }
    },
    activity {// 活动类型

        @Override
        String val() {
            return name();
        }
    },
    advert {// 广告类型

        @Override
        String val() {
            return name();
        }
    },
    help {// 帮助

        @Override
        String val() {
            return name();
        }
    },
    category {// 类目

        @Override
        String val() {
            return name();
        }
    },
    shopCategory {// 店铺类目

        @Override
        String val() {
            return name();
        }
    },
    window {// 窗口

        @Override
        String val() {
            return name();
        }
    },
    brand {// 品牌

        @Override
        String val() {
            return name();
        }
    },
    shopCategorys {// 店铺类目服务

        @Override
        String val() {
            return name();
        }
    },
    lotteryLuckDraw {// 抽奖活动

        @Override
        String val() {
            return name();
        }
    },
    lotteryAwards {// 奖项

        @Override
        String val() {
            return name();
        }
    },
    windowTitle {//自定义窗口数据类型

        @Override
        String val() {
            return name();
        }
    },
    staticPath { //静态页面存放路径

        @Override
        String val() {
            return name();
        }
    },
    jsCssPath { //页面js、css样式路径

        @Override
        String val() {
            return name();
        }
    },
    cssAndJsPath { //css、js样式存储路径

        @Override
        String val() {
            return name();
        }
    },
    cmsPath { //cms系统图片路径

        @Override
        String val() {
            return name();
        }
    },
    gysPicPath {//供应商图片路径

        @Override
        String val() {
            return name();
        }
    },
    gysPortalPath {//供应商路径

        @Override
        String val() {
            return name();
        }
    },
    picPath { //pic样式存储路径

        @Override
        String val() {
            return name();
        }
    },
    detailPath { //详细页面存储路径

        @Override
        String val() {
            return name();
        }
    },
    advPath { //广告存储路径

        @Override
        String val() {
            return name();
        }
    },
    searchPath {//搜索路径

        @Override
        String val() {
            return name();
        }
    },

    keywords { // 网页关键字

        @Override
        String val() {
            return name();
        }
    },
    description {// 网页描述

        @Override
        String val() {
            return name();
        }
    },
    cmsShopId {// 店铺ID

        @Override
        String val() {
            return name();
        }
    },
    title {//标题

        @Override
        String val() {
            return name();
        }
    },

    promotionData {//活动数

        @Override
        String val() {
            return name();
        }
    },
    pageType {//页面类型

        @Override
        String val() {
            return name();
        }
    },
    supplyType {//供应商页面类型

        @Override
        String val() {
            return name();
        }
    };

    //搜索引擎的单独一套变量
	/*
     * css_js_path{ String val(){ return "css_js_path"; }
     * 
     * },
     * 
     * product_image_path{ String val(){ return "product_image_path"; }
     * 
     * }, b2b_css_js_path{ String val(){ return "b2b_css_js_path"; }
     * 
     * }, zyc_css_js_path{ String val(){ return "zyc_css_js_path"; }
     * 
     * }, cms_product_page_path{ String val(){ return "cms_product_page_path"; }
     * 
     * }, b2b_cms_root_path{ String val(){ return "b2b_cms_root_path"; }
     * 
     * }, zyc_cms_root_path{ String val(){ return "zyc_cms_root_path"; }
     * 
     * }, cms_advert_path{ String val(){ return "cms_advert_path"; }
     * 
     * }, cms_path{ String val(){ return "cms_path"; }
     * 
     * },
     * 
     * portal_path{ String val(){ return "portal_path"; }
     * 
     * },
     * 
     * facade_path{ String val(){ return "facade_path"; }
     * 
     * };
     */
    abstract String val();


}