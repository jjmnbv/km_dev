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
<script type="text/javascript">
    /**  进入处理咨询的页面  **/
	function gotoConsultList()
	{
		document.forms[0].action= "/app/gotoQueryConsultList.action";
		document.forms[0].submit();
		//location.href="/app/gotoQueryConsultList.action";
	}
	
</script>
</head>
<s:set name="parent_name" value="'产品咨询评价管理'" scope="request" />
<s:set name="name" value="'产品咨询'" scope="request" />
<s:set name="son_name" value="'咨询回复'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<body>
	<s:form name="consultForm" method="post" action="/app/replyConsult.action">
	<input type="hidden" value="reply"  name="viewType"   />
    <input type="hidden"  name="searchConsut.consultId"  value="<s:property value='searchConsut.consultId'/>"    />

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
				  <td align="left"><s:property value="searchConsut.type" /></td>
		  </tr>
				<tr>
				  <td height="23" align="left">咨询时间：</td>
				  <td align="left"><s:date name="searchConsut.consultDate"  format="yyyy-MM-dd hh:mm:ss" /></td>
		  </tr>
				<tr>
				  <td height="23" align="left">咨询内容：</td>
				  <td align="left"><s:property value="searchConsut.consultContent" /></td>
		  </tr>
				<tr>
				  <td align="left">回       复：</td>
				  <td align="left"><label for="textarea"></label>
			      <textarea name="searchConsut.replyContent" id="textarea" cols="45" rows="5"></textarea></td>
		  </tr>
		</table>
        <table width="1025" height="50" border="0" align="center" style="float: left; position: absolute; top: 320px;">
			<tr>
					<td width="362" height="46" align="center">&nbsp;</td>
					<td width="653" align="left"><input type="submit" name="button"  class="btnStyle"    id="button" value="回复审核">
                    <input type="button" name="button"  class="btnStyle" onClick="gotoConsultList();"   id="button" value="返回">
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
</body>
</html>

