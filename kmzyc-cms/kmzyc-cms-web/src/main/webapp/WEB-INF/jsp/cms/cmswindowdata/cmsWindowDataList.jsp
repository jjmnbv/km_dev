<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
	<head>
		<title>窗口数据管理</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		<script src="/etc/js/dialog.js"></script>
		<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
		<script type="text/javascript"  src="/etc/js/windowData.js"></script>
		<script type="text/javascript"  src="/etc/js/rowDisplay.js"></script>
		<script type="text/javascript">
		function gotoList(){
			var pageId = "";
			pageId = $("#pageId").val();
			var adminType = $("#adminType").val();
			var stylesId = "";
			stylesId = $("#stylesId").val();
			var parentwindowId = $("#parentwindowId").val();
			if(stylesId!=""){
				window.location.href="/cms/cmsPageAction_visualization.action?stylesId="+stylesId;
				return;
			}
			if(parentwindowId!=""){
				window.location.href="/cms/cmsWindowDataAction_queryPageList.action?windowId="+parentwindowId+"&pageId="+pageId;
			}else if(pageId!=""){
				location.href="/cms/cmsPageAction_pageDetail.action?pageId="+pageId+"&adminType="+adminType;
			}else{
				location.href="/cms/cmsWindowAction_queryWindowPage.action?adminType="+adminType;
			}
			
		}

        /** 关闭类目弹出层  **/
        function closeCategory(checkeds,windowId){
        	closeThis();
        	var pageId = $("#pageId").val();
        	var adminType = $("#adminType").val();
        	location.href="categoryAction_openCategoryList.action?checkeds="+checkeds+"&dataType=2&windowId="+windowId+"&pageId="+pageId+"&adminType="+adminType;
        }
        /** 关闭品牌弹出层  **/
        function closeProdBrand(checkeds,windowId){
        	closeThis();
        	var pageId = $("#pageId").val();
        	var adminType = $("#adminType").val();
        	location.href="prodBrandAction_openProdBrandList.action?checkeds="+checkeds+"&dataType=3&windowId="+windowId+"&pageId="+pageId+"&adminType="+adminType;
        }
        /** 关闭窗口弹出层  **/
        function closeWindow(checkeds,windowId){
        	closeThis();
        	var pageId = $("#pageId").val();
        	var adminType = $("#adminType").val();
        	location.href="cmsWindowAction_openWindowList.action?checkeds="+checkeds+"&dataType=4&windowId="+windowId+"&pageId="+pageId+"&adminType="+adminType;
        }
        /** 关闭活动弹出层  **/
        function  closeViewPromotion(checkeds,windowId){
        	closeThis();
        	var pageId = $("#pageId").val();
        	var adminType = $("#adminType").val();
        	location.href="viewPromotionAction_openViewPromotionList.action?checkeds="+checkeds+"&dataType=1&windowId="+windowId+"&pageId="+pageId+"&adminType="+adminType;
        }
        /** 关闭活动商品弹出层  **/
        function  closeViewPromotionProduct(checkeds,windowId){
        	closeThis();
        	var pageId = $("#pageId").val();
        	var adminType = $("#adminType").val();
        	location.href="viewPromotionProductAction_openViewPromotionProductList.action?checkeds="+checkeds+"&dataType=14&windowId="+windowId+"&pageId="+pageId+"&adminType="+adminType;
        }
        
        /** 关闭资讯类别弹出层  **/
        function  closeInformationType(checkeds,windowId){
        	closeThis();
        	var pageId = $("#pageId").val();
        	var adminType = $("#adminType").val();
        	location.href="InformationType_openInformationTypeList.action?checkeds="+checkeds+"&dataType=8&windowId="+windowId+"&pageId="+pageId+"&adminType="+adminType;
        }

		
        /** 关闭产品弹出层  **/
        function  closeViewProductInfo(checkeds,windowId){
        	closeThis();
        	var pageId = $("#pageId").val();
        	var adminType = $("#adminType").val();
        	location.href="viewProductInfoAction_openViewProductInfoList.action?checkeds="+checkeds+"&dataType=0&windowId="+windowId+"&pageId="+pageId+"&adminType="+adminType;
        }
        /** 关闭排自定义数据弹出层  **/
        function closeOpenUserDefinedDataInfo(windowId){
            closeThis();
            var pageId = $("#pageId").val();
            var adminType = $("#adminType").val();
            window.location.href="/cms/cmsWindowDataAction_queryPageList.action?windowId="+windowId+"&pageId="+pageId+"&adminType="+adminType;
        }
        /** 关闭排行榜产品数据弹出层  **/
        function closeRankingList(checkeds,windowId){
        	closeThis();
        	var pageId = $("#pageId").val();
        	var adminType = $("#adminType").val();
        	location.href="categoryAction_rankingList.action?checkeds="+checkeds+"&dataType=7&windowId="+windowId+"&pageId="+pageId+"&adminType="+adminType;
        }
        
           /** 关闭抽奖弹出层  **/
        function closeLottery(checkeds,windowId){
        	closeThis();
        	var pageId = $("#pageId").val();
        	var adminType = $("#adminType").val();
        	location.href="cmsLottery_openLotteryActinListt.action?checkeds="+checkeds+"&dataType=9&windowId="+windowId+"&pageId="+pageId+"&adminType="+adminType;
        }
          /** 关闭奖品弹出层  **/
        function closeLotteryPrize(checkeds,windowId){
        	closeThis();
        	var pageId = $("#pageId").val();
        	var adminType = $("#adminType").val();
        	location.href="cmsLotteryPrize_opLotteryPrizeActinList.action?checkeds="+checkeds+"&dataType=10&windowId="+windowId+"&pageId="+pageId+"&adminType="+adminType;
        }
        //修改自定义数据
        function updateByKey(windowDataId)
        {
        	var windowId=$("#windowId").val();
        	var pageId=$("#pageId").val();
        	var adminType = $("#adminType").val();
            dialog("修改自定义信息","iframe:/cms/cmsWindowDataAction_updateUserDefineDate.action?windowDataId="+windowDataId+"&windowId="+windowId+"&pageId="+pageId+"&adminType="+adminType,"700px","420px","iframe");
        }
      //跳转窗口绑定数据
 		function bink(id)
 		{
 			var adminType = $("#adminType").val();
 			var parentwindowId = $("#windowId").val();
 			var pageId = $("#pageId").val();
 			window.location.href="/cms/cmsWindowDataAction_queryPageList.action?windowId="+id+"&parentwindowId="+parentwindowId+"&pageId="+pageId+"&adminType="+adminType;
 		}
      
 		//跳转编辑页
 		function editPage(id)
 		{
 			//var pageNo=$("#pageNo").val();
			window.location.href="/cms/cmsWindowAction_gotoEditWin.action?windowId="+id;
			//pageForm.action="/cms/cmsWindowAction_gotoEditWin.action?windowId="+id; 
 			//pageForm.submit();
 		}
 		
        function updateSort(v){
            var $v = $(v);
            var sort = $v.val();
            var windowDataId = $v.attr("data_windowDataId");
            if(parseInt(sort)==sort)
            {
            	$.ajax({
            		   type: "POST",
            		   url: "cmsWindowDataAction_updateSort.action",
            		   data: "windowDataId="+windowDataId+"&sort="+sort,
            		   success: function(msg){
            		   }
            		});
            }else{
            	 var msg="<img src='../etc/images/tipMsg.png' style='vertical-align:middle;'/>请输入整型数据，用于排序!";
                 messageDialog("消息提示","text:"+msg,"300px","100px","text");
                 var timer_alert = setTimeout(function() {
     				messageCloseThis();
     			}, 2000);
      			v.focus();
            }
        }
		</script>
	</head>
	<body>
		<!-- 导航栏 -->
		<s:set name="parent_name" value="'基础功能'" scope="request" />
		<s:set name="name" value="'数据管理'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<div  style="height:90%;overflow-y:scroll; " >
		<s:form name="windowDataForm"   onsubmit=" return checkAllTextValid(this)" 
			action="/cms/cmsWindowDataAction_queryPageList.action" method="post">
			<s:token></s:token>
			<input type="hidden" name="adminType" id="adminType" value="<s:property value='adminType'/>">
			<input type="hidden" name="stylesId" id="stylesId" value="<s:property value='stylesId'/>">
			
			<!-- 查询条件 -->
		    <table width="98%" align="center" height="90" border="0"	class="content_table"
			cellpadding="0" cellspacing="0">
				<tr>
					<td width="60%" valign="middle" colspan="2">
				 <input class="backBtn"  onclick="gotoList()" type="button" value=""> 
						<input class="delBtn" type="button" value=""
							onclick="deleteSelected('windowDataIds');">
					</td>
				</tr>
				
				<tr width="100%" height="40">
					
					<td align="left" width="20%">
						数据类型:
						<select name="queryType">
							<option value="">全部</option>
							<option value="0" <s:if test="queryType==0">selected="selected"</s:if>>产品</option>
							<option value="1" <s:if test="queryType==1">selected="selected"</s:if>>活动</option>
							<option value="2" <s:if test="queryType==2">selected="selected"</s:if>>运营类目</option>
							<option value="3" <s:if test="queryType==3">selected="selected"</s:if>>品牌</option>
							<option value="4" <s:if test="queryType==4">selected="selected"</s:if>>窗口</option>
							<option value="5" <s:if test="queryType==5">selected="selected"</s:if>>动态数据</option>
							<option value="6" <s:if test="queryType==6">selected="selected"</s:if>>自定义数据</option>
							<option value="7" <s:if test="queryType==7">selected="selected"</s:if>>产品排行榜所需物理类目</option>
							<option value="8" <s:if test="queryType==8">selected="selected"</s:if>>资讯类别</option>
							<option value="9" <s:if test="queryType==9">selected="selected"</s:if>>抽奖活动</option>
							<option value="10" <s:if test="queryType==10">selected="selected"</s:if>>奖项</option>
						</select>
					</td>
					
					<td align="right" >
						<INPUT TYPE="submit" class="queryBtn" value="">
					</td>
				</tr>
			
			<tr>  
				<td colspan="4"  align="center" >
				    <input type="button" style="height: 30px;" value="添加产品" onClick="popUpViewProductInfo();"  />&nbsp;
					<input type="button" style="height: 30px;" value="添加活动" onClick="popUpViewPromotion();" />&nbsp;
					<input type="button" style="height: 30px;" value="添加活动商品" onClick="popUpViewPromotionProduct();" />&nbsp;
					<input type="button" style="height: 30px;" value="添加运营类目" onClick="popUpcategory();" />&nbsp;
					<input type="button" style="height: 30px;" value="添加品牌" onClick="popUpprodBrand();"/>&nbsp;
					<input type="button" style="height: 30px;" value="添加窗口" onClick="popUpcmsWindow();"  />&nbsp;
					<input type="button" style="height: 30px;" value="添加自定义数据" onClick="popUpUserdefine();"  />&nbsp;
					<input type="button" style="height: 30px;" value="添加产品排行榜所需物理类目" onClick="popUprankingList();"  />&nbsp;
					<input type="button" style="height: 30px;" value="添加资讯类别" onClick="popInformationType();"  />&nbsp;
					<!--  <input type="button" value="添加抽奖活动" onclick="popUpLottery();"  />&nbsp;
					<input type="button" value="添加奖项" onclick="popUpLotteryPrize();"  />&nbsp;-->
					<input type="hidden" id="windowId" name="windowId" value="<s:property value='windowId' />">
					<input type="hidden" id="pageId" name="pageId" value="<s:property value='pageId' />">
					<input type="hidden" id="parentwindowId" name="parentwindowId" value="<s:property value='parentwindowId' />">
					
				</td>
			</tr>
			</table>
			<!-- 数据列表区域 -->
			<table width="98%" class="list_table" cellpadding="3" align="center"
				cellspacing="0" border="1" id="list_table">
				<tr class="dataName">
					<th width="5%">
						<input type='checkbox' name='windowData'  onclick="checkAll(this,'windowDataIds');">
					</th>
					<th>数据名称</th>
					<th>自定义URL</th>
					<th>自定义数据</th>
					<th>数据类型</th>
					<th>排序</th>
					<th width="60">操作</th>
				</tr>
				<s:iterator id="custiterator" value="page.dataList">
					<tr class="dataTr">
						<td width="5%"><input type="checkbox" name="windowDataIds" value="<s:property value='windowDataId'/>"></td>
						<td><s:property value="dataName" /></td>
					    <td>
					    <s:if test="dataType==6">
							<s:property value="user_defined_url" />
						</s:if>
						</td>
						<td>
							<s:property value="remark" />
						</td>
						<!--  
						<td>
						<s:if test="dataType==10">
							  <s:property value="user_defined_name" />
						</s:if>
						</td>
						
						<td>
						<s:if test="dataType==10">
							   <s:property value="user_defined_url" />
						</s:if>
						</td>
						-->
						<td>
							<s:if test="dataType==0">产品</s:if>
							<s:elseif test="dataType==1">活动</s:elseif>
							<s:elseif test="dataType==2">运营类目</s:elseif>
							<s:elseif test="dataType==3">品牌</s:elseif>
							<s:elseif test="dataType==4">窗口</s:elseif>
							<s:elseif test="dataType==5">动态数据</s:elseif>
							<s:elseif test="dataType==6">自定义数据</s:elseif>
							<s:elseif test="dataType==7">产品排行榜所需物理类目</s:elseif>
							<s:elseif test="dataType==8">资讯类别</s:elseif>
							<s:elseif test="dataType==9">抽奖活动</s:elseif>
							<s:elseif test="dataType==10">奖项</s:elseif>
							<input type="hidden" id="dataType" name="dataTypes" value="<s:property value='dataType' />"/>
						</td>
						<td>
							<input style="width: 40px;" type="text" data_windowDataId="<s:property value='windowDataId' />" onBlur="updateSort(this);" value="<s:property value='sort' />" />
						</td>
						
						<td>
						<s:if test="dataType==6">
							<img title="修改" style="cursor: pointer;" src="/etc/images/icon_modify.png"   onclick="updateByKey(<s:property value='windowDataId'/>)" />
						</s:if>
						<s:if test="dataType==4">
							<img title="绑定" style="cursor: pointer;" src="/etc/images/icon_select.png"   onclick='bink(<s:property value="windowId"/>)' />
							<img title="修改" style="cursor: pointer;" src="/etc/images/icon_modify.png"   onclick='editPage(<s:property value="windowId"/>)' />
						</s:if>
						     <img title="删除" style="cursor: pointer;" src="/etc/images/icon_delete.png"   onclick="deleteByKey(<s:property value='windowDataId'/>)" />
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

