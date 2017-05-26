<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>增加类目属性</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<link href="/etc/css/validate.css" type="text/css" rel="stylesheet">
<script language="JavaScript" src="/etc/js/qtip/jquery.min.1.8.3.js" type="text/javascript"></script>
<script type="text/javascript"  src="/etc/js/validate/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/validate/jquery.metadata.js"></script>
<script type="text/javascript"  src="/etc/js/validate/messages_cn.js"></script>

</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>
<s:form name="cateAttrForm" id="cateAttrForm" action="/app/saveCategoryAttr.action" method="POST" >
<input type="hidden" id="categoryId" name="categoryAttr.categoryId" value="<s:property value="categoryAttr.categoryId"/>"/>
<input type="hidden" id="categoryIsUsed" value="<s:property value="categoryAttr.isUsed"/>"/>
<!-- hidden properties -->
<s:hidden name="categoryAttr.categoryAttrId" id="categoryAttrId"  />

	<table width="95%" class="tableInput1" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
		<tr> 
			<th width="30%" align="right"><font color="red">*</font>属性名称：</th>
			<td width="70%" align="left">
				<s:if test='categoryAttr.categoryId != null && categoryAttr.isUsed == 1 && categoryAttr.isSku == 1'>
					<input type="hidden" name="categoryAttr.categoryAttrName" value="<s:property value="categoryAttr.categoryAttrName"/>" />
					<s:property value="categoryAttr.categoryAttrName"/>
				</s:if>
				<s:else>
					<input name="categoryAttr.categoryAttrName" id="categoryAttrName" size="20" maxlength="20" type="text" value="<s:property value='categoryAttr.categoryAttrName'/>" />
				</s:else>
			</td>
		</tr>
		<tr> 
			<th align="right"><font color="red">*</font>输入类型：</th>
			<td align="left"> 
				<s:select name="categoryAttr.inputType" list="#{0:'文本框',1:'单选框',2:'多选框',3:'下拉框'}" listKey="key" listValue="value" id="inputType" ></s:select>
			</td>
		</tr>
		<tr> 
			<th align="right"><font color="red">*</font>是否必填：</th>
			<td align="left"> 
				<s:radio list="#{'0':'否','1':'是'}" name="categoryAttr.isReq" />
			</td>
		</tr>
		<tr> 
			<th align="right"><font color="red">*</font>是否导航：</th>
			<td align="left"> 
				<s:radio list="#{'0':'否','1':'是'}" name="categoryAttr.isNav" />
			</td>
		</tr>
		<tr> 
			<th align="right"><font color="red">*</font>是否SKU规格：</th>
			<td align="left"> 
				<s:radio list="#{'0':'否','1':'是'}" name="categoryAttr.isSku"  />
			</td>
		</tr>
		<tr> 
			<th align="right">排序：</th>
			<td align="left"> 
				<input type="text" name="categoryAttr.sortno" maxlength="3" id="sortno" size="5"  value="<s:property value='categoryAttr.sortno'/>" />  
			</td>
		</tr>
		<tr id="addAttrValueTr"> 
			<th colspan="2" align="left" class="modeltitle"><INPUT id="addAttrValue" class="btngreen" TYPE="button" value="添加属性值"></th>
		</tr>
		<s:iterator value="categoryAttr.categoryAttrValueList">
		<tr class="dynaAttr"> 
			<th align="right">属性值：</th>
			<td align="left">
				<s:if test='isUsed == 1'>
					<s:property value="categoryAttrValue" />
				</s:if>
				<s:else>
					<input type="hidden" name="categoryAttrValueList.categoryAttrValueId" value="<s:property value='categoryAttrValueId'/>" />
					<input type="text" maxlength="20" name="categoryAttrValueList.categoryAttrValue" value="<s:property value='categoryAttrValue'/>" onBlur="checkRepeatValue(this);" />
					<a href="#" onClick="delAttrValue(this,<s:property value='categoryAttrValueId'/>);">&nbsp;删除&nbsp;</a>
				</s:else>
			</td>
		</tr>
		</s:iterator>
		<s:iterator value="categoryAttr.oldValueIds" var="val" >
			<input type="hidden" name="categoryAttr.oldValueIds" value="<s:property value='#val'/>" />
		</s:iterator>
		<tr id="AttrValueTR">
			<td colspan="2" align="center">
				<input id="submitButton" type="button" class="btngreen" style="height:30px" onClick="frmSubmit();" value="  保存  " />&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="btngreen" value=" 关闭 " style="height:30px" onClick="closeDiv();"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</td>
		</tr>
	</table>
 
