<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="/etc/js/jquery-latest.pack.js"></script>
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
   
	$(document).ready(function(){
		var test = $("input[name='prizeData1']").attr('readonly');
		var test2 = $("input[name='meetData1']").attr('readonly');
		//alert(test);
		//alert(test2);
        initValidator();
        var tid = $("#promotionTypeId").val();
        promotionTypeChange(tid);
	});
	var initValidator = function(){
		$("#frm").validate({
            rules: {
				"promotionRule.promotionRuleRuleName":{required:true,maxlength:100,checkName:true},
        		"promotionRule.promotionTypeId":{required:true},
        		"promotionRule.promotionRuleExplain":{maxlength:80},
        		"discount":{checkDiscount:true},
        		"meetData1":{checkMeetData:true},
        		"salePrice":{checkPrizeData:true},
        		"prizeData1":{checkPrizeData:true}
				
            },
            messages:{
            	//"promotionRule.promotionRuleRuleName":"请输入名称",
        		"promotionRule.promotionTypeId":'请选择类型'
        		//"zhekou":"请输入折扣",
				//"maimanjine":"请输入买满条件",
				//"jianmianjine":"请输入优惠的金额"
        	},
        	success: function (label){
 	            label.removeClass("checked").addClass("checked");
           	}
        });
	};
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
					return false;
				},
			   	success:function(data){
			   		code = data.code;
			   	}
		  });
		 return code==0;
	},'该名称已经重复');
	jQuery.validator.addMethod("checkDiscount",function checkDiscount(value, element){
		var prep = $(element).parent(); 
		if(prep.css('display')=='none'||prep.css('display')==''){
			return true;
		}
		if(value){
			//var re=/^(\d{0,1})(\.{0,1}\d{0,1}?)$/;
			//var re=/^\d{0,1}(\.{0,1}\d{0,1})$/;
			var re=/^[1-9]([.][1-9])?$/;
			return re.test(value);
		}else{
			return false;
		}
		
	});
	jQuery.validator.addMethod("checkMeetData",function checkMeetData(value, element){
		
		var prep = $(element).parent(); 
		if(prep.css('display')=='none'||prep.css('display')==''){
			return true;
		}
		if(value){
			var re=/^[1-9]\d{0,7}([.][0-9]\d{0,1})?$/;
			return re.test(value);
		}else{
			return false;
		}
	});
	jQuery.validator.addMethod("checkPrizeData",function checkPrizeData(value, element){
		var prep = $(element).parent(); 
		if(prep.css('display')=='none'||prep.css('display')==''){
			return true;
		}
		var type = $("#promotionTypeId").val();
		if(type==10){//特价
			if(value){
				var re=/^[0-9]\d{0,6}([.][0-9]\d{0,1})?$/;
				return re.test(value);
			}
			return true;
		}
		if(value){
			if(type==6){//满额减免
				var re=/^[1-9]\d{0,6}([.][0-9]\d{0,1})?$/;
				return re.test(value);
			}
			return true;
		}else{
			return false;
		}
		
	});
	
	function promotionTypeChange(type){
		//$("input[name^='prizeData']").attr('readonly',false).val('');
		if(type==10){
			$(".salePrice").show();
			$(".dazhe").hide();
			$(".mansong").hide();
			$(".biaodashi").hide();
		}else
		if(type==8){//打折
			$(".salePrice").hide();
			$(".dazhe").show();
			$(".mansong").hide();
			$(".biaodashi").hide();
		}else if(type==6){//满额减免
			$(".salePrice").hide();
			$(".dazhe").hide();
			$(".mansong").show();
			$(".biaodashi").hide();
			$("input[name^='prizeData']").attr('readonly',false).val('');
		}else if(type==5){//换购
			$(".salePrice").hide();
			$(".dazhe").hide();
			$(".mansong").show();
			$(".biaodashi").hide();
			//$("a .mansong").hide();
			$("a[class='mansong']").hide();
			$("input[name^='prizeData']").attr('readonly',false).val('');
			//$('tr.notSale').on('click',"input[name^='prizeData']",selectIncrease);
		}else if(type==4){//满额送券
			$(".salePrice").hide();
			$(".dazhe").hide();
			$(".mansong").show();
			$(".biaodashi").hide();
			$("input[name^='prizeData']").attr('readonly',true).val('');
			//$("input[name^='prizeData']").bind('click',selectCouponId);
			//$('tr.notSale').on('click',"input[name^='prizeData']",selectCouponId);
		}else if(type==3){//满赠
			$(".salePrice").hide();
			$(".dazhe").hide();
			$(".mansong").show();
			$(".biaodashi").hide();
			$("input[name^='prizeData']").attr('readonly',true).val('');
		}else if(type==2){//其它
			$(".salePrice").hide();
			$(".dazhe").hide();
			$(".mansong").hide();
			$(".biaodashi").show();
		}
	}
