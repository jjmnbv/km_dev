<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<%String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>申诉列表 </title>
				<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
 <script src="/etc/js/dialog.js"></script>
 <Script src="/etc/js/97dater/WdatePicker.js"></Script>
		
		<script type="text/javascript">
  /** 删除消息信息  **/
function deleteSelected(id) {
	var obj = document.getElementsByName(id);
	var count = 0;
	var cehcked = false;
	// 遍历所有用户，找出被选中的用户
	for ( var i = 0; i < obj.length; i++) {
		
		if (obj[i].checked) {
			cehcked=true;
		}
	}
	if (!cehcked) {
		alert("请选择要删除的数据。");
		return false;
	} else if (confirm('是否确认删除?') == true) {
		document.acctAppealInfoForm.action = "/messageCenter/publishMessage_delete.action";
		document.acctAppealInfoForm.submit();
	}
}


/**进入申诉处理页面**/
function gotoUpdate(id) {
	document.acctAppealInfoForm.action = "/acctBusiness/acctAppealInfo_preEdit.action?bnesAcctAppealInfo.accountAppealId="+id;
	document.acctAppealInfoForm.submit();
}

/**进入申诉查看页面**/
function gotoView(id) {
	document.acctAppealInfoForm.action = "/acctBusiness/acctAppealInfo_preView.action?bnesAcctAppealInfo.accountAppealId="+id;
	document.acctAppealInfoForm.submit();
}
/**添加申诉**/
function gotoAdd() {
	document.acctAppealInfoForm.action = "/acctBusiness/acctAppealInfo_preAdd.action?";
	document.acctAppealInfoForm.submit();
}
    //账号层
function queryAccountInfo(id) {
    dialog("选择会员账号","iframe:/accounts/accountInfo_preDetail.action?showType=4&n_AccountId="+id ,"900px","760px","iframe");   
}
 
