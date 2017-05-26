<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改报表信息</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css">
<Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
<script language="JavaScript" src="/etc/js/dialog.js" type="text/javascript"></script>
<script src="/etc/js/jquery-latest.pack.js"></script>
</head>
<body onkeydown="changeKey();">
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>


<s:if test='rtnMessage.equals("addOk")'>
    <SCRIPT LANGUAGE="JavaScript">
    <!--
        alert("基本信息保存成功，请继续配置其他内容...!");
    //-->
    </SCRIPT>
</s:if>

<s:form name="form1" action="doUpdateReportMain.action" method="POST"  namespace='/report' onsubmit="return Validator.Validate(this,3)">

<!-- hidden properties -->
<INPUT TYPE="hidden" name="reportId" value="<s:property value='model.reportId'/>">

<!-- 标题条 -->
<div class="pagetitle">修改报表信息:</div>

<!-- 按钮条 -->
<table width="98%" align="center" class="topbuttonbar" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
	    <td width="90%" valign="middle">
            <INPUT class="btngreen" TYPE="submit" value=" 保存 ">
            <INPUT class="btngreen" TYPE="button" value=" 预览sql语句 " onclick="viewSql();">
			<INPUT class="btngreen" TYPE="button" value=" 报表查询 " onclick="gotoSearch(<s:property value='model.reportId'/>);">
		</td>
	    <td width="10%" align="center">
            <a href="#" onclick="gotoList();">>&nbsp;返回&nbsp;</a>
        </td>
	</tr>
</table>
<br><br>

<!-- 数据编辑区域 -->


<!--报表主信息-->
<table width="85%" class="tableInput1" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
    <tr> 
		<th colspan="4" align="left" class="modeltitle">基本信息</th>
	</tr>
    <tr> 
        <th width="15%" align="right"><font color="red">*</font>报表名称：</th>
        <td width="35%"> 
            <input name="reportName" type="text" style="width:98%" require="true" dataType="LimitB" max="100" min="1" msg="报表名称必输，且不超过50个汉字"  value="<s:property value='model.reportName'/>">
        </td>
        <th width="15%" align="right">报表编号：</th>
        <td width="35%"> 
            <input name="reportNo" type="text" style="width:98%" require="false" dataType="LimitB" max="40" min="0" msg="报表编号必输，且不超过20个汉字" value="<s:property value='model.reportNo'/>">
        </td>
    </tr>
    <tr> 
        <th align="right">所属分组：</th>
        <td> 
            <input name="reportGroup" type="text" style="width:98%"  dataType="LimitB" max="20" min="0" msg="所属分组不﨣过10个汉字" value="<s:property value='model.reportGroup'/>">
        </td>
        <th align="right">横向显示比例：</th>
        <td> 
            <input name="dispPn" type="text" style="width:20%" value="100" require="false" dataType="Double"  msg="横向显示比例必须是数字" value="<s:property value='model.dispPn'/>">%（请填入数字）
        </td>
    </tr>
</table>

    <br><hr style="width:90%"><br>
    
<!--显示列-->
<div style="width:90%;margin-left:15px">
    <div style="float:left;width:10%;">
        <table>
            <tr height="40px">
                <td align="right">
                    <font color="red" size="3">SELECT</font>
                </td>
            </tr>
        </table>
    </div>
    <div style="float:left;width:90%;"> 
        <table width="95%" class="tableStyle1" align="right" cellpadding="3" cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#C1C8D2" style="border-collapse: collapse">
            <tr height="23px">
                <th align="center" width="30%">字段名称</th>
                <th align="center" width="30%">列中文名称</th>
                <th align="center" width="10%">数据类型</th>
                <th align="center" width="10%">顺序号</th>
                <th align="center" width="10%">显示</th>
                <th align="center" width="10%">合计</th>
                <th align="center" width="10%">操作</th>
            </tr>
            <s:iterator id="custiterator" value="colsList">
            <tr>
                <td align="center">
                    <a href="javascript:popColDetail('<s:property value='colId'/>')"><s:property value="fieldName"/></a>
                </td>
                <td align="center">
                    <a href="javascript:popColDetail('<s:property value='colId'/>')"><s:property value="colNameCn"/></a>
                </td>
                <td align="center">
                    <s:if test="colType eq 1">INT</s:if>
                    <s:if test="colType eq 2">DOUBLE</s:if>
                    <s:if test="colType eq 3">STRING</s:if>
                    <s:if test="colType eq 4">TIMESTAMP</s:if>
                    <s:if test="colType eq 5">LONG</s:if>
					<s:if test="colType eq 9">系统param</s:if>
                </td>
                <td align="center">
                    <s:property value="colOrder"/>
                </td>
                <td align="center">
                    <s:if test="isShow eq 1">是</s:if>
                    <s:else>否</s:else>
                </td>
                <td align="center">
                    <s:if test="isSum eq 1">是</s:if>
                    <s:else>否</s:else>
                </td>
                <td align="center">
                    <input type="button" class="btngray" onclick="gotoDelCol('<s:property value='colId'/>')" value="删除">
                </td>
            </tr>
            </s:iterator>
            <tr>
                <td align="center">
                    <input type="text" name="col.FieldName" style="width:80%">
                </td>
                <td align="center">
                    <input type="text" name="col.colNameCn" style="width:80%">
                </td>
                <td align="center">
                    <select name="col.colType" onchange="selColType(this.value);">
                        <option value="1">INT</option>
                        <option value="2">DOUBLE</option>
                        <option value="3" selected>STRING</option>
                        <option value="4">TIMESTAMP</option>
                        <option value="5">LONG</option>
						<option value="9">系统param</option>
                    </select>
                </td>
                <td align="center">
                    <input type="text" name="col.colOrder" style="width:20px">
                </td>
                <td align="center">
                    <input type="checkbox" name="col.isShow" value="1" checked>
                </td>
                <td align="center">
                    <input type="checkbox" name="col.isSum" value="1">
                </td>
                <td align="center">
                    <input type="button" class="btngray" onclick="gotoColAdd()" value="增加">
                </td>
            </tr>
			<tr id="colParamTr" style="display:none;">
				<td align="center" colspan="7">
					参数表对应编码：<input type="text" name="col.paramGp" dataType="limit" max="20" msg="参数表对应编码不超过20个字符" style="width:30%" value="">
				</td>
			</tr>
        </table>
    </div>
