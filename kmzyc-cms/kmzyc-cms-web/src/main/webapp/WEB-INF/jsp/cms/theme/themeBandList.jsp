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

	

     
		
        /** 关闭弹出层操作  **/
        function  closeViewProductInfo(checkeds,themeId){
        	closeThis();
        //	location.href="viewProductInfoAction_openViewProductInfoList.action?checkeds="+checkeds+"&dataType=0&windowId="+windowId;
       //     alert("checkeds:"+checkeds);
             window.location.href="/cms/cmsThemeAction_gotoConfirmBand.action?cmsTheme.themeId="+themeId+"&checkeds="+checkeds;
            
        }
        
         //弹出窗口层
        function gotoBandList(actionType) {
        	var themeId=$("#themeId").val();
            dialog("选择窗口信息","iframe:/cms/cmsThemeAction_"+actionType+".action?cmsTheme.themeId="+themeId+"&cmsTemplate.themeId="+themeId,"900px","530px","iframe");
        }
         
            
    /** 单条删除客户等级信息  **/
    function  deleteByKey(id,themeId){
         if(confirm("是否确认删除? ")==true){
          
           location.href="/cms/cmsThemeAction_delBandData.action?cmsThemeTemplate.themeTemplateId="+id+"&cmsThemeTemplate.themeId="+themeId+"&cmsTheme.themeId="+themeId;
         }
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
        	       var themeId= $("#themeId").val();
        	     //  cmsThemeTemplate.themeId="+themeId
                  document.pageForm.action="/cms/cmsThemeAction_delBandDatas.action";
                  document.pageForm.submit();
         }
    }
     
      function gotoList(id){
 	var pageForm= window.document.getElementById("pageForm");
 	document.pageForm.action="/cms/cmsThemeAction_queryThemeList.action";
 	document.pageForm.submit();
 
 }
    
		</script>
	</head>
	<body>
		<!-- 导航栏 -->
		<s:set name="parent_name" value="'基础功能'" scope="request" />
		<s:set name="name" value="'数据管理'" scope="request" />
		<s:set name="son_name" value="'绑定数据列表'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<div  style="height:90%;overflow-y:scroll; " >
		<s:form name="pageForm"  id="pageForm"  onsubmit="return checkAllTextValid(this)" 
			action="/cms/cmsThemeAction_gotoBandList.action" method="post">
			<s:token></s:token>
			
			<!-- 查询条件 -->
		    <table width="98%" align="center" height="90" border="0"	class="content_table"
			cellpadding="0" cellspacing="0">
				<tr>
					<td width="60%" valign="middle" colspan="2">
					<!-- <input class="backBtn"  onclick="gotoList()" type="button" value=""> -->
						<input class="delBtn" type="button" value=""
							onclick="deleteSelected('dataIds');">
						&nbsp;&nbsp;
			<input class="backBtn"  onclick="gotoList()" type="button" value="" style="cusor:pointer;">	
					</td>
				</tr>
				
				<tr width="100%" height="40">
					
					<td align="left" width="20%">
						数据类型:
						<select name="cmsThemeTemplate.type">
						
							<option value="">全部</option>
							<option value="12" <s:if test="cmsThemeTemplate.type==12">selected="selected"</s:if>>页面变量类型</option>
							<option value="13" <s:if test="cmsThemeTemplate.type==13">selected="selected"</s:if>>窗口变量类型</option>
						

						
						</select>
					</td>
					
					<td align="right" >
						<INPUT TYPE="submit" class="queryBtn" value="">
					</td>
				</tr>
			
			<tr>  
				<td colspan="4"  align="center" >
				    <input type="button" value="添加页面或窗口变量模板" onclick="gotoBandList('gotoBandPage');"   style="cursor:pointer;"/>&nbsp;
				     <input type="hidden" id="themeId" name="cmsTheme.themeId" value="<s:property value='cmsTheme.themeId'/>">
				     <input type="hidden" id="cmsThemeTemplate.themeId" name="cmsThemeTemplate.themeId" value="<s:property value='cmsTheme.themeId'/>">
				<!-- 	<input type="button" value="添加窗口" onclick="gotoBandList('gotoBandWindow');" style="cursor:pointer;"/>&nbsp; -->
					
					
					
					
				</td>
			</tr>
			</table>
			<!-- 数据列表区域 -->
			<table width="98%" class="list_table" cellpadding="3" align="center"
				cellspacing="0" border="1" id="list_table">
				<tr class="dataName">
					<th width="5%">
						<input type='checkbox' name='windowDataIds'  onclick="checkAll(this,'dataIds');">
					</th>
					<th>
						数据名称
					</th>
					<th>
						数据内容
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
							<input type="checkbox" name="dataIds" value="<s:property value='themeTemplateId'/>">
						</td>
						<td>
							<s:property value="name" />
						</td>
					
						<td>
						   <s:property value="content" />
						</td>
						<td>
						
							
							   <s:if test="type==13">窗口变量模板</s:if>
							   <s:if test="type==12">页面变量模板</s:if>
							 
						</td>
						
						<td> 
						   <!--     	<img title="绑定" style="cursor: pointer;" src="/etc/images/icon_select.png"   onclick="gotoBandList('gotoBandPage');"/>--> 
						   
						     <img title="删除" style="cursor: pointer;" src="/etc/images/icon_delete.png"   onclick="deleteByKey(<s:property value='themeTemplateId'/>,<s:property value='themeId'/>);" />
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
	</body>
</html>

