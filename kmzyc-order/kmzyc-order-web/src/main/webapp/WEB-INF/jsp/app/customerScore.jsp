<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订单评价信息获得稅分</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"  src="/etc/js/jquery-1.4.2.min.js"></script>
 <script src="/etc/js/dialog.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.metadata.js"></script>
<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>
</head>
<body>

   <table align="center">
       <tr>
          <td><s:property value="#request.guestNum"/></td>
          <td><s:property value="#request.str"/></td>
       </tr>
       <tr align="center">
           <td><input class="backBtn"  onclick="gotoList()" type="button" value=" "></td>
       </tr>
            
       
   </table>
   
  
<SCRIPT LANGUAGE="JavaScript"> 
function gotoList(){
    location.href="/app/assessInfoActionList.action";
}
</SCRIPT>
</body>
</html>