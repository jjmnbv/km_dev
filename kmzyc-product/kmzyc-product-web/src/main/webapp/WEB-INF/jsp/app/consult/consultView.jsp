<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>增加明细</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>


<s:form name="popForm" action="saveSectionsDetail.action" method="POST"  namespace='/sys' onsubmit="javasript:Validator.Validate(this,3);parent.closePopDiv2();"
enctype="multipart/form-data">

<!-- hidden properties -->
	<table width="95%" class="tableInput1" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
		<tr> 
			<th colspan="2" align="left" class="modeltitle">咨询详情</th>
		</tr>
		<tr> 
			<th width="30%" align="right">产品标题：</th>
			<td width="70%" align="left"> 
            <s:property value="searchConsut.productTitle"  /></td>
		</tr>
		<tr> 
			<th height="33" align="right">咨询时间：</th>
			<td align="left"> 
			<s:date name="searchConsut.consultDate" format="yyyy-MM-dd hh:mm:ss" />
			 </td>
		</tr>
		<tr>
		  <th align="right">咨询内容：</th>
		  <td align="left">
           <s:property value="searchConsut.consultContent"  />
          </td>
	  </tr>
		<tr> 
			<th align="right">回复内容：</th>
			<td align="left"> 
             <s:property value="searchConsut.replyContent"  />
			</td>
		</tr>
		<tr>
		  <th align="right">回复人ID：</th>
		  <td align="left">
         <s:property value="searchConsut.replyPerson"  />
          </td>
	  </tr>
		<tr>
		  <th align="right">回复状态：</th>
		  <td align="left">
            <s:property   value="#request.ConsultReplyMap[searchConsut.replyStatus]"  />
          </td>
	  </tr>
		<tr>
		  <th align="right">审核人ID：</th>
		  <td align="left">
           <s:property value="searchConsut.checkManId"  />
          </td>
	  </tr>
		<tr> 
			<th align="right">审核人：</th>
			<td align="left"> 
            <s:property value="searchConsut.checkMan"  />
			</td>
		</tr>
		<tr>
		  <th align="right">审核状态：</th>
		  <td align="left"> 
         
          <s:property   value="#request.ConsultCheckMap[searchConsut.checkStatus]"  />
          </td>
	  </tr>
		<tr>
		  <th align="right">备注：</th>
		  <td align="left"> <s:property value="searchConsut.remark"  /></td>
	  </tr>

		<tr>
			<td colspan="2" align="center">&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="btnStyle" value=" 关闭 " onClick="closeDiv()"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

		  </td>
		</tr>
	</table>




</s:form>
<SCRIPT LANGUAGE="JavaScript">


function closeDiv(){
	parent.closeThis();
}



</SCRIPT>
</BODY>
</HTML>