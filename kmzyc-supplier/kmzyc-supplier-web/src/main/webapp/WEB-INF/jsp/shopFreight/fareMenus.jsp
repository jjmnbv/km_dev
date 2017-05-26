<%@ page language="java" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html>
<head>
<meta name="Keywords" content="" />
<meta name="Description" content="" />
<jsp:include page="/WEB-INF/jsp/common/template.jsp">
	<jsp:param name="titlePrefix" value="运费管理"></jsp:param>
</jsp:include>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE"/>
<meta name="renderer" content="webkit|ie-comp|ie-stand"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>运费管理</title>
</head>
<body>
<!-- 顶部导航开始 -->
<jsp:include page="/WEB-INF/jsp/common/menubars/topMenu_index.jsp"></jsp:include>
<!-- 顶部导航结束 -->
<div class="container-fluid">
	<div class="row-fluid">
		<!--左侧菜单开始-->
		<jsp:include page="/WEB-INF/jsp/common/menubars/leftMenu_shop.jsp"></jsp:include>
		<!--左侧菜单结束-->
		<div class="content">
		  	<div class="row-fluid">
		    	<div class="navbar">
		      		
		    	</div>
		  	</div>
		  <div class="row-fluid"> 
		  	<!-- block -->
		  		<div class="block_01 shopheight">
		  		<div class="navbar-inner">
		        		<ul class="breadcrumb">
			          		<i class="icon-home"></i>
			          		<li>店铺 <span class="divider">/</span></li>
			          		<li>运费管理</li>
		        		</ul>
		      		</div>
		      		<div class="block-content collapse in"> 
				        <!--内容菜单开始-->
				        <ul class="nav nav-tabs">
				          <li class="active"><a href="#home" data-toggle="tab">运费管理</a></li>
				        </ul>
				        <!--内容菜单结束--> 
		        		<!--提示开始-->
		        		<div class="alert">
							提示：您还没有设置店铺运费。请您根据业务需要，选择进行店铺运费设置或单品运费设置。
		          		</div>
		          	</div>
		            <div class="btnnobg">
		              <a href="/supplier/toAddFare.action"  class="btn btn-success btn-large"><i class="icon-truck  icon-white"></i> 设置店铺运费</a>　　
		              <a  href="/product/findAllProductSkuForFreight.action" class="btn btn-danger btn-large"><i class="icon-barcode icon-white"></i> 设置单品运费</a>
		            </div>
		     	 </div>
		    </div>
  		 </div>
<!-- 底部 -->
<jsp:include page="/WEB-INF/jsp/common/menubars/bottomMenu.jsp"></jsp:include>
<!-- 底部  end-->
</body>
</html>
