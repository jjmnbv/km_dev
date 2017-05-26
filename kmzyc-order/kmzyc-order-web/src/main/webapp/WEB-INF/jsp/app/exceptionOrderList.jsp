<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>异常订单信息列表</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="/etc/css/jquery-ui.css">
		<link rel="stylesheet" type="text/css" href="/etc/css/autocompletestyles.css">
		<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.validate.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.metadata.js"></script>
		<script type="text/javascript" src="/etc/js/messages_cn.js"></script>
		<script type="text/javascript" src="/etc/js/chili-1.7.pack.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.blockUI.js"></script>
		<script type="text/javascript" src="/etc/js/urchin.js"></script>
		<script type="text/javascript" src="/etc/js/messages_cn.js"></script>
		<script type="text/javascript" src="/etc/autocomplete/jquery.autocomplete.js"></script>
		<script  src="/etc/js/97dater/WdatePicker.js"></script>
		<script src="/etc/js/dialog.js"></script>
		<script type="text/javascript" src="/etc/js/app/orderUtil.js"></script>
		<script type="text/javascript">
		var listAction = "/app/qryExceptionOrderPageList.action";
		var detailOrderAction = "/app/orderItemdetailAction.action";
		var initAddExceptionorderAction = "/app/initAddExceptionOrder.action";
		var renewExceptionOrderAction = "/app/renewExceptionOrder.action";
		var sefTarget="_self";

		  

		
		$(function(){
			   var countriesArray;
				//.attr("onpropertychange"
			   $.ajax({
					//async:'false',
					url:'selectSupplierListByName.action',
					success:function(data){
						  countriesArray = $.map(data, function (value, key) { 
								return { value: value, name: key }; 
							});
						  
						  $("#commerceNameComplate").bind("change",function(){
							  //$("#commerceIdComplate").val(null);
							});
						$('#commerceNameComplate').autocomplete({
					    	lookup: countriesArray,
					      	minChars: 0 ,
					      //  minLength: 1,
					        onSelect: function (value) {
					        	 console.log(value);
					        	 $("#commerceIdSelect").val('2');
					        }
					    });
					},
					dataType:'json'
				}); 
				
		});


		function clearCommerceName(){
			if( $("#commerceIdSelect").val()!='2'){
				$('#commerceNameComplate').val('');
			}
		}
		
		function renewExceptionOrder(paramOrderCode){
		 if(confirm('确认解除异常订单状态吗？\n\n解除后，订单将回到正常订单处理流程中。')){
			 jQuery.ajax({
	    			async:false,
	    			url:renewExceptionOrderAction,
	    			type:'POST',
	    			dataType:'json',
	    			data:{
	        			"map['orderCode']":paramOrderCode,
	    				t_i_m_e:Math.random()
	    			},
	    			timeout:'6000',
	    			success:function(data){
	    				if(data.renewResult=="SUCESS"){
	    					alert("已成功恢复异常订单!");
	    					document.forms[0].action=listAction;
	    				    document.forms[0].target=sefTarget;
	    				    document.forms[0].submit();
	    				}else{
	    					alert("恢复异常订单时失败，请稍后重试或联系系统管理员!");
	    				}
	    			},
	    			error:function(){
	    				alert("恢复异常订单时失败，请稍后重试或联系系统管理员!");
	    			}
	    		});	
			 }
		}

		function checkOrderExceptionForm(){
			$("#pageNo").val('1');
			return true;
		}

		function  changeCreateDateRange(){
			changeDateRange($('#createDateRange'),$('#createDateStart'),$('#createDateEnd'));
		}

		function  changePayDateRange(){
			changeDateRange($('#payDateRange'),$('#payDateStart'),$('#payDateEnd'));
		}
		
		function addExceptionOrder(){
			 document.forms[0].action=initAddExceptionorderAction;
		     document.forms[0].target=sefTarget;
		     document.forms[0].submit();
		}
	    
		</script>
	</head>
	<body>
		<s:set name="parent_name" value="'业务操作'" scope="request"/>
		<s:set name="name" value="'订单管理'" scope="request"/>
		<s:set name="son_name" value="'异常订单管理'" scope="request"/>
		<s:include value="/WEB-INF/jsp/public/title.jsp"/>
		<div style="height:10px;"></div>
		<form id="exceptionOrderListSearch" action="qryExceptionOrderPageList.action" method="POST" onSubmit="javascript:checkOrderExceptionForm()">
		<input type="hidden" name="map['status']" value="-3">
		<table class="table_search" width="98%" align="center" cellpadding="0" cellspacing="3" >
			<tr>
				<td align="left">
					订单号&nbsp;：
					<input class="condition" name="map['orderCode']" type="text" value='<s:property value="map['orderCode']"/>' onkeyup="this.value=this.value.replace(/[, ]/g,'')">
				</td>
		        <td align="left">
		           	订单状态：
		           	<select class="condition" name="status" style="width:156px" disabled="disabled" >
				   		<option value='-3' selected="selected" >异常订单</option>
				    </select>
				</td>
				<td align="left">
		           	下单账号：
		           	<input class="condition" name="map['customerAccount']" type="text" value='<s:property value="map['customerAccount']"/>'>
				</td>
			</tr>
			<tr>
				<td align="left">
					收货人&nbsp;：
					 <input class="condition" name="map['consigneeName']" type="text" value='<s:property value="map['consigneeName']"/>'>
				</td>
		        <td align="left">
		           	收货电话：
		           	<input class="condition" name="map['consigneeMobile']" type="text" value='<s:property value="map['consigneeMobile']"/>'>
				</td>
				<td align="left">
		           	下单时间：
		           	<input class="Wdate condition" id="createDateStart" name="map['createDateStart']" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'createDateEnd\'),\'%y-%M-%d %H:%m:%s\'}'})" type="text" value='<s:property value="map['createDateStart']"/>'>
		           	至
		           	<input class="Wdate condition" id="createDateEnd" name="map['createDateEnd']" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d %H:%m:%s',minDate:'#F{$dp.$D(\'createDateStart\')}'})" type="text" value='<s:property value="map['createDateEnd']"/>'>
				</td>
				<td align="left">
		           	下单时间范围：
		           	<select class="condition" id="createDateRange" name="map['createDateRange']" style="width:96px" onChange="changeCreateDateRange()">
				    	<option value="all" <s:if test='map["createDateRange"]=="all"'>selected="selected"</s:if>>全部</option>
						<option value="today"   <s:if test='map["createDateRange"]=="today"'>selected="selected"</s:if>>今天</option>
						<option value="ysday"   <s:if test='map["createDateRange"]=="ysday"'>selected="selected"</s:if>>昨天</option>
						<option value="tsweek"  <s:if test='map["createDateRange"]=="tsweek"'>selected="selected"</s:if>>本周</option>
						<option value="lsweek"  <s:if test='map["createDateRange"]=="lsweek"'>selected="selected"</s:if>>上周</option>
						<option value="tsmonth" <s:if test='map["createDateRange"]=="tsmonth"'>selected="selected"</s:if>>本月</option>
						<option value="lsmonth" <s:if test='map["createDateRange"]=="lsmonth"'>selected="selected"</s:if>>上月</option>
					</select>
				</td>
			</tr>
			<tr>
				<td align="left">
					商品标题：
					<input class="condition hideCondition" name="map['commodityName']" type="text" value='<s:property value="map['commodityName']"/>'>
				</td>
		        <td align="left">
		           	商品编号：
		          <input class="condition hideCondition" name="map['commodityCode']" type="text" value='<s:property value="map['commodityCode']"/>'>
				</td>
				<td align="left">
		           	支付时间：
		           	<input class="Wdate condition" id="payDateStart" name="map['payDateStart']" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'payDateEnd\'),\'%y-%M-%d %H:%m:%s\'}'})" type="text" value='<s:property value="map['payDateStart']"/>'>
		           	至
		           	<input class="Wdate condition" id="payDateEnd" name="map['payDateEnd']" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d %H:%m:%s',minDate:'#F{$dp.$D(\'payDateStart\')}'})" type="text" value='<s:property value="map['payDateEnd']"/>'>
				</td>
				<td align="left">
		           	支付时间范围：
		           	<select class="condition" id="payDateRange" name="map['payDateRange']" style="width:96px" onChange="changePayDateRange()">
				    	<option value="all"     <s:if test='map["payDateRange"]=="all"'>selected="selected"</s:if>>全部</option>
						<option value="today"   <s:if test='map["payDateRange"]=="today"'>selected="selected"</s:if>>今天</option>
						<option value="ysday"   <s:if test='map["payDateRange"]=="ysday"'>selected="selected"</s:if>>昨天</option>
						<option value="tsweek"  <s:if test='map["payDateRange"]=="tsweek"'>selected="selected"</s:if>>本周</option>
						<option value="lsweek"  <s:if test='map["payDateRange"]=="lsweek"'>selected="selected"</s:if>>上周</option>
						<option value="tsmonth" <s:if test='map["payDateRange"]=="tsmonth"'>selected="selected"</s:if>>本月</option>
						<option value="lsmonth" <s:if test='map["payDateRange"]=="lsmonth"'>selected="selected"</s:if>>上月</option>
					</select>
				</td>
			</tr>
			<tr>
				<td align="left">
					销售类型：
					<select id="commerceIdSelect" class="condition hideCondition" name="map['commerceId']" 
							value='<s:property value="map['commerceId']"/>' style="width:156px"  onchange="clearCommerceName()">
						<option value="" <s:if test='map["commerceId"]==""'>selected="selected"</s:if> >全部</option>
					   	<option value="1" <s:if test='map["commerceId"]=="1"'>selected="selected"</s:if> >自营</option>
					   	<option value="2" <s:if test='map["commerceId"]=="2"'>selected="selected"</s:if> >第三方</option>
					</select>
				</td>
		        <td align="left">
		           	订单来源：
			           	<select class="condition hideCondition" name="map['orderSource']" value='<s:property value="map['orderSource']"/>' style="width:156px">
			           		<option value="" <s:if test='map["orderSource"]==""'>selected="selected"</s:if> >全部</option>
			           		<option value="1" <s:if test='map["orderSource"]=="1"'>selected="selected"</s:if> >网页</option>
			           		<option value="2" <s:if test='map["orderSource"]=="2"'>selected="selected"</s:if> >APP</option>
			           		<!-- <option value="3" <s:if test='map["orderSource"]=="3"'>selected="selected"</s:if> >微信</option> -->
			           		<option value="4" <s:if test='map["orderSource"]=="4"'>selected="selected"</s:if> >WAP</option>
			           	</select>
				   
				</td>
				
				<td align="left">
		           	所属商家：
		           	<!-- <input type="hidden" id="commerceIdComplate" name="map['commerceId']" value='<s:property value="map['commerceId']"/>' /> -->
		           	<input class="condition" id="commerceNameComplate" name="map['commerceName']" type="text" value='<s:property value="map['commerceName']"/>'>
				</td>
				<td align="left">
		          &nbsp;&nbsp;
					<input type="submit" class="queryBtn" value="" />&nbsp;
					<input type="reset" class="btn-custom" value=" 重置 " class="cleanCheck">
					<input type="button" onClick="addExceptionOrder()" class="btn-custom"  value="新增异常订单"/>&nbsp;
				</td>
			</tr>
		</table>
		<br/>
		<table class="list_table" width="98%" align="center" cellpadding="3" cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc" style="border-collapse: collapse;font-size:12px">
			<tr>
		   		<th width="15%">订单号</th>
		<!--    		<th width="5%">渠道</th> -->
		   		<th width="5%">来源</th>
		   		<th width="11%">销售类型</th>
		   		<th width="10%">下单账号</th>
		   		<th width="15%">收货人</th>
		   		<th width="5%">订单状态</th>
		   		<th width="10%">订单金额</th>
		   		<th width="6%">实付金额</th>
		   		<th width="6%">下单时间</th>
		   		<th width="10%">操作</th>
			</tr>
			<s:iterator id="order" value="page.dataList">
			<tr>
		   		 <td>
		   		 	<s:if test="#order.orderStatus==16">&nbsp;&nbsp;&nbsp;&nbsp;[主]</s:if><s:if test="#order.parentOrderCode!=null">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;┗</s:if><a href="/app/orderItemdetailAction.action?orderCode=<s:property value='#order.orderCode'/>&map['byStatus']=<s:property value='map["byStatus"]'/>"><s:property value="#order.orderCode"/></a>
		   		 	<s:if test="null!=#order.orderOperationRemark"><br/><font style="color:red;"><s:property value="#order.orderOperationRemark"/></font></s:if>	
		   		 </td>
		   	<%-- 	 <td><s:property value="#order.orderChannel"/></td> --%>
		   		 <td><s:property value="#order.orderSourceStr"/></td>
		   		 <td><s:property value="null==#order.commerceId?'自营':'第三方'"/>
		   		 <s:if test="null!=#order.commerceId && null!=#order.commerceName">[<s:property value="#order.commerceName"/>]</s:if></td>
		   		 <td><s:property value="#order.customerAccount"/></td>
		   		 <td><s:property value="#order.consigneeName"/>
		   		 <s:if test="null!=#order.consigneeMobile">[<s:property value="#order.consigneeMobile"/>]</s:if></td>
		   		 <td><s:property value="#order.orderStatusStr"/></td>
		   		 <td><s:property value="#order.originalOrderSum"/></td>
		   		 <td><s:property value="#order.amountPayable"/></td>
		   		 <td><s:date name="#order.createDate" format="yyyy-MM-dd HH:mm:ss" /></td>
		   		 <td>
		   		 	<s:if test="#order.orderStatus!=16"><a href="javascript:renewExceptionOrder('<s:property value="#order.orderCode"/>')">恢复</a></s:if>
		   		 </td>
			</tr>
			</s:iterator>
		</table>
		<table class="page_table" width="98%" align="center">
		   <tr>
		     <td align="right"><s:include  value="/WEB-INF/jsp/public/pager.jsp"/></td>
		   </tr>
		</table>
		</form>
		<div id="question" style="display:none"></div>
	</body>
</html>