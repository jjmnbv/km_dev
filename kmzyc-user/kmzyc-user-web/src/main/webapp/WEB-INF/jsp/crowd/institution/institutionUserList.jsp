<%@page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>机构查询</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<script type="text/javascript" src="/etc/js/pageCommon.js"></script>
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	var message = $("#message").val();
	if(message!='0'){
		alert(message);
	}
    
});

//禁止结算
function gotoUpdate(id){
	if (confirm("您确定要禁止结算这条数据？")){
		
		$.ajax({
			url:'/crowd/disClear.action',
			type:'post',
			dataType:'json',
			data:{'institutionUser.id':id},
			success:function(data){
				var success = data.success;
				if(success==true){
					alert(data.msg);
					$("#message").val('0');
					window.location.reload();
				}else{
					alert(data.msg);
					return;
				}
			},
			error:function(){
				alert('操作失败请联系管理员');
			}
		});      
 }
}
//结算单独一条数据
function gotoDetail(id){
	location.href="/crowd/queryInstitutionUserList.action?ids="+id+"&edit=clearAll"+"&clearingType=1";
}
function clearAll(type){
	if(type=='1'){
		if($("input[type='checkbox'][name='ids']:checked").length==0){
    		alert("请选择需结算数据！");
    		return false;
    	}
	}
$("#edit").val("clearAll"); 
$("#clearingType").val(type);
$("#queryinstitutionUserList").submit();
}
function unClearAll(){
	var obj = $("input[type='checkbox'][name='ids']:checked");
		if(obj.length==0){
    		alert("请选择需要禁止结算数据！");
    		return false;
    	}
	for(var i=0;i<obj.length;i++){
		var data =$(obj[i]).attr('data');
		if(data==1||data==2){
			alert("已结算和禁止结算的数据不能进行该操作！");
			return;
		}
		
	}	
	var obj = $("input[type='checkbox'][name='ids']:checked");
	var ids="";
	for(var i=0;i<obj.length;i++){
		if(i==0){
			ids= ids+obj[i].value;
		}else{
			ids=ids+","+obj[i].value;
		}
		
	}
	
$("#edit").val("unClearAll"); 
$.ajax({
	url:'/crowd/disClear.action',
	type:'post',
	dataType:'json',
	data:{'institutionUser.id':0,'ids':ids},
	success:function(data){
		var success = data.success;
		if(success==true){
			alert("操作成功！");
			$("#message").val('0');
			window.location.reload();
		}else{
			alert(data.msg);
			return;
		}
	},
	error:function(){
		alert('操作失败请联系管理员');
	}
}); 
$("input[name='ids']").each(function(){
	   $(this).attr("checked",false);
	  });  
}
function submitForm(){
	
	document.getElementsByName("page.pageNo")[0].value=1;
	$("#queryinstitutionUserList").submit();
}

