﻿<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<table width="100%" border=0  cellspacing="0" cellpadding="5">
<tr height="1" class="toolbar" valign="right" border=0>
		<td valign="bottom" align="left">

			共<font color='#008000'> <s:property value='page.recordCount'/> </font>条记录，
			第<font color='#008000'> <s:property value='page.pageNo'/> </font>页，
			共<font color='#008000'> <s:property value='page.pageCount'/> </font>页
</td>
<td align="right">
			跳转到
			<INPUT TYPE="text" id="pageNo" name="page.pageNo" size="1" value="<s:property value='page.pageNo'/>" onchange="goPage(this.value)" style="width: 16px; height: 18px">
			页
			&nbsp;&nbsp;&nbsp;

<s:if test="(page.pageCount==0)||(page.pageCount==1)">
	<img src="/etc/images/ICON_FIRST.gif" alt="首页" align="absmiddle" border=0>
	<img src="/etc/images/ICON_PRIOR.gif" alt="上页" align="absmiddle" border=0>
	<img src="/etc/images/ICON_NEXT.gif" alt="下页" align="absmiddle" border=0>
	<img src="/etc/images/ICON_LAST.gif" alt="末页" align="absmiddle" border=0>
</s:if>
<s:else>
    <s:if test="page.pageNo==1">
		<img src="/etc/images/ICON_FIRST.gif" alt="首页" align="absmiddle" border=0>
		<img src="/etc/images/ICON_PRIOR.gif" alt="上页" align="absmiddle" border=0>
		<a href="#" onClick="javascript:nextPage();"><img src="/etc/images/ICON_NEXT.gif" alt="下页" align="absmiddle" border=0></a>
		<a href="#" onClick="javascript:lastPage();"><img src="/etc/images/ICON_LAST.gif" alt="末页" align="absmiddle" border=0></a>
    </s:if>
	<s:elseif test="page.pageNo==page.pageCount">
		<a href="#" onClick="javascript:firstPage();"><img src="/etc/images/ICON_FIRST.gif" alt="首页" align="absmiddle" border=0></a>
		<a href="#" onClick="javascript:priorPage();"><img src="/etc/images/ICON_PRIOR.gif" alt="上页" align="absmiddle" border=0></a>
		<img src="/etc/images/ICON_NEXT.gif" alt="下页" align="absmiddle" border=0>
		<img src="/etc/images/ICON_LAST.gif" alt="末页" align="absmiddle" border=0>
    </s:elseif>
    <s:else>
		<a href="#" onClick="javascript:firstPage();"><img src="/etc/images/ICON_FIRST.gif" alt="首页" align="absmiddle" border=0></a>
		<a href="#" onClick="javascript:priorPage();"><img src="/etc/images/ICON_PRIOR.gif" alt="上页" align="absmiddle" border=0></a>
		<a href="#" onClick="javascript:nextPage();"><img src="/etc/images/ICON_NEXT.gif" alt="下页" align="absmiddle" border=0></a>
		<a href="#" onClick="javascript:lastPage();"><img src="/etc/images/ICON_LAST.gif" alt="末页" align="absmiddle" border=0></a>
    </s:else>
</s:else>

</td>
</tr>
</table>
<Script LANGUAGE="JavaScript">
function firstPage()
{
    document.getElementById('pageNo').value=1;
    goPage();
}
function lastPage()
{
    document.getElementById('pageNo').value=<s:property value='page.pageCount'/>;
    goPage();
}
function priorPage()
{
    document.getElementById('pageNo').value=parseInt(document.getElementById('pageNo').value)-1;
    goPage();
}
function nextPage()
{
    document.getElementById('pageNo').value=parseInt(document.getElementById('pageNo').value)+1;
    goPage();
}
function goPage()
{
	
  var re = /^[1-9]\d*$/i;
  var s = document.getElementById('pageNo').value;
    if (re.test(s))
    {
        document.forms[0].submit();
    }else{
    	alert("页码必须是正整数");
    }
}


/**
*以下函数用于记录的选择
*/
function checkCkSelected(oForm){
	window.event.returnValue = false;

	for (var i=0;i<oForm.all.tags("input").length;i++){
		var ele = oForm.all.tags("input")[i];
		var ct = ele.getAttribute("type");
		if ((ele.type=="checkbox")&&(ele.checked==true))
		return true;
	}
	return false;
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

function deleteSelected(sName)
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

function outlineMyRow(ckr)
{
    var otr = ckr.parentElement.parentElement;
    if(otr.tagName.toUpperCase()=="TR"){
     if(ckr.checked==true){
      
      ckr.ocls = otr.className;
      otr.className="select";
      }else{
       otr.className=ckr.ocls;
      }
    }
}
</Script>