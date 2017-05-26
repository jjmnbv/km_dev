<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>报表管理</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
</head>
<body>

<s:if test='rtnMessage.equals("updateOk")'>
    <SCRIPT LANGUAGE="JavaScript">
    <!--
        alert("报表修改成功!");
    //-->
    </SCRIPT>
</s:if>

<s:form action="listReportMain.action" method="POST"  namespace='/report'>

<!-- 标题条 -->
<div class="pagetitle">报表管理:</div>

<!-- 按钮条 -->
<table width="98%" align="center" class="topbuttonbar" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
	    <td width="90%" valign="middle">
            <INPUT class="btngreen" TYPE="button" value="+ 新增 " onclick="gotoAdd();">
			<input class="btngreen" type="button" value="- 删除 "  onclick="gotoDel('delId');">
		</td>
	    <td width="10%" align="center">
            &nbsp;
        </td>
	</tr>
</table>

<!-- 查询条件区域 -->
<table  width="98%" class="searcharea" align="center" cellpadding="0" cellspacing="0" >
	<tr>
		<td align="right">报表名称：</td>
		<td>
		     <input name="reportName" type="text" value="<s:property value='model.reportName'/>">
		</td>
		<td width="10%" align="center">
			<INPUT TYPE="submit" class="btngray" value=" 查询 ">
		</td>
	</tr>
</table>

<!-- 数据列表区域 -->
<table width="98%" class="tableStyle1" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
	<tr>
	    <th align="center" width="5%">
            <input type='checkbox' name='allbox' onclick='checkAll(this)'>
		</th>
		<th align="center" width="15%">报表编号</th>
        <th align="center" width="20%">报表名称</th>
		<th align="center" width="45%">SQL语句</th>
		<th align="center" width="15%">操作</th>
	</tr>
	<s:iterator id="custiterator" value="reportList">
	<tr>
	    <td align="center">
		    <input type="checkbox" name="delId"  value='<s:property value="reportId"/>'>
		</td>
		<td align="center">
            <s:property value="reportNo"/>
        </td>
		<td align="center">
            <a href="/report/gotoReportMainUpdate.action?reportId=<s:property value='reportId'/>"><s:property value="reportName"/></a>
        </td>
        <td align="center">
            <s:property value="reportSqlFrom"/>&nbsp;<s:property value="reportSqlWhere"/>
        </td>
        <td align="center">
            <!--INPUT TYPE="button" class="btngray" value=" 修改 " onclick="gotoUpdate(<s:property value='reportId'/>)"-->
            <INPUT TYPE="button" class="btngray" value="报表查询" onclick="gotoSearch(<s:property value='reportId'/>)">
        </td>
	</tr>
	</s:iterator>
</table>

<br>

</s:form>


<SCRIPT LANGUAGE="JavaScript">
<!--
//新增
function gotoAdd(){
    location.href="/report/gotoReportMainAdd.action";
}
//修改
function gotoUpdate(reportId){
    location.href="/report/gotoReportMainUpdate.action?reportId="+reportId;
}
//报表查询
function gotoSearch(reportId){
    location.href="/report/gotoReportQuery.action?reportId="+reportId;
}

function checkAll(ck)
{
  for (var i=0;i<ck.form.all.tags("input").length;i++){
    var ele = ck.form.all.tags("input")[i];
    /*var ct = ele.getAttribute("type");*/
    if ((ele.type=="checkbox")){
      if(ck.checked!=ele.checked)
        ele.click();
    }
  }
}

function checkSelected(sName){
   window.event.returnValue = false;
   var chs = document.getElementsByName(sName);
   for(var i=0;i<chs.length;i++){
   	  var ele = chs[i];
   	  if(ele.type=="checkbox" && ele.checked==true)
	   	  return true;
   }
   return false;
}

function gotoDel(sName)
{

   if (!checkSelected(sName))
   {
      alert("请选择要删除的数据！");
      return false;
   }
   if (confirm("你确定要删除选中的数据？"))
   {
      doDelete(sName);
   }
}

function doDelete(){
	document.forms[0].action="deleteReportMain.action";
	document.forms[0].submit();
}

//-->
</SCRIPT>
</BODY>
</HTML>

