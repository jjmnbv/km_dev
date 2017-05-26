<%@ page language="java"  import="com.kmzyc.zkconfig.ConfigurationUtil" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/html/common/version.jsp"%>
<%
    String titlePrefix = request.getParameter("titlePrefix");

    String jspPath = request.getRequestURI();
    boolean rootPage = jspPath.indexOf("WEB-INF/") == -1; //是否为根路径下的jsp，如error.jsp、index.jsp等
    String templatePath = null;
    if(!rootPage){
        templatePath  = jspPath.substring(jspPath.indexOf("WEB-INF/")+"WEB-INF/".length(), jspPath.lastIndexOf("."));
    }

    String staticUrl = ConfigurationUtil.getString("WS_CSS_JS_PATH");
    String debug = "1";//1:开发版本 0：线上版本
    String jsBaseUrl = "1"== debug ? "script/" : "js/";
    String cssBaseUrl = "1"== debug ? "style/default/" : "css/default/";

    String imageBaseUrl = "images/";
%>
<meta charset="utf-8" />
<title><%=titlePrefix%></title>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black-translucent" />
<meta name="Keywords" content="康美中药城,康美中药城商城,康美,康美官方旗舰店,健康第一,康美药业,康美中药城,商城,康美人生,国参传奇,菊皇茶,西洋参,花旗参,红参,冬虫夏草,虫草瘦身食品,人参,三七,田七,丹参,新开河,新开河红参,红参,高丽参,花旗参,白参,人参切片,参茸贵细"/>
<meta name="Description" content="康美中药城是康美药业股份有限公司旗下运营的网上医药商城，通过康美药业的医药行业优势和强大的医药配送体系，致力于发展中国最大的医药零售电子商务市场。便捷、诚信的服务，为您提供愉悦的网上购物体验!"/>
<%
    if(!rootPage){
%>
<link type="text/css" rel="stylesheet" href="<%=staticUrl%><%=cssBaseUrl%><%=templatePath%>.css?version=<%=version%>"/>
<%
    }
%>
<link type="text/css" rel="stylesheet" href="/fonts/fonts.css?version="<%=version%>"/>
<script type="text/javascript">
    var KM = KM || {};
    KM.VERSION = '<%=version%>';//项目版本号
</script>

<script type="text/javascript" src="<%=staticUrl%><%=jsBaseUrl%>seajs/2.0.2/sea.js?version=<%=version%>"></script>
<%
    if(debug.equals("1")){
%>
<script type="text/javascript" src="<%=staticUrl%><%=jsBaseUrl%>config.js?version=<%=version%>"></script>
<%
    }
%>
<script type="text/javascript">
    seajs.use(['<%=staticUrl%><%=jsBaseUrl%>view/common.js']);
    <%
        if(!rootPage){
    %>
    seajs.use(['<%=staticUrl%><%=jsBaseUrl%>view/<%=templatePath%>.js']);
    <%
        }
    %>
</script>
