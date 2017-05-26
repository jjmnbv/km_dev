<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>风险评估</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="/etc/css/block.css">
		<link rel="stylesheet" type="text/css" href="/etc/css/jq.css">
		<link rel="stylesheet" type="text/css" href="/etc/css/jquery-ui.css">
		<link rel="stylesheet" type="text/css" href="/etc/css/autocompletestyles.css">
		<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
		<script type="text/javascript" src="/etc/js/jquery-migrate-1.2.1.min.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.validate.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.metadata.js"></script>
		<script type="text/javascript" src="/etc/js/messages_cn.js"></script>
		<script type="text/javascript" src="/etc/js/jquery-ui.min.js"></script>
		<script type="text/javascript" src="/etc/js/chili-1.7.pack.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.blockUI.js"></script>
		<script type="text/javascript" src="/etc/js/urchin.js"></script>
		<script type="text/javascript" src="/etc/js/messages_cn.js"></script>
		<script type="text/javascript" src="/etc/autocomplete/jquery.autocomplete.js"></script>
		<script  src="/etc/js/97dater/WdatePicker.js"></script>
		<script type="text/javascript">
		$(function(){
			$(".moreCondition").hide();
		  	$("#lessButton").hide();

			$(".cleanCheck").click(function(){
				cleanCheck();
			});
			
			$("#orderListSearch").validate({   
			     rules: {
					"map['orderCode']": {commonChar:true},
					"map['commodityName']": {unusualChar:true},
					"map['commodityCode']":{num1:true},
					"map['consigneeName']": {unusualChar:true},
					//"map['consigneeMobile']": {cellphone:true},
					"map['agencyNumber']":{num1:true},
					"map['customerAccount']": {unusualChar:true},
					"map['stockOutID']": {num1:true},
					"map['settlementNo']":{num1:true},
					"map['distributionId']":{num1:true}
				},submitHandler: function (form) {
 					$("#pageNo").val(1);
 					var pds= $('#payDateStart').val();
					var pde= $('#payDateEnd').val();
 					if(pds.length>0&&0==pde.length){
						alert('请选择支付结束时间！');
						return;
	 				}else if(pde.length>0&&0==pds.length){
	 					alert('请选择支付开始时间！');
						return;
		 			}
		        	form.submit();
		   		}
			});
			$("#payDateRange").change(function(){
				$('#payDateStart').val(""); 
				$('#payDateEnd').val("");
			});
			$("#createDateRange").change(function(){
				$('#createDateStart').val(""); 
				$('#createDateEnd').val("");
			});
		
			function recover(){
				$(".condition").not(":disabled").val("");
			};

			// 清空
			$("#clean").click(function(){
				recover();
			});

			//更多条件
			$("#moreButton").click(function(){
				$(".moreCondition").show();
				$("#lessButton").show();
				$("#moreButton").hide();
			});

			//更少条件
			$("#lessButton").click(function(){
				$(".hideCondition").val("");
				$(".moreCondition").hide();
				$("#lessButton").hide();
				$("#moreButton").show();
			});

			 $("#select_other_support").click(function(){
				  if($("#select_other_support").val()=="2"){
					  $("#commerceNameSpan").css('display', 'block');
					  $("#commerceNameDiv").css('display', 'block');
					  }
				  if($("#select_other_support").val()=="" || $("#select_other_support").val()=="1"){
					  $("#commerceNameSpan").css('display', 'none');
					  $("#commerceNameDiv").css('display', 'none');
					  $("#autocomplete").val(null);
					  }
				  });

			  // 选择所有
			  $("#checkAll").click(function(){
				  alert($(this).attr("checked")=="checked");
				  if($(this).attr("checked")=="checked"){
					  //$(":checkbox").not($("#checkAll")).removeAttr("checked");
					  $(":checkbox").not($(this)).removeAttr("checked");
				  }else{
					  $(":checkbox").not($(this)).attr("checked","checked");
					  //$(":checkbox").not($("#checkAll")).attr("checked","checked");
				  }
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
							  //$("#hid_supplier_id").val(null);
							});
						$('#autocomplete').autocomplete({
					    	lookup: countriesArray,
					      	minChars: 0 ,
					      //  minLength: 1,
					        onSelect: function (value) {
					        	//$("#hid_supplier_id").val(value.name);
					        }
					    });
					},
					dataType:'json'
					
				});
			
		});  
		</script>
	</head>
	<body>
		<s:set name="parent_name" value="'订单风控'" scope="request"/>
		<s:set name="name" value="'风险评估'" scope="request"/>
		<s:include value="/WEB-INF/jsp/public/title.jsp"/>
		<form id="orderListSearch" action="/app/gotoOrderRiskList.action" method="POST">

		<table class="table_search" width="98%" align="center" cellpadding="0" cellspacing="0" >
			<tr>
				<th align="right">订单号：</th>
		        <td><input class="condition" name="map['orderCode']" type="text" value='<s:property value="map['orderCode']"/>' /></td>
				<th align="right">订单状态：</th>
		        <td>
		           	<select class="condition" name="map['status']" style="width:156px">
						<option value="" <s:if test='map["status"]==""'>selected="selected"</s:if> >全部</option>
				 		<option value="21" <s:if test='map["status"]==21'>selected="selected"</s:if>>待风险评估</option>
				 		<option value="22" <s:if test='map["status"]==22'>selected="selected"</s:if>>风控通过</option>
				 		<option value="-3" <s:if test='map["status"]==-3'>selected="selected"</s:if>>异常订单</option>
					</select>
				</td>
			</tr>
			<tr>		
				<th align="right">下单账号：</th>
		        <td><input class="condition" name="map['customerAccount']" type="text" value='<s:property value="map['customerAccount']"/>'></td>
				<th align="right">收货人：</th>
		        <td><input class="condition" name="map['consigneeName']" type="text" value='<s:property value="map['consigneeName']"/>'></td>
				<th align="right">收货电话：</th>
		        <td><input class="condition" name="map['consigneeMobile']" type="text" value='<s:property value="map['consigneeMobile']"/>'></td>
			</tr>
			<tr>
				<th align="right">下单时间从：</th>

		        <td><input class="Wdate condition" id="createDateStart" name="map['createDateStart']" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'createDateEnd\'),\'%y-%M-%d %H:%m:%s\'}'})" type="text" value='<s:property value="map['createDateStart']"/>'></td>
				<th align="right">下单时间到：</th>
	
		        <td><input class="Wdate condition" id="createDateEnd" name="map['createDateEnd']" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d %H:%m:%s',minDate:'#F{$dp.$D(\'createDateStart\')}'})" type="text" value='<s:property value="map['createDateEnd']"/>'></td>
				<th align="right">下单时间范围：</th>
		        <td>
				  	<select class="condition" id="createDateRange" name="map['createDateRange']" style="width:156px">
				 		<option value="" <s:if test='map["createDateRange"]==""'>selected="selected"</s:if> >全部</option>
				 		<option value="today" <s:if test='map["createDateRange"]=="today"'>selected="selected"</s:if> >今天</option>
				 		<option value="ysday" <s:if test='map["createDateRange"]=="ysday"'>selected="selected"</s:if> >昨天</option>
				 		<option value="tsweek" <s:if test='map["createDateRange"]=="tsweek"'>selected="selected"</s:if> >本周</option>
				 		<option value="lsweek" <s:if test='map["createDateRange"]=="lsweek"'>selected="selected"</s:if> >上周</option>
				 		<option value="tsmonth" <s:if test='map["createDateRange"]=="tsmonth"'>selected="selected"</s:if> >本月</option>
				 		<option value="lsmonth" <s:if test='map["createDateRange"]=="lsmonth"'>selected="selected"</s:if> >上月</option>
				 	</select>
				</td>
			</tr>
			<tr>
				<th align="right">支付时间从：</th>
		     
		        <td><input class="Wdate condition" id="payDateStart" name="map['payDateStart']" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'payDateEnd\'),\'%y-%M-%d %H:%m:%s\'}'})" type="text" value='<s:property value="map['payDateStart']"/>'></td>
				<th align="right">支付时间到：</th>
		    
		        <td><input class="Wdate condition" id="payDateEnd" name="map['payDateEnd']" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d %H:%m:%s',minDate:'#F{$dp.$D(\'payDateStart\')}'})" type="text" value='<s:property value="map['payDateEnd']"/>'></td>
				<th align="right">支付时间范围：</th>
		        <td>
		        	<select class="condition" id="payDateRange" name="map['payDateRange']" style="width:156px">
				 		<option value="" <s:if test='map["payDateRange"]==""'>selected="selected"</s:if> >全部</option>
				 		<option value="today" <s:if test='map["payDateRange"]=="today"'>selected="selected"</s:if> >今天</option>
				 		<option value="ysday" <s:if test='map["payDateRange"]=="ysday"'>selected="selected"</s:if> >昨天</option>
				 		<option value="tsweek" <s:if test='map["payDateRange"]=="tsweek"'>selected="selected"</s:if> >本周</option>
				 		<option value="lsweek" <s:if test='map["payDateRange"]=="lsweek"'>selected="selected"</s:if> >上周</option>
				 		<option value="tsmonth" <s:if test='map["payDateRange"]=="tsmonth"'>selected="selected"</s:if> >本月</option>
				 		<option value="lsmonth" <s:if test='map["payDateRange"]=="lsmonth"'>selected="selected"</s:if> >上月</option>
				 	</select>
				</td>
			</tr>
			<tr class="moreCondition">
				<th align="right">商品名称：</th>
		        <td><input class="condition hideCondition" name="map['commodityName']" type="text" value='<s:property value="map['commodityName']"/>'></td>	
				<s:if test='map["commerceId"]=="2"'>
				<th align="right"><span class="moreCondition">所属商家：</span></th>
				<td>
				<div id="commerceNameDiv">	
		        	<input id="autocomplete" name="map['commerceName']" value='<s:property value="map['commerceName']"/>' type="text" class="condition hideCondition">
	 			</div>
				</s:if>
				<s:else>
				<th align="right">客服账户：</th>
      			<td><input class="condition hideCondition" name="map['agencyNumber']" type="text" value='<s:property value="map['agencyNumber']"/>'></td>
				</s:else>
				<th align="right">商品编号：</th>
				<td><input class="condition hideCondition" name="map['commodityCode']" type="text" value='<s:property value="map['commodityCode']"/>'></td>
			</tr>
			<tr class="moreCondition">
				<th align="right"><span class="moreCondition">订单来源：</span></th>
				<td>
					<span class="moreCondition">
		           	<select class="condition hideCondition" name="map['orderSource']" value='<s:property value="map['orderSource']"/>' style="width:156px">
		           		<option value="" <s:if test='map["orderSource"]==""'>selected="selected"</s:if> >全部</option>
		           		<option value="1" <s:if test='map["orderSource"]=="1"'>selected="selected"</s:if> >网页</option>
		           		<option value="2" <s:if test='map["orderSource"]=="2"'>selected="selected"</s:if> >APP</option>
		           		<option value="4" <s:if test='map["orderSource"]=="4"'>selected="selected"</s:if> >WAP</option>
		           	</select>
	 				</span>
				</td>
				<th align="right"><span class="moreCondition">销售类型：</span></th>
				<td>
					<span class="moreCondition">	
					<select id="select_other_support" class="condition hideCondition" name="map['commerceId']" value='<s:property value="map['commerceId']"/>' style="width:156px">
					     	<option value="" <s:if test='map["commerceId"]==""'>selected="selected"</s:if> >全部</option>
					 		<option value="1" <s:if test='map["commerceId"]=="1"'>selected="selected"</s:if> >自营</option>
					 		<option value="2" <s:if test='map["commerceId"]=="2"'>selected="selected"</s:if> >第三方</option>
					</select>
					</span>
				</td>
				<s:if test='map["commerceId"] !="2"'>
				<th align="right"><span  id="commerceNameSpan" <s:if test='map["commerceName"]==""'> style="display:none" </s:if>><span class="moreCondition">所属商家：</span></span></th>
				<td>
					<div id="commerceNameDiv" <s:if test='map["commerceName"]==""'> style="display:none" </s:if>>	
					<input id="autocomplete" name="map['commerceName']" value='<s:property value="map['commerceName']"/>' type="text" class="condition hideCondition">
				 	</div>
				</td>
			 	</s:if>
			</tr>
			<tr>
				<th align="right"><span class="moreCondition">下单类型：</span></th>
		        <td>
		        	<span class="moreCondition">	
					<select class="condition hideCondition" name="map['orderPurchaserType']" value='<s:property value="map['orderPurchaserType']"/>' style="width:156px">
				 		<option value="" <s:if test='map["orderPurchaserType"]==""'>selected="selected"</s:if> >全部</option>
				 		<s:iterator id="type" value="purchaserTypes">
				 		<option value="<s:property value='#type.key'/>" <s:if test='map["orderPurchaserType"]==#type.key'>selected="selected"</s:if> ><s:property value="#type.value"/></option>
				 		</s:iterator>
					</select>
				 	</span>
				</td>
		
				<td colspan="2" >
                <input type="submit" class="queryBtn" value=""/>
					<input type="reset" value=" 重置 " class="btn-custom"/>
					<input type="button" value=" 清空 " id="clean"class="btn-custom"/>
					<input type="button" value="更多选项 " class="btn-custom" id="moreButton" />
					<input type="button" value="隐藏选项" class="btn-custom" id="lessButton" />
		        </td>
		        <s:if test='map["commerceId"]!="2"'>
		        <th><span class="moreCondition">客服处理:</span></th>
				<td>	
					<span class="moreCondition">
					<select class="condition hideCondition" id="handle" name="map['handle']" value='<s:property value="map['handle']"/>'  style="width:156px">
						<option value="" <s:if test='map["handle"]==""'>selected="selected"</s:if> >全部</option>
						<option value="1" <s:if test='map["handle"]=="1"'>selected="selected"</s:if> >未处理</option>
						<option value="2" <s:if test='map["handle"]=="2"'>selected="selected"</s:if> >已处理</option>
					</select>	
					</span>
				</td>
				</s:if>
			</tr>
		</table>
		<br/>
		<b>&nbsp;&nbsp;&nbsp;&nbsp;订单总金额：<s:property value="totalMoney"/>&nbsp;&nbsp;&nbsp;&nbsp;实付总额：<s:property value="totalActual"/></b>
		<table class="list_table" width="98%" align="center" cellpadding="3" cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc" style="border-collapse: collapse;font-size:12px">
			<tr>
		   		<th width="15%">订单号</th>
		   	<!-- 	<th width="5%">渠道</th> -->
		   		<th width="5%">来源</th>
		   		<th width="15%">销售类型</th>
		   		<th width="15%">下单账号</th>
		   		<th width="15%">收货人</th>
		   		<th width="5%">订单状态</th>
		   		<th width="5%">订单金额</th>
		   		<th width="10%">下单时间</th>
		   		<th width="5%">操作</th>
			</tr>
			<s:iterator id="order" value="page.dataList">
			<tr>
				<td>
		   		 	<s:if test="#order.orderStatus==16">&nbsp;&nbsp;&nbsp;&nbsp;[主]</s:if>
		   		 	<s:if test="#order.parentOrderCode!=null">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;┗</s:if>
		   		 		<a href="/app/orderItemdetailAction.action?orderCode=<s:property value='#order.orderCode'/>&map['byStatus']=<s:property value='map["byStatus"]'/>"><s:property value="#order.orderCode"/></a>
		   		 	<s:if test="null!=#order.orderOperationRemark"><br/><font style="color:red;"><s:property value="#order.orderOperationRemark"/></font></s:if>	
	   			</td>
	   		 	<%-- <td><s:property value="#order.orderChannel"/></td> --%>
	   		 	<td><s:property value="#order.orderSourceStr"/></td>
	   		 	<td><s:property value="null==#order.commerceId?'自营':'第三方'"/>
	   		 		<s:if test="null!=#order.commerceId && null!=#order.commerceName">[<s:property value="#order.commerceName"/>]</s:if>
	   		 	</td>
	   		 	<td><s:property value="#order.customerAccount"/></td>
	   		 	<td><s:property value="#order.consigneeName"/>
	   		 	<s:if test="null!=#order.consigneeMobile">[<s:property value="#order.consigneeMobile"/>]</s:if>
	   		 	</td>
	   		 	<td><s:property value="#order.orderStatusStr"/></td>
	   		 	<td><s:property value="#order.amountPayable"/></td>
	   		 	<td><s:date name="#order.createDate" format="yyyy-MM-dd HH:mm:ss" /></td>

 				<td><a href="/app/gotoOrderRiskDetail.action?orderCode=<s:property value='#order.orderCode'/>&preSearchMapStr=<s:property value='preSearchMapStr'/>
 				&prePageNo=<s:property value='prePageNo'/>&prePageSize=<s:property value='prePageSize'/>">评估</a></td>
<%--  				<td><a href="<s:url action="gotoOrderRiskDetail.action?" >   
                              <s:param name="orderCode" value ="#order.orderCode"  />
                             <s:param name="preSearchMap" value="preSearchMap" />
                            </s:url>"
                    > 评估</a></td> --%>
			</tr>
			</s:iterator>
		</table>
		<br/>
		<table class="page_table" width="98%" align="center">
			<tr>
		     	<td align="right"><s:include  value="/WEB-INF/jsp/public/pager.jsp"/></td>
		   	</tr>
		</table>
		</form>
	</body>
</html>