<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
	<head>
		<title>站点列表</title>
	<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
<script type="text/javascript"  src="/etc/js/checkeds.js"></script>
<script type="text/javascript"  src="/etc/js/rowDisplay.js"></script>
		<script>
 /** 授权用户信息  */
   function selectOneAccount(){   
    var obj = document.getElementsByName("siteIds");
		var objLen = obj.length;
		var i;
		var k = 0;
		for (i = 0; i < objLen; i++) {
			if (obj[i].checked == true) {
				k = k + 1
				break;
			}

		}
		if (k == 0) {
			alert("请选择要授权的用户！");
			return false;
		}
    $.ajax({
                cache: true,
                type: "POST",
                url:"/cms/cmsSite_addSite.action",
                data:$('#cmsSiteForm').serialize(),// 你的formid
                async: false,
                error: function(request) {
                    alert("授权异常");
                },
                success: function(data) {
                        parent.closeOpenSiteDiv();
                }
            });  
    }
    
    
     function chSubmit(){   
       document.getElementById("pageNo1").value=1;
       document.getElementById('cmsSiteForm').submit();
      }
	</script>
	</head>
	<body>
		<!-- 导航栏 -->
		
		<div style="height: 90%; overflow-y: scroll;">
			<s:form class="pageForm" name="cmsSiteForm" id="cmsSiteForm"
				onsubmit="return checkAllTextValid(this)"
				action="/cms/cmsSite_popUPquery.action" method="post">
				<table width="98%" align="center" height="90" border="0"
					class="content_table" cellpadding="0" cellspacing="0">
					<tr>
					</tr>
					<td align="left" >
						站点名：
						<input name="cmsSite.name" type="text"
							value="<s:property value="cmsSite.name"/>">
					</td>
					<td align="left" >
						站点域名：
						<input name="cmsSite.url" type="text"
							value="<s:property value="cmsSite.url"/>">
					</td>
					<td align="left" >
						状态:
						<select name="cmsSite.status">
							<option value=""
								<s:if test="cmsSite.status==null">selected="selected"</s:if>>
								全部
							</option>
							<option value="0"
								<s:if test="cmsSite.status==0">selected="selected"</s:if>>
								有效
							</option>
							<option value="1"
								<s:if test="cmsSite.status==1">selected="selected"</s:if>>
								无效
							</option>
						</select>
					</td>
					<td align="right">
					<input type="hidden" id="userId" name="userId"
							value='<s:property value="cmsSite.userId"/>'>
						<input type="hidden" id="checkeds" name="checkeds"
							value='<s:property value="checkeds"/>'>
						<INPUT TYPE="submit" class="queryBtn" value="">
					</td>

					</tr>
				</table>
				<table width="98%" class="list_table" cellpadding="3" align="center">
					<tr>
						<th width="5%">
							<input type='checkbox' name='promotion' id="promotion"
								onclick="checkAllpop(this,'siteIds')">
						</th>
						<th>
							站点名
						</th>
						<th>
							站点域名
						</th>
						<th>
							状态
						</th>
					</tr>
					<s:iterator id="cmsSite" value="page.dataList">
						<tr>
							<td width="5%">
								<input type="checkbox" name="siteIds"
									value='<s:property value="siteId"/>'
									onclick="checkpromotionId(this);">
							</td>
							<TD>
								<s:property value="name" />
							</TD>
							<TD>
								<s:property value="url" />
							</TD>
							<TD>
								<s:if test="status==0">
				有效
				</s:if>
								<s:if test="status==1">
				无效
				</s:if>
							</TD>
						</tr>
					</s:iterator>
				</table>
				<table class="page_table" width="98%" align="center" cellpadding="0"
					cellspacing="0" border="0">
					<tr>
						<td>
							<s:set name="form_name" value="'cmsSiteForm'" scope="request"></s:set>
							<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
						</td>
					</tr>
				</table>
				<!-- 底部 按钮条 -->
				<table width="60%" class="edit_bottom" height="30" border="0"
					cellpadding="0" cellspacing="0">
					<tr>
						<td align="left">
							<input class="saveBtn" type="button" value=" "
								onclick="selectOneAccount();">
						</td>
						<td width="20%" align="center"></td>
					</tr>
				</table>
			</s:form>
		</div>
	</body>
</html>

