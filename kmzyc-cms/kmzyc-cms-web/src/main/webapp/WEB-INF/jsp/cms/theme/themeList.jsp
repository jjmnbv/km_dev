<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
	<head>
		<title>主题管理</title>

	<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		<script src="/etc/js/dialog.js"></script>
		<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
		<script type="text/javascript"  src="/etc/js/checkeds.js"></script>
		<script type="text/javascript"  src="/etc/js/rowDisplay.js"></script>
		<script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
		<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>
	</head>
	<body>
		<!-- 导航栏 -->
		<script>
//var pageForm=$("#themeForm");
$(document).ready(function() {
	 
	var checks = "";
	checks = $("#checkeds").val();
	if (checks != "") {
		var checkboxs = document.getElementsByName("levelId");
		var myarr = new Array();
		myarr = checks.split(',');
		for ( var i = 0; i < checkboxs.length; i++) {
			for ( var j = 0; j < myarr.length; j++) {
				if (checkboxs[i].value == myarr[j]) {
					checkboxs[i].checked = true;
					break;
				}
			}
		}
	}
	pageForm = window.document.getElementById("supplier_queryPageList");
	
	
	
});

//返回
function gotoList() {
	history.back();
}

/** 修改  **/
function gotoUpdate(id) {
	//   var pageNo=$("#pageNo").val();
	location.href = "/cms/cmsThemeAction_gotoUpdate.action?cmsTheme.themeId="
			+ id;
}
/**绑定  **/
function gotoBandList(id) {
	//   var pageNo=$("#pageNo").val();
	location.href = "/cms/cmsThemeAction_gotoBandList.action?cmsThemeTemplate.themeId="+ id+"&cmsTheme.themeId="+id;
}

/**删除**/
function gotoDel(id) {
	//   var pageNo=$("#pageNo").val();
	  if(confirm("是否确认删除? ")==true){
		location.href = "/cms/cmsThemeAction_delThemeData.action?cmsTheme.themeId="+id;
	}
}

     /** 批量删除数据信息  **/
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
        	    
                  document.themeForm.action="/cms/cmsThemeAction_delThemeDatas.action";
                  document.themeForm.submit();
         }
    }