</div>

    <br>

<!--from 和 where 子句-->
<div style="width:90%;margin-left:45px">
    <div style="float:left;width:10%;">
        <table>
            <tr height="40px">
                <td align="right">
                    <font color="red" size="3">FROM</FONT>
                </td>
                
            </tr>
            <tr height="40px">
                <td align="right">
                    <font color="red" size="3">WHERE</FONT>
                </td>
            </tr>
        </table>
    </div>
    <div style="float:left:width:90%">
        <table>
            <tr height="40px">
                <td>
                    <input name="reportSqlFrom" type="text" size="100" value="<s:property value='model.reportSqlFrom'/>" dataType="Limit" max="500" min="0" msg="from子句不超过1000个字符">
                </td>
            </tr>
            <tr height="40px">
                <td>
                    <input name="reportSqlWhere" type="text" size="100" value="<s:property value='model.reportSqlWhere'/>" dataType="Limit" max="1500" min="0" msg="where子句不超过1000个字符">
                </td>
            </tr>
        </table>
    </div>
</div>

<!--参数列-->
<div style="float:left;width:90%;margin-left:12px" > 
    <table width="85%" class="tableStyle1" align="right" cellpadding="3" cellspacing="0" bgcolor="#f2f8ff" border="1"  bordercolor="#C1C8D2" style="border-collapse: collapse">
        <tr height="23px">
            <th align="center" width="20%">条件子句</th>
            <th align="center" width="20%">参数标题</th>
            <th align="center" width="10%">数据校验</th>
            <th align="center" width="10%">控件类型</th>
            <th align="center" width="10%">顺序号</th>
            <th align="center" width="10%">显示</th>
            <th align="center" width="10%">必须</th>
            <th align="center" width="10%">操作</th>
        </tr>
        <s:iterator id="custiterator" value="paramsList">
        <tr>
            <td align="center">
                <a href="javascript:popParamDetail('<s:property value='paramId'/>')">
                    <s:property value="subSentence"/>
                </a>
            </td>
            <td align="center">
                <a href="javascript:popParamDetail('<s:property value='paramId'/>')">
                    <s:property value="paramName"/>
                </a>
            </td>
            <td align="center">
                <s:if test="dataType eq 1">INT</s:if>
                <s:if test="dataType eq 2">DOUBLE</s:if> 
                <s:if test="dataType eq 3">STRING</s:if>
                <s:if test="dataType eq 4">TIMESTAMP</s:if>
                <s:if test="dataType eq 5">LONG</s:if>
                <s:if test="dataType eq 6">LIKE</s:if> 
            </td>
            <td align="center">
                <s:if test="controlType eq 1">文本</s:if>
                <s:if test="controlType eq 2">读取参数表</s:if>
                <s:if test="controlType eq 3">读取其它表</s:if>
            </td>
            <td align="center">
                <s:property value="paramOrder"/>
            </td>
            <td align="center">
                <s:if test="isShow eq 1">是</s:if>
                <s:else>否</s:else>
            </td>
            <td align="center">
                <s:if test="isMust eq 1">是</s:if>
                <s:else>否</s:else>
            </td>
            <td align="center">
                <input type="button" class="btngray" onclick="gotoDelParam('<s:property value='paramId'/>')" value="删除">
            </td>
        </tr>
        </s:iterator>
        <tr>
            <td align="center">
                <input type="text" name="param.subSentence" dataType="limitB" max="50" msg="条件子句不超过50个字符" style="width:80%">
            </td>
            <td align="center">
                <input type="text" name="param.paramName" dataType="limitB" max="50" msg="参数标题不超过50个字符" style="width:80%">
            </td>
            <td align="center">
                <select name="param.dataType">
                    <option value="1">INT</option>
                    <option value="2">DOUBLE</option>
                    <option value="3" selected>STRING</option>
                    <option value="4">TIMESTAMP</option>
                    <option value="5">LONG</option>
                    <option value="6">LIKE</option>
                </select>
            </td>
            <td align="center">
                <select name="param.controlType" onchange="selType(this.value);">
                    <option value="1">文本</option>
                    <option value="2">读取参数表</option>
                    <option value="3">读取其它表</option>
                </select>
            </td>
            <td align="center">
                <input type="text" name="param.paramOrder" style="width:20px">
            </td>
            <td align="center">
                <input name="param.isShow" type="checkbox" value="1" checked>
            </td>
            <td align="center">
                <input name="param.isMust" type="checkbox" value="1">
            </td>
            <td align="center">
                <input type="button" class="btngray" onclick="gotoParamAdd()" value="增加">
            </td>
        </tr>
        <tr id="addTr" style="display:none;">
           
            <td id="addTdCode" align="center" style="display:none" colspan="8">
                参数表对应编码：<input type="text" name="param.paramCode" dataType="limit" max="20" msg="参数表对应编码不超过20个字符" style="width:30%" value="">
            </td>
            <td id="addTdSql" align="center" style="display:none" colspan="8">
                参数相关执行SQL：<input type="text" name="param.paramSql" dataType="limit" max="500" msg="参数相关执行sql不超过500个字符" style="width:50%" value="">
            </td>
        </tr>
    </table>
