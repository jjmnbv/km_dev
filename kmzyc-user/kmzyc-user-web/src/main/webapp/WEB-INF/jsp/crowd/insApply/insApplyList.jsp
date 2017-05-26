<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false"%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<script type="text/javascript" src="/etc/js/pageCommon.js"></script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css">


<title>审核机构申请记录管理列表</title>
<!--<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">-->
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">

<style type="text/css">
.tableStyle1 {
	font-size: 12px;
}

</style>

</head>
<s:set name="parent_name" value="'机构管理'" scope="request"/>
<s:set name="name" value="'机构审核列表'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<body>
	<s:form id="auditSpreaderListForm" name="auditSpreaderListForm" method="post" action="/crowd/listInsApp.action" >
		<!-- 查询条件区域 -->
		<table width="98%" class="content_table"  cellpadding="0" align="center" cellspacing="0">
			<tr height="30px">
				<td align="left">机构名称：<input name="institutionApplyRecordCriteria.institutionName" type="text" 
					class="input_style" value="<s:property value='institutionApplyRecordCriteria.institutionName' />"></td>
				<td align="left">联系电话：<input name="institutionApplyRecordCriteria.institutionPhonenumber"  type="text" 
					class="input_style" value="<s:property value='institutionApplyRecordCriteria.institutionPhonenumber' />"></td>
				
				<td align="left">机构编码：<input name="institutionApplyRecordCriteria.institutionCode"  type="text" 
					class="input_style" value="<s:property value='institutionApplyRecordCriteria.institutionCode' />"></td>
                    				<td align="left">审核状态：<select name="institutionApplyRecordCriteria.auditStatus">
						<option <s:if test="institutionApplyRecordCriteria.auditStatus == 0">selected="selected"</s:if> value="0">审核中</option>
						<option <s:if test="institutionApplyRecordCriteria.auditStatus == 2">selected="selected"</s:if>  value="2">审核拒绝</option>
					</select>	
					</td>
                    				<td>创建时间：<input
					type="text" id="d523" readonly class="Wdate" size="15" style="width: 158px;"
					value="<s:date name = 'institutionApplyRecordCriteria.createStart' format='yyyy-MM-dd HH:mm:ss' />"
					name = 'institutionApplyRecordCriteria.createStart'
					onclick="WdatePicker({el:'d523',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />至：
<input
					type="text" id="d524" readonly class="Wdate" size="15" style="width: 158px;"
					value="<s:date name = 'institutionApplyRecordCriteria.createEnd' format='yyyy-MM-dd HH:mm:ss' />"
					name = 'institutionApplyRecordCriteria.createEnd'
					onclick="WdatePicker({el:'d524',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
				</td>
                				<td align="right" rowspan="2" ><INPUT TYPE="submit" class="queryBtn"
					value=""></td>
			</tr>
		</table>
	

		<!-- 数据列表区域 -->
		<table width="98%" class="list_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
			<tr>
				
				<th align="center" width="12%">机构编码</th>
				<th align="center" width="7%">机构名称</th>
				<th align="center" width="10%">机构地址</th>
				<th align="center" width="10%">机构联系人</th>
				<th align="center" width="12%">联系电话</th>
				<th align="center" width="12%">业务员</th>
				<th align="center" width="10%">创建时间</th>
				<th align="center" width="12%">审核状态</th>
				<th align="center" width="10%">操作</th>
			</tr>
			<s:iterator value="page.dataList">
				<tr>
					
					<td align="center"><s:property value="institutionCode"/></td>
					<td align="center"><s:property escape="false" value="institutionName"/></td>
					<td align="center"><s:property escape="false" value="institutionAddress"/></td>
					<td align="center"><s:property escape="false" value="institutionContactor"/></td>
					<td align="center"><s:property value="institutionPhonenumber"/></td>
					<td align="center"><s:property value="bagmanName"/></td>
					<td align="center"> 
					
					<s:date name="createDate" format="yyyy-MM-dd HH:mm:ss"/>
					
					</td> 
					<td align="center">
						<s:if test="auditeState == 0">审核中</s:if>
						<s:if test="auditeState == 1">审核通过</s:if>
						<s:if test="auditeState == 2">审核拒绝</s:if>
					
					</td>
					<td align="center">
							<s:if test="auditeState == 0"><img title="审核" style="cursor: pointer;" src="/etc/images/icon/kaitong.png" onClick="getDetail(<s:property value="id"/>)"></s:if>
							<s:if test="auditeState != 0"><img title="查看" style="cursor: pointer;" src="/etc/images/icon/search.png" onClick="getDetail(<s:property value="id"/>)"></s:if>
					</td>
				</tr>
			</s:iterator>
			
		</table>
		<table width="98%" align="center" class="page_table">
				<tr>
					<td><s:set name="form_name" value="'auditSpreaderListForm'"
							scope="request"></s:set> <jsp:include
							page="/WEB-INF/jsp/common/page.jsp"></jsp:include></td>
				</tr>
			</table>
		</s:form>
		<!-- 消息提示页面 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
</body>
<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript">

    /**  查看详情   **/
    function getDetail(inviteId){
    	location.href="getAuditInsAppDetail.action?aid="+inviteId
    }


</script>
</html>