</script>
</head>
<body>
	<!-- 标题条 -->
	<s:set name="parent_name" value="'机构管理'" scope="request" />

	<s:if test="edit=='edit'">
		<s:set name="name" value="'账户编辑'" scope="request" />
	</s:if>
	<s:else>
		<s:set name="name" value="'引荐注册列表'" scope="request" />
	</s:else>

	<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
	<div style="height: 90%; overflow-y: scroll;">


		<s:form id="queryinstitutionUserList" name="queryinstitutionUserList"
			action="/crowd/queryInstitutionUserList.action" method="POST">
			<input type="hidden" id="edit" name="edit"
				value="<s:property value="edit"/>">
			<input type="hidden" id="message" name="message"
				value="<s:property value="message"/>">
			<input type="hidden" id="clearingType" name="clearingType" value="">
			<!-- 查询条件区域 -->
			<table width="98%" class="content_table" align="center"
				cellpadding="0" cellspacing="0">
				<tr>
					<td colspan="8">
						<!--  <input class="addBtn" type="button" value="" onclick="gotoAdd();"> -->
						<%-- <input class="delBtn" type="button" value=""  onclick="deleteSelected('n_AccountIds');">--%>
					</td>
				</tr>

				<tr>
					<td align="right">用户名：</td>
					<td><input name="institutionUser.userName" type="text"
						value="<s:property value='institutionUser.userName'/>"></td>
					<td align="right">手机：</td>
					<td><input name="institutionUser.userPhoneNum" type="text"
						value="<s:property value='institutionUser.userPhoneNum'/>"></td>
					<td align="right">引荐机构编码：</td>
					<td><input name="institutionUser.institutionCode" type="text"
						value="<s:property value='institutionUser.institutionCode'/>">
					</td>
					<td align="right">引荐机构名称：</td>
					<td><input name="institutionUser.institutionName" type="text"
						value="<s:property value='institutionUser.institutionName'/>">
					</td>




				</tr>
				<tr>
					<td align="right">结算状态：</td>
					<td><select name="institutionUser.clearingStatus">
							<option value=""
								<s:if test='institutionUser.clearingStatus==""'>selected="selected"</s:if>>
								请选择</option>
							<option value=0
								<s:if test='institutionUser.clearingStatus=="0"'>selected="selected"</s:if>>
								未结算</option>
							<option value="1"
								<s:if test='institutionUser.clearingStatus=="1"'>selected="selected"</s:if>>
								已结算</option>
							<option value="2"
								<s:if test='institutionUser.clearingStatus=="2"'>selected="selected"</s:if>>
								禁止结算</option>
							<option value="3"
								<s:if test='institutionUser.clearingStatus=="3"'>selected="selected"</s:if>>
								无需结算</option>
					</select></td>

					<td align="right">机构验证手机：</td>
					<td><input name="institutionUser.institutionPhoneNum"
						type="text"
						value="<s:property value='institutionUser.institutionPhoneNum'/>">
					</td>
					<td align="right">注册时间：</td>
					<td><input type="text" id="d523" readonly
						class="Wdate"
						value="<s:date name = 'institutionUser.startDate' format='yyyy-MM-dd HH:mm:ss' />"
						name="institutionUser.startDate"
						onclick="WdatePicker({el:'d523',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
						至：<input type="text" id="d524" readonly class="Wdate"
						value="<s:date name = 'institutionUser.endDate' format='yyyy-MM-dd HH:mm:ss' />"
						name="institutionUser.endDate"
						onclick="WdatePicker({el:'d524',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
					</td>


					<td align="center" colspan="8"><INPUT TYPE="button" onClick="submitForm()"
 						class="queryBtn" value=""></td>

				</tr>
			</table>
            			<table width="98%" class="content_table" align="center"
				cellpadding="0" cellspacing="0">
				<tr>
                <td>
			&nbsp;&nbsp;&nbsp;<input class="btn-custom" style="height: 30px;" type="button" value="结算全部"
				onclick="clearAll('0')">
			<input type="button" class="btn-custom" style="height: 30px;" value="结算选中" onClick="clearAll('1')">
			<input type="button" class="btn-custom" style="height: 30px;" value="批量禁止结算" onClick="unClearAll()">
            </td>
            </tr>
            </table>
			<!-- 数据列表区域 -->
			<table width="98%" class="list_table" align="center" cellpadding="3"
				cellspacing="0" border="1" bordercolor="#C1C8D2">
				<tr>
					<th align="center" width="5%"><input type='checkbox'
						name='allbox' onClick="checkAll(this,'ids')"></th>
					<th align="center">用户名</th>
					<th align="center">手机</th>
					<th align="center">引荐机构编码</th>
					<th align="center">引荐机构名称</th>
					<th align="center">机构验证手机</th>
					<th align="center">注册分利现金</th>
					<th align="center">注册时间</th>
					<th align="center">结算状态</th>
					<th align="center">操作</th>

				</tr>
				<s:iterator id="accountiterator" value="page.dataList">
					<tr>
						<td align="center" width="5%"><input type="checkbox"
							name="ids" value='<s:property value="id"/>' data="<s:property value="clearingStatus" />"/></td>

						<td align="center"><s:property value="userName" /></td>
						<td align="center"><s:property value="userPhoneNum" /></td>
						<td align="center"><s:property value="institutionCode" /></td>
						<td align="center"><s:property value="institutionName" /></td>
						<td align="center"><s:property value="institutionPhoneNum" /></td>
						<td align="center"><s:property value='clearingAmount' /></td>

						<td align="center"><s:date name="createTime"
								format="yyyy-MM-dd HH:mm:ss" /></td>
						<td align="center"><s:if test="clearingStatus==0">未结算</s:if>
							<s:elseif test="clearingStatus==1">已结算</s:elseif> <s:elseif
								test="clearingStatus==2">禁止结算</s:elseif><s:elseif
								test="clearingStatus==3">无需结算</s:elseif> <s:else>未知状态 </s:else></td>
						<td width="70px;"><s:if test="clearingStatus==0">
								<input value="结算" class="btn-custom" type="button"
									onclick="gotoDetail(<s:property value="id"/>)" />
								<input class="btn-custom" value="禁止结算" type="button"
									onclick="gotoUpdate(<s:property value="id"/>)" />
								<%-- <img title="删除" style="cursor: pointer;" src="/etc/images/icon_delete.png"   onclick="deleteByKey(<s:property value="n_AccountId"/>)"/>--%>
							</s:if></td>

					</tr>
				</s:iterator>
			</table>

			<table width="98%" align="center" class="page_table">
				<tr>
					<td><s:set name="form_name" value="'queryinstitutionUserList'"
							scope="request"></s:set> <jsp:include
							page="/WEB-INF/jsp/common/page.jsp"></jsp:include></td>
				</tr>
			</table>
		</s:form>
		<!-- 消息提示页面 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
	</div>
</body>
</html>

