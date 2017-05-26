<%@page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>风控黑名单</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="/etc/css/block.css">
		<link rel="stylesheet" type="text/css" href="/etc/css/jq.css">
		<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.blockUI.js"></script>
		<script type="text/javascript">
		$(function(){
			$('#btnQuery').bind('click', function(){
				var type=$('#type').val();
				var content=$('#txtcontent').val();
			 	$('#blackListForm').submit();
			});
		});	
		function gotoAdd(){
			$.blockUI({ message: ($('#block').empty().load("/app/gotoAddBlackList.action"))
		  	    , css: { position:'absolute',top:'20%' ,width: '300px',cursor:'auto' }
			,overlayCSS:{cursor:'auto'}
			});
		}
		function removeBlackList(id,name){
			if(confirm('确认风控黑名单中移除'+name+'？')){
				$.ajax({
		            async: false,
		            url: '/app/removeBlackList.action?id='+id,
		            cache:false,
		            type:'post',
		            success: function (data) {
						if(null!=data&&'null'!=data&&data.length>0){
							alert('风控黑名单中移除'+name+'成功！');
							window.location.reload();
				        }
		            },
		            error:function(){
		            	window.location.reload();
			        }
		        });
			}
		}
		</script>
	</head>
	<body>
		<s:set name="parent_name" value="'订单风控'" scope="request"/>
		<s:set name="name" value="'风控黑名单'" scope="request"/>
		<s:include value="/WEB-INF/jsp/public/title.jsp"/>
		<form id="blackListForm" action="/app/blackList.action" method="post">
		<table class="table_search" width="98%" align="center" cellpadding="0" cellspacing="0" >
			<tr>
		        <td>
				   名单类型：
					<select id="type" name="params['type']">
						<option value="" <s:if test='params["type"]==""'>selected="selected"</s:if>>全部</option>
				    	<option value="1" <s:if test='params["type"]==1'>selected="selected"</s:if>>下单账号</option>
				   		<option value="2" <s:if test='params["type"]==2'>selected="selected"</s:if>>收货手机号码</option>
				 		<option value="3" <s:if test='params["type"]==3'>selected="selected"</s:if>>入驻商家</option>				   
				   	</select>
				</td>
				<td>
				    条目内容：<input name="params['content']" id="txtcontent" type="text" value='<s:property value="params['content']"/>' />
				</td>
				<td>
				    <input type="button" id="btnQuery" class="queryBtn" value="" />
                    <input type="button" class="btn-custom" id="" value="新增黑名单" onClick="gotoAdd()" />
				</td>
			</tr>
		</table>
		<table class="list_table" width="98%" align="center" cellpadding="3" cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc" style="border-collapse: collapse;font-size:12px">
			<tr>
		   		<th width="10%">类型</th>
		   		<th width="50%">条目内容</th>
		   		<th width="10%">添加者</th>
		   		<th width="20%">添加时间</th>
		   		<th width="10%">操作</th>
			</tr>
			<s:iterator id="black" value="page.dataList">
			<tr>
				<td><s:property value="#black.typeStr"/></td>
				<td><s:property value="#black.content"/></td>
				<td><s:property value="#black.operator"/></td>
				<td><s:date name="#black.operatDate" format="yyyy-MM-dd HH:mm:ss" /></td>
				<td><a href="javascript:removeBlackList(<s:property value="#black.bid"/>,'<s:property value="#black.content"/>');" >删除</a></td>
			</tr>	
			</s:iterator>
		</table>
		<br/>
		<table class="page_table" width="98%" align="center">
		   <tr>
		     <td align="right"><s:include  value="/WEB-INF/jsp/public/pager.jsp"/></td>
		   </tr>
		</table>
		</form>
		<div id="block" style="display:none"></div>
	</body>
</html>