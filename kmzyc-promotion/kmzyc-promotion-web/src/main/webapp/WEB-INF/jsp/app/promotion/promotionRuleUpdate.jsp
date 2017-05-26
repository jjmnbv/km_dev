<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="/etc/js/qtip/jquery.min.1.8.3.js"></script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="/etc/js/dialog.js" type="text/javascript"></script>
<script type="text/javascript"  src="/etc/js/validate/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/validate/jquery.metadata.js"></script>
<script type="text/javascript"  src="/etc/js/validate/messages_cn.js"></script>
<script language="JavaScript" src="/etc/js/artDialog4.1.7/artDialog.js?skin=default" type="text/javascript"></script>
<script language="JavaScript" src="/etc/js/artDialog4.1.7/plugins/iframeTools.source.js" type="text/javascript"></script>

<title>促销活动</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/validate.css" type="text/css" rel="stylesheet">
<style type="text/css">
.tableStyle1 {
	font-size: 12px;
}
.selector ul{width:120px;margin:0;padding:0;position:absolute;left:-1px;top:20px;border:1px solid #36c;list-style:none;display:none}
.selector li a{width:120px;line-height:24px;display:block;color:#000;background:#fff;text-decoration:none;text-indent:10px}
.selector li a:hover{background:#ccc}
.selector .arr{position:absolute;right:0;top:0}
</style>
<script type="text/javascript">
   
 
	function selectCouponId(){
		dialog("选择优惠券","iframe:/common/selectCouponList.action?coupon.couponGivetypeId=3" ,"700px","500px","iframe",50);
	}
	function receiveCoupon(objs){
		if(objs.length!=1){
			alert('请选择一项！');
			return;
		}
		$("#promotionRuleCouponId").val(objs[0].value);
		$("#prizeData").val(objs[0].title+"(优惠券)");
		closeThis();
	}
	$(document).ready(function(){
        $("#frm").validate({
            rules: {
				"promotionRule.promotionRuleRuleName":{required:true,maxlength:50,unusualChar:true,checkName:true},
        		"promotionRule.promotionTypeId":{required:true}
				
            },
            messages:{
            	"promotionRule.promotionRuleRuleName":"请输入名称",
        		"promotionRule.promotionTypeId":'请选择类型'
        		//"zhekou":"请输入折扣",
				//"maimanjine":"请输入买满条件",
				//"jianmianjine":"请输入优惠的金额"
        	},
        	success: function (label){
 	            label.removeClass("checked").addClass("checked");
           	}
        });
        var tid = $("#promotionTypeId").val();
        promotionTypeChange(tid);
	});	
	jQuery.validator.addMethod("checkName", function(value, element) {
		 var id = $("#promotionRuleId").val();
		 var name = $("#promotionRuleRuleName").val();
		 var isVal = false;
		 var code = 1;
		 $.ajax({
				dataType:'json',
				type:'post',
				url:'/promotion/checkPromotionRuleName.action',
				async: false,
				data:{'promotionRule.promotionRuleId':id,'promotionRule.promotionRuleRuleName':name},
				error:function(){
					alert('请求失败，请稍后重试或与管理员联系！');
					return;
				},
			   	success:function(data){
			   		code = data.code;
			   	}
		  });
		 return code==0;
	},'该名称已经重复');
	function promotionTypeChange(type){
		if(type==10){
			$(".notSale").hide();
		}else {
			$(".notSale").show();
		}
		if(type==8){//打折
			$(".dazhe").show();
			$(".mansong").hide();
		}else if(type==6){//满额减免
			$(".dazhe").hide();
			$(".mansong").show();
			$("input[name='prizeData']").attr('readonly',false);
			$("input[name='prizeData']").unbind();
		}else if(type==4){//满额送券
			$(".dazhe").hide();
			$(".mansong").show();
			$("input[name='prizeData']").attr('readonly',true);
			$("input[name='prizeData']").bind('click',selectCouponId);
		}
	}
	function addDataSpan(obj){
		$(obj).after("<br class='mansong'><span class='mansong'>"+$(obj).prev().html()+"</span><a class='mansong' href='javascript:void(0)' onclick='removeDataSpan(this)'>-</a>");
		var type = $("#promotionTypeId").val();
		if(type==6){
			$("input[name='prizeData']").attr('readonly',false);
			$("input[name='prizeData']").unbind();
		}else if(type==4){
			$("input[name='prizeData']").attr('readonly',true);
			$("input[name='prizeData']").bind('click',selectCouponId);
		}
	}
	function removeDataSpan(obj){
		$(obj).prev().prev().remove();
		$(obj).prev().remove();
		$(obj).remove();
	}
</script>
</head>
<s:set name="parent_name" value="'促销管理'" scope="request"/>
<s:set name="name" value="'促销规则列表'" scope="request"/>
<s:set name="son_name" value="'查看促销规则'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<body>
	<s:form action="/promotion/promotionRuleSave.action" id="frm" method="POST" namespace='/promotion'>
	<s:hidden id="promotionRuleId" name="promotionRule.promotionRuleId"/>
	<!-- 数据编辑区域 -->
	<table width="95%" class="edit_table" align="center" cellpadding="3"
		cellspacing="0" border="1" bordercolor="#C7D3E2"
		style="border-collapse: collapse; font-size: 12px;">
		<tr>
			<th colspan="2" align="left" class="edit_title">基本信息</th>
		</tr>
		<tr>
			<th align="right" width="30%" class="eidt_rowTitle">规则名称：</th>
			<td  width="68%" ><label> <s:property value="promotionRule.promotionRuleRuleName"/></label>
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle">活动类别：</th>
			<td><s:property value="%{#request.promotionTypeMap[promotionRule.promotionTypeId]}"/>
			</td>
		</tr>
		<s:if test="promotionRule.promotionTypeId!=2">
		<tr>
			<th align="right" class="eidt_rowTitle">规则数据：</th>
			<td><s:iterator value="promotionRule.promotionRuleDataList" var="dataObj" >
				<s:if test="promotionRule.promotionTypeId==10">价格<s:property value="prizeData"/></s:if>
				<s:elseif test="promotionRule.promotionTypeId==8">打<s:property value="prizeData"/>折</s:elseif>
				<s:elseif test="promotionRule.promotionTypeId==6">满<s:property value="meetData"/>元
				减<s:property value="prizeData"/>元</s:elseif>
				<s:elseif test="promotionRule.promotionTypeId==5">满<s:property value="meetData"/>元
				换购id为<a title="点击预览" href="javascript:;" onclick="javascript:queryPromotionProductList(<s:property value='prizeData'/>)"><s:property value="prizeData"/>（加价购组合）</a></s:elseif>
				<s:elseif test="promotionRule.promotionTypeId==4">满<s:property value="meetData"/>元
				送id为<a title="点击预览" href="/app/queryCouponDetail.action?viewType=show&couponId=<s:property value='prizeData'/>"><s:property value="prizeData"/></a>（优惠券）</s:elseif>
				<s:elseif test="promotionRule.promotionTypeId==3">满<s:property value="meetData"/>元
				赠skuId为<a title="点击预览" href="javascript:;" onclick="javascript:toProductSku(<s:property value='prizeData'/>)"><s:property value="prizeData"/>（商品）</a></s:elseif>
			;</s:iterator>
			</td>
		</tr>
		</s:if>
		<s:elseif test="promotionRule.promotionTypeId==2">
		<tr><th align="right" class="eidt_rowTitle">规则数据：</th>
		<td>表达式:<s:property value="promotionRule.promotionRuleExpression"/></td></tr>
		</s:elseif>
		<tr>
			<th align="right" class="eidt_rowTitle">规则说明：</th>
			<td>
			<s:property value="promotionRule.promotionRuleExplain" />
			</td>
		</tr>
	</table>

	<!-- 底部 按钮条 -->
	<table width="98%" align="center" class="edit_bottom" height="30"
		border="0" cellpadding="0" cellspacing="0" style="font-size: 12px;">
		<tr>
			<td align="center">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
				<input type="button" class="backBtn" onClick="javascript:history.back(-1);" />
			<td width="20%" align="center">
			<td width="20%" align="center">
			</td>
		</tr>
	</table>

	<br>
	<br>
	</s:form>
</body>
<script language="javascript">
var valueArray = new Array();
function editable(select1){
	var newvalue ;
    if(select1.value == ""){
    	newvalue = prompt("输入数字","");
    } else if(select1.value!='-1'){
    	newvalue = select1.value;
    }
    if(newvalue){
	    addValue(newvalue);
	}
 }
 
 function addValue(newvalue){
  	$("#promotionRuleExpression").val($("#promotionRuleExpression").val()+newvalue);
  	valueArray.push($("#promotionRuleExpression").val());
 }
 function chexiao(){
	 if(valueArray.length==0)return;
	valueArray.pop();
	var length =valueArray.push();
	$("#promotionRuleExpression").val(valueArray[length-1]);
 }
 /**  查看加价购组合商品  **/
 function queryPromotionProductList(promotionId){
 	var url ="/promotion/queryPromotionProductList.action?promotionProduct.promotionId="+promotionId+"&promotion.promotionId="+promotionId;
 	art.dialog.data("readOnly", true);
 	myDialog = art.dialog.open(url, {
		   title: '查看加价购商品',
		   width:870,
			height:450,
			drag:false,
			 lock: true
	   });
	}
 function toProductSku(skuId){
	$.ajax({
		dataType:'json',
		url:'/app/previewProductInfoPage.action?skuId='+skuId,
		error:function(){alert('请求失败，请稍后重试或与管理员联系！');},
		success:function(date){
			var pageUrl = date.pageUrl;
			if(pageUrl!=null && pageUrl!=""){
				window.open(pageUrl, "_blank");
			}else{
				alert('预览失败!');
			}
		}
	});
 }
 
 </script>
</html>

