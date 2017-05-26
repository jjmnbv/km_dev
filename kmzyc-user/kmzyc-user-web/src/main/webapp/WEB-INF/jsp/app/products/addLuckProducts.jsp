<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>列表选择SKU码</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<script src="/etc/js/jquery-1.8.3.js"></script>
<script src="/etc/js/jquery.form.js"></script>




<script type="text/javascript"
	src="/etc/js/easy_validator.pack.js"></script>
	<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="/etc/js/common.js"></script>
 <script src="/etc/js/dialog.js"></script>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/validate.css" type="text/css" rel="stylesheet">
<link href="/etc/css/validate.css"    />
<style type="text/css">
body {
	padding: 0px;
	margin: 0px;
}

table {
	margin-left: 10px;
}
</style>
<script type="text/javascript">
//类目加载
function change(ca,categoryId){
	   var categoryHtml = '';
	   if(categoryId=='categoryId2'){
			categoryHtml = '<option value="">--二级类目--</option>';
		}else if(categoryId=='categoryId3'){
			categoryHtml = '<option value="">--三级类目--</option>';
		}
		/* if($('#'+ca).val()==""){ */
		$('#'+categoryId).html(categoryHtml);
		if(ca=='categoryId1'){
			$('#categoryId3').html('<option value="">--三级类目--</option>');
		}
		/* 	return;
		} */
	  
	    $.ajax({
			type: "post",
			url: "/userInfo/productinfo_initCategory.action?categoryId="+$('#'+ca).val(),
			dataType : "json",
			success: function(data){	
				var size = data.length;
				for(var i=0;i<size;i++){
					categoryHtml += '<option value="'+data[i].categoryId+'">'+data[i].categoryName+'</option>';
				}
				$('#'+categoryId).html(categoryHtml);
			}
	 		});  
}
function selectList() {
	var purchaseId=$("#purchaseId").val();
	if ($("input:checked").length == 0) {
		alert("请添加关联的产品");
		return false;
	} 
	//var params = new Array();
	//document.productInfoForm.action="/userInfo/productinfo_insertDetails.action?purchaseId="+purchaseId;
	//document.productInfoForm.submit();
	var t = this;
	var ajax_option={

			url:"/userInfo/productinfo_insertDetails.action?purchaseId="+purchaseId,//默认是form action

			success:function(data){
				t.parent.art.dialog.get('dg_test34243').close();
			}
	}

	$('#pform2').ajaxSubmit(ajax_option);

	
	
	
	
	}
	
function removeRepetitive(arr){
    var len = arr.length;
    var newArr = [];
    if (len < 2) return arr;
    for (var i = 0; i < len; i++) {
        for (var j = i + 1; j < len; j++) {
            if (arr[i] === arr[j]) {
                j = ++i;
               // alert(arr[i]);
            }
        }
        if(arr[i]){
        	 newArr.push(arr[i]);
         }
       
    }
    return newArr;
}
$(document).ready(function(){ 
	
var psku='<%=request.getAttribute("pSkuCodeLists")%>';
var checkLists=$("#listTable input[name='skuCodeLists']");
checkLists.each(function(){
	var datas = this.value;
	 var co=datas.split("|");
	  var skuC=	co[0];
	  if(psku.indexOf(co[0])>0){
		  this.checked=true;
		  this.disabled=true;
	  }
	  //psku.indexOf(co[0]);
	  
	//alert( psku.indexOf(co[0]));
});

$('#selectInfo').click(function() {
			var idschecks=$("#listTable input[name='skuCodeLists']:checked"); 
			if(idschecks.size()==0){
				alert('请选择商户','提示');
				return false;
			}else{
			   var oldNameArr = $('#productTitleLists',parent.window.document).val().split('|');
			   var oldIdArr = $('#skuCodeLists',parent.window.document).val().split('|');
			   var oldColArr = $('#colLists',parent.window.document).val().split('|');
				idschecks.each(function(){	
				  var data = this.value;
				  if(!this.disabled){
					
				 
				  var aa=data.split("|");
				  var skuCode=	aa[0];
				  //alert(supplierId,'提示');
				  var productTitle=aa[1];
				  var col=aa[2];
				  //alert(corporateName,'提示');
				  if(productTitle){
					  oldNameArr.push(productTitle);
					 }
				  if(skuCode){
					  oldIdArr.push(skuCode);
					 }
				  if(col){
					  oldColArr.push(col);
					 }
				  }
				});
				var newNameArr = oldNameArr;
				var newIdArr = oldIdArr;
				var newColArr = oldColArr;
			
				   var method = "closeProductInfo";
	         if(method!=null||method!=""){
	            parent.closeProductInfo(newIdArr.join('|'),newNameArr.join('|'),newColArr.join('|'));
	         }else{
	           parent.closeProductInfo(newIdArr.join('|'),newNameArr.join('|'),newColArr.join('|'));
	         }
		   }
		});
});

function checkAlls(titleName,checkBoxName){
	 var checkBox = document.getElementsByName(checkBoxName);
	// alert("tt");
	 for (var i = 0; i < checkBox.length; i++){
	  var temp = checkBox[i];
	    if(!temp.disabled){
		  if(titleName.checked){
		      temp.checked = true;
		  }
		  else{
			 temp.checked = false;
		      
		  }
	    }
	 }
} 
</script>
</head>
<body>


