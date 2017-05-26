<%@page contentType="text/html;charset=UTF-8" import="com.pltfm.sys.util.StaticParams"  isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<title>地址管理</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet"
			type="text/css" />
		<script type="text/javascript" src="/etc/js/jquery-1.8.3.js">
</script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
		<script src="/etc/js/dialog.js">
</script>
		<script type="text/javascript" src="/etc/js/pageCommon.js">
</script>
		<script type="text/javascript">
/** 删除地址信息  **/
function deleteSelected(id, accountLoginId) {
	var obj = document.getElementsByName(id);
	var count = 0;
	var obj_cehcked = null;
	// 遍历所有用户，找出被选中的用户
	for ( var i = 0; i < obj.length; i++) {
		if (obj[i].checked) {
			count++;
			obj_cehcked = obj[i];
		}
	}
	if (count == 0) {
		alert("请选择要删除的数据。");
		return false;
	} else if (confirm('是否确认删除?') == true) {
		document.userLevelForm.action = "/accounts/address_operateDeleteAll.action?accountLoginId="+accountLoginId;
		document.userLevelForm.submit();
	}
}
/**  进入新增地址信息页面  **/
function gotoAdd() {
	var accountLogin = $('#accountLogin').val();
	//判断当前记录数，如果大于10提醒，不允许再添加
	 var totalCount = parseInt('<s:property default="0" value="page.recordCount" />');
	if(totalCount>=10&&""!=accountLogin){ 
		alert("当前客户的收货地址最多只能添加10条！");		
	}else{ 
		location.href = "/accounts/address_operateAdd.action";
	 } 
	
}

/** 进入修改地址页面  **/
function editUserLevel(id, loginId) {
	location.href = "/accounts/address_operateEdit.action?accountLoginId="+loginId+"&addressId=" + id;
}

