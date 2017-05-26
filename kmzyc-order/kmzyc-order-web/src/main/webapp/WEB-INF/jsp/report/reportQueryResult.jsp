<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.opensymphony.xwork2.ognl.OgnlValueStack"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<%@ page import="com.pltfm.sys.model.ReportColumnSetting"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.pltfm.sys.bean.ReportMainBean"%>
<%@ page import="java.sql.ResultSet"%>
<%	 
	OgnlValueStack stack = (OgnlValueStack)request.getAttribute("struts.valueStack");	
	List colsList= (List)stack.findValue("colsList");
    String userName = (String)stack.findValue("userName");
    List resultList= (List)stack.findValue("resultList");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:property value="model.reportName"/></title>
<Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
<Script language="JavaScript" src="/etc/js/jquery-latest.pack.js" type="text/javascript"></Script>
<style>
th{
    font:bold 14px "宋体"; 
}
td{
    font:normal 14px "宋体"; 
}
</style>
</head>
<body onkeydown="changeKey();">
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>
<s:form name="form1" action="/report/generateExcel.action" method="POST"  namespace='/report' onsubmit="return  Validator.Validate(this,3)">


<input type="hidden" id="reportId" name="reportId" value="<s:property value='model.reportId'/>">
<input type="hidden" id="viewSql" name="viewSql" value="<s:property value='viewSql'/>">

<table width="<s:property value='model.dispPn'/>%"  align="center" border="0" cellspacing="6" cellpadding="0">
    <tr>
	    <td colspan="2" align="center" style="padding-bottom: 8px">
            <font size="5"><b><s:property value="model.reportName"/></b></font></td>
	</tr>
	<tr>
	    <td align="left">制表人：<U>&nbsp;&nbsp;<%=userName%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</U></td>
		<td align="right">制表日期：_________年_____月_____日</td>
	</tr>
</table>

<table width="<s:property value='model.dispPn'/>%" align="center" border="1" style="border-collapse: collapse; " bordercolor="#111111" cellspacing="0" cellpadding="4">
  <tr style="background-color: #E4E4E4">
        <s:iterator value="colsList">
            <th align="center"><s:property value="colNameCn"/></th> <!--显示表格标题-->
		</s:iterator>
	</tr>
<%
	    java.text.DecimalFormat df = new java.text.DecimalFormat("###,##0.00"); 
        Double []countStr = new Double[colsList.size()];     //用于保存合计的数据
        if(resultList!=null && resultList.size()>0){
            for(int i=0;i<resultList.size();i++){    //循环显示表格数据
                Object[] obj = (Object[])resultList.get(i);
%>
	<tr>
<%
                 for(int j=0;j<obj.length;j++){       //显示各列数据
                    Object val = obj[j];
%>
        <td align="center">
<%
                    if(colsList!=null && colsList.size()>0){    
                        ReportColumnSetting colVo = (ReportColumnSetting)colsList.get(j);
						//判断列的类型（'1'INT，'2'DOUBLE，'3'STRING，'4'DATATIME,'5'LONG,'9'系统param）

						 //处理合计数  '1'INT，'2'DOUBLE， '5'LONG
						if( ("1".equals(colVo.getColType())||"2".equals(colVo.getColType())||"5".equals(colVo.getColType()))  &&  "1".equals(colVo.getIsSum())){
							if(i==0){
								if(val!=null  &&  !"".equals(val))
								    countStr[j] = Double.valueOf(val.toString());
								else
                                    countStr[j] = Double.valueOf("0.0");
							}else{
								if(val!=null  &&  !"".equals(val))
								    countStr[j] += Double.valueOf(val.toString());
							}
						}

						//如果是Double类型   '2'DOUBLE
                        if("2".equals(colVo.getColType())){    //
                            if(val!=null){      //如果结果数据不为空
%>
            <%=df.format((Double)val)%>
<%
                            }else{
%>
            &nbsp;
<%
                            }
                            continue;
                        }

						//如果是时间格式     '4'DATATIME
                        if("4".equals(colVo.getColType())){ 
                            if(val!=null){      //如果结果数据不为空
%>
            <%=new SimpleDateFormat("yyyy-MM-dd").format((Date)(val))%>
<%
                            }else{
%>
            &nbsp;
<%              
                            }
                            continue;
                        }

                        //如果是PARAM参数   '9'系统param
                        if("9".equals(colVo.getColType())){ 
                            if(val!=null){      //如果结果数据不为空
%>
            <%=StaticParams.getParamNameByCd("all",colVo.getParamGp(),val.toString())%>
<%
                            }else{
%>
            &nbsp;
<%              
                            }
                            continue;
                        }
						//如果是其它类型
						else{
                            if(val!=null){
%>
            <%=val.toString()%>
<%                  
                            }else{
%>
            &nbsp;
<%
                            }
                            continue;
                        }
                    }
%>
        </td>
<%
                }   // end for()
%>
	</tr>
<%
	}   // end for()
}  // end if
%>
    <tr>
        <td align="center"><b>合计</b></td>    <!--显示合计的内容-->
<%
            if(colsList!=null && colsList.size()>0){
                for(int k=1;k<colsList.size();k++){
%>
            <td align="center">
<%
                if(countStr[k]==null){
%>
            /
<%}else{%>
                <%=df.format(countStr[k])%>
<%}%>
            </td>
<%
                }
            }
%>
    </tr>
</table>

<br>
<p align="center">
    <input type="button" value="  打 印  " id="printBtn" onclick="hiddenThis();">&nbsp;&nbsp;
    <input type="submit" value="  生成excel  " id="excelBtn">&nbsp;&nbsp;
    <input type="button" value="关闭" id="closeBtn" onclick="window.close();">&nbsp;&nbsp;
</p></p>

<br>
<br>
</s:form>

<SCRIPT LANGUAGE="JavaScript">
<!--

//打印
function hiddenThis()
{
    document.getElementById("printBtn").style.visibility = "hidden";
    document.getElementById("closeBtn").style.visibility = "hidden";
    document.getElementById("excelBtn").style.visibility = "hidden";
    window.print();
    document.getElementById("printBtn").style.visibility = "visible";
    document.getElementById("closeBtn").style.visibility = "visible";  
    document.getElementById("excelBtn").style.visibility = "visible";
}

</SCRIPT>
</BODY>
</HTML>