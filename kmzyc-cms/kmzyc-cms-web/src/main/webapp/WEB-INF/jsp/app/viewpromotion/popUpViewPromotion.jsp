<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>活动信息管理</title>
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
	           parent.closeViewPromotion(checkeds,windowId);
         }

         
</script>
</head>
<body>
<s:form  name="viewPromotionForm" action="/cms/viewPromotionAction_queryViewromotion.action" 
    onsubmit="return checkAllTextValid(this)" method="post">
<table  width="98%" class="content_table" align="center" cellpadding="0" cellspacing="0"  style="margin:10 0 10 0px;">
	<tr>
		<!--<td align="right">编号：</td>
		<td>
		     <input name="viewPromotion.promotionId" type="text" value="<s:property value='viewPromotion.promotionId'/>"/>
		</td>
		--><td align="right">活动名称：</td>
		<td>
	       <input name="viewPromotion.promotionName" type="text" value="<s:property value='viewPromotion.promotionName'/>"/>
		</td>
		<td align="right">活动类型：</td>
		<td>
		  	<select name="viewPromotion.promotionTypeId">
		     <option value="">全部</option>
		     <s:iterator value="listPromotionType" id="listPromotionType">
		     	<s:if test="promotionTypeId == viewPromotion.promotionTypeId">
					<option selected="selected" value="<s:property value='promotionTypeId'/>"><s:property value="promotionTypeName"/></option>
				</s:if>
				<s:else>
					<option value="<s:property value='promotionTypeId'/>"><s:property value="promotionTypeName"/></option>
				</s:else>
			</s:iterator>
			</select>
		</td>
		<td align="right"><%--渠道：--%></td>
		<td>
			<%--<select name="channel">
			<s:if test="channelQuery.split(',').length != 1">
				<option value="<s:property value='channelQuery'/>">全部渠道</option>
			</s:if>
		    	<s:generator val="channelQuery" separator="," id="channels">   
				</s:generator> 
			<s:iterator status="channelIndex" value="#request.channels" id="channelName">
				<s:if test="#channelName==channel">
					<option selected="selected" value="<s:property value="channelName"/>"><s:property value="channelName"/></option>
				</s:if> 
				<s:else>
						<option value="<s:property value="channelName"/>"><s:property value="channelName"/></option>
				</s:else>
			</s:iterator>   
			</select>    --%>
		</td>
		
	</tr>
	<tr>
		 <td align="right">活动时间：</td>
		<td colspan="4">
	       <input name="viewPromotion.startTime" type="text" readonly="readonly" value="<s:date name='viewPromotion.startTime' format='yyyy-MM-dd HH:mm:ss'/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
	       	至
	       	<input name="viewPromotion.endTime" type="text" readonly="readonly" value="<s:date name='viewPromotion.endTime' format='yyyy-MM-dd HH:mm:ss'/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
		</td>
		
		<input type="hidden" id="checkeds" name="checkeds" value='<s:property value="checkeds"/>'>
		
		<input type="hidden" id="windowId" name="windowId" value='<s:property value="windowId"/>'>
		<input type="hidden" id="pageId" name="pageId" value='<s:property value="pageId"/>'>
		<input type="hidden" id="adminType" name="adminType" value='<s:property value="adminType"/>'>
		<td align="center">
			<input type="submit" class="queryBtn" value="">
		</td>
	</tr>
</table>


<!-- 数据列表区域 -->
<table width="98%" class="list_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
	<tr>
	    <th><input type='checkbox' name='promotion' id="promotion"  onclick="checkAllpop(this,'promotionIds')"></th>
		<th align="center">编号</th>
		<th align="center">活动名称</th>
		<th align="center">活动类型</th>
		<th align="center">广告语</th>
		<th align="center">活动开始时间</th>
		<th align="center">活动结束时间</th>
	</tr>
	<s:iterator var="p" id="viewPromotioniterator"  value="page.dataList">
	<tr>
	     <td>
		     <s:if test="flag!=1"><input type="checkbox" name="promotionIds" value='<s:property value="promotionId"/>' onclick="checkpromotionId(this);">
		     </s:if>
	     </td>
		<td align="center">
		     <s:property value="promotionId"/>
		</td>
		<td align="center">
		     <s:property value="promotionName"/>  
		</td>
		<td align="center">
			<s:property value="categoryMap[promotionTypeId]"/>
		</td>
		<td align="center">
		   <s:property value="slogan"/>
		</td>
		<td align="center">
		   <s:date name="startTime" format="yyyy-MM-dd HH:mm:ss"/>
		</td>
		<td align="center">
		   <s:date name="endTime" format="yyyy-MM-dd HH:mm:ss"/>
		</td>
	</tr>
	</s:iterator>
</table>

<table width="98%"  align="center" class="page_table">
	<tr>
		<td>
			<s:set name="form_name"  value="'viewPromotionForm'"  scope="request"></s:set>
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

