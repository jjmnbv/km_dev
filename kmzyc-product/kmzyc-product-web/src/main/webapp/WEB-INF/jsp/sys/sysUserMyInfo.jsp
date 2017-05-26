<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<%@ page import="com.opensymphony.xwork2.ognl.OgnlValueStack"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的信息</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script src="/etc/js/jquery-latest.pack.js"></script>
<script src="/etc/js/dialog.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
</head>
<body onkeydown="changeKey();">
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>
<%
  OgnlValueStack stack = (OgnlValueStack)request.getAttribute("struts.valueStack");
  String userSex = (String)stack.findValue("model.userSex");
%>


<s:form action="doSysUserMyInfoUpdate.action" method="POST"  namespace='/sys' onsubmit="return  Validator.Validate(this,3)">

<!-- hidden properties -->
<INPUT TYPE="hidden" name="userId" value="<s:property value='model.userId'/>">


<!-- 标题条 -->
<div class="pagetitle">我的信息:</div>

<!-- 按钮条 -->
<table width="98%" align="center" class="topbuttonbar" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
	    <td width="90%" valign="middle">
            <INPUT class="btngreen" TYPE="submit" style="height:30px" value=" 保存 ">
		</td>
	    <td width="10%" align="center"><!--a href="#" onclick="gotoList();">>&nbsp;返回&nbsp;</a--></td>
	</tr>
</table>

<!-- 数据编辑区域 -->
<table width="95%" class="tableInput1" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
    <!-- error message -->
    <s:if test='rtnMessage.equals("updateOK")'>
		<SCRIPT LANGUAGE="JavaScript">
		<!--
            alert("信息修改成功!");
		//-->
		</SCRIPT>
	  </s:if>
	  <s:elseif test="rtnMessage != null">
		<tr> 
			<td colspan="2" align="center"> 
				<font color="red"><s:property value='rtnMessage'/></font>
			</td>
		</tr>
	  </s:elseif>

    <tr> 
        <th width="30%" align="right"><font color="red">*</font>姓名：</th>
        <td> 
            <s:property value='model.userReal'/>
        </td>
    </tr>
    <tr> 
        <th width="30%" align="right"><font color="red">*</font>用户名：</th>
        <td> 
            <s:property value='model.userName'/>
        </td>
    </tr>
    <tr> 
        <th width="30%" align="right"><font color="red">*</font>所属部门：</th>
        <td> 
            <s:property value='deptName'/>
        </td>
    </tr>
    <tr> 
        <th width="30%" align="right"><font color="red">*</font>岗位角色：</th>
        <td> 
            <s:property value='model.roleName'/>
        </td>
    </tr>
    <tr> 
        <th width="30%" align="right"><font color="red">*</font>身份证号码：</th>
        <td> 
            <s:property value='model.userCardno'/>
        </td>
    </tr>
    <tr> 
        <th width="30%" align="right"><font color="red">*</font>性别：</th>
        <td> 
            <input name="userSex" type="radio" value="1" <%="1".equals(userSex)?"checked":""%> />
            <img src="/etc/images/boy.gif">男 &nbsp;&nbsp;
            <input name="userSex" type="radio" value="0"  <%="0".equals(userSex)?"checked":""%>/>
            <img src="/etc/images/girl.gif">女
        </td>
    </tr>
    <tr> 
        <th width="30%" align="right"><font color="red">*</font>联系电话：</th>
        <td> 
            <input name="userPhone" type="text" require="true" dataType="LimitB" max="20" min="1" msg="联系电话必输，且不超过20个字符" value="<s:property value='model.userPhone'/>"/>
        </td>
    </tr>

    <tr> 
        <th width="30%" align="right"><font color="red">*</font>生日：</th>
        <td> 
            <input name="userBirthday" type="text" class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" value='<s:date   name="model.userBirthday"  format="yyyy-MM-dd"/>'/>
        </td>
    </tr>
    <tr> 
        <th width="30%" align="right">备注：</th>
        <td> 
            <textarea name="userRemark" cols="50" rows="4" wrap="PHYSICAL" require="false" dataType="LimitB" max="200" msg="备注不要超过100个汉字"><s:property value='model.userRemark'/></textarea> 
        </td>
    </tr>
</table>


