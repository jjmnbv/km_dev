<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>推广短信审核</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
 <script src="/etc/js/dialog.js"></script>
		<script type="text/javascript">
		
		window.onload = changeEmailText;
		function  changeEmailText(){
			var txtN = document.getElementsByName("emailContextName"); 
			var finalText = "";
			var finalTitle = "";
			for(i=0;i<txtN.length;i++){ 
				finalText = delHtmlTag(txtN[i].innerText);
				finalTitle = delHtmlTag(txtN[i].innerText);
				if(isNull(finalText)){
					finalText = "图片";
				}else if (finalText.length>10){
					finalText = finalText.substring(0, 10) +"..."
				}
				txtN[i].innerText= finalText;
				txtN[i].title = finalTitle;
			}
		}
		
		//去除html标签
		 function delHtmlTag(str){
			  return str.replace(/<[^>]+>/g,"").replace(/&nbsp;/ig, "");//去掉所有的html标记和空格
		 }
		
		//判断字符串为空 
		 function isNull( str ){
			 if ( str == "" ) return true;
			 var regu = "^[ ]+$";
			 var re = new RegExp(regu);
			 return re.test(str);
		 }
		


function  gotoComit(id)
{

	document.emailsmsverifyForm.action="/emailsmsverify/emailsmsverify_preVerify.action?emailSmsPromotion.promotionId="+ id;
	document.emailsmsverifyForm.submit();

}

//审核
function  applyVerify(id,caseNo)
{  
	if(caseNo==1){
		if(confirm("是否确认审核通过? ")==true){
			document.emailsmsverifyForm.action="/emailsmsverify/emailsmsverify_applyVerifyAgree.action?emailSmsPromotion.promotionId="+id+"&appStatus="+3;
			document.emailsmsverifyForm.submit();
		 }
	}
	else if(caseNo==2){
		if(confirm("是否确认审核拒绝? ")==true){
			document.emailsmsverifyForm.action="/emailsmsverify/emailsmsverify_applyVerifyRefuse.action?emailSmsPromotion.promotionId="+id+"&appStatus="+5;
			document.emailsmsverifyForm.submit();
		 }
	}
}

 function gotoSeach(id)
 {

	 document.emailsmsverifyForm.action="/emailsmsverify/emailsmsverify_preSerch.action?emailSmsPromotion.promotionId="+ id;
		document.emailsmsverifyForm.submit();

 }



