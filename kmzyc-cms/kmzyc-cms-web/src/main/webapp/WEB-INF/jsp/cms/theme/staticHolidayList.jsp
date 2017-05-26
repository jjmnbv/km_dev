<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<title>专题页列表</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet"
			type="text/css" />
		<script type="text/javascript" src="/etc/js/jquery-1.8.3.js">
</script>
		<script src="/etc/js/dialog.js">
</script>
		<script type="text/javascript" src="/etc/js/pageCommon.js">
</script>
		<script type="text/javascript" src="/etc/js/checkeds.js">
</script>
	</head>
	<body>
		<!-- 导航栏 -->
		<script>
var pageForm;
/** 删除文章信息  **/
function deleteSelected(id) {
	var obj = document.getElementsByName(id);
	var count = 0;
	var obj_cehcked = null;
	// 遍历所有用户，找出被选中的用户
	for ( var i = 0; i < obj.length; i++) {
		if (obj[i].checked) {
			count++;
			obj_cehcked = obj[i];
		}
	}
	if (count == 0) {
		alert("请选择要删除的数据。");
		return false;
	} else if (confirm('是否确认删除?') == true) {
		document.staticForm.action = "/cms/cmsThemeAction_staticHolidayDelDatas.action";
		document.staticForm.submit();
	}
}
//多条发布
function publishSelected(id) {
	var obj = document.getElementsByName(id);
	var count = 0;
	var obj_cehcked = null;
	// 遍历所有用户，找出被选中的用户
	for ( var i = 0; i < obj.length; i++) {
		if (obj[i].checked) {
			count++;
			obj_cehcked = obj[i];
		}
	}
	if (count == 0) {
		alert("请选择要发布的数据。");
		return false;
	} else if (confirm('是否确认发布?') == true) {
		document.staticForm.action = "/cms/cmsThemeAction_publishStaticAll.action";
		document.staticForm.submit();
	}
}
function gotoAdd() {
	var pageForm = window.document.getElementById("staticForm");
	pageForm.action = "/cms/cmsThemeAction_gotoStaticHolidayAdd.action";
	pageForm.submit();
}

/** 修改 **/
function gotoEdit(id) {
	//  alert("iD"+id);                                                        
	location.href = "/cms/cmsThemeAction_gotoStaticHolidayEdit.action?infor.inforId="
			+ id;
}

