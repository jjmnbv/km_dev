<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="/etc/js/qtip/jquery.min.1.8.3.js"></script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet"
	type="text/css" />
<script src="/etc/js/jquery-latest.pack.js"></script>
<script src="/etc/js/dialog.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<title>点评管理</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<script type="text/javascript">
    /**  进入处理咨询的页面  **/
	function gotoAnser(consultId)
	{
		//alert("/app/gotoConsultReply.action?pre_consultId="+consultId);
		location.href="/app/gotoConsultReply.action?pre_consultId="+consultId;
	}
    /** 进入审核的页面 **/
    function gotoCheck(consultId)
    {
    	location.href="/app/gotoConsultCheck.action?pre_consultId="+consultId;
    }
	/**  自动审核**/
	function check_byself()
	{
		location.href="/app/openCheck_byself.action";
	}
	/**查看咨询详情**/
	function gotoViewConsult(consultId)
	{
		  dialog("查看审核明细","iframe:/app/gotoViewConsult.action?pre_consultId="+consultId,"600px","400px","iframe");
	}
</script>
</head>
<body>
<s:set name="parent_name" value="'客户信息'" scope="request" />
<s:set name="name" value="'客户参与信息'" scope="request" />
<s:set name="son_name" value="'点评'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<div  style="height:90%;overflow-y:auto; " >
	<s:form name="consultForm" method="post" action="/customers/participate_queryPageConsult.action">
		<!-- 查询条件区域 -->
		<table width="98%"  class="content_table" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td width="8%" align="left">产品名称：</td>
				<td width="15%" align="left"><input
					name="searchConsut.productName" type="text" class="input_style"
					value="<s:property value='searchConsut.productName'/>"></td>
				<td width="8%" align="left">咨询内容：</td>
				<td width="15%" align="left"><input
					name="searchConsut.consultContent" type="text" class="input_style"
					value="<s:property value='searchConsut.consultContent'/>"></td>
				<td width="8%" align="left">回复状态：</td>
				<td width="25%" align="left">
                <label for="select"></label>
                </td>
				<td width="3%" align="center"><INPUT TYPE="submit"
					class="queryBtn" value=""></td>
			</tr>
			<tr>
				<td height="28" align="left">咨询时间：</td>
			  <td align="left"><input name="searchConsut.consultStart"
					type="text" class="input_style" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" 
					value=""></td>
				<td align="left">到</td>
				<td align="left"><input name="searchConsut.consultEnd"
					type="text" class="input_style" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" 
					value=""></td>
				<td align="left">咨询类型：</td>
				<td align="left">
                <label for="select2"></label>
			    </td>
			</tr>
		</table>
		<!-- 数据列表区域 -->
		<table width="98%" class="list_table" align="center" cellpadding="3"
			cellspacing="0" border="1" >
			<tr>
				<th align="center" width="8%">产品名称</th>
				<th width="6%" align="center">咨询人</th>
				<th width="10%" align="center">咨询类型</th>
				<th width="8%" align="center">咨询内容</th>
				<th width="15%" align="center">回复状态</th>
				<th width="12%" align="center">审核内容</th>
				<th width="12%" align="center">审核状态</th>
				<th width="13%" align="center">咨询时间</th>
				<th width="28%" align="center">操作</th>
			</tr>
			<s:iterator id="custiterator" value="page.dataList">
				<tr>
					<td align="center" width="8%"><s:property value="productName" /></td>
					<td align="center"><s:property value="custNickname" /></td>
					<td align="center"> <!--<s:property   value="#request.ConsultTypeMap[type]"  />--></td>
					<td align="center"><s:property value="consultContent" /></td>
					<td align="center">
                    <!--    <s:property   value="#request.ConsultReplyMap[replyStatus]"  /> -->
					</td>
					<td align="center">  <s:property   value="replyContent"  /></td>
					<td align="center">  
                 <!--<s:property   value="#request.ConsultCheckMap[checkStatus]"  />--></td>
					<td align="center"><s:date name="consultDate"
							format="yyyy-MM-dd" /></td>
					<td align="center">
                     <input TYPE="hidden" style="cursor: pointer;" value="<s:property value='consultId'/>" >
              <s:if test="replyStatus==0"> 
                     <input TYPE="button" style="cursor: pointer;" value=" 回复 " onClick="gotoAnser(<s:property value='consultId'/>)">  
			  </s:if>
			       	
						&nbsp;&nbsp;
						    <s:if test="checkStatus==0 &&replyStatus==1"> 
						<input TYPE="button" style="cursor: pointer;" value="审核"
						 onClick="gotoCheck(<s:property value='consultId'/>)"> 
				  </s:if>
                 <input TYPE="button" style="cursor: pointer;" value="查看"
						 onClick="gotoViewConsult(<s:property value='consultId'/>)"> 
				</tr>
			</s:iterator>
		</table>

		<!-- 分页按钮区 -->
			<table class="page_table" width="98%" align="center" cellpadding="0"
				cellspacing="0" border="0">
				<tr>
					<td>
						<s:set name="form_name" value="'consultForm'" scope="request"></s:set>
						<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
					</td>
				</tr>
			</table>
	</s:form>
	</div>
</body>
</html>