</script>
	</head>
	<body>
		<!-- 导航栏 -->
		<s:set name="parent_name" value="'推广短信'" scope="request" />
		<s:set name="name" value="'模板审核'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<div style="height: 90%; overflow-y: auto;">
			<s:form name="emailsmsverifyForm" method="post" enctype="multipart/form-data"
				action="/emailsmsverify/emailsmsverify_pageList.action">
				<s:token/>
				<!-- 查询条件区域 -->
				<table width="98%" class="content_table" align="center"
					cellpadding="0" cellspacing="0">
					 
					<tr>
						<td width="100">
							推广标题：
						</td>
						<td>
							<input name="emailSmsPromotion.title" type="text"
								value="<s:property value='title'/>">
						</td>
						<td width="100">
							推广内容：
						</td>
						<td>
							<input name="emailSmsPromotion.promoteContent" type="text"
								value="<s:property value='promoteContent'/>">
						</td>
						<td>
							推广类型：
							<select name="emailSmsPromotion.promotionType">
							
									<option value="" <s:if test='emailSmsPromotion.promotionType==""'>selected="selected"</s:if> >
										请选择
									</option>
							
									<option value="1" <s:if test='emailSmsPromotion.promotionType=="1"'>selected="selected"</s:if>>
										短信
									</option>
							</select>
						</td>
					</tr>
					
					<tr>
						<td width="100">
							发布状态：
						</td>
						<td>
							<select name="emailSmsPromotion.status">
							<option value="1" selected="selected">待审核</option>
						</select>
						</td>
                        <td colspan="8" align="right">
							<INPUT TYPE="submit" class="queryBtn" value="">
						</td>
					</tr>
				</table>

				<!-- 数据列表区域 -->
				<table width="98%" class="list_table" cellpadding="3" align="center"
					cellspacing="0" border="1">
					<tr>
							<th>
							推广标题
						</th> 
						<th>
							推广内容
						</th>
						<th>
						   推广类型
						</th>
						<th>
							是否定时发布
						</th>
						<th>
							发布日期
						</th>
						<th>
							发布状态
						</th>
							
						
						<th>
							操作
						</th>
					</tr>
					<s:if test="#request.isMenu=='true'">
					<tr>
						<td colspan="100">
							请输入具体条件进行查询，如果想查询全部数据请直接点击查询按钮
						</td>
					</tr>
					</s:if>
					<s:else>
					<s:iterator id="custiterator" value="page.dataList">
						<tr>
							<td align="center" title="<s:property value="title" />">
								<s:if test="title.length()>10">
				   		 	 	<s:property value='title.substring(0,10)'/>...
					 			</s:if>
					 			<s:else>
					 			<s:property value='title'/>
					 			</s:else>
							</td>
							<s:if test="#custiterator.promotionType==1">
							<td align="center" title="<s:property value="smsText" />">
								<s:if test="smsText.length()>10">
				   		 	 	<s:property value='smsText.substring(0,10)'/>...
					 			</s:if>
					 			<s:else>
					 			<s:property value='smsText'/>
					 			</s:else>
							</td>
							</s:if>
							
							<s:if test="#custiterator.promotionType==2">
							<td align="center" name="emailContextName" title="<s:property value="emailContext" />"  >
					 			<s:property value='emailContext'/>
							</td>
							</s:if>
							<td align="center">
								<s:if test="#custiterator.promotionType==1">短信</s:if>
								<s:if test="#custiterator.promotionType==2">邮件</s:if>
							</td>
							<td align="center">
								<s:if test="#custiterator.isTime==1">是</s:if>
								<s:if test="#custiterator.isTime==0">否</s:if>
							</td>
							<td align="center">
								<s:date name="timingDate" format="yyyy-MM-dd HH:mm:ss" />
							</td>
                            
							<td align="center">
							<s:if test="#custiterator.status==4">已发布</s:if>
                                 <s:if test="#custiterator.status==3">审核通过</s:if>
                                 <s:if test="#custiterator.status==2">审核拒绝</s:if>
								<s:if test="#custiterator.status==1">待审核</s:if>
								<s:if test="#custiterator.status==0">草稿</s:if>
							</td>
								<%-- <td align="center">
								 
								<s:if test="#custiterator.smsType==3">广告短信</s:if>
							</td> --%>
							
						 
							
							<td align="center">							
								 <s:if test="#custiterator.status==1">
								 	<img title="详情" style="cursor: pointer;"
										src="/etc/images/icon_msn.gif"
										onclick='gotoComit(<s:property value="promotionId"/>)' />
										
										<img title="审核通过" style="cursor: pointer;"
										src="/etc/images/sus.gif"
										onclick='applyVerify(<s:property value="promotionId"/>,1)' />
										
										<img title="审核拒绝" style="cursor: pointer;"
										src="/etc/images/stop.png"
										onclick='applyVerify(<s:property value="promotionId"/>,2)' />
								 </s:if>
							</td>
						</tr>
					</s:iterator>
					</s:else>
				</table>

				<s:if test="#request.isMenu!='true'">
				<table width="98%" align="right">
					<tr>
						<td>
							<s:set name="form_name" value="'emailsmsverifyForm'"
								scope="request"></s:set>
							<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
						</td>
					</tr>
				</table>
				</s:if>
			</s:form>
			<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
		</div>
	</body>
</html>

