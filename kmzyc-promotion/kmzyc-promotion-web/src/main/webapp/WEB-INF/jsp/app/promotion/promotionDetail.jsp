<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="/etc/js/qtip/jquery.min.1.8.3.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css">

<script language="JavaScript" src="/etc/js/artDialog4.1.7/artDialog.js?skin=default" type="text/javascript"></script>
<script language="JavaScript" src="/etc/js/artDialog4.1.7/plugins/iframeTools.source.js" type="text/javascript"></script>


<script type="text/javascript"  src="/etc/js/validate/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/validate/jquery.metadata.js"></script>
<script type="text/javascript"  src="/etc/js/validate/messages_cn.js"></script>
<script type="text/javascript"  src="/etc/js/promotion.js"></script>
<title>促销活动</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/validate.css" type="text/css" rel="stylesheet">

<style type="text/css">
.tableStyle1 {
	font-size: 12px;
}
.emDiv,.sbDiv{
float: left;position:relative;margin:3px 5px 2px 0;
white-space:nowrap;height:15px;line-height: 15px;
cursor:pointer;border-radius:17px;border-style:
solid;border-width:1px;font-size:14px;
padding:2px 19px;border-color:#edb8b8;
background-color:#ffeaea;color:#c30!important;
display:inline-block;vertical-align:middle;
}

