<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
	<head>
		<title>页面列表</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet"
			type="text/css" />
		<script type="text/javascript" src="/etc/js/jquery-latest.pack.js"></script>
		<script src="/etc/js/dialog.js"></script>
		<script type="text/javascript" src="/etc/js/pageCommon.js"></script>
		<script type="text/javascript" src="/etc/js/checkeds.js"></script>
		<script type="text/javascript" src="/etc/js/rowDisplay.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.validate.js"></script>
		<script type="text/javascript" src="/etc/js/messages_cn.js"></script>

		<script type="text/javascript">
		
		$(document).ready(function(){
			var timer_alert = setTimeout(function() {
				messageCloseThis();
			}, 2000);
			
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
        	
        	$("#pageForm").validate({
		         rules: {
		         		"keyWords.id_keyword":{keywordIdChar:true,maxlength:10}
			        	},
			     success: function (label){
			            label.removeClass("checked").addClass("checked");
			            }
		          });
	   });
	   
		</script>

	</head>
	<body>
		<!-- 导航栏 -->
		<s:set name="parent_name" value="'基础功能'" scope="request" />
		<s:set name="name" value="'页面列表'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<div style="height: 90%; overflow-y: scroll;">
			<s:form class="pageForm" name="pageForm" id="pageForm"
				onsubmit="return checkAllTextValid(this)"
				action="/cms/cmsPageAction_queryForPage.action" method="post">
				<!-- hidden -->
				<input type="hidden" id="pageNo_keyWords" name="keyWords.pageNo"
					value="<s:property value='keyWords.pageNo'/>">
				<input type="hidden" id="pageSize_keyWords" name="keyWords.pageSize"
					value="<s:property value='keyWords.pageSize'/>" />
				<table width="98%" align="center" height="90" border="0"
					class="content_table" cellpadding="0" cellspacing="0">
					<tr>
						<td width="60%" valign="middle" colspan="2">
							<!-- hidden -->
							<input type="hidden" name="checkeds" id="checkeds"
								value="<s:property value='checkeds'/>" />
							<!-- /hidden -->

							
						</td>
					</tr>
					<tr>
						<s:if test="adminType==0">
							<input name="adminType" id="adminType" type="hidden" value="0" />
						</s:if>
						<td >
							页面编号:
							<input type="text" id="id_keyword" name="keyWords.id_keyword"
								value="<s:property value='keyWords.id_keyword'/>">
						</td>
						<td >
							页面名称:
							<input type="text" id="name_keyword" name="keyWords.name_keyword"
								value="<s:property value='keyWords.name_keyword'/>">
						</td>
					</tr>
					<tr>
						<td>
							输出路径:
							<input type="text" id="outPath_keyword"
								name="keyWords.outPath_keyword"
								value="<s:property value='keyWords.outPath_keyword'/>">
						</td>
						<td >
							发布状态:
							<select name="keyWords.status_keyword" id="status_keyword">
								<option value=""
									<s:if test="keyWords.status_keyword==''">selected="selected"</s:if>>
									全部
								</option>
								<option value="0"
									<s:if test="keyWords.status_keyword==0">selected="selected"</s:if>>
									未发布
								</option>
								<option value="1"
									<s:if test="keyWords.status_keyword==1">selected="selected"</s:if>>
									已发布
								</option>
								<option value="2"
									<s:if test="keyWords.status_keyword==2">selected="selected"</s:if>>
									已修改
								</option>
							</select>
						</td>
						<td>发布类型：
							<select name="cmsPage.publicType" id="publicType">
							<option value="" <s:if test="cmsPage.publicType==null">selected="selected"</s:if>>全部</option>
					        <option value="0" <s:if test="cmsPage.publicType==0">selected="selected"</s:if>>人工发布</option>
					        <option value="1" <s:if test="cmsPage.publicType==1">selected="selected"</s:if>>定时发布</option>
					        <option value="2" <s:if test="cmsPage.publicType==2">selected="selected"</s:if>>动态页面</option>
					        <option value="3" <s:if test="cmsPage.publicType==3">selected="selected"</s:if>>邮件发布</option>
					        <option value="4" <s:if test="cmsPage.publicType==4">selected="selected"</s:if>>详情页标准模板</option>
					        <option value="20" <s:if test="cmsPage.publicType==20">selected="selected"</s:if>>详情页秒杀模板</option>
					        <option value="22" <s:if test="cmsPage.publicType==22">selected="selected"</s:if>>详情页处方模板</option>
					        <option value="30" <s:if test="cmsPage.publicType==30">selected="selected"</s:if>>详情页预售模板</option>
					        <option value="5" <s:if test="cmsPage.publicType==5">selected="selected"</s:if>>搜索引擎页面</option>
					        <option value="6" <s:if test="cmsPage.publicType==6">selected="selected"</s:if>>供应商页面</option>
					        <option value="7" <s:if test="cmsPage.publicType==7">selected="selected"</s:if>>供应商动态页面</option>
					        <option value="8" <s:if test="cmsPage.publicType==8">selected="selected"</s:if>>搜索商动态页面</option>
					        <option value="9" <s:if test="cmsPage.publicType==9">selected="selected"</s:if>>动态专题页</option>
					        <option value="10" <s:if test="cmsPage.publicType==10">selected="selected"</s:if>>行业资讯</option>
					        <option value="11" <s:if test="cmsPage.publicType==11">selected="selected"</s:if>>本站公告</option>
					        <option value="12" <s:if test="cmsPage.publicType==12">selected="selected"</s:if>>健康知识</option>
					        <option value="13" <s:if test="cmsPage.publicType==13">selected="selected"</s:if>>抽奖页面</option>
					        <option value="14" <s:if test="cmsPage.publicType==14">selected="selected"</s:if>>套餐页面</option>
					        <option value="23" <s:if test="cmsPage.publicType==23">selected="selected"</s:if>>组方页面</option>
					        <option value="15" <s:if test="cmsPage.publicType==15">selected="selected"</s:if>>每周新话题</option>
					        <option value="16" <s:if test="cmsPage.publicType==16">selected="selected"</s:if>>母婴百科</option>
					        <option value="17" <s:if test="cmsPage.publicType==17">selected="selected"</s:if>>养生保健百科</option>
					        <option value="18" <s:if test="cmsPage.publicType==18">selected="selected"</s:if>>美容个护百科</option>
					        <option value="19" <s:if test="cmsPage.publicType==19">selected="selected"</s:if>>病症百科</option>
					        <option value="21" <s:if test="cmsPage.publicType==21">selected="selected"</s:if>>排行页面</option>   
					        <option value="24" <s:if test="cmsPage.publicType==24">selected="selected"</s:if>>活动中心最新公告</option>
					        <option value="25" <s:if test="cmsPage.publicType==25">selected="selected"</s:if>>活动中心活动细则</option>
					        <option value="26" <s:if test="cmsPage.publicType==26">selected="selected"</s:if>>活动中心新手帮助</option>
					        <option value="27" <s:if test="cmsPage.publicType==27">selected="selected"</s:if>>活动中心活动报名</option>
					        <option value="28" <s:if test="cmsPage.publicType==28">selected="selected"</s:if>>活动中心审核过程</option>
					        <option value="29" <s:if test="cmsPage.publicType==29">selected="selected"</s:if>>活动中心活动设置</option>
					        </select>
						</td>
						<td align="right">
							<input type="submit" class="queryBtn" value="" />
                            <INPUT class="addBtn"
								<s:if test="cmsPage.publicType==9">id="admin0"</s:if>
								<s:else>id="admin1"</s:else> TYPE="button" value="" />
							<!-- <input type="hidden" name="cmsPage.publicType" id="publicType"
								value="<s:property value='cmsPage.publicType'/>" /> -->
							<s:if test="adminType!=0 && cmsPage.publicType!=9">
								<input class="delBtn" type="button" value="" />
								<!-- 
								<input type="button"
									value='<s:if test="timingPublishFlag==0">关闭页面更新定时器 </s:if><s:else>开启页面更新定时器</s:else>'
									onclick="timing();" />
									
								<input type="button" value="立即定时发布" onclick="publish();" /> -->
							</s:if>
							<s:elseif test="cmsPage.publicType == 9">
								<input class="delBtn" type="button" value="" />
							</s:elseif>
							<input type="button" class='<s:if test="adminType!=0 && cmsPage.publicType!=9">allPublishadmin</s:if><s:elseif test="cmsPage.publicType==9">allPublishtheme</s:elseif><s:else>allPublish</s:else> btn-custom' value="多选发布" />
						</td>
					</tr>
				</table>
				<table width="98%" class="list_table" cellpadding="3" align="center">
					<tr>
						<th width="5%">
							<input type='checkbox' name='level'
								onclick="checkAllpop(this,'levelId')">
						</th>
						<th width="5%">
							编号
						</th>
						<th width="10%">
							页面名称
						</th>
						<th width="10%">
							页面标题
						</th>
						<th width="10%">
							输出路径
						</th>
						<th width="10%">
							主题名称
						</th>
						<th width="5%">
							发布类型
						</th>
						<th width="5%">
							发布状态
						</th>
						<th width="10%">
						         修改日期
						</th>
						<th width="5%">
						         修改人
						</th>
						<th width="15%">
							操作
						</th>
					</tr>
					<s:iterator id="custiterator" value="page.dataList">
						<tr>
							<td width="5%">
								<input type="checkbox" name="levelId" id="levelId"
									value='<s:property value="pageId"/>'
									onclick="checkpromotionId(this);">
							</td>
							<td>
								<s:property value="pageId" />
							</td>
							<td>
								<s:property value="name" />
							</td>
							<td>
								<s:property value="title" />
							</td>
							<td>
								<s:property value="outputPath" />
							</td>
							<td>
								<s:property value="theme" />
							</td>
							<td>
								<s:if test="publicType==0">人工发布</s:if>
								<s:if test="publicType==1">定时发布</s:if>
								<s:if test="publicType==2">动态页面</s:if>
								<s:if test="publicType==3">邮件发布</s:if>
								<s:if test="publicType==4">详情页标准模板</s:if>
								<s:if test="publicType==20">详情页秒杀模板</s:if>
								<s:if test="publicType==22">详情页处方模板</s:if>
								<s:if test="publicType==30">详情页预售模板</s:if>
								
								<s:if test="publicType==5">搜索引擎页面</s:if>
								<s:if test="publicType==6">供应商页面</s:if>
								<s:if test="publicType==7">供应商动态页面</s:if>
								<s:if test="publicType==8">搜索商动态页面</s:if>
								<s:if test="publicType==9">动态专题页</s:if>
								<s:if test="publicType==10">资讯发布</s:if>
								<s:if test="publicType==11">本站公告</s:if>
								<s:if test="publicType==12">健康知识</s:if>
								<s:if test="publicType==13">抽奖页面</s:if>
								<s:if test="publicType==14">套餐页面</s:if>	
								<s:if test="publicType==23">组方页面</s:if>		
							    <s:if test="publicType==21">排行页面</s:if>
							    <s:if test="publicType==24">活动中心最新公告</s:if>
								<s:if test="publicType==25">活动中心活动细则</s:if>
								<s:if test="publicType==26">活动中心新手帮助</s:if>
								<s:if test="publicType==27">活动中心活动报名</s:if>	
								<s:if test="publicType==28">活动中心审核过程</s:if>		
							    <s:if test="publicType==29">活动中心活动设置</s:if>	
							</td>

							<td>
								<s:if test="status==0 ">未发布</s:if>
								<s:if test="status==1">已发布</s:if>
								<s:if test="status==2">
									<div style="color: #F00">
										已修改
									</div>
								</s:if>
							</td>
							
							<td><s:date name="modifyDate" format="yyyy-MM-dd HH:mm:ss"/>
							
								</td>
								<td>
									<s:property value="sysUserMap[modified]"/>
								</td>
							
							<td>

								<img title="详情" style="cursor: pointer;"
									src="/etc/images/icon_detail.png"
									<s:if test="adminType==0">onclick='pageDetail(<s:property value="pageId"/>)'</s:if>
									<s:else>onclick='adminPageDetail(<s:property value="pageId"/>)'</s:else> />
								<img title="修改" style="cursor: pointer;"
									src="/etc/images/icon_modify.png"
									<s:if test="adminType==0 && cmsPage.publicType != 9">onclick='editPage(<s:property value="pageId"/>)'</s:if>
									<s:elseif test="cmsPage.publicType == 9">onclick='themeEditPage(<s:property value="pageId"/>)'</s:elseif>
									<s:else>onclick='adminEditPage(<s:property value="pageId"/>)'</s:else> />
								<img title="预览" style="cursor: pointer;"
									src="/etc/images/icon_preview.png"
									onclick='preview(<s:property value="pageId"/>)' />
								<img title="发布" style="cursor: pointer;"
									src="/etc/images/icon_publish.png"
									<s:if test="adminType==0 && cmsPage.publicType != 9">onclick='pagePublish(<s:property value="pageId"/>)'</s:if>
									<s:elseif test="cmsPage.publicType == 9">onclick='themePublish(<s:property value="pageId"/>)'</s:elseif>
									<s:else>onclick='adminPublish(<s:property value="pageId"/>)'</s:else> />
								<s:if test="cmsPage.publicType!=9">
									<img title="绑定" style="cursor: pointer;"
										src="/etc/images/icon_select.png"
										onclick='bindWindow(<s:property value="pageId"/>)' />
								</s:if>
								
								<img title="可视化数据" style="cursor: pointer;"
									src="/etc/images/tizheng.png"
									onclick='visualizationdata("<s:property value="pageId"/>");' />
								
								<s:if test="adminType==0 && cmsPage.publicType!=9"></s:if>
								<s:else>

									<img title="删除" style="cursor: pointer;"
										src="/etc/images/icon_delete.png"
										onclick='deletePage(<s:property value="pageId"/>)' />
								</s:else>
								
								
								
							</td>
						</tr>
					</s:iterator>
				</table>
				<table class="page_table" width="98%" align="center" cellpadding="0"
					cellspacing="0" border="0">
					<tr>
						<td>
							<s:set name="form_name" value="'pageForm'" scope="request"></s:set>
							<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
						</td>
					</tr>
				</table>
			</s:form>
			<!-- 消息提示页面 -->
			<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
		</div>
		<script type="text/javascript">
		var pageForm= window.document.getElementById("pageForm");
		$(function(){
			//跳转到添加页面
	   		$("#admin0").click(function(){
		   		pageForm.action="/cms/cmsPageAction_gotoAddTheme.action"; 
 				pageForm.submit();
	   		});
	   		$("#admin1").click(function(){
 				pageForm.action="/cms/cmsPageAction_gotoAddPage.action"; 
 				pageForm.submit();
	   		});
	   		
	   		//页面批量删除
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
					window.location.href="/cms/cmsPageAction_delAllPage.action?checkeds="+checks+"&cmsPage.publicType="+$("#publicType").val();
				}
	   		});
	   		
	   		
	   		
	   		//页面批量发布
	   		$(".allPublish").click(function(){
	   			var checks=$("#checkeds").val();
	   			if(checks=='')
	   			{
	   				var msg="<img src='../etc/images/tipMsg.png' style='vertical-align:middle'/>请选择!";
		            messageDialog("消息提示","text:"+msg ,"300px","102px","text");  
	   				return ;
	   			}
	   			var ok=confirm("确定发布所选吗？！");
	   			if(ok==false)
	   			{
	   				return ;
	   			}
	   			else
	   			{
		   			var url = "/cms/cmsPageAction_parseAll.action?checkeds="+checks+"&adminType=0";
	   				if($("#publicType").val()==9){
	   					url+="&cmsPage.publicType="+$("#publicType").val();
		   			}
					window.location.href=url;
				}
	   		});

	   	//页面批量发布
	   		$(".allPublishtheme").click(function(){
	   			var checks=$("#checkeds").val();
	   			if(checks=='')
	   			{
	   				var msg="<img src='../etc/images/tipMsg.png' style='vertical-align:middle'/>请选择!";
		            messageDialog("消息提示","text:"+msg ,"300px","102px","text");  
	   				return ;
	   			}
	   			var ok=confirm("确定发布所选吗？！");
	   			if(ok==false)
	   			{
	   				return ;
	   			}
	   			else
	   			{
		   			var url = "/cms/cmsPageAction_parseAll.action?checkeds="+checks+"&cmsPage.publicType="+$("#publicType").val();;
					window.location.href=url;
				}
	   		});

	   	//页面批量发布
	   		$(".allPublishadmin").click(function(){
	   			var checks=$("#checkeds").val();
	   			if(checks=='')
	   			{
	   				var msg="<img src='../etc/images/tipMsg.png' style='vertical-align:middle'/>请选择!";
		            messageDialog("消息提示","text:"+msg ,"300px","102px","text");  
	   				return ;
	   			}
	   			var ok=confirm("确定发布所选吗？！");
	   			if(ok==false)
	   			{
	   				return ;
	   			}
	   			else
	   			{
					window.location.href="/cms/cmsPageAction_parseAll.action?checkeds="+checks;
				}
	   		});
	   		
	   });
	   
	   
	   
	   
	   
	   
		//关闭弹出层
		function closeOpenUserInfo(result){
            closeThis();
            if(result==1)
			{
    			var url ="/cms/cmsPageAction_queryPage.action";
            	if($("#adminType").val()!=null && $("#adminType").val()!="undefined" && $("#adminType").val()!=""){
     	 			url = url+"?adminType="+$("#adminType").val()
     	 	 	}
				window.location.href=url;
			}
        }
        //返回
		function gotoList(){
  			 history.back();
 		}
 		//跳转编辑页
 		function editPage(pageId)
 		{
 			pageForm.action="/cms/cmsPageAction_gotoEditPage.action?pageId="+pageId;
 			pageForm.submit();
 		}

 		//跳转编辑页
 		function themeEditPage(pageId)
 		{
 			pageForm.action="/cms/cmsPageAction_gotoThemeEditPage.action?pageId="+pageId;
 			pageForm.submit();
 		}
 		
 		//定时器开发事件
 		function timing(){
 		//   alert("aaaaaaa");
 			pageForm.action="/cms/cmsPageAction_updateTimingFlag.action";
 			pageForm.submit();
 		
 		}
 		function adminEditPage(pageId)
 		{
 			pageForm.action="/cms/cmsPageAction_gotoEditPage.action?pageId="+pageId;
 			pageForm.submit();
 		}
 		//单个页面删除
 		function deletePage(id)
 		{
 			var ok=confirm("确定删除吗？删除后将不能恢复！");
	   		if(ok==false)
	   		{
	   			return ;
	   		}
	   		else
	   		{
				window.location.href="/cms/cmsPageAction_delPage.action?pageId="+id+"&cmsPage.publicType="+$("#publicType").val();
			}
 		}
 		//跳转绑定页面
 		function bindWindow(id)
 		{
 	 		var url = "iframe:/cms/cmsPageAction_gotoBindWindow.action?pageId="+id+"&cmsPage.publicType="+$("#publicType").val();
 	 		if($("#adminType").val()!=null && $("#adminType").val()!="undefined" && $("#adminType").val()!=""){
 	 			url = url+"&adminType="+$("#adminType").val();
 	 	 	}
 			dialog("选择窗口", url,"900px","530px","iframe");  
 		}
 		//详情页
 		function pageDetail(pageId)
 		{
 			var id_keyword=$("#id_keyword").val(); 
	   		var name_keyword=$("#name_keyword").val();
	   		var outPath_keyword=$("#outPath_keyword").val();
	   		var status_keyword=$("#status_keyword").val();
	   		var pageNo=$("#pageNo_keyWords").val();
	   		var pageSize=$("#pageSize_keyWords").val();
	   		window.location.href="/cms/cmsPageAction_pageDetail.action?adminType=0&pageId="+pageId+"&keyWords.id_keyword="+id_keyword
	   				+"&keyWords.name_keyword="+name_keyword+"&keyWords.outPath_keyword="+outPath_keyword+"&keyWords.status_keyword="
	   				+status_keyword+"&keyWords.pageNo="+pageNo+"&keyWords.pageSize="+pageSize+"&cmsPage.publicType="+$("#publicType").val();
 		}
 		function adminPageDetail(pageId)
 		{
 			var id_keyword=$("#id_keyword").val(); 
	   		var name_keyword=$("#name_keyword").val();
	   		var outPath_keyword=$("#outPath_keyword").val();
	   		var status_keyword=$("#status_keyword").val();
	   		var pageNo=$("#pageNo_keyWords").val();
	   		var pageSize=$("#pageSize_keyWords").val();
	   		window.location.href="/cms/cmsPageAction_pageDetail.action?pageId="+pageId+"&keyWords.id_keyword="+id_keyword
	   				+"&keyWords.name_keyword="+name_keyword+"&keyWords.outPath_keyword="+outPath_keyword+"&keyWords.status_keyword="
	   				+status_keyword+"&keyWords.pageNo="+pageNo+"&keyWords.pageSize="+pageSize+"&cmsPage.publicType="+$("#publicType").val();
 		}
 		//页面发布
 		function pagePublish(id)
 		{
 			window.location.href="/cms/cmsPageAction_pagePublic.action?adminType=0&cmsPage.pageId="+id;
 		}

 		function themePublish(id)
 		{
 			window.location.href="/cms/cmsPageAction_pagePublic.action?cmsPage.pageId="+id+"&cmsPage.publicType="+$("#publicType").val();
 		}
 		function adminPublish(id)
 		{
 			window.location.href="/cms/cmsPageAction_pagePublic.action?cmsPage.pageId="+id;
 		}
 		//页面预览
 		function preview(id)
 		{
 			window.open("/cms/pagePublish_preview.action?cmsPage.pageId="+id);
 			
 		}

 		function visualization(id)
 		{
 			window.location.href="/cms/cmsPageAction_visualization.action?stylesId="+id;
 		}

 		function visualizationdata(id)
 		{
 			window.open("/cms/pagePublish_preVisualization.action?cmsPage.pageId="+id);
 		}
 		
 		//提示信息显示
 		function showTips(msgTips)
 		{
 			var msg="<img src='../etc/images/tipMsg.png' style='vertical-align:middle'/>"+msgTips;
		    messageDialog("消息提示","text:"+msg ,"300px","102px","text");
 		}
 		function publish(){
 			window.location.href="/cms/cmsPageAction_publish.action";
 	 	}
</script>
	</body>
</html>

