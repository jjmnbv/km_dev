<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>文件上传</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="/etc/css/block.css">
<link rel="stylesheet" type="text/css" href="/etc/css/jq.css">
<link rel="stylesheet" type="text/css" href="/etc/css/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="/etc/css/autocompletestyles.css">
<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript">
function check(){

	if (($("#doc").val() == null) || ($("#doc").val() == '')
				|| ($("#doc").val() == 'undefined') || ($("#doc").val() == 'null')) {
		    alert("请选择文件");
			return false;
		} else {
			return true;
		}
	}
</script>
</head>
<body>
<s:set name="parent_name" value="'业务操作'" scope="request"/>
<s:set name="name" value="'功能管理'" scope="request"/>
<s:set name="son_name" value="'订单评分导入'" scope="request"/>
<s:include value="/WEB-INF/jsp/public/title.jsp"/>
<form action="/app/orderAssess/uploadOrderAssess.action" method="post" 	enctype="multipart/form-data" name="form1" onsubmit="return check();">
  <table align="center" >
    <tr>
      <td style="font-size:14px">评分的excel文件:</td>
      <td><s:file name="doc" id="doc"/>
        <s:token></s:token></td>
    </tr>
    <tr>
      <td align="left" style="font-size:13px; color:#666;"><br>
        <font color="red">注意事项：</font><br>
        1.excel文件必须严格按照指定的格式<br>
        2.格式不允许有空行，不论前后左右中。<br>
        3.列数和列的位置必须保持和模板一致。 </td>
    </tr>
    <tr>
      <td colspan="3" align="center"><br>
        <input type="submit" name="Submit" value="提交" class="btn-custom"></td>
    </tr>
  </table>
</form>
</body>
</html>
