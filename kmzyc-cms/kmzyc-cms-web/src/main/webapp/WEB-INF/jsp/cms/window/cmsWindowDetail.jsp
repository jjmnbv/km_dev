<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
	<head>
		<title>窗口详情</title>
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
	 
 		function gotoList(){
		   	  //var pageNo=$("#pageNo").val();
			  //location.href="/cms/cmsWindowAction_queryWindowByKey.action";
			//  var pageForm= window.document.getElementById("windowDetailForm");
			//  pageForm.action="/cms/cmsWindowAction_queryWindowPage.action"; 
		 	//  pageForm.submit();
		 	var id_keyword=$("#id_keyword").val(); 
	   		var name_keyword=$("#name_keyword").val();
	   		var type_keyword=$("#type_keyword").val();
	   		var theme_keyword=$("#theme_keyword").val();
	   		var status_keyword=$("#status_keyword").val();
	   		var pageNo=$("#pageNo_keyWords").val();
	   		var pageSize=$("#pageSize_keyWords").val();
	   		var adminType = $("#adminType").val();
	   		window.location.href="/cms/cmsWindowAction_queryWindowPage.action?keyWords.id_keyword="+id_keyword
	   				+"&keyWords.name_keyword="+name_keyword+"&keyWords.type_keyword="+type_keyword+"&keyWords.status_keyword="
	   				+status_keyword+"&keyWords.pageNo="+pageNo+"&keyWords.pageSize="+pageSize+"&keyWords.theme_keyword="+theme_keyword+
	   				"&adminType="+adminType;
			}
		function closeOpenUserInfo(accountId,account,name){
            closeThis();
            var id=$("#cmsPageId").val();
            window.location.href="/cms/cmsPageAction_pageDetail.action?pageId="+id;
        }
        /** 删除数据信息  **/
    function deleteSelected(id){
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
                  document.windowDetailForm.action="/cms/cmsWindowDataAction_deleteDatas.action";
                  document.windowDetailForm.submit();
         }
    }
   
    
    /** 单条删除客户等级信息  **/
    function  deleteByKey(id){
         if(confirm("是否确认删除? ")==true){
           var windowId=$("#windowId").val();
           var adminType = $("#adminType").val();
           location.href="/cms/cmsWindowDataAction_deleteData.action?dataId="+id+"&windowId="+windowId+"&backType=1"+"&adminType="+adminType;
         }
    }

    function updateSort(v){
        var $v = $(v);
        var sort = $v.val();
        var windowDataId = $v.attr("data_windowDataId");
        var adminType = $("#adminType").val();
        if(parseInt(sort)==sort)
        {
        	$.ajax({
        		   type: "POST",
        		   url: "cmsWindowDataAction_updateSort.action",
        		   data: "windowDataId="+windowDataId+"&sort="+sort+"&adminType="+adminType,
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
		<s:set name="name" value="'窗口详情'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<div  style="height:90%;" >
		<s:form class="windowDetailForm" id="windowDetailForm" name="windowDetailForm" onsubmit="return checkAllTextValid(this)" action="/cms/cmsWindowAction_windowDetail.action" method="post">
			<s:token></s:token>
			<input type="hidden" name="windowId" id="windowId" value="<s:property value="cmsPageDetail.cmsWindow.windowId" />"/>
			<input type="hidden" name="checkeds" id="checkeds" value="<s:property value="checkeds"/>"/>
			<input type="hidden" name="backType" id="backType" value="1"/>
			<input type="hidden" name="pageNo" id="pageNo" value="<s:property value="pageNo"/>"/>
			<!-- keyWords -->
			<input type="hidden" name="keyWords.id_keyword" id="id_keyword" value="<s:property value='keyWords.id_keyword'/>">
			<input type="hidden" name="keyWords.name_keyword" id="name_keyword" value="<s:property value='keyWords.name_keyword'/>">
			<input type="hidden" name="keyWords.theme_keyword" id="theme_keyword" value="<s:property value='keyWords.theme_keyword'/>">
			<input type="hidden" name="keyWords.status_keyword" id="status_keyword" value="<s:property value='keyWords.status_keyword'/>"/>
			<input type="hidden" name="keyWords.type_keyword" id="type_keyword" value="<s:property value='keyWords.type_keyword'/>">
			<input type="hidden" id="pageNo_keyWords" name="keyWords.pageNo" value="<s:property value='keyWords.pageNo'/>">
			<input type="hidden" id="pageSize_keyWords" name="keyWords.pageSize" value="<s:property value='keyWords.pageSize'/>"/>
			<input type="hidden" name="adminType" id="adminType" value="<s:property value='adminType'/>">
		<table width="98%" class="detail_table list_table" cellpadding="3" align="center">
			<tr> 
				<th colspan="2" align="left" class="edit_title">窗口信息</th>
			</tr>
					
				<tr>
					<td><span>窗口编号:</span><s:property value="cmsPageDetail.cmsWindow.windowId" /> </td>
					<td><span>模板:</span><s:property value="cmsPageDetail.cmsWindow.templateId" /> </td>
				</tr>
				<tr>
					<td><span>窗口名称:</span><s:property value="cmsPageDetail.cmsWindow.name" /></td>
					<td><span>窗口主题:</span><s:property value="cmsPageDetail.cmsWindow.theme" /></td>
				</tr>
				<tr>
					<td><span>状态:</span><s:if test="cmsPageDetail.cmsWindow.status==0">有效</s:if><s:if test="cmsPageDetail.cmsWindow.status==1">无效</s:if></td>
					<td><span>数据类型:</span><s:if test="cmsPageDetail.cmsWindow.paramsType==0">系统传参</s:if><s:if test="cmsPageDetail.cmsWindow.paramsType==1">人工绑定</s:if></td>
				</tr>
				<tr>
					<td><span>创建人:</span><s:property value="cmsPageDetail.user_Cre" /></td>
					<td><span>创建时间:</span><s:date name="cmsPageDetail.cmsWindow.createDate"/> </td>
				</tr>
				<tr>
					<td><span>修改人:</span><s:property value="cmsPageDetail.user_Mod" /></td>
					<td><span>修改时间:</span><s:date name="cmsPageDetail.cmsWindow.modifyDate"/></td>
				</tr>
				<tr> 
					<td colspan="2"><span>备注:</span><s:property value="cmsPageDetail.cmsWindow.remark" /></td>
				</tr>
				<tr>
					<td colspan="2"><span>已选择的的数据:
					<input type="button" value="删除" class="binkBtn" onclick="deleteSelected('windowDataIds')"/></span></td>
				</tr>
				<tr>
					<td align="left" width="20%">
						数据类型:
						<select name="queryType">
						<s:if test="queryType==0">
							<option value="">全部</option>
							<option value="0" selected="selected">产品</option>
							<option value="1">活动</option>
							<option value="2">类目</option>
							<option value="3">品牌</option>
							<option value="4">窗口</option>
							<option value="5">动态数据</option>
							<option value="6">自定义数据</option>
							<option value="7">产品排行榜所需类目</option>
						</s:if>
						<s:elseif test="queryType==1">
							<option value="">全部</option>
							<option value="0">产品</option>
							<option value="1" selected="selected">活动</option>
							<option value="2">类目</option>
							<option value="3">品牌</option>
							<option value="4">窗口</option>
							<option value="5">动态数据</option>
							<option value="6">自定义数据</option>
							<option value="7">产品排行榜所需类目</option>
						</s:elseif>
						
						<s:elseif test="queryType==2">
							<option value="">全部</option>
							<option value="0">产品</option>
							<option value="1">活动</option>
							<option value="2" selected="selected">类目</option>
							<option value="3">品牌</option>
							<option value="4">窗口</option>
							<option value="5">动态数据</option>
							<option value="6">自定义数据</option>
							<option value="7">产品排行榜所需类目</option>
						</s:elseif>
						<s:elseif test="queryType==3">
							<option value="">全部</option>
							<option value="0">产品</option>
							<option value="1">活动</option>
							<option value="2">类目</option>
							<option value="3" selected="selected">品牌</option>
							<option value="4">窗口</option>
							<option value="5">动态数据</option>
							<option value="6">自定义数据</option>
							<option value="7">产品排行榜所需类目</option>
						</s:elseif>
						<s:elseif test="queryType==4">
							<option value="">全部</option>
							<option value="0">产品</option>
							<option value="1">活动</option>
							<option value="2">类目</option>
							<option value="3">品牌</option>
							<option value="4" selected="selected">窗口</option>
							<option value="5">动态数据</option>
							<option value="6">自定义数据</option>
							<option value="7">产品排行榜所需类目</option>
						</s:elseif>
						<s:elseif test="queryType==5">
							<option value="">全部</option>
							<option value="0">产品</option>
							<option value="1">活动</option>
							<option value="2">类目</option>
							<option value="3">品牌</option>
							<option value="4">窗口</option>
							<option value="5" selected="selected">动态数据</option>
							<option value="6">自定义数据</option>
							<option value="7">产品排行榜所需类目</option>
						</s:elseif>
						<s:elseif test="queryType==6">
							<option value="">全部</option>
							<option value="0">产品</option>
							<option value="1">活动</option>
							<option value="2">类目</option>
							<option value="3">品牌</option>
							<option value="4">窗口</option>
							<option value="5">动态数据</option>
							<option value="6" selected="selected">自定义数据</option>
							<option value="7">产品排行榜所需类目</option>
						</s:elseif>
						<s:elseif test="queryType==7">
							<option value="">全部</option>
							<option value="0">产品</option>
							<option value="1">活动</option>
							<option value="2">类目</option>
							<option value="3">品牌</option>
							<option value="4">窗口</option>
							<option value="5">动态数据</option>
							<option value="6">自定义数据</option>
							<option value="7" selected="selected">产品排行榜所需类目</option>
						</s:elseif>
						
						<s:else>
							<option value="" selected="selected">全部</option>
							<option value="0">产品</option>
							<option value="1">活动</option>
							<option value="2">类目</option>
							<option value="3">品牌</option>
							<option value="4">窗口</option>
							<option value="5">动态数据</option>
							<option value="6">自定义数据</option>
							<option value="7">产品排行榜所需类目</option>
						</s:else>
							
						</select>
					</td>
					<td align="right" >
						<INPUT TYPE="submit" class="queryBtn" value="">
					</td>
				</tr>
			</table>
			<table width="98%" class="list_table" cellpadding="3" align="center">
				<tr>
					<th width="5%">
						<input type='checkbox' name='level'  onclick="checkAllpop(this,'windowDataIds')">
					</th>
					<th>编号</th>
					<th>数据名称</th>
					<th>数据类型</th>
					<th>排序</th>
					<th>操作</th>
				</tr>
			<s:iterator id="custiterator" value="cmsPageDetail.page.dataList">
				<tr>
					<td width="5%">
							<input type="checkbox" name="windowDataIds" id="levelId"
								value='<s:property value="windowDataId"/>' onclick="checkpromotion(this);">
					</td>
					<td><s:property value="windowDataId"/></td>
					<td><s:property value="dataName"/></td>
					<td>
						<s:if test="dataType==0">
								产品
						</s:if>
						<s:elseif test="dataType==1">
								活动
						</s:elseif>
						<s:elseif test="dataType==2">
								类目
						</s:elseif>
						<s:elseif test="dataType==3">
								品牌
						</s:elseif>
						<s:elseif test="dataType==4">
								窗口
						</s:elseif>
						<s:elseif test="dataType==5">
								动态数据
						</s:elseif>
						<s:elseif test="dataType==6">
								自定义数据
						</s:elseif>
						<s:elseif test="dataType==7">
								产品排行榜所需类目
						</s:elseif>
					</td>
					<td>
						<input style="width: 40px;" type="text" data_windowDataId="<s:property value='windowDataId' />" onblur="updateSort(this);" value="<s:property value='sort' />" />
					</td>
					<td>
					<img title="删除" style="cursor: pointer;" src="/etc/images/icon_delete.png"   onclick='deleteByKey(<s:property value="windowDataId"/>)' />
					</td>	
				</tr>
			</s:iterator>
			</table>
			<table class="page_table" width="98%" align="center" cellpadding="0"
				cellspacing="0" border="0">
				<tr>
					<td>
						<s:set name="form_name" value="'windowDetailForm'" scope="request"></s:set>
						<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
					</td>
				</tr>
			</table>
			<s:if test="adminType!=0">
				<table width="95%" class="edit_table" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
					
					
					<tr> 
						<td width="20%" align="right"><font color="red">*</font>窗口内容：</td>
						
						<td width="80%"> 
							当光标在编辑器中，按F11键切换全屏幕编辑。ESC可退出全屏幕编辑。
						</td>
					</tr>
					<tr> 
						<td colspan="2">
							<textarea   name="cmsPageDetail.cmsWindow.content" id="content" cols="100" rows="8" style="height:300px;"><s:property value="cmsPageDetail.cmsWindow.content" /></textarea>
						</td>
					</tr>
					
				</table>
			</s:if>
			<table width="60%"  class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0">
				<tr> 
					<td align="left">
						<input class="backBtn"  onclick="gotoList()" type="button" value="">
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

