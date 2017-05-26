<%@ page import="com.kmzyc.zkconfig.ConfigurationUtil" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/html/common/version_gys.jsp"%>
<%
    final String TITLE_SUFFIX = "康美供应商平台";
    String titlePrefix = request.getParameter("titlePrefix");
    String title = titlePrefix + "-" + TITLE_SUFFIX;
    String jspPath = request.getRequestURI();
    boolean rootPage = jspPath.indexOf("WEB-INF/") == -1; //是否为根路径下的jsp，如error.jsp、index.jsp等
    String templatePath = null;
    if(!rootPage){
        templatePath  = jspPath.substring(jspPath.indexOf("WEB-INF/")+"WEB-INF/".length(), jspPath.lastIndexOf("."));
    }

    String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	request.setAttribute("basePath",basePath);
    
    String staticUrl = "http://jscss.kmb2b.com";
    Boolean isDebug = ConfigurationUtil.getBooleanValue("isDebug");//true:开发版本 false：线上版本
    String jsBaseUrl = isDebug ? "/resgys/script/" : "/resgys/js/";
    String cssBaseUrl = isDebug ? "/resgys/style/default/" : "/resgys/css/default/";
    String imageBaseUrl = "/resgys/images/";
    
    request.setAttribute("jsBaseUrl",jsBaseUrl);
    request.setAttribute("staticUrl",staticUrl);
    request.setAttribute("cssBaseUrl",cssBaseUrl);
    request.setAttribute("imageBaseUrl",imageBaseUrl);
%>
<!-- 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />防止IE8,7进入怪异模式 -->

<!-- 新的版本兼容 -->
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><%=title%></title>
<link rel="shortcut icon" href="<%=staticUrl%><%=imageBaseUrl%>kmzl.ico" type="image/x-icon"/>
<link type="text/css" rel="stylesheet" href="<%=staticUrl%><%=cssBaseUrl%>bootstrap.min.css?version=<%=version%>"/><!--缓存头部共用样式 -->
<%--<link type="text/css" rel="stylesheet" href="<%=staticUrl%><%=cssBaseUrl%>css.css?version=<%=version%>"/><!--缓存头部共用样式 -->--%>
<link type="text/css" rel="stylesheet" href="<%=staticUrl%><%=cssBaseUrl%>styles.css?version=<%=version%>"/><!--缓存头部共用样式 -->
<link type="text/css" rel="stylesheet" href="<%=staticUrl%><%=cssBaseUrl%>thickbox.css?version=<%=version%>"/><!--缓存头部共用样式 -->
<link type="text/css" rel="stylesheet" href="<%=staticUrl%><%=cssBaseUrl%>tpl.css?version=<%=version%>"/><!--缓存头部共用样式 -->
<link href="<%=staticUrl%><%=jsBaseUrl%>common/autocomplete/autocompletestyles.css" rel="stylesheet" type="text/css">


<%
    //if(!rootPage){
%>
<!--<link type="text/css" rel="stylesheet" href="<%=staticUrl%><%=cssBaseUrl%><%=templatePath%>.css?version=<%=version%>"/>-->
<%
   // }
%>
<!--[if lte IE 9]><style type="text/css">.css3pie{behavior: url(<%=staticUrl%>/resgys/style/css3pie/PIE.htc);}</style><![endif]-->
<script type="text/javascript">
    var KM = KM || {};
    KM.VERSION = '<%=version%>';//项目版本号
</script>
<script type="text/javascript" src="<%=staticUrl%><%=jsBaseUrl%>seajs/2.0.2/sea.js?version=<%=version%>"></script>
<script type="text/javascript" src="<%=staticUrl%><%=jsBaseUrl%>config.js?version=<%=version%>"></script>
<script type="text/javascript" src="<%=staticUrl%><%=jsBaseUrl%>common/My97DatePicker/WdatePicker.js?version=<%=version%>"></script>


<script type="text/javascript">
	seajs.use(['<%=staticUrl%><%=jsBaseUrl%>view/scripts.js']); 
	seajs.use(['<%=staticUrl%><%=jsBaseUrl%>view/common.js']); 
	seajs.use(['<%=staticUrl%><%=jsBaseUrl%>view/bootstrap.min.js']);
 	seajs.use(['<%=staticUrl%><%=jsBaseUrl%>view/bootstrap.js']); 
 	
 	
<%
    if(!rootPage){
%>
    seajs.use(['<%=staticUrl%><%=jsBaseUrl%>view/<%=templatePath%>.js']);
<%
    }
%>
</script>
