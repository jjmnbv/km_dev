<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<%@page import="com.pltfm.cms.parse.PathConstants"%>
<%@page import="java.io.File"%>

<html>
	<head>
		<title>主题列表</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		<script src="/etc/js/dialog.js"></script>
		<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
		<script type="text/javascript"  src="/etc/js/checkeds.js"></script>
		<script type="text/javascript"  src="/etc/js/rowDisplay.js"></script>
		<script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
		<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>
		<script type="text/javascript">
		$(document).ready(function(){
			$("#userLevelForm").validate({
		         rules: {
						"cmsTheme.name": {maxlength:42}
			        	},
			     success: function (label){
			            label.removeClass("checked").addClass("checked");
			            }
		          });
		});
		</script>
		<style type="text/css">
		.content_table
		{
			margin-left:20px;
		}
	</style>
	</head>
	<body >
	<!-- 导航栏 -->
		<div style="height: 90%; overflow-y: scroll;">

			<s:form id="" name="userLevelForm"
				onsubmit="return checkAllTextValid(this)" action="" method="post">
				<s:token></s:token>

				<table width="98%" align="center" height="90" border="0"
					class="content_table" cellpadding="0" cellspacing="0">
					<tr>

						<td width="60%" valign="middle" colspan="2">
							<input type="hidden" id="checkeds" name="checkeds" value="" />
						</td>
					</tr>
					<tr width="60%">
						<td>
							专题名称：
							<input name="cmsTheme.name" type="text"
								value="<s:property value="cmsTheme.name"/>" />
						</td>
						<td>
							专题类型：
                              
                           
		
								<select name="cmsTheme.type">
						        <option value="0" selected="selected">动态专题页</option>
						        <option value="1">静态专题页</option>
						        </select>
		

						</td>

						<td align="right">
							<INPUT TYPE="submit" class="queryBtn" value="">
						</td>
					</tr>
				</table>
				<!-- 数据列表区域 -->
				<table width="98%" class="list_table" cellpadding="3" align="center"
					cellspacing="0" border="1">
					<tr>
						<th width="5%">
							<input type='checkbox' name='level'
								onclick="checkAllpop(this,'levelId')">
						</th>
						<th>
							专题ID
						</th>
						<th>
							专题名称
						</th>

						<th>
							专题类型
						</th>


						<th width="60">
							操作
						</th>
					</tr>
					<s:iterator value="page.dataList">
						<tr>
							<td width="5%">
								<input type="checkbox" name="themeId"
									value='<s:property value="themeId"/>'
									onclick="checkpromotionId(this);">

							</td>
							<td>
								<s:property value="themeId" />
							</td>
							<td>
								<s:property value="name" />
							</td>
							
							<td>
							<s:if test="type==0">
								动态专题页
							</s:if>
							<s:elseif test="type==1">
								静态专题页
							</s:elseif>
							</td>
							<td>
								<img title="选择" style="cursor: pointer;" src="/etc/images/icon_select.png"   onclick='selTempate(<s:property value="themeId"/>)' />
								<img title="查看主题图片" style="cursor: pointer;" src="/etc/images/icon_preview.png"   onclick='viewImage("<s:property value="picture"/>")' />
							</td>
						
						</tr>

					</s:iterator>



				</table>
				<table class="page_table" width="98%" align="center" cellpadding="0"
					cellspacing="0" border="0">
					<tr>
						<td>

							<s:set name="form_name" value="'userLevelForm'" scope="request"></s:set>
							<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
						</td>
					</tr>
				</table>
			</s:form>



			<!-- 消息提示页面 -->
			<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>


			<div width="100%">



			</div>
		</div>
	</body>
	<script type="text/javascript">
		//模板选择
		function selTempate(id)
		{
			$.ajax({
				url:"/cms/cmsPageAction_selThemeTemplate.action?ajax=yes",
				data:"cmsTheme.themeId="+id,
				type:"post",
				success:function(result){
					parent.closeOpenDialog(result);
				},
				error:function(){
					alert("出错了");
				}
			});
		}
		<%
		String imageOut = PathConstants.cmsPicPath();
		imageOut = imageOut+"/";
		%>
	var imageOut = "<%=imageOut%>";
		//查看图片
		function viewImage(imageName){	
			window.open(imageOut+imageName);
		}
	</script>
</html>

