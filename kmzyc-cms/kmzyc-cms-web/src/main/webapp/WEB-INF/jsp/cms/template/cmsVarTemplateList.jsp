<%@page contentType="text/html;charset=UTF-8"%> 
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
	<head>
		<title>模板管理</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript"  src="/etc/js/jquery-latest.pack.js"></script>
		<script src="/etc/js/dialog.js"></script>
		<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
		<script type="text/javascript"  src="/etc/js/checkeds.js"></script>
		<script type="text/javascript"  src="/etc/js/rowDisplay.js"></script>
 		<script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
		<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>
		<script type="text/javascript">
		
		$(document).ready(function(){
    		var checks = "";
    		checks = $("#checkeds").val();
    		if(checks!=""){
        		var checkboxs = document.getElementsByName("levelId");
        		var myarr = new Array();
    			myarr = checks.split(',');
        		for(var i = 0; i<checkboxs.length; i++){
            		for(var j = 0; j<myarr.length; j++){
                		if(checkboxs[i].value==myarr[j]){
                			checkboxs[i].checked = true;
                			break;
                    	}
                	}
            	}
        	}
        	$("#templateForm").validate({
	         rules: {
					"cmsTemplate.templateId": {keywordIdChar:true,maxlength:10}
		        	},
		     success: function (label){
		            label.removeClass("checked").addClass("checked");
		            }
	          });
	   });
		
		/**绑定  **/
