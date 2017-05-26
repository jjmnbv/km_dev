<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
    <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>产品供应商</title>
    <link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" src="/etc/js/jquery-latest.pack.js"></script>
    <script type="text/javascript" src="/etc/js/common.js"></script>
    <style type="text/css">
    .tableStyle1 {
        font-size: 12px;
    }
    </style>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>

	<s:set name="parent_name" value="'供应商管理'" scope="request" />
	<s:set name="name" value="'供应商店铺列表'" scope="request" />
	<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

	<s:form action="/app/supplierShopList.action" method="POST"
		id="frm" name='frm'>
        <input type="hidden" id="rtnMsg" value="<s:property value="rtnMessage"/>" />
		<!-- 查询条件区域 -->
		<table width="98%" class="content_table" align="center"
			cellpadding="0" cellspacing="0">

			<tr>
				<!-- 根据查询字段的多少判断colspan-->
			<%--  	<td width="80%" valign="middle" colspan="4"><INPUT
					class="addBtn" TYPE="button" value="" onclick="gotoAdd();">
					<input class="delBtn" type="button" value=""
					onclick="gotoDel('supplierId');" />--%>
			</tr>
			<tr>
				<td>店铺标题：&nbsp; &nbsp; <s:textfield
						name="searchShopMain.shopTitle" cssClass="input_style" /></td>
				<td>店铺名称：&nbsp;&nbsp;<s:textfield
						name="searchShopMain.shopName" cssClass="input_style" /></td>
				<td>商户名称：&nbsp;&nbsp;<s:textfield
						name="searchShopMain.corporateName" cssClass="input_style" /></td>
			</tr>
			<tr>
				<td>店铺状态：&nbsp; &nbsp;  
					<s:select name="searchShopMain.status" headerKey="" headerValue="--全部--"
                              list="#{0:'关闭',1:'开启'}"></s:select>
				</td>
				<td>审核状态：&nbsp; &nbsp;
                    <s:select list="#request.suppliersStatusMap" name="searchShopMain.auditStatus"
                              id="channel" headerKey=""  style="width:111px" headerValue="--请选择--"></s:select>
				</td>
				<td>
                    <input type="submit" class="queryBtn" value="">&nbsp;&nbsp;
				    <input type="button" onClick="exportSupplierShopInfo()" class="btn-custom" value="导出">
				</td>
			</tr>
		</table>

		<!-- 数据列表区域 -->
		<table width="98%" class="list_table" align="center" cellpadding="3"
			cellspacing="0" border="1" bordercolor="#C1C8D2">
			<tr>
				<th align="center" width="5%">
                    <input type='checkbox' id='allbox' name='allbox' onclick='checkAll(this)'/>
				</th>
				<th align="center" width="10%">店铺标题</th>
				<th align="center" width="19%">店铺名称</th>
				<th align="center" width="10%">店铺状态</th>
				<th align="center" width="10%">审核状态</th>
				<th align="center" width="10%">申请时间</th>
				<th align="center" width="10%">商户名称</th>
				<th align="center" width="10%">操作</th>
			</tr>
			<s:iterator id="supplieriterator" value="page.dataList">
				<tr>
					<td align="center" width="5%"><input type="checkbox"
						id="check" name="supplierId"
						value='<s:property value="supplierId"/>'></td>
					<td align="center"><s:property value="shopTitle" />
					</td>
					<td align="center"><s:property value="shopName" /></td>
					<td align="center"><s:if test="status == 0">关闭</s:if><s:if test="status == 1">开启</s:if></td>
					<td align="center">
					<s:if test="auditStatus == 1">
					申请中，未审核
					</s:if>
					<s:elseif test="auditStatus == 2">
					审核通过
					</s:elseif>
					<s:elseif test="auditStatus == 3">
					审核未通过
					</s:elseif>
					<s:elseif test="auditStatus == 4">
					永久关闭
					</s:elseif>
					<s:elseif test="auditStatus == 0">
					编辑
					</s:elseif>
					</td>
					<td align="center">
					<s:date name="applyTime" format="yyyy-MM-dd" />
					</td>
					<td align="center"><s:property value="corporateName" /></td>
					<td align="center">
					<s:if test="auditStatus == 1">
					<img
						title="审核" style="cursor: pointer;"
						src="/etc/images/button_new/view.png"
						onclick="gotoView(<s:property value='shopId'/>)" />
						</s:if>
						<%--<s:if test="auditStatus == 2">
							<img type="button" style="cursor: pointer;" title="永久关闭" src="/etc/images/button_new/stopuse.png" onclick="closeSupplierShopStatus(<s:property value='shopId'/>)">
						</s:if>
						<s:if test="auditStatus == 3 || auditStatus == 4">
							<img type="button" style="cursor: pointer;" title="启用店铺" value="管理商品" src="/etc/images/button_new/upshelf.png" onclick="openSupplierShopStatus(<s:property value='shopId'/>)">
						</s:if>--%>
						<img title="详情" style="cursor: pointer;" src="/etc/images/button_new/select.png"
                             onclick="gotoUpdate(<s:property value='shopId'/>)" />
					</td>
				</tr>
			</s:iterator>
		</table>
		<!-- 分页按钮区 -->
		<table width="98%" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td>
                    <%@ include file="/WEB-INF/jsp/public/pager.jsp"%>
				</td>
			</tr>
		</table>
		<br>
		<br>


	</s:form>
	<form name="frm"  method="post" >
        <input type="hidden"  name="pageNum"  value="<s:property value='pageNum' />"   />
        <input type="hidden"  name="searchShopMain.shopTitle"  value="<s:property value='searchShopMain.shopTitle' />"   />
        <input type="hidden"  name="searchShopMain.shopName"  value="<s:property value='searchShopMain.shopName' />"   />
        <input type="hidden"  name="searchShopMain.status"  value="<s:property value='searchShopMain.status' />"   />
        <input type="hidden"  name="searchShopMain.auditStatus"  value="<s:property value='searchShopMain.auditStatus' />"   />
   </form>
