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
           	var templateId = $("#templateId").val();
           	if(checkeds == ""){
           		var msg="<img src='../etc/images/tipMsg.png' style='vertical-align:middle'/>请选择!";
                messageDialog("消息提示","text:"+msg ,"300px","102px","text");
                var timer_alert = setTimeout(function() {
        				messageCloseThis();
        			}, 2000);
                return;
            }
           	 //  alert("checkeds:"+checkeds);
	           parent.closeViewProductInfo(checkeds,templateId);
         }

         
</script>
</head>
<body>
<s:form  name="viewProductInfoForm" action="/cms/cmsTemplateAction_gotoWindowList.action" method="post">
<table  width="98%" class="content_table" align="center" cellpadding="0" cellspacing="0"  style="margin:10 0 10 0px;">
	<tr>
		<td align="right" >名称：</td>
		<td>
		     <input name="cmsTemplate.name" type="text" value="<s:property value='cmsTemplate.name'/>"/>
		     
		</td>
		  
		<td align="right">类型：</td>
		<td>
			<select name="cmsTemplate.type">
		    
			        <option value="">全部</option>
				 	<option value="13" <s:if test="cmsTemplate.type==13">selected="selected"</s:if>>窗口变量类型</option>
					<option value="12" <s:if test="cmsTemplate.type==12">selected="selected"</s:if>>页面变量类型</option>
				
			</select>      	
				
			<input type="submit" class="queryBtn" value="">
			<input type="hidden" name="checkeds" id="checkeds" value="<s:property value='checkeds'/>"/>
			
		
			
			 <input type="hidden" id="templateId" name="cmsTemplate.templateId" value="<s:property value='cmsTemplate.templateId'/>">
	
		</td>
		
	</tr>

	
</table>


<!-- 数据列表区域 -->
<table width="98%" class="list_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
	<tr>
	    <th><input type='checkbox' name='promotion' id="promotion"  onclick="checkAllpop(this,'promotionIds')"></th>
		<th align="center">编号</th>
		<th align="center">名称</th>
		<th align="center">主题</th>
		<th align="center">类型</th>
	
	</tr>
	<s:iterator id="cmsTemplate"  value="page.dataList">
	<tr>
	     <td>
		     <s:if test="flagId==null&&templateId!=cmsTemplate.templateId">
		      <input type="checkbox" id="promotionIds" name="promotionIds" value='<s:property value="templateId"/>' 
		      onclick="checkpromotionId(this);">
		     </s:if>
	     </td>
		<td align="center">
		     <s:property value="templateId"/>
		</td>
			<td align="center">
		     <s:property value="name"/>
		</td>
		<td align="center">
		     <s:property value="theme"/>
		</td>
	
		<td align="center">
		    
		
		   <s:if test="type==13">窗口变量模板</s:if>
		   <s:if test="type==12">页面变量模板</s:if>
		
		   
		   
		  
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

