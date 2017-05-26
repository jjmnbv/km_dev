<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>按方抓药审核信息</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
 <script src="/etc/js/dialog.js"></script>
<script type="text/javascript">
    /**  进入详情个人信息页面  **/
    function gotoDetail(id){
  
      location.href="/userInfo/commercialTenantBasicCopy_CommercialTenantBasicCopyDetail.action?commercialCopyId="+id;
    } 
 
    function gotoVerify(id)
    {
      location.href="/userInfo/prescripinfo_toUpdatePurchaseInfo.action?purchaseId="+id;
    }

    function gotoDelete(id)
    {
    	if(confirm('是否确认删除?') == true){
    	  location.href="/userInfo/prescripinfo_deletePurchaseInfo.action?purchaseId="+id;
    	}
    }
    function gotoUpdate(id)
    {
    	 location.href="/userInfo/commercialTenantBasicCopy_CommercialTenantBasicUpdate.action?commercialCopyId="+id;
        }
</script>
</head>
<body>
<!-- 标题条 -->
<s:set name="parent_name" value="'药方管理'" scope="request" />
<s:set name="name" value="'药方审核信息列表'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<div  style="height:90%;overflow-y:auto; " >
<s:form name="prescripinfoForm" action="/userInfo/prescripinfo_selectPageList.action" 
    onsubmit=" return checkAllTextValid(this)"  method="post">
    <s:token/>
<!-- 查询条件区域 -->
<table width="98%" class="content_table" align="center" cellpadding="0" cellspacing="0" >
	<tr>
 		<td colspan="8">
 		</td>
 	</tr>
	<tr>
		<td align="right">会员账号：</td>
		<td>
		     <input name="purchaseListDO.loginAccount" type="text" value="<s:property value='purchaseListDO.loginAccount'/>">
		</td>
		<td align="right">手机号码：</td>
		<td>
		     <input name="purchaseListDO.mobile" type="text" value="<s:property value='purchaseListDO.mobile'/>">
		</td>
		<td align="right">药方名称：</td>
		<td>
		     <input name="purchaseListDO.presName" type="text" value="<s:property value='purchaseListDO.presName'/>">
		</td>
		<td align="right">状态：</td>
		<td>
		    <select name="purchaseListDO.presStatus">
									<option value="" <s:if test='purchaseListDO.presStatus==""'>selected="selected"</s:if>>
										所有
									</option>
									<option value="0" <s:if test='purchaseListDO.presStatus=="0"'>selected="selected"</s:if>>
										草稿
									</option>
									<option value="1" <s:if test='purchaseListDO.presStatus=="1"'>selected="selected"</s:if>>
								   待审核
									</option>
									<option value="2" <s:if test='purchaseListDO.presStatus=="2"'>selected="selected"</s:if>>
								   审核通过
						         </option>
								<option value="3" <s:if test='purchaseListDO.presStatus=="3"'>selected="selected"</s:if>>
								   审核不通过
						         </option>
							      </select>
		</td>
		 			<td align="right" colspan="4">
			<INPUT TYPE="submit" class="queryBtn" value="">
		</td>
	</tr>
</table>
<!-- 数据列表区域 -->
<table width="98%" class="list_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
	<tr>
		<th align="center" >会员账号</th>
		<th align="center" >手机号码</th>
		<th align="center" >药方名称</th>
		<th align="center" >状态</th>
		<th align="center" >审核情况</th>
		<th align="center" >操作</th>
	</tr>
	<s:iterator id="accountiterator"  value="page.dataList">
	<tr> 
		<td align="center">
		     <s:property value="loginAccount"/>
		</td>
		<td align="center">
		   <s:property value="mobile"/>
		</td>
	 
		<td align="center">
		     <s:property value="presName"/>
		</td>
			<td align="center">
		    <s:if test="presStatus==0">草稿</s:if>
		 	<s:if test="presStatus==1">待审核</s:if>
	        <s:if test="presStatus==2">审核通过</s:if>
	        <s:if test="presStatus==3">审核不通过</s:if>
		</td>
		
		
			<td align="center">	
		 	 <s:property value="reviewDescription"/>
		</td>
		
		<td>

		<img title="审核" style="cursor: pointer;" src="/etc/images/u177_normal.png"  onclick="gotoVerify(<s:property value="purchaseId"/>)" />
        <img title="删除" style="cursor: pointer;" src="/etc/images/u173_normal.png"  onclick="gotoDelete(<s:property value="purchaseId"/>)" />
		
		
		</td>
	</tr>
	</s:iterator>
</table>

<table width="98%" align="center" class="page_table">
	<tr>
		<td>
			<s:set name="form_name"  value="'prescripinfoForm'"  scope="request"></s:set>
			<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
		</td>
	</tr>
</table>
</s:form>
</div>
<!-- 消息提示页面 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
</body>
</html>