function gotoBandList(id) {
	//   var pageNo=$("#pageNo").val();
	location.href = "/cms/cmsTemplateAction_gotoBandList.action?cmsTemplate.templateId="+ id;
}
		
		</script>
		
	</head>
	<body >
		<!-- 导航栏 -->
		<s:set name="parent_name" value="'基础功能'" scope="request" />
		<s:set name="name" value="'模板管理'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<div  style="height:90%;overflow-y:scroll; " >
		<s:form id="templateForm" class="templateForm" name="templateForm" onsubmit="return checkAllTextValid(this)" action="/cms/cmsTemplateAction_queryVarTemplate.action" method="post">
			<!-- hidden -->
	
			<table width="98%" align="center" height="90" border="0"	class="content_table"
			cellpadding="0" cellspacing="0">
				<tr>
					<td width="60%" valign="middle" colspan="2">
						<!-- hidden -->
						<input type="hidden" name="checkeds" id="checkeds" value="<s:property value='checkeds'/>"/>
						<input type="hidden" name="pid" value="<s:property value="pid"/>" id="pid"/>
						<input type="hidden" name="path" value="<s:property value="path"/>" id="path"/>
						<!-- /hidden -->
						<INPUT class="addBtn" TYPE="button" value="" />
						<input class="delBtn" type="button" value=""/>
					</td>
				</tr>
				<tr>
					<td width="45%">
						模板编号:<input type="text"  name="cmsTemplate.templateId" value="<s:property value='cmsTemplate.templateId'/>"/>
					</td>
					<td width="45%">
						模板名称:<input type="text"  name="cmsTemplate.name" value="<s:property value='cmsTemplate.name'/>">
					</td>
				</tr>
				<tr>
					<td width="45%">
						模板主题:<input type="text"  name="cmsTemplate.theme" value="<s:property value='cmsTemplate.theme'/>">
					</td>
					<td width="45%">
						模板类型:<select name="cmsTemplate.type" >
							<option value="" <s:if test="cmsTemplate.type==''">selected="selected"</s:if>>全部</option>
					   
					        <option value="13" <s:if test="cmsTemplate.type==13">selected="selected"</s:if>>窗口变量模板</option>
					        <option value="12" <s:if test="cmsTemplate.type==12">selected="selected"</s:if>>页面变量模板</option>
					        </select>
						状态:<select name="cmsTemplate.status" >
							<option value="" <s:if test="cmsTemplate.status==''">selected="selected"</s:if>>全部</option>
					        <option value="0" <s:if test="cmsTemplate.status==0">selected="selected"</s:if>>有效</option>
					        <option value="1" <s:if test="cmsTemplate.status==1">selected="selected"</s:if>>无效</option>
					        </select>
					</td>
					 <td align="right"><input type="submit" class="queryBtn" value=""/></td>
				</tr>
			</table>
			<table width="98%" class="list_table" cellpadding="3" align="center">
				<tr>
					<th width="5%">
						<input type='checkbox' name='level'  onclick="checkAllpop(this,'levelId')">
					</th>
					<th>编号</th>
					<th>模板名称</th>
					<th>模板主题</th>
					<th>模板类型</th>
					<th>状态</th>
					<th >
					         修改日期
					</th>
					<th >
					         修改人
					</th>
					<th>操作</th>
				</tr>
			<s:iterator id="custiterator" value="page.dataList">
				<tr>
					<td width="5%">
							<input type="checkbox" name="levelId" id="levelId"
								value='<s:property value="templateId"/>' onclick="checkpromotionId(this);">
					</td>
					<td><s:property value="templateId"/></td>
					<td><s:property value="name"/></td>
					<td><s:property value="theme"/></td>
					<td>
					<s:if test="type==13">窗口变量模板</s:if>
					<s:if test="type==12">页面变量模板</s:if>
					</td>
					<td><s:if test="status==1">无效</s:if><s:if test="status==0">有效</s:if></td>
						<td><s:date name="modifyDate" format="yyyy-MM-dd HH:mm:ss"/>
							
						</td>
						<td>
							<s:property value="sysUserMap[modified]"/>
						</td>
				
					<td>
					<s:if test="type==13">
					
					      	<img title="绑定" style="cursor: pointer;" src="/etc/images/icon_select.png"   onclick="gotoBandList(<s:property value="templateId"/>);"/>
					</s:if>
						<img title="详情" style="cursor: pointer;" src="/etc/images/icon_detail.png"   onclick='templateDetail(<s:property value="templateId"/>)' />
						<img title="修改" style="cursor: pointer;" src="/etc/images/icon_modify.png"   onclick='editTemplate(<s:property value="templateId"/>)' />
						<img title="删除" style="cursor: pointer;" src="/etc/images/icon_delete.png"   onclick='deleteTemplate(<s:property value="templateId"/>)' />
					</td>	
				</tr>
			</s:iterator>
			</table>
			<table class="page_table" width="98%" align="center" cellpadding="0"
				cellspacing="0" border="0">
				<tr>
					<td>
						<s:set name="form_name" value="'templateForm'" scope="request"></s:set>
						<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
					</td>
				</tr>
			</table>
		</s:form>
			<!-- 消息提示页面 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
		</div>
	</body>
	<script type="text/javascript">
	var pageForm= window.document.getElementById("templateForm");
	 $(function(){
	 		//批量删除
			$(".delBtn").click(function(){
				var checks=$("#checkeds").val();
				if(checks=='')
				{
					var msg="<img src='../etc/images/tipMsg.png' style='vertical-align:middle'/>请选择!";
		            messageDialog("消息提示","text:"+msg ,"300px","102px","text");  
					return ;
				}
				var ok=confirm("确定删除吗？删除后将不能恢复！");
	   			if(ok==false)
	   			{
	   				return ;
	   			}
	   			else
	   			{
					window.location.href="/cms/cmsTemplateAction_deleteAll.action?checkeds="+checks;
				}
			});
			//跳转添加模板页
			$(".addBtn").click(function(){
				pageForm.action="/cms/cmsTemplateAction_gotoTemplateAdd.action?type=var"; 
 				pageForm.submit();
				//window.location.href="/cms/cmsTemplateAction_gotoTemplateAdd.action?pid="+pid+"&path="+path+"&pageNo="+pageNo;
			});
		});
		//跳转编辑页
		function editTemplate(id)
		{
			//var pageNo=$("#pageNo").val();
			//window.location.href="/cms/cmsTemplateAction_getTemplateById.action?templateId="+id+"&pageNo="+pageNo;
			pageForm.action="/cms/cmsTemplateAction_getTemplateById.action?templateId="+id; 
 			pageForm.submit();
		}
		//单个模板删除
		function deleteTemplate(id)
		{
			var ok=confirm("确定删除吗？删除后将不能恢复！");
	   		if(ok==false)
	   		{
	   			return ;
	   		}
	   		else
	   		{
				window.location.href="/cms/cmsTemplateAction_deleteTemplate.action?templateId="+id;
			}
		}
		//模板详情
		function templateDetail(id)
		{
			
	   		
	   		
	   		pageForm.action="/cms/cmsTemplateAction_getTemplateDetail.action?templateId="+id; 
 			pageForm.submit();
		}
		//关闭弹出层
		 function closeOpenUserInfo(accountId,account,name){
            closeThis();
            $("#loginAccount").val(account);
             $("#n_LoginId").val(accountId);
        }
        //返回
		 function gotoList(){
  			 history.back();
 		}
	</script>
</html>

