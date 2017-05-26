<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script src="/etc/js/jquery-latest.pack.js"></script>
<script src="/etc/js/dialog.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<script src="/etc/js/jquery-1.8.3.js"></script>
<style type="text/css">
	.eidt_rowTitle{padding:0px;}
</style>
</head>
<body>


<!-- 导航栏 -->
<s:set name="parent_name" value="'仓库管理'" scope="request"/>
<s:set name="name" value="'退货单'" scope="request"/>
<s:set name="son_name" value="'退货单审核'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

<s:form action="/app/editReturnNotesResult.action" method="POST" namespace='/app' id="frm" name='frm'>

<s:if test='order.status == 1'>
	<s:set name="statusName" value="'确认到货'" scope="request"/>
	<s:property value="#statusName" />
</s:if>
<s:if test='order.status == 2'>
	<s:set name="statusName" value="'进行质检'" scope="request"/>
</s:if>
<s:if test='order.status == 3'>
	<s:set name="statusName" value="'同意退货'" scope="request"/>
</s:if>

<!-- 数据编辑区域 -->
<table width="65%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse;font-size:12px;">
	<tr> 
		<th colspan="4" align="left" class="edit_title">退货单信息</th>
		<s:hidden name="order.returnId" id="returnId" ></s:hidden>
		<s:hidden name="order.custName" ></s:hidden>
		<s:hidden name="order.productNo" ></s:hidden>
		<s:hidden name="order.productId" ></s:hidden>
		<s:hidden name="order.productName" ></s:hidden>
		<s:hidden name="order.productSkuId" ></s:hidden>
		<s:hidden name="order.productSku" ></s:hidden>
		<s:hidden name="order.productCounts" ></s:hidden>
		<s:hidden name="order.orderdetailId" ></s:hidden>
		<s:hidden name="order.orderType" ></s:hidden>
		<s:hidden name="order.orderCode" id="orderCode" ></s:hidden>
		<s:hidden name="order.warehouseId" ></s:hidden>
		<s:hidden name="order.unitPrice" ></s:hidden>
		<s:hidden name="order.totalPrice" ></s:hidden>
		<s:hidden id="orderStatus" name="order.status" ></s:hidden>
		<s:hidden id="handleResult" name="order.handleResult" ></s:hidden>
	</tr>
	<tr> 
		<th align="right" class="eidt_rowTitle" width="150" >退换货单编号：</th>
		<td width="200" > 
			<s:property value='order.orderCode'/>
		</td>
		<th align="right" class="eidt_rowTitle" width="150" >客户名称：</th>
		<td width="200" > 
			<s:property value='order.custName'/>
		</td>
	</tr>
	<tr> 
		<th align="right" class="eidt_rowTitle">产品编号：</th>
		<td> 
			<s:property value='order.productNo'/>
		</td>
		<th align="right" class="eidt_rowTitle">产品名称：</th>
		<td> 
			<s:property value='order.productName'/>
		</td>
	</tr>
	<tr> 
		<th align="right" class="eidt_rowTitle">产品SKU：</th>
		<td> 
			<s:property value='order.productSku'/>
		</td>
		<th align="right" class="eidt_rowTitle">退货数量：</th>
		<td> 
			<s:property value='order.productCounts'/>
		</td>
	</tr>
	<tr> 
		<th align="right" class="eidt_rowTitle">产品出库条码：</th>
		<td id="tdBarCode" >
			<s:if test='order.status == 1 and order.handleResult >= 2 '>
				<input name="order.barCode" id="inputBarCode" class="input_style" />
			</s:if>
			<s:else>
				<s:property value='order.barCode'/>
			</s:else>
		</td>
		<th align="right" class="eidt_rowTitle">类型：</th>
		<td> 
			<s:if test="order.orderType == 1">退货</s:if>
			<s:else>换货</s:else>
		</td>
	</tr>
	<s:if test='order.handleResult == 3'>
		<tbody id="checkDiv" <s:if test='order.status lt 3'>style="display:none;"</s:if> >
			<tr> 
				<th align="right" class="eidt_rowTitle">客户问题说明：</th>
				<td colspan="3">
					<s:property value="order.productDesc" />
				</td>
			</tr>
			<tr> 
				<th align="right" class="eidt_rowTitle">外观情况：</th>
				<td colspan="3">
					<textarea rows="5" cols="50" name="order.exteriorState" style="resize: none;"></textarea>
				</td>
			</tr>
			<tr> 
				<th align="right" class="eidt_rowTitle">包装情况：</th>
				<td colspan="3">
					<textarea rows="5" cols="50" name="order.packageState" style="resize: none;"></textarea>
				</td>
			</tr>
			<tr> 
				<th align="right" class="eidt_rowTitle">检测结果：</th>
				<td colspan="3">
					<textarea rows="5" cols="50" name="order.detectResult" style="resize: none;"></textarea>
				</td>
			</tr>
			<tr> 
				<th align="right" class="eidt_rowTitle">检测是否存在故障：</th>
				<td> 
					<s:select list="#{0:'否',1:'是'}" name="order.detectisFault" ></s:select>
				</td>
				<th align="right" class="eidt_rowTitle">检测故障是否人为：</th>
				<td> 
					<s:select list="#{0:'否',1:'是'}" name="order.faultIspeople" ></s:select>
				</td>
			</tr>
			<tr> 
				<th align="right" class="eidt_rowTitle">手续费：</th>
				<td colspan="3">
					<input name="order.maintenanceCost" id="maintenanceCost" />
				</td>
			</tr>
		</tbody>
		<tr> 
			<th align="right" class="eidt_rowTitle">备注：</th>
			<td colspan="3">
				<textarea rows="5" cols="50" name="order.remark" style="resize: none;" ></textarea>
			</td>
		</tr>
	</s:if>
	<s:else>
		<s:if test='order.status gt 3'>
			<tr> 
				<th align="right" class="eidt_rowTitle">客户问题说明：</th>
				<td colspan="3">
					<s:property value="order.productDesc" />
				</td>
			</tr>
			<tr> 
				<th align="right" class="eidt_rowTitle">外观情况：</th>
				<td colspan="3">
					<s:property value="order.exteriorState" />
				</td>
			</tr>
			<tr> 
				<th align="right" class="eidt_rowTitle">包装情况：</th>
				<td colspan="3">
					<s:property value="order.packageState" />
				</td>
			</tr>
			<tr> 
				<th align="right" class="eidt_rowTitle">检测结果：</th>
				<td colspan="3">
					<s:property value="order.detectResult" />
				</td>
			</tr>
			<tr> 
				<th align="right" class="eidt_rowTitle">检测是否存在故障：</th>
				<td> 
					<s:if test='order.detectisFault == 0'>否</s:if><s:else>是</s:else>
				</td>
				<th align="right" class="eidt_rowTitle">检测故障是否人为：</th>
				<td> 
					<s:if test='order.faultIspeople == 0'>否</s:if><s:else>是</s:else>
				</td>
			</tr>
			<tr> 
				<th align="right" class="eidt_rowTitle">手续费：</th>
				<td colspan="3">
					<s:property value="order.maintenanceCost" />
				</td>
			</tr>
		</s:if>
		<tr> 
			<th align="right" class="eidt_rowTitle">备注：</th>
			<td colspan="3">
				<s:property value="order.remark" />
			</td>
		</tr>
	</s:else>