em{
margin-left:-8px;vertical-align:top;display:inline-block;font-style:normal;
text-decoration:none;white-space:nowrap;line-height:15px;cursor:pointer;font-size:14px;
}
.aclose,.deleteP{position: absolute;right: -2px;top: -1px;text-decoration: none;font-family: verdana;border-radius: 0 17px 17px 0;
font-weight: bold;padding: 2px 5px 2px 3px;border-width: 1px;border-style: solid;border-color:#edb8b8!important;color:#c30!important;}

</style>
</head>
<s:set name="parent_name" value="'促销管理'" scope="request"/>
<s:set name="name" value="'活动管理'" scope="request"/>
<s:set name="son_name" value="'查看促销活动'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<body>
	<s:form action="/promotion/promotionAddOrUpdate.action"	method="POST" namespace='/promotion' id="frm">
	<s:hidden name="promotion.promotionId" id="promotionId"></s:hidden>
	<s:hidden name="promotion.promotionTypeId" id="promotionTypeId"></s:hidden>
	<input type="hidden" value='<s:property value="promotion.productFilterType"/>' id="productFilterTypeDB"/>
	<input type="hidden" value='<s:property value="promotion.productFilterSql"/>' id="productFilterSqlDB"/>
	<input type="hidden" value='<s:property value="operationType"/>' id="operationType"/>
	<s:hidden name="promotion.promotionFilterProductName" id="promotionFilterProductName"></s:hidden>
	<!-- 数据编辑区域 -->
	<table width="95%" class="edit_table" align="center" cellpadding="3"
		cellspacing="0" border="1" bordercolor="#C7D3E2"
		style="border-collapse: collapse; font-size: 12px;">
		<!-- error message -->
		<s:if test="rtnMessage != null">
			<tr>
				<td colspan="2" align="center"><font color="red"><s:property
					value='rtnMessage' /></font></td>
			</tr>
		</s:if>
		<tr>
			<th colspan="2" align="left" class="edit_title">基本信息</th>
		</tr>
		<tr>
			<th width="20%" align="right" class="eidt_rowTitle">所属商家：</th>
		  <td width="80%">
            	${promotion.shopNames}
		  </td>
		</tr>
		<tr>
			<th width="20%" align="right" class="eidt_rowTitle">活动类型：</th>
		  <td width="80%">
            	<s:property value='#request.promotionTypeMap[promotion.promotionType]' />
		  </td>
		</tr>
		<tr>
			<th width="20%" align="right" class="eidt_rowTitle">优惠数据：</th>
		  	<td width="80%" id="ruleData" value="xx">
		  	<input type="hidden" value="<s:property value="ruleData"/>" id ="ruleDataValue"/>
		  	<input type="hidden" value="<s:property value="promotion.promotionType"/>" id ="promotionType"/>
            	<s:if test="null== promotion.promotionNote || ''==promotion.promotionNote">
	            	<s:iterator value="promotion.ruleDatas" var="dataObj" >
					<s:if test="promotion.promotionType==6">
					满<s:property value="meetData"/><s:if test="meetDataType==1">
					元
					</s:if>
					<s:elseif test="meetDataType==2">
					件
					</s:elseif>  ，减<s:property value="prizeData"/>元
					</s:if>
					<s:elseif test="promotion.promotionType==4">
					满<s:property value="meetData"/><s:if test="meetDataType==1">
					元
					</s:if>
					<s:elseif test="meetDataType==2">
					件
					</s:elseif>  ，
					送id为<a title="点击预览" href="/coupon/couponRule_pageShow.action?viewType=show&couponId=<s:property value='entityId'/>"><s:property value="entityId"/></a>（优惠券）
					</s:elseif>
					；
					</s:iterator>
					
				</s:if>
            	<s:else>
            	
            		<s:property value='promotion.promotionNote' />
				</s:else>
		  	</td>
		</tr>
		<tr>
			<th width="20%" align="right" class="eidt_rowTitle">活动标题：</th>
		  <td width="80%">
             <s:property value='promotion.promotionTitle' />
		  </td>
		</tr>
		<tr>
			<th width="20%" align="right" class="eidt_rowTitle">前端显示名称：</th>
		  <td width="80%">
             <s:property value='promotion.promotionName' />
		  </td>
		</tr>
		
		<tr>
			<th align="right" class="eidt_rowTitle">广告语：</th>
			<td><label><s:property value='promotion.slogan' /></label></td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle">优先级</th>
			<td>
			<s:property value="promotion.promotionPriority" />
			</td>
		</tr>
	  <tr>
			<th align="right" class="eidt_rowTitle">活动时间：</th>
			<td>从<input name="promotion.startTime" id="startTime" disabled="true"
					type="text" class="input_style" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d 0:0:0'})" 
					value="<s:date name='promotion.startTime'  format='yyyy-MM-dd HH:mm:ss'/>"/> 
			到
			<input name="promotion.endTime" id="endTime"disabled="true"
					type="text" class="input_style" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d 0:0:0'})" 
					value="<s:date name='promotion.endTime' format='yyyy-MM-dd HH:mm:ss'/>"/>
			</td>
		</tr>
	  <tr>
		 <th align="right" class="eidt_rowTitle">筛选商品类型：</th>
		  <td>
		  <s:if test="promotion.productFilterType==2">
		   <s:radio  disabled="true" name="promotion.productFilterType" id="productFilterType"
			  list="#{2:'指定商品'}"/> 
		  </s:if>
		  <s:elseif test="promotion.productFilterType==1">
		  <s:radio  disabled="true" name="promotion.productFilterType" id="productFilterType"
			  list="#{1:'全场'}"/>
			 </s:elseif>
			  <s:elseif test="promotion.productFilterType==3">
		  <s:radio  disabled="true" name="promotion.productFilterType" id="productFilterType"
			  list="#{3:'商品类目'}"/>
			  <br>
			  <input readonly id="productFilterSqlName" value="" size="80"></input>
			  </s:elseif>
			  <s:elseif test="promotion.productFilterType==4">
		  <s:radio  disabled="true" name="promotion.productFilterType" id="productFilterType"
			  list="#{4:'商品品牌'}"/>
			  <br>
			  <input readonly id="productFilterSqlName" value="" size="80"></input>
			  </s:elseif>
		  
	   	<br>   
	   	                    
		
   		 <s:hidden name="promotion.productFilterSql" id="productFilterSql" />
	  	</td>
	  </tr>
	  <s:if test="promotion.productFilterType==2">
	   <tr>
			<th align="right" class="eidt_rowTitle">活动商品</th>
			<td>
			<INPUT TYPE="button" class="btn-custom button-2s" value="查看" onClick="queryPromotionProductList(<s:property value='promotion.promotionId' />)" />
			</td>
		</tr>
		</s:if>
		<s:else>
		<th align="right" class="eidt_rowTitle">例外商品</th>
			<td>
			<INPUT TYPE="button" class="btn-custom button-2s" value="查看" onClick="queryPromotionExceptionProductList(<s:property value='promotion.promotionId' />)" />
			</td>
		</s:else>
		<tr>
			<th align="right" class="eidt_rowTitle">活动描述：</th>
			<td><label> <s:property value="promotion.promotionDescribe"/></label></td>
		</tr>
		
		<tr><td></td>
			<td align="left">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
				<input type="button" class="backBtn" onClick="goBack();" />
				</td>
		</tr>
	</table>
</s:form>
</body>
<script type="text/javascript">
    //返回
    function goBack(){
    	var operationType = $("#operationType").val();
    	var id = $("#promotionId").val();
    	if(operationType==0){
    	window.location.href = "/promotion/queryPromotionList.action?promotion.promotionId="+id;
    	}
    	else{
    		window.location.href = "/promotion/checkPromotionList.action?promotion.promotionId="+id;
    	}
    	
    }
    
	/**  查看商品  **/
    function queryPromotionProductList(promotionId){
    	art.dialog.data("readOnly", true);
    	var url ="/promotion/queryPromotionProductList.action?promotionProduct.promotionId="+promotionId+"&promotion.promotionId="+promotionId;
    	 myDialog = art.dialog.open(url, {
  		   title: '查看促销活动商品',
  		   width:870,
  			height:450,
  			drag:false,
  			 lock: true
  	   });
	}
    /**  查看例外商品  **/
    function queryPromotionExceptionProductList(promotionId){
    	art.dialog.data("readOnly", true);
    	var url ="/promotion/queryPromotionExceptionProductList.action?promotionProduct.promotionId="+promotionId+"&promotion.promotionId="+promotionId;
    	 myDialog = art.dialog.open(url, {
  		   title: '查看促销活动商品',
  		   width:870,
  			height:450,
  			drag:false,
  			 lock: true
  	   });
	}
    
	function queryMutalPromotionList(ids){
		if(ids=='all')return;
		//ids = ids.substring(1,ids.length-1);
		//ids = "("+ids+")";
		art.dialog.data("readOnly", true);
		art.dialog.data("selectedIds", ids);
		var url = "/promotion/selectConflictPromotion.action?promotion.promotionIds="+ids;
   	    myDialog = art.dialog.open(url, {
 		   title: '查看互斥活动',
 		   width:870,
 			height:600,
 			drag:false,
 			 lock: true
 	   });
	}
	var gotoRule = function(){
		var id = $("#promotionRuleId").val();
		if(id){
		  window.location.href="/promotion/gotoPromotionRuleAddOrUpdate.action?promotionRule.promotionRuleId="+id;
		}else{
			alert("你还没选择规则!");
		}
		
	};
	
	

	 function shopHideFilterSqlName(){
			$("#productFilterSqlName").hide();
			$(".emDiv").remove();
			$(".aclose").remove();
		}
	 function hideFilterSqlName(){
			shopHideFilterSqlName();
			
		}
	 var  showFilterSqlName = function(filterSqlName){
			var names = filterSqlName.val();
			var nameArray = names.split(",");
			var html = "";
			for(var i=0;i<nameArray.length;i++){
				if(nameArray[i]==""||nameArray[i]==null){
					continue;
				}
				var nameIdArray = nameArray[i].split(":");
				html = html + spellHTML(nameIdArray[0],nameIdArray[1]);
				}
			html = html;
			filterSqlName.before(html);
		};
		var spellHTML=function(id,title){
			var html = '<div class="emDiv"><em unselectable="on">'+title+'</em>'+
			 '</div>';
			return html;
		};
		$(document).ready(function(){
			var promotionType = $("#promotionType");
			var promotionFilterProductName =  $("#promotionFilterProductName").val();  
	 		showPromotionFilterProductName(promotionFilterProductName);
	 		
	 		if(promotionType.val()==5||promotionType.val()==3||promotionType.val()==11){
	 		var ruleData = $("#ruleDataValue").val();
	 		$("#ruleData").html(ruleData);
	 		}
	 		
	 		var promotionTypeId = $("#promotionTypeId");
	 		var promotionId = $("#promotionId").val();
	 		if(promotionId){
	 			$('#promotionRuleName').attr("disabled",true);
	 		}
	 		
	 		if(promotionTypeId.val()==10){
	 			 $(':radio[value="2"]').attr("checked",true);
	 			 $(':radio[name="promotion.productFilterType"]').attr("disabled",true);
	 			 //$("#productFilterSqlName").val('');
	 		}
	 		var filterSqlType = $(':radio[name="promotion.productFilterType"]:checked');
	 		//radio_click(filterSqlType.val());
	 	});
		function showPromotionFilterProductName(name){
			var nameArray = name.split(",");
			var promotionFilterProductName = "";
			for(var i=0;i<nameArray.length;i++){
				if(nameArray[i]==""||nameArray[i]==null){
					continue;
				}
				var nameIdArray = nameArray[i].split(":");
				promotionFilterProductName = promotionFilterProductName + nameIdArray[1]+",";
			}
			
			$("#productFilterSqlName").val(promotionFilterProductName);
		}
		
</script>
</html>

