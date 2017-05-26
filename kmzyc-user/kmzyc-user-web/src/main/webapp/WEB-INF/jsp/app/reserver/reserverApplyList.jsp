<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>预备金审核列表</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.min.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript">
    function gotoDetail(applyNotesId,editId){
    	location.href="/userInfo/reserverApplyInfo_queryServerApplyDetail.action?applyNotesId="+applyNotesId+"&&editId="+editId;
    }
	   
   
</script>
</head>
<body>

<s:set name="parent_name" value="'预备金管理'" scope="request" />
<s:set name="name" value="'预备金审核列表'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>


<!-- 查询条件区域 -->
<s:form name="reserverApplyForm"  action="/userInfo/reserverApplyInfo_PageList.action" method="post">
<table  width="98%" class="content_table" align="center" cellpadding="0" cellspacing="0" >
	<tr>
		<td align="right">申请人账号:</td>
		<td><input name="reserverApplyInfo.accountLogin" type="text" value="<s:property value='reserverApplyInfo.accountLogin'/>"></td>
		<td align="right">申请状态:</td>
		<td><select name="reserverApplyInfo.status" ><option value="" <s:if test='reserverApplyInfo.status==""'>selected="selected"</s:if>>所有 </option>
		<option value="1" <s:if test='reserverApplyInfo.status=="1"'>selected="selected"</s:if>>待审核 </option>
		<option value="2" <s:if test='reserverApplyInfo.status=="2"'>selected="selected"</s:if>>审核通过 </option>
		<option value="3" <s:if test='reserverApplyInfo.status=="3"'>selected="selected"</s:if>>审核拒绝</option></select></td>
		<td align="right">申请时间:</td>
		<td><input type="text" name="reserverApplyInfo.startDate" value ="<s:date name = 'reserverApplyInfo.startDate' format='yyyy-MM-dd HH:mm:ss' />" id="d523" class="Wdate"       onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'d521\')}'})"  />
		至<input type="text" name="reserverApplyInfo.endDate" value ="<s:date name = 'reserverApplyInfo.endDate' format='yyyy-MM-dd HH:mm:ss' />" id="d521" class="Wdate"     onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'d523\')}'})" /></td>	
			<td align="right">联系电话:</td>
		<td><input name="reserverApplyInfo.phone" type="text" value="<s:property value='reserverApplyInfo.phone'/>"></td>
		<td align="right">申请类型:</td>
		<td style="padding-left: 2px"><select name="reserverApplyInfo.applyType" style="width:75"><option value="" <s:if test='reserverApplyInfo.applyType==""'>selected="selected"</s:if>>所有</option>
		<option value="1" <s:if test='reserverApplyInfo.applyType=="1"'>selected="selected"</s:if>> 开通 </option>
		<option value="2" <s:if test='reserverApplyInfo.applyType=="2"'>selected="selected"</s:if>>变更 </option></select></td>
		<td align="right"><INPUT TYPE="submit" class="queryBtn" value="">&nbsp;&nbsp;&nbsp;&nbsp;</td>	
    </tr>
</table>


<!-- 数据列表区域 -->
<table width="98%" class="list_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
	<tr>
		<th align="center" >申请人账号</th>
		<th align="center" >申请公司</th>
		<th align="center" >申请额度</th>
		<th align="center" >原本额度</th>
		<th align="center" >还款周期</th>
		<th align="center" >联系电话</th>
		<th align="center" >申请类型</th>
		<th align="center" >审核状态</th>
		<th align="center" >申请时间</th>
		<th align="center" >操作</th>
	</tr>
  <s:iterator id="custiterator" value="page.dataList">
	<tr>
		<td align="center">
		  <s:property value="accountLogin" />
		</td>
		<td align="center">
		  <s:property value="corporateName" />
		</td>
		<td align="center">
		  ￥<s:property value="%{formatDouble(applyLimit)}" />
		</td>
		<td align="center">
		  ￥<s:property value="%{formatDouble(originalLimit)}" />
		</td>
		<td align="center">
		  <s:if test="settlementType==1">
		  		月度结算
		  </s:if>
		  <s:elseif test="settlementType==2">
		  		季度结算
		  </s:elseif>
		  <s:elseif test="settlementType==3">
		  		半年结算
		  </s:elseif>
		  <s:elseif test="settlementType==4">
		  		年度结算
		  </s:elseif>
		</td>
		<td align="center">
		  <s:property value="phone" />
		</td>
		<td align="center">
		  <s:if test="%{applyType==1}">开通</s:if>
		  <s:else>变更</s:else>
		</td>
		<td align="center">
			<s:if test="%{status==1}">
			      待审核
			</s:if>
			<s:elseif test="%{status==2}">
			    审核通过
			</s:elseif>
			<s:elseif test="%{status==3}">
			    审核拒绝
			</s:elseif>
		</td>
		<td align="center">
		    <s:date name = "applyDate" format="yyyy-MM-dd HH:mm:ss"/>
		</td>
		<td width="100px;">
		   <s:if test="%{status==1}">
		   		<img title="查看" style="cursor: pointer;" src="/etc/images/icon/search.png"  onclick="gotoDetail(<s:property value="applyNotesId"/>,1)"/>
				<img title="修改" style="cursor: pointer;" src="/etc/images/icon/xiugai.png"  onclick="gotoDetail(<s:property value="applyNotesId"/>,2)"/>
				<img title="审核" style="cursor: pointer;" src="/etc/images/icon/shenhe.png"  onclick="gotoDetail(<s:property value="applyNotesId"/>,3)"/>
		   </s:if> <s:else>
		   		<img title="查看" style="cursor: pointer;" src="/etc/images/icon/search.png"  onclick="gotoDetail(<s:property value="applyNotesId"/>,1)"/>
		   </s:else>
			
		</td>
		</tr>
	</s:iterator>
</table>
<table width="98%" align="center" class="page_table">
	<tr>
		<td>
			<s:set name="form_name"  value="'reserverApplyForm'"  scope="request"></s:set>
			<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
		</td>
	</tr>
</table>
</s:form>
<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
</body>
</html>

