<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="/etc/js/qtip/jquery.min.1.8.3.js"></script>
<script type="text/javascript" src="/etc/js/common.js"></script>

<title>促销活动规则列表</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">

<style type="text/css">
.tableStyle1 {
	font-size: 12px;
}

</style>
<script type="text/javascript">
  var gotoAdd = function(){
	  window.location.href="/promotion/gotoPromotionRuleAddOrUpdate.action";
  };
  var gotoUpdate = function(id){
	  window.location.href="/promotion/gotoPromotionRuleAddOrUpdate.action?promotionRule.promotionRuleId="+id;
  };
  function deleteOnePromotion(o){
	$('input[name="promotionId"]').attr("checked",false);
	$(o).parent().parent().children().children().attr("checked",true);
	del_sect();
  }
  var del_sect = function(){
	  var ids ="";
	  $('input[name="promotionId"]:checked').each(function(){ 
		   ids += ($(this).val())+"-";
	  });
	 //alert(ids);
	 //return;
	  if(ids&&confirm('已在活动中使用规则无法删除，是否确认删除选定的规则？')){
		  $.ajax({
				dataType:'json',
				type:'post',
				url:'/promotion/deletePromotionRule.action',
				data:{'promotionRuleIds':ids},
				error:function(){
					alert('请求失败，请稍后重试或与管理员联系！')
				},
			   	success:function(data){
			   		var code = data.code;
			   		switch (code) {
			   			case 0:
			   				var title = data.title;
			   				alert(title);
			   				window.location.reload();
			   			break;
			   			default :
			   				alert('删除失败！');
			   			break;
			   		}
			   	}
		  });
	  }else{
		  
	  }
  }
function update(obj,id){
	var pSpan=$(obj).prev();
  	var pInput=$(obj).prev().prev();
  	var isShow =pInput.css('display');
  	if(isShow=='none'){
  		pInput.val(pSpan.html());
  		pInput.css({'display':'inline'});
  		pSpan.css({'display':'none'});
  		pInput.focus();
  		return;
  	}else{
  		saveNum(pSpan,pInput,id);
  	}
}
function saveNum(pSpan,pInput,id){
var num = pInput.val();
if(num==''){
	pInput.focus();
	return;
}
if(num==pSpan.html()){
	pInput.css({'display':'none'});
		pSpan.html(num);
		pSpan.css({'display':'inline'});
}else{
	$.ajax({
		url:'/promotion/updatePromotionRulePriority.action',
		type:'post',
		dataType:'json',
		data:{'promotionRule.promotionRuleId':id,'promotionRule.promotionRulePriority':num},
		success:function(data){
			var code = data.code;
			if(code==0){
				pInput.css({'display':'none'});
	    		pSpan.html(num);
	    		pSpan.css({'display':'inline'});
			}else{
				alert(data.title);
				pInput.focus();
				return;
			}
		},
		error:function(){
			alert('操作失败请联系管理员');
		}
	});
}
}
document.onkeydown=function(event){
    var e = event || window.event || arguments.callee.caller.arguments[0];
    if(e && e.keyCode==27){ // 按 Esc要做的事情
    	var pInput = document.activeElement;
    	if(pInput.name=="promotionRulePriority"){
    		leaveUpdate(pInput);
    	 }
    }
    if(e && e.keyCode==113){ // 按 F2要做的事情
   		
    }            
     if(e && e.keyCode==13){
    	 // enter 键要做的事情
    }
}; 

</script>
</head>
<s:set name="parent_name" value="'促销管理'" scope="request"/>
<s:set name="name" value="'促销规则列表'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<body>
	<s:form name="queryPromotionRuleForm" method="post"
		action="/promotion/queryPromotionRuleList.action">
		<table width="98%" align="center" class="topbuttonbar" height="30"
			border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="90%" valign="middle"><input class="addBtn"
					type="button" value="" onClick="gotoAdd();"> <input
					class="delBtn" type="button" value="" onClick="del_sect();">
				</td>
				<td width="10%" align="center">
					<!--a href="#" onclick="gotoList();">>&nbsp;返回&nbsp;</a-->
				</td>
			</tr>
		</table>

		<!-- 查询条件区域 -->
		<table width="98%" class="searcharea" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td align="right">规则名称：</td>
				<td><s:textfield name="promotionRule.promotionRuleRuleName" id="promotionRuleRuleName" type="text" class="input_style"
					/>
					</td>
				<td align="right">活动类型：
					<s:select  list="%{#request.promotionTypeMap}"  headerKey="" headerValue="全部" listKey="key" listValue="value" name="promotionRule.promotionTypeId"></s:select>
				</td>
				<td align="center"><INPUT TYPE="submit" class="queryBtn"
					value=""></td>
			</tr>
		</table>


		<!-- 数据列表区域 -->
		<table width="98%" class="list_table" align="center" cellpadding="3"
			cellspacing="0" border="1" bordercolor="#C1C8D2">
			<tr>
				<th align="center" width="5%"><input type='checkbox'
					name='allbox' onclick='checkAll(this)'></th>
				<th align="center">规则名称</th>
				<th align="center">规则类别</th>
				<th align="center">表达式说明</th>
				<!--<th align="center">规则优先级</th>  -->
				<th align="center">操作</th>
			</tr>
			<s:iterator  value="page.dataList">
				<tr onMouseOver="this.style.backgroundColor='#def2fa'"
				onMouseOut="this.style.backgroundColor='#FFFFFF'">
					<td align="center" width="5%"><input type="checkbox"
						name="promotionId" value='<s:property value="promotionRuleId"/>' /></td>
					<td align="center"><s:property value="promotionRuleRuleName"/></td>
					<td align="center"><s:property value="#request.promotionTypeMap[promotionTypeId]" /></td>
					
					<td align="center"><s:property value="promotionRuleExplain" /></td>
					<%-- 
					<td align="center">
					
					<input title='按Esc退出修改' 
					style="display:none" size="2" onkeyup="value=value.replace(/[^\d]/g,'') " 
					value="<s:property value='promotionRulePriority' />" id="promotionRulePriority" name="promotionRulePriority" />
					<span id="noEditpricrity" style="display:inline"><s:property value="promotionRulePriority" /></span>&nbsp;
					<img TYPE="button" style="cursor: pointer;" title="点击修改或者保存"
					 src="/etc/images/icon_modify.png" onclick="javascript:update(this,<s:property value='promotionRuleId'/>);"/>
					</td>
					--%>
					<td align="center">
						<img TYPE="button"   style="cursor: pointer;" title="查看" value="查看 " src="/etc/images/button_new/view.png"
						onClick="gotoUpdate(<s:property value='promotionRuleId'/>)">&nbsp;&nbsp;
						<img TYPE="button"  style="cursor: pointer;" title="删除" value="删除" src="/etc/images/button_new/delete.png"
						onClick="deleteOnePromotion(this)">&nbsp;&nbsp;
					</td>
				</tr>
			</s:iterator>
		</table>

		<!-- 分页按钮区 -->
		<table width="98%" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td><%@ include file="/WEB-INF/jsp/public/pager.jsp"%>
				</td>
			</tr>
		</table>
	</s:form>
</body>
</html>

