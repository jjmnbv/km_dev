<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>订单评价信息回复</title>
    <style type="text/css">
<!--
.STYLE2 {font-size: 24px}
.STYLE3 {font-size: 18px}
-->
    </style>
</head>
<body>
   <table>
      <form action="/app/orderAIReply.action" method="post">
        <tr>
           <td>回复:<input type="hidden" name="orderAssessReply.assessInfoId" value="333"></td>
           <input type="hidden" name="orderAssessReply.replyGuestNum" value="999">
        <tr>
        <tr>
          <td><textarea rows="1" cols="30" name="orderAssessReply.replyContent"></textarea></td>
           <td><input  type="submit" value="回复"></td>
        </tr>
        
      </form>
   </table>
</body>
</html>