</table>

<!-- 底部 按钮条 -->
<table width="65%" align="center" class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0" style="font-size:12px;">
	<tr> 
		<td align="center">
		<s:if test="order.status == 1">
			<input type="button" class="btn-custom btnStyle" id="btnChange" value="确认到货" onClick="changeStatus(1);" />
			<input type="button" class="btn-custom btnStyle" id="btnRefuse" value="拒绝退货" onClick="changeStatus(2);"
                   style="display: none"/>
		</s:if>
		<s:elseif test="order.status == 2">
			<input type="button" class="btn-custom btnStyle" id="btnChange" value="进行质检" onClick="changeStatus(1);" />
			<input type="button" class="btn-custom btnStyle" id="btnRefuse" value="拒绝退货" onClick="changeStatus(2);"
                   style="display: none"/>
		</s:elseif>
		<s:elseif test="order.status == 3">
			<input type="button" class="btn-custom btnStyle" id="btnChange" value="同意退货" onClick="changeStatus(1);" />
			<input type="button" class="btn-custom btnStyle" id="btnRefuse" value="拒绝退货" onClick="changeStatus(2);" />
		</s:elseif>
			<input type="button" class="backBtn" onClick="gotoList()" />
		</td>
		<td width="20%" align="center"></td>
	</tr>
