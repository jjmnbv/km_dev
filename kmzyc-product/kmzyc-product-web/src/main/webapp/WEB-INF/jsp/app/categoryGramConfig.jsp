<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>运营类目配置</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/js/ztree/ztree.css" type="text/css" rel="stylesheet">
<script language="javascript" type="text/javascript" src="/etc/js/ztree/jquery-1.4.4.min.js"></script>
<script language="javascript" type="text/javascript" src="/etc/js/ztree/jquery.ztree.core-3.5.min.js"></script>

<style type="text/css">
body{height:350px;}

.edit_table{
	margin-top:5px;
	margin-bottom: 15px;
	margin-left: 15px;
}

.edit_table_option {
    border: 1px solid #CDD7E1;
    margin-bottom: 15px;
    margin-left: 15px;
    margin-top: 5px;
}

.edit_table_option tr {
    background: none repeat scroll 0 0 #F1F8F0;
    border: 1px solid #CDD7E1;
    color: #656766;
    font-weight: normal;
    height: 35px;
    text-align: left;
}


.edit_table_option td {
    border: 1px solid #CDD7E1;
    clear: both;
    font-size: 14px;
}

.sqlString{
	padding:5px;
	border: 1px solid #CDD7E1;
	background: none repeat scroll 0 0 #F1F8F0;
}

ul.ztree {
    background: none repeat scroll 0 0 #F0F6E4;
    border: 1px solid #617775;
    height: 160px;
    margin-top: 10px;
    overflow-x: auto;
    overflow-y: scroll;
    width: 220px;
}
</style>

</head>
<body>
<input type="hidden" id="rtnMsg" value="<s:property value='#request.rtnMsg' />" />

<form action="/app/editCategoryCondition.action" method="post" id="conForm" >
<input type="hidden" name="execSql" id="execSql" value="" >
<input type="hidden" name="sqlString" id="sqlString" value="" >
<input type="hidden" value="<s:property value='#request.categoryMap' />" >
<s:hidden name="category.categoryId"></s:hidden>

<table  width="98%" class="content_table" height="35" align="center" cellpadding="0" cellspacing="0" >
	<tr>
		<td>
			<INPUT TYPE="button"  class="btnStyle" value="增加条件" onclick="addParentCondition();" >
			<!--
			<input type="button" class="btnStyle_09" value="增加子查询" >
			-->
			<input type="button" class="saveBtn" value="" onclick="saveFrm();" >
		</td>
	</tr>
</table>

<p class="sqlString">已存在的条件关系：<s:property value="category.sqlString" default="暂无" /></p>

<table width="90%" class="edit_table" align="center" cellpadding="3" cellspacing="0"
	border="1" bordercolor="#C7D3E2"
	style="border-collapse: collapse; font-size: 12px;">
	<tr>
		<th align="center">条件</th>
		<th align="center">运算符</th>
		<th align="center" width="180px;">值</th>
		<th align="center">
			<INPUT TYPE="button"  class="btnStyle" value="增加条件" onclick="addCondition(this.parentNode.parentNode.parentNode);" >
			&nbsp;
			<input type="button" class="btnStyle" value="删除" onclick="removeParentCondition(this.parentNode.parentNode.parentNode.parentNode);" />
		</th>
	</tr>
	<tr>
		<td align="center">
			<s:select list="#request.conditionMap" name="conName" onchange="conNameChange(this);" ></s:select>
		</td>
		<td align="center">
			<select name="conOperator">
				<option value="equal">等于</option>
				<option value="notEqual">不等于</option>
				<option value="like">包含</option>
				<option value="notLike">不包含</option>
			</select>
		</td>
		<td align="center">
			<input type="text" class="conValue" maxlength="20"  style="display: none;" onchange="conValueChange(this);" />
			<s:select list="#request.brandMap" cssClass="conValue" style="display: none;" onchange="conValueChange(this,1);" ></s:select>
			<input type="text" name="cateValue" class="conValue" onfocus="showMenu(this);"  style="display: none;" readonly="readonly" />
			<input type="hidden" name="conValue" value="" />
		</td>
		<td align="center">
			<input type="button" class="btnStyle" value="删除" onclick="removeCondition(this.parentNode.parentNode);" />
		</td>
	</tr>
</table>
</form>
<div id="menuContent" class="menuContent" style="display:none; position: absolute;">
	<ul id="treeDemo" class="ztree" style="margin-top:0; width:160px;"></ul>
