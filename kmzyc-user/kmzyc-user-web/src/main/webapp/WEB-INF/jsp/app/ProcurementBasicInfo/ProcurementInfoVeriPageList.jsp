<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>采购商信息变更审核</title>
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
        location.href="/userInfo/commercialTenantBasicCopy_commercialTenantBasicCopyVerify.action?commercialCopyId="+id;
    }

    function gotoUpdate(id)
    {
    	 location.href="/userInfo/commercialTenantBasicCopy_CommercialTenantBasicUpdate.action?commercialCopyId="+id;
        }
</script>
</head>
<body>
<!-- 标题条 -->
<s:set name="parent_name" value="'采购商管理'" scope="request" />
<s:set name="name" value="'采购商信息变更审核列表'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<div  style="height:90%;overflow-y:auto; " >
<s:form name="CommercialTenantBasicCopyForm" action="/userInfo/commercialTenantBasicCopy_VeriPageList.action" 
    onsubmit=" return checkAllTextValid(this)"  method="post">
    <s:token/>
<!-- 查询条件区域 -->
<table width="98%" height="130" class="content_table" align="center" cellpadding="0" cellspacing="0" >
	<tr>
 		<td colspan="8">
 		</td>
 	</tr>
	<tr>
		<td align="right">会员账号：</td>
		<td>
		     <input name="commercialTenantBasicCopyDO.loginName" type="text" value="<s:property value='commercialTenantBasicCopyDO.loginName'/>">
		</td>
		<td align="right">联系人姓名：</td>
		<td>
		     <input name="commercialTenantBasicCopyDO.contactsName" type="text" value="<s:property value='commercialTenantBasicCopyDO.contactsName'/>">
		</td>
		<td align="right">手机号码：</td>
		<td>
		     <input name="commercialTenantBasicCopyDO.mobile" type="text" value="<s:property value='commercialTenantBasicCopyDO.mobile'/>">
		</td>
		<td align="right">公司名称：</td>
		<td>
		     <input name="commercialTenantBasicCopyDO.corporateName" type="text" value="<s:property value='commercialTenantBasicCopyDO.corporateName'/>">
		</td>
		
	</tr>
		<tr>
		<td align="right">工商注册号：</td>
		<td>
		     <input name="commercialTenantBasicCopyDO.businessLicenceRegister" type="text" value="<s:property value='commercialTenantBasicCopyDO.businessLicenceRegister'/>">
		</td>
		<td align="right">申请状态：</td>
		<td>
		  		<select name="commercialTenantBasicCopyDO.reviewChange">
									<option value="" <s:if test='commercialTenantBasicCopyDO.reviewChange==""'>selected="selected"</s:if>>
										请选择
									</option>
									<option value="0" <s:if test='commercialTenantBasicCopyDO.reviewChange=="0"'>selected="selected"</s:if>>
										 提交申请
									</option>
									<option value="1" <s:if test='commercialTenantBasicCopyDO.reviewChange=="1"'>selected="selected"</s:if>>
								   审核通过
									</option>
								<option value="2" <s:if test='commercialTenantBasicCopyDO.reviewChange=="2"'>selected="selected"</s:if>>
								   审核不通过
						         </option>
							      </select>
		</td>
	</tr>
	<tr>
	 
 			<td align="right" colspan="4">
			<INPUT TYPE="submit" class="queryBtn" value="">
		</td>
	</tr>
</table>
<!-- 数据列表区域 -->
<table width="98%" class="list_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
	<tr>
		<th align="center" >会员账号</th>
		<th align="center" >联系人姓名</th>
		<th align="center" >手机号码</th>
		<th align="center" >信用等级</th>
		<th align="center" >公司名称</th>
		<th align="center" >工商注册号</th>
		<th align="center" >营业执照有效期</th>
		<th align="center" >成立日期</th>
 	    <th align="center" >变更审核状态</th>
 	    <th align="center" >审核备注</th>
		<th align="center" >操作</th>
	</tr>
	<s:iterator id="accountiterator"  value="page.dataList">
	<tr> 
		<td align="center">
		     <s:property value="loginName"/>
		</td>
		<td align="center">
		   <s:property value="contactsName"/>
		</td>
	 
		<td align="center">
		     <s:property value="mobile"/>
		</td>
			<td align="center">
		     <s:property value="creditRating"/>
		</td>
		<td align="center">
		     <s:property value="corporateName"/>
		</td>	
		<td align="center">
		     <s:property value="businessLicenceRegister"/>
		</td>
		<td align="center">
		     从	<s:date   name="blinceStartdate"  format="yyyy-MM-dd" />至<s:date   name="blinceEnddate"  format="yyyy-MM-dd" />
		</td>
		
		<td align="center">
		<s:date   name="foundDate"  format="yyyy-MM-dd" />
		</td>
			<td align="center">	
		 	<s:if test="reviewChange==0">提交申请</s:if>
	        <s:if test="reviewChange==1">审核通过</s:if>
	        <s:if test="reviewChange==2">审核不通过</s:if>
		</td>
		<td align="center">	
		
			 
		     <s:property value="description"/>
	 
		</td>
	 
		<td>
		     <img title="详情" style="cursor: pointer;" src="/etc/images/u175_normal.png"  onclick="gotoDetail(<s:property value="commercialCopyId"/>)"/>
		<s:if test="reviewChange==0">	
   <img title="修改" style="cursor: pointer;" src="/etc/images/u171_normal.png"  onclick="gotoUpdate(<s:property value="commercialCopyId"/>)" />
			<img title="审核" style="cursor: pointer;" src="/etc/images/u177_normal.png"  onclick="gotoVerify(<s:property value="commercialCopyId"/>)" />
		 </s:if>
		</td>
	</tr>
	</s:iterator>
</table>

<table width="98%" align="center" class="page_table">
	<tr>
		<td>
			<s:set name="form_name"  value="'CommercialTenantBasicCopyForm'"  scope="request"></s:set>
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