<!-- 底部 按钮条 -->
<table width="98%" align="center" class="bottombuttonbar" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
		<td width="90%" valign="middle">
			<INPUT class="btngreen" TYPE="submit" style="height:30px" value=" 保存 ">
		</td>
		<td width="10%" align="center"><!--a href="#" onclick="gotoList();">>&nbsp;返回&nbsp;</a--></td>
	</tr>
</table>

</s:form>
<SCRIPT LANGUAGE="JavaScript">
<!--

//返回我的桌面界面
function gotoList(){
    location.href="/sys/gotoSysMain.action";
}


//弹出层 选择角色
function popSelectRole() {
    dialog("选择岗位角色","iframe:/sys/gotoPopSelectRole.action" ,"500px","340px","iframe");
}
function closeOpenRoleDiv(roleId,roleName){
    closeThis();
    document.forms[0].roleListStr.value = roleId;
	document.forms[0].roleName.value = roleName;
}



//弹出选择客户窗口
function popSearchDeptPage(){

	var URL="/sys/gotoSearchDeptPage.action";
	window.open(URL,"deptLeaveWord","width=600,height=400,top=100,left=200,toolbar=0,menubar=0,scrollbars=1,resizable=0,location=0,status=1");
}

function refreshList(deptId,deptName){
	document.forms[0].deptId.value = deptId;
	document.forms[0].deptName.value = deptName;
}



function checkSelect(){
	//校验身份证合法性
	if(!isIDno(document.forms[0].userCardno.value))
	{
        return false;
	}	

    return true;
}



/*
==================================================================
功能：验证身份证号码是否有效
提示信息：输入身份证号不正确！
使用：isIDno(obj)
返回：bool
==================================================================
*/
function isIDno(idcard) {
    var Errors=new Array("验证通过!",
                         "身份证号码位数不对!",
                         "身份证号码出生日期超出范围或含有非法字符!",
                         "身份证号码校验错误!",
                         "身份证地区非法!"
                        );
  
    var area={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"}
    
    var idcard,Y,JYM;
    var S,M;
    var idcard_array = new Array();
    idcard_array = idcard.split("");
    //地区检验
    if(area[parseInt(idcard.substr(0,2))]==null) {
        alert(Errors[4]);
        return false;
    }
    //身份号码位数及格式检验
    switch(idcard.length){
    case 15:
        //15位身份号码检测
        if ( (parseInt(idcard.substr(6,2))+1900) % 4 == 0 || ((parseInt(idcard.substr(6,2))+1900) % 100 == 0 && (parseInt(idcard.substr(6,2))+1900) % 4 == 0 )){
            ereg=/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/;//测试出生日期的合法性
        } else {
            ereg=/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/;//测试出生日期的合法性
        }
        if(!ereg.test(idcard)) {
            alert(Errors[2]);
            return false;
        } else {
            return true;
        }
    break;
    case 18:
        //18位身份号码检测
        //出生日期的合法性检查 
        //闰年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))
        //平年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))
        if ( parseInt(idcard.substr(6,4)) % 4 == 0 || (parseInt(idcard.substr(6,4)) % 100 == 0 && parseInt(idcard.substr(6,4))%4 == 0 )){
            ereg=/^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/;//闰年出生日期的合法性正则表达式
        } else {
            ereg=/^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;//平年出生日期的合法性正则表达式
        }
        if(ereg.test(idcard)) { //测试出生日期的合法性
            //计算校验位
            S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10])) * 7
            + (parseInt(idcard_array[1]) + parseInt(idcard_array[11])) * 9
            + (parseInt(idcard_array[2]) + parseInt(idcard_array[12])) * 10
            + (parseInt(idcard_array[3]) + parseInt(idcard_array[13])) * 5
            + (parseInt(idcard_array[4]) + parseInt(idcard_array[14])) * 8
            + (parseInt(idcard_array[5]) + parseInt(idcard_array[15])) * 4
            + (parseInt(idcard_array[6]) + parseInt(idcard_array[16])) * 2
            + parseInt(idcard_array[7]) * 1 
            + parseInt(idcard_array[8]) * 6
            + parseInt(idcard_array[9]) * 3 ;
            Y = S % 11;
            M = "F";
            JYM = "10X98765432";
            M = JYM.substr(Y,1);//判断校验位
            if(M !== idcard_array[17]) {
                alert(Errors[3]);
                return false;
            }
            return true;
        } else {
            alert(Errors[2]);
            return false;
        } 
        break;
    default:
        alert(Errors[1]);
        return false;
        break;
    }
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
//-->
</SCRIPT>
</BODY>
</HTML>