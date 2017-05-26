<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>品牌信息管理</title>
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
      
       /** 关闭类目弹出层  **/
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
           	
	           parent.closeProdBrand(checkeds,windowId);
         }

         
</script>
</head>
<body>
<s:form  name="prodBrandForm" action="/cms/prodBrandAction_queryProdBrand.action" 
    onsubmit="return checkAllTextValid(this)" method="post">
<table  width="98%" class="content_table" align="center" cellpadding="0" cellspacing="0"  style="margin:10 0 10 0px;">
	<tr>
		<!--<td align="right">编号：</td>
		<td>
		     <input name="prodBrand.brandId" type="text" value="<s:property value='prodBrand.brandId'/>"/>
		</td>
		--><td align="right">品牌名称：</td>
		<td>
	       <input name="prodBrand.brandName" type="text" value="<s:property value='prodBrand.brandName'/>"/>
		</td>
		<td align="right">品牌国籍：</td>
		<td>
			<select name="prodBrand.nation">
				<option value="" selected="selected">全部</option>
				<option value="国内"
					<s:if test='prodBrand.nation == "国内"'> selected="selected"</s:if>>国内</option>
				<option value="国外"
					<s:if test='prodBrand.nation == "国外"'> selected="selected"</s:if>>国外</option>
			</select>
		</td>
		
		
		
	</tr>
	<tr>
		<td align="right">英文名称：</td>
		<td>
		  	 <input name="prodBrand.engName" type="text" value="<s:property value='prodBrand.engName'/>"/>
		</td>
		<td align="right">中文拼音：</td>
		<td>
		  	 <input name="prodBrand.chnSpell" type="text" value="<s:property value='prodBrand.chnSpell'/>"/>
		</td>
		<td align="right">
			<input type="hidden" id="checkeds" name="checkeds" value='<s:property value="checkeds"/>'/>
			<input type="hidden" id="windowId" name="windowId" value='<s:property value="windowId"/>'/>
			<input type="hidden" id="pageId" name="pageId" value='<s:property value="pageId"/>'>
			<input type="hidden" id="adminType" name="adminType" value='<s:property value="adminType"/>'>
			<input type="submit" class="queryBtn" value="" >
		</td>
	</tr>
	
	
	
</table>


<!-- 数据列表区域 -->
<table width="98%" class="list_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
	<tr>
	    <th><input type='checkbox' name='promotion' id="promotion"  onclick="checkAllpop(this,'promotionIds')"></th>
		<th align="center">编号</th>
		<th align="center">品牌名称</th>
		<th align="center">品牌国籍</th>
		<th align="center">品牌主页</th>
		<th align="center">英文名称</th>
		<th align="center">中文拼音</th>
	</tr>
	<s:iterator id="viewPromotioniterator"  value="page.dataList">
	<tr>
	     <td>
		     <s:if test="flag!=1"><input type="checkbox" name="promotionIds" value='<s:property value="brandId"/>' onclick="checkpromotionId(this);">
		     </s:if>
	     </td>
		<td align="center">
		     <s:property value="brandId"/>
		</td>
		<td align="center">
		     <s:property value="brandName"/>
		</td>
		<td align="center">
		   <s:property value="nation"/>
		</td>
		<td align="center">
		   <s:property value="homepage"/>
		</td>
		
		<td align="center">
		   <s:property value="engName"/>
		</td>
		
		<td align="center">
		   <s:property value="chnSpell"/>
		</td>
		
	</tr>
	</s:iterator>
</table>

<table width="98%"  align="center" class="page_table">
	<tr>
		<td>
			<s:set name="form_name"  value="'prodBrandForm'"  scope="request"></s:set>
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