/** 单条删除地址信息  **/
function deleteByKey(id, loginId) {
	if (confirm("是否确认删除? ") == true) {
		location.href = "/accounts/address_operateDelete.action?accountLoginId="+loginId+"&addressId="
				+ id;
	}
}
//弹出 选择账号层
function queryAccountInfo(id) {
	dialog("选择会员账号",
			"iframe:/accounts/accountInfo_preDetail.action?showType=4&n_AccountId="
					+ id, "900px", "760px", "iframe");

}
</script>
	</head>
	<body>
		<!-- 导航栏 -->
		<s:if test="%{showType==null}">
			<s:set name="parent_name" value="'客户资料'" scope="request" />
			<s:set name="name" value="'地址管理'" scope="request" />
			<s:set name="son_name" value="'查询'" scope="request" />
			<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		</s:if>
		<div style="height: 90%; overflow-y: auto;">
			<s:form name="userLevelForm"
				onsubmit=" return checkAllTextValid(this)"
				action="/accounts/address_queryPageList.action" method="post">
			<%-- 	<s:hidden name="address.loginId"/> --%>
				<!-- 查询条件 -->
				<table width="98%" align="center" border="0" class="content_table" cellpadding="0" cellspacing="0" style="display: <s:if test="1==viewOnly" >none</s:if>;">
						<tr>
							<td width="80%" valign="middle" colspan="6">
								
								<!--
								&nbsp;&nbsp; 
								<input class="delBtn" type="button" value=""
									onclick="deleteSelected('addressIds', <s:property value='address.loginId'/>);">
							 	-->
							</td>
						</tr>
						
				<tr>
						<s:if test="%{showType!=null}">
							<td align="right">
								会员账号：</td>
								<td><input id="accountLogin" name="address.accountLogin" type="text"
									value="<s:property value='address.accountLogin'/>" readonly placeholder="精确查询">
							</td>
						</s:if>
						<s:else>
						   	<td align="right">
								会员账号：</td>
								<td><input id="accountLogin" name="address.accountLogin" type="text" placeholder="精确查询"
									value="<s:property value='address.accountLogin'/>" >	
							</td>
						</s:else>
					<%-- 	<td align="right">
							收货人：</td>
							<td><input name="address.name" type="text"
								value="<s:property value='address.name'/>">
						</td> 
						<td align="right">
						手机号码：</td>
						<td><input name="address.cellphone" type="text" value="<s:property value='address.cellphone'/>"  placeholder="精确查询"/></td>
					</tr>
					<tr>
					<td align="right">
						创建日期从：</td><td>
						<input type="text" id="d523" readonly="readonly" class="Wdate"  value ="<s:date name = 'address.d_createdate' format='yyyy-MM-dd HH:mm:ss' />"    name="address.d_createdate"  onclick="WdatePicker({el:'d523',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
					</td>
					<td align="right">
					至：</td><td>
					<input type="text" id="d521" readonly="readonly" class="Wdate"  value ="<s:date name = 'address.endDate' format='yyyy-MM-dd HH:mm:ss' />"    name="address.endDate"  onclick="WdatePicker({el:'d521',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
					</td>
					<td align="right">
					收货地址：</td><td><input name="fuzzyAddress" type="text" value="<s:property value='address.fuzzyAddress' />" />
					</td> --%>
					<td align="right">
							<INPUT TYPE="submit" class="queryBtn" value="">
							<input type="hidden" name="showType" value="<s:property value='showType'/>">
                            <input class="addBtn" TYPE="button" value=""
									onclick="gotoAdd();">
						</td>
					</tr>
				</table>
				<s:if test="%{showType!=null}">
				<table width="98%" align="left" height="60" border="0" class="content_table" cellpadding="0" cellspacing="0" >
					<tr>
						<td align="left">&nbsp;&nbsp;&nbsp;会员账号：<s:property value='address.accountLogin'/></td>
					</tr>
				</table>
				</s:if>
				<!-- 数据列表区域 -->
				<table width="98%" class="list_table" cellpadding="3" align="center"
					cellspacing="0" border="1">
					<tr>
						<s:if test="1!=viewOnly">
						<th width="5%">
							<input type='checkbox' name='allbox'
								onclick="checkAll(this,'addressIds')">
						</th>
						</s:if>
						<s:if test="%{showType==null}">
						<th>
							会员账号
						</th>
						</s:if>
						<th>
							收货人
						</th>
						<th>
							手机号码
						</th>
						<th>
							联系电话
						</th>
						<th>
							地址
						</th>
						<th>
							详细地址
						</th>
						<th>
							邮编
						</th>
						<th>
							默认地址
						</th>
						<th>
							创建日期
						</th>
						<s:if test="1!=viewOnly">
						<th>
							操作
						</th>
						</s:if>
					</tr>
					<s:iterator id="custiterator" value="page.dataList">
						<tr>
							<s:if test="1!=viewOnly">
							<td width="5%">
								<input type="checkbox" name="addressIds"
									value='<s:property value="n_addressId"/>'>
							</td>
							</s:if>
							<s:if test="%{showType==null}">
							<td>
								<!-- <a href="#" onclick="queryAccountInfo(<s:property value="n_accountId"/>);"> -->
									<s:property value="accountLogin" />
								<!-- </a> -->
							</td>
							</s:if>
							<td>
								<s:property value="name" />
							</td>
							<td>
								<s:property value="cellphone" />
							</td>
							<td>
								<s:property value="telephone" />
							</td>

							<td>
								<s:property value="province" />
								<s:property value="city" />
								<s:property value="area" />
							</td>
							<td>
								<s:property value="detailedAddress" />
							</td>
							<td>
								<s:property value="postalcode" />
							</td>
							<td>
								<s:if test="status==0">
						   是
						   </s:if>
								<s:else>
						   否
						   </s:else>
							</td>
							<td>
							<s:date   name="d_createdate"  format="yy-MM-dd HH:mm:ss"/>
							</td>
							<s:if test="1!=viewOnly">
							<td>
								<img title="修改" style="cursor: pointer;"
									src="/etc/images/icon_modify.png"
									onclick="editUserLevel(<s:property value='n_addressId'/>,<s:property value='loginId'/>)" />
								<!-- <img title="删除" style="cursor: pointer;"
									src="/etc/images/icon_delete.png"
									onclick="deleteByKey(<s:property value="n_addressId"/>, <s:property value='loginId'/>)" />
									-- -->
							</td>
							</s:if>
						</tr>
					</s:iterator>
				</table>
				<s:if test="%{showType==null}">
					<table class="page_table" width="98%" align="center" cellpadding="0"
						cellspacing="0" border="0">
						<tr>
							<td>
								<s:set name="form_name" value="'userLevelForm'" scope="request"></s:set>
								<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
							</td>
						</tr>
					</table>
				</s:if>
			</s:form>
		</div>
		<!-- 消息提示 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
	</body>
</html>