/** 单条删除文章信息  **/
function deleteByKey(id) {
	if (confirm("是否确认删除? ") == true) {
		location.href = "/cms/cmsThemeAction_staticHolidayDel.action?infor.inforId="
				+ id;
	}
}
function publishStatic(id) {
	location.href = "/cms/cmsThemeAction_publishStaticHoliday.action?infor.inforId="
			+ id;
}
</script>

		<!-- 导航栏 -->
        <s:set name="parent_name" value="'基础功能'" scope="request" />
		<s:set name="name" value="'静态专题页列表'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

		<div style="height: 90%; overflow-y: scroll;">

			<s:form id="staticForm" name="staticForm"  onsubmit="return checkAllTextValid(this)"
				action="/cms/cmsThemeAction_queryStaticHolidayList.action"
				method="post">
				<s:token></s:token>

				<!-- 查询条件 -->
				<table width="98%" align="center" border="0" class="content_table" cellpadding="0" cellspacing="0">
					<tr>
						<td align="left" width="18%">
							专题标题：

							<input name="infor.name" type="text"
								value="<s:property value="infor.name"/>"/>


						</td>
						<td align="left" width="18%">
							专题类型：
							<select name="infor.templateType">
								<option value="">
									全部
									</option>
										<option value="7"
											<s:if test="infor.templateType==7">selected="selected"</s:if>>
											窄版静态专题页
										</option>
										<option value="8"
											<s:if test="infor.templateType==8">selected="selected"</s:if>>
											宽版静态专题页
										</option>
										<option value="9"
											<s:if test="infor.templateType==9">selected="selected"</s:if>>
											商家入驻模板
										</option>
										<option value="17"
											<s:if test="infor.templateType==17">selected="selected"</s:if>>
											招商入驻
										</option>
										<option value="18"
											<s:if test="infor.templateType==18">selected="selected"</s:if>>
											合作模式
										</option>
										<option value="19"
											<s:if test="infor.templateType==19">selected="selected"</s:if>>
											商家规则
										</option>
										<option value="20"
											<s:if test="infor.templateType==20">selected="selected"</s:if>>
											商家店铺
										</option>
										<option value="21"
											<s:if test="infor.templateType==21">selected="selected"</s:if>>
											活动中心
										</option>
										<option value="22"
											<s:if test="infor.templateType==22">selected="selected"</s:if>>
											商户服务
										</option>
										
										 
							</select>

						</td>
						<td align="right">
							<td align="left" width="18%">
								输出路径：
								<input name="infor.content" type="text" value="<s:property value="infor.content"/>"/>

							</td>
							<td align="right">

								<td align="left" width="20%">

									发布状态：
									<select name="infor.status" id="infor.status">
										<option value=""
											<s:if test="infor.status==null">selected="selected"</s:if>>
											全部
										</option>
										<option value="0"
											<s:if test="infor.status==0">selected="selected"</s:if>>
											已发布
										</option>
										<option value="1"
											<s:if test="infor.status==1">selected="selected"</s:if>>
											未发布
										</option>
									</select>

								</td>
								<td align="right">
									<INPUT TYPE="submit" class="queryBtn" value="">
                                    <input class="addBtn" TYPE="button" value="" onClick="gotoAdd();" style="cursor: pointer;">
							<input class="delBtn" type="button" value=""
								onclick="deleteSelected('dataIds');" style="cursor: pointer;">
							<input type="button" value="多条发布" onClick="publishSelected('dataIds');" class="btn-custom"> 
							<input type="hidden" id="checkeds" name="checkeds"
								value="<s:property value='checkeds'/>" />
								</td>
					</tr>
				</table>
				<!-- 数据列表区域 -->
				<table width="98%" class="list_table" cellpadding="3" align="center"
					cellspacing="0" border="1">
					<tr>
						<th width="5%">
							<input type='checkbox' name='level'
								onclick="checkAllpop(this,'dataIds')">
						</th>
						<th>
							编号
						</th>
						<th>
							专题标题
						</th>
						<th>
							专题类型

						</th>

						<th>
							输出路径
						</th>

						<th>
							状态
							<!--0.有效1.无效-->
						</th>
						
						<th >
					         修改日期
					</th>
					<th >
					         修改人
					</th>
						<th width="80">
							操作
						</th>
					</tr>
					<s:iterator value="page.dataList">
						<tr>
							<td width="5%">
								<input type="checkbox" name="dataIds"
									value='<s:property value="InforId"/>'
									onclick="checkpromotionId(this);">

							</td>
							<td>
								<s:property value="inforId" />
							</td>
							<td>

								<s:property value="name" />
							</td>
							<td>

								<s:if test="templateType==7">窄版静态专题页</s:if>
								<s:if test="templateType==8">宽版静态专题页</s:if>
								<s:if test="templateType==9">商家入驻模板</s:if>
								<s:if test="templateType==17">招商入驻</s:if>
								<s:if test="templateType==18">合作模式</s:if>
								<s:if test="templateType==19">商家规则</s:if>
								<s:if test="templateType==20">商家店铺</s:if>
								<s:if test="templateType==21">活动中心</s:if>
								<s:if test="templateType==22">商户服务</s:if>
								
								
								
							
									
								
							</td>

							<td>

								<s:property value="content" />
							</td>

							<td>

								<s:if test="status==0">已发布</s:if>
								<s:if test="status==1">未发布</s:if>
							</td>



 					<td><s:date name="modifyDate" format="yyyy-MM-dd HH:mm:ss"/>
							
						</td>
						<td>
							<s:property value="sysUserMap[modified]"/>
						</td>


							<td>
							
									<img title="发布"
										style="cursor: pointer;"
										src="/etc/images/icon_publish.png"
										onclick="publishStatic(<s:property value="inforId"/>)" />
								
								<img title="修改" style="cursor: pointer;"
									src="/etc/images/icon_modify.png"
									onclick="gotoEdit(<s:property value="inforId"/>)" />
								<img title="删除" style="cursor: pointer;"
									src="/etc/images/icon_delete.png"
									onclick="deleteByKey(<s:property value="inforId"/>)" />
							</td>
						</tr>

					</s:iterator>



				</table>
				<table class="page_table" width="98%" align="center" cellpadding="0"
					cellspacing="0" border="0">
					<tr>
						<td>
							&nbsp;

							<s:set name="form_name" value="'staticForm'" scope="request"></s:set>
							<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
						</td>
					</tr>
				</table>
			</s:form>




			<!-- 消息提示页面 -->
			<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>


			<!-- 消息提示 -->
			<div width="100%">



			</div>
		</div>



	</body>
</html>
