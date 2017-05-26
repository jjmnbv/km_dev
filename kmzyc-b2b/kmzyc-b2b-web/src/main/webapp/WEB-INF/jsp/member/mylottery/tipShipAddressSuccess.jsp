<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java"  import="com.kmzyc.zkconfig.ConfigurationUtil"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/html/common/version.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%
    final String TITLE_SUFFIX = "康美中药城,中国首个线上药材市场,康美药业股份有限公司旗下\n";
    String title = TITLE_SUFFIX;
    String titlePrefix = request.getParameter("titlePrefix");
    if (null != titlePrefix && !"".equals(titlePrefix.trim())){
        title = titlePrefix + "-" + TITLE_SUFFIX;
    }

    String jspPath = request.getRequestURI();
    boolean rootPage = jspPath.indexOf("WEB-INF/") == -1; //是否为根路径下的jsp，如error.jsp、index.jsp等
    String templatePath = null;
    if(!rootPage){
        templatePath  = jspPath.substring(jspPath.indexOf("WEB-INF/")+"WEB-INF/".length(), jspPath.lastIndexOf("."));
    }

    String staticUrl = ConfigurationUtil.getString("WAP_CSS_JS_PATH");
    String debug = "1";//1:开发版本 0：线上版本
    String jsBaseUrl = "1"== debug ? "script/" : "js/";
    String cssBaseUrl = "1"== debug ? "style/default/" : "css/default/";

    String imageBaseUrl = "images/";
%>
<meta name="Keywords" content="康美中药城,中国首个线上药材市场,康美,康美官方旗舰店,健康第一,康美药业,康美中药城,商城,康美人生,国参传奇,菊皇茶,西洋参,花旗参,红参,冬虫夏草,虫草瘦身食品,人参,三七,田七,丹参,新开河,新开河红参,红参,高丽参,花旗参,白参,人参切片,参茸贵细"/>
<meta name="Description" content="康美中药城是康美药业股份有限公司旗下运营的网上药材采购商城。便捷、诚信的服务，为您提供愉悦的网上购物体验!"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" /><!-- 防止IE8,7进入怪异模式 -->
<meta name="apple-itunes-app" content="app-id=432274380">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="renderer" content="webkit" />
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport" />
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<meta http-equiv="Expires" CONTENT="-1">           
<meta http-equiv="Cache-Control" CONTENT="no-cache">           
<meta http-equiv="Pragma" CONTENT="no-cache"> 

<title><%=title%></title>
<link rel="shortcut icon" href="<%=staticUrl%><%=imageBaseUrl%>kmzl.ico" type="image/x-icon"/>
<!-- 资讯样式引用 -->
<link type="text/css" rel="stylesheet" href="<%=staticUrl%><%=cssBaseUrl%>common.css?version=<%=version%>"/><!--缓存头部共用样式 -->
<link type="text/css" rel="stylesheet" href="/fonts/fonts.css?version=<%=version%>"/>
<%
    if(!rootPage){
%>
<link type="text/css" rel="stylesheet" href="<%=staticUrl%><%=cssBaseUrl%><%=templatePath%>.css?version=<%=version%>"/>
<%
    }
%>
<!--[if lte IE 9]><style type="text/css">.css3pie{behavior: url(<%=staticUrl%>/css/css3pie/PIE.htc);}</style><![endif]-->
<script type="text/javascript">
    var KM = KM || {};
    KM.VERSION = '<%=version%>';//项目版本号
    KM.staticUrl = '<%=staticUrl%>';
    KM.jsBaseUrl = '<%=jsBaseUrl%>';
</script>
<script type="text/javascript" src="<%=staticUrl%><%=jsBaseUrl%>seajs/2.0.2/sea.js?version=<%=version%>"></script>
<script type="text/javascript" src="<%=staticUrl%><%=jsBaseUrl%>config.js?version=<%=version%>"></script>

<script type="text/javascript">
    seajs.use(['<%=staticUrl%><%=jsBaseUrl%>view/common.js']);
</script>

</head>

<body class="l-w1000">
	<!--顶部登录条-->
  <%--   <%@ include file="/html/common/wap/portal-head.jsp" %>
 --%>
  		<jsp:include page="/html/common/wap/portal-head.jsp">
	     	<jsp:param name="pageTitle" value="地址提交成功"></jsp:param>
	    </jsp:include> 
	    
   		<!--内容区域  -->
   		<div class="container">
		  <div class="ibox float-e-margins">
		    <div class="ibox-title">
		      <div class="ui-tipbox-content text-center">
		        <h3 class="ui-tipbox-title">奖品收货地址提交成功！</h3>
		        <p class="mag-top">请耐心等待奖品送达！有关订单配送问题请联系</p>
		        <p class="text-success fz-zt">刘瑞峰：0755-33189825</p>
		      </div>
		    </div>
		    <div class="list-box text-center ibox-content"> <a target="_blank" href="http://m.kmb2b.com/">康美中药城&gt;&gt;</a></div>
		  </div>
		</div>

	<!--底部-->
   <%@ include file="/html/common/wap/portal-foot.jsp" %>  
</body>
</html>

