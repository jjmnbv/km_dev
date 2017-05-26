<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<%@page import="com.pltfm.cms.parse.PathConstants"%>
<%@page import="java.io.File"%>
<html>
	<head>
		<title>风格可视化管理</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		
		<script type="text/javascript" src="/etc/js/jquery-latest.pack.js"></script>
		<script src="/etc/js/dialog.js"></script>
		<script type="text/javascript" src="/etc/js/pageCommon.js"></script>
		<script type="text/javascript" src="/etc/js/checkeds.js"></script>
		<script type="text/javascript" src="/etc/js/rowDisplay.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.validate.js"></script>
		<script type="text/javascript" src="/etc/js/messages_cn.js"></script>
		
		<script type="text/javascript">
		var pageForm= window.document.getElementById("pageForm");
		
		function gotoList(){
				location.href="/cms/cmsStylesAction_queryForPage.action";
		}

         function addPageBtn(){
             
        	 location.href="/cms/cmsPageVisualizationAction_gotoAddVisualization.action?stylesId="+$("#stylesId").val(); 
   		}

         /** 单条删除站点信息  **/
         function  deleteByKey(id){
             if(confirm("是否确认删除? ")==true){
     	 
     	 	   location.href="/cms/cmsPageVisualizationAction_delVisualizationAction.action?visualizationId="+id+"&stylesId="+$("#stylesId").val();;   	
               
              }
         }
        
		</script>
		 <%
	String imageOut = PathConstants.cmsPicPath();
	imageOut = imageOut+"/";
	%>
	</head>
	<body>
		<!-- 导航栏 -->
		<s:set name="parent_name" value="'基础功能'" scope="request" />
		<s:set name="name" value="'风格可视化管理'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<div  style="height:90%;overflow-y:scroll; " >
		<s:form name="pageFormVisualization" class="pageFormVisualization" id="pageFormVisualization"  onsubmit="return checkAllTextValid(this)"
			action="/cms/cmsPageVisualizationAction_queryForPage.action" method="post">
			<s:token></s:token>
			<input type="hidden" name="adminType" id="adminType" value="<s:property value='adminType'/>">
			<!-- 查询条件 -->
		    <table width="98%" align="center" height="90" border="0"	class="content_table"
			cellpadding="0" cellspacing="0">
				<tr>
					<td width="60%" valign="middle" colspan="2">
					<INPUT class="addBtn" id="addBtn" TYPE="button" value="" onclick="addPageBtn();" />
				 <input class="backBtn"  onclick="gotoList()" type="button" value=""> 
							<input type="hidden" name="stylesId" id="stylesId" value="<s:property value='stylesId'/>" />
							
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
					<th>
						风格编号
					</th>
					<th>
						数据编号
					</th>
					<th>
						高度
					</th>
					<th>
						宽度
					</th>
					<th>
						类名
					</th>
					<th>
						数据类型
					</th>
					<th>
						是否隐藏
					</th>
					<th>
						图片
					</th>
					<th>
						排序
					</th>
					<th width="60">
						操作
					</th>
				</tr>
				<s:iterator id="custiterator" value="page.dataList">
					<tr class="dataTr">
						<td width="5%">
							<input type="checkbox" name="windowDataIds" value="<s:property value='pageVisualizationId'/>">
						</td>
						<td>
							<s:property value="stylesId" />
						</td>
					
						<td>
							<s:property value="dataid" />
						</td>
						<td>
							<s:property value="height" />
						</td>
						<td>
							<s:property value="width" />
						</td>
						
						<td>
							<s:property value="divclass" />
						</td>
						<td>
							<s:if test="datatype==1">
								窗口
							</s:if>
							<s:elseif test="datatype==2">
								广告
							</s:elseif>
						</td>
						<td>
							<s:if test="isdisplay==0">	
								是
							</s:if>
							<s:elseif test="isdisplay==1">
								否
							</s:elseif>
						</td>
						<td>
						<a href="<%=imageOut%><s:property value="pic"/>" target="_blank">
								<s:property value="pic" />
						</a>
						</td>
						<td>
							<s:property value="sort" />
						</td>
						<td>
						     <img title="删除" style="cursor: pointer;" src="/etc/images/icon_delete.png"   onclick="deleteByKey(<s:property value='pageVisualizationId'/>)" />
						</td>
					</tr>
				</s:iterator>
			</table>
			
			<table class="page_table" width="98%" align="center" cellpadding="0"
				cellspacing="0" border="0">
				<tr>
					<td>

						<s:set name="form_name" value="'pageFormVisualization'" scope="request"></s:set>
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

