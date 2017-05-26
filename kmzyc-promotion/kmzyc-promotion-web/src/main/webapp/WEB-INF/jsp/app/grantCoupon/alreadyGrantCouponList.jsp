<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="/etc/js/qtip/jquery.min.1.8.3.js"></script>
<title>优惠券发放</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="/etc/js/common.js"></script>
</head>
<s:set name="parent_name" value="'优惠券管理'" scope="request" />
 <s:set name="name" value="'已发放优惠券列表'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<body>
	<s:form name="frm" id="frm" method="post" action="/coupon/showAlreadyGrantCouponList.action" style=" margin-top:9px">
		<!-- 查询条件区域 -->
		<table width="98%" class="searcharea" height="100" align="center" cellpadding="0" cellspacing="0">
				<tr>		
					<td align="right">发放类型：</td>
					<td><s:select name="paraForQuery.grantType" list="#request.couponGrantTypeMap"  headerKey="" headerValue="---全部类型---"/></td>
					<td align="right">规则ID:</td>
					<td><s:textfield cssClass="input_style" name="paraForQuery.couponId" id="couponId"/></td>
					<td align="right">规则名称:</td>
					<td><s:textfield cssClass="input_style" name="paraForQuery.couponAlreadyGrant.couponName"/></td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td align="right">用户名:</td>
					<td><s:textfield cssClass="input_style" name="paraForQuery.customerName" id="customerName"/></td>
					<td align="right">状态:</td>
					<td><s:select name="paraForQuery.couponStatus" list="#request.couponStatusMap" headerKey="" headerValue="---全部状态--"/></td>
					<td colspan="2">&nbsp;</td>
					<td align="center"><INPUT TYPE="button" class="queryBtn" value="" onClick="doSearch()" /></td>		
				</tr>
		</table>


		<!-- 数据列表区域 -->
		<table width="98%" class="list_table" align="center" cellpadding="3"
			cellspacing="0" border="1" bordercolor="#C1C8D2">
			<tr>
				<th  align="center">发放券ID</th>
				<th  align="center">用户名</th>	
				<th  align="center">规则ID</th>		
				<th  align="center">优惠券规则名称</th>
				<th  align="center">发放类型</th>
				<th  align="center">面值</th>
				<th  align="center">不记名优惠券NO</th>
				<th  align="center">激活码</th>
				<th  align="center">可用时间范围</th>
				<th  align="center">使用时间</th>
				<th  align="center">优惠券状态</th>
			</tr>
			<s:iterator id="objIterator" value="page.dataList">
				<tr>
					<td>
							<s:property value="couponGrantId" />
					</td>
					<td>
							<s:property value="customerName" />
					</td>					
					<td>
							<s:property value="couponId" />
					</td>
					<td>
							<s:property value="couponAlreadyGrant.couponName" />
					</td>
					<td>
							<s:property value="#request.couponGrantTypeMap[grantType]" />
					</td>
					<td>
							<s:property value="couponAlreadyGrant.couponMoney" />
					</td>
					<td>
							<s:property value="couponInfoNo" />
					</td>
					<td>
							<s:property value="activeCode" />
					</td>
					<td>
						<s:date name="couponAlreadyGrant.starttime" format="yyyy-MM-dd HH:mm:ss" />至<s:date name="couponAlreadyGrant.endtime" format="yyyy-MM-dd HH:mm:ss" />				
					</td>
					<td>
						<s:date name="useTime" format="yyyy-MM-dd HH:mm:ss" />
					</td>
					<td>
						<s:property value="#request.couponStatusMap[couponStatus]" />
					</td>
				</tr>
			</s:iterator>
		</table>

		<!-- 分页按钮区 -->
		<table width="98%" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td><%@ include file="/WEB-INF/jsp/public/pagerList.jsp"%>
				</td>
			</tr>
		</table>
	</s:form>
	
	<script type="text/javascript">
		//查询
		function doSearch(){			
			//检查输入的规则ID是否为数字
			var regExp=/^\d*$/;
			if($("#couponId").val()!="" && !regExp.test($("#couponId").val())){
				alert("规则ID请输入数字!");
				$("#couponId").focus();
				return;
			}
			
			document.getElementById('pageNo').value = 1;
			document.forms['frm'].submit();
		}
	</script>
</body>
</html>