<s:if test='!rtnMessage.isEmpty()'>
	<SCRIPT LANGUAGE="JavaScript">
		alert(document.getElementById("rtnMsg").value);
	</SCRIPT>
</s:if>

<script language="JavaScript">
//返回我的桌面界面
function gotoList(){
    location.href="/basedata/gotoSysMain.action";
}

function gotoAdd(){
    document.frm.action="/basedata/addSupplier.action";
    document.frm.submit();
}

function gotoUpdate(id){
    var pageNum=$("#pageNo option:selected").val();
    document.forms[0].action="/app/gotoSupplierShopUpdate.action?shopId="+id+"&pageNum="+pageNum;
    document.forms[0].submit();
}

function gotoView(id){
    var pageNum=$("#pageNo option:selected").val();
	document.forms[0].action="/app/gotoSupplierShopView.action?shopId="+id+"&pageNum="+pageNum;
	document.forms[0].submit();
}

function closeSupplierShopStatus(id){
    var answer = confirm("确认永久关闭吗?");
    if (!answer) {
        return false;
    }
    $.ajax({
        url: '/app/supplierShopStatus.action',
        async:false,
        data: 'shopId='+id,
        success: function(info) {
            if('0' == info){
                alert("系统异常，停用失败!");
                return;
            }else{
                alert("关店成功!");
                document.forms[1].action= "/app/supplierShopList.action";
                document.forms[1].submit();
            }
        }
    });
}

function openSupplierShopStatus(id) {
    var answer = confirm("确认启用店铺吗?");
    if (!answer) {
        return false;
    }
    $.ajax({
        url: '/app/supplierShopStatus.action',
        async: false,
        data: 'shopId=' + id + "&ckType=" + 1,
        success: function (info) {
            if ('0' == info) {
                alert("系统异常，启用失败!");
                return;
            } else {
                alert("启用成功!");
                document.forms[1].action = "/app/supplierShopList.action";
                document.forms[1].submit();
            }
        }
    });

}

function exportSupplierShopInfo() {
    $('#frm').attr("action", "/app/exportSupplierShopList.action");
    $('#frm').submit();
    $('#frm').attr("action", "/app/supplierShopList.action");
}
</script>
</body>
</html>