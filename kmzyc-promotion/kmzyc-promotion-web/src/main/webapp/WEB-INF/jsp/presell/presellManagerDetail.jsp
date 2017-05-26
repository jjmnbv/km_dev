<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="/etc/js/qtip/jquery.min.1.8.3.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css">

<script language="JavaScript" src="/etc/js/artDialog4.1.7/artDialog.js?skin=default" type="text/javascript"></script>
<script language="JavaScript" src="/etc/js/artDialog4.1.7/plugins/iframeTools.source.js" type="text/javascript"></script>


<script type="text/javascript"  src="/etc/js/validate/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/validate/jquery.metadata.js"></script>
<script type="text/javascript"  src="/etc/js/validate/messages_cn.js"></script>
<script type="text/javascript"  src="/etc/js/promotion.js"></script>
<title>查看</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/validate.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/etc/css/autocompletestyles.css">
<style type="text/css">
.tableStyle1{font-size:15px}
.emDiv,.sbDiv{position:relative;float:left;display:inline-block;margin:3px 5px 2px 0;padding:2px 19px;height:15px;border-color:#edb8b8;border-style:solid;border-width:1px;border-radius:17px;background-color:#ffeaea;color:#c30!important;vertical-align:middle;white-space:nowrap;font-size:14px;line-height:15px;cursor:pointer}
em{display:inline-block;margin-left:-8px;vertical-align:top;text-decoration:none;white-space:nowrap;font-style:normal;font-size:14px;line-height:15px;cursor:pointer}
.aclose,.deleteP{position:absolute;top:-1px;right:-2px;padding:2px 5px 2px 3px;border-color:#edb8b8!important;border-style:solid;border-width:1px;border-radius:0 17px 17px 0;color:#c30!important;text-decoration:none;font-weight:700;font-family:verdana}
</style>
</head>      
<s:set name="parent_name" value="'预售管理  '" scope="request"/>
<s:set name="name" value="'预售商品管理'" scope="request"/>
<s:set name="son_name" value="'查看'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<body>
<div id="maind" style="width:90%;margin-left:5%;text-algin:center;">
<div id="content">		
<form action="" method="post" id="myForm">
	<!-- 数据编辑区域 -->
	<table width="90%" class="edit_table" align="center" cellpadding="3"
		cellspacing="0" border="1" bordercolor="#C7D3E2"
		style="border-collapse: collapse; font-size: 12px;">
		<!-- error message -->
		<s:if test="rtnMessage != null">
			<tr>
				<td colspan="2" align="center"><font color="red"><s:property
					value='rtnMessage' /></font></td>
			</tr>
		</s:if>
		
		
		<tr>
			<th width="15%" align="right" class="eidt_rowTitle">预售标题</th>
		  	<td width="85%">
		  	${info.presellTitle}
		  	<input id="inputPresellId" value="${info.presellId}" type="hidden">
		  	</td>
		</tr>
		<tr>
			<th  align="right" class="eidt_rowTitle">商家类别</th>
		  	<td >
		  	 <c:if test="${info.shopSort ==1 }"> 康美自营代销</c:if> 
			 <c:if test="${info.shopSort ==2 }">指定入驻商家</c:if> 
			</td>
		</tr>
		<c:if test="${info.shopSort ==2 }">
		  <tr>
			<th  align="right" class="eidt_rowTitle">商家名称</th>
		  	<td >${info.corporateName}</td>
		  </tr>
		</c:if>
		<tr>
			<th  align="right" class="eidt_rowTitle">预售商品</th>
		  	<td >
		  		<table width="98%" class="list_table" align="center" cellpadding="3"
				       cellspacing="0" border="1" bordercolor="#C1C8D2">
				    <tr>
						<th align="center" width="20%">商品标题</th>
						<th align="center" width="12%">SKU</th>
						<th align="center" width="15%">品牌</th>
						<th align="center" width="7%">单价</th>
						<th align="center" width="7%">实际库存</th>
						<th align="center" width="7%">预售价</th>
						<th align="center" width="7%">定金</th>
						<th align="center" width="10%">预售库存（件）</th>
					</tr>
					<c:forEach var="detailLitem" items="${detailList}"> 
					<c:if test="${detailLitem.presellId == info.presellId }">
					 <tr>
						<td align="center" width="20%">${detailLitem.productTitle}</td>
						<td align="center" width="12%">${detailLitem.productSkuCode}</td>
						<td align="center" width="15%">${detailLitem.brandName}</td>
						<td align="center" width="7%"><fmt:formatNumber value="${detailLitem.price}" pattern="0.00"/></td>
						<td align="center" width="7%">${detailLitem.stock}</td>
						<td align="center" width="7%"><fmt:formatNumber value="${detailLitem.presellPrice}" pattern="0.00"/></td>
						<td align="center" width="7%"><fmt:formatNumber value="${detailLitem.depositPrice}" pattern="0.00"/></td>
						<td align="center" width="10%">${detailLitem.presellStock}</td>
					</tr>
					</c:if>	
				    </c:forEach>

			
				</table>
			</td>
		</tr><tr>
			<th  align="right" class="eidt_rowTitle">初始预售数</th>
			<td >${info.initialPresellNum}件</td>
		</tr>
		<tr>
			<th  align="right" class="eidt_rowTitle">每人限购</th>
			<c:choose>
		  	<c:when test="${not empty info.byLimit and info.byLimit!=0}">
		    	<td>${info.byLimit}件</td>
		  	</c:when>
		  	<c:otherwise>
		  		<td>不限购</td>
		  	</c:otherwise>
		  	</c:choose>
		</tr>
		
		
		<tr>
			<th  align="right" class="eidt_rowTitle">定金支付起止时间</th>
		  	<td ><fmt:formatDate value="${info.depositStartTime}" pattern="yyyy-MM-dd HH:mm:ss" /> 至    <fmt:formatDate value="${info.depositEndTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
		</tr>
		<tr>
			<th  align="right" class="eidt_rowTitle">尾款支付起止时间</th>
		  	<td ><fmt:formatDate value="${info.finalpayStartTime}" pattern="yyyy-MM-dd HH:mm:ss" /> 至    <fmt:formatDate value="${info.finalpayEndTime}" pattern="yyyy-MM-dd HH:mm:ss" /> </td>
		</tr>
		<tr>
			<th  align="right" class="eidt_rowTitle">发货时间</th>
		   <td ><fmt:formatDate value="${info.deliveryStartTime}" pattern="yyyy-MM-dd HH:mm:ss" /> 至    <fmt:formatDate value="${info.deliveryEndTime}" pattern="yyyy-MM-dd HH:mm:ss" />  </td>
		</tr>
		<tr>
			<th  align="right" class="eidt_rowTitle">预售说明</th>
		  	<td >${info.presellDescribe}</td>
		</tr>
		<tr>
		  	<td colspan="2"  align="center">
			<div style="margin:10px">
				<input type="button" class="backBtn" id="return" onClick="goBack();" style="height:30px;">	
 			</div>
		  	 </td>
		</tr>
	</table>
	<br><br><br><br><br><br>
	<input type="hidden" name="" id="auditStatus" value="${info.auditStatus }">
</form>	
</div></div>
</body>
<script type="text/javascript" src="/etc/autocomplete/jquery.autocomplete.js"></script>
<script type="text/javascript">

$(function(){
	//提审按钮 
	$("#submitPresellBtn")
				.click(
						function() {
							if (!confirm("确认提审吗？")) {
								return;
							}
							var presellId = $("#inputPresellId").val();
							$.ajax({
								type: 'post',
								dataType:'json',
								url:'submitPresellApp.action?presellId='+presellId,
								success:function(data){
									if(data=="success"){        //提交成功
										alert("操作成功");
										location.href="queryPresellManagerList.action?flag=0";
									}else {						//提交失败
										alert("提交失败，请联系管理员！ ");
									}
								}
							}); 
						})

	})
	
	//返回
	function goBack(){
		window.location.href = "/presell/queryPresellManagerList.action?flag=0" ;
		
	}
</script>
</html>

