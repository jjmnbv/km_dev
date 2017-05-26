<%@ page language="java" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html>
<head>
<meta name="Keywords" content="" />
<meta name="Description" content="" />
<jsp:include page="/WEB-INF/jsp/common/template.jsp">
	<jsp:param name="titlePrefix" value="运费管理-店铺运费"></jsp:param>
</jsp:include>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE"/>
<meta name="renderer" content="webkit|ie-comp|ie-stand"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>运费管理-店铺运费</title>
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
			          <li>运费管理 <span class="divider">/</span></li>
			          <li>设置店铺运费</li>
			        </ul>
			      </div>
		      <div class="block-content collapse in"> 
		        <!--内容菜单开始-->
		        <ul class="nav nav-tabs">
		          <li class="active"><a href="#home" data-toggle="tab"  >设置店铺运费</a></li>
		          <li><a href="/product/findAllProductSkuForFreight.action" >设置单品运费</a></li>
		        </ul>
				<form class="form-horizontal" >
					<fieldset>
						<s:hidden name="supplierFare.fareId" id="fareId"/>
						<div class="control-group"><label class="control-label"
							for="typeahead"><span class="required">*</span>每单固定运费（元）： </label>
						<div class="controls">
							<s:textfield type="text" class="span3" id="firstHeavyFreightId" data-provide="typeahead" data-items="4" name="supplierFare.firstHeavyFreight"/>
							<p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10 j_manageBrand_error" style="display: none;">
								<i class="ui-icon ui-icon-error"></i><span id="manageBrand_error_msg"></span>
							</p>
						</div>
						</div>
					
						<div class="control-group"><label class="control-label" for="typeahead"><span class="required">*</span>最低包邮金额（元）： </label>
						<div class="controls">
							<s:textfield type="text" class="span3" id="freePrice" data-provide="typeahead" data-items="4" name="supplierFare.freePrice" />
							<p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10 j_freePrice_error" style="display: none;">
								<i class="ui-icon ui-icon-error"></i><span id="freePrice_error_msg"></span>
							</p>
						</div>
						</div>
					
					<div class="form-actions">
						<a class="btn btn-success btn-large j_save_update">
							<i class="icon-arrow-up icon-white"></i>
							保 存
						</a>
					</div>
					</fieldset>
				</form>
				<form id="frm"></form>

</div>
		          
		      </div>
		    </div>
  		 </div>
<!-- 底部 -->
<jsp:include page="/WEB-INF/jsp/common/menubars/bottomMenu.jsp"></jsp:include>
<!-- 底部  end-->
</body>
</html>