</script>
</head>
<s:set name="parent_name" value="'促销管理'" scope="request"/>
<s:set name="name" value="'促销规则列表'" scope="request"/>
<s:set name="son_name" value="'添加促销规则'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<body>
	<s:form action="/promotion/promotionRuleSave.action" id="frm" method="POST"  namespace='/promotion'>
	<s:hidden id="promotionRuleId" name="promotionRule.promotionRuleId"/>
	<!-- 数据编辑区域 -->
	<table width="95%" class="edit_table" align="center" cellpadding="3"
		cellspacing="0" border="1" bordercolor="#C7D3E2"
		style="border-collapse: collapse; font-size: 12px;">
		<tr>
			<th colspan="2" align="left" class="edit_title">基本信息</th>
		</tr>
		<tr>
			<th align="right" width="30%" class="eidt_rowTitle"><font color="red">*</font>规则名称：</th>
			<td  width="68%" ><label> <s:textfield name="promotionRule.promotionRuleRuleName"
				id="promotionRuleRuleName" size="100" maxlength="100" style="width:400px"/> </label>
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle"><font color="red">*</font>活动类别：</th>
			<td><s:select list="%{#request.promotionTypeMap}" listKey="key" listValue="value" 
				id="promotionTypeId" name="promotionRule.promotionTypeId" headerKey="" headerValue="请选择"
				 onchange="promotionTypeChange(this.value)"></s:select>
			</td>
		</tr>
		<tr class="notSale">
			<th align="right" class="eidt_rowTitle">规则数据：</th>
			<td >
			<span class="salePrice" style="display:none;">
			价格
			<s:textfield name="salePrice" id="salePrice" size="5" 
			 maxlength="10" title="请输入特价统一的价格，如果没有可以不输。"/>
			</span>
			<span class="dazhe" style="display:none;">
			折扣
			<s:textfield name="discount" id="discount" size="5" 
			 maxlength="128" title="请输入小于10的整数或者小数，如8表示8折。"/>
			</span>
			<span class="mansong" style="display:none;">
			满
			<s:textfield name="meetData1" 
			id="meetData1" size="5" maxlength="128" title="请输入正确的买满金额。"/>
			送/减/换购
			<s:textfield name="prizeData1" title="请输入正确的优惠数据。"onclick="javascript:selectPrice(this);"
			id="prizeData1" size="5" onchange="javascript:moneyChange(this)"
			maxlength="128"/>
			<input type="hidden" name="prizeData1prizeData1" id="prizeData1prizeData1" />
			<a  class='mansong' href="javascript:;" onclick="addDataSpan(this)">增加</a>&nbsp;&nbsp;
			<a class='mansong' href='javascript:void(0)' onclick='removeDataSpan()'>移除</a>
			</span>
			<span class="biaodashi" style="display:none;">
				表达式<s:textfield name="promotionRule.promotionRuleExpression" 
				id="promotionRuleExpression" size="32" maxlength="32"/>
			</span>
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle">规则说明：</th>
			<td>
			<s:textfield name="promotionRule.promotionRuleExplain" id="promotionRuleExplain" size="80" 
				maxlength="80"/>
			</td>
		</tr>
	</table>

	<!-- 底部 按钮条 -->
	<table width="98%" align="center" class="edit_bottom" height="30"
		border="0" cellpadding="0" cellspacing="0" style="font-size: 12px;">
		<tr>
			<td align="center"><INPUT class="saveBtn" TYPE="submit"
				value="">
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
var myDialog = null;
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
 var returnId = "";
 
 function addDataSpan(obj){
		var c = $("input[name^='meetData']").length;
		if(c==5)return;
		c = c+1;
		var one = '满 <input type="text" name="meetData'+c+'" size="5" maxlength="128" value="" id="meetData'+c+'" title="请输入正确的买满金额。"/>'+
		' 送/减/换购 <input type="text" name="prizeData'+c+'" size="5" maxlength="128" value="" id="prizeData'
		+c+'" title="请输入正确的优惠数据。" onclick="javascript:selectPrice(this);"/><input type="hidden" name="prizeData'
		+c+'prizeData'+c+'" id="prizeData'+c+'prizeData'+c+'"/>';
		$('span.mansong:last').after("<br class='mansong'><br class='mansong'><span class='mansong'>"+one+
				"</span>");
		$("#meetData"+c).rules("add", {required: true,checkMeetData:true});
		$("#prizeData"+c).rules("add", {required: true,checkPrizeData:true});
		var type = $("#promotionTypeId").val();
		if(type==6){//满额减免
			$("#prizeData"+c).attr('readonly',false);
			$("#prizeData"+c).unbind();
		}else if(type==4||type==3){//满额送券,满额赠品,
			$("#prizeData"+c).attr('readonly',true);
		}
	}
	function removeDataSpan(){
		var c = $("input[name^='meetData']").length;
		if(c==1)return;
		var nam = "input[name='meetData"+c+"']";
		//;
		$(nam).parent().prev().prev().remove();
		$(nam).parent().prev().remove();
		$(nam).parent().remove();
		
	}
	function selectIncrease(){
		 myDialog = art.dialog.open('/promotion/selectIncrease.action', {
			   title: '选择加价购组合',
			   width:700,
				height:500,
				drag:false,
				 lock: true
		   });
	}
	function receiveIncrease(id,name){
		$("#"+returnId+returnId).val(id);
		$.ajax({
			url:'/promotion/checkIncrease.action',
			dataType:'json',
			data:{'promotionId':id},
			async: false,
			success:function(json){
				if(json.isSuccess==true){
					$("#"+returnId).val(name+"(加价购组合)");
					myDialog.close();
				}else{
					alert("该组合商品中加价为空或者加价为负数");
					return;
				}
			},
			error:function(){
				alert("系统异常，请联系管理员");
				return;
			}
			
		});
		
	}
	function receiveCoupon(objs){
		if(objs.length!=1){
			alert('请选择一项！');
			return;
		}
		$("#"+returnId+returnId).val(objs[0].value);
		$("#"+returnId).val(objs[0].title+"(优惠券)");
		closeThis();
	}
	 function selectProduct(){
		 art.dialog.data("promotionType",5);
		 myDialog = art.dialog.open('/promotion/findAllProductSku.action', {
			   title: '选择商品',
			   width:800,
				height:700,
				drag:false,
				 lock: true
		   });
	 }
	function selectPrice(obj){
		//alert("d");
		var type = $("#promotionTypeId").val();
		returnId = $(obj).attr("id");
		
		//alert(type);
		switch (type) {
		case '10'://特价
			break;
		case '8'://打折
			break;
		case '6'://满减
			break;
		case '5'://换购
			selectIncrease();
			break;
		case '4'://送券
			//selectCouponId();
	 		
			dialog("选择优惠券","iframe:/common/selectCouponList.action?coupon.couponGivetypeId=3" ,"700px","500px","iframe",50);
			break;
		case '3'://满赠
			selectProduct();
			break;
		default:
			break;
		}
	}
	
	function moneyChange(obj){
		var type = $("#promotionTypeId").val();
		returnId = $(obj).attr("id");
		$("#"+returnId+returnId).val($(obj).val());
	}
 </script>
</html>

