<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
	<head>
		<title>窗口列表</title>
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
			var timer_alert = setTimeout(function() {
				messageCloseThis();
			}, 2000);
			
    		var checks = "";
    		checks = $("#checkeds").val();
    		if(checks!=""){
        		var checkboxs = document.getElementsByName("promotionIds");
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
        	
        $("#windowForm").validate({
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
		<s:set name="name" value="'窗口列表'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<div  style="height:90%;overflow-y:scroll; " >
		<s:form class="pageForm" name="windowForm" id="windowForm" onsubmit="return checkAllTextValid(this)" action="/cms/cmsWindowAction_queryWindowPage.action" method="post">
		<table width="98%" align="center" height="90" border="0"	class="content_table"
			cellpadding="0" cellspacing="0">
				<tr>
					<td width="60%" valign="middle" colspan="2">
						<!-- hidden -->
						<input type="hidden" id="pageNo_keyWords" name="keyWords.pageNo" value="<s:property value='keyWords.pageNo'/>">
						<input type="hidden" id="pageSize_keyWords" name="keyWords.pageSize" value="<s:property value='keyWords.pageSize'/>"/>
						<input type="hidden" name="checkeds" id="checkeds" value="<s:property value='checkeds'/>"/>
						<input type="hidden" name="adminType" id="adminType" value="<s:property value='adminType'/>">
						<!-- /hidden -->
						<INPUT class="addBtn" TYPE="button" value="" />
						<s:if test="adminType!=0">
							<input class="delBtn" type="button" value=""/>
						</s:if>
					</td>
				</tr>
				<tr>
					<td width="45%">
						窗口编号:<input type="text" id="id_keyword" name="keyWords.id_keyword" value="<s:property value='keyWords.id_keyword'/>">
					</td>
					<td width="45%">
						窗口名称:<input type="text" id="name_keyword" name="keyWords.name_keyword" value="<s:property value='keyWords.name_keyword'/>">
					</td>
				</tr>
				<tr>
					<td width="45%">
						窗口主题:<input type="text" id="theme_keyword" name="keyWords.theme_keyword" value="<s:property value='keyWords.theme_keyword'/>">
					</td>
					<td width="45%">
						绑定类型:<select name="keyWords.type_keyword" id="type_keyword">
							<option value="" <s:if test="keyWords.type_keyword==''">selected="selected"</s:if>>全部</option>
					        <option value="0" <s:if test="keyWords.type_keyword==0">selected="selected"</s:if>>系统绑定</option>
					        <option value="1" <s:if test="keyWords.type_keyword==1">selected="selected"</s:if>>人工绑定</option>
					        </select>
						<input type="hidden" name="keyWords.status_keyword" id="status_keyword"/>
					<!-- 	状态:<select name="keyWords.status_keyword" id="status_keyword">
							<option value="" <s:if test="keyWords.status_keyword==''">selected="selected"</s:if>>全部</option>
					        <option value="0" <s:if test="keyWords.status_keyword==0">selected="selected"</s:if>>有效</option>
					        <option value="1" <s:if test="keyWords.status_keyword==1">selected="selected"</s:if>>无效</option>
					        </select>
					         -->
					        
					</td>
					<td  align="right"><input type="submit" class="queryBtn" value=""/></td>
				</tr>
			</table>
		<table width="98%" class="list_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
			<tr>
			    <th><input type='checkbox' name='promotion' id="promotion"  onclick="checkAllpop(this,'promotionIds')"></th>
				<th align="center">编号</th>
				<th align="center">窗口名称</th>
				<th align="center">窗口主题</th>
				<th align="center">参数绑定类型</th>
				<!-- <th align="center">状态</th> -->
				<th>
						         修改日期
						</th>
						<th>
						         修改人
						</th>
				<th align="center">操作</th>
			</tr>
			<s:iterator id="cmsWindowiterator"  value="page.dataList">
			<tr>
			     <td>
				     <input type="checkbox" name="promotionIds" value='<s:property value="windowId"/>' onclick="checkpromotionId(this);">
				     
			     </td>
				<td align="center">
				     <s:property value="windowId"/>
				</td>
				<td align="center">
				     <s:property value="name"/>  
				</td>
				<td align="center">
				     <s:property value="theme"/>
				</td>
				<td align="center">
				     <s:if test="paramsType==0">系统传参</s:if><s:if test="paramsType==1">人工绑定</s:if>
				</td>
				
				<!-- 
				<td align="center">
				     <s:if test="status==0">有效</s:if><s:if test="status==1">无效</s:if>
				</td>
				 -->
				 
				<td><s:date name="modifyDate" format="yyyy-MM-dd HH:mm:ss"/>
							
						</td>
						<td>
							<s:property value="sysUserMap[modified]"/>
						</td>
				<td>
					<img title="详情" style="cursor: pointer;" src="/etc/images/icon_detail.png"   onclick='windowDetail(<s:property value="windowId"/>)' />
				   
				    <img title="绑定" style="cursor: pointer;" src="/etc/images/icon_select.png"   onclick='bink(<s:property value="windowId"/>)' />
					<s:if test="adminType!=0">
						<img title="修改" style="cursor: pointer;" src="/etc/images/icon_modify.png"   onclick='editPage(<s:property value="windowId"/>)' />
						<img title="删除" style="cursor: pointer;" src="/etc/images/icon_delete.png"   onclick='deletePage(<s:property value="windowId"/>)' />
					</s:if>
				</td>
			</tr>
			</s:iterator>
		</table>
		
		<table width="98%"  align="center" class="page_table">
			<tr>
				<td>
					<s:set name="form_name"  value="'windowForm'"  scope="request"></s:set>
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
		var pageForm= window.document.getElementById("windowForm");
		$(function(){
			//跳转添加页
	   		$(".addBtn").click(function(){
	   			//var pageNo=$("#pageNo").val();
	   			//window.location.href="/cms/cmsWindowAction_gotoAddPage.action?pageNo="+pageNo;
	   			pageForm.action="/cms/cmsWindowAction_gotoAddPage.action"; 
 				pageForm.submit();
	   		});
	   		//批量删除窗口
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
					window.location.href="/cms/cmsWindowAction_delAllWindow.action?checkeds="+checks;
				}
	   		});
	   });
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
 		//跳转编辑页
 		function editPage(id)
 		{
 			//var pageNo=$("#pageNo").val();
			//window.location.href="/cms/cmsWindowAction_gotoEditWin.action?windowId="+id+"&pageNo="+pageNo;
			pageForm.action="/cms/cmsWindowAction_gotoEditWin.action?windowId="+id; 
 			pageForm.submit();
 		}
 		//单个窗口删除
 		function deletePage(id)
 		{
 			var ok=confirm("确定删除吗？删除后将不能恢复！");
	   		if(ok==false)
	   		{
	   			return ;
	   		}
	   		else
	   		{
				window.location.href="/cms/cmsWindowAction_delWindow.action?windowId="+id;
			}
 		}
 		//跳转窗口绑定数据
 		function bink(id)
 		{
 			var adminType = $("#adminType").val();
 			window.location.href="/cms/cmsWindowDataAction_queryPageList.action?windowId="+id+"&adminType="+adminType;
 		}
 		//窗口详情
 		function windowDetail(id)
 		{
 			var id_keyword=$("#id_keyword").val(); 
	   		var name_keyword=$("#name_keyword").val();
	   		var type_keyword=$("#type_keyword").val();
	   		var theme_keyword=$("#theme_keyword").val();
	   		var status_keyword=$("#status_keyword").val();
	   		var pageNo=$("#pageNo_keyWords").val();
	   		var pageSize=$("#pageSize_keyWords").val();
	   		var adminType = $("#adminType").val();
	   		window.location.href="/cms/cmsWindowAction_windowDetail.action?windowId="+id+"&keyWords.id_keyword="+id_keyword
	   				+"&keyWords.name_keyword="+name_keyword+"&keyWords.type_keyword="+type_keyword+"&keyWords.status_keyword="
	   				+status_keyword+"&keyWords.pageNo="+pageNo+"&keyWords.pageSize="+pageSize+"&keyWords.theme_keyword="+theme_keyword+
	   				"&adminType="+adminType; 
 		}
 		//提示信息显示
 		function showTips()
 		{
 			var msg="<img src='../etc/images/tipMsg.png' style='vertical-align:middle'/>该窗口类型有误或失效，不能绑定数据!";
		    messageDialog("消息提示","text:"+msg ,"300px","102px","text");
 		}
	</script>
</html>

