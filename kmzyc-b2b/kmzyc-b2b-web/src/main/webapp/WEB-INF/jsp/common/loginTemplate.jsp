<%@ page language="java"  import="com.kmzyc.zkconfig.ConfigurationUtil" pageEncoding="UTF-8"%>
<%@ include file="/html/common/version.jsp"%>
<%
    final String TITLE_SUFFIX = "";
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
    String staticUrl = null;
    if(request.getRequestURL().indexOf("login.kmb2b.com") <0){
      staticUrl=ConfigurationUtil.getString("WAP_CSS_JS_PATH");
    }else{
      staticUrl=ConfigurationUtil.getString("SSL_WAP_CSS_JS_PATH");
    }
    String debug = "1";//1:开发版本 0：线上版本
    String jsBaseUrl = "1"== debug ? "script/" : "js/";
    String cssBaseUrl = "1"== debug ? "style/default/" : "css/default/";
    String imageBaseUrl = "images/";
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" /><!-- 防止IE8,7进入怪异模式 -->
<meta name="apple-itunes-app" content="app-id=432274380">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="renderer" content="webkit" />
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<meta http-equiv="Expires" CONTENT="-1">           
<meta http-equiv="Cache-Control" CONTENT="no-cache">           
<meta http-equiv="Pragma" CONTENT="no-cache"> 
<title><%=title%></title>
<link rel="shortcut icon" href="<%=staticUrl%><%=imageBaseUrl%>kmzl.ico" type="image/x-icon"/>
<link type="text/css" rel="stylesheet" href="<%=staticUrl%><%=cssBaseUrl%>common.css?version=<%=version%>"/>
<link type="text/css" rel="stylesheet" href="/fonts/fonts.css?version=<%=version%>"/>
<!--[if lte IE 9]><style type="text/css">.css3pie{behavior: url(<%=staticUrl%>/css/css3pie/PIE.htc);}</style><![endif]-->
<script type="text/javascript">
    var KM = KM || {};
    KM.VERSION = '<%=version%>';
    KM.staticUrl = '<%=staticUrl%>';
    KM.jsBaseUrl = '<%=jsBaseUrl%>';
    KM.cssBaseUrl = '<%=cssBaseUrl%>';
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
</script>
