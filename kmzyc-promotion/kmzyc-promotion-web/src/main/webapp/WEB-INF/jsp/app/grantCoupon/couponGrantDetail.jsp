<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看</title>
<script src="/etc/js/dialog.js"></script>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<link href="/etc/js/dtree/dtree.css" type="text/css" rel="stylesheet">
<link href="/etc/js/ztree/ztree.css" type="text/css" rel="stylesheet">

<link href="/etc/css/validate.css"  rel="stylesheet" type="text/css" />
<script src="/etc/js/jquery-latest.pack.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
 <Script src="/etc/js/grantCoupon.js"></Script>
<script language="javascript" type="text/javascript" src="/etc/js/ztree/jquery-1.4.4.min.js"></script>
<script language="javascript" type="text/javascript" src="/etc/js/ztree/jquery.ztree.core-3.5.min.js"></script>
<script language="javascript" type="text/javascript" src="/etc/js/ztree/jquery.ztree.exhide-3.5.min.js"></script>
<script language="javascript" type="text/javascript"  src="/etc/js/ztree/jquery.ztree.excheck-3.5.min.js"></script>
 <script type="text/javascript" src="/etc/js/common.js"></script>
<script type="text/javascript" src="/etc/js/validate/easy_validator.pack.js"></script>

<script type="text/javascript"  src="/etc/js/validate/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/validate/jquery.metadata.js"></script>
<script type="text/javascript"  src="/etc/js/validate/messages_cn.js"></script>
<script type="text/javascript" src="/etc/js/couponGrant/couponGrantDetail.js"></script>

