<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<html>
<head>
</head>
<body>
<form action="/third/importCode.action" method="post" name="frm" id="frm">
	cardId:<input type="text" id="cardId" name="cardId" value=""/>
    couponId:<input type="text" name="couponId" id="couponId"/>
    num:<input type="text" name="num" id="num"/>
    tableName:<input type="text" name="tableName" id="tableName"/>
	<input type="button" onclick="submitForm()" value="开始导入"/>
</form>
</body>
<script type="text/javascript">
	function submitForm(){
		var cardId=document.getElementById("cardId").value;
		var couponId=document.getElementById("couponId").value;
		var num=document.getElementById("num").value;
		var tableName=document.getElementById("tableName").value;

		if(cardId=="" || couponId==""){
			alert("卡券和所定义的规则id不能为空!");
			return false;
		}
		document.forms['frm'].submit();
	}
</script>
</html>