</div>

<br><br>
<!-- 按钮条 -->
<table width="98%" align="center" class="bottombuttonbar" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
	    <td width="90%" valign="middle">
            <INPUT class="btngreen" TYPE="submit" value=" 保存 ">
            <INPUT class="btngreen" TYPE="button" value=" 预览sql语句 " onclick="viewSql();">
			<INPUT class="btngreen" TYPE="button" value=" 报表查询 " onclick="gotoSearch(<s:property value='model.reportId'/>);">
		</td>
	    <td width="10%" align="center">
            <a href="#" onclick="gotoList();">>&nbsp;返回&nbsp;</a>
        </td>
	</tr>
</table>

<br><br>
</s:form>



<SCRIPT LANGUAGE="JavaScript">
var reportId = document.forms[0].reportId.value;
<!--
function gotoList(){
    location.href="/report/listReportMain.action";
}

//增加要显示的列
function gotoColAdd(){
    form1.action = "/report/saveCol.action";
    form1.submit();
}

//增加要显示的参数
function gotoParamAdd(){
    form1.action = "/report/saveParam.action";
    form1.submit();
}

//得到显示列的详情
function popColDetail(colId){
    dialog("修改报表列","iframe:/report/gotoColUpdate.action?colId="+colId,"600px","300px","iframe");
}

//得到参数表的详情
function popParamDetail(paramId){
    dialog("修改报表参数","iframe:/report/gotoParamUpdate.action?paramId="+paramId,"600px","340px","iframe");
}

//删除显示列信息
function gotoDelCol(colId){
    if(confirm("你确定要删除？")){
        form1.action = "/report/deleteCol.action?colId="+colId;
        form1.submit();
    }
}

//显示参数信息
function gotoDelParam(paramId){
    if(confirm("你确定要删除？")){
        form1.action = "/report/deleteParam.action?paramId="+paramId;
        form1.submit();
    }
}

//预览sql语句
function viewSql(){
    dialog("预览sql语句","iframe:/report/gotoViewSql.action?reportId="+reportId,"600px","300px","iframe");
}

//报表查询
function gotoSearch(reportId){
    location.href="/report/gotoReportQuery.action?reportId="+reportId;
}

//关闭
function closePop(){
	closeThis();
	location.href="/report/gotoReportMainUpdate.action?reportId="+reportId ;
}

//光标移动
function changeKey()
{
	var tr=event.srcElement.getAttribute("type");
	if("textarea"!=tr && "button" != tr)
	{
			if(13 == event.keyCode)
			{
				event.keyCode=9;
			}
  }
}


//选择显示列类型
function selColType(typeId){
    if(typeId==9){
        document.getElementById("colParamTr").style.display="block";
    }else{
        document.getElementById("colParamTr").style.display="none";
	}
}


//选择查询条件控件类型
function selType(typeId){
    if(typeId==2){
        document.getElementById("addTr").style.display="block";
        document.getElementById("addTdCode").style.display="block";
        document.getElementById("addTdSql").style.display="none";
    }
    else if(typeId==3){
        document.getElementById("addTr").style.display="block";
        document.getElementById("addTdCode").style.display="none";
        document.getElementById("addTdSql").style.display="block";
    }else{
        document.getElementById("addTr").style.display="none";
	}
}
</SCRIPT>
</BODY>
</HTML>