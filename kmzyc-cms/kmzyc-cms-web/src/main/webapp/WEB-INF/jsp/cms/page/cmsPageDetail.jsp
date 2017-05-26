<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
	<head>
		<title>页面列表</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		<script src="/etc/js/dialog.js"></script>
		<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
		<script type="text/javascript"  src="/etc/js/checkeds.js"></script>
		<script type="text/javascript"  src="/etc/js/rowDisplay.js"></script>
		
		<link rel="stylesheet" href="/etc/js/codemirror/codemirror.css">
		<link rel="stylesheet" href="/etc/js/codemirror/fullscreen.css">
		<link rel="stylesheet" href="/etc/js/codemirror/erlang-dark.css">
		<script src="/etc/js/codemirror/codemirror.js"></script>
		<script src="/etc/js/codemirror/xml.js"></script>
		<script src="/etc/js/codemirror/fullscreen.js"></script>
		<script src="/etc/js/codemirror/userdefined.js"></script>
		
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
	   });
	 function checkpromotion(obj){
 	 var check = "";
 	 	//checkeds以逗号隔开的Id的字符串，要对应Action的属性
      check = $("#checkeds").val();
      if(obj.checked){
     	 if(check==""){
         	 check = obj.value;
          }else{
              check +=","+obj.value;
          }
     	 $("#checkeds").val(check);
      }else{
          if(check!=""){
              if(check.indexOf(","+obj.value)>-1){
             	 check = check.replace(","+obj.value, "");
              }

              if(check.indexOf(obj.value+",")>-1){
             	 check = check.replace(obj.value+",", "");
              }
              
              if(check.indexOf(obj.value)>-1){
             	 check = check.replace(obj.value, "");
              }
         	$("#checkeds").val(check);
          }
      }
  }
	   function deleteWindow(id)
 		{
 			var ok=confirm("确定删除吗？");
	   		if(ok==false)
	   		{
	   			return ;
	   		}
	   		else
	   		{
	 			var pageId=$("#cmsPageId").val();
				window.location.href="/cms/cmsPageAction_delPwDate.action?windowId="+id+"&pageId="+pageId;
			}
 		}
 		function bink(id)
 		{
 			var pageId=$("#cmsPageId").val();
 			var adminType=$("#adminType").val();
 			window.location.href="/cms/cmsWindowDataAction_queryPageList.action?windowId="+id+"&pageId="+pageId+"&adminType="+adminType;
 		}
 		function bindWindow(id)
 		{
 	 		
 			var adminType=$("#adminType").val();
 			dialog("选择窗口","iframe:/cms/cmsPageAction_gotoBindWindow.action?pageId="+id+"&backType=1&adminType="+adminType+"&cmsPage.publicType="+$("#publicType").val() ,"1000px","520px","iframe"); 
 		}
 		
		
		function goBack(){
			var adminType=$("#adminType").val();
 			var id_keyword=$("#id_keyword").val(); 
	   		var name_keyword=$("#name_keyword").val();
	   		var outPath_keyword=$("#outPath_keyword").val();
	   		var theme_keyword=$("#theme_keyword").val();
	   		var status_keyword=$("#status_keyword").val();
	   		var pageNo=$("#pageNo_keyWords").val();
	   		var pageSize=$("#pageSize_keyWords").val();
	   		window.location.href="/cms/cmsPageAction_queryForPage.action?adminType="+adminType+"&keyWords.id_keyword="+id_keyword
	   				+"&keyWords.name_keyword="+name_keyword+"&keyWords.outPath_keyword="+outPath_keyword+"&keyWords.status_keyword="
	   				+status_keyword+"&keyWords.pageNo="+pageNo+"&keyWords.pageSize="+pageSize+"&cmsPage.publicType="+$("#publicType").val();
		 }
		function closeOpenUserInfo(result){
            closeThis();
            var id=$("#cmsPageId").val();
            var adminType = $("#adminType").val();
           // window.location.href="/cms/cmsPageAction_pageDetail.action?pageId="+id;
            if(result==2)
			{
				window.location.href="/cms/cmsPageAction_pageDetail.action?pageId="+id+"&adminType="+adminType; 
			}
        }
        function delAll()
        {
        	var ok=confirm("确定删除吗？");
	   		if(ok==false)
	   		{
	   			return ;
	   		}
	   		else
	   		{
	 			var pageId=$("#cmsPageId").val();
	 			var checkeds=$("#checkeds").val();
	 			var adminType=$("#adminType").val();
	 			var publicType=$("#publicType").val();
	 			
				window.location.href="/cms/cmsPageAction_delAll.action?checkeds="+checkeds+"&pageId="+pageId+"&cmsPage.publicType="+publicType+"&adminType="+adminType;
			}
        }
        function showTips(msgTips)
 		{
 			var msg="<img src='../etc/images/tipMsg.png' style='vertical-align:middle'/>"+msgTips;
		    messageDialog("消息提示","text:"+msg ,"300px","102px","text");
 		}
 		
 		
 		function editPage(id)  //修改窗口
 		{  var pageid=$("#pageId").val();

			window.location.href="/cms/cmsWindowAction_gotoEditWin.action?pageId="+pageid+"&windowId="+id; 
 		}
 		
 		
 	
		</script>
	<style type="text/css">
		.detail_table tr td
		{
			text-align: left;
		}
		.detail_table tr td span
		{
			font-weight: bold;
		}
		.detail_table .binkBtn
		{
			background-color: buttonface;
			cursor: pointer;
			border: 1px solid #CBCBCB;
			height: 25px;
		}
	</style>
	</head>
	<body >
		<!-- 导航栏 -->
		<s:set name="parent_name" value="'基础功能'" scope="request" />
		<s:set name="name" value="'页面详情'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<div  style="height:90%;" >
		<s:form id="pageDetailForm" name="pageDetailForm" onsubmit="return checkAllTextValid(this)" action="/cms/cmsPageAction_pageDetail.action" method="post">
			<input type="hidden" id="cmsPageId" name="pageId" value="<s:property value="cmsPageDetail.cmsPage.pageId" />" />
			<input type="hidden" name="checkeds" id="checkeds" value="<s:property value="checkeds"/>"/>
			<input type="hidden" id="status" value="<s:property value="cmsPageDetail.cmsPage.status" />"/>
			<input name="adminType" id="adminType" type="hidden" value="<s:property value="adminType" />"/>
		    <!-- keyWords -->
			<input type="hidden" id="id_keyword" name="keyWords.id_keyword" value="<s:property value='keyWords.id_keyword'/>">
			<input type="hidden" id="name_keyword" name="keyWords.name_keyword" value="<s:property value='keyWords.name_keyword'/>">
			<input type="hidden" id="outPath_keyword" name="keyWords.outPath_keyword" value="<s:property value='keyWords.outPath_keyword'/>">
			<input type="hidden" id="status_keyword" name="keyWords.status_keyword" value="<s:property value='keyWords.status_keyword'/>"/>
			<input type="hidden" id="pageNo_keyWords" name="keyWords.pageNo" value="<s:property value='keyWords.pageNo'/>">
			<input type="hidden" id="pageSize_keyWords" name="keyWords.pageSize" value="<s:property value='keyWords.pageSize'/>"/>
			<input type="hidden" name="cmsPage.publicType" id="publicType" value="<s:property value='cmsPage.publicType'/>" />
					<table width="98%" class="detail_table list_table" cellpadding="3" align="center">
			<tr> 
				<th colspan="2" align="left" class="edit_title">页面信息</th>
			</tr>
				<tr>
					<td><span>页面编号:</span><s:property value="cmsPageDetail.cmsPage.pageId" /> <input id="pageId" type="hidden" value="<s:property value="cmsPageDetail.cmsPage.pageId" />"/></td>
					<td><span>页面名称:</span><s:property value="cmsPageDetail.cmsPage.name" /></td>
				</tr>
				<tr>
					<td><span>页面标题:</span><s:property value="cmsPageDetail.cmsPage.title" /></td>
					<td><span>发布类型:</span>
						<s:if test="cmsPageDetail.cmsPage.publicType==0">人工发布</s:if>
						<s:if test="cmsPageDetail.cmsPage.publicType==1">定时发布</s:if>
						<s:if test="cmsPageDetail.cmsPage.publicType==2">动态页面</s:if>
						<s:if test="cmsPageDetail.cmsPage.publicType==3">其他</s:if>
						<s:if test="cmsPageDetail.cmsPage.publicType==4">详情页标准模板</s:if>
						<s:if test="cmsPageDetail.cmsPage.publicType==20">详情页秒杀模板</s:if>
						<s:if test="cmsPageDetail.cmsPage.publicType==22">详情页处方模板</s:if>
						<s:if test="cmsPageDetail.cmsPage.publicType==30">详情页预售模板</s:if>
						<s:if test="cmsPageDetail.cmsPage.publicType==5">搜索引擎页面</s:if>
						<s:if test="cmsPageDetail.cmsPage.publicType==6">供应商页面</s:if>
						<s:if test="cmsPageDetail.cmsPage.publicType==7">供应商动态页面</s:if>
						<s:if test="cmsPageDetail.cmsPage.publicType==8">搜索商动态页面</s:if>
						<s:if test="cmsPageDetail.cmsPage.publicType==9">动态专题页</s:if>
						<s:if test="cmsPageDetail.cmsPage.publicType==10">行业资讯</s:if>
						<s:if test="cmsPageDetail.cmsPage.publicType==11">本站公告</s:if>
						<s:if test="cmsPageDetail.cmsPage.publicType==12">健康知识</s:if>
						<s:if test="cmsPageDetail.cmsPage.publicType==13">抽奖页面</s:if>
					     <s:if test="cmsPageDetail.cmsPage.publicType==14">套餐页面</s:if>
					     <s:if test="cmsPageDetail.cmsPage.publicType==23">组方页面</s:if>
					 
					     <s:if test="cmsPageDetail.cmsPage.publicType==15">每周新话题</s:if>
					     <s:if test="cmsPageDetail.cmsPage.publicType==16">母婴百科</s:if>
					     <s:if test="cmsPageDetail.cmsPage.publicType==17">养生保健百科</s:if>
					     <s:if test="cmsPageDetail.cmsPage.publicType==18">美容个护百科</s:if>
					     <s:if test="cmsPageDetail.cmsPage.publicType==19">病症百科</s:if>
					     <s:if test="cmsPageDetail.cmsPage.publicType==21">排行页面</s:if>
					</td>
				</tr>
				<tr>
					<td><span>状态:</span>
					<s:if test="cmsPageDetail.cmsPage.status==0">未发布</s:if>
					<s:if test="cmsPageDetail.cmsPage.status==1">已发布</s:if>
					<s:if test="cmsPageDetail.cmsPage.status==2">已修改</s:if></td>
					<td><span>输出地址:</span><s:property value="cmsPageDetail.cmsPage.outputPath" /></td>
				</tr>
				<tr>
					<td><span>创建人:</span><s:property value="cmsPageDetail.user_Cre" /></td>
					<td><span>创建时间:</span><s:date name="cmsPageDetail.cmsPage.createDate"/> </td>
				</tr>
				<tr>
					<td><span>修改人:</span><s:property value="cmsPageDetail.user_Mod" /></td>
					<td><span>修改时间:</span><s:date name="cmsPageDetail.cmsPage.modifyDate"/></td>
				</tr>
				<s:if test="adminType==0 || cmsPage.publicType==9">
				<tr>
					<td><span>主题名称:</span><s:property value="cmsPage.theme" /></td>
				</tr>		
				</s:if>
				<tr>
					<td colspan="2"><span>已选择的窗口:<input type="button" value="继续选择" class="binkBtn" onclick="bindWindow(<s:property value="cmsPageDetail.cmsPage.pageId" />)"/>
					<input type="button" value="删除" class="binkBtn" onclick="delAll()"/></span></td>
				</tr>
			</table>
			<table width="98%" class="list_table" cellpadding="3" align="center">
				<tr>
					<th width="5%">
						<input type='checkbox' name='level'  onclick="checkAllpop(this,'levelId')">
					</th>
					<th>编号</th>
					<th>窗口名称</th>
					<th>窗口主题</th>
					<th>修改日期</th>
					<th>状态</th>
					<th>操作</th>
				</tr>
			<s:iterator id="custiterator" value="cmsPageDetail.page.dataList">
				<tr>
					<td width="5%">
							<input type="checkbox" name="levelId" id="levelId"
								value='<s:property value="windowId"/>' onclick="checkpromotion(this);">
					</td>
					<td><s:property value="windowId"/></td>
					<td><s:property value="name"/></td>
					<td><s:property value="theme"/></td>
					<td><s:date name="modifyDate" format="yyyy-MM-dd HH:mm:ss"/></td>
					<td><s:if test="status==0">有效</s:if><s:if test="status==1">无效</s:if></td>
					<td>
						<img title="绑定" style="cursor: pointer;" src="/etc/images/icon_select.png"   onclick='bink(<s:property value="windowId"/>)' />
						<img title="修改" style="cursor: pointer;" src="/etc/images/icon_modify.png"   onclick='editPage(<s:property value="windowId"/>)' />
						<img title="删除" style="cursor: pointer;" src="/etc/images/icon_delete.png"   onclick='deleteWindow(<s:property value="windowId"/>)' />
					</td>	
				</tr>
			</s:iterator>
			</table>
			<table class="page_table" width="98%" align="center" cellpadding="0"
				cellspacing="0" border="0">
				<tr>
					<td>
						<s:set name="form_name" value="'pageDetailForm'" scope="request"></s:set>
						<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
					</td>
				</tr>
			</table>
			<table width="95%" class="edit_table" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
			
				
				<tr> 
					<td width="20%" align="right"><font color="red">*</font>页面内容：</td>
					
					<td width="80%"> 
						当光标在编辑器中，按F11键切换全屏幕编辑。ESC可退出全屏幕编辑。
					</td>
				</tr>
				<tr> 
					<td colspan="2">
						<textarea   name="cmsPage.content" id="content" cols="100" rows="8" style="height:300px;"><s:property value="cmsPageDetail.cmsPage.content"/></textarea>
					</td>
				</tr>
				
			</table>
			<table width="60%"  class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0">
				<tr> 
					<td align="left">
						<input class="backBtn"  onclick="goBack(<s:property value="adminType"/>)" type="button" value="">
					</td>
					<td width="20%" align="center"></td>
				</tr>
			</table>
		</s:form>
						<!-- 消息提示页面 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
		</div>
	</body>
</html>

