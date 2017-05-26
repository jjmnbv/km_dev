<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<style type="text/css">
.listTitle {
	height: 40px;
	line-height:40px;
	background: #D6F2D9;
	border-bottom: 1px solid #079346;
	vertical-align: middle;
	font-size: 14;
	color: #028043;
	margin:0px;
}
.listTitle span{
 padding-left:20px;
 height:40px;
 vertical-align: middle;
 margin:0px;
}
</style>
<script type="text/javascript" src="/etc/js/myModel.js"></script>
<script type="text/javascript">
$(function(){
	mytable("#fff","#EEF9F3","#cfc","#cfc");
});
</script>
<!-- 导航栏 -->
<div width="100%" class="listTitle">
	<span><img src="/etc/images/icon_02.png"   style="width:20px;height:20px;vertical-align: middle;"/>&nbsp;&nbsp;
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