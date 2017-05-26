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
         
		function bink(id)
		{
			var operateType = $("#operateType").val();
			$.ajax({
				url:"/cms/cmsPromotion_ajaxpromotion.action?ajax=yes",
				data:"promotionId="+id+"&operateType="+operateType,
				type:"post",
			     datatype:'json',
				success:function(result){
				   if(result!='null')
				    {
					parent.closeOpenDialog(result)}
					else
					{
					alert('该活动已存在');
					}
				},
				error:function(){
					alert("出错了");
				}
			});
		}
</script>
</head>
<body>
<s:form  name="viewPromotionForm" action="/cms/cmsPromotion_promotion.action" 
    onsubmit="return checkAllTextValid(this)" method="post">
    <input type="hidden" id="operateType" name="operateType" value="<s:property value='operateType'/>"/>
<table  width="98%" class="content_table" align="center" cellpadding="0" cellspacing="0"  style="margin:10 0 10 0px;">
	<tr>
		<td align="right">编号：</td>
		<td>
		     <input name="viewPromotion.promotionId" type="text" value="<s:property value='viewPromotion.promotionId'/>"/>
		</td>
		<td align="right">活动名称：</td>
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
		<td colspan="3">
	       <input name="viewPromotion.startTime" type="text" readonly="readonly" value="<s:date name='viewPromotion.startTime' format='yyyy-MM-dd HH:mm:ss'/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
	       	至
	       	<input name="viewPromotion.endTime" type="text" readonly="readonly" value="<s:date name='viewPromotion.endTime' format='yyyy-MM-dd HH:mm:ss'/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
		</td>
		
		<input type="hidden" id="checkeds" name="checkeds" value='<s:property value="checkeds"/>'>
		
		<input type="hidden" id="windowId" name="windowId" value='<s:property value="windowId"/>'>
		<td align="right">活动状态：</td>
		<td>
			<select name="viewPromotion.expiredFlag">
				<option value="">全部</option>
				<option value="1" <s:if test="viewPromotion.expiredFlag==1">selected="selected"</s:if>>未过期</option>
				<option value="2" <s:if test="viewPromotion.expiredFlag==2">selected="selected"</s:if>>已过期</option>
			</select>
		</td>
		<td align="center">
			<input type="submit" class="queryBtn" value="">
		</td>
	</tr>
</table>


<!-- 数据列表区域 -->
<table width="98%" class="list_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
	<tr>
	   
		<th align="center">编号</th>
		<th align="center">活动名称</th>
		<th align="center">活动规则</th>
		<th align="center">广告语</th>
		<th align="center">活动开始时间</th>
<th align="center">活动结束时间</th>
<th align="center">选择</th>		

	</tr>
	<s:iterator id="viewPromotioniterator"  value="page.dataList">
	<tr>
	     
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
		<td align="center">
		  <img title="添加" style="cursor: pointer;" src="/etc/images/icon_select.png"   onclick='bink(<s:property value="promotionId"/>)' />
		
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


</s:form>
</body>
</html>

