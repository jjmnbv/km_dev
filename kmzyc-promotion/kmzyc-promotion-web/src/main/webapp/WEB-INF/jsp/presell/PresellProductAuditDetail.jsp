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
.tableStyle1 {
	font-size: 15px;
}
.emDiv,.sbDiv{
float: left;position:relative;margin:3px 5px 2px 0;
white-space:nowrap;height:15px;line-height: 15px;
cursor:pointer;border-radius:17px;border-style:
solid;border-width:1px;font-size:14px;
padding:2px 19px;border-color:#edb8b8;
background-color:#ffeaea;color:#c30!important;
display:inline-block;vertical-align:middle;
}

em{
margin-left:-8px;vertical-align:top;display:inline-block;font-style:normal;
text-decoration:none;white-space:nowrap;line-height:15px;cursor:pointer;font-size:14px;
}
.aclose,.deleteP{position: absolute;right: -2px;top: -1px;text-decoration: none;font-family: verdana;border-radius: 0 17px 17px 0;
font-weight: bold;padding: 2px 5px 2px 3px;border-width: 1px;border-style: solid;border-color:#edb8b8!important;color:#c30!important;}


.update_status{
color:#888888;

font-size: 12px;
}
</style>
</head>      
<s:set name="parent_name" value="'预售管理  '" scope="request"/>
<s:set name="name" value="'预售审核'" scope="request"/>
<s:set name="son_name" value="' 编辑'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<body>
	<div style="margin:10px">
			<button class="backBtn" id="return"></button>
			
 	</div>
<div id="maind" style="width:90%;margin-left:5%;text-algin:center;">
<div id="content">		
<form action="saveAuditSpreaderDetail.action" method="post" id="myForm">
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
		  	<td width="85%">${info.presellTitle}</td>
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
		  	<td >
		  	<c:choose>
		  	<c:when test="${not empty info.byLimit and info.byLimit!=0}">
		    	${info.byLimit}件
		  	</c:when>
		  	<c:otherwise>
		  	不限购
		  	</c:otherwise>
		  	</c:choose>
		  	</td>
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
	    <c:if test="${status==1}">
		<tr>
		  	<td colspan="2"  align="center">
		
			  	<div class="btn_save">
			  		<input type="button"   value="审核通过" id="saveSpreaderPass" />
			  		<input type="button"   value="审核拒绝" id="saveSpreaderReject" />
			  	</div>
			 
		  	 </td>
		</tr>
		</c:if>
	</table>
	<br><br><br><br><br><br>
<%-- 	<input type="hidden" name="" id="applyType" value="${info.applyType }"> --%>
	<input type="hidden" id="auditStatus" value="${info.auditStatus }">
	<input type="hidden" id="presellId"   value="${info.presellId }">
<%-- 	<input type="hidden" name="spreaderApplyRecord.enchashmentType"   value="${info.enchashmentType }"> --%>
</form>	
</div></div>
</body>
<script type="text/javascript" src="/etc/autocomplete/jquery.autocomplete.js"></script>
<script type="text/javascript">

$(function(){
	//返回
  
	$('#return').click(function() {
			var status = $("#auditStatus").val();
			window.location.href = "/presell/queryAuditPresellList.action?flag=0" ;
		});

		$(".update_status").hide();
		var type = $("#applyType").val();
		if (type == 2) {
			$(".update_status").show()
		}

		$(".btn_save").hide();
		var status = $("#auditStatus").val();
		if (status == 0) {
			$(".btn_save").show();
		}

		$("#saveSpreaderPass")
				.click(
						function() {
							if (!confirm("确认审核通过吗？")) {
								return;
							}
			    			$.ajax({
			    				type:'post',
			    				url:'/presell/UpdateAuditPresell.action',
			    				data:{'auditStatus':1,'presellId':$("#presellId").val()},
			    				dataType:'json',
			    				beforeSend:function(){
	 								$("#saveSpreaderPass").attr("disabled","disabled");
			    				},
			    				success:function(data){
			    					var code = data.code;
			    					if(code==0){
										alert('审核成功');
										window.location.href = "/presell/getAuditPresellDetail.action?presellId="+$("#presellId").val() ;
			    		    		}else if (code==2){
			    		    			alert('该产品已经存在预售活动！');
			    		    		}else{
			    		    			alert('审核异常！');
			    		    		}
			    				}
			    			});
							
						})

		$("#saveSpreaderReject")
				.click(
						function() {
							if (!confirm("确认拒绝吗？")) {
								return;
							}
			    			$.ajax({
			    				type:'post',
			    				url:'/presell/UpdateAuditPresell.action',
			    				data:{'auditStatus':2,'presellId':$("#presellId").val()},
			    				dataType:'json',
			    				beforeSend:function(){
	 								$("#saveSpreaderReject").attr("disabled","disabled");
			    				},
			    				success :function(data){
			    					var code = data.code;
			    					if(code==0){
										alert('审核成功');
										window.location.href = "/presell/getAuditPresellDetail.action?presellId="+$("#presellId").val() ;
			    		    		}else{
			    		    			alert('审核异常！');
			    		    		}
			    				}
			    			});
						})

	})
</script>
</html>

