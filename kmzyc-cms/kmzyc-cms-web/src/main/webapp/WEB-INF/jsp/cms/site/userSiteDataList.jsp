<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
	<head>
		<title>数据授权列表</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		<script src="/etc/js/dialog.js"></script>
		<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
		<script type="text/javascript"  src="/etc/js/windowData.js"></script>
		<script type="text/javascript"  src="/etc/js/rowDisplay.js"></script>
		<script type="text/javascript">

		function gotoList(){
				location.href="/cms/cmsUserSite_queryForPage.action";
		}


        /** 关闭页面弹出层  **/
        function  closePage(checkeds,userId,siteId){
        	closeThis();
        	location.href="cmsPageAction_openPageList.action?checkeds="+checkeds+"&userId="+userId+"&siteId="+siteId+"&dataType=1";
        }

        /** 关闭广告弹出层  **/
        function  closeAdv(checkeds,userId,siteId){
        	closeThis();
        	location.href="Adv_openAdvList.action?checkeds="+checkeds+"&userId="+userId+"&siteId="+siteId+"&dataType=2";
        }

        /** 关闭活动弹出层  **/
        function  closePomotion(checkeds,userId,siteId){
        	closeThis();
        	location.href="cmsPromotion_openPomotionList.action?checkeds="+checkeds+"&userId="+userId+"&siteId="+siteId+"&dataType=3";
        }

        /** 关闭窗口弹出层  **/
        function  closeWindow(checkeds,userId,siteId){
        	closeThis();
        	location.href="cmsWindowAction_popOpenWindowList.action?checkeds="+checkeds+"&userId="+userId+"&siteId="+siteId+"&dataType=4";
        }

        
     
        //修改自定义数据
        function updateByKey(windowDataId)
        {
        	var windowId=$("#windowId").val();
            dialog("修改自定义信息","iframe:/cms/cmsWindowDataAction_updateUserDefineDate.action?windowDataId="+windowDataId+"&windowId="+windowId,"700px","420px","iframe");
        }
        function deleteById(id){
        	if(confirm("是否确认删除? ")==true){
                var userId = $("#userId").val();
                var siteId = $("#siteId").val();
               document.windowDataForm.action="/cms/cmsSiteData_deleteData.action?userSiteDataIds="+id;
               document.windowDataForm.submit();
               // location.href="/cms/cmsSiteData_deleteData.action?userSiteDataIds="+id+"&userId="+userId+"&siteId="+siteId;
              }            
        }

        function deleteSelect(id){
        	var obj = document.getElementsByName(id);
            var count = 0;
            var obj_cehcked = null;
            // 遍历所有用户，找出被选中的用户
            for (var i = 0; i < obj.length; i++) {
                if (obj[i].checked) {
                    count++;
                    obj_cehcked = obj[i];
                }
            }
             if (count == 0) {
                    alert("请选择要删除的数据。");
                    return false;
             }else if(confirm('是否确认删除?')==true){
                      document.windowDataForm.action="/cms/cmsSiteData_deleteData.action";
                      document.windowDataForm.submit();
             }
        }

      //弹出页面层
	    function popUpPage() {
	    	var userId=$("#userId").val();
	    	var siteId=$("#siteId").val();
	        dialog("选择页面信息","iframe:/cms/cmsPageAction_queryByPage.action?userId="+userId+"&siteId="+siteId+"&dataType=1" ,"900px","530px","iframe");
	    }

	    
	    //弹出广告层
	    function popUpAdv() {
	    	var userId=$("#userId").val();
	    	var siteId=$("#siteId").val();
	        dialog("选择广告信息","iframe:/cms/Adv_queryByAdv.action?userId="+userId+"&siteId="+siteId+"&dataType=2" ,"900px","530px","iframe");
	    }
	    
	    //弹出活动层
	    function popUpPromotion() {
	    	var userId=$("#userId").val();
	    	var siteId=$("#siteId").val();
	        dialog("选择活动信息","iframe:/cms/cmsPromotion_queryByPromotion.action?userId="+userId+"&siteId="+siteId+"&dataType=3" ,"900px","530px","iframe");
	    }
	    //弹出窗口层
	    function popUpWindows() {
	    	var userId=$("#userId").val();
	    	var siteId=$("#siteId").val();
	    	 dialog("选择窗口信息","iframe:/cms/cmsWindowAction_popQueryWindow.action?userId="+userId+"&siteId="+siteId+"&dataType=4" ,"900px","530px","iframe");
	    }
	    
	    
		</script>
	</head>
	<body>
		<!-- 导航栏 -->
		<s:set name="parent_name" value="'授权管理 '" scope="request" />
		<s:set name="name" value="'用户站点列表'" scope="request" />
		<s:set name="son_name" value="'数据授权列表'" scope="request" />
		
		
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<div  style="height:90%;overflow-y:scroll; " >
		<s:form name="windowDataForm"   onsubmit=" return checkAllTextValid(this)"
			action="/cms/cmsSiteData_warrant.action" method="post">
			<s:token></s:token>
			<!-- 查询条件 -->
		    <table width="98%" align="center" height="90" border="0"	class="content_table"
			cellpadding="0" cellspacing="0">
				<tr>
					<td width="60%" valign="middle" colspan="2">
					<input class="backBtn"  onclick="gotoList()" type="button" value="">
						<input class="delBtn" type="button" value=""
							onclick="deleteSelect('userSiteDataIds');">
							
					</td>
				</tr>
				
				<tr width="100%" height="40">
					
					<td align="left" width="20%">
						数据类型:
						<select name="cmsSiteData.dataType">
						<s:if test="cmsSiteData.dataType==1">
							<option value="">全部</option>
							<option value="1" selected="selected">页面</option>
							<option value="2">广告</option>
							<option value="3">活动</option>
							<option value="4">窗口</option>
						</s:if>
						<s:elseif test="cmsSiteData.dataType==2">
							<option value="">全部</option>
							<option value="1">页面</option>
							<option value="2" selected="selected">广告</option>
							<option value="3">活动</option>
							<option value="4">窗口</option>
						</s:elseif>
						
						<s:elseif test="cmsSiteData.dataType==3">
							<option value="">全部</option>
							<option value="1">页面</option>
							<option value="2">广告</option>
							<option value="3" selected="selected">活动</option>
							<option value="4">窗口</option>
						</s:elseif>
						
						<s:elseif test="cmsSiteData.dataType==4">
							<option value="">全部</option>
							<option value="1">页面</option>
							<option value="2">广告</option>
							<option value="3">活动</option>
							<option value="4" selected="selected">窗口</option>
						</s:elseif>
						
						<s:else>
							<option value="" selected="selected">全部</option>
							<option value="1">页面</option>
							<option value="2">广告</option>
							<option value="3">活动</option>
							<option value="4">窗口</option>
						</s:else>
							
						</select>
					</td>
					
					<td align="right" >
						<INPUT TYPE="submit" class="queryBtn" value="">
					</td>
				</tr>
			
			<tr>  
				<td colspan="4"  align="center" >
				    <input type="button" value="页面授权" onclick="popUpPage();"  />&nbsp;
					<input type="button" value="广告授权" onclick="popUpAdv();" />&nbsp;
					<input type="button" value="活动授权" onclick="popUpPromotion();" />&nbsp;
					<input type="button" value="窗口授权" onclick="popUpWindows();" />&nbsp;
					<input type="hidden" id="userId" name="userId" value="<s:property value='userId' />">
					<input type="hidden" id="siteId" name="siteId" value="<s:property value='siteId' />">
					
				</td>
			</tr>
			</table>
			<!-- 数据列表区域 -->
			<table width="98%" class="list_table" cellpadding="3" align="center"
				cellspacing="0" border="1" id="list_table">
				<tr class="dataName">
					<th width="5%">
						<input type='checkbox' name='windowData'  onclick="checkAll(this,'userSiteDataIds');">
					</th>
					<th>
						数据名称
					</th>
					<th>
						数据类型
					</th>
					<th width="60">
						操作
					</th>
				</tr>
				<s:iterator id="custiterator" value="page.dataList">
					<tr class="dataTr">
						<td width="5%">
							<input type="checkbox" name="userSiteDataIds" value="<s:property value='userSiteDataId'/>">
						</td>
						<td>
							<s:property value="dataName" />
						</td>
					
						<td>
							<s:if test="dataType==1">
								页面
							</s:if>
							<s:elseif test="dataType==2">
								广告
							</s:elseif>
							<s:elseif test="dataType==3">
								活动
							</s:elseif>
							<s:elseif test="dataType==4">
								窗口
							</s:elseif>
						</td>
						
						<td>
						     <img title="删除" style="cursor: pointer;" src="/etc/images/icon_delete.png"   onclick="deleteById(<s:property value='userSiteDataId'/>)" />
						</td>
					</tr>
				</s:iterator>
			</table>
			
			<table class="page_table" width="98%" align="center" cellpadding="0"
				cellspacing="0" border="0">
				<tr>
					<td>

						<s:set name="form_name" value="'windowDataForm'" scope="request"></s:set>
						<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
					</td>
				</tr>
			</table>
			
		</s:form>
		
		
		<!-- 消息提示页面 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
		</div>
	</body>
</html>