</table>

</s:form>
<script language="JavaScript">
function gotoList(){
    location.href="/app/findAllReturnNotes.action";
}

function changeStatus(arg){
	if(arg==1){
		if ($("#btnChange").val() == "确认到货") {
			$.post(
				"/app/editReturnNotesStatus.action",
				{
					orderCode:$("#orderCode").val(),
					returnId:$("#returnId").val(),
					inputBarCode:$("#inputBarCode").val(),
					orderStatus:'2'
				},
				function(data){
					if("success"==data){
						$("#orderStatus").val("2");
						$("#btnChange").val("进行质检");
						$("#tdBarCode").html($("#inputBarCode").val());
					}else{
						alert("系统发生错误，请确认客户是否已发货或联系管理员！");
					}
				}
			);
		} else if (($("#btnChange").val() == "进行质检")) {
			$.post(
				"/app/editReturnNotesStatus.action",
				{
					returnId:$("#returnId").val(),
					orderStatus:'3'
				},
				function(data){
					if("success"==data){
						$("#orderStatus").val("3");
						$("#btnChange").val("同意退货");
						$("#btnRefuse").css("display","");
						document.getElementById("checkDiv").style.display = "";
					}else{
						alert("系统发生错误，请联系管理员！");
					}
				}
			);
		} else if (($("#btnChange").val() == "同意退货")){
			$("#orderStatus").val("4");
			$("#handleResult").val("1");
			if($("#maintenanceCost").val()==""){
				$("#maintenanceCost").val("0");
			}else{
				var priceTest = /^\d+(\.?\d{1,2})?$/;
				if(!priceTest.test($("#maintenanceCost").val())){
					alert("请输入正确的价格，且请不要超过2位小数！");
					$("#maintenanceCost").select();
					return;
				}
			}
			
			$.post(
				'/app/editReturnNotesResult.action',
				$("#frm").serializeArray(),
				function(data){
					if("1" == data){
						alert("操作成功！");
						location.href="/app/findAllReturnNotes.action";
					}
					if("0" == data){
						if(confirm("该商品库存不足，请联系客户后，确定是否转为退货单？\r\n确定则转为退货单，取消则流程不再进行，请在商品有库存时，再执行换货！")){
							$.post(
								'/app/editReturnNotesResult.action?changeReturnOrder=true',
								$("#frm").serializeArray(),	
								function(data){
									if("1" == data){
										alert("操作成功！");
										location.href="/app/findAllReturnNotes.action";
									}
									if("-1" == data){
										alert("系统发生错误，请稍后再试或联系管理员！");
									}
								}
							);
						}
					}
					if("-1" == data){
						alert("系统发生错误，请稍后再试或联系管理员！");
					}
				}
			);
			
		}
	}else{
		if($("#orderStatus").val() == "1"){
			alert("请先确认商品是否已收到！");
			return;
		}
		
		if($("#orderStatus").val()=="3"){
			$("#orderStatus").val("5");
		}
		$("#maintenanceCost").val("0");
		$("#handleResult").val("0");
		$.post(
			'/app/editReturnNotesResult.action',
			$("#frm").serializeArray(),	
			function(data){
				if("1" == data){
					alert("操作成功！");
					location.href="/app/findAllReturnNotes.action";
				}
				if("-1" == data){
					alert("系统发生错误，请稍后再试或联系管理员！");
				}
			}
		);
	}
}
</script>
</body>
</html>