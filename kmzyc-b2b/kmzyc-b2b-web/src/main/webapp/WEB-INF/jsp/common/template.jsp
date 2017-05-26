<%@ page language="java"  import="com.kmzyc.zkconfig.ConfigurationUtil" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/html/common/version.jsp"%>
<%
    final String TITLE_SUFFIX = "康美中药城商城-中国首个线上药材市场";
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

    boolean isWap = "1".equals(request.getParameter("isWap"));
    String staticUrl = ConfigurationUtil.getString((isWap?"WAP_CSS_JS_PATH" : "CSS_JS_PATH"));
    String debug = "0";//1:开发版本 0：线上版本
    String jsBaseUrl = "1"== debug ? "script/" : "js/";
    String cssBaseUrl = "1"== debug ? "style/default/" : "css/default/";

    String imageBaseUrl = "images/";
%>
<meta name="Keywords" content="康美中药城，康美药业，滋补商城，康美商城，健康商城"/>
<meta name="Description" content="康美中药城,中国首个线上药材市场，国家药监局认证，发货迅速,为您提供愉悦的线上采购服务体验！"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" /><!-- 防止IE8,7进入怪异模式 -->
<title><%=title%></title>
<script type="text/javascript" >								
var _oztime = (new Date()).getTime();								
</script>
<link rel="shortcut icon" href="<%=staticUrl%><%=imageBaseUrl%>kmzl.ico" type="image/x-icon"/>
<!-- 资讯样式引用 -->
<link type="text/css" rel="stylesheet" href="<%=staticUrl%><%=cssBaseUrl%>common.css?version=<%=version%>"/><!--缓存头部共用样式 -->
<link type="text/css" rel="stylesheet" href="<%=staticUrl%><%=cssBaseUrl%>jsp/member/home/home.css?version=<%=version%>"/>
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
    seajs.use(['<%=staticUrl%><%=jsBaseUrl%>view/navigation_shopcart.js']);
    <%
        if(!rootPage){
    %>
    seajs.use(['<%=staticUrl%><%=jsBaseUrl%>view/<%=templatePath%>.js']);
    <%
        }
    %>
</script>
