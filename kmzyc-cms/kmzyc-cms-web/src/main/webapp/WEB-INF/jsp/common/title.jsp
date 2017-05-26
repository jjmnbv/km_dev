<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<style type="text/css">
.listTitle {
	height: 40px;
	line-height:40px;
	background: #c7e3f1;
	
	vertical-align: middle;
	font-size:13;
	color: #222222;
	margin-bottom: 20px;
}
.listTitle span{
 padding-left:20px;
 height:40px;
 line-height:40px;
 vertical-align: middle;
 margin-top:5px;
}
.listTitle span img{
   vertical-align: middle;
}
.content_table{
	background-color: #f5f5f5;
    border: 1px solid #e3e3e3;
    -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,0.05);
    -moz-box-shadow: inset 0 1px 1px rgba(0,0,0,0.05);
    box-shadow: inset 0 1px 1px rgba(0,0,0,0.05);
    overflow: hidden;
    
    padding: 6px;

	
	}</style>
<!-- 导航栏 -->
<div width="100%" class="listTitle"  height="40">
    <span>
	<img src="/etc/images/icon_02.png"  />&nbsp;&nbsp;
		<img src="/etc/images/icon_08.png" />&nbsp;&nbsp;<s:property value="#request.parent_name"/>&nbsp;&nbsp;
		<img src="/etc/images/icon_08.png" />&nbsp;&nbsp;<s:property value="#request.name"/>
		<s:if test="#request.son_name!=null">
		 &nbsp;&nbsp;<img src="/etc/images/icon_08.png" />&nbsp;&nbsp;<s:property value="#request.son_name"/>
		</s:if>
		<s:if test="#request.grandson_name!=null">
		 &nbsp;&nbsp;<img src="/etc/images/icon_08.png" />&nbsp;&nbsp;<s:property value="#request.grandson_name"/>
		</s:if>
	  </span>
</div>