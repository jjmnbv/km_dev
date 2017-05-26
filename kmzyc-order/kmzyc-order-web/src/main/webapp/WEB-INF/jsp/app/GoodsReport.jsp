<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品销售分析表</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css" href="/etc/css/block.css">
<link rel="stylesheet" type="text/css" href="/etc/css/jq.css">
<link rel="stylesheet" type="text/css" href="/etc/css/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="/etc/css/autocompletestyles.css">
<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="/etc/js/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript" src="/etc/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="/etc/js/chili-1.7.pack.js"></script>
<script type="text/javascript" src="/etc/js/jquery.blockUI.js"></script>
<script type="text/javascript" src="/etc/js/urchin.js"></script>
<script type="text/javascript" src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript" src="/etc/js/jquery.metadata.js"></script>
<script type="text/javascript" src="/etc/js/messages_cn.js"></script>
<script src="/etc/js/97dater/WdatePicker.js"></script>
<script type="text/javascript" src="/etc/autocomplete/jquery.autocomplete.js"></script>
		<script>
			var isSub=false;
			function exportFile(){
				if(isSub){
					return;
				}
				isSub=true;
				var startDate=$('#startDate').val();
				var endDate=$('#endDate').val();
				var channel=$('#channel').val();
				var commerceId =$('#commerceId').val();
				var orderSource=$('#orderSource').val();
				var bCategoryId=$('#categoryId1').val();
				var mCategoryId=$('#categoryId2').val();
				var sCategoryId=$('#categoryId3').val();
				var commoditySku=$('#commoditySku').val();
				var supplier=$('#supplier').val();
				var orderPreferentialCode=$('#orderPreferentialCode').val();
				var payMethodWayStr=$('#payMethodWayStr').val();
				
				$.ajax({
		            async: false,
		            url: '/app/goodsReport.action',
		            cache:false,
		            type:'POST',
		            data: { "map['startDate']": startDate,
			                "map['endDate']": endDate,
			            	'channel': channel,
			            	'commerceId':commerceId,
			            	'orderSource':orderSource,
						    'bCategoryId':bCategoryId,
						    'mCategoryId':mCategoryId,
							'sCategoryId':sCategoryId,
							'commoditySku':commoditySku,
							'supplier':supplier,
							'orderPreferentialCode':orderPreferentialCode,
							'payMethodWayStr':payMethodWayStr
						   },
		            success: function (data) {
			            try{
		            	var start=data.indexOf('\'path\':\'');
		            	var end=data.indexOf('.xlsx');
		            	var url=data.substring(start+8,end+5);
		            	$('#downLoadLink').attr('href',url).find('span').click();
			            }catch(e){
			            	alert('生成报表出错！');
				        }
		            	isSub=false;
		            },
		            error:function(){
						alert('生成报表出错！');
			        }
		        });
			}
			
			
			function change2(sourceCategoryId,targetCategoryId){
				
				var categoryHtml = '';
				if(targetCategoryId=='categoryId2'){
					categoryHtml = '<option value="">--二级类目--</option>';
				}else if(targetCategoryId=='categoryId3'){
					categoryHtml = '<option value="">--三级类目--</option>';
				}
				
				if($('#'+sourceCategoryId).val()==""){
					$('#'+targetCategoryId).html(categoryHtml);
					if(sourceCategoryId=='categoryId1'){
						$('#categoryId3').html('<option value="">--三级类目--</option>');
					}
					return;
				}
				
				$.ajax({
					dataType:'json',
					url:'/app/selectCategory.action?id='+$('#'+sourceCategoryId).val(),
					error:function(){alert('请求失败，请稍后重试或与管理员联系！')},
					success:function(date){
						var categoryList = date.categoryList;
						var size = categoryList.length;
						
						for(var i=0;i<size;i++){
							categoryHtml += '<option value="'+categoryList[i].categoryId+'">'+categoryList[i].categoryName+'</option>';
						}
						$('#'+targetCategoryId).html(categoryHtml);
					}
				});
			}
			
			$(function(){
				var countriesArray;
				   $.ajax({
						//async:'false',
						url:'selectSupplierListByName.action',
						success:function(data){
							  countriesArray = $.map(data, function (value, key) { 
								return { value: value, name: key }; 
								});
							  
							  $("#supplier").bind("change",function(){
								  $("#hid_supplier_id").val(null);
								})
							$('#supplier').autocomplete({
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
			});
			
		</script>
</head>
<body>
	<a href="#" id="downLoadLink" style="display: none;" target="_blank"><span id="downLoadSp" ></span></a>
	<s:set name="parent_name" value="'业务操作'" scope="request" />
	<s:set name="name" value="'订单报表'" scope="request" />
	<s:set name="son_name" value="'商品销售分析表'" scope="request" />
	<s:include value="/WEB-INF/jsp/public/title.jsp" />
	<s:form action="goodsReportAction" method="post" name="GoodsReportForm">
		<table width="98%" align="center" border="0" class="table_search">
			<tr>
				<td align="right">开始时间</td>
				<td><input class="Wdate condition"
					id="startDate" name="map['startDate']"
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endDate\'),\'%y-%M-%d %H:%m:%s\'}'})"
					type="text" value='<s:property value="map['startDate']"/>'>
				</td>
				<td align="right" >结束时间</td>
				<td><input class="Wdate condition"
					id="endDate" name="map['endDate']"
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d %H:%m:%s',minDate:'#F{$dp.$D(\'startDate\')}'})"
					type="text" value='<s:property value="map['endDate']"/>'></td>
					
				<td align="right">销售类型：</td>
				<td>	
		           <span >	
				   <select id="commerceId"  name="commerceId" value='<s:property value="map['commerceId']"/>'">
				       	<option value="" <s:if test='map["commerceId"]==""'>selected="selected"</s:if> >全部</option>
				   		<option value="1" <s:if test='map["commerceId"]=="1"'>selected="selected"</s:if> >自营</option>
				   		<option value="2" <s:if test='map["commerceId"]=="2"'>selected="selected"</s:if> >第三方</option>
				   </select>
				   </span>
				</td>
				<tr>
				<%--删除 <td align="right">渠道</td>
				<td>
					<select id="channel" name="channel">
						<option value="-1">-----请选择-----</option>
						<s:iterator value="channelList" var="channel">
						<s:property value="#channel.orderDictionaryCode"/>
							<option value="<s:property value='#channel.orderDictionaryCode'/>" <s:if test='map["channel"]==#channel.orderDictionaryCode'>selected="selected"</s:if> ><s:property value='#channel.orderDictionaryValue'/></option>
						</s:iterator>
					</select>
				</td> --%>
				
				<td align="right">订单来源</td>
				<td>	
		           	<select  id="orderSource" name="orderSource">
		           		<option value="" <s:if test='map["orderSource"]==""'>selected="selected"</s:if> >全部</option>
		           		<option value="1" <s:if test='map["orderSource"]=="1"'>selected="selected"</s:if> >网页</option>
		           		<option value="2" <s:if test='map["orderSource"]=="2"'>selected="selected"</s:if> >APP</option>
		           		<!-- <option value="3" <s:if test='map["orderSource"]=="3"'>selected="selected"</s:if> >微信</option> -->
		           		<option value="4" <s:if test='map["orderSource"]=="4"'>selected="selected"</s:if> >WAP</option>
		           	</select>
				</td>
		    <td align="right">类别：</td>
			   <td align="left style="width:300px">
				<s:select list="#request.categoryList"
				name="bCategoryId" id="categoryId1" listKey="categoryId" listValue="categoryName"
				headerKey="" headerValue="--一级类目--"
				onchange="change2('categoryId1','categoryId2');"></s:select> 
				<s:select list="#request.mCategoryList"
				name="mCategoryId" id="categoryId2"  listKey="categoryId" listValue="categoryName" 
				headerKey="" headerValue="--二级类目--"
				onchange="change2('categoryId2','categoryId3');"></s:select> 
				<s:select list="#request.sCategoryList" id="categoryId3" 
				headerKey="" headerValue="--三级类目--" 
				name="sCategoryId"  listKey="categoryId" listValue="categoryName"></s:select>
			</td>	
			
			<tr>
				
			
			<td align="right">商品SKU：</td>
		        <td>
				    <input  id="commoditySku" name="commoditySku" type="text" value='<s:property value="map['commoditySku']"/>'>
				</td>				
			<td align="right">供应商:</td>
		           <td>
						<input id="supplier" name="supplier"  type="text" value='<s:property value="map['supplier']"/>'>

					</td>
								
			<td align="right">活动ID：</td>
				<td>
				     <input  id="orderPreferentialCode" name="orderPreferentialCode" type="text" value='<s:property value="map['orderPreferentialCode']"/>'>
				</td>
			</tr>
			<tr>
			<td align="right">支付方式</td>
				<td>
						<select id="payMethodWayStr" name="payMethodWayStr">
							<option value="-1">-----请选择-----</option>
							<s:iterator value="payMethodList" var="dict">
							<s:property value="#dict.orderDictionaryValue"/>
								<option value="<s:property value='#dict.orderDictionaryValue'/>" <s:if test='map["payMethodWayStr"]==#dict.orderDictionaryValue'>selected="selected"</s:if> ><s:property value='#dict.orderDictionaryValue'/></option>
							</s:iterator>
						</select>
					</td>
				<td colspan="6" align="right">
					<input type="reset" value=" 重置 " class="btn-custom"/>&nbsp;&nbsp;
					<input type="submit" class="queryBtn" value=""/>&nbsp;&nbsp;
 					<input type="button" value="导出查询结果" class="btn-custom" onClick="exportFile()">&nbsp;&nbsp; 
				</td>
			</tr>
		</table>
		<br/>
		<br/>
		<b>&nbsp;&nbsp;&nbsp;&nbsp;订单总金额：<s:property value="totalMoney"/>&nbsp;&nbsp;&nbsp;&nbsp;实付总额：<s:property value="totalActual"/></b>
		<table class="list_table" width="98%" align="center" cellpadding="3"
			cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc"
			style="border-collapse: collapse; font-size: 12px">
			<tr>
		     	<th>商品SKU</th>    
			    <th>商品名称</th>
			    <th>供应商</th>
			    <!-- <th>品牌名</th> -->
			    <th>一级类目</th>
			    <th>二级类目</th>
			    <th>三级类目</th>
			    <th>销售类型</th>
			    <th>下单账号</th>
			    <!-- <th>订单号</th> -->
			    <th>下单时间</th>
			    <th>订单来源</th>
			    <th>下单类型</th>
			    <!-- <th>订单类型</th> -->
			    <th>订单金额</th>
			    <th>商品实收金额</th>
			    <th>支付方式</th>
			    <th>商品售价</th>
<!-- 			    <th>成本单价</th>
			    <th>商品数量</th>
			    <th>成本小计</th>
			    <th>佣金比例</th>
			    <th>实收佣金</th> -->
			    <th>是否活动</th>
<!-- 			    <th>退货数</th>
			    <th>金额</th>   -->
    
			</tr>
			<s:iterator value="page.dataList" id="map">
				<tr>
					<td>
						<s:property value="#map.COMMODITY_SKU"/>
					</td>
					<td>
						<s:property value="#map.PROCUCT_NAME"/>
					</td>
					<td>
						<s:property value="#map.SUPPLIER_NAME"/>
					</td>
					<%-- <td>
						<s:property value="#map.BRAND_NAME"/>
					</td> --%>
					<td>
						<s:property value="#map.C1"/>
					</td>
					<td>
						<s:property value="#map.C2"/>
					</td>
					<td>
						<s:property value="#map.C3" />
					</td>
					<td>
						<s:property value="#map.SUPPLIER_TYPE_STR"/>
					</td>
					<td>
						<s:property value="#map.CUSTOMER_ACCOUNT"/>
					</td>
					<%-- <td>
						<s:property value="#map.ORDER_CODE"/>
					</td> --%>
					<td>
					   <%--  <s:date name="#map.CREATE_DATE" format="yyyy-MM-dd HH:mm:ss" /> --%>
						<s:property value="#map.CREATE_DATE_STR"/>
					</td>
					<td>
						<s:property value="#map.ORDER_SOURCE_STR"/>
					</td>
					<td>
						<s:property value="#map.ORDER_PURCHASER_TYPE_STR"/>
					</td>
					<%-- <td>
						<s:property value="#map.ORDER_TYPE_STR"/>
					</td> --%>
					<td>
						<s:property value="#map.AMOUNT_PAYABLE"/>						
					</td>
					<td>
						<s:property value="#map.COMMODITY_INCOMING_SUM"/>
					</td>
					<td>
						<s:property value="#map.PAYMENT_WAY_STR"/>
					</td>
					<td>
						<s:property value="#map.COMMODITY_CALLED_PRICE"/>
					</td>
<%-- 					<td>
						<s:property value="#map.COMMODITY_COST_PRICE"/>
					</td> --%>
<%-- 					<td>
						<s:number name="#map.COMMODITY_NUMBER"/>
					</td>
					<td>
						<s:property value="#map.COST_SUM"/>
					</td>					
					<td>
						<s:property value="#map.COMMISSION_RATE"/>
					</td>
					<td>
						<s:property value="#map.COMMISSION_MONEY"/>
					</td> --%>
					<td>
						<s:property value="#map.ORDER_PREFERENTIAL_CODE"/>
					</td>
<%-- 					
					<td>
						<s:number name="#map.ALTER_NUM"/>
						<s:number name="null"/>
					</td>
					<td>
						<s:property value="#map.ALTER_MONEY_SUM"/>
					</td>	 --%>				
				</tr>
			</s:iterator>
		</table>
		<br/>
		<table class="page_table" width="98%" align="center">
		   <tr>
		     <td align="right"><s:include  value="/WEB-INF/jsp/public/pager.jsp"/></td>
		   </tr>
		</table>
	</s:form>
</body>
<script type="text/javascript">
	function exportQueryResult(){
		
	}
</script>
</html>