</script>
		<!-- 导航栏 -->
		<s:set name="parent_name" value="'基础功能'" scope="request" />
		<s:set name="name" value="'主题管理'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<div style="height: 90%; overflow-y: scroll;">

			<s:form id="themeForm" name="themeForm"
				onsubmit="return checkAllTextValid(this)" action="/cms/cmsThemeAction_queryThemeList.action" method="post">
				<s:token></s:token>

				<table width="98%" align="center" border="0"class="content_table" cellpadding="0" cellspacing="0">
					<tr width="60%">
						<td>
							主题名称：
							<input name="cmsTheme.name" type="text"
								value="<s:property value="cmsTheme.name"/>" />
						</td>
						<td>
							主题类型：
                              
                           
		
								<select name="cmsTheme.type">
								<option value="" >全部</option>
						        <option value="0" <s:if test="cmsTheme.type==0">selected="selected"</s:if>>动态主题页</option>
						        <option value="1" <s:if test="cmsTheme.type==1">selected="selected"</s:if>>静态主题页</option>
						        <option value="2" <s:if test="cmsTheme.type==2">selected="selected"</s:if>>供应商店铺默认版</option>
						        <option value="3" <s:if test="cmsTheme.type==3">selected="selected"</s:if>>供应商店铺简版</option>
						      <option value="7"  <s:if test="cmsTheme.type==7">selected="selected"</s:if>>供应商店铺药房一版</option>
					          <option value="8"  <s:if test="cmsTheme.type==8">selected="selected"</s:if>>供应商店铺药房简一版</option>
					          <option value="10"  <s:if test="cmsTheme.type==10">selected="selected"</s:if>>供应商店铺药房二版</option>
					          <option value="11"  <s:if test="cmsTheme.type==11">selected="selected"</s:if>>供应商店铺药房三版</option>
					           <option value="12"  <s:if test="cmsTheme.type==12">selected="selected"</s:if>>供应商店铺药房四版</option>
					            <option value="13"  <s:if test="cmsTheme.type==13">selected="selected"</s:if>>供应商店铺药房简四版</option>
		                      <option value="14"  <s:if test="cmsTheme.type==14">selected="selected"</s:if>>供应商店铺药房五版</option>
		                      <option value="15"  <s:if test="cmsTheme.type==15">selected="selected"</s:if>>供应商店铺药房六版</option>
						        <option value="9"  <s:if test="cmsTheme.type==9">selected="selected"</s:if>>供应商店铺美食版</option>
						        <option value="4" <s:if test="cmsTheme.type==4">selected="selected"</s:if>>供应商店铺其他页面</option>
						        <option value="5" <s:if test="cmsTheme.type==5">selected="selected"</s:if>>供应商店铺头部页面</option>
						        <option value="6" <s:if test="cmsTheme.type==6">selected="selected"</s:if>>供应商店铺动态头部页面</option>
						        </select>
		

						</td>

						<td align="right">
							<INPUT TYPE="submit" class="queryBtn" value="">
                            							<input type="hidden" id="checkeds" name="checkeds" value="" />

								<INPUT class="addBtn" id="addBtn" TYPE="button" value="" style="cursor: pointer;"/>
						
						<input class="delBtn" type="button" value="" onClick="deleteSelected('dataIds');"/>
						<input name="cmsTheme.siteId"  type="hidden" value="<s:property value="cmsTheme.siteId"/>"/>
						</td>
					</tr>
				</table>
				<!-- 数据列表区域 -->
				<table width="98%" class="list_table" cellpadding="3" align="center"
					cellspacing="0" border="1">
					<tr>
						<th width="5%">
							<input type='checkbox' name='level'
								onclick="checkAll(this,'dataIds');">
						</th>
						<th>
							主题ID
						</th>
						<th>
							主题名称
						</th>

						<th>
							主题类型
						</th>
						<th >
					         修改日期
						</th>
						<th >
						         修改人
						</th>

						<th>
							操作
						</th>
					</tr>
					<s:iterator value="page.dataList">
						<tr>
							<td width="5%">
								<input type="checkbox" name="dataIds"
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

								
								<s:if test="type==0">动态主题页</s:if>
								<s:if test="type==1">静态主题页</s:if>
								<s:if test="type==2">供应商店铺默认版</s:if>
                                 <s:if test="type==3">供应商店铺简认版</s:if>
                                 <s:if test="type==7">供应商店铺药房一版</s:if>
                                 <s:if test="type==8">供应商店铺药房简一版</s:if>
                                 <s:if test="type==10">供应商店铺药房二版</s:if>
                                 <s:if test="type==11">供应商店铺药房三版</s:if>
                                  <s:if test="type==12">供应商店铺药房四版</s:if>
                                  <s:if test="type==13">供应商店铺药房简四版</s:if>
                                  <s:if test="type==14">供应商店铺药房五版</s:if>
                                  <s:if test="type==15">供应商店铺药房六版</s:if> 
                                  <s:if test="type==9">供应商店铺美食版</s:if>
                                 <s:if test="type==4">供应商店铺其他页面</s:if>
                                 <s:if test="type==5">供应商店铺头部页面</s:if>
                                 <s:if test="type==6">供应商店铺动态头部页面</s:if>
                                 
                                 
                                 
							</td>
							<td>
							<s:date name="modifyDate" format="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td>
								<s:property value="sysUserMap[modified]"/>
							</td>

							<td>

							   	<img title="绑定" style="cursor: pointer;" src="/etc/images/icon_select.png"   onclick='gotoBandList(<s:property value="themeId"/>)' />
								<!-- 
								<img title="详情" style="cursor: pointer;" src="/etc/images/icon_detail.png"  onclick='pageDetail(<s:property value="pageId"/>)'/>
								 -->
								<img title="修改" style="cursor: pointer;"
									src="/etc/images/icon_modify.png"
									onclick="gotoUpdate(<s:property value="themeId"/>)" />
                                <img title="删除" style="cursor: pointer;" src="/etc/images/icon_delete.png"   onclick='gotoDel(<s:property value="themeId"/>)' />

							</td>
						</tr>

					</s:iterator>



				</table>
				<table class="page_table" width="98%" align="center" cellpadding="0"
					cellspacing="0" border="0">
					<tr>
						<td>

							<s:set name="form_name" value="'themeForm'" scope="request"></s:set>
							<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
						</td>
					</tr>
				</table>
			</s:form>



			<!-- 消息提示页面 -->
			<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>


			<div width="100%">

			<script type="text/javascript">
			 var themeForm= window.document.getElementById("themeForm");
			$(function(){
				//跳转到添加页面
		   		$("#addBtn").click(function(){
			   		themeForm.action="/cms/cmsThemeAction_gotoAdd.action"; 
	 				themeForm.submit();
		   		});
		   });		
	   		</script>


			</div>
		</div>
	</body>
</html>