</script>
	</head>
	<body>
		<!-- 导航栏 -->
		<s:set name="parent_name" value="'客户成长'" scope="request" />
		<s:set name="name" value="'投诉建议'" scope="request" />
		<%--<s:set name="son_name" value="'申诉列表'" scope="request" /> --%>
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		 <div  style="height:90%;overflow-y:auto; " >
		<s:form name="acctAppealInfoForm" method="post"
			action="/acctBusiness/acctAppealInfo_list.action">
			<!-- 查询条件区域 -->
			<table width="98%" class="content_table" align="center"
				cellpadding="0" cellspacing="0">
				<tr>
					<td align="right">
						会员账号：
					</td>
					<td >
						<input name="bnesAcctAppealInfoQry.accountName" type="text" value="<s:property value='bnesAcctAppealInfoQry.accountName'/>">
					</td>
					<td align="right">
						申诉标题：
					</td>
					<td>
						<input name="bnesAcctAppealInfoQry.appealTitle" type="text" value="<s:property value='bnesAcctAppealInfoQry.appealTitle'/>">
					</td>
					<td align="right">
						申诉日期：
					</td>
					<td>
						<input type="text" name="bnesAcctAppealInfoQry.createDateBefore" value="<s:date name = 'bnesAcctAppealInfoQry.createDateBefore' format='yyyy-MM-dd HH:mm:ss' />" id="d523" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'d521\')}'})">
						至<input type="text" name="bnesAcctAppealInfoQry.createDateEnd" value="<s:date name = 'bnesAcctAppealInfoQry.createDateEnd' format='yyyy-MM-dd HH:mm:ss' />" id="d521" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'d523\')}'})">
					</td>
				</tr>
				<tr>
					<td align="right" >
						相关订单：
					</td>
					<td>
						<input name="bnesAcctAppealInfoQry.order_Desc" type="text" value="<s:property value='bnesAcctAppealInfoQry.order_Desc'/>">
					</td>
					<td align="right">
						处理人：
					</td>
					<td>
						<input name="bnesAcctAppealInfoQry.disposePersonName" type="text" value="<s:property value='bnesAcctAppealInfoQry.disposePersonName'/>">
					</td>
					<td align="right">
						申诉类型：
					</td>
					<td>
						<select name="bnesAcctAppealInfoQry.type">
							<option value="" <s:if test='bnesAcctAppealInfoQry.type==""'>selected="selected"</s:if>>全部</option>
							<option value="1" <s:if test='bnesAcctAppealInfoQry.type=="1"'>selected="selected"</s:if>>产品相关</option>
							<option value="2" <s:if test='bnesAcctAppealInfoQry.type=="2"'>selected="selected"</s:if>>价格相关</option>
							<option value="3" <s:if test='bnesAcctAppealInfoQry.type=="3"'>selected="selected"</s:if>>物流相关</option>
							<option value="4" <s:if test='bnesAcctAppealInfoQry.type=="4"'>selected="selected"</s:if>>服务相关</option>
							<option value="5" <s:if test='bnesAcctAppealInfoQry.type=="5"'>selected="selected"</s:if>>其他问题</option>
						</select>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						处理状态：
						<select name="bnesAcctAppealInfoQry.dealStatus">
							<option value="" <s:if test='bnesAcctAppealInfoQry.dealStatus==""'>selected="selected"</s:if>>所有</option>
							<option value="1" <s:if test='bnesAcctAppealInfoQry.dealStatus=="1"'>selected="selected"</s:if>>已处理</option>
							<option value="2" <s:if test='bnesAcctAppealInfoQry.dealStatus=="2"'>selected="selected"</s:if>>未处理</option>
						</select>
					</td>
					<td>
						<INPUT TYPE="submit" class="queryBtn" value="">
					</td>
				</tr>
			</table>

			<!-- 数据列表区域 -->
			<table width="98%" class="list_table" cellpadding="3" align="center"
				cellspacing="0" border="1">
				<tr>
					<th width="8%">
						登录账号
					</th>
					<th  width="7%">
						申诉类型
					</th>
					<th  width="8%">
						申诉标题
					</th>
					<th  width="18%">
						申诉内容
					</th>
					<th  width="10%">
						相关订单
					</th>
					<th  width="12%">
						申诉日期
					</th>
					<th  width="5%">
						处理人
					</th>
					<th  width="15%">
						处理意见
					</th>
					<th  width="10%">
						处理日期
					</th>
					<th  width="12%">
						处理状态
					</th>
					<th  width="5%">
						操作
					</th>
				</tr>
				<s:iterator id="custiterator" value="page.dataList">
					<tr>
						<td align="center">
                             <s:property value="accountName" />
						</td>
						<td align="center">
						<s:if test="type==0">
						     其它
						 </s:if>
						 <s:if test="type==1">
						    产品相关
						 </s:if>
						 <s:if test="type==2">
						     价格相关
						 </s:if>
						 <s:if test="type==3">
						     物流相关
						 </s:if>
						 <s:if test="type==4">
						     服务相关
						 </s:if>
						 <s:if test="type==5">
						    其他问题
						 </s:if>
						</td>
						<td align="center" title="<s:property value="appealTitle" />" >
							<s:if test="appealTitle.length()>6">
			   		 	 	<s:property value='appealTitle.substring(0,6)'/>...
				 			</s:if>
				 			<s:else>
				 			<s:property value='appealTitle'/>
				 			</s:else>
						</td>
						<td align="center" title="<s:property value="appealContent" />" >
							<s:if test="appealContent.length()>14">
			   		 	 	<s:property value='appealContent.substring(0,14)'/>...
				 			</s:if>
				 			<s:else>
				 			<s:property value='appealContent'/>
				 			</s:else>
						</td>
						<td align="center">							
							<s:property value="order_Desc" />
						</td>
						<td align="center">							
							<s:date name="appealCreateDate" />
						</td>
						<td align="center">
							<s:property value="disposePersonName" />
						</td>
						<td align="center" title="<s:property value="appealSuggestion" />" >
							<s:if test="appealSuggestion.length()>10">
			   		 	 	<s:property value='appealSuggestion.substring(0,10)'/>...
				 			</s:if>
				 			<s:else>
				 			<s:property value='appealSuggestion'/>
				 			</s:else>
						</td>
						<td align="center">
							 <s:date name="disposeDate" />
						</td>
						<td align="center">
						  <s:if test="appealSuggestion==null">
						  	未处理
						  </s:if>	
						  	 <s:if test="appealSuggestion!=null">	
						  	 已处理
						  	 </s:if>		
						</td>
						<td align="center">
						  <s:if test="appealSuggestion==null">
						  		<img title="处理" style="cursor: pointer;"
								src="/etc/images/icon_modify.png"
								onclick='gotoUpdate(<s:property value="accountAppealId"/>)' />		
						  </s:if>	
						  	 <s:if test="appealSuggestion!=null">	
						  	 <img title=" 已处理" style="cursor: pointer;"
								src="/etc/images/icon_msn.gif"
								onclick='gotoView(<s:property value="accountAppealId"/>)' />		
						  	 </s:if>		
						</td>
					</tr>
				</s:iterator>
			</table>
				<table class="page_table" width="98%" align="center" cellpadding="0"
				cellspacing="0" border="0">
				<tr>
					<td>
						<s:set name="form_name" value="'acctAppealInfoForm'"
							scope="request"></s:set>
						<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
					</td>
				</tr>
			</table>
		</s:form>
		</div>
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
	</body>
</html>

