<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>产品管理</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<style type="text/css">
	.tableStyle1{font-size:12px;}
</style>
<!--<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>-->
<script type="text/javascript" src="/etc/js/jquery-latest.pack.js"></script>
<script type="text/javascript" src="/etc/js/jquery.form.js"></script>
<script type="text/javascript" src="/etc/js/product/product.js"></script>


<script type="text/javascript">
function coupsupply()
{
	 if(!checkIdSeled())
 		 {
 		 alert('请选择供应商！')
 		 }
 	 else
 		 {
   if (confirm("您确定要选择这些供应商吗？"))
	   {
		   
	 parent.closeThis();
	 var havechoosed = document.getElementById("haveChoosedSuplly").value;
	 $.ajax({
		type: "post",
		url: "/app/getIdreturnTableForSupply.action?supplyArry="+select_id+"&haveChoosedSuplly="+havechoosed,
		dataType : "json",
		success: function(data){
 		window.$ = window.parent.$;
		 $("#supplyContent").html("");
		  $("#editBody1").html("");
		$("#supplyContent").append(data);
		},
		error: function(){
			//请求出错处理
			alert('出错了')
		}});
	   }}}
 var select_name="";
 var select_id="";
    function checkIdSeled(){
		var r=false;
		var  b = "";var c="";
		$("input[name=productSkuCode]").each(function(){
			if( $(this).attr("checked") ){
				r=true;
			 	b=b+$(this).attr("value")+",";
				c=c+$(this).attr("id")+",";
				return ;
			}});
		select_name=b;
		select_id=c;
		return r;}
function doSearch()
{
	location.href="/app/chooseCouponProduct.action"; 
}

</script>

</head>
<body>
 
<s:form action="/app/chooseCouponSupply.action" method="POST"  namespace='/app' id="frm" name='frm'>
<input  type="hidden" name="haveChoosedSuplly" id="haveChoosedSuplly" value="<s:property value='haveChoosedSuplly' />" />
<!-- 查询条件区域 -->
<table  width="98%" class="content_table" align="center" height="90" cellpadding="0" cellspacing="0"  style=" margin-top:11px; ">
	<tr>
		
		<td width="45%"> 名称：<s:textfield name="suppliersInfo.corporateName" cssClass="input_style" id="productName" /> <INPUT TYPE="submit"   class="queryBtn" value=""></td>
		
	</tr>
    
    
    
</table>


<!-- 数据列表区域 -->
<table width="98%"  class="list_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
	<tr>
	    <th align="center" width="9%">
	    	
          <input type="checkbox" name="checkbox" id="checkbox"  onclick="checkAll(this,'skuId')">
        <label for="checkbox"></label></th>
		<th align="center" width="22%">供应商编码</th>
		<th align="center" width="13%">供应商名称</th>
		<th align="center" width="30%">描述</th>
	</tr>
	<s:iterator id="productiterator" value="page.dataList" status="st" >
	<tr>
	    <td align="center" width="9%">
			<input type="checkbox" name="productSkuCode"  id='<s:property value="supplierId"/>' calss="<s:property value='contactsName' />"
             value='<s:property value="contactsName"/>'>
		</td>
		<td align="center"><s:property value="supplierId" /></td>
		<td align="center"><s:property value="corporateName" /></td>
		<td align="center"><s:property value="saleProductDescribe" /></td>
		
		
	  </tr>
	</s:iterator>
</table>

<!-- 分页按钮区 -->
<table  width="98%" align="center" cellpadding="0" cellspacing="0">
    <tr>
	    <td>
			<%@ include file="/WEB-INF/jsp/public/pager.jsp"%>
		</td>
	</tr>
</table>

<table  width="98%" align="center" cellpadding="0" cellspacing="0">
    <tr>
	    <td style="text-align:center">
	    <input type="button" name="choose" id="ok_choose" class="btn-custom btnStyle" value="确定选择" onClick="coupsupply()"></td>
	</tr>
</table>
 




<br><br>


</s:form>
 
</BODY>
</HTML>