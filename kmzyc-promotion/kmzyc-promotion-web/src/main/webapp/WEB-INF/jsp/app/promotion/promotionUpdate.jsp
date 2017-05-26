<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="/etc/js/jquery-latest.pack.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css">

<script language="JavaScript" src="/etc/js/artDialog4.1.7/artDialog.js?skin=default" type="text/javascript"></script>
<script language="JavaScript" src="/etc/js/artDialog4.1.7/plugins/iframeTools.source.js" type="text/javascript"></script>
<script language="JavaScript" src="/etc/js/dialog.js" type="text/javascript"></script>

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
#product{
	
	
	
}

.sbDiv,.emDiv{
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
<script type="text/javascript">
var myDialog;
   function radio_click(type){
	   if(type!=2){
		   $("#exceptionProduct").show();
		  
	   }else{
		   $("#exceptionProduct").hide();
		   
	   }
	   
	   $("#productFilterNames").html("<br/>");
	   var dbFilterType = $("#productFilterTypeDB").val();
	   var dbPromotionFilterProductName = $("#promotionFilterProductName").val();
	   $("#productFilterSql").val("");
	   if(type==1||type==2){
		   $("#selectEm").attr("disabled","disabled");
		   $("#emDiv").css("display","none");
		}else {
			$("#selectEm").removeAttr("disabled");
			$("#emDiv").css("display","block");
			if(type==dbFilterType){
				$("#productFilterSql").val($("#productFilterSqlDB").val());
				showFilterSqlName(dbPromotionFilterProductName,"emDiv","aclose","'productFilterSql'");
			}else{
				
			}
			
		}
   }
   function selectProductFilter(){
		var type = $("input[name='promotion.productFilterType']:checked").val();
		if(type==1||type==2){
			 return;
		}else if(type==3){//选择商品类别
			selectProductType();
		}else if(type==4){//选择品牌
			selectProductBrand();
		}
		
	}
	function selectProductType(){
		art.dialog.data("selectedIds", $("#productFilterSql").val());
		myDialog = art.dialog.open('/common/queryCategoryListByPromotion.action?type=phy', {
			   title: '选择商品类目',
			   width:600,
				height:500,
				drag:false,
				opacity: 0.87,
				 lock: true
		   });
		   
		//dialog("选择促销活动产品的类目","iframe:/common/queryCategoryList.action" ,"300px","500px","iframe",50);
	}
	function selectProductBrand(){
		art.dialog.data("selectedIds", $("#productFilterSql").val());
		myDialog = art.dialog.open('/common/selectProductBrand.action', {
			   title: '选择商品品牌',
			   width:900,
				height:600,
				drag:false,
				 lock: true
		   });
 		//dialog("选择商品品牌","iframe:/common/selectProductBrand.action" ,"700px","500px","iframe",50);
   	}
	
	function receiveProductCategory(nodes){
 		var names ="";
 		var ids =",";
 		if(nodes.length<=0){
 			alert("请选择一个子类目");
 			return;
 		}
 		if(nodes.length>80){
 			alert("选择的子类目不能超过80个");
 			return;
 		}
	 	var count = 0;
		var names ="";
		var ids =",";
		var sqlId = $("#productFilterSql").val();
		var showHtml ="";
		for(i=0;i<nodes.length;i++){
			if(nodes[i].isParent){
				continue;
	 		}
			count ++;
			if(sqlId.indexOf(nodes[i].categoryId)>=0){
				continue;
			}
			ids += nodes[i].categoryId+",";
			var html = spellHTML(nodes[i].categoryId,nodes[i].categoryName,"emDiv","aclose","'productFilterSql'");
			showHtml = showHtml +html;
		}
		if(count<=0){
 			alert("请选择一个子类目");
 			return;
 		}
		$("#productFilterSql").val(sqlId+ids);
		$("#productFilterNames").append(showHtml);
		myDialog.close();
		
 	}
 	function receiveProductPrand(o){
 		
 		
 		var names ="";
 		var ids =",";
 		var sqlId = $("#productFilterSql").val();
 		var showHtml ="";
 	    
 		for(i=0;i<o.length;i++){
 			if(sqlId.indexOf(o[i].value)>=0){
 				continue;
 			}
			ids += o[i].value+",";
			var html = spellHTML(o[i].value,o[i].title,"emDiv","aclose","'productFilterSql'");
			showHtml = showHtml +html;
 		}
		$("#productFilterSql").val(sqlId+ids);
		$("#productFilterNames").append(showHtml);
		myDialog.close();
 	}
 
 	
 	$(document).ready(function(){
 		var promotionType = $("#promotionType");
 		
 		if(promotionType.val()==5||promotionType.val()==3||promotionType.val()==11){
 		var ruleData = $("#ruleDataValue").val();
 		//$("#ruleData").html(ruleData);
 		}
 		
 		
 		if(promotionType.val()==10){
 			 $(':radio[value="2"]').attr("checked",true);
 			 $(':radio[name="promotion.productFilterType"]').attr("disabled",true);
 		}
 		var filterSqlType = $(':radio[name="promotion.productFilterType"]:checked');
 		radio_click(filterSqlType.val());
        
      });
 	jQuery.validator.addMethod("checkTime", function(value, element) {
 		 var startTime=$("#startTime").val();  
 		 var now = new Date();
 	    var start=new Date(startTime.replace("-", "/").replace("-", "/"));  
 	    var endTime=value;  
 	    var end=new Date(endTime.replace("-", "/").replace("-", "/"));
 	    if(end<now){  
	        return false;  
	    } 
 	    if(end<start){  
 	        return false;  
 	    }  
 	    return true;  
		 
 	},"结束时间不能早于开始时间,且不能早于当前时间！");
 	
 	jQuery.validator.addMethod("checkTime2", function(value, element) {
		 var startTime=$("#startTime").val();  
	    var start=new Date(startTime.replace("-", "/").replace("-", "/"));  
	    var endTime=value;  
	    var end=new Date(endTime.replace("-", "/").replace("-", "/"));
	    var promotionTypeId = $("#promotionType").val();
	    var isSuccess = false;
	    if(promotionTypeId==4){
	    	var promotionId = $("#promotionId").val();
	    	$.ajax({
	    		url:"/promotion/checkPromoitonTimeInCoupon.action?promotion.promotionId="+
	    		promotionId+"&promotion.startTime="+startTime+"&promotion.endTime="+endTime,
				type: "post",
				dataType: "json",
				async:false,
				success: function (returnValue) {
				 //alert(returnValue.isSuccess);
				 isSuccess = returnValue.isSuccess;
				}
	       });
	    }else{
	    	return true;
	    }
	    return isSuccess;
	},"起始时间不能超过优惠券的有效时间！");
 	
 	
 	
 	jQuery.validator.addMethod("checkSql", function(value, element) {
 		var filterSqlType = $('input:radio[name="promotion.productFilterType"]:checked').val();
 		if(filterSqlType==1||filterSqlType==2){
 			return true;
 		}
 		if(value==''||value==null)return false;

// 		if(value.test(/^,+/)){
 //			return false;
 	//	}
 		return true;
 		
 	},'请输入合法参数!');
 	
 	function selectConflicPromoiton(){
 		var startTime=$("#startTime").val()||'';  
 		var endTime=$("#endTime").val()||'';  
		var type=$('#promotionType').val()||'';
		var promotionId = $("#promotionId").val()||'';
		var shopSort = $("#shopSort").val();
		if(startTime==""||endTime==""||type==""){
			alert("请先填写起始时间和规则！");
			return ;
		}
		var url = "/promotion/selectConflictPromotion.action?promotion.promotionId="+promotionId
						+"&promotion.startTime="+startTime+"&promotion.endTime="+endTime
						+"&promotion.shopSort="+shopSort;;
		art.dialog.data("selectedIds", $("#mutexPromotionId").val());
		//art.dialog.data("promotionTypeId", $("#promotionTypeId").val());
		myDialog = art.dialog.open(url, {
			   title: '设置互斥活动',
			   width:900,
				//height:400,
				 autoOpen: false
				//drag:false,
				 //lock: false
		   });
 	}
 	
</script>
</head>
<s:set name="parent_name" value="'促销管理" scope="request"/>
<s:set name="name" value="'活动管理'" scope="request"/>
<s:if test="promotion.promotionId==null">
<s:set name="son_name" value="'添加促销活动'" scope="request"/>
</s:if><s:else>
<s:set name="son_name" value="'修改促销活动'" scope="request"/>
</s:else>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<body>
    
    
    
	
	<s:form action="/promotion/queryExceptionProductList.action"	method="POST" namespace='/promotion' id="frm">
	<input type="hidden" value='<s:property value="promotion.productFilterType"/>' id="productFilterTypeDB"/>
	<input type="hidden" value='<s:property value="promotion.productFilterSql"/>' id="productFilterSqlDB"/>
	<input type="hidden" name="promotion.promotionType" id="promotionType" value='<s:property value="promotion.promotionType"/>'/>
	<s:hidden name="promotion.promotionFilterProductName" id="promotionFilterProductName"></s:hidden>
	<s:hidden name="promotion.shopSort" id="shopSort"></s:hidden>
	<s:hidden name="promotion.supplierId" id="supplierId"></s:hidden>
	<s:hidden name="promotion.promotionId" id="promotionId"></s:hidden>
	<input type="hidden" value='<s:property value="promotion.status"/>' id="status"/>
	
	<!-- 数据编辑区域 -->
	<table width="95%" class="edit_table" align="center" cellpadding="3"
		cellspacing="0" border="1" bordercolor="#C7D3E2"
		style="border-collapse: collapse; font-size: 12px;">
		<tr>
			<th colspan="2" align="left" class="edit_title">基本信息管理</th>
		</tr>
		
		<tr>
			<th width="20%" align="right" class="eidt_rowTitle">所属商家：</th>
		  <td width="80%">
            	${promotion.shopNames}
		  </td>
		</tr>
		<tr>
			<th width="20%" align="right" class="eidt_rowTitle">类型：</th>
		  <td width="80%">
            	<s:property value='#request.promotionTypeMap[promotion.promotionType]' />
		  </td>
		</tr>
		<tr>
			<th width="20%" align="right" class="eidt_rowTitle">优惠数据：</th>
		  	<td width="80%" >
		  	<div id="ruleData" value="xx">
            	<s:if test="null== promotion.promotionNote || ''==promotion.promotionNote">
            	    <s:iterator value="promotion.ruleDatas" var="dataObj" >
					<s:if test="promotion.promotionType==6">
					满<s:property value="meetData"/>
					<s:if test="meetDataType==1">
					元
					</s:if>
					<s:elseif test="meetDataType==2">
					件
					</s:elseif>  
					，减<s:property value="prizeData"/>元
					</s:if>
					<s:elseif test="promotion.promotionType==55">
					满<s:property value="meetData"/>
					<s:if test="meetDataType==1">
					元
					</s:if>
					<s:elseif test="meetDataType==2">
					件
					</s:elseif> ，
					加<s:property value="prizeData"/>元，可得skuId为<a title="点击预览" href="javascript:;" onClick="javascript:toProductSku(<s:property value='entityId'/>)">
					<s:property value="entityId"/></a>的商品<s:property value="num1"/>件
					</s:elseif>
					<s:elseif test="promotion.promotionType==4">
					满<s:property value="meetData"/>
					<s:if test="meetDataType==1">
					元
					</s:if>
					<s:elseif test="meetDataType==2">
					件
					</s:elseif> ，
					送id为<a title="点击预览" href="/coupon/couponRule_pageShow.action?viewType=show&couponId=<s:property value='entityId'/>"><s:property value="entityId"/></a>（优惠券）
					</s:elseif>
					<s:elseif test="promotion.promotionType==3">满<s:property value="meetData"/>
					<s:if test="meetDataType==1">
					元
					</s:if>
					<s:elseif test="meetDataType==2">
					件
					</s:elseif> 
					，赠skuId为<a title="点击预览" href="javascript:;" onClick="javascript:toProductSku(<s:property value='entityId'/>)"><s:property value="entityId"/></a>的商品<s:property value="num1"/>件</s:elseif>
					；
					</s:iterator>
				</s:if>
            	<s:else>
            	${promotion.promotionNote}
            		
				</s:else>
				</div>
		  	</td>
		</tr>
		<tr>
			<th width="20%" align="right" class="eidt_rowTitle"><font color="red">*</font>活动标题：</th>
		  <td width="80%">
		   <input <s:if test="promotion.status==2">disabled="true"</s:if> type="text" name="promotion.promotionTitle" id="promotionTitle" size="70" 
             maxlength="70" value="<s:property value='promotion.promotionTitle' />"
				style="width:400px" onblur=""/> 
		  </td>
		</tr>
		<tr>
			<th width="20%" align="right" class="eidt_rowTitle"><font color="red">*</font>前端显示名称：</th>
		  <td width="80%">
             <input type="text" name="promotion.promotionName" id="promotionName" size="70" 
             maxlength="70" value="<s:property value='promotion.promotionName' />"
				style="width:400px" onBlur=""/> 
		  </td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle">广告语：</th>
			<td><label><input type="text" name="promotion.slogan" value="<s:property value='promotion.slogan' />"
				id="slogan" size="20" maxlength="50" style="width:322px"/> </label></td>
		</tr>
	  <tr>
			<th align="right" class="eidt_rowTitle"><font color="red">*</font>活动时间：</th>
			<td>从<input name="promotion.startTime" id="startTime" <s:if test="promotion.status==2">disabled="true"</s:if>
					type="text" class="input_style" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d 0:0:0'})" 
					value="<s:date name='promotion.startTime'  format='yyyy-MM-dd HH:mm:ss'/>"/> 
			到
			<input name="promotion.endTime" id="endTime"<s:if test="promotion.status==2">disabled="true"</s:if>
					type="text" class="input_style" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d 0:0:0'})" 
					value="<s:date name='promotion.endTime' format='yyyy-MM-dd HH:mm:ss'/>"/>
			</td>
			
			
		</tr>
	  <tr>
		  <th align="right" class="eidt_rowTitle">筛选商品类型：</th>
		  <td><br/>
		
		  <div id = "productFilterTypeRadio">
		   <s:if test="promotion.promotionType==8||promotion.promotionType==10||promotion.promotionType==12">
		   <s:radio onclick="radio_click(this.value)" name="promotion.productFilterType" id="productFilterType"
			  list="#{2:'指定商品'}"/> 
		  </s:if>
		  <s:if test="promotion.promotionType!=8&&promotion.promotionType!=10&&promotion.promotionType!=11&&promotion.promotionType!=12&&promotion.status==2">
		  <input type="hidden" value='<s:property value="promotion.productFilterType"/>' name="promotion.productFilterType"/>
		  <s:radio onclick="radio_click(this.value)" name="promotion.productFilterType" id="productFilterType"
			  list="#{1:'全场',2:'指定商品',3:'商品类目',4:'商品品牌'}" disabled="true"/>
			  <s:if test="promotion.status==1">
			  	<input class="selectEm" type="button" id="selectEm" onClick="selectProductFilter()" disabled="disabled" value="选择"/>
		    </s:if>
		  </s:if>
		  <s:if test="promotion.promotionType!=8&&promotion.promotionType!=10&&promotion.promotionType!=11&&promotion.promotionType!=12&&promotion.status!=2">
		   <s:radio onclick="radio_click(this.value)" name="promotion.productFilterType" id="productFilterType"
			  list="#{1:'全场',2:'指定商品',3:'商品类目',4:'商品品牌'}" />
			  <s:if test="promotion.status==1">
			  	<input class="selectEm" type="button" id="selectEm" onClick="selectProductFilter()" disabled="disabled" value="选择"/>
		 </s:if>
		  </s:if>
		 <s:if test="promotion.promotionType==11&&promotion.status!=2">
		 <s:if test="promotion.promotionData!=null">
		  
		  <s:radio onclick="radio_click(this.value)" name="promotion.productFilterType" id="productFilterType"
			  list="#{1:'全场',2:'指定商品',3:'商品类目',4:'商品品牌'}" />
			  <s:if test="promotion.status==1">
			  	<input class="selectEm" type="button" id="selectEm" onClick="selectProductFilter()" disabled="disabled" value="选择"/>
		    </s:if>
		    </s:if>
		    <s:else>
		     <s:radio onclick="radio_click(this.value)" name="promotion.productFilterType" id="productFilterType"
			  list="#{2:'指定商品'}"/> 
		    </s:else>
		  </s:if>
		  <s:if test="promotion.promotionType==11&&promotion.status==2">
		 <s:if test="promotion.promotionData!=null">
		 
		  <s:radio onclick="radio_click(this.value)" name="promotion.productFilterType" id="productFilterType"
			  list="#{1:'全场',2:'指定商品',3:'商品类目',4:'商品品牌'}" disabled="true"/>
			  <s:if test="promotion.status==1">
			  	<input class="selectEm" type="button" id="selectEm" onClick="selectProductFilter()" disabled="disabled" value="选择"/>
		    </s:if>
		    </s:if>
		    <s:else>
		     <s:radio onclick="radio_click(this.value)" name="promotion.productFilterType" id="productFilterType"
			  list="#{2:'指定商品'}"/> 
		    </s:else>
		  </s:if>
		   
		 	<input type="hidden" name="promotion.productFilterSql" 
   		 	id="productFilterSql" value='<s:property value="promotion.productFilterSql"/>'/>
		 </div>
		  <div id="productFilterNames"><br/>
		  </div>
	  </td>
	  </tr>
<!-- 	  <tr>
			<th align="right" class="eidt_rowTitle">强制排他：</th>
			<td>
				<input type="checkbox" id="mutexAll" <s:if test="promotion.mutexPromotionId=='all'" >checked="checked"</s:if> onchange="javascript:mutexAllChange(this);"/>设置为强制排它
			</td>
	</tr> -->
<!--	  <tr>
			<th align="right" class="eidt_rowTitle">互斥活动：</th>
			<td>
			<div>
			<c:forEach items="${exclusionPromotionMap}" var="exclusPro">
			<div class="sbDiv"><em unselectable="on">${exclusPro.value}</em>
			<a class="deleteP" hidefocus="hidefocus" data-value="${exclusPro.key}" onclick="javascript:delName(this,'mutexPromotionId');">x</a>
			</div></c:forEach>
			<input class="selectComPromotion" type="button" style="float:left;" id="selectComPromotion" <s:if test="promotion.mutexPromotionId=='all'" >disabled="disabled"</s:if> style="float:left;"  onclick="selectConflicPromoiton();" value="选择">
			</div>
			<span>
			<input type="hidden" style="display: inline;" name="promotion.mutexPromotionId"  
			data-value='<s:property value="promotion.mutexPromotionId"/>' 
			value='<s:property value="promotion.mutexPromotionId"/>' id="mutexPromotionId" 
			size="50"/>
			</span>
			</td>
		</tr> -->
		<tr display="none">
			<th align="right" class="eidt_rowTitle">活动描述：</th>
			<td><label> <s:textarea name="promotion.promotionDescribe" id="promotionDescribe" rows="8" cols="45"/></label></td>
			<input type="hidden" value="<s:property value="ruleData"/>" id ="ruleDataValue"/>
		</tr>
		<tr id="exceptionProduct">
		
		 <th valign="top" align="right" id="exceptionProductTh" class="eidt_rowTitle" >

                  活动例外商品：
      </th>
		  <td valign="top" id="exceptionProductTd">
		  <!-- 查询条件区域 -->
		<table width="98%"   cellpadding="0"
			cellspacing="0">
			<tr>
				<td align="right" width="120px" height="10px">SKU编号：</td>
				<td><input name="promotionProduct.productSkuCode" type="text" class="input_style"
					value="<s:property value='promotionProduct.productSkuCode' />"></td>
                <td align="right" width="120px">产品主标题：</td>
				<td><input name="promotionProduct.productTitle" type="text" class="input_style"
					value="<s:property value='promotionProduct.productTitle' />"></td>
				<td align="center"><INPUT TYPE="button" class="queryBtn" onClick="$('#frm').submit();"
					value=""></td><s:if test="promotion.status==1"><td><input class="addBtn"
					type="button" value="" onClick="gotoAddProduct();"></td></s:if>
			</tr>
		</table>


		<!-- 数据列表区域 -->
		<table width="98%" class="list_table" align="center" cellpadding="3"
			cellspacing="0" border="1" bordercolor="#C1C8D2">
			<tr>
				<th align="center">产品主标题</th>
				<th align="center">SKU编号</th>
				<th align="center">SKU属性</th>
				<th align="center">状态</th>
				<th align="center">商家</th>
				<th align="center">品牌</th>
				<th align="center">操作</th>
			</tr>
			<s:iterator  value="page.dataList">
				<tr onMouseOver="this.style.backgroundColor='#def2fa'"
				onMouseOut="this.style.backgroundColor='#FFFFFF'">
					
					<td align="center"><s:property value="productName"/></td>
					<td align="center"><s:property value="productSkuCode" /></td>
					<td align="center"><s:iterator value="productSkuAttrList" >
					<s:property value='categoryAttrName'/>：<s:property value='categoryAttrValue'/>&nbsp;&nbsp;&nbsp;&nbsp;
					</s:iterator></td>
					<td align="center">
					<s:if test="productStatus==3">上架</s:if>
					<s:elseif test="productStatus==4">下架</s:elseif>
					</td>
					<td align="center">
					<s:property value="shopName"/>
					</td>
					<td align="center"><s:property value='brandName'/></td>
					<td align="center"><s:if test="promotion.status==1"><a href="javascript:void(0);" onClick="javascript:deleteProduct(<s:property value='promotionProductId' />);">删除</a></s:if></td>
				</tr>
			</s:iterator>
		</table>

		<!-- 分页按钮区 -->
		<table width="98%" align="center" cellpadding="0" cellspacing="0"  >
			<tr>
				<td><%@ include file="/WEB-INF/jsp/public/pager.jsp"%>
				</td>
			</tr>
		</table>
		</td>
		</tr>
		<tr><td></td>
			<td align="left"><INPUT  class="saveBtn" TYPE="button"
				value="" onClick="javascript:submitForm();" >
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
				<input type="button" class="backBtn" onClick="javascript:back();" />
		</tr>
	</table>
	
</s:form>

</body>
<script type="text/javascript">
    
	var delName = function(obj,ementId){
		var id = $(obj).attr("data-value");
		var productFilterSql = ","+$("#"+ementId).val()+",";
		productFilterSql = productFilterSql.replace(","+id+",",",");
		//productFilterSql = productFilterSql.replace(/,{2,20}/,"");
		if(productFilterSql.replace(/,/g,'')==''){
			productFilterSql ='';
		}
		$("#"+ementId).val(productFilterSql);
		
		$(obj).parent().remove();
	};
	var  showFilterSqlName = function(filterSqlName,divClassName,aClassName,delName){
		//var names = filterSqlName.val();
		var nameArray = filterSqlName.split(",");
		var html = "";
		for(var i=0;i<nameArray.length;i++){
			if(nameArray[i]==""||nameArray[i]==null){
				continue;
			}
			var nameIdArray = nameArray[i].split(":");
			html = html + spellHTML(nameIdArray[0],nameIdArray[1],divClassName,aClassName,delName);
		}
		$("#productFilterNames").append(html);
		//$("#imageButton").before(html);
		//$("#productFilterSql").before('<input class="selectEm" type="button" id="selectEm" style="float:left;" value="选择">');
	};
	var spellHTML=function(id,title,divClass,aClass,delName){
		
		var status = $('#status').val();
		var html = '<div class="'+divClass+'"><em unselectable="on">'+title+'</em>';
		if(status==1){
			html= html+ '<a class="'+aClass+'" hidefocus="hidefocus" data-value="'+id+'" onclick="javascript:delName(this,'+delName+');">x</a></div>';
		 }else{
			 html= html+ '<a class="'+aClass+'" hidefocus="hidefocus" data-value="'+id+'"></a></div>'; 
		 }
		
		return html;
	};
function shopHideFilterSqlName(){
		$("#productFilterSqlName").hide();
		$(".emDiv").remove();
		$(".aclose").remove();
	}
	/**接收互斥*/
function receiveConflicId(o){
		
	 //myDialog.close();
	 
	var names ="";
	var ids =",";
	if(o.length==0){
		//alert("请选择一个活动");
		return;
	}
	var sqlId = $("#mutexPromotionId").val();
	var showHtml ="";
	for(i=0;i<o.length;i++){
		
		var id = o[i].value;//o[i].attr("value");
		if(sqlId.indexOf(id)>=0){
			continue;
		}
		var name = o[i].title;
		//names += o[i].value+":"+o[i].title+",";
		ids += id+",";
		var html = '<div class="sbDiv"><em unselectable="on">'+name+'</em>'+
		 '<a class="deleteP" hidefocus="hidefocus" data-value="'+id+'" onclick="javascript:delName(this,\'mutexPromotionId\');">x</a></div>';
			
			//spellHTML(o[i].value,o[i].title);
		showHtml = showHtml +html;
	}
	$("#mutexPromotionId").val(sqlId+ids);
	$("#mutexPromotionId").attr("data-value",sqlId+ids);
	//$("#productFilterSqlName").val(names);
	$("#selectComPromotion").before(showHtml);
	myDialog.close();
}
	function mutexAllChange(o){
		if($(o).attr("checked")==true){
			$("#mutexPromotionId").val("all");
			$("#selectComPromotion").attr("disabled","disabled");
		}else{
			var ve = $("#mutexPromotionId").attr("data-value");
			if(ve=='all'){
				ve='';
			}
			$("#mutexPromotionId").val(ve);
			$("#selectComPromotion").removeAttr("disabled");
		}
		
	}
	function submitForm(){
		$("#frm").validate({
            rules: {
				"promotion.promotionName":{required:true,maxlength:80},
				"promotion.promotionTitle":{required:true,maxlength:80},
				"promotion.startTime":{required:true},
				"promotion.endTime":{required:true,checkTime:true,checkTime2:true},
				"promotion.promotionDescribe":{maxlength:200},
				"promotion.productFilterSql":{checkSql:true,maxlength:300},
				"promotion.mutexPromotionId":{maxlength:100}
        	},
        	messages:{
        		"promotion.promotionName":'请输入合法的名称',
        		"promotion.productFilterSql":'商品类型未选择完整,或类目超过最大限制',
        		"promotion.promotionDescribe":'最多不能超过两百字'
        	},
        	success: function (label){
	            label.removeClass("checked").addClass("checked");
	        }
        });
		
		$("#frm").attr('action','/promotion/updatePromotion.action');
		$("#frm").submit();
	}
	/**  进入选择产品页面  **/
    function gotoAddProduct(){
		var productFilterTypeDB =$("#productFilterTypeDB").val();
		var productFilterType =$('#productFilterTypeRadio input[name="promotion.productFilterType"]:checked ').val();
		
		if(productFilterTypeDB!=productFilterType&&(productFilterType==2||productFilterTypeDB==2)){
			alert('筛选商品类型发生变化,请保存后再进行添加');
			return;
		}
		var promotionId = $("#promotionId").val();
		var shopCodes = $("#supplierId").val();
		var shopSort = $("#shopSort").val();
    	 dialog("查看所有SKU商品","iframe:/promotion/findAllProductSkuForException.action?promotionProduct.promotionId="
    	 +promotionId+"&productSku.product.shopCode="+shopCodes+"&productSku.product.suppliterType="+shopSort ,"800px","600px","iframe",50);
    }
    function closePopDiv(promotionId){
		closeThis();
		
		var result="保存完毕！如所添加商品已存在该活动中则不会添加！";
		
		
		alert(result);
		$("#frm").submit();
	}
    function deleteProduct(promotionProductId){
    	if (confirm("您确定要删除选中的数据吗？")){
           $.ajax({
				//toIncreasePromotionAddOrUpdate
				url:'/promotion/deleteExceptionProduct.action',
				type:'post',
				dataType:'json',
				data:{'promotionProduct.promotionProductIds':promotionProductId},
				success:function(data){
					var code = data.code;
					if(code==0){
						alert("删除成功！");
						$("#frm").submit();
					}else{
						alert(data.module);
						//pInput.focus();
						return;
					}
				},
				error:function(){
					alert('操作失败请联系管理员');
				}
			});
   		}
    }
    function back(){
    	var id = $("#promotionId").val();
    	window.location.href = "/promotion/queryPromotionList.action?promotion.promotionId="+id;
    }
    
  </script>
</html>

