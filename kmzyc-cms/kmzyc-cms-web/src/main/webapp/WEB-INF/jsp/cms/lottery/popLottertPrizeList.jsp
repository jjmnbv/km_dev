<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
	<head>
		<title>选择奖项</title>
			<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet"
			type="text/css" />
		<script type="text/javascript" src="/etc/js/jquery-latest.pack.js"></script>
		<script src="/etc/js/dialog.js"></script>
		<script type="text/javascript" src="/etc/js/pageCommon.js"></script>
		<script type="text/javascript" src="/etc/js/checkeds.js"></script>
		<script type="text/javascript" src="/etc/js/rowDisplay.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.validate.js"></script>
		<script type="text/javascript" src="/etc/js/messages_cn.js"></script>
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		<script>

   function selectOneAccount(){   
    var obj = document.getElementsByName("lotteryPrizeIds");
    	var windowId = $("#windowId").val();
		var objLen = obj.length;
		var i;
		var k = 0;
		var value;
		var checkeds = "";
           	checkeds = $("#checkeds").val();
		for (i = 0; i < objLen; i++) {
		
			if (obj[i].checked == true) {
				k = k + 1
                value=obj[i].value+",";
			}

		}
		if (k == 0) {
			alert("请选择要绑定的奖奖项！");
			return false;
		}
		$.ajax({
	 		url:"/cms/cmsLotteryPrize_checkLotteryPrize.action",
	 		async:false,
	 		data:"windowId="+windowId,
        	type:'post',
	 		success:function(result)
	 		{
	 		   ok=result;
	 		}
	 	});
	 	if(ok==0)
	 	{
	 	alert("请先绑定抽奖活动！");
	 		return false;
	 	}
      parent.closeLotteryPrize(checkeds,windowId);
    }
	</script>
	</head>
	<body>
		<!-- 导航栏 -->
		<div style="height: 90%; overflow-y: scroll;">
			<s:form class="pageForm" name="lotteryAwardsForm" id="lotteryAwardsForm"
				onsubmit="return checkAllTextValid(this)"
				action="/cms/cmsLotteryPrize_queryForPage.action" method="post">
				<table width="98%" align="center" height="90" border="0"
					class="content_table" cellpadding="0" cellspacing="0">
					<tr>
					</tr>
					<td align="left">
						奖项标题：
						<input name="lotteryAwards.title" type="text"
							value="<s:property value="lotteryPrize.title"/>">
					</td>
					
					
					<td align="right">
			<INPUT TYPE="submit" class="queryBtn" value="">
			<input type="hidden" name="checkeds" id="checkeds" value="<s:property value='checkeds'/>"/>
			<input type="hidden" id="windowId" name="windowId" value='<s:property value="windowId"/>'>
			<input type="hidden" id="pageId" name="pageId" value='<s:property value="pageId"/>'>
			<input type="hidden" id="adminType" name="adminType" value='<s:property value="adminType"/>'>
					</td>

					</tr>
				</table>
				<table width="98%" class="list_table" cellpadding="3" align="center">
					<tr>
						<th width="5%">
							<input type='checkbox' name='promotion' id="promotion"
							value="<s:property value="awardsId"/>"	onclick="checkAllpop(this,'lotteryPrizeIds')">
						</th>
						<th>
							奖项标题
						</th>
						<th>
							中奖概率
						</th>
						<th>
							奖项数量
						</th>
						<th>
							开奖时间
						</th>
						
					</tr>
					<s:iterator id="lotteryPrize" value="page.dataList">
						<tr>
							<td width="5%">
							 <s:if test="flag!=1">
								<input type="checkbox" name="lotteryPrizeIds"
									value='<s:property value="awardsId"/>'
									onclick="checkpromotionId(this);">
							</s:if>
							</td>
							<TD>
								<s:property value="title" />
							</TD>
							<TD>
								<s:property value="probability" />
							</TD>
							<TD>
								<s:property value="awardsCount" />
							</TD>
							<TD>
							<s:date name="awardsDate" format="yyyy-MM-dd HH:mm:ss"/>
							</TD>
							
							
						</tr>
					</s:iterator>
				</table>
				<table class="page_table" width="98%" align="center" cellpadding="0"
					cellspacing="0" border="0">
					<tr>
						<td>
							<s:set name="form_name" value="'lotteryAwardsForm'" scope="request"></s:set>
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

