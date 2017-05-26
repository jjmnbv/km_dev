<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>客户类别定义</title>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css">
<link href="/etc/js/dtree/dtree.css" type="text/css" rel="stylesheet">
<script language="JavaScript" type="text/javascript" src="/etc/js/dtree/dtree.js" ></script>
<Script language="JavaScript" type="text/javascript" src="/etc/js/Form.js"></Script>
<script language="JavaScript" src="/etc/js/dialog.js" type="text/javascript"></script>
<script language="JavaScript" src="/etc/js/jquery-latest.pack.js" type="text/javascript"></script>
<link href="/etc/css/style_besines.css" type="text/css" rel="stylesheet">

</head>
<body>
<s:form name="updateForm" action="cust_editCustType.action" method="POST"  namespace='/customers' onsubmit="return checkForm();"  ></s:form>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>
<s:if test='rtnMessage.equals("updateOK")'>
		<SCRIPT LANGUAGE="JavaScript">
		
            alert("菜单修改成功!");
		
		</SCRIPT>
</s:if>



<!-- 标题条 -->
<s:set name="parent_name" value="'业务开通'" scope="request"/>
<s:set name="name" value="'客户类别定义'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<div  style="height:90%;overflow-y:auto; " >
<!-- 按钮条 -->
 <table width="98%" align="center" height="50" border="0"	class="content_table"
			cellpadding="0" cellspacing="0">
			<tr> 
	    <td >
	    <input class="addBtn" TYPE="button"  onclick="addParentCusType();">
           
		</td>
	</tr>
</table>

<table width="98%" cellpadding="3" class="list_table" 
				cellspacing="0" border="1">
				
	<tr>
	    <td align="center"  width="12%"  valign="top" style="background-color:#F1F8F0">
	    	<div class="dtree" id="dtree_show" align="left"  >
				<p><a href="javascript: d.openAll();">展开所有</a> | <a href="javascript: d.closeAll();">关闭所有</a></p>
			   
				<script type="text/javascript">
				   d = new dTree('d');
					d.add(0,-1,'客户类别');
					<s:iterator id="custiterator"  value="list">
					d.add(<s:property value="customerTypeId"/>,<s:if test="customerTypeId==null||customerTypeId==''">0</s:if><s:else><s:property value="parentId"/></s:else>,'<s:property value="name"/>','cust_findCustType.action?customerTypeId=<s:property value="customerTypeId"/>','修改菜单-<s:property value="name"/>', 'ifr');
					</s:iterator>
					document.write(d);
				</script>
			</div>
	    </td>
	    <td align="left" width="70%" valign="top">
	    	<iframe id="ifr" name="ifr" width="100%" height="400" src="" frameBorder="no" scrolling="no" STYLE="background-color:transparency" allowTransparency="true"></iframe>
		</td>
	</tr>
</table>
<SCRIPT LANGUAGE="JavaScript">


function addParentCusType(){
window.document.updateForm.action = "cust_adddCustType.action";
window.document.updateForm.submit();
}
</SCRIPT>
</div>
</BODY>
</HTML>
