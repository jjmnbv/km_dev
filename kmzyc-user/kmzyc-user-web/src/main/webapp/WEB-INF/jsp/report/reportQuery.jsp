<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.opensymphony.xwork2.ognl.OgnlValueStack"%>
<%@ page import="java.util.List"%>
<%@ page import="com.pltfm.sys.bean.SysParamBean"%>
<%@ page import="com.pltfm.sys.model.SysParam"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.sql.ResultSet"%>
<%
    
	OgnlValueStack stack = (OgnlValueStack)request.getAttribute("struts.valueStack");	
	List paramsList= (List)stack.findValue("paramsList");
    SysParamBean bean = new SysParamBean();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>报表查询</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<script language="JavaScript" src="/etc/js/dialog.js" type="text/javascript"></script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css">
<Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
<script src="/etc/js/jquery-latest.pack.js"></script>
</head>
<body>

<s:form name="form1" action="/report/reportQuery.action" method="POST" target="blank" namespace='/report'>


<INPUT TYPE="hidden" name="reportId" value="<s:property value='model.reportId'/>">

<!-- 标题条 -->
<div class="pagetitle"><s:property value="model.reportName"/>:</div>

<!-- 按钮条 -->
<table width="98%" align="center" class="topbuttonbar" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
	    <td width="90%" valign="middle">&nbsp;
			
		</td>
	    <td width="10%" align="center">
            <a href="#"  onClick="gotoList();"><input class="backBtn" onclick="gotoList()" type="button" value=""></a>
        </td>
	</tr>
</table>

<table  width="98%" class="searcharea" align="center" cellpadding="0" cellspacing="0" >
    <s:iterator id="custiterator" value="paramsList" status="st">   <!-- 循环显示的参数-->
    <s:set id="code" name="code" scope="request" value="paramCode"/>
    <s:set id="sql" name="sql" scope="request" value="paramSql"/>
        <tr>
            <td align="right" width="40%"><s:property value="paramName"/>：</td>
            <td align="left" width="60%"> 
                <!--判断增加的数据控件-->
                <s:if test="controlType eq 1">     <!--文本-->
                    <!--如果是时间类型-->
                    <s:if test="dataType eq 4"> 
                        <input id="param<s:property value='#st.count'/>" namestr="<s:property value='paramId'/>" type="text" name="<s:property value='paramId'/>" class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="">
                    </s:if>
                    <!--如果是数字类型-->
                    <s:elseif test="(dataType ne 3)&&(dataType ne 6)">  
                        <input id="param<s:property value='#st.count'/>"  namestr="<s:property value='paramId'/>" type="text" name="<s:property value='paramId'/>" onBlur="check_inputNumber(this.value)">
                    </s:elseif>
                    <!--如果是字符类型-->
                    <s:else>
                        <input id="param<s:property value='#st.count'/>"  namestr="<s:property value='paramId'/>" type="text" name="<s:property value='paramId'/>">
                    </s:else>
                </s:if>
                <s:if test="controlType eq 2">      <!--读取参数表-->
                <%
                    String code = (String)request.getAttribute("code");
                    //List optList = bean.getSysParamList("all",code);    //得到参数列表
                    List optList = null;  
                %>
                    <select id="param<s:property value='#st.count'/>"  namestr="<s:property value='paramId'/>" name="<s:property value='paramId'/>">
                        <option value=""></option>
                <%
                    if(optList!=null && optList.size()>0){      //循环显示各参数
                        for(int i=0;i<optList.size();i++){
                            SysParam sysParam = (SysParam)optList.get(i);
                %>
                        <option value="<%=sysParam.getParamValue()%>">
                            <%=sysParam.getParamName()%>
                        </option>
                <%      
                        }
                    }
                %>
                    </select>
                </s:if>
                <s:if test="controlType eq 3">      <!--读取其它表-->
                <%
                    String sql = (String)request.getAttribute("sql");
                    //List list = reportBean.executeSql(sql);     //得到读取显示列表
                    List list = null;    
                %>
                    <select id="param<s:property value='#st.count'/>"  namestr="<s:property value='paramId'/>" name="<s:property value='paramId'/>">
                        <option value=""></option>
                <%
                    if(list!=null && list.size()>0){    //循环显示
                        for(int j=0;j<list.size();j++){
                            Object[] obj = (Object[])list.get(j);
                            String id = obj[0].toString();
                            String name = obj[1].toString();
                %>
                        <option value="<%=id%>">
                            <%=name%>
                        </option>
                <%      
                        }
                    }
                %>
                    </select>

                </s:if>
            </td>
        </tr>
    </s:iterator>
	<tr>
		<td align="center" colspan="2">
			<INPUT TYPE="submit" class="btngray" value=" 查询 " >
		</td>
	</tr>
</table>

<br>

</s:form>


<SCRIPT LANGUAGE="JavaScript">
<!--

//返回列表页面
function gotoList(){
    location.href="/report/listReportMain.action";
}


//报表查询弹出窗口
/*
function openNewWindow(){
    var url = "/report/reportQuery.action?reportId="+reportId;

    for(var i=1;i<=paramCount;i++){
        var id = "param"+i;
        var paramId = document.getElementById(id).value;        //参数值
        var paramName = document.getElementById(id).namestr;    //参数名
        url += "&"+paramName+"="+paramId;
    }     
    alert("url="+url);
    //if(childWin==null || childWin.closed){         
        childWin=window.open(url,
                             "qfLoanCtrPrint", "top=0"+"; left=0"+"; height=768,width=1024,status=NO,toolbar=NO,menubar=NO,scrollbars=Yes,location=NO,resizable=yes");
        childWin.parentWin = this;
    //} 

}
*/

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


function check_inputNumber(theValue){
	if(!isNumber(theValue)){ 
        alert("此项只允许输入数字！"); 
        return false;
	} 
}

/*判断是否用户输入全为数字*/ 
function isNumber(ui) { 
  var notValid=/\D{1,}/; 
  return (!notValid.test(ui));
} 
//-->
</SCRIPT>
</BODY>
</HTML>

