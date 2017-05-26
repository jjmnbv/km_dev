<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择操作员</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<script src="/etc/js/jquery-latest.pack.js"></script>
<style>
.deptarea{
    float: left; 
	width: 120px; 
	border: 1px solid #BEBEBE;
	background-color: #FFFFCC;
    padding: 5px 0px 0px 5px; 
}

.deptarea li  {
    padding: 5px 0px 0px 3px; 
}

.current  {
    padding: 5px 0px 0px 3px; 
	background:url("/etc/images/arrow_right.gif") no-repeat left;
}


.userArea{
    float: left; 
	width: 380px; 
    padding: 5px 0px 0px 30px;
}
</style>
<script src="/etc/js/jquery-latest.pack.js"></script>
<script type="text/javascript">  
var str;   
function check(opt){  
	str = "."+opt.value;   
	if($("#unchecked").children('option').size()>0){   
		$("#unchecked").children('option').attr('flag','false');   
		$(str).attr('flag','true');   
	}  
}   
function unCheck(opt){   
	str = "."+opt.value;   
	if($("#checked").children('option').size()>0){   
		$("#checked").children('option').attr('flag','false');   
		$(str).attr('flag','true');   
	}   
} 

function select(opt){   
	str = "."+opt.value;   
	$("#checked").append("<option value='"+opt.value+"' class='"+opt.value+"'>"+$.trim($(str).text())+"</option>");   
	$("#unchecked").children('option').remove(str);   
}   
function selectAllButton(){   
	$.each($("#unchecked").children('option'), function(i, n){   
		$("#checked").append("<option value='"+n.value+"' class='"+n.value+"'>"+$.trim($(n).text())+"</option>");   
	});
	$("#unchecked").children('option').remove();   
}  
function selectButton(){   
	alert(str);
}  


function unSelect(opt){   
	str = "."+opt.value;   
	$("#unchecked").append("<option value='"+opt.value+"' class='"+opt.value+"'>"+$.trim($(str).text())+"</option>");   
	$("#checked").children('option').remove(str);   
}   
  
function resetMyForm(){   
	$.each($("#checked").children('option'), function(i, n){   
		$("#unchecked").append("<option value='"+n.value+"' class='"+n.value+"'>"+$.trim($(n).text())+"</option>");   
	});    
	$("#checked").children('option').remove();   
}

//确定
function submitMyForm(){   
	var form = document.getElementById("myForm"); 
	var idStr="";
	var nameStr="";
	for(i=0;i<form.userIds.length;i++){     
		form.userIds[i].selected=true;   
        idStr += form.userIds[i].value + ",";
		nameStr += form.userIds[i].text + ",";
	}
	idStr = idStr.substring(0,idStr.lastIndexOf(","));
	nameStr = nameStr.substring(0,nameStr.lastIndexOf(","));
	//alert(idStr + "  --  " + nameStr);
    //调用母页面函数
	parent.closeOpenUserDiv(idStr,nameStr);
} 
</script>  
</head>
<body>
<form id="myForm" name="myForm">  


<div>
    <div class="deptarea">
			<s:iterator id="dataObj" value="dataList" status="idx">
				<li id="dept_<s:property value='#idx.index+1'/>"><a href="#" onclick='changeDept(<s:property value="#idx.index+1"/>,<s:property value="deptId"/>)'><s:property value="deptName"/></a>
			</s:iterator>
	</div>
	<div class="userArea">
		<table width="98%" align="center" border="0" cellpadding="0" cellspacing="0">
		    <tr>
			    <td style="height: 20px " align="center">待选</td>
				<td></td>
				<td style="font-weight: bold" align="center">已选</td>
			</tr>
		    <tr>
			    <td id="userArea" width="40%">
				    <select id="unchecked" multiple="multiple" style="height: 250px; width: 150px;" size="15" onclick="check(this)" ondblclick="select(this);"></select>
				</td>
				<td width="20%" align="center" valign="middle">
				    <INPUT TYPE="button" class="btngray" value="全选&gt;&gt;" onClick="selectAllButton()"><br>
					<br>
					<INPUT TYPE="button" class="btngray" value="&lt;&lt;全删" onClick="resetMyForm()">
				</td>
				<td width="40%">
				    <select id="checked" name="userIds" multiple="multiple" style="height: 250px; width: 150px;" size="15" onclick="unCheck(this)" ondblclick="unSelect(this);"></select>  
				</td>
			</tr>
			<SCRIPT LANGUAGE="JavaScript">
				//设置默认的已选人员
				var idStr = "<s:property value='userIdStr'/>";
				var nameStr = "<s:property value='userNameStr'/>";
				var idArr = idStr.split(",");
				var nameArr = nameStr.split(",");
                if(idStr!=""  &&  idArr.length>0){
					$.each(idArr , function(i, n){
						$("#checked").append("<option value='"+n+"' class='"+n+"'>"+nameArr[i]+"</option>");
					});
				}
			</SCRIPT>
			<tr>
			    <td colspan="3" style="height: 30px" align="center">
				    <input type="button" class="btn-custom btngreen"  value="" onClick="submitMyForm();" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		            <input type="button" class="btn-custom btngray" value="" onClick="resetMyForm();" />
				</td>
			</tr>
		</table>


	</div>
</div>

</form>

<script type="text/javascript">
function changeDept(indexId,deptId){
	//点击部门样式
	document.getElementById("dept_"+indexId).className="current";
    //未点击部门样式
	for(var i=1;i<=<s:property value='dataList.size()'/>;i++){
		if(i!=indexId){
			document.getElementById("dept_"+i).className="";
		}
	}
    //ajax刷新用户列表
	$.post("/sys/ajaxGetUsersByDept.action?deptId="+deptId ,  function(data){$("#userArea").html(data)});
}
</script>	

</body>
</html>

