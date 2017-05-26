<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>产品关联查看列表</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<style type="text/css">
.tableStyle1 {
	font-size: 12px;
}
</style>
<script type="text/javascript" src="/etc/js/jquery-latest.pack.js"></script>
<script type="text/javascript" src="/etc/js/common.js"></script>

</head>
<body>

	<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>

	<s:set name="parent_name" value="'产品管理'" scope="request" />
	<s:set name="name" value="'产品关联'" scope="request" />
	<s:set name="son_name" value="'产品关联查看列表'" scope="request"></s:set>

	<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

	<s:form action="/basedata/prodSupplierShow.action" method="POST"
		id="frm" name='frm'>

	
			
<input type="hidden" id="rtnMsg" value="<s:property value="rtnMessage"/>" />
			
			


		<input type="hidden" name="product.productSkuId"
			value="<s:property value='tiedSade.mainSku' />" />

		<input type="hidden" name="tiedSade.mainSku"
			value="<s:property value='tiedSade.mainSku' />" />

		<!-- 查询条件区域 -->
		<table width="98%" class="content_table" align="center" height="100"
			cellpadding="0" cellspacing="0">
			<tr>
				<td style="margin-left:-100px">主产品名称: <s:property
						value="product.procuctName" />
				</td>

				<td>主产品SKU编码 :<s:property value="product.productSkuCode" />
				</td>
				<td>主产品SKU值：<s:property value="tiedSade.mainSku" />
				</td>
			</tr>

			<tr>
				<!-- 根据查询字段的多少判断colspan-->
				<td width="80%" valign="middle" colspan="4"><INPUT
					class="addBtn" TYPE="button" value=""
					onclick="gotoAdd(<s:property value='tiedSade.mainSku' />);">
					<input class="delBtn" type="button" value=""
					onclick="gotoDel('tiedSadeId');" />
			</tr>
		</table>


		<!-- 数据列表区域 -->
		<table width="98%" class="list_table" align="center" cellpadding="3"
			cellspacing="0" border="1" bordercolor="#C1C8D2">
			<tr>
				<th align="center" width="5%"><input type='checkbox'
					id='allbox' name='allbox' onclick='checkAll(this)'>
				</th>
				<th align="center" width="12%">产品的SKU值</th>
				<th align="center" width="15%">产品的SKU编号</th>
				<th align="center" width="15%">产品的名称</th>
				
				
			</tr>
			<s:iterator id="supplieriterator" value="matchProdutShowList">
				<tr>
					<td align="center" width="5%"><input type="checkbox"
						id="check" name="tiedSadeId"
						value='<s:property value="tiedSadeId"/>'></td>
					<td align="center"><s:property value="productSkuId" />
					</td>
					<td align="center"><s:property value="skuCode" />
					</td>
					<td align="center"><s:property value="selfName" />
					</td>
					
				</tr>
			</s:iterator>
		</table>

		<p style="position:relative;left:0px">
			<input class="backBtn" style="position:absolute;left:500px"
				type="button" onclick="back()">
			</td>
		</p>

		<br>

		<br>

	</s:form>
	
	
	
<s:if test='!rtnMessage.isEmpty()'>
	<SCRIPT LANGUAGE="JavaScript">
	
		alert(document.getElementById("rtnMsg").value);

	</SCRIPT>
</s:if>
			
	
	
	
	
	
	
	<SCRIPT type="text/javascript">

function gotoAdd(mainSku){

document.frm.action="/tiedSale/tsAdd.action?productmainTied.productSkuId="+mainSku;
document.frm.submit(); 

}

function gotoUpdate(id){
    var text=document.getElementById("price"+id);
    var matchPrice=text.value;
    var price=matchPrice.replace(/^\s*|\s*$/g,"");    
    if(price.length==0){
    alert("输入值不能为空");
    return ;
    }
    if(price.search(/^[0-9]*$/)==-1){
    alert("输入值只能为数字");
    return ;
    }
    if(Number(price)>9999999){
    alert("你的价格过大，请重新输入");
    return  ;
    }
    document.frm.action="/tiedSale/update.action?tiedSade.tiedSadeId="+id+"&tiedSade.tiedSadeSkuPrice="+price;
    document.frm.submit();    
}

function checkAll(ck)
{
  var inputs = ck.form.getElementsByTagName("input");
  for (var i=0;i<inputs.length;i++){
    var ele = inputs[i];
    if ((ele.type=="checkbox")){
      if(ck.checked!=ele.checked)
        ele.click();
    }
  }
}


  function  back(){
   document.frm.action = "/tiedSale/stQuery.action";
		document.frm.submit();
   }	

  function gotoDel(id){
       var obj = document.getElementsByName(id);
                var count = 0; 
                // 遍历所有用户，找出被选中的用户
                for (var i = 0; i < obj.length; i++) {
                    if (obj[i].checked) {
                        count++;  
                    }
                }
                 if (count == 0) {
                        alert("请选择要删除的数据。");
                 }else if(confirm('是否确认删除?')==true){ 
                       document.frm.action="/tiedSale/delST.action";
                       document.frm.submit();         
 }}
 
 
 
  
</SCRIPT>
</body>

</HTML>