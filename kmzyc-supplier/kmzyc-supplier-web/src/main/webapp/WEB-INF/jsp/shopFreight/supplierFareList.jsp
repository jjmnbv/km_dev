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
<div class="row-fluid"><!--左侧菜单开始--> <jsp:include
	page="/WEB-INF/jsp/common/menubars/leftMenu_shop.jsp"></jsp:include> <!--左侧菜单结束-->
<div class="content">
<div class="row-fluid">
<div class="navbar"></div>
</div>
<div class="row-fluid"><!-- block -->
<div class="block_01 shopheight">
<div class="navbar-inner">
<ul class="breadcrumb">
	<i class="icon-home"></i>
	<li>店铺 <span class="divider">/</span></li>
	<li>运费管理 <span class="divider">/</span></li>
	<li>设置店铺运费</li>
</ul>
</div>
<div class="block-content collapse in"><!--内容菜单开始-->
<ul class="nav nav-tabs">
	<li class="active"><a href="#home" data-toggle="tab">设置店铺运费</a></li>
	<li><a href="/product/findAllProductSkuForFreight.action">设置单品运费</a></li>
</ul>

<s:form action="toSupplierFare.action" method="post" id="frm" name="frm" namespace="supplier">
<table cellpadding="0" cellspacing="0" border="0"
	class="table com_tablest">
	<tbody>
		<tr>
			<td class="width150">每单固定价格</td>
			<td class="width150">免运费价格</td>
			<td class="width200">操作</td>
		</tr>
	</tbody>
</table>
<table cellpadding="0" cellspacing="0" border="0"
	class="table  table-bordered">
	<tbody>
	<s:iterator value="pagintion.recordList" id="shopMain">
		<tr>
			<td class="width150">
				<s:property value="firstHeavyFreight" />
			</td>
			<td class="width150">
				<s:property value="freePrice" />
			</td>
			<td class="width200">
				<button class="btn btn-mini btn-success j_update_fare" data-shopid="<s:property value='fareId'/>">修改店铺运费</button>
			</td>
		</tr>
	</s:iterator>
	</tbody>
</table>
</s:form>
</div>

</div>
</div>
</div>
<!-- 底部 --> <jsp:include
	page="/WEB-INF/jsp/common/menubars/bottomMenu.jsp"></jsp:include> <!-- 底部  end-->
</body>
</html>
