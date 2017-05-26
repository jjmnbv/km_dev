<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>生成结算单</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		
		
		<link rel="stylesheet" type="text/css" href="/etc/css/orderTab.css">
		 <link rel="stylesheet" type="text/css" href="/etc/css/block.css">
		<link rel="stylesheet" type="text/css" href="/etc/css/jq.css">
		<link rel="stylesheet" type="text/css" href="/etc/css/autocompletestyles.css">
		
<style type="text/css">


</style>
	</head>
	<body>


	
	<s:set name="parent_name" value="'商家结算管理 '" scope="request"/>
	<s:set name="name" value="'结算单管理'" scope="request"/>
	<s:set name="son_name" value="'生成结算单'" scope="request"/>
	<s:include value="/WEB-INF/jsp/public/title.jsp" />
	<div style="margin:10px">
			<button class="backBtn" id="return"></button>
			
			<input type="button" class="btn-custom"  id="createSettlement" value="生成结算单">
 	</div>
		

<div style="background-color: #c7e3f1;width: 74%;height: 60px;margin-left:5%;text-algin:center;font-family: '微软雅黑 Regular', '微软雅黑';
font-size: 12px;">
	   <p style="padding-left: 15px;margin-bottom: 0px;padding-top: 5px"><span>请选择商家和结算账期生成结算单</span><span>。</span></p>
	   <p style="padding-left: 15px;padding-top: -15px"><span>如所选对应结算单已经存在，则不允许重复生成或者覆盖，</span><span>如结算单金额存在任何问题，请在运营核对调整</span><span>环节操作</span><span>提交差异调整项修正。</span></p>
</div>
 <div id="maind" style="width:90%;margin-left:5%;text-algin:center;">
		

	<div id="content" style="border: 0px">
	<form action="createSettlementPage.action" method="POST" >
		
		 <table  width="100%" style="font-size:15px">
	   <tr >
		   <td align="right" width="20%" >对应结算商家：</td>
		   <td>
		 
 				<div id="commerceNameDiv" >	
		          <input id="autocomplete" name="supplierName" value='${supplierName }' type="text" >
				   </div>
		   
		   
		   </td>
	   </tr>
	    <tr >
		   <td align="right" width="20%" ></td>
		   <td>&nbsp; </td>
	   </tr>
	   
	    <tr >
		   <td align="right">要生成的结算账期：</td>
		   <td><select class="period_y" name="periodY" id="period_y">
		   	<option     value="">选择</option>
		   	<c:if test="${not empty dataList }">
		   	
		   		<c:forEach items="${dataList}" var="item">
		   			<option   <c:if test="${periodY == item }"> selected="selected" </c:if>  value="${item }">${item }</option>
		   		</c:forEach>
		   	</c:if>
		   
		   </select>
		   </td>
	   </tr>

		</table>
<br>	<br>	
		
		<table  width="100%" style="font-size:15px" id="periodTable">
	   	<c:if test="${not empty periodList }">
		   	
		   	
		   		<c:forEach items="${periodList}" var="item" varStatus="index">
		   			
		   			<c:if test="${index.index % 6 == 0 }">
		   			 <tr >
		   			</c:if>
							<td><input type="radio" class="period_radio"
								name="period_radio" 
								<c:if test="${ item.settlementPeriodFlag eq 'y' }">disabled="disabled" </c:if>
		   			  value="${item.settlementPeriod }"> ${item.settlementPeriod } </td>
		   		</c:forEach>
		   	
		   	<c:if test="${index.index % 6 == 0 }">
		   			 </tr>
		   			</c:if></c:if>
		   
	    <tr >
		   <td> </td>
	   </tr>

		</table>
		<input id="hid_supplier_id" type="hidden" name="sellerId" value="${sellerId }">
		<input id="period" type="hidden" name="period" value="">
	</form>
	
	</div>			
</div>

<div id="question" style="display:none"></div>

</body>
<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="/etc/js/jquery.blockUI.js"></script>
		<script type="text/javascript" src="/etc/autocomplete/jquery.autocomplete.js"></script>
<script type="text/javascript">
	 $(document).ready(function(){
			//返回
		    $('#return').click(function(){
		    	window.location.href="/app/listClearingOrders.action?flag=1";
		    });
			
			$(".period_y").bind("change",function(){
				
				
				$("form").submit();
			})
			
		    $('#createSettlement').click(function(){
		    	
		    	 var sellerId = $("#hid_supplier_id").val();
				var period = $("input[class='period_radio']:checked").val(); 
				
				
				 
				
				if(!sellerId || sellerId == ""){
					alert("请选择商家");return
				}
				if(!period || period == ""){
					alert("请选择帐期");return
				}
				
				 $("#period").val(period);	
				 
				   $.ajax({
						//async:'false',
						url:'generateSettlement.action',
						data:{period:period,sellerId:sellerId},
						success:function(result){
								alert(result);
								window.location.href="listClearingOrders.action?flag=1";
						},
						error:function(e){
							alert("生成结算单异常");
						}
				   });
		    });
			
			
			
		    var countriesArray;
			
			   $.ajax({
					//async:'false',
					url:'selectSupplierListByName.action',
					success:function(data){
						  countriesArray = $.map(data, function (value, key) { 
							return { value: value, name: key }; 
							});
						  
						  $("#autocomplete").bind("change",function(){
							  $("#hid_supplier_id").val(null);
							  $("#periodTable").remove();
							  $("#period_y").val(null);
							})
						$('#autocomplete').autocomplete({
					    	lookup: countriesArray,
					      	minChars: 0 ,
					      //  minLength: 1,
					        onSelect: function (value) {
					        	 $("#hid_supplier_id").val(value.name);
					        	
					        
					        }
					    })
					    
					
					    
					},
					dataType:'json'
					
				}); 
		    
			   function load(){
				   
				   $("form").submit();
			   }
		
		    
		});
	</script>
</html>