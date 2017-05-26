<%@page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ page import="com.kmzyc.zkconfig.ConfigurationUtil" %>
<%String cssAndJsPath = ConfigurationUtil.getString("cssAndJsPath"); %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML>
<html class="no-js">
<head>
<title>康美 - 供应商平台</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/WEB-INF/jsp/common/template.jsp">
	<jsp:param name="titlePrefix" value="供应商平台"></jsp:param>
</jsp:include>
</head>
<body>
        <jsp:include page="/WEB-INF/jsp/common/menubars/topMenu_index.jsp"></jsp:include>
		
<div class="container-fluid">
<div class="row-fluid"> 
  
  <div class="content" style=" margin:0 auto;">
    <div class="row-fluid"> 
      <!-- block -->
      <div class="block_01 shopheight">
        <div class="navbar-inner">
          <ul class="breadcrumb">
            <i class="icon-home"></i>
            <li>页面错误</li>
          </ul>
        </div>
        <div class="error"><img src="<%=cssAndJsPath %>resgys/images/error.png" alt=""/></div>
        <div class="block-content prompt"> 
亲爱的商家，您好！页面出现错误，请您稍后重试或与管理员联系。
        </div>
      </div>
    </div>
  </div>
  <hr>
</div>
</div>
<jsp:include page="/WEB-INF/jsp/common/menubars/bottomMenu.jsp"></jsp:include>
</body>
</html>
