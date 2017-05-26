<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>退换货申请</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/etc/css/orderTab.css">
<link rel="stylesheet" type="text/css" href="/etc/css/block.css">
<link rel="stylesheet" type="text/css" href="/etc/css/jq.css">
<link rel="stylesheet" type="text/css" href="/etc/css/jquery-ui.css">
<style type="text/css">
li {list-style-type:none;line-height:25px}
.Complaints-text{width:600px;height:100px}
.upload-photos{ padding:10px 0px;}
.upload-photos li{ height:50px; border:1px solid #e6e6e6; float:left; margin-right:10px;}
.upload-photos li img{ width:50px; height:50px;}
.upload-photos-Address{ background-color:#fffdee; border:1px solid #edd28b; padding:10px 0px; height:100%; width:600px;}
.upload-photos-Address label{ float:left;}
.upload-photos-form{}
.upload-photos-form span{ padding-left:45px;}
.upload-photos-Address .tisp{ padding-left:80px;}
.upload-photos-Address .tisp ul{ display:block; float:left;}
.upload-photos-Address .tisp ul li{ line-height:25px; color:#e1a156;}
font{color:blue}
</style>
<script type="text/javascript">
$(function(){
	
	//取消
    $('#cancel').bind('click', $.unblockUI);
	
    $("#ckForm").validate({     
	       rules: {
				"addressId":{required:true},
				"address":{required:true}
	       },
	   	   submitHandler: function (form) {
	            $.post(
	                	'/app/orderBackdownbackDownAction.action',
	               		$("#ckForm").serialize(),
	 	           		function(result){
	  			            alert(result);
	  			            if(result.indexOf("失败")>0){
	  			            	history.go(0);
	  			            }else{
	  			            	location.reload();
	  			            }
	  	     			}
	            );	
          }
	});
	
 });
</script>
</head>
<body>
<s:set name="parent_name" value="'业务操作'" scope="request"/>
<s:set name="name" value="'退换货管理'" scope="request"/>
<s:set name="son_name" value="'退换货申请'" scope="request"/>
<s:include value="/WEB-INF/jsp/public/title.jsp"/>
<form id="ckForm" action="">
	<table class="list_table" width="100%" align="center" cellpadding="3" cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc" style="border-collapse: collapse;font-size:12px">
		<tr>
			<td >商品名称：</td>
		</tr>
		<tr>
			<td >服务类型：				 
				<select  name="alterType">
		   			  <s:iterator id="st" value="returnType">
		   			  <option value="<s:property value='#st.key'/>"><s:property value="#st.value"/></option>
		   			  </s:iterator>
				 </select>
			</td>
		</tr>
		<tr>
			<td>商品数量：
				<select  name="alterNum" value="">
					<s:iterator value="new int[item.commodityNumber]" status="i">
    				    <option value='<s:property value="#i.index+1"/>'><s:property value="#i.index+1"/></option>
  					</s:iterator>
				 </select>
			</td>
		</tr>
		<tr><td>申请凭据：<input type="checkbox" name="evidence" value="1" checked="checked"/>有发票</td></tr>
		<tr><td>问题描述：<textarea class="alterComment" onpropertychange="if(getStrLeng(value)>500) value=value.substr(0,500)" oninput="if(getStrLeng(value)>500) value=value.substr(0,100)" name="alterComment" rows="5" cols="25">1</textarea></td></tr>
		<tr>
			<td>商品退回方式：
				 <select name="backType">
		   			  <option value="<s:property value='1'/>"><s:property value="快递至康美"/></option>
				 </select>
			</td>
		</tr>
		<tr><td>收货人：<input name="name" value="1"/></td></tr>
		<tr><td>收货电话：<input name="phone" value="1"/></td></tr>
		<tr><td>地址：<input name="address" value="1"/></td></tr>
		<tr>
			<td>
				<input type="hidden" name="" value=""/>
				<input type="submit" id="submit0" class="saveBtn" value=""/>
				&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" class="backBtn" id="cancel" />
				<input type="hidden" name="orderCode" value='<s:property value="item.orderCode"/>'/>
				<input type="hidden" name="itemId" value='<s:property value="item.orderItemId"/>'/>
			</td>
		</tr>
	</table>
</form>
</body>
</html>

