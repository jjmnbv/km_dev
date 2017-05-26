<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<style type="text/css">
.listTitle{height:40px;line-height:40px;background:#c7e3f1;vertical-align:middle;font-size:13;color:#222;margin-bottom:20px}
.listTitle span{padding-left:20px;height:40px;line-height:40px;vertical-align:middle;margin-top:5px}
.listTitle span img{vertical-align:middle}
.searcharea{background-color:#f5f5f5;border:1px solid #e3e3e3;-webkit-box-shadow:inset 0 1px 1px rgba(0,0,0,.05);-moz-box-shadow:inset 0 1px 1px rgba(0,0,0,.05);box-shadow:inset 0 1px 1px rgba(0,0,0,.05);overflow:hidden;padding:6px}
.uneditable-input,input[type=text],input[type=url],input[type=search],input[type=tel],input[type=color],input[type=password],input[type=datetime],input[type=datetime-local],input[type=date],input[type=month],input[type=time],input[type=week],input[type=number],input[type=email],select,textarea{background-color:#fff;border:1px solid #ccc;-webkit-box-shadow:inset 0 1px 1px rgba(0,0,0,.075);-moz-box-shadow:inset 0 1px 1px rgba(0,0,0,.075);box-shadow:inset 0 1px 1px rgba(0,0,0,.075);-webkit-transition:border linear .2s,box-shadow linear .2s;-moz-transition:border linear .2s,box-shadow linear .2s;-o-transition:border linear .2s,box-shadow linear .2s;transition:border linear .2s,box-shadow linear .2s;display:inline-block;height:30px;padding:4px 6px;margin-bottom:3px;font-size:14px;line-height:20px;color:#555;vertical-align:middle}
.searcharea td{color:#999;line-height:31px;    font-size: 13px;}
#frm{margin:20px}

</style>
<!-- 导航栏 -->
<div width="100%" class="listTitle">
	<span><img src="/etc/images/icon_02.png"   style="vertical-align: middle;"/>&nbsp;
		<img src="/etc/images/icon_08.png" />&nbsp;<s:property value="#request.parent_name"/>&nbsp;
		<img src="/etc/images/icon_08.png" />&nbsp;<s:property value="#request.name"/>
		<s:if test="#request.son_name!=null">
		 &nbsp;<img src="/etc/images/icon_08.png" />&nbsp;<s:property value="#request.son_name"/>
		</s:if>
		</span>
</div>