</head>
<body >
 
	 <s:set name="parent_name" value="'优惠劵管理'" scope="request" />
	
	<s:set name="name" value="'优惠券发放设置'" scope="request" />
	<s:set name="name" value="'查看'" scope="request" />
	
	<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
   
	<s:form action="/coupon/detailCoupon.action" method="POST" namespace='/coupon' id="couponForm" onsubmit="return couponFormChk()">
	<s:token></s:token>
	<table width="98%" align="center" height="35" border="0"	class="content_table"
			cellpadding="0" cellspacing="0">
			<tr>
				<td width="20%"  align="right"><font color="red">*</font>优惠券规则：</td>
				<td width="80%" align="left">&nbsp;&nbsp;<a href="javascript:void(0);" onClick="couponDetail();" id="couponNameShow"><s:property value="coupongrantSeting.couponName"/></a> 
					<input type="hidden" id="couponIdHid" name="coupongrantSeting.couponId" value="<s:property value='coupongrantSeting.couponId'/>"/>
					<input type="hidden" id="couponIssuingId"  name="coupongrantSeting.couponIssuingId" value="<s:property value='coupongrantSeting.couponIssuingId'/>"/>
				</td>
				
			</tr>
			<tr>
				<td width="20%"  align="right" ><font color="red">*</font>发布类型：</td>
				<td width="80%" align="left">
					<s:if test="coupongrantSeting.couponGivetypeId==1">	手工发放</s:if>
					<s:elseif test="coupongrantSeting.couponGivetypeId==2">注册发放</s:elseif>
					<s:elseif test="coupongrantSeting.couponGivetypeId==6">不记名发放</s:elseif>
				</td>
			</tr>
			<tr>
				<s:if test="coupongrantSeting.couponGivetypeId==2">
					<td width="20%"  align="right" ><font color="red">*</font>发放起止时间：</td>
					<td width="80%" align="left">
						<s:date name="coupongrantSeting.issuingStartTime" format="yyyy-MM-dd HH:mm:ss" />
								&nbsp;&nbsp;至&nbsp;&nbsp;
							<s:date name="coupongrantSeting.issuingEndTime" format="yyyy-MM-dd HH:mm:ss" />
					</td>
				</s:if>
				<s:else>
					<td width="20%"  align="right" ><font color="red">*</font>发放时间：</td>
					<td width="80%" align="left">
						<s:date name="coupongrantSeting.createTime" format="yyyy-MM-dd HH:mm:ss" />
					</td>
				</s:else>
			</tr>
			<tr>
				<td width="20%"  align="right" >发放备注：</td>
				<td width="80%" align="left">
						<s:property value="coupongrantSeting.couponDesc"/>
					</td>
				
			</tr>
			<s:if test="coupongrantSeting.couponGivetypeId==1">
			<tr>
				<td width="20%"  align="right" valign="top"><font color="red">*</font>发放范围：</td>
				<td width="80%" align="left">
					发放给以下指定会员：<br/>
					<table width="326" height="39" border="1" align="left"
										cellspacing="0" >
										<tbody>
												<tr>
													<th width="169">会员账号</th>
													<th width="156">会员姓名</th>
												</tr>
					                     </tbody>
					                     <s:iterator id="custiterator" value="userList">
					                     <tr>
					                     	<td width="169"><s:property value="loginAccount"/></td>
					                     	<td width="156"><s:property value="name"/></td>
					                     </tr>
					                     </s:iterator>
					 </table>
				</td>
			</tr>
			</s:if>
			<s:elseif test="coupongrantSeting.couponGivetypeId==2">
			<tr>
				<td width="20%"  align="right" >状态：</td>
				<td width="20%"  align="left" >
					<s:if test="coupongrantSeting.isStatus==1">未开始</s:if>
					<s:elseif test="coupongrantSeting.isStatus==2">已完成</s:elseif>
					<s:elseif test="coupongrantSeting.isStatus==3">进行中</s:elseif>
					<s:elseif test="coupongrantSeting.isStatus==4">暂停</s:elseif>
					<s:elseif test="coupongrantSeting.isStatus==5">截止</s:elseif>
				</td>
			</tr>
			</s:elseif>
			<s:else><!-- 不记名优惠券 -->
				<tr>
				
					<table width="98%" align="center" height="35" border="0"	class="content_table"
			            cellpadding="0" cellspacing="0">
							<tr> 
								<th colspan="5" align="left" class="edit_title">发放详细规则</th>
							</tr>
							<tr>
							
								<td>激活状态：<s:select name="couponGrantVO.actStatus"
									id="isStatus" list="#{0:'未激活',1:'已激活'}" headerKey=""
									headerValue="---全部状态--">
									</s:select>
									<input type="hidden" id="couponGrantVOouponI"  name="couponGrantVO.couponIssuingId" value="<s:property value='coupongrantSeting.couponIssuingId'/>"/>
									</td>
								<td>券ID：<input type="text"  id="couponGrantId" name="couponGrantVO.couponGrantId"  value="<s:property value='couponGrantVO.couponGrantId'/>"  
								onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')"/></td>
								<td>关联会员：<input type="text"  id="customerName" name="couponGrantVO.customerName"  value="<s:property value='couponGrantVO.customerName'/>"/></td>
								<td>使用状态：<s:select name="couponGrantVO.couponStatus"
									id="couponStatus" list="#{1:'未发放',3:'未使用',4:'已使用',5:'已过期',7:'已冻结'}" headerKey=""
									headerValue="---全部状态--">
									</s:select></td>
								<td><input type="button"  class="btn-custom" onclick="selectCouponGrant()" value="查询"/>&nbsp;&nbsp;&nbsp;<input type="button" class="btn-custom" onClick="exportCouponGrant()" value="导出"/></td>
							</tr>
					</table>
				</tr>
				<tr>
					<table width="98%" class="list_table" align="center" cellpadding="3"
							cellspacing="0" border="1" bordercolor="#C1C8D2">
						<tr>
							<th width="6%" align="center">券ID</th>
							<th width="6%" align="center">激活状态</th>
							<th width="6%" align="center">优惠券号</th>
							<th width="6%" align="center">优惠券激活码</th>
							<th width="14%" align="center">有效时间</th>
							<th width="6%" align="center">关联会员</th>
							<th width="11%" align="center">激活时间</th>
							<th width="8%" align="center">使用状态</th>
							<th width="8%" align="center">使用时间</th>
						</tr>
						<s:iterator id="custiterator" value="page.dataList">
							<!-- 数据显示区 -->
							<tr>
								<td><s:property value="couponGrantId"/></td>
								<td>
									<s:if test="actStatus==0">未激活</s:if>
									<s:else>已激活</s:else>
								</td>
								<td><s:property value="couponInfoNo"/></td>
								<td><s:property value="activeCode"/></td>
								<td>
									<s:date name="starttime" format="yyyy-MM-dd HH:mm:ss" />&nbsp;&nbsp;至&nbsp;&nbsp;
									<s:date name="endtime" format="yyyy-MM-dd HH:mm:ss" />
								</td>
								<td><s:property value="customerName"/></td>
								<td><s:date name="actTime" format="yyyy-MM-dd HH:mm:ss" /></td>
								<td>
									<s:if test="couponStatus==3">未使用</s:if>
									<s:elseif test="couponStatus==2">已发放</s:elseif>
									<s:elseif test="couponStatus==4">已使用</s:elseif>
									<s:elseif test="couponStatus==1">未发放</s:elseif>
									<s:elseif test="couponStatus==5">已过期</s:elseif>
									<s:else>已冻结</s:else>
								</td>
								<td><s:date name="useTime" format="yyyy-MM-dd HH:mm:ss" /></td>
							</tr>
						</s:iterator>
					</table>
					<table width="98%" align="center" cellpadding="0" cellspacing="0">
						<tr>
						<td>
				  	    </td>
							<td><%@ include file="/WEB-INF/jsp/public/pager.jsp"%>
							</td>
						</tr>
				</table>
				
			</s:else>
			</table>
			
			<input  type="button" class="backBtn" onClick="backPage()" /><!--  <input type="button" value="测试" onclick="test()"/>-->
	</s:form>
</body>
<style type="text/css">
#auto_div {
	width: 515px;
	height: 230px;
	/*word-break:break-all;*/
	word-wrap: break-word;
	/*自动出现滚动条*/
	overflow: auto; /*超出div部份则自动隐藏*/
	*overflow-x: hidden;
	border: 1px solid #666;
}

#leve_div {
	width: 444px;
	height: 118px;
	word-wrap: break-word;
	overflow: auto;
	*overflow-x: hidden;
	border: 1px solid #666;
}
</style>
<script type="text/javascript">
    
</script>

 
</HTML>


