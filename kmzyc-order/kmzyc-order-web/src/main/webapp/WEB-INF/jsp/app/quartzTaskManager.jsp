<%@page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>定时任务列表</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="/etc/css/block.css">
		<link rel="stylesheet" type="text/css" href="/etc/css/jq.css">
		<link rel="stylesheet" type="text/css" href="/etc/css/jquery-ui.css">
		<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
	</head>
	<body>
		<s:set name="parent_name" value="'业务操作'" scope="request" />
		<s:set name="name" value="'功能管理'" scope="request" />
		<s:set name="son_name" value="'定时任务列表'" scope="request" />
		<s:include value="/WEB-INF/jsp/public/title.jsp" />
		<s:form action="quartzManager" method="get" name="quartzManagerForm" id="quartzManagerForm">
			<table width="98%" align="center" border="0" class="table_search">
				<tr>
					<td align="right">任务名称</td>
					<td>
						<input type="text" name="jobName" id="jobName" value='${paramsMap.jobName}' />
					</td>
					<td align="right">任务组</td>
					<td>
						<input type="text" name="jobGroup" id="jobGroup" value="${paramsMap.jobGroup}" />
					</td>
					<td align="left">是否启用</td>
					<td>
						<select id="isVolatile" name="isVolatile" data="${paramsMap.isVolatile}">
							<option value="" >---请选择---</option>
							<option value="0">----启用----</option>
							<option value="1">----停用----</option>
						</select>
					</td>
                    					<td align="center">
						<input type="button" class="btn-custom btnReset" value=" 重置 "/>
						<input type="submit" class="queryBtn" value=""/>
						<input type="button" class="addBtn btnAddTask" value=""/>
					</td>
				</tr>
			</table>
			<table class="list_table" width="98%" align="center" cellpadding="3" cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc" style="border-collapse: collapse; font-size: 12px">
				<thead>
					<tr>
						<th width="6%">任务ID</th>
						<th width="8%">系统编码</th>
						<th width="8%">任务名称</th>
						<th width="8%">任务组</th>
						<th width="8%">任务类</th>
						<th width="8%">触发器名称</th>
						<th width="8%">触发器组</th>
						<th width="10%">触发表达式</th>
						<th width="4%">状态</th>
						<th width="12%">描述</th>
						<th width="10%">创建时间</th>
						<th width="10%">操作</th>
					</tr>
				</thead>
				<tbody>
				<s:if test="null!=page.dataList">
					<s:iterator id="task" value="page.dataList"> 
					<tr>
						<td width="6%"  style="padding:0;" ><s:property value="#task.taskId"/></td>
						<td width="8%"  style="padding:0;"valueKey="sysCode" class="allowEditTd noallownull"><s:property value="#task.sysCode"/></td>
						<td width="8%"  style="padding:0;"valueKey="jobName" class="allowEditTd noallownull"><s:property value="#task.jobName"/></td>
						<td width="8%"  style="padding:0;"valueKey="jobGroup" class="allowEditTd noallownull"><s:property value="#task.jobGroup"/></td>
						<td width="8%"  style="padding:0;"valueKey="jobClass" class="allowEditTd noallownull"><s:property value="#task.jobClass"/></td>
						<td width="8%"  style="padding:0;"valueKey="triggerName" class="allowEditTd noallownull"><s:property value="#task.triggerName"/></td>
						<td width="8%"  style="padding:0;"valueKey="triggerGroup" class="allowEditTd noallownull"><s:property value="#task.triggerGroup"/></td>
						<td width="10%" style="padding:0;" valueKey="cronExpression" class="allowEditTd noallownull"><s:property value="#task.cronExpression"/></td>
						<td width="4%"  style="padding:0;"valueKey="isVolatile" class="allowEditTd noallownull"><s:property value="#task.isVolatile"/></td>
						<td width="12%" style="padding:0;" valueKey="description" class="allowEditTd"><s:property value="#task.jobDescription"/></td>
						<td width="10%" style="padding:0;"><s:date name="#task.createDate" format="yyyy-MM-dd hh:mm:ss" /></td>
						<td width="10%" style="padding:0;">
							<a href="javascript:void(0);" class="updateQuartzTask" dataValue="<s:property value="#task.taskId"/>">修改</a>&nbsp;&nbsp;
							<a href="javascript:void(0);" class="refleshQuartzTask" dataValue="<s:property value="#task.taskId"/>">刷新</a>
						</td>
					</tr>
					</s:iterator>
				</s:if>
				</tbody>
			</table>
			<br/>
			<table class="page_table" width="98%" align="center">
			   <tr>
			     <td align="right"><s:include  value="/WEB-INF/jsp/public/pager.jsp"/></td>
			   </tr>
			</table>
		</s:form>
		<script type="text/javascript">
		String.prototype.trimLR=function() {
			return this.replace(/(^\s*)|(\s*$)/g,'');
		}
		$(function(){
			var selectVal=$('#isVolatile').attr('data');
			$('#isVolatile option[value='+selectVal+']').attr('selected','selected');
			$('.btnReset').click(function(){
				document.getElementById('quartzManagerForm').reset();
				$('.list_table input').each(function(){
					var innerObj=$(this);
					if('add'==innerObj.attr('addFlag')){
						innerObj.parent().parent().remove();
						return;
					}
					innerObj.parent().html(innerObj.attr('orgValue'));
				});
				$('.updateQuartzTask').html('修改');
			});
			$('.btnAddTask').on('click',function(){
				var inputs=$('.list_table input');
				if(inputs.length>0){
					inputs.each(function(){
						shake($(this));
					});
					return ;
				}
				var trHtml='<tr><td width="6%"></td>'+
				'<td width="8%" style="padding:0;" valueKey="sysCode" class="allowEditTd noallownull"></td>'+
				'<td width="8%" style="padding:0;" valueKey="jobName" class="allowEditTd noallownull"></td>'+
				'<td width="8%" style="padding:0;" valueKey="jobGroup" class="allowEditTd noallownull"></td>'+
				'<td width="8%" style="padding:0;" valueKey="jobClass" class="allowEditTd noallownull"></td>'+
				'<td width="8%" style="padding:0;" valueKey="triggerName" class="allowEditTd noallownull"></td>'+
				'<td width="8%" style="padding:0;" valueKey="triggerGroup" class="allowEditTd noallownull"></td>'+
				'<td width="10%" style="padding:0;" valueKey="cronExpression" class="allowEditTd noallownull"></td>'+
				'<td width="4%" style="padding:0;" valueKey="isVolatile" class="allowEditTd noallownull">0</td>'+
				'<td width="12%" style="padding:0;" valueKey="description" class="allowEditTd"></td>'+
				'<td width="10%" style="padding:0;"></td><td width="10%" style="padding:0;"><a href="javascript:void(0);" class="updateQuartzTask" dataValue="">保存</a></td></tr>';
				$('.list_table tbody').append(trHtml).find('tr:last .allowEditTd').each(function(){
					var innerObj=$(this);
					var input='<input type="text" addFlag="add" orgValue="'+innerObj.text()+'" style=";padding:0;margin:0;border:1px solid #00A2FF;font-size:16px;width:'+(innerObj.width()-2)+'px" valueKey="'+innerObj.attr('valueKey')+'" value="'+innerObj.text()+'"  />';
					innerObj.html(input);
				});
			});
			//修改 
			$(document).on('click','.updateQuartzTask',function(){
				var obj=$(this);
				var html=obj.html();
				if('修改'==html){
					var inputs=$('.list_table input');
					if(inputs.length>0){
						inputs.each(function(){
							shake($(this));
						});
						return ;
					}
					obj.html('保存').parent().parent().find('.allowEditTd').each(function(index){
						var innerObj=$(this);
						var input='<input type="text" orgValue="'+innerObj.text()+'" style="padding:0;margin:0;border:1px solid #00A2FF;font-size:16px;width:'+(innerObj.width()-2)+'px" valueKey="'+innerObj.attr('valueKey')+'" value="'+innerObj.text()+'"  />';
						innerObj.html(input);
					});
				}else {
					var allowSubmit=true;
					var params='';
					obj.parent().parent().find('input').each(function(){
						var ctinput=$(this);
						if(ctinput.parent().hasClass('noallownull')&&(null==this.value||this.value.trimLR().length<1)){
							allowSubmit=false;
							shake(ctinput);
						}else if(ctinput.attr('orgValue')!=ctinput.val()){
							//判断参数是否为定时任务的描述，是则不进行编码 
							if(ctinput.attr('valueKey') == 'description'){
								params+=ctinput.attr('valueKey')+'='+this.value.trimLR()+'&'
							}else{
								params+=ctinput.attr('valueKey')+'='+escape(this.value.trimLR())+'&';
							}
						}
					});
					if(allowSubmit&&params.length>0){
						if(!confirm('是否要保存定时任务'+obj.attr('datavalue')+'！')){
							return;
						}
						var taskid=obj.attr('datavalue');
						var reqUrl='/app/updateQuartzTask.action?';
						if(null!=taskid&&taskid.length>0){
							params+='taskid='+taskid;
						}else{
							reqUrl='/app/addQuartzTask.action?';
						}
						$.ajax({
							url:reqUrl+params,
							datatype: "text",
		                    async: false,
							success: function(){
								$('#quartzManagerForm').submit();
					      	},
					      	error:function(){
					      		if(null!=taskid&&taskid.length>0){
					      			alert('更新失败！');
								}else{
									alert('保存失败！');
								}
						    }
						});
					}else if(allowSubmit&&0==params.length){
						obj.parent().parent().find('input').each(function(){
							var org=$(this);
							org.parent().html(org.attr('orgValue'));
							obj.html('修改');
						});
					}
				}
			});
			$('.refleshQuartzTask').click(function(){
				var taskId=$(this).attr('dataValue');
				if(confirm('该操作会及时更新任务'+taskId+'，请谨慎操作！')){
					$.ajax({
						url:'/app/refleshTask.action?taskId='+taskId,
						datatype: "text",
	                    async: false,
						success: function(){
							$('#quartzManagerForm').submit();
				      	},
				      	error:function(){
				      		alert('更新定时任务失败！');
					    }
					});
				}
			});
		});
		//闪烁
		function shake(obj){
			var i = 0,t= false ,o =obj.css('border'),cls ='1px solid red',times=5;
			if(t) return;
			t = setInterval(function(){
				i++;
				obj.css('border',i%2 ? cls : o);
				if(i==2*times){
					clearInterval(t);
					obj.removeClass(cls);
				}
			},200);
		};
		</script>
	</body>
</html>