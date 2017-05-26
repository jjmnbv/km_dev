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
					"keyWords.id_keyword": {keywordIdChar:true,maxlength:10}
		        	},
		     success: function (label){
		            label.removeClass("checked").addClass("checked");
		            }
	          });
	   });
		
		</script>
		
	</head>
	<body >
		<!-- 导航栏 -->
		<s:set name="parent_name" value="'基础功能'" scope="request" />
		<s:set name="name" value="'模板管理'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<div  style="height:90%;overflow-y:scroll; " >
		<s:form id="templateForm" class="templateForm" name="templateForm" onsubmit="return checkAllTextValid(this)" action="/cms/cmsTemplateAction_queryTemplate.action" method="post">
			<!-- hidden -->
			<input type="hidden" id="pageNo_keyWords" name="keyWords.pageNo" value="<s:property value='keyWords.pageNo'/>">
			<input type="hidden" id="pageSize_keyWords" name="keyWords.pageSize" value="<s:property value='keyWords.pageSize'/>"/>
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
						模板编号:<input type="text" id="id_keyword" name="keyWords.id_keyword" value="<s:property value='keyWords.id_keyword'/>"/>
					</td>
					<td width="45%">
						模板名称:<input type="text" id="name_keyword" name="keyWords.name_keyword" value="<s:property value='keyWords.name_keyword'/>">
					</td>
				</tr>
				<tr>
					<td width="45%">
						模板主题:<input type="text" id="theme_keyword" name="keyWords.theme_keyword" value="<s:property value='keyWords.theme_keyword'/>">
					</td>
					<td width="45%">
						模板类型:<select name="keyWords.type_keyword" id="type_keyword">
							<option value="" <s:if test="keyWords.type_keyword==''">selected="selected"</s:if>>全部</option>
					        <option value="0" <s:if test="keyWords.type_keyword==0">selected="selected"</s:if>>窗口模板</option>
					        <option value="1" <s:if test="keyWords.type_keyword==1">selected="selected"</s:if>>页面模板</option>
					        <option value="2" <s:if test="keyWords.type_keyword==2">selected="selected"</s:if>>供应商广告模板</option>
					        <option value="3" <s:if test="keyWords.type_keyword==3">selected="selected"</s:if>>活动模板</option>
					        <option value="4" <s:if test="keyWords.type_keyword==4">selected="selected"</s:if>>资讯模板</option>
					        <option value="5" <s:if test="keyWords.type_keyword==5">selected="selected"</s:if>>帮助模板</option>
					         <option value="6" <s:if test="keyWords.type_keyword==6">selected="selected"</s:if>>移动端模板</option>
					        <option value="9" <s:if test="keyWords.type_keyword==9">selected="selected"</s:if>>商家入驻模板</option>
					        <option value="7" <s:if test="keyWords.type_keyword==7">selected="selected"</s:if>>窄版静态专题页模板</option>
					        <option value="8" <s:if test="keyWords.type_keyword==8">selected="selected"</s:if>>宽版静态专题页模板</option>
					        <option value="10" <s:if test="keyWords.type_keyword==10">selected="selected"</s:if>>广告模板</option>
					        <option value="11" <s:if test="keyWords.type_keyword==11">selected="selected"</s:if>>招商帮助模板</option>
					        <!-- <option value="15" <s:if test="keyWords.type_keyword===15">selected="selected"</s:if>>抽奖模板</option>
                            <option value="16" <s:if test="keyWords.type_keyword===16">selected="selected"</s:if>>抽奖窗口模板</option> -->
                             <option value="17" <s:if test="keyWords.type_keyword===17">selected="selected"</s:if>>招商入驻</option>
                             <option value="18" <s:if test="keyWords.type_keyword===18">selected="selected"</s:if>>合作模式</option>
                             <option value="19" <s:if test="keyWords.type_keyword===19">selected="selected"</s:if>>商家规则</option>       
                              <option value="20" <s:if test="keyWords.type_keyword===20">selected="selected"</s:if>>商家店铺</option>
                              <option value="21" <s:if test="keyWords.type_keyword===21">selected="selected"</s:if>>活动中心</option>
                               <option value="22" <s:if test="keyWords.type_keyword===22">selected="selected"</s:if>>商户服务</option>
                               <option value="23" <s:if test="keyWords.type_keyword===23">selected="selected"</s:if>>活动中心帮助模板</option>
                               <option value="24" <s:if test="keyWords.type_keyword==24">selected="selected"</s:if>>中药城帮助中心模板</option>
					        </select>
						状态:<select name="keyWords.status_keyword" id="status_keyword">
							<option value="" <s:if test="keyWords.status_keyword==''">selected="selected"</s:if>>全部</option>
					        <option value="0" <s:if test="keyWords.status_keyword==0">selected="selected"</s:if>>有效</option>
					        <option value="1" <s:if test="keyWords.status_keyword==1">selected="selected"</s:if>>无效</option>
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
					<td><s:if test="type==0">窗口模板</s:if><s:if test="type==1">页面模板</s:if><s:if test="type==2">供应商广告模板</s:if><s:if test="type==3">活动模板</s:if>
					<s:if test="type==4">资讯模板</s:if><s:if test="type==5">帮助模板</s:if><s:if test="type==6">移动端模板</s:if>
					<s:if test="type==7">窄版静态专题页模板</s:if>
					<s:if test="type==8">宽版静态专题页模板</s:if>
					  <s:if test="type==15">抽奖模板</s:if>
					   <s:if test="type==16">抽奖窗口模板</s:if>
					
					<s:if test="type==17">招商入驻</s:if>
					<s:if test="type==18">合作模式</s:if>
					<s:if test="type==19">商家规则</s:if>
					<s:if test="type==20">商家店铺</s:if>
					<s:if test="type==21">活动中心</s:if>
					<s:if test="type==22">商户服务</s:if>
					<s:if test="type==24">中药城帮助中心模板</s:if>
					</td>
					<td><s:if test="status==1">无效</s:if><s:if test="status==0">有效</s:if></td>
						<td><s:date name="modifyDate" format="yyyy-MM-dd HH:mm:ss"/>
							
						</td>
						<td>
							<s:property value="sysUserMap[modified]"/>
						</td>
				
					<td>
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
				pageForm.action="/cms/cmsTemplateAction_gotoTemplateAdd.action"; 
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
			var id_keyword=$("#id_keyword").val(); 
	   		var name_keyword=$("#name_keyword").val();
	   		var theme_keyword=$("#theme_keyword").val();
	   		var type_keyword=$("#type_keyword").val();
	   		var status_keyword=$("#status_keyword").val();
	   		var pageNo=$("#pageNo_keyWords").val();
	   		var pageSize=$("#pageSize_keyWords").val();
	   		window.location.href="/cms/cmsTemplateAction_getTemplateDetail.action?templateId="+id+"&keyWords.id_keyword="+id_keyword
	   				+"&keyWords.name_keyword="+name_keyword+"&keyWords.type_keyword="+type_keyword+"&keyWords.status_keyword="
	   				+status_keyword+"&keyWords.pageNo="+pageNo+"&keyWords.pageSize="+pageSize+"&keyWords.theme_keyword="+theme_keyword;
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