<s:form action="/userInfo/productinfo_addLuckProducts.action"
	onsubmit=" return checkAllTextValid(this)" method="post" id="pform2"  name="productInfoForm">
		<s:hidden name="checkedId" id="checkedId"/>
	  <input type="hidden" id="purchaseId" name="purchaseListDetailDO.purchaseId" value="<s:property value="purchaseId"/>" />
	  <input type="hidden" id="pSkuCodeLists" name="pSkuCodeLists" value="<s:property value="pSkuCodeLists"/>" />
	  <input type="hidden" id="supplierIds" name="supplierIds" value="<s:property value="supplierIds"/>" />
	   <input type="hidden" id="supplierTypes" name="supplierTypes" value="<s:property value="supplierTypes"/>" />
	  
	<br />
	
	<table width="98%" height="100" class="content_table" align="center" cellpadding="0" cellspacing="0" >
	<tr>
 		<td colspan="8">
 		</td>
 	</tr>
	<tr>
		<td align="right">SKU编码：</td>
		<td>
		
		     <input name="purchaseListDetailDO.skuCode" type="text" value="<s:property value='purchaseListDetailDO.skuCode'/>">
		</td>
		<td align="right">产品标题：</td>
		<td>
		  <input name="purchaseListDetailDO.productTitle" type="text" value="<s:property value='purchaseListDetailDO.productTitle'/>">   
		</td>
		<td align="right">物理类目：</td>
		<td colspan="3"> <s:select list="categoryList"
					name="bcategoryId" id="categoryId1" listKey="categoryId" listValue="categoryName"
					headerKey="" headerValue="--一级类目--"
					onchange="change('categoryId1','categoryId2');">
				</s:select> 
				<s:select  list="categoryList2"
					name="mcategoryId" id="categoryId2"  listKey="categoryId" listValue="categoryName"
					headerKey="" headerValue="--二级类目--"
					onchange="change('categoryId2','categoryId3');">
				</s:select>  
				<s:select list="categoryList3"
					name="tcategoryId" id="categoryId3" listKey="categoryId" listValue="categoryName"
					headerKey="" headerValue="--三级类目--">
				</s:select></td>
		
	</tr>
		
	<tr>
	 <td align="right" colspan="2">
			 <input type="button" value="保存所选" id="selectInfo">
		</td>
 			<td align="right" colspan="2">
			<INPUT TYPE="submit" class="queryBtn" value="">
		</td>
	</tr>
</table>
	
	<br />

	<!-- 数据列表区域 -->
	<table id="listTable" width="98%" class="list_table" align="center" cellpadding="3"
			cellspacing="0" border="1" bordercolor="#C1C8D2">
		<tr>
		<th  align="center" width="5%">
		<input type='checkbox' name='allbox'
								onclick="checkAlls(this,'skuCodeLists')">
		</th>
		<th align="center" >SKU编号</th>
		<th align="center" >产品标题</th>
		<th align="center" >渠道</th>
		<th align="center" >品牌</th>
		<th align="center" >状态</th>
		<th align="center" >销售单价</th>
		<th align="center" >SKU描述</th>
		
		</tr>
		<s:iterator id="productiterator" value="page.dataList" >
			<tr>
			
				<td align="center" width="5%">
				
				<input type="checkbox" name="skuCodeLists"
									value='<s:property value="skuCode"/>|<s:property value="productTitle" />|<s:property value="col" />' 
							>
									
				</td>
				<input type="hidden"  name="productRelationSku"     />
				
				<td  align="center" width="5%"><s:property value="skuCode" /></td>
				<td align="center"  style="word-break: break-all">
					<s:property value="productTitle" />
				</td>
				<td align="center"  style="word-break: break-all">
					<s:property value='channel'    />
				</td>
				
				<td align="center"  style="word-break: break-all">
					<s:property value='frandName'    />
				</td>
				
				<td align="center"  style="word-break: break-all">
				
				<s:if test="status==0">草稿</s:if>
		 	<s:if test="status==1">待审核</s:if>
	        <s:if test="status==2">已审核,待上架</s:if>
	        <s:if test="status==3">已上架</s:if>
	        <s:if test="status==4">已下架</s:if>
	        <s:if test="status==5">系统下架</s:if>
	        <s:if test="status==6">审核未通过</s:if>
				
				</td>
				
				
			
				
				<td align="center"  style="word-break: break-all">
				<s:property value="productPrice" />
				</td>
				
				
				
				<td align="center"  style="word-break: break-all">
			
			          	<s:property value="col" />
			          	
			  
			          	
				</td>
				
				
			</tr>
		</s:iterator>
	</table>

	<table width="98%" align="center" class="page_table">
	<tr>
		<td>
			<s:set name="form_name"  value="'productInfoForm'"  scope="request"></s:set>
			<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
		</td>
	</tr>
</table>       
</s:form>   


<s:if test='!rtnMessage.isEmpty()'>
	<SCRIPT LANGUAGE="JavaScript">
	
	  var msg=document.getElementById("rtnMsg").value;
		//alert(msg);
		
		if(msg){
		
		parent.closeOpenSku();
		
		}

	</SCRIPT>
</s:if>
	



</BODY>
</HTML>

