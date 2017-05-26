<%@ page language="java"  import="com.kmzyc.zkconfig.ConfigurationUtil" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/html/common/version.jsp"%>
<%
    final String TITLE_SUFFIX = "康美中药城-中国首个线上药材市场";
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
<meta name="Keywords" content="康美中药城，康美药业，滋补商城，康美商城，健康商城"/>
<meta name="Description" content="康美中药城，是中国首个线上药材市场，提供在线滋补品、营养保健、中西药品、健康食品等销售。康美药业旗下滋补电商，国家药监局认证，发货迅速,为您提供愉悦的线上健康服务体验！"/>
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
<script type="text/javascript" >								
var _oztime = (new Date()).getTime();								
</script>
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
    <%
        if(!rootPage){
    %>
    seajs.use(['<%=staticUrl%><%=jsBaseUrl%>view/<%=templatePath%>.js']);
    <%
        }
    %>
</script>
