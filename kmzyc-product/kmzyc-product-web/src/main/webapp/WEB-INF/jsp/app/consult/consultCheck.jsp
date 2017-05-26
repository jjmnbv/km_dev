<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="/etc/js/qtip/jquery.min.1.8.3.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<title>评价管理</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">

<style type="text/css">
.tableStyle1 {
	font-size: 12px;
}
</style>

<script  type="text/javascript">
    /**打回的处理**/
	function refuse_a()
	{
		var onsutRemarkval=$("#textarea").val();
		var consult = $("#consultId").val();
		location.href="/app/replyConsult.action?viewType=refuse&pre_consultId="+consult+"&remark="+encodeURI(encodeURI(onsutRemarkval));
	}
	
 /**  进入处理咨询的页面  **/
	function gotoConsultList()
	{
		//location.href="/app/gotoQueryConsultList.action";
		document.forms[0].action= "/app/gotoQueryConsultList.action";
		document.forms[0].submit();
	}
	
</script>
</head>
<s:set name="parent_name" value="'产品咨询评价管理'" scope="request" />
<s:set name="name" value="'产品咨询'" scope="request" />
<s:set name="son_name" value="'咨询审查'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

<body>
	<s:form name="consultForm" id="consultForm"  method="post" action="/app/replyConsult.action">
    <input type="hidden" id="status_type" value="check"  name="viewType"   />
    <input type="hidden"  id="consultId"  name="searchConsut.consultId"  value="<s:property value='searchConsut.consultId'/>"    />

		<!-- 标题条 -->
		<!--<div class="pagetitle">栏目管理:</div>
		<!-- 按钮条 -->
		

		<!-- 查询条件区域 --><!-- 数据列表区域 -->
		<table width="61%" height="252" border="1" align="left" cellpadding="3"
			cellspacing="0" bordercolor="#C1C8D2" class="tableStyle1">
			<tr>
				<th align="left" width="15%">产品标题：</th>
				<th width="85%" align="left"><s:property value='searchConsut.productTitle'/></th>
		  </tr>
			 
				<tr>
					<td width="15%" height="23" align="left"> 
				    咨询类型：</td>
				  <td align="left">   <s:property value="#request.ConsultTypeMap[searchConsut.type]" /></td>
		  </tr>
				<tr>
				  <td height="23" align="left">咨询时间：</td>
				  <td align="left"><s:date  name="searchConsut.consultDate"  format="yyyy-MM-dd hh:mm:ss" /></td>
                 
		  </tr>
				<tr>
				  <td height="23" align="left">咨询内容：</td>
				  <td align="left"><s:property value="searchConsut.consultContent" /></td>
		  </tr>
				<tr>
				  <td height="23" align="left">回复内容：</td>
				  <td align="left"><s:property value="searchConsut.replyContent" /></td>
		  </tr>
				<tr>
				  <td height="23" align="left">回复人：</td>
				  <td align="left"><s:property value="searchConsut.replyPerson" /></td>
		  </tr>
				<tr>
				  <td align="left">备       注：</td>
				  <td align="left"><label for="textarea"></label>
			      <textarea name="searchConsut.remark" id="textarea" cols="45" rows="5"><s:property value="searchConsut.remark" /></textarea></td>
		  </tr>
		</table>
            <table width="1025" height="30" border="0" align="center" style="float: left; position: absolute; top: 320px;">
				<tr>
					<td align="center"> 
           <input type="submit" name="button" id="button" value="通过"  class="btn-custom btnStyle"  >
             <input type="button" name="button" onClick="refuse_a()"  id="refuse" value="拒绝"  class="btn-custom btnStyle"   />
             
               <input type="button" name="button" onClick="gotoConsultList();"  value="返回" class=" btn-custom btnStyle"  />
               
</td>
				</tr>
			</table>
    <input type="hidden" value="<s:property value='newSearchConsut.productTitle'/>" name="newSearchConsut.productTitle"></input>
	<input type="hidden" value="<s:property value='newSearchConsut.consultContent'/>" name="newSearchConsut.consultContent"></input>
	<input type="hidden" value="<s:property value='newSearchConsut.replyStatus'/>" name="newSearchConsut.replyStatus"></input>
	<input type="hidden" value="<s:date name='newSearchConsut.consultStart'  format='yyyy-MM-dd' />" name="newSearchConsut.consultStart"></input>
	<input type="hidden" value="<s:date name='newSearchConsut.consultEnd'  format='yyyy-MM-dd' />" name="newSearchConsut.consultEnd"></input>
	<input type="hidden" value="<s:property value='newSearchConsut.type'/>" name="newSearchConsut.type"></input>
       
<!-- 分页按钮区 --></s:form>
<form method="post">
	<input type="hidden" value="<s:property value='newSearchConsut.productTitle'/>" name="newSearchConsut.productTitle"></input>
	<input type="hidden" value="<s:property value='newSearchConsut.consultContent'/>" name="newSearchConsut.consultContent"></input>
	<input type="hidden" value="<s:property value='newSearchConsut.replyStatus'/>" name="newSearchConsut.replyStatus"></input>
	<input type="hidden" value="<s:date name='newSearchConsut.consultStart'  format='yyyy-MM-dd' />" name="newSearchConsut.consultStart"></input>
	<input type="hidden" value="<s:date name='newSearchConsut.consultEnd'  format='yyyy-MM-dd' />" name="newSearchConsut.consultEnd"></input>
	<input type="hidden" value="<s:property value='newSearchConsut.type'/>" name="newSearchConsut.type"></input>
	</form>
</body>
</html>