</s:form>
<script type="text/javascript" >
$(document).ready(function() {
	//添加属性值
	$("#addAttrValue").click(function () {
	  var AttrValueTR = $("#AttrValueTR");
	  var AttrValueTRHTML = '';
	  AttrValueTRHTML += '<tr class="dynaAttr">';
	  AttrValueTRHTML += '<th align="right">属性值：</th> ';
	  AttrValueTRHTML += '<td align="left"><input type="text" maxLength="20" name="categoryAttrValueList.categoryAttrValue" size="20" onblur="checkRepeatValue(this);" />';
	  AttrValueTRHTML += '<a href="#" onclick="delAttrValue(this);">&nbsp;删除&nbsp;</a></td>';
	  AttrValueTRHTML += "</tr>";
	  AttrValueTR.before(AttrValueTRHTML);
	});
	
	$("#inputType").change(function(){
		if(this.value==0){
			$("#addAttrValue").attr("disabled",true);
			$("input[type='radio'][name='categoryAttr.isNav'][value='0']").attr("checked",true);
			$("input[type='radio'][name='categoryAttr.isNav']").attr("disabled",true);
			$(".dynaAttr").remove();
		}else{
			$("input[type='radio'][name='categoryAttr.isNav']").attr("disabled",false);
			$("#addAttrValue").attr("disabled",false);
		}
		if(this.value==2){
			$("input[type='radio'][name='categoryAttr.isSku']").attr("disabled",false);
		}else{
			$("input[type='radio'][name='categoryAttr.isSku'][value='0']").attr("checked",true);
			$("input[type='radio'][name='categoryAttr.isSku']").attr("disabled",true);
			$("input[type='radio'][name='categoryAttr.isReq']").attr("disabled",false);
		}
		
	}).change();
	
	
	if($("#categoryAttrId").val()!=""&&$("#categoryIsUsed").val()==1){
		$("#inputType").attr("disabled",true);
		$("input[type='radio'][name='categoryAttr.isSku']").attr("disabled",true);
	}
});
//删除属性值
function delAttrValue(obj,valueId){
	
	if(typeof(valueId) == "undefined"){
		$(obj).parent().parent().remove();
	}else{
		$.post(
			"/app/findIsUsedSkuValue.action",
			{
				attrValueId:valueId
			},
			function(msg){
				if("isUsed" == msg){
					alert("该属性值已被使用，不能删除！");
				}else{
					$("#cateAttrForm").append('<input type="hidden" name="categoryAttr.deleteValueIds" value="'+valueId+'" />');
					$(obj).parent().parent().remove();
				}
			}
		);
	}
}

function checkRepeatValue(obj){
	if(obj.value!=""){
		var flag = false;
		$("input[name='categoryAttrValueList.categoryAttrValue']").each(function(i){
			if(obj != this){
				if(obj.value == this.value){
					flag = true;
					return false;
				}
			}
		});
		
		if(flag){
			alert("已有相同的属性值，请重命名！");
			setTimeout(function(){
				obj.select();
			},10);
			
		}
		
	}
}

//提交表单数据
function frmSubmit(){
	var flag = true;
	if($("#categoryAttrName").val()==""){
		alert("属性名不能为空");
		$("#categoryAttrName").select();
		return;
	}else{
		var tel=/^[^\|"'<>~&$%{}《》#｛｝￥（）【】|]*$/;
		if(!(tel.test($("#categoryAttrName").val()))){
			alert("不能包含特殊字符！");
			$("#categoryAttrName").select();
			return;
		}
		if(!($("input[type='radio'][name='categoryAttr.isSku']")&&$("input[type='radio'][name='categoryAttr.isSku']:checked").val() == "1")){
			$.ajax({
				async:false,
				url:"/app/findAttrRepeatName.action",
				data:"categoryAttr.categoryId="+$("#categoryId").val()+"&categoryAttr.categoryAttrId="+$("#categoryAttrId").val()+"&categoryAttr.categoryAttrName="+$("#categoryAttrName").val(),
				type:"POST",
				success:function(data){
					if(data=="true"){
						flag = false;
						alert("属性名【"+$("#categoryAttrName").val()+"】已存在，请重命名！");
						$("#categoryAttrName").select();
						return;
					}
				}
			});
		}
	}
	if($("#sortno").val()!=""&&!/^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/.test($("#sortno").val())){
		alert("排序只能为数字！");
		$("#sortno").select();
		return;
	}
	
	if(!flag){
		return;
	}
	if($("#inputType").val()!=0){
		if($("tr.dynaAttr").length==0){
			alert("请添加属性值！");
			return;
		}else{
			$("input[type='text'][name='categoryAttrValueList.categoryAttrValue']").each(function(i){
				if(this.value.replace(/(^\s*)|(\s*$)/g, "")==""){
					alert("请输入属性值！");
					this.select();
					flag = false;
					return false;
				}else if(this.value.length>30){
					alert("请最多输入30个字符！");
					this.select();
					flag = false;
					return false;
				}
			});
		}
	}
	if(!flag){
		return;
	}
	
	$("input[name$='categoryAttrValueList.categoryAttrValueId']").each(function(i){
		$(this).attr("name","categoryAttr.categoryAttrValueList["+i+"].categoryAttrValueId");
	});
	$("input[name$='categoryAttrValueList.categoryAttrValue']").each(function(i){
		$(this).attr("name","categoryAttr.categoryAttrValueList["+i+"].categoryAttrValue");
	});
	
	if(flag){
		$("#inputType").attr("disabled",false);
		$("input[type='radio'][name='categoryAttr.isSku']").attr("disabled",false);
		$("#cateAttrForm").submit();
	}
	
}
function closeDiv(){
    parent.closeThis();
}
</script>
</BODY>
</HTML>