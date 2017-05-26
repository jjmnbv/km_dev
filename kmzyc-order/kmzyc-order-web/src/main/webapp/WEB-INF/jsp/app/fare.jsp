<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>运费配置</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/etc/css/block.css">
<link rel="stylesheet" type="text/css" href="/etc/css/jq.css">
<link rel="stylesheet" type="text/css" href="/etc/css/jquery-ui.css">
<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="/etc/js/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript" src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript" src="/etc/js/jquery.metadata.js"></script>
<script type="text/javascript" src="/etc/js/messages_cn.js"></script>
<script type="text/javascript" src="/etc/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="/etc/js/chili-1.7.pack.js"></script>
<script type="text/javascript" src="/etc/js/jquery.blockUI.js"></script>
<script type="text/javascript" src="/etc/js/urchin.js"></script>
<script type="text/javascript">
 $(function(){
	 
		// 新增
        $('#newFare').click(function(evt) {
            $.blockUI({ message: ($('#question').empty().load("/app/fareTypeshowSaveAction.action?currid="+Math.random()))
            	, css: { top:'20%' ,width: '380px' }
        		, overlayCSS: { cursor: 'default' }
            });
        });
        
        // 更新
        $('.upd').click(function(evt) {
        	var type_id = $(this).attr("name");
            $.blockUI({ message: ($('#question').empty().load("/app/fareTypeshowEditAction.action?type_id="+type_id+"&currid="+Math.random()))
            	, css: { top:'20%' ,width: '380px' }
            	, overlayCSS: { cursor: 'default' }
            });
        });
        
        // 删除
        $('.del').click(function() {
        	var type_id = $(this).attr("name");
        	if(confirm('确定要删除此配置吗？')){
	            $.post(
	            	"/app/fareTypedeleteAction.action",
	           		{type_id:type_id},
  	           		function(result){
  			            alert(result);
  			            if(result.indexOf("失败")>0){
  			            	history.go(0);
  			            }else{
  			            	location.reload();
  			            }
  	     			}
	           	);
			}; 
        });
        
        // 刷新
        $('.refurbish').click(function() {
   			 location.reload();
        });
        
 });
</script>
</head>
<body>
<s:set name="parent_name" value="'业务操作'" scope="request"/>
<s:set name="name" value="'订单管理'" scope="request"/>
<s:set name="son_name" value="'运费配置'" scope="request"/>
<s:include value="/WEB-INF/jsp/public/title.jsp"/>
<br/>
&nbsp;&nbsp;&nbsp;&nbsp;<button id="newFare" class="addBtn"></button>&nbsp;&nbsp;&nbsp;&nbsp;<button class="btn-custom refurbish">刷新</button>
<br/>
<br/>
<table class="list_table" width="98%" align="center" cellpadding="3" cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc" style="border-collapse: collapse;font-size:12px">
	<tr>
		<th>序号</th>
		<th>适用站点</th>
   		<th>名称</th>
   		<th>首重(公斤)</th>
   		<th>续重(公斤)</th>
   		<th>首重费(元)</th>
   		<th>续重费(元)</th>
   		<th>免运费起价(元)</th>
   		<th>状态</th>
   		<th>操作</th>
	</tr>
	<s:iterator id="fare" value="fareList">
	<tr>
		 <td><s:property value="#fare.type_id"/></td>
		 <td><s:property value="#fare.site"/></td>
   		 <td><s:property value="#fare.name"/></td>
   		 <td><s:property value="#fare.firstHeavy"/></td>
   		 <td><s:property value="#fare.continuedHeavy"/></td>
   		 <td><s:property value="#fare.firstHeavyFreight"/></td>
   		 <td><s:property value="#fare.continuedHeavyFreight"/></td>
   		 <td><s:property value="#fare.freePrice"/></td>
   		 <td><s:property value="#fare.disabled==0?'启用':'关闭'"/></td>
		 <td>
			 <a href="javascript:;" class="upd" name="<s:property value='#fare.type_id'/>">修改</a>&nbsp;|&nbsp;
			 <a href="javascript:;" class="del" name="<s:property value='#fare.type_id'/>">删除</a>
		</td>
	</tr>
	</s:iterator>
</table>
<br/>

<div id="question" style="display:none; cursor: default"></div>

</body>
</html>

