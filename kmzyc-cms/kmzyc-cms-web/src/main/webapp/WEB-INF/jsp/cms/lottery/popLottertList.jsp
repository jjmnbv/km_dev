<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
	<head>
		<title>选择活动</title>
			<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet"
			type="text/css" />
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		<script type="text/javascript" src="/etc/js/jquery-latest.pack.js"></script>
		<script src="/etc/js/dialog.js"></script>
		<script type="text/javascript" src="/etc/js/pageCommon.js"></script>
		<script type="text/javascript" src="/etc/js/checkeds.js"></script>
		<script type="text/javascript" src="/etc/js/rowDisplay.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.validate.js"></script>
		<script type="text/javascript" src="/etc/js/messages_cn.js"></script>
		
	
		<script type="text/javascript">
	   
   function selectOneAccount(){   
    var obj = document.getElementsByName("luckDrawIds");
    	var windowId = $("#windowId").val();
		var objLen = obj.length;
		var i;
		var k = 0;
		var value;
		var ok="";
		for (i = 0; i < objLen; i++) {
		
			if (obj[i].checked == true) {
				k = k + 1
                value=obj[i].value;
			}

		}
		if (k == 0) {
			alert("请选择要绑定的活动！");
			return false;
		}
		if (k >1) {
			alert("只能选择一个抽奖活动！");
			return false;
		}	
	 	$.ajax({
	 		url:"/cms/cmsLottery_checkLottery.action",
	 		async:false,
	 		data:"windowId="+windowId,
        	type:'post',
	 		success:function(result)
	 		{
	 		   ok=result;
	 		}
	 	});
	 	if(ok>0)
	 	{
	 	alert("已经选择抽奖活动！");
	 		return false;
	 	}
      parent.closeLottery(value,windowId);
    }
   
   function checkViewProductInfo(){
  	 $('#luckDrawId').val($.trim($('#luckDrawId').val()));
  	 $('#titel').val($.trim($('#titel').val()));
  	 if(isNaN($('#luckDrawId').val()) || isNaN($('#luckDrawId').val())){
      	 $('#errors').html("请输入合法的数字");
      	  return false;
     	 }
     	 if($('#luckDrawId').val().length>10 || $('#luckDrawId').val().length>10 || $('#titel').val().length>32){
     		$('#errors').html("输入字符过长");
    	 	 return false;
    	  }
     	$('#errors').html("");
   }
   
   $(document).ready(function(){
	   //$("#lotteryLuckDrawForm").validate({
	  //       rules: {
		//			"lotteryLuckDraw.luckDrawId": {keywordIdChar:true,maxlength:10}
		  //      	},
		    // success: function (label){
		      //      label.removeClass("checked").addClass("checked");
		       //     }
	         // });
   });
	</script>
	</head>
	<body>
		<!-- 导航栏 -->
		<div style="height: 90%; overflow-y: scroll;">
			<s:form class="pageForm" name="lotteryLuckDrawForm" id="lotteryLuckDrawForm"
				onsubmit="return checkViewProductInfo()"
				action="/cms/cmsLottery_queryForPage.action" method="post">
				<table width="98%" align="center" height="90" border="0"
					class="content_table" cellpadding="0" cellspacing="0">
					<tr>
					</tr>
					<td align="left">
						活动编号：
						<input id="luckDrawId" name="lotteryLuckDraw.luckDrawId" type="text"
							value="<s:property value="lotteryLuckDraw.luckDrawId"/>">
					</td>
					<td align="left">
						活动标题：
						<input id="titel" name="lotteryLuckDraw.titel" type="text"
							value="<s:property value="lotteryLuckDraw.titel"/>">
					</td>
					<td align="left">
						抽奖类型:
						<select name="lotteryLuckDraw.activeType">
							<option value=""
								<s:if test="lotteryLuckDraw.activeType==null">selected="selected"</s:if>>
								全部
							</option>
							<option value="0"
								<s:if test="lotteryLuckDraw.activeType==0">selected="selected"</s:if>>
								即开型
							</option>
							<option value="1"
								<s:if test="lotteryLuckDraw.activeType==1">selected="selected"</s:if>>
								后开型
							</option>
						</select>
					</td>
					<td align="right">
					
						<INPUT TYPE="submit" class="queryBtn" value="">
					<input type="hidden" name="checkeds" id="checkeds" value="<s:property value='checkeds'/>"/>
			<input type="hidden" id="windowId" name="windowId" value='<s:property value="windowId"/>'>
			<input type="hidden" id="pageId" name="pageId" value='<s:property value="pageId"/>'>
			<input type="hidden" id="adminType" name="adminType" value='<s:property value="adminType"/>'>
			
			<br/>
			<label id="errors" style="color: red;"></label>
					</td>

					</tr>
				</table>
				<table width="98%" class="list_table" cellpadding="3" align="center">
					<tr>
						<th width="5%">
							<input type='checkbox' name='promotion' id="promotion"
							value="<s:property value="luckDrawId"/>"	onclick="checkAllpop(this,'luckDrawIds')">
						</th>
						<th>
							活动编号
						</th>
						<th>
							活动标题
						</th>
						<th>
							抽奖类型
						</th>
						<%--<th>--%>
							<%--活动发布渠道--%>
						<%--</th>--%>
						<th>
							活动开始时间
						</th>
						<th>
							活动结束时间
						</th>
					</tr>
					<s:iterator id="lotteryLuckDraw" value="page.dataList">
						<tr>
							<td width="5%">
							 <s:if test="flag!=1">
								<input type="checkbox" name="luckDrawIds"
									value='<s:property value="luckDrawId"/>'
									onclick="checkpromotionId(this);">
							</s:if>
							</td>
							<TD>
								<s:property value="luckDrawId" />
							</TD>
							<TD>
								<s:property value="titel" />
							</TD>
							<TD>
								<s:if test="activeType==0">
				                                  即开型				</s:if>
								<s:if test="activeType==1">
				                                   后开型
				               </s:if>
							</TD>
							<%--<TD>--%>
								<%--<s:if test="channelId==1">--%>
				           <%--B2B --%>
				<%--</s:if>--%>
								<%--<s:if test="channelId==2">--%>
				<%--ZYC--%>
				<%--</s:if>--%>
								<%--<s:if test="channelId==3">--%>
				<%--B2B--%>
				<%--</s:if>--%>
							<%--</TD>--%>
							<TD>
								<s:date name="starttime" format="yyyy-MM-dd HH:mm:ss"/>
							</TD>
							<TD>
								<s:date name="endtime" format="yyyy-MM-dd HH:mm:ss"/>
							</TD>
							
						</tr>
					</s:iterator>
				</table>
				<table class="page_table" width="98%" align="center" cellpadding="0"
					cellspacing="0" border="0">
					<tr>
						<td>
							<s:set name="form_name" value="'lotteryLuckDrawForm'" scope="request"></s:set>
							<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
						</td>
					</tr>
				</table>
				<!-- 底部 按钮条 -->
				<table width="60%" class="edit_bottom" height="30" border="0"
					cellpadding="0" cellspacing="0">
					<tr>
						<td align="left">
							<input class="saveBtn" type="button" value=" "
								onclick="selectOneAccount();">
						</td>
						<td width="20%" align="center"></td>
					</tr>
				</table>
			</s:form>
		</div>
	</body>
</html>

