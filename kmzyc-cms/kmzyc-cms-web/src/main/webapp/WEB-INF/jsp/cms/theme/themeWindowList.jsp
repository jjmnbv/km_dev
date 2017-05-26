<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>产品信息管理</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
<script type="text/javascript"  src="/etc/js/checkeds.js"></script>
<script type="text/javascript"  src="/etc/js/rowDisplay.js"></script>
 <Script src="/etc/js/97dater/WdatePicker.js"></Script> 
<script type="text/javascript">
       $(document).ready(function(){
    		var checks = "";
    		checks = $("#checkeds").val();
    		if(checks!=""){
        		var checkboxs = document.getElementsByName("promotionIds");
        		var myarr = new Array();
    			myarr = checks.split(',');
        		for(var i = 0; i<checkboxs.length; i++){
            		for(var j = 0; j<myarr.length; j++){
                		if(checkboxs[i].value==myarr[j]){
                			checkboxs[i].checked = true;
                			break;
                    	}
                	}
            	}
        	}
	   });
      
        /** 选择团出窗口数据  **/
         function selectOneAccount(){
           	var checkeds = "";
           	checkeds = $("#checkeds").val();
           	var windowId = $("#windowId").val();
           	if(checkeds == ""){
           		var msg="<img src='../etc/images/tipMsg.png' style='vertical-align:middle'/>请选择!";
                messageDialog("消息提示","text:"+msg ,"300px","102px","text");
                var timer_alert = setTimeout(function() {
        				messageCloseThis();
        			}, 2000);
                return;
            }
	           parent.closeViewProductInfo(checkeds,windowId);
         }

         
</script>
</head>
<body>
<s:form  name="viewProductInfoForm" action="/cms/viewProductInfoAction_queryViewProductInfo.action" method="post">
<table  width="98%" class="content_table" align="center" cellpadding="0" cellspacing="0"  style="margin:10 0 10 0px;">
	<tr>
		<td align="right" onmouseover="showotherGoodsList();">SKU编码：</td>
		<td>
		     <input name="viewProductInfo.productSkuCode" type="text" value="<s:property value='viewProductInfo.productSkuCode'/>"/>
		</td>
		<td align="right">产品名称：</td>
		<td>
	       <input name="viewProductInfo.procuctName" type="text" value="<s:property value='viewProductInfo.procuctName'/>"/>
		</td>
		<td align="right">品牌：</td>
		<td>
			<select name="viewProductInfo.brandId">
		     <option value="">全部品牌</option>
			     <s:iterator value="prodList" id="prodList">
			     	<s:if test="brandId == viewProductInfo.brandId">
						<option selected="selected" value="<s:property value='brandId'/>"><s:property value="brandName"/></option>
					</s:if>
					<s:else>
						<option value="<s:property value='brandId'/>"><s:property value="brandName"/></option>
					</s:else>
				</s:iterator>
			</select>      	
		</td>
		
		
		
		
	</tr>
	<tr>
	<td align="right">类目：</td>
		<td>
			<select name="viewProductInfo.categoryId">
		     <option value="">全部类目</option>
			     <s:iterator value="categoryList" id="categoryList">
			     	<s:if test="categoryId == viewProductInfo.categoryId">
						<option selected="selected" value="<s:property value='categoryId'/>"><s:property value="categoryName"/></option>
					</s:if>
					<s:else>
						<option value="<s:property value='categoryId'/>"><s:property value="categoryName"/></option>
					</s:else>
				</s:iterator>
			</select>      	
		</td>
		<td align="right"></td>
		<td>

		</td>
		
		<td align="right" colspan="4">
			<input type="submit" class="queryBtn" value="">
			<input type="hidden" name="checkeds" id="checkeds" value="<s:property value='checkeds'/>"/>
			<input type="hidden" id="windowId" name="windowId" value='<s:property value="windowId"/>'>
		</td>
		
	</tr>
	
</table>


<!-- 数据列表区域 -->
<table width="98%" class="list_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
	<tr>
	    <th><input type='checkbox' name='promotion' id="promotion"  onclick="checkAllpop(this,'promotionIds')"></th>
		<th align="center">编号</th>
		<th align="center">产品名称</th>
		<th align="center">类目名称</th>
		<th align="center">品牌</th>
		<th align="center">SKU编码</th>
		<th align="center">价格</th>
	</tr>
	<s:iterator id="viewPromotioniterator"  value="page.dataList">
	<tr>
	     <td>
		     <s:if test="flag!=1"><input type="checkbox" id="promotionIds" name="promotionIds" value='<s:property value="productId"/>' onclick="checkpromotionId(this);">
		     </s:if>
	     </td>
		<td align="center">
		     <s:property value="productId"/>
		</td>
		<td align="center">
		     <s:property value="procuctName"/>
		</td>
		<td align="center">
		   <s:property value="categoryName"/>
		</td>
		<td align="center">
		   <s:property value="brandName"/>
		</td>
		<td align="center">
		   <s:property value="productSkuCode"/>
		</td>
		<td align="center">
		   <s:property value="price"/>
		</td>
	</tr>
	</s:iterator>
</table>

<table width="98%"  align="center" class="page_table">
	<tr>
		<td>
			<s:set name="form_name"  value="'viewProductInfoForm'"  scope="request"></s:set>
			<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
		</td>
	</tr>
</table>

<!-- 底部 按钮条 -->
<table width="60%"  class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
		<td align="left">
			<input class="saveBtn" type="button" value=" " onclick="selectOneAccount();">
		</td>
		<td width="20%" align="center"></td>
	</tr>
</table>

</s:form>
</body>
</html>