</div>

<script type="text/javascript">
	//父条件的克隆值
	var cloneTb;
	//子条件的克隆值
	var cloneTr;
	$(function(){
		if($("#rtnMsg").val()!=""){
			alert($("#rtnMsg").val());
		}
		
		cloneTb = $("table:eq(1)").clone(true);
		cloneTr = $("table:eq(1)").find("tr:eq(1)").clone(true);
		
		//绑定条件名变化
		$(".edit_table").find("[name='conName']").change(function(){
			conNameChange(this);
		}).change();
		
		
		
	});

	//增加子条件
	function addCondition(arg){
		var _html = "<tr><td colspan='4' align='center'><select name='conBool'><option value='AND'>并且</option><option value='OR'>或者</option></select></td></tr>";
		var _cloneTr = cloneTr.clone(true);
		$(arg).append(_html);
		$(arg).append(_cloneTr);
		$(arg).find("[name='conName']:last").change();
	}
	
	//删除子条件
	function removeCondition(arg){
		if(arg.rowIndex == 1){
			alert("请不要删除第一个条件！");
			return;
		}
		$(arg).prev().remove();
		$(arg).remove();
	}
	
	//增加父条件
	function addParentCondition(){
		var _html = "<table  width='90%' class='edit_table_option' style='margin-top:0px;margin-bottom:0px;' ><tr><td align='left'><select name='conParBool'><option value='AND'>并且</option><option value='OR'>或者</option></select></td></tr></table>";
		var _cloneTb = cloneTb.clone(true);
		$("#conForm").append(_html);
		$("#conForm").append(_cloneTb);
		$(".edit_table:last").find("[name='conName']").change();
	}
	
	//删除父条件
	function removeParentCondition(arg){
		if($("table:eq(1)")[0]==arg){
			alert("请不要删除第一个条件！");
			return;
		}
		$(arg).prev().remove();
		$(arg).remove();
	}
	
	function saveFrm(){
		var flag = false;
		var obj;
		var condition = "";
		$("input[type='hidden'][name='conValue']").each(function(k){
			if($.trim(this.value) == ""){
				obj = this;
				flag = true;
				condition = "请输入条件值！";
				return false;
			}else{
				var tel=/^[^\|"'<>~&$%{}《》#!！｛｝￥（）……+【】:*=|]*$/;
				if(!tel.test(this.value)){
					obj = this;
					flag = true;
					condition = "请不要输入特殊字符！";
					return false;
				}
			}
		});
		if(flag){
			alert(condition);
			setTimeout(function(){
				$(obj).siblings("input[type='text']:visible").select();
			},10);
			return;
		}
		var _execSql = "";
		var _sqlStr = "";
		$("table.edit_table").each(function(i){
			var $tb = $(this);
			if(i>0){
				_execSql += " " + $($("table.edit_table_option")[i-1]).find("select").val() + " ";
				_sqlStr += " " + $($("table.edit_table_option")[i-1]).find("select option:selected").text() + " ";
			}
			
			_execSql += "(";
			_sqlStr += "(";
			$(this).find("select[name='conName']").each(function(j){
				
				if(j>0){
					_execSql += " " + $tb.find("select[name='conBool']")[j-1].value + " ";
					_sqlStr += " " + $($tb.find("select[name='conBool']")[j-1]).find("option:selected").text() + " ";
				}
				
				var _opCondition = $tb.find("select[name='conOperator']")[j].value;
				var _conValue = $tb.find("input[type='hidden'][name='conValue']")[j].value;
				var _conName = $(this).find("option:selected").text();
				var conditionName = this.value;
				if(_opCondition == "equal"){
					if("prodTitle" == conditionName){
						conditionName = "prodTitle_cp";
					}
					_execSql += conditionName + ":" + _conValue;
					
					if("pcId" == conditionName){
						_sqlStr += _conName + " 等于 " + $tb.find("input[type='text'][name='cateValue']")[j].value;
					}else{
						_sqlStr += _conName + " 等于 " + _conValue;
					}
					
				}
				
				if(_opCondition == "notEqual"){
					if("prodTitle" == conditionName){
						conditionName = "prodTitle_cp";
					}
					_execSql += "-" + conditionName + ":" + _conValue;
					
					if("pcId" == conditionName){
						_sqlStr += _conName + " 不等于 " + $tb.find("input[type='text'][name='cateValue']")[j].value;
					}else{
						_sqlStr += _conName + " 不等于 " + _conValue;
					}
				}
				
				if(_opCondition == "like"){
					if("prodTitle" == conditionName){
						_execSql += conditionName + ":" + '"' + _conValue + '"';
					}else{
						_execSql += conditionName + ":" + "*" + _conValue + "*";
					}
					_sqlStr += _conName + " 包含 " +_conValue;
				}
				
				if(_opCondition == "notLike"){
					if("prodTitle" == conditionName){
						_execSql += "-" + conditionName + ":" + '"' + _conValue + '"';
					}else{
						_execSql += "-" + conditionName + ":" + "*" + _conValue + "*";
					}
					_sqlStr += _conName + " 不包含 " +_conValue;
				}
				
			});
			_execSql += ")";
			_sqlStr += ")";
		});
		$("#execSql").val(_execSql);
		$("#sqlString").val(_sqlStr);
		
		$("#conForm").submit();
	}
	
	//条件名变化的事件
	function conNameChange(arg){
		$(arg).parent().parent().find("input.conValue").val("");
		$(arg).parent().parent().find("input[name='conValue']").val("");
		$(arg).parent().parent().find("option[value='equal']").attr("selected","selected");
		if(arg.value == "prodTitle"){
			$(arg).parent().parent().find(".conValue").hide();
			$(arg).parent().parent().find("option[value$='ike']").show();
			$($(arg).parent().parent().find(".conValue")[0]).show();
			changeOperator(arg,2);
		}
		if(arg.value == "brandName"){
			$(arg).parent().parent().find(".conValue").hide();
			$($(arg).parent().parent().find(".conValue")[1]).show();
			$(arg).parent().parent().find("option[value$='ike']").hide();
			conValueChange($(arg).parent().parent().find(".conValue")[1],1);
			changeOperator(arg,1);
		}
		if(arg.value == "pcId"){
			$(arg).parent().parent().find(".conValue").hide();
			$(arg).parent().parent().find("option[value$='ike']").hide();
			$($(arg).parent().parent().find(".conValue")[2]).show();
			changeOperator(arg,1);
		}
	}
	
	//条件值变化的事件
	function conValueChange(arg,type){
		var _text = "";
		if(type == 1){
			_text = arg.options[arg.selectedIndex].text;
		}else{
			_text = arg.value;
		}
		$(arg).siblings("input[type='hidden'][name='conValue']")[0].value = _text;
	}
	
	//改变逻辑判断语句
	function changeOperator(obj,arg){
		var _select = $(obj).parent().parent().find("select[name='conOperator']");
		$(_select).empty();
		if(arg === 1){
			$(_select).append("<option value='equal'>等于</option>");
			$(_select).append("<option value='notEqual'>不等于</option>");
		}
		if(arg === 2){
			$(_select).append("<option value='equal'>等于</option>");
			$(_select).append("<option value='notEqual'>不等于</option>");
			$(_select).append("<option value='like'>包含</option>");
			$(_select).append("<option value='notLike'>不包含</option>");
		}
	}
	
	// 类目值 Begin
	var setting = {
		view: {
			dblClickExpand: false,
			selectedMulti: false
		},
		data: {
			key: {
				name: "categoryName"
			},
			simpleData: {
				enable: true,
				idKey: "categoryId",
				pIdKey: "parentId",
				rootPId: 0
			}
		},
		callback: {
			onClick: onClick
		}
	};

	var zNodes = <s:property value="#request.categoryMap" escape="false"/>;

	var inputObj;
	
	function onClick(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
		nodes = zTree.getSelectedNodes(),
		v = nodes[0].categoryName;
		inputObj.value = v;
		var _vId = nodes[0].categoryId;
		$(inputObj).siblings("input[type='hidden'][name='conValue']")[0].value = _vId;
	}
	function showMenu(arg) {
		inputObj = arg;
		var cityObj = $(arg);
		var cityOffset = $(arg).offset();
		$("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");

		$("body").bind("mousedown", onBodyDown);
	}
	function hideMenu() {
		$("#menuContent").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown);
	}
	function onBodyDown(event) {
		if (!(event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
			hideMenu();
		}
	}
	$(document).ready(function(){
		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	});
	// 类目值  End
</script>

</body>
